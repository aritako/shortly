import type { Metadata } from 'next';
import { geistSans, geistMono } from '@/lib/fonts';
import './globals.css';
import Navbar from '@/features/landing/Navbar';

export const metadata: Metadata = {
  title: 'Shortly',
  description: 'No BS Link Shortener',
};
export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en">
      <body
        className={`${geistSans.variable} ${geistMono.variable} antialiased`}
      >
        <Navbar />
        {children}
      </body>
    </html>
  );
}
