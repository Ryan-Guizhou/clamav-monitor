# ClamAV Guard

This module serves as the main frontend application for the ClamAV Monitor project. It is built with Vue.js and TypeScript, providing a dynamic and interactive user interface.

For Chinese documentation, please refer to <mcfile name="README.zh-CN.md" path="c:\Users\Administrator\OneDrive\桌面\clamav-monitor\clamav-guard\README.zh-CN.md"></mcfile>.

## Project Structure

- `src/`: Contains the source code for the Vue.js application.
  - `App.vue`: The main Vue component.
  - `main.ts`: The entry point of the application.
  - `components/`: Reusable Vue components.
  - `views/`: Vue components for different views/pages.
  - `router/`: Vue Router configuration.
  - `stores/`: Pinia/Vuex stores for state management.
  - `axios.ts`: Axios instance for API calls.
- `public/`: Static assets.
- `index.html`: The main HTML file.
- `package.json`: Project dependencies and scripts.
- `vite.config.ts`: Vite build configuration.

## Getting Started

1.  **Install dependencies**:
    ```bash
    npm install
    ```
2.  **Run the development server**:
    ```bash
    npm run dev
    ```
    The application will be accessible at `http://localhost:5173` (or another port if 5173 is in use).

## Build for Production

```bash
npm run build
```

This will generate production-ready assets in the `dist` directory.

## Deployment

This application can be deployed as a static web server or integrated with a backend server. Refer to the `Dockerfile` for Docker deployment instructions.

This template should help get you started developing with Vue 3 in Vite.

## Recommended IDE Setup

[VSCode](https://code.visualstudio.com/) + [Volar](https://marketplace.visualstudio.com/items?itemName=Vue.volar) (and disable Vetur).

## Type Support for `.vue` Imports in TS

TypeScript cannot handle type information for `.vue` imports by default, so we replace the `tsc` CLI with `vue-tsc` for type checking. In editors, we need [Volar](https://marketplace.visualstudio.com/items?itemName=Vue.volar) to make the TypeScript language service aware of `.vue` types.

## Customize configuration

See [Vite Configuration Reference](https://vite.dev/config/).

## Project Setup

```sh
npm install
```

### Compile and Hot-Reload for Development

```sh
npm run dev
```

### Type-Check, Compile and Minify for Production

```sh
npm run build
```
