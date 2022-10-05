package com.chiyou.messy.code.recovery.plugin.action;

import com.chiyou.messy.code.recovery.plugin.uitls.ConvertUtils;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.ui.components.JBLabel;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

/**
 * @author chiyou
 * @Description
 * @create 2022-10-02
 */
public class PopAction extends AnAction {


    //案例：醱勤掩极鏢厗腔峉儂ㄛ蠅眒冪羶衄域楊疑疑汜湔狟賸ㄛ憩砉岆醱勤骨痌腔瓷珨欴ㄛ疑疑華徹藩珨毞ㄛ毀奧汜堤賸拸癹洷咡﹝

    @Override
    public void actionPerformed(AnActionEvent e) {
        //获取当前编辑器对象
        Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        //获取选择的数据模型
        SelectionModel selectionModel = editor.getSelectionModel();
        //获取当前选择的文本
        String selectedText = selectionModel.getSelectedText();
        //乱码恢复
        String result = ConvertUtils.convertText(selectedText);
        //创建JBPopupFactory实例
        JBPopupFactory instance = JBPopupFactory.getInstance();
        TextField label = new TextField(result);
        JPanel panel = new JPanel();
        panel.setSize(40,20);
        panel.add(label);
        //添加按钮
        JButton jButton = new JButton("复制");
        jButton.addActionListener(x -> {
            setClipboardString(result);
//            panel.setVisible(false);
        });
        panel.add(jButton);
        instance.createComponentPopupBuilder(new JScrollPane(panel), new JBLabel())//参数说明：内容对象,优先获取
                .setMovable(true)
                .setResizable(true)
                .setNormalWindowLevel(false)
                .setMinSize(new Dimension(40,20))
                .createPopup()
                .showInBestPositionFor(e.getDataContext());



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
        //提示复制成功
        NotificationGroup notificationGroup = new NotificationGroup("copyId", NotificationDisplayType.BALLOON, false);
        Notification notification = notificationGroup.createNotification("复制成功", MessageType.INFO);
        Notifications.Bus.notify(notification);
    }



}
