import General.PowerTools;
import Interfaces.PowerRegistryAdditions;
import UI.Windows.MainWindow;


import java.io.IOException;

public class Main implements PowerRegistryAdditions {

    public static void main(String[] args) throws IOException {
        System.out.println(System.getProperty("os.name"));
        MainWindow.initialize();
    }


}