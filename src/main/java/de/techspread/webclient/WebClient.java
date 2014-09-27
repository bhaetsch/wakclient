package de.techspread.webclient;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

/** Simple client for HTTP and HTTPS. */
public class WebClient {

	/** HttpClient. */
	private DefaultHttpClient client;
	
	/** URL Prefix. */
	private String prefix = "https://www.wak-sh.de";

	/** 
	 * This constructor registers HTTP and HTTPS Schemes
	 * and initalizes the webclient.
	 */
	public WebClient() {
		SchemeRegistry registry = new SchemeRegistry();
		final int httpPort = 80;
		final int httpsPort = 443;
		registry.register(new Scheme("http", httpPort, PlainSocketFactory.getSocketFactory()));
		registry.register(new Scheme("https", httpsPort, SSLSocketFactory.getSocketFactory()));
		ThreadSafeClientConnManager connectionManager = new ThreadSafeClientConnManager(registry);
		client = new DefaultHttpClient(connectionManager);
	}

	/**
	 * Set a proxy.
	 * @param hostname Proxy hostname
	 * @param port Proxy Port
	 */
	public final void setProxy(final String hostname, final int port) {
		client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, new HttpHost(hostname, port));
	}

	/**
	 * Fetch a site via HttpGet.
	 * @param url Site URL
	 * @return Sourcecode of the site
	 * @throws IOException Error while fetching the site
	 */
	public final String get(final String url) throws IOException {
		return execute(new HttpGet(prefix + url));
	}

	/**
	 * Fetch a site via HttpPost.
	 * @param url Site URL
	 * @param params Parameters
	 * @return Sourcecode of the site
	 * @throws IOException Error while fetching the site
	 */
	public final String post(final String url, final List<NameValuePair> params) throws IOException {
		HttpPost httppost = new HttpPost(prefix + url);
		UrlEncodedFormEntity sendentity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
		httppost.setEntity(sendentity);
		return execute(httppost);
	}

	/**
	 * Executes an HttpGet or HttPost.
	 * @param postget HttpGet or HttpPost
	 * @return Sourcecode of the site
	 * @throws IOException Error while fetching
	 */
	private String execute(final HttpRequestBase postget) throws IOException {
		String result = null;
		HttpResponse response = null;
		response = client.execute(postget);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			result = EntityUtils.toString(entity);
			result = result.replace("amp;", "");
			result = result.replace("%2F", "/");
		}
		return result;
	}

	/**
	 * Get the entity of a site.
	 * @param url Site URL
	 * @return Site Entity
	 * @throws IOException Error while getting the entity
	 */
	public final HttpEntity getEntity(final String url) throws IOException {
		HttpGet httpget = new HttpGet(prefix + url);
		HttpResponse response = client.execute(httpget);
		return response.getEntity();
	}

	/**
	 * Shuts down the webclient.
	 */
	public final void shutdown() {
		client.getConnectionManager().shutdown();
	}
}