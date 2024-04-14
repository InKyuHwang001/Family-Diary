<script setup lang="js">
import {ref} from "vue";
import {useRouter} from "vue-router";
import axios from "axios";

const title = ref("");
const body = ref("");

const router = useRouter();

const write = function (){
  axios
      .post("/inner/api/v1/posts", {
            title: title.value,
            body: body.value
      })
      .then(() => {
        router.replace({name:"home"})
      })
}
</script>

<template>
  <div class="mt-2">
    <el-input v-model="title" placeholder="제목을 입력하세요"></el-input>
  </div>

  <div class="mt-2">
    <el-input v-model="body" type="textarea" placeholder="내용을 입력하세요"  rows="15"/>
  </div>

  <div class="mt-2">
    <div class="d-flex justify-content-end">
      <el-button type="primary" @click="write()">작성완료</el-button>
    </div>
  </div>
</template>
<style></style>