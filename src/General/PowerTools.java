package General;

import com.github.tuupertunut.powershelllibjava.PowerShell;
import com.github.tuupertunut.powershelllibjava.PowerShellExecutionException;


import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import com.sun.jna.platform.win32.Advapi32Util;
import com.sun.jna.platform.win32.Win32Exception;
import com.sun.jna.platform.win32.WinReg;
import com.sun.security.auth.module.NTSystem;
import org.json.JSONObject;

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

    // Нужна чтобы получить все ключи и значения в json, очень важно.
    public static Map<String, String> getkv(JSONObject e){
        Iterator<String> keys = e.keys();
        // Key, Value
        Map<String, String> map = new HashMap<>();

        while (keys.hasNext()) {
            String key = keys.next();
            String programs = e.get(key).toString();
            map.put(key, programs);
        }

        return map;
    }

    public static String openFile(String path) {
        try(BufferedReader br = new BufferedReader(new FileReader(path))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            String everything = sb.toString();
            return everything;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static String getContentFromURL(String e) {
        URL url = null;
        BufferedReader in = null;
        StringBuilder output = new StringBuilder();
        String inputLine;
        try {
            url = new URL(e);
            in = new BufferedReader(
                    new InputStreamReader(url.openStream()));
            while ((inputLine = in.readLine()) != null)
                output.append(inputLine);
            in.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        return output.toString();
    }

    public static Map<String, App> initApps() {

        //https://pastebin.com/raw/kUHvgmYv
        String appsURL = getContentFromURL("https://pastebin.com/raw/kUHvgmYv");

        //String jsFile = appsURL;
        System.out.println(appsURL);
        JSONObject apps = new JSONObject(appsURL);

        Map<String, App> map = new HashMap<>(Map.of());
        for (var x : apps.toMap().entrySet()) {
            String category = x.getKey();
            //System.out.println(category);
            ArrayList<Object> myList = (ArrayList) x.getValue();

            for (var rofls : myList) {
                Map<String, Object> appInfo = (HashMap) rofls;
                map.put(appInfo.get("Name").toString(), new App(appInfo.get("Name").toString(), category, appInfo.get("Link").toString(), appInfo.get("ID").toString()));
                //System.out.println(appInfo.get("Name") + " | " + appInfo.get("Link") + " | " + appInfo.get("ID"));
            }
        }
        return map;
    }

    public int installApps(List<App> e) {
        StringBuilder command = new StringBuilder();
        command.append("winget install ");

        for (var app : e) {
            command.append(app.getWingetID()).append(" ");
        }

        try {
            shell.executeCommands(command.toString());
        } catch (PowerShellExecutionException ex) {
            return -1;
        } catch (IOException ex) {
            return -2;
        }

        return 1;
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
