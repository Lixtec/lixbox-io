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
package fr.lixbox.io.edi.plugin.model.jaxb;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for anonymous complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}attributs"/>
 *         &lt;element ref="{}balises"/>
 *         &lt;element ref="{}schemas"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "attributs", "balises", "schemas" })
@XmlRootElement(name = "edi-config")
public class EdiConfig implements Serializable
{
    private static final long serialVersionUID = 3169157873246056925L;
    @XmlElement(required = true) protected Attributs attributs;
    @XmlElement(required = true) protected Balises balises;
    @XmlElement(required = true) protected Schemas schemas;



    public Attributs getAttributs()
    {
        return attributs;
    }
    public void setAttributs(Attributs value)
    {
        this.attributs = value;
    }



    public Balises getBalises()
    {
        return balises;
    }
    public void setBalises(Balises value)
    {
        this.balises = value;
    }



    public Schemas getSchemas()
    {
        return schemas;
    }
    public void setSchemas(Schemas value)
    {
        this.schemas = value;
    }
}
