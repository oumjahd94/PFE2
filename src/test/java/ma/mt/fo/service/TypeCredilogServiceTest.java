package ma.mt.fo.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import ma.mt.fo.entity.TypeCredilog;
import ma.mt.fo.dao.TypeCredilogJpaRepository;
import ma.mt.fo.service.TypeCredilogService;

@RunWith(SpringRunner.class)
public class TypeCredilogServiceTest {
	/**
	 * The Class TypeCredilogServiceTestContextConfiguration.
	 */
	@TestConfiguration
	static class TypeCredilogServiceTestContextConfiguration {

		/**
		 * Type credilog service.
		 *
		 * @return the type credilog service
		 */
		@Bean
		public TypeCredilogService TypeCredilogService() {
			return new TypeCredilogService();
		}
	}

	/** The type credilog service. */
	@Autowired
	private TypeCredilogService typeCredilogService;

	/** The type credilog jpa repository. */
	@MockBean
	private TypeCredilogJpaRepository typeCredilogJpaRepository;

	/**
	 * Sets the up.
	 */
	// write test cases here
	@Before
	public void setUp() {
		TypeCredilog sakane = new TypeCredilog();
		sakane.setIdTypeCredilog(4L);
		Long idTypeCredilog = sakane.getIdTypeCredilog();
		Mockito.when(typeCredilogJpaRepository.findOne(idTypeCredilog)).thenReturn(sakane);
	}

	/**
	 * When valid id then type credilogshould be found.
	 */
	// Since the setup is done, the test case will be simpler
	@Test
	public void whenGetAllTypeCredilog() {
		Integer size = typeCredilogService.listTypeCredilog().size();
		assertThat(size.equals(1));
	}
}
