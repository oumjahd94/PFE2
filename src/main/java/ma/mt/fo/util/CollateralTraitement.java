/**
 * 
 */
package ma.mt.fo.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author anass
 * 
 */
public class CollateralTraitement {

	public static String typeChampsInt = "";

	public static Map<String, String> mapCommentaires = new HashMap<String, String>();

	public static Map<Integer, Vector<Vector<String>>> readExcelFileHistorique(String nameFile, boolean isGraphe) {Map<Integer, Vector<Vector<String>>> mapFeuillesValues = new HashMap<Integer, Vector<Vector<String>>>();
	
	try { 
		
	    FileInputStream inputStream = new FileInputStream(nameFile);
	    POIFSFileSystem myExcelFile = new POIFSFileSystem(inputStream);
	    HSSFWorkbook workBook = new HSSFWorkbook(myExcelFile);

	    // Récupérer le nombre de feuilles
	    int nombreFeuillesExcel = workBook.getNumberOfSheets(); 
	    
	    for (int i = 0; i < nombreFeuillesExcel; i++) { 
	    	
		Vector<Vector<String>> cellVectorHolder = new Vector<Vector<String>>();    
		
		// Récupératoin de la feuille
		HSSFSheet feuille = workBook.getSheetAt(i);    
		
		// Parcourir les lignes de la feuille
		for (int ligne = feuille.getFirstRowNum() + 1; ligne <= feuille.getLastRowNum(); ligne++) { 
			
		    Row row = feuille.getRow(ligne);
		    System.out.println("ligne ==> " + ligne);
		    Vector<String> cellStoreVector = new Vector<String>(); 
		    
		    if (row != null) {
			for (int colonne = row.getFirstCellNum(); colonne <= row.getLastCellNum(); colonne++) {
			    System.out.println("colonne ==> " + colonne);
			    Cell cellule = row.getCell(colonne);
			    String valueCellule = "";
			    if (cellule != null) {
				Comment comment = cellule.getCellComment(); 
				
				if (comment != null) {
				    RichTextString commentaire = comment.getString();
				    mapCommentaires.put(cellule.getRichStringCellValue().getString(), commentaire.getString());
				}

				int type = cellule.getCellType();
				if (type == HSSFCell.CELL_TYPE_FORMULA) {
				    type = cellule.getCachedFormulaResultType();
				}

				switch (type) { 
				
				case HSSFCell.CELL_TYPE_STRING:
				    valueCellule = cellule.getRichStringCellValue().toString();
				    break;
				    
				case HSSFCell.CELL_TYPE_NUMERIC:
				    double d = cellule.getNumericCellValue();
				    CellStyle style = cellule.getCellStyle();
				    String dataFormat = style.getDataFormatString();

				    if (dataFormat.equals("DD\\-MMM\\-YY") || dataFormat.equals("d-mmm-yy")) {
					// Il s'agit d'une date
					GregorianCalendar cal = new GregorianCalendar(Locale.FRANCE);
					cal.setTime(HSSFDateUtil.getJavaDate(d));
					SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE);
					String sdate = sdf.format(HSSFDateUtil.getJavaDate(d));
					sdate = sdate.replaceAll("\\.", "");
					valueCellule = sdate;
				    } 
				    
				    else if (dataFormat.equals("0.00%") || dataFormat.contains("%")) { 
				    	
					if (colonne == 2 || colonne == 3 || colonne == 4 || colonne == 7) {
					    NumberFormat decimalFormat = new DecimalFormat("#.#");
					    d *= 100.0;
					    valueCellule = decimalFormat.format(d);
					} else {
					    NumberFormat decimalFormat = new DecimalFormat("#.#");
					    d *= 100.0;
					    valueCellule = decimalFormat.format(d);
					}
					// Il s'agit d'un pourcentage

					// .concat(" %");
				    } else {
					// Il s'agit d'un nombre double
					// d = Math.round(d);
					// d = PricingTraitement.arrondi(d, 2);
					if (isGraphe) {
					    NumberFormat decimalFormat = new DecimalFormat("############.#");
					    valueCellule = decimalFormat.format(d); 
					    
					} else {
					    if (dataFormat.contains("#,##0.00") || dataFormat.equals("0.00")) {
						NumberFormat decimalFormat = new DecimalFormat("###,###,###.#");
						valueCellule = decimalFormat.format(d);
					    } else {
						NumberFormat decimalFormat = new DecimalFormat("###,###,###");
						valueCellule = decimalFormat.format(d);
					    }
					}
				    }
				    break;
				case HSSFCell.CELL_TYPE_BLANK:
				    valueCellule = cellule.getStringCellValue().concat(" -- ");
				    break;

				default:
				    break;
				}
				// valueCellule = getValueCellule(cellule);
				cellStoreVector.add(valueCellule);
			    }
			}
			if (cellStoreVector != null && !cellStoreVector.isEmpty())
			    cellVectorHolder.addElement(cellStoreVector);
		    }
		}
		// Insertion du vecteur dans le MAP (clé : Nom de la feuille ,
		// valeur : vecteur des lignes)
		mapFeuillesValues.put(i, cellVectorHolder);
	    }
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return mapFeuillesValues;
	}

	public static Vector<Vector<String>> readExcelFilePerformanceActif(String nameFile) {     
		
		Vector<Vector<String>> cellVectorHolder = new Vector<Vector<String>>();   
		
		try {  
			
			FileInputStream inputStream = new FileInputStream(nameFile);
			POIFSFileSystem myExcelFile = new POIFSFileSystem(inputStream);
			XSSFWorkbook workBook = new XSSFWorkbook(inputStream);

			// Récupératoin de la feuille
			XSSFSheet feuille = workBook.getSheetAt(0); 
			// Parcourir les lignes de la feuille
			for (int ligne = feuille.getFirstRowNum() + 1; ligne <= feuille.getLastRowNum(); ligne++) {
				Row row = feuille.getRow(ligne);
				Vector<String> cellStoreVector = new Vector<String>();
				if (row != null) {
					for (int colonne = row.getFirstCellNum(); colonne <= row.getLastCellNum(); colonne++) {
						Cell cellule = row.getCell(colonne);
						String valueCellule = "";
						if (cellule != null) {
							Comment comment = cellule.getCellComment();
							if (comment != null) {
								RichTextString commentaire = comment.getString();
								mapCommentaires.put(cellule.getRichStringCellValue().getString(),
										commentaire.getString());
							}

							int type = cellule.getCellType();
							if (type == XSSFCell.CELL_TYPE_FORMULA) {
								type = cellule.getCachedFormulaResultType();
							}

							switch (type) {
							case XSSFCell.CELL_TYPE_STRING:
								valueCellule = cellule.getRichStringCellValue().toString();
								break;
							case XSSFCell.CELL_TYPE_NUMERIC:
								double d = cellule.getNumericCellValue();
								CellStyle style = cellule.getCellStyle();
								String dataFormat = style.getDataFormatString();

								if (dataFormat.equals("DD\\-MMM\\-YY") || dataFormat.equals("MMM\\-YY")
										|| dataFormat.equals("mmm-yy")) {
									// Il s'agit d'une date
									GregorianCalendar cal = new GregorianCalendar(Locale.FRANCE);
									cal.setTime(DateUtil.getJavaDate(d));
									SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE);
									String sdate = sdf.format(DateUtil.getJavaDate(d));
									sdate = sdate.replaceAll("\\.", "");
									valueCellule = sdate;
								} else if (dataFormat.equals("0.00%") || dataFormat.contains("%")) {
									// Il s'agit d'un pourcentage
									NumberFormat decimalFormat = new DecimalFormat("#.##");
									d *= 100.0;
									valueCellule = decimalFormat.format(d);
									// .concat(" %");
								} else {
									// Il s'agit d'un nombre double
									NumberFormat decimalFormat = new DecimalFormat("############.####");
									d = Math.round(d);
									valueCellule = decimalFormat.format(d);
								}
								break;
							case XSSFCell.CELL_TYPE_BLANK:
								valueCellule = cellule.getStringCellValue().concat(" -- ");
								break;

							default:
								break;
							}
							// valueCellule = getValueCellule(cellule);
							cellStoreVector.add(valueCellule);
						}
					}
					if (cellStoreVector != null && !cellStoreVector.isEmpty())
						cellVectorHolder.addElement(cellStoreVector);
				}
			}
			// Insertion du vecteur dans le MAP (clé : Nom de la feuille ,
			// valeur : vecteur des lignes)
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cellVectorHolder;
	}

	public static Vector<Vector<String>> readExcelFilePricer(String nameFile) {
		Vector<Vector<String>> cellVectorHolder = new Vector<Vector<String>>();
		try {
			FileInputStream inputStream = new FileInputStream(nameFile);
			POIFSFileSystem myExcelFile = new POIFSFileSystem(inputStream);

			XSSFWorkbook workBook = new XSSFWorkbook(inputStream);

			// Récupératoin de la feuille 0
			XSSFSheet feuille = workBook.getSheetAt(0);
			// Parcourir les lignes de la feuille
			for (int ligne = 6; ligne < feuille.getLastRowNum(); ligne++) {
				Row row = feuille.getRow(ligne);
				Vector<String> cellStoreVector = new Vector<String>();
				if (row != null) {
					for (int colonne = 0; colonne <= row.getLastCellNum(); colonne++) {
						Cell cellule = row.getCell(colonne);
						String valueCellule = "";
						if (cellule != null) {
							CellStyle style = cellule.getCellStyle();
							short indexColor = style.getFillForegroundColor();
							if (true) {
								int type = cellule.getCellType();
								if (type == XSSFCell.CELL_TYPE_FORMULA) {
									type = cellule.getCachedFormulaResultType();
								}
								switch (type) {
								case XSSFCell.CELL_TYPE_STRING:
									valueCellule = cellule.getRichStringCellValue().toString();
									break;
								case XSSFCell.CELL_TYPE_NUMERIC:
									double d = cellule.getNumericCellValue();
									String dataFormat = style.getDataFormatString();

									if (dataFormat.equals("DD\\-MMM\\-YY")) {
										// Il s'agit d'une date
										GregorianCalendar cal = new GregorianCalendar(Locale.FRANCE);
										cal.setTime(DateUtil.getJavaDate(d));
										SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE);
										String sdate = sdf.format(DateUtil.getJavaDate(d));
										sdate = sdate.replaceAll("\\.", "");
										valueCellule = sdate;
									} else if (dataFormat.equals("0.00%") || dataFormat.contains("%")) {
										// Il s'agit d'un pourcentage
										NumberFormat decimalFormat = new DecimalFormat("#.####");
										d *= 100.0;
										valueCellule = decimalFormat.format(d).concat(" %");
									} else {
										// Il s'agit d'un nombre double
										NumberFormat decimalFormat = new DecimalFormat("#.####");
										valueCellule = decimalFormat.format(d);
									}
									break;
								case XSSFCell.CELL_TYPE_BLANK:
									valueCellule = cellule.getStringCellValue().concat(" -- ");
									break;

								default:
									break;
								}
								cellStoreVector.add(valueCellule);
							}

						}
					}
					if (cellStoreVector != null && !cellStoreVector.isEmpty())
						cellVectorHolder.addElement(cellStoreVector);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cellVectorHolder;
	}

	// read presentation file
	public static Vector<Vector<String>> readPresentationFonds(String nameFile) {
		Map<String, String> mapChampsValeur = new HashMap<String, String>();

		Vector<Vector<String>> vecDonnees = new Vector<Vector<String>>();

		FileInputStream inputStream;  
				
		try {
		    inputStream = new FileInputStream(nameFile);

		    POIFSFileSystem myExcelFile = new POIFSFileSystem(inputStream);

		    HSSFWorkbook workBook = new HSSFWorkbook(myExcelFile);

		    // Récupératoin de la premiere feuille
		    HSSFSheet feuille = workBook.getSheetAt(0);
		    // Parcourir les lignes de la feuille
		    for (int ligne = feuille.getFirstRowNum(); ligne <= feuille.getLastRowNum(); ligne++) {
			Vector<String> vecLignes = new Vector<String>();
			Row row = feuille.getRow(ligne);
			if (row != null) {
			    for (int colonne = 0; colonne < 4; colonne++) {
				Cell cellule = row.getCell(colonne);
				String valeur = getValueCellule(cellule);
				vecLignes.add(valeur);
			    }
			    vecDonnees.add(vecLignes);
			}
		    }
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		}
		return vecDonnees;
	    }

	public static String getValueCellule(Cell cellule) {
		String valueCellule = "";
		if (cellule != null) {
			Comment comment = cellule.getCellComment();
			if (comment != null) {
				RichTextString commentaire = comment.getString();
				mapCommentaires.put(cellule.getRichStringCellValue().getString(), commentaire.getString());
			}

			int type = cellule.getCellType();
			if (type == XSSFCell.CELL_TYPE_FORMULA) {
				type = cellule.getCachedFormulaResultType();
			}

			switch (type) {
			case XSSFCell.CELL_TYPE_STRING:
				valueCellule = cellule.getRichStringCellValue().toString();
				break;
			case XSSFCell.CELL_TYPE_NUMERIC:
				double d = cellule.getNumericCellValue();
				CellStyle style = cellule.getCellStyle();
				String dataFormat = style.getDataFormatString();

				if (dataFormat.equals("DD\\-MMM\\-YY")) {
					// Il s'agit d'une date
					GregorianCalendar cal = new GregorianCalendar(Locale.FRANCE);
					cal.setTime(DateUtil.getJavaDate(d));
					SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.FRANCE);
					String sdate = sdf.format(DateUtil.getJavaDate(d));
					sdate = sdate.replaceAll("\\.", "");
					valueCellule = sdate;
				} else if (dataFormat.equals("0.00%") || dataFormat.contains("%")) {
					// Il s'agit d'un pourcentage
					NumberFormat decimalFormat = new DecimalFormat("#.##");
					d *= 100.0;
					valueCellule = decimalFormat.format(d);
					// .concat(" %");
				} else {
					// Il s'agit d'un nombre double
					NumberFormat decimalFormat = new DecimalFormat("###,###,###,###.####");
					d = Math.round(d);
					valueCellule = decimalFormat.format(d);
				}
				break;
			case XSSFCell.CELL_TYPE_BLANK:
				valueCellule = cellule.getStringCellValue().concat(" -- ");
				break;

			default:
				break;
			}
		}
		return valueCellule;
	}

	public static Map<Integer, Vector<Vector<String>>> readExcelFileSnapshot(String nameFile) {  
		
		Map<Integer, Vector<Vector<String>>> mapFeuillesValues = new HashMap<Integer, Vector<Vector<String>>>();

		try {
		    FileInputStream inputStream = new FileInputStream(nameFile);
		    POIFSFileSystem myExcelFile = new POIFSFileSystem(inputStream);

		    HSSFWorkbook workBook = new HSSFWorkbook(myExcelFile);

		    // Récupérer le nombre de feuilles
		    int nombreFeuillesExcel = workBook.getNumberOfSheets();
		    for (int i = 0; i < nombreFeuillesExcel; i++) {
			Vector<Vector<String>> cellVectorHolder = new Vector<Vector<String>>();
			// Récupératoin de la feuille
			HSSFSheet feuille = workBook.getSheetAt(i);
			// Parcourir les lignes de la feuille
			for (int ligne = feuille.getFirstRowNum(); ligne <= feuille.getLastRowNum(); ligne++) {
			    Row row = feuille.getRow(ligne);
			    Vector<String> cellStoreVector = new Vector<String>();
			    if (row != null) {
				for (int colonne = row.getFirstCellNum(); colonne <= row.getLastCellNum(); colonne++) {
				    Cell cellule = row.getCell(colonne);
				    String valueCellule = "";
				    if (cellule != null) {
					Comment comment = cellule.getCellComment();
					if (comment != null) {
					    RichTextString commentaire = comment.getString();
					    mapCommentaires.put(cellule.getRichStringCellValue().getString(), commentaire.getString());
					}

					int type = cellule.getCellType();
					if (type == HSSFCell.CELL_TYPE_FORMULA) {
					    type = cellule.getCachedFormulaResultType();
					}

					switch (type) {
					case HSSFCell.CELL_TYPE_STRING:
					    valueCellule = cellule.getRichStringCellValue().toString();
					    break;
					case HSSFCell.CELL_TYPE_NUMERIC:
					    double d = cellule.getNumericCellValue();
					    CellStyle style = cellule.getCellStyle();
					    String dataFormat = style.getDataFormatString();

					    if (dataFormat.equals("DD\\-MMM\\-YY")||dataFormat.equals("DD/MM/YYYY")||dataFormat.equals("MM-YY")||dataFormat.equals("DD-MM-YY")) {
						// Il s'agit d'une date
						GregorianCalendar cal = new GregorianCalendar(Locale.FRANCE);
						cal.setTime(HSSFDateUtil.getJavaDate(d));
						SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.FRANCE);
						String sdate = sdf.format(HSSFDateUtil.getJavaDate(d));
						sdate = sdate.replaceAll("\\.", "");
						valueCellule = sdate;
					    } else if (dataFormat.equals("0.00%") || dataFormat.contains("%")) {
						// Il s'agit d'un pourcentage
						NumberFormat decimalFormat = new DecimalFormat("#.#");
						d *= 100.0;
						valueCellule = decimalFormat.format(d);
					    } else if (dataFormat.equals("0.00") || dataFormat.contains("#,##0.00") || dataFormat.contains("#,##0")) {
						if (colonne == 3) {
						    NumberFormat decimalFormat = new DecimalFormat("###,###,###,###");
						    valueCellule = decimalFormat.format(d);
						} else {
						    NumberFormat decimalFormat = new DecimalFormat("###,###,###,###");
						    valueCellule = decimalFormat.format(d);
						}
					    } else {
						// Il s'agit d'un nombre double
						NumberFormat decimalFormat = new DecimalFormat("###,###,###,###");
						NumberFormat decimalFormatCoupon = new DecimalFormat("###,###,###,###.###");
						if (colonne == 6) {
						    valueCellule = decimalFormatCoupon.format(d);
						} else {
						    valueCellule = decimalFormat.format(d);
						}
					    }
					    break;
					case HSSFCell.CELL_TYPE_BLANK:
					    valueCellule = cellule.getStringCellValue().concat(" -- ");
					    break;

					default:
					    break;
					}
					cellStoreVector.add(valueCellule);
				    }
				}
				if (cellStoreVector != null && !cellStoreVector.isEmpty())
				    cellVectorHolder.addElement(cellStoreVector);
			    }
			}

			// Insertion du vecteur dans le MAP (clé : Nom de la feuille ,
			// valeur : vecteur des lignes)
			mapFeuillesValues.put(i, cellVectorHolder);
		    }
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		}
		return mapFeuillesValues;
	}

	
	
    public static Map<Integer, Vector<Vector<String>>> readExcelFileCashflow(String nameFile) {
	Map<Integer, Vector<Vector<String>>> mapFeuillesValues = new HashMap<Integer, Vector<Vector<String>>>();
	// Map<String, String> mapCommentaires = new HashMap<String, String>();

	try {
	    FileInputStream inputStream = new FileInputStream(nameFile);
	    POIFSFileSystem myExcelFile = new POIFSFileSystem(inputStream);

	    HSSFWorkbook workBook = new HSSFWorkbook(myExcelFile);

	    // Récupérer le nombre de feuilles
	    int nombreFeuillesExcel = workBook.getNumberOfSheets();
	    for (int i = 0; i < nombreFeuillesExcel; i++) {
		Vector<Vector<String>> cellVectorHolder = new Vector<Vector<String>>();
		// Récupératoin de la feuille
		HSSFSheet feuille = workBook.getSheetAt(i);
		// Parcourir les lignes de la feuille
		for (int ligne = feuille.getFirstRowNum(); ligne <= feuille.getLastRowNum(); ligne++) {
		    Row row = feuille.getRow(ligne);
		    Vector<String> cellStoreVector = new Vector<String>();
		    if (row != null) {
			for (int colonne = row.getFirstCellNum(); colonne <= row.getLastCellNum(); colonne++) {
			  //  System.out.println("colonne ==> " + colonne);
			    Cell cellule = row.getCell(colonne);
			    String valueCellule = "";
			    if (cellule != null) {
				Comment comment = cellule.getCellComment();
				if (comment != null) {
				    RichTextString commentaire = comment.getString();
				    mapCommentaires.put(cellule.getRichStringCellValue().getString(), commentaire.getString());
				}

				int type = cellule.getCellType();
				if (type == HSSFCell.CELL_TYPE_FORMULA) {
				    type = cellule.getCachedFormulaResultType();
				}

				switch (type) {
				case HSSFCell.CELL_TYPE_STRING:
				    valueCellule = cellule.getRichStringCellValue().toString();
				    break;
				case HSSFCell.CELL_TYPE_NUMERIC:
				    double d = cellule.getNumericCellValue();
				    CellStyle style = cellule.getCellStyle();
				    String dataFormat = style.getDataFormatString();

				    if (dataFormat.equals("DD\\-MMM\\-YY")) {
					// Il s'agit d'une date
					GregorianCalendar cal = new GregorianCalendar(Locale.FRANCE);
					cal.setTime(HSSFDateUtil.getJavaDate(d));
					SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.FRANCE);
					String sdate = sdf.format(HSSFDateUtil.getJavaDate(d));
					sdate = sdate.replaceAll("\\.", "");
					valueCellule = sdate;
				    } else if (dataFormat.equals("0.00%") || dataFormat.contains("%")) {
					// Il s'agit d'un pourcentage
					NumberFormat decimalFormat = new DecimalFormat("#.#");
					d *= 100.0;
					valueCellule = decimalFormat.format(d);
					// .concat(" %");
				    } else if (dataFormat.equals("0.00")) {
					NumberFormat decimalFormat = new DecimalFormat("#.#");
					valueCellule = decimalFormat.format(d);
				    } else {

					// Il s'agit d'un nombre double
					NumberFormat decimalFormat = new DecimalFormat("###,###,###,###");

					d = Math.round(d);
					valueCellule = decimalFormat.format(d);
				    }
				    break;
				case HSSFCell.CELL_TYPE_BLANK:
				    valueCellule = cellule.getStringCellValue().concat(" -- ");
				    break;

				default:
				    break;
				}
				cellStoreVector.add(valueCellule);
			    }
			}
			if (cellStoreVector != null && !cellStoreVector.isEmpty())
			    cellVectorHolder.addElement(cellStoreVector);
		    }

		}

		// Insertion du vecteur dans le MAP (clé : Nom de la feuille ,
		// valeur : vecteur des lignes)
		mapFeuillesValues.put(i, cellVectorHolder);
	    }
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return mapFeuillesValues;
    }

	public static void getCelluleValue(XSSFCell cellule) {
		CellStyle style = cellule.getCellStyle();
		int dataF = style.getDataFormat();
		String dataFormat = style.getDataFormatString();
		int type = cellule.getCellType();
		if (type == XSSFCell.CELL_TYPE_FORMULA) {
			type = cellule.getCachedFormulaResultType();
		}
		switch (type) {
		case XSSFCell.CELL_TYPE_STRING:
			// System.out.println("value => " + cellule.getRichStringCellValue());
			break;
		case XSSFCell.CELL_TYPE_NUMERIC:
			// System.out.println("value => " + cellule.getNumericCellValue());
			break;
		case XSSFCell.CELL_TYPE_BLANK:
			System.out.println("value => " + cellule.getStringCellValue());

			break;

		default:
			break;
		}
	}

	public static Vector<Vector<String>> readExcelFile(String nameFile) {  
		
		Vector<Vector<String>> cellVectorHolder = new Vector<Vector<String>>(); 
		
		try {
		    FileInputStream inputStream = new FileInputStream(nameFile);
		    POIFSFileSystem myExcelFile = new POIFSFileSystem(inputStream);

		    HSSFWorkbook workBook = new HSSFWorkbook(myExcelFile);

		    // récupérer le nombre de feuilles
		    int nombreFeuillesExcel = workBook.getNumberOfSheets();
		    for (int i = 0; i < nombreFeuillesExcel; i++) {
			// Récupératoin de la feuille
			HSSFSheet feuille = workBook.getSheetAt(i);

			// Boucle sur l'iterateur
			for (int ligne = feuille.getFirstRowNum() + 1; ligne <= feuille.getLastRowNum(); ligne++) {
			    HSSFRow myRow = feuille.getRow(ligne);
			    Iterator<Cell> cellIter = myRow.cellIterator();

			    // Création du vecteur qui contiendra les cellules de la
			    // ligne
			    // en cours
			    Vector<String> cellStoreVector = new Vector<String>();
			    while (cellIter.hasNext()) {
				// Récupération de la cellule
				HSSFCell cellule = (HSSFCell) cellIter.next();
				String valueCell = "";
				if (cellule != null) {

				    int type = cellule.getCellType();

				    Comment comment = cellule.getCellComment();
				    if (comment != null) {
					RichTextString commentaire = comment.getString();
					mapCommentaires.put(cellule.getRichStringCellValue().getString(), commentaire.getString());
				    }
				    String valueCellule = "";
				    switch (type) {
				    case HSSFCell.CELL_TYPE_STRING:
					//System.out.println("value => " + cellule.getRichStringCellValue());
					valueCellule = cellule.getRichStringCellValue().toString();
					break;
				    case HSSFCell.CELL_TYPE_NUMERIC:
					double d = cellule.getNumericCellValue();
					CellStyle style = cellule.getCellStyle();
					String dataFormat = style.getDataFormatString();

					if (dataFormat.equals("DD\\-MMM\\-YY") || dataFormat.equals("MMM\\-YY") || dataFormat.equals("mmm-yy")) {
					    // Il s'agit d'une date
					    GregorianCalendar cal = new GregorianCalendar(Locale.FRANCE);
					    cal.setTime(HSSFDateUtil.getJavaDate(d));
					    SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.FRANCE);
					    String sdate = sdf.format(HSSFDateUtil.getJavaDate(d));
					    sdate = sdate.replaceAll("\\.", "");
					    valueCellule = sdate;
					} else if (dataFormat.equals("0.00%") || dataFormat.contains("%")) {

					    // Il s'agit d'un pourcentage
					    NumberFormat decimalFormat = new DecimalFormat("#.#");
					    d *= 100.0;
					    valueCellule = decimalFormat.format(d);

					} else {
					    if (dataFormat.contains("#,##0.00") || dataFormat.equals("0.00")) {
						NumberFormat decimalFormat = new DecimalFormat("###,###,###.#");
						valueCellule = decimalFormat.format(d);
					    } else {
						NumberFormat decimalFormat = new DecimalFormat("###,###,###");
						valueCellule = decimalFormat.format(d);
					    }
					    // Il s'agit d'un nombre double
//					    NumberFormat decimalFormat = new DecimalFormat("###,###,###,###.####");
//					    valueCellule = decimalFormat.format(d);
					    if (valueCellule.equals("0")) {
						valueCellule = "--";
					    }
					}
					break;
				    case HSSFCell.CELL_TYPE_BLANK:
					valueCellule = cellule.getStringCellValue().concat(" -- ");
					break;

				    default:
					break;
				    }
				    cellStoreVector.add(valueCellule);
				}

			    }
			    if (cellStoreVector != null && !cellStoreVector.isEmpty())
				cellVectorHolder.addElement(cellStoreVector);
			}
		    }

		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		}
		return cellVectorHolder;

	}

	public static Map<Integer, String> getHeaders(String nameFile) {// Vector<String> cellStoreVector = new Vector<String>();
		Map<Integer, String> mapDatas = new HashMap<Integer, String>();

		try {
		    FileInputStream inputStream = new FileInputStream(nameFile);
		    POIFSFileSystem myExcelFile = new POIFSFileSystem(inputStream);

		    HSSFWorkbook workBook = new HSSFWorkbook(myExcelFile);

		    // TODO : récupérer le nombre de feuilles
		    int nombreFeuillesExcel = workBook.getNumberOfSheets();
		    for (int i = 0; i < nombreFeuillesExcel; i++) {
			// Récupératoin de la feuille
			HSSFSheet feuille = workBook.getSheetAt(i);

			// Création de l'iterateur pour parcourir les lignes
			HSSFRow rowHead = feuille.getRow(feuille.getFirstRowNum());
			if (rowHead != null) {
			    // Iterator<Cell> cellIter = rowHead.cellIterator();
			    // Iterator<HSSFCell> cellIter = rowHead.cellIterator();
			    for (int colonne = rowHead.getFirstCellNum(); colonne <= rowHead.getLastCellNum(); colonne++) {
				// while (cellIter.hasNext()) {
				// Récupération de la cellule
				HSSFCell cellule = rowHead.getCell(colonne);
				// HSSFCell cellule = rowHead.getCell(colonne);

				if (cellule != null) {
				    String valueCellule = "";
				    int index = cellule.getColumnIndex();

				    int type = cellule.getCellType();
				    if (type == HSSFCell.CELL_TYPE_FORMULA) {
					type = cellule.getCachedFormulaResultType();
				    }

				    Comment comment = cellule.getCellComment();
				    if (comment != null) {
					RichTextString commentaire = comment.getString();
					mapCommentaires.put(cellule.getRichStringCellValue().getString(), commentaire.getString());
				    }
				    
				    switch (type) {
				    case HSSFCell.CELL_TYPE_STRING:
					valueCellule = cellule.getRichStringCellValue().toString();
					break;
				    case HSSFCell.CELL_TYPE_NUMERIC:
					double d = cellule.getNumericCellValue();
					CellStyle style = cellule.getCellStyle();
					String dataFormat = style.getDataFormatString();
					if (dataFormat.equals("DD\\-MMM\\-YY")) {
					    // Il s'agit d'une date
					    GregorianCalendar cal = new GregorianCalendar(Locale.FRANCE);
					    cal.setTime(HSSFDateUtil.getJavaDate(d));
					    String dateLine = cal.get(Calendar.DAY_OF_MONTH) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.YEAR);
					    valueCellule = dateLine;
					} else if (dataFormat.equals("0,00%")) {
					    // Il s'agit d'un pourcentage
					    NumberFormat percentFormat = NumberFormat.getPercentInstance(Locale.FRANCE);
					    valueCellule = percentFormat.format(d);
					} else {
					    // Il s'agit d'un nombre double
					    NumberFormat decimalFormat = new DecimalFormat("#.####");
					    valueCellule = decimalFormat.format(d);
					}
					break;
				    case HSSFCell.CELL_TYPE_BLANK:
					valueCellule = cellule.getStringCellValue();
					break;

				    default:
					break;
				    }
				    // cellStoreVector.addElement(valueCellule);
				    mapDatas.put(index, valueCellule);
				}
			    }
			}
		    }

		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		}
		return mapDatas;
	}

	public static Map<Integer, String> getHeadersGraphes(String nameFile) {
		// Vector<String> cellStoreVector = new Vector<String>();
		Map<Integer, String> mapDatas = new HashMap<Integer, String>();

		try {
			FileInputStream inputStream = new FileInputStream(nameFile);
			POIFSFileSystem myExcelFile = new POIFSFileSystem(inputStream);

			XSSFWorkbook workBook = new XSSFWorkbook(inputStream);

			// récupérer le nombre de feuilles
			int nombreFeuillesExcel = workBook.getNumberOfSheets();
			for (int i = 0; i < nombreFeuillesExcel; i++) {
				// Récupératoin de la feuille
				XSSFSheet feuille = workBook.getSheetAt(i);

				// Création de l'iterateur pour parcourir les lignes
				XSSFRow rowHead = feuille.getRow(feuille.getFirstRowNum());
				if (rowHead != null) {
					// Iterator<XSSFCell> cellIter = rowHead.cellIterator();
					for (int colonne = rowHead.getFirstCellNum() + 1; colonne <= rowHead.getLastCellNum(); colonne++) {
						// Récupération de la cellule
						XSSFCell cellule = rowHead.getCell(colonne);
						if (cellule != null) {
							String valueCellule = getValueCellule(cellule);
							valueCellule = valueCellule.replaceAll("\\(%\\)", "");
							valueCellule = valueCellule.replaceAll("\\(MAD\\)", "");
							valueCellule = valueCellule.replaceAll("\\(Mois\\)", "");
							valueCellule = valueCellule.replaceAll("\\(An\\)", "");
							valueCellule = valueCellule.replaceAll("\\(Ans\\)", "");
							int index = cellule.getColumnIndex();
							// cellStoreVector.addElement(valueCellule);
							mapDatas.put(index, valueCellule);
						}

					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mapDatas;
	}

	public static Map<Integer, String> getHeadersCashFlow(String nameFile) {
		// Vector<String> cellStoreVector = new Vector<String>();
		Map<Integer, String> mapDatas = new HashMap<Integer, String>();

		try {
			FileInputStream inputStream = new FileInputStream(nameFile);
			POIFSFileSystem myExcelFile = new POIFSFileSystem(inputStream);

			XSSFWorkbook workBook = new XSSFWorkbook(inputStream);

			// récupérer le nombre de feuilles
			int nombreFeuillesExcel = workBook.getNumberOfSheets();
			for (int i = 0; i < nombreFeuillesExcel; i++) {
				// Récupératoin de la feuille
				XSSFSheet feuille = workBook.getSheetAt(i);

				// Création de l'iterateur pour parcourir les lignes
				XSSFRow rowHead = feuille.getRow(feuille.getFirstRowNum() + 1);
				if (rowHead != null) {
					// Iterator<XSSFCell> cellIter = rowHead.cellIterator();
					for (int colonne = rowHead.getFirstCellNum() + 1; colonne <= 11; colonne++) {
						// Récupération de la cellule
						XSSFCell cellule = rowHead.getCell(colonne);
						if (cellule != null) {
							Comment comment = cellule.getCellComment();
							RichTextString commentaire = null;
							String valueCellule = "";
							if (comment != null) {
								commentaire = comment.getString();
								if (commentaire != null && !"".equals(commentaire)) {
									valueCellule = commentaire.getString();
								}
								mapCommentaires.put(cellule.getRichStringCellValue().getString(),
										commentaire.getString());
							} else {
								valueCellule = getValueCellule(cellule);
							}
							// getValueCellule(cellule);
							valueCellule = valueCellule.replaceAll("\\(%\\)", "");
							valueCellule = valueCellule.replaceAll("\\(MAD\\)", "");
							valueCellule = valueCellule.replaceAll("\\(Mois\\)", "");
							if (valueCellule.endsWith("A ") || valueCellule.endsWith("P1 ")
									|| valueCellule.endsWith("A1 ") || valueCellule.contains("P1")
									|| valueCellule.contains("A1")) {
								// valueCellule =
								// valueCellule.substring(valueCellule.length()
								// - 1, valueCellule.length() );
								valueCellule = valueCellule.replace("A ", "");
								valueCellule = valueCellule.replace("P1 ", "");
								valueCellule = valueCellule.replace("P1", "");
								valueCellule = valueCellule.replace("A1 ", "");
								valueCellule = valueCellule.replace("A1", "");
							}

							int index = cellule.getColumnIndex();
							// cellStoreVector.addElement(valueCellule);
							// if (colonne == 10 || colonne == 11 || colonne ==
							// 12) {
							// valueCellule = valueCellule.replace(" A", "");
							// valueCellule = valueCellule.replace(" P1", "");
							// mapDatas.put(9, valueCellule);
							// colonne = 12;
							//
							// } else if (colonne == 13 || colonne == 14 ||
							// colonne == 15) {
							// valueCellule = valueCellule.replace(" A", "");
							// valueCellule = valueCellule.replace(" P1", "");
							// mapDatas.put(10, valueCellule);
							// colonne = 15;
							// } else if (colonne == 16 || colonne == 17 ||
							// colonne == 18) {
							// valueCellule = valueCellule.replace(" A", "");
							// valueCellule = valueCellule.replace(" P1", "");
							// mapDatas.put(11, valueCellule);
							// break;
							// } else {
							mapDatas.put(index, valueCellule);
							// }
						}
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mapDatas;
	}

	public static Vector<String> getTrimestres(String nameFile) {
		Vector<String> listeTrimestre = new Vector<String>();
		try {
			FileInputStream inputStream = new FileInputStream(nameFile);
			POIFSFileSystem myExcelFile = new POIFSFileSystem(inputStream);

			XSSFWorkbook workBook = new XSSFWorkbook(inputStream);
			// Récupératoin de la feuille
			XSSFSheet feuille = workBook.getSheetAt(0);

			for (int ligne = feuille.getFirstRowNum() + 1; ligne <= feuille.getLastRowNum(); ligne++) {
				XSSFRow myRow = feuille.getRow(ligne);
				if (myRow != null) {
					XSSFCell myCell = myRow.getCell(0);
					// Création du vecteur qui contiendra les cellules de la
					// ligne
					// en cours

					// Récupération de la cellule
					String valueCell = "";
					if (myCell != null) {
						if (myCell.getCellType() != XSSFCell.CELL_TYPE_BLANK) {
							if (myCell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
								valueCell = myCell.getStringCellValue();
							} else if (myCell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
								double d = myCell.getNumericCellValue();
								GregorianCalendar cal = new GregorianCalendar(Locale.FRANCE);
								if (myCell.getColumnIndex() == 0) {
									cal.setTime(DateUtil.getJavaDate(d));

									SimpleDateFormat sdf = new SimpleDateFormat("MMM-yyyy", Locale.FRANCE);

									// String dateLine =
									// cal.get(Calendar.DAY_OF_MONTH) + "/" +
									// (cal.get(Calendar.MONTH) + 1) + "/" +
									// cal.get(Calendar.YEAR);
									String sdate = sdf.format(DateUtil.getJavaDate(d));
									sdate = sdate.replaceAll("\\.", "");
									valueCell = sdate;
								} else {
									Double numValue = (Double) myCell.getNumericCellValue();
									NumberFormat numFormat = new DecimalFormat("#.####");
									String valueFormatNumber = numFormat.format(numValue);

									valueCell = valueFormatNumber;
								}
							} else if (myCell.getCellType() == XSSFCell.CELL_TYPE_BOOLEAN) {
								valueCell = (myCell.getBooleanCellValue() ? "1" : "0");
							}
							listeTrimestre.addElement("'" + valueCell + "'");
						}
					}
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return listeTrimestre;
	}

	public static Vector<String> getPeriodes(String nameFile) {
		Vector<String> listeTrimestre = new Vector<String>();
		try {
			FileInputStream inputStream = new FileInputStream(nameFile);
			POIFSFileSystem myExcelFile = new POIFSFileSystem(inputStream);

			XSSFWorkbook workBook = new XSSFWorkbook(inputStream);
			// Récupératoin de la feuille
			XSSFSheet feuille = workBook.getSheetAt(0);

			for (int ligne = 6; ligne <= feuille.getLastRowNum(); ligne++) {
				XSSFRow myRow = feuille.getRow(ligne);
				if (myRow != null) {
					XSSFCell myCell = myRow.getCell(1);
					// Création du vecteur qui contiendra les cellules de la
					// ligne
					// en cours

					// Récupération de la cellule
					String valueCell = "";
					if (myCell != null) {
						if (myCell.getCellType() != XSSFCell.CELL_TYPE_BLANK) {
							if (myCell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
								valueCell = myCell.getStringCellValue();
							} else if (myCell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
								double d = myCell.getNumericCellValue();
								GregorianCalendar cal = new GregorianCalendar(Locale.FRANCE);
								if (myCell.getColumnIndex() == 0) {
									cal.setTime(DateUtil.getJavaDate(d));

									SimpleDateFormat sdf = new SimpleDateFormat("MMM-yyyy", Locale.FRANCE);

									// String dateLine =
									// cal.get(Calendar.DAY_OF_MONTH) + "/" +
									// (cal.get(Calendar.MONTH) + 1) + "/" +
									// cal.get(Calendar.YEAR);
									String sdate = sdf.format(DateUtil.getJavaDate(d));
									sdate = sdate.replaceAll("\\.", "");
									valueCell = sdate;
								} else {
									Double numValue = (Double) myCell.getNumericCellValue();
									NumberFormat numFormat = new DecimalFormat("#.####");
									String valueFormatNumber = numFormat.format(numValue);

									valueCell = valueFormatNumber;
								}
							} else if (myCell.getCellType() == XSSFCell.CELL_TYPE_BOOLEAN) {
								valueCell = (myCell.getBooleanCellValue() ? "1" : "0");
							}
							listeTrimestre.addElement("'" + valueCell + "'");
						}
					}
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return listeTrimestre;
	}

	public static Vector<String> getDates(String nameFile) {
		Vector<String> listeTrimestre = new Vector<String>();
		try {
			FileInputStream inputStream = new FileInputStream(nameFile);
			POIFSFileSystem myExcelFile = new POIFSFileSystem(inputStream);

			XSSFWorkbook workBook = new XSSFWorkbook(inputStream);
			// Récupératoin de la feuille
			XSSFSheet feuille = workBook.getSheetAt(0);

			for (int ligne = feuille.getFirstRowNum() + 1; ligne <= feuille.getPhysicalNumberOfRows(); ligne++) {
				XSSFRow myRow = feuille.getRow(ligne);
				if (myRow != null) {
					XSSFCell myCell = myRow.getCell(0);
					// Création du vecteur qui contiendra les cellules de la
					// ligne
					// en cours

					// Récupération de la cellule
					String valueCell = "";
					if (myCell != null) {
						if (myCell.getCellType() != XSSFCell.CELL_TYPE_BLANK) {
							if (myCell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
								valueCell = myCell.getStringCellValue();
							} else if (myCell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
								double d = myCell.getNumericCellValue();
								GregorianCalendar cal = new GregorianCalendar(Locale.FRANCE);
								if (myCell.getColumnIndex() == 0) {
									cal.setTime(DateUtil.getJavaDate(d));

									SimpleDateFormat sdf = new SimpleDateFormat("MMM-yyyy", Locale.FRANCE);

									// String dateLine =
									// cal.get(Calendar.DAY_OF_MONTH) + "/" +
									// (cal.get(Calendar.MONTH) + 1) + "/" +
									// cal.get(Calendar.YEAR);
									String sdate = sdf.format(DateUtil.getJavaDate(d));
									sdate = sdate.replaceAll("\\.", "");
									valueCell = sdate;
								} else {
									Double numValue = (Double) myCell.getNumericCellValue();
									NumberFormat numFormat = new DecimalFormat("#.####");
									String valueFormatNumber = numFormat.format(numValue);

									valueCell = valueFormatNumber;
								}
							} else if (myCell.getCellType() == XSSFCell.CELL_TYPE_BOOLEAN) {
								valueCell = (myCell.getBooleanCellValue() ? "1" : "0");
							}
							listeTrimestre.addElement(valueCell);
						}
					}
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return listeTrimestre;
	}

	public static void printCellDataToConsole(Vector<Vector<String>> dataPerformanceActif) {

		for (int i = 0; i < dataPerformanceActif.size(); i++) {
			Vector<String> cellStoreVector = dataPerformanceActif.elementAt(i);

			for (int j = 0; j < cellStoreVector.size(); j++) {
				String myCell = cellStoreVector.elementAt(j);

				if (myCell != null) {
					System.out.println(myCell.concat(" | "));
				}
			}
			System.out.print("\n");

		}
	}

	public static Map<Integer, Vector<Vector<String>>> readExcelFileHistoriquePeriode(String nameFile,
			String idPeriode) {
		Map<Integer, Vector<Vector<String>>> mapFeuillesValues = new HashMap<Integer, Vector<Vector<String>>>();

		try {
			FileInputStream inputStream = new FileInputStream(nameFile);
			POIFSFileSystem myExcelFile = new POIFSFileSystem(inputStream);
			

			XSSFWorkbook workBook = new XSSFWorkbook(inputStream);

			// Récupérer le nombre de feuilles
			for (int idFeuille = 0; idFeuille < workBook.getNumberOfSheets(); idFeuille++) {

				System.out.println("feuille " + idFeuille);
				Vector<Vector<String>> cellVectorHolder = new Vector<Vector<String>>();
				// Récupératoin de la feuille
				XSSFSheet feuille = workBook.getSheetAt(idFeuille);
				// Parcourir les lignes de la feuille
				feuille.getPhysicalNumberOfRows();
				int indexRow = 0;
				if (!idPeriode.equals("-1")) {
					indexRow = Integer.parseInt(idPeriode);
					Row row = feuille.getRow(indexRow + 1);

					Vector<String> cellStoreVector = new Vector<String>();
					if (row != null) {
						for (int colonne = row.getFirstCellNum(); colonne <= row.getLastCellNum(); colonne++) {
							Cell cellule = row.getCell(colonne);
							String valueCellule = "";
							if (cellule != null) {
								int type = cellule.getCellType();
								if (type == XSSFCell.CELL_TYPE_FORMULA) {
									type = cellule.getCachedFormulaResultType();
								}

								switch (type) {
								case XSSFCell.CELL_TYPE_STRING:
									valueCellule = cellule.getRichStringCellValue().toString();
									break;
								case XSSFCell.CELL_TYPE_NUMERIC:
									double d = cellule.getNumericCellValue();
									CellStyle style = cellule.getCellStyle();
									String dataFormat = style.getDataFormatString();

									if (dataFormat.equals("DD\\-MMM\\-YY")) {
										// Il s'agit d'une date
										GregorianCalendar cal = new GregorianCalendar(Locale.FRANCE);
										cal.setTime(DateUtil.getJavaDate(d));
										SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.FRANCE);

										String sdate = sdf.format(DateUtil.getJavaDate(d));
										sdate = sdate.replaceAll("\\.", "");
										valueCellule = sdate;
									} else if (dataFormat.equals("0.00%") || dataFormat.contains("%")) {
										// Il s'agit d'un pourcentage
										NumberFormat decimalFormat = new DecimalFormat("#.####");
										d *= 100.0;
										valueCellule = decimalFormat.format(d).concat(" %");

									} else {
										// Il s'agit d'un nombre double
										NumberFormat decimalFormat = new DecimalFormat("#.####");
										valueCellule = decimalFormat.format(d);
									}
									break;
								case XSSFCell.CELL_TYPE_BLANK:
									valueCellule = cellule.getStringCellValue().concat(" -- ");
									break;

								default:
									break;
								}
								cellStoreVector.add(valueCellule);
							}
						}
						if (cellStoreVector != null && !cellStoreVector.isEmpty())
							cellVectorHolder.addElement(cellStoreVector);
					}

				}

				// Insertion du vecteur dans le MAP (clé : Nom de la feuille ,
				// valeur : vecteur des lignes)
				mapFeuillesValues.put(idFeuille, cellVectorHolder);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mapFeuillesValues;
	}

	public static Vector<Vector<String>> readExcelFileCashFlow(String nameFile) {
		
		Vector<Vector<String>> cellVectorHolder = new Vector<Vector<String>>();
		
		try {   
			
			FileInputStream inputStream = new FileInputStream(nameFile);
			POIFSFileSystem myExcelFile = new POIFSFileSystem(inputStream);
			XSSFWorkbook workBook = new XSSFWorkbook(inputStream);

			// Récupératoin de la feuille
			XSSFSheet feuille = workBook.getSheetAt(0);
			// Parcourir les lignes de la feuille
			for (int ligne = feuille.getFirstRowNum() + 1; ligne <= feuille.getLastRowNum(); ligne++) {
				Row row = feuille.getRow(ligne);
				Vector<String> cellStoreVector = new Vector<String>();
				if (row != null) {
					for (int colonne = row.getFirstCellNum(); colonne <= row.getLastCellNum(); colonne++) {
						Cell cellule = row.getCell(colonne);
						String valueCellule = "";
						if (cellule != null) {
							Comment comment = cellule.getCellComment();
							if (comment != null) {
								RichTextString commentaire = comment.getString();
								mapCommentaires.put(cellule.getRichStringCellValue().getString(),
										commentaire.getString());
							}

							int type = cellule.getCellType();
							if (type == XSSFCell.CELL_TYPE_FORMULA) {
								type = cellule.getCachedFormulaResultType();
							}

							switch (type) {
							case XSSFCell.CELL_TYPE_STRING:
								valueCellule = cellule.getRichStringCellValue().toString();
								break;
							case XSSFCell.CELL_TYPE_NUMERIC:
								double d = cellule.getNumericCellValue();
								CellStyle style = cellule.getCellStyle();
								String dataFormat = style.getDataFormatString();

								if (dataFormat.equals("DD\\-MMM\\-YY")) {
									// Il s'agit d'une date
									GregorianCalendar cal = new GregorianCalendar(Locale.FRANCE);
									cal.setTime(DateUtil.getJavaDate(d));
									SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE);
									String sdate = sdf.format(DateUtil.getJavaDate(d));
									sdate = sdate.replaceAll("\\.", "");
									valueCellule = sdate;
								} else if (dataFormat.equals("0.00%") || dataFormat.contains("%")) {
									// Il s'agit d'un pourcentage
									NumberFormat decimalFormat = new DecimalFormat("#.##");
									d *= 100.0;
									valueCellule = decimalFormat.format(d);
									// .concat(" %");
								} else {
									// Il s'agit d'un nombre double
									NumberFormat decimalFormat = new DecimalFormat("############.####");
									d = Math.round(d);
									valueCellule = decimalFormat.format(d);
								}
								break;
							case XSSFCell.CELL_TYPE_BLANK:
								valueCellule = cellule.getStringCellValue().concat(" -- ");
								break;

							default:
								break;
							}
							// valueCellule = getValueCellule(cellule);
							cellStoreVector.add(valueCellule);
						}
					}
					if (cellStoreVector != null && !cellStoreVector.isEmpty())
						cellVectorHolder.addElement(cellStoreVector);
				}
			}
			// Insertion du vecteur dans le MAP (clé : Nom de la feuille ,
			// valeur : vecteur des lignes)
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cellVectorHolder;
	}

}
