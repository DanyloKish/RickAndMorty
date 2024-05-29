package mate.academy.rickandmorty.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.dto.internal.CharacterDto;
import mate.academy.rickandmorty.service.CharacterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/characters")
@Tag(name = "Rick&Morty's character management", description = "Endpoints for managing characters"
        + " in the universe the animated series Rick & Morty.")
public class CharacterController {
    private final CharacterService characterService;

    @GetMapping("/random")
    @Operation(summary = "Get random character", description = "The request randomly generates"
            + " a wiki about one character of the animated series.")
    @ApiResponse(responseCode = "200", description = "The character returned successfully",
            content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CharacterDto.class))})
    public CharacterDto getRandom() {
        return characterService.getRandomCharacter();
    }

    @GetMapping("/by-name")
    @Operation(summary = "Get certain characters by name",
            description = "This request takes a string as an argument, and returns a list of"
                    + " all characters whose name contains the specified string.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of characters returned "
                    + "successfully", content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation =
                                    CharacterDto.class))) }),
            @ApiResponse(responseCode = "400", description = "Missing or incorrect 'name' "
                    + "parameter", content = @Content)
    })

    public List<CharacterDto> getByName(@Parameter(description = "part of the character's name",
            example = "rick") @RequestParam("name") String namePart) {
        return characterService.findCharactersByName(namePart);
    }
}
