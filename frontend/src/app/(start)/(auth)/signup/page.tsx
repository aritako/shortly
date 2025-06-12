'use client';

import Page from '@/components/custom/Page';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { useSignUp } from '@/features/auth/hooks';
import { SignUpFormType, signUpFormSchema } from '@/schemas/auth';
import { Label } from '@/components/ui/label';
import { useRouter } from 'next/navigation';
import React from 'react';
import { useForm, SubmitHandler } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import Link from 'next/link';

export default function SignUpPage() {
  const router = useRouter();
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<SignUpFormType>({
    resolver: zodResolver(signUpFormSchema),
  });

  const onSubmit: SubmitHandler<SignUpFormType> = async (data) => {
    try {
      const responseData = await useSignUp(data);
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
          <span className="font-bold text-4xl">Create an Account</span>
          <span className="text-muted-foreground">
            Enter your information to get started.
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
            <Label htmlFor="email">Email</Label>
            <Input
              id="email"
              placeholder="john.doe@example.com"
              required
              type="email"
              className="m-0"
              {...register('email')}
            />
            {errors.email && (
              <span className="text-xs text-red-500">
                {errors.email.message}
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
          <div className="w-full space-y-2">
            <Label htmlFor="confirm-password">Confirm Password</Label>
            <Input
              id="confirm-password"
              required
              type="password"
              className="m-0"
              {...register('confirmPassword')}
            />
            {errors.confirmPassword && (
              <span className="text-xs text-red-500">
                {errors.confirmPassword.message}
              </span>
            )}
          </div>
          <Button className="w-full cursor-pointer my-1">Sign Up</Button>
          <span className="text-sm text-muted-foreground">
            Already have an account?{' '}
            <Link href="/login" className="text-primary hover:underline">
              Log In
            </Link>
          </span>
        </div>
      </form>
    </Page>
  );
}
