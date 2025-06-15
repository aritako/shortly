'use client';

import { ColumnDef } from '@tanstack/react-table';
import { UrlMapping } from '@/schemas/urlMapping';

export const columns: ColumnDef<UrlMapping>[] = [
  {
    header: 'Created At',
    accessorKey: 'createdAt',
    cell: ({ row }) => {
      const date = new Date(row.original.createdAt);
      return date.toLocaleDateString();
    },
  },
  {
    header: 'Original URL',
    accessorKey: 'originalUrl',
  },
  {
    header: 'Short URL',
    accessorKey: 'shortCode',
  },
  {
    header: 'Click Count',
    accessorKey: 'clickCount',
  },
];
