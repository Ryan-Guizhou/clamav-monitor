<template>
  <a-card>
    <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 16px;">
      <h2>{{ $t('apiKeys.title') }}</h2>
      <div>
        <a-button type="primary" @click="onAdd">{{ $t('common.add') }}</a-button>
      </div>
    </div>
    <a-table :columns="columns" :data-source="data" row-key="key" bordered>
      <template #bodyCell="{ column, record }">
        <template v-if="column.key === 'actions'">
          <a-space>
            <a-button size="small" @click="onEdit(record)">{{ $t('common.edit') }}</a-button>
            <a-button type="primary" danger size="small" @click="onDelete(record)">{{ $t('common.delete') }}</a-button>
          </a-space>
        </template>
      </template>
    </a-table>
    <!-- 新增/编辑弹窗 -->
    <a-modal v-model:open="modalVisible" :title="modalType === 'add' ? $t('apiKeys.addTitle') : $t('apiKeys.editTitle')" @ok="onModalOk">
      <a-form :model="modalForm" layout="vertical">
        <a-form-item :label="$t('apiKeys.key')" name="key">
          <a-input v-model:value="modalForm.key" :disabled="modalType === 'edit'" />
        </a-form-item>
        <a-form-item :label="$t('apiKeys.status')" name="status">
          <a-select v-model:value="modalForm.status">
            <a-select-option value="Active">Active</a-select-option>
            <a-select-option value="Revoked">Revoked</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item :label="$t('apiKeys.createdAt')" name="createdAt">
          <a-input v-model:value="modalForm.createdAt" />
        </a-form-item>
      </a-form>
    </a-modal>
  </a-card>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue';
import { message } from 'ant-design-vue';
import { useI18n } from 'vue-i18n';
import apiClient from '@/axios';

const { t } = useI18n();

const columns = [
  { title: t('apiKeys.key'), dataIndex: 'key', key: 'key' },
  { title: t('apiKeys.status'), dataIndex: 'status', key: 'status' },
  { title: t('apiKeys.createdAt'), dataIndex: 'createdAt', key: 'createdAt' },
  { title: t('common.actions'), key: 'actions' },
];

const data = ref([]);

const modalVisible = ref(false);
const modalType = ref<'add' | 'edit'>('add');
const modalForm = reactive({ id: null, key: '', status: 'Active', createdAt: '' });

function onAdd() {
  modalType.value = 'add';
  modalForm.id = null;
  modalForm.key = '';
  modalForm.status = 'Active';
  modalForm.createdAt = '';
  modalVisible.value = true;
}
function onEdit(record: any) {
  modalType.value = 'edit';
  modalForm.id = record.id;
  modalForm.key = record.apiKey;
  modalForm.status = record.status;
  modalForm.createdAt = record.createTime;
  modalVisible.value = true;
}
async function onDelete(record: any) {
  try {
    await apiClient.delete(`/api-keys/${record.id}`);
    message.success(t('apiKeys.deleteSuccess'));
    fetchApiKeys();
  } catch (error) {
    message.error(t('apiKeys.deleteFailure'));
  }
}
async function onModalOk() {
  try {
    if (modalType.value === 'add') {
      await apiClient.post('/api-keys');
      message.success(t('apiKeys.addSuccess'));
    } else {
      await apiClient.put(`/api-keys/${modalForm.id}`, { status: modalForm.status });
      message.success(t('apiKeys.editSuccess'));
    }
    fetchApiKeys();
    modalVisible.value = false;
  } catch (error) {
    message.error(modalType.value === 'add' ? t('apiKeys.addFailure') : t('apiKeys.editFailure'));
  }
}

async function fetchApiKeys() {
  try {
    const response = await apiClient.get('/api-keys');
    data.value = response.data.data;
  } catch (error) {
    message.error(t('apiKeys.fetchFailure'));
  }
}

onMounted(() => {
  fetchApiKeys();
});
</script>