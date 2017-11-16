package dz.ibdaa.gestionCommande.service;

import org.springframework.data.jpa.repository.JpaRepository;

import dz.ibdaa.gestionCommande.domain.Produit;

public interface ProduitRepository extends JpaRepository<Produit, Integer>{

}
