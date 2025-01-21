import request from '@/utils/request';

export const uploadAvatar = (file: File) => {
  const formData = new FormData();
  formData.append('avatar', file);
  
  return request.post('/employee/upload/avatar', formData, {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  });
}; 