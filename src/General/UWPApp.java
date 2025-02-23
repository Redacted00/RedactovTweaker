package General;

import java.awt.*;

public class UWPApp {
    String Name;
    String Version;
    String Location;
    Image Icon;

    public String getName(){
        return Name;
    }
    public String getVersion(){
        return Version;
    }
    public String getLoc(){
        return Location;
    }
    public Image getIcon(){
        return Icon;
    }

    public void setup(String n, String v, String l) {
        Name = n;
        Version = v;
        Location = l;
    }

}
