package dz.ibdaa.gestionCommande.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dz.ibdaa.gestionCommande.domain.Commande;
import dz.ibdaa.gestionCommande.domain.Produit;
import dz.ibdaa.gestionCommande.domain.ProduitCommande;
import dz.ibdaa.gestionCommande.service.CommandeRepository;
import dz.ibdaa.gestionCommande.service.ProduitRepository;

@RestController
public class CommandeController {

	@Autowired
	private CommandeRepository commandeService;

	@Autowired
	private ProduitRepository produitRepository;

	@RequestMapping(path = "/api/filteredCommandes")
	public @ResponseBody List<Commande> getFilteredList(
			@RequestParam(value = "filter", defaultValue = "") String filter,
			@RequestParam(value = "dateDebut", defaultValue = "") String dateDebut,
			@RequestParam(value = "dateFin", defaultValue = "") String dateFin,
			@RequestParam(value = "filterAttribut", defaultValue = "") String filterAttribut,
			@RequestParam(value = "filterValue", defaultValue = "-1") String filterValue,
			@RequestParam(value = "pageIndex", defaultValue = "0") Integer pageIndex) {
		return this.commandeService.getFilteredList(filter, dateDebut, dateFin, filterAttribut, filterValue, 20,
				pageIndex);
	}

	@RequestMapping(path = "/api/filteredCommandes/count")
	public @ResponseBody long count(@RequestParam(value = "filter", defaultValue = "") String filter,
			@RequestParam(value = "dateDebut", defaultValue = "") String dateDebut,
			@RequestParam(value = "dateFin", defaultValue = "") String dateFin,
			@RequestParam(value = "filterAttribut", defaultValue = "") String filterAttribut,
			@RequestParam(value = "filterValue", defaultValue = "-1") String filterValue) {
		return this.commandeService.count(filter, dateDebut, dateFin, filterAttribut, filterValue);
	}

	@RequestMapping(path = "/api/createCommande", method = RequestMethod.POST)
	@Transactional
	public @ResponseBody Commande createCommande(@RequestBody ProduitCommande produitCommande) {
		Commande commande = this.commandeService.save(produitCommande.getCommande());
		for (Produit produit : produitCommande.getProduits()) {
			produit.setCommande(commande);
			this.produitRepository.save(produit);
		}
		return commande;
	}

	@RequestMapping(path = "/api/editCommande", method = RequestMethod.PUT)
	@Transactional
	public @ResponseBody Commande editCommande(@RequestBody ProduitCommande produitCommande) {
		Commande commande = this.commandeService.save(produitCommande.getCommande());
		if (produitCommande.isTouchedList()) {
			this.commandeService.removeProduits(commande.getId());
			for (Produit produit : produitCommande.getProduits()) {
				produit.setCommande(commande);
				this.produitRepository.save(produit);
			}
		}
		return commande;
	}
	
	@RequestMapping(path = "/api/deleteCommande", method = RequestMethod.DELETE)
	@Transactional
	public @ResponseBody Integer removeCommande(@RequestBody Integer commandeId) {
		this.commandeService.removeProduits(commandeId);
		this.commandeService.delete(commandeId);
		return commandeId;
	}

}
