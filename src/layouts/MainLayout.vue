<template>
  <div class="layout">
    <header class="header">
      <div class="logo">人力资源管理系统</div>
      <nav class="nav">
        <router-link to="/">首页</router-link>
        <router-link to="/profile">个人信息</router-link>
        <router-link v-if="isAdmin" to="/employee-management">员工管理</router-link>
      </nav>
      <div class="user-info">
        <img :src="userInfo?.avatar || '/default-avatar.png'" class="avatar" />
        <span>{{ userInfo?.username }}</span>
        <button @click="handleLogout">退出登录</button>
      </div>
    </header>
    
    <main class="main">
      <router-view></router-view>
    </main>
  </div>
</template>

<script setup lang="ts">
import { useAuthStore } from '@/stores/auth'
import { useRouter } from 'vue-router'
import { storeToRefs } from 'pinia'

const router = useRouter()
const authStore = useAuthStore()
const { isAdmin, userInfo } = storeToRefs(authStore)

const handleLogout = () => {
  authStore.logout()
  router.push('/login')
}
</script>

<style scoped>
.layout {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

.header {
  display: flex;
  align-items: center;
  padding: 1rem 2rem;
  background-color: #fff;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.nav {
  flex: 1;
  margin: 0 2rem;
}

.nav a {
  margin-right: 1rem;
  text-decoration: none;
  color: #333;
}

.nav a.router-link-active {
  color: #1890ff;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 1rem;
}

.avatar {
  width: 32px;
  height: 32px;
  border-radius: 50%;
}

.main {
  flex: 1;
  padding: 2rem;
  background-color: #f0f2f5;
}
</style> 