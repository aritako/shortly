'use client';
import { ReactNode } from 'react';
import { useAxiosAuth } from '@/hooks/useAxiosAuth';

export default function AuthClientProvider({
  children,
}: {
  children: ReactNode;
}) {
  useAxiosAuth();
  return <>{children}</>;
}
