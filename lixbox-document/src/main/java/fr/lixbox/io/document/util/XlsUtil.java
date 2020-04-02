/*******************************************************************************
 *    
 *                           FRAMEWORK Lixbox
 *                          ==================
 *      
 *   Copyrigth - LIXTEC - Tous droits reserves.
 *   
 *   Le contenu de ce fichier est la propriete de la societe Lixtec.
 *   
 *   Toute utilisation de ce fichier et des informations, sous n'importe quelle
 *   forme necessite un accord ecrit explicite des auteurs
 *   
 *   @AUTHOR Ludovic TERRAL
 *
 ******************************************************************************/
package fr.lixbox.io.document.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.lixbox.common.util.DateUtil;
import fr.lixbox.common.util.StringUtil;
import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;


/**
 * Cette classe est un utilitaire qui traite les fichiers Excel
 * 
 * @author ludovic.terral
 */
public class XlsUtil
{
    // ----------- Attribut -----------   
    private static final Log LOG = LogFactory.getLog(XlsUtil.class);    

    

    // ----------- Methode -----------
    private XlsUtil()
    {
        LOG.debug("XlsUtil cree");
    }



    /**
     *  Cette methode cree un fichier XLS
     *  en fichier formate CSV avec un separateur de champs par defaut ";".
     *  
     * @param fichierXls
     * @return
     */
    public static File convertXlsToCsv(File fichierXls)
    {
    	// SEPARATEUR DE CHAMP PAR DEFAUT D UN FICHIER CSV EST ";"
    	return convertXlsToCsv(fichierXls, ";");
    }



    /**
     *  Cette methode cree un fichier XLS
     *  en fichier formate CSV avec le separateur de champs transmis en parametre.
     *  
     *  @param fichierXls
     *  @param _separateurChamps
     */
	public static File convertXlsToCsv(File fichierXls, String _separateurChamps)
    {
        String separateurChamps = StringUtil.isEmpty(_separateurChamps)?";":_separateurChamps;
    	
        File f = null;
        if (((null != fichierXls) && (null != fichierXls.getAbsolutePath())) && (fichierXls.getAbsolutePath().length() > 0))
        {
            // File to store data in form of CSV
            f = new File(fichierXls.getAbsolutePath() + ".csv");
            f.deleteOnExit();
            LOG.info("Conversion du fichier " + fichierXls.getAbsolutePath() + " au format CSV ...");
         
            final String encoding = "ISO8859-1";
            
            try
            (                    
                OutputStream os = new FileOutputStream(f);
                OutputStreamWriter osw = new OutputStreamWriter(os, encoding);  
                BufferedWriter bw = new BufferedWriter(osw); 
            )
            {
                // OUTPUT FILE
                final WorkbookSettings ws = new WorkbookSettings();
                ws.setLocale(new Locale("fr", "FR"));
                final Workbook workbook = Workbook.getWorkbook(fichierXls, ws);
                
                // Gets the sheets from workbook
                final int length = workbook.getNumberOfSheets();
                for (int sheet = 0; sheet < length; sheet++)
                {
                    Sheet s = workbook.getSheet(sheet);
                    
                    bw.write(s.getName());
                    bw.newLine();
                    
                    Cell[] row;
                    
                    // Gets the cells from sheet
                    int length2 = s.getRows();
                    LOG.info("Page " + (sheet+1) + " - Nbre Lignes a convertir : " + length2);
                    
                    for (int i = 0 ; i < length2 ; i++)
                    {
                        row = s.getRow(i);
                        int numeroLigne = i + 1;
                        
                        if (row.length > 0)
                        {
                        	int ligneADerouler = row.length + 1;
                        	LOG.debug("Nbre Cellules a derouler : " + ligneADerouler);
                        	StringBuilder ligneCsvGenere = new StringBuilder();
                            bw.write(row[0].getContents());
                            ligneCsvGenere.append(row[0].getContents());
                            for (int j = 1; j < row.length; j++)
                            {
                                bw.write(';');
                                if (CellType.DATE.equals(row[j].getType()))                                    
                                {
                                    bw.write(DateUtil.format(((DateCell)row[j]).getDate(),"dd/MM/yyyy"));
                                }
                                else
                                {
                                    bw.write(row[j].getContents());
                                }
                                ligneCsvGenere.append(separateurChamps);
                                ligneCsvGenere.append(row[j].getContents());
                            }
                            
                            LOG.debug("Ligne " + numeroLigne + " : " + ligneCsvGenere);
                        }
                        bw.newLine();
                    }
                }
            }
            catch (IOException | BiffException e)
            {
            	LOG.error(e);
                f = null;
            }
            LOG.info("Conversion terminee.");
        }
        return f;
    }
}
