import { useEffect } from 'react';
import { useAuthStore } from '@/stores/authStore';

export function useAuthenticatedEffect(
  effectFn: () => void | (() => void),
  deps: any[] = []
) {
  const isLoading = useAuthStore((s) => s.isLoading);
  const accessToken = useAuthStore((s) => s.accessToken);

  useEffect(() => {
    if (isLoading || !accessToken) return;
    return effectFn();
  }, [isLoading, accessToken, ...deps]);
}
