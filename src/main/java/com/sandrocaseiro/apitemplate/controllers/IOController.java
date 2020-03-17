package com.sandrocaseiro.apitemplate.controllers;

import com.sandrocaseiro.apitemplate.models.dto.file.DSaveFileReq;
import com.sandrocaseiro.apitemplate.models.io.IArquivoExtrato;
import com.sandrocaseiro.apitemplate.services.IOService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import java.io.IOException;

@RestController
@RequestScope
@RequiredArgsConstructor
public class IOController {
    private final IOService ioService;

    @GetMapping("/v1/io")
    public IArquivoExtrato read() {
        return ioService.readFile();
    }

    @PostMapping("/v1/io")
    @ResponseStatus(HttpStatus.CREATED)
    public void generate() throws IOException {
        IArquivoExtrato extrato = ioService.readFile();
        ioService.generateFile(extrato);
    }

    @PostMapping("/v1/io/new")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void newFile(DSaveFileReq file) {
        Assert.notNull(file.getAttachment(), "Uploaded");
    }
}
