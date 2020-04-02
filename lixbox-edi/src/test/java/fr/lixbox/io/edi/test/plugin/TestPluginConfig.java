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
package fr.lixbox.io.edi.test.plugin;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.lixbox.common.exceptions.BusinessException;
import fr.lixbox.io.edi.plugin.PluginConfig;
import fr.lixbox.io.edi.plugin.model.jaxb.Attribut;
import fr.lixbox.io.edi.plugin.model.jaxb.Balise;
import fr.lixbox.io.edi.plugin.model.jaxb.SegGroupDesc;
import fr.lixbox.io.edi.ressource.EdiResources;
import junit.framework.TestCase;

/**
 * Cette classe permet de tester le fonctionnement du service de configuration
 * du systeme EDI.
 * 
 * @author ludovic.terral
 */
public class TestPluginConfig extends TestCase
{
    // ----------- Attribut -----------
    private static final Log LOG = LogFactory.getLog(TestPluginConfig.class);



    // ----------- Méthode -----------
    public void main(String[] args)
    {
        junit.textui.TestRunner.run(TestPluginConfig.class);
    }



    public TestPluginConfig(String name)
    {
        super(name);
    }



    /**
     * Cas 1: demander la description d'un schéma inexistant
     */
    public void testPluginConfig_getStructure_cas1()
    {
        LOG.info("--------------------------------\nDebut testPluginConfig_getStructure_cas1\n");
        long debut = Calendar.getInstance().getTimeInMillis();
        
        // Préparation du test
        String schema = "INCONNU";
        String version = "XXXX";
        
        
        /*
         * Execution du test
         */
        List<SegGroupDesc> list = null;
        try
        {
            list = PluginConfig.getStructure(schema, version);
        }
        catch (BusinessException be)
        {
            LOG.info(be);
            assertTrue("Erreur inattendue", be.getMessage()
                    .equalsIgnoreCase(EdiResources.getString("ERROR.FORMAT.INCONNU", schema)));
        }
        catch (Exception e)
        {
            assertNull("Exception levée non attendue", e);
        }
        
        
        // Vérification du test
        if (list == null)
        {
            LOG.info("==== Resultat Cas 1: Succes");
        }
        else
        {
            LOG.info("==== Resultat Cas 1: Echec");
            assertNotNull("Erreur inattendue", list);
        }
        long duree = Calendar.getInstance().getTimeInMillis() - debut;
        LOG.info("durée:" + duree + " ms\n");
        LOG.info("Fin testPluginConfig_getStructure_cas1\n--------------------------------\n\n");
    }



    /**
     * Cas 2: demander la description d'une balise inexistante
     */
    public void testPluginConfig_getBalise_cas1()
    {
        LOG.info("--------------------------------\nDebut testPluginConfig_getBalise_cas1\n");
        long debut = Calendar.getInstance().getTimeInMillis();
        
        // Préparation du test
        String balise = "INC";
        
        
        /*
         * Execution du test
         */
        Balise list = null;
        try
        {
            list = PluginConfig.getBalise(balise);
        }
        catch (BusinessException be)
        {
            LOG.info(be);
            assertTrue("Erreur inattendue", be.getMessage()
                    .equalsIgnoreCase(EdiResources.getString("ERROR.BALISE.INCONNUE", balise)));
        }
        catch (Exception e)
        {
            assertNull("Exception levée non attendue", e);
        }
        
        
        // Vérification du test
        if (list == null)
        {
            LOG.info("==== Resultat Cas 1: Succes");
        }
        else
        {
            LOG.info("==== Resultat Cas 1: Echec");
            assertNotNull("Erreur inattendue", list);
        }
        long duree = Calendar.getInstance().getTimeInMillis() - debut;
        LOG.info("durée:" + duree + " ms\n");
        LOG.info("Fin testPluginConfig_getBalise_cas1\n--------------------------------\n\n");
    }



    /**
     * Cas 3: demander la description d'un attribut inexistant
     */
    public void testPluginConfig_getAttribut_cas1()
    {
        LOG.info("--------------------------------\nDebut testPluginConfig_getAttribut_cas1\n");
        long debut = Calendar.getInstance().getTimeInMillis();
        
        // Préparation du test
        String attr = "2365";
        
        
        /*
         * Execution du test
         */
        Attribut list = null;
        try
        {
            list = PluginConfig.getAttribut(attr);
        }
        catch (BusinessException be)
        {
            LOG.info(be);
            assertTrue("Erreur inattendue", be.getMessage()
                    .equalsIgnoreCase(EdiResources.getString("ERROR.ATTR.INCONNU", attr)));
        }
        catch (Exception e)
        {
            assertNull("Exception levée non attendue", e);
        }
        
        
        // Vérification du test
        if (list == null)
        {
            LOG.info("==== Resultat Cas 1: Succes");
        }
        else
        {
            LOG.info("==== Resultat Cas 1: Echec");
            assertNotNull("Erreur inattendue", list);
        }
        long duree = Calendar.getInstance().getTimeInMillis() - debut;
        LOG.info("durée:" + duree + " ms\n");
        LOG.info("Fin testPluginConfig_getAttribut_cas1\n--------------------------------\n\n");
    }



    /**
     * Cas 4: demander la description d'un groupe de balise inexistant Cas 5:
     * demander la description d'un groupe de balise d'un sch�ma inexistant
     */
    public void testPluginConfig_getDescripteur_cas1()
    {
        LOG.info("--------------------------------\nDebut testPluginConfig_getDescripteur_cas1\n");
        long debut = Calendar.getInstance().getTimeInMillis();
       
        // Préparation du test
        String schema = "INCONNU";
        int index = 1;
        
        
        /*
         * Execution du test
         */
        SegGroupDesc list = null;
        try
        {
            list = PluginConfig.getDescripteur(schema, index);
        }
        catch (BusinessException be)
        {
            LOG.info(be);
            assertTrue("Erreur inattendue", be.getMessage()
                    .equalsIgnoreCase(EdiResources.getString("ERROR.FORMAT.INCONNU", schema)));
        }
        catch (Exception e)
        {
            assertNull("Exception levée non attendue", e);
        }
        
        
        // Vérification du test
        if (list == null)
        {
            LOG.info("==== Resultat Cas 1: Succes");
        }
        else
        {
            LOG.info("==== Resultat Cas 1: Echec");
            assertNotNull("Erreur inattendue", list);
        }
        long duree = Calendar.getInstance().getTimeInMillis() - debut;
        LOG.info("durée:" + duree + " ms\n");
        LOG.info("Fin testPluginConfig_getDescripteur_cas1\n--------------------------------\n\n");
    }



    public void testPluginConfig_getDescripteur_cas2()
    {
        LOG.info("--------------------------------\nDebut testPluginConfig_getDescripteur_cas2\n");
        long debut = Calendar.getInstance().getTimeInMillis();
        
        // Préparation du test
        String schema = "IFTDGN04A";
        int index = 31;


        //Execution du test
        SegGroupDesc list = null;
        try
        {
            list = PluginConfig.getDescripteur(schema, index);
        }
        catch (BusinessException be)
        {
            LOG.info(be);
            assertTrue("Erreur inattendue", be.getMessage()
                    .equalsIgnoreCase(EdiResources.getString("ERROR.MESSAGE.PARAM")));
        }
        catch (Exception e)
        {
            assertNull("Exception levée non attendue", e);
        }
        
        
        // Vérification du test
        if (list == null)
        {
            LOG.info("==== Resultat Cas 2: Succes");
        }
        else
        {
            LOG.info("==== Resultat Cas 2: Echec");
            assertNotNull("Erreur inattendue", list);
        }
        long duree = Calendar.getInstance().getTimeInMillis() - debut;
        LOG.info("durée:" + duree + " ms\n");
        LOG.info("Fin testPluginConfig_getDescripteur_cas2\n--------------------------------\n\n");
    }
}
