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

import fr.lixbox.io.edi.plugin.model.jaxb.SegGroupDesc;

/**
 * Cette classe stocke les groupes de segments
 *  d'un document.
 * 
 * @author ludovic.terral
 */
public class SegmentGroup implements Serializable
{   
    // ----------- Attributs -----------
    private static final long serialVersionUID = -109495718600551195L;
    private String nom;
    private SegGroupDesc descripteur;
    private List<Segment> listeSegment;
    private List<SegmentGroup> listeGroupe;

    

    // ----------- Methodes -----------
    public SegmentGroup()
    {
        super();
        this.nom="SG";
        this.listeSegment = new ArrayList<>();
        this.listeGroupe = new ArrayList<>();
    }

    

    public List<Segment> getListeSegment()
    {
        return listeSegment;
    }
        

    
    /**
     * Renvoie un segment de la liste
     * 
     * @return un segment
     */
    public Segment getSegment(int i)
    {
        if (listeSegment!=null && i<listeSegment.size())
        {
            return listeSegment.get(i);
        }
        return null;
    } 
    public Segment getSegment(String cle, int index)
    {
        List<Segment> liste = getSegment(cle);        
        if (liste!=null && index<liste.size())
        {
            return liste.get(index);
        }
        return null;
    }  
    public List<Segment>  getSegment(String cle)
    {
        List<Segment> liste = new ArrayList<>();
        for (Segment seg : listeSegment)
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
    public Segment  getSegment(String cle, String code)
    {
        for (Segment seg : listeSegment)
        {
            if (seg!=null&&
                    seg.getNom()!=null&&
                    cle!=null&&
                    seg.getNom().equalsIgnoreCase(cle)&&
                    seg.getElement(0)!=null && 
                    seg.getElement(0).getAttribut(0)!=null &&
                    seg.getElement(0).getAttribut(0).getValeur()!=null &&
                    seg.getElement(0).getAttribut(0).getValeur().equals(code))
            {
                return seg;
            }
        }
        return null;
    } 

    
    public List<SegmentGroup> getListeGroupe()
    {
        return listeGroupe;
    }
    public SegmentGroup getGroupe(int i)
    {
        if (listeGroupe!=null && i<listeGroupe.size())
        {
            return listeGroupe.get(i);
        }
        return null;
    }  
    public SegmentGroup getGroupe(String cle, int index)
    {
        List<SegmentGroup> liste = getGroupe(cle);        
        if (liste!=null && index<liste.size())
        {
            return liste.get(index);
        }
        return null;
    }  
    public List<SegmentGroup>  getGroupe(String cle)
    {
        List<SegmentGroup> liste = new ArrayList<>();
        for (SegmentGroup seg : listeGroupe)
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


    
    public String getNom()
    {
        return nom;
    }
    public void setNom(String nom)
    {
        this.nom = nom;
    }
    
    

    public SegGroupDesc getDescripteur()
    {
        return descripteur;
    }
    public void setDescripteur(SegGroupDesc descripteur)
    {
        this.descripteur = descripteur;
    }


    
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();    
        for (int i=0; i<listeSegment.size();i++)
        {
            sb.append(listeSegment.get(i)+"\n");
        }  
        for (int i=0; i<listeGroupe.size();i++)
        {            
            sb.append(listeGroupe.get(i));
        }
        return sb.toString();
    }
}
