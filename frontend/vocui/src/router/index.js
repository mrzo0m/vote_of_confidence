import Vue from 'vue'
import Auth from '@okta/okta-vue'
import Router from 'vue-router'

const CALLBACK_PATH = '/implicit/callback';

const config = {
  issuer: 'https://dev-853004.okta.com/oauth2/default',
  clientId: '0oa15c29gsFacKKwr4x6',
  redirectUri: window.location.origin +  '/implicit/callback',
  scopes: ['openid', 'profile', 'email'],
  pkce: true
};

Vue.use(Auth, {...config});


const router = new Router({
  mode: 'history',
  routes: [
    { path: CALLBACK_PATH, component: Auth.handleCallback() },
  ]
});

Vue.use(Router);

router.beforeEach(Vue.prototype.$auth.authRedirectGuard());

export default router