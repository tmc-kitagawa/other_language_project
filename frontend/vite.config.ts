import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';
import path from 'path';

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      '/api': 'http://localhost:80',
      '/login': 'http://localhost:80',
    },
  },
  build: {
    outDir: path.join(__dirname, '../backend/src/main/resources/static'),
  },
});
