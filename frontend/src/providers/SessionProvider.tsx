'use client';

import { useEffect } from 'react';
import axios from '@/lib/axios';
import { useAuthStore } from '@/stores/authStore';

export default function SessionLoader({
  children,
}: {
  children: React.ReactNode;
}) {
  const setAccessToken = useAuthStore((s) => s.setAccessToken);
  const setIsLoading = useAuthStore((s) => s.setIsLoading);

  useEffect(() => {
    const refreshAccessToken = async () => {
      try {
        const res = await axios.post(
          '/api/auth/refresh',
          {},
          { withCredentials: true }
        );
        setAccessToken(res.data.accessToken);
      } catch (err) {
        console.log('Failed to refresh token', err);
        setAccessToken(null);
      } finally {
        setIsLoading(false);
      }
    };

    refreshAccessToken();
  }, [setAccessToken, setIsLoading]);

  return <>{children}</>;
}
