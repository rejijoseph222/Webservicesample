package com.nexercise.client.android.helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.content.Context;

import com.nexercise.client.android.constants.APIConstants;
import com.nexercise.client.android.constants.ApplicationConstants;
import com.nexercise.client.android.constants.HttpHeaderConstants;
import com.nexercise.client.android.constants.UserPreferencesConstants;
import com.nexercise.client.android.utils.Funcs;
import com.nexercise.client.android.utils.Logger;

public enum WebServiceHelper {
	INSTANCE;

	 Context _context;

	WebServiceHelper() {

	}


	public void setContext(Context context) {
		 _context = context;
		Logger.context = context;
	}


	public String getFromWebService(String query) throws ClientProtocolException, IOException, Exception {

		HttpClient httpclient = returnDefaultHTTPClientWithTimeOut();

		HttpGet request = new HttpGet(URI.create(query));

		// TODO : Have to change the following base 64 encoded string
		// to get user and password from APIConstants class

		request.setHeader(HttpHeaderConstants.AUTHORIZATION, "Basic " + APIConstants.API_HTTP_AUTH_BASE64);
		request.setHeader(HttpHeaderConstants.X_OS, Funcs.getOSVersion());
		request.setHeader(HttpHeaderConstants.X_DEVICE_MODEL, Funcs.getDeviceName());
		request.setHeader(HttpHeaderConstants.X_LOCALE, Funcs.getLocale());
		request.setHeader(HttpHeaderConstants.X_NEXERCISE_CLIENT_VERSION, ApplicationConstants.APP_VERSION);
		request.setHeader(HttpHeaderConstants.USER_AGENT, Funcs.getUserAgent());
		request.setHeader(HttpHeaderConstants.USER_ID, getUserID());
//		HttpResponse response= httpclient.execute(request);
//		String result= Funcs.getStringFromInputStream(response.getEntity().getContent());
//		Logger.writeAPIRequestLog("GET  " + query, "");
		String result = "";
		try{
			result = httpclient.execute(request, new BasicResponseHandler());
		}catch (OutOfMemoryError E) {
		}
//		if ( result != null ) {

			// if (result.length() > 60) {
			// Logger.writeAPIResponseLog(result.substring(0, 20));
			// Logger.writeAPIResponseLog(result.substring(result.length()-20, result.length()-1));
			// } else {
//			Logger.writeAPIResponseLog(result);

			// }

//		}

		httpclient.getConnectionManager().shutdown();
		return result;
	}
	public String getFromWebService(String query, boolean useHttpResponse) throws ClientProtocolException, IOException, Exception {

		HttpClient httpclient = returnDefaultHTTPClientWithTimeOut();

		HttpGet request = new HttpGet(URI.create(query));

		// TODO : Have to change the following base 64 encoded string
		// to get user and password from APIConstants class

		request.setHeader(HttpHeaderConstants.AUTHORIZATION, "Basic " + APIConstants.API_HTTP_AUTH_BASE64);
		request.setHeader(HttpHeaderConstants.X_OS, Funcs.getOSVersion());
		request.setHeader(HttpHeaderConstants.X_DEVICE_MODEL, Funcs.getDeviceName());
		request.setHeader(HttpHeaderConstants.X_LOCALE, Funcs.getLocale());
		request.setHeader(HttpHeaderConstants.X_NEXERCISE_CLIENT_VERSION, ApplicationConstants.APP_VERSION);
		request.setHeader(HttpHeaderConstants.USER_AGENT, Funcs.getUserAgent());
		request.setHeader(HttpHeaderConstants.USER_ID, getUserID());
//		HttpResponse response= httpclient.execute(request);
//		String result= Funcs.getStringFromInputStream(response.getEntity().getContent());
//		Logger.writeAPIRequestLog("GET  " + query, "");
		String result = "";
		try{
			HttpResponse httpresponse = httpclient.execute(request);
			result = getResponseBody(httpresponse);
		}catch (OutOfMemoryError E) {
		}
//		if ( result != null ) {

			// if (result.length() > 60) {
			// Logger.writeAPIResponseLog(result.substring(0, 20));
			// Logger.writeAPIResponseLog(result.substring(result.length()-20, result.length()-1));
			// } else {
//			Logger.writeAPIResponseLog(result);

			// }

//		}

		httpclient.getConnectionManager().shutdown();
		return result;
	}
	public String getFromWebService(String query, String userId) throws ClientProtocolException, IOException, Exception {

		HttpClient httpclient = returnDefaultHTTPClientWithTimeOut();

		HttpGet request = new HttpGet(URI.create(query));

		// TODO : Have to change the following base 64 encoded string
		// to get user and password from APIConstants class

		request.setHeader(HttpHeaderConstants.AUTHORIZATION, "Basic " + APIConstants.API_HTTP_AUTH_BASE64);
		request.setHeader(HttpHeaderConstants.X_OS, Funcs.getOSVersion());
		request.setHeader(HttpHeaderConstants.X_DEVICE_MODEL, Funcs.getDeviceName());
		request.setHeader(HttpHeaderConstants.X_LOCALE, Funcs.getLocale());
		request.setHeader(HttpHeaderConstants.X_NEXERCISE_CLIENT_VERSION, ApplicationConstants.APP_VERSION);
		request.setHeader(HttpHeaderConstants.USER_AGENT, Funcs.getUserAgent());
		request.setHeader(HttpHeaderConstants.USER_ID, userId);

//		HttpResponse response= httpclient.execute(request);
//		String result= Funcs.getStringFromInputStream(response.getEntity().getContent());
//		Logger.writeAPIRequestLog("GET  " + query, "");
		String result = null;
		try{
			result = httpclient.execute(request, new BasicResponseHandler());
		}catch(Exception e){
			
		}
//		if ( result != null ) {

			// if (result.length() > 60) {
			// Logger.writeAPIResponseLog(result.substring(0, 20));
			// Logger.writeAPIResponseLog(result.substring(result.length()-20, result.length()-1));
			// } else {
//			Logger.writeAPIResponseLog(result);

			// }

//		}

		httpclient.getConnectionManager().shutdown();
		return result;
	}
	public String getFromWebServiceForFacebook(String query) throws ClientProtocolException, IOException, Exception {

		HttpClient httpclient = returnDefaultHTTPClientWithTimeOut();

		HttpGet request = new HttpGet(URI.create(query));
		request.setHeader(HttpHeaderConstants.AUTHORIZATION, "Basic " + APIConstants.API_HTTP_AUTH_BASE64);
		request.setHeader(HttpHeaderConstants.X_OS, Funcs.getOSVersion());
		request.setHeader(HttpHeaderConstants.X_DEVICE_MODEL, Funcs.getDeviceName());
		request.setHeader(HttpHeaderConstants.X_LOCALE, Funcs.getLocale());
		request.setHeader(HttpHeaderConstants.X_NEXERCISE_CLIENT_VERSION, ApplicationConstants.APP_VERSION);
		request.setHeader(HttpHeaderConstants.USER_AGENT, Funcs.getUserAgent());
		request.setHeader(HttpHeaderConstants.USER_ID, getUserID());

		ResponseHandler < String > handler = new BasicResponseHandler();
		String result = "";

		// Can throw exception

		Logger.writeAPIRequestLog("GET  " + query, "");
		result = httpclient.execute(request, handler);
		if ( result != null ) {

			// if (result.length() > 60) {
			// Logger.writeAPIResponseLog(result.substring(0, 20));
			// Logger.writeAPIResponseLog(result.substring(result.length()-20, result.length()-1));
			// } else {
			Logger.writeAPIResponseLog(result);

			// }

		}

		httpclient.getConnectionManager().shutdown();
		return result;
	}


	public String getDataFromTextFile(Context context, String fileName) throws IOException {

		InputStream myInput = context.getAssets().open(fileName);
		StringBuilder text = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(myInput));
		String line = reader.readLine();
		if ( line != null ) {
			text.append(line);
		}

		return text.toString();
	}


	public String postOnWebService(String path, String json) throws ClientProtocolException, IOException, Exception {

		HttpClient httpclient = returnDefaultHTTPClientWithTimeOut();
		HttpPost httpost = new HttpPost(path);

		StringEntity se = null;
		// Can throw exception
		se = new StringEntity(json);

		httpost.setEntity(se);
		httpost.setHeader(HttpHeaderConstants.AUTHORIZATION, "Basic " + APIConstants.API_HTTP_AUTH_BASE64);
		httpost.setHeader(HttpHeaderConstants.X_OS, Funcs.getOSVersion());
		httpost.setHeader(HttpHeaderConstants.X_DEVICE_MODEL, Funcs.getDeviceName());
		httpost.setHeader(HttpHeaderConstants.X_LOCALE, Funcs.getLocale());
		httpost.setHeader(HttpHeaderConstants.X_NEXERCISE_CLIENT_VERSION, ApplicationConstants.APP_VERSION);
		httpost.setHeader(HttpHeaderConstants.USER_AGENT, Funcs.getUserAgent());
		httpost.setHeader(HttpHeaderConstants.USER_ID, getUserID());

		ResponseHandler < String > responseHandler = new BasicResponseHandler();
		String result = "";

//		Logger.writeAPIRequestLog("POST  " + path, json);
//		HttpResponse response= httpclient.execute(httpost);
//		result= Funcs.getStringFromInputStream(response.getEntity().getContent());
		try{
			result = httpclient.execute(httpost, responseHandler);
		}catch (OutOfMemoryError E) {
		}
		
		if ( result != null ) {

			// if (response.length() > 60) {
			// Logger.writeAPIResponseLog(response.substring(0, 20));
			// Logger.writeAPIResponseLog(response.substring(response.length()-20, response.length()-1));
			// } else {
//			Logger.writeAPIResponseLog(response);

			// }

		}

		httpclient.getConnectionManager().shutdown();
		return result;
	}


	public String postOnWebService(String path, String json , String userIdAsHeader) throws ClientProtocolException, IOException, Exception {

		HttpClient httpclient = returnDefaultHTTPClientWithTimeOut();
		HttpPost httpost = new HttpPost(path);

		StringEntity se = null;
		// Can throw exception
		se = new StringEntity(json);

		httpost.setEntity(se);
		httpost.setHeader(HttpHeaderConstants.AUTHORIZATION, "Basic " + APIConstants.API_HTTP_AUTH_BASE64);
		httpost.setHeader(HttpHeaderConstants.X_OS, Funcs.getOSVersion());
		httpost.setHeader(HttpHeaderConstants.X_DEVICE_MODEL, Funcs.getDeviceName());
		httpost.setHeader(HttpHeaderConstants.X_LOCALE, Funcs.getLocale());
		httpost.setHeader(HttpHeaderConstants.X_NEXERCISE_CLIENT_VERSION, ApplicationConstants.APP_VERSION);
		httpost.setHeader(HttpHeaderConstants.USER_AGENT, Funcs.getUserAgent());
		httpost.setHeader(HttpHeaderConstants.USER_ID, userIdAsHeader);

		ResponseHandler < String > responseHandler = new BasicResponseHandler();
		String response = "";

		Logger.writeAPIRequestLog("POST  " + path, json);
		response = httpclient.execute(httpost, responseHandler);
		if ( response != null ) {

			// if (response.length() > 60) {
			// Logger.writeAPIResponseLog(response.substring(0, 20));
			// Logger.writeAPIResponseLog(response.substring(response.length()-20, response.length()-1));
			// } else {
			Logger.writeAPIResponseLog(response);

			// }

		}

		httpclient.getConnectionManager().shutdown();
		return response;
	}
	
	public String postOnWebService(String path, String json , String userIdAsHeader, boolean useHttpResponse) throws ClientProtocolException, IOException, Exception {

		HttpClient httpclient = returnDefaultHTTPClientWithTimeOut();
		HttpPost httpost = new HttpPost(path);

		StringEntity se = null;
		// Can throw exception
		se = new StringEntity(json);

		httpost.setEntity(se);
		httpost.setHeader(HttpHeaderConstants.AUTHORIZATION, "Basic " + APIConstants.API_HTTP_AUTH_BASE64);
		httpost.setHeader(HttpHeaderConstants.X_OS, Funcs.getOSVersion());
		httpost.setHeader(HttpHeaderConstants.X_DEVICE_MODEL, Funcs.getDeviceName());
		httpost.setHeader(HttpHeaderConstants.X_LOCALE, Funcs.getLocale());
		httpost.setHeader(HttpHeaderConstants.X_NEXERCISE_CLIENT_VERSION, ApplicationConstants.APP_VERSION);
		httpost.setHeader(HttpHeaderConstants.USER_AGENT, Funcs.getUserAgent());
		httpost.setHeader(HttpHeaderConstants.USER_ID, userIdAsHeader);

		ResponseHandler < String > responseHandler = new BasicResponseHandler();
		String response = "";

		Logger.writeAPIRequestLog("POST  " + path, json);
		
		HttpResponse httpresponse =  httpclient.execute(httpost);
		response = getResponseBody(httpresponse);
		
		//response = httpclient.execute(httpost, responseHandler);
		if ( response != null ) {
			Logger.writeAPIResponseLog(response);

		}

		httpclient.getConnectionManager().shutdown();
		return response;
	}


	public String postOnWebService(String path, String json, ResponseHandler < String > responseHandler) throws ClientProtocolException, IOException, Exception {

		HttpClient httpclient = returnDefaultHTTPClientWithTimeOut();
		HttpPost httpost = new HttpPost(path);

		StringEntity se = null;
		// Can throw exception
		se = new StringEntity(json);

		httpost.setEntity(se);
		httpost.setHeader(HttpHeaderConstants.AUTHORIZATION, "Basic " + APIConstants.API_HTTP_AUTH_BASE64);
		httpost.setHeader(HttpHeaderConstants.X_OS, Funcs.getOSVersion());
		httpost.setHeader(HttpHeaderConstants.X_DEVICE_MODEL, Funcs.getDeviceName());
		httpost.setHeader(HttpHeaderConstants.X_LOCALE, Funcs.getLocale());
		httpost.setHeader(HttpHeaderConstants.X_NEXERCISE_CLIENT_VERSION, ApplicationConstants.APP_VERSION);
		httpost.setHeader(HttpHeaderConstants.USER_AGENT, Funcs.getUserAgent());
		httpost.setHeader(HttpHeaderConstants.USER_ID, getUserID());

		String response = "";

		Logger.writeAPIRequestLog("POST  " + path, json);
		response = httpclient.execute(httpost, responseHandler);
		if ( response != null ) {

			// if ( response.length() > 60 ) {
			// Logger.writeAPIResponseLog(response.substring(0, 20));
			// Logger.writeAPIResponseLog(response.substring(response.length() - 20, response.length() - 1));
			// }
			// else {
			
			Logger.writeAPIResponseLog(response);
			

			// }

		}

		httpclient.getConnectionManager().shutdown();
		return response;
	}
	
	
	
	public String getUUIDFromWebService(String query) throws ClientProtocolException, IOException, Exception {

		HttpClient httpclient = returnDefaultHTTPClientWithTimeOut();

		HttpGet request = new HttpGet(URI.create(query));

		// TODO : Have to change the following base 64 encoded string
		// to get user and password from APIConstants class
		
		

		request.setHeader(HttpHeaderConstants.AUTHORIZATION, "Basic " + APIConstants.API_HTTP_AUTH_BASE64);
		request.setHeader(HttpHeaderConstants.X_OS, Funcs.getOSVersion());
		request.setHeader(HttpHeaderConstants.X_DEVICE_MODEL, Funcs.getDeviceName());
		request.setHeader(HttpHeaderConstants.X_LOCALE, Funcs.getLocale());
		request.setHeader(HttpHeaderConstants.X_NEXERCISE_CLIENT_VERSION, ApplicationConstants.APP_VERSION);
		request.setHeader(HttpHeaderConstants.USER_AGENT, Funcs.getUserAgent());
		request.setHeader(HttpHeaderConstants.USER_ID, getUserID());

		

//		HttpResponse response= httpclient.execute(request);
//		String result= Funcs.getStringFromInputStream(response.getEntity().getContent());
//		Logger.writeAPIRequestLog("GET  " + query, "");
		String result="";
		try {

			HttpEntity entity = httpclient.execute(request).getEntity();
			
			result=EntityUtils.toString(entity);
			
		} catch (Exception e) {
			// TODO: handle exception
			
			e.printStackTrace();
		}
		
	
		httpclient.getConnectionManager().shutdown();
		return result;
	}
	

	public String putOnWebService(String path, String json) throws ClientProtocolException, IOException, Exception {

		HttpClient httpclient = returnDefaultHTTPClientWithTimeOut();
		HttpPut httpPut = new HttpPut(path);
		StringEntity se = null;
		// Can throw exception
		se = new StringEntity(json);

		httpPut.setEntity(se);
		httpPut.setHeader(HttpHeaderConstants.AUTHORIZATION, "Basic " + APIConstants.API_HTTP_AUTH_BASE64);
		httpPut.setHeader(HttpHeaderConstants.X_OS, Funcs.getOSVersion());
		httpPut.setHeader(HttpHeaderConstants.X_DEVICE_MODEL, Funcs.getDeviceName());
		httpPut.setHeader(HttpHeaderConstants.X_LOCALE, Funcs.getLocale());
		httpPut.setHeader(HttpHeaderConstants.X_NEXERCISE_CLIENT_VERSION, ApplicationConstants.APP_VERSION);
		httpPut.setHeader(HttpHeaderConstants.USER_AGENT, Funcs.getUserAgent());
		httpPut.setHeader(HttpHeaderConstants.USER_ID, getUserID());

		ResponseHandler < String > responseHandler = new BasicResponseHandler();
		String response = "";

		Logger.writeAPIRequestLog("PUT  " + path, json);
		
		try{
			response = httpclient.execute(httpPut, responseHandler);
		}catch (OutOfMemoryError E) {
		}
		
		if ( response != null ) {
			//
			// if (response.length() > 60) {
			// Logger.writeAPIResponseLog(response.substring(0, 20));
			// Logger.writeAPIResponseLog(response.substring(response.length()-20, response.length()-1));
			// } else {
			try{
				Logger.writeAPIResponseLog(response);
			}catch(Exception e){
			}

			// }

		}

		httpclient.getConnectionManager().shutdown();
		return response;
	}

	public String putOnWebService(String path, String json,boolean useHttpResponse) throws ClientProtocolException, IOException, Exception {

		HttpClient httpclient = returnDefaultHTTPClientWithTimeOut();
		HttpPut httpPut = new HttpPut(path);
		StringEntity se = null;
		// Can throw exception
		se = new StringEntity(json);

		httpPut.setEntity(se);
		httpPut.setHeader(HttpHeaderConstants.AUTHORIZATION, "Basic " + APIConstants.API_HTTP_AUTH_BASE64);
		httpPut.setHeader(HttpHeaderConstants.X_OS, Funcs.getOSVersion());
		httpPut.setHeader(HttpHeaderConstants.X_DEVICE_MODEL, Funcs.getDeviceName());
		httpPut.setHeader(HttpHeaderConstants.X_LOCALE, Funcs.getLocale());
		httpPut.setHeader(HttpHeaderConstants.X_NEXERCISE_CLIENT_VERSION, ApplicationConstants.APP_VERSION);
		httpPut.setHeader(HttpHeaderConstants.USER_AGENT, Funcs.getUserAgent());
		httpPut.setHeader(HttpHeaderConstants.USER_ID, getUserID());
		String response = "";

		Logger.writeAPIRequestLog("PUT  " + path, json);
		try{
			HttpResponse httpresponse = httpclient.execute(httpPut);
			response = getResponseBody(httpresponse);
		}catch (OutOfMemoryError E) {
		}

		if ( response != null ) {
			//
			// if (response.length() > 60) {
			// Logger.writeAPIResponseLog(response.substring(0, 20));
			// Logger.writeAPIResponseLog(response.substring(response.length()-20, response.length()-1));
			// } else {
			try{
				Logger.writeAPIResponseLog(response);
			}catch(Exception e){
			}

			// }

		}
		httpclient.getConnectionManager().shutdown();
		return response;
	}


	public String putOnWebService(String path, String userId, String json) throws ClientProtocolException, IOException, Exception {
    
    	HttpClient httpclient = returnDefaultHTTPClientWithTimeOut();
    	HttpPut httpPut = new HttpPut(path);
    	StringEntity se = null;
    	// Can throw exception
    	se = new StringEntity(json);
    
    	httpPut.setEntity(se);
    	httpPut.setHeader(HttpHeaderConstants.AUTHORIZATION, "Basic " + APIConstants.API_HTTP_AUTH_BASE64);
    	httpPut.setHeader(HttpHeaderConstants.X_OS, Funcs.getOSVersion());
    	httpPut.setHeader(HttpHeaderConstants.X_DEVICE_MODEL, Funcs.getDeviceName());
    	httpPut.setHeader(HttpHeaderConstants.X_LOCALE, Funcs.getLocale());
    	httpPut.setHeader(HttpHeaderConstants.X_NEXERCISE_CLIENT_VERSION, ApplicationConstants.APP_VERSION);
    	httpPut.setHeader(HttpHeaderConstants.USER_AGENT, Funcs.getUserAgent());
		httpPut.setHeader(HttpHeaderConstants.USER_ID, userId);
    
    	ResponseHandler < String > responseHandler = new BasicResponseHandler();
    	String response = "";
    
    	Logger.writeAPIRequestLog("PUT  " + path, json);
    	try{
    		response = httpclient.execute(httpPut, responseHandler);
		}catch (OutOfMemoryError E) {
		}
    	
    	if ( response != null ) {
    		//
    		// if (response.length() > 60) {
    		// Logger.writeAPIResponseLog(response.substring(0, 20));
    		// Logger.writeAPIResponseLog(response.substring(response.length()-20, response.length()-1));
    		// } else {
    		Logger.writeAPIResponseLog(response);
    
    		// }
    
    	}
    
    	httpclient.getConnectionManager().shutdown();
    	return response;
    }

	 public String deleteFromWebService(String path) throws ClientProtocolException, IOException, Exception {

	        HttpClient httpclient = returnDefaultHTTPClientWithTimeOut();

	        HttpDelete httpDelete = new HttpDelete(path);
	        httpDelete.setHeader(HttpHeaderConstants.AUTHORIZATION, "Basic " + APIConstants.API_HTTP_AUTH_BASE64);
	        httpDelete.setHeader(HttpHeaderConstants.X_OS, Funcs.getOSVersion());
	        httpDelete.setHeader(HttpHeaderConstants.X_DEVICE_MODEL, Funcs.getDeviceName());
	        httpDelete.setHeader(HttpHeaderConstants.X_LOCALE, Funcs.getLocale());
	        httpDelete.setHeader(HttpHeaderConstants.X_NEXERCISE_CLIENT_VERSION, ApplicationConstants.APP_VERSION);
	        httpDelete.setHeader(HttpHeaderConstants.USER_AGENT, Funcs.getUserAgent());

	        ResponseHandler < String > responseHandler = new BasicResponseHandler();
	        String response = "";

	        // Can throw exception
	        try{
	        	response = httpclient.execute(httpDelete, responseHandler);
			}catch (OutOfMemoryError E) {
			}

	        httpclient.getConnectionManager().shutdown();
	        return response;
	    }

    public String deleteFromWebService(String path, String userId) throws ClientProtocolException, IOException, Exception {

		HttpClient httpclient = returnDefaultHTTPClientWithTimeOut();

		HttpDelete httpDelete = new HttpDelete(path);
		httpDelete.setHeader(HttpHeaderConstants.AUTHORIZATION, "Basic " + APIConstants.API_HTTP_AUTH_BASE64);
		httpDelete.setHeader(HttpHeaderConstants.X_OS, Funcs.getOSVersion());
		httpDelete.setHeader(HttpHeaderConstants.X_DEVICE_MODEL, Funcs.getDeviceName());
		httpDelete.setHeader(HttpHeaderConstants.X_LOCALE, Funcs.getLocale());
		httpDelete.setHeader(HttpHeaderConstants.X_NEXERCISE_CLIENT_VERSION, ApplicationConstants.APP_VERSION);
		httpDelete.setHeader(HttpHeaderConstants.USER_AGENT, Funcs.getUserAgent());
		httpDelete.setHeader(HttpHeaderConstants.USER_ID, userId);

		ResponseHandler < String > responseHandler = new BasicResponseHandler();
		String response = "";

		// Can throw exception
		response = httpclient.execute(httpDelete, responseHandler);
//		HttpResponse httpresponse= httpclient.execute(httpDelete);
//		response= Funcs.getStringFromInputStream(httpresponse.getEntity().getContent());

		httpclient.getConnectionManager().shutdown();
		return response;
	}


	public String getContentFromUrl(String strURL) throws IOException {

		URLConnection conn = null;
		InputStream inputStream = null;
		URL url = new URL(strURL);
		conn = url.openConnection();
		HttpURLConnection httpConn = (HttpURLConnection) conn;
		httpConn.setRequestMethod("GET");
		httpConn.connect();
		if ( httpConn.getResponseCode() == HttpURLConnection.HTTP_OK ) {
			inputStream = httpConn.getInputStream();
		}
		return Funcs.getStringFromInputStream(inputStream);
	}


	private HttpClient returnDefaultHTTPClientWithTimeOut() {

		HttpParams httpParameters = new BasicHttpParams();
		// Set the timeout in milliseconds until a connection is established.
		// The default value is zero, that means the timeout is not used.
		int timeoutConnection = 30000;
		HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
		// Set the default socket timeout (SO_TIMEOUT)
		// in milliseconds which is the timeout for waiting for data.
		int timeoutSocket = 60000;
		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

		DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
		return httpClient;
	}
	
	private String getUserID() {
		return PreferenceHelper.getStringPreference(_context,
				UserPreferencesConstants.USER_PREFERENCES, UserPreferencesConstants.USER_UUID, "");
	}
	
	public static String getResponseBody(HttpResponse response) {

	    String response_text = null;
	    HttpEntity entity = null;
	    try {
	        entity = response.getEntity();
	        response_text = _getResponseBody(entity);
	    } catch (ParseException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        if (entity != null) {
	            try {
	                entity.consumeContent();
	            } catch (IOException e1) {
	            }
	        }
	    }
	    return response_text;
	}

	public static String _getResponseBody(final HttpEntity entity) throws IOException, ParseException {

	    if (entity == null) {
	        throw new IllegalArgumentException("HTTP entity may not be null");
	    }

	    InputStream instream = entity.getContent();

	    if (instream == null) {
	        return "";
	    }

	    if (entity.getContentLength() > Integer.MAX_VALUE) {
	        throw new IllegalArgumentException(

	        "HTTP entity too large to be buffered in memory");
	    }

	    String charset = getContentCharSet(entity);

	    if (charset == null) {

	        charset = HTTP.DEFAULT_CONTENT_CHARSET;

	    }

	    Reader reader = new InputStreamReader(instream, charset);

	    StringBuilder buffer = new StringBuilder();

	    try {

	        char[] tmp = new char[1024];

	        int l;

	        while ((l = reader.read(tmp)) != -1) {

	            buffer.append(tmp, 0, l);

	        }

	    } finally {

	        reader.close();

	    }

	    return buffer.toString();

	}

	public static String getContentCharSet(final HttpEntity entity) throws ParseException {

	    if (entity == null) {
	        throw new IllegalArgumentException("HTTP entity may not be null");
	    }

	    String charset = null;

	    if (entity.getContentType() != null) {

	        HeaderElement values[] = entity.getContentType().getElements();

	        if (values.length > 0) {

	            NameValuePair param = values[0].getParameterByName("charset");

	            if (param != null) {

	                charset = param.getValue();

	            }

	        }

	    }

	    return charset;

	}
}