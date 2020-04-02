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

import fr.lixbox.common.exceptions.BusinessException;
import fr.lixbox.common.util.ExceptionUtil;
import fr.lixbox.io.document.converter.Converter;
import fr.lixbox.io.document.converter.DocxToPDFConverter;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;

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
            report = XDocReportRegistry.getRegistry().loadReport(in, reportId+RandomUtils.nextDouble(), TemplateEngineKind.Velocity);
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
            ReferenceInsertionEventHandler rieh = (String s, Object o) -> {if (o == null) { return ""; } return o; };
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