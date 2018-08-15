package ma.mt.fo.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ma.mt.fo.dao.FondsDataCashflowJpaRepository;
import ma.mt.fo.entity.FondsDataCashflow;
import ma.mt.fo.service.interfaces.IFondsDataCashflowService;

/**
 * The Class FondsDataCashflowService.
 */
@Service
public class FondsDataCashflowService implements IFondsDataCashflowService {
	/** The Constant LOG. */
	private static final Logger LOG = Logger.getLogger(FondsDataCashflowService.class);

	/** The fonds data cashflow jpa repository. */
	@Autowired
	private FondsDataCashflowJpaRepository fondsDataCashflowJpaRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see ma.mt.service.IFondsDataCashflowService#listFondsDataCashflows()
	 */
	@Override
	public List<FondsDataCashflow> listFondsDataCashflows() {
		try {
			return fondsDataCashflowJpaRepository.findAll();
		} catch (Exception e) {
			LOG.info("Error listFondsDataCashflow items !");
		}
		return null;
	}

}
