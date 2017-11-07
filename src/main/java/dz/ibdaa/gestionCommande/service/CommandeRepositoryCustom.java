package dz.ibdaa.gestionCommande.service;

import java.util.List;

import dz.ibdaa.gestionCommande.domain.Commande;

public interface CommandeRepositoryCustom {
	
	public List<Commande> getFilteredList(String filtre, String dateDebut, String dateFin, String filterAttribut,
			String filterValue, int maxRowPerPage, int index);

}
