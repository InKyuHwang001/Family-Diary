<script setup lang="js">
import {ref} from "vue";
import {useRouter} from "vue-router";
import axios from "axios";

const title = ref("");
const body = ref("");

const router = useRouter();

const post = ref({
  id: 0,
  title: "",
  content: "",
});

const props = defineProps({
  postId: {
    require: true,
  },
});

axios.get(`/api/posts/${props.postId}`).then((response) => {
  post.value = response.data;
});

const modify = () => {
  axios.put(`/api/posts/${props.postId}`, post.value).then(() => {
    router.replace({ name: "home" });
  });
};
</script>

<template>
  <div>
    <el-input v-model="post.title" />
  </div>

  <div class="mt-2">
    <el-input v-model="post.content" type="textarea" rows="15" />
  </div>

  <div class="mt-2 d-flex justify-content-end">
    <el-button type="warning" @click="modify()">수정완료</el-button>
  </div>
</template>

<style></style>