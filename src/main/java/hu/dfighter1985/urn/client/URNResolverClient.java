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

package hu.dfighter1985.urn.client;

import hu.dfighter1985.urn.client.model.URNResolverResponse;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

public class URNResolverClient
{
    private final String endpoint;
    
    private final static String GETNBN = "/GetNBN";
    private final static String DELETENBN = "/DeleteNBN";
    private final static String REMAPNBN = "/RemapNBN";
    
    public URNResolverClient( final String endpoint )
    {
        this.endpoint = endpoint;
    }
    
    public URNResolverResponse resolveUrn( final String urn )
    {
        final Client client = ClientBuilder.newClient();
        final URNResolverResponse response = client
                .target( endpoint )
                .path( "/urns/" + urn )
                .request( MediaType.APPLICATION_XML )
                .get( URNResolverResponse.class );
        
        return response;
    }
    
    private ResolverResponse startLegacyURNProcess( final URL url ) throws IOException
    {
        final ResolverResponse response = new ResolverResponse();
        URLConnection connection = null;
        BufferedReader reader = null;
        String statusLine = null;
        String tidLine = null;
        
        try
        {
            connection = url.openConnection();
            
            reader = new BufferedReader(
                    new InputStreamReader(
                            new BufferedInputStream(
                                    connection.getInputStream() )  ) );
            
            statusLine = reader.readLine();
            tidLine = reader.readLine();            
        }
        finally
        {
            if( reader != null )
            {
                try
                {
                    reader.close();
                    reader = null;
                }
                catch( final IOException ioe )
                {                    
                }
            }
        }
        
        if( statusLine != null )
        {
            final String statusParts[] = statusLine.split( ":", 3 );
            response.status = statusParts[ 0 ];
            response.statusCode = Integer.parseInt( statusParts[ 1 ] );
            response.message = statusParts[ 2 ];
        }
        
        if( tidLine != null )
        {
            final String tidParts[] = tidLine.split( ":" );
            if( tidParts[ 0 ].equals( "tid" ) )
            {
                response.tid = tidParts[ 1 ];
            }
        }
        
        return response;
    }
    
    public ResolverResponse finishLegacyURNProcess( final URL url ) throws IOException
    {
        final ResolverResponse response = new ResolverResponse();
        URLConnection connection = null;
        BufferedReader reader = null;
        String line = null;
        
        try
        {
            connection = url.openConnection();
            
            reader = new BufferedReader(
                    new InputStreamReader(
                            new BufferedInputStream(
                                    connection.getInputStream() )  ) );
            
            line = reader.readLine();
        }
        finally
        {
            if( reader != null )
            {
                try
                {
                    reader.close();
                    reader = null;
                }
                catch( final IOException ioe )
                {                    
                }
            }
        }
        
        if( line != null )
        {
            final String parts[] = line.split( ":", 3 );
            if( parts.length == 3 )
            {
                response.status = parts[ 0 ];
                response.statusCode = Integer.parseInt( parts[ 1 ] );
                response.message = parts[ 2 ];
            }
        }
        
        return response;
    }
    
    public ResolverResponse startRegisterURN( final String url ) throws MalformedURLException, IOException
    {
        final URL u = new URL( endpoint + GETNBN + "?url=" + url );
        final ResolverResponse response = startLegacyURNProcess( u );
        return response;
    }
    
    public ResolverResponse confirmRegisterURN( final String urn, final String url, final String tid ) throws MalformedURLException, IOException
    {
        final URL u = new URL( endpoint + GETNBN + "?url=" + url + "&urn=" + urn + "&tid=" + tid );
        final ResolverResponse response = finishLegacyURNProcess( u );
        return response;
    }
    
    public ResolverResponse startDeleteURL( final String urn, final String url ) throws MalformedURLException, IOException
    {
        final URL u = new URL( endpoint + DELETENBN + "?urn=" + urn + "&url=" + url );
        final ResolverResponse response = startLegacyURNProcess( u );        
        return response;
    }
    
    public ResolverResponse confirmDeleteUrl( final String urn, final String url, final String tid ) throws MalformedURLException, IOException
    {
        final URL u = new URL( endpoint + DELETENBN + "?url=" + url + "&urn=" + urn + "&tid=" + tid );
        final ResolverResponse response = finishLegacyURNProcess( u );
        return response;
    }
    
    public ResolverResponse startModifyURL( final String urn, final String oldUrl, final String newUrl ) throws MalformedURLException, IOException
    {
        final URL u = new URL( endpoint + REMAPNBN + "?urn=" + urn + "&oldurl=" + oldUrl + "&newurl=" + newUrl );
        final ResolverResponse response = startLegacyURNProcess( u );                
        return response;
    }
    
    public ResolverResponse confirmModifyUrl(
            final String urn,
            final String oldUrl,
            final String newUrl,
            final String tid ) throws MalformedURLException, IOException
    {
        final URL u = new URL( endpoint + REMAPNBN + "?oldurl=" + oldUrl + "&newurl=" + newUrl + "&urn=" + urn + "&tid=" + tid );
        final ResolverResponse response = finishLegacyURNProcess( u );
        return response;
    }
}
