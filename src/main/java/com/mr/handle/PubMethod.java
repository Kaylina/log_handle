package com.mr.handle;


import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with Kaylina
 * Time: 2017/5/28 13:55
 * Description: 非空判断类
 */
public class PubMethod {

    public static boolean isEmpty(String Value) {
        if (Value == null || Value.trim().equals(""))
            return true;
        else
            return false;
    }

    public static boolean isEmpty(StringBuffer Value) {
        if (Value == null || (Value.toString().trim()).equals(""))
            return true;
        else
            return false;
    }

    public static boolean isEmpty(List list) {
        if (list == null || list.size() == 0)
            return true;
        else
            return false;
    }

    public static boolean isEmpty(Set set) {
        if (set == null || set.size() == 0)
            return true;
        else
            return false;
    }

    public static boolean isEmpty(Map map) {
        if (map == null || map.size() == 0)
            return true;
        else
            return false;
    }

    public static boolean isEmpty(Object Value) {
        if (Value == null)
            return true;
        else
            return false;
    }

    public static boolean isEmpty(Double value) {
        if (value == null || value.doubleValue() == 0.0)
            return true;
        else
            return false;
    }

    public static boolean isEmpty(Long obj) {
        if (obj == null || obj.longValue() == 0)
            return true;
        else
            return false;
    }

    public static boolean isEmpty(Object[] Value) {
        if (Value == null || Value.length == 0)
            return true;
        else
            return false;
    }


    public static void main(String[] args) throws MalformedURLException {

        String a = "";
        System.out.println();
    }
}
