package ma.mt.fo.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ma.mt.fo.entity.ScenariosFondsDDT;
import ma.mt.fo.service.interfaces.IScenariosFondsDDTService;

/**
 * The Class ScenariosFondsDDTRestController.
 */
@RestController
@RequestMapping(value = "/api/scenariosFondsDDT/")
public class ScenariosFondsDDTRestController {

	/** The scenarios fonds DDT service. */
	@Autowired
	private IScenariosFondsDDTService scenariosFondsDDTService;

	/**
	 * List scenarios fonds DDT.   
	 *
	 * @return the list
	 */
	@CrossOrigin
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<ScenariosFondsDDT> listScenariosFondsDDT() {
		return scenariosFondsDDTService.listScenariosFondsDDT();
	}

}
