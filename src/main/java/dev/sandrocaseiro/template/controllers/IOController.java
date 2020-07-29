package dev.sandrocaseiro.template.controllers;

import dev.sandrocaseiro.template.models.DResponse;
import dev.sandrocaseiro.template.models.dto.file.DSaveFileReq;
import dev.sandrocaseiro.template.models.io.IArquivoExtrato;
import dev.sandrocaseiro.template.services.IOService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Tag(name = "IO", description = "IO Operations")
public class IOController {
    private final IOService ioService;

    @GetMapping("/v1/io")
    @Operation(summary = "Read a CNAB file", description = "Read a CNAB file and return it's contents", responses = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(schema = @Schema(implementation = DResponse.class))})
    })
    public IArquivoExtrato read() {
        return ioService.readFile();
    }

    @PostMapping("/v1/io")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Generate a CNAB file", description = "Generate a new CNAB file, based on an example file", responses = {
        @ApiResponse(responseCode = "201", description = "Created", content = {@Content(schema = @Schema(implementation = DResponse.class))}),
        @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(schema = @Schema(implementation = DResponse.class))})
    })
    public void generate() throws IOException {
        IArquivoExtrato extrato = ioService.readFile();
        ioService.generateFile(extrato);
    }

    @PostMapping(value = "/v1/io/new", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "File upload", description = "Upload a file", responses = {
        @ApiResponse(responseCode = "204", description = "No content", content = {@Content(schema = @Schema(implementation = DResponse.class))}),
        @ApiResponse(responseCode = "500", description = "Server error", content = {@Content(schema = @Schema(implementation = DResponse.class))})
    })
    public void newFile(DSaveFileReq file) {
        Assert.notNull(file.getAttachment(), "Uploaded");
    }
}
