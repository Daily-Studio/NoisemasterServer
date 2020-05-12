package ac.inu.noisemaster.core.noise.dto.place;

import ac.inu.noisemaster.core.noise.domain.model.Place;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlaceDTO {
    private Long placeId;
    private String tag;
    private String gridX;
    private String gridY;

    @Builder
    public PlaceDTO(Long placeId, String tag, String gridX, String gridY) {
        this.placeId = placeId;
        this.tag = tag;
        this.gridX = gridX;
        this.gridY = gridY;
    }

    public static PlaceDTO from(Place place) {
        return PlaceDTO.builder()
                .placeId(place.getId())
                .tag(place.getTag())
                .gridX(place.getGridX())
                .gridY(place.getGridY())
                .build();
    }
}
