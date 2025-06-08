import { cn } from '@/lib/utils';
import React from 'react';

interface PageProps {
  children: React.ReactNode;
  className?: string;
}

export default function Page({ children, className }: PageProps) {
  return (
    <div className="flex w-full justify-center p-12">
      <div className={cn('flex flex-col max-w-5xl w-full gap-4', className)}>
        {children}
      </div>
    </div>
  );
}
