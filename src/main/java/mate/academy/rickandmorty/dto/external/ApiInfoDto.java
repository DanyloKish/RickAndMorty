package mate.academy.rickandmorty.dto.external;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ApiInfoDto {
    @JsonProperty("count")
    private int characterCount;
    @JsonProperty("pages")
    private int pagesCount;
    @JsonProperty("next")
    private String nextPageUrl;
    @JsonProperty("prev")
    private String prevPageUrl;
}
