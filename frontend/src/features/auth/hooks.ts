import { SignUpFormType, LoginFormType } from './schemas';

export async function useSignUp(data: SignUpFormType) {
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

export async function useLogin(data: LoginFormType) {
  const response = await fetch(
    `${process.env.NEXT_PUBLIC_BASE_API}/api/auth/login`,
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
    throw new Error(errorData.message || 'Failed to log in!');
  }
  const responseData = await response.json();
  return responseData;
}
