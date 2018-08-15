package ma.mt.fo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ma.mt.fo.entity.EcheancierPrevisionnel;

/**
 * The Interface EcheancierPrevisionnelJpaRepository.
 */
public interface EcheancierPrevisionnelJpaRepository extends JpaRepository<EcheancierPrevisionnel, Long> {

	/**
	 * Select current echeanciers by type credilog.
	 *
	 * @param idTypeCredilog
	 *            the id type credilog
	 * @param idScenarioFondsRA
	 *            the id scenario fonds RA
	 * @param idScenarioFondsDDT
	 *            the id scenario fonds DDT
	 * @return the list
	 */
		@Query("select echeancier from EcheancierPrevisionnel echeancier "
				+ "where echeancier.typeCredilog.idTypeCredilog = ?1 "
				+ "and echeancier.scenarioFondsRa.idScenarioFondsRa= ?2 " + "and echeancier.scenarioFondsDdt.idScenarioDdt= ?3 "
				+ "and echeancier.datePublication <= current_date " + "and echeancier.dateExpiration > current_date "
				+ "order by echeancier.datePublication DESC, echeancier.dateExpiration desc")
	EcheancierPrevisionnel selectCurrentEcheanciers(
			Long idTypeCredilog, 
			Long idScenarioFondsRA,
			Long idScenarioFondsDDT);
}
