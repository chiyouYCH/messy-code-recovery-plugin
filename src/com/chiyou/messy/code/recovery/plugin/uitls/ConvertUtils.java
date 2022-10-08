package com.chiyou.messy.code.recovery.plugin.uitls;

import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 转换工具类
 * @author chiyou
 * @Description
 * @create 2022-10-04
 */
public class ConvertUtils {

    //常见编码格式
    private static String[] charsetArr = {"UTF-8","GB18030","GB2312","GBK","Windows-1252","ISO8859-1","Big5","Shift_Jis"};
    /**
     * 转换乱码
     * @param text
     * @return
     */
    public static String convertText(String text){
        List<String> usualList = WordUtils.getUsualList();
        String result = new String();
        try {
            int resultMax = 0;
            for (String curCharset : charsetArr) {
                byte[] btArr = text.getBytes(curCharset);
                for (String originCharset : charsetArr) {
                    String newStr = new String(btArr, originCharset);
                    String[] singleWord = newStr.split("");
                    //属于常用汉字
                    List<String> collect = Arrays.stream(singleWord).filter(s -> usualList.contains(s)).collect(Collectors.toList());
                    if (collect != null && resultMax<collect.size()) {
                        result = newStr;
                        resultMax = collect.size();
                    }
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return StringUtils.isNotEmpty(result)?result:text;
    }
}
