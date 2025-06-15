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
      <div style="flex: 1;">
        <VTypography variant="h3" class="font-weight-bold" style="color: #005A9C;">
          컨텐츠 영역
        </VTypography>
      </div>

      <div style="width: 360px;">
        <LoginTabs />
      </div>
    </VContainer>
  </VContainer>
</template>

<script setup>
import LoginTabs from './components/LoginTabs.vue'
</script>

<style scoped>
/* 필요시 추가 */
</style>
