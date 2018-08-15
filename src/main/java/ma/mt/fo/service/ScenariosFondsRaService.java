package ma.mt.fo.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ma.mt.fo.dao.ScenariosFondsRAJpaRepository;
import ma.mt.fo.entity.ScenariosFondsRA;
import ma.mt.fo.service.interfaces.IScenariosFondsRaService;

/**
 * The Class ScenariosFondsRaService.
 */
@Service
public class ScenariosFondsRaService implements IScenariosFondsRaService {
	/** The Constant LOG. */
	private static final Logger LOG = Logger.getLogger(ScenariosFondsRaService.class);

	/** The scenarios fonds RA jpa repository. */
	@Autowired
	private ScenariosFondsRAJpaRepository scenariosFondsRAJpaRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see ma.mt.service.IScenariosFondsRaService#listScenarioFondsRa()
	 */
	@Override
	public List<ScenariosFondsRA> listScenarioFondsRa() {
		try {
			return scenariosFondsRAJpaRepository.findAll();
		} catch (Exception e) {
			LOG.info("Error listScenariosFondsRAJpaRepository items !");
		}
		return null;
	}
}
