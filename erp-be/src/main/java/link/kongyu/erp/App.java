package link.kongyu.erp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Luojun
 * @version v1.0.0
 * @since 2025/9/25
 */

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);

        System.out.println("Spring-Boot execute!!!");
    }
}
