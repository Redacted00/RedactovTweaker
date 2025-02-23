package General;

import java.io.IOException;

public class MainProvider {

    static PowerTools tools;

    static {
        try {
            tools = new PowerTools();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static PowerTools getTools() {
        return tools;
    }

}
