<template>
  <div
    class="d-flex align-center justify-center"
    style="height: 100vh; padding: 16px;"
  >
    <VCard class="pa-6 text-center" elevation="3" width="320">
      <VCardTitle class="justify-center text-h6 font-weight-bold">
        로그인 성공하였습니다
      </VCardTitle>
      <VCardText class="mt-2 mb-4">
        웹에서 계속 진행해주세요.
      </VCardText>
      <VBtn color="primary" block @click="handleClose">
        닫기
      </VBtn>
    </VCard>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router';

const router = useRouter()

const handleClose = () => {
  router.push("/")
}
</script>
