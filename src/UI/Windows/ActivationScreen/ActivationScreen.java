/*
 * Created by JFormDesigner on Sun Mar 09 21:49:52 YEKT 2025
 */

package UI.Windows.ActivationScreen;

import javax.swing.border.*;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import java.awt.*;
import javax.swing.*;
import net.miginfocom.swing.*;

/**
 * @author Redactov
 */
public class ActivationScreen extends JFrame {
    public ActivationScreen() {

        // UI Additions
        FlatMacDarkLaf.setup();
        UIManager.put("flatlaf.useWindowDecorations", true);
        UIManager.put("flatlaf.menuBarEmbedded", true);
        UIManager.put("TitlePane.centerTitle", true);
        UIManager.put("TitlePane.showIconBesideTitle", true);
        UIManager.put("TitlePane.borderColor", new Color(40,40,40,255));
        UIManager.put("TabbedPane.tabType", "card");
        UIManager.put("Table.alternateRowColor", new Color(48, 48, 48));

        initComponents();
        setSize(400,345);
        setResizable(false);
        setVisible(true);

        //initComponents();
    }

    public void setLogs(String text) {
        LogsTextPane.setText(text);
    }

    public void setSuccessful() {
        label1.setText("The script was successfully completed!");
        button1.setEnabled(false);
        progressBar1.setForeground(new Color(167, 223, 0));
        progressBar1.setValue(1000);
        progressBar1.setIndeterminate(false);
    }

    public void addLine(String text) {
        LogsTextPane.setText(LogsTextPane.getText() + "\n" + text);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - redact
        label1 = new JLabel();
        hSpacer1 = new JPanel(null);
        progressBar1 = new JProgressBar();
        hSpacer2 = new JPanel(null);
        scrollPane1 = new JScrollPane();
        LogsTextPane = new JTextPane();
        button1 = new JButton();

        //======== this ========
        setTitle("Activation...");
        var contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
            "insets 5 3 5 3,hidemode 3,gap 5 5",
            // columns
            "[grow,fill]",
            // rows
            "[]" +
            "[24,shrink 0,fill]" +
            "[grow,fill]" +
            "[]"));

        //---- label1 ----
        label1.setText("Please wait to finish ");
        contentPane.add(label1, "cell 0 0,alignx center,growx 0");
        contentPane.add(hSpacer1, "cell 0 1,alignx left,growx 0");

        //---- progressBar1 ----
        progressBar1.setIndeterminate(true);
        contentPane.add(progressBar1, "cell 0 1");
        contentPane.add(hSpacer2, "cell 0 1,alignx right,growx 0");

        //======== scrollPane1 ========
        {
            scrollPane1.setBorder(new TitledBorder("Logs"));

            //---- LogsTextPane ----
            LogsTextPane.setEditable(false);
            LogsTextPane.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
            scrollPane1.setViewportView(LogsTextPane);
        }
        contentPane.add(scrollPane1, "cell 0 2");

        //---- button1 ----
        button1.setText("Abort");
        contentPane.add(button1, "cell 0 3");
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - redact
    private JLabel label1;
    private JPanel hSpacer1;
    private JProgressBar progressBar1;
    private JPanel hSpacer2;
    private JScrollPane scrollPane1;
    private JTextPane LogsTextPane;
    private JButton button1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
