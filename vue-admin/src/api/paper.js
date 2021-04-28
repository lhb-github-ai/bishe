/*jshint esversion: 6 */
import request from '@/utils/request';

export default  {

  getListByPage(current,limit,teacherId,paperQuery){
    return request({
    url: `/exam/paper/listByPage/${current}/${limit}/${teacherId}`,
    method: 'post',
    data:paperQuery
         });
    },
        addPaper(paper){
          return request({
            url: `/exam/paper/addPaper`,
            method: 'post',
            data:paper
        });
       },
       updatePaper(paper){
        return request({
          url: `/exam/paper/update`,
          method: 'post',
          data:paper
      });
     },
       findByName(paperName){
        return request({
          url: `/exam/paper/findByName/${paperName}`,
          method: 'get',
      });
     },
     findById(paperId){
      return request({
        url: `/exam/paper/findById/${paperId}`,
        method: 'get',
    });
   },
   savePaper(paper){
    return request({
      url: `/exam/paper/savePaper`,
      method: 'post',
      data:paper
    });
    },
    delete(id){
      return request({
      url: `/exam/paper/${id}`,
      method: 'delete',
    });
   },

   //试卷详情
   getPaperDetail(id,frequency) {
    return request({
      url: `/exam/paperdetail/listPaperDetail/${id}/${frequency}`,
      method: 'get'
    })
  },
};