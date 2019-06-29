package io.github.henryssondaniel.teacup.service.visualization.mysql.v1._0;

import static org.assertj.core.api.Assertions.assertThat;

import com.sun.net.httpserver.HttpPrincipal;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.FilterRegistration;
import javax.servlet.MultipartConfigElement;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletRegistration.Dynamic;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.ServletSecurityElement;
import javax.servlet.SessionCookieConfig;
import javax.servlet.SessionTrackingMode;
import javax.servlet.WriteListener;
import javax.servlet.descriptor.JspConfigDescriptor;
import javax.servlet.descriptor.JspPropertyGroupDescriptor;
import javax.servlet.descriptor.TaglibDescriptor;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import javax.servlet.http.HttpUpgradeHandler;
import javax.servlet.http.Part;
import javax.servlet.http.WebConnection;
import org.junit.jupiter.api.Test;

class AccountResourceTest {
  @Test
  void changePassword() {
    try (var response = new AccountResource().changePassword("{}", new TestHttpServletRequest())) {
      assertThat(response).isNotNull();
    }
  }

  @Test
  void login() {
    try (var response = new AccountResource().logIn("{}", new TestHttpServletRequest())) {
      assertThat(response).isNotNull();
    }
  }

  @Test
  void recover() {
    try (var response = new AccountResource().recover("{}", new TestHttpServletRequest())) {
      assertThat(response).isNotNull();
    }
  }

  @Test
  void signUp() {
    try (var response = new AccountResource().signUp("{}", new TestHttpServletRequest())) {
      assertThat(response).isNotNull();
    }
  }

  @Test
  void verify() {
    try (var response = new AccountResource().verify("{}", new TestHttpServletRequest())) {
      assertThat(response).isNotNull();
    }
  }

  private static final class TestAsyncContext implements AsyncContext {
    private static final Logger LOGGER = Logger.getLogger(TestAsyncContext.class.getName());

    @Override
    public void addListener(AsyncListener listener) {
      LOGGER.log(Level.FINE, "Add listener");
    }

    @Override
    public void addListener(
        AsyncListener listener, ServletRequest servletRequest, ServletResponse servletResponse) {
      LOGGER.log(Level.FINE, "Add listener");
    }

    @Override
    public void complete() {
      LOGGER.log(Level.FINE, "Complete");
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends AsyncListener> T createListener(Class<T> clazz) {
      LOGGER.log(Level.FINE, "Create listener");
      return (T) new TestAsyncListener();
    }

    @Override
    public void dispatch() {
      LOGGER.log(Level.FINE, "Dispatch");
    }

    @Override
    public void dispatch(String path) {
      LOGGER.log(Level.FINE, "Dispatch");
    }

    @Override
    public void dispatch(ServletContext context, String path) {
      LOGGER.log(Level.FINE, "Dispatch");
    }

    @Override
    public ServletRequest getRequest() {
      LOGGER.log(Level.FINE, "Get request");
      return new TestHttpServletRequest();
    }

    @Override
    public ServletResponse getResponse() {
      LOGGER.log(Level.FINE, "Get response");
      return new TestServletResponse();
    }

    @Override
    public long getTimeout() {
      LOGGER.log(Level.FINE, "Get timeout");
      return 0L;
    }

    @Override
    public boolean hasOriginalRequestAndResponse() {
      LOGGER.log(Level.FINE, "Has original request and response");
      return false;
    }

    @Override
    public void setTimeout(long timeout) {
      LOGGER.log(Level.FINE, "Set timeout");
    }

    @Override
    public void start(Runnable run) {
      LOGGER.log(Level.FINE, "Start");
    }
  }

  private static final class TestAsyncListener implements AsyncListener {
    private static final Logger LOGGER = Logger.getLogger(TestAsyncListener.class.getName());

    @Override
    public void onComplete(AsyncEvent event) {
      LOGGER.log(Level.FINE, "On complete");
    }

    @Override
    public void onError(AsyncEvent event) {
      LOGGER.log(Level.FINE, "On error");
    }

    @Override
    public void onStartAsync(AsyncEvent event) {
      LOGGER.log(Level.FINE, "On start async");
    }

    @Override
    public void onTimeout(AsyncEvent event) {
      LOGGER.log(Level.FINE, "On timeout");
    }
  }

  private static final class TestDynamic implements FilterRegistration.Dynamic {
    private static final Logger LOGGER = Logger.getLogger(TestDynamic.class.getName());

    @Override
    public void addMappingForServletNames(
        EnumSet<DispatcherType> dispatcherTypes, boolean isMatchAfter, String... servletNames) {
      LOGGER.log(Level.FINE, "Add mapping for servlet names");
    }

    @Override
    public void addMappingForUrlPatterns(
        EnumSet<DispatcherType> dispatcherTypes, boolean isMatchAfter, String... urlPatterns) {
      LOGGER.log(Level.FINE, "Add mapping for URL patterns");
    }

    @Override
    public String getClassName() {
      LOGGER.log(Level.FINE, "Get class name");
      return "";
    }

    @Override
    public String getInitParameter(String name) {
      LOGGER.log(Level.FINE, "Get init parameter");
      return "";
    }

    @Override
    public Map<String, String> getInitParameters() {
      LOGGER.log(Level.FINE, "Get init parameters");
      return Collections.emptyMap();
    }

    @Override
    public String getName() {
      LOGGER.log(Level.FINE, "Get name");
      return "";
    }

    @Override
    public Collection<String> getServletNameMappings() {
      LOGGER.log(Level.FINE, "Get servlet name mappings");
      return Collections.emptyList();
    }

    @Override
    public Collection<String> getUrlPatternMappings() {
      LOGGER.log(Level.FINE, "get URL pattern mappings");
      return Collections.emptyList();
    }

    @Override
    public void setAsyncSupported(boolean isAsyncSupported) {
      LOGGER.log(Level.FINE, "Set async supported");
    }

    @Override
    public boolean setInitParameter(String name, String value) {
      LOGGER.log(Level.FINE, "Set init parameter");
      return false;
    }

    @Override
    public Set<String> setInitParameters(Map<String, String> initParameters) {
      LOGGER.log(Level.FINE, "Set init parameters");
      return Collections.emptySet();
    }
  }

  private static final class TestEventListener implements EventListener {}

  private static final class TestFilter implements Filter {
    private static final Logger LOGGER = Logger.getLogger(TestFilter.class.getName());

    @Override
    public void destroy() {
      LOGGER.log(Level.FINE, "Destroy");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
      LOGGER.log(Level.FINE, "Do filter");
    }

    @Override
    public void init(FilterConfig filterConfig) {
      LOGGER.log(Level.FINE, "Init");
    }
  }

  private static final class TestHttpServlet extends HttpServlet {
    private static final long serialVersionUID = -3018315882460360245L;
  }

  private static final class TestHttpServletRequest implements HttpServletRequest {
    private static final Logger LOGGER = Logger.getLogger(TestHttpServletRequest.class.getName());

    @Override
    public boolean authenticate(HttpServletResponse response) {
      LOGGER.log(Level.FINE, "Authenticate");
      return false;
    }

    @Override
    public String changeSessionId() {
      LOGGER.log(Level.FINE, "Change session ID");
      return "";
    }

    @Override
    public AsyncContext getAsyncContext() {
      LOGGER.log(Level.FINE, "Get async context");
      return new TestAsyncContext();
    }

    @Override
    public Object getAttribute(String name) {
      LOGGER.log(Level.FINE, "Get attribute");
      return name;
    }

    @Override
    public Enumeration<String> getAttributeNames() {
      LOGGER.log(Level.FINE, "Get attribute names");
      return Collections.emptyEnumeration();
    }

    @Override
    public String getAuthType() {
      LOGGER.log(Level.FINE, "Get auth type");
      return "";
    }

    @Override
    public String getCharacterEncoding() {
      LOGGER.log(Level.FINE, "Get character encoding");
      return "";
    }

    @Override
    public int getContentLength() {
      LOGGER.log(Level.FINE, "Get content length");
      return 0;
    }

    @Override
    public long getContentLengthLong() {
      LOGGER.log(Level.FINE, "Get content length long");
      return 0L;
    }

    @Override
    public String getContentType() {
      LOGGER.log(Level.FINE, "Get content type");
      return "";
    }

    @Override
    public String getContextPath() {
      LOGGER.log(Level.FINE, "Get context path");
      return "";
    }

    @Override
    public Cookie[] getCookies() {
      LOGGER.log(Level.FINE, "Get cookies");
      return new Cookie[1];
    }

    @Override
    public long getDateHeader(String name) {
      LOGGER.log(Level.FINE, "Get date header");
      return 0L;
    }

    @Override
    public DispatcherType getDispatcherType() {
      LOGGER.log(Level.FINE, "Get dispatcher type");
      return DispatcherType.ASYNC;
    }

    @Override
    public String getHeader(String name) {
      LOGGER.log(Level.FINE, "Get header");
      return "";
    }

    @Override
    public Enumeration<String> getHeaderNames() {
      LOGGER.log(Level.FINE, "Get header names");
      return Collections.emptyEnumeration();
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
      LOGGER.log(Level.FINE, "Get headers");
      return Collections.emptyEnumeration();
    }

    @Override
    public ServletInputStream getInputStream() {
      LOGGER.log(Level.FINE, "Get input stream");
      return (ServletInputStream) InputStream.nullInputStream();
    }

    @Override
    public int getIntHeader(String name) {
      LOGGER.log(Level.FINE, "Get int header");
      return 0;
    }

    @Override
    public String getLocalAddr() {
      LOGGER.log(Level.FINE, "Get local addr");
      return "";
    }

    @Override
    public String getLocalName() {
      LOGGER.log(Level.FINE, "Get local name");
      return "";
    }

    @Override
    public int getLocalPort() {
      LOGGER.log(Level.FINE, "Get local port");
      return 0;
    }

    @Override
    public Locale getLocale() {
      LOGGER.log(Level.FINE, "Get locale");
      return Locale.getDefault();
    }

    @Override
    public Enumeration<Locale> getLocales() {
      LOGGER.log(Level.FINE, "Get locales");
      return Collections.emptyEnumeration();
    }

    @Override
    public String getMethod() {
      LOGGER.log(Level.FINE, "Get method");
      return "";
    }

    @Override
    public String getParameter(String name) {
      LOGGER.log(Level.FINE, "Get parameter");
      return "";
    }

    @Override
    public Map<String, String[]> getParameterMap() {
      LOGGER.log(Level.FINE, "Get parameter map");
      return Collections.emptyMap();
    }

    @Override
    public Enumeration<String> getParameterNames() {
      LOGGER.log(Level.FINE, "Get parameter names");
      return Collections.emptyEnumeration();
    }

    @Override
    public String[] getParameterValues(String name) {
      LOGGER.log(Level.FINE, "Get parameter values");
      return new String[1];
    }

    @Override
    public Part getPart(String name) {
      LOGGER.log(Level.FINE, "Get part");
      return new TestPart();
    }

    @Override
    public Collection<Part> getParts() {
      LOGGER.log(Level.FINE, "Get parts");
      return Collections.emptyList();
    }

    @Override
    public String getPathInfo() {
      LOGGER.log(Level.FINE, "Get path info");
      return "";
    }

    @Override
    public String getPathTranslated() {
      LOGGER.log(Level.FINE, "Get path translated");
      return "";
    }

    @Override
    public String getProtocol() {
      LOGGER.log(Level.FINE, "Get protocol");
      return "";
    }

    @Override
    public String getQueryString() {
      LOGGER.log(Level.FINE, "Get query string");
      return "";
    }

    @Override
    public BufferedReader getReader() {
      LOGGER.log(Level.FINE, "Get header");
      return (BufferedReader) Reader.nullReader();
    }

    @Override
    public String getRealPath(String path) {
      LOGGER.log(Level.FINE, "Get real path");
      return "";
    }

    @Override
    public String getRemoteAddr() {
      LOGGER.log(Level.FINE, "Get remote addr");
      return "";
    }

    @Override
    public String getRemoteHost() {
      LOGGER.log(Level.FINE, "Get remote host");
      return "";
    }

    @Override
    public int getRemotePort() {
      LOGGER.log(Level.FINE, "Get remote port");
      return 0;
    }

    @Override
    public String getRemoteUser() {
      LOGGER.log(Level.FINE, "Get remote user");
      return "";
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String path) {
      LOGGER.log(Level.FINE, "Get request dispatcher");
      return new TestRequestDispatcher();
    }

    @Override
    public String getRequestURI() {
      LOGGER.log(Level.FINE, "Get request URI");
      return "";
    }

    @Override
    public StringBuffer getRequestURL() {
      LOGGER.log(Level.FINE, "Get request URL");
      return new StringBuffer(1);
    }

    @Override
    public String getRequestedSessionId() {
      LOGGER.log(Level.FINE, "Get requested session ID");
      return "";
    }

    @Override
    public String getScheme() {
      LOGGER.log(Level.FINE, "Get scheme");
      return "";
    }

    @Override
    public String getServerName() {
      LOGGER.log(Level.FINE, "Get server name");
      return "";
    }

    @Override
    public int getServerPort() {
      LOGGER.log(Level.FINE, "Get server port");
      return 0;
    }

    @Override
    public ServletContext getServletContext() {
      LOGGER.log(Level.FINE, "Get servlet context");
      return new TestServletContext();
    }

    @Override
    public String getServletPath() {
      LOGGER.log(Level.FINE, "Get servlet path");
      return "";
    }

    @Override
    public HttpSession getSession(boolean create) {
      LOGGER.log(Level.FINE, "Get session");
      return new TestHttpSession();
    }

    @Override
    public HttpSession getSession() {
      LOGGER.log(Level.FINE, "Get session");
      return new TestHttpSession();
    }

    @Override
    public Principal getUserPrincipal() {
      LOGGER.log(Level.FINE, "Get user principal");
      return new HttpPrincipal("", "");
    }

    @Override
    public boolean isAsyncStarted() {
      LOGGER.log(Level.FINE, "Is async started");
      return false;
    }

    @Override
    public boolean isAsyncSupported() {
      LOGGER.log(Level.FINE, "Is async supported");
      return false;
    }

    @Override
    public boolean isRequestedSessionIdFromCookie() {
      LOGGER.log(Level.FINE, "Is requested session ID from cookie");
      return false;
    }

    @Override
    public boolean isRequestedSessionIdFromURL() {
      LOGGER.log(Level.FINE, "Is requested session ID from URL");
      return false;
    }

    @Override
    public boolean isRequestedSessionIdFromUrl() {
      LOGGER.log(Level.FINE, "Is requested session ID from URL");
      return false;
    }

    @Override
    public boolean isRequestedSessionIdValid() {
      LOGGER.log(Level.FINE, "Is requested session ID valid");
      return false;
    }

    @Override
    public boolean isSecure() {
      LOGGER.log(Level.FINE, "Is secure");
      return false;
    }

    @Override
    public boolean isUserInRole(String role) {
      LOGGER.log(Level.FINE, "Is user in role");
      return false;
    }

    @Override
    public void login(String username, String password) {
      LOGGER.log(Level.FINE, "Login");
    }

    @Override
    public void logout() {
      LOGGER.log(Level.FINE, "Logout");
    }

    @Override
    public void removeAttribute(String name) {
      LOGGER.log(Level.FINE, "Remove attribute");
    }

    @Override
    public void setAttribute(String name, Object o) {
      LOGGER.log(Level.FINE, "Set attribute");
    }

    @Override
    public void setCharacterEncoding(String env) {
      LOGGER.log(Level.FINE, "Set character encoding");
    }

    @Override
    public AsyncContext startAsync() {
      LOGGER.log(Level.FINE, "Start async");
      return new TestAsyncContext();
    }

    @Override
    public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) {
      LOGGER.log(Level.FINE, "Start async");
      return new TestAsyncContext();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends HttpUpgradeHandler> T upgrade(Class<T> handlerClass) {
      LOGGER.log(Level.FINE, "Upgrade");
      return (T) new TestHttpUpgradeHandler();
    }
  }

  private static final class TestHttpSession implements HttpSession {
    private static final Logger LOGGER = Logger.getLogger(TestHttpSession.class.getName());

    @Override
    public Object getAttribute(String name) {
      LOGGER.log(Level.FINE, "Get attribute");
      return "";
    }

    @Override
    public Enumeration<String> getAttributeNames() {
      LOGGER.log(Level.FINE, "Get attribute names");
      return Collections.emptyEnumeration();
    }

    @Override
    public long getCreationTime() {
      LOGGER.log(Level.FINE, "Get creation time");
      return 0L;
    }

    @Override
    public String getId() {
      LOGGER.log(Level.FINE, "Get ID");
      return "";
    }

    @Override
    public long getLastAccessedTime() {
      LOGGER.log(Level.FINE, "Get last accessed time");
      return 0L;
    }

    @Override
    public int getMaxInactiveInterval() {
      LOGGER.log(Level.FINE, "Get max inactive interval");
      return 0;
    }

    @Override
    public ServletContext getServletContext() {
      LOGGER.log(Level.FINE, "Get servlet context");
      return new TestServletContext();
    }

    @Override
    public HttpSessionContext getSessionContext() {
      LOGGER.log(Level.FINE, "Get session context");
      return new TestHttpSessionContext();
    }

    @Override
    public Object getValue(String name) {
      LOGGER.log(Level.FINE, "Get value");
      return "";
    }

    @Override
    public String[] getValueNames() {
      LOGGER.log(Level.FINE, "Get value names");
      return new String[1];
    }

    @Override
    public void invalidate() {
      LOGGER.log(Level.FINE, "Invalidate");
    }

    @Override
    public boolean isNew() {
      LOGGER.log(Level.FINE, "Is new");
      return false;
    }

    @Override
    public void putValue(String name, Object value) {
      LOGGER.log(Level.FINE, "Put value");
    }

    @Override
    public void removeAttribute(String name) {
      LOGGER.log(Level.FINE, "Remove attribute");
    }

    @Override
    public void removeValue(String name) {
      LOGGER.log(Level.FINE, "Remove value");
    }

    @Override
    public void setAttribute(String name, Object value) {
      LOGGER.log(Level.FINE, "Get attribute");
    }

    @Override
    public void setMaxInactiveInterval(int interval) {
      LOGGER.log(Level.FINE, "Set max inactive interval");
    }
  }

  private static final class TestHttpSessionContext implements HttpSessionContext {
    private static final Logger LOGGER = Logger.getLogger(TestHttpSessionContext.class.getName());

    @Override
    public Enumeration<String> getIds() {
      LOGGER.log(Level.FINE, "Get IDs");
      return Collections.emptyEnumeration();
    }

    @Override
    public HttpSession getSession(String sessionId) {
      LOGGER.log(Level.FINE, "Get session");
      return new TestHttpSession();
    }
  }

  private static final class TestHttpUpgradeHandler implements HttpUpgradeHandler {
    private static final Logger LOGGER = Logger.getLogger(TestHttpUpgradeHandler.class.getName());

    @Override
    public void destroy() {
      LOGGER.log(Level.FINE, "Destroy");
    }

    @Override
    public void init(WebConnection wc) {
      LOGGER.log(Level.FINE, "Init");
    }
  }

  private static final class TestJspConfigDescriptor implements JspConfigDescriptor {
    private static final Logger LOGGER = Logger.getLogger(TestJspConfigDescriptor.class.getName());

    @Override
    public Collection<JspPropertyGroupDescriptor> getJspPropertyGroups() {
      LOGGER.log(Level.FINE, "Get JSP property groups");
      return Collections.emptyList();
    }

    @Override
    public Collection<TaglibDescriptor> getTaglibs() {
      LOGGER.log(Level.FINE, "Get tag libs");
      return Collections.emptyList();
    }
  }

  private static final class TestPart implements Part {
    private static final Logger LOGGER = Logger.getLogger(TestPart.class.getName());

    @Override
    public void delete() {
      LOGGER.log(Level.FINE, "Delete");
    }

    @Override
    public String getContentType() {
      LOGGER.log(Level.FINE, "Get content type");
      return "";
    }

    @Override
    public String getHeader(String name) {
      LOGGER.log(Level.FINE, "Get header");
      return "";
    }

    @Override
    public Collection<String> getHeaderNames() {
      LOGGER.log(Level.FINE, "Get header names");
      return Collections.emptyList();
    }

    @Override
    public Collection<String> getHeaders(String name) {
      LOGGER.log(Level.FINE, "Get headers");
      return Collections.emptyList();
    }

    @Override
    public InputStream getInputStream() {
      LOGGER.log(Level.FINE, "Get input stream");
      return InputStream.nullInputStream();
    }

    @Override
    public String getName() {
      LOGGER.log(Level.FINE, "Get name");
      return "";
    }

    @Override
    public long getSize() {
      LOGGER.log(Level.FINE, "Get size");
      return 0L;
    }

    @Override
    public String getSubmittedFileName() {
      LOGGER.log(Level.FINE, "Get submitted file name");
      return "";
    }

    @Override
    public void write(String fileName) {
      LOGGER.log(Level.FINE, "Write");
    }
  }

  private static final class TestRequestDispatcher implements RequestDispatcher {
    private static final Logger LOGGER = Logger.getLogger(TestRequestDispatcher.class.getName());

    @Override
    public void forward(ServletRequest request, ServletResponse response) {
      LOGGER.log(Level.FINE, "Forward");
    }

    @Override
    public void include(ServletRequest request, ServletResponse response) {
      LOGGER.log(Level.FINE, "Include");
    }
  }

  private static final class TestServletContext implements ServletContext {
    private static final Logger LOGGER = Logger.getLogger(TestServletContext.class.getName());

    @Override
    public FilterRegistration.Dynamic addFilter(String filterName, String className) {
      LOGGER.log(Level.FINE, "Add filter");
      return new TestDynamic();
    }

    @Override
    public FilterRegistration.Dynamic addFilter(String filterName, Filter filter) {
      LOGGER.log(Level.FINE, "Add filter");
      return new TestDynamic();
    }

    @Override
    public FilterRegistration.Dynamic addFilter(
        String filterName, Class<? extends Filter> filterClass) {
      LOGGER.log(Level.FINE, "Add filter");
      return new TestDynamic();
    }

    @Override
    public void addListener(String className) {
      LOGGER.log(Level.FINE, "Add listener");
    }

    @Override
    public <T extends EventListener> void addListener(T t) {
      LOGGER.log(Level.FINE, "Add listener");
    }

    @Override
    public void addListener(Class<? extends EventListener> listenerClass) {
      LOGGER.log(Level.FINE, "Add listener");
    }

    @Override
    public Dynamic addServlet(String servletName, String className) {
      LOGGER.log(Level.FINE, "Add servlet");
      return new TestServletDynamic();
    }

    @Override
    public Dynamic addServlet(String servletName, Servlet servlet) {
      LOGGER.log(Level.FINE, "Add servlet");
      return new TestServletDynamic();
    }

    @Override
    public Dynamic addServlet(String servletName, Class<? extends Servlet> servletClass) {
      LOGGER.log(Level.FINE, "Add servlet");
      return new TestServletDynamic();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Filter> T createFilter(Class<T> clazz) {
      LOGGER.log(Level.FINE, "Create filter");
      return (T) new TestFilter();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends EventListener> T createListener(Class<T> clazz) {
      LOGGER.log(Level.FINE, "Create listener");
      return (T) new TestEventListener();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Servlet> T createServlet(Class<T> clazz) {
      LOGGER.log(Level.FINE, "Create servlet");
      return (T) new TestHttpServlet();
    }

    @Override
    public void declareRoles(String... roleNames) {
      LOGGER.log(Level.FINE, "Declare roles");
    }

    @Override
    public Object getAttribute(String name) {
      LOGGER.log(Level.FINE, "Get attribute");
      return "";
    }

    @Override
    public Enumeration<String> getAttributeNames() {
      LOGGER.log(Level.FINE, "Get attribute names");
      return Collections.emptyEnumeration();
    }

    @Override
    public ClassLoader getClassLoader() {
      LOGGER.log(Level.FINE, "Get class loader");
      return ClassLoader.getSystemClassLoader();
    }

    @Override
    public ServletContext getContext(String uripath) {
      LOGGER.log(Level.FINE, "Get context");
      return new TestServletContext();
    }

    @Override
    public String getContextPath() {
      LOGGER.log(Level.FINE, "Get context path");
      return "";
    }

    @Override
    public Set<SessionTrackingMode> getDefaultSessionTrackingModes() {
      LOGGER.log(Level.FINE, "Get default session tracking modes");
      return Collections.emptySet();
    }

    @Override
    public int getEffectiveMajorVersion() {
      LOGGER.log(Level.FINE, "Get effective major version");
      return 0;
    }

    @Override
    public int getEffectiveMinorVersion() {
      LOGGER.log(Level.FINE, "Get effective minor version");
      return 0;
    }

    @Override
    public Set<SessionTrackingMode> getEffectiveSessionTrackingModes() {
      LOGGER.log(Level.FINE, "Get effective session tracking modes");
      return Collections.emptySet();
    }

    @Override
    public FilterRegistration getFilterRegistration(String filterName) {
      LOGGER.log(Level.FINE, "Get filter registration");
      return new TestDynamic();
    }

    @Override
    public Map<String, ? extends FilterRegistration> getFilterRegistrations() {
      LOGGER.log(Level.FINE, "Get filter registrations");
      return Collections.emptyMap();
    }

    @Override
    public String getInitParameter(String name) {
      LOGGER.log(Level.FINE, "Get init parameter");
      return "";
    }

    @Override
    public Enumeration<String> getInitParameterNames() {
      LOGGER.log(Level.FINE, "Get init parameter names");
      return Collections.emptyEnumeration();
    }

    @Override
    public JspConfigDescriptor getJspConfigDescriptor() {
      LOGGER.log(Level.FINE, "Get JSP config descriptor");
      return new TestJspConfigDescriptor();
    }

    @Override
    public int getMajorVersion() {
      LOGGER.log(Level.FINE, "Get major version");
      return 0;
    }

    @Override
    public String getMimeType(String file) {
      LOGGER.log(Level.FINE, "Get mime type");
      return "";
    }

    @Override
    public int getMinorVersion() {
      LOGGER.log(Level.FINE, "Get minor version");
      return 0;
    }

    @Override
    public RequestDispatcher getNamedDispatcher(String name) {
      LOGGER.log(Level.FINE, "Get named dispatcher");
      return new TestRequestDispatcher();
    }

    @Override
    public String getRealPath(String path) {
      LOGGER.log(Level.FINE, "Get real path");
      return "";
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String path) {
      LOGGER.log(Level.FINE, "Get request dispatcher");
      return new TestRequestDispatcher();
    }

    @Override
    public URL getResource(String path) throws MalformedURLException {
      LOGGER.log(Level.FINE, "Get resource");
      return new URL("");
    }

    @Override
    public InputStream getResourceAsStream(String path) {
      LOGGER.log(Level.FINE, "Get resource as stream");
      return InputStream.nullInputStream();
    }

    @Override
    public Set<String> getResourcePaths(String path) {
      LOGGER.log(Level.FINE, "Get resource paths");
      return Collections.emptySet();
    }

    @Override
    public String getServerInfo() {
      LOGGER.log(Level.FINE, "Get server info");
      return "";
    }

    @Override
    public Servlet getServlet(String name) {
      LOGGER.log(Level.FINE, "Get servlet");
      return new TestHttpServlet();
    }

    @Override
    public String getServletContextName() {
      LOGGER.log(Level.FINE, "Get servlet context name");
      return "";
    }

    @Override
    public Enumeration<String> getServletNames() {
      LOGGER.log(Level.FINE, "Get servlet names");
      return Collections.emptyEnumeration();
    }

    @Override
    public ServletRegistration getServletRegistration(String servletName) {
      LOGGER.log(Level.FINE, "Get servlet registration");
      return new TestServletDynamic();
    }

    @Override
    public Map<String, ? extends ServletRegistration> getServletRegistrations() {
      LOGGER.log(Level.FINE, "Get servlet registrations");
      return Collections.emptyMap();
    }

    @Override
    public Enumeration<Servlet> getServlets() {
      LOGGER.log(Level.FINE, "Get servlets");
      return Collections.emptyEnumeration();
    }

    @Override
    public SessionCookieConfig getSessionCookieConfig() {
      LOGGER.log(Level.FINE, "Get session cookie config");
      return new TestSessionCookieConfig();
    }

    @Override
    public String getVirtualServerName() {
      LOGGER.log(Level.FINE, "Get virtual server name");
      return "";
    }

    @Override
    public void log(String msg) {
      LOGGER.log(Level.FINE, "Log");
    }

    @Override
    public void log(Exception exception, String msg) {
      LOGGER.log(Level.FINE, "Log");
    }

    @Override
    public void log(String message, Throwable throwable) {
      LOGGER.log(Level.FINE, "Log");
    }

    @Override
    public void removeAttribute(String name) {
      LOGGER.log(Level.FINE, "Remove attribute");
    }

    @Override
    public void setAttribute(String name, Object object) {
      LOGGER.log(Level.FINE, "Set attribute");
    }

    @Override
    public boolean setInitParameter(String name, String value) {
      LOGGER.log(Level.FINE, "Set init parameter");
      return false;
    }

    @Override
    public void setSessionTrackingModes(Set<SessionTrackingMode> sessionTrackingModes) {
      LOGGER.log(Level.FINE, "Set session tracking modes");
    }
  }

  private static final class TestServletDynamic implements Dynamic {
    private static final Logger LOGGER = Logger.getLogger(TestServletDynamic.class.getName());

    @Override
    public Set<String> addMapping(String... urlPatterns) {
      LOGGER.log(Level.FINE, "Add mapping");
      return Collections.emptySet();
    }

    @Override
    public String getClassName() {
      LOGGER.log(Level.FINE, "Get class name");
      return "";
    }

    @Override
    public String getInitParameter(String name) {
      LOGGER.log(Level.FINE, "Get init parameter");
      return "";
    }

    @Override
    public Map<String, String> getInitParameters() {
      LOGGER.log(Level.FINE, "Get init parameters");
      return Collections.emptyMap();
    }

    @Override
    public Collection<String> getMappings() {
      LOGGER.log(Level.FINE, "Get mappings");
      return Collections.emptyList();
    }

    @Override
    public String getName() {
      LOGGER.log(Level.FINE, "Get name");
      return "";
    }

    @Override
    public String getRunAsRole() {
      LOGGER.log(Level.FINE, "Get run as role");
      return "";
    }

    @Override
    public void setAsyncSupported(boolean isAsyncSupported) {
      LOGGER.log(Level.FINE, "Set async supported");
    }

    @Override
    public boolean setInitParameter(String name, String value) {
      LOGGER.log(Level.FINE, "Set init parameter");
      return false;
    }

    @Override
    public Set<String> setInitParameters(Map<String, String> initParameters) {
      LOGGER.log(Level.FINE, "Set init parameters");
      return Collections.emptySet();
    }

    @Override
    public void setLoadOnStartup(int loadOnStartup) {
      LOGGER.log(Level.FINE, "Set load on startup");
    }

    @Override
    public void setMultipartConfig(MultipartConfigElement multipartConfig) {
      LOGGER.log(Level.FINE, "Set multipart config");
    }

    @Override
    public void setRunAsRole(String roleName) {
      LOGGER.log(Level.FINE, "Set run as role");
    }

    @Override
    public Set<String> setServletSecurity(ServletSecurityElement constraint) {
      LOGGER.log(Level.FINE, "Set servlet security");
      return Collections.emptySet();
    }
  }

  private static final class TestServletOutputStream extends ServletOutputStream {
    private static final Logger LOGGER = Logger.getLogger(TestServletOutputStream.class.getName());

    @Override
    public boolean isReady() {
      LOGGER.log(Level.FINE, "Is ready");
      return false;
    }

    @Override
    public void setWriteListener(WriteListener writeListener) {
      LOGGER.log(Level.FINE, "Set write listener");
    }

    @Override
    public void write(int bytes) {
      LOGGER.log(Level.FINE, "Write");
    }
  }

  private static final class TestServletResponse implements ServletResponse {
    private static final Logger LOGGER = Logger.getLogger(TestServletResponse.class.getName());

    @Override
    public void flushBuffer() {
      LOGGER.log(Level.FINE, "Flash buffer");
    }

    @Override
    public int getBufferSize() {
      LOGGER.log(Level.FINE, "Get buffer size");
      return 0;
    }

    @Override
    public String getCharacterEncoding() {
      LOGGER.log(Level.FINE, "Get character encoding");
      return "";
    }

    @Override
    public String getContentType() {
      LOGGER.log(Level.FINE, "Get content type");
      return "";
    }

    @Override
    public Locale getLocale() {
      LOGGER.log(Level.FINE, "Get locale");
      return Locale.getDefault();
    }

    @Override
    public ServletOutputStream getOutputStream() {
      LOGGER.log(Level.FINE, "Get output stream");
      return new TestServletOutputStream();
    }

    @Override
    public PrintWriter getWriter() throws IOException {
      LOGGER.log(Level.FINE, "Get writer");
      return new PrintWriter("", Charset.defaultCharset());
    }

    @Override
    public boolean isCommitted() {
      LOGGER.log(Level.FINE, "Is committed");
      return false;
    }

    @Override
    public void reset() {
      LOGGER.log(Level.FINE, "Reset");
    }

    @Override
    public void resetBuffer() {
      LOGGER.log(Level.FINE, "Reset buffer");
    }

    @Override
    public void setBufferSize(int size) {
      LOGGER.log(Level.FINE, "Set buffer size");
    }

    @Override
    public void setCharacterEncoding(String charset) {
      LOGGER.log(Level.FINE, "Set character encoding");
    }

    @Override
    public void setContentLength(int len) {
      LOGGER.log(Level.FINE, "Set content length");
    }

    @Override
    public void setContentLengthLong(long len) {
      LOGGER.log(Level.FINE, "Set content length long");
    }

    @Override
    public void setContentType(String type) {
      LOGGER.log(Level.FINE, "Set content type");
    }

    @Override
    public void setLocale(Locale loc) {
      LOGGER.log(Level.FINE, "Set locale");
    }
  }

  private static final class TestSessionCookieConfig implements SessionCookieConfig {
    private static final Logger LOGGER = Logger.getLogger(TestSessionCookieConfig.class.getName());

    @Override
    public String getComment() {
      LOGGER.log(Level.FINE, "Get comment");
      return "";
    }

    @Override
    public String getDomain() {
      LOGGER.log(Level.FINE, "Get domain");
      return "";
    }

    @Override
    public int getMaxAge() {
      LOGGER.log(Level.FINE, "Get max age");
      return 0;
    }

    @Override
    public String getName() {
      LOGGER.log(Level.FINE, "Get name");
      return "";
    }

    @Override
    public String getPath() {
      LOGGER.log(Level.FINE, "Get path");
      return "";
    }

    @Override
    public boolean isHttpOnly() {
      LOGGER.log(Level.FINE, "Is HTTP only");
      return false;
    }

    @Override
    public boolean isSecure() {
      LOGGER.log(Level.FINE, "Is secure");
      return false;
    }

    @Override
    public void setComment(String comment) {
      LOGGER.log(Level.FINE, "Set comment");
    }

    @Override
    public void setDomain(String domain) {
      LOGGER.log(Level.FINE, "Set domain");
    }

    @Override
    public void setHttpOnly(boolean httpOnly) {
      LOGGER.log(Level.FINE, "Set HTTP only");
    }

    @Override
    public void setMaxAge(int maxAge) {
      LOGGER.log(Level.FINE, "Set max age");
    }

    @Override
    public void setName(String name) {
      LOGGER.log(Level.FINE, "Set name");
    }

    @Override
    public void setPath(String path) {
      LOGGER.log(Level.FINE, "Set path");
    }

    @Override
    public void setSecure(boolean secure) {
      LOGGER.log(Level.FINE, "Set secure");
    }
  }
}
