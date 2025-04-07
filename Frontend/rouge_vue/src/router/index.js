import { createRouter, createWebHistory } from 'vue-router'
import DefaultLayout from '@/layouts/DefaultLayout.vue'
import AuthLayout from '@/layouts/AuthLayout.vue'
import { useAuthStore } from '@/store/auth'

const routes = [
  {
    path: '/',
    component: DefaultLayout,
    meta: { requiresAuth: true },
    children: [
      { path: '', component: () => import('@/views/Home.vue') },
      {
        path: 'websites',
        children: [
          { path: 'show', component: () => import('@/views/websites/Show.vue') },
          { path: 'search', component: () => import('@/views/websites/Search.vue') },
          { path: 'diagram', component: () => import('@/views/websites/Diagram.vue') },
        ]
      },
      {
        path: 'review',
        children: [
          { path: 'detect', component: () => import('@/views/review/Detect.vue') },
          { path: 'task', component: () => import('@/views/review/Task.vue') },
        ]
      },
      {
        path: 'trace',
        children: [
          { path: 'homology', component: () => import('@/views/trace/Homology.vue') },
          { path: 'person', component: () => import('@/views/trace/Person.vue') }
        ]
      },
      {
        path: 'qa',
        component: () => import('@/views/qa/Index.vue')
      },
      {
        path: 'system',
        children: [
          { path: 'users', component: () => import('@/views/system/Users.vue') },
          { path: 'extension', component: () => import('@/views/system/Extension.vue') }
        ]
      }

    ]
  },
  {
    path: '/',
    component: AuthLayout,
    children: [
      { path: 'login', component: () => import('@/views/auth/Login.vue') },
      { path: 'register', component: () => import('@/views/auth/Register.vue') }
    ]
  },
  {
    path: '/:pathMatch(.*)*',
    component: () => import('@/views/NotFound.vue')
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const authStore = useAuthStore()

  if (to.meta.requiresAuth && !authStore.isAuthenticated) {
    next('/login')
  } else {
    next()
  }
})

export default router