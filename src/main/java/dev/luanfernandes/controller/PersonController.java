package dev.luanfernandes.controller;

import static dev.luanfernandes.domain.constants.PathConstants.PERSON_CREATE;
import static dev.luanfernandes.domain.constants.PathConstants.PERSON_DELETE;
import static dev.luanfernandes.domain.constants.PathConstants.PERSON_DOWNLOAD_PHOTO;
import static dev.luanfernandes.domain.constants.PathConstants.PERSON_FIND_ALL;
import static dev.luanfernandes.domain.constants.PathConstants.PERSON_FIND_BY_ID;
import static dev.luanfernandes.domain.constants.PathConstants.PERSON_UPDATE;
import static dev.luanfernandes.domain.constants.PathConstants.PERSON_UPLOAD_PHOTO;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON_VALUE;
import static org.springframework.util.MimeTypeUtils.IMAGE_JPEG_VALUE;

import dev.luanfernandes.domain.request.PersonCreateRequest;
import dev.luanfernandes.domain.request.PersonUpdateRequest;
import dev.luanfernandes.domain.response.PageResponse;
import dev.luanfernandes.domain.response.PersonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
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
public interface PersonController {

    @Operation(
            summary = "Create a new person",
            tags = {"Person"},
            responses = {
                @ApiResponse(
                        responseCode = "201",
                        description = "Created",
                        content =
                                @Content(
                                        mediaType = APPLICATION_JSON_VALUE,
                                        schema = @Schema(implementation = PersonResponse.class)),
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
                        responseCode = "404",
                        description = "Not Found",
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
                        responseCode = "422",
                        description = "Unprocessable Entity",
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
                        responseCode = "429",
                        description = "Too Many Requests",
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
    @PostMapping(PERSON_CREATE)
    ResponseEntity<PersonResponse> create(@RequestBody @Valid PersonCreateRequest request);

    @Operation(
            summary = "Find a person by ID",
            tags = {"Person"},
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Person found",
                        content =
                                @Content(
                                        mediaType = APPLICATION_JSON_VALUE,
                                        schema = @Schema(implementation = PersonResponse.class)),
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
                        description = "Person not found",
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
    @GetMapping(PERSON_FIND_BY_ID)
    ResponseEntity<PersonResponse> findById(@PathVariable String id);

    @Operation(
            summary = "Find all persons with pagination",
            tags = {"Person"},
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Paginated list of persons",
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
                                                        example = APPLICATION_JSON_VALUE))),
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
    @GetMapping(PERSON_FIND_ALL)
    ResponseEntity<PageResponse<PersonResponse>> findAll(
            @RequestParam(defaultValue = "10") int pageSize, @RequestParam(required = false) String paginationKey);

    @Operation(
            summary = "Update a person",
            tags = {"Person"},
            responses = {
                @ApiResponse(
                        responseCode = "204",
                        description = "Person updated successfully",
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
                        responseCode = "404",
                        description = "Person not found",
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
                        responseCode = "422",
                        description = "Unprocessable Entity",
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
    @PutMapping(PERSON_UPDATE)
    ResponseEntity<Void> update(@PathVariable String id, @RequestBody @Valid PersonUpdateRequest request);

    @Operation(
            summary = "Delete a person by ID",
            tags = {"Person"},
            responses = {
                @ApiResponse(responseCode = "204", description = "Person deleted successfully"),
                @ApiResponse(
                        responseCode = "404",
                        description = "Person not found",
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
    @DeleteMapping(PERSON_DELETE)
    ResponseEntity<Void> delete(@PathVariable String id);

    @Operation(
            summary = "Upload a photo for a person",
            tags = {"Person"},
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Photo uploaded successfully",
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
                        description = "Bad Request - Invalid file format",
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
                        responseCode = "404",
                        description = "Person not found",
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
                        responseCode = "422",
                        description = "Unprocessable Entity",
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
                        responseCode = "429",
                        description = "Too Many Requests",
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
    @PutMapping(PERSON_UPLOAD_PHOTO)
    ResponseEntity<Void> uploadPhoto(@PathVariable String id, @RequestParam MultipartFile file);

    @Operation(
            summary = "Download a photo of a person",
            tags = {"Person"},
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Photo downloaded successfully (JPEG)",
                        content =
                                @Content(
                                        mediaType = IMAGE_JPEG_VALUE,
                                        schema = @Schema(type = "string", format = "binary")),
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
                                                        example = IMAGE_JPEG_VALUE))),
                @ApiResponse(
                        responseCode = "404",
                        description = "Person or photo not found",
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
                                                        format = "text",
                                                        example = APPLICATION_PROBLEM_JSON_VALUE))),
                @ApiResponse(
                        responseCode = "404",
                        description = "Person or photo not found",
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
                                                        format = "text",
                                                        example = APPLICATION_PROBLEM_JSON_VALUE)))
            })
    @GetMapping(PERSON_DOWNLOAD_PHOTO)
    ResponseEntity<byte[]> downloadPhoto(@PathVariable String id);
}
