package com.oberasoftware.home.security.client;

import com.oberasoftware.home.security.ClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @author Renze de Vries
 */
public abstract class BaseClient {
    private static final Logger LOG = LoggerFactory.getLogger(BaseClient.class);

    protected enum REQUEST_MODE {
        GET,
        POST,
        DELETE,
        PUT
    }

    private final String endpointUrl;

    protected BaseClient(String endpointUrl) {
        this.endpointUrl = endpointUrl;
    }

    public String getEndpointUrl() {
        return endpointUrl;
    }

    private URL getUrl(String resource, Map<String, String> params) throws MalformedURLException, UnsupportedEncodingException {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(endpointUrl).append(URLEncoder.encode(resource, "UTF8"));

        boolean first = true;
        for(Map.Entry<String, String> param : params.entrySet()) {
            char queryChar = first ? '?' : '&';
            first = false;
            urlBuilder.append(queryChar).append(param.getKey()).append("=").append(URLEncoder.encode(param.getValue(), "UTF8"));
        }
        return new URL(urlBuilder.toString());
    }

    protected ClientResponse doInternalRequest(String connectionString, Map<String, String> params, byte[] postStream, REQUEST_MODE mode) throws ClientException {
        LOG.debug("Doing request to resource: {}", connectionString);

        HttpURLConnection urlConnection = null;
        try {
            URL url = getUrl(connectionString, params);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(mode.toString());
            urlConnection.setRequestProperty("Accept-Charset", "UTF-8");
            urlConnection.addRequestProperty("content-type", "application/json");

            if(postStream != null) {
                urlConnection.setDoOutput(true);
                urlConnection.getOutputStream().write(postStream);
            } else {
                urlConnection.connect();
            }
            return handleResponse(new ClientResponse(urlConnection.getInputStream(), urlConnection.getResponseCode()));
        } catch(MalformedURLException e) {
            throw new ClientException("The remote client url is invalid", e);
        } catch(UnsupportedEncodingException e) {
            throw new ClientException("Unsupported encoding", e);
        } catch(IOException e) {
            if(urlConnection != null) {
                try {
                    return handleResponse(new ClientResponse(urlConnection.getErrorStream(), urlConnection.getResponseCode()));
                } catch(IOException ex) {
                    throw new ClientException("Unable to connect to client", ex);
                }
            } else {
                throw new ClientException("Unable to remote, fatal exception on connection", e);
            }
        }
    }

    private ClientResponse handleResponse(ClientResponse response) throws ClientException {
        int responseCode = response.getStatus();

        if(responseCode >= 200 && responseCode <300) {
            return response;
        } else {
            String responseEntity = response.getEntityAsString();
            LOG.error("Remote server response with an error: {}", responseEntity);
            throw new ClientException("Unable to execute remote operation: " + response.getStatus());
        }
    }

}
