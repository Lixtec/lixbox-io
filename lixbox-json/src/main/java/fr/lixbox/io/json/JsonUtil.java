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
package fr.lixbox.io.json;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import fr.lixbox.common.util.StringUtil;


/**
 * Cette classe est un utilitaire qui mappe des flux JSON en pojo et reciproquement
 * 
 * @author ludovic.terral
 */
public class JsonUtil
{
    // ----------- Attribut -----------   
    private static final Log LOG = LogFactory.getLog(JsonUtil.class);
    

    
    // ----------- Methode -----------
    private JsonUtil()
    {
        LOG.debug("JsonUtil cree");
    }
        
    
    
    public static <T> T transformJsonToObject(String json, TypeReference<T> typeRef)
    {
        T result = null;
        if (StringUtil.isNotEmpty(json) && typeRef!=null)
        {
            ObjectMapper mapper = new ObjectMapper();
            try 
            {            
                result = mapper.readValue(json, typeRef);
            } 
            catch (Exception e) 
            {
                LOG.error(e,e);
            }
        }
        return result;
    }
    
    
    
    public static String transformObjectToJson(Object object, boolean prettyFormat)
    {
        String result = null;
        if (object!=null) 
        {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(Include.NON_NULL); 
            if (prettyFormat)
            {
                mapper.enable(SerializationFeature.INDENT_OUTPUT);
            }
            try 
            {   
                result = mapper.writeValueAsString(object);
            } 
            catch (JsonProcessingException e) 
            {
                LOG.error(e,e);
            }
        }
        return result;
    }
    
    
        
    @SuppressWarnings("unchecked")
    public static <T> T transformJsonToObjectWithType(String json)
    {
        T result = null;
        if (StringUtil.isNotEmpty(json))
        {
            ObjectMapper mapper = new ObjectMapper();
            try 
            {   
                List<?> object = mapper.readValue(json, new TypeReference<List<?>>(){});            
                result = (T) mapper.readValue((String)object.get(1), Class.forName((String) object.get(0)));
            } 
            catch (Exception e) 
            {
                LOG.error(e,e);
            }
        }
        return result;
    }
    
    
    
    public static String transformObjectToJsonWithType(Object object, boolean prettyFormat)
    {
        String result = null;
        if (object!=null) 
        {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(Include.NON_NULL); 
            if (prettyFormat)
            {
                mapper.enable(SerializationFeature.INDENT_OUTPUT);
            }
            try 
            {
                List<Object> tmp = new ArrayList<>();
                tmp.add(object.getClass().getName());
                tmp.add(mapper.writeValueAsString(object));
                result = mapper.writeValueAsString(tmp);
            } 
            catch (Exception e) 
            {
                LOG.error(e,e);
            }
        }
        return result; 
    }
}
