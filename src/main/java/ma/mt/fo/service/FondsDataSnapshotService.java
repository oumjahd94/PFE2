package ma.mt.fo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ma.mt.fo.dao.FondsDataSnapshotJpaRepository;
import ma.mt.fo.dao.TypeCredilogJpaRepository;
import ma.mt.fo.entity.FondsDataSnapshot;
import ma.mt.fo.entity.TypeCredilog;
import ma.mt.fo.service.interfaces.IFondsDataSnapshotService;
import ma.mt.fo.util.CollateralTraitement;
import ma.mt.fo.util.Helper;
import ma.mt.fo.util.HelperSnapshotPerformanceActif;
import ma.mt.fo.util.HelperSnapshotStructurePassif;

/**
 * The Class FondsDataSnapshotService.
 */
@Service
public class FondsDataSnapshotService implements IFondsDataSnapshotService {
	/** The Constant LOG. */
	private static final Logger LOG = Logger.getLogger(FondsDataSnapshotService.class);

	/** The fonds data snapshot jpa repository. */
	@Autowired
	private FondsDataSnapshotJpaRepository fondsDataSnapshotJpaRepository;
	@Autowired
	private TypeCredilogJpaRepository typeCredilogJpaRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see ma.mt.service.IFondsDataSnapshotService#listFondsDataSnapshot()
	 */
	@Override
	public List<FondsDataSnapshot> listFondsDataSnapshot() {

		try {
			return fondsDataSnapshotJpaRepository.findAll();
		} catch (Exception e) {
			LOG.info("Error listFondsDataSnapshotJpaRepository items !");
		}
		return null;
	}

	@Override
	public Helper trouverSnapshotByIdTypeCredilog(Long idTypeCredilog) { 
		
		HelperSnapshotPerformanceActif helperSnapshotPerformanceActif = new HelperSnapshotPerformanceActif();
		HelperSnapshotStructurePassif helperStructurePassif = new HelperSnapshotStructurePassif();
		Helper helper = null;     
		
		Vector<Vector<String>> vecPresentation = new Vector<Vector<String>>();   
		Map<Integer, Vector<Vector<String>>> dataSnapshot = new HashMap<Integer, Vector<Vector<String>>>();  
		
		// find typeCredilog element
		TypeCredilog typeCredilogObject = typeCredilogJpaRepository.findOne(idTypeCredilog);
		
		String tmpDirectory = System.getProperty("java.io.tmpdir") + "/fonds/snapshot/";  
		
		if (idTypeCredilog == null) {
			LOG.info("Error idTypeCredilog is null");
		} 
		
		else { 
			
			String fichePresentationTypeCredilog = "";
			// select fiche presentation file   
			
			if (typeCredilogJpaRepository.getOne(idTypeCredilog) != null) {
				fichePresentationTypeCredilog = typeCredilogJpaRepository.getOne(idTypeCredilog).getFichePresentation();
			}    
			
			// select fiche presentation snapshot file
			FondsDataSnapshot fondsDataSnapshot = fondsDataSnapshotJpaRepository.getByTypeCredilog(typeCredilogObject); 
			
			if (fondsDataSnapshot != null) { 
				
				String ficheSnapshot = fondsDataSnapshot.getFicheSnapshot();
				
				if (fichePresentationTypeCredilog != null && !"".equals(fichePresentationTypeCredilog)) {
					vecPresentation = CollateralTraitement
							.readPresentationFonds(tmpDirectory.concat(fichePresentationTypeCredilog));
				}
				
				if (ficheSnapshot != null && !"".equals(ficheSnapshot)) {  
					
					helper = new Helper();
					dataSnapshot = CollateralTraitement.readExcelFileSnapshot(tmpDirectory.concat(ficheSnapshot));
					// feuille data line 1
					helperSnapshotPerformanceActif.setDataFeuille1Ligne1(
							dataSnapshot.get(0).get(0).toArray(new String[dataSnapshot.get(0).get(0).size()]));
					// feuille data line 2
					helperSnapshotPerformanceActif.setDataFeuille1Ligne2(
							dataSnapshot.get(0).get(1).toArray(new String[dataSnapshot.get(0).get(1).size()]));
					// feuille data line 3
					helperSnapshotPerformanceActif.setDataFeuille1Ligne3(
							dataSnapshot.get(0).get(2).toArray(new String[dataSnapshot.get(0).get(2).size()]));
					List<String[]> dataVecRendement = new ArrayList<>();
					
					dataVecRendement.add(helperSnapshotPerformanceActif.getDataFeuille1Ligne1());
					dataVecRendement.add(helperSnapshotPerformanceActif.getDataFeuille1Ligne2());
					dataVecRendement.add(helperSnapshotPerformanceActif.getDataFeuille1Ligne3()); 
					
					helper.setVecRendement(dataVecRendement);
					// helper.setVecPassif(dataSnapshot.get(1));
					helper.setVecCompteHeaders(dataSnapshot.get(2).get(0));
					helper.setVecCompteData(dataSnapshot.get(2).get(1));
					Vector<Vector<String>> passifs = dataSnapshot.get(1);
					passifs.remove(0);
					helper.setVecPassif(dataSnapshot.get(1)); 
					
					// helper.setVecCompte(dataSnapshot.get(2));
					// helper.setHeaders(CollateralTraitement.getHeaders(tmpDirectory.concat(ficheSnapshot)));
					// helper.setDataSnapshot(dataSnapshot);
					helper.setVecPresentation(vecPresentation);
					// helper.setMapCommentaires(CollateralTraitement.mapCommentaires);
				}
			}
		}
		return helper;
	}

}
