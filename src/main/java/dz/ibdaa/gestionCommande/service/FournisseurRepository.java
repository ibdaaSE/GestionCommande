package dz.ibdaa.gestionCommande.service;

import org.springframework.data.jpa.repository.JpaRepository;

import dz.ibdaa.gestionCommande.domain.Fournisseur;

public interface FournisseurRepository extends JpaRepository<Fournisseur, Integer>, FournisseurRepositoryCustom{

}
