// ========================================================================
// Copyright 2007 Mort Bay Consulting Pty. Ltd.
// ------------------------------------------------------------------------
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at 
// http://www.apache.org/licenses/LICENSE-2.0
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//========================================================================

package org.mortbay.cometd;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.cometd.Message;
import org.mortbay.util.ajax.JSON;


/* ------------------------------------------------------------ */
/**
 * @author aabeling
 * @author gregw
 * 
 */
public class JSONPTransport extends AbstractTransport
{
    public final static String __DEFAULT_CALLBACK="jsonpcallback";
    int _responses=0;
    PrintWriter _out;
    String _jsonp= null;
    boolean _commented;
    String _mimeType;
    
    public JSONPTransport(boolean commented,String jsonp)
    {
        setJSONCommented(commented);
        _commented=commented;
        _jsonp=jsonp;
    }
    
    public void send(Message message) throws IOException
    {
        if (message!=null)
        {
            if (_responses==0)
            {
                HttpServletResponse response=getResponse();
                response.setContentType(_mimeType);
                _out=response.getWriter();
                if (_commented)
                    _out.write("/*");
                _out.write(this._jsonp==null?__DEFAULT_CALLBACK:_jsonp);
                _out.write("([");
            }
            else
            {
                _out.write(",\r\n");
            }

            String r=(message instanceof MessageImpl)
                ?((MessageImpl)message).getJSON()
                :JSON.toString(message);
            ((MessageImpl)message).decRef();
            _responses++;
            _out.write(r);
        }
    }

    public void complete() throws IOException
    {
        HttpServletResponse response=getResponse();
        response.setStatus(200);

        if (_responses==0)
        {
            response.setContentType(_mimeType);
            _out=response.getWriter();
            if (_commented)
                _out.write("/*");
            _out.write(this._jsonp==null?__DEFAULT_CALLBACK:_jsonp);
            _out.write("([");
        }
        if (_commented)
            _out.write("])*/\r\n");
        else
            _out.write("])\r\n");
        _out.close();
    }

    /* ------------------------------------------------------------ */
    public boolean resumePoll()
    {
        return true;
    }

    /* ------------------------------------------------------------ */
    public String getJsonp()
    {
        return _jsonp;
    }

    /* ------------------------------------------------------------ */
    @Override
    public String toString()
    {
        return "JSONPTransport[jsonp="+this._jsonp+"]";
    }

    /* ------------------------------------------------------------ */
    /**
     * @return the commented
     */
    public boolean isJSONCommented()
    {
        return _commented;
    }

    /* ------------------------------------------------------------ */
    /**
     * @param commented the commented to set
     */
    public void setJSONCommented(boolean commented)
    {
        _commented=commented;
        _mimeType=commented?"text/javascript-comment-filtered; charset=utf-8":"text/javascript; charset=utf-8";
    }
}