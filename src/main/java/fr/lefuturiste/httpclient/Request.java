package fr.lefuturiste.httpclient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Request {
    private String protocol = "HTTP/1.1";
    private String method;
    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> queryParams = new HashMap<>();
    private URL url;
    private String body;
    private Class httpAdapterClass = HttpURLConnectionAdapter.class;

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Request setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public Request setMethod(String method) {
        this.method = method.toUpperCase();
        return this;
    }

    public String getQueryParamsAsString() {
        StringBuilder str = new StringBuilder();
        str.append('?');
        boolean first = true;
        for (java.util.Map.Entry<String, String> e : this.queryParams.entrySet()) {
            if (first)
                first = false;
            else
                str.append('&');
            str.append(e.getKey());
            str.append('=');
            str.append(e.getValue());
        }
        return str.toString();
    }

    public URL getUrl() throws MalformedURLException {
        StringBuilder url = new StringBuilder(this.url.toString());
        if (!this.queryParams.isEmpty()) {
            url.append(this.getQueryParamsAsString());
        }
        return new URL(url.toString());
    }

    public Request setUrl(URL url) {
        this.url = url;
        return this;
    }

    public Request setUrl(String url) throws MalformedURLException {
        this.url = new URL(url);
        return this;
    }

    public Request addQueryParam(String key, String value) {
        this.queryParams.put(key, value);
        return this;
    }

    public Request addHeader(String key, String value) {
        this.headers.put(key, value);
        return this;
    }

    public Request setProtocol(String protocol) {
        this.protocol = protocol;
        return this;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getMethod() {
        return method;
    }

    public Request setHttpAdapterClass(Class httpAdapterClass) {
        this.httpAdapterClass = httpAdapterClass;
        return this;
    }

    public String getHttpAdapterClassName() {
        return httpAdapterClass.getSimpleName();
    }

    public String getBody() {
        return body;
    }

    public Request setBody(String body) {
        this.body = body;
        return this;
    }

    public Request setJsonObjectAsBody(JSONObject jsonObject)
    {
        this.body = jsonObject.toString(0);
        this.headers.put("Content-Type", "application/json");
        return this;
    }

    public Request setJsonArrayAsBody(JSONArray jsonArray)
    {
        this.body = jsonArray.toString(0);
        this.headers.put("Content-Type", "application/json");
        return this;
    }

}
