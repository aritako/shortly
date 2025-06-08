'use client';

import Page from '@/components/custom/Page';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import React from 'react';
import { useForm, SubmitHandler } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { Label } from '@/components/ui/label';
import { loginFormSchema, LoginFormType } from '@/features/auth/schemas';
import Link from 'next/link';
import { useLogin } from '@/features/auth/hooks';
import { useRouter } from 'next/navigation';

export default function LoginPage() {
  const router = useRouter();
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm({
    resolver: zodResolver(loginFormSchema),
  });

  const onSubmit: SubmitHandler<LoginFormType> = async (data) => {
    try {
      const responseData = await useLogin(data);
      if (responseData) {
        localStorage.setItem('accessToken', responseData.accessToken);
        router.push('/dashboard');
      }
    } catch (error) {
      console.error('An error occurred:', error);
    }
  };

  return (
    <Page className="max-w-md">
      <form onSubmit={handleSubmit(onSubmit)}>
        <div className="flex flex-col items-center gap-2">
          <span className="font-bold text-4xl">Welcome Back.</span>
          <span className="text-muted-foreground">
            Enter your credentials to login your account.
          </span>
        </div>
        <div className="my-4 space-y-4 flex flex-col items-center gap-2">
          <div className="w-full space-y-2">
            <Label htmlFor="username">Username</Label>
            <Input
              id="username"
              placeholder="coolguy123"
              className="m-0"
              {...register('username')}
            />
            {errors.username && (
              <span className="text-xs text-red-500">
                {errors.username.message}
              </span>
            )}
          </div>
          <div className="w-full space-y-2">
            <Label htmlFor="password">Password</Label>
            <Input
              id="password"
              required
              type="password"
              className="m-0"
              {...register('password')}
            />
            {errors.password && (
              <span className="text-xs text-red-500">
                {errors.password.message}
              </span>
            )}
          </div>
          <Button className="w-full cursor-pointer my-1">Login</Button>
          <span className="text-sm text-muted-foreground">
            Don't have an account?{' '}
            <Link href="/signup" className="text-primary hover:underline">
              Sign Up
            </Link>
          </span>
        </div>
      </form>
    </Page>
  );
}
