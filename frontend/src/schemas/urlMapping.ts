import { z } from 'zod';

export const urlMappingSchema = z.object({
  id: z.number(),
  originalUrl: z.string().url(),
  shortCode: z.string().min(1),
  clickCount: z.number().int().min(0),
  createdAt: z.string().datetime(),
  userId: z.number(),
});

export type UrlMapping = z.infer<typeof urlMappingSchema>;
