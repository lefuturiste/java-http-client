package fr.lefuturiste.httpclient;

import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

class HttpURLConnectionAdapter extends HttpAdapter {

    public static Response execute(Request request) throws IOException {
        Response response = new Response();
        HttpURLConnection urlConnection;
        if (request.getUrl().getProtocol().equals("https")) {
            urlConnection = (HttpsURLConnection) request.getUrl().openConnection();
        } else {
            urlConnection = (HttpURLConnection) request.getUrl().openConnection();
        }
        urlConnection.setRequestProperty("Accept", "*/*");
        urlConnection.setRequestMethod(request.getMethod());
        request.getHeaders().forEach(urlConnection::setRequestProperty);
        urlConnection.connect();
        response.setStatus(urlConnection.getResponseCode());
        response.setStatusText(urlConnection.getResponseMessage());
        response.setHeaders(urlConnection.getHeaderFields());
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        StringBuilder body = new StringBuilder();
        String line = bufferedReader.readLine();
        while (line != null) {
            if (!line.isEmpty()) {
                body.append(line);
                body.append("\n");
            }
            line = bufferedReader.readLine();
        }
        response.setBody(body.toString());
        StatusLine statusLine = new StatusLine(urlConnection.getHeaderField(null));
        response.setProtocol(statusLine.getProtocol());
        response.setStatusText(statusLine.getStatusMessage());
        response.setStatus(statusLine.getStatusCode());
        return response;
    }
}
