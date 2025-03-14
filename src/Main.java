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


    public static void main(String[] args) throws IOException, URISyntaxException {
        System.out.println(System.getProperty("os.name"));

//        Runtime rt = Runtime.getRuntime();
//        String[] commands = {"General/dq.bat"};
//        Process proc = rt.exec(commands);
//
//        BufferedReader stdInput = new BufferedReader(new
//                InputStreamReader(proc.getInputStream()));
//
//        BufferedReader stdError = new BufferedReader(new
//                InputStreamReader(proc.getErrorStream()));
//
//        // Read the output from the command
//        System.out.println("Here is the standard output of the command:\n");
//        String s = null;
//        while ((s = stdInput.readLine()) != null) {
//             System.out.println(s);
//        }
//
//        // Read any errors from the attempted command
//        System.out.println("Here is the standard error of the command (if any):\n");
//        while ((s = stdError.readLine()) != null) {
//            System.out.println(s);
//        }

        File tempDir = new File(".temp");
        if (!tempDir.exists()) {
            FileUtils.forceMkdir(tempDir);
        }

        System.out.println();

        MainWindow.initialize();
    }
}

