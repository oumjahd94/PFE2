package ma.mt.fo.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ma.mt.fo.dao.TypeCredilogJpaRepository;
import ma.mt.fo.entity.TypeCredilog;
import ma.mt.fo.service.interfaces.ITypeCredilogService;

/**
 * The Class TypeCredilogService.
 */
@Service
public class TypeCredilogService implements ITypeCredilogService {
	/** The Constant LOG. */
	private static final Logger LOG = Logger.getLogger(TypeCredilogService.class);

	/** The type credilog jpa repository. */
	@Autowired
	private TypeCredilogJpaRepository typeCredilogJpaRepository;

	/*
	 * (non-Javadoc)file
	 * 
	 * @see ma.mt.service.ITypeCredilogService#listTypeCredilog()
	 */
	@Override
	public List<TypeCredilog> listTypeCredilog() {
		try {
			return typeCredilogJpaRepository.findAll();
		} catch (Exception e) {
			LOG.info("Error listTypeCredilog items !");
		}
		return null;
	}
}
