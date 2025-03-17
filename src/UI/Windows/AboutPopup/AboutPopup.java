/*
 * Created by JFormDesigner on Fri Feb 14 15:07:02 YEKT 2025
 */

package UI.Windows.AboutPopup;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import java.awt.*;
import javax.swing.*;



/**
 * @author Redactov
 */
public class AboutPopup extends JFrame {
    public AboutPopup() {

        // UI Additions
        FlatMacDarkLaf.setup();
        UIManager.put("flatlaf.useWindowDecorations", true);
        UIManager.put("flatlaf.menuBarEmbedded", true);
        UIManager.put("TitlePane.centerTitle", true);
        UIManager.put("TitlePane.showIconBesideTitle", true);
        UIManager.put("TitlePane.borderColor", new Color(40,40,40,255));
        UIManager.put("TabbedPane.tabType", "card");
        UIManager.put("Table.alternateRowColor", new Color(48, 48, 48));

        //super(owner);
        initComponents();
        setSize(400,180);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - redact
        panel1 = new JPanel();
        label1 = new JLabel();
        panel2 = new JPanel();
        label2 = new JLabel();
        label3 = new JLabel();
        textPane1 = new JTextPane();

        //======== this ========
        setTitle("About");
        var contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));

        //======== panel1 ========
        {
            panel1.setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new javax. swing. border. EmptyBorder
            ( 0, 0, 0, 0) , "", javax. swing. border. TitledBorder. CENTER, javax. swing. border
            . TitledBorder. BOTTOM, new java .awt .Font ("" ,java .awt .Font .BOLD ,12 ), java. awt
            . Color. red) ,panel1. getBorder( )) ); panel1. addPropertyChangeListener (new java. beans. PropertyChangeListener( ){ @Override public void
            propertyChange (java .beans .PropertyChangeEvent e) {if ("" .equals (e .getPropertyName () )) throw new RuntimeException( )
            ; }} );
            panel1.setLayout(new GridBagLayout());
            ((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {133, 0, 0};
            ((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {0, 0};
            ((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
            ((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

            //---- label1 ----
            label1.setIcon(new ImageIcon(getClass().getResource("/resources/rticonsmall.png")));
            panel1.add(label1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.VERTICAL,
                new Insets(0, 5, 0, 5), 0, 0));

            //======== panel2 ========
            {
                panel2.setLayout(new GridBagLayout());
                ((GridBagLayout)panel2.getLayout()).columnWidths = new int[] {0, 0};
                ((GridBagLayout)panel2.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
                ((GridBagLayout)panel2.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
                ((GridBagLayout)panel2.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0, 1.0E-4};

                //---- label2 ----
                label2.setText("Redactov Tweaker 0.3A");
                label2.setFont(label2.getFont().deriveFont(label2.getFont().getStyle() | Font.BOLD, label2.getFont().getSize() + 10f));
                panel2.add(label2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 1, 0), 0, 0));

                //---- label3 ----
                label3.setText(" Made by Pavel Redactov");
                label3.setFont(label3.getFont().deriveFont(label3.getFont().getSize() + 3f));
                panel2.add(label3, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 5, 1, 0), 0, 0));

                //---- textPane1 ----
                textPane1.setBackground(new Color(0x1e1e1e));
                textPane1.setText("A program that allows you to customize your system, install necessary programs, configure settings that are inaccessible to the average user, activate Windows and Office.");
                textPane1.setEditable(false);
                textPane1.setAutoscrolls(false);
                textPane1.setFocusable(false);
                panel2.add(textPane1, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 5, 0, 0), 0, 0));
            }
            panel1.add(panel2, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        }
        contentPane.add(panel1);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - redact
    private JPanel panel1;
    private JLabel label1;
    private JPanel panel2;
    private JLabel label2;
    private JLabel label3;
    private JTextPane textPane1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
