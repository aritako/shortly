'use client';

import Page from '@/components/custom/Page';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { LoginFormType, loginFormSchema } from '@/schemas/auth';
import { zodResolver } from '@hookform/resolvers/zod';
import Link from 'next/link';
import { useRouter, useSearchParams } from 'next/navigation';
import { useForm, SubmitHandler } from 'react-hook-form';
import api from '@/lib/axios';
import { useAuthStore } from '@/stores/authStore';

export default function LoginPage() {
  const router = useRouter();
  const searchParams = useSearchParams();
  const setAccessToken = useAuthStore((s) => s.setAccessToken);

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<LoginFormType>({
    resolver: zodResolver(loginFormSchema),
  });

  const onSubmit: SubmitHandler<LoginFormType> = async (data) => {
    try {
      const response = await api.post('/api/auth/login', data);
      if (response.status === 200) {
        const { accessToken } = response.data;
        setAccessToken(accessToken);
        router.push('/dashboard');
      } else {
        throw new Error('Response not ok from login');
      }
    } catch (error) {
      console.error('Login failed:', error);
    }
  };

  return (
    <Page className="max-w-md">
      <form onSubmit={handleSubmit(onSubmit)}>
        <div className="flex flex-col items-center gap-2">
          <span className="font-bold text-4xl">Welcome Back</span>
          <span className="text-muted-foreground">
            Enter your credentials to access your account.
          </span>
        </div>
        <div className="my-4 space-y-4 flex flex-col items-center gap-2">
          <div className="w-full space-y-2">
            <Label htmlFor="username">Username</Label>
            <Input
              id="username"
              placeholder="coolguy123"
              required
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
          <Button className="w-full cursor-pointer my-1">Log In</Button>
          <div className="flex flex-col items-center gap-2">
            <Link
              href="/forgot-password"
              className="text-sm text-primary hover:underline"
            >
              Forgot your password?
            </Link>
            <span className="text-sm text-muted-foreground">
              Don't have an account?{' '}
              <Link href="/signup" className="text-primary hover:underline">
                Sign Up
              </Link>
            </span>
          </div>
        </div>
      </form>
    </Page>
  );
}
