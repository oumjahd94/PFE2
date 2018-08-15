package ma.mt.fo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ma.mt.fo.entity.FondsDataCashflow;

/**
 * The Interface FondsDataCashflowJpaRepository.
 */
public interface FondsDataCashflowJpaRepository extends JpaRepository<FondsDataCashflow, Long> {
	@Query("select cashflow from FondsDataCashflow cashflow where cashflow.typeCredilog.idTypeCredilog = ?1 and cashflow.scenariosFondsRa.idScenarioFondsRa= ?2 AND cashflow.scenarioFondsDdt.idScenarioDdt = ?3 and cashflow.datePublication <= current_date and cashflow.dateExpiration > current_date order by cashflow.datePublication desc, cashflow.dateExpiration desc")
	FondsDataCashflow selectFondsDataCashFlow(Long idTypeCredilog, Long idScenarioFondRA, Long idScenarioFondDDT);
}
