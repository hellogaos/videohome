package videohome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

import java.net.InetAddress;

@SpringBootApplication
@ServletComponentScan
public class VideoHomeServer {

    public static void main(String[] args) throws Exception {
        System.out.println("***** 欢迎使用“影视小窝” *****\n");
        SpringApplication.run(VideoHomeServer.class, args);
        System.out.println(" 服务已启动完成");
        System.out.println(" ╔════════════════════════════════════════════════");
        System.out.println(" ║ 本机请在浏览器访问: 127.0.0.1");
        System.out.println(" ║ 局域网内其它设备请在浏览器访问: "+InetAddress.getLocalHost().getHostAddress());
        System.out.println(" ║ (若无法访问，请尝试关闭本机的防火墙)");
        System.out.println(" ╚════════════════════════════════════════════════");
        System.out.println(" 若要停止服务，只需关闭该窗口即可");

        Thread.sleep (2000);
        Runtime.getRuntime().exec("explorer http://127.0.0.1");

    }

}
