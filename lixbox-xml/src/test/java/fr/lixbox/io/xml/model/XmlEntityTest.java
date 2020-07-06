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

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.lixbox.common.model.enumeration.NiveauEvenement;


/**
 * Cette classe est une entite de test.
 * 
 * @author ludovic.terral
 */
@XmlRootElement(name="XmlEntityTest")
@XmlType()
public class XmlEntityTest implements Serializable
{
    // ----------- Attribut -----------   
    protected static final Log LOG = LogFactory.getLog(XmlEntityTest.class);
    private static final long serialVersionUID = -681305381616951524L;

    private NiveauEvenement niveau;
    private String libelle;
    
    
    
    // ----------- Methode -----------
    public XmlEntityTest()
    {
        LOG.debug("<XmlEntityTest created>");
        this.libelle="defaut";
        this.niveau=NiveauEvenement.WARN;
    }
    
    
    
    public XmlEntityTest(NiveauEvenement niveau, 
            String libelle)
    {
        LOG.debug("<XmlEntityTest created>");
        this.libelle=libelle;
        this.niveau=niveau;
    }

    
 
    @XmlElement(required=true)    
    public String getLibelle()
    {
        return this.libelle;
    }
    public void setLibelle(String libelle)
    {
        this.libelle = libelle;
    }
    
    

    @XmlElement(required=true)
    public NiveauEvenement getNiveau()
    {
        return this.niveau;
    }
    public void setNiveau(NiveauEvenement niveau)
    {
        this.niveau = niveau;
    }
}
