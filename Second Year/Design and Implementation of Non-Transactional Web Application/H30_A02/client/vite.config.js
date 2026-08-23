import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
const CLIENTPORT = 4444;
const SERVERPORT = 8888;

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    port: CLIENTPORT,
    proxy: {
      '/api': `http://localhost:${SERVERPORT}`,
      '/movies': `http://localhost:${SERVERPORT}`,
      '/actors':`http://localhost:${SERVERPORT}`,
      '/years':`http://localhost:${SERVERPORT}`,
      '/genres':`http://localhost:${SERVERPORT}`
    }
  }
})
