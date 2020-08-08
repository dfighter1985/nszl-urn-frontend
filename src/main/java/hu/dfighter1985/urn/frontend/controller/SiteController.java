/*
MIT License
Copyright (c) 2020 dfighter1985
Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:
The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package hu.dfighter1985.urn.frontend.controller;

import hu.dfighter1985.urn.client.ResolverResponse;
import hu.dfighter1985.urn.client.URNResolverClient;
import hu.dfighter1985.urn.client.model.URNResolverResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class SiteController
{
    private static final Logger LOG = Logger.getLogger( SiteController.class );
    
    private final URNResolverClient client;
    
    @Autowired
    public SiteController( final URNResolverClient client )
    {
        this.client = client;
        LOG.info( "Starting up..." );
    }
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView showIndex()
    {
        return new ModelAndView( "index" );
    }
    
    private ModelAndView redirectToUrl( final URNResolverResponse response )
    {
        ModelAndView mv;
        final String url = response.data.resolving_information.url_info.url;
        mv =  new ModelAndView( new RedirectView( url ) );
        return mv;
    }
    
    private ModelAndView showUrls( final URNResolverResponse response )
    {
        final List< String > urls = new ArrayList< String >();
        urls.add( response.data.resolving_information.url_info.url );
        
        final ModelAndView mv = new ModelAndView( "resolve" );
        mv.addObject( "urn", response.data.resolving_information.pi_info.identifier );
        mv.addObject( "urls", urls );
        return mv;
    }
    
    private ModelAndView resolveUrn( final String urn, final boolean redirect )
    {
        ModelAndView mv;
        
        try
        {
            final URNResolverResponse response = client.resolveUrn( urn );
            if( redirect )
                return redirectToUrl( response );
            else
                return showUrls( response );
        }        
        catch( final RuntimeException rte )
        {
            LOG.error( "Failed to resolve URN: " + urn, rte );
            mv = new ModelAndView( "resolve" );
            mv.addObject( "Error", "Error resolving URN" );
        }
        
        return mv;
    }
    
    @RequestMapping(value = "/resolve", method = RequestMethod.GET)
    public ModelAndView showResolve(
            @RequestParam(required = false) String urnid,
            @RequestParam(required = false) boolean redirect )
    {
        if( urnid == null || urnid.isEmpty() )
        {
            return new ModelAndView( "resolve" );
        }
        
        final ModelAndView mv = resolveUrn( urnid, redirect );
        return mv;
    }
    
    private ModelAndView registerUrn( final String url )
    {
        ResolverResponse response = null;
        
        try
        {
            response = client.startRegisterURN( url );
        }
        catch( final MalformedURLException mue )
        {
            LOG.error( "", mue );
        }
        catch( final IOException ioe )
        {
            LOG.error( "", ioe );
        }
        
        if( response == null )
        {
            final ModelAndView mv = new ModelAndView( "register" );
            mv.addObject( "Error", "Error registering URN" );
            return mv;
        }
        
        if( response.statusCode != 0 )
        {
            final ModelAndView mv = new ModelAndView( "register" );
            mv.addObject( "Error", "Error registering URN: " + response.message );
            return mv;
        }
        
        final ModelAndView mv = new ModelAndView( "register_confirm" );
        mv.addObject( "URN", response.message );
        mv.addObject( "URL", url );
        mv.addObject( "TID", response.tid );
        return mv;
    }
    
    private ModelAndView confirmUrn( final String urn, final String url, final String tid )
    {
        ResolverResponse response = null;
        
        try
        {
            response = client.confirmRegisterURN( urn, url, tid );
        }
        catch( final MalformedURLException mue )
        {
            LOG.error( "", mue );
        }
        catch( final IOException ioe )
        {
            LOG.error( "", ioe );
        }
        
        final ModelAndView mv = new ModelAndView( "register_confirm" );
        
        if( response == null )
        {
            mv.addObject( "Error", "Error registering URN" );
        }
        else
        if( response.statusCode != 0 )
        {
            mv.addObject( "Error", "Error registering URN: " + response.message );
        }
        else
        {
            mv.addObject( "URN", urn );
            mv.addObject( "URL", url );
            mv.addObject( "TID", tid );
            mv.addObject( "Message", "URN successfully registered." );
        }
        
        return mv;
    }
    
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView showRegister(
            @RequestParam(required = false) final String urn,
            @RequestParam(required = false) final String url,
            @RequestParam(required = false) final String tid )
    {
        if( url != null && !url.isEmpty() &&
                urn != null && !urn.isEmpty() &&
                tid != null && !tid.isEmpty() )
        {
            return confirmUrn( urn, url, tid );
        }
        else
        if( url != null && !url.isEmpty() )
        {
            return registerUrn( url );
        }
        else
        {
            final ModelAndView mv = new ModelAndView( "register" );
            return mv;
        }
    }
    
    private ModelAndView deleteUrnUrlPair( final String urn, final String url )
    {
        ResolverResponse response = null;
        
        final ModelAndView mv = new ModelAndView();
        mv.addObject( "urn", urn );
        mv.addObject( "url", url );        
        
        try
        {
            response = client.startDeleteURL( urn, url );
        }
        catch( final MalformedURLException mue )
        {
            LOG.error( "", mue );
        }
        catch( final IOException ioe )
        {
            LOG.error( "", ioe );
        }
        
        if( response == null )
        {
            mv.setViewName( "delete" );
            mv.addObject( "Error", "Error deleting URN - URL pair." );
        }
        else
        if( response.statusCode != 0 )
        {
            mv.setViewName( "delete" );
            mv.addObject( "Error", "Error deleting URN - URL pair: " + response.message );
        }
        else
        {
            mv.setViewName( "delete_confirm" );
            mv.addObject( "tid", response.tid );
        }
        
        return mv;
    }
    
    private ModelAndView confirmDelete( final String urn, final String url, final String tid )
    {
        ResolverResponse response = null;
        
        final ModelAndView mv = new ModelAndView( "delete_confirm" );
        mv.addObject( "urn", urn );
        mv.addObject( "url", url );
        mv.addObject( "tid", tid );
        
        try
        {
            response = client.confirmDeleteUrl( urn, url, tid );
        }
        catch( final MalformedURLException mue )
        {
            LOG.error( "", mue );
        }
        catch( final IOException ioe )
        {
            LOG.error( "", ioe );
        }
        
        if( response == null )
        {
            mv.addObject( "Error", "Error deleting urn - url pair!" );
        }
        else
        if( response.statusCode != 0 )
        {
            mv.addObject( "Error", "Error deleting urn - url pair: " + response.message );
        }
        else
        {
            mv.addObject( "Message", "Successfully deleted urn - url pair!" );
        }
        
        return mv;
    }
    
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ModelAndView showDelete(
            @RequestParam(required = false) final String urn,
            @RequestParam(required = false) final String url,
            @RequestParam(required = false) final String tid)
    {
        if( urn != null && !urn.isEmpty() &&
                url != null && !url.isEmpty() &&
                tid != null && !tid.isEmpty() )
        {
            return confirmDelete( urn, url, tid );
        }
        else
        if( urn != null && !urn.isEmpty() && url != null && !url.isEmpty())
        {
            return deleteUrnUrlPair( urn, url );
        }
        
        final ModelAndView mv = new ModelAndView( "delete" );
        return mv;
    }
    
    
    private ModelAndView remapUrn(
            final String urn,
            final String oldurl,
            final String newurl )
    {
        ResolverResponse response = null;
        
        final ModelAndView mv = new ModelAndView();
        mv.addObject( "urn", urn );
        mv.addObject( "oldurl", oldurl );
        mv.addObject( "newurl", newurl );
        
        try
        {
            response = client.startModifyURL( urn, oldurl, newurl );
        }
        catch( final MalformedURLException mue )
        {
            LOG.error( "", mue );
        }
        catch( final IOException ioe )
        {
            LOG.error( "", ioe );
        }
        
        if( response == null )
        {
            mv.setViewName( "remap" );
            mv.addObject( "Error", "Error remapping" );
        }
        else
        {
            if( response.statusCode != 0 )
            {
                mv.setViewName( "remap" );
                mv.addObject( "Error", "Error remapping: " + response.message );
            }
            else
            {
                mv.setViewName( "remap_confirm" );
                mv.addObject( "tid", response.tid );
            }
        }
        
        return mv;
    }
    
    private ModelAndView confirmRemap( final String urn,
            final String oldurl,
            final String newurl,
            final String tid )
    {
        final ModelAndView mv = new ModelAndView( "remap_confirm" );
        mv.addObject( "urn", urn);
        mv.addObject( "oldurl", oldurl );
        mv.addObject( "newurl", newurl );
        mv.addObject( "tid", tid);
        
        ResolverResponse response = null;
        
        try
        {
            response = client.confirmModifyUrl(urn, oldurl, newurl, tid );
        }
        catch( final MalformedURLException mue )
        {
            LOG.error( "", mue );
        }
        catch( final IOException ioe )
        {
            LOG.error( "", ioe );
        }
        
        if( response == null )
        {
            mv.addObject( "Error", "Error confirming" );
        }
        else
        {
            if( response.statusCode != 0 )
            {
                mv.addObject( "Error", "Error confirming remap: " + response.message );
            }
            else
            {
                mv.addObject( "Message", "Remap successful!" );
            }
        }
        
        return mv;
    }
    
    @RequestMapping(value = "/remap", method = RequestMethod.GET)
    public ModelAndView showRemap(
            @RequestParam(required = false) final String urn,
            @RequestParam(required = false) final String oldurl,
            @RequestParam(required = false) final String newurl,
            @RequestParam(required = false) final String tid
    )
    {
        if( urn != null && !urn.isEmpty() &&
            oldurl != null && !oldurl.isEmpty() &&
            newurl != null && !newurl.isEmpty() )
        {
            if( tid != null && !tid.isEmpty() )
            {
                return confirmRemap( urn, oldurl, newurl, tid );
            }
            else
            {
                return remapUrn( urn, oldurl, newurl );
            }
        }
        
        final ModelAndView mv = new ModelAndView();
        mv.setViewName( "remap" );
        return mv;
    }
}
