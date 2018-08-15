package ma.mt.fo.service.interfaces;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import ma.mt.fo.entity.FondsDataPricing;
import ma.mt.fo.util.HelpPricing;

/**
 * The Interface IFondsDataPricingService.
 */
public interface IFondsDataPricingService {
	/**
	 * List fonds data pricing.
	 *
	 * @return the list
	 */
	List<FondsDataPricing> listFondsDataPricing();

	/**
	 * Do pricing.
	 *
	 * @param idTypeCredilog
	 *            the id type credilog
	 * @param act
	 *            the action
	 * @param idScenarioFondRa
	 *            the id scenario fond ra
	 * @param idScenarioFondDDT
	 *            the id scenario fond DDT 
	 * @return the help pricing  
	 */ 
	
	HelpPricing doPricing(Long idTypeCredilog, int act, double scenarioRA, double scenarioDDT,
			String dateTrans, double val1, double val2, double val3, double val4, double val5,
			double val6, double val7, double val8, double val9, double valA, double valB, double valC) throws IOException;
	         
}
