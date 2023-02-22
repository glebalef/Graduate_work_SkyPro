package ru.skypro.homework.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.service.AdsService;
import ru.skypro.homework.service.ImageService;

import java.io.IOException;

@RequestMapping("/image")
@CrossOrigin(value = "http://localhost:3000")
@RestController
public class ImageController {

    private final ImageService imageService;
    private final AdsService adsService;

    public ImageController(ImageService imageService,  AdsService adsService) {
        this.imageService = imageService;
        this.adsService = adsService;
    }
    @PatchMapping( value = "/{id}", consumes =  MediaType.MULTIPART_FORM_DATA_VALUE)
    public String updateImage (
            @RequestParam MultipartFile pic,
            @PathVariable Long id) throws IOException {
        imageService.uploadImage(id, pic);

        return pic.getName();
    }



}
