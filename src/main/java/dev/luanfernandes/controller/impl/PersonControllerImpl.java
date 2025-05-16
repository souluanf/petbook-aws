package dev.luanfernandes.controller.impl;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

import dev.luanfernandes.controller.PersonController;
import dev.luanfernandes.domain.request.PersonCreateRequest;
import dev.luanfernandes.domain.request.PersonUpdateRequest;
import dev.luanfernandes.domain.response.PageResponse;
import dev.luanfernandes.domain.response.PersonResponse;
import dev.luanfernandes.domain.response.PhotoDownload;
import dev.luanfernandes.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class PersonControllerImpl implements PersonController {

    private final PersonService pessoaService;

    @Override
    public ResponseEntity<PersonResponse> create(PersonCreateRequest request) {
        return ResponseEntity.ok(pessoaService.create(request));
    }

    @Override
    public ResponseEntity<PersonResponse> findById(String id) {
        return ResponseEntity.ok(pessoaService.findById(id));
    }

    @Override
    public ResponseEntity<PageResponse<PersonResponse>> findAll(int pageSize, String paginationKey) {
        return ResponseEntity.ok(pessoaService.findAll(pageSize, paginationKey));
    }

    @Override
    public ResponseEntity<Void> update(String id, PersonUpdateRequest request) {
        pessoaService.update(id, request);
        return noContent().build();
    }

    @Override
    public ResponseEntity<Void> delete(String id) {
        pessoaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> uploadPhoto(String id, MultipartFile file) {
        pessoaService.uploadPhoto(id, file);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<byte[]> downloadPhoto(String id) {
        PhotoDownload photo = pessoaService.downloadPhoto(id);
        return ok().contentType(photo.contentType()).body(photo.content());
    }
}
