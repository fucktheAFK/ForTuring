<template>
  <div class="salary">
    <el-card>
      <template #header>
        <div class="card-header">
          <h2>工资查询</h2>
          <el-date-picker
            v-model="selectedMonth"
            type="month"
            placeholder="选择月份"
            format="YYYY年MM月"
            @change="loadSalaryData"
          />
        </div>
      </template>

      <el-descriptions :column="2" border>
        <el-descriptions-item label="基本工资">{{ salary.base }}</el-descriptions-item>
        <el-descriptions-item label="绩效工资">{{ salary.performance }}</el-descriptions-item>
        <el-descriptions-item label="加班工资">{{ salary.overtime }}</el-descriptions-item>
        <el-descriptions-item label="满勤奖">{{ salary.attendance }}</el-descriptions-item>
        <el-descriptions-item label="社保扣除">-{{ salary.insurance }}</el-descriptions-item>
        <el-descriptions-item label="个税扣除">-{{ salary.tax }}</el-descriptions-item>
        <el-descriptions-item label="实发工资" :span="2">
          <span class="total-salary">{{ calculateTotal }}</span>
        </el-descriptions-item>
      </el-descriptions>

      <div class="salary-chart" v-if="showChart">
        <h3>近6个月工资趋势</h3>
        <el-chart :option="chartOption" />
      </div>
    </el-card>
  </div>
</template>

<script lang="ts" setup>
import { ref, computed } from 'vue';
import type { SalaryDetail } from '@/types';
import { ElMessage } from 'element-plus';

const selectedMonth = ref(new Date());
const showChart = ref(true);

const salary = ref<SalaryDetail>({
  base: 0,
  performance: 0,
  overtime: 0,
  attendance: 0,
  insurance: 0,
  tax: 0
});

const calculateTotal = computed(() => {
  const { base, performance, overtime, attendance, insurance, tax } = salary.value;
  return base + performance + overtime + attendance - insurance - tax;
});

const loadSalaryData = async () => {
  try {
    const response = await fetch(`/api/salary?month=${selectedMonth.value.toISOString()}`);
    if (!response.ok) throw new Error('获取工资数据失败');
    salary.value = await response.json();
  } catch (error) {
    ElMessage.error('获取工资数据失败');
  }
};

// 图表配置
const chartOption = computed(() => ({
  tooltip: {
    trigger: 'axis'
  },
  xAxis: {
    type: 'category',
    data: ['1月', '2月', '3月', '4月', '5月', '6月']
  },
  yAxis: {
    type: 'value'
  },
  series: [{
    data: [10000, 12000, 11000, 13000, 12500, 13500],
    type: 'line',
    smooth: true
  }]
}));

// 初始加载当月数据
loadSalaryData();
</script>

<style scoped>
.salary {
  max-width: 1000px;
  margin: 0 auto;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.total-salary {
  font-size: 1.2em;
  font-weight: bold;
  color: #409EFF;
}

.salary-chart {
  margin-top: 20px;
  padding-top: 20px;
  border-top: 1px solid #EBEEF5;
}
</style> 