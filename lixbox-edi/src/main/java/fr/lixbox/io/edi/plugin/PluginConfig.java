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
package fr.lixbox.io.edi.plugin;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.lixbox.common.exceptions.BusinessException;
import fr.lixbox.common.exceptions.ProcessusException;
import fr.lixbox.common.stream.util.StreamStringUtil;
import fr.lixbox.common.util.CollectionUtil;
import fr.lixbox.io.edi.plugin.model.jaxb.Attribut;
import fr.lixbox.io.edi.plugin.model.jaxb.Balise;
import fr.lixbox.io.edi.plugin.model.jaxb.EdiConfig;
import fr.lixbox.io.edi.plugin.model.jaxb.Schema;
import fr.lixbox.io.edi.plugin.model.jaxb.SegGroupDesc;
import fr.lixbox.io.edi.ressource.EdiResources;
import fr.lixbox.io.xml.util.JaxbUtil;


/**
 *  Cette classe permet d'initialiser le systeme en
 *  récupérant les paramètres contenu dans le fichier
 *  de configuration.
 *  
 *  @author ludovic.terral
 */
public class PluginConfig implements Serializable
{
    // ----------- Attributs -----------   
    private static final long serialVersionUID = 4276149172556748743L;
    private static final Log LOG = LogFactory.getLog(PluginConfig.class);
    private static HashMap<String,Attribut> attributMap;
    private static HashMap<String,Balise> baliseMap;
    private static HashMap<String,Schema> schemaMap;
    private static EdiConfig ediConfig = null;
    
    
    
    // ----------- Methodes -----------
    static
    {
        try
        {
            String config = StreamStringUtil.read(PluginConfig.class.getResourceAsStream("/fr/lixbox/io/edi/config.xml"));
            LOG.debug(config);
            ediConfig = JaxbUtil.unMarshallValidate(EdiConfig.class,PluginConfig.class.getResourceAsStream("/fr/lixbox/io/edi/config.xsd"),config);
            
            if (ediConfig!=null
                    &&ediConfig.getAttributs()!=null
                    &&!CollectionUtil.isEmpty(ediConfig.getAttributs().getAttribut()))
            {
                attributMap = new HashMap<>();
                for (int i=0; i<ediConfig.getAttributs().getAttribut().size(); i++)
                {
                    attributMap.put(
                            (ediConfig.getAttributs().getAttribut().get(i)).getCode(),
                            ediConfig.getAttributs().getAttribut().get(i));
                }
            }
            
            if (ediConfig!=null
                    &&ediConfig.getBalises()!=null
                    &&!CollectionUtil.isEmpty(ediConfig.getBalises().getBalise()))
            {
                baliseMap = new HashMap<>();
                for (int i=0; i<ediConfig.getBalises().getBalise().size(); i++)
                {
                    baliseMap.put(
                            (ediConfig.getBalises().getBalise().get(i)).getNom(),
                            ediConfig.getBalises().getBalise().get(i));
                }  
            }
            
            if (ediConfig!=null
                    &&ediConfig.getSchemas()!=null
                    &&!CollectionUtil.isEmpty(ediConfig.getSchemas().getSchema()))
            {
                schemaMap = new HashMap<>();
                for (int i=0; i<ediConfig.getSchemas().getSchema().size(); i++)
                {
                    schemaMap.put(
                            (ediConfig.getSchemas().getSchema().get(i)).getCode()+
                            (ediConfig.getSchemas().getSchema().get(i)).getVersion(),
                            ediConfig.getSchemas().getSchema().get(i));
                }                
            }
        }
        catch (BusinessException e) 
        {
            throw new ProcessusException("Initialisation impossible",e);
        }
    }
    


    private PluginConfig()
    {        
        super();
    }
    
    
    
    /**
     * Cette methode permet de récuperer le descripteur
     * associe à un schéma 
     * 
     * @param schema
     * 
     * @return les descripteurs
     * 
     * @throws BusinessException
     */
    public static List<SegGroupDesc> getStructure(String schema, String version)
    throws BusinessException
    {
        if (schemaMap.get(schema+version)==null)
        {
            throw new BusinessException(EdiResources.getString("ERROR.FORMAT.INCONNU",schema));
        }
        return CollectionUtil.convertAnyListToArrayList(schemaMap.get(schema+version).getSegGroupDesc());
    }
    
    
    
    /**
     * Cette méthode permet de récuperer une balise
     * et sa description
     * 
     * @param nom
     * 
     * @return la balise
     * 
     * @throws BusinessException 
     */
    public static Balise getBalise(String nom) throws BusinessException
    {
        if (baliseMap.get(nom)==null)
        {
            throw new BusinessException(EdiResources.getString("ERROR.BALISE.INCONNUE",nom));
        }
        return baliseMap.get(nom);
    }
    
    
    
    /**
     * Cette méthode permet de récuperer un attribut
     * et sa description
     * 
     * @param nom
     * 
     * @return l'attribut
     * 
     * @throws BusinessException 
     */
    public static Attribut getAttribut(String nom) throws BusinessException
    {
        if (attributMap.get(nom)==null)
        {
            throw new BusinessException(EdiResources.getString("ERROR.ATTR.INCONNU",nom));
        }
        return attributMap.get(nom);
    }
    
    
    
    /**
     * Cette méthode permet de récuperer le descripteur
     * associé à un schéma 
     * 
     * @param schema
     * 
     * @return les descripteurs
     * 
     * @throws BusinessException 
     */
    public static SegGroupDesc getDescripteur(String schema, int i) 
    throws BusinessException
    {
        if (schemaMap.get(schema)==null)
        {
            throw new BusinessException(EdiResources.getString("ERROR.FORMAT.INCONNU",schema));
        }
        if (i>=schemaMap.get(schema).getSegGroupDesc().size())
        {
            throw new BusinessException(EdiResources.getString("ERROR.MESSAGE.PARAM"));
        }        
        return schemaMap.get(schema).getSegGroupDesc().get(i);
    }
}
