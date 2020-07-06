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
