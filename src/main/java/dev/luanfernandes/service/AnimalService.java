package dev.luanfernandes.service;

import dev.luanfernandes.domain.entity.Animal;
import dev.luanfernandes.domain.request.AnimalCreateRequest;
import dev.luanfernandes.domain.request.AnimalUpdateRequest;
import dev.luanfernandes.domain.response.AnimalResponse;
import dev.luanfernandes.domain.response.PageResponse;
import dev.luanfernandes.domain.response.PhotoDownload;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface AnimalService {
    AnimalResponse create(AnimalCreateRequest request);

    void uploadPhoto(String id, MultipartFile file);

    PhotoDownload downloadPhoto(String id);

    PageResponse<AnimalResponse> findAll(int pageSize, String paginationKey);

    Animal findByAnimalId(String animalId);

    AnimalResponse findById(String animalId);

    List<AnimalResponse> findByPessoaId(String pessoaId);

    AnimalResponse update(String animalId, AnimalUpdateRequest request);

    void delete(String animalId);
}
