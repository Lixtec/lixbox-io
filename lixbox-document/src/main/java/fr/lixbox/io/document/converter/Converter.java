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