<template>
  <a-card>
    <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 16px;">
      <h2>{{ $t('apiKeys.title') }}</h2>
      <div>
        <a-button type="primary" @click="onAdd">{{ $t('common.add') }}</a-button>
      </div>
    </div>
    <a-table
        :columns="columns"
        :data-source="apiKeys"
        :pagination="pagination"
        row-key="id"
        bordered
        @change="handleTableChange"
    >
      <template #bodyCell="{ column, record }">
        <template v-if="column.key === 'createdAt'">
          {{ formatDate(record.createdAt) }}
        </template>
        <template v-else-if="column.key === 'status'">
          <a-tag :color="record.status === 'Active' ? 'green' : 'red'">
            {{ record.status === 'Active' ? $t('apiKeys.statusActive') : $t('apiKeys.statusRevoked') }}
          </a-tag>
        </template>
        <template v-else-if="column.key === 'actions'">
          <a-space>
            <a-button size="small" @click="onEdit(record)">{{ $t('common.edit') }}</a-button>
            <a-button type="primary" danger size="small" @click="onDelete(record)">{{ $t('common.delete') }}</a-button>
          </a-space>
        </template>
      </template>
    </a-table>

    <!-- 新增/编辑弹窗 -->
    <a-modal
        v-model:open="modalVisible"
        :title="modalType === 'add' ? $t('apiKeys.addTitle') : $t('apiKeys.editTitle')"
        @ok="onModalOk"
        :confirm-loading="modalLoading"
    >
      <a-form :model="modalForm" layout="vertical">
        <a-form-item :label="$t('apiKeys.key')" name="key">
          <a-input v-model:value="modalForm.key" :disabled="modalType === 'edit'" />
        </a-form-item>
        <a-form-item :label="$t('apiKeys.status')" name="status">
          <a-select v-model:value="modalForm.status">
            <a-select-option value="Active">{{ $t('apiKeys.statusActive') }}</a-select-option>
            <a-select-option value="Revoked">{{ $t('apiKeys.statusRevoked') }}</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item :label="$t('apiKeys.createdAt')" name="createdAt" v-if="modalType === 'edit'">
          <a-input v-model:value="formattedCreatedAt" disabled />
        </a-form-item>
      </a-form>
    </a-modal>
  </a-card>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, computed } from 'vue';
import { message } from 'ant-design-vue';
import { useI18n } from 'vue-i18n';
import apiClient from '@/axios';
import dayjs from 'dayjs';

const { t } = useI18n();

const columns = [
  { title: t('apiKeys.key'), dataIndex: 'key', key: 'key' },
  {
    title: t('apiKeys.status'),
    dataIndex: 'status',
    key: 'status',
    filters: [
      { text: t('apiKeys.statusActive'), value: 'Active' },
      { text: t('apiKeys.statusRevoked'), value: 'Revoked' },
    ],
    onFilter: (value: string, record: any) => record.status === value,
  },
  { title: t('apiKeys.createdAt'), dataIndex: 'createdAt', key: 'createdAt' },
  { title: t('common.actions'), key: 'actions' },
];

const apiKeys = ref<any[]>([]);
const pagination = reactive({
  current: 1,
  pageSize: 10,
  total: 0,
  showSizeChanger: true,
  showQuickJumper: true,
  showTotal: (total: number, range: number[]) =>
      t('common.paginationTotal', {
        total,
        start: range[0],
        end: range[1]
      })
});

const modalVisible = ref(false);
const modalLoading = ref(false);
const modalType = ref<'add' | 'edit'>('add');
const modalForm = reactive({
  id: null as number | null,
  key: '',
  status: 'Active' as 'Active' | 'Revoked',
  createdAt: ''
});

// 格式化创建时间显示
const formattedCreatedAt = computed(() => {
  return modalForm.createdAt
      ? formatDate(modalForm.createdAt)
      : '';
});

// 格式化日期函数
function formatDate(isoString: string) {
  return dayjs(isoString).format('YYYY-MM-DD HH:mm:ss');
}

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
  modalForm.key = record.key;
  modalForm.status = record.status;
  modalForm.createdAt = record.createdAt;
  modalVisible.value = true;
}

async function onDelete(record: any) {
  try {
    await apiClient.delete(`/api-keys/${record.id}`);
    message.success(t('apiKeys.deleteSuccess'));
    fetchApiKeys(pagination.current, pagination.pageSize);
  } catch (error) {
    message.error(t('apiKeys.deleteFailure'));
  }
}

async function onModalOk() {
  modalLoading.value = true;
  try {
    if (modalType.value === 'add') {
      // 添加API密钥
      const response = await apiClient.post('/api-keys');
      if (response.data.code === "200") {
        message.success(t('apiKeys.addSuccess'));
      } else {
        message.error(response.data.msg || t('apiKeys.addFailure'));
      }
    } else {
      // 更新API密钥状态
      const response = await apiClient.put(`/api-keys/${modalForm.id}`, {
        status: modalForm.status
      });
      if (response.data.code === "200") {
        message.success(t('apiKeys.editSuccess'));
      } else {
        message.error(response.data.msg || t('apiKeys.editFailure'));
      }
    }

    fetchApiKeys(pagination.current, pagination.pageSize);
    modalVisible.value = false;
  } catch (error) {
    message.error(modalType.value === 'add'
        ? t('apiKeys.addFailure')
        : t('apiKeys.editFailure'));
  } finally {
    modalLoading.value = false;
  }
}

async function fetchApiKeys(page = 1, pageSize = 10) {
  try {
    const response = await apiClient.get('/api-keys', {
      params: {
        page,
        size: pageSize
      }
    });

    if (response.data.code === "200") {
      apiKeys.value = response.data.data.data || [];
      pagination.total = response.data.data.total || 0;
      pagination.current = page;
      pagination.pageSize = pageSize;
    } else {
      message.error(response.data.msg || t('apiKeys.fetchFailure'));
    }
  } catch (error) {
    message.error(t('apiKeys.fetchFailure'));
  }
}

function handleTableChange(pag: any) {
  fetchApiKeys(pag.current, pag.pageSize);
}

onMounted(() => {
  fetchApiKeys();
});
</script>