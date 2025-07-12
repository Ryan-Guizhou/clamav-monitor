<template>
  <a-card>
    <h2>设置</h2>
    <a-form :model="form" layout="vertical" @finish="onFinish" style="max-width: 400px;">
      <a-form-item label="API 限流 (请求/分钟)" name="rateLimit">
        <a-input-number v-model:value="form.rateLimit" :min="1" style="width: 100%" />
      </a-form-item>
      <a-form-item label="日志等级" name="logLevel">
        <a-select v-model:value="form.logLevel">
          <a-select-option value="Info">Info</a-select-option>
          <a-select-option value="Debug">Debug</a-select-option>
          <a-select-option value="Warn">Warn</a-select-option>
          <a-select-option value="Error">Error</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item>
        <a-button type="primary" html-type="submit">保存设置</a-button>
      </a-form-item>
    </a-form>
  </a-card>
</template>

<script setup lang="ts">
import { reactive } from 'vue';
import { message } from 'ant-design-vue';

const form = reactive({
  rateLimit: 100,
  logLevel: 'Info',
});

function onFinish() {
  message.success('设置已保存（mock）');
}
</script> 