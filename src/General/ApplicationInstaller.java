package General;
import UI.Windows.LoadingScreen.LoadAppsScreen;
import com.github.tuupertunut.powershelllibjava.PowerShell;
import com.github.tuupertunut.powershelllibjava.PowerShellExecutionException;

import javax.swing.*;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class ApplicationInstaller extends Thread {
    PowerShell shell = MainProvider.getTools().shell;

    List<App> Applications;
    //App CurrentApp;
    LoadAppsScreen frame;

    public ApplicationInstaller(List<App> apps) {

        System.out.println("Winget Installer setup");
        Applications = apps;

        // Setup loading screen
        frame = new LoadAppsScreen();

        // Setup new thread
        //WingetInstaller thread = new WingetInstaller(apps);
        //thread.start();

    }

    private void downloadFile(String urlS) throws IOException {
        // Создаю ссылку
        URL url = new URL(urlS);
        // Начинаю скачивание
        BufferedInputStream bis = new BufferedInputStream(url.openStream());

        // Создаю файл куда буду записывать данные
        FileOutputStream fis = new FileOutputStream(url.getFile());
        // Буффер
        byte[] buffer = new byte[1024];
        int count = 0;
        // Записывает в созданный нами файл информацию из скачивания
        while((count = bis.read(buffer,0,1024)) != -1)
        {
            fis.write(buffer, 0, count);
        }
        fis.close();
        bis.close();
    }

    public void run() {
        System.out.println("Starting new installing thread!!!!");
        for (var e : Applications) {
//            try {
//                System.out.println("Setup: " + e.getName());
//                frame.changeLogText("Installing " + e.Name);
//                System.out.println("Logging: ");
//                System.out.println(shell.executeCommands("winget install " + e.getWingetID()));
//            } catch (PowerShellExecutionException | IOException ex) {
//                System.out.println("Something went wrong");
//                throw new RuntimeException(ex);
//            }

            System.out.println("Setup: " + e.getName());
            frame.changeLogText("Installing " + e.Name);
            try {
                downloadFile(e.Link);
            } catch (IOException ex) {
                System.out.println("Failed to download IOException: " + ex.toString() );
                throw new RuntimeException(ex);
            }
        }

        frame.setVisible(false);
        frame.dispose();
        JOptionPane.showMessageDialog(null, "The applications were successfully installed!");
    }




}
