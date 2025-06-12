'use client';
import { createContext, useContext, useState, useCallback } from 'react';
import api from '@/lib/axios';
import AuthClientProvider from '@/contexts/AuthClientProvider';

interface AuthContextType {
  token: string | null;
  isAuthenticated: boolean;
  setToken: (token: string) => void;
  logout: () => void;
  refreshToken: () => Promise<boolean>;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export function AuthProvider({ children }: { children: React.ReactNode }) {
  const [token, setTokenState] = useState<string | null>(null);

  const setToken = useCallback((token: string) => {
    setTokenState(token);
  }, []);

  const logout = useCallback(() => {
    setTokenState(null);
    api.post('/api/auth/logout', {});
  }, []);

  const refreshToken = useCallback(async () => {
    try {
      const response = await api.post('/api/auth/refresh', {});
      if (response.status !== 200) return false;
      const data = response.data;
      if (data.accessToken) {
        setTokenState(data.accessToken);
        return true;
      }
      return false;
    } catch {
      return false;
    }
  }, []);

  const isAuthenticated = !!token;

  return (
    <AuthContext.Provider
      value={{ token, isAuthenticated, setToken, logout, refreshToken }}
    >
      <AuthClientProvider>{children}</AuthClientProvider>
    </AuthContext.Provider>
  );
}

export function useAuth() {
  const context = useContext(AuthContext);
  if (context === undefined) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
}
