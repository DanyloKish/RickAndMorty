package mate.academy.rickandmorty.dto.mapper;

import mate.academy.rickandmorty.dto.external.ApiCharacterDto;
import mate.academy.rickandmorty.dto.internal.CharacterDto;
import mate.academy.rickandmorty.model.CartoonCharacter;
import mate.academy.rickandmorty.model.Gender;
import mate.academy.rickandmorty.model.Status;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        implementationPackage = "<PACKAGE_NAME>.impl"
)
public interface CharacterMapper {
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(source = "id", target = "externalId"),
            @Mapping(source = "status", target = "status", qualifiedByName = "statusByValue"),
            @Mapping(source = "gender", target = "gender", qualifiedByName = "genderByValue")
    })
    CartoonCharacter toCharacterEntity(ApiCharacterDto characterDto);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "externalId", ignore = true),
            @Mapping(source = "status", target = "status", qualifiedByName = "statusByValue"),
            @Mapping(source = "gender", target = "gender", qualifiedByName = "genderByValue")
    })
    void toCharacterEntity(ApiCharacterDto characterDto, @MappingTarget CartoonCharacter character);

    CharacterDto toCharacterDto(CartoonCharacter character);

    @Named("statusByValue")
    default Status statusByValue(String value) {
        return Status.getByValue(value);
    }

    @Named("genderByValue")
    default Gender genderByValue(String value) {
        return Gender.getByValue(value);
    }
}
