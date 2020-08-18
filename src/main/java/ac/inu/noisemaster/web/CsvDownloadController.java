package ac.inu.noisemaster.web;

import ac.inu.noisemaster.core.noise.dto.csv.CsvLogDto;
import ac.inu.noisemaster.core.noise.service.CsvService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/csv")
public class CsvDownloadController {

    private final CsvService csvService;

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadCsv(@RequestParam("csvPath") String csvPath) throws IOException {
        File target = new File(csvPath);
        HttpHeaders header = new HttpHeaders();
        Resource rs = null;
        if (target.exists()) {
            String mimeType = Files.probeContentType(Paths.get(target.getAbsolutePath()));
            if (mimeType == null) {
                mimeType = "text/csv";
            }
            rs = new UrlResource(target.toURI());
            header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename =\"" + rs.getFilename() + "\"");
            header.setCacheControl("no-cache");
            header.setContentType(MediaType.parseMediaType(mimeType));
        }
        return new ResponseEntity<>(rs, header, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CsvLogDto>> getListOfCsv() {
        return ResponseEntity.ok(csvService.getList());
    }
}
