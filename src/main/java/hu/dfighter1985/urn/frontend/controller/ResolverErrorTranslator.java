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

import java.util.HashMap;
import java.util.Map;

/**
 * Translates URN resolver client error message to message bundle keys
 *
 */
public class ResolverErrorTranslator
{
    private static final Map< String, String > map = new HashMap< String, String >();
    
    static {
        map.put( "wrong or missing URN in document", "error.wrong_or_missing_urn" );
        map.put( "Missing parameter (url)", "error.missing_url_parameter" );
        map.put( "Invalid url parameter", "error.invalid_url_parameter" );
        map.put( "URL already registered", "error.url_already_registered" );
        map.put( "Internal server error", "error.internal_server_error" );
        map.put( "Error generating URN", "error.error_generating_urn" );
        map.put( "Missing parameter (urn)", "error.missing_urn_parameter" );
        map.put( "Invalid URN", "error.invalid_urn" );
        map.put( "Invalid tid", "error.invalid_tid" );
        map.put( "URN already registered", "error.urn_already_registered" );
    }
    
    
    /**
     * Maps a URN resolver client error message to a message bundle key
     * 
     * @param errorMessage URN resolver client error messagge
     * @return Returns a message bundle key
     */
    public static String translate( final String errorMessage )
    {
        final String value = map.get( errorMessage );
        if( value != null )
        {
            return value;
        }
        else
        {
            return "error.error";
        }
    }
}
