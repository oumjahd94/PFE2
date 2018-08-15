package ma.mt.fo.service;


import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ma.mt.fo.dao.FondsDataRapportJpaRepository;
import ma.mt.fo.entity.FondsDataRapport;
import ma.mt.fo.service.interfaces.IFondDataRapportService;
import javax.ws.rs.core.Response;   


@Service
public class FondsDataRapportService implements IFondDataRapportService {
    
	
	private static final Logger LOG = Logger.getLogger(FondsDataHistoriqueService.class);

	@Autowired
	FondsDataRapportJpaRepository fondsDataRapportJpaRepository  ;   
	
	@Override
	public Response faireTelechargerFichier(Long idDocument) {
        
      return null ; 
	}

	@Override
	public List<FondsDataRapport> listFondsDataRapport() {
		try {
			// findAll fonds Data Rapports
			return fondsDataRapportJpaRepository.findAll();
		} catch (Exception e) {
			// show error log
			LOG.info("Error listFondsDataRapport items !");
		}
		return null;
	} 
	
}
