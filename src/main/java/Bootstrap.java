import java.io.File;

public class Bootstrap {

    public static void main(String[] args) throws Exception {
        String fileName = new File(Bootstrap.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getName();
        System.out.println(fileName);

        String command = String.format("start Holyrics/jre/bin/javaw -javaagent:\"%s\" -cp \"Holyrics.exe;lib/*\" -Xmx850m -Xms850m -XX:MaxDirectMemorySize=512m -Dsun.java2d.d3d=false -Dfile.encoding=UTF-8 com.limagiran.holyrics.Holyrics", fileName);
        Runtime rt = Runtime.getRuntime();
        String[] commands = {"cmd.exe", "/c", command};
        System.out.println("Before running process");
        rt.exec(commands).waitFor();
    }

}
