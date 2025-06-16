import HomePage from "@/pages/home/HomePage.vue"
import LoginLayout from "@/pages/login/LoginLayout.vue"
import LoginPage from "@/pages/login/LoginPage.vue"
import QRLoginSuccessPage from "@/pages/login/QRLoginSuccessPage.vue"

import { createRouter, createWebHistory } from 'vue-router'

const routes = [
    {
        path: '/',
        component: HomePage
    },
    {
        path: '/login',
        component: LoginLayout,
        children: [
          {
            path: '',
            component: LoginPage,
          },
          {
            path: 'qr-success',
            component: QRLoginSuccessPage,
          }
        ]
      }

]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router