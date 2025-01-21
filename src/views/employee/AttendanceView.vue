//AttendanceView.vue
<template>
  <div class="attendance">
    <el-card>
      <template #header>
        <div class="card-header">
          <h2>考勤记录</h2>
          <el-date-picker
            v-model="selectedMonth"
            type="month"
            placeholder="选择月份"
            format="YYYY年MM月"
            @change="loadAttendanceData"
          />
        </div>
      </template>

      <el-table :data="attendanceRecords" border style="width: 100%">
        <el-table-column prop="date" label="日期" width="120" />
        <el-table-column prop="checkIn" label="上班时间" width="120" />
        <el-table-column prop="checkOut" label="下班时间" width="120" />
        <el-table-column prop="status" label="状态">
          <template #default="{ row }">
            <el-tag :type="getStatusType(row.status as Status)">
              {{ row.status }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="duration" label="工作时长" width="120" />
        <el-table-column prop="remark" label="备注" />
      </el-table>

      <div class="summary-section">
        <el-descriptions :column="4" border>
          <el-descriptions-item label="正常出勤">{{ summary.normal }}天</el-descriptions-item>
          <el-descriptions-item label="迟到">{{ summary.late }}次</el-descriptions-item>
          <el-descriptions-item label="早退">{{ summary.early }}次</el-descriptions-item>
          <el-descriptions-item label="缺勤">{{ summary.absent }}天</el-descriptions-item>
        </el-descriptions>
      </div>
    </el-card>
  </div>
</template>

<script lang="ts" setup>
import { ref } from 'vue';
import { ElMessage } from 'element-plus';

interface AttendanceRecord {
  date: string;
  checkIn: string;
  checkOut: string;
  status: string;
  duration: string;
  remark: string;
}

interface Summary {
  normal: number;
  late: number;
  early: number;
  absent: number;
}

type Status = '正常' | '迟到' | '早退' | '缺勤';

const selectedMonth = ref(new Date());
const attendanceRecords = ref<AttendanceRecord[]>([]);
const summary = ref<Summary>({
  normal: 0,
  late: 0,
  early: 0,
  absent: 0
});

function getStatusType(status: string): 'success' | 'warning' | 'info' | 'primary' | 'danger' {
  // 你的逻辑
  return 'info'; // 返回合法的类型值
}

const loadAttendanceData = async () => {
  try {
    const response = await fetch(`/api/attendance?month=${selectedMonth.value.toISOString()}`);
    if (!response.ok) throw new Error('获取考勤数据失败');
    const data = await response.json();
    attendanceRecords.value = data.records;
    summary.value = data.summary;
  } catch (error) {
    ElMessage.error('获取考勤数据失败');
  }
};

// 初始加载当月数据
loadAttendanceData();
</script>

<style scoped>
.attendance {
  max-width: 1200px;
  margin: 0 auto;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.summary-section {
  margin-top: 20px;
  padding-top: 20px;
  border-top: 1px solid #EBEEF5;
}
</style>
