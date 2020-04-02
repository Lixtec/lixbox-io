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
package fr.lixbox.io.edi.ressource;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author ludovic.terral
 *
 */
public class EdiResources
{
    private static final String BUNDLE_NAME = "fr.lixbox.io.edi.ressource.ressources"; //$NON-NLS-1$

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
            .getBundle(BUNDLE_NAME);


    private EdiResources()
    {
        // unused
    }


    /**
     * Renvoie le texte associé au resource bundle.
     * 
     * @param key
     * 
     * @return message le texte.
     */
    public static String getString(String key)
    {
        try
        {
            return RESOURCE_BUNDLE.getString(key);
        }
        catch (MissingResourceException e)
        {
            return '!' + key + '!';
        }
    }


    /**
     * Renvoie le texte associéau resource bundle en utilisant des masques de
     * formatage.
     * 
     * @param key
     * @param parameters
     * 
     * @return message le texte.
     */
    public static String getString(String key, Object[] parameters)
    {
        String baseMsg;
        try
        {
            baseMsg = RESOURCE_BUNDLE.getString(key);
        }
        catch (MissingResourceException e)
        {
            return '!' + key + '!';
        }
        return MessageFormat.format(baseMsg, parameters);
    }


    /**
     * Renvoie le texte associé au resource bundle en utilisant des masques de
     * formatage.
     * 
     * @param key
     * @param parameter
     * 
     * @return message le texte.
     */
    public static String getString(String key, Object parameter)
    {
        return getString(key, new Object[] { parameter });
    }
}
