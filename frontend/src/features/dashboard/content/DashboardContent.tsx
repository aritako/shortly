'use client';
import React, { useEffect } from 'react';
import DashboardContentCard from './DashboardContentCard';
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from '@/components/ui/card';
import LinksTable from '../links-table/LinksTable';
import axios from '@/lib/axios';
import { useIsAuthReady } from '@/stores/authStore';
import { useQuery } from '@tanstack/react-query';
import { columns } from '../links-table/columns';

export default function DashboardContent() {
  const isAuthReady = useIsAuthReady();
  const { data, isLoading, error } = useQuery({
    queryKey: ['urls'],
    queryFn: async () => {
      const res = await axios.get('/api/url');
      return res.data;
    },
    enabled: isAuthReady,
  });

  return (
    <div className="space-y-4">
      <div className="grid md:grid-cols-2 lg:grid-cols-4 gap-4">
        <DashboardContentCard
          title="Total Links"
          value="4"
          description="+2 from last month"
        />
        <DashboardContentCard
          title="Total Clicks"
          value="4698"
          description="+12% from last month"
        />
        <DashboardContentCard
          title="Avg Clicks / Link"
          value="1175"
          description="+5% from last month"
        />
        <DashboardContentCard
          title="This Month"
          value="847"
          description="clicks this month"
        />
      </div>
      <Card>
        <CardHeader>
          <CardTitle className="text-sm font-medium">Your Links</CardTitle>
          <CardDescription className="text-xs text-muted-foreground">
            Manage and track your shortened links here.
          </CardDescription>
        </CardHeader>
        <CardContent>
          <LinksTable columns={columns} data={data?.urls ?? []} />
        </CardContent>
      </Card>
    </div>
  );
}
