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

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.lixbox.common.exceptions.BusinessException;
import fr.lixbox.common.exceptions.ProcessusException;
import fr.lixbox.common.model.ConteneurEvenement;
import fr.lixbox.common.model.enumeration.NiveauEvenement;
import fr.lixbox.common.stream.util.StreamStringUtil;
import fr.lixbox.common.util.CollectionUtil;
import fr.lixbox.io.edi.model.Document;
import fr.lixbox.io.edi.model.Segment;
import fr.lixbox.io.edi.model.SegmentGroup;
import fr.lixbox.io.edi.plugin.PluginConfig;
import fr.lixbox.io.edi.plugin.model.jaxb.SegGroupDesc;
import fr.lixbox.io.edi.ressource.EdiResources;


/**
 *  Cette classe permet d'extraire un document à partir
 *  d'une chaine de caracteres.
 *  
 *  @author ludovic.terral
 */
public class Marshaller implements Serializable
{
    // ----------- Attribut -----------   
    private static final long serialVersionUID = 4276149172556748743L;
    private String chaineDocument;
    private static final Log LOG = LogFactory.getLog(Marshaller.class);
    

    // ----------- methode -----------
    public Marshaller(String chaine)
    {
        super();
        this.chaineDocument = chaine;
    }
    
        
    
    public Marshaller(InputStream is)
    {
        super();
        this.chaineDocument = StreamStringUtil.read(is);
    }
     
    

    /**
     * Cette méthode permet de mapper un document EDI dans
     * les objets Java associés.
     * 
     * @return l'image du document EDI dans les objets JAVA
     * 
     * @throws BusinessException
     */
    public Document marshall() throws BusinessException
    {        
        try        
        {            
            Document result = new Document();         
            
            /* ---------------------------------------
             * Extraire le document
             *-----------------------------------------*/
            ArrayList<Segment> listeSegment = new ArrayList<>(); 
            int iPrev = -1;
            int iFin = chaineDocument.indexOf('\'',iPrev);
            if (iFin>0)
            {
                while (chaineDocument.charAt(iFin-1) == '?')
                {
                    iFin = chaineDocument.indexOf('\'',iFin+1);
                }
            }
            EDITokenizer token=null;
            do
            {                  
                token = new EDITokenizer(chaineDocument.substring(iPrev+1,iFin));
                listeSegment.add(token.getSegment());
                iPrev = iFin;
                if (iFin>0)
                {
                    while (iFin!=-1 && (iFin==iPrev || '?' == (chaineDocument.charAt(iFin-1))))
                    {
                        iFin = chaineDocument.indexOf('\'',iFin+1);
                    }
                } 
            }            
            while (iFin!=-1);
            
            
            /* ---------------------------------------
             * Valider le document
             * -> forme des balises
             * -> respect des informations des balises de
             *    controle
             *-----------------------------------------*/
            ConteneurEvenement listeEvent = new ConteneurEvenement();
            for (Segment seg: listeSegment)
            {
                listeEvent.getEvenements().addAll(Validator.validateBalise(seg));
            }
                        
            
            if (!CollectionUtil.isEmpty(listeEvent.getEvenementTypeErreur()))
            {
                throw new BusinessException(EdiResources.getString("ERROR.BLOC"),listeEvent);
            }

            Segment UNB=listeSegment.get(0);
            Segment UNH=listeSegment.get(1);
            Segment UNT=listeSegment.get(listeSegment.size()-2);
            
            if (!UNT.getElement(1).getAttribut(0).getValeur().equals(UNH.getElement(0).getAttribut(0).getValeur()))
            {
                listeEvent.add( NiveauEvenement.ERROR,
                                 EdiResources.getString("ERROR.ELM.INCORRECT","CONTROL"),
                                 "balise","UNT");
            }
            
            if (!UNT.getElement(0).getAttribut(0).getValeur().equals(String.valueOf(listeSegment.size()-2)))
            {
                listeEvent.add( NiveauEvenement.ERROR,
                                EdiResources.getString("ERROR.ELM.INCORRECT.EXT",
                                        new String[]
                                         {"NBRE SEGMENT",
                                          UNT.getElement(0).getAttribut(0).getValeur(),
                                          String.valueOf(listeSegment.size()-2)}),
                                "balise","UNT");
            }
            if (!CollectionUtil.isEmpty(listeEvent.getEvenementTypeErreur()))
            {
                throw new BusinessException(EdiResources.getString("ERROR.BLOC"),listeEvent);
            }   
            
            
            /* ---------------------------------------
             * Extraire Entete document
             *-----------------------------------------*/
            result.setSyntaxType(UNB.getElement(0).getAttribut(0).getValeur());
            result.setSyntaxVersion(UNB.getElement(0).getAttribut(1).getValeur());
            result.setDateRedaction(UNB.getElement(3).getAttribut(0).getValeur(),UNB.getElement(3).getAttribut(1).getValeur());
            result.setControl(UNH.getElement(0).getAttribut(0).getValeur());            
            result.setDocType(UNH.getElement(1).getAttribut(0).getValeur());            
            result.setDocVersion(UNH.getElement(1).getAttribut(1).getValeur());            
            result.setDocRelease(UNH.getElement(1).getAttribut(2).getValeur());            
            result.setDocAgency(UNH.getElement(1).getAttribut(3).getValeur());            
            result.setSender(UNB.getElement(1));
            result.setReceiver(UNB.getElement(2));
            
            
            /* ---------------------------------------
             * Formater le document
             *-----------------------------------------*/           
            if (PluginConfig.getStructure(result.getDocType(), result.getDocRelease())!=null)
            {
                return formaterDocument(result, listeSegment);
            }           
            throw new BusinessException(
                    EdiResources.getString(
                            "ERROR.FORMAT.INCONNU",
                            result.getDocType()));
        }        
        catch (BusinessException be)
        {
            LOG.trace(be);
            throw be;
        }
        catch (ProcessusException e)
        {
            LOG.error(e, e);
            throw e;
        }
        catch (Exception e)
        {
            LOG.error(e, e);
            throw new ProcessusException(e);
        }  
    }
    
    

    /**
     * Cette methode permet de formater un document 
     * si le schema est connu.
     * 
     * @param document a formater
     * 
     * @return le document format�
     * 
     * @throws BusinessException 
     */
    private Document formaterDocument(Document document, List<Segment> listeSegment) 
    throws BusinessException
    {   
        List<SegmentGroup> listeGroupe=extractSegmentGroup(PluginConfig.getStructure(document.getDocType(), document.getDocRelease()),listeSegment);
        document.getListeGroupe().addAll(listeGroupe);
        return document;
    }
   
    
    
    /**
     * Cette methode permet d'extraire les segmentGroups contenus 
     * dans une liste de segments donnée
     * 
     * @param descriptor    liste des segmentGroup à rechercher
     * @param listeSegment  liste de segments à analyser
     * 
     * @return  la liste des segmentGroups trouvée
     */
    private List<SegmentGroup> extractSegmentGroup(List<SegGroupDesc> descriptor, List<Segment> listeSegment)
    { 
        SegmentGroup group=null;   
        ArrayList<SegmentGroup> listeResult= new ArrayList<>();
        if (descriptor==null)
        {
            return Collections.emptyList();
        }                               
       
        //Traitement des branches de l'arbre de meme niveau          
        try
        {
            int indexDescript = descripteurSuivant(descriptor,0,listeSegment);
            do
            {                   
                if (indexDescript!=-2 && indexDescript!=-1)
                {
                    group = extractSegmentGroupUnit(listeSegment,indexOf(descriptor,indexDescript,listeSegment));
                }
                else
                {
                    group = extractSegmentGroupUnit(listeSegment,indexOf(descriptor,descriptor.size()-1,listeSegment));
                }
                if (group != null)
                {
                    listeResult.add(group);
                }
                indexDescript=descripteurSuivant(descriptor,indexDescript,listeSegment);
            }
            while (((indexDescript<descriptor.size()&&indexDescript!=-1)||
                   (indexDescript==descriptor.size()-1&&indexDescript==-2)));
        }
        catch (Exception e) 
        {
            LOG.error(e);
        }
        
        
        if (!listeResult.isEmpty())
        {
            int indexDescriptPrev = 0;

            //Identification des groupes
            for (int i=0; i<listeResult.size();i++)
            {
                for (int j=indexDescriptPrev; j<descriptor.size();j++)                
                {
                    if  (listeResult.get(i)!= null &&
                            listeResult.get(i).getSegment(0)!= null &&
                            descriptor.get(j) != null &&
                            listeResult.get(i).getSegment(0).getNom()
                            .equalsIgnoreCase(descriptor.get(j).getInitBalise()))
                    {
                        indexDescriptPrev = j;
                        listeResult.get(i).setDescripteur(descriptor.get(j));
                        listeResult.get(i).setNom(descriptor.get(j).getName());
                        
                        //Analyse des branches de niveau n-1
                        if (!listeResult.get(i).getDescripteur().getSegGroupDesc().isEmpty())
                        {
                            List<SegmentGroup> tampon = extractSegmentGroup(
                                    (ArrayList<SegGroupDesc>) CollectionUtil.convertAnyListToArrayList(
                                            listeResult.get(i).getDescripteur().getSegGroupDesc()),
                                            listeResult.get(i).getListeSegment());                        
                            if (tampon!=null)
                            {
                                listeResult.get(i).getListeGroupe().addAll(tampon);
                            }
                        }
                    }
                }
            }
            
            //Recuperer les champs de la branche
            for (int i=0; i<listeResult.size();i++)
            {
                if  (listeResult.get(i)!= null &&
                        listeResult.get(i).getDescripteur()== null)
                {
                    listeSegment.addAll(listeResult.get(i).getListeSegment());
                    listeResult.remove(i);
                }
            }          
            return listeResult;
        }
        return Collections.emptyList();
    }
    
   
    
    /**
     * Cette methode permet d'extraire un segmentGroup 
     * contenu dans une liste de segments donnee
     * 
     * @param descriptor    segmentGroup à rechercher
     * @param listeSegment  liste de segments à analyser
     * 
     * @return  la liste des segmentGroups trouvée
     */
    private SegmentGroup extractSegmentGroupUnit(List<Segment> listeSegment, Integer indexNext)
    {
        if (listeSegment.isEmpty())
        {
            return null;
        }              
        if (indexNext==-1)
        {
            return null;
        }
        
        SegmentGroup group= new SegmentGroup();        
        group.getListeSegment().addAll(listeSegment.subList(0,indexNext));
        for (int i=0; i<indexNext;i++)
        {
            listeSegment.remove(0);
        }
        if (group.getListeGroupe().isEmpty()&&group.getListeSegment().isEmpty())
        {
            return null;
        }
        return group;
    }
    
    
    
    /**
     * Cette methode permet de trouver l'index de la première balise du prochain segmentGroup.
     */
    private Integer indexOf(List<SegGroupDesc> descript, int index, List<Segment> liste)
    {
        int i1 = -1;
        int i2 = -1;
        
        
        //trouver l'index du prochain groupe i1
        if (index != -2)
        {
            for (int i=index+1;i<descript.size();i++)
            {
                for (int j=1 ; j<liste.size();j++)
                {
                    if (liste.get(j).getNom().equals(descript.get(i).getInitBalise()))
                    {
                        if (i1!=-1 && j<i1)
                        {
                            i1 = j;
                        }
                        if (i1==-1)
                        {
                            i1= j;
                        }                
                    }
                }
            }
        }
        else
        {
            i1=-1;
        }
        
        
        //trouver l'index de la prochaine it�ration i2
        if (index!=-2)
        {
            for (int j=1 ; j<liste.size();j++)
            {
                if (liste.get(j).getNom().equals(descript.get(index).getInitBalise()))
                {
                    if (i2!=-1 && j<i2)
                    {
                        i2 = j;
                    }
                    if (i2==-1)
                    {
                        i2= j;
                    }  
                }
            }
        }
        else
        {
            for (int j=1 ; j<liste.size();j++)
            {
                if (liste.get(j).getNom().equals(descript.get(descript.size()-1).getInitBalise()))
                {
                    if (i2!=-1 && j<i2)
                    {
                        i2 = j;
                    }
                    if (i2==-1)
                    {
                        i2= j;
                    }  
                }
            }
        }
        
        if (i1!=-1 && i2!=-1 && i1>i2)
        {
            return i2;
        }
        if (i1!=-1 && i2!=-1 && i1<i2)
        {
            return i1;
        }
        if (i1!=-1 && i2==-1)
        {
            return i1;
        }
        if (i1==-1 && i2!=-1)
        {
            return i2;
        }    
        if (index==descript.size()-1)
        {
            return liste.size();
        }
        return -1;
    }
    
        
    
    /**
     * Cette methode permet de savoir si le prochain groupe est une itération ou le segmentGroup suivant.
     */
    private int descripteurSuivant(List<SegGroupDesc> descript, int index, List<Segment> liste)
    {
        int i1 = -1;
        int j1= -1;
        int i2 = -1;        
        int j2= -1;      
        int i3 = -1;        
        int j3 = -1;
        
        
        //trouver l'index du prochain groupe i1 
        if (index != -2)
        {
            for (int i=index+1;i<descript.size();i++)
            {
                for (int j=1 ; j<liste.size();j++)
                {
                    if (liste.get(j).getNom().equals(descript.get(i).getInitBalise()))
                    {
                        if (i1!=-1 && j<j1)
                        {
                            i1 = i;
                            j1=j;
                        }
                        if (i1==-1)
                        {
                            i1= i;
                            j1=j;
                        }                
                    }
                }
            }
        }
        else
        {
            i1=-1;
        }
    
        
        //trouver l'index de la prochaine it�ration i2
        if (index!=-2)
        {
            for (int j=1 ; j<liste.size();j++)
            {
                if (liste.get(j).getNom().equals(descript.get(index).getInitBalise()))
                {
                    if (i2!=-1 && j<j2)
                    {
                        i2 = index;
                        j2=j;
                    }
                    if (i2==-1)
                    {
                        i2= index;
                        j2=j;
                    }  
                }
            }
        }
        else
        {           
            for (int j=0 ; j<liste.size();j++)
            {
                if (liste.get(j).getNom().equals(descript.get(descript.size()-1).getInitBalise()))
                {
                    if (i2!=-1 && j<j2)
                    {
                        i2 = index;
                        j2=j;
                    }
                    if (i2==-1)
                    {
                        i2= index;
                        j2=j;
                    }  
                }
            }            
        }
        
        
        
        //v�rifier si l'it�ration en r�ellement une
        if (index != -2)
        {
            if (i2!=-1)
            {
                for (int i=index;i<descript.size();i++)
                {
                    for (int j=0 ; j<liste.subList(0,i2).size();j++)
                    {
                        if (liste.get(j).getNom().equals(descript.get(i).getInitBalise()))
                        {
                            if (i3!=-1 && j<j3)
                            {
                                i3 = i;
                                j3=j;
                            }
                            if (i3==-1)
                            {
                                i3= i;
                                j3=j;
                            }                
                        }
                    }
                }
            }
        }
        else
        {
            i3=-1;
        }           

        
        if (i1!=-1)
        {
            if (i2!=-1)
            {
                if (i3!=-1)
                {
                    if (i1<i2 && i1<i3)
                    {
                        return i1;
                    }   
                    if (i2==i3 && i1>i2)
                    {
                        return i2;
                    }        
                    if (i1>i2 && i2!=i3 && j2<j3)
                    {
                        return i2;
                    } 
                    if (i1>i2 && i2!=i3 && j2>j3)
                    {
                        return i3;
                    }  
                }
                else
                {            
                    if (i1>i2)
                    {
                        return i2;
                    }        
                    if (i1<=i2)
                    {
                        return i1;
                    }
                }
            }
            else
            {
                return i1;
            }
        }
        else
        {            
            if (i2!=-1)
            {
                if (i3!=-1)
                {
                    if (j2==j3)
                    {
                        return i2;
                    }        
                    if (i2<i3 && j2<j3)
                    {
                        return i2;
                    }
                    if (i2<i3 && j2>j3)
                    {
                        return i3;
                    }
                }
                else
                {            
                    return i2;
                }
            }
        }
        
        if (index==descript.size()-1)
        {
            return -2;
        } 

        return -1;
    }
}
