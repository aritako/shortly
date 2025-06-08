import { SignUpFormType } from './schemas';

export async function useSignUp(data: SignUpFormType) {
  console.log('Signing up with data:', data);
  const response = await fetch(
    `${process.env.NEXT_PUBLIC_BASE_API}/api/auth/register`,
    {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(data),
    }
  );
  if (!response.ok) {
    const errorData = await response.json();
    throw new Error(errorData.message || 'Failed to sign up!');
  }
  const responseData = await response.json();
  return responseData;
}
