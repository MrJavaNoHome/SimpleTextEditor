/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kck3texteditor;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author honzik
 */
public class FontWindow extends JFrame {

    private TextEditor mainFrameWindow;
    
    public FontWindow(TextEditor mainFrameWindow) {

        this.mainFrameWindow = mainFrameWindow;
        setTitle("Font settings");
        String fonts[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        JList fontNameList = new JList(fonts);
        JScrollPane fontNameScroller = new JScrollPane(fontNameList);
        
        Integer[] fontSizeArray = new Integer[65];

        for (int i = 0; i < 65; i++) {
            fontSizeArray[i] = i + 8;
        }
        JList fontSizeList = new JList(fontSizeArray);
        JScrollPane fontSizeScroller = new JScrollPane(fontSizeList);

        /*Font style*/
        Integer[] fontStyleIntList = new Integer[4];

        fontStyleIntList[0] = Font.PLAIN;
        fontStyleIntList[1] = Font.BOLD;
        fontStyleIntList[2] = Font.ITALIC;

        String[] fontStyleString = new String[3];
        fontStyleString[0] = "PLAIN";
        fontStyleString[1] = "BOLD";
        fontStyleString[2] = "ITALIC";

        JList fontStyleList = new JList(fontStyleString);
        JScrollPane fontStyleScroller = new JScrollPane(fontStyleList);

        /*Buttony*/
        JButton setFontButton = new JButton("Ok");
        JButton abortButton = new JButton("Close");

        setFontButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String fontName = (String) fontNameList.getSelectedValue();
                int styleIndexArray = fontStyleList.getSelectedIndex();
                int fontStyle = fontStyleIntList[styleIndexArray];
                int fontSize = (Integer) fontSizeList.getSelectedValue();

                //TO-DO Sprawdzic czy sa zaznaczone opcje, Jezeli nie nalezy uzyc obecnie uzywanej opcji dla nowo ustawianej czcionki
                mainFrameWindow.changeAreaTextFont(fontName, fontStyle, fontSize);
                mainFrameWindow.setEnabled(true);
                dispose();

            }
        });

        abortButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainFrameWindow.setEnabled(true);
                dispose();
            }
        });

        /*Zaznaczenie opcji obecnej czcionki, aby uniknac braku wybrania wartosci przez usera*/
        Font actualFont = mainFrameWindow.area.getFont();
        String fontName = actualFont.getName();
        int fontStyle = actualFont.getStyle();
        int fontSize = actualFont.getSize();

        fontNameList.setSelectedValue(fontName, true);
        fontSizeList.setSelectedValue(fontSize, true);
        fontStyleList.setSelectedValue(fontStyleString[fontStyle], true);

        /*Uklad*/
        JLabel fontLabel = new JLabel("Font:");
        JLabel sizeLabel = new JLabel("Font size:");
        JLabel styleLabel = new JLabel("Style:");

        JPanel components = new JPanel();
        components.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        components.add(fontLabel, c);

        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 0;
        components.add(sizeLabel, c);
        c.weightx = 0.5;
        c.gridx = 2;
        c.gridy = 0;
        components.add(styleLabel, c);

        //c.fill = GridBagConstraints.VERTICAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 1;
        components.add(fontNameScroller, c);

        c.gridx = 1;
        c.gridy = 1;
        components.add(fontSizeScroller, c);

        c.gridx = 2;
        c.gridy = 1;
        components.add(fontStyleScroller, c);

        JPanel southPanel = new JPanel();

        southPanel.add(setFontButton);
        southPanel.add(abortButton);

        c.gridx = 0;
        c.gridy = 2;
        components.add(southPanel, c);

//        components.setLayout(new BorderLayout());
//
//        components.add(fontNameScroller, BorderLayout.CENTER);
//        components.add(fontSizeScroller, BorderLayout.PAGE_START);
//
//        components.add(fontStyleScroller, BorderLayout.PAGE_END);
//
//        southPanel.add(setFontButton);
//
//        southPanel.add(abortButton);
//
//        //components.add(southPanel, BorderLayout.PAGE_START);
//        components.add(southPanel, BorderLayout.PAGE_END);
//        
        this.add(components);
        setAlwaysOnTop(true);
        fontNameList.setLayoutOrientation(JList.VERTICAL);
        pack();
        setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(0
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
