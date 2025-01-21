import { defineStore } from 'pinia'
import { ref } from 'vue'

export interface UserInfo {
  id: number
  username: string
  role: 'admin' | 'employee'
  avatar?: string
  name: string
  email?: string
  department?: string
  position?: string
}

export const useUserStore = defineStore('user', () => {
  const userInfo = ref<UserInfo | null>(null)
  const token = ref<string | null>(null)
  const isAuthenticated = ref(false)

  function setUserInfo(info: UserInfo | null) {
    userInfo.value = info
  }

  function setToken(newToken: string | null) {
    token.value = newToken
    isAuthenticated.value = !!newToken
    if (newToken) {
      localStorage.setItem('token', newToken)
    } else {
      localStorage.removeItem('token')
    }
  }

  function logout() {
    setUserInfo(null)
    setToken(null)
  }

  // 初始化时检查本地存储的token
  const storedToken = localStorage.getItem('token')
  if (storedToken) {
    setToken(storedToken)
  }

  return {
    userInfo,
    token,
    isAuthenticated,
    setUserInfo,
    setToken,
    logout
  }
}) 