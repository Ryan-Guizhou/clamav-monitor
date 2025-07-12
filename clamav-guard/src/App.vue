<script setup lang="ts">
import { ref, computed, watch } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useI18n } from 'vue-i18n';

const router = useRouter();
const route = useRoute();
const { t, locale } = useI18n();

const userAvatar = ref(localStorage.getItem('avatar') || '');
const userInitials = computed(() => {
  const avatar = userAvatar.value;
  if (avatar && !avatar.startsWith('http')) {
    return avatar;
  }
  return '';
});

const menuMap: Record<string, string> = {
  dashboard: t('menu.dashboard'),
  'scan-history': t('menu.scanHistory'),
  'engine-status': t('menu.engineStatus'),
  'api-keys': t('menu.apiKeys'),
  settings: t('menu.settings'),
};

const selectedKey = ref(route.name ? String(route.name) : 'dashboard');
const pageTitle = computed(() => menuMap[selectedKey.value] || t('menu.dashboard'));

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

watch(
  () => route.name,
  (name) => {
    selectedKey.value = name ? String(name) : 'dashboard';
  }
);

function onMenuClick({ key }: { key: string }) {
  selectedKey.value = key;
  router.push({ name: key });
}
function setLocale(l: string) {
  locale.value = l;
}
</script>

<template>
  <router-view v-slot="{ Component, route }">
    <template v-if="['login', 'register'].includes(route.name as string)">
      <component :is="Component" />
    </template>
    <template v-else>
      <div class="main-bg">
        <Particles id="main-particles" :options="particlesOptions" class="particles-bg" />
        <a-layout style="min-height: 100vh">
          <a-layout-sider width="220" theme="dark">
            <div class="logo">
              <h2>ClamGuard</h2>
            </div>
            <a-menu
              theme="dark"
              mode="inline"
              :selectedKeys="[selectedKey]"
              @click="onMenuClick"
            >
              <a-menu-item key="dashboard">{{ $t('menu.dashboard') }}</a-menu-item>
              <a-menu-item key="scan-history">{{ $t('menu.scanHistory') }}</a-menu-item>
              <a-menu-item key="engine-status">{{ $t('menu.engineStatus') }}</a-menu-item>
              <a-menu-item key="api-keys">{{ $t('menu.apiKeys') }}</a-menu-item>
              <a-menu-item key="settings">{{ $t('menu.settings') }}</a-menu-item>
            </a-menu>
          </a-layout-sider>
          <a-layout>
            <a-layout-header style="background: #fff; padding: 0 24px; display: flex; align-items: center; justify-content: space-between;">
              <h1 style="margin: 0; font-size: 1.5rem;">{{ pageTitle }}</h1>
              <div>
                <a-button-group>
                  <a-button :type="locale === 'en' ? 'primary' : 'default'" @click="setLocale('en')">EN</a-button>
                  <a-button :type="locale === 'zh' ? 'primary' : 'default'" @click="setLocale('zh')">中</a-button>
                </a-button-group>
                <a-avatar v-if="userAvatar.startsWith('http')" :src="userAvatar" class="user-avatar" />
                <a-avatar v-else-if="userInitials" class="user-avatar">{{ userInitials }}</a-avatar>
              </div>
            </a-layout-header>
            <a-layout-content style="margin: 24px;">
              <component :is="Component" />
            </a-layout-content>
          </a-layout>
        </a-layout>
      </div>
    </template>
  </router-view>
</template>

<style scoped>
.main-bg {
  min-height: 100vh;
  width: 100vw;
  position: fixed;
  left: 0; top: 0;
  z-index: 0;
  background: var(--bg-gradient);
  overflow: hidden;
}
.particles-bg {
  position: fixed;
  left: 0; top: 0;
  width: 100vw;
  height: 100vh;
  z-index: 0;
  pointer-events: none;
}
.logo {
  text-align: center;
  margin: 24px 0;
  color: #6c5ce7;
  z-index: 2;
}
.user-avatar {
  margin-left: 16px;
  cursor: pointer;
  background-color: #6c5ce7;
}
</style>
