package ac.inu.noisemaster.core.noise.service;

import ac.inu.noisemaster.core.noise.domain.Device;
import ac.inu.noisemaster.core.noise.domain.Noise;
import ac.inu.noisemaster.core.noise.domain.Place;
import ac.inu.noisemaster.core.noise.dto.device.DeviceRecentBundleResDTO;
import ac.inu.noisemaster.core.noise.dto.noise.NoisePagingResDTO;
import ac.inu.noisemaster.core.noise.dto.noise.NoiseResDTO;
import ac.inu.noisemaster.core.noise.dto.noise.NoiseSaveDTO;
import ac.inu.noisemaster.core.noise.repository.PlaceRepository;
import ac.inu.noisemaster.core.noise.repository.device.DeviceRepository;
import ac.inu.noisemaster.core.noise.repository.noise.NoiseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NoiseService {

    private final NoiseRepository noiseRepository;
    private final DeviceRepository deviceRepository;
    private final PlaceRepository placeRepository;

    @Transactional
    public NoiseResDTO save(NoiseSaveDTO noiseSaveDTO) {
        Device device = noiseSaveDTO.toDeviceEntity();
        Device finalDevice = device;
        device = deviceRepository.findAll().stream()
                .filter(device::isSame)
                .findFirst()
                .orElseGet(() -> deviceRepository.save(finalDevice));

        Place place = noiseSaveDTO.toPlaceEntity();
        Place finalPlace = place;
        place = placeRepository.findAll().stream()
                .filter(place::isSameGrid)
                .findFirst()
                .orElseGet(() -> placeRepository.save(finalPlace));

        Noise noise = noiseRepository.save(noiseSaveDTO.toNoiseEntity(device, place));
        return NoiseResDTO.from(noise);
    }

    @Transactional(readOnly = true)
    public NoisePagingResDTO findNoiseAdvance(String device, Double decibel, String temperature, String tag, String date, Pageable pageable) {
        Page<Noise> pagedNoise = noiseRepository.findDynamicPagingQueryAdvance(device, decibel, temperature, tag, date, pageable);
        return NoisePagingResDTO.of(pagedNoise);
    }

    @Transactional(readOnly = true)
    public DeviceRecentBundleResDTO findRecentNoises() {
        return DeviceRecentBundleResDTO.of(deviceRepository.findRecentDevice());
    }
}
