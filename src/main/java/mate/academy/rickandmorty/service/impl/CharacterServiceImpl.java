package mate.academy.rickandmorty.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.dto.external.ApiCharacterDto;
import mate.academy.rickandmorty.dto.external.ApiResponseDto;
import mate.academy.rickandmorty.dto.internal.CharacterDto;
import mate.academy.rickandmorty.dto.mapper.CharacterMapper;
import mate.academy.rickandmorty.model.CartoonCharacter;
import mate.academy.rickandmorty.repository.CharacterRepository;
import mate.academy.rickandmorty.service.ApiHttpClient;
import mate.academy.rickandmorty.service.CharacterService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CharacterServiceImpl implements CharacterService {
    private static final String endPoint = "/character";
    @Value("${external.api.url}")
    private String externalApiUrl;
    private final ApiHttpClient httpClient;
    private final CharacterRepository characterRepository;
    private final CharacterMapper characterMapper;

    @PostConstruct
    @Override
    public void fetchCharactersFromApi() {
        ApiResponseDto<ApiCharacterDto> responseDto =
                httpClient.get(externalApiUrl + endPoint, new TypeReference<>() {
                });
        saveOrUpdateCharactersToDB(responseDto);
        while (responseDto.getInfo().getNextPageUrl() != null) {
            responseDto = httpClient.get(responseDto.getInfo().getNextPageUrl(),
                    new TypeReference<>() {});
            saveOrUpdateCharactersToDB(responseDto);
        }
    }

    @Override
    public CharacterDto getRandomCharacter() {
        long randomId = new Random().nextLong(characterRepository.count()) + 1L;
        CartoonCharacter character = characterRepository.findById(randomId).orElseThrow();
        return characterMapper.toCharacterDto(character);
    }

    @Override
    public List<CharacterDto> findCharactersByName(String namePart) {
        return characterRepository.findAllByNameContains(namePart).stream()
                .map(characterMapper::toCharacterDto)
                .toList();
    }

    private void saveOrUpdateCharactersToDB(ApiResponseDto<ApiCharacterDto> responseDto) {
        Map<Long, ApiCharacterDto> apiCharacterDtos = Arrays.stream(responseDto.getResults())
                .collect(Collectors.toMap(ApiCharacterDto::getId, Function.identity()));
        Set<Long> externalIds = apiCharacterDtos.keySet();
        List<CartoonCharacter> existingCharacters =
                characterRepository.findAllByExternalIdIn(externalIds);
        existingCharacters.forEach(character -> characterMapper.toCharacterEntity(
                apiCharacterDtos.get(character.getExternalId()), character));
        characterRepository.saveAll(existingCharacters);
        Map<Long, CartoonCharacter> existingCharactersWithIds = existingCharacters.stream()
                .collect(Collectors.toMap(CartoonCharacter::getExternalId, Function.identity()));
        Set<Long> existingIds = existingCharactersWithIds.keySet();
        externalIds.removeAll(existingIds);
        List<CartoonCharacter> newCharactersToSave = externalIds.stream()
                .map(id -> characterMapper.toCharacterEntity(apiCharacterDtos.get(id)))
                .toList();
        characterRepository.saveAll(newCharactersToSave);
    }
}
