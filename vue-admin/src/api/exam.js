/*jshint esversion: 6 */
import request from '@/utils/request';

export default  {      
      getListByPage(current,limit,VoScore){
        return request({
        url: `/exam/score/listByPageAndId/${current}/${limit}`,
        method: 'post',
        data:VoScore
             });
        },
        delete(id){
          return request({
          url: `/exam/score/${id}`,
          method: 'delete',
        });
       },
};