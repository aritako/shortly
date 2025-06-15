import axios from 'axios';
import { useAuthStore } from '@/stores/authStore';

const instance = axios.create({
  baseURL: process.env.NEXT_PUBLIC_BASE_API,
  withCredentials: true,
});

let isRefreshing = false;
let failedQueue: any[] = [];

const processQueue = (error: any, token: string | null = null) => {
  failedQueue.forEach((prom) => {
    if (error) {
      prom.reject(error);
    } else {
      prom.resolve(token);
    }
  });

  failedQueue = [];
};

instance.interceptors.request.use(
  (config) => {
    const token = useAuthStore.getState().accessToken;
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

instance.interceptors.response.use(
  (res) => res,
  async (error) => {
    const originalRequest = error.config;
    if (error.response?.status === 401 && !originalRequest._retry) {
      originalRequest._retry = true;

      if (isRefreshing) {
        return new Promise((resolve, reject) => {
          failedQueue.push({
            resolve: (token: string) => {
              originalRequest.headers.Authorization = `Bearer ${token}`;
              resolve(instance(originalRequest));
            },
            reject: (err: any) => reject(err),
          });
        });
      }

      isRefreshing = true;

      try {
        const response = await axios.post(
          `${process.env.NEXT_PUBLIC_BACKEND_URL}/auth/refresh`,
          {},
          { withCredentials: true }
        );
        const newToken = response.data.accessToken;

        useAuthStore.getState().setAccessToken(newToken); // ✅ directly update store
        processQueue(null, newToken);

        originalRequest.headers.Authorization = `Bearer ${newToken}`;
        return instance(originalRequest);
      } catch (err) {
        processQueue(err, null);
        return Promise.reject(err);
      } finally {
        isRefreshing = false;
      }
    }

    return Promise.reject(error);
  }
);

export default instance;
