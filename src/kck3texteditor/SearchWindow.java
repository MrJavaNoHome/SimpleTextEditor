/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kck3texteditor;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SearchWindow extends JFrame {

    private TextEditor textEditor;

    public SearchWindow(TextEditor mainFrameWindow) {
        this.setTitle("Search");
        this.textEditor = mainFrameWindow;
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JTextField text = new JTextField("Phrase:");
        JButton findButton = new JButton("Search");
        JButton abortButton = new JButton("Close");
        JCheckBox wholeWordBox = new JCheckBox("Whole words");
        JCheckBox matchCaseBox = new JCheckBox("Match case");

        JPanel panel = new JPanel();
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                .addComponent(text)
                .addComponent(findButton)
        );
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(text)
                        .addComponent(findButton))
                .addComponent(wholeWordBox)
                .addComponent(matchCaseBox)
                .addComponent(abortButton)
        );

        this.add(panel);
        //pack();
        mainFrameWindow.setEnabled(false);
        setVisible(true);
    }

}
