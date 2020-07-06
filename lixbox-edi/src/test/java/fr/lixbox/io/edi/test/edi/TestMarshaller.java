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
package fr.lixbox.io.edi.test.edi;

import java.util.Calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.lixbox.common.exceptions.BusinessException;
import fr.lixbox.common.model.ConteneurEvenement;
import fr.lixbox.io.edi.model.Document;
import fr.lixbox.io.edi.model.Segment;
import fr.lixbox.io.edi.model.SegmentGroup;
import fr.lixbox.io.edi.service.Marshaller;
import junit.framework.TestCase;


/**
 * Cette classe permet de tester le fonctionnement du
 * service de parsing.
 * 
 * @author ludovic.terral
 */
public class TestMarshaller extends TestCase
{
    // ----------- Attribut -----------   
    private static final Log LOG = LogFactory.getLog(TestMarshaller.class);
       
    
    
    // ----------- Méthode -----------
    public void main(String[] args)
    { 
        junit.textui.TestRunner.run(TestMarshaller.class);
    }
    
    
    public TestMarshaller(String name)
    {
        super(name);
    }
    
    
    
    /**
     * Ce test permet de controler la méthode de marshalling du document EDI.
     * 
     * Cas 1: parsing d'un message IFTDGN avec deux erreurs.
     * Cas 2: parsing d'un message IFTDGN correct sans retour à ligne.
     * Cas 3: parsing d'un message IFTDGN correct avec retour à ligne.
     * Cas 4: parsing d'un message BAPLIE avec 4 erreurs.
     * Cas 5: parsing d'un message BAPLIE avec 2 erreurs.
     * Cas 6: parsing d'un message BAPLIE correct.
     */
    public void testMarshaller_marshall_cas1()
    {
        LOG.info("--------------------------------\nDebut testMarshaller_marshall_cas1\n");
        long debut = Calendar.getInstance().getTimeInMillis();
        
        //Préparation du test
        ConteneurEvenement listEvent=null;
        
        
        /*
         * Execution du test 
        */
        Document doc = null;
        try
        {
            Marshaller test = new Marshaller(getClass().getResourceAsStream("/data/IFTDGN.EDI"));
            doc = test.marshall();
        }
        catch (BusinessException be)
        {
            listEvent= be.getConteneur();
            LOG.info(be.getConteneur());
        }  
        catch (Exception e)
        {
            assertNull("Exception levée non attendue", e);
        }  
        

        //Vérification du test     
        if (doc==null && listEvent.getEvenements().size()==2)
        {
            LOG.info("==== Resultat Cas 1: Succes");
        }
        else
        {
            LOG.info("==== Resultat Cas 1: Echec");            
        }        
        
        long duree = Calendar.getInstance().getTimeInMillis()-debut;
        LOG.info("dur�e:" + duree +" ms\n");  
        LOG.info("Fin testMarshaller_marshall_cas1\n--------------------------------\n\n");
    }  
    
    
    
    public void testMarshaller_marshall_cas1b()
    {
        LOG.info("--------------------------------\nDebut testMarshaller_marshall_cas1b\n");
        long debut = Calendar.getInstance().getTimeInMillis();
        
        //Préparation du test
        ConteneurEvenement listEvent=null;
        
        /*
         * Execution du test 
        */
        Document doc = null;
        try
        {
            Marshaller test = new Marshaller(getClass().getResourceAsStream("/data/APERAK1.EDI"));
            doc = test.marshall();
        }
        catch (BusinessException be)
        {
            listEvent= be.getConteneur();
            LOG.info(be.getConteneur());
        }  
        catch (Exception e)
        {
            e.printStackTrace();
            assertNull("Exception levée non attendue", e);
        }  
        

        //Vérification du test     
        if (doc==null && listEvent.getEvenements().size()==2)
        {
            LOG.info("==== Resultat Cas 1: Succes");
        }
        else
        {
            LOG.info("==== Resultat Cas 1: Echec");            
        }        
        
        long duree = Calendar.getInstance().getTimeInMillis()-debut;
        LOG.info("dur�e:" + duree +" ms\n");  
        LOG.info("Fin testMarshaller_marshall_cas1b\n--------------------------------\n\n");
    } 
    
    
    
    public void testMarshaller_marshall_cas2()
    {
        LOG.info("--------------------------------\nDebut testMarshaller_marshall_cas2\n");
        long debut = Calendar.getInstance().getTimeInMillis();
        
        //Préparation du test
        /* Sans objet*/
        
        
        /*
         * Execution du test 
        */
        Document doc = null;
        try
        {
            Marshaller test = new Marshaller(getClass().getResourceAsStream("/data/IFTDGN2.EDI"));
            doc = test.marshall();
        }
        catch (Exception e)
        {
            assertNull("Exception levée non attendue", e);
        }  
        

        //Vérification du test     
        if (doc!=null)
        {
            LOG.info(doc);
            LOG.info("==== Resultat Cas 2: Succes");
        }     
        
        long duree = Calendar.getInstance().getTimeInMillis()-debut;
        LOG.info("dur�e:" + duree +" ms\n");  
        LOG.info("Fin testMarshaller_marshall_cas2\n--------------------------------\n\n");
    } 
    
    
    
    public void testMarshaller_marshall_cas3()
    {
        LOG.info("--------------------------------\nDebut testMarshaller_marshall_cas3\n");
        long debut = Calendar.getInstance().getTimeInMillis();
        
        //Préparation du test
        /* Sans objet*/

        
        /*
         * Execution du test 
        */
        Document doc = null;
        try
        {
            Marshaller test = new Marshaller(getClass().getResourceAsStream("/data/IFTDGN4.EDI"));
            doc = test.marshall();
        }
        catch (Exception e)
        {
            assertNull("Exception levée non attendue", e);
        }  
        

        //Vérification du test     
        assertNotNull("Erreur inattendue",doc);
        assertTrue("PARSING ERROR SG_HEADER",doc.getGroupe("SG_HEADER").size()==1);
        
        assertTrue("PARSING ERROR SG_2",doc.getGroupe("SG_2").size()==1);
        assertTrue("PARSING ERROR SG_4",doc.getGroupe("SG_4").size()==1);
        assertTrue("PARSING ERROR SG_6",doc.getGroupe("SG_6").size()==1);
        assertTrue("PARSING ERROR SG_7",doc.getGroupe("SG_7").size()==1);
        assertTrue("PARSING ERROR SG_10",doc.getGroupe("SG_7",0).getGroupe("SG_10").size()==3);
        assertTrue("PARSING ERROR SG_12",doc.getGroupe("SG_7",0).getGroupe("SG_12").size()==2);
        assertTrue("PARSING ERROR SG_14",doc.getGroupe("SG_7",0).getGroupe("SG_12",0).getGroupe("SG_14").size()==1);
        
        assertTrue("PARSING ERROR SG_FOOTER",doc.getGroupe("SG_FOOTER").size()==1);
     
        
        long duree = Calendar.getInstance().getTimeInMillis()-debut;
        LOG.info("dur�e:" + duree +" ms\n");  
        LOG.info("Fin testMarshaller_marshall_cas3\n--------------------------------\n\n");
    }
    
    
    
    public void testMarshaller_marshall_cas3b()
    {
        LOG.info("--------------------------------\nDebut testMarshaller_marshall_cas3b\n");
        long debut = Calendar.getInstance().getTimeInMillis();
        
        //Préparation du test
        /* Sans objet*/

        
        /*
         * Execution du test 
        */
        Document doc = null;
        try
        {
            Marshaller test = new Marshaller(getClass().getResourceAsStream("/data/TEST.EDI"));
            doc = test.marshall();
        }
        catch (Exception e)
        {
            assertNull("Exception levée non attendue", e);
        }  
        

        //Vérification du test     
        assertNotNull("Erreur inattendue",doc);
        assertTrue("PARSING ERROR SG_HEADER",doc.getGroupe("SG_HEADER").size()==1);
        assertTrue("PARSING ERROR SG_4",doc.getGroupe("SG_4").size()==2);
        assertTrue("PARSING ERROR SG_7",doc.getGroupe("SG_7").size()==2);
        assertTrue("PARSING ERROR SG_10",doc.getGroupe("SG_7",0).getGroupe("SG_10").size()==3);
        assertTrue("PARSING ERROR SG_12",doc.getGroupe("SG_7",0).getGroupe("SG_12").size()==2);
        assertTrue("PARSING ERROR SG_14",doc.getGroupe("SG_7",0).getGroupe("SG_12",0).getGroupe("SG_14").size()==1);
        assertTrue("PARSING ERROR SG_10_b",doc.getGroupe("SG_7",1).getGroupe("SG_10").size()==2);
        assertTrue("PARSING ERROR SG_12_b",doc.getGroupe("SG_7",1).getGroupe("SG_12").size()==3);
        assertTrue("PARSING ERROR SG_14_b",doc.getGroupe("SG_7",1).getGroupe("SG_12",0).getGroupe("SG_14").size()==1);
        
        assertTrue("PARSING ERROR SG_FOOTER",doc.getGroupe("SG_FOOTER").size()==1);
     
        
        long duree = Calendar.getInstance().getTimeInMillis()-debut;
        LOG.info("dur�e:" + duree +" ms\n");  
        LOG.info("Fin testMarshaller_marshall_cas3b\n--------------------------------\n\n");
    }  
    
    
    public void testMarshaller_marshall_cas4()
    {
        LOG.info("--------------------------------\nDebut testMarshaller_marshall_cas4\n");
        long debut = Calendar.getInstance().getTimeInMillis();
        
        //Préparation du test
        /* Sans objet*/
        ConteneurEvenement listEvent=null;
        
        
        /*
         * Execution du test 
        */
        Document doc = null;
        try
        {
            Marshaller test = new Marshaller(getClass().getResourceAsStream("/data/BAPLIE.EDI"));
            doc = test.marshall();
        }
        catch (BusinessException be)
        {
            listEvent= be.getConteneur();
            LOG.info(be.getConteneur());
        }  
        catch (Exception e)
        {
            assertNull("Exception levée non attendue", e);
        }  
        

        //Vérification du test     
        if (doc==null && listEvent.getEvenements().size()==4)
        {
            LOG.info("==== Resultat Cas 4: Succes");
        }
        else
        {
            LOG.info("==== Resultat Cas 4: Echec");
        }        
        
        long duree = Calendar.getInstance().getTimeInMillis()-debut;
        LOG.info("dur�e:" + duree +" ms\n");  
        LOG.info("Fin testMarshaller_marshall_cas4\n--------------------------------\n\n");
    }  
    
    
    public void testMarshaller_marshall_cas5()
    {
        LOG.info("--------------------------------\nDebut testMarshaller_marshall_cas5\n");
        long debut = Calendar.getInstance().getTimeInMillis();
        
        //Préparation du test
        /* Sans objet*/
        ConteneurEvenement listEvent=null;
        
        
        /*
         * Execution du test 
        */
        Document doc = null;
        try
        {
            Marshaller test = new Marshaller(getClass().getResourceAsStream("/data/CHOPIN_DEPART.EDI"));
            doc = test.marshall();
        }
        catch (BusinessException be)
        {
            listEvent= be.getConteneur();
            LOG.info(be.getConteneur());
        }  
        catch (Exception e)
        {
            assertNull("Exception levée non attendue", e);
        }  
        

        //Vérification du test     
        if (doc==null && listEvent!=null && listEvent.getEvenements()!=null && listEvent.getEvenements().size()==1)
        {
            LOG.info("==== Resultat Cas 5: Succes");
        }
        else
        {
            assertTrue("Erreur anormale",false);
            LOG.info("==== Resultat Cas 5: Echec");
        }        
        
        long duree = Calendar.getInstance().getTimeInMillis()-debut;
        LOG.info("durée:" + duree +" ms\n");  
        LOG.info("Fin testMarshaller_marshall_cas5\n--------------------------------\n\n");
    } 
        
    
    /**
     * Ce test permet de controler la méthode de parcours de l'arbre
     * du document. On devra fournir 4 valeurs dans différentes zones
     * du document IFTDGN3.EDI
     */
    public void testDocument_getNode()
    {
        LOG.info("--------------------------------\nDebut testDocument_getNode\n");
        long debut = Calendar.getInstance().getTimeInMillis();
        
        //Préparation du test
        Document doc = null;
        Marshaller test = null;
        try
        {
            test = new Marshaller(getClass().getResourceAsStream("/data/IFTDGN4.EDI"));
            doc = test.marshall();
        }
        catch (Exception e)
        {
            assertNull("Exception levée non attendue", e);
        }  
        
        
        /*
         * Execution du test 
        */
        String valeur1 = null;
        String valeur2= null;
        String valeur5= null;
        String valeur6= null;
        Segment valeur3= null;
        SegmentGroup valeur4= null;
    
        try
        {
            valeur1 = (String) doc.getNode("(G){SG_2}[0].(S){TDT}[0].(E){C222}[0].(A){8212}[0]");
            valeur2 = (String) doc.getNode("(G){SG_4}[0].(G){SG_5}[0].(S){CTA}[0].(E){3139}[0].(A)[0]");            
            valeur3 = ((Segment)doc.getNode("(G){SG_12}[1].(S){GID}[0]"));
            valeur4 = ((SegmentGroup)doc.getNode("(G){SG_12}[0].(G){SG_14}[0]"));
            valeur5 = (String) doc.getNode("(G){SG_2}[0].(S){DTM}/133/.(E){C507}[0].(A){2380}[0]");
            valeur6 = (String) doc.getNode("(G){SG_7}[0].(S){LOC}/11/.(E){C517}[0].(A){3225}[0]");
            LOG.info("Valeur 1: "+valeur1);
            LOG.info("Valeur 2: "+valeur2);
            LOG.info("Valeur 3: "+valeur3);
            LOG.info("Valeur 4:\n"+valeur4);
            LOG.info("Valeur 5:\n"+valeur5);
            LOG.info("Valeur 6:\n"+valeur6);
        }
        catch (Exception e)
        {
            assertNull("Exception levée non attendue", e);
        }  
        

        //Vérification du test     
        assertTrue("Mauvaise Valeur2","IC".equalsIgnoreCase(valeur2));
        assertTrue("Mauvaise Valeur5","200609291400".equalsIgnoreCase(valeur5));
        assertTrue("Mauvaise Valeur6","ESBCN".equalsIgnoreCase(valeur6));        

        
        long duree = Calendar.getInstance().getTimeInMillis()-debut;
        LOG.info("dur�e:" + duree +" ms\n");  
        LOG.info("Fin testDocument_getNode\n--------------------------------\n\n");
    }      
}