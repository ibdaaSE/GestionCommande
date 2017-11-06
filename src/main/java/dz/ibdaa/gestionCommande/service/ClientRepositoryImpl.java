package dz.ibdaa.gestionCommande.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

import dz.ibdaa.gestionCommande.domain.Client;
import dz.ibdaa.gestionCommande.util.DatabaseQuery;

public class ClientRepositoryImpl implements ClientRepositoryCustom{
	
	@Autowired
	private EntityManager em;

	@Override
	public List<Client> getFilteredList(String filtre, int maxRowPerPage, int index) {
		String querySearch = DatabaseQuery.getQuerySearch("*", "Client c",
				"c.raisonSociale c.cp c.responsable c.ville c.pays c.email", filtre) +
				" ORDER BY c.id DESC";

		Query query = em.createNativeQuery(querySearch, Client.class);
		query.setMaxResults(maxRowPerPage);
		query.setFirstResult(index);
		List<Client> resultList = (List<Client>) query.getResultList();
		em.close();
		return resultList;
	}

	@Override
	public long count(String filtre) {
		String querySearch = DatabaseQuery.getQuerySearch("count(id)", "Client c",
				"c.raisonSociale c.cp c.responsable c.ville c.pays c.email", filtre);

		Query query = em.createNativeQuery(querySearch);
		List resultList = query.getResultList();
		if (resultList.isEmpty()) {
            return 0l;
        }
		long count = Long.valueOf(resultList.get(0).toString());
		em.close();
		return count;
	}

}
