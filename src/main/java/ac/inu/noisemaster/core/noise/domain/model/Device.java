package ac.inu.noisemaster.core.noise.domain.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "device")
    private List<Noise> noises = new ArrayList<>();

    public Device(String name) {
        this.name = name;
    }

    public boolean isSame(Device device) {
        return this.name.equals(device.name);
    }

    public Noise getNoiseOne() {
        return noises.get(0);
    }

}
