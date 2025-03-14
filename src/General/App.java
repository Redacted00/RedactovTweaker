package General;

import org.json.JSONObject;

import java.util.Map;
import java.util.Objects;

public class App {
    String Name;
    String Link;
    String WingetID;
    String Category;

    public App(String name, String category, String link, String ID) {
        Category = category;
        Name = name;
        Link = link;
        WingetID = ID;
    }

    public String getName() {
        return Name;
    }

    public String getLink() {
        return Link;
    }

    public String getWingetID() {
        return WingetID;
    }

    public String getCategory() {
        return Category;
    }

}
