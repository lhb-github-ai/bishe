/*jshint esversion: 6 */
import request from '@/utils/request';
export default  {  
  addNotice(notice) {
    return request({
      url: `/exam/banner/addNotice`,
      method: 'post',
      data:notice,
    });
  },
  remove(id) {
    return request({
      url: `/exam/banner/${id}`,
      method: 'delete',
    });
  },
  updateNotice(notice) {
    return request({
      url: `/exam/banner/updateNotice`,
      method: 'post',
      data:notice,
    });
  },
  listById(id) {
    return request({
      url: `/exam/banner/listById/${id}`,
      method: 'get',
    });
  },
  listAll() {
    return request({
      url: `/exam/banner/listAll`,
      method: 'get',
    });
  },
  listByPage(current,limit) {
    return request({
      url: `/exam/banner/listByPage/${current}/${limit}`,
      method: 'post',
    });
  },
}
