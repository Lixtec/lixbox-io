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
package fr.lixbox.io.edi.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.lixbox.common.exceptions.BusinessException;
import fr.lixbox.common.exceptions.ProcessusException;
import fr.lixbox.io.edi.model.Attribut;
import fr.lixbox.io.edi.model.Element;
import fr.lixbox.io.edi.model.Segment;
import fr.lixbox.io.edi.ressource.EdiResources;

/**
 *  Cette classe extrait les élements d'un segment EDI à partir
 *  d'une chaine de caractères.
 *  
 *  @author ludovic.terral
 */
public class EDITokenizer
{
    // ----------- Attributs -----------
    private String chaineElement;
    private static final Log LOG = LogFactory.getLog(EDITokenizer.class);
    
    
    
    // ----------- Méthodes -----------       
    public EDITokenizer(String chaine)
    {
        super();
        this.chaineElement = chaine;
    }
    
    
    
    /**
     * Cette méthode permet d'extraire une balise dans un objet java 
     * 
     * @return un objet java image de la balise
     * 
     * @throws BusinessException
     */
    public Segment getSegment() throws BusinessException
    {        
        try        
        {          
            Segment result = new Segment();
            result.setNom(chaineElement.trim().substring(0,3));
            int i = chaineElement.indexOf('+');
            if (i==-1)
            {
                throw new BusinessException(EdiResources.getString("ERROR.SEGMENT.ELM.ABSENT"));
            }
            int iPrev = i;
            while (i!=-1)
            {
                i=chaineElement.indexOf('+',i+1);
                if (i!=-1)
                {
                    if (chaineElement.substring(iPrev+1,i).length()>0)
                    {
                        if ("COM".equalsIgnoreCase(result.getNom()) && "?".equalsIgnoreCase(chaineElement.substring(iPrev+1,i)))
                        {
                        	result.getListeElement().add(getElement("elm",chaineElement.substring(iPrev+2, chaineElement.length())));
						}
                        else
                        {
							result.getListeElement().add(getElement("elm",chaineElement.substring(iPrev+1,i)));
						}
                    	
                    }
                    else
                    {
                        result.getListeElement().add(null);
                    }
                }
                else
                {
                    if (chaineElement.substring(iPrev+1).length()>0)
                    {
                        result.getListeElement().add(getElement("elm",chaineElement.substring(iPrev+1)));
                    }
                    else
                    {
                        result.getListeElement().add(null);
                    }                    
                }
                iPrev=i;
            }     
            return result;
        }        
        catch (BusinessException be)
        {
            LOG.trace(be);
            throw be;
        }
        catch (ProcessusException e)
        {
            LOG.error(e, e);
            throw e;
        }
        catch (Exception e)
        {
            LOG.error(e, e);
            throw new ProcessusException(e);
        } 
    }

    
    
    /**
     * Cette méthode permet d'extraire un élément constituant
     * un segment.
     * 
     * @param nom
     * @param chaine
     * 
     * @return un element
     */
    private Element getElement(String nom,String chaine)
    {
        Element result = new Element();
        result.setNom(nom);
        int i = chaine.indexOf(':');
        if (i==-1)
        { 
          
        	 result.getListeAttribut().add(new Attribut("attr",chaine));
    	  
        }
        else
        {           
            int iPrev = 0;
            while (i!=-1)
            {
                if (i-iPrev!=0)
                {
                    result.getListeAttribut().add(new Attribut("attr",chaine.substring(iPrev,i))); 
                }
                else
                {
                    result.getListeAttribut().add(new Attribut("attr",null));
                }
                iPrev=i+1;
                i=chaine.indexOf(':',iPrev);
                
            }
            result.getListeAttribut().add(new Attribut("attr",chaine.substring(iPrev)));
        }
        return result;
    }
    
    
    
    @Override
    public String toString()
    {
        return chaineElement;
    }
}
