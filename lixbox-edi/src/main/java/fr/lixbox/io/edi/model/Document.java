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
import java.util.Calendar;
import java.util.List;

import fr.lixbox.common.util.DateUtil;
import fr.lixbox.common.util.StringUtil;

/**
 * Cette classe stocke les attributs d'un document EDI.
 * 
 * @author ludovic.terral
 */
public class Document implements Serializable
{   
    // ----------- Attributs -----------
    private static final long serialVersionUID = -1090495718600551195L;
    private String syntaxType;
    private String syntaxVersion;
    private Calendar dateRedaction;
    private String control;
    private String docType;
    private String docVersion;
    private String docRelease;
    private String docAgency;
    private Element sender;
    private Element receiver;
    private List<SegmentGroup> listeGroupe;


    
    // ----------- Methodes -----------
    public Document()
    {
        super();
        this.syntaxType = null;
        this.docType = null;
        this.dateRedaction = Calendar.getInstance();
        this.listeGroupe = new ArrayList<>();
    }

    

    public String getControl()
    {
        return control;
    }
    public void setControl(String control)
    {
        this.control = control;
    }


    
    public Calendar getDateRedaction()
    {
        return dateRedaction;
    }
    public void setDateRedaction(Calendar dateRedaction)
    {
        this.dateRedaction = dateRedaction;
    }
    public void setDateRedaction(String date, String time)
    {
        if (date.length()==6)
        {
            this.dateRedaction.set
            (
                Integer.parseInt(date.substring(0,2))+2000,
                Integer.parseInt(date.substring(2,4))-1,
                Integer.parseInt(date.substring(4,6))
            );
        }
        if (time.length()==3)
        {
            this.dateRedaction.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time.substring(0,1)));
            this.dateRedaction.set(Calendar.MINUTE, Integer.parseInt(time.substring(1,3)));
            this.dateRedaction.set(Calendar.SECOND, 0);
        }
        if (time.length()==4)
        {
            this.dateRedaction.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time.substring(0,2)));
            this.dateRedaction.set(Calendar.MINUTE, Integer.parseInt(time.substring(2,4)));
            this.dateRedaction.set(Calendar.SECOND, 0);
        }
        if (time.length()==6)
        {
            this.dateRedaction.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time.substring(0,2)));
            this.dateRedaction.set(Calendar.MINUTE, Integer.parseInt(time.substring(2,4)));
            this.dateRedaction.set(Calendar.SECOND, Integer.parseInt(time.substring(4,6)));
        }
    }

    

    public String getDocAgency()
    {
        return docAgency;
    }
    public void setDocAgency(String docAgency)
    {
        this.docAgency = docAgency;
    }

    
    
    public String getDocRelease()
    {
        return docRelease;
    }
    public void setDocRelease(String docRelease)
    {
        this.docRelease = docRelease;
    }

    

    public String getDocType()
    {
        return docType;
    }
    public void setDocType(String docType)
    {
        this.docType = docType;
    }


    
    public String getDocVersion()
    {
        return docVersion;
    }
    public void setDocVersion(String docVersion)
    {
        this.docVersion = docVersion;
    }


    
    public Element getReceiver()
    {
        return receiver;
    }
    public void setReceiver(Element receiver)
    {
            this.receiver = receiver;
    }


    
    public Element getSender()
    {
        return sender;
    }
    public void setSender(Element sender)
    {
        this.sender = sender;
    }


    public String getSyntaxType()
    {
        return syntaxType;
    }
    public void setSyntaxType(String syntaxType)
    {
        this.syntaxType = syntaxType;
    }
    


    public String getSyntaxVersion()
    {
        return syntaxVersion;
    }
    public void setSyntaxVersion(String syntaxVersion)
    {
        this.syntaxVersion = syntaxVersion;
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
            if (seg!=null && 
                    seg.getNom()!=null && 
                    cle!=null&&
                    seg.getNom().equalsIgnoreCase(cle))
            {
                liste.add(seg);
            }
        }
        return liste;
    } 
    
    
    
    /**
     * Cette méthode permet d'obtenir une valeur ou un objet 
     * d'une branche de données du document
     * (G)[1].(S)[1].(A)[2]
     * <ul>
     * <li>(G) indique un groupe de segment</li>
     * <li>(S) indique un segment</li>
     * <li>(E) indique un element</li>
     * <li>(A) indique un attribut</li>
     * <li>[n] indique l'index de l'objet à atteindre</li>
     * <li>[/n/] indique le code de l'objet à atteindre</li>
     * <li>{N} indique le nom du groupe</li>
     * </ul>
     * 
     * @param token la pattern de parcours
     * @param node  l'objet à parcourir
     * 
     * @return l'objet correspondant à la zone.
     */
    private Object getNode(String token, Object node)
    {
        if (token.length()==0)
        {
            if (node instanceof Attribut)
            {
                return node.toString();
            }
            return node;
        }
        
        /** 
         * Parcours de l'arbre niveau Document 
         **/
        if (node instanceof Document)
        {           
            String index = StringUtil.substringBetween(StringUtil.substringBefore(token,"."),"[","]");                
            String type  = StringUtil.substringBetween(StringUtil.substringBefore(token,"."),"(",")");
            String name  = StringUtil.substringBetween(StringUtil.substringBefore(token,"."),"{","}");          
            
            if (index!=null && StringUtil.isNumeric(index) && type!=null && type.matches("^(G)$") && name!=null)
            {                
                return getNode(
                        StringUtil.substringAfter(token,"."),
                        ((Document)node).getGroupe(name,Integer.parseInt(index)));
            }
            return null;
        } 
        
        /** 
         * Parcours de l'arbre niveau groupe de segment 
         **/
        if (node instanceof SegmentGroup)
        {
            String index = StringUtil.substringBetween(StringUtil.substringBefore(token,"."),"[","]");                
            String type  = StringUtil.substringBetween(StringUtil.substringBefore(token,"."),"(",")");
            String name  = StringUtil.substringBetween(StringUtil.substringBefore(token,"."),"{","}");
            String code  = StringUtil.substringBetween(StringUtil.substringBefore(token,"."),"/","/");      
            if (index!=null && StringUtil.isNumeric(index) && type!=null && type.matches("^(G)$") && name!=null)
            {
                return getNode(
                        StringUtil.substringAfter(token,"."),
                        ((SegmentGroup)node).getGroupe(name,Integer.parseInt(index)));
            }
            if (index!=null && StringUtil.isNumeric(index) && type!=null && type.matches("^(S)$") && name!=null)
            {
                return getNode(
                        StringUtil.substringAfter(token,"."),
                        ((SegmentGroup)node).getSegment(name, Integer.parseInt(index)));
            }
            if (index==null && type!=null && type.matches("^(S)$") && name!=null && code != null)
            {
                return getNode(
                        StringUtil.substringAfter(token,"."),
                        ((SegmentGroup)node).getSegment(name, code));
            }
            return null;
        } 
                
        
        /** 
         * Parcours de l'arbre niveau Segment 
         **/
        if (node instanceof Segment)
        {
            String index = StringUtil.substringBetween(StringUtil.substringBefore(token,"."),"[","]");                
            String type  = StringUtil.substringBetween(StringUtil.substringBefore(token,"."),"(",")");
            String name  = StringUtil.substringBetween(StringUtil.substringBefore(token,"."),"{","}");
            String code  = StringUtil.substringBetween(StringUtil.substringBefore(token,"."),"/","/");    
            if (index!=null && StringUtil.isNumeric(index) && type!=null && type.matches("^(E)$") && name!=null)            
            {
                return getNode(
                        StringUtil.substringAfter(token,"."),
                        ((Segment)node).getElement(name,Integer.parseInt(index)));
            }           
            if (index==null && type!=null && type.matches("^(E)$") && name!=null &&code != null)
            {
                return getNode(
                        StringUtil.substringAfter(token,"."),
                        ((Segment)node).getElement(name, code));
            }
            return null;
        }
        
        
        /** 
         * Parcours de l'arbre niveau Element 
         **/
        if (node instanceof Element)
        {
            String index = StringUtil.substringBetween(StringUtil.substringBefore(token,"."),"[","]");                
            String type  = StringUtil.substringBetween(StringUtil.substringBefore(token,"."),"(",")");
            String name  = StringUtil.substringBetween(StringUtil.substringBefore(token,"."),"{","}");
            String code  = StringUtil.substringBetween(StringUtil.substringBefore(token,"."),"/","/"); 
            if (index!=null && StringUtil.isNumeric(index) && type!=null && type.matches("^(A)$") && name!=null)            
            {
                return getNode(
                        StringUtil.substringAfter(token,"."),
                        ((Element)node).getAttribut(name,Integer.parseInt(index)));
            }  
            if (index!=null && StringUtil.isNumeric(index) && type!=null && type.matches("^(A)$") && name==null)            
            {
                return getNode(
                        StringUtil.substringAfter(token,"."),
                        ((Element)node).getAttribut(Integer.parseInt(index)));
            }  
            if (index==null && type!=null && type.matches("^(A)$") && name!=null && code != null)
            {
                return getNode(
                        StringUtil.substringAfter(token,"."),
                        ((Element)node).getAttribut(name, code));
            }
        }
        return null;
    }

    
        
    /**
     * Cette méthode permet d'obtenir une valeur ou un objet 
     * d'une branche de données du document
     * (G)[1].(S)[1].(A)[2]
     * <ul>
     * <li>(G) indique un groupe de segment</li>
     * <li>(S) indique un segment</li>
     * <li>(E) indique un element</li>
     * <li>(A) indique un attribut</li>
     * <li>[n] indique l'index de l'objet à atteindre
     * <li>{N} indique le nom du groupe</li>
     * </ul>
     * 
     * @param token la pattern de parcours
     * 
     * @return l'objet correspondant à la zone.
     */
    public Object getNode(String token)
    {
        return getNode(token,this);
    }
    
    
    
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("DOCUMENT");
        sb.append("    \n\tType Syntaxe: " +getSyntaxType());
        sb.append("    \n\tVersion Syntaxe: " + getSyntaxVersion());
        sb.append("    \n\tDate Rédaction: " + DateUtil.format(getDateRedaction(),"dd/MM/yyyy HH:mm"));
        sb.append("    \n\tCRC: " + getControl());
        sb.append("    \n\tType Doc: " + getDocType());
        sb.append("    \n\tVersion Document: " +getDocVersion());
        sb.append("    \n\tRelease Document: " +getDocRelease());
        sb.append("    \n\tDoc Agency: " + getDocAgency());       
        sb.append("\n\tListe des Groupes:\n");        
        for (int i=0; i<listeGroupe.size();i++)
        {
            if (getGroupe(i)!=null)
            {
                sb.append("\t  SEGMENTGROUP n"+i+" NOM: "+getGroupe(i).getNom()+"\n");
                sb.append(getGroupe(i)+"\n");
            }
        }        
        return sb.toString();
    }
}
