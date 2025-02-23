/*
 * Created by JFormDesigner on Fri Feb 14 14:04:27 YEKT 2025
 */

package UI.Windows;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import General.MainProvider;
import General.PowerTools;
import Interfaces.TweaksInterface;
import UI.Windows.AboutPopup.AboutPopup;
import com.formdev.flatlaf.extras.*;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.github.tuupertunut.powershelllibjava.PowerShellExecutionException;


import com.sun.jna.platform.win32.Win32Exception;


/**
 * @author Redactov
 */
public class MainWindow extends JFrame implements TweaksInterface {

    static MainWindow mainw;
    boolean isAboutOpened = false;
    AboutPopup popup = new AboutPopup(mainw);
    PowerTools tools = MainProvider.getTools();
    boolean finishedsetup;




    String[] Col = {"Category", "Program", "Install?"};

    JCheckBox[] TweaksCheckboxes;

    public MainWindow() {
        initComponents();
        initRadioButtons();
        TweaksCheckboxes = new JCheckBox[]{checkBox1, checkBox2, checkBox3, wifisense_checkbox, storagesense_checkbox, deltempfiles_checkbox, disablecopilot_checkbox, disablebackgroundapps_checkbox, removeonedrive_checkbox};
        menuBar1.add(Box.createHorizontalGlue());
        setSize(720,480);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);



        //JCheckBox[] winappschecks = new JCheckBox[]{OutlookCheck,GrooveMusicCheck,MicrosoftStoreCheck,MusicCheck,PhotosCheck,OneDriveCheck,RecallCheck,MicrosoftTeamsCheck,PaintCheck,SnipSketchCheck,XboxCheck,CalculatorCheck};
    }

    public static void initialize(){
        FlatMacDarkLaf.setup();
        UIManager.put("flatlaf.useWindowDecorations", true);
        UIManager.put("flatlaf.menuBarEmbedded", true);
        UIManager.put("TitlePane.centerTitle", true);
        UIManager.put("TitlePane.showIconBesideTitle", true);
        //UIManager.put("TitlePane.buttonSize", new Dimension(20,3));
        UIManager.put("TitlePane.borderColor", new Color(40,40,40,255));
        UIManager.put("TabbedPane.tabType", "card");
        mainw = new MainWindow();
    }

    public void initRadioButtons() {
        finishedsetup = false;
        darktheme_btn.setSelected(isDarkThemeEnabled());

        try {
            bingsearch_btn.setSelected(isBingSearchEnabled());
        } catch (Win32Exception e) {
            bingsearch_btn.setEnabled(false);
        }

        showhiddenfiles_btn.setSelected(isShowHiddenFilesEnabled());
        showfileext_btn.setSelected(isShowFileExtensionsEnabled());

        if (tools.getWindowsVersion() == 10) {
            searchbtn_btn.setEnabled(false);
        } else {
            searchbtn_btn.setSelected(isSearchButtonEnabled());
        }

        try {
            centeritems_btn.setSelected(isCenteredItemsinTaskBar());
        } catch (Win32Exception e) {
            centeritems_btn.setEnabled(false);
        }
        finishedsetup = true;
    }

    public void initProgramTable(){
    }

    private void button1MouseClicked(MouseEvent e) {
        System.out.println("button click");
        popup.setVisible(true);
    }

    private void button3MouseClicked(MouseEvent e) {

        int confirmed = JOptionPane.showConfirmDialog(null, "This will change some keys in your registry. Do you want to continue?", "Registry changes", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
        if (confirmed == JOptionPane.YES_OPTION) {

            if (checkBox1.isSelected()) {
                SetTelemetry(true);
            }
            if (checkBox2.isSelected()) {
                ActivityHistory(true);
            }
            if (checkBox3.isSelected()) {
                LocationTracking(true);
            }
            if (wifisense_checkbox.isSelected()) {
                WifiSense(true);
            }
            if (storagesense_checkbox.isSelected()) {
                StorageSense(true);
            }
            if (deltempfiles_checkbox.isSelected()) {
                try {
                    DeleteTempFiles(true);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            if (disablecopilot_checkbox.isSelected()) {
                try {
                    DisableCopilot(true);
                } catch (PowerShellExecutionException | IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            if (disablebackgroundapps_checkbox.isSelected()) {
                disableBackgroundApps(true);
            }
            if (removeonedrive_checkbox.isSelected()) {
                try {
                    onedrive(true);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }

    }



    private void button2MouseClicked(MouseEvent e) {
        tools.backupRegistry("HKLM", "Registry_LocalMachine_Backup.reg");
        tools.backupRegistry("HKCU", "Registry_CurrentUser_Backup.reg");
    }

    private void button1StateChanged(ChangeEvent e) {
        // TODO add your code here
    }

    private void DarkThemeChanged(ItemEvent e) {
        if (finishedsetup) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                changeDarkThemeState(0);
            } else {
                changeDarkThemeState(1);
            }
        }
    }

    private void BingSearchChanged(ItemEvent e) {
        if (finishedsetup) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                changeBingSearchState(1);
            } else {
                changeBingSearchState(0);
            }
        }
    }

    private void ShowHiddenFilesChanged(ItemEvent e) {
        int i;
        if (finishedsetup) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                i = 0;
            } else {
                i = 1;
            }
            try {
                ShowHiddenFiledChange(i);
            } catch (PowerShellExecutionException | IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private void ShowFileExtChanged(ItemEvent e) {
        int i;
        if (finishedsetup) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                i = 0;
            } else {
                i = 1;
            }
            try {
                ShowFileExtChange(i);
            } catch (PowerShellExecutionException | IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private void SearchBtnChanged(ItemEvent e) {
        if (finishedsetup) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                SearchBtnChange(1);
            } else {
                SearchBtnChange(0);
            }
        }
    }

    private void CenterItemsChanged(ItemEvent e) {
        if (finishedsetup) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                CenteredItemsinTaskBarChange(1);
            } else {
                CenteredItemsinTaskBarChange(0);
            }
        }
    }

    private void darktheme_btnMouseClicked(MouseEvent e) {

    }

    private void ClearBtnClicked(MouseEvent e) {
        for (JCheckBox checkBox : TweaksCheckboxes) {
            checkBox.setSelected(false);
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - Redactov
        menuBar1 = new JMenuBar();
        button1 = new JButton();
        tabbedPane1 = new JTabbedPane();
        panel1 = new JPanel();
        panel2 = new JPanel();
        label1 = new JLabel();
        panel4 = new JPanel();
        textArea1 = new JTextArea();
        panel7 = new JPanel();
        panel5 = new JPanel();
        checkBox1 = new JCheckBox();
        checkBox2 = new JCheckBox();
        checkBox3 = new JCheckBox();
        wifisense_checkbox = new JCheckBox();
        storagesense_checkbox = new JCheckBox();
        deltempfiles_checkbox = new JCheckBox();
        separator1 = new JSeparator();
        disablecopilot_checkbox = new JCheckBox();
        disablebackgroundapps_checkbox = new JCheckBox();
        removeonedrive_checkbox = new JCheckBox();
        panel6 = new JPanel();
        darktheme_btn = new JRadioButton();
        recomends_btn = new JRadioButton();
        bingsearch_btn = new JRadioButton();
        showhiddenfiles_btn = new JRadioButton();
        showfileext_btn = new JRadioButton();
        searchbtn_btn = new JRadioButton();
        centeritems_btn = new JRadioButton();
        panel8 = new JPanel();
        button3 = new JButton();
        button4 = new JButton();
        button2 = new JButton();

        //======== this ========
        setTitle("Redactov Tweaker");
        setIconImage(new ImageIcon(getClass().getResource("/resources/redactovtweakericon.png")).getImage());
        setFont(new Font("Inter", Font.PLAIN, 12));
        var contentPane = getContentPane();
        contentPane.setLayout(new GridLayout());

        //======== menuBar1 ========
        {
            menuBar1.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

            //---- button1 ----
            button1.setIcon(new FlatSVGIcon("resources/userdark.svg"));
            button1.setMinimumSize(new Dimension(30, 30));
            button1.setMaximumSize(new Dimension(30, 30));
            button1.setBorderPainted(false);
            button1.setBackground(new Color(0x1e1e1e));
            button1.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    button1MouseClicked(e);
                    button1MouseClicked(e);
                }
            });
            button1.addChangeListener(e -> button1StateChanged(e));
            menuBar1.add(button1);
        }
        setJMenuBar(menuBar1);

        //======== tabbedPane1 ========
        {
            tabbedPane1.setFont(new Font("Inter", Font.PLAIN, 12));

            //======== panel1 ========
            {
                panel1.setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new javax. swing. border
                . EmptyBorder( 0, 0, 0, 0) , "JFor\u006dDesi\u0067ner \u0045valu\u0061tion", javax. swing. border. TitledBorder. CENTER, javax
                . swing. border. TitledBorder. BOTTOM, new java .awt .Font ("Dia\u006cog" ,java .awt .Font .BOLD ,
                12 ), java. awt. Color. red) ,panel1. getBorder( )) ); panel1. addPropertyChangeListener (new java. beans
                . PropertyChangeListener( ){ @Override public void propertyChange (java .beans .PropertyChangeEvent e) {if ("bord\u0065r" .equals (e .
                getPropertyName () )) throw new RuntimeException( ); }} );
                panel1.setLayout(new GridBagLayout());
                ((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {0, 0};
                ((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {8, 0, 0};
                ((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
                ((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {0.0, 1.0, 1.0E-4};

                //======== panel2 ========
                {
                    panel2.setBorder(new TitledBorder(UIManager.getBorder("ComboBox.border"), "Applications to install"));
                    panel2.setPreferredSize(new Dimension(14, 26));
                    panel2.setLayout(new GridLayout());

                    //---- label1 ----
                    label1.setText("TODO");
                    panel2.add(label1);
                }
                panel1.add(panel2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            tabbedPane1.addTab("Applications", panel1);

            //======== panel4 ========
            {
                panel4.setLayout(new GridBagLayout());
                ((GridBagLayout)panel4.getLayout()).columnWidths = new int[] {0, 0};
                ((GridBagLayout)panel4.getLayout()).rowHeights = new int[] {49, 0, 0, 0, 0};
                ((GridBagLayout)panel4.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
                ((GridBagLayout)panel4.getLayout()).rowWeights = new double[] {0.0, 1.0, 0.0, 0.0, 1.0E-4};

                //---- textArea1 ----
                textArea1.setText("\u041f\u0435\u0440\u0435\u0434 \u043f\u0440\u0438\u043c\u0435\u043d\u0435\u043d\u0438\u0435\u043c \u043a\u0430\u043a\u0438\u0445 \u043b\u0438\u0431\u043e \u043d\u0430\u0441\u0442\u0440\u043e\u0435\u043a \u0432\u043e \u0432\u043a\u043b\u0430\u0434\u043a\u0435 Tweaks \u0440\u0435\u043a\u043e\u043c\u0435\u043d\u0434\u0443\u0435\u0442\u0441\u044f \u0441\u0434\u0435\u043b\u0430\u0442\u044c\n\u0431\u044d\u043a\u0430\u043f \u0440\u0435\u0435\u0441\u0442\u0440\u0430 (\u043a\u043d\u043e\u043f\u043a\u0430 Backup Registry \u0441\u043f\u0440\u0430\u0432\u0430 \u0441\u043d\u0438\u0437\u0443).");
                textArea1.setEditable(false);
                textArea1.setFont(new Font("JetBrains Mono ExtraBold", Font.PLAIN, 14));
                textArea1.setForeground(new Color(0xffcc00));
                panel4.add(textArea1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));

                //======== panel7 ========
                {
                    panel7.setLayout(new GridLayout());

                    //======== panel5 ========
                    {
                        panel5.setBorder(new TitledBorder(UIManager.getBorder("ComboBox.border"), "General tweaks"));
                        panel5.setLayout(new GridBagLayout());
                        ((GridBagLayout)panel5.getLayout()).columnWidths = new int[] {0, 0, 0};
                        ((GridBagLayout)panel5.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                        ((GridBagLayout)panel5.getLayout()).columnWeights = new double[] {1.0, 0.0, 1.0E-4};
                        ((GridBagLayout)panel5.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

                        //---- checkBox1 ----
                        checkBox1.setText("Telemetry");
                        panel5.add(checkBox1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                            GridBagConstraints.WEST, GridBagConstraints.VERTICAL,
                            new Insets(0, 0, 5, 5), 0, 0));

                        //---- checkBox2 ----
                        checkBox2.setText("Activity History");
                        panel5.add(checkBox2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                            GridBagConstraints.WEST, GridBagConstraints.VERTICAL,
                            new Insets(0, 0, 5, 5), 0, 0));

                        //---- checkBox3 ----
                        checkBox3.setText("Location Tracking");
                        panel5.add(checkBox3, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                            GridBagConstraints.WEST, GridBagConstraints.VERTICAL,
                            new Insets(0, 0, 5, 5), 0, 0));

                        //---- wifisense_checkbox ----
                        wifisense_checkbox.setText("Wifi-Sense");
                        panel5.add(wifisense_checkbox, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
                            GridBagConstraints.WEST, GridBagConstraints.VERTICAL,
                            new Insets(0, 0, 5, 5), 0, 0));

                        //---- storagesense_checkbox ----
                        storagesense_checkbox.setText("Storage Sense");
                        panel5.add(storagesense_checkbox, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
                            GridBagConstraints.WEST, GridBagConstraints.VERTICAL,
                            new Insets(0, 0, 5, 5), 0, 0));

                        //---- deltempfiles_checkbox ----
                        deltempfiles_checkbox.setText("Delete Temporary Files");
                        panel5.add(deltempfiles_checkbox, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0,
                            GridBagConstraints.WEST, GridBagConstraints.VERTICAL,
                            new Insets(0, 0, 5, 5), 0, 0));

                        //---- separator1 ----
                        separator1.setBorder(new TitledBorder("text"));
                        panel5.add(separator1, new GridBagConstraints(0, 6, 2, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                            new Insets(0, 0, 5, 0), 0, 0));

                        //---- disablecopilot_checkbox ----
                        disablecopilot_checkbox.setText("Disable Copilot");
                        panel5.add(disablecopilot_checkbox, new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0,
                            GridBagConstraints.WEST, GridBagConstraints.VERTICAL,
                            new Insets(0, 0, 5, 5), 0, 0));

                        //---- disablebackgroundapps_checkbox ----
                        disablebackgroundapps_checkbox.setText("Disable Background Apps");
                        panel5.add(disablebackgroundapps_checkbox, new GridBagConstraints(0, 8, 1, 1, 0.0, 0.0,
                            GridBagConstraints.WEST, GridBagConstraints.VERTICAL,
                            new Insets(0, 0, 5, 5), 0, 0));

                        //---- removeonedrive_checkbox ----
                        removeonedrive_checkbox.setText("Remove OneDrive");
                        panel5.add(removeonedrive_checkbox, new GridBagConstraints(0, 9, 1, 1, 0.0, 0.0,
                            GridBagConstraints.WEST, GridBagConstraints.VERTICAL,
                            new Insets(0, 0, 5, 5), 0, 0));
                    }
                    panel7.add(panel5);

                    //======== panel6 ========
                    {
                        panel6.setBorder(new TitledBorder(UIManager.getBorder("ComboBox.border"), "Other features"));
                        panel6.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                        panel6.setLayout(new GridBagLayout());
                        ((GridBagLayout)panel6.getLayout()).columnWidths = new int[] {0, 0};
                        ((GridBagLayout)panel6.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                        ((GridBagLayout)panel6.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
                        ((GridBagLayout)panel6.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

                        //---- darktheme_btn ----
                        darktheme_btn.setText("Dark Theme");
                        darktheme_btn.addItemListener(e -> DarkThemeChanged(e));
                        darktheme_btn.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                darktheme_btnMouseClicked(e);
                            }
                        });
                        panel6.add(darktheme_btn, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                            GridBagConstraints.WEST, GridBagConstraints.VERTICAL,
                            new Insets(0, 0, 5, 0), 0, 0));

                        //---- recomends_btn ----
                        recomends_btn.setText("Recommendations on block screen");
                        recomends_btn.setEnabled(false);
                        panel6.add(recomends_btn, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                            GridBagConstraints.WEST, GridBagConstraints.VERTICAL,
                            new Insets(0, 0, 5, 0), 0, 0));

                        //---- bingsearch_btn ----
                        bingsearch_btn.setText("Bing Search in Start Menu");
                        bingsearch_btn.addItemListener(e -> BingSearchChanged(e));
                        panel6.add(bingsearch_btn, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                            GridBagConstraints.WEST, GridBagConstraints.VERTICAL,
                            new Insets(0, 0, 5, 0), 0, 0));

                        //---- showhiddenfiles_btn ----
                        showhiddenfiles_btn.setText("Show hidden files");
                        showhiddenfiles_btn.addItemListener(e -> ShowHiddenFilesChanged(e));
                        panel6.add(showhiddenfiles_btn, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
                            GridBagConstraints.WEST, GridBagConstraints.VERTICAL,
                            new Insets(0, 0, 5, 0), 0, 0));

                        //---- showfileext_btn ----
                        showfileext_btn.setText("Show file extensions");
                        showfileext_btn.addItemListener(e -> ShowFileExtChanged(e));
                        panel6.add(showfileext_btn, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
                            GridBagConstraints.WEST, GridBagConstraints.VERTICAL,
                            new Insets(0, 0, 5, 0), 0, 0));

                        //---- searchbtn_btn ----
                        searchbtn_btn.setText("Search Button in TaskBar");
                        searchbtn_btn.addItemListener(e -> SearchBtnChanged(e));
                        panel6.add(searchbtn_btn, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0,
                            GridBagConstraints.WEST, GridBagConstraints.VERTICAL,
                            new Insets(0, 0, 5, 0), 0, 0));

                        //---- centeritems_btn ----
                        centeritems_btn.setText("Center items in TaskBar");
                        centeritems_btn.addItemListener(e -> CenterItemsChanged(e));
                        panel6.add(centeritems_btn, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0,
                            GridBagConstraints.WEST, GridBagConstraints.VERTICAL,
                            new Insets(0, 0, 5, 0), 0, 0));
                    }
                    panel7.add(panel6);
                }
                panel4.add(panel7, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));

                //======== panel8 ========
                {

                    //---- button3 ----
                    button3.setText("Execute");
                    button3.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            button3MouseClicked(e);
                        }
                    });

                    //---- button4 ----
                    button4.setText("Clear");
                    button4.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            ClearBtnClicked(e);
                        }
                    });

                    //---- button2 ----
                    button2.setText("Backup Registry");
                    button2.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            button2MouseClicked(e);
                        }
                    });

                    GroupLayout panel8Layout = new GroupLayout(panel8);
                    panel8.setLayout(panel8Layout);
                    panel8Layout.setHorizontalGroup(
                        panel8Layout.createParallelGroup()
                            .addGroup(panel8Layout.createSequentialGroup()
                                .addComponent(button3)
                                .addGap(0, 0, 0)
                                .addComponent(button4)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 422, Short.MAX_VALUE)
                                .addComponent(button2))
                    );
                    panel8Layout.setVerticalGroup(
                        panel8Layout.createParallelGroup()
                            .addComponent(button3)
                            .addGroup(panel8Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(button4)
                                .addComponent(button2))
                    );
                }
                panel4.add(panel8, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            tabbedPane1.addTab("Tweaks", panel4);

            tabbedPane1.setSelectedIndex(1);
        }
        contentPane.add(tabbedPane1);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - Redactov
    private JMenuBar menuBar1;
    private JButton button1;
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JPanel panel2;
    private JLabel label1;
    private JPanel panel4;
    private JTextArea textArea1;
    private JPanel panel7;
    private JPanel panel5;
    private JCheckBox checkBox1;
    private JCheckBox checkBox2;
    private JCheckBox checkBox3;
    private JCheckBox wifisense_checkbox;
    private JCheckBox storagesense_checkbox;
    private JCheckBox deltempfiles_checkbox;
    private JSeparator separator1;
    private JCheckBox disablecopilot_checkbox;
    private JCheckBox disablebackgroundapps_checkbox;
    private JCheckBox removeonedrive_checkbox;
    private JPanel panel6;
    private JRadioButton darktheme_btn;
    private JRadioButton recomends_btn;
    private JRadioButton bingsearch_btn;
    private JRadioButton showhiddenfiles_btn;
    private JRadioButton showfileext_btn;
    private JRadioButton searchbtn_btn;
    private JRadioButton centeritems_btn;
    private JPanel panel8;
    private JButton button3;
    private JButton button4;
    private JButton button2;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
