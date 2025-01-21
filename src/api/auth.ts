import request from '@/utils/request';

interface LoginData {
  username: string;
  password: string;
}

export const login = (data: LoginData) => 
  request.post('/auth/login', null, { params: data });

export const logout = () => 
  request.post('/auth/logout'); 