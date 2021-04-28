package excel;/*
 *@Author lee
 * @date 2020/07/25
 */

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class DemoExcel {

    @ExcelProperty(value = "题库名")
    private String bankName;

    @ExcelProperty(value = "题目内容")
    private String question;

    @ExcelProperty(value = "题目解析")
    private String analysis;

    @ExcelProperty(value = "正确答案")
    private String answer;

    @ExcelProperty(value = "难度等级")
    private String level;

    @ExcelProperty(value = "选项A")

    private String optionA;

    @ExcelProperty(value = "选项B")

    private String optionB;

    @ExcelProperty(value = "选项C")

    private String optionC;

    @ExcelProperty(value = "选项D")

    private String optionD;
}
