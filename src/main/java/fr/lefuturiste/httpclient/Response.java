package fr.lefuturiste.httpclient;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Response {
    private int status;
    private String protocol;
    private String statusText;
    private Map<String, List<String>> headers = new HashMap<>();
    private String body;
    private JSONTokener jsonTokener;
    private JSONObject jsonObject;
    private JSONArray jsonArray;

    public Response setStatus(int status) {
        this.status = status;
        return this;
    }

    public Response setStatusText(String statusText) {
        this.statusText = statusText;
        return this;
    }

    public Response setHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
        return this;
    }

    public Response setBody(String body) {
        this.body = body;
        return this;
    }

    public Response setProtocol(String protocol) {
        this.protocol = protocol;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getStatusText() {
        return statusText;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }

    public JSONTokener getJsonTokener() {
        if (this.jsonTokener == null) {
            this.jsonTokener = new JSONTokener(this.body);
        }
        return this.jsonTokener;
    }

    public JSONObject getJsonObject() {
        if (this.jsonObject == null) {
            this.jsonObject = new JSONObject(this.getJsonTokener());
        }
        return this.jsonObject;
    }

    public JSONArray getJsonArray() {
        if (this.jsonArray == null) {
            this.jsonArray = new JSONArray(this.getJsonTokener());
        }
        return this.jsonArray;
    }

}
