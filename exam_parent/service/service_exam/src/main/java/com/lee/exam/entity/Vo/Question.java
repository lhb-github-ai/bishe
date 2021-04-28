package com.lee.exam.entity.Vo;/*
 *@Author lee
 * @date 2020/10/20
 */

import lombok.Data;

@Data
public class Question {
    private String paperId;
    private String doubleCheck;
    private String IsZOrS;

    public String getPaperId() {
        return paperId;
    }

    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }

    public String getDoubleCheck() {
        return doubleCheck;
    }

    public void setDoubleCheck(String doubleCheck) {
        this.doubleCheck = doubleCheck;
    }

    public String getIsZOrS() {
        return IsZOrS;
    }

    public void setIsZOrS(String isZOrS) {
        IsZOrS = isZOrS;
    }
}
