package dev.luanfernandes.service;

import dev.luanfernandes.domain.request.PersonCreateRequest;
import dev.luanfernandes.domain.request.PersonUpdateRequest;
import dev.luanfernandes.domain.response.PageResponse;
import dev.luanfernandes.domain.response.PersonResponse;
import dev.luanfernandes.domain.response.PhotoDownload;
import org.springframework.web.multipart.MultipartFile;

public interface PersonService {
    PersonResponse create(PersonCreateRequest request);

    void uploadPhoto(String id, MultipartFile file);

    PhotoDownload downloadPhoto(String id);

    PersonResponse findById(String id);

    PageResponse<PersonResponse> findAll(int pageSize, String paginationKey);

    PersonResponse update(String id, PersonUpdateRequest request);

    void delete(String id);
}
