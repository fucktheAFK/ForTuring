<template>
  <div class="employee-management">
    <div class="page-header">
      <h2>员工管理</h2>
      <button class="add-button" @click="showAddDialog = true">添加员工</button>
    </div>

    <!-- 搜索区域 -->
    <div class="search-area">
      <div class="search-inputs">
        <input 
          v-model="searchParams.keyword" 
          placeholder="搜索姓名/用户名" 
          class="search-input"
        />
        <select v-model="searchParams.department" class="search-select">
          <option value="">所有部门</option>
          <option v-for="dept in departments" :key="dept" :value="dept">
            {{ dept }}
          </option>
        </select>
        <select v-model="searchParams.status" class="search-select">
          <option value="">所有状态</option>
          <option value="active">在职</option>
          <option value="resigned">离职</option>
        </select>
      </div>
      <button class="search-button" @click="handleSearch">搜索</button>
    </div>

    <!-- 员工列表 -->
    <div class="table-container">
      <table>
        <thead>
          <tr>
            <th>用户名</th>
            <th>姓名</th>
            <th>部门</th>
            <th>职位</th>
            <th>入职日期</th>
            <th>状态</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="employee in employees" :key="employee.id">
            <td>{{ employee.username }}</td>
            <td>{{ employee.name }}</td>
            <td>{{ employee.department }}</td>
            <td>{{ employee.position }}</td>
            <td>{{ formatDate(employee.joinDate) }}</td>
            <td>
              <span :class="['status', employee.status]">
                {{ employee.status === 'active' ? '在职' : '离职' }}
              </span>
            </td>
            <td>
              <button class="action-button edit" @click="handleEdit(employee)">编辑</button>
              <button 
                class="action-button delete" 
                @click="handleDelete(employee)"
                v-if="employee.status === 'active'"
              >
                删除
              </button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- 添加/编辑员工对话框 -->
    <div class="dialog-overlay" v-if="showAddDialog || showEditDialog">
      <div class="dialog">
        <h3>{{ showEditDialog ? '编辑员工' : '添加员工' }}</h3>
        <form @submit.prevent="handleSubmit">
          <div class="form-group">
            <label>用户名</label>
            <input v-model="formData.username" required :disabled="showEditDialog" />
          </div>
          <div class="form-group">
            <label>姓名</label>
            <input v-model="formData.name" required />
          </div>
          <div class="form-group">
            <label>部门</label>
            <select v-model="formData.department" required>
              <option v-for="dept in departments" :key="dept" :value="dept">
                {{ dept }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label>职位</label>
            <input v-model="formData.position" required />
          </div>
          <div class="form-group">
            <label>入职日期</label>
            <input type="date" v-model="formData.joinDate" required />
          </div>
          <div class="dialog-buttons">
            <button type="button" @click="closeDialog">取消</button>
            <button type="submit">确定</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import * as employeeApi from '@/api/employee'
import type { Employee } from '@/api/employee'

// 部门列表
const departments = ['技术部', '人事部', '财务部', '市场部', '销售部']

// 搜索参数
const searchParams = reactive({
  keyword: '',
  department: '',
  status: ''
})

// 表单数据
const formData = reactive({
  name: '',
  username: '',
  department: '',
  position: '',
  joinDate: '',
  status: 'active' as const
})

// 状态变量
const showAddDialog = ref(false)
const showEditDialog = ref(false)
const employees = ref<Employee[]>([])
const currentEmployee = ref<Employee | null>(null)
const loading = ref(false)

// 方法
const handleSearch = async () => {
  try {
    loading.value = true
    const { data } = await employeeApi.fetchEmployees()
    employees.value = data
  } catch (error) {
    console.error('获取员工列表失败:', error)
  } finally {
    loading.value = false
  }
}

const handleEdit = (employee: Employee) => {
  currentEmployee.value = employee
  Object.assign(formData, {
    name: employee.name,
    username: employee.username,
    department: employee.department,
    position: employee.position,
    joinDate: employee.joinDate
  })
  showEditDialog.value = true
}

const handleDelete = async (employee: Employee) => {
  if (!employee.id || !confirm('确定要删除该员工吗？')) return
  
  try {
    await employeeApi.deleteEmployee(employee.id)
    employees.value = employees.value.filter(e => e.id !== employee.id)
  } catch (error) {
    console.error('删除失败:', error)
  }
}

const handleSubmit = async () => {
  try {
    if (showEditDialog.value && currentEmployee.value?.id) {
      await employeeApi.updateEmployee({
        ...formData,
        id: currentEmployee.value.id,
        status: currentEmployee.value.status
      })
      const index = employees.value.findIndex(e => e.id === currentEmployee.value?.id)
      if (index !== -1) {
        employees.value[index] = {
          ...currentEmployee.value,
          ...formData
        }
      }
    } else {
      const { data } = await employeeApi.addEmployee({
        ...formData,
        status: 'active'
      })
      employees.value.push(data)
    }
    closeDialog()
  } catch (error) {
    console.error('保存失败:', error)
  }
}

const closeDialog = () => {
  showAddDialog.value = false
  showEditDialog.value = false
  currentEmployee.value = null
  Object.assign(formData, {
    name: '',
    username: '',
    department: '',
    position: '',
    joinDate: '',
    status: 'active' as const
  })
}

const formatDate = (date: string) => {
  return new Date(date).toLocaleDateString('zh-CN')
}

// 初始化
onMounted(() => {
  handleSearch()
})
</script>

<style scoped>
.employee-management {
  padding: 20px;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.add-button {
  padding: 8px 16px;
  background-color: #1890ff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.search-area {
  display: flex;
  gap: 10px;
  margin-bottom: 20px;
}

.search-inputs {
  display: flex;
  gap: 10px;
  flex: 1;
}

.search-input, .search-select {
  padding: 8px;
  border: 1px solid #d9d9d9;
  border-radius: 4px;
}

.search-input {
  flex: 1;
}

.search-button {
  padding: 8px 16px;
  background-color: #52c41a;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.table-container {
  background: white;
  border-radius: 8px;
  overflow: auto;
}

table {
  width: 100%;
  border-collapse: collapse;
}

th, td {
  padding: 12px;
  text-align: left;
  border-bottom: 1px solid #f0f0f0;
}

th {
  background-color: #fafafa;
  font-weight: 500;
}

.status {
  padding: 4px 8px;
  border-radius: 4px;
  font-size: 0.9em;
}

.status.active {
  background-color: #e6f7ff;
  color: #1890ff;
}

.status.resigned {
  background-color: #fff1f0;
  color: #ff4d4f;
}

.action-button {
  padding: 4px 8px;
  border: none;
  border-radius: 4px;
  margin-right: 8px;
  cursor: pointer;
}

.action-button.edit {
  background-color: #faad14;
  color: white;
}

.action-button.delete {
  background-color: #ff4d4f;
  color: white;
}

.dialog-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.dialog {
  background: white;
  padding: 24px;
  border-radius: 8px;
  width: 400px;
}

.form-group {
  margin-bottom: 16px;
}

.form-group label {
  display: block;
  margin-bottom: 8px;
}

.form-group input,
.form-group select {
  width: 100%;
  padding: 8px;
  border: 1px solid #d9d9d9;
  border-radius: 4px;
}

.dialog-buttons {
  display: flex;
  justify-content: flex-end;
  gap: 8px;
  margin-top: 24px;
}

.dialog-buttons button {
  padding: 8px 16px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.dialog-buttons button[type="button"] {
  background-color: #f0f0f0;
}

.dialog-buttons button[type="submit"] {
  background-color: #1890ff;
  color: white;
}
</style>
