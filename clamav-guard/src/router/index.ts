import { createRouter, createWebHistory } from 'vue-router';
import Dashboard from '@/views/Dashboard.vue';
import ScanHistory from '@/views/ScanHistory.vue';
import EngineStatus from '@/views/EngineStatus.vue';
import ApiKeys from '@/views/ApiKeys.vue';
import Settings from '@/views/Settings.vue';
import Login from '@/views/Login.vue';
import Register from '@/views/Register.vue';

const routes = [
  { path: '/login', name: 'login', component: Login },
  { path: '/register', name: 'register', component: Register },
  {
    path: '/',
    redirect: '/dashboard',
    meta: { requiresAuth: true },
    children: [
      { path: 'dashboard', name: 'dashboard', component: Dashboard },
      { path: 'scan-history', name: 'scan-history', component: ScanHistory },
      { path: 'engine-status', name: 'engine-status', component: EngineStatus },
      { path: 'api-keys', name: 'api-keys', component: ApiKeys },
      { path: 'settings', name: 'settings', component: Settings },
    ]
  }
];

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
});

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token');
  if (to.matched.some(record => record.meta.requiresAuth) && !token) {
    next({ name: 'login' });
  } else {
    next();
  }
});

export default router;
