import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import React from 'react';

interface DashboardContentCardProps {
  title: string;
  value: string | number;
  description: string;
}

export default function DashboardContentCard({
  title,
  value,
  description,
}: DashboardContentCardProps) {
  return (
    <Card>
      <CardHeader className="flex flex-row items-center justify-between space-y-0 ">
        <CardTitle className="text-sm font-medium">{title}</CardTitle>
      </CardHeader>
      <CardContent>
        <div className="text-2xl font-bold">{value}</div>
        <p className="text-xs text-muted-foreground">{description}</p>
      </CardContent>
    </Card>
  );
}
