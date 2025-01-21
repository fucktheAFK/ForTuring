// 工资详情
export interface SalaryDetail {
  base: number;
  performance: number;
  overtime: number;
  attendance: number;
  insurance: number;
  tax: number;
}

// 考勤记录
export interface AttendanceRecord {
  date: string;
  status: 'normal' | 'late' | 'early' | 'absent';
  statusText: string;
  checkIn: string;
  checkOut: string;
}

// 请假记录
export interface LeaveRecord {
  id: number;
  type: string;
  startDate: string;
  endDate: string;
  status: 'pending' | 'approved' | 'rejected';
  statusText: string;
}

// 请假申请
export interface LeaveRequest {
  userId: number;
  type: string;
  startDate: string;
  endDate: string;
  reason: string;
}

// 添加培训活动接口
export interface Training {
  id: number;
  title: string;
  startDate: string;
  endDate: string;
  description: string;
  status: string;
} 