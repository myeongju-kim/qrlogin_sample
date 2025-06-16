const TOKEN_KEY = 'access_token'

export const setToken = (token) => {
    sessionStorage.setItem(TOKEN_KEY, token)
}

export const getToken = () => {
    return sessionStorage.getItem(TOKEN_KEY)
}

export const isExistToken = () => {
    const token = getToken()
    return token !== undefined && token !== null && token !== ''
}