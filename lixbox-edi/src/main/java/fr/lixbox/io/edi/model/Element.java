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
package fr.lixbox.io.edi.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *  Cette classe stocke les attributs d'un element.
 *  
 *  @author ludovic.terral
 */
public class Element implements Serializable
{    
    // ----------- Attributs -----------
    private static final long serialVersionUID = -3350002158061402722L;
    private String nom;
    private List<Attribut> listeAttribut;
    
    
    
    // ----------- Methodes -----------    
    public Element()
    {
        super();
        this.nom= "";
        this.listeAttribut = new ArrayList<>();
    }    

    public Element(String nom, List<Attribut> attributs)
    {
        super();
        this.nom= nom;
        this.listeAttribut = attributs;
    }
    
    
    
    public String getNom()
    {
        return nom;
    }
    public void setNom(String nom)
    {
        this.nom = nom;
    }

    
    
    public List<Attribut> getListeAttribut()
    {
        return listeAttribut;
    }
    
    
    
    public Attribut getAttribut(int i)
    {
        if (listeAttribut!=null && i<listeAttribut.size())
        {
            return listeAttribut.get(i);
        }
        return null;
    } 
    public Attribut getAttribut(String cle, int index)
    {
        List<Attribut> liste = getAttribut(cle);        
        if (liste!=null && index<liste.size())
        {
            return liste.get(index);
        }
        return null;
    }  
    public List<Attribut>  getAttribut(String cle)
    {
        List<Attribut> liste = new ArrayList<>();
        for (Attribut seg : listeAttribut)
        {
            if (seg!=null&&
                    seg.getNom()!=null&&
                    cle!=null&&
                    seg.getNom().equalsIgnoreCase(cle))
            {
                liste.add(seg);
            }
        }
        return liste;
    } 
    public Attribut  getAttribut(String cle, String code)
    {
        for (Attribut elm : listeAttribut)
        {
            if (elm!=null&&
                    elm.getNom()!=null&&
                    elm.getValeur()!=null&&
                    elm.getNom().equalsIgnoreCase(cle)&&
                    elm.getValeur().equals(code))
            {
                return elm;
            }
        }
        return null;
    } 

    
    
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<listeAttribut.size();i++)
        {
            sb.append(listeAttribut.get(i)+":");
        } 
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }
}
