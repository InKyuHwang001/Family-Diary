<script setup lang="js">
import {ref} from "vue";
import {useRouter} from "vue-router";
import axios from "axios";

const password = ref("");
const userName = ref("");
const email = ref("");

const router = useRouter();

const signup = function () {
  axios
      .post("/inner/api/v1/users/signup", {
        email: email.value,
        password: password.value,
        userName: userName.value
      })
      .then(() => {
        router.replace({name: "home"})
      })
}
</script>

<template>
  <el-form id="signupForm" class="align-content-center" label-width="auto" style="max-width: 600px">

    <h2>회원가입</h2>
    <div class="mt-2">
      <el-form-item label="email">
        <el-input v-model="email" placeholder="email"/>
      </el-form-item>
    </div>
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
      <el-button type="success" @click="signup()">회원가입</el-button>
    </div>

  </el-form>

</template>
<style scoped lang="css">
#signupForm {
  width: 50%;
  margin: auto;

}

</style>