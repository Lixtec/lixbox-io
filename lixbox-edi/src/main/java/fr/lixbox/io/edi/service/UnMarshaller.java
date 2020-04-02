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
package fr.lixbox.io.edi.service;

import java.io.Serializable;
import java.util.Calendar;

import fr.lixbox.common.exceptions.BusinessException;
import fr.lixbox.common.model.ConteneurEvenement;
import fr.lixbox.common.util.DateUtil;
import fr.lixbox.common.util.StringUtil;
import fr.lixbox.io.edi.model.Document;
import fr.lixbox.io.edi.model.Element;
import fr.lixbox.io.edi.model.Segment;
import fr.lixbox.io.edi.model.SegmentGroup;
import fr.lixbox.io.edi.ressource.EdiResources;



/**
 *  Cette classe genere une chaine EDI image du
 *  document JAVA
 *  
 *  @author ludovic.terral
 */
public class UnMarshaller implements Serializable
{  
    // ----------- Attribut -----------   
    private static final long serialVersionUID = 427614917256748743L;
    private Document doc;

    

    // ----------- Methode -----------
    public UnMarshaller(Document doc)
    {
        super();
        this.doc = doc;
    }
     
    

    /**
     * Cette methode permet de mapper un document EDI dans
     * les objets Java associes.
     * 
     * @return l'image du document EDI sous forme de chaine
     * 
     * @throws BusinessException 
     */
    public String unMarshall() throws BusinessException
    {
        StringBuilder sb = new StringBuilder();
        sb.append(representEnteDocumentToEdi(doc.getSyntaxType(),doc.getSyntaxVersion(),doc.getDocType(),
                doc.getDocVersion(),doc.getDocRelease(), doc.getDocAgency(),doc.getDateRedaction(),
                doc.getControl(),doc.getSender(),
                doc.getReceiver()));
        for (Integer i=0; i<doc.getListeGroupe().size();i++)
        {
            if (doc.getGroupe(i) != null)
            {
                    sb.append(representSegmentGroupToEdi(doc.getGroupe(i)));
            }
        }         
        return sb.toString(); 
    }  
    
    
    
    /**
     * Cette methode permet de generer les balises 
     * UNB et UNH du document.
     * 
     * @param typeSyntaxe
     * @param versionSyntax
     * @param typeDoc
     * @param versionDoc
     * @param releaseDoc
     * @param agency           
     * @param date 
     * @param CRC   
     * @param sender            
     * @param receiver
     * 
     * @return une chaine
     */ 
    public String representEnteDocumentToEdi(
            String typeSyntaxe, 
            String versionSyntax, 
            String typeDoc,
            String versionDoc,
            String releaseDoc,
            String agency,            
            Calendar date,  
            String CRC,   
            Element sender,            
            Element receiver)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("UNB"+"+"+typeSyntaxe+":"+versionSyntax+"+");
        for (int i=0;i<sender.getListeAttribut().size();i++)
        {
            sb.append(sender.getAttribut(i));
            if (i<(sender.getListeAttribut().size()-1))
            {
                sb.append(":");
            }
        }
        sb.append("+");
        for (int i=0;i<receiver.getListeAttribut().size();i++)
        {
            sb.append(receiver.getAttribut(i));
            if (i<(receiver.getListeAttribut().size()-1))
            {
                sb.append(":");
            }
        }
        sb.append("+"+DateUtil.format(date.getTime(),"ddMMyyyy:hhmmss")+"+"+
                  CRC+"+"+"++"+"+"+"+"+"PAM"+"'\n");
        sb.append("UNH"+"+"+CRC+"+"+typeDoc+"+"+versionDoc+"+"+releaseDoc+"+"+
                  agency+"'\n");        
        return sb.toString();   
    }    
    
    
    
    /**
     * Cette methode permet de representer un champs d'une balise
     * 
     * @param element
     * 
     * @return une chaine image de l'element
     */
    public String representElementToEdi(Element element)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("+");
        if (element!=null && element.getListeAttribut()!=null)
        {
            for (Integer i=0; i<element.getListeAttribut().size();i++)
            {
                if (element.getAttribut(i)!=null && !StringUtil.isEmpty(element.getAttribut(i).getValeur()))
                {
                    sb.append(element.getAttribut(i).getValeur().trim());                      
                }
                else
                {
                    sb.append("");
                }
                if (i<(element.getListeAttribut().size()-1))
                {
                    sb.append(":");
                }
            }    
        }
        else
        {
            sb.append("");
        }
        return sb.toString();        
    }    
    
    
    
    /**
     * Cette methode permet de representer
     * une balise EDI.
     * 
     * @param segment
     * 
     * @return une chaine
     */
    public String representSegmentToEdi(Segment segment)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(segment.getNom());  
        for (Integer i=0; i<segment.getListeElement().size();i++)
        {
            sb.append(representElementToEdi(segment.getElement(i)));
        }               
        sb.append("'\n");  
        return sb.toString();        
    }    
    
    
    
    /**
     * Cette methode permet de representer un groupe de segment
     * 
     * @param groupe
     * 
     * @return une chaine
     * 
     * @throws BusinessException 
     */
    public String representSegmentGroupToEdi(SegmentGroup groupe) 
    throws BusinessException
    {
        ConteneurEvenement listeEvent = new ConteneurEvenement();
        listeEvent.add(Validator.validateBalise(groupe.getListeSegment()));
        if (!listeEvent.getEvenements().isEmpty())
        {
            throw new BusinessException(EdiResources.getString("ERROR.BLOC"),listeEvent);
        }
        StringBuilder sb = new StringBuilder();
        for (Integer i=0; i<groupe.getListeSegment().size();i++)
        {
             if (groupe.getSegment(i)!=null)
             {
                 sb.append(representSegmentToEdi(groupe.getSegment(i)));
             }
        }  
        for (Integer i=0; i<groupe.getListeGroupe().size();i++)
        {
            if (groupe.getGroupe(i)!=null) 
            {
                sb.append(representSegmentGroupToEdi(groupe.getGroupe(i)));
            }
        }     
        return sb.toString(); 
    }
}
