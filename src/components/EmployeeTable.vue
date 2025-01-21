<template>
  <div class="employee-table">
    <el-table :data="employees" border style="width: 100%">
      <el-table-column prop="username" label="用户名" />
      <el-table-column prop="name" label="姓名" />
      <el-table-column prop="department" label="部门" />
      <el-table-column prop="position" label="职位" />
      <el-table-column prop="joinDate" label="入职日期">
        <template #default="{ row }">
          {{ formatDate(row.joinDate) }}
        </template>
      </el-table-column>
      <el-table-column prop="status" label="状态">
        <template #default="{ row }">
          <el-tag :type="row.status === 'active' ? 'success' : 'danger'">
            {{ row.status === 'active' ? '在职' : '离职' }}
          </el-tag>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import type { Employee } from '@/api/employee';
import * as employeeApi from '@/api/employee';

const employees = ref<Employee[]>([]);

const loadEmployees = async () => {
  try {
    const { data } = await employeeApi.fetchEmployees();
    employees.value = data;
  } catch (error) {
    console.error('获取员工列表失败:', error);
  }
};

const formatDate = (date: string) => {
  return new Date(date).toLocaleDateString('zh-CN');
};

onMounted(() => {
  loadEmployees();
});
</script>

<style scoped>
.employee-table {
  margin: 20px;
}
</style>