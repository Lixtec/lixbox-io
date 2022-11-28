/**
 * Copyright (C) 2011-2015 The XDocReport Team <xdocreport@googlegroups.com>
 *
 * All rights reserved.
 *
 * Permission is hereby granted, free  of charge, to any person obtaining
 * a  copy  of this  software  and  associated  documentation files  (the
 * "Software"), to  deal in  the Software without  restriction, including
 * without limitation  the rights to  use, copy, modify,  merge, publish,
 * distribute,  sublicense, and/or sell  copies of  the Software,  and to
 * permit persons to whom the Software  is furnished to do so, subject to
 * the following conditions:
 *
 * The  above  copyright  notice  and  this permission  notice  shall  be
 * included in all copies or substantial portions of the Software.
 *
 * THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 * EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 * MERCHANTABILITY,    FITNESS    FOR    A   PARTICULAR    PURPOSE    AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE,  ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package fr.lixbox.io.document.xdocreport.template.velocity.cache;

import java.io.Reader;

import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.resource.Resource;
import org.apache.velocity.runtime.resource.loader.ResourceLoader;
import org.apache.velocity.util.ExtProperties;

import fr.lixbox.io.document.xdocreport.core.io.IEntryInfo;
import fr.lixbox.io.document.xdocreport.core.io.XDocArchive;
import fr.lixbox.io.document.xdocreport.template.ITemplateEngine;
import fr.lixbox.io.document.xdocreport.template.cache.ITemplateCacheInfoProvider;
import fr.lixbox.io.document.xdocreport.template.utils.TemplateUtils;
import fr.lixbox.io.document.xdocreport.template.velocity.VelocityConstants;

/**
 * Velocity resource loader {@link ResourceLoader} implementation used to cache entry name of {@link XDocArchive} which
 * must be merged with Java model with velocity template engine.
 */
public class XDocReportEntryResourceLoader
    extends ResourceLoader
    implements VelocityConstants
{

    private ITemplateEngine templateEngine = null;

    @Override
    public void commonInit( RuntimeServices rs, ExtProperties configuration )
    {
        super.commonInit( rs, configuration );
        this.templateEngine = (ITemplateEngine) rs.getProperty( VELOCITY_TEMPLATE_ENGINE_KEY );
    }

    @Override
    public void init( ExtProperties configuration )
    {
        // Do nothing
    }

    @Override
    public Reader getResourceReader(String source, String encoding)
            throws ResourceNotFoundException
    {
        IEntryInfo cacheInfo =
            TemplateUtils.getTemplateCacheInfo( templateEngine.getTemplateCacheInfoProvider(), source );
        if ( cacheInfo != null )
        {
            Reader reader = cacheInfo.getReader();
            if ( reader != null )
            {
                return reader;
            }
        }
        throw new ResourceNotFoundException( "Cannot find input stream for the entry with source=" + source );
        //
        //
        // InputStream inputStream = getEntryInputStream(source);
        // if (inputStream == null) {
        // throw new ResourceNotFoundException(
        // "Cannot find input stream for the entry with source="
        // + source);
        // }
        // return inputStream;
    }

    /**
     * Overrides superclass for better performance.
     */
    @Override
    public boolean resourceExists( String resourceName )
    {
        ITemplateCacheInfoProvider templateCacheInfoProvider = templateEngine.getTemplateCacheInfoProvider();
        if ( templateCacheInfoProvider == null )
        {
            return false;
        }
        IEntryInfo cacheInfo =
            TemplateUtils.getTemplateCacheInfo( templateEngine.getTemplateCacheInfoProvider(), resourceName );
        return ( cacheInfo != null );
    }

    /**
     * @see org.apache.velocity.runtime.resource.loader.ResourceLoader#isSourceModified(org.apache.velocity.runtime.resource.Resource)
     */
    public boolean isSourceModified( Resource resource )
    {
        return getLastModified( resource ) != resource.getLastModified();
    }

    /**
     * @see org.apache.velocity.runtime.resource.loader.ResourceLoader#getLastModified(org.apache.velocity.runtime.resource.Resource)
     */
    public long getLastModified( Resource resource )
    {
        String resourceName = resource.getName();
        IEntryInfo cacheInfo =
            TemplateUtils.getTemplateCacheInfo( templateEngine.getTemplateCacheInfoProvider(), resourceName );
        if ( cacheInfo != null )
        {
            return cacheInfo.getLastModified();
        }
        return 0;
    }

}
