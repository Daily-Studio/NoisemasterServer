package ac.inu.noisemaster.core.noise.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Noise extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double decibel;
    private String device;
    private String temperature;
    @ManyToOne
    private Place place;

    @Builder
    public Noise(Double decibel, String device, String temperature, String tag, Place place) {
        this.decibel = decibel;
        this.device = device;
        this.temperature = temperature;
        this.place = place;
    }

}
