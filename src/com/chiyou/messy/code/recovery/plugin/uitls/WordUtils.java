package com.chiyou.messy.code.recovery.plugin.uitls;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author chiyou
 * @Description
 * @create 2022-10-04
 */
public class WordUtils {


    /**
     * 获取常用汉字
     * @return
     */
    public static List<String> getUsualList() {
        InputStream is = WordUtils.class.getResourceAsStream("/word.txt");
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            List<String> result = new ArrayList<>();

            while(br.ready()) {
                String line = br.readLine();
                String[] words = line.split(" ");
                for (String word : words) {
                    result.add(word);
                }
            }
            return result;
        } catch (IOException var20) {
            System.err.println(String.format(Locale.getDefault(), "%s: load model failure!", "/word.txt"));
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException var19) {
                System.err.println(String.format(Locale.getDefault(), "%s: close failure!", "/word.txt"));
            }
        }
        return null;
    }
}
