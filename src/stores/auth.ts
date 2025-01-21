import { defineStore } from 'pinia';
import * as authApi from '@/api/auth';

interface UserInfo {
  id: number;
  username: string;
  role: 'admin' | 'employee';
  avatar?: string;
  name: string;
  department: string;
  position: string;
  joinDate: string;
}

export const useAuthStore = defineStore('auth', {
  state: () => ({
    user: null as UserInfo | null,
    token: localStorage.getItem('token'),
  }),
  
  getters: {
    isAuthenticated: (state) => !!state.token,
    isAdmin: (state) => state.user?.role === 'admin',
    userInfo: (state) => state.user
  },
  
  actions: {
    async login(username: string, password: string) {
      try {
        const response = await authApi.login({ username, password });
        const { token, user } = response.data;
        
        this.token = token;
        this.user = user;
        localStorage.setItem('token', token);
      } catch (error) {
        console.error('登录失败:', error);
        throw error;
      }
    },
    
    async logout() {
      try {
        await authApi.logout();
      } finally {
        this.token = null;
        this.user = null;
        localStorage.removeItem('token');
      }
    }
  }
}); 