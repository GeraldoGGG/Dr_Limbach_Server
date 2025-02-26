package com.allMighty.business_logic_domain.image;

import com.allMighty.client.UrlProperty;
import com.allMighty.enumeration.EntityType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(UrlProperty.Article.PATH)
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;


   /*  @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImage(
            @PathVariable Long id,
            @RequestParam("type") EntityType type) {

       // Retrieve the image entity based on the id and enum parameter.
        List<ImageDTO> image = imageService.getImages(id, type);

        if (image == null || image.getImageData() == null) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();
        // Set the content type (change if your images are in a different format)
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentLength(image.getImageData().length);

        return new ResponseEntity<>(image.getImageData(), headers, HttpStatus.OK);
    }*/
}
