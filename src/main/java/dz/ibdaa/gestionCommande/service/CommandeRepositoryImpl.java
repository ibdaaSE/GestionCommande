package dz.ibdaa.gestionCommande.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

import dz.ibdaa.gestionCommande.domain.Commande;
import dz.ibdaa.gestionCommande.domain.Produit;
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

	@Override
	public long count(String filtre, String dateDebut, String dateFin, String filterAttribut, String filterValue) {
		String querySearch = DatabaseQuery.getQueryJoinSearch("count(raisonSociale)", "(" + DatabaseQuery.getQueryDateSearch("*", "Commande", "", "",
				"DATE(dateCommande)", dateDebut, dateFin) + ") cm LEFT JOIN Client c ON cm.client = c.id",
		"c.raisonSociale c.responsable cm.numero cm.refCommandeClient", filtre, filterAttribut, filterValue);

		Query query = em.createNativeQuery(querySearch);
		List resultList = query.getResultList();
		if (resultList.isEmpty()) {
            return 0l;
        }
		long count = Long.valueOf(resultList.get(0).toString());
		em.close();
		return count;
	}
	
	@Override
	public void removeProduits(Integer commandeId){
		String querySearch = "DELETE FROM Produits WHERE commande = " + commandeId;
		Query query = em.createNativeQuery(querySearch);
		em.getTransaction().begin();
		query.executeUpdate();
		em.getTransaction().commit();
	}
	
	public List<Produit> getProduits(Integer commandeId) {

		String querySearch = DatabaseQuery.getQueryJoinSearch("*", "Produits p",
				"", "", "p.commande", "" + commandeId);

		Query query = em.createNativeQuery(querySearch, Produit.class);

		List<Produit> resultList = query.getResultList();
		em.close();
		return resultList;
	}

}
