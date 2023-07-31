package com.guidopierri.api.controller;
import com.guidopierri.api.model.BrownNoiseGenerator;
import com.guidopierri.api.model.WhiteNoiseGenerator;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
@CrossOrigin
@RestController
public class SoundController {

    @GetMapping(value = "/whitenoise", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> getWhiteNoise() throws IOException {
        String fileName = "white_noise.wav";
        WhiteNoiseGenerator.generateWhiteNoiseWav(fileName);

        Path path = Paths.get(fileName);
        byte[] fileBytes = Files.readAllBytes(path);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData(fileName, fileName);

        return new ResponseEntity<>(fileBytes, headers, HttpStatus.OK);
    }
        @GetMapping(value = "/brownnoise", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
        public ResponseEntity<byte[]> getBrownNoise() throws IOException {
            String fileName = "brown_noise.wav";
            BrownNoiseGenerator.generateBrownNoiseWav(fileName);

            Path path = Paths.get(fileName);
            byte[] fileBytes = Files.readAllBytes(path);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData(fileName, fileName);
            return new ResponseEntity<>(fileBytes, headers, HttpStatus.OK);
        }
    @GetMapping(value = "/brownnoise-4000hz", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> getBrownNoise4000Hz() throws IOException {
        String fileName = "brown_noise-4000Hz.wav";
        BrownNoiseGenerator.generateFilteredBrownNoiseWav(fileName);

        Path path = Paths.get(fileName);
        byte[] fileBytes = Files.readAllBytes(path);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData(fileName, fileName);
        return new ResponseEntity<>(fileBytes, headers, HttpStatus.OK);
    }
    }


