/*jshint esversion: 6 */
import request from '@/utils/request';
export default  {  
  login(username, password) {
    return request({
      url: `service/user/loginUser/${username}/${password}`,
      method: 'post',
    });
  }
 };
export function login(username, password) {
  return request({
    url: `service/user/login/`,
    method: 'post',
  });
}

export function getInfo(token,id,role) {
  return request({
    url: `service/user/info/${id}/${role}`,
    method: 'get',
    params: { token }
  });
}

export function logout() {
  return request({
    url: '/service/user/logout',
    method: 'post'
  });
}
