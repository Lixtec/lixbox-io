/*******************************************************************************
 *    
 *                           FRAMEWORK Lixbox
 *                          ==================
 *      
 * This file is part of lixbox-io.
 *
 *    lixbox-supervision is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    lixbox-supervision is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *    along with lixbox-io.  If not, see <https://www.gnu.org/licenses/>
 *   
 *   @AUTHOR Lixbox-team
 *
 ******************************************************************************/
package fr.lixbox.io.document.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.format.CellDateFormatter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import fr.lixbox.common.util.StringUtil;


/**
 * Cette classe est un utilitaire qui traite les fichiers Excel au format XLSX.
 * 
 * @author ludovic.terral
 *
 */
public class XlsxUtil
{
    // ----------- Attribut -----------   
    private static final Log LOG = LogFactory.getLog(XlsxUtil.class);    

    

    // ----------- Methode -----------
    private XlsxUtil()
    {
        LOG.debug("XlsxUtil cree");
    }
    
    
    
    /**
     *  Cette methode cree un fichier XLSX
     *  en fichier formate CSV avec un separateur de champs par defaut ";".
     *  
     * @param fichierXlsx
     * @return
     */
    public static File convertXlsxToCsv(File fichierXlsx)
    {
    	// SEPARATEUR DE CHAMP PAR DEFAUT D UN FICHIER CSV EST ";"
    	return convertXlsxToCsv(fichierXlsx, ";");
    }



    /**
     *  Cette methode cree un fichier XLSX
     *  en fichier formate CSV avec le separateur de champs transmis en parametre.
     *  
     *  @param fichierXlsx
     *  @param _separateurChamps
     */
    @SuppressWarnings("resource")
	public static File convertXlsxToCsv(File fichierXlsx, String _separateurChamps)
    {
    	// DEFINIR UN SEPARATEUR DE CHAMP
        String separateurChamps = StringUtil.isEmpty(_separateurChamps)?";":_separateurChamps;
        File f = null;
        if (((null != fichierXlsx) && (null != fichierXlsx.getAbsolutePath())) && (fichierXlsx.getAbsolutePath().length() > 0))
        {
            // File to store data in form of CSV
            f = new File(fichierXlsx.getAbsolutePath() + ".csv");
            f.deleteOnExit();
            LOG.info("Conversion du fichier " + fichierXlsx.getAbsolutePath() + " au format CSV ...");
         
            final String encoding = "ISO8859-1";
            
            try (
                FileInputStream fis = new FileInputStream(fichierXlsx);
                OutputStream os = new FileOutputStream(f);
                OutputStreamWriter osw = new OutputStreamWriter(os, encoding);                
                BufferedWriter bw = new BufferedWriter(osw);
            )
            {

                
                //------------------------------------------------------------------------------
                // Reading data from XLSX file
                XSSFWorkbook book = new XSSFWorkbook(fis);
                
                // Gets the sheets from book
                final int length = book.getNumberOfSheets();
                for (int numSheet = 0; numSheet < length; numSheet++)
                {
	                XSSFSheet sheet = book.getSheetAt(numSheet);
	                
	                bw.write(sheet.getSheetName());
                    bw.newLine();
	                
	                Iterator<Row> itr = sheet.iterator();
	                
	                // Gets the cells from sheet
                    int nbreLigne = sheet.getPhysicalNumberOfRows();
                    int nbreCellulesParLigne = 0;
                    LOG.info("Page " + (numSheet+1) + " - Nbre Lignes a convertir : " + nbreLigne);
	
	                // Iterating over Excel file in Java                    
                    int numeroLigne = 0;
	                while (itr.hasNext())
	                {
	                    Row row = itr.next();
	                    numeroLigne = numeroLigne + 1;
	                    
	                    if (row.getPhysicalNumberOfCells() > 0)
                        {
                        	int cellulesADerouler = row.getPhysicalNumberOfCells();
                        	if (0 == nbreCellulesParLigne)
                        	{
                        		nbreCellulesParLigne = cellulesADerouler;
							}
                        	LOG.info("Nbre Cellules a derouler : " + cellulesADerouler);
                        	
                        	StringBuilder ligneCsvGenere = new StringBuilder();
                        	int compteurControleCelluleTraitee = 0;
                        	
                        	// Iterating over each column of Excel file
    	                    Iterator<Cell> cellIterator = row.cellIterator();
    	                    
    	                    while (cellIterator.hasNext())
    	                    {
    	                        Cell cell = cellIterator.next();
    	                        
    	                        // CONTROLE DE LA POSITION DE LA CELLULE
    	                        int positionCellule = cell.getColumnIndex();
    	                        int differencePositionCellule = positionCellule - compteurControleCelluleTraitee;
    	                        if (0 < differencePositionCellule)
    	                        {
    	                        	// UNE OU PLUSIEURS CELLULES VIDES SONT DETECTEES
    	                        	for (int j = 0; j < differencePositionCellule; j++)
    	                        	{
    	                        	   ligneCsvGenere.append("");
	         	                       ligneCsvGenere.append(separateurChamps);
									}
								}
    	                        
    	                        switch (cell.getCellType())
    	                        {
    	    	                    case STRING:
    	    	                    	try
    	     	                        {
    	    	                    	    ligneCsvGenere.append(cell.getStringCellValue());
    	     	                        }
    	     	                        catch (Exception e)
    	     	                        {
    	     	                        	// ON N A PAS REUSSI A RECUPERRER LE CONTENU DE LA CELLULE
    	     	                        	LOG.error(e);
    	     	                        }
    	    	                        break;
    	    	                    case NUMERIC:
    	    	                    	try
    	     	                        {
    	    	                    		if (DateUtil.isCellDateFormatted(cell))
    	    	                    		{
    	    	                    			// CAS D UNE DATE (AND TIME)
    	    	                    			CellValue cValue = book.getCreationHelper().createFormulaEvaluator().evaluate(cell);
    	    	                    			double dv = cValue.getNumberValue();
    	    	                    			if (DateUtil.isCellDateFormatted(cell))
    	    	                    			{
    	    	                    			    Date date = DateUtil.getJavaDate(dv);
    	    	                    			    String dateFmt = cell.getCellStyle().getDataFormatString();
    	    	                    			    /* strValue = new SimpleDateFormat(dateFmt).format(date); - won't work as 
    	    	                    			    Java fmt differs from Excel fmt. If Excel date format is mm/dd/yyyy, Java 
    	    	                    			    will always be 00 for date since "m" is minutes of the hour.*/

    	    	                    			    ligneCsvGenere.append(new CellDateFormatter(dateFmt).format(date)); 
    	    	                    			    // takes care of idiosyncrasies of Excel
    	    	                    			}
    	    	                    	    }
    	    	                    		else
    	    	                    		{
    	    	                    			// CAS D UN NOMBRE (ENTIER, DECIMAL, ...)
    	    	                    		    ligneCsvGenere.append(cell.getNumericCellValue());
    	    	                    		}
    	     	                        }
    	     	                        catch (Exception e)
    	     	                        {
    	     	                        	// ON A PAS REUSSI A RECUPERRER LE CONTENU DE LA CELLULE
    	     	                        	LOG.error(e);
    	     	                        }
    	    	                        break;
    	    	                    case BOOLEAN:
    	    	                    	try
    	     	                        {
    	    	                    	    ligneCsvGenere.append(cell.getBooleanCellValue());
    	     	                        }
    	     	                        catch (Exception e)
    	     	                        {
    	     	                        	// ON A PAS REUSSI A RECUPERRER LE CONTENU DE LA CELLULE
    	     	                        	LOG.error(e.toString());
    	     	                        }
    	    	                        break;
    	    	                    case BLANK:
    	    	                    	try
    	     	                        {
    	    	                    	    ligneCsvGenere.append("");
    	     	                        }
    	     	                        catch (Exception e)
    	     	                        {
    	     	                        	// ON N A PAS REUSSI A RECUPERRER LE CONTENU DE LA CELLULE
    	     	                        	LOG.error(e);
    	     	                        }
    	    	                        break;
    	    	                    default:
    	                        }
    	                        
    	                        // QUOI QU IL ARRIVE ON AJOUTE LE SEPARATEUR DE CHAMP (POUR NE PAS DECALER LES CELLULES SUR UNE LIGNE)
    	                        ligneCsvGenere.append(separateurChamps);
                                
                                compteurControleCelluleTraitee = compteurControleCelluleTraitee + 1 + differencePositionCellule;
    	                    }
    	                    
    	                    int differenceCellulesTraitees = nbreCellulesParLigne - compteurControleCelluleTraitee;
    	                    if (0 < differenceCellulesTraitees)
    	                    {
    	                    	// SI A LA FIN IL MANQUE DES CELLULES, COMPLETER PAR LE NOMBRE DE SEPARATEURS MANQUANTS 
								for (int j = 0; j < differenceCellulesTraitees; j++)
								{
								    ligneCsvGenere.append("");
								    ligneCsvGenere.append(separateurChamps);
								}
							}
                            
    	                    // SUPPRIMER LE DERNIER CARACTERE DE SEPARATION DE CHAMPS
    	                    ligneCsvGenere.delete(ligneCsvGenere.length() - 1, ligneCsvGenere.length());
    	                    
    	                    // ECRITURE DANS LE BUFFER
    	                    bw.write(ligneCsvGenere.toString());
    	                    
                            LOG.info("Ligne " + numeroLigne + " : " + ligneCsvGenere);
                        }
                        bw.newLine();
	                }
                }
            }
            catch (IOException e)
            {
            	LOG.error(e);
                f = null;
            }
            LOG.info("Conversion terminee.");
        }
        return f;
    }
}
