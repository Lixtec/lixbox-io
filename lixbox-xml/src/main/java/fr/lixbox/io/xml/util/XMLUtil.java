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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.input.sax.XMLReaders;
import org.xml.sax.InputSource;

import fr.lixbox.common.exceptions.ProcessusException;
import fr.lixbox.common.stream.util.StreamStringUtil;
import fr.lixbox.common.util.CollectionUtil;
import fr.lixbox.common.util.StringUtil;
import fr.lixbox.io.xml.model.ErrorStorer;

/**
 * Cette classe est un utilitaire qui traite le contenu de messages Xml
 * 
 * @author ludovic.terral
 */
public class XMLUtil
{
    // ----------- Attribut -----------
    private static final Log LOG = LogFactory.getLog(XMLUtil.class);
    private final Document objDocument;
    private final ErrorStorer objErrorStorer;
    private final String contenu;

    
    // ----------- Methode -----------
    public XMLUtil(String content)
    {      
        SAXBuilder sxb = new SAXBuilder();
        try
        {
            objErrorStorer = new ErrorStorer();
            sxb.setErrorHandler(objErrorStorer);
            objDocument = sxb.build(new StringReader(content));
            this.contenu = content;
        }
        catch (Exception e)
        {
            throw new ProcessusException("XMLUtil", e);
        }
    }



    public XMLUtil(File xmlFile)
    {
        SAXBuilder sxb = new SAXBuilder(XMLReaders.XSDVALIDATING);  
        try
        {
            objErrorStorer = new ErrorStorer();
            sxb.setErrorHandler(objErrorStorer);
            objDocument = sxb.build(xmlFile);
            this.contenu = StreamStringUtil.read(new FileInputStream(xmlFile));
        }
        catch (Exception e)
        {
            throw new ProcessusException("XMLUtil", e);
        }
    }



    public XMLUtil(InputSource objXMLFileUrl)
    {      
        SAXBuilder sxb = new SAXBuilder();
        try
        {
            objErrorStorer = new ErrorStorer();
            sxb.setErrorHandler(objErrorStorer);
            objDocument = sxb.build(objXMLFileUrl);
            this.contenu = StreamStringUtil.read(objXMLFileUrl.getByteStream());                    
        }
        catch (Exception e)
        {
            throw new ProcessusException("XMLUtil", e);
        }
    }
    
    
    
    /**
     * Cette methode convertit le document suivant la feuille 
     * de style suivante
     * 
     * @param inXSL
     * @param encoding
     * 
     * @throws TransformerException
     * @throws UnsupportedEncodingException 
     */
    public String transform(File inXSL, String encoding)
            throws TransformerException 
    {
        String result = null;
        try (FileInputStream fis = new FileInputStream(inXSL))
        {        
            result = transform(fis, encoding);
        }
        catch (IOException e)
        {
            LOG.trace(e);
        }
        return result;
    }
            
            
            
    /**
     * Cette methode convertit le document suivant la feuille 
     * de style suivante
     * 
     * @param inXSL
     * @param encoding
     * 
     * @throws TransformerException
     * @throws IOException 
     */
    public String transform(InputStream inXSL, String encoding)
            throws TransformerException, IOException 
    {
        ByteArrayOutputStream outOs = new ByteArrayOutputStream();
        TransformerFactory factory = TransformerFactory.newInstance();
        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        StreamSource xslStream = new StreamSource(inXSL);
        Transformer transformer = factory.newTransformer(xslStream);
        String tmp = contenu;
        StreamSource in = new StreamSource(new ByteArrayInputStream(tmp.getBytes(encoding)));
        StreamResult out = new StreamResult(outOs);
        transformer.transform(in,out);        
        String result = new String(outOs.toByteArray(), Charset.forName(encoding));
        outOs.close();
        inXSL.close();
        return result;
    }

    

    /**
     * Cette methode recupere le document DOM associe a l'utilitaire.
     * 
     * @return un document DOM
     */
    public Document getDocument()
    {
        return objDocument;
    }

    

    /**
     * Cette methode donne la liste des elements du document XML dont 
     * la balise s'appelle comme le parametre
     * 
     * @param sTagName
     * 
     * @return la liste des elements
     * 
     * @throws ProcessusException
     */
    public List<Element> getElementsByTagName(String sTagName)
    {
        List<Element> objNodeList = null;
        try
        {
            objNodeList = objDocument.getRootElement().getChildren(sTagName);            
            if (CollectionUtil.isEmpty(objNodeList))
            {
                objNodeList = null;
            }
        }
        catch (Exception e)
        {
            throw new ProcessusException("getElementsByTagName()", e);
        }
        return objNodeList;
    }

    

    /**
     * Cette methode donne la valeur d'un attribut d'un element
     * 
     * @param sElementName
     * @param sAttributeName
     * 
     * @return the XML node's attribute value
     * 
     * @throws ProcessusException
     */
    public String getElementAttributeValue(String sElementName, String sAttributeName)
    {
        String sAttributeValue = null;
        try
        {
            Element objElement = objDocument.getRootElement().getChild(sElementName);
            if (objElement != null)
            {
                sAttributeValue = objElement.getAttributeValue(sAttributeName);
                if (StringUtil.isEmpty(sAttributeValue))
                {
                    sAttributeValue = null;
                }
            }
        }
        catch (Exception e)
        {
            throw new ProcessusException("getElementAttributeByName()", e);
        }
        return sAttributeValue;
    }

    

    /**
     * Cette methode donne la liste des elements enfants dont le nom de la balise
     * est le second parametre.
     * 
     * @param objElement
     * @param sChildElementName
     * 
     * @return une liste d'element correspondant au nom de la balise
     * 
     * @throws ProcessusException
     */
    public List<Element> getChildElements(Element objElement, String sChildElementName)
    {        
        List<Element> result = null; 
        try
        {
            result = objElement.getChildren(sChildElementName);
        }
        catch (Exception e)
        {
            throw new ProcessusException("getAllChildElements()", e);
        }
        return result;
    }


    
    /**
     * Cette methode donne la liste de l'ensemble des enfants d'un element
     * 
     * @param objElement
     * @return la liste des enfants de l'elements
     * 
     * @throws ProcessusException
     */
    public List<Element> getAllChildElements(Element objElement)
    {
        List<Element> objChildList = null;
        try
        {
            objChildList = objElement.getChildren();
        }
        catch (Exception e)
        {
            throw new ProcessusException("getAllChildElements()", e);
        }
        return objChildList;
    }

    

    /**
     * Cette methode donne la valeur d'un element
     * 
     * @param objElement
     * 
     * @return la valeur d'un element
     * 
     * @throws ProcessusException
     */
    public String getElementValue(Element objElement)
    {
        String sValue = null;
        try
        {        
            sValue = objElement.getValue();
        }
        catch (Exception e)
        {
            throw new ProcessusException("getElementValueString()", e);
        }
        return sValue;
    }

    

    /**
     * Cette methode donne la valeur d'un element
     * 
     * @param objElement
     * 
     * @return la valeur d'un element
     */
    public String getElementValue(String element)
    {
        String sValue = null;
        try
        {
            if (!CollectionUtil.isEmpty(getElementsByTagName(element)))
            {
                Element objElement = getElementsByTagName(element).get(0);
                sValue = getElementValue(objElement);
            }
        }
        catch (Exception e)
        {
            throw new ProcessusException("getElementValueString()", e);
        }
        return sValue;
    }
}
