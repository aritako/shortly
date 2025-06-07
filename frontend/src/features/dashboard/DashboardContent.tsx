import React from 'react';
import DashboardContentCard from './DashboardContentCard';

export default function DashboardContent() {
  return (
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
  );
}
