<template>
  <a-card>
    <h2>引擎状态</h2>
    <div v-if="loading">Loading...</div>
    <div v-if="error">{{ error }}</div>
    <a-row v-if="status" gutter="16">
      <a-col :span="8">
        <a-card class="status-card">
          <h3>ClamAV 版本</h3>
          <p class="stat-value">{{ status.clamavVersion }}</p>
        </a-card>
      </a-col>
      <a-col :span="8">
        <a-card class="status-card">
          <h3>病毒库版本</h3>
          <p class="stat-value">{{ status.virusDbVersion }}</p>
        </a-card>
      </a-col>
      <a-col :span="8">
        <a-card class="status-card">
          <h3>病毒库更新时间</h3>
          <p class="stat-value">{{ status.virusDbUpdateTime }}</p>
        </a-card>
      </a-col>
      <a-col :span="8">
        <a-card class="status-card">
          <h3>进程状态</h3>
          <p class="stat-value" :class="{ 'running-status': status.processStatus === 'Running' }" :style="{ color: status.processStatus === 'Running' ? '#2ecc71' : '#e74c3c' }">{{ status.processStatus }}</p>
        </a-card>
      </a-col>
      <a-col :span="8">
        <a-card class="status-card">
          <h3>内存占用</h3>
          <p class="stat-value">{{ status.memoryUsage }}</p>
        </a-card>
      </a-col>
      <a-col :span="8">
        <a-card class="status-card">
          <h3>病毒特征数</h3>
          <p class="stat-value">{{ status.virusSignatures }}</p>
        </a-card>
      </a-col>
    </a-row>
  </a-card>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import apiClient from '@/axios';

const status = ref(null);
const loading = ref(true);
const error = ref<string | null>(null);

onMounted(async () => {
  try {
    const response = await apiClient.get('/engine-status');
    status.value = response.data.data;
  } catch (err) {
    error.value = 'Failed to fetch engine status.';
  } finally {
    loading.value = false;
  }
});
</script>

<style scoped>
.stat-value {
  font-size: 1.5rem;
  font-weight: 600;
}

.status-card {
  min-height: 150px; /* Adjust as needed */
}

.running-status {
  font-weight: bold;
}
</style>