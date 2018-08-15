package ma.mt.fo.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ma.mt.fo.entity.FondsDataHistorique;
import ma.mt.fo.entity.FondsDataRapport;
import ma.mt.fo.service.interfaces.IFondDataRapportService;
import ma.mt.fo.service.interfaces.IFondsDataHistoriqueService;
import ma.mt.fo.util.HelperHistorique;

@RestController
@RequestMapping(value = "/api/fondsDataHistoriques")
public class FondsDataHistoriqueRestController {

	@Autowired
	private IFondsDataHistoriqueService fondsDataHistoriqueService;  
 
		
	@CrossOrigin
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<FondsDataHistorique> listFondsDataHistorique() {
		return fondsDataHistoriqueService.listFondsDataHistorique();
	} 
	
	@CrossOrigin
	@RequestMapping(value = "/{idTypeCredilog}", method = RequestMethod.GET)
	public HelperHistorique trouverHistoriqueFondsByIdTypeCredilog(
			@PathVariable(name = "idTypeCredilog") Long idTypeCredilog) {
		return fondsDataHistoriqueService.trouverHistoriqueFondsByIdTypeCredilog(idTypeCredilog);
	}  
	
	
		
}
