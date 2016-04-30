/**
 * 
 */
package org.mortbay.cometd;

import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import junit.framework.TestCase;

import org.cometd.Channel;

/**
 * @author athena
 *
 */
public class ChannelImplTest extends TestCase
{
    private Channel _channel;
    private AbstractBayeux _bayeux;
    public void setUp() throws Exception 
    {
        _bayeux = new BayeuxStub();
    }

    public void testRemoves() throws Exception
    {
        String[][] tests = new String[][] {
           // added,  expected   , remove , removed, expected
           {"/test", "[/, /test]", "/test", "/test", "[/]"},
           {"/test/123", "[/, /test, /test/123]", "/test/123", "/test/123", "[/, /test]"},
           {"/test/123", "[/, /test, /test/123]", "/test/abc", null, "[/, /test, /test/123]"},
           {"/test/123", "[/, /test, /test/123]", "/123", null, "[/, /test, /test/123]"},
           {"/test/123", "[/, /test, /test/123]", "/test", null, "[/, /test, /test/123]"}
        };
        
        for ( String[] test : tests )
        {
            _bayeux.getChannel(test[0], true);
            assertEquals(test[1], _bayeux.getChannels().toString());
            
            Channel removed = _bayeux.removeChannel(test[2]);
            assertEquals(test[3], removed == null? null : removed.toString());
            assertEquals(test[4], _bayeux.getChannels().toString());
        }
    }
    
    
    static class BayeuxStub extends AbstractBayeux
    {
        public BayeuxStub()
        {
            try
            {
                _random=SecureRandom.getInstance("SHA1PRNG");
            }
            catch (Exception e)
            {
                _random=new Random();
            }

            _random.setSeed(_random.nextLong()^hashCode()^Runtime.getRuntime().freeMemory());
            _channelIdCache=new ConcurrentHashMap<String, ChannelId>();
        }
         
        public ClientImpl newRemoteClient()
        {
            return null;
        }
        
    }
}
