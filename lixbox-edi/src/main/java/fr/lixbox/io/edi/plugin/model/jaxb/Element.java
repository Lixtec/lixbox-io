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
 * Java class for element complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="element">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="attr-ref" type="{}attr-ref" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="code" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="isOblig" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="occurence" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="att-ref" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "element", propOrder = { "attrRef" })
public class Element implements Serializable
{
    private static final long serialVersionUID = 9046778683480020625L;
    @XmlElement(name = "attr-ref") private List<AttrRef> attrRef;
    @XmlAttribute protected String code;
    @XmlAttribute protected String name;
    @XmlAttribute protected boolean isOblig;
    @XmlAttribute protected int occurence;
    @XmlAttribute(name = "att-ref") private String attRef;
    


    public List<AttrRef> getAttrRef()
    {
        if (attrRef == null)
        {
            attrRef = new ArrayList<>();
        }
        return this.attrRef;
    }



    public String getCode()
    {
        return code;
    }
    public void setCode(String value)
    {
        this.code = value;
    }



    public String getName()
    {
        return name;
    }
    public void setName(String value)
    {
        this.name = value;
    }



    public Boolean isIsOblig()
    {
        return isOblig;
    }
    public void setIsOblig(Boolean value)
    {
        this.isOblig = value;
    }



    public Integer getOccurence()
    {
        return occurence;
    }
    public void setOccurence(Integer value)
    {
        this.occurence = value;
    }



    public String getAttRef()
    {
        return attRef;
    }
    public void setAttRef(String value)
    {
        this.attRef = value;
    }
}
