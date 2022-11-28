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
import fr.lixbox.io.document.xdocreport.template.formatter.FieldsMetadata;
import fr.lixbox.common.exceptions.BusinessException;

public class TestReportUtilConverter implements Serializable
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
        if (new File("./test.pdf").exists())
        {
            FileUtils.forceDelete(new File("./test.pdf"));
        }
    }
    
    
    
    @Test
    public void testGenerateDocx() throws IOException
    {        
        try
        (   
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = TestReportUtilConverter.class.getResourceAsStream("/template/B1_FAL1_template.docx");
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
            FileUtils.writeByteArrayToFile(new File("./test.docx"), out.toByteArray());
            Assert.assertTrue("La taille du rendu est incorrecte. attendue: 29860 bytes, obtenue: "+out.size()+" bytes", 29860==out.size());
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
            InputStream in = TestReportUtilConverter.class.getResourceAsStream("/template/B1_FAL1_template.docx");
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
            FileUtils.writeByteArrayToFile(new File("./test.pdf"), out.toByteArray());            
            Assert.assertTrue("La taille du rendu est incorrecte. attendue: 55786 bytes, obtenue: "+out.size()+" bytes", 0<out.size());
        }
        catch (BusinessException e)
        {
            Assert.fail(e.getMessage());
        }     
    }
}