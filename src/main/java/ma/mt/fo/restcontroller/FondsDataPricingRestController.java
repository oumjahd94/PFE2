package ma.mt.fo.restcontroller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ma.mt.fo.entity.FondsDataPricing;
import ma.mt.fo.service.interfaces.IFondsDataPricingService;
import ma.mt.fo.util.HelpPricing;

/**
 * The Class FondsDataPricingRestController.
 */
@RestController
@RequestMapping(value = "/api/fondsDataPricings")
public class FondsDataPricingRestController {

	/** The fonds data pricing service. */ 
	@Autowired
	private IFondsDataPricingService fondsDataPricingService;

	/**
	 * List fonds data pricing.
	 *
	 * @return the list
	 */ 	
	@CrossOrigin
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<FondsDataPricing> listFondsDataPricing() {
		return fondsDataPricingService.listFondsDataPricing();
	}

	/**
	 * Do pricing.
	 *
	 * @param idTypeCredilog
	 *            the id type credilog
	 * @param action
	 *            the action
	 * @param idScenarioFondRa
	 *            the id scenario fond ra
	 * @param idScenarioFondDDT
	 *            the id scenario fond DDT
	 * @return the help pricing
	 * @throws ParseException 
	 */ 
	
	// web service de pricing 
	
	@CrossOrigin
	@RequestMapping(value = "/{idTypeCredilog}", method = RequestMethod.POST)
	public HelpPricing doPricing(
			
			@PathVariable(name = "idTypeCredilog") Long idTypeCredilog, 
			@RequestParam("sceRA") String valueScenarioRA,  
			@RequestParam("sceDDT") String valueScenarioDDT,
			@RequestParam("dtTransaction") String dateTransaction, 
			@RequestParam("value1") String value1, 
			@RequestParam("value2") String value2, 
			@RequestParam("value3") String value3, 
			@RequestParam("value4") String value4, 
			@RequestParam("value5") String value5, 
			@RequestParam("value6") String value6, 
			@RequestParam("value7") String value7, 
			@RequestParam("value8") String value8, 
			@RequestParam("value9") String value9,
			@RequestParam("valueA") String valueA,
			@RequestParam("valueB") String valueB, 
			@RequestParam("valueC") String valueC, 
			@RequestParam("action") String action
			) throws ParseException { 
		      
		   double val1 = Double.parseDouble(value1) ; 
		   double val2 = Double.parseDouble(value2) ; 
		   double val3 = Double.parseDouble(value3) ; 
		   double val4 = Double.parseDouble(value4) ; 
		   double val5 = Double.parseDouble(value5) ; 
		   double val6 = Double.parseDouble(value6) ; 
		   double val7 = Double.parseDouble(value7) ; 
		   double val8 = Double.parseDouble(value8) ;  
		   double val9 = Double.parseDouble(value9) ; 
		   double valA = Double.parseDouble(valueA) ;  
		   double valB = Double.parseDouble(valueB) ; 
		   double valC = Double.parseDouble(valueC) ;     
		   
		   int act = Integer.parseInt(action) ;     
		   
		   double scenarioRA  = Double.parseDouble(valueScenarioRA) ; 
		   double scenarioDDT = Double.parseDouble(valueScenarioDDT) ;  
		   
		   		
		try { 
			
			return fondsDataPricingService.doPricing(
					idTypeCredilog, 
					act,scenarioRA,
					scenarioDDT,
					dateTransaction, 
					val1, val2, val3, val4, val5, val6, val7, val8,val9, 
					valA, valB, valC); 
			
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
