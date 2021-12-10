import java.io.File;

public class Bootstrap {

    public static void main(String[] args) throws Exception {
        String fileName = new File(Bootstrap.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getName();
        System.out.println(fileName);

        String command = String.format("start Holyrics/jre/bin/javaw -javaagent:\"%s\" -cp \"Holyrics.exe;lib/*\" com.limagiran.holyrics.Holyrics", fileName);

        Runtime rt = Runtime.getRuntime();
        String[] commands = {"cmd.exe", "/c", command};
        rt.exec(commands);
    }

}
