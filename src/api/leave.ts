import request from '@/utils/request';

export interface LeaveRequest {
  id?: number;
  userId: number;
  type: string;
  startDate: string;
  endDate: string;
  reason: string;
  status?: 'pending' | 'approved' | 'rejected';
}

// 管理员接口
export const getPendingLeaveRequests = () => 
  request.get('/admin/leave-requests');

export const approveLeaveRequest = (id: number) => 
  request.put(`/admin/leave-request/approve/${id}`);

export const rejectLeaveRequest = (id: number) => 
  request.put(`/admin/leave-request/reject/${id}`);

// 员工接口
export const submitLeaveRequest = (data: LeaveRequest) => 
  request.post('/employee/leave-request', data); 