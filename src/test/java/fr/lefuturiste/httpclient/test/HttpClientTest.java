package fr.lefuturiste.httpclient.test;

import static org.junit.Assert.assertTrue;

import fr.lefuturiste.httpclient.HttpClient;
import fr.lefuturiste.httpclient.Request;
import fr.lefuturiste.httpclient.Response;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

/**
 * Unit test for simple HttpClient.
 */
public class HttpClientTest
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    private HttpClient getHttpClient()
    {
        return new HttpClient();
    }

    @Test
    public void shouldMakeGetRequest() throws Exception {
        UUID requestUuid = UUID.randomUUID();
        String url = "https://httpbin.org/get";
        Request request = new Request()
                .setUrl(url)
                .addHeader("User-Agent", requestUuid.toString())
                .addHeader("X-Custom-Header", "value")
                .addQueryParam("key", "value");
        Response response = this.getHttpClient().get(request);

        Assert.assertEquals(200, response.getStatus());
        Assert.assertEquals("OK", response.getStatusText());
        Assert.assertEquals(requestUuid.toString(), ((JSONObject) response.getJsonObject().get("headers")).get("User-Agent"));
        Assert.assertEquals("value", ((JSONObject) response.getJsonObject().get("headers")).get("X-Custom-Header"));
        Assert.assertEquals("value", ((JSONObject) response.getJsonObject().get("args")).get("key"));
        Assert.assertEquals(request.getUrl().getHost(), ((JSONObject) response.getJsonObject().get("headers")).get("Host"));
        Assert.assertEquals(url + request.getQueryParamsAsString(), response.getJsonObject().get("url"));
    }
}
