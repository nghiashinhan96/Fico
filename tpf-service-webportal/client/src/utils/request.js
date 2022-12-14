import axios from 'axios'
import { MessageBox, Message } from 'element-ui'
import store from '@/store'
import * as cookie from '@/utils/cookie'
import router, { resetRouter } from '@/router'

// tao axios goi api
const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API,
})

// setting truoc khi api dang gui di
service.interceptors.request.use(
  config => {
    const token = cookie.getToken()
    if (token) {
      config.headers['Authorization'] = 'Bearer ' + token
    }
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

// setting sau khi api nhan response ve
service.interceptors.response.use(
  response => {
    let res = { data: response.data }
    if (response && response.headers && response.headers['x-pagination-total']) {
      res.total = response.headers['x-pagination-total']
    }

    return res
    // if the custom code is not 20000, it is judged as an error.
    // if (res.code !== 20000) {
    //   Message({
    //     message: res.message || 'Error',
    //     type: 'error',
    //     duration: 5 * 1000
    //   })

    //   // 50008: Illegal token; 50012: Other clients logged in; 50014: Token expired;
    //   if (res.code === 50008 || res.code === 50012 || res.code === 50014) {
    //     // to re-login
    //     MessageBox.confirm('You have been logged out, you can cancel to stay on this page, or log in again', 'Confirm logout', {
    //       confirmButtonText: 'Re-Login',
    //       cancelButtonText: 'Cancel',
    //       type: 'warning'
    //     }).then(() => {
    //       store.dispatch('user/resetToken').then(() => {
    //         location.reload()
    //       })
    //     })
    //   }
    //   return Promise.reject(new Error(res.message || 'Error'))
    // } else {
    //   return res
    // }
  },
  error => {
    if (!error.response) {

    } else if (error.response.status === 401) {
      cookie.clearCookie()

      router.push(`/login?redirect=${router.fullPath}`)
    } else {
      const message = error.response.data['message'] || error.response.data['error_description']

    }
    Message({
      message: error.message,
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)

export default service
