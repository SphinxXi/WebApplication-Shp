package com.app.util;

/**
 * @author SphinxXi
 * @classname StringUtil
 * @createTime: 2023/12/16 17:00
 *
 *  字符串工具类
 */
public class StringUtil {
    public static boolean isEmpyt(String str){
        if(str==null || "".equals(str.trim())){
            return true;
        }
        return false;
    }
}
