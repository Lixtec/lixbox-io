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
