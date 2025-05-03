import React from 'react';

interface PageProps {
  children: React.ReactNode;
}

export default function Page({ children }: PageProps) {
  return (
    <div className="flex w-full justify-center p-16">
      <div className="max-w-5xl w-full">{children}</div>
    </div>
  );
}
