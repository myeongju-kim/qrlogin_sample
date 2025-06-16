<template>
  <VCard class="p-6 rounded-xl shadow-md w-full max-w-xs mx-auto text-center">
    <VCardTitle class="text-lg font-semibold mb-4">
      {{ userId }}님, 환영합니다!
    </VCardTitle>

    <div class="action-row">
      <div class="action-btn">메일</div>
      <div class="divider"></div>
      <div class="action-btn">쪽지</div>
      <div class="divider"></div>
      <div class="action-btn">카페</div>
      <div class="divider"></div>
      <div class="action-btn">블로그</div>
    </div>
  </VCard>
</template>

<script setup>
import { $apiGet } from '@/utils/apiUtils';
import { onMounted, ref } from 'vue';



const userId = ref('')

const getUserId = async () => {
    try {
        const res = await $apiGet('/auth/user-info')
        userId.value = res
    } catch (err) {
      alert("확인해라;;")
    }
}
onMounted(() => {
    getUserId()
})
</script>

<style scoped>
.action-row {
  display: flex;
  align-items: center;
  background-color: #f0f0f0;
  border-radius: 8px;
  padding: 8px 12px;
  user-select: none;
}

.action-btn {
  padding: 6px 12px;
  color: #555;
  background-color: #f0f0f0;
  cursor: pointer;
  border-radius: 4px;
  transition: background-color 0.2s ease;
  user-select: none;
}
.action-btn:hover {
  background-color: #dcdcdc;
}

.divider {
  width: 1px;
  height: 16px;
  background-color: #bbb;
  margin: 0 8px;
}
</style>
