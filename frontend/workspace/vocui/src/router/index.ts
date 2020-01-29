import Vue from 'vue'
import Router from 'vue-router'
import Auth from '@okta/okta-vue'
import Private from '@/components/Private'

const config = {
  issuer: 'https://dev-853004.okta.com/oauth2/default',
  clientId: '0oa15c29gsFacKKwr4x6',
  redirectUri: 'http://localhost:8080/implicit/callback',
  scopes: ['openid', 'profile', 'email'],
  pkce: true
}

Vue.use(Auth, { ...config })

const CALLBACK_PATH = '/implicit/callback'

const router = new Router({
  mode: 'history',
  routes: [
    { path: CALLBACK_PATH, component: Auth.handleCallback() },
    { path: '/private', component: Private, meta: { requiresAuth: true } },
  ]
})

Vue.use(Router)

router.beforeEach(Vue.prototype.$auth.authRedirectGuard())

export default router

// import Vue from 'vue'
// import VueRouter from 'vue-router'
// import Home from '../views/Home.vue'
// import Auth from '@okta/okta-vue'

// Vue.use(VueRouter)
// Vue.use(Auth, {
//   issuer: 'https://dev-853004.okta.com/oauth2/default',
//   clientId: '0oa15c29gsFacKKwr4x6',
//   redirectUri: 'http://localhost:8080/implicit/callback',
//   scopes: ['openid', 'profile', 'email'],
//   pkce: true
// })

// const routes = [
//   {
//     path: '/',
//     name: 'home',
//     component: Home
//   },
//   { path: '/implicit/callback',
//     component: Auth.handleCallback()
//   },
//   {
//     path: '/about',
//     name: 'about',
//     // route level code-splitting
//     // this generates a separate chunk (about.[hash].js) for this route
//     // which is lazy-loaded when the route is visited.
//     component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
//   }
// ]

// const router = new VueRouter({
//   routes
// })

// export default router
