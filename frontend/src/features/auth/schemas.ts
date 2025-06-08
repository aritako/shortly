import { z } from 'zod';

export const signUpFormSchema = z
  .object({
    username: z.string().min(1, 'Username is required'),
    email: z.string().email('Invalid email address'),
    password: z
      .string()
      .min(8, 'Password must be at least 8 characters.')
      .regex(/[0-9]/, 'Password must contain at least one number.')
      .regex(
        /[^A-Za-z0-9]/,
        'Password must contain at least one special character.'
      ),
    confirmPassword: z
      .string()
      .min(8, 'Password must be at least 8 characters.')
      .regex(/[0-9]/, 'Password must contain at least one number.')
      .regex(
        /[^A-Za-z0-9]/,
        'Password must contain at least one special character.'
      ),
  })
  .refine(
    (data: { password: string; confirmPassword: string }) =>
      data.password === data.confirmPassword,
    {
      message: 'Passwords do not match.',
      path: ['confirmPassword'],
    }
  );

export type SignUpFormType = z.infer<typeof signUpFormSchema>;
