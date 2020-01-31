package ac.inu.noisemaster.core.noise.service;

import ac.inu.noisemaster.core.noise.domain.Noise;
import ac.inu.noisemaster.core.noise.domain.Place;
import ac.inu.noisemaster.core.noise.dto.NoisePagingResDTO;
import ac.inu.noisemaster.core.noise.dto.NoiseResDTO;
import ac.inu.noisemaster.core.noise.dto.NoiseSaveDTO;
import ac.inu.noisemaster.core.noise.repository.NoiseRepository;
import ac.inu.noisemaster.core.noise.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NoiseService {

    private final NoiseRepository noiseRepository;
    private final PlaceRepository placeRepository;

    @Transactional
    public NoiseResDTO save(NoiseSaveDTO noiseSaveDTO) {
        Place place = noiseSaveDTO.toPlaceEntity();
        Place finalPlace = place;
        place = placeRepository.findAll().stream()
                .filter(place::isSameGrid)
                .findFirst()
                .orElseGet(() -> placeRepository.save(finalPlace));

        Noise noise = noiseRepository.save(noiseSaveDTO.toNoiseEntity(place));
        return NoiseResDTO.from(noise);
    }

    @Transactional(readOnly = true)
    public NoisePagingResDTO findNoiseAdvance(String device, String decibel, String temperature, String tag, Pageable pageable) {
        Page<Noise> pagedNoise = noiseRepository.findDynamicPagingQueryAdvance(device, decibel, temperature, tag, pageable);
        return NoisePagingResDTO.of(pagedNoise);
    }
}
