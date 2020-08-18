package ac.inu.noisemaster.core.noise.domain.model;

import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Getter
public class Noises {
    private final List<Noise> noises;

    public Noises(final List<Noise> noises) {
        this.noises = noises;
    }

    public Noise getFirstNoise() {
        return this.noises.get(0);
    }

    public Noise getLastNoise() {
        int size = this.noises.size();
        return this.noises.get(size - 1);
    }

    public boolean isEmpty() {
        return this.noises.isEmpty();
    }

    public List<Noise> get() {
        return Collections.unmodifiableList(this.noises);
    }
}
