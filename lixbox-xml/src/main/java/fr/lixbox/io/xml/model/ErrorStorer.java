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
package fr.lixbox.io.xml.model;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;

/**
 * Cette classe stocke les erreurs ainsi que les anomalies
 * rencontrees lors du parsing d'un fichier XML.
 * 
 * @author ludovic.terral
 */
public class ErrorStorer implements ErrorHandler
{
    // ----------- Methode -----------   
    /**
     * Cette methode recupere une notification d'anomalie.
     * 
     * @param exception 
     *            
     * @throws SAXParseException erreur du parser
     */
    public void warning(final SAXParseException exception) throws SAXParseException
    {
        throw exception;
    }

    

    /**
     * Cette methode recupere une notification d'erreur.
     * 
     * @param exception
     *            
     * @throws SAXParseException erreur du parser
     */
    public void error(final SAXParseException exception) throws SAXParseException
    {
        throw exception;
    }


    
    /**
     * Cette methode recupere une notification d'erreur critique.
     * 
     * @param exception
     *            
     * @throws SAXParseException erreur du parser
     */
    public void fatalError(final SAXParseException exception) throws SAXParseException
    {
        throw exception;
    }
}
