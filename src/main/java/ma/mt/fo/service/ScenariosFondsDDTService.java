package ma.mt.fo.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ma.mt.fo.dao.ScenariosFondsDDTJpaRepository;
import ma.mt.fo.entity.ScenariosFondsDDT;
import ma.mt.fo.service.interfaces.IScenariosFondsDDTService;

/**
 * The Class ScenariosFondsDDTService.
 */
@Service
public class ScenariosFondsDDTService implements IScenariosFondsDDTService {
	/** The Constant LOG. */
	private static final Logger LOG = Logger.getLogger(ScenariosFondsDDTService.class);

	/** The scenarios fonds DDT jpa repository. */
	@Autowired
	private ScenariosFondsDDTJpaRepository scenariosFondsDDTJpaRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see ma.mt.service.IScenariosFondsDDTService#listScenariosFondsDDT()
	 */
	@Override
	public List<ScenariosFondsDDT> listScenariosFondsDDT() {
		try {
			return scenariosFondsDDTJpaRepository.findAll();
		} catch (Exception e) {
			LOG.info("Error listScenariosFondsDDTJpaRepository items !");
		}
		return null;
	}
}
