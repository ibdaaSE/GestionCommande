package dz.ibdaa.gestionCommande.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dz.ibdaa.gestionCommande.domain.Commande;
import dz.ibdaa.gestionCommande.service.CommandeRepository;

@RestController
public class CommandeController {
	
	@Autowired
	private CommandeRepository commandeService;

	@RequestMapping(path = "/api/filteredCommandes")
	public @ResponseBody List<Commande> getFilteredList(@RequestParam(value = "filter", defaultValue = "") String filter,
			@RequestParam(value = "dateDebut", defaultValue = "") String dateDebut,
			@RequestParam(value = "dateFin", defaultValue = "") String dateFin,
			@RequestParam(value = "filterAttribut", defaultValue = "") String filterAttribut,
			@RequestParam(value = "filterValue", defaultValue = "-1") String filterValue,
			@RequestParam(value = "pageIndex", defaultValue = "0") Integer pageIndex) {
		return this.commandeService.getFilteredList(filter, dateDebut, dateFin, filterAttribut, filterValue, 20, pageIndex);
	}

}
