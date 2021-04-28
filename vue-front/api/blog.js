import request from '@/utils/request'
export default {
  aaddBlog(blog) {
    return request({
      url: `/exam/blog/addBlog`,
      method: 'post',
      data:blog
    })
  },
  listAllType() {
    return request({
      url: `/exam/type/listAll`,
      method: 'get',
    });
  },
  listByPage(current,limit,blogQuery) {
    return request({
      url: `/exam/blog/listByPage/${current}/${limit}`,
      method: 'post',
      data:blogQuery
    });
  },
  listById(id) {
    return request({
      url: `/exam/blog/listById/${id}`,
      method: 'get',
    });
  },
  aaddComment(blog) {
    return request({
      url: `/exam/comment/addStudent`,
      method: 'post',
      data:blog
    })
  },
  listAllComment(blogId) {
    return request({
      url: `/exam/comment/listAll/${blogId}`,
      method: 'get',
    });
  },
}