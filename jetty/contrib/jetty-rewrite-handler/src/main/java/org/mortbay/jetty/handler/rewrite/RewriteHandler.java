//========================================================================
//$Id: RewriteHandler.java 1143 2008-07-23 05:03:31Z gregw $
//Copyright 2004-2005 Mort Bay Consulting Pty. Ltd.
//------------------------------------------------------------------------
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at 
//http://www.apache.org/licenses/LICENSE-2.0
//Unless required by applicable law or agreed to in writing, software
//distributed under the License is distributed on an "AS IS" BASIS,
//WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//See the License for the specific language governing permissions and
//limitations under the License.
//========================================================================
package org.mortbay.jetty.handler.rewrite;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mortbay.jetty.HttpConnection;
import org.mortbay.jetty.Request;
import org.mortbay.jetty.handler.HandlerWrapper;
import org.mortbay.jetty.servlet.PathMap;
import org.mortbay.log.Log;
import org.mortbay.util.LazyList;

/* ------------------------------------------------------------ */
/**
 *<p> Rewrite handler is responsible for managing the rules. Its capabilities
 * is not only limited for url rewrites such as RewritePatternRule or RewriteRegexRule. 
 * There is also handling for cookies, headers, redirection, setting status or error codes 
 * whenever the rule finds a match. 
 * 
 * <p> The rules can be matched by the ff. options: pattern matching of PathMap 
 * (class PatternRule), regular expressions (class RegexRule) or certain conditions set 
 * (e.g. MsieSslRule - the requests must be in SSL mode).
 * 
 * Here are the list of rules:
 * <ul>
 * <li> CookiePatternRule - adds a new cookie in response. </li>
 * <li> HeaderPatternRule - adds/modifies the HTTP headers in response. </li>
 * <li> RedirectPatternRule - sets the redirect location. </li>
 * <li> ResponsePatternRule - sets the status/error codes. </li>
 * <li> RewritePatternRule - rewrites the requested URI. </li>
 * <li> RewriteRegexRule - rewrites the requested URI using regular expression for pattern matching. </li>
 * <li> MsieSslRule - disables the keep alive on SSL for IE5 and IE6. </li>
 * <li> LegacyRule - the old version of rewrite. </li>
 * <li> ForwardedSchemeHeaderRule - set the scheme according to the headers present. </li>
 * </ul>
 *
 * <p> The rules can be grouped into rule containers (class RuleContainerRule), and will only 
 * be applied if the request matches the conditions for their container
 * (e.g., by virtual host name)
 *
 * Here are a list of rule containers:
 * <ul>
 * <li> VirtualHostRuleContainerRule - checks whether the request matches one of a set of virtual host names.</li>
 * </ul>
 * 
 * Here is a typical jetty.xml configuration would be: <pre>
 * 
 *   &lt;Set name="handler"&gt;
 *     &lt;New id="Handlers" class="org.mortbay.jetty.handler.rewrite.RewriteHandler"&gt;
 *       &lt;Set name="rules"&gt;
 *         &lt;Array type="org.mortbay.jetty.handler.rewrite.Rule"&gt;
 *
 *           &lt;Item&gt; 
 *             &lt;New id="rewrite" class="org.mortbay.jetty.handler.rewrite.RewritePatternRule"&gt;
 *               &lt;Set name="pattern"&gt;/*&lt;/Set&gt;
 *               &lt;Set name="replacement"&gt;/test&lt;/Set&gt;
 *             &lt;/New&gt;
 *           &lt;/Item&gt;
 *
 *           &lt;Item&gt; 
 *             &lt;New id="response" class="org.mortbay.jetty.handler.rewrite.ResponsePatternRule"&gt;
 *               &lt;Set name="pattern"&gt;/session/&lt;/Set&gt;
 *               &lt;Set name="code"&gt;400&lt;/Set&gt;
 *               &lt;Set name="reason"&gt;Setting error code 400&lt;/Set&gt;
 *             &lt;/New&gt;
 *           &lt;/Item&gt;
 *
 *           &lt;Item&gt; 
 *             &lt;New id="header" class="org.mortbay.jetty.handler.rewrite.HeaderPatternRule"&gt;
 *               &lt;Set name="pattern"&gt;*.jsp&lt;/Set&gt;
 *               &lt;Set name="name"&gt;server&lt;/Set&gt;
 *               &lt;Set name="value"&gt;dexter webserver&lt;/Set&gt;
 *             &lt;/New&gt;
 *           &lt;/Item&gt;
 *
 *           &lt;Item&gt; 
 *             &lt;New id="header" class="org.mortbay.jetty.handler.rewrite.HeaderPatternRule"&gt;
 *               &lt;Set name="pattern"&gt;*.jsp&lt;/Set&gt;
 *               &lt;Set name="name"&gt;title&lt;/Set&gt;
 *               &lt;Set name="value"&gt;driven header purpose&lt;/Set&gt;
 *             &lt;/New&gt;
 *           &lt;/Item&gt;
 *
 *           &lt;Item&gt; 
 *             &lt;New id="redirect" class="org.mortbay.jetty.handler.rewrite.RedirectPatternRule"&gt;
 *               &lt;Set name="pattern"&gt;/test/dispatch&lt;/Set&gt;
 *               &lt;Set name="location"&gt;http://jetty.mortbay.org&lt;/Set&gt;
 *             &lt;/New&gt;
 *           &lt;/Item&gt;
 *
 *           &lt;Item&gt; 
 *             &lt;New id="regexRewrite" class="org.mortbay.jetty.handler.rewrite.RewriteRegexRule"&gt;
 *               &lt;Set name="regex"&gt;/test-jaas/$&lt;/Set&gt;
 *               &lt;Set name="replacement"&gt;/demo&lt;/Set&gt;
 *             &lt;/New&gt;
 *           &lt;/Item&gt;
 *           
 *           &lt;Item&gt; 
 *             &lt;New id="forwardedHttps" class="org.mortbay.jetty.handler.rewrite.ForwardedSchemeHeaderRule"&gt;
 *               &lt;Set name="header"&gt;X-Forwarded-Scheme&lt;/Set&gt;
 *               &lt;Set name="headerValue"&gt;https&lt;/Set&gt;
 *               &lt;Set name="scheme"&gt;https&lt;/Set&gt;
 *             &lt;/New&gt;
 *           &lt;/Item&gt;
 *           
 *           &lt;Item&gt;
 *             &lt;New id="virtualHost" class="org.mortbay.jetty.handler.rewrite.VirtualHostRuleContainer"&gt;
 *
 *               &lt;Set name="virtualHosts"&gt;
 *                 &lt;Array type="java.lang.String"&gt;
 *                   &lt;Item&gt;mortbay.com&lt;/Item&gt;
 *                   &lt;Item&gt;www.mortbay.com&lt;/Item&gt;
 *                   &lt;Item&gt;mortbay.org&lt;/Item&gt;
 *                   &lt;Item&gt;www.mortbay.org&lt;/Item&gt;
 *                 &lt;/Array&gt;
 *               &lt;/Set&gt;
 *
 *               &lt;Call name="addRule"&gt;
 *                 &lt;Arg&gt;
 *                   &lt;New class="org.mortbay.jetty.handler.rewrite.CookiePatternRule"&gt;
 *                     &lt;Set name="pattern"&gt;/*&lt;/Set&gt;
 *                     &lt;Set name="name"&gt;CookiePatternRule&lt;/Set&gt;
 *                     &lt;Set name="value"&gt;1&lt;/Set&gt;
 *                   &lt;/New&gt;
 *                 &lt;/Arg&gt;
 *               &lt;/Call&gt;
 *    
 *             &lt;/New&gt;
 *           &lt;/      Item&gt;
 * 
 *         &lt;/Array&gt;
 *       &lt;/Set&gt;
 *
 *       &lt;Set name="handler"&gt;
 *         &lt;New id="Handlers" class="org.mortbay.jetty.handler.HandlerCollection"&gt;
 *           &lt;Set name="handlers"&gt;
 *            &lt;Array type="org.mortbay.jetty.Handler"&gt;
 *              &lt;Item&gt;
 *                &lt;New id="Contexts" class="org.mortbay.jetty.handler.ContextHandlerCollection"/&gt;
 *              &lt;/Item&gt;
 *              &lt;Item&gt;
 *                &lt;New id="DefaultHandler" class="org.mortbay.jetty.handler.DefaultHandler"/&gt;
 *              &lt;/Item&gt;
 *              &lt;Item&gt;
 *                &lt;New id="RequestLog" class="org.mortbay.jetty.handler.RequestLogHandler"/&gt;
 *              &lt;/Item&gt;
 *            &lt;/Array&gt;
 *           &lt;/Set&gt;
 *         &lt;/New&gt;
 *       &lt;/Set&gt;
 *
 *     &lt;/New&gt;
 *   &lt;/Set&gt;
 * </pre>
 * 
 */
public class RewriteHandler extends HandlerWrapper
{
    
    private RuleContainer _rules;
    
    /* ------------------------------------------------------------ */
    public RewriteHandler()
    {
        _rules = new RuleContainer();
    }

    /* ------------------------------------------------------------ */
    /**
     * To enable configuration from jetty.xml on rewriteRequestURI, rewritePathInfo and
     * originalPathAttribute
     * 
     * @param legacyRule old style rewrite rule
     */
    public void setLegacyRule(LegacyRule legacyRule)
    {
        _rules.setLegacyRule(legacyRule);
    }

    /* ------------------------------------------------------------ */
    /**
     * Returns the list of rules.
     * @return an array of {@link Rule}.
     */
    public Rule[] getRules()
    {
        return _rules.getRules();
    }

    /* ------------------------------------------------------------ */
    /**
     * Assigns the rules to process.
     * @param rules an array of {@link Rule}. 
     */
    public void setRules(Rule[] rules)
    {
        _rules.setRules(rules);
    }

    /*------------------------------------------------------------ */
    /**
     * Assigns the rules to process.
     * @param rules a {@link RuleContainer} containing other rules to process
     */
    public void setRules(RuleContainer rules)
    {
        _rules = rules;
    }

    /* ------------------------------------------------------------ */
    /**
     * Add a Rule
     * @param rule The rule to add to the end of the rules array
     */
    public void addRule(Rule rule)
    {
        _rules.addRule(rule);
    }
   

    /* ------------------------------------------------------------ */
    /**
     * @return the rewriteRequestURI If true, this handler will rewrite the value
     * returned by {@link HttpServletRequest#getRequestURI()}.
     */
    public boolean isRewriteRequestURI()
    {
        return _rules.isRewriteRequestURI();
    }

    /* ------------------------------------------------------------ */
    /**
     * @param rewriteRequestURI true if this handler will rewrite the value
     * returned by {@link HttpServletRequest#getRequestURI()}.
     */
    public void setRewriteRequestURI(boolean rewriteRequestURI)
    {
        _rules.setRewriteRequestURI(rewriteRequestURI);
    }

    /* ------------------------------------------------------------ */
    /**
     * @return true if this handler will rewrite the value
     * returned by {@link HttpServletRequest#getPathInfo()}.
     */
    public boolean isRewritePathInfo()
    {
        return _rules.isRewritePathInfo();
    }

    /* ------------------------------------------------------------ */
    /**
     * @param rewritePathInfo true if this handler will rewrite the value
     * returned by {@link HttpServletRequest#getPathInfo()}.
     */
    public void setRewritePathInfo(boolean rewritePathInfo)
    {
        _rules.setRewritePathInfo(rewritePathInfo);
    }

    /* ------------------------------------------------------------ */
    /**
     * @return the originalPathAttribte. If non null, this string will be used
     * as the attribute name to store the original request path.
     */
    public String getOriginalPathAttribute()
    {
        return _rules.getOriginalPathAttribute();
    }

    /* ------------------------------------------------------------ */
    /**
     * @param originalPathAttribte If non null, this string will be used
     * as the attribute name to store the original request path.
     */
    public void setOriginalPathAttribute(String originalPathAttribute)
    {
        _rules.setOriginalPathAttribute(originalPathAttribute);
    }


    /* ------------------------------------------------------------ */
    /**
     * @deprecated 
     */
    public PathMap getRewrite()
    {
        return _rules.getRewrite();
    }

    /* ------------------------------------------------------------ */
    /**
     * @deprecated
     */
    public void setRewrite(PathMap rewrite)
    {
        _rules.setRewrite(rewrite);
    }

    /* ------------------------------------------------------------ */
    /**
     * @deprecated
     */
    public void addRewriteRule(String pattern, String prefix)
    {
        _rules.addRewriteRule(pattern,prefix);
    }
    
    /* ------------------------------------------------------------ */
    /* (non-Javadoc)
     * @see org.mortbay.jetty.handler.HandlerWrapper#handle(java.lang.String, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, int)
     */
    public void handle(String target, HttpServletRequest request, HttpServletResponse response, int dispatch) throws IOException, ServletException
    {
        if (isStarted())
        { 
            String returned = _rules.matchAndApply(target, request, response);
            target = (returned == null) ? target : returned;
            
            if (!_rules.isHandled())
            {
                super.handle(target, request, response, dispatch);
            }
        }
    }
    
}
