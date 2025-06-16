<template>
  <VContainer fluid>
    <!-- 상단 검색바 -->
    <VAppBar color="#005A9C" dark>
      <VToolbarTitle>킹명주 포털</VToolbarTitle>
      <VSpacer />
      <VTextField
        prepend-inner-icon="mdi-magnify"
        placeholder="검색어를 입력하세요"
        dense
        hide-details
        solo-inverted
        rounded
        style="max-width: 400px"
      />
    </VAppBar>

    <VContainer class="d-flex" style="gap: 32px; margin-top: 48px;">
      <div class="d-flex" style="height: 80vh;">
        <div class="flex-grow-1 position-relative">
          <img
            src="../../assets/image/otani.jpg"
            alt="오타니"
            class="headline-img"
          />
        <div class="position-absolute headline-text">
          [속보] 오타니 투수 복귀 임박
        </div>
      </div>
      </div>
      <div class="login-tab">
        <AfterLoginCard v-if="isExistToken()" />
        <LoginTabs v-else/>
      </div>
    </VContainer>
  </VContainer>
</template>

<script setup>
import LoginTabs from './components/LoginTabs.vue'
import { isExistToken } from '@/store/user';
import AfterLoginCard from './components/AfterLoginCard.vue';

</script>

<style scoped>
.headline-img {
  width: 80%; 
  height: 80%; 
  object-fit: cover;
}
.headline-text {
  top: 0;
  width: 80%;
  background-color: rgba(0, 0, 0, 0.5);
  color: white;
  padding: 16px;
  display: flex;
  align-items: center;
  font-weight: bold;
  font-size: 1.25rem;
}
.login-tab{
  width: 500px;
}
</style>
