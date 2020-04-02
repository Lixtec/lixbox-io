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
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for segGroupDesc complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="segGroupDesc">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="segGroupDesc" type="{}segGroupDesc" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="initBalise" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="occurence" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "segGroupDesc", propOrder = { "segGroupDesc" })
public class SegGroupDesc implements Serializable
{
    private static final long serialVersionUID = 8946062327101527045L;
    private List<SegGroupDesc> segGroupDesc;
    @XmlAttribute(required = true) protected String name;
    @XmlAttribute(required = true) protected String initBalise;
    @XmlAttribute(required = true) protected int occurence;


    
    public List<SegGroupDesc> getSegGroupDesc()
    {
        if (segGroupDesc == null)
        {
            segGroupDesc = new ArrayList<>();
        }
        return this.segGroupDesc;
    }

    
    
    public String getName()
    {
        return name;
    }
    public void setName(String value)
    {
        this.name = value;
    }


    
    public String getInitBalise()
    {
        return initBalise;
    }
    public void setInitBalise(String value)
    {
        this.initBalise = value;
    }



    public int getOccurence()
    {
        return occurence;
    }
    public void setOccurence(int value)
    {
        this.occurence = value;
    }
}
