import { Home, Settings, LinkIcon, BarChart3, Plus, User } from 'lucide-react';

export const mainItems = [
  {
    title: 'Dashboard',
    url: '/dashboard',
    icon: Home,
  },
  {
    title: 'My Links',
    url: '/dashboard/links',
    icon: LinkIcon,
  },
  {
    title: 'Analytics',
    url: '/dashboard/analytics',
    icon: BarChart3,
  },
  {
    title: 'Create Link',
    url: '/dashboard/create',
    icon: Plus,
  },
];

export const accountItems = [
  {
    title: 'Account',
    url: '/dashboard/account',
    icon: User,
  },
  {
    title: 'Settings',
    url: '/dashboard/settings',
    icon: Settings,
  },
];
