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
package fr.lixbox.io.edi.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import fr.lixbox.common.exceptions.BusinessException;
import fr.lixbox.common.model.ConteneurEvenement;
import fr.lixbox.common.model.Evenement;
import fr.lixbox.common.model.enumeration.NiveauEvenement;
import fr.lixbox.common.util.StringUtil;
import fr.lixbox.io.edi.model.Segment;
import fr.lixbox.io.edi.plugin.PluginConfig;
import fr.lixbox.io.edi.plugin.model.jaxb.AttrRef;
import fr.lixbox.io.edi.plugin.model.jaxb.Attribut;
import fr.lixbox.io.edi.plugin.model.jaxb.Balise;
import fr.lixbox.io.edi.plugin.model.jaxb.Element;
import fr.lixbox.io.edi.ressource.EdiResources;


/**
 *  Cette classe permet d'extraire un document à partir
 *  d'une chaine de caracteres.
 *  
 *  @author ludovic.terral
 */
public class Validator implements Serializable
{
    // ----------- Attribut -----------   
    private static final long serialVersionUID = 4276149172556748743L;
    

    // ----------- Methode -----------
    private Validator()
    {
        super();
    }
    
 
    
    /**
     * Cette methode permet de valider un groupe de balise
     * 
     * @param listSeg
     * 
     * @return une collection d'evenements de validation.
     * 
     * @throws BusinessException 
     */
    public static List<Evenement> validateBalise(List<Segment> listSeg) 
    throws BusinessException
    {
        List<Evenement> liste = new ArrayList<>();
        for (Segment seg: listSeg)
        {
            liste.addAll(validateBalise(seg));
        }
        return liste;
    }
    
    
    
    /**
     * Cette methode permet de verifier la conformite des champs de la 
     * balise en verifiant en particulier:
     * -> la presence,
     * -> la taille,
     * -> le type de contenu.
     *  
     * @param seg
     * 
     * @return une collection d'evenements de validation.
     * 
     * @throws BusinessException 
     */
    public static List<Evenement> validateBalise(Segment seg) throws BusinessException
    {
        ConteneurEvenement listeEvent = new ConteneurEvenement();   
        Balise balise = PluginConfig.getBalise(seg.getNom());
        
        if (balise==null)
        {
            listeEvent.add(NiveauEvenement.ERROR,
                           EdiResources.getString("ERROR.BALISE.INCONNUE",seg.getNom()),
                           "NomBalise",
                           seg.getNom());
            throw new BusinessException(EdiResources.getString("ERROR.BLOC",seg.getNom()),listeEvent);
        }
        
        
        //Verification de la presence des champs obligatoires:
        for (int i=0; i<balise.getElement().size();i++)
        {
            Element elmJaxb=balise.getElement().get(i);

            if (elmJaxb.isIsOblig() && seg.getElement(i)==null)
            {
                String codeAttrib = "";
                if (elmJaxb.getCode()!=null)
                {
                    codeAttrib = elmJaxb.getCode()+"."+elmJaxb.getAttRef();
                }
                else
                {
                    codeAttrib = elmJaxb.getAttRef();
                }
                listeEvent.add(NiveauEvenement.ERROR,
                               EdiResources.getString("ERROR.MESSAGE.ATTR.OBLIGATOIRE",codeAttrib),"NomBalise",seg.getNom()); 
            }
        }   
        
        
        /* 
         * Controle sur chaque champs non null
         * -> presence des elements obligatoires
         * -> controle du type de donnee
         * -> controle de la longueur de donnees
         */
        for (int i=0; i<balise.getElement().size();i++)
        {
            Element elmJaxb=balise.getElement().get(i);
            fr.lixbox.io.edi.model.Element elm = null;
            
            if (i<seg.getListeElement().size())
            {
                elm = seg.getListeElement().get(i);
            }
            else
            {
                elm=null;
            }
            
            if (!StringUtil.isEmpty(elmJaxb.getAttRef()))
            {
                Attribut attrJaxb = PluginConfig.getAttribut(elmJaxb.getAttRef());                
                if (elm!=null && 
                        elm.getListeAttribut()!=null && 
                        elm.getListeAttribut().size()>1)
                {
                    listeEvent.add(NiveauEvenement.ERROR,
                                   EdiResources.getString("ERROR.MESSAGE.ATTR.MALFORME",elm),
                                   "NomBalise",seg.getNom()); 
                    break;
                }
                if (elm!=null)
                {
                    elm.setNom(attrJaxb.getCode());
                    listeEvent.add(validateAttribut(null,attrJaxb,elm.getAttribut(0),seg.getNom()));
                }
            }
            else
            {
                for (int j=0; j<elmJaxb.getAttrRef().size();j++)
                {
                    AttrRef attrRef =elmJaxb.getAttrRef().get(j);
                    Attribut attrJaxb = PluginConfig.getAttribut(attrRef.getCode());
                    if (attrRef.isIsOblig()&&elm!=null&&elm.getAttribut(j)==null)
                    {
                        listeEvent.add( NiveauEvenement.ERROR,
                                        EdiResources.getString("ERROR.MESSAGE.ATTR.OBLIGATOIRE",attrRef.getCode()),
                                        "NomBalise",seg.getNom()); 
                        break;
                    }
                    if (elm!=null && elm.getAttribut(j)!=null)
                    {
                        elm.getAttribut(j).setNom(attrJaxb.getCode());
                        listeEvent.add(validateAttribut(elmJaxb.getCode(),attrJaxb,elm.getAttribut(j),seg.getNom()));
                    }
                }
                if (elm!=null)
                {
                    elm.setNom(elmJaxb.getCode());
                }
            }
        } 
        return listeEvent.getEvenements();
    }
    

    
    /**
     * Cette methode permet de verifier la conformite d'un champ:
     * Controle du type 
     *  si AN verifie la longueur
     *  si N  verifie le type int
     *  
     * @param elmCode : code l'element complexe
     * @param attrJaxb : description de l'attribut de reference
     * @param attr : attribut a valider
     * @param baliseName : nom de la balise
     * 
     * @return une collection d'evenements de validation.
     */    
    public static List<Evenement> validateAttribut(String elmCode, Attribut attrJaxb, fr.lixbox.io.edi.model.Attribut attr, String baliseName)
    {
        String codeAttrib = "";
        if (elmCode!=null)
        {
            codeAttrib = elmCode+"."+attrJaxb.getCode();
        }
        else
        {
            codeAttrib = attrJaxb.getCode();
        }
        
        ConteneurEvenement listeEvent = new ConteneurEvenement();
        if (attr==null || baliseName==null)
        {
            listeEvent.add( NiveauEvenement.ERROR,
                            EdiResources.getString(
                                    "ERROR.MESSAGE.PARAM"),
                                    "sequence",baliseName);
            return listeEvent.getEvenements();
        }
        if(attr.getValeur()!=null)
        {            
            if ("AN".equals(attrJaxb.getType()))
            {
                if (attr.getValeur().length()>attrJaxb.getSize())
                {
                    listeEvent.add( NiveauEvenement.ERROR,
                                    EdiResources.getString(
                                            "ERROR.MESSAGE.ATTR.LENGTH",
                                            new Object[] 
                                            {
                                                codeAttrib,
                                                attrJaxb.getSize()}),
                                                "sequence",baliseName);                       
                }
            }
            else
            {
                try
                {                
                    Integer.valueOf(StringUtil.trim(attr.getValeur()));
                }
                catch(Exception e)
                {
                    listeEvent.add( NiveauEvenement.ERROR,
                                    EdiResources.getString(
                                            "ERROR.MESSAGE.ATTR.TYPE",
                                            new Object[] 
                                                {codeAttrib, attrJaxb.getType()}),
                                                "sequence",baliseName);             
                }                    
            }
        }
        return listeEvent.getEvenements();
    }
}
