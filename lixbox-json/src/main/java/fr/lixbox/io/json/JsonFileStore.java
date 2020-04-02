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

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.lixbox.common.exceptions.ProcessusException;

/**
 * Cette classe sérialise et désérialise des objets java au format JSON dans un fichier
 * 
 * @author ludovic.terral
 */
public class JsonFileStore
{
    // ----------- Attribut(s) -----------   
    private static final Log LOG = LogFactory.getLog(JsonFileStore.class);
    private java.nio.file.Path path;



    // ----------- Methode(s) -----------
    public JsonFileStore()
    {
        path = Paths.get(System.getProperty("java.io.tmpdir") + "/jsonStore");
        createPath();
    }
        
    
    
    public JsonFileStore(Path path)
    {
        this.path = path;
        createPath();
    }
    
    

    public boolean write(Object javaObject)
    {
        Boolean result = false;
        String storeDatas = "Content error";
        ObjectMapper mapper = new ObjectMapper();
        try 
        {
            createPath();
            storeDatas = mapper.writeValueAsString(javaObject);
            Files.write(path, storeDatas.getBytes(StandardCharsets.UTF_8), StandardOpenOption.TRUNCATE_EXISTING);
            result = true;
        } 
        catch (IOException e) 
        {
            LOG.error(e);
        }
        return result;
    }



    @SuppressWarnings("unchecked")
    public <T> T load(TypeReference<T> typeRef)
    {
        T result = null;        
        try 
        {
            ObjectMapper mapper = new ObjectMapper();
            if (!path.toFile().exists()||Files.readAllBytes(path).length==0)
            {
                JavaType type = mapper.getTypeFactory().constructType(typeRef);
                Class<?> cls = type.getRawClass();                
                result = (T) cls.getConstructor().newInstance();
            }
            else
            {
                result = mapper.readValue(Files.readAllBytes(path), typeRef);
            }            
        } 
        catch (IOException | InstantiationException | IllegalAccessException | IllegalArgumentException | 
                InvocationTargetException | NoSuchMethodException | SecurityException ex)
        {
            throw new ProcessusException(ex);
        }
        return result;
    }
    
    
    
    private void createPath()
    {
        try
        {            
            LOG.debug("#####################: " + path);
            if (!path.toFile().exists())
            {
                LOG.debug(Files.createFile(path));
            }
        }
        catch (IOException ex)
        {
            LOG.error(ex);
        }
    }
}