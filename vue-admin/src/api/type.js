/*jshint esversion: 6 */
import request from '@/utils/request';
export default  {  
  addNotice(notice) {
    return request({
      url: `/exam/type/addNotice`,
      method: 'post',
      data:notice,
    });
  },
  remove(id) {
    return request({
      url: `/exam/type/${id}`,
      method: 'delete',
    });
  },
  updateNotice(notice) {
    return request({
      url: `/exam/type/updateNotice`,
      method: 'post',
      data:notice,
    });
  },
  listById(id) {
    return request({
      url: `/exam/type/listById/${id}`,
      method: 'get',
    });
  },
  listAll() {
    return request({
      url: `/exam/type/listAll`,
      method: 'get',
    });
  },
  listByPage(current,limit) {
    return request({
      url: `/exam/type/listByPage/${current}/${limit}`,
      method: 'post',
    });
  },
}
