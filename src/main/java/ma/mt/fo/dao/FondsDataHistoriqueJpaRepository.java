package ma.mt.fo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ma.mt.fo.entity.FondsDataHistorique;

/**
 * The Interface FondsDataHistoriqueJpaRepository.
 */
public interface FondsDataHistoriqueJpaRepository extends JpaRepository<FondsDataHistorique, Long> {

	@Query("select h from FondsDataHistorique h " + "where h.typeCredilog.idTypeCredilog= ?1 "
			+ "and h.datePublication <= current_date " + "and h.dateExpiration > current_date "
			+ "order by h.datePublication desc, h.dateExpiration desc")
	FondsDataHistorique findOneByTypeCredilog(Long idTypeCredilog);

}
