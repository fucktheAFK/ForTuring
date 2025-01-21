//Home.vue
<template>
  <div class="home">
    <div v-for="leave in leaves" :key="leave.id" class="leave-item">
      <span>{{ leave.type }} - {{ leave.status }}</span>
      <span>{{ formatDate(leave.startDate) }} 至 {{ formatDate(leave.endDate) }}</span>
    </div>

    <div v-for="training in trainings" :key="training.id" class="training-item">
      <div class="training-title">{{ training.title }}</div>
      <div class="training-time">{{ formatDate(training.startDate) }}</div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref } from 'vue';

interface Leave {
  id: number;
  type: string;
  status: string;
  startDate: string;
  endDate: string;
}

interface Training {
  id: number;
  title: string;
  startDate: string;
}

const leaves = ref<Leave[]>([
  {
    id: 1,
    type: '年假',
    status: '已批准',
    startDate: '2024-03-15',
    endDate: '2024-03-22'
  },
  {
    id: 2,
    type: '病假',
    status: '待审批',
    startDate: '2024-04-01',
    endDate: '2024-04-03'
  }
]);

const trainings = ref<Training[]>([
  {
    id: 1,
    title: 'Vue.js 高级课程',
    startDate: '2024-03-25'
  },
  {
    id: 2,
    title: 'TypeScript 入门培训',
    startDate: '2024-04-05'
  }
]);

const formatDate = (date: string): string => {
  const options: Intl.DateTimeFormatOptions = { year: 'numeric', month: '2-digit', day: '2-digit' };
  return new Date(date).toLocaleDateString('zh-CN', options);
};
</script>

<style scoped>
.home {
  max-width: 800px;
  margin: 0 auto;
}

.leave-item,
.training-item {
  margin-bottom: 20px;
  padding: 10px;
  border: 1px solid #dcdcdc;
  border-radius: 4px;
}

.training-title {
  font-weight: bold;
}

.training-time {
  color: #666;
  font-size: 14px;
}
</style>
