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
