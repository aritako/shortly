'use client';
import { useEffect } from 'react';
import api from '@/lib/axios';
import { useAuth } from '@/contexts/AuthContext';

export function useAxiosAuth() {
  const { token, refreshToken } = useAuth();

  useEffect(() => {
    // Request interceptor: attach access token
    const requestInterceptor = api.interceptors.request.use(
      (config) => {
        if (token) {
          config.headers = config.headers || {};
          config.headers['Authorization'] = `Bearer ${token}`;
        }
        return config;
      },
      (error) => Promise.reject(error)
    );

    // Response interceptor: handle 401 and refresh
    const responseInterceptor = api.interceptors.response.use(
      (response) => response,
      async (error) => {
        const originalRequest = error.config;
        if (
          error.response &&
          error.response.status === 401 &&
          !originalRequest._retry
        ) {
          originalRequest._retry = true;
          const refreshed = await refreshToken();
          if (refreshed) {
            // Get the new token from context
            const { token: newToken } = useAuth();
            if (newToken) {
              originalRequest.headers['Authorization'] = `Bearer ${newToken}`;
              return api(originalRequest);
            }
          }
        }
        return Promise.reject(error);
      }
    );

    return () => {
      api.interceptors.request.eject(requestInterceptor);
      api.interceptors.response.eject(responseInterceptor);
    };
  }, [token, refreshToken]);
}
