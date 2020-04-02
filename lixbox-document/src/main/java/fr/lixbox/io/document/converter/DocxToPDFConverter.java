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
package fr.lixbox.io.document.converter;

import java.io.InputStream;
import java.io.OutputStream;

import org.apache.log4j.Level;
import org.docx4j.Docx4jProperties;
import org.docx4j.convert.out.pdf.PdfConversion;
import org.docx4j.convert.out.pdf.viaXSLFO.Conversion;
import org.docx4j.convert.out.pdf.viaXSLFO.PdfSettings;
import org.docx4j.fonts.IdentityPlusMapper;
import org.docx4j.fonts.Mapper;
import org.docx4j.fonts.PhysicalFonts;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.utils.Log4jConfigurator;

import fr.lixbox.common.exceptions.BusinessException;
import fr.lixbox.common.util.ExceptionUtil;



/**
 * Ce converter assure la conversion d'un fichier word docx en pdf.
 * 
 * @author ludovic.terral
 */
public class DocxToPDFConverter extends Converter 
{
    // ----------- Attribut(s) -----------   
    private static final long serialVersionUID = 7818887038257676598L;   
    


    // ----------- Methode(s) -----------   
	public DocxToPDFConverter(InputStream inStream, OutputStream outStream) 
	{
		super(inStream, outStream);
	}
	
	
    
    public void convert() throws BusinessException
    {
        try 
        {
            String regex = null;
            PhysicalFonts.setRegex(regex);
            WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(inStream);
            
            Mapper fontMapper = new IdentityPlusMapper();
            wordMLPackage.setFontMapper(fontMapper);
            PdfConversion converter = new Conversion(wordMLPackage);     
            Docx4jProperties.getProperties().setProperty("docx4j.Log4j.Configurator.disabled", "false");
            Log4jConfigurator.configure();            
            org.docx4j.convert.out.pdf.viaXSLFO.Conversion.log.setLevel(Level.OFF);
            converter.output(outStream, new PdfSettings());
   
        } 
        catch (Exception e) 
        {
             ExceptionUtil.traiterException(e, "DOCXCONV", true);
        }
    }   
}