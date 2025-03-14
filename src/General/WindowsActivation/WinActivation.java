package General.WindowsActivation;

import UI.Windows.ActivationScreen.ActivationScreen;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

public class WinActivation extends Thread {

    ActivationScreen frame;
    WinActivationType type;

    public WinActivation(WinActivationType Type) {
        System.out.println("Windows Activation setup");
        frame = new ActivationScreen();
        type = Type;
    }

    private String getScriptUrl() {

        String result = "";

        switch (type) {
            case HWID -> {
                result = "https://raw.githubusercontent.com/massgravel/Microsoft-Activation-Scripts/refs/heads/master/MAS/Separate-Files-Version/Activators/HWID_Activation.cmd";
            }
            case Ohook -> {
                result = "https://raw.githubusercontent.com/massgravel/Microsoft-Activation-Scripts/refs/heads/master/MAS/Separate-Files-Version/Activators/Ohook_Activation_AIO.cmd";
            }
            case KMS38 -> {
                result = "https://raw.githubusercontent.com/massgravel/Microsoft-Activation-Scripts/refs/heads/master/MAS/Separate-Files-Version/Activators/KMS38_Activation.cmd";
            }
        }
        return result;
    }

    private void e() throws IOException {
        
        frame.addLine("Preparing " + type + " activation...");
        InputStream in = new URL(getScriptUrl()).openStream();
        Path path = Paths.get(".temp/activate.cmd");
        Files.copy(in, path, StandardCopyOption.REPLACE_EXISTING);
        frame.addLine("Preparations are complete! Start activation...");

        ProcessBuilder builder = new ProcessBuilder(
                path.toAbsolutePath().toString(), "/" + type);

        builder.redirectErrorStream(true);
        Process p = builder.start();
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while (true) {
            line = r.readLine();
            if (line == null) { break; }
            System.out.println(line);
            frame.addLine(line);
        }

        frame.setSuccessful();
    }

    public void run() {
        System.out.println("thread runnn");
        System.out.println(type);

        try {
            e();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
