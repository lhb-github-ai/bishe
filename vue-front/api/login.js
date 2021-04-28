import request from '@/utils/request'
export default {

    submitLogin(studentId,password) {
      return request({
        url: `/exam/student/login/${studentId}/${password}`,
        method: 'get',
      })
    },
    getLoginInfo(studentId) {
      return request({
        url: `/exam/student/getInfo/${studentId}`,
        method: 'get',
      })
    },
    listById(id) {
      return request({
        url: `/exam/student/listById/${id}`,
        method: 'get',
      })
    },
    update(student) {
      return request({
        url: `/exam/student/updateStudent`,
        method: 'post',
        data:student
      })
    },
}