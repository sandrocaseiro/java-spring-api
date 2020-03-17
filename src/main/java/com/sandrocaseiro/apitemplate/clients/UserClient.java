package com.sandrocaseiro.apitemplate.clients;

import com.sandrocaseiro.apitemplate.models.AResponse;
import com.sandrocaseiro.apitemplate.models.api.AUserByIdResp;
import com.sandrocaseiro.apitemplate.models.api.AUserUpdateReq;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user", url = "${endpoints.api1.base-url}")
public interface UserClient {

    @PatchMapping(value = "/v1/users/{id}/balance", consumes = MediaType.APPLICATION_JSON_VALUE)
    AResponse updateBalance(@PathVariable("id") int id, AUserUpdateReq user);

    @GetMapping(value = "/v1/users/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    AResponse<AUserByIdResp> getById(@PathVariable("id") int id);
}
