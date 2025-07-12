<template>
  <div>
    <a-row gutter="16" class="mb-4">
      <a-col :span="6">
        <a-card>
          <h3>总扫描数</h3>
          <p class="stat-value">{{ totalScans }}</p>
        </a-card>
      </a-col>
      <a-col :span="6">
        <a-card>
          <h3>感染文件</h3>
          <p class="stat-value">{{ infectedFiles }}</p>
        </a-card>
      </a-col>
      <a-col :span="6">
        <a-card>
          <h3>平均扫描时长</h3>
          <p class="stat-value">150ms</p>
        </a-card>
      </a-col>
      <a-col :span="6">
        <a-card>
          <h3>引擎状态</h3>
          <p class="stat-value" style="color: #2ecc71">● 在线</p>
        </a-card>
      </a-col>
    </a-row>
    <a-row gutter="16">
      <a-col :span="12">
        <a-card title="最近扫描">
          <a-list :data-source="recentScans" bordered>
            <template #renderItem="{ item }">
              <a-list-item>
                <span>{{ item.filename }}</span>
                <span :style="{ color: item.status === 'Clean' ? '#2ecc71' : '#e74c3c' }">{{ item.status }}</span>
              </a-list-item>
            </template>
          </a-list>
        </a-card>
      </a-col>
      <a-col :span="12">
        <a-card title="威胁排行">
          <a-list :data-source="topThreats" bordered>
            <template #renderItem="{ item }">
              <a-list-item>
                <span>{{ item.name }}</span>
                <span style="color: #a0a0a0">{{ item.count }}</span>
              </a-list-item>
            </template>
          </a-list>
        </a-card>
      </a-col>
    </a-row>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import apiClient from '@/axios';

const totalScans = ref(0);
const infectedFiles = ref(0);
const recentScans = ref([]);

// Mock data, as backend does not provide this API yet
const topThreats = ref([
  { name: 'Win.Trojan.Generic-123', count: 45 },
  { name: 'JS.Adware.Subprop-456', count: 23 },
  { name: 'Linux.Exploit.CVE-2023-1234', count: 12 },
  { name: 'PDF.Phishing.Bank-789', count: 5 },
]);

async function fetchDashboardData() {
  try {
    const response = await apiClient.get('/scan-history');
    const scanHistory = response.data.data;
    totalScans.value = scanHistory.length;
    infectedFiles.value = scanHistory.filter(item => item.status === 'Infected').length;
    recentScans.value = scanHistory.slice(0, 4);
  } catch (error) {
    console.error('Failed to fetch dashboard data:', error);
  }
}

onMounted(() => {
  fetchDashboardData();
});
</script>

<style scoped>
.mb-4 { margin-bottom: 1.5rem; }
.stat-value { font-size: 2rem; font-weight: 600; }
</style>