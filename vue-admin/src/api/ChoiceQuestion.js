/*jshint esversion: 6 */
import request from '@/utils/request';

export default  {

       
      getListByPage(current,limit,choiceQuestionQuery){
        return request({
        url: `/exam/choice_question/listByPage/${current}/${limit}`,
        method: 'post',
        data:choiceQuestionQuery
             });
        },
        addChoiceQuestion(choiceQuestion){
          return request({
            url: `/exam/choice_question/addChoiceQuestion`,
            method: 'post',
            data:choiceQuestion
        });
       },
       listById(id){
        return request({
        url: `/exam/choice_question/listById/${id}`,
        method: 'get',
             });
        },
        update(choiceQuestion){
            return request({
              url: `/exam/choice_question/update`,
              method: 'post',
              data:choiceQuestion
          });
         },
         deleteQuestion(id){
            return request({
            url: `/exam/choice_question/${id}`,
            method: 'delete',
          });
         },
         listByLV(level,paperId){
          return request({
          url: `/exam/choice_question/listByLV/${level}/${paperId}`,
          method: 'get',
               });
          },

};