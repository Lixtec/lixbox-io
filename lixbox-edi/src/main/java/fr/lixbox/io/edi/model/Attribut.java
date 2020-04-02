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

/**
 *  Cette classe stocke un attribut d'un element.
 *  
 *  @author ludovic.terral
 */
public class Attribut implements Serializable
{    
    // ----------- Attributs -----------
    private static final long serialVersionUID = -3380002158061402722L;
    private String nom;
    private String valeur;
    
    
    
    // ----------- Methodes -----------    
    public Attribut(String nom, String valeur)
    {
        super();
        setNom(nom);
        setValeur(valeur);
    } 
    
    
    
    public String getNom()
    {
        return nom;
    }
    public void setNom(String nom)
    {
        this.nom = nom;
    }



    public String getValeur()
    {
        return valeur;
    }
    public void setValeur(String valeur)
    {
        this.valeur = valeur;
    }



    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(getValeur());     
        return sb.toString();
    }
}
