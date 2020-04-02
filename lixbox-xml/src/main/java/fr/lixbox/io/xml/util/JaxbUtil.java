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
package fr.lixbox.io.xml.util;

import static javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI;

import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.util.ValidationEventCollector;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.SAXException;

import fr.lixbox.common.exceptions.BusinessException;
import fr.lixbox.common.exceptions.ProcessusException;
import fr.lixbox.common.model.ConteneurEvenement;
import fr.lixbox.common.model.Evenement;
import fr.lixbox.common.model.enumeration.NiveauEvenement;
import fr.lixbox.common.resource.LixboxResources;


/**
 * Cette classe est un utilitaire qui mappe des flux XML en pojo et reciproquement
 * 
 * @author ludovic.terral
 */
public class JaxbUtil
{
    // ----------- Attribut -----------   
    private static final Log LOG = LogFactory.getLog(JaxbUtil.class);
    
    private static boolean prettyFormat = true;
    

    
    // ----------- Methode -----------
    private JaxbUtil()
    {
        LOG.debug("JAXBUtil cree");
    }



    public static boolean isPrettyFormat()
    {
        return prettyFormat;
    }
    public static void setPrettyFormat(boolean _prettyFormat)
    {
        prettyFormat = _prettyFormat;
    }
        
    

    /**
     * Cette methode sert a construire et a remplir les objets
     * correspondant a un message XML si le fichier XML est valide
     * 
     * @param xmlRootElement
     * @param pathSchema
     * @param fluxXml
     * 
     * @return renvoie un objet JAXB
     * 
     * @throws BusinessException
     */
    @SuppressWarnings({ "unchecked" })
    public static <T> T unMarshallValidate(Class<T> xmlRootElement, 
        String pathSchema, String fluxXml)
        throws BusinessException
    {
        final ValidationEventCollector collecteurStatus = new ValidationEventCollector();
        T result = null;        
        try
        {
            final JAXBContext jc = JAXBContext.newInstance(xmlRootElement);
            final Unmarshaller unmarshaller = jc.createUnmarshaller();
            
            // Validation du document XML activee
            final SchemaFactory sf = SchemaFactory.newInstance(W3C_XML_SCHEMA_NS_URI);
            final Schema schema = sf.newSchema(xmlRootElement.getResource(pathSchema));
            unmarshaller.setSchema(schema);
            
            unmarshaller.setEventHandler(collecteurStatus);
            result = (T) unmarshaller.unmarshal(new StreamSource(new StringReader(fluxXml))); // $codepro.audit.disable unnecessaryCast
        }
        catch (UnmarshalException ue)
        {
            LOG.fatal(LixboxResources.getString("ERROR.DOC.LECT"));
            LOG.fatal(ue);
            throw new ProcessusException(LixboxResources.getString("ERROR.DOC.LECT"), ue);
        }
        catch (JAXBException je)
        {
            LOG.fatal(LixboxResources.getString("ERROR.JAXB.GEN.JAVA"));
            LOG.fatal(je);
            throw new ProcessusException(LixboxResources.getString("ERROR.JAXB.GEN.JAVA"));
        }
        catch (SAXException se) 
        {
            LOG.fatal(LixboxResources.getString("ERROR.JAXB.VALIDATION.ACTIV"));
            LOG.fatal(se);
            throw new ProcessusException(LixboxResources.getString("ERROR.JAXB.VALIDATION.ACTIV"));
        }
        

        // Preparation de la liste des status        
        final ValidationEvent[] listEvent = collecteurStatus.getEvents();
        if (0 != listEvent.length)
        {
            final ConteneurEvenement listeEvent = new ConteneurEvenement();
            listeEvent.add(NiveauEvenement.WARN, 
                            LixboxResources.getString("ERROR.MESSAGE.MALFORME"),
                            "classeSignature",
                            "JaxbUtil.unMarshallValidate");
            for (int i = 0; i < listEvent.length; i++)
            {
                listeEvent.add(NiveauEvenement.WARN, 
                               LixboxResources
                                    .getString(
                                            "ERROR.MESSAGE.MALFORME.EXT",
                                            new Object[]{
                                                    listEvent[i].getLocator().getLineNumber(),
                                                    listEvent[i].getLocator().getColumnNumber(),
                                                    listEvent[i].getMessage()}),
                                            "classeSignature",
                                            "JaxbUtil.unMarshallValidate");
            }
            for (Evenement event : listeEvent.getEvenements())
            {
                LOG.error(event);
            }
            throw new BusinessException(LixboxResources.getString("ERROR.MESSAGE.MALFORME"), listeEvent);            
        }
        return result;
    }
    
    

    /**
     * Cette methode sert a construire et a remplir les objets
     * correspondant a un message XML si le fichier XML est valide
     * 
     * @param xmlRootElement
     * @param pathSchema
     * @param fluxXml
     * 
     * @return renvoie un objet JAXB
     * 
     * @throws BusinessException
     */
    @SuppressWarnings({"unchecked" })
    public static <T> T unMarshallValidate(Class<T> xmlRootElement, 
        InputStream schemaStream, String fluxXml)
        throws BusinessException
    {
        final ValidationEventCollector collecteurStatus = new ValidationEventCollector();
        T result = null;        
        try
        {
            final JAXBContext jc = JAXBContext.newInstance(xmlRootElement);
            final Unmarshaller unmarshaller = jc.createUnmarshaller();
            
            // Validation du document XML activee
            final SchemaFactory sf = SchemaFactory.newInstance(W3C_XML_SCHEMA_NS_URI);
            final Schema schema = sf.newSchema(new StreamSource(schemaStream));
            unmarshaller.setSchema(schema);
            
            unmarshaller.setEventHandler(collecteurStatus);
            result = (T) unmarshaller.unmarshal(new StreamSource(new StringReader(fluxXml))); // $codepro.audit.disable unnecessaryCast
        }
        catch (UnmarshalException ue)
        {
            LOG.fatal(LixboxResources.getString("ERROR.DOC.LECT"));
            LOG.fatal(ue);
            throw new ProcessusException(LixboxResources.getString("ERROR.DOC.LECT"), ue);
        }
        catch (JAXBException je)
        {
            LOG.fatal(LixboxResources.getString("ERROR.JAXB.GEN.JAVA"));
            LOG.fatal(je);
            throw new ProcessusException(LixboxResources.getString("ERROR.JAXB.GEN.JAVA"));
        }
        catch (SAXException se) 
        {
            LOG.fatal(LixboxResources.getString("ERROR.JAXB.VALIDATION.ACTIV"));
            LOG.fatal(se);
            throw new ProcessusException(LixboxResources.getString("ERROR.JAXB.VALIDATION.ACTIV"));
        }
        

        // Preparation de la liste des status        
        final ValidationEvent[] listEvent = collecteurStatus.getEvents();
        if (0 != listEvent.length)
        {
            final ConteneurEvenement listeEvent = new ConteneurEvenement();
            listeEvent.add(NiveauEvenement.WARN, 
                            LixboxResources.getString("ERROR.MESSAGE.MALFORME"),
                            "classeSignature",
                            "JaxbUtil.unMarshallValidate");
            for (int i = 0; i < listEvent.length; i++)
            {
                listeEvent.add(NiveauEvenement.WARN, 
                               LixboxResources
                                    .getString(
                                            "ERROR.MESSAGE.MALFORME.EXT",
                                            new Object[]{
                                                    listEvent[i].getLocator().getLineNumber(),
                                                    listEvent[i].getLocator().getColumnNumber(),
                                                    listEvent[i].getMessage()}),
                                            "classeSignature",
                                            "JaxbUtil.unMarshallValidate");
            }
            for (Evenement event : listeEvent.getEvenements())
            {
                LOG.error(event);
            }
            throw new BusinessException(LixboxResources.getString("ERROR.MESSAGE.MALFORME"), listeEvent);            
        }
        return result;
    }
    


    /**
     * Cette methode sert a construire et a remplir les objets
     * correspondant a un message XML si le fichier XML est valide
     * 
     * @param xmlRootElement
     * @param fluxXml
     * 
     * @return renvoie un objet JAXB
     * 
     * @throws BusinessException
     */
    @SuppressWarnings({ "unchecked" })
    public static <T> T  unMarshallUnValidate(Class<T> xmlRootElement, String fluxXml)
    {    
        T result = null;
        try
        {
            final JAXBContext jc = JAXBContext.newInstance(xmlRootElement);
            final Unmarshaller unmarshaller = jc.createUnmarshaller(); // $codepro.audit.disable unnecessaryCast
            result = (T) unmarshaller.unmarshal(new StreamSource(new StringReader(fluxXml))); // $codepro.audit.disable unnecessaryCast
        }
        catch (UnmarshalException ue)
        {
            LOG.fatal(LixboxResources.getString("ERROR.DOC.LECT"));
            LOG.fatal(ue.getStackTrace());
            throw new ProcessusException(LixboxResources.getString("ERROR.DOC.LECT"), ue);
        }
        catch (JAXBException je)
        {
            LOG.fatal(LixboxResources.getString("ERROR.JAXB.GEN.JAVA"));
            LOG.fatal(je.getStackTrace());
            throw new ProcessusException(LixboxResources.getString("ERROR.JAXB.GEN.JAVA"));
        }
        return result;
    }
    


    /**
     * Cette methode sert a construire et a remplir le flux xml
     * correspondant a un message XML au format JAXB Object
     * 
     * @param xmlRootElement
     * @param jaxbObject
     * @param encoding
     * 
     * @return renvoie une flux xml
     * 
     * @throws BusinessException
     */
    public static <T> String marshallUnValidate(Class<T> xmlRootElement, T jaxbObject, String encoding)
    {
        try
        {
            final JAXBContext jc = JAXBContext.newInstance(xmlRootElement);
            final Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, isPrettyFormat());
            marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
            
            // transfert
            final StringWriter tampon = new StringWriter();
            marshaller.marshal(jaxbObject, tampon);
            return tampon.toString();
        }
        catch (Exception e)
        {
            LOG.error(LixboxResources.getString("ERROR.JAXB.GEN.XML"));
            LOG.error(e,e);
            throw new ProcessusException(LixboxResources.getString("ERROR.JAXB.GEN.XML"), e);
        }
    }
        


    /**
     * Cette methode sert a construire et a remplir le flux xml
     * correspondant a un message XML  au format JAXB Object
     * avec une validation.
     * 
     * @param xmlRootElement
     * @param pathSchema
     * @param jaxbObject
     * @param encoding
     * 
     * @return renvoie une flux xml
     * 
     * @throws BusinessException
     */ 
    public static <T> String marshallValidate(Class<T> xmlRootElement, 
        String pathSchema, T jaxbObject, String encoding)
    {
        final ValidationEventCollector collecteurStatus = new ValidationEventCollector();
        
        try
        {
            final JAXBContext jc =  JAXBContext.newInstance(xmlRootElement);
            final Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, isPrettyFormat());
            marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
            
            
            // Validation du document XML activee
            final SchemaFactory sf = SchemaFactory.newInstance(W3C_XML_SCHEMA_NS_URI);
            final Schema schema = sf.newSchema(xmlRootElement.getResource(pathSchema));
            marshaller.setSchema(schema);            
            marshaller.setEventHandler(collecteurStatus);

            //Transformation
            final StringWriter tampon = new StringWriter();
            marshaller.marshal(jaxbObject, tampon);
            
            //validation
            final ValidationEvent[] listEvent = collecteurStatus.getEvents();
            if (0 != listEvent.length)
            {
                final ConteneurEvenement listeEvent = new ConteneurEvenement();
                listeEvent.add( NiveauEvenement.WARN, 
                                LixboxResources.getString("ERROR.MESSAGE.MALFORME"),
                                "classeSignature",
                                "JaxbUtil.marshallValidate");
                for (int i = 0; i < listEvent.length; i++)
                {
                    listeEvent.add(NiveauEvenement.WARN, 
                                   LixboxResources
                                        .getString(
                                                "ERROR.MESSAGE.MALFORME.EXT",
                                                new Object[]{
                                                        listEvent[i].getLocator().getLineNumber(),
                                                        listEvent[i].getLocator().getColumnNumber(),
                                                        listEvent[i].getMessage()}),
                                                "classeSignature",
                                                "JaxbUtil.marshallValidate");
                }
                throw new BusinessException(LixboxResources.getString("ERROR.MESSAGE.MALFORME"), listeEvent);
            }            
            return tampon.toString();
        }
        catch (Exception e)
        {
            LOG.fatal(LixboxResources.getString("ERROR.JAXB.GEN.XML"));
            LOG.fatal(e.getStackTrace());
            throw new ProcessusException(LixboxResources.getString("ERROR.JAXB.GEN.XML"), e);
        }
    }
}
