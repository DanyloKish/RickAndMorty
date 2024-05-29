package mate.academy.rickandmorty.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.service.ApiHttpClient;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ApiHttpClientImpl implements ApiHttpClient {
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper;

    @Override
    public <T> T get(String url, TypeReference<T> typeReference) {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .build();
        try {
            HttpResponse<String> httpResponse =
                    httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(httpResponse.body(), typeReference);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Can't get API data from URL: " + url, e);
        }
    }
}
