package fr.lefuturiste.httpclient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpSocketAdapter extends HttpAdapter {
    public static Response execute(Request request) throws Exception {
        URL url = request.getUrl();
        StringBuilder pathUsedBuilder = new StringBuilder();
        pathUsedBuilder.append(url.getPath());
        if (url.getPath().isEmpty()) {
            pathUsedBuilder.append("/");
        }
        if (url.getQuery() != null) {
            pathUsedBuilder.append("?").append(url.getQuery());
        }
        if (url.getRef() != null) {
            pathUsedBuilder.append("#").append(url.getRef());
        }
        String hostUsed = url.getHost();
        int portUsed = url.getPort() == -1 ? url.getDefaultPort() : url.getPort();
        ArrayList<String> requestLines = new ArrayList<>();
        requestLines.add(request.getMethod() + " " + pathUsedBuilder.toString() + " " + request.getProtocol());
        for (Map.Entry<String, String> header : request.getHeaders().entrySet()) {
            requestLines.add(header.getKey() + ": " + header.getValue());
        }
        requestLines.add("");

//        System.out.println(hostUsed + ":" + portUsed);
//        for (String requestLine : requestLines) {
//            System.out.println(requestLine);
//        }

        Socket socket = new Socket(hostUsed, portUsed);
        OutputStream outputStream = socket.getOutputStream();
        PrintWriter printWriter = new PrintWriter(outputStream, true);
        for (String requestLine : requestLines) {
            printWriter.println(requestLine);
        }
        BufferedReader input = new BufferedReader(
                new InputStreamReader(
                        socket.getInputStream()
                )
        );
        String line = input.readLine();
        ArrayList<String> responseLines = new ArrayList<>();
        while (line != null) {
            responseLines.add(line);
            line = input.readLine();
        }
        socket.close();
        Response response = new Response();
        StatusLine statusLine = new StatusLine(responseLines.get(0));
        response
                .setStatus(statusLine.getStatusCode())
                .setStatusText(statusLine.getStatusMessage())
                .setProtocol(statusLine.getProtocol());
        Map<String, List<String>> headers = new HashMap<>();
        int bodyStartAt = 0;
        for (int i = 1; i < responseLines.size(); i++) {
            String headerLine = responseLines.get(i);
            if (headerLine.isEmpty()) {
                bodyStartAt = i+1;
                break;
            }
            String[] headerComponents = headerLine.split(":");
            String headerValue = headerComponents[1];
            if (headerValue.substring(0, 1).equals(" ")) {
                headerValue = headerValue.substring(1);
            }
            if (!headers.containsKey(headerComponents[0])) {
                List<String> headerArray = new ArrayList<>();
                headerArray.add(headerValue);
                headers.put(headerComponents[0], headerArray);
            } else {
                List<String> headerArray = headers.get(headerComponents[0]);
                headerArray.add(headerValue);
                headers.replace(headerComponents[0], headerArray);
            }
        }
        if (bodyStartAt == 0) {
            throw new InvalidResponseException();
        }
        StringBuilder body = new StringBuilder();
        for (int i = bodyStartAt; i < responseLines.size(); i++) {
            String transferEncodingHeader = headers.get("Transfer-Encoding").get(0);
            if (transferEncodingHeader != null && transferEncodingHeader.equals("chunked")) {
                System.out.println(Integer.parseInt(responseLines.get(i), 16));
                break;
            }
            body.append(responseLines.get(i));
            body.append("\n");
        }
        response
                .setHeaders(headers)
                .setBody(body.toString());
        return response;
    }
}
