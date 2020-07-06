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
package fr.lixbox.io.xml.test;


import java.io.File;
import java.util.Calendar;

import fr.lixbox.common.stream.util.StreamStringUtil;
import fr.lixbox.common.util.StringUtil;
import fr.lixbox.io.xml.util.XMLUtil;
import junit.framework.TestCase;


/**
 * Cette classe permet de valider l'utilitaire de traitement
 * des strings.
 * 
 * @author ludovic.terral
 */
public class TestXmlUtil extends TestCase 
{
    // ----------- Attribut -----------
    

    
    
    // ----------- Methode -----------
    public TestXmlUtil(String arg0) 
    {
        super(arg0);
    }

    
    
    public void testXmlUtil_TransformerXslt() 
    {
        System.out.println("-------------------------------");
        System.out.println("Debut testXmlUtil_TransformerXslt");
        System.out.println("-------------------------------\n");
        final long debut = Calendar.getInstance().getTimeInMillis();
        
        
        // Preparation du test
        File inXslt = new File(this.getClass().getResource("/fr/lixbox/io/xml/testXmlUtil/transform.xsl").getFile());
        String content = StreamStringUtil.read(this.getClass().getResourceAsStream("/fr/lixbox/io/xml/testXmlUtil/in.xml"));
        
        
        // execution du test
        String result=null;
        try 
        {
            XMLUtil xmlUtil = new XMLUtil(content);            
            result = xmlUtil.transform(inXslt, "ISO-8859-1");
        } 
        catch (Exception e)
        {
            System.err.println(e.getMessage());
            assertNull("Exception levee non attendue", e);
        }   
        

        //Verification des resultats
        System.out.println(result);
        if (!StringUtil.isEmpty(result) && result.contains("etatmat"))
        {
            System.out.println("==== Resultat testXmlUtil_TransformerXslt: Succes\n");
        }
        else
        {
            System.out.println("==== Resultat testXmlUtil_TransformerXslt: Echec");
            assertNull("testHttpUtil_InterrogerServeur: Echec", "");
        } 

        
        final long duree = Calendar.getInstance().getTimeInMillis() - debut;
        System.out.println("duree:" + duree + " ms");  
        System.out.println("----------------------------");
        System.out.println("Fin testXmlUtil_TransformerXslt");
        System.out.println("-----------------------------\n");   
    }
}
