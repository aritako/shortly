import Link from 'next/link';
import React from 'react';

export default function Navbar() {
  return (
    <header className="px-4 lg:px-6 h-16 flex items-center border-slate-200 border-b">
      <Link href="/">
        <span className="ml-2 text-xl font-bold">Shortly</span>
      </Link>
    </header>
  );
}
