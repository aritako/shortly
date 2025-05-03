import { Geist, Geist_Mono } from 'next/font/google';

export const geistSans = Geist({
  weight: ['400', '500', '600', '700'],
  variable: '--font-geist-sans',
  subsets: ['latin'],
});

export const geistMono = Geist_Mono({
  weight: ['400', '500', '600', '700'],
  variable: '--font-geist-mono',
  subsets: ['latin'],
});
