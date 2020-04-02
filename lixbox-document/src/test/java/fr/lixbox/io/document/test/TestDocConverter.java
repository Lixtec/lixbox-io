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
package fr.lixbox.io.document.test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import fr.lixbox.io.document.util.ReportUtil;
import fr.lixbox.common.exceptions.BusinessException;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;

public class TestDocConverter implements Serializable
{
    // ----------- Attribut(s) -----------
    private static final long serialVersionUID = 201506181048L;
    


    // ----------- Methode(s) -----------
    @After
    public void tearOf() throws IOException
    {
        if (new File("./test.docx").exists())
        {
            FileUtils.forceDelete(new File("./test.docx"));
        }
    }
    
    
    
    @Test
    public void testGenerateDocx() throws IOException
    {        
        try
        (   
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = TestDocConverter.class.getResourceAsStream("/template/B1_FAL1_template.docx");
        )
        {   
            ReportUtil reportUtil = new ReportUtil(in, Calendar.getInstance().getTimeInMillis()+"");
                        
            //remplissage du contexte
            Map<String, Object> context = new HashMap<String, Object>();
            context.put("folder", "test");
            context.put("XA","X");
            context.put("XD", "");
            context.put("name_type", "mon navire");
            context.put("imo_number", "IMO1234586");
            context.put("call_sign", "123CALL");
            context.put("voyage_number", "VN_1234");
            context.put("port_call", "Arrival : FRMRS");
            context.put("flag_state", "FR");
            context.put("date_eta", "12/12/2018 12:30");
            context.put("date_etd", "12/12/2018 12:30");            
            FieldsMetadata metadatas = reportUtil.getFieldsMetadata();      
            reportUtil.generateReportDocxToDocx(out, context, metadatas);
            //FileUtils.writeByteArrayToFile(new File("./test.docx"), out.toByteArray());
            Assert.assertTrue("La taille du rendu est incorrecte. attendue: 15180 bytes, obtenue: "+out.size()+" bytes", 15180==out.size());
        }
        catch (BusinessException e)
        {
            Assert.fail(e.getMessage());
        }     
    }

    
    
    @Test
    public void testConvertDocxToPdf() throws IOException
    {        
        try
        (   
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = TestDocConverter.class.getResourceAsStream("/template/B1_FAL1_template.docx");
        )
        {   
            ReportUtil reportUtil = new ReportUtil(in, Calendar.getInstance().getTimeInMillis()+"");
                        
            //remplissage du contexte
            Map<String, Object> context = new HashMap<String, Object>();
            context.put("folder", "test");
            context.put("XA","X");
            context.put("XD", "");
            context.put("name_type", "mon navire");
            context.put("imo_number", "IMO1234586");
            context.put("call_sign", "123CALL");
            context.put("voyage_number", "VN_1234");
            context.put("port_call", "Arrival : FRMRS");
            context.put("flag_state", "FR");
            context.put("date_eta", "12/12/2018 12:30");
            context.put("date_etd", "12/12/2018 12:30");            
            FieldsMetadata metadatas = reportUtil.getFieldsMetadata();      
            reportUtil.generateReportDocxToPdf(out, context, metadatas);
//            FileUtils.writeByteArrayToFile(new File("./test.pdf"), out.toByteArray());            
            Assert.assertTrue("La taille du rendu est incorrecte. attendue: 55786 bytes, obtenue: "+out.size()+" bytes", 0<out.size());
        }
        catch (BusinessException e)
        {
            Assert.fail(e.getMessage());
        }     
    }
}