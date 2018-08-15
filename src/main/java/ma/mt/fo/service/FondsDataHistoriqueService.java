package ma.mt.fo.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ma.mt.fo.dao.FondsDataHistoriqueJpaRepository;
import ma.mt.fo.dao.FondsDataSnapshotJpaRepository;
import ma.mt.fo.entity.FondsDataHistorique;
import ma.mt.fo.entity.FondsDataSnapshot;
import ma.mt.fo.service.interfaces.IFondsDataHistoriqueService;
import ma.mt.fo.util.CollateralTraitement;
import ma.mt.fo.util.HelperHistorique;

/**
 * The Class FondsDataHistoriqueService.
 */
@Service
public class FondsDataHistoriqueService implements IFondsDataHistoriqueService {
	/** The Constant LOG. */
	private static final Logger LOG = Logger.getLogger(FondsDataHistoriqueService.class);

	/** The fonds data historique jpa repository. */
	@Autowired
	private FondsDataHistoriqueJpaRepository fondsDataHistoriqueJpaRepository;

	/** The fonds data snapshot jpa repository. */
	@Autowired
	private FondsDataSnapshotJpaRepository fondsDataSnapshotJpaRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see ma.mt.service.IFondsDataHistoriqueService#listFondsDataHistorique()
	 */
	@Override
	public List<FondsDataHistorique> listFondsDataHistorique() {
		try {
			// findAll fonds Data Historique
			return fondsDataHistoriqueJpaRepository.findAll();
		} catch (Exception e) {
			// show error log
			LOG.info("Error listFondsDataHistorique items !");
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ma.mt.fo.service.interfaces.IFondsDataHistoriqueService#
	 * trouverHistoriqueFondsByIdTypeCredilog(java.lang.Long)
	 */
	@Override
	public HelperHistorique trouverHistoriqueFondsByIdTypeCredilog(Long idTypeCredilog) {
		List<String> headers = new ArrayList<>();
		// instanciate new HelpHistorique
		HelperHistorique helpHistorique = new HelperHistorique();
		// find TypeCredilog by id
		FondsDataHistorique historiqueFile = fondsDataHistoriqueJpaRepository.findOneByTypeCredilog(idTypeCredilog);
		// historiqueFile not null
		if (historiqueFile.getFicheHistorique() != null && !"".equals(historiqueFile.getFicheHistorique())) {
			String tmpDirectory = System.getProperty("java.io.tmpdir") + "/fonds/historique/";
			helpHistorique.setDataHistoriqueFonds(CollateralTraitement
					.readExcelFileHistorique(tmpDirectory.concat(historiqueFile.getFicheHistorique()), false));
			helpHistorique.setVecObligationA(helpHistorique.getDataHistoriqueFonds().get(0));
			helpHistorique.setVecObligationB(helpHistorique.getDataHistoriqueFonds().get(1));
			helpHistorique.setVecObligationS(helpHistorique.getDataHistoriqueFonds().get(2));
			//
			Iterator<java.util.Map.Entry<Integer, String>> iterator = CollateralTraitement
					.getHeaders(tmpDirectory.concat(historiqueFile.getFicheHistorique())).entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, String> entry = (Map.Entry) iterator.next();
				headers.add(entry.getValue());
			}
			helpHistorique.setHeaders(headers);
			//
		} else {
			helpHistorique.setErreurCredilog(true);
		}
		// selection snapshot
		FondsDataSnapshot fondsDataSnapshot = fondsDataSnapshotJpaRepository.selectionSnapshot(idTypeCredilog);
		Date dateArrete = null;
		// select date arrete
		if(fondsDataSnapshot == null) {
			// Show log data info
		}else {
			dateArrete = fondsDataSnapshot.getDateArrete();
		}   
		
		// init last date arrete
		String dateDernierArrete = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (dateArrete != null) {
			try {
				// declare dateArr
				Date dateArr;
				// parse string date to date
				dateArr = sdf.parse(dateArrete.toString());
				// format date
				SimpleDateFormat sdfFrench = new SimpleDateFormat("dd/MM/yyyy");
				dateDernierArrete = sdfFrench.format(dateArr);
				helpHistorique.setIdClass("3");
				// set dateDernierArrete
				helpHistorique.setDateArrete(dateDernierArrete);
			} catch (ParseException e) {
				LOG.info("error date parsing");
				e.printStackTrace();
			}

		}
		return helpHistorique;
	}

}
