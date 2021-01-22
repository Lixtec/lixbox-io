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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
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
    public static File convertXlsToCsv(File fichierXls, Charset charset)
    {
    	// SEPARATEUR DE CHAMP PAR DEFAUT D UN FICHIER CSV EST ";"
    	return convertXlsToCsv(fichierXls, ";", charset);
    }



    /**
     *  Cette methode cree un fichier XLS
     *  en fichier formate CSV avec le separateur de champs transmis en parametre.
     *  
     *  @param fichierXls
     *  @param separateurChamps
     */
	public static File convertXlsToCsv(File fichierXls, String separateurChamps, Charset charset)
    {
        separateurChamps = StringUtil.isEmpty(separateurChamps)?";":separateurChamps;
        File f = null;
        if (((null != fichierXls) && (null != fichierXls.getAbsolutePath())) && (fichierXls.getAbsolutePath().length() > 0))
        {
            // File to store data in form of CSV
            f = new File(fichierXls.getAbsolutePath() + ".csv");
            f.deleteOnExit();
            LOG.info("Conversion du fichier " + fichierXls.getAbsolutePath() + " au format CSV ...");
         
            try
            (                    
                OutputStream os = new FileOutputStream(f);
                OutputStreamWriter osw = new OutputStreamWriter(os, charset);
                BufferedWriter bw = new BufferedWriter(osw); 
            )
            {
                // OUTPUT FILE
                final WorkbookSettings ws = new WorkbookSettings();
                ws.setLocale(new Locale("fr", "FR"));
                ws.setEncoding(charset.displayName());
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
