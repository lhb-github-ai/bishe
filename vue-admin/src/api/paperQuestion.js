/*jshint esversion: 6 */
import request from '@/utils/request';

export default  {

      listByBankId(paperId,level){
        return request({
        url: `/exam/paper-question/listByBankId/${paperId}/${level}`,
        method: 'get',
             });
        }, 
      
        addPaperQuestion(Question,checkedChoiceQuestions){
          const params = new URLSearchParams();
              params.append('checkedChoiceQuestions', checkedChoiceQuestions);
          return request({
          url: `/exam/paper-question/addPaperQuestion`,
          method: 'post',
          data:Question,params
               });
          },
          listQuestion(paerp_id,doubleCheck,isZOrS){
               return request({
               url: `/exam/paper-question/listChoiceQuestion/${paerp_id}/${doubleCheck}/${isZOrS}`,
               method: 'get',
                    });
               }, 
          listJudgeQuestion(paerp_id,doubleCheck,isZOrS){
                    return request({
                    url: `/exam/paper-question/listJudgeQuestion/${paerp_id}/${doubleCheck}/${isZOrS}`,
                    method: 'get',
                         });
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
          delete(id){
                    return request({
                    url: `/exam/paper-question/${id}`,
                    method: 'delete',
                  });
                 }, 
          removeBath(checkedChoiceQuestions){
               const params = new URLSearchParams();
               params.append('checkedChoiceQuestions', checkedChoiceQuestions);
                    return request({
                    url: `/exam/paper-question/removeBath`,
                    method: 'post',
                    data:params
                  });
                 },
         deleteByPaperId(paerp_id,isZOrS){
                    return request({
                    url: `/exam/paper-question/remove/${paerp_id}/${isZOrS}`,
                    method: 'delete',
                         });
                    }, 
          listByPaperId(paerp_id){
                    return request({
                    url: `/exam/paper-question/listByPaperId/${paerp_id}`,
                    method: 'get',
                         });
                    }, 
           addSuiJiPaperQuestion(SuJiNum){
                         return request({
                         url: `/exam/paper-question/addSuijiPaperQuestion`,
                         method: 'post',
                         data:SuJiNum
                              });
                         },            
                    
};