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
package fr.lixbox.io.edi.plugin.model.jaxb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for schema complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="schema">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="segGroupDesc" type="{}segGroupDesc" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="code" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="nom" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="version" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "schema", propOrder = { "segGroupDesc" })
public class Schema implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 3989355570008799516L;
    @XmlElement(required = true) private List<SegGroupDesc> segGroupDesc;
    @XmlAttribute(required = true) protected String code;
    @XmlAttribute(required = true) protected String nom;
    @XmlAttribute(required = true) protected String version;



    public List<SegGroupDesc> getSegGroupDesc()
    {
        if (segGroupDesc == null)
        {
            segGroupDesc = new ArrayList<>();
        }
        return this.segGroupDesc;
    }

    
    
    public String getCode()
    {
        return code;
    }
    public void setCode(String value)
    {
        this.code = value;
    }



    public String getNom()
    {
        return nom;
    }
    public void setNom(String value)
    {
        this.nom = value;
    }



    public String getVersion()
    {
        return version;
    }
    public void setVersion(String value)
    {
        this.version = value;
    }
}
