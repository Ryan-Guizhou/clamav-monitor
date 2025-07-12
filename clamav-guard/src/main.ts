
import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/reset.css';
import { createI18n } from 'vue-i18n';
import Particles from '@tsparticles/vue3';
import { loadFull } from 'tsparticles';

const messages = {
  en: {
    menu: {
      dashboard: 'Dashboard',
      scanHistory: 'Scan History',
      engineStatus: 'Engine Status',
      apiKeys: 'API Keys',
      settings: 'Settings',
    },
    login: {
      title: 'Login',
      slogan: 'AI-powered Security for Your Files',
      username: 'Username',
      password: 'Password',
      login: 'Login',
      usernameRequired: 'Please input your username!',
      passwordRequired: 'Please input your password!',
      success: 'Login successful (mock)',
      toRegister: 'No account? Register',
    },
    register: {
      title: 'Register',
      slogan: 'Create your secure ClamGuard account',
      username: 'Username',
      password: 'Password',
      confirmPassword: 'Confirm Password',
      register: 'Register',
      usernameRequired: 'Please input your username!',
      passwordRequired: 'Please input your password!',
      confirmPasswordRequired: 'Please confirm your password!',
      passwordMismatch: 'Passwords do not match!',
      success: 'Register successful (mock)',
      toLogin: 'Already have an account? Login',
    },
    dashboard: {
      totalScans: 'Total Scans',
      infectedFiles: 'Infected Files',
      avgScanTime: 'Avg. Scan Time',
      engineStatus: 'Engine Status',
      online: '● Online',
      recentScans: 'Recent Scans',
      topThreats: 'Top Threats',
    },
    scanHistory: {
      title: 'Scan History',
      fileId: 'File ID',
      filename: 'Filename',
      status: 'Status',
      virus: 'Virus Name',
      scannedAt: 'Scanned At',
    },
    engineStatus: {
      title: 'Engine Status',
      clamavVersion: 'ClamAV Version',
      dbVersion: 'Database Version',
      dbUpdated: 'Database Last Updated',
      processStatus: 'Process Status',
      running: 'Running',
      memory: 'Memory Usage',
      signatures: 'Signatures',
    },
    apiKeys: {
      title: 'API Keys',
      key: 'Key',
      status: 'Status',
      createdAt: 'Created At',
      actions: 'Actions',
      addTitle: 'Add API Key',
      editTitle: 'Edit API Key',
      addSuccess: 'Added successfully (mock)',
      editSuccess: 'Edited successfully (mock)',
      deleteSuccess: 'Deleted successfully (mock)',
    },
    settings: {
      title: 'Settings',
      rateLimit: 'API Rate Limit (requests/min)',
      logLevel: 'Log Level',
      save: 'Save Settings',
      saved: 'Settings saved (mock)',
    },
    common: {
      add: 'Add',
      edit: 'Edit',
      delete: 'Delete',
      actions: 'Actions',
    },
  },
  zh: {
    menu: {
      dashboard: '仪表盘',
      scanHistory: '扫描历史',
      engineStatus: '引擎状态',
      apiKeys: 'API 密钥',
      settings: '设置',
    },
    login: {
      title: '登录',
      slogan: 'AI 驱动的文件安全守护',
      username: '用户名',
      password: '密码',
      login: '登录',
      usernameRequired: '请输入用户名！',
      passwordRequired: '请输入密码！',
      success: '登录成功（mock）',
      toRegister: '没有账号？注册',
    },
    register: {
      title: '注册',
      slogan: '创建你的 ClamGuard 安全账户',
      username: '用户名',
      password: '密码',
      confirmPassword: '确认密码',
      register: '注册',
      usernameRequired: '请输入用户名！',
      passwordRequired: '请输入密码！',
      confirmPasswordRequired: '请确认密码！',
      passwordMismatch: '两次密码不一致！',
      success: '注册成功（mock）',
      toLogin: '已有账号？登录',
    },
    dashboard: {
      totalScans: '总扫描数',
      infectedFiles: '感染文件',
      avgScanTime: '平均扫描时长',
      engineStatus: '引擎状态',
      online: '● 在线',
      recentScans: '最近扫描',
      topThreats: '威胁排行',
    },
    scanHistory: {
      title: '扫描历史',
      fileId: '文件ID',
      filename: '文件名',
      status: '状态',
      virus: '病毒名',
      scannedAt: '扫描时间',
    },
    engineStatus: {
      title: '引擎状态',
      clamavVersion: 'ClamAV 版本',
      dbVersion: '病毒库版本',
      dbUpdated: '病毒库更新时间',
      processStatus: '进程状态',
      running: '运行中',
      memory: '内存占用',
      signatures: '病毒特征数',
    },
    apiKeys: {
      title: 'API 密钥',
      key: '密钥',
      status: '状态',
      createdAt: '创建时间',
      actions: '操作',
      addTitle: '新增 API 密钥',
      editTitle: '编辑 API 密钥',
      addSuccess: '新增成功（mock）',
      editSuccess: '修改成功（mock）',
      deleteSuccess: '删除成功（mock）',
    },
    settings: {
      title: '设置',
      rateLimit: 'API 限流 (请求/分钟)',
      logLevel: '日志等级',
      save: '保存设置',
      saved: '设置已保存（mock）',
    },
    common: {
      add: '新增',
      edit: '编辑',
      delete: '删除',
      actions: '操作',
    },
  },
};

const i18n = createI18n({
  legacy: false,
  locale: 'en',
  fallbackLocale: 'en',
  messages,
});

const app = createApp(App);
app.use(router);
app.use(Antd);
app.use(Particles, {
  init: async (engine) => {
    await loadFull(engine);
  }
});
app.use(i18n);
app.mount('#app');
