package General;
import General.MainProvider;
import UI.Windows.LoadingScreen.LoadAppsScreen;
import com.github.tuupertunut.powershelllibjava.PowerShell;
import com.github.tuupertunut.powershelllibjava.PowerShellExecutionException;

import javax.swing.*;
import java.io.IOException;
import java.util.List;

public class WingetInstaller extends Thread {
    PowerShell shell = MainProvider.getTools().shell;

    List<App> Applications;
    //App CurrentApp;
    LoadAppsScreen frame;

    public WingetInstaller(List<App> apps) {

        System.out.println("Winget Installer setup");
        Applications = apps;

        // Setup loading screen
        frame = new LoadAppsScreen();

        // Setup new thread
        //WingetInstaller thread = new WingetInstaller(apps);
        //thread.start();

    }


    public void run() {
        System.out.println("Starting new installing thread!!!!");
        for (var e : Applications) {
            try {
                System.out.println("Setup: " + e.getName());
                frame.changeLogText("Installing " + e.Name);
                System.out.println("Logging: ");
                System.out.println(shell.executeCommands("winget install " + e.getWingetID()));
            } catch (PowerShellExecutionException | IOException ex) {
                System.out.println("Something went wrong");
                throw new RuntimeException(ex);
            }
        }
        frame.setVisible(false);
        frame.dispose();
        JOptionPane.showMessageDialog(null, "The applications were successfully installed!");
    }




}
