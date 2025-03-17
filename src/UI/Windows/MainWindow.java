/*
 * Created by JFormDesigner on Fri Feb 14 14:04:27 YEKT 2025
 */

package UI.Windows;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.table.*;

import General.App;
import General.MainProvider;
import General.PowerTools;
import General.WindowsActivation.WinActivation;
import General.WindowsActivation.WinActivationType;
import General.WingetInstaller;
import Interfaces.TweaksInterface;
import UI.Windows.AboutPopup.AboutPopup;
import UI.Windows.LoadingScreen.LoadAppsScreen;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.*;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.ui.*;
import com.github.tuupertunut.powershelllibjava.PowerShellExecutionException;
import com.sun.jna.platform.win32.Win32Exception;




/**
 * @author Redactov
 */
public class MainWindow extends JFrame implements TweaksInterface {

    static MainWindow mainw;
    boolean isAboutOpened = false;
    AboutPopup popup;
    PowerTools tools = MainProvider.getTools();
    boolean finishedsetup;

    Map<String, App> apps = PowerTools.initApps();
    List<App> selectedApps = new ArrayList<App>();


    String[] Col = {"Category", "Program", "Install?"};

    JCheckBox[] TweaksCheckboxes;

    public MainWindow() {
        initComponents();
        initRadioButtons();
        initAppsTable();
        TweaksCheckboxes = new JCheckBox[]{checkBox1, checkBox2, checkBox3, wifisense_checkbox, storagesense_checkbox, deltempfiles_checkbox, disablecopilot_checkbox, disablebackgroundapps_checkbox, removeonedrive_checkbox};
        menuBar1.add(Box.createHorizontalGlue());
        setSize(720,480);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);



        //JCheckBox[] winappschecks = new JCheckBox[]{OutlookCheck,GrooveMusicCheck,MicrosoftStoreCheck,MusicCheck,PhotosCheck,OneDriveCheck,RecallCheck,MicrosoftTeamsCheck,PaintCheck,SnipSketchCheck,XboxCheck,CalculatorCheck};
    }

    public static void initialize() {
        FlatLaf.setGlobalExtraDefaults( Collections.singletonMap( "@accentColor", "#F5424BFF" ) );
        FlatMacDarkLaf.setup();
        //UIManager.put("Component.accentColor", new Color(245, 66,75));
        UIManager.put("flatlaf.useWindowDecorations", true);
        UIManager.put("flatlaf.menuBarEmbedded", true);
        UIManager.put("TitlePane.centerTitle", true);
        UIManager.put("TitlePane.showIconBesideTitle", true);
        //UIManager.put("TitlePane.buttonSize", new Dimension(20,3));
        UIManager.put("TitlePane.borderColor", new Color(40,40,40,255));
        UIManager.put("TabbedPane.tabType", "card");
        UIManager.put("Table.alternateRowColor", new Color(48, 48, 48));
        mainw = new MainWindow();
    }



    public void initAppsTable() {
        AppsTable.setRowSelectionAllowed(false);
        DefaultTableModel model = (DefaultTableModel) AppsTable.getModel();

        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
        AppsTable.setRowSorter(sorter);

        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sortKeys.add(new RowSorter.SortKey(1, SortOrder.ASCENDING));
        sorter.setSortKeys(sortKeys);

        for (var e : apps.entrySet()) {
            System.out.println(e.getKey() + e.getValue().getCategory() + e.getValue().getLink());
            model.addRow(new Object[]{e.getKey(), e.getValue().getCategory(), e.getValue().getLink(), false});
        }

        model.addTableModelListener(
                new TableModelListener() {
                    @Override
                    public void tableChanged(TableModelEvent e) {
                        String appName = model.getValueAt(e.getFirstRow(), 0).toString();
                        App app = apps.get(appName);
                        boolean installProperty = (boolean) model.getValueAt(e.getFirstRow(), 3);
                        //System.out.println(installProperty);
                        if (installProperty) {
                            //System.out.println("add");
                            selectedApps.add(app);
                        }else {
                            //System.out.println("remove");
                            selectedApps.remove(app);
                        }
                        button5.setEnabled(!selectedApps.isEmpty());
                    }
        });

        textField1.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = textField1.getText();
                if (text.isEmpty()) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = textField1.getText();
                if (text.isEmpty()) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });

        button5.setEnabled(!selectedApps.isEmpty());

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

        //popup.setVisible(true);

        if (popup == null) {
            popup = new AboutPopup();
        } else if (!popup.isShowing()) {
            popup = new AboutPopup();
        }
        //System.out.println(popup.isShowing());
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

    private void InstallButtonClicked(MouseEvent e) {
        if (button5.isEnabled()) {
            StringBuilder message = new StringBuilder();
            message.append("This applications are going to be installed on your PC:\n\n");
            for (var x : selectedApps) {
                message.append("    • ").append(x.getName()).append("\n");
            }
            int optionChoose = JOptionPane.showConfirmDialog(null, message, "Do you want to continue?", JOptionPane.YES_NO_OPTION);

            if (optionChoose == JOptionPane.YES_OPTION) {
                System.out.println("Yes");
                WingetInstaller thread = new WingetInstaller(selectedApps);
                thread.start();
            } else {
                System.out.println("No");
            }
        }
    }

    private void KMS38ActivationBtnMouseClicked(MouseEvent e) {
        WinActivation thread = new WinActivation(WinActivationType.KMS38);
        thread.start();
    }

    private void HWIDActivationBtnMouseClicked(MouseEvent e) {
        System.out.println("HWIDBUTTON");
        WinActivation thread = new WinActivation(WinActivationType.HWID);
        thread.start();
    }

    private void OhookActivationBtnMouseClicked(MouseEvent e) {
        WinActivation thread = new WinActivation(WinActivationType.Ohook);
        thread.start();
    }

    private void aboutMassgraveTeamPressed(MouseEvent e) {
        System.out.println("click about massgrave");
        try {
            Desktop.getDesktop().browse(URI.create("https://github.com/massgravel"));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void AppsTableMouseClicked(MouseEvent e) {
        int selectedRow = AppsTable.getSelectedRow();
        int selectedColumn = AppsTable.getSelectedColumn();
        //System.out.println(iz + "\n" + index);
        //System.out.println(e.getButton());
        if (selectedColumn == 2 & e.getButton() == MouseEvent.BUTTON1 & e.getClickCount() == 2) {
            try {
                Desktop.getDesktop().browse(new URI((String) AppsTable.getValueAt(selectedRow, selectedColumn)));
            } catch (IOException | URISyntaxException ex) {
                throw new RuntimeException(ex);
            }
        }
    }







    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - redact
        menuBar1 = new JMenuBar();
        button1 = new JButton();
        tabbedPane1 = new JTabbedPane();
        panel1 = new JPanel();
        textField1 = new JTextField();
        panel2 = new JPanel();
        scrollPane1 = new JScrollPane();
        AppsTable = new JTable();
        button5 = new JButton();
        panel4 = new JPanel();
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
        panel3 = new JPanel();
        panel9 = new JPanel();
        HWIDActivationPanel = new JPanel();
        HWIDActivationBtn = new JButton();
        label2 = new JLabel();
        OhookActivationPanel = new JPanel();
        OhookActivationBtn = new JButton();
        label3 = new JLabel();
        KMS38ActivationPanel = new JPanel();
        KMS38ActivationBtn = new JButton();
        label4 = new JLabel();
        panel11 = new JPanel();
        label1 = new JLabel();
        label5 = new JLabel();

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
                panel1.setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new javax
                . swing. border. EmptyBorder( 0, 0, 0, 0) , "", javax. swing
                . border. TitledBorder. CENTER, javax. swing. border. TitledBorder. BOTTOM, new java .awt .
                Font ("" ,java .awt .Font .BOLD ,12 ), java. awt. Color. red
                ) ,panel1. getBorder( )) ); panel1. addPropertyChangeListener (new java. beans. PropertyChangeListener( ){ @Override
                public void propertyChange (java .beans .PropertyChangeEvent e) {if ("" .equals (e .getPropertyName (
                ) )) throw new RuntimeException( ); }} );
                panel1.setLayout(new GridBagLayout());
                ((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {0, 0};
                ((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {0, 333, 8, 0};
                ((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
                ((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};

                //---- textField1 ----
                textField1.setToolTipText("Search");
                textField1.setBorder(new FlatTextBorder());
                panel1.add(textField1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));

                //======== panel2 ========
                {
                    panel2.setBorder(new TitledBorder(UIManager.getBorder("ComboBox.border"), "Applications to install"));
                    panel2.setPreferredSize(new Dimension(14, 26));
                    panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS));

                    //======== scrollPane1 ========
                    {

                        //---- AppsTable ----
                        AppsTable.setModel(new DefaultTableModel(
                            new Object[][] {
                            },
                            new String[] {
                                "Name", "Category", "Link", "Install?"
                            }
                        ) {
                            Class<?>[] columnTypes = new Class<?>[] {
                                String.class, String.class, Object.class, Boolean.class
                            };
                            boolean[] columnEditable = new boolean[] {
                                false, false, false, true
                            };
                            @Override
                            public Class<?> getColumnClass(int columnIndex) {
                                return columnTypes[columnIndex];
                            }
                            @Override
                            public boolean isCellEditable(int rowIndex, int columnIndex) {
                                return columnEditable[columnIndex];
                            }
                        });
                        AppsTable.setRowSelectionAllowed(false);
                        AppsTable.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                AppsTableMouseClicked(e);
                            }
                        });
                        scrollPane1.setViewportView(AppsTable);
                    }
                    panel2.add(scrollPane1);
                }
                panel1.add(panel2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));

                //---- button5 ----
                button5.setText("Install");
                button5.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        InstallButtonClicked(e);
                    }
                });
                panel1.add(button5, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            tabbedPane1.addTab("Applications", panel1);

            //======== panel4 ========
            {
                panel4.setLayout(new GridBagLayout());
                ((GridBagLayout)panel4.getLayout()).columnWidths = new int[] {0, 0};
                ((GridBagLayout)panel4.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
                ((GridBagLayout)panel4.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
                ((GridBagLayout)panel4.getLayout()).rowWeights = new double[] {1.0, 0.0, 0.0, 1.0E-4};

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
                panel4.add(panel7, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
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
                panel4.add(panel8, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            tabbedPane1.addTab("Tweaks", panel4);

            //======== panel3 ========
            {
                panel3.setLayout(new GridBagLayout());
                ((GridBagLayout)panel3.getLayout()).columnWidths = new int[] {0, 0};
                ((GridBagLayout)panel3.getLayout()).rowHeights = new int[] {0, 0, 0};
                ((GridBagLayout)panel3.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
                ((GridBagLayout)panel3.getLayout()).rowWeights = new double[] {1.0, 0.0, 1.0E-4};

                //======== panel9 ========
                {
                    panel9.setLayout(new GridLayout(4, 0, 0, 5));

                    //======== HWIDActivationPanel ========
                    {
                        HWIDActivationPanel.setBorder(UIManager.getBorder("ComboBox.border"));
                        HWIDActivationPanel.setLayout(new GridBagLayout());
                        ((GridBagLayout)HWIDActivationPanel.getLayout()).columnWidths = new int[] {0, 0, 0};
                        ((GridBagLayout)HWIDActivationPanel.getLayout()).rowHeights = new int[] {0, 0};
                        ((GridBagLayout)HWIDActivationPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
                        ((GridBagLayout)HWIDActivationPanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                        //---- HWIDActivationBtn ----
                        HWIDActivationBtn.setText("HWID");
                        HWIDActivationBtn.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                HWIDActivationBtnMouseClicked(e);
                            }
                        });
                        HWIDActivationPanel.add(HWIDActivationBtn, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 0, 5), 0, 0));

                        //---- label2 ----
                        label2.setText("Permanent / Requires Internet Connection ");
                        HWIDActivationPanel.add(label2, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 0, 0), 0, 0));
                    }
                    panel9.add(HWIDActivationPanel);

                    //======== OhookActivationPanel ========
                    {
                        OhookActivationPanel.setBorder(UIManager.getBorder("ComboBox.border"));
                        OhookActivationPanel.setLayout(new GridBagLayout());
                        ((GridBagLayout)OhookActivationPanel.getLayout()).columnWidths = new int[] {0, 0, 0};
                        ((GridBagLayout)OhookActivationPanel.getLayout()).rowHeights = new int[] {0, 0};
                        ((GridBagLayout)OhookActivationPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
                        ((GridBagLayout)OhookActivationPanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                        //---- OhookActivationBtn ----
                        OhookActivationBtn.setText("Ohook");
                        OhookActivationBtn.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                OhookActivationBtnMouseClicked(e);
                            }
                        });
                        OhookActivationPanel.add(OhookActivationBtn, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 0, 5), 0, 0));

                        //---- label3 ----
                        label3.setText("Permanent / Office activation");
                        OhookActivationPanel.add(label3, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 0, 0), 0, 0));
                    }
                    panel9.add(OhookActivationPanel);

                    //======== KMS38ActivationPanel ========
                    {
                        KMS38ActivationPanel.setBorder(UIManager.getBorder("ComboBox.border"));
                        KMS38ActivationPanel.setLayout(new GridBagLayout());
                        ((GridBagLayout)KMS38ActivationPanel.getLayout()).columnWidths = new int[] {0, 0, 0};
                        ((GridBagLayout)KMS38ActivationPanel.getLayout()).rowHeights = new int[] {0, 0};
                        ((GridBagLayout)KMS38ActivationPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
                        ((GridBagLayout)KMS38ActivationPanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                        //---- KMS38ActivationBtn ----
                        KMS38ActivationBtn.setText("KMS38");
                        KMS38ActivationBtn.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                KMS38ActivationBtnMouseClicked(e);
                            }
                        });
                        KMS38ActivationPanel.add(KMS38ActivationBtn, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 0, 5), 0, 0));

                        //---- label4 ----
                        label4.setText("Till the Year 2038 ");
                        KMS38ActivationPanel.add(label4, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 0, 0), 0, 0));
                    }
                    panel9.add(KMS38ActivationPanel);
                }
                panel3.add(panel9, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.NONE,
                    new Insets(0, 0, 5, 0), 0, 0));

                //======== panel11 ========
                {
                    panel11.setLayout(new GridBagLayout());
                    ((GridBagLayout)panel11.getLayout()).columnWidths = new int[] {0, 0, 0};
                    ((GridBagLayout)panel11.getLayout()).rowHeights = new int[] {0, 0};
                    ((GridBagLayout)panel11.getLayout()).columnWeights = new double[] {1.0, 1.0, 1.0E-4};
                    ((GridBagLayout)panel11.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

                    //---- label1 ----
                    label1.setText("Activation methods made by MASSGRAVE team");
                    panel11.add(label1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 3), 0, 0));

                    //---- label5 ----
                    label5.setText("(?)");
                    label5.setForeground(new Color(0x666666));
                    label5.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    label5.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            aboutMassgraveTeamPressed(e);
                        }
                    });
                    panel11.add(label5, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 0), 0, 0));
                }
                panel3.add(panel11, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            tabbedPane1.addTab("Activation", panel3);

            tabbedPane1.setSelectedIndex(1);
        }
        contentPane.add(tabbedPane1);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - redact
    private JMenuBar menuBar1;
    private JButton button1;
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JTextField textField1;
    private JPanel panel2;
    private JScrollPane scrollPane1;
    private JTable AppsTable;
    private JButton button5;
    private JPanel panel4;
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
    private JPanel panel3;
    private JPanel panel9;
    private JPanel HWIDActivationPanel;
    private JButton HWIDActivationBtn;
    private JLabel label2;
    private JPanel OhookActivationPanel;
    private JButton OhookActivationBtn;
    private JLabel label3;
    private JPanel KMS38ActivationPanel;
    private JButton KMS38ActivationBtn;
    private JLabel label4;
    private JPanel panel11;
    private JLabel label1;
    private JLabel label5;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
