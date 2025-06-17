<template>
    <div
      class="d-flex justify-center align-center"
      style="height: 100vh; padding: 16px;"
    >
      <VCard outlined width="320">
        <VCardTitle class="justify-center">로그인</VCardTitle>
        <VCardText>
          <VTextField label="아이디" v-model="loginRequest.userId" outlined dense />
          <VTextField
            label="비밀번호"
            v-model="loginRequest.password"
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
import { setToken } from '@/store/user'
import { $apiPost } from '@/utils/apiUtils'
import { ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { VCard, VCardTitle, VCardText, VTextField, VBtn } from 'vuetify/components'
  
const router = useRouter()
const route = useRoute()
const loginRequest = ref({
  mode: undefined,
  token: undefined,
  userId: '',
  password: '',
})
  
const login = async () => {
  loginRequest.value.mode = route.query.mode;
  loginRequest.value.token = route.query.token;

  try {
      const res = await $apiPost('/auth/login', loginRequest.value)
      if(!res.isSuccess){
        alert(res.message)
        return
      }
      setToken(res.token)
      const redirectUri = loginRequest.value.mode === 'qrmode' ? '/login/qr-success' : '/'
      router.push(redirectUri)
    } catch (err) {
      alert("로그인 정보를 올바르게 입력해주세요.")
    }
}
  </script>
  