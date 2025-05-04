import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import React from 'react';

export default function ProductDemo() {
  return (
    <div className="border-1 border-slate-200 p-4 w-1/2 flex flex-col gap-4">
      <div className="flex flex-col items-center">
        <h1 className="font-bold text-lg">Shorten a link</h1>
        <h3 className="text-muted-foreground">
          Paste a long URL to create a short link
        </h3>
      </div>
      <Input type="url" placeholder="https://example.com/my-really-long-url" />
      <Button className="cursor-pointer">Shorten</Button>
    </div>
  );
}
