package ma.mt.fo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ma.mt.fo.entity.FondsDataCollateralPerformance;
import ma.mt.fo.entity.TypeCredilog;

/**
 * The Interface PerformanceCollateral.
 */
public interface PerformanceCollateralJpaRepository extends JpaRepository<FondsDataCollateralPerformance, Long> {

	/**
	 * Gets the by type credilog.
	 *
	 * @param typeCredilog
	 *            the type credilog
	 * @return the by type credilog
	 */
	FondsDataCollateralPerformance getByTypeCredilog(TypeCredilog typeCredilog);

	/**
	 * Select performance actif fonds.
	 *
	 * @param idTypeCredilog
	 *            the id type credilog
	 * @return the string
	 */
	@Query("select f.ficheCollateral from FondsDataCollateralPerformance f "
			+ "where f.typeCredilog.idTypeCredilog = :idTypeCredilog "
			+ "and f.datePublication <= current_date and f.dateExpiration > current_date "
			+ "order by f.datePublication DESC, f.dateExpiration DESC")
	String findOneByTypeCredilog(@Param("idTypeCredilog") Long idTypeCredilog);
}
