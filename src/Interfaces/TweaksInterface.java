package Interfaces;

import General.PowerTools;
import com.github.tuupertunut.powershelllibjava.PowerShell;
import com.github.tuupertunut.powershelllibjava.PowerShellExecutionException;
import com.sun.jna.platform.win32.Advapi32Util;
import com.sun.jna.platform.win32.WinReg;
import General.MainProvider;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

public interface TweaksInterface extends PowerRegistryAdditions {

    PowerTools tools = MainProvider.getTools();
    PowerShell shell = tools.getShell();

    public static final int WINDOWS_VERSION = tools.getWindowsVersion();

    // Телеметрия
    default void SetTelemetry(boolean e) {
        registrySetIntValue(WinReg.HKEY_LOCAL_MACHINE, "SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Policies\\DataCollection", "AllowTelemetry", 1, 0, e);
        registrySetIntValue(WinReg.HKEY_LOCAL_MACHINE, "SOFTWARE\\Policies\\Microsoft\\Windows\\DataCollection", "AllowTelemetry", 1, 0, e);
        registrySetIntValue(WinReg.HKEY_CURRENT_USER, "SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\ContentDeliveryManager", "ContentDeliveryAllowed", 1, 0, e);
        registrySetIntValue(WinReg.HKEY_CURRENT_USER, "SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\ContentDeliveryManager", "OemPreInstalledAppsEnabled", 1, 0, e);
        registrySetIntValue(WinReg.HKEY_CURRENT_USER, "SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\ContentDeliveryManager", "PreInstalledAppsEnabled", 1, 0, e);
        registrySetIntValue(WinReg.HKEY_CURRENT_USER, "SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\ContentDeliveryManager", "PreInstalledAppsEverEnabled", 1, 0, e);
        registrySetIntValue(WinReg.HKEY_CURRENT_USER, "SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\ContentDeliveryManager", "SubscribedContent-338387Enabled", 1, 0, e);
        registrySetIntValue(WinReg.HKEY_CURRENT_USER, "SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\ContentDeliveryManager", "SubscribedContent-338388Enabled", 1, 0, e);
        registrySetIntValue(WinReg.HKEY_CURRENT_USER, "SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\ContentDeliveryManager", "SubscribedContent-338389Enabled", 1, 0, e);
        registrySetIntValue(WinReg.HKEY_CURRENT_USER, "SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\ContentDeliveryManager", "SubscribedContent-353698Enabled", 1, 0, e);
        registrySetIntValue(WinReg.HKEY_CURRENT_USER, "SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\ContentDeliveryManager", "SystemPaneSuggestionsEnabled", 1, 0, e);
        registrySetIntValue(WinReg.HKEY_CURRENT_USER, "SOFTWARE\\Microsoft\\Siuf\\Rules", "NumberOfSIUFInPeriod", 0, 0, e);
        registrySetIntValue(WinReg.HKEY_LOCAL_MACHINE, "SOFTWARE\\Policies\\Microsoft\\Windows\\DataCollection", "DoNotShowFeedbackNotifications", 0, 1, e);
        registrySetIntValue(WinReg.HKEY_CURRENT_USER, "SOFTWARE\\Policies\\Microsoft\\Windows\\CloudContent", "DisableTailoredExperiencesWithDiagnosticData", 0, 1, e);
        registrySetIntValue(WinReg.HKEY_LOCAL_MACHINE, "SOFTWARE\\Policies\\Microsoft\\Windows\\AdvertisingInfo", "DisabledByGroupPolicy", 0, 1, e);
        registrySetIntValue(WinReg.HKEY_LOCAL_MACHINE, "SOFTWARE\\Microsoft\\Windows\\Windows Error Reporting", "Disabled", 0, 1, e);
        registrySetIntValue(WinReg.HKEY_LOCAL_MACHINE, "SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\DeliveryOptimization\\Config", "DODownloadMode", 1, 1, e);
        registrySetIntValue(WinReg.HKEY_LOCAL_MACHINE, "SYSTEM\\CurrentControlSet\\Control\\Remote Assistance", "fAllowToGetHelp", 1, 0, e);
        registrySetIntValue(WinReg.HKEY_CURRENT_USER, "SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Explorer\\OperationStatusManager", "EnthusiastMode", 0, 1, e);
        registrySetIntValue(WinReg.HKEY_CURRENT_USER, "SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Explorer\\Advanced", "ShowTaskViewButton", 1, 0, e);
        registrySetIntValue(WinReg.HKEY_CURRENT_USER, "SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Explorer\\Advanced\\People", "PeopleBand", 1, 0, e);
        registrySetIntValue(WinReg.HKEY_CURRENT_USER, "SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Explorer\\Advanced", "LaunchTo", 1, 1, e);
        registrySetIntValue(WinReg.HKEY_LOCAL_MACHINE, "SYSTEM\\CurrentControlSet\\Control\\FileSystem", "LongPathsEnabled", 0, 1, e);
        registrySetIntValue(WinReg.HKEY_LOCAL_MACHINE, "SOFTWARE\\Microsoft\\Windows NT\\CurrentVersion\\Multimedia\\SystemProfile", "SystemResponsiveness", 1, 0, e);
        registrySetIntValue(WinReg.HKEY_LOCAL_MACHINE, "SOFTWARE\\Microsoft\\Windows NT\\CurrentVersion\\Multimedia\\SystemProfile", "NetworkThrottlingIndex", 1, 429496729, e);
        registrySetIntValue(WinReg.HKEY_CURRENT_USER, "Control Panel\\Desktop", "MenuShowDelay", 1, 1, e);
        registrySetIntValue(WinReg.HKEY_CURRENT_USER, "Control Panel\\Desktop", "AutoEndTasks", 1, 1, e);
        registrySetIntValue(WinReg.HKEY_LOCAL_MACHINE, "SYSTEM\\CurrentControlSet\\Control\\Session Manager\\Memory Management", "ClearPageFileAtShutdown", 0, 0, e);
        registrySetIntValue(WinReg.HKEY_LOCAL_MACHINE, "SYSTEM\\ControlSet001\\Services\\Ndu", "Start", 1, 2, e);
        registrySetIntValue(WinReg.HKEY_CURRENT_USER, "SYSTEM\\CurrentControlSet\\Services\\LanmanServer\\Parameters", "IRPStackSize", 20, 30, e);
        registrySetIntValue(WinReg.HKEY_CURRENT_USER, "SOFTWARE\\Policies\\Microsoft\\Windows\\Windows Feeds", "EnableFeeds", 1, 0, e);
        registrySetIntValue(WinReg.HKEY_CURRENT_USER, "Software\\Microsoft\\Windows\\CurrentVersion\\Feeds", "ShellFeedsTaskbarViewMode", 1, 2, e);
        registrySetIntValue(WinReg.HKEY_CURRENT_USER, "Software\\Microsoft\\Windows\\CurrentVersion\\Policies\\Explorer", "HideSCAMeetNow", 1, 1, e);
        registrySetIntValue(WinReg.HKEY_CURRENT_USER, "Software\\Microsoft\\Windows\\CurrentVersion\\UserProfileEngagement", "ScoobeSystemSettingEnabled", 1, 0, e);
        System.out.println("Telemetry executed");
    }

    default void ActivityHistory(boolean e) {
        registrySetIntValue(WinReg.HKEY_LOCAL_MACHINE, "SOFTWARE\\Policies\\Microsoft\\Windows\\System", "EnableActivityFeed", 1, 0, e);
        registrySetIntValue(WinReg.HKEY_LOCAL_MACHINE, "SOFTWARE\\Policies\\Microsoft\\Windows\\System", "PublishUserActivities", 1, 0, e);
        registrySetIntValue(WinReg.HKEY_LOCAL_MACHINE, "SOFTWARE\\Policies\\Microsoft\\Windows\\System", "UploadUserActivities", 1, 0, e);
        System.out.println("Activity History executed");
    }

    default void LocationTracking(boolean e) {
        registrySetStringValue(WinReg.HKEY_LOCAL_MACHINE, "SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\CapabilityAccessManager\\ConsentStore\\location", "Value", "Allow", "Deny", e);
        registrySetIntValue(WinReg.HKEY_LOCAL_MACHINE, "SOFTWARE\\Microsoft\\Windows NT\\CurrentVersion\\Sensor\\Overrides\\{BFA794E4-F964-4FDB-90F6-51056BFE4B44}", "SensorPermissionState", 1, 0, e);
        registrySetIntValue(WinReg.HKEY_LOCAL_MACHINE, "SYSTEM\\CurrentControlSet\\Services\\lfsvc\\Service\\Configuration", "Status", 1, 0, e);
        registrySetIntValue(WinReg.HKEY_LOCAL_MACHINE, "SYSTEM\\Maps", "AutoUpdateEnabled", 1, 0, e);
        System.out.println("Loc Tracking executed");
    }

    default void WifiSense(boolean e) {
        registrySetIntValue(WinReg.HKEY_LOCAL_MACHINE, "SOFTWARE\\WOW6432Node\\Microsoft\\Windows\\CurrentVersion\\Uninstall\\Microsoft Edge", "NoRemove", 1, 0, true);
    }

    default void StorageSense(boolean e) {
        registrySetIntValue(WinReg.HKEY_LOCAL_MACHINE, "Software\\Microsoft\\PolicyManager\\default\\WiFi\\AllowWiFiHotSpotReporting", "Value", 1, 0, e);
        registrySetIntValue(WinReg.HKEY_LOCAL_MACHINE, "Software\\Microsoft\\PolicyManager\\default\\WiFi\\AllowAutoConnectToWiFiSenseHotspots", "Value", 1, 0, e);
    }

    default void DeleteTempFiles(boolean e) throws IOException {
        FileUtils.cleanDirectory(new File("C:\\Windows\\Temp"));
    }

    default void DisableCopilot(boolean e) throws PowerShellExecutionException, IOException {
        registrySetIntValue(WinReg.HKEY_LOCAL_MACHINE, "SOFTWARE\\Policies\\Microsoft\\Windows\\WindowsCopilot", "TurnOffWindowsCopilot", 0, 1, e);
        registrySetIntValue(WinReg.HKEY_CURRENT_USER, "Software\\Policies\\Microsoft\\Windows\\WindowsCopilot", "TurnOffWindowsCopilot", 0, 1, e);
        registrySetIntValue(WinReg.HKEY_CURRENT_USER, "Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\Advanced", "ShowCopilotButton", 1, 0, e);

        if (e) {
            shell.executeCommands("dism /online /remove-package /package-name:Microsoft.Windows.Copilot");
        } else {
            shell.executeCommands("dism /online /add-package /package-name:Microsoft.Windows.Copilot");
        }
    }

    default void disableBackgroundApps(boolean e) {
        registrySetIntValue(WinReg.HKEY_CURRENT_USER, "Software\\Microsoft\\Windows\\CurrentVersion\\BackgroundAccessApplications", "GlobalUserDisabled", 0, 1, e);
    }

    default void onedrive(boolean e) throws IOException {
        Runtime.getRuntime().exec("taskkill /f /im OneDrive.exe");
        Runtime.getRuntime().exec("winget uninstall Microsoft.OneDrive");
    }

    default boolean isDarkThemeEnabled() {
        int AppsUseLightTheme = Advapi32Util.registryGetIntValue(WinReg.HKEY_CURRENT_USER, "Software\\Microsoft\\Windows\\CurrentVersion\\Themes\\Personalize", "AppsUseLightTheme");
        int SystemUsesLightTheme = Advapi32Util.registryGetIntValue(WinReg.HKEY_CURRENT_USER, "Software\\Microsoft\\Windows\\CurrentVersion\\Themes\\Personalize", "SystemUsesLightTheme");

        return AppsUseLightTheme == 0 || SystemUsesLightTheme == 0;
    }

    default void changeDarkThemeState(int v) {
        Advapi32Util.registrySetIntValue(WinReg.HKEY_CURRENT_USER, "Software\\Microsoft\\Windows\\CurrentVersion\\Themes\\Personalize", "AppsUseLightTheme", v);
        Advapi32Util.registrySetIntValue(WinReg.HKEY_CURRENT_USER, "Software\\Microsoft\\Windows\\CurrentVersion\\Themes\\Personalize", "SystemUsesLightTheme", v);
    }

    default boolean isBingSearchEnabled() {
        int BingSearchEnabled = Advapi32Util.registryGetIntValue(WinReg.HKEY_CURRENT_USER, "Software\\Microsoft\\Windows\\CurrentVersion\\Search", "BingSearchEnabled");
        return BingSearchEnabled == 1;
    }

    default void changeBingSearchState(int v) {
        Advapi32Util.registrySetIntValue(WinReg.HKEY_CURRENT_USER, "Software\\Microsoft\\Windows\\CurrentVersion\\Search", "BingSearchEnabled", v);
    }

    default boolean isShowHiddenFilesEnabled() {
        int Hidden = Advapi32Util.registryGetIntValue(WinReg.HKEY_CURRENT_USER, "Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\Advanced", "Hidden");
        return Hidden == 1;
    }

    default void ShowHiddenFiledChange(int v) throws PowerShellExecutionException, IOException {
        Advapi32Util.registrySetIntValue(WinReg.HKEY_CURRENT_USER, "Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\Advanced", "Hidden", v);
        shell.executeCommands("Stop-Process -ProcessName explorer -Force");
    }

    default boolean isShowFileExtensionsEnabled() {
        int HideFileExt = Advapi32Util.registryGetIntValue(WinReg.HKEY_CURRENT_USER, "Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\Advanced", "HideFileExt");
        return HideFileExt == 0;
    }

    default void ShowFileExtChange(int v) throws PowerShellExecutionException, IOException {
        Advapi32Util.registrySetIntValue(WinReg.HKEY_CURRENT_USER, "Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\Advanced", "HideFileExt", v);
        shell.executeCommands("Stop-Process -ProcessName explorer -Force");
    }

    default boolean isSearchButtonEnabled() {
        int SearchboxTaskbarMode = 0;
        //System.out.println(WINDOWS_VERSION);
        if (WINDOWS_VERSION == 11) {
            SearchboxTaskbarMode = Advapi32Util.registryGetIntValue(WinReg.HKEY_CURRENT_USER, "Software\\Microsoft\\Windows\\CurrentVersion\\Search", "SearchboxTaskbarMode");
        } else if (WINDOWS_VERSION == 10) {
            SearchboxTaskbarMode = Advapi32Util.registryGetIntValue(WinReg.HKEY_CURRENT_USER, "SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Search", "SearchboxTaskbarMode");
        }

        return SearchboxTaskbarMode == 1 || SearchboxTaskbarMode == 2;
    }

    default void SearchBtnChange(int v) {
        if (WINDOWS_VERSION == 11) {
            Advapi32Util.registrySetIntValue(WinReg.HKEY_CURRENT_USER, "Software\\Microsoft\\Windows\\CurrentVersion\\Search", "SearchboxTaskbarMode", v);
        } else if (WINDOWS_VERSION == 10) {
            Advapi32Util.registrySetIntValue(WinReg.HKEY_CURRENT_USER, "SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Search", "SearchboxTaskbarMode", v);
        }
    }

    default boolean isCenteredItemsinTaskBar() {
        int TaskbarAl = Advapi32Util.registryGetIntValue(WinReg.HKEY_CURRENT_USER, "Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\Advanced", "TaskbarAl");
        return TaskbarAl == 1;
    }

    default void CenteredItemsinTaskBarChange(int v) {
        Advapi32Util.registrySetIntValue(WinReg.HKEY_CURRENT_USER, "Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\Advanced", "TaskbarAl", v);
    }


}
