import { defineConfig } from 'vite';

export default defineConfig({
  root: './src',
  assetsInclude: ['public/**'],
  build: {
    outDir: '../dist',
    minify: false,
    emptyOutDir: true,
  },
});
