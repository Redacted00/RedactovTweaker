package General;

import com.github.tuupertunut.powershelllibjava.PowerShell;
import com.github.tuupertunut.powershelllibjava.PowerShellExecutionException;


import java.io.IOException;
import java.util.Objects;
import com.sun.jna.platform.win32.Advapi32Util;
import com.sun.jna.platform.win32.Win32Exception;
import com.sun.jna.platform.win32.WinReg;
import com.sun.security.auth.module.NTSystem;

public class PowerTools {

    PowerShell shell = PowerShell.open();

    public PowerTools() throws IOException {}

    public boolean ifAppExist(String pkg) throws PowerShellExecutionException, IOException {
        return !Objects.equals(shell.executeCommands("Get-AppxPackage " + pkg), "");
    }

    public PowerShell getShell() {
        return shell;
    }


    public int getWindowsVersion(){
        if (Objects.equals(System.getProperty("os.name"), "Windows 11")) {
            return 11;
        } else if (Objects.equals(System.getProperty("os.name"), "Windows 10")) {
            return 10;
        }
        return -1;
    }


    // About Copilot
    public boolean ifCopilot() {
        try {
            int cop = Advapi32Util.registryGetIntValue(WinReg.HKEY_LOCAL_MACHINE, "SOFTWARE\\Policies\\Microsoft\\Windows", "TurnOffWindowsCopilot");
            return cop != 1;
        }catch (Win32Exception e) {
            return true;
        }
    }

    public void setCopilot(boolean e) {
        try{
            int cop = Advapi32Util.registryGetIntValue(WinReg.HKEY_LOCAL_MACHINE, "SOFTWARE\\Policies\\Microsoft\\Windows", "TurnOffWindowsCopilot");

            if (e) {
                Advapi32Util.registrySetIntValue(WinReg.HKEY_LOCAL_MACHINE, "SOFTWARE\\Policies\\Microsoft\\Windows", "TurnOffWindowsCopilot", 0);
            }else {
                Advapi32Util.registrySetIntValue(WinReg.HKEY_LOCAL_MACHINE, "SOFTWARE\\Policies\\Microsoft\\Windows", "TurnOffWindowsCopilot", 1);
            }
            return;

        }catch (Exception e1) {
            //Advapi32Util.registryCreateKey(WinReg.HKEY_LOCAL_MACHINE, "SOFTWARE\\Policies\\Microsoft\\Windows", "TurnOffWindowsCopilot");
            if (e) {
                Advapi32Util.registrySetIntValue(WinReg.HKEY_LOCAL_MACHINE, "SOFTWARE\\Policies\\Microsoft\\Windows", "TurnOffWindowsCopilot", 0);
            }else {
                Advapi32Util.registrySetIntValue(WinReg.HKEY_LOCAL_MACHINE, "SOFTWARE\\Policies\\Microsoft\\Windows", "TurnOffWindowsCopilot", 1);

            }
            return;
        }
    }

    public void backupRegistry(String keyname, String filename) {
        try {
            shell.executeCommands("reg export " + keyname + " " + filename + " /y");
        } catch (PowerShellExecutionException | IOException e) {
            throw new RuntimeException(e);
        }
    }



    public boolean isAdmin() {
        String groups[] = (new NTSystem()).getGroupIDs();
        for (String group : groups) {
            if (group.equals("S-1-5-32-544"))
                return true;
        }
        return false;
    }

}
