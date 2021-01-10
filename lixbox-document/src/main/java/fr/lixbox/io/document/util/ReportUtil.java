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
package fr.lixbox.io.document.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.docx4j.model.datastorage.migration.VariablePrepare;
import org.docx4j.model.fields.merge.DataFieldName;
import org.docx4j.model.fields.merge.MailMerger;
import org.docx4j.model.fields.merge.MailMerger.OutputField;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;

import fr.lixbox.common.exceptions.BusinessException;
import fr.lixbox.common.util.ExceptionUtil;
import fr.lixbox.io.document.converter.Converter;
import fr.lixbox.io.document.converter.DocxToPDFConverter;

/**
 * Cet utilitaire genere un rapport depuis un template word vers un flux binaire.
 * 
 * @author ludovic.terral
 */
public class ReportUtil
{
    // ----------- Attribut -----------   
    private static final Log LOG = LogFactory.getLog(ReportUtil.class);    
    private static final String REPORT_UTIL_CODE = "RPTUTL";

    private InputStream template;
    
    

    // ----------- Methode -----------
    public ReportUtil(InputStream template) throws BusinessException
    {       
        try        
        {
            LOG.debug("ReportUtil cree");
            this.template = template;
        }
        catch (Exception e)
        {
            ExceptionUtil.traiterException(e, REPORT_UTIL_CODE, true);
        }
    }
    
    
    
    public void generateReportDocxToDocx(OutputStream out, Map<String, String> datas) 
        throws BusinessException
    {
        try
        {
            WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(template);
            VariablePrepare.prepare(wordMLPackage);
            MainDocumentPart mainDocumentPart = wordMLPackage.getMainDocumentPart();
            mainDocumentPart.addStyledParagraphOfText("Title", "Hello World!");
            mainDocumentPart.addParagraphOfText("Welcome To Baeldung");
            mainDocumentPart.variableReplace(datas);
            
            List<Map<DataFieldName, String>> mergedFields = new ArrayList<>();
            Map<DataFieldName, String> item = new HashMap<>();
            for (Entry<String, String> entry : datas.entrySet())
            {
	            item.put(new DataFieldName("$"+entry.getKey()), entry.getValue());
	            mergedFields.add(item);
            }
            MailMerger.setMERGEFIELDInOutput(OutputField.REMOVED);
            MailMerger.performMerge(wordMLPackage, item, true);
            
            
            //generate word
            long start = System.currentTimeMillis();
            wordMLPackage.save(out);
            out.close();
            LOG.debug("Report processed in " + (System.currentTimeMillis() - start) + " ms");
        }
        catch (Exception e)
        {
            ExceptionUtil.traiterException(e, REPORT_UTIL_CODE, true);
        }
    }



    public void generateReportDocxToPdf(OutputStream out, Map<String, String> datas) 
        throws BusinessException
    {        
        byte[] reportDatas=new byte[0];
        
        //generation du report
        try (ByteArrayOutputStream outTmp = new ByteArrayOutputStream())
        {
            generateReportDocxToDocx(outTmp, datas);
            reportDatas = outTmp.toByteArray();
        }
        catch (Exception e)
        {
            ExceptionUtil.traiterException(e, REPORT_UTIL_CODE, true);
        }
    
        
        //convertion vers docx        
        try (            
            ByteArrayInputStream inTmp = new ByteArrayInputStream(reportDatas);
        )
        {
            long start = System.currentTimeMillis();
            Converter converter = new DocxToPDFConverter(inTmp, out);
            converter.convert();
            LOG.debug("Pdf Report processed in " + (System.currentTimeMillis() - start) + " ms");
        }
        catch (Exception e)
        {
            ExceptionUtil.traiterException(e, REPORT_UTIL_CODE, true);
        }
    }   
}