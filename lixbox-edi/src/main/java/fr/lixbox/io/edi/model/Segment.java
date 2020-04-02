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

/**
 *  Cette classe stocke les elements d'un segment.
 *  
 *  @author ludovic.terral
 */
public class Segment implements Serializable
{    
    // ----------- Attributs -----------
    private String nom;
    private List<Element> listeElement;
    private static final long serialVersionUID = -430427553450809365L;
    
    
    
    // ----------- Methodes -----------    
    public Segment()
    {
        super();
        this.nom= "";
        this.listeElement = new ArrayList<>();
    }  

     
    
    public Segment(String nom, List<Element> listeElement)
    {
        super();
        this.nom= nom;
        this.listeElement = listeElement;
    } 
 
        
    
    public String getNom()
    {
        return nom;
    }
    public void setNom(String nom)
    {
        this.nom = nom;
    }



    public List<Element> getListeElement()
    {
        return listeElement;
    }


    
    public Element getElement(int i)
    {
        if (listeElement!=null && i<listeElement.size())
        {
            return listeElement.get(i);
        }
        return null;
    } 
    public Element getElement(String cle, int index)
    {
        List<Element> liste = getElement(cle);        
        if (liste!=null && index<liste.size())
        {
            return liste.get(index);
        }
        return null;
    }  
    public List<Element>  getElement(String cle)
    {
        List<Element> liste = new ArrayList<>();
        for (Element seg : listeElement)
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
    public Element  getElement(String cle, String code)
    {
        for (Element elm : listeElement)
        {
            if (elm!=null&&
                    elm.getNom()!=null&&
                    elm.getAttribut(0)!=null&&
                    elm.getAttribut(0).getValeur()!=null&&
                    elm.getNom().equalsIgnoreCase(cle)&&
                    elm.getAttribut(0).getValeur().equals(code))
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
        sb.append("\t\tSEGMENT: NAME="+getNom()+"\tVALEUR= ");   
        for (int i=0; i<listeElement.size();i++)
        {
            sb.append(listeElement.get(i)+"+");
        }      
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }  
}
