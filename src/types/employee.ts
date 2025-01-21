export interface Employee {
  id: number;
  username: string;
  name: string;
  department: string;
  position: string;
  joinDate: string;
  status: 'active' | 'inactive';
}