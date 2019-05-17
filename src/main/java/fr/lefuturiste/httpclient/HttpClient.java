package fr.lefuturiste.httpclient;

public class HttpClient {
//
//    public static void main(String[] args) {
//        Request request = new Request();
//        try {
//            request.setUrl(new URL("http://jsonplaceholder.typicode.com/users"));
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        request.setMethod("GET");
//        HttpClient httpClient = new HttpClient();
//        try {
//            Response response = httpClient.execute(request);
//            System.out.println(response.getJsonArray().length());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public Response get(Request request) throws Exception {
        request.setMethod("GET");
        return this.execute(request);
    }

    public Response post(Request request) throws Exception {
        request.setMethod("POST");
        return this.execute(request);
    }

    public Response put(Request request) throws Exception {
        request.setMethod("PUT");
        return this.execute(request);
    }

    public Response delete(Request request) throws Exception {
        request.setMethod("DELETE");
        return this.execute(request);
    }

    public Response execute(Request request) throws Exception {
        switch (request.getHttpAdapterClassName()) {
            case "HttpURLConnectionAdapter":
                return HttpURLConnectionAdapter.execute(request);
            case "HttpSocketAdapter":
                return HttpSocketAdapter.execute(request);
        }
        return null;
    }
}
