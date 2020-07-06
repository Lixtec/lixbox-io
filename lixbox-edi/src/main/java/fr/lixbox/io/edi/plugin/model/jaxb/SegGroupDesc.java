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
