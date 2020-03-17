package com.sandrocaseiro.apitemplate.models;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DSearchReq {
    @NotNull
    private String searchContent;
}
