/*jshint esversion: 6 */
import request from '@/utils/request';

export default  {

       
      getListByPage(current,limit,choiceQuestionQuery){
        return request({
        url: `/exam/judge_question/listByPage/${current}/${limit}`,
        method: 'post',
        data:choiceQuestionQuery
             });
        },
        addChoiceQuestion(judgeQuestion){
          return request({
            url: `/exam/judge_question/addJudgeQuestion`,
            method: 'post',
            data:judgeQuestion
        });
       },
       listById(id){
        return request({
        url: `/exam/judge_question/listById/${id}`,
        method: 'get',
             });
        },
        update(judgeQuestion){
            return request({
              url: `/exam/judge_question/update`,
              method: 'post',
              data:judgeQuestion
          });
         },
         deleteQuestion(id){
            return request({
            url: `/exam/judge_question/${id}`,
            method: 'delete',
          });
         },
         listByLV(level,paperId){
          return request({
          url: `/exam/judge_question/listByLV/${level}/${paperId}`,
          method: 'get',
               });
          },

};