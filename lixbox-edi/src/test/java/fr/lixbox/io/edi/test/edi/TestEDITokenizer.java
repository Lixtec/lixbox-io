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
package fr.lixbox.io.edi.test.edi;

import java.util.Calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.lixbox.io.edi.model.Segment;
import fr.lixbox.io.edi.service.EDITokenizer;
import junit.framework.TestCase;

/**
 * Cette classe permet de tester le fonctionnement du service EDITokenizer.
 * 
 * @author ludovic.terral
 */
public class TestEDITokenizer extends TestCase
{
    // ----------- Attribut -----------
    private static final Log LOG = LogFactory.getLog(TestEDITokenizer.class);



    // ----------- Méthode -----------
    public void main(String[] args)
    {
        junit.textui.TestRunner.run(TestEDITokenizer.class);
    }



    public TestEDITokenizer(String name)
    {
        super(name);
    }



    /**
     * Ce test permet de controler la méthode de marshalling du document EDI.
     * 
     * Cas 1: tokenisation d'un segment LOC. Cas 2: tokenisation d'un segment
     * UNB. Cas 3: tokenisation d'un segment FTX.
     * 
     */
    public void testEDITokenizer_getSegment_cas1()
    {
        LOG.info("--------------------------------\nDebut testEDITokenizer_getSegment_cas1\n");
        long debut = Calendar.getInstance().getTimeInMillis();
        // Préparation du test
        String chaineTest = "LOC+90+639917::ELT++::DK:754+7080000729821::9";
        
        
        /*
         * Execution du test
         */
        Segment segment = null;
        try
        {
            EDITokenizer test = new EDITokenizer(chaineTest);
            segment = test.getSegment();
        }
        catch (Exception e)
        {
            assertNull("Exception lev�e non attendue", e);
        }
        
        
        // Vérification du test
        if (segment != null)
        {
            assertTrue("Resultat Cas 1: Echec: Mauvaisaise Identification de la balise",
                    segment.getNom().equalsIgnoreCase("LOC"));
            assertTrue("Resultat Cas 1: Echec: Défaut sur le nombre d'éléments extraits",
                    segment.getListeElement().size() == 5);
            assertTrue(
                    "Resultat Cas 1: Echec: Défaut sur le nbre d'attributs de l'élément \"::DK:754\"",
                    segment.getElement(3).getListeAttribut().size() == 4);
            assertTrue(
                    "Resultat Cas 1: Echec: Défaut attribut 2 de l'élément non null \"::DK:754\"",
                    segment.getElement(3).getAttribut(0).getValeur() == null);
            assertTrue("Resultat Cas 1: Echec: Défaut sur le nbre d'attributs de l'élément \"90\"",
                    segment.getElement(0).getListeAttribut().size() == 1);
            assertTrue("Resultat Cas 1: Echec: Défaut élément 2 non null",
                    segment.getElement(2) == null);
            LOG.info("==== Resultat Cas 1: Succes");
        }
        else
        {
            LOG.info("==== Resultat Cas 1: Echec");
            assertNull("Resultat Cas 1: Echec", "Resultat Cas 1: Echec");
        }
        long duree = Calendar.getInstance().getTimeInMillis() - debut;
        LOG.info("dur�e:" + duree + " ms\n");
        LOG.info("Fin testEDITokenizer_getSegment_cas1\n--------------------------------\n\n");
    }



    public void testEDITokenizer_getSegment_cas2()
    {
        LOG.info("--------------------------------\nDebut testEDITokenizer_getSegment_cas2\n");
        long debut = Calendar.getInstance().getTimeInMillis();
        // Préparation du test
        String chaineTest = "UNB+UNOC:3+5790000701278:14+5790000432752:14+030827:1450+334357++DK-TIS-MET+++DK";
        
        
        /*
         * Execution du test
         */
        Segment segment = null;
        try
        {
            EDITokenizer test = new EDITokenizer(chaineTest);
            segment = test.getSegment();
        }
        catch (Exception e)
        {
            assertNull("Exception levée non attendue", e);
        }
        
        
        // Vérification du test
        if (segment != null)
        {
            assertTrue("Resultat Cas 2: Echec: Mauvaisaise Identification de la balise",
                    segment.getNom().equalsIgnoreCase("UNB"));
            assertTrue("Resultat Cas 2: Echec: Défaut sur le nombre d'éléments extraits",
                    segment.getListeElement().size() == 10);
            assertTrue("Resultat Cas 2: Echec: Mauvaise Valeur de l'élement DK-TIS-MET", segment
                    .getElement(6).getAttribut(0).getValeur().equalsIgnoreCase("DK-TIS-MET"));
            LOG.info("==== Resultat Cas 2: Succes");
        }
        else
        {
            LOG.info("==== Resultat Cas 2: Echec");
            assertNull("Resultat Cas 2: Echec", "Resultat Cas 2: Echec");
        }
        long duree = Calendar.getInstance().getTimeInMillis() - debut;
        LOG.info("durée:" + duree + " ms\n");
        LOG.info("Fin testEDITokenizer_getSegment_cas2\n--------------------------------\n\n");
    }
}