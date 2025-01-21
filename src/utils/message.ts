import { ElMessage } from 'element-plus'

type MessageType = 'success' | 'warning' | 'info' | 'error'

export const showMessage = (message: string, type: MessageType = 'info') => {
  ElMessage({
    message,
    type,
    duration: 3000,
    showClose: true,
  })
}

export const showSuccess = (message: string) => showMessage(message, 'success')
export const showError = (message: string) => showMessage(message, 'error')
export const showWarning = (message: string) => showMessage(message, 'warning')
export const showInfo = (message: string) => showMessage(message, 'info') 