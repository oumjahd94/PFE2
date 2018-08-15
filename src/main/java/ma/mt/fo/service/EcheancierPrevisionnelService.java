package ma.mt.fo.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ma.mt.fo.dao.EcheancierPrevisionnelJpaRepository;
import ma.mt.fo.dao.ScenariosFondsDDTJpaRepository;
import ma.mt.fo.dao.ScenariosFondsRAJpaRepository;
import ma.mt.fo.dao.TypeCredilogJpaRepository;
import ma.mt.fo.entity.EcheancierPrevisionnel;
import ma.mt.fo.service.interfaces.IEcheancierPrevisionnelService;

/**
 * The Class EcheancierPrevisionnelService.
 */
@Service
public class EcheancierPrevisionnelService implements IEcheancierPrevisionnelService {
	/** The Constant LOG. */
	private static final Logger LOG = Logger.getLogger(EcheancierPrevisionnelService.class);

	/** The echeancier previsionnel jpa repository. */
	@Autowired
	private EcheancierPrevisionnelJpaRepository echeancierPrevisionnelJpaRepository;

	/** The scenarios fonds DDT jpa repository. */
	@Autowired
	private ScenariosFondsDDTJpaRepository scenariosFondsDDTJpaRepository;

	/** The scenarios fonds RA jpa repository. */
	@Autowired
	private ScenariosFondsRAJpaRepository scenariosFondsRAJpaRepository;

	/** The type credilog jpa repository. */
	@Autowired
	private TypeCredilogJpaRepository typeCredilogJpaRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ma.mt.service.IEcheancierPrevisionnelService#listEcheancierPrevisionnel()
	 */
	@Override
	public List<EcheancierPrevisionnel> listEcheancierPrevisionnel() {
		try {
			return echeancierPrevisionnelJpaRepository.findAll();
		} catch (Exception e) {
			LOG.info("Error listEcheancierPrevisionnelJpaRepository items !");
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ma.mt.service.IEcheancierPrevisionnelService#createNewEcheancierPrevisionnel(
	 * org.springframework.web.multipart.MultipartFile,
	 * org.springframework.web.multipart.MultipartFile,
	 * org.springframework.web.multipart.MultipartFile, java.util.Date,
	 * java.util.Date, java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	@Override
	public boolean createNewEcheancierPrevisionnel(MultipartFile partP1, MultipartFile partP2, MultipartFile partS,
			Date datePublication, Date dateExpiration, Long scenariosFondsDDTs, Long scenariosFondsRa,
			Long idTypeCredilogType) {
		// partP1 is null or empty
		if (partP1 == null || partP1.isEmpty()) {
			LOG.info("Null or empty partp1 file to upload");
			return false;
		}
		// partP2 is null or empty
		if (partP2 == null || partP2.isEmpty()) {
			LOG.info("Null or empty partp2 file to upload");
			return false;
		}
		// partS is null or empty
		if (partS == null || partS.isEmpty()) {
			LOG.info("Null or empty partS file to upload");
			return false;
		}
		try {
			// get file name of partP1
			String partP1Name = partP1.getOriginalFilename();
			// get file name of partP2
			String partP2Name = partP2.getOriginalFilename();
			// get file name of partS
			String partSName = partS.getOriginalFilename();
			// get tmp directory
			String tmpDirectory = System.getProperty("java.io.tmpdir");
			// create a dateString directory to upload file
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
			String dateString = formatter.format(Calendar.getInstance().getTime());

			File repertoire = new File(tmpDirectory + "/" + "EcheancierPrevisionnel");
			File repertoireDest = new File(tmpDirectory + "/" + "EcheancierPrevisionnel" + "/" + dateString);
			if (repertoire.mkdirs() && repertoireDest.mkdirs()) {
				// repertoires created
				Path rootLocation = Paths.get(tmpDirectory);
				// tmpFolder for partP1
				String tmpFolderPartP1 = dateString + File.separator + partP1Name;
				// tmpFolder for partP2
				String tmpFolderPartP2 = dateString + File.separator + partP2Name;
				// tmpFolder for partS
				String tmpFolderPartS = dateString + File.separator + partSName;
				// resolve tmpFolderPartP1
				Path pathPartP1 = rootLocation.resolve(tmpFolderPartP1);
				// resolve tmpFolderPartP2
				Path pathPartP2 = rootLocation.resolve(tmpFolderPartP2);
				// resolve tmpFolderPartS
				Path pathPartS = rootLocation.resolve(tmpFolderPartS);
				LOG.info("File partP1 upload path: %s" + pathPartP1);
				LOG.info("File partP2 upload path: %s" + pathPartP2);
				LOG.info("File partS upload path: %s" + pathPartS);
				// copy partP1 file
				Files.copy(partP1.getInputStream(), pathPartP1);
				// copy partP2 file
				Files.copy(partP2.getInputStream(), pathPartP2);
				// copy partS file
				Files.copy(partS.getInputStream(), pathPartS);
				// set Echeancier Previsionnel
				EcheancierPrevisionnel echeancierPrevisionnel = new EcheancierPrevisionnel();
				echeancierPrevisionnel.setPartP1(tmpFolderPartP1);
				echeancierPrevisionnel.setPartP2(tmpFolderPartP2);
				echeancierPrevisionnel.setPartS(tmpFolderPartS);
				echeancierPrevisionnel.setDatePublication(datePublication);
				echeancierPrevisionnel.setDateExpiration(dateExpiration);
				echeancierPrevisionnel.setScenarioFondsDdt(scenariosFondsDDTJpaRepository.findOne(scenariosFondsDDTs));
				echeancierPrevisionnel.setScenarioFondsRa(scenariosFondsRAJpaRepository.findOne(scenariosFondsRa));
				echeancierPrevisionnel.setTypeCredilog(typeCredilogJpaRepository.findOne(idTypeCredilogType));
				echeancierPrevisionnelJpaRepository.save(echeancierPrevisionnel);
				LOG.trace("TypeCredilogService : file uploaded successfully");
				return true;
			} else {
				LOG.info("TypeCredilogService : Error creating directory");
				return false;
			}
		} catch (IOException e) {
			LOG.info("TypeCredilogService : Error uploading file");
			return false;
		}
	}

}
