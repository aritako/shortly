import type { Metadata } from 'next';
import { geistSans, geistMono } from '@/lib/fonts';
import './globals.css';
import { AuthProvider } from '@/contexts/AuthContext';
import SessionContext from '@/contexts/SessionContext';

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
        {/* <AuthProvider>{children}</AuthProvider> */}
        <SessionContext>{children}</SessionContext>
      </body>
    </html>
  );
}
