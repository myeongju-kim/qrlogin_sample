<template>
    <div
      class="d-flex justify-center align-center"
      style="height: 100vh; padding: 16px;"
    >
      <VCard outlined width="320">
        <VCardTitle class="justify-center">로그인</VCardTitle>
        <VCardText>
          <VTextField label="아이디" v-model="userId" outlined dense />
          <VTextField
            label="비밀번호"
            v-model="password"
            type="password"
            outlined
            dense
            class="mt-4"
          />
          <VBtn color="primary" block class="mt-6" @click="login">로그인</VBtn>
        </VCardText>
      </VCard>
    </div>
  </template>
  
  <script setup>
  import { ref } from 'vue'
  import { VCard, VCardTitle, VCardText, VTextField, VBtn } from 'vuetify/components'
  
  const userId = ref('')
  const password = ref('')
  
  const login = () => {
    console.log('로그인 시도:', userId.value, password.value)
  }
  </script>
  