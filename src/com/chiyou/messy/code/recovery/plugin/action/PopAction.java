package com.chiyou.messy.code.recovery.plugin.action;

import com.chiyou.messy.code.recovery.plugin.uitls.ConvertUtils;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.ui.awt.RelativePoint;

import javax.swing.*;
import java.awt.*;

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

        Color bg = UIManager.getColor("Panel.background");

        JTextField label = new JTextField(result);
        label.setHorizontalAlignment(JTextField.CENTER);
        label.setBackground(bg);
        label.setFont(new Font(null,Font.PLAIN,18));
        label.setBorder(null);

        instance.createBalloonBuilder(label)
                .setFillColor(bg)
                .createBalloon()
                .show(RelativePoint.getCenterOf(WindowManager.getInstance().getAllProjectFrames()[0].getComponent()), Balloon.Position.above);

    }
}
