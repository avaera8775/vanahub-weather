import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
    },
    {
      path: '/news',
      name: 'news',
      // route level code-splitting
      // this generates a separate chunk (News.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/NewsView.vue'),
    },
    {
      path: '/weather',
      name: 'weather',
      // route level code-splitting
      // this generates a separate chunk (Weather.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/WeatherView.vue'),
    },
    {
      path: '/api',
      name: 'api',
      // route level code-splitting
      // this generates a separate chunk (Weather.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/apiDocumentation.vue'),
    },
  ],
})

export default router