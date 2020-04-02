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
package fr.lixbox.io.xml.test;


import java.util.Calendar;

import fr.lixbox.common.model.enumeration.NiveauEvenement;
import fr.lixbox.common.util.StringUtil;
import fr.lixbox.io.xml.model.XmlEntityTest;
import fr.lixbox.io.xml.util.JaxbUtil;
import junit.framework.TestCase;


/**
 * Cette classe permet de valider l'utilitaire de traitement
 * des collections.
 * 
 * @author ludovic.terral
 */
public class TestJaxbUtil extends TestCase 
{
    // ----------- Attribut -----------

    
    
    // ----------- Methode -----------
    public TestJaxbUtil(String arg0) 
    {
        super(arg0);
    }

    
    
    /**
     * Ce test permet de verifier la fonction de marshalling sans validation
     */
    public void testJaxbUtil_marshallUnValidate_1() 
    {
        System.out.println("--------------------------------------");
        System.out.println("Debut testJaxbUtil_marshallUnValidate_1");
        System.out.println("--------------------------------------\n");
        final long debut = Calendar.getInstance().getTimeInMillis();
        
        
        //Preparation du test
        final XmlEntityTest entityTest = new XmlEntityTest(NiveauEvenement.INFO, "testJaxbUtil_marshallUnValidate_1");
        
        
        /*
         * Execution du test 
        */
        String test = null;
        try
        {
            test = JaxbUtil.marshallUnValidate(XmlEntityTest.class, entityTest, "ISO-8859-1");           
            System.out.println(entityTest);
            System.out.println("Result:\n" + test);
        }
        catch (Exception e)
        {
            assertNull("Exception levee non attendue", e);
        }        
                

        //Verification des resultats
        if (!StringUtil.isEmpty(test))
        {
            System.out.println("==== Resultat testJaxbUtil_marshallUnValidate_1: Succes");
        }
        else
        {
            System.out.println("==== Resultat testJaxbUtil_marshallUnValidate_1: Echec");
            assertFalse("resultat nul", StringUtil.isEmpty(test));
            assertTrue("Contenu resultat Incorrect", 177 == test.length());
        } 

        
        final long duree = Calendar.getInstance().getTimeInMillis() - debut;
        System.out.println("duree:" + duree + " ms");  
        System.out.println("------------------------------------");
        System.out.println("Fin testJaxbUtil_marshallUnValidate_1");
        System.out.println("------------------------------------\n");    
    }
}
