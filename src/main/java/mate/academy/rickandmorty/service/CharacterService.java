package mate.academy.rickandmorty.service;

import java.util.List;
import mate.academy.rickandmorty.dto.internal.CharacterDto;

public interface CharacterService {
    void fetchCharactersFromApi();

    CharacterDto getRandomCharacter();

    List<CharacterDto> findCharactersByName(String namePart);
}
