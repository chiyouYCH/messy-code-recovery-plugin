package com.chiyou.messy.code.recovery.plugin.uitls;

import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
        result = StringUtils.isNotEmpty(result) ? result : text;
        result = unicodeDecode(result);
        return result;
    }

    /**
     * 转Unicode为汉字
     * @param text
     * @return
     */
    public static String unicodeDecode(String text) {
        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
        Matcher matcher = pattern.matcher(text);
        char ch;
        while (matcher.find()) {
            ch = (char) Integer.parseInt(matcher.group(2), 16);
            text = text.replace(matcher.group(1), ch + "");
        }
        return text;
    }
}
