package com.chiyou.messy.code.recovery.plugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.ui.components.JBLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * @author chiyou
 * @Description
 * @create 2022-10-02
 */
public class PopAction extends AnAction {

    private static String[] charsetArr = {"UTF-8","GB18030","GB2312","GBK","Windows-1252","ISO8859-1","Big5","Shift_Jis"};


    @Override
    public void actionPerformed(AnActionEvent e) {
        //获取当前编辑器对象
        Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        //获取选择的数据模型
        SelectionModel selectionModel = editor.getSelectionModel();
        //获取当前选择的文本
        String selectedText = selectionModel.getSelectedText();

        String result = printText(selectedText);

        JBPopupFactory instance = JBPopupFactory.getInstance();//创建JBPopupFactory实例
        TextField label = new TextField(result);
        JPanel panel = new JPanel();
        panel.setSize(40,20);
        panel.add(label);
        JButton jButton = new JButton("复制");
        jButton.addActionListener(x -> {
            setClipboardString(result);

        });
        panel.add(jButton);

//        EditorTextField editorTextField = new EditorTextField();
//        editorTextField.setText(result);
        instance.createComponentPopupBuilder(new JScrollPane(panel), new JBLabel())//参数说明：内容对象,优先获取
//                .setTitle("恢复结果")
                .setMovable(true)
                .setResizable(true)
                .setNormalWindowLevel(false)
                .setMinSize(new Dimension(40,20))
                .createPopup()
                .showInFocusCenter();



    }

    /**
     * 把文本设置到剪贴板（复制）
     */
    public static void setClipboardString(String text) {
        // 获取系统剪贴板
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        // 封装文本内容
        Transferable trans = new StringSelection(text);
        // 把文本内容设置到系统剪贴板
        clipboard.setContents(trans, null);

    }


    private static String printText(String text){
        List<String> usualList = getUsualList();
        String result = new String();
        try {
            int resultMax = 0;
            for (String curCharset : charsetArr) {
                byte[] btArr = text.getBytes(curCharset);
                for (String originCharset : charsetArr) {
                    String newStr = new String(btArr, originCharset);
                    String[] singleWord = newStr.split("");
                    List<String> collect = Arrays.stream(singleWord).filter(s -> usualList.contains(s)).collect(Collectors.toList());
                    if (collect != null &&   resultMax<collect.size()) {
                        result = newStr;
                        resultMax = collect.size();
                    }
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static java.util.List<String> getUsualList() {
        InputStream is = TestAction.class.getResourceAsStream("/word.txt");
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
            System.err.println(String.format(Locale.getDefault(), "%s: load model failure!", "/prob_emit.txt"));
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException var19) {
                System.err.println(String.format(Locale.getDefault(), "%s: close failure!", "/prob_emit.txt"));
            }
        }
        return null;
    }
}
