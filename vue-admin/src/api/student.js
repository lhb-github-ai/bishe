/*jshint esversion: 6 */
import request from '@/utils/request';

export default  {      
      getListByPage(current,limit,studentQuery){
        return request({
        url: `/exam/student/listByPage/${current}/${limit}`,
        method: 'post',
        data:studentQuery
             });
        },
        addStudent(student){
          return request({
            url: `/exam/student/addStudent`,
            method: 'post',
            data:student
        });
       },
        updateStudent(student){
            return request({
              url: `/exam/student/updateStudent`,
              method: 'post',
              data:student
          });
         },
         deleteStudent(id){
            return request({
            url: `/exam/student/${id}`,
            method: 'delete',
          });
         },
         listById(id){
          return request({
          url: `/exam/student/listById/${id}`,
          method: 'get',
               });
          },
};