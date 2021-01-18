package com.ahdz.oricalshelves.util;

import com.ahdz.oricalshelves.MyApplication;

import java.util.ArrayList;
import java.util.List;

/**
 *    可以产生各种list的工具类
 */
public class ListUtil {


    /**
     *   返回空字符串list
     * @param num
     * @return
     */
    public static List<String> returnList(int num) {

        List<String> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            list.add("");
        }

        return list;
    }


    public static List<Integer> numList(int num) {
        return numList(0, num);
    }

    /**
     *  返回从 start到end的数组
     * @param start
     * @param end
     * @return
     */
    public static List<Integer> numList(int start,int end) {
        List<Integer> list = new ArrayList<>();

        for (int i = start; i <  end; i++) {
            list.add(i);
        }
        return list;
    }


    public static List<String> returnnumList(int start ,int end){
        List<String> list = new ArrayList<>();
        for (int i = start; i <  end; i++) {
            list.add(i+"");
        }
        return list;
    }


    public static List<String> arraysToList(String[] strings) {
        if (strings==null || strings.length==0){
            return new ArrayList<>();
        }
        List<String> list = new ArrayList<>();
        for (int i = 0; i < strings.length; i++) {
            list.add(strings[i]);
        }
        return list;
    }

    public static List<String> arraysToList(int arrayId) {
        String[] strings = MyApplication.getInstance().getResources().getStringArray(arrayId);
        if (strings==null || strings.length==0){
            return new ArrayList<>();
        }
        List<String> list = new ArrayList<>();
        for (int i = 0; i < strings.length; i++) {
            list.add(strings[i]);
        }
        return list;
    }


    public static <T> List<T> getTList(int num) {
        List<T> list = new ArrayList<T>();
        T data = (T) new Object();
        for (int i = 0; i < num; i++) {
            list.add(data);
        }
        return list;
    }



}
