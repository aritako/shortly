import { useEffect } from 'react';
import { useAuth } from '@/contexts/AuthContext';

export function useAuthenticatedEffect(
  effectFn: () => void | (() => void),
  deps: any[] = []
) {
  const { isLoading, accessToken } = useAuth();

  useEffect(() => {
    if (isLoading || !accessToken) return;
    return effectFn();
  }, [isLoading, accessToken, ...deps]);
}
