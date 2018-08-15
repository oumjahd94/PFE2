package ma.mt.fo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ma.mt.fo.entity.ScenariosFondsRA;

/**
 * The Interface ScenariosFondsRAJpaRepository.
 */
public interface ScenariosFondsRAJpaRepository extends JpaRepository<ScenariosFondsRA, Long> {

	/**
	 * Selection defaults scenario RA.
	 *
	 * @param idTypeCredilog
	 *            the id type credilog
	 * @return the scenarios fonds RA
	 */
	@Query(value="select scenarioRa from ScenariosFondsRA scenarioRa "
			+ "where scenarioRa.typeCredilog.idTypeCredilog= ?1 "
			+ "and scenarioRa.defaultTx = 1 "
			+ "and scenarioRa.dateCreation <= current_date "
			+ "and scenarioRa.dateExpiration > current_date")
	ScenariosFondsRA selectionDefaultsScenarioRA(Long idTypeCredilog);     

	/**
	 * Select list RA.
	 *
	 * @param idTypeCredilog
	 *            the id type credilog
	 * @return the list
	 */
	@Query("Select scenarioRa "
			+ "from ScenariosFondsRA scenarioRa "
			+ "where scenarioRa.typeCredilog.idTypeCredilog = ?1 "
			+ "and scenarioRa.dateCreation <= current_date "
			+ "and scenarioRa.dateExpiration > current_date")
	List<ScenariosFondsRA> selectListRA(Long idTypeCredilog);
}
