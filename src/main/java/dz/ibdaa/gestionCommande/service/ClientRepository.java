package dz.ibdaa.gestionCommande.service;

import org.springframework.data.jpa.repository.JpaRepository;

import dz.ibdaa.gestionCommande.domain.Client;

public interface ClientRepository extends JpaRepository<Client, Integer>, ClientRepositoryCustom{

}
