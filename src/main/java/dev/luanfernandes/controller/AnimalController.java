package dev.luanfernandes.controller;

import static dev.luanfernandes.domain.constants.PathConstants.ANIMAL_CREATE;
import static dev.luanfernandes.domain.constants.PathConstants.ANIMAL_DELETE;
import static dev.luanfernandes.domain.constants.PathConstants.ANIMAL_DOWNLOAD_PHOTO;
import static dev.luanfernandes.domain.constants.PathConstants.ANIMAL_FIND_ALL;
import static dev.luanfernandes.domain.constants.PathConstants.ANIMAL_FIND_BY_ID;
import static dev.luanfernandes.domain.constants.PathConstants.ANIMAL_FIND_BY_PERSON;
import static dev.luanfernandes.domain.constants.PathConstants.ANIMAL_UPDATE;
import static dev.luanfernandes.domain.constants.PathConstants.ANIMAL_UPLOAD_PHOTO;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON_VALUE;

import dev.luanfernandes.domain.request.AnimalCreateRequest;
import dev.luanfernandes.domain.request.AnimalUpdateRequest;
import dev.luanfernandes.domain.response.AnimalResponse;
import dev.luanfernandes.domain.response.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping(produces = {APPLICATION_JSON_VALUE, APPLICATION_PROBLEM_JSON_VALUE})
public interface AnimalController {

    @Operation(
            summary = "Create a new animal",
            tags = {"Animal"},
            responses = {
                @ApiResponse(
                        responseCode = "201",
                        description = "Animal created",
                        content =
                                @Content(
                                        mediaType = APPLICATION_JSON_VALUE,
                                        schema = @Schema(implementation = AnimalResponse.class)),
                        headers =
                                @Header(
                                        name = "Content-Type",
                                        description = "Response content type",
                                        schema =
                                                @Schema(
                                                        type = "string",
                                                        maxLength = 50,
                                                        pattern = ".*",
                                                        format = "text",
                                                        example = APPLICATION_JSON_VALUE))),
                @ApiResponse(
                        responseCode = "400",
                        description = "Bad Request",
                        content =
                                @Content(
                                        mediaType = APPLICATION_PROBLEM_JSON_VALUE,
                                        schema = @Schema(implementation = ProblemDetail.class)),
                        headers =
                                @Header(
                                        name = "Content-Type",
                                        description = "Response content type",
                                        schema =
                                                @Schema(
                                                        type = "string",
                                                        maxLength = 50,
                                                        pattern = ".*",
                                                        format = "text",
                                                        example = APPLICATION_PROBLEM_JSON_VALUE))),
                @ApiResponse(
                        responseCode = "500",
                        description = "Internal Server Error",
                        content =
                                @Content(
                                        mediaType = APPLICATION_PROBLEM_JSON_VALUE,
                                        schema = @Schema(implementation = ProblemDetail.class)),
                        headers =
                                @Header(
                                        name = "Content-Type",
                                        description = "Response content type",
                                        schema =
                                                @Schema(
                                                        type = "string",
                                                        maxLength = 50,
                                                        pattern = ".*",
                                                        format = "text",
                                                        example = APPLICATION_PROBLEM_JSON_VALUE)))
            })
    @PostMapping(ANIMAL_CREATE)
    ResponseEntity<AnimalResponse> create(@RequestBody @Valid AnimalCreateRequest request);

    @Operation(
            summary = "Find an animal by ID",
            tags = {"Animal"},
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Animal found",
                        content =
                                @Content(
                                        mediaType = APPLICATION_JSON_VALUE,
                                        schema = @Schema(implementation = AnimalResponse.class)),
                        headers =
                                @Header(
                                        name = "Content-Type",
                                        description = "Response content type",
                                        schema =
                                                @Schema(
                                                        type = "string",
                                                        maxLength = 50,
                                                        pattern = ".*",
                                                        format = "text",
                                                        example = APPLICATION_JSON_VALUE))),
                @ApiResponse(
                        responseCode = "404",
                        description = "Animal not found",
                        content =
                                @Content(
                                        mediaType = APPLICATION_PROBLEM_JSON_VALUE,
                                        schema = @Schema(implementation = ProblemDetail.class)),
                        headers =
                                @Header(
                                        name = "Content-Type",
                                        description = "Response content type",
                                        schema =
                                                @Schema(
                                                        type = "string",
                                                        maxLength = 50,
                                                        pattern = ".*",
                                                        format = "text",
                                                        example = APPLICATION_PROBLEM_JSON_VALUE)))
            })
    @GetMapping(ANIMAL_FIND_BY_ID)
    ResponseEntity<AnimalResponse> findByAnimalIdIndex(@PathVariable String id);

    @Operation(
            summary = "Find animals by person ID",
            tags = {"Animal"},
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "List of animals found",
                        content =
                                @Content(
                                        mediaType = APPLICATION_JSON_VALUE,
                                        schema = @Schema(implementation = AnimalResponse.class)),
                        headers =
                                @Header(
                                        name = "Content-Type",
                                        description = "Response content type",
                                        schema =
                                                @Schema(
                                                        type = "string",
                                                        maxLength = 50,
                                                        pattern = ".*",
                                                        format = "text",
                                                        example = APPLICATION_JSON_VALUE)))
            })
    @GetMapping(ANIMAL_FIND_BY_PERSON)
    ResponseEntity<List<AnimalResponse>> findByPerson(@PathVariable String personId);

    @Operation(
            summary = "Find all animals with pagination",
            tags = {"Animal"},
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Paginated list of animals",
                        content =
                                @Content(
                                        mediaType = APPLICATION_JSON_VALUE,
                                        schema = @Schema(implementation = PageResponse.class)),
                        headers =
                                @Header(
                                        name = "Content-Type",
                                        description = "Response content type",
                                        schema =
                                                @Schema(
                                                        type = "string",
                                                        maxLength = 50,
                                                        pattern = ".*",
                                                        format = "text",
                                                        example = APPLICATION_JSON_VALUE)))
            })
    @GetMapping(ANIMAL_FIND_ALL)
    ResponseEntity<PageResponse<AnimalResponse>> findAll(
            @RequestParam(defaultValue = "10") int pageSize, @RequestParam(required = false) String paginationKey);

    @Operation(
            summary = "Update an animal",
            tags = {"Animal"},
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Animal updated",
                        content =
                                @Content(
                                        mediaType = APPLICATION_JSON_VALUE,
                                        schema = @Schema(implementation = AnimalResponse.class)),
                        headers =
                                @Header(
                                        name = "Content-Type",
                                        description = "Response content type",
                                        schema =
                                                @Schema(
                                                        type = "string",
                                                        maxLength = 50,
                                                        pattern = ".*",
                                                        format = "text",
                                                        example = APPLICATION_JSON_VALUE)))
            })
    @PutMapping(ANIMAL_UPDATE)
    ResponseEntity<AnimalResponse> update(@PathVariable String id, @RequestBody @Valid AnimalUpdateRequest request);

    @Operation(
            summary = "Delete an animal by ID",
            tags = {"Animal"},
            responses = {@ApiResponse(responseCode = "204", description = "Animal deleted")})
    @DeleteMapping(ANIMAL_DELETE)
    ResponseEntity<Void> delete(@PathVariable String id);

    @Operation(
            summary = "Upload a photo for an animal",
            tags = {"Animal"},
            requestBody =
                    @io.swagger.v3.oas.annotations.parameters.RequestBody(
                            description = "Image file (JPEG or PNG)",
                            required = true,
                            content = {
                                @Content(
                                        mediaType = "image/jpeg",
                                        schema = @Schema(type = "string", format = "binary")),
                                @Content(mediaType = "image/png", schema = @Schema(type = "string", format = "binary"))
                            }),
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Photo uploaded successfully",
                        content =
                                @Content(
                                        mediaType = APPLICATION_JSON_VALUE,
                                        schema = @Schema(type = "string", example = "Success")),
                        headers =
                                @Header(
                                        name = "Content-Type",
                                        description = "Response content type",
                                        schema =
                                                @Schema(
                                                        type = "string",
                                                        maxLength = 50,
                                                        format = "text",
                                                        example = APPLICATION_JSON_VALUE)))
            })
    @PutMapping(ANIMAL_UPLOAD_PHOTO)
    ResponseEntity<Void> uploadPhoto(@PathVariable String id, @RequestParam MultipartFile file);

    @Operation(
            summary = "Download a photo of an animal",
            tags = {"Animal"},
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Photo downloaded successfully (JPEG or PNG)",
                        content = {
                            @Content(mediaType = "image/jpeg", schema = @Schema(type = "string", format = "binary")),
                            @Content(mediaType = "image/png", schema = @Schema(type = "string", format = "binary"))
                        },
                        headers =
                                @Header(
                                        name = "Content-Type",
                                        description = "Response content type",
                                        schema =
                                                @Schema(
                                                        type = "string",
                                                        maxLength = 50,
                                                        format = "text",
                                                        example = "image/jpeg")))
            })
    @GetMapping(ANIMAL_DOWNLOAD_PHOTO)
    ResponseEntity<byte[]> downloadPhoto(@PathVariable String id);
}
