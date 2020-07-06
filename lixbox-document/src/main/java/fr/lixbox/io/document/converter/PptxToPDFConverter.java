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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;

import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfWriter;

import fr.lixbox.common.exceptions.BusinessException;
import fr.lixbox.common.util.ExceptionUtil;


/**
 * Ce converter assure la conversion d'un fichier pptx en pdf.
 * 
 * @author ludovic.terral
 */
public class PptxToPDFConverter extends Converter
{
    // ----------- Attribut(s) -----------   
    private static final long serialVersionUID = 7915688703257676598L;   
    
    private transient List<XSLFSlide> slides;
    


    // ----------- Methode(s) -----------   
	public PptxToPDFConverter(InputStream inStream, OutputStream outStream)
	{
		super(inStream, outStream);
	}



    @Override
    public void convert() throws BusinessException
    {
        try
        {
            Dimension pgsize = processSlides();
            double zoom = 2; // magnify it by 2 as typical slides are low res
            AffineTransform at = new AffineTransform();
            at.setToScale(zoom, zoom);
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, outStream);
            document.open();
            
            for (int i = 0; i < getNumSlides(); i++) 
            {
                BufferedImage bufImg = new BufferedImage((int)Math.ceil(pgsize.width*zoom), (int)Math.ceil(pgsize.height*zoom), BufferedImage.TYPE_INT_RGB);
                Graphics2D graphics = bufImg.createGraphics();
                graphics.setTransform(at);
            
                //clear the drawing area
                graphics.setPaint(getSlideBGColor(i));
                graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));
                try
                {
                    drawOntoThisGraphic(i, graphics);
                }
                catch(Exception e)
                {
                    //Just ignore, draw what I have
                }
                
                Image image = Image.getInstance(bufImg, null);
                document.setPageSize(new Rectangle(image.getScaledWidth(), image.getScaledHeight()));
                document.newPage();
                image.setAbsolutePosition(0, 0);
                document.add(image);
            }
            document.close();
            
            writer.close();
        }
        catch (Exception e)
        {
            ExceptionUtil.traiterException(e, "PPTXPDFCONV", true);
        }  
    }
	


    protected Dimension processSlides() throws IOException
    {
        try (
            InputStream iStream = inStream;
                XMLSlideShow ppt = new XMLSlideShow(iStream);
        )
        {            
            Dimension dimension = ppt.getPageSize();
            slides = ppt.getSlides();
            return dimension;
        }
    }



    protected int getNumSlides()
    {
        return slides.size();
    }



    protected void drawOntoThisGraphic(int index, Graphics2D graphics)
    {
        slides.get(index).draw(graphics);
    }



    protected Color getSlideBGColor(int index)
    {
        return slides.get(index).getBackground().getFillColor();
    }
}