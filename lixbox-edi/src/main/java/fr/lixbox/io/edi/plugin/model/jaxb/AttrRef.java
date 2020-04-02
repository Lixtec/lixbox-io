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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for attr-ref complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="attr-ref">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="code" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="isOblig" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "attr-ref")
public class AttrRef  implements Serializable
{
    private static final long serialVersionUID = 7309400096002032682L;
    @XmlAttribute(required = true) protected String code;
    @XmlAttribute(required = true) protected boolean isOblig;

    
        
    public String getCode()
    {
        return code;
    }
    public void setCode(String value) 
    {
        this.code = value;
    }

        
    
    public boolean isIsOblig() 
    {
        return isOblig;
    }
    public void setIsOblig(boolean value) 
    {
        this.isOblig = value;
    }
}
