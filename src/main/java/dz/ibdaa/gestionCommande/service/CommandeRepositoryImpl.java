package dz.ibdaa.gestionCommande.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

import dz.ibdaa.gestionCommande.domain.Commande;
import dz.ibdaa.gestionCommande.util.DatabaseQuery;

public class CommandeRepositoryImpl implements CommandeRepositoryCustom {

	@Autowired
	private EntityManager em;

	public List<Commande> getFilteredList(String filtre, String dateDebut, String dateFin, String filterAttribut,
			String filterValue, int maxRowPerPage, int index) {

		String querySearch = DatabaseQuery.getQueryJoinSearch("*", "(" + DatabaseQuery.getQueryDateSearch("*", "Commande", "", "",
						"DATE(dateCommande)", dateDebut, dateFin) + ") cm LEFT JOIN Client c ON cm.client = c.id",
				"c.raisonSociale c.responsable cm.numero cm.refCommandeClient", filtre, filterAttribut, filterValue)
				+ " ORDER BY cm.dateCommande DESC";

		Query query = em.createNativeQuery(querySearch, Commande.class);
		query.setMaxResults(maxRowPerPage);
		query.setFirstResult(index);

		List<Commande> resultList = query.getResultList();
		em.close();
		return resultList;
	}

}
