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
