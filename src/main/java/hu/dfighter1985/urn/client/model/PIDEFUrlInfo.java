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

package hu.dfighter1985.urn.client.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="url_info", namespace="http://nbn-resolving.org/pidef")
public class PIDEFUrlInfo
{
    @XmlElement(namespace="http://nbn-resolving.org/pidef")
    public String url;
    
    @XmlElement(namespace="http://nbn-resolving.org/pidef")
    public boolean frontpage;
    
    @XmlElement(namespace="http://nbn-resolving.org/pidef")
    public int origin;
    
    @XmlElement(namespace="http://nbn-resolving.org/pidef")
    public int status;
    
    @XmlElement(namespace="http://nbn-resolving.org/pidef")
    public String created;
    
    @XmlElement(namespace="http://nbn-resolving.org/pidef")
    public String last_modified;
}

