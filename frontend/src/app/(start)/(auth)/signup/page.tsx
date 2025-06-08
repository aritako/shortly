'use client';

import Page from '@/components/custom/Page';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { useSignUp } from '@/features/auth/hooks';
import { SignUpFormType, signUpFormSchema } from '@/features/auth/schemas';
import { Label } from '@radix-ui/react-label';
import { useRouter } from 'next/navigation';
import React from 'react';
import { useForm, SubmitHandler } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';

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
      <form onSubmit={handleSubmit(onSubmit)} className="flex flex-col gap-4">
        <div className="flex flex-col items-center gap-2">
          <span className="font-bold text-4xl">Create an Account</span>
          <span className="text-muted-foreground">
            Enter your information to get started.
          </span>
        </div>
        <div>
          <div>
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
        </div>
        <div className="space-y-4">
          <div className="space-y-2">
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
          <div className="space-y-2">
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
          <div className="space-y-2">
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
        </div>
      </form>
    </Page>
  );
}
