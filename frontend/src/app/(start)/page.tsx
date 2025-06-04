'use client';
import Page from '@/components/custom/Page';
import { Button } from '@/components/ui/button';
import ProductDemo from '@/features/landing/ProductDemo';
import { ArrowRight } from 'lucide-react';
import { redirect } from 'next/navigation';
export default function Home() {
  return (
    <Page className="gap-12">
      <section className="flex flex-col items-center gap-4">
        <h1 className="font-bold text-5xl">
          Shorten, share, and track your links.
        </h1>
        <h3 className="text-muted-foreground md:text-lg">
          Create short links, Share them anywhere. No BS.
        </h3>
        <div className="flex gap-2">
          <Button
            onClick={() => redirect('/signup')}
            className="px-8 py-5 min-w-40 cursor-pointer"
          >
            Get Started <ArrowRight />
          </Button>
          <Button
            variant="outline"
            className="px-8 py-5 min-w-40 cursor-pointer"
          >
            Learn More
          </Button>
        </div>
      </section>
      <section className="flex justify-center">
        <ProductDemo />
      </section>
    </Page>
  );
}
