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

import org.odftoolkit.odfdom.doc.OdfTextDocument;

import fr.lixbox.common.exceptions.BusinessException;
import fr.lixbox.common.util.ExceptionUtil;
import fr.opensagres.odfdom.converter.pdf.PdfConverter;
import fr.opensagres.odfdom.converter.pdf.PdfOptions;

/**
 * Ce converter assure la conversion d'un fichier odt en pdf.
 * 
 * @author ludovic.terral
 */
public class OdtToPDFConverter extends Converter 
{
    // ----------- Attribut(s) -----------   
    private static final long serialVersionUID = 7818887038257676598L;   
    


    // ----------- Methode(s) -----------   
    public OdtToPDFConverter(InputStream inStream, OutputStream outStream) 
    {
        super(inStream, outStream);
    }



    @Override
    public void convert() throws BusinessException
    {
        try
        {
            OdfTextDocument document = OdfTextDocument.loadDocument(inStream);
            PdfOptions options = PdfOptions.create();
            PdfConverter.getInstance().convert(document, outStream, options);
        }
        catch (Exception e)
        {
            ExceptionUtil.traiterException(e, "ODTPDFCONV", true);
        }  
	}
}