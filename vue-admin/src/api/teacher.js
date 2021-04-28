/*jshint esversion: 6 */
import request from '@/utils/request';

export default  {      
      getListByPage(current,limit,teacherQuery){
        return request({
        url: `/exam/teacher/listByPage/${current}/${limit}`,
        method: 'post',
        data:teacherQuery
             });
        },
        addTeacher(teacher){
          return request({
            url: `/exam/teacher/addTeacher`,
            method: 'post',
            data:teacher
        });
       },
        updateTeacher(teacher){
            return request({
              url: `/exam/teacher/updateTeacher`,
              method: 'post',
              data:teacher
          });
         },
         deleteTeacher(id){
            return request({
            url: `/exam/teacher/${id}`,
            method: 'delete',
          });
         },
         listById(id){
          return request({
          url: `/exam/teacher/listById/${id}`,
          method: 'get',
               });
          },
          updateAdmin(teacher){
            return request({
              url: `/exam/admin/updateNotice`,
              method: 'post',
              data:teacher
          });
         },
         listByIdAdmin(id){
          return request({
          url: `/exam/admin/listById/${id}`,
          method: 'get',
               });
          },
};