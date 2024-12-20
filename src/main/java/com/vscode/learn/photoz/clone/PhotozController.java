package com.vscode.learn.photoz.clone;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Collection;

@RestController
public class PhotozController {

    private final PhotosService photosService;

    public PhotozController(PhotosService photosService) {
        this.photosService = photosService;
    }
    // private List<Photo> db1 = List.of(new Photo("1", "hello.jpg"));

    @GetMapping("/")
    public String hello() {
        return "HELLO, MANISH";
    }

    @GetMapping("/photos")
    public Collection<Photo> get() {
        return photosService.get();
    }

    @GetMapping("/photos/{id}")
    public Photo getById(@PathVariable String id) {
        Photo photo = photosService.get(id);
        if (photo == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return photo;
    }

    @DeleteMapping("/photos/{id}")
    public void delete(@PathVariable String id) {
        Photo photo = photosService.remove(id);
        if (photo == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/photos")
    public Photo create(@RequestPart("data") MultipartFile file) throws IOException {
        Photo photo = photosService.save(file.getOriginalFilename(), file.getContentType(), file.getBytes());
        return photo;
    }
}
