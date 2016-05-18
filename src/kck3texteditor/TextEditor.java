package kck3texteditor;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.text.*;

public class TextEditor extends JFrame {

    private TextEditor thisFrame;
    private JFrame displayedWindow;
    private String imageLocation = "images/";
    public JTextArea area = new JTextArea(20, 120);
    private FontWindow fontWindow;

    private JFileChooser dialog = new JFileChooser(System.getProperty("user.dir"));
    private String currentFile = "Untitled";
    private boolean changed = false;
    ActionMap m;

    //Listener klawisza dla uaktywnienia trybu zapisu
    private KeyListener k1 = new KeyAdapter() {
        public void keyPressed(KeyEvent e) {
            changed = true;
            Save.setEnabled(true);
            SaveAs.setEnabled(true);
        }
    };

    private boolean IsWindowOpened(Object window) {
        if (window == null) {
            return false;
        } else {
            return true;
        }
    }

    Action OpenFontWindow = new AbstractAction("Font") {
        public void actionPerformed(ActionEvent e) {
            fontWindow = new FontWindow(thisFrame);
            fontWindow.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        }
    };

    Action OpenSearchWindow = new AbstractAction("Search") {
        public void actionPerformed(ActionEvent e) {
            SearchWindow2 searchWindow = new SearchWindow2(thisFrame);
            searchWindow.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        }
    };
    
    Action OpenChangeWindow = new AbstractAction("Replace") {
        public void actionPerformed(ActionEvent e) {
            ChangeWindow changeWindow = new ChangeWindow(thisFrame);
            changeWindow.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        }
    };

    Action ChangeWordWrap = new AbstractAction("Word wrap") {
        public void actionPerformed(ActionEvent e) {
            boolean isLineWrapActive = area.getLineWrap();
            area.setWrapStyleWord(!isLineWrapActive);
            area.setLineWrap(!isLineWrapActive);
        }
    };

    Action Open = new AbstractAction("Open", new ImageIcon(imageLocation + "open.png")) {
        public void actionPerformed(ActionEvent e) {
            saveOld();
            if (dialog.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                readInFile(dialog.getSelectedFile().getAbsolutePath());
            }
            SaveAs.setEnabled(true);
        }
    };

    Action Save = new AbstractAction("Save", new ImageIcon(imageLocation + "save.png")) {
        public void actionPerformed(ActionEvent e) {
            if (!currentFile.equals("Untitled")) {
                saveFile(currentFile);
            } else {
                saveFileAs();
            }
        }
    };

    Action New = new AbstractAction("New", new ImageIcon(imageLocation + "new.png")) {
        public void actionPerformed(ActionEvent e) {
            saveOld();
            area.setText("");
            currentFile = "Untitled";
            setTitle(currentFile);
            changed = false;
            Save.setEnabled(false);
            SaveAs.setEnabled(false);
        }
    };

    Action SaveAs = new AbstractAction("Save as...") {
        public void actionPerformed(ActionEvent e) {
            saveFileAs();
        }
    };

    Action Quit = new AbstractAction("Exit") {
        public void actionPerformed(ActionEvent e) {
            saveOld();
            System.exit(0);
        }
    };

    Action About = new AbstractAction("About", new ImageIcon(imageLocation + "about.png")) {
        public void actionPerformed(ActionEvent e) {
            JFrame aboutFrame = new JFrame("About");
            JLabel authorsLabel = new JLabel();
            JLabel facultyLabel = new JLabel();
            JLabel dateLabel = new JLabel();
            authorsLabel.setText("Honzik");
            facultyLabel.setText("https://github.com/kondzio14");

            //JLabel aboutImgLabel = new JLabel();
            ImageIcon aboutImg = new ImageIcon(imageLocation + "about-big.png", "aboutImgBig");

            JLabel aboutImgLabel = new JLabel(aboutImg);

            JPanel textDataPanel = new JPanel();
            textDataPanel.add(authorsLabel);
            textDataPanel.add(facultyLabel);
            textDataPanel.add(dateLabel);
            textDataPanel.setLayout(new BoxLayout(textDataPanel, BoxLayout.Y_AXIS));

            JButton closeAboutWindowButton = new JButton("Close window");
            closeAboutWindowButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    thisFrame.setEnabled(true);
                    aboutFrame.dispose();
                }
            });

            JPanel buttonPanel = new JPanel();
            buttonPanel.add(closeAboutWindowButton);

            JPanel leftPanel = new JPanel();
            leftPanel.add(textDataPanel);
            leftPanel.add(buttonPanel);
            leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

            JPanel imgPanel = new JPanel();
            imgPanel.add(aboutImgLabel, BorderLayout.LINE_END);

            //JPanel mainPanel = new JPanel(new GridLayout());
            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.add(leftPanel, BorderLayout.LINE_START);
            mainPanel.add(imgPanel, BorderLayout.LINE_END);

            /*
             JEditorPane editorPane = new JEditorPane("text/html", htmlCode);
             leftPanel.add(editorPane);
             */
            //JScrollPane htmlIconInfo = new JScrollPane();
            //TO-DO
            //htmlIconInfo.set
            //leftPanel.add(jta);
            aboutFrame.add(mainPanel);
            aboutFrame.pack();
            aboutFrame.setResizable(false);

            int mainPositionX = getX();
            int mainPositionY = getY();
            int positionOffset = 30;
            aboutFrame.setLocation(mainPositionX + positionOffset, mainPositionY + positionOffset);

            //public JDialog(Frame owner,String title,boolean modal)
            //JDialog jDialog = new JDialog(this, "", true);
            thisFrame.setEnabled(false);
            aboutFrame.setVisible(
                    true);

        }
    };

    public TextEditor(String windowName) {
        this.setMinimumSize(new Dimension(500,200));
        thisFrame = this;
        area.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(area, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        add(scroll, BorderLayout.CENTER);

        /* Menu */
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");
        JMenu helpMenu = new JMenu("Help");
        JMenu formatMenu = new JMenu("Format");

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(helpMenu);
        menuBar.add(formatMenu);

        fileMenu.add(New);
        fileMenu.add(Open);
        fileMenu.add(Save);
        fileMenu.add(SaveAs);
        fileMenu.add(Quit);
        helpMenu.add(About);
        fileMenu.addSeparator();
        helpMenu.addSeparator();

        JCheckBoxMenuItem wordWrapCheckItem = new JCheckBoxMenuItem(ChangeWordWrap);
        formatMenu.add(wordWrapCheckItem);

        formatMenu.add(OpenFontWindow);

        m = area.getActionMap();
        Action Cut = m.get(DefaultEditorKit.cutAction);
        Action Copy = m.get(DefaultEditorKit.copyAction);
        Action Paste = m.get(DefaultEditorKit.pasteAction);

        //Ustawienie braku ikonek dla opcji menu
        for (int i = 0; i < fileMenu.getItemCount() - 1; i++) {
            fileMenu.getItem(i).setIcon(null);
        }
        for (int i = 0; i < helpMenu.getItemCount() - 1; i++) {
            helpMenu.getItem(i).setIcon(null);
        }

        editMenu.add(Cut);
        editMenu.add(Copy);
        editMenu.add(Paste);
        editMenu.add(OpenSearchWindow);
        editMenu.add(OpenChangeWindow);

        editMenu.getItem(0).setText("Cut");
        editMenu.getItem(1).setText("Copy");
        editMenu.getItem(2).setText("Paste");

        JToolBar tool = new JToolBar("Toolbar");
        add(tool, BorderLayout.NORTH);

        JButton newFileButton = new JButton(New);
        JButton saveFileButton = new JButton(Save);
        JButton cutButton = new JButton(Cut);
        JButton copyButton = new JButton(Copy);
        JButton pasteButton = new JButton(Paste);
        JButton aboutButton = new JButton(About);

        tool.add(newFileButton);
        tool.add(saveFileButton);
        tool.add(cutButton);
        tool.add(copyButton);
        tool.add(pasteButton);
        tool.add(aboutButton);

        cutButton.setText("Cut");
        cutButton.setIcon(new ImageIcon(imageLocation + "cut.png"));
        copyButton.setText("Copy");
        copyButton.setIcon(new ImageIcon(imageLocation + "copy.png"));
        pasteButton.setText("Paste");
        pasteButton.setIcon(new ImageIcon(imageLocation + "paste.png"));

        Save.setEnabled(false);
        SaveAs.setEnabled(false);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        area.addKeyListener(k1);
        setTitle(currentFile);
        setVisible(true);
    }

    private void saveFileAs() {
        if (dialog.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            saveFile(dialog.getSelectedFile().getAbsolutePath());
        }
    }

    private void saveOld() {
        if (changed) {
            if (JOptionPane.showConfirmDialog(this, "Would you like to save " + currentFile + " ?", "Save", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                saveFile(currentFile);
            }
        }
    }

    private void readInFile(String fileName) {
        try {
            FileReader r = new FileReader(fileName);
            area.read(r, null);
            r.close();
            currentFile = fileName;
            setTitle(currentFile);
            changed = false;
        } catch (IOException e) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(this, "File not found: " + fileName);
        }
    }

    private void saveFile(String fileName) {
        try {
            FileWriter w = new FileWriter(fileName);
            area.write(w);
            w.close();
            currentFile = fileName;
            setTitle(currentFile);
            changed = false;
            Save.setEnabled(false);
        } catch (IOException e) {
        }
    }

    public void changeAreaTextFont(String fontName, int fontStyle, int fontSize) {
        area.setFont(new Font(fontName, fontStyle, fontSize));
    }

}
