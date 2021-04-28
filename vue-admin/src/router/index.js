/*jshint esversion: 6 */
import Vue from 'vue';
import Router from 'vue-router';

// in development-env not use lazy-loading, because lazy-loading too many pages will cause webpack hot update too slow. so only in production use lazy-loading;
// detail: https://panjiachen.github.io/vue-element-admin-site/#/lazy-loading

Vue.use(Router)

/* Layout */
import Layout from '../views/layout/Layout'

/**
* hidden: true                   if `hidden:true` will not show in the sidebar(default is false)
* alwaysShow: true               if set true, will always show the root menu, whatever its child routes length
*                                if not set alwaysShow, only more than one route under the children
*                                it will becomes nested mode, otherwise not show the root menu
* redirect: noredirect           if `redirect:noredirect` will no redirect in the breadcrumb
* name:'router-name'             the name is used by <keep-alive> (must set!!!)
* meta : {
    title: 'title'               the name show in submenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar,
  }
**/
export const constantRouterMap = [
  { path: '/login', component: () => import('@/views/login/index'), hidden: true },
  { path: '/404', component: () => import('@/views/404'), hidden: true },


  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    name: '首页',
    meta: { title: '首页' },
    hidden: true,
    children: [{
      path: 'dashboard',
      component: () => import('@/views/dashboard/index'),
    
    }]
  },
  {
    path: '/bank',
    component: Layout,
    redirect: '/bank/list',
    name: '类型管理',
    meta: { title: '类型管理' },
    children: [
      {
        path: 'list',
        name: '类型列表',
        component: () => import('@/views/bank/index'),
        meta: { title: '类型列表' }
      },
      {
        path: 'addBank',
        name: '添加类型',
        component: () => import('@/views/bank/addBank'),
        meta: { title: '添加类型' }
      },
      {
        path: 'editBank/:id',   
        component: () => import('@/views/bank/addBank'),
        meta: { title: '修改类型', noCache: true },
        hidden: true
      }
    ]
  },
  {
    path: '/question',
    component: Layout,
    redirect: '/question/list',
    name: '题库管理',
    meta: { title: '题库管理' },
    children: [
      {
        path: 'choiceQuestion',
        name: '单选题目列表',
        component: () => import('@/views/question/choiceQuestion'),
        meta: { title: '单选题目列表' }
      },
      {
        path: 'addChoiceQuestion',
        name: '添加单选题目',
        component: () => import('@/views/question/addChoiceQuestion'),
        meta: { title: '添加单选题目' }
      },
      {
        path: 'editChoiceQuestion/:id',   
        component: () => import('@/views/question/addChoiceQuestion'),
        meta: { title: '修改单选题目', noCache: true },
        hidden: true
      },
      {
        path: 'batch',   
        component: () => import('@/views/question/batch'),
        hidden: true
      },
      {
        path: 'judgeQuestion',
        name: '判断题目列表',
        component: () => import('@/views/question/judgeQuestion'),
        meta: { title: '判断题目列表' }
      },
      {
        path: 'addJudgeQuestion',
        name: '添加判断题目',
        component: () => import('@/views/question/addJudgeQuestion'),
        meta: { title: '添加判断题目' }
      },
      {
        path: 'editJudgeQuestion/:id',   
        component: () => import('@/views/question/addJudgeQuestion'),
        meta: { title: '修改单选题目', noCache: true },
        hidden: true
      },
      {
        path: 'batchTwo',   
        component: () => import('@/views/question/batchTwo'),
        hidden: true
      },
      
    ]
  },
  {
    path: '/paper',
    component: Layout,
    redirect: '/paper/list',
    name: '试卷管理',
    meta: { title: '试卷管理' },
    children: [
      {
        path: 'list',
        name: '试卷列表',
        component: () => import('@/views/paper/index'),
        meta: { title: '试卷列表' }
      },
      {
        path: 'addPaper',
        name: '添加试卷',
        component: () => import('@/views/paper/addPaper'),
        meta: { title: '添加试卷' },
      
      },
      {
        path: 'editPaper/:paperId',   
        component: () => import('@/views/paper/addPaper'),
        meta: { title: '修改试卷', noCache: true },
        hidden: true
      },
      {
        path: 'paper_question/:paperId',   
        component: () => import('@/views/paper/paper_question'),
        hidden: true
      },
      {
        path: 'Paper/:paperId',   
        component: () => import('@/views/paper/Paper'),
        hidden: true
      },
      {
        path: 'publish/:paperId',   
        component: () => import('@/views/paper/publish'),
        hidden: true
      },
      {
        path: 'choiceQuestion/:paperId',   
        component: () => import('@/views/paper/choiceQuestion'),
        hidden: true
      },
      {
        path: 'JudgeQuestion/:paperId',   
        component: () => import('@/views/paper/JudgeQuestion'),
        hidden: true
      },
    ]
  },
  {
    path: '/student',
    component: Layout,
    redirect: '/student/index',
    name: '学生管理',
    meta: { title: '学生管理' },
    children: [
      {
        path: 'index',
        name: '学生列表',
        component: () => import('@/views/student/index'),
        meta: { title: '学生列表'}
      },
      {
        path: 'addStudent',
        name: '添加学生',
        component: () => import('@/views/student/addStudent'),
        meta: { title: '添加学生信息' }
      },
      {
        path: 'editStudent/:id',   
        component: () => import('@/views/student/addStudent'),
        meta: { title: '修改学生信息', noCache: true },
        hidden: true
      },
      {
        path: 'batch',   
        component: () => import('@/views/student/batch'),
        hidden: true
      },
     
    ]
  },
  {
    path: '/teacher',
    component: Layout,
    redirect: '/teacher/index',
    name: '老师管理',
    meta: { title: '老师管理'},
    children: [
      {
        path: 'index',
        name: '老师列表',
        component: () => import('@/views/teacher/index'),
        meta: { title: '老师列表' }
      },
      {
        path: 'addTeacher',
        name: '添加老师',
        component: () => import('@/views/teacher/addTeacher'),
        meta: { title: '添加老师信息'}
      },
      {
        path: 'editTeacher/:id',   
        component: () => import('@/views/teacher/addTeacher'),
        meta: { title: '修改老师信息', noCache: true },
        hidden: true
      },
      {
        path: 'batch',   
        component: () => import('@/views/student/batch'),
        hidden: true
      },
     
    ]
  },
  {
    path: '/exam',
    component: Layout,
    redirect: '/exam/index',
    name: '成绩管理',
    meta: { title: '成绩管理' },
    children: [
      {
        path: 'index',
        name: '成绩列表',
        component: () => import('@/views/exam/index'),
        meta: { title: '成绩列表' }
      },
      {
        path: 'paperDetail/:id/:frequency/:score',   
        component: () => import('@/views/exam/paperDetail'),
        hidden: true
      },
     
    ]
  },
  {
    path: '/notice',
    component: Layout,
    redirect: '/notice/index',
    name: '公告管理',
    meta: { title: '公告管理' },
    children: [
      {
        path: 'index',
        name: '公告列表',
        component: () => import('@/views/notice/index'),
        meta: { title: '公告列表' }
      },
      {
        path: 'addNotice',
        name: '添加公告',
        component: () => import('@/views/notice/addNotice'),
        meta: { title: '添加公告' }
      },
      {
        path: 'editNotice/:id',   
        component: () => import('@/views/notice/addNotice'),
        meta: { title: '修改公告信息', noCache: true },
        hidden: true
      },
     
    ]
  },
  {
    path: '/banner',
    component: Layout,
    redirect: '/banner/index',
    name: '轮播图管理',
    meta: { title: '轮播图管理',  },
    children: [
      {
        path: 'index',
        name: '轮播图列表',
        component: () => import('@/views/banner/index'),
        meta: { title: '轮播图列表', }
      },
      {
        path: 'addBanner',
        name: '添加轮播图',
        component: () => import('@/views/banner/addBanner'),
        meta: { title: '添加轮播图',  }
      },
      {
        path: 'editBanner/:id',   
        component: () => import('@/views/banner/addBanner'),
        meta: { title: '修改轮播图', noCache: true },
        hidden: true
      },
     
    ]
  },
  {
    path: '/type',
    component: Layout,
    redirect: '/notice/index',
    name: '信息类型管理',
    meta: { title: '信息类型管理' },
    children: [
      {
        path: 'index',
        name: '信息类型列表',
        component: () => import('@/views/type/index'),
        meta: { title: '信息列表' }
      },
      {
        path: 'addNotice',
        name: '添加信息类型',
        component: () => import('@/views/type/addType'),
        meta: { title: '添加类型' }
      },
      {
        path: 'editNotice/:id',   
        component: () => import('@/views/type/addType'),
        meta: { title: '修改类型', noCache: true },
        hidden: true
      },
     
    ]
  },
  { path: '*', redirect: '/404', hidden: true }
]

export default new Router({
  // mode: 'history', //后端支持可开
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})
