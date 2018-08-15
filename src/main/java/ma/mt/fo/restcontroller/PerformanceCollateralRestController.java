package ma.mt.fo.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ma.mt.fo.service.PerformanceCollateralService;
import ma.mt.fo.util.HelperPerformanceCollateral;

/**
 * The Class PerformanceCollateralRestController.
 */
@RestController
@RequestMapping("/api/performanceCollaterals")
public class PerformanceCollateralRestController {

	/** The performance collateral service. */
	@Autowired
	private PerformanceCollateralService performanceCollateralService;

	/**
	 * Trouver fonds data collateral.
	 *
	 * @param idTypeCredilog
	 *            the id type credilog
	 * @return the fonds data collateral performance
	 */
	
	@CrossOrigin
	@RequestMapping(value = "/{idTypeCredilog}", method = RequestMethod.GET)
	public HelperPerformanceCollateral showPerformanceCollateral(
			@PathVariable(name = "idTypeCredilog") Long idTypeCredilog) { 
		
		return performanceCollateralService.trouverCollateralPerfByIdTypeCredilog(idTypeCredilog); 
		
	}
}
