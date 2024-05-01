import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'

import "normalize.css";

import ElementPlus from "element-plus";
import "element-plus/dist/index.css";

import "bootstrap/dist/css/bootstrap-utilities.css"; // bootstrap@5.3.3

import vueCookies from "vue-cookies";

const app = createApp(App)

app.use(createPinia());
app.use(router);
app.use(ElementPlus);
app.use(vueCookies);
app.$cookies.config("7d")


app.mount('#app')
