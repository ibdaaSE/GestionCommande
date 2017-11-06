package dz.ibdaa.gestionCommande.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dz.ibdaa.gestionCommande.domain.Client;
import dz.ibdaa.gestionCommande.service.ClientRepository;

@RestController
public class ClientController {

	@Autowired
	private ClientRepository clientService;

	@RequestMapping(path = "/api/filteredClients")
	public @ResponseBody List<Client> getFilteredList(@RequestParam(value = "filter", defaultValue = "") String filter,
			@RequestParam(value = "pageIndex", defaultValue = "0") Integer pageIndex) {
		return this.clientService.getFilteredList(filter, 20, pageIndex);
	}

	@RequestMapping(path="/api/filteredClients/count")
	public @ResponseBody long count(@RequestParam(value="filter", defaultValue="") String filter) {
		return this.clientService.count(filter);
	}

}
