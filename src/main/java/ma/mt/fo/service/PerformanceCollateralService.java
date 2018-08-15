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

import ma.mt.fo.dao.FondsDataSnapshotJpaRepository;
import ma.mt.fo.dao.PerformanceCollateralJpaRepository;
import ma.mt.fo.entity.FondsDataCollateralPerformance;
import ma.mt.fo.entity.FondsDataSnapshot;
import ma.mt.fo.entity.TypeCredilog;
import ma.mt.fo.service.interfaces.IPerformanceCollateralService;
import ma.mt.fo.util.CollateralTraitement;
import ma.mt.fo.util.HelperPerformanceCollateral;
import ma.mt.fo.util.HelperSnapshotPerformanceActif;

/**
 * The Class PerformanceCollateralService.
 */
@Service
public class PerformanceCollateralService implements IPerformanceCollateralService {
	/** The Constant LOG. */
	private static final Logger LOG = Logger.getLogger(PerformanceCollateralService.class);
	/** The performance collateral service. */
	@Autowired
	private PerformanceCollateralJpaRepository performanceCollateralJpaCollateral;
	/** The fonds data snapshot jpa repository. */
	@Autowired
	private FondsDataSnapshotJpaRepository fondsDataSnapshotJpaRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see ma.mt.fo.service.interfaces.IPerformanceCollateralService#
	 * trouverFondsDataCollateral(ma.mt.fo.entity.TypeCredilog)
	 */
	@Override
	public FondsDataCollateralPerformance trouverFondsDataCollateral(TypeCredilog typeCredilog) {
		return performanceCollateralJpaCollateral.getByTypeCredilog(typeCredilog);
	}

	/**
	 * Trouver snapshot by id type credilog.
	 *
	 * @param idTypeCredilog
	 *            the id type credilog
	 * @return the helper performance collateral
	 */
	public String trouverPerfCollateralByIdTypeCredilog(int idTypeCredilog) {
		Long idTypeCredilogLong = (long) idTypeCredilog;
		return performanceCollateralJpaCollateral.findOneByTypeCredilog(idTypeCredilogLong);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ma.mt.fo.service.interfaces.IPerformanceCollateralService#
	 * trouverCollateralPerfByIdTypeCredilog(java.lang.Long)
	 */
	@Override
	public HelperPerformanceCollateral trouverCollateralPerfByIdTypeCredilog(Long idTypeCredilog) {
	
		//HelperSnapshotPerformanceActif helperSnapshotPerformanceActif = new HelperSnapshotPerformanceActif();
		

		List<String> headers = new ArrayList<String>();
		
		String idClass = "2";
		Boolean erreurCredilog = false;  
		
		String tmpDirectory = System.getProperty("java.io.tmpdir") + "/fonds/performance/";   
		
		
		// define helperPerformanceCollateral object
		HelperPerformanceCollateral helperPerformanceCollateral = null;
	 
		if (idTypeCredilog == null) {
			LOG.info("Error idTypeCredilog is null");
		} 		
		
		else { 

			// instancie new helperPerformance object
			helperPerformanceCollateral = new HelperPerformanceCollateral();
			
			// set performanceCollateral file
			String presentationPerformanceCollateral = performanceCollateralJpaCollateral
					.findOneByTypeCredilog(idTypeCredilog);  
			// performanceCollateral object not null and not empty    
			System.out.println("\n\n  O daba  \n\n" + performanceCollateralJpaCollateral); 

			if (presentationPerformanceCollateral != null && !"".equals(presentationPerformanceCollateral)) {
				// read excel file historique
				helperPerformanceCollateral
				        .setDataPerformanceActif(
				        CollateralTraitement.readExcelFile(tmpDirectory + presentationPerformanceCollateral)); 
				
				Iterator<java.util.Map.Entry<Integer, String>> iterator = CollateralTraitement
						.getHeaders(tmpDirectory + presentationPerformanceCollateral).entrySet().iterator();
				
				while (iterator.hasNext()) { 
					
					Map.Entry<String,String> entry = (Map.Entry) iterator.next(); 
					headers.add(entry.getValue());
				}  
				
				helperPerformanceCollateral.setHeaders(headers); 
			    		    
			} else {
				helperPerformanceCollateral.setErreurCredilog(true);
			}    
			
			FondsDataSnapshot ficheSnapshotDate = fondsDataSnapshotJpaRepository.selectionSnapshot(idTypeCredilog);
			System.out.println(ficheSnapshotDate.getDateExpiration()); 
			
		     Date dateArrete = ficheSnapshotDate.getDateArrete();  
			
						
			String dateDernierArrete = "";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
			
			if (dateArrete != null) {
				try { 
					
					Date dateArr;
					dateArr = sdf.parse(dateArrete.toString());
					SimpleDateFormat sdfFrench = new SimpleDateFormat("dd/MM/yyyy");
					dateDernierArrete = sdfFrench.format(dateArr);
					helperPerformanceCollateral.setIdClass(idClass);
					helperPerformanceCollateral.setDateArrete(dateDernierArrete);
					helperPerformanceCollateral.setIdTypeCredilog(idTypeCredilog);
					helperPerformanceCollateral.setErreurCredilog(erreurCredilog); 
					
				} catch (ParseException e) {
					LOG.info("error parse date");
					e.printStackTrace();
				}

			}

		}
		return helperPerformanceCollateral;
	}
}
