package com.sandrocaseiro.apitemplate.models.dto.file;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Data
public class DSaveFileReq {
    private int id;
    private String name;
    private MultipartFile attachment;
}
