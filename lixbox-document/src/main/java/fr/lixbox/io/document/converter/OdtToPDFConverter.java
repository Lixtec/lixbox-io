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