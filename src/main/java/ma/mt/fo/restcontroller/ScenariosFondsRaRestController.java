package ma.mt.fo.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ma.mt.fo.entity.ScenariosFondsRA;
import ma.mt.fo.service.interfaces.IScenariosFondsRaService;

/**
 * The Class ScenariosFondsRaRestController.
 */
@RestController
@RequestMapping(value = "/api/scenariosFondsRa/")
public class ScenariosFondsRaRestController {

	/** The scenarios fonds ra service. */
	@Autowired
	private IScenariosFondsRaService scenariosFondsRaService;

	/**
	 * List scenario fonds ra.
	 *
	 * @return the list
	 */
	@CrossOrigin
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<ScenariosFondsRA> listScenarioFondsRa() {
		return scenariosFondsRaService.listScenarioFondsRa();
	}

}
