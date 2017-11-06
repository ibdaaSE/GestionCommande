package dz.ibdaa.gestionCommande.service;

import java.util.List;

import dz.ibdaa.gestionCommande.domain.Fournisseur;

public interface FournisseurRepositoryCustom {
	
	public List<Fournisseur> getFilteredList(String filtre, int maxRowPerPage, int index);

	public long count(String filtre);

}
