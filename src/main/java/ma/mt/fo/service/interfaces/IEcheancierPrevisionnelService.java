package ma.mt.fo.service.interfaces;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ma.mt.fo.entity.EcheancierPrevisionnel;

/**
 * The Interface IEcheancierPrevisionnelService.
 */

public interface IEcheancierPrevisionnelService {
	/**
	 * List echeancier previsionnel.
	 *
	 * @return the list
	 */
	List<EcheancierPrevisionnel> listEcheancierPrevisionnel();

	boolean createNewEcheancierPrevisionnel(MultipartFile partP1, MultipartFile partP2, MultipartFile partS,
			Date datePublication, Date dateExpiration, Long scenariosFondsDDTs, Long scenariosFondsRa,
			Long idTypeCredilogType);   
	
}
