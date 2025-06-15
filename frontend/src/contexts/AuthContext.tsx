'use client';
import { createContext, useContext, useEffect, useState } from 'react';
import axios from '@/lib/axios'; // axios instance with interceptors

interface AuthContextType {
  accessToken: string | null;
  setAccessToken: (token: string | null) => void;
  isLoading: boolean;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider = ({ children }: { children: React.ReactNode }) => {
  const [accessToken, setAccessToken] = useState<string | null>(null);
  const [isLoading, setIsLoading] = useState(true);
  // Auto-refresh on page load if refreshToken exists
  useEffect(() => {
    const refreshAccessToken = async () => {
      try {
        const res = await axios.post(
          '/api/auth/refresh',
          {},
          { withCredentials: true }
        );
        setAccessToken(res.data.accessToken);
        import('@/lib/tokenUtils').then((mod) =>
          mod.setAccessToken(res.data.accessToken)
        );
      } catch (err) {
        console.log('Failed to refresh token', err);
        setAccessToken(null);
      } finally {
        setIsLoading(false);
      }
    };

    refreshAccessToken();
  }, []);

  useEffect(() => {
    console.log('Access token updated:', accessToken);
  }, [accessToken]);

  return (
    <AuthContext.Provider value={{ accessToken, setAccessToken, isLoading }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const ctx = useContext(AuthContext);
  if (!ctx) throw new Error('useAuth must be used within AuthProvider');
  return ctx;
};
