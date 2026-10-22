// ===== 前后端联调的公共代码，三个页面共用 =====

const API_BASE = '/api';

// ---------- token 的存与取 ----------
function getToken() {
    return localStorage.getItem('token');
}
function saveToken(token) {
    localStorage.setItem('token', token);
}
function clearToken() {
    localStorage.removeItem('token');
}

// 没登录就踢回登录页
function requireLogin() {
    if (!getToken()) {
        location.href = '/login.html';
    }
}

// ---------- 统一请求函数 ----------
// 所有接口都从这里走，好处是：token、错误处理只写一次
async function request(path, options = {}) {
    const headers = { 'Content-Type': 'application/json' };
    const token = getToken();
    if (token) {
        headers['Authorization'] = 'Bearer ' + token;
    }

    const res = await fetch(API_BASE + path, { ...options, headers });

    // 204 表示成功但没有响应体
    if (res.status === 204) {
        return null;
    }

    // 尝试解析 JSON，解析失败就返回 null
    const body = await res.json().catch(() => null);

    // 401 有两种含义，要分开处理：
    //   登录接口返回 401 → 用户名或密码错误，把错误显示给用户
    //   其他接口返回 401 → token 没了或过期，清掉并跳回登录页
    if (res.status === 401) {
        if (path.startsWith('/auth/')) {
            throw new Error((body && body.message) ? body.message : '用户名或密码错误');
        }
        clearToken();
        location.href = '/login.html';
        return null;
    }

    // 其他非 2xx：把后端的 message 抛出去，页面里 catch 后显示
    if (!res.ok) {
        const msg = (body && body.message) ? body.message : '请求失败（' + res.status + '）';
        throw new Error(msg);
    }

    // 成功：后端统一格式 { code, message, data }，我们只要 data
    return body ? body.data : null;
}
