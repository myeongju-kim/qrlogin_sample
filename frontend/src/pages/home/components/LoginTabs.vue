<template>
  <VCard>
    <VTabs v-model="tab" background-color="#005A9C" dark>
      <VTab value="login">아이디/비밀번호</VTab>
      <VTab value="qrcode">QR 코드</VTab>
    </VTabs>

    <VWindow v-model="tab" style="min-height: 360px;">
      <!-- 로그인 탭 -->
      <VWindowItem value="login">
        <VCardText class="flex flex-col justify-center h-full px-4">
          <VTextField label="아이디" v-model="userId" outlined dense />
          <VTextField label="비밀번호" v-model="password" type="password" outlined dense class="mt-2" />
          <VBtn color="#005A9C" class="mt-4" block @click="login">로그인</VBtn>
        </VCardText>
      </VWindowItem>

      <!-- QR 코드 탭 -->
      <VWindowItem value="qrcode">
        <VCardText class="flex flex-col items-center justify-center h-full">
          <img
            :src="qrCodeUrl"
            alt="QR 코드"
            class="mb-6"
             style="width: 200px; height: 200px; display: block; margin: 0 auto;"
          />

          <div class="text-h6 text-center font-weight-bold mb-1">
            <text style="color: #005A9C;">남은시간</text> {{ formattedTime }}
          </div>
          <div class="text-subtitle-2 text-center px-4 mt-1">
            공용 네트워크, 공용 PC라면 안전을 위해<br />
            QR코드로 로그인해주세요.
          </div>
        </VCardText>
      </VWindowItem>
    </VWindow>
  </VCard>
</template>

<script setup>
import { ref, watch, onBeforeUnmount, computed } from 'vue'
import { $apiGet } from '@/utils/apiUtils'
import { setToken } from '@/store/user'
import { useRouter } from 'vue-router'

const tab = ref('login')
const userId = ref('')
const password = ref('')
const timeLeft = ref(0)
let timer = null
const qrCodeUrl = ref('')
const token = ref('')

const formattedTime = computed(() => {
  const min = String(Math.floor(timeLeft.value / 60)).padStart(2, '0')
  const sec = String(timeLeft.value % 60).padStart(2, '0')
  return `${min}분 ${sec}초`
})

const fetchQrCodeImage = async () => {
    try {
      const res = await $apiGet('/auth/qrcode')
      qrCodeUrl.value = 'data:image/png;base64,' + res.qrCodeUrl
      token.value = res.token
    } catch (err) {
      console.error('QR 코드 이미지 로딩 실패:', err)
    }
}

const checkLogin = async () => {
  try {
    const res = await $apiGet(`/auth/qrcode/${token.value}/login`)
    if(!res.isSuccess){
      return
    }
    clearInterval(timer)
    setToken(res.token)
    window.location.reload()
  } catch (err) {
    console.error("알 수 없는 에러 발생:", err)
  }
}

const startTimer = () => {
  clearInterval(timer)
  timeLeft.value = 180
  timer = setInterval(() => {
    if(timeLeft.value % 2 == 0 ){
      checkLogin()
    }
    if (timeLeft.value > 0) {
      timeLeft.value--
    } else {
      clearInterval(timer)
    }
  }, 1000)
}

watch(tab, (newVal) => {
  if (newVal === 'qrcode') {
    fetchQrCodeImage()
    startTimer()
  } else {
    clearInterval(timer)
  }
})

onBeforeUnmount(() => {
  clearInterval(timer)
})

const login = () => {
  console.log('로그인 시도:', userId.value, password.value)
}
</script>
