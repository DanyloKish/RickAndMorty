package mate.academy.rickandmorty.service;

import com.fasterxml.jackson.core.type.TypeReference;

public interface ApiHttpClient {
    <T> T get(String url, TypeReference<T> typeReference);
}
