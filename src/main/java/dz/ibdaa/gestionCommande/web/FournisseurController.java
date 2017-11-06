package dz.ibdaa.gestionCommande.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dz.ibdaa.gestionCommande.domain.Fournisseur;
import dz.ibdaa.gestionCommande.service.FournisseurRepository;

@RestController
public class FournisseurController {
	
	@Autowired
	private FournisseurRepository fournisseurService;

	@RequestMapping(path = "/api/filteredFournisseurs")
	public @ResponseBody List<Fournisseur> getFilteredList(@RequestParam(value = "filter", defaultValue = "") String filter,
			@RequestParam(value = "pageIndex", defaultValue = "0") Integer pageIndex) {
		return this.fournisseurService.getFilteredList(filter, 20, pageIndex);
	}

	@RequestMapping(path="/api/filteredFournisseurs/count")
	public @ResponseBody long count(@RequestParam(value="filter", defaultValue="") String filter) {
		return this.fournisseurService.count(filter);
	}

}
