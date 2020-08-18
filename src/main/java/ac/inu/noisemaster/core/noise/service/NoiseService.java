package ac.inu.noisemaster.core.noise.service;

import ac.inu.noisemaster.core.noise.domain.model.Device;
import ac.inu.noisemaster.core.noise.domain.model.Noise;
import ac.inu.noisemaster.core.noise.domain.model.Place;
import ac.inu.noisemaster.core.noise.domain.repository.PlaceRepository;
import ac.inu.noisemaster.core.noise.domain.repository.device.DeviceRepository;
import ac.inu.noisemaster.core.noise.domain.repository.noise.NoiseRepository;
import ac.inu.noisemaster.core.noise.dto.device.DeviceRecentBundleResDTO;
import ac.inu.noisemaster.core.noise.dto.noise.NoisePagingResDTO;
import ac.inu.noisemaster.core.noise.dto.noise.NoiseResDTO;
import ac.inu.noisemaster.core.noise.dto.noise.NoiseSaveDTO;
import ac.inu.noisemaster.core.noise.dto.place.PlaceDTO;
import ac.inu.noisemaster.core.noise.dto.place.PlaceTagUpdateReqDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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

        //TODO 일단 이대로 두지만 개선해야함
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
        List<Device> devices = deviceRepository.findAll();
        List<Noise> recentNoises = new ArrayList<>();
        for (Device device : devices) {
            Optional<Noise> recentNoise = noiseRepository.findRecentNoiseByDeviceName(device.getName());
            recentNoise.ifPresent(recentNoises::add);
        }
        return DeviceRecentBundleResDTO.of(recentNoises);
    }

    @Transactional
    public PlaceDTO updateTag(PlaceTagUpdateReqDTO placeTagUpdateReqDTO) {
        Place place = placeRepository.findById(placeTagUpdateReqDTO.getId())
                .orElseThrow(() -> new NoSuchElementException(placeTagUpdateReqDTO.getId() + "은 존재하지 않는 ID 입니다."));
        place.updateTag(placeTagUpdateReqDTO);
        return PlaceDTO.from(place);
    }
}
