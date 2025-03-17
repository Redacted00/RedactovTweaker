import UI.Windows.MainWindow;
import com.github.tuupertunut.powershelllibjava.PowerShell;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws IOException {
        //System.out.println(System.getProperty("os.name"));

        File tempDir = new File(".temp");
        if (!tempDir.exists()) {
            FileUtils.forceMkdir(tempDir);
        }

        System.out.println();

        MainWindow.initialize();
    }
}

