/// <reference types="vite/client" />

import { defineConfig, loadEnv } from 'vite';
import vue from '@vitejs/plugin-vue';
import { fileURLToPath, URL } from 'node:url';

// https://vitejs.dev/config/
export default defineConfig(({ mode }) => {
  // 根据当前工作目录中的 `mode` 加载 .env 文件
  const env = loadEnv(mode, process.cwd(), '');
  
  return {
    plugins: [vue()],
    base: mode === 'production' ? '/hrm/' : '/',
    server: {
      proxy: {
        '/api': {
          target: env.VITE_API_BASE_URL || 'http://localhost:8080',
          changeOrigin: true,
          rewrite: (path) => path.replace(/^\/api/, ''),
        },
      },
    },
    resolve: {
      alias: {
        "@": fileURLToPath(new URL('./src', import.meta.url))
      },
    },
    build: {
      // 打包配置
      outDir: 'dist', // 输出目录
      assetsDir: 'assets', // 静态资源目录
      sourcemap: false, // 不生成 sourcemap
      chunkSizeWarningLimit: 1500, // 文件大小警告的限制
      rollupOptions: {
        output: {
          // 分包配置
          manualChunks: {
            'vue-vendor': ['vue', 'vue-router', 'pinia'],
            'element-plus': ['element-plus'],
          },
          // 用于从入口点创建的块的打包输出格式
          chunkFileNames: 'assets/js/[name]-[hash].js',
          // 用于输出静态资源的命名
          assetFileNames: 'assets/[ext]/[name]-[hash].[ext]',
        }
      },
      // 压缩配置
      minify: 'terser',
      terserOptions: {
        compress: {
          // 生产环境时移除console
          drop_console: true,
          drop_debugger: true,
        },
      },
    },
  }
});
