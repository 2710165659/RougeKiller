import { createRouter, createWebHashHistory, createWebHistory } from 'vue-router'
import DefaultLayout from '@/layouts/DefaultLayout.vue'
import AuthLayout from '@/layouts/AuthLayout.vue'
import { useAuthStore } from '@/store/auth'

const routes = [
  {
    path: '/',
    component: DefaultLayout,
    meta: { requiresAuth: true },
    children: [
      { path: '', redirect: '/websites/show' },
      {
        path: 'websites',
        children: [
          { path: 'show', component: () => import('@/views/websites/WebsitesShow.vue') },
          { path: 'search', component: () => import('@/views/websites/WebsitesSearch.vue') },
          { path: 'detail/:id', component: () => import('@/views/websites/WebsitesDetail.vue') },
        ]
      },
      {
        path: 'review',
        children: [
          { path: 'detect', component: () => import('@/views/review/ReviewDetect.vue') },
          { path: 'task', component: () => import('@/views/review/ReviewTask.vue') },
        ]
      },
      {
        path: 'trace',
        children: [
          { path: 'homology', component: () => import('@/views/trace/TraceHomology.vue') },
          { path: 'person', component: () => import('@/views/trace/TracePerson.vue') }
        ]
      },
      {
        path: 'qa',
        component: () => import('@/views/qa/QaIndex.vue')
      },
      {
        path: 'system',
        children: [
          { path: 'users', component: () => import('@/views/system/SystemUsers.vue') },
          { path: 'extension', component: () => import('@/views/system/SystemExtension.vue') }
        ]
      }

    ]
  },
  {
    path: '/',
    component: AuthLayout,
    children: [
      { path: 'login', component: () => import('@/views/auth/AuthLogin.vue') },
      { path: 'register', component: () => import('@/views/auth/AuthRegister.vue') }
    ]
  },
  {
    path: '/:pathMatch(.*)*',
    component: () => import('@/views/NotFound.vue')
  }
]

const router = createRouter({
  history: createWebHashHistory(),
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