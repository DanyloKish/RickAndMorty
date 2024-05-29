package mate.academy.rickandmorty.dto.internal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import mate.academy.rickandmorty.model.Gender;
import mate.academy.rickandmorty.model.Status;

@Data
@Schema(description = "Character DTO Information")
public class CharacterDto {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Character Id",
            example = "123")
    private Long id;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Character Id"
            + " from external API", example = "123")
    private Long externalId;
    @Schema(description = "Character's name", example = "Rick Sanchez")
    private String name;
    @Schema(description = "Character's status", example = "Alive")
    private Status status;
    @Schema(description = "Character's gender", example = "Male")
    private Gender gender;
}
