<script setup lang="js">
import {ref} from "vue";
import {useRouter} from "vue-router";
import axios from "axios";

const password = ref("");
const userName = ref("");

const router = useRouter();

const login = function () {
  axios
      .post("/inner/api/v1/users/login", {
        password: password.value,
        userName: userName.value
      })
      .then(() => {
        alert("성공")
        //router.replace({name: "home"})
      })
}
</script>

<template>
  <el-form id="loginForm" class="align-content-center" label-width="auto" style="max-width: 600px">

    <h2>로그인</h2>
    <div class="mt-2">
      <el-form-item label="user name">
        <el-input v-model="userName" placeholder="user name"/>
      </el-form-item>
    </div>
    <div class="mt-2">
      <el-form-item label="password">
        <el-input v-model="password" placeholder="password" type="password"/>
      </el-form-item>
    </div>
    <div class="mt-2 d-flex justify-content-end">
      <el-button type="success" @click="login()">로그인</el-button>
    </div>

  </el-form>

</template>
<style scoped lang="css">
#loginForm {
  width: 500px;
  margin: auto;
}

</style>