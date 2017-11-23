package dz.ibdaa.gestionCommande.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

import dz.ibdaa.gestionCommande.domain.Commande;
import dz.ibdaa.gestionCommande.security.model.User;

@Configuration
public class RepositoryConfig extends RepositoryRestConfigurerAdapter{

	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
		// TODO Auto-generated method stub
		config.exposeIdsFor(Commande.class);
		config.exposeIdsFor(User.class);
	}
	
	

}
