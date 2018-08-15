package ma.mt.fo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ma.mt.fo.entity.FondsDataSnapshot;
import ma.mt.fo.entity.TypeCredilog;

/**
 * The Interface FondsDataSnapshotJpaRepository.
 */
public interface FondsDataSnapshotJpaRepository extends JpaRepository<FondsDataSnapshot, Long> {
	FondsDataSnapshot getByTypeCredilog(TypeCredilog typeCredilog);

	@Query("select s from FondsDataSnapshot " + "s where s.typeCredilog.idTypeCredilog= ?1 "
			+ "and s.datePublication <= current_date " + "and s.dateExpiration > current_date "
			+ "order by s.datePublication desc, s.dateExpiration desc")
	FondsDataSnapshot selectionSnapshot(Long idTypeCredilog);
}
