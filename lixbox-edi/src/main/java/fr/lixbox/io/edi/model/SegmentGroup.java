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
