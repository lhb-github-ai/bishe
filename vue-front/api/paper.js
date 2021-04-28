import request from '@/utils/request'
export default {
  getPaperList(current,limit,id,type) {
    return request({
      url: `/exam/paper/list/${current}/${limit}/${id}/${type}`,
      method: 'get'
    })
  },
  GetById(paperId) {
    return request({
      url: `/exam/paper/findById/${paperId}`,
      method: 'get'
    })
  },
  listQuestionDX(paerp_id,doubleCheck){
    return request({
    url: `/exam/paper-question/listChoiceQuestionDX/${paerp_id}/${doubleCheck}`,
    method: 'get',
           });
        }, 
  listQuestionPD(paerp_id,doubleCheck){
   return request({
    url: `/exam/paper-question/listQuestionPD/${paerp_id}/${doubleCheck}`,
    method: 'get',
         });
        }, 
    addPaperDetail(studentId,paperDetail) {
          return request({
            url: `/exam/paperdetail/add/${studentId}`,
            method: 'post',
            data:paperDetail
          })
        },
    getPaperDetail(id,frequency) {
          return request({
            url: `/exam/paperdetail/listPaperDetail/${id}/${frequency}`,
            method: 'get'
          })
        },
}