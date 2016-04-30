// ========================================================================
// Copyright 2006 Mort Bay Consulting Pty. Ltd.
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
// ========================================================================

package org.mortbay.cometd.ext;

import java.util.HashMap;
import java.util.Map;

import org.cometd.Bayeux;
import org.cometd.Extension;
import org.cometd.Message;


public class TimesyncExtension implements Extension
{
    public TimesyncExtension()
    {
    }
    
    public Message rcv(Message message)
    {
        return message;
    }

    public Message rcvMeta(Message message)
    {
        Map<String,Object> ext=(Map<String,Object>)message.get(Bayeux.EXT_FIELD);
        if (ext!=null)
        {
            Map<String,Object> sync=(Map<String,Object>)ext.get("timesync");
            if (sync!=null)
                sync.put("ts",new Long(System.currentTimeMillis()));
        }
        return message;
    }

    public Message send(Message message)
    {
        return message;
    }

    public Message sendMeta(Message message)
    {
        Message associated = message.getAssociated();
        if (associated!=null)
        {
            Map<String,Object> ext=(Map<String,Object>)associated.get(Bayeux.EXT_FIELD);
            if (ext!=null)
            {
                Map<String,Object> sync=(Map<String,Object>)ext.get("timesync");

                if (sync!=null)
                {
                    final long ts=((Long)sync.get("ts")).longValue();
                    final long p=System.currentTimeMillis()-ts;
                    sync.put("p",new Long(p));

                    ext=(Map<String,Object>)message.get(Bayeux.EXT_FIELD);
                    if (ext==null)
                    {
                        ext=new HashMap<String, Object>();
                        message.put(Bayeux.EXT_FIELD,ext);
                    }
                    ext.put("timesync",sync);
                }
            }
        }
        return message;
    }
}