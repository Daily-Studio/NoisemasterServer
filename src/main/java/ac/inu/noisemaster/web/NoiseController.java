package ac.inu.noisemaster.web;

import ac.inu.noisemaster.core.noise.dto.device.DeviceRecentBundleResDTO;
import ac.inu.noisemaster.core.noise.dto.noise.NoisePagingResDTO;
import ac.inu.noisemaster.core.noise.dto.noise.NoiseResDTO;
import ac.inu.noisemaster.core.noise.dto.noise.NoiseSaveDTO;
import ac.inu.noisemaster.core.noise.dto.place.PlaceDTO;
import ac.inu.noisemaster.core.noise.dto.place.PlaceTagUpdateReqDTO;
import ac.inu.noisemaster.core.noise.service.NoiseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/noise")
@RequiredArgsConstructor
public class NoiseController {

    private final NoiseService noiseService;

    @GetMapping
    public ResponseEntity<NoisePagingResDTO> findNoiseAdvance(@RequestParam(required = false) String device,
                                                              @RequestParam(required = false) Double decibel,
                                                              @RequestParam(required = false) String temperature,
                                                              @RequestParam(required = false) String tag,
                                                              @RequestParam(required = false) String date,
                                                              @RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "10") int size) {
        return new ResponseEntity<>(noiseService.findNoiseAdvance(device, decibel, temperature, tag, date, PageRequest.of(page, size)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<NoiseResDTO> saveNoise(@RequestBody NoiseSaveDTO noiseSaveDTO) {
        return new ResponseEntity<>(noiseService.save(noiseSaveDTO), HttpStatus.CREATED);
    }

    @GetMapping("/recent")
    public ResponseEntity<DeviceRecentBundleResDTO> findRecentNoises() {
        return new ResponseEntity<>(noiseService.findRecentNoises(), HttpStatus.OK);
    }

    //TODO tag 목록 보기

    @PutMapping("/place/tag")
    public ResponseEntity<PlaceDTO> updateTag(@RequestBody @Valid PlaceTagUpdateReqDTO placeTagUpdateReqDTO) {
        return new ResponseEntity<>(noiseService.updateTag(placeTagUpdateReqDTO), HttpStatus.OK);
    }

}
