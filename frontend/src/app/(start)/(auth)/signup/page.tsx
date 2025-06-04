import Page from '@/components/custom/Page';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Label } from '@radix-ui/react-label';
import React from 'react';

export default function SignUpPage() {
  return (
    <Page className="max-w-md">
      <div className="flex flex-col items-center gap-2">
        <span className="font-bold text-4xl">Create an Account</span>
        <span className="text-muted-foreground">
          Enter your information to get started.
        </span>
      </div>
      <div>
        <div>
          <Label htmlFor="username">Username</Label>
          <Input id="username" placeholder="coolguy123" />
        </div>
      </div>
      <div className="space-y-4">
        <div className="space-y-2">
          <Label htmlFor="email">Email</Label>
          <Input
            id="email"
            placeholder="john.doe@example.com"
            required
            type="email"
          />
        </div>
        <div className="space-y-2">
          <Label htmlFor="password">Password</Label>
          <Input id="password" required type="password" />
        </div>
        <div className="space-y-2">
          <Label htmlFor="confirm-password">Confirm Password</Label>
          <Input id="confirm-password" required type="password" />
        </div>
        <Button className="w-full cursor-pointer">Sign Up</Button>
      </div>
    </Page>
  );
}
