/*jshint esversion: 6 */
import request from '@/utils/request';
export default  {  
  addNotice(notice) {
    return request({
      url: `/exam/notice/addNotice`,
      method: 'post',
      data:notice,
    });
  },
  remove(id) {
    return request({
      url: `/exam/notice/${id}`,
      method: 'delete',
    });
  },
  updateNotice(notice) {
    return request({
      url: `/exam/notice/updateNotice`,
      method: 'post',
      data:notice,
    });
  },
  listById(id) {
    return request({
      url: `/exam/notice/listById/${id}`,
      method: 'get',
    });
  },
  listAll() {
    return request({
      url: `/exam/notice/listAll`,
      method: 'get',
    });
  },
  listByPage(current,limit,noticeQuery) {
    return request({
      url: `/exam/notice/listByPage/${current}/${limit}`,
      method: 'post',
      data:noticeQuery,
    });
  },
}
