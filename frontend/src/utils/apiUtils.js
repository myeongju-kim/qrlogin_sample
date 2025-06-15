import axios from 'axios'

const api = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || '/', 
  timeout: 10000, 
  headers: {
    'Content-Type': 'application/json',
  },
})

api.interceptors.request.use(
  config => {
    return config
  },
  error => Promise.reject(error)
)

api.interceptors.response.use(
  response => response.data,
  error => {
    console.error('API 에러:', error)
    return Promise.reject(error)
  }
)

export const $apiGet = (url, params = {}, config = {}) => {
    return api.get(url, {
      params,
      responseType: config.responseType || 'json',
      ...config,
    })
  }

// POST 요청
export const $apiPost = (url, data = {}, config = {}) => {
  return api.post(url, data, config)
}
