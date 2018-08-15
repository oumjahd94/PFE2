package ma.mt.fo.dao;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ma.mt.fo.entity.ScenariosFondsDDT;

/**
 * The Interface ScenariosFondsDDTJpaRepository.
 */
public interface ScenariosFondsDDTJpaRepository extends JpaRepository<ScenariosFondsDDT, Long> { 
	


	@Query("Select scenariosFondsDDT from ScenariosFondsDDT scenariosFondsDDT "
			+ "where scenariosFondsDDT.typeCredilog.idTypeCredilog = ?1 "
			+ "and scenariosFondsDDT.dateCreationDdt <= current_date "
			+ "and scenariosFondsDDT.DateExpirationDdt > current_date")
	List<ScenariosFondsDDT> selectListDDT(Long idTypeCredilog); 
	
	
	@Query("select scenariosFondsDDT from ScenariosFondsDDT scenariosFondsDDT "
			+ "where scenariosFondsDDT.typeCredilog.idTypeCredilog= ?1 " 
			+ "and scenariosFondsDDT.defaultTx = 1 "
			+ "and scenariosFondsDDT.dateCreationDdt <= current_date "
			+ "and scenariosFondsDDT.DateExpirationDdt > current_date")
	 ScenariosFondsDDT selectionDefaultsScenarioDDT(Long idTypeCredilog);
}
