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
import java.util.Map;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.event.EventCartridge;
import org.apache.velocity.app.event.ReferenceInsertionEventHandler;
import org.apache.velocity.context.Context;

import fr.lixbox.common.exceptions.BusinessException;
import fr.lixbox.common.util.ExceptionUtil;
import fr.lixbox.io.document.converter.Converter;
import fr.lixbox.io.document.converter.DocxToPDFConverter;
import fr.lixbox.io.document.xdocreport.document.IXDocReport;
import fr.lixbox.io.document.xdocreport.document.registry.XDocReportRegistry;
import fr.lixbox.io.document.xdocreport.template.IContext;
import fr.lixbox.io.document.xdocreport.template.TemplateEngineKind;
import fr.lixbox.io.document.xdocreport.template.formatter.FieldsMetadata;

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

    private IXDocReport report = null;
    
    

    // ----------- Methode -----------
    public ReportUtil(InputStream in, String reportId) throws BusinessException
    {       
        try        
        {
            LOG.debug("ReportUtil cree");
            report = XDocReportRegistry.getRegistry().loadReport(in, reportId+RandomUtils.secure().randomDouble(), TemplateEngineKind.Velocity);
        }
        catch (Exception e)
        {
            ExceptionUtil.traiterException(e, REPORT_UTIL_CODE, true);
        }
    }
    
    
    
    public FieldsMetadata getFieldsMetadata()
    {
        return report.createFieldsMetadata();
    }
    
    
    
    public void generateReportDocxToDocx(OutputStream out, 
            Map<String, Object> datas, FieldsMetadata metadatas) 
        throws BusinessException
    {
        try
        {
            //remplissage du contexte
            IContext context = report.createContext();  
            EventCartridge eventCartridge = new EventCartridge();
            ReferenceInsertionEventHandler rieh = (Context c, String s, Object o) -> {if (o == null) { return ""; } return o; };
            eventCartridge.addEventHandler(rieh);            
            eventCartridge.attachToContext((VelocityContext) context);         
            report.setFieldsMetadata(metadatas);
            context.putMap(datas);
            
            //generate word
            long start = System.currentTimeMillis();
            report.process(context, out);
            LOG.debug("Report processed in " + (System.currentTimeMillis() - start) + " ms");
        }
        catch (Exception e)
        {
            ExceptionUtil.traiterException(e, REPORT_UTIL_CODE, true);
        }
    }

        
    
    public void generateReportDocxToPdf(OutputStream out, 
            Map<String, Object> datas, FieldsMetadata metadatas) 
        throws BusinessException
    {        
        byte[] reportDatas=new byte[0];
        
        //generation du report
        try (
                ByteArrayOutputStream outTmp = new ByteArrayOutputStream();
        )
        {
            generateReportDocxToDocx(outTmp, datas, metadatas);
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