package dz.ibdaa.gestionCommande.service;

import java.util.List;

import dz.ibdaa.gestionCommande.domain.Commande;
import dz.ibdaa.gestionCommande.domain.Produit;

public interface CommandeRepositoryCustom {
	
	public List<Commande> getFilteredList(String filtre, String dateDebut, String dateFin, String filterAttribut,
			String filterValue, int maxRowPerPage, int index);
	
	public long count(String filtre, String dateDebut, String dateFin, String filterAttribut,
			String filterValue);
	
	public void removeProduits(Integer commandeId);
	
	public List<Produit> getProduits(Integer commandeId);

}
