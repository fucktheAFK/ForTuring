<template>
  <div class="profile">
    <div class="profile-header">
      <div class="avatar-section">
        <div class="avatar-container">
          <img :src="userInfo?.avatar || '/default-avatar.png'" class="avatar" />
          <div class="avatar-upload">
            <label for="avatar-input" class="upload-label" :class="{ loading: loading.avatar }">
              <i class="upload-icon">{{ loading.avatar ? '⌛' : '📷' }}</i>
            </label>
            <input 
              type="file" 
              id="avatar-input" 
              accept="image/*"
              @change="handleAvatarChange" 
              class="hidden"
              :disabled="loading.avatar"
            />
          </div>
        </div>
        <h2>{{ userInfo?.username }}</h2>
        <p class="employee-id">工号：{{ profile.employeeId }}</p>
      </div>
    </div>

    <div class="profile-content">
      <!-- 基本信息卡片 -->
      <div class="profile-card" :class="{ loading: loading.profile }">
        <div class="card-header">
          <h3>基本信息</h3>
          <button @click="toggleEdit" class="edit-button" :disabled="loading.profile">
            {{ editMode.basic ? '保存' : '编辑' }}
          </button>
        </div>
        <div class="info-grid">
          <div class="info-item">
            <label>姓名</label>
            <input 
              v-if="editMode.basic" 
              v-model="profile.name"
              class="edit-input"
            />
            <span v-else>{{ profile.name }}</span>
          </div>
          <div class="info-item">
            <label>部门</label>
            <input 
              v-if="editMode.basic" 
              v-model="profile.department"
              class="edit-input"
              disabled
            />
            <span v-else>{{ profile.department }}</span>
          </div>
          <div class="info-item">
            <label>职位</label>
            <input 
              v-if="editMode.basic" 
              v-model="profile.position"
              class="edit-input"
              disabled
            />
            <span v-else>{{ profile.position }}</span>
          </div>
          <div class="info-item">
            <label>入职日期</label>
            <span>{{ formatDate(profile.joinDate) }}</span>
          </div>
        </div>
      </div>

      <!-- 工资信息卡片 -->
      <div class="profile-card" :class="{ loading: loading.salary }">
        <div class="card-header">
          <h3>工资信息</h3>
          <select 
            v-model="selectedMonth" 
            class="month-select"
            :disabled="loading.salary"
          >
            <option v-for="month in months" :key="month" :value="month">
              {{ month }}
            </option>
          </select>
        </div>
        <div class="salary-details">
          <div class="salary-item">
            <span class="label">基本工资</span>
            <span class="amount">¥{{ salary.base }}</span>
          </div>
          <div class="salary-item">
            <span class="label">绩效工资</span>
            <span class="amount">¥{{ salary.performance }}</span>
          </div>
          <div class="salary-item">
            <span class="label">加班工资</span>
            <span class="amount">¥{{ salary.overtime }}</span>
          </div>
          <div class="salary-item">
            <span class="label">满勤奖励</span>
            <span class="amount">¥{{ salary.attendance }}</span>
          </div>
          <div class="salary-item">
            <span class="label">社保扣除</span>
            <span class="amount">-¥{{ salary.insurance }}</span>
          </div>
          <div class="salary-item">
            <span class="label">个税扣除</span>
            <span class="amount">-¥{{ salary.tax }}</span>
          </div>
          <div class="salary-item total">
            <span class="label">实发工资</span>
            <span class="amount">¥{{ totalSalary }}</span>
          </div>
        </div>
      </div>

      <!-- 考勤记录卡片 -->
      <div class="profile-card" :class="{ loading: loading.attendance }">
        <div class="card-header">
          <h3>考勤记录</h3>
        </div>
        <div class="attendance-list">
          <div v-for="record in attendanceRecords" :key="record.date" class="attendance-item">
            <span class="date">{{ record.date }}</span>
            <span :class="['status', record.status]">{{ record.statusText }}</span>
            <span class="time">{{ record.checkIn }} - {{ record.checkOut }}</span>
          </div>
        </div>
      </div>

      <!-- 请假记录卡片 -->
      <div class="profile-card">
        <div class="card-header">
          <h3>请假记录</h3>
          <button 
            @click="showLeaveDialog = true" 
            class="add-button"
            :disabled="loading.leave"
          >
            申请请假
          </button>
        </div>
        <div class="leave-list">
          <div v-for="leave in leaveRecords" :key="leave.id" class="leave-item">
            <div class="leave-info">
              <span class="leave-type">{{ leave.type }}</span>
              <span class="leave-date">{{ formatDate(leave.startDate) }} 至 {{ formatDate(leave.endDate) }}</span>
            </div>
            <span :class="['leave-status', leave.status]">{{ leave.statusText }}</span>
          </div>
        </div>
      </div>
    </div>

    <!-- 请假申请对话框 -->
    <div class="dialog-overlay" v-if="showLeaveDialog">
      <div class="dialog">
        <h3>请假申请</h3>
        <form @submit.prevent="handleLeaveSubmit">
          <div class="form-group">
            <label>请假类型</label>
            <select v-model="leaveForm.type" required>
              <option value="annual">年假</option>
              <option value="sick">病假</option>
              <option value="personal">事假</option>
            </select>
          </div>
          <div class="form-group">
            <label>开始日期</label>
            <input type="date" v-model="leaveForm.startDate" required />
          </div>
          <div class="form-group">
            <label>结束日期</label>
            <input type="date" v-model="leaveForm.endDate" required />
          </div>
          <div class="form-group">
            <label>请假原因</label>
            <textarea v-model="leaveForm.reason" required></textarea>
          </div>
          <div class="dialog-buttons">
            <button 
              type="button" 
              @click="showLeaveDialog = false"
              :disabled="loading.leave"
            >
              取消
            </button>
            <button type="submit" :disabled="loading.leave">
              {{ loading.leave ? '提交中...' : '提交' }}
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, computed } from 'vue'
import { useAuthStore } from '@/stores/auth'
import { storeToRefs } from 'pinia'
import * as employeeApi from '@/api/employee'
import * as leaveApi from '@/api/leave'
import * as uploadApi from '@/api/upload'
import { ElMessage } from 'element-plus'

interface AttendanceRecord {
  date: string
  status: 'normal' | 'late' | 'early' | 'absent'
  statusText: string
  checkIn: string
  checkOut: string
}

interface LeaveRecord {
  id: number
  type: string
  startDate: string
  endDate: string
  status: 'pending' | 'approved' | 'rejected'
  statusText: string
}

interface SalaryDetail {
  base: number
  performance: number
  overtime: number
  attendance: number
  insurance: number
  tax: number
}

const authStore = useAuthStore()
const { userInfo } = storeToRefs(authStore)

// 个人信息
const profile = reactive({
  employeeId: '',
  name: '',
  username: '',
  department: '',
  position: '',
  joinDate: '',
  status: 'active' as const
})

// 编辑模式状态
const editMode = reactive({
  basic: false
})

// 工资信息
const selectedMonth = ref(new Date().toISOString().slice(0, 7))
const months = Array.from({ length: 6 }, (_, i) => {
  const date = new Date()
  date.setMonth(date.getMonth() - i)
  return date.toISOString().slice(0, 7)
})

const salary = reactive<SalaryDetail>({
  base: 0,
  performance: 0,
  overtime: 0,
  attendance: 0,
  insurance: 0,
  tax: 0
})

const totalSalary = computed(() => {
  const { base, performance, overtime, attendance, insurance, tax } = salary
  return base + performance + overtime + attendance - insurance - tax
})

// 考勤记录
const attendanceRecords = ref<AttendanceRecord[]>([])

// 请假记录
const leaveRecords = ref<LeaveRecord[]>([])

// 请假表单
const showLeaveDialog = ref(false)
const leaveForm = reactive({
  type: 'annual',
  startDate: '',
  endDate: '',
  reason: ''
})

// 加载状态
const loading = reactive({
  profile: false,
  salary: false,
  attendance: false,
  avatar: false,
  leave: false
})

// 方法
const loadProfileData = async () => {
  if (!userInfo.value?.id) return
  
  try {
    loading.profile = true
    const { data } = await employeeApi.getProfile(userInfo.value.id)
    Object.assign(profile, data)
  } catch (error) {
    console.error('获取个人信息失败:', error)
    ElMessage.error('获取个人信息失败')
  } finally {
    loading.profile = false
  }
}

const loadSalaryData = async () => {
  if (!userInfo.value?.id) return
  
  try {
    loading.salary = true
    const { data } = await employeeApi.getEmployeeSalary(userInfo.value.id)
    Object.assign(salary, data)
  } catch (error) {
    console.error('获取工资信息失败:', error)
    ElMessage.error('获取工资信息失败')
  } finally {
    loading.salary = false
  }
}

const loadAttendanceData = async () => {
  if (!userInfo.value?.id) return
  
  try {
    loading.attendance = true
    const { data } = await employeeApi.getAttendance(userInfo.value.id)
    attendanceRecords.value = data
  } catch (error) {
    console.error('获取考勤记录失败:', error)
    ElMessage.error('获取考勤记录失败')
  } finally {
    loading.attendance = false
  }
}

const handleLeaveSubmit = async () => {
  if (!userInfo.value?.id) return
  
  try {
    loading.leave = true
    await leaveApi.submitLeaveRequest({
      ...leaveForm,
      userId: userInfo.value.id
    })
    
    showLeaveDialog.value = false
    Object.assign(leaveForm, {
      type: 'annual',
      startDate: '',
      endDate: '',
      reason: ''
    })
    ElMessage.success('请假申请提交成功')
  } catch (error) {
    console.error('提交请假申请失败:', error)
    ElMessage.error('提交请假申请失败')
  } finally {
    loading.leave = false
  }
}

const formatDate = (date: string) => {
  return new Date(date).toLocaleDateString('zh-CN')
}

// 初始化
onMounted(() => {
  loadProfileData()
  loadSalaryData()
  loadAttendanceData()
})

const handleAvatarChange = async (event: Event) => {
  const input = event.target as HTMLInputElement
  if (!input.files?.length) return

  try {
    loading.avatar = true
    const file = input.files[0]
    const { data } = await uploadApi.uploadAvatar(file)
    
    if (userInfo.value) {
      userInfo.value.avatar = data.url
    }
    
    ElMessage.success('头像上传成功')
  } catch (error) {
    console.error('上传头像失败:', error)
    ElMessage.error('头像上传失败')
  } finally {
    loading.avatar = false
  }
}

const handleSaveProfile = async () => {
  if (!userInfo.value?.id) return
  
  try {
    await employeeApi.updateProfile({
      ...profile,
      id: userInfo.value.id,
      status: profile.status
    })
    editMode.basic = false
    ElMessage.success('个人信息更新成功')
  } catch (error) {
    console.error('更新个人信息失败:', error)
    ElMessage.error('更新个人信息失败')
  }
}

// 更新模板中的编辑按钮点击事件
const toggleEdit = async () => {
  if (editMode.basic) {
    await handleSaveProfile()
  } else {
    editMode.basic = true
  }
}
</script>

<style scoped>
.profile {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.profile-header {
  background: white;
  border-radius: 8px;
  padding: 2rem;
  margin-bottom: 2rem;
  text-align: center;
}

.avatar-section {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 1rem;
}

.avatar-container {
  position: relative;
  width: 120px;
  height: 120px;
}

.avatar {
  width: 100%;
  height: 100%;
  border-radius: 50%;
  object-fit: cover;
}

.avatar-upload {
  position: absolute;
  right: 0;
  bottom: 0;
}

.upload-label {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 32px;
  height: 32px;
  background: #1890ff;
  border-radius: 50%;
  cursor: pointer;
  color: white;
}

.hidden {
  display: none;
}

.employee-id {
  color: #666;
}

.profile-content {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 1.5rem;
}

.profile-card {
  background: white;
  border-radius: 8px;
  padding: 1.5rem;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1rem;
}

.card-header h3 {
  margin: 0;
  color: #1f2937;
}

.edit-button, .add-button {
  padding: 6px 12px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.edit-button {
  background-color: #1890ff;
  color: white;
}

.add-button {
  background-color: #52c41a;
  color: white;
}

.info-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 1rem;
}

.info-item {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.info-item label {
  color: #666;
  font-size: 0.9em;
}

.edit-input {
  padding: 6px;
  border: 1px solid #d9d9d9;
  border-radius: 4px;
}

.edit-input:disabled {
  background-color: #f5f5f5;
}

.month-select {
  padding: 6px;
  border: 1px solid #d9d9d9;
  border-radius: 4px;
}

.salary-details {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.salary-item {
  display: flex;
  justify-content: space-between;
  padding: 0.5rem 0;
  border-bottom: 1px solid #f0f0f0;
}

.salary-item.total {
  border-top: 2px solid #f0f0f0;
  border-bottom: none;
  font-weight: bold;
  margin-top: 0.5rem;
}

.attendance-list, .leave-list {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.attendance-item, .leave-item {
  display: flex;
  justify-content: space-between;
  padding: 0.5rem 0;
  border-bottom: 1px solid #f0f0f0;
}

.status {
  padding: 2px 8px;
  border-radius: 4px;
  font-size: 0.9em;
}

.status.normal {
  background-color: #e6f7ff;
  color: #1890ff;
}

.status.late {
  background-color: #fff1f0;
  color: #ff4d4f;
}

.leave-status {
  padding: 2px 8px;
  border-radius: 4px;
  font-size: 0.9em;
}

.leave-status.pending {
  background-color: #fff7e6;
  color: #faad14;
}

.leave-status.approved {
  background-color: #f6ffed;
  color: #52c41a;
}

.leave-status.rejected {
  background-color: #fff1f0;
  color: #ff4d4f;
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
.form-group select,
.form-group textarea {
  width: 100%;
  padding: 8px;
  border: 1px solid #d9d9d9;
  border-radius: 4px;
}

.form-group textarea {
  height: 100px;
  resize: vertical;
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

.profile-card.loading {
  position: relative;
  pointer-events: none;
  opacity: 0.7;
}

.profile-card.loading::after {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(255, 255, 255, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
}

.upload-label.loading {
  pointer-events: none;
  opacity: 0.7;
}

button:disabled {
  opacity: 0.7;
  cursor: not-allowed;
}
</style> 