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

import org.docx4j.Docx4J;
import org.docx4j.fonts.PhysicalFonts;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

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
    private static final long serialVersionUID = 202101102246L;
    


    // ----------- Methode(s) -----------   
	public DocxToPDFConverter(InputStream inStream, OutputStream outStream) 
	{
		super(inStream, outStream);
	}
	
	
    
	@Override
    public void convert() throws BusinessException
    {
        try 
        {
            String regex = null;
            PhysicalFonts.setRegex(regex);
            WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(inStream);
            inStream.close();
            Docx4J.toPDF(wordMLPackage, outStream);
            outStream.close();
        } 
        catch (Exception e) 
        {
             ExceptionUtil.traiterException(e, "DOCXCONV", true);
        }
    }   
}