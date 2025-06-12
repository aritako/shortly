class ApiClient {
  private baseUrl: string;

  constructor() {
    this.baseUrl = process.env.NEXT_PUBLIC_BASE_API || '';
  }

  async fetchWithAuth(
    url: string,
    options: RequestInit = {},
    token?: string,
    refreshTokenFn?: () => Promise<boolean>,
    getToken?: () => string | null
  ): Promise<Response> {
    let headers: Record<string, string> = Object.assign(
      { 'Content-Type': 'application/json' },
      options.headers ? (options.headers as Record<string, string>) : {}
    );
    if (token) {
      headers['Authorization'] = `Bearer ${token}`;
    }

    let response = await fetch(`${this.baseUrl}${url}`, {
      ...options,
      headers,
      credentials: 'include',
    });

    // If unauthorized, try to refresh token and retry once
    if (response.status === 401 && refreshTokenFn && getToken) {
      const refreshed = await refreshTokenFn();
      if (refreshed) {
        const newToken = getToken();
        if (newToken) {
          headers['Authorization'] = `Bearer ${newToken}`;
          response = await fetch(`${this.baseUrl}${url}`, {
            ...options,
            headers,
            credentials: 'include',
          });
        }
      }
    }
    return response;
  }

  async get(
    url: string,
    options: RequestInit = {},
    token?: string,
    refreshTokenFn?: () => Promise<boolean>,
    getToken?: () => string | null
  ) {
    return this.fetchWithAuth(
      url,
      { ...options, method: 'GET' },
      token,
      refreshTokenFn,
      getToken
    );
  }

  async post(
    url: string,
    data: any,
    options: RequestInit = {},
    token?: string,
    refreshTokenFn?: () => Promise<boolean>,
    getToken?: () => string | null
  ) {
    return this.fetchWithAuth(
      url,
      {
        ...options,
        method: 'POST',
        body: JSON.stringify(data),
      },
      token,
      refreshTokenFn,
      getToken
    );
  }

  async put(
    url: string,
    data: any,
    options: RequestInit = {},
    token?: string,
    refreshTokenFn?: () => Promise<boolean>,
    getToken?: () => string | null
  ) {
    return this.fetchWithAuth(
      url,
      {
        ...options,
        method: 'PUT',
        body: JSON.stringify(data),
      },
      token,
      refreshTokenFn,
      getToken
    );
  }

  async delete(
    url: string,
    options: RequestInit = {},
    token?: string,
    refreshTokenFn?: () => Promise<boolean>,
    getToken?: () => string | null
  ) {
    return this.fetchWithAuth(
      url,
      { ...options, method: 'DELETE' },
      token,
      refreshTokenFn,
      getToken
    );
  }
}

export const apiClient = new ApiClient();
