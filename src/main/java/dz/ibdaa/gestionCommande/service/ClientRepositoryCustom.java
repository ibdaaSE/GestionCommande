package dz.ibdaa.gestionCommande.service;

import java.util.List;

import dz.ibdaa.gestionCommande.domain.Client;

public interface ClientRepositoryCustom {

	public List<Client> getFilteredList(String filtre, int maxRowPerPage, int index);

	public long count(String filtre);

}
