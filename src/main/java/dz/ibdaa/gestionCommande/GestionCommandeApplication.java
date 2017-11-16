package dz.ibdaa.gestionCommande;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.hateoas.HypermediaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class GestionCommandeApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionCommandeApplication.class, args);
	}
}
