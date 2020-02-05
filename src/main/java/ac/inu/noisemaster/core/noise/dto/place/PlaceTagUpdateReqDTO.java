package ac.inu.noisemaster.core.noise.dto.place;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class PlaceTagUpdateReqDTO {
    @NotNull
    private Long id;
    @NotNull
    private String tag;

    public PlaceTagUpdateReqDTO(Long id, String tag) {
        this.id = id;
        this.tag = tag;
    }
}
