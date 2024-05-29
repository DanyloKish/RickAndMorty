package mate.academy.rickandmorty.dto.external;

import lombok.Data;

@Data
public class ApiResponseDto<T> {
    private ApiInfoDto info;
    private T[] results;
}
