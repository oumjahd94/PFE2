package ma.mt.fo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ma.mt.fo.entity.FondsDataPricing;

/**
 * The Interface FondsDataPricingJpaRepository.
 */
public interface FondsDataPricingJpaRepository extends JpaRepository<FondsDataPricing, Long> {
	@Query("select "
			+ "pricing from FondsDataPricing pricing "
			+ "where pricing.typeCredilog.idTypeCredilog= ?1 "
			+ "and pricing.datePublication <= current_date "
			+ "and pricing.dateExpiration > current_date "
			+ "order by pricing.datePublication desc, pricing.dateExpiration desc")
	FondsDataPricing selectPricing(Long idTypeCredilog);
}
