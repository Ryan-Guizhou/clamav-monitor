<template>
  <a-card>
    <h2>扫描历史</h2>
    <a-table :columns="columns" :data-source="data" row-key="id" bordered />
  </a-card>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import apiClient from '@/axios';

const columns = [
  { title: '文件ID', dataIndex: 'fileId', key: 'fileId' },
  { title: '文件名', dataIndex: 'filename', key: 'filename' },
  { title: '状态', dataIndex: 'status', key: 'status' },
  { title: '病毒名', dataIndex: 'virusName', key: 'virusName' },
  { title: '扫描时间', dataIndex: 'scannedAt', key: 'scannedAt' },
];

const data = ref([]);

async function fetchScanHistory() {
  try {
    const response = await apiClient.get('/scan-history');
    data.value = response.data.data;
  } catch (error) {
    console.error('Failed to fetch scan history:', error);
  }
}

onMounted(() => {
  fetchScanHistory();
});
</script>