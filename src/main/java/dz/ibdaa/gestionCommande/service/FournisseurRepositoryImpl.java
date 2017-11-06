package dz.ibdaa.gestionCommande.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

import dz.ibdaa.gestionCommande.domain.Fournisseur;
import dz.ibdaa.gestionCommande.util.DatabaseQuery;

public class FournisseurRepositoryImpl implements FournisseurRepositoryCustom {

	@Autowired
	private EntityManager em;

	@Override
	public List<Fournisseur> getFilteredList(String filtre, int maxRowPerPage, int index) {
		String querySearch = DatabaseQuery.getQuerySearch("*", "Fournisseur f",
				"f.raisonSociale f.cp f.responsable f.ville f.pays f.email", filtre) + " ORDER BY f.id DESC";

		Query query = em.createNativeQuery(querySearch, Fournisseur.class);
		query.setMaxResults(maxRowPerPage);
		query.setFirstResult(index);
		List<Fournisseur> resultList = (List<Fournisseur>) query.getResultList();
		em.close();
		return resultList;
	}

	@Override
	public long count(String filtre) {
		String querySearch = DatabaseQuery.getQuerySearch("count(id)", "Fournisseur f",
				"f.raisonSociale f.cp f.responsable f.ville f.pays f.email", filtre);

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
