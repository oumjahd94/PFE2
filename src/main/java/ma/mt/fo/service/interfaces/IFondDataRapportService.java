package ma.mt.fo.service.interfaces;

import java.util.List;

import javax.ws.rs.core.Response;

import ma.mt.fo.entity.FondsDataRapport;

public interface IFondDataRapportService {
     
	public Response faireTelechargerFichier(Long idDocument);

	List<FondsDataRapport> listFondsDataRapport();  
	
}    
