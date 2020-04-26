package ac.inu.noisemaster.core.noise.domain.model;

import ac.inu.noisemaster.core.noise.dto.place.PlaceTagUpdateReqDTO;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tag;
    private String gridX;
    private String gridY;

    @Builder
    public Place(String tag, String gridX, String gridY) {
        this.tag = tag;
        this.gridX = gridX;
        this.gridY = gridY;
    }

    public boolean isSameGrid(Place place) {
        return this.tag.equals(place.tag);
    }

    public void updateTag(PlaceTagUpdateReqDTO placeTagUpdateReqDTO) {
        this.tag = placeTagUpdateReqDTO.getTag();
    }
}
