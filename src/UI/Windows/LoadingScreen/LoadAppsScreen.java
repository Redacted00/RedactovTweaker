/*
 * Created by JFormDesigner on Thu Feb 27 22:26:13 YEKT 2025
 */

package UI.Windows.LoadingScreen;

import java.awt.*;
import javax.swing.*;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import net.miginfocom.swing.*;

/**
 * @author Redactov
 */
public class LoadAppsScreen extends JFrame {
    public LoadAppsScreen() {

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
        setSize(400,125);
        setResizable(false);
        setVisible(true);
    }

    public void changeLogText(String e) {
        label2.setText(e);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - redact
        label1 = new JLabel();
        hSpacer1 = new JPanel(null);
        progressBar1 = new JProgressBar();
        hSpacer2 = new JPanel(null);
        label2 = new JLabel();

        //======== this ========
        setTitle("Applications are downloading...");
        var contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
            "insets 0,hidemode 3",
            // columns
            "[grow,fill]",
            // rows
            "[grow,fill]" +
            "[24,shrink 0,fill]" +
            "[grow,fill]"));

        //---- label1 ----
        label1.setText("Applications will be installed soon...");
        contentPane.add(label1, "cell 0 0,alignx center,growx 0");
        contentPane.add(hSpacer1, "cell 0 1,alignx left,growx 0");

        //---- progressBar1 ----
        progressBar1.setIndeterminate(true);
        contentPane.add(progressBar1, "cell 0 1");
        contentPane.add(hSpacer2, "cell 0 1,alignx trailing,growx 0");

        //---- label2 ----
        label2.setText("Loading...");
        contentPane.add(label2, "cell 0 2,alignx center,growx 0");
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
    private JLabel label2;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
