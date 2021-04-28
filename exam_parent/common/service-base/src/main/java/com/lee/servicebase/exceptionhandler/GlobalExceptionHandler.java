package com.lee.servicebase.exceptionhandler;/*
 *@Author lee
 * @date 2020/07/21
 */



import com.alibaba.excel.exception.ExcelAnalysisException;
import com.lee.exam.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
      public R error(Exception e){
        e.printStackTrace();
        return R.error().message("执行了全局异常处理...");
      }


    @ExceptionHandler(ExamException.class)
    @ResponseBody
    public R error(ExamException e){
        e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMsg());
    }
    @ExceptionHandler(ExamNullException.class)
    @ResponseBody
    public R error1(ExamNullException e){
        e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMsg());
    }
}
