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
