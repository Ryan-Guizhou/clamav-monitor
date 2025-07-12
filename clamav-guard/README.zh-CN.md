# ClamAV Guard

此模块是 ClamAV 监控项目的主要前端应用程序。它使用 Vue.js 和 TypeScript 构建，提供动态和交互式的用户界面。

## 项目结构

- `src/`: 包含 Vue.js 应用程序的源代码。
  - `App.vue`: 主 Vue 组件。
  - `main.ts`: 应用程序的入口点。
  - `components/`: 可重用的 Vue 组件。
  - `views/`: 不同视图/页面的 Vue 组件。
  - `router/`: Vue Router 配置。
  - `stores/`: Pinia/Vuex 状态管理。
  - `axios.ts`: 用于 API 调用的 Axios 实例。
- `public/`: 静态资源。
- `index.html`: 主 HTML 文件。
- `package.json`: 项目依赖和脚本。
- `vite.config.ts`: Vite 构建配置。

## 快速开始

1.  **安装依赖**：
    ```bash
    npm install
    ```
2.  **运行开发服务器**：
    ```bash
    npm run dev
    ```
    应用程序将在 `http://localhost:5173` 访问（如果 5173 端口被占用，则为其他端口）。

## 生产构建

```bash
npm run build
```

这将在 `dist` 目录中生成生产就绪的资产。

## 部署

此应用程序可以部署为静态 Web 服务器或与后端服务器集成。有关 Docker 部署说明，请参阅 `Dockerfile`。

此模板应能帮助您开始使用 Vite 开发 Vue 3。

## 推荐的 IDE 设置

[VSCode](https://code.visualstudio.com/) + [Volar](https://marketplace.visualstudio.com/items?itemName=Vue.volar)（并禁用 Vetur）。

## TS 中 `.vue` 导入的类型支持

TypeScript 默认无法处理 `.vue` 导入的类型信息，因此我们使用 `vue-tsc` 替换 `tsc` CLI 进行类型检查。在编辑器中，我们需要 [Volar](https://marketplace.visualstudio.com/items?itemName=Vue.volar) 来使 TypeScript 语言服务识别 `.vue` 类型。

## 自定义配置

请参阅 [Vite 配置参考](https://vite.dev/config/)。

## 项目设置

```sh
npm install
```

### 开发环境编译和热重载

```sh
npm run dev
```

### 生产环境类型检查、编译和压缩

```sh
npm run build
```