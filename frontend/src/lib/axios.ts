import axios from 'axios';

const api = axios.create({
  baseURL: process.env.NEXT_PUBLIC_BASE_API,
  withCredentials: true, // send cookies
});

export default api;
