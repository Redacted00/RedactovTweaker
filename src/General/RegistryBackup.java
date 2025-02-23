package General;

import com.github.tuupertunut.powershelllibjava.PowerShell;
import com.github.tuupertunut.powershelllibjava.PowerShellExecutionException;

import java.io.IOException;

public class RegistryBackup {

    PowerShell shell = MainProvider.tools.getShell();

    public RegistryBackup(String keyname, String filename) {
        try {
            shell.executeCommands("reg export " + keyname + " " + filename + " /y");
        } catch (PowerShellExecutionException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void run() {
    }

}
