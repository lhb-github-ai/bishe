
  import request from '@/utils/request'
export default {
    listAll() {
        return request({
          url: `/exam/notice/listAll`,
          method: 'get',
        });
      },
}