package ac.inu.noisemaster.web;

import ac.inu.noisemaster.core.noise.dto.NoisePagingResDTO;
import ac.inu.noisemaster.core.noise.dto.NoiseResDTO;
import ac.inu.noisemaster.core.noise.dto.NoiseSaveDTO;
import ac.inu.noisemaster.core.noise.service.NoiseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/noise")
@RequiredArgsConstructor
public class NoiseController {

    private final NoiseService noiseService;

    //날짜 검색조건 추가
    //데시벨은 입력한 데시벨의 10단위로 10~19 까지 나오게
    @GetMapping
    public ResponseEntity<NoisePagingResDTO> findNoiseAdvance(@RequestParam(required = false) String device,
                                                              @RequestParam(required = false) String decibel,
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

    //GET 디바이스별 가장 최근 정보 출력 ( 태그, 디바이스 아이디, 가장 최근 입력시간, 가장 최근 데시벨, 온도)
    @GetMapping("/top")
    public ResponseEntity<Object> find(){
        return null;
    }

    //tag 수정

}
