package dz.ibdaa.gestionCommande.service;

import org.springframework.data.jpa.repository.JpaRepository;

import dz.ibdaa.gestionCommande.domain.Commande;

public interface CommandeRepository extends JpaRepository<Commande, Integer>, CommandeRepositoryCustom {

}
