package com.lee.exam;/*
 *@Author lee
 * @date 2020/12/21
 */

import org.springframework.stereotype.Component;
import java.util.Random;
import java.util.HashSet;

@Component
public class SuJiRandom {

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
