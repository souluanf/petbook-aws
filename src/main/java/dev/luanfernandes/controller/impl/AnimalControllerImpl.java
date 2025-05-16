package dev.luanfernandes.controller.impl;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

import dev.luanfernandes.controller.AnimalController;
import dev.luanfernandes.domain.request.AnimalCreateRequest;
import dev.luanfernandes.domain.request.AnimalUpdateRequest;
import dev.luanfernandes.domain.response.AnimalResponse;
import dev.luanfernandes.domain.response.PageResponse;
import dev.luanfernandes.domain.response.PhotoDownload;
import dev.luanfernandes.service.impl.AnimalServiceImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class AnimalControllerImpl implements AnimalController {

    private final AnimalServiceImpl animalService;

    @Override
    public ResponseEntity<AnimalResponse> create(AnimalCreateRequest request) {
        return ok(animalService.create(request));
    }

    @Override
    public ResponseEntity<AnimalResponse> findByAnimalIdIndex(String id) {
        return ok(animalService.findById(id));
    }

    @Override
    public ResponseEntity<List<AnimalResponse>> findByPerson(String personId) {
        return ok(animalService.findByPessoaId(personId));
    }

    @Override
    public ResponseEntity<PageResponse<AnimalResponse>> findAll(int pageSize, String paginationKey) {
        return ok(animalService.findAll(pageSize, paginationKey));
    }

    @Override
    public ResponseEntity<AnimalResponse> update(String id, AnimalUpdateRequest request) {
        return ok(animalService.update(id, request));
    }

    @Override
    public ResponseEntity<Void> delete(String id) {
        animalService.delete(id);
        return noContent().build();
    }

    @Override
    public ResponseEntity<Void> uploadPhoto(String id, MultipartFile file) {
        animalService.uploadPhoto(id, file);
        return noContent().build();
    }

    @Override
    public ResponseEntity<byte[]> downloadPhoto(String id) {
        PhotoDownload photo = animalService.downloadPhoto(id);
        return ok().contentType(photo.contentType()).body(photo.content());
    }
}
