package academy.devdojo.springboot2essentials.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnimePostRequestBody {

    @NotEmpty(message = "The anime name cannot be empty")
    @Schema(description = "This is the anime's name", example = "Dragon Ball Z" , required = true)
    private String name;

    @Schema(description = "É um código de barras no padrão EAN definido pela GS1", example = "12345678910" , required = true)
    private String ean13;

    private String url;
}
