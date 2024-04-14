<script setup lang="js">
</script>

<template>
  <el-header class="header">
    <el-menu mode="horizontal" router>
      <el-menu-item index="/">Home</el-menu-item>
      <el-menu-item index="/write">글 작성</el-menu-item>
      <el-menu-item index="/login">로그인</el-menu-item>
      <el-menu-item index="/signup">회원가입</el-menu-item>
    </el-menu>
  </el-header>
</template>

<style scoped>

.header {
  padding: 0;
  height: 60px;
}
</style>
