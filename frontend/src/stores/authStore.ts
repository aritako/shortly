// stores/authStore.ts
import { create } from 'zustand';

interface AuthState {
  accessToken: string | null;
  isLoading: boolean;
  setAccessToken: (token: string | null) => void;
  setIsLoading: (value: boolean) => void;
}

export const useAuthStore = create<AuthState>((set) => ({
  accessToken: null,
  isLoading: true,
  setAccessToken: (token) => set({ accessToken: token }),
  setIsLoading: (value) => set({ isLoading: value }),
}));

export const useIsAuthReady = () =>
  useAuthStore((s) => !s.isLoading && !!s.accessToken);
