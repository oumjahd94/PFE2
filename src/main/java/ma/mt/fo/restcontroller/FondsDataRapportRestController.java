package ma.mt.fo.restcontroller;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.StringTokenizer;
import javax.ws.rs.Produces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.ServletOutputStream;
import ma.mt.fo.dao.FondsDataRapportJpaRepository;
import ma.mt.fo.entity.FondsDataHistorique;
import ma.mt.fo.entity.FondsDataRapport;
import ma.mt.fo.service.FondsDataRapportService;
import ma.mt.fo.service.interfaces.IFondDataRapportService;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping(value = "/api/fondsDataRapport")
public class FondsDataRapportRestController {
   
	@Autowired
	FondsDataRapportJpaRepository fondsDataRapportJpaRepository  ;   
		
    @Autowired
    IFondDataRapportService   iFondDataRapportService ; 
	
	@CrossOrigin
	@RequestMapping(value = "/listeRapports", method = RequestMethod.GET)
	public List<FondsDataRapport> listFondsDataRapport() {
		return iFondDataRapportService.listFondsDataRapport();  		
	}   
	
	@CrossOrigin
	@Produces("application/pdf")
	@RequestMapping(value = "/downloadPDF/{idDocument}", method = RequestMethod.GET)
	public ResponseEntity<byte[]>  DownloadRapport(@PathVariable(name = "idDocument")Long idDocument){
			
	    
		String  rapport   = fondsDataRapportJpaRepository.findOne(idDocument).getFicheRapport();  
		String chemin = System.getProperty("java.io.tmpdir") + "/documentation/rapport/"+ rapport;  
        		
		Path path = Paths.get(chemin);
	    byte[] pdfContents = null;
	    try {
	        pdfContents = Files.readAllBytes(path);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(MediaType.parseMediaType("application/download"));
		headers.add("content-disposition", "attachment; filename=" + rapport);
		return new ResponseEntity<byte[]>(pdfContents, headers, HttpStatus.OK);
	} 
	
	@CrossOrigin
	@Produces("application/pdf")
	@RequestMapping(value = "/downloadFile/{idDocument}", method = RequestMethod.GET)
	public ServletOutputStream TelechargerFichier(@PathVariable(name = "idDocument") Long idDocument)
			      throws IOException, Exception
			   {
			   
			String  document   = fondsDataRapportJpaRepository.findOne(idDocument).getFicheRapport();  
			String cheminFichier = System.getProperty("java.io.tmpdir") + "/documentation/rapport/"+ document;  
		 
		 try
			 /*     */     {
			 /* 491 */       String nomFichier = "";
			 /*     */       HttpHeaders headers = new HttpHeaders();

			 /* 493 */       if (document != null) {
			 /* 494 */         StringTokenizer st = new StringTokenizer(document, "/");
			 /* 495 */         while (st.hasMoreTokens()) {
			 /* 496 */           int i = st.countTokens();
			 /* 497 */           switch (i) {
			 /*     */           case 1: 
			 /* 499 */             nomFichier = st.nextToken();
			 /* 500 */             break;
			 /*     */           default: 
			 /* 502 */             st.nextToken();
			 /*     */           }
			 /*     */         }
			 /*     */       }
			 /* 506 */       if ((document != null) && (document.compareTo("") != 0)) {
			 /* 507 */         File fichier = new File(cheminFichier);
			 /*     */         
			 /* 509 */         if (fichier.exists())
			 /*     */         {
			 /* 511 */           headers.setContentType(MediaType.parseMediaType(("application/download")));
			 /*     */           
			 /*     */             
			 /* 514 */           if (headers.get("User-Agent").indexOf("MSIE 5.5") < 0) {
			 /* 515 */             headers.add("Content-Disposition", "attachment; filename=\"" + nomFichier + "\"");
			 /*     */           } else {
			 /* 517 */             headers.add("Content-Disposition", "attachment;");
			 /* 518 */             headers.add("Content-Disposition", "filename=\"" + nomFichier + "\"");
			 /*     */           }
			 /*     */           
			 /*     */           Process p = Runtime.getRuntime().exec("MyApp");

			 /* 522 */           ServletOutputStream outs  = (ServletOutputStream) p.getOutputStream(); 
			 /*     */           
			 /* 524 */           InputStream in = new FileInputStream(fichier);
			 /*     */           
			 /* 526 */           int bit = 256;
			 /* 527 */           int i = 0;
			 /*     */           try {
			 /* 529 */             while (bit >= 0) {
			 /* 530 */               bit = in.read();
			 /* 531 */               if ((bit >= 0) && (bit <= 128)) {
			 /* 532 */                 outs.write(bit);
			 /*     */               } else
			 /* 534 */                 outs.write(bit);
			 /*     */             }
			 /*     */           } catch (IOException ioe) {
			 /* 537 */             ioe.printStackTrace(System.out);
			 /*     */           }
			 /* 539 */           outs.flush();
			 /* 540 */           outs.close();
			 /* 541 */           in.close();
			 return outs ; 
			 /*     */         } else {
			 /* 543 */           throw new Exception("Le fichier demandé n'existe pas");
			 /*     */         }
			 /*     */       }
			 /*     */     }
			 /*     */     catch (Exception e) {
			 /* 548 */       throw new Exception("Téléchargement de fichier non réussi : " + e.toString());
			 /*     */     }
		return null; 
		 
		                   
			 /*     */   }
	

}
