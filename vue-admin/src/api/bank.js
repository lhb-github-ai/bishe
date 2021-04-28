/*jshint esversion: 6 */
import request from '@/utils/request';

export default  {      
      getListByPage(current,limit,bankQuery){
        return request({
        url: `/exam/bank/listByPage/${current}/${limit}`,
        method: 'post',
        data:bankQuery
             });
        },
        addBank(bank){
          return request({
            url: `/exam/bank/addBank`,
            method: 'post',
            data:bank
        });
       },
       listById(id){
        return request({
        url: `/exam/bank/listById/${id}`,
        method: 'get',
             });
        },
        findAllList(){
          return request({
          url: `/exam/bank/list`,
          method: 'get',
               });
          },
        updateBank(bank){
            return request({
              url: `/exam/bank/updateBank`,
              method: 'post',
              data:bank
          });
         },
         deleteBank(id){
            return request({
            url: `/exam/bank/${id}`,
            method: 'delete',
          });
         },

};