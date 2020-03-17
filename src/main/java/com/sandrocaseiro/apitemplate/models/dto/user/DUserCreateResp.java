package com.sandrocaseiro.apitemplate.models.dto.user;

import lombok.Data;

import java.util.List;

@Data
public class DUserCreateResp {
    private int id;
    private String name;
    private String email;
    private int groupId;
    private List<Integer> roles;
}
