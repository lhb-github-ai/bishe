import request from '@/utils/request'
export default {
  addScore(Score) {
    return request({
      url: `/exam/score/addScore`,
      method: 'post',
      data:Score
    })
  },
  getList(current,limit,studentId) {
    return request({
      url: `/exam/score/listByPage/${current}/${limit}/${studentId}`,
      method: 'get'
    })
  },
 
}