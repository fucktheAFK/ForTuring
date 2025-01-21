import axios from 'axios'
import type { Employee } from '@/types/employee'
import type { Training } from '@/types'

interface SalaryDetail {
  base: number
  performance: number
  overtime: number
  attendance: number
  insurance: number
  tax: number
}

interface AttendanceRecord {
  date: string
  status: 'normal' | 'late' | 'early' | 'absent'
  statusText: string
  checkIn: string
  checkOut: string
}

const api = axios.create({
  baseURL: '/api'
})

export const fetchEmployees = () => {
  return api.get<Employee[]>('/employees')
}

export const getEmployee = (id: number) => {
  return api.get<Employee>(`/employees/${id}`)
}

export const deleteEmployee = (id: number) => {
  return api.delete(`/employees/${id}`)
}

export const updateEmployee = (employee: Employee) => {
  return api.put<Employee>(`/employees/${employee.id}`, employee)
}

export const addEmployee = (employee: Omit<Employee, 'id'>) => {
  return api.post<Employee>('/employees', employee)
}

export const getProfile = (id: number) => {
  return api.get<Employee>(`/employee/profile/${id}`)
}

export const updateProfile = (profile: Employee) => {
  return api.put<Employee>('/employee/profile', profile)
}

export const getEmployeeSalary = (id: number) => {
  return api.get<SalaryDetail>(`/employee/salary/${id}`)
}

export const getAttendance = (id: number) => {
  return api.get<AttendanceRecord[]>(`/employee/attendance/${id}`)
}

export const getTrainings = () => {
  return api.get<Training[]>('/employee/trainings')
}

export type { Employee, SalaryDetail, AttendanceRecord }
