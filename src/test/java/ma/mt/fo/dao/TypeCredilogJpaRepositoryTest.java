package ma.mt.fo.dao;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import ma.mt.fo.entity.TypeCredilog;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TypeCredilogJpaRepositoryTest {
	/** The entity manager. */
	@Autowired
	private TestEntityManager entityManager;
	/** The type credilog jpa repository. */
	@Autowired
	private TypeCredilogJpaRepository typeCredilogJpaRepository;

	/**
	 * When find all then return all type credilog.
	 */
	@Test
	public void whenFindAll_thenReturnAllTypeCredilog() {
		// given credilog 1
		TypeCredilog typeCredilog1 = new TypeCredilog();
		typeCredilog1.setEntitledTypeCredilog("Sakane");
		typeCredilog1.setFichePresentation(System.getProperty("java.io.tmpdir"));
		entityManager.persist(typeCredilog1);
		entityManager.flush();
		// then
		Integer sizeTypeCredilog = typeCredilogJpaRepository.findAll().size();
		assertThat(sizeTypeCredilog.equals(1));
	}
}
