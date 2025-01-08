package management_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

//@SpringBootApplication
@SpringBootApplication(scanBasePackages = "management_system")
//@ComponentScan(basePackages = {"management_system.mapper", "management_system"})
public class ManagementSystemApplication {
	public static void main(String[] args) {
		SpringApplication.run(ManagementSystemApplication.class, args);
	}

}
