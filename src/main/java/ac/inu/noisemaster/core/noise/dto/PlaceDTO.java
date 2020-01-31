package ac.inu.noisemaster.core.noise.dto;

import ac.inu.noisemaster.core.noise.domain.Place;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PlaceDTO {
    private String tag;
    private String gridX;
    private String gridY;

    @Builder
    public PlaceDTO(String tag, String gridX, String gridY) {
        this.tag = tag;
        this.gridX = gridX;
        this.gridY = gridY;
    }

    public static PlaceDTO from(Place place) {
        return PlaceDTO.builder()
                .tag(place.getTag())
                .gridX(place.getGridX())
                .gridY(place.getGridY())
                .build();
    }
}
