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
import java.io.Serializable;

import fr.lixbox.common.exceptions.BusinessException;


/**
 * Cette classe est la base des convertisseurs de documents.
 * 
 * @author ludovic.terral
 */
public abstract class Converter implements Serializable
{    
    // ----------- Attribut(s) -----------   
    private static final long serialVersionUID = -2206551593776424271L;
    
    protected transient InputStream inStream;
    protected transient OutputStream outStream;

    

    // ----------- Methode(s) -----------   
    public Converter(InputStream inStream, OutputStream outStream)
	{
		this.inStream = inStream;
		this.outStream = outStream;
	}

    
    
	public abstract void convert() throws BusinessException;
}