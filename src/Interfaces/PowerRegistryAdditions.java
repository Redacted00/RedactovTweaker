package Interfaces;

import com.sun.jna.platform.win32.Advapi32Util;
import com.sun.jna.platform.win32.WinReg;
import General.PowerTools;
import java.util.Objects;

public interface PowerRegistryAdditions {

    default void registrySetIntValue(WinReg.HKEY HKEY, String path, String name, int defaultval, int val, boolean e) {
        if (e) {
            if (val == -1) {
                Advapi32Util.registryDeleteValue(HKEY, path, name);
            }else {
                Advapi32Util.registrySetIntValue(HKEY, path, name, val);
            }
        }else {
            if (defaultval == -1) {
                Advapi32Util.registryDeleteValue(HKEY, path, name);
            }else {
                Advapi32Util.registrySetIntValue(HKEY, path, name, defaultval);
            }
        }
    }

    default void registrySetStringValue(WinReg.HKEY HKEY, String path, String name, String defaultval, String val, boolean e) {
        if (e) {
            if (Objects.equals(val, "<del>")) {
                Advapi32Util.registryDeleteValue(HKEY, path, name);
            }else {
                Advapi32Util.registrySetStringValue(HKEY, path, name, val);
            }
        }else {
            if (Objects.equals(defaultval, "<del>")) {
                Advapi32Util.registryDeleteValue(HKEY, path, name);
            }else {
                Advapi32Util.registrySetStringValue(HKEY, path, name, defaultval);
            }
        }
    }


}
