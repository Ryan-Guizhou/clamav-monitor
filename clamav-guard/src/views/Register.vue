<template>
  <div class="login-bg">
    <Particles id="register-particles" :options="particlesOptions" class="particles-bg" />
    <div class="login-glow"></div>
    <div class="login-container">
      <div class="brand-title">ClamGuard</div>
      <div class="brand-slogan">{{ $t('register.slogan') }}</div>
      <a-card class="glass-card" style="max-width: 380px; margin: 0 auto;">
        <div class="svg-illustration">
          <svg width="80" height="80" viewBox="0 0 80 80" fill="none" xmlns="http://www.w3.org/2000/svg">
            <defs>
              <radialGradient id="shield-glow" cx="50%" cy="50%" r="60%">
                <stop offset="0%" stop-color="#a29bfe" stop-opacity="0.7"/>
                <stop offset="100%" stop-color="#6c5ce7" stop-opacity="0.2"/>
              </radialGradient>
            </defs>
            <ellipse cx="40" cy="70" rx="28" ry="8" fill="url(#shield-glow)"/>
            <path d="M40 10C53 16 66 18 66 18V36C66 54 52 66 40 70C28 66 14 54 14 36V18C14 18 27 16 40 10Z" fill="#23243a" stroke="#6c5ce7" stroke-width="2.5"/>
            <path d="M40 10C53 16 66 18 66 18V36C66 54 52 66 40 70C28 66 14 54 14 36V18C14 18 27 16 40 10Z" fill="url(#shield-glow)"/>
            <path d="M28 38L37 47L54 30" stroke="#6c5ce7" stroke-width="3" stroke-linecap="round" stroke-linejoin="round"/>
          </svg>
        </div>
        <h2 style="text-align:center; margin-bottom: 24px; color:var(--brand)">{{ $t('register.title') }}</h2>
        <a-form :model="form" layout="vertical" @finish="onFinish">
          <a-form-item :label="$t('register.username')" name="username" :rules="[{ required: true, message: $t('register.usernameRequired') }]">
            <a-input v-model:value="form.username" size="large" />
          </a-form-item>
          <a-form-item :label="$t('register.password')" name="password" :rules="[{ required: true, message: $t('register.passwordRequired') }]">
            <a-input-password v-model:value="form.password" size="large" />
          </a-form-item>
          <a-form-item :label="$t('register.confirmPassword')" name="confirmPassword" :rules="[{ required: true, message: $t('register.confirmPasswordRequired') }]">
            <a-input-password v-model:value="form.confirmPassword" size="large" />
          </a-form-item>
          <a-form-item>
            <a-button type="primary" html-type="submit" block size="large" class="glow-btn">{{ $t('register.register') }}</a-button>
          </a-form-item>
          <div style="text-align:right;">
            <a @click.prevent="goLogin">{{ $t('register.toLogin') }}</a>
          </div>
        </a-form>
      </a-card>
    </div>
  </div>
</template>

<script setup lang="ts">
import { reactive } from 'vue';
import { useRouter } from 'vue-router';
import { message } from 'ant-design-vue';
import { useI18n } from 'vue-i18n';
import apiClient from '@/axios';

const { t } = useI18n();
const router = useRouter();
const form = reactive({ username: '', password: '', confirmPassword: '' });

const particlesOptions = {
  background: { color: 'transparent' },
  fpsLimit: 60,
  particles: {
    number: { value: 60, density: { enable: true, area: 800 } },
    color: { value: ['#6c5ce7', '#a29bfe', '#00bcd4'] },
    shape: { type: 'circle' },
    opacity: { value: 0.5 },
    size: { value: 3, random: true },
    move: {
      enable: true,
      speed: 1.2,
      direction: 'none',
      random: true,
      straight: false,
      outModes: { default: 'out' },
    },
    links: {
      enable: true,
      distance: 120,
      color: '#a29bfe',
      opacity: 0.3,
      width: 1.2,
    },
    glow: {
      enable: true,
      color: '#a29bfe',
      radius: 10,
      intensity: 0.5,
    },
  },
  detectRetina: true,
};

async function onFinish() {
  if (!form.username || !form.password || !form.confirmPassword) return;
  if (form.password !== form.confirmPassword) {
    message.error(t('register.passwordMismatch'));
    return;
  }
  try {
    await apiClient.post('/register', {
      username: form.username,
      password: form.password
    });
    message.success(t('register.success'));
    router.push('/login');
  } catch (error) {
    message.error(t('register.failure'));
  }
}
function goLogin() {
  router.push('/login');
}
</script>

<style scoped>
.svg-illustration {
  display: flex;
  justify-content: center;
  margin-top: 8px;
  margin-bottom: 8px;
  animation: fadeInUp 0.7s cubic-bezier(.23,1.01,.32,1) 0.1s both;
}
@keyframes fadeInUp {
  from { opacity: 0; transform: translateY(30px); }
  to { opacity: 1; transform: none; }
}
.particles-bg {
  position: fixed;
  left: 0; top: 0;
  width: 100vw;
  height: 100vh;
  z-index: 0;
  pointer-events: none;
}
.login-bg {
  min-height: 100vh;
  width: 100vw;
  position: fixed;
  left: 0; top: 0;
  z-index: 0;
  background: var(--bg-gradient);
  overflow: hidden;
}
.login-glow {
  position: absolute;
  left: 50%;
  top: 30%;
  width: 600px;
  height: 600px;
  background: radial-gradient(circle, var(--brand-glow) 0%, transparent 70%);
  filter: blur(80px);
  opacity: 0.5;
  transform: translate(-50%, -50%);
  z-index: 1;
  pointer-events: none;
}
.login-container {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  position: relative;
  z-index: 2;
}
.brand-title {
  font-size: 2.6rem;
  margin-bottom: 0.5rem;
  color: var(--brand);
  font-weight: 800;
  letter-spacing: 0.12em;
  text-shadow: 0 0 24px var(--brand-glow);
}
.brand-slogan {
  color: var(--brand-glow);
  font-size: 1.1rem;
  margin-bottom: 2.2rem;
  text-align: center;
  letter-spacing: 0.04em;
}
.glow-btn {
  box-shadow: 0 0 16px 0 var(--brand-glow);
  transition: box-shadow 0.2s;
}
.glow-btn:hover {
  box-shadow: 0 0 32px 0 var(--brand-glow);
}
@media (max-width: 600px) {
  .login-glow { width: 300px; height: 300px; }
  .brand-title { font-size: 2rem; }
  .brand-slogan { font-size: 1rem; }
}
</style>