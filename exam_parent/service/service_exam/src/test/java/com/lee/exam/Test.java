package com.lee.exam;/*
 *@Author lee
 * @date 2020/12/17
 */

import java.util.HashSet;
import java.util.Random;

public class Test {

    public static void main(String[] args) {
        String sre= random(1,3);
         String[] chars=   sre.split(",");
        for (int i = 0; i < chars.length; i++) {
            System.out.println( chars[i] );
        }


    }

    public static String random(Integer size,Integer num){
        Random random = new Random();
        StringBuilder result = new StringBuilder();
        HashSet<Integer> hs = new HashSet<Integer>();
        while(hs.size() < num){
            hs.add(random.nextInt(size)+1);
        }
        for (Integer integer : hs) {
//            System.out.println(integer);
            result = result.append(integer);
            result.append(",");
        }
        return (result.substring(0, result.length()-1));
    }
}
