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
 * <p>Java class for attribut complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="attribut">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="code" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="size" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "attribut")
public class Attribut  implements Serializable
{
    private static final long serialVersionUID = -8507596711722863985L;
    @XmlAttribute(required = true) protected String code;
    @XmlAttribute(required = true) protected String name;
    @XmlAttribute(required = true) protected String type;
    @XmlAttribute(required = true) protected int size;

        
    
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



    public String getType()
    {
        return type;
    }
    public void setType(String value)
    {
        this.type = value;
    }



    public int getSize()
    {
        return size;
    }
    public void setSize(int value)
    {
        this.size = value;
    }
}
