package dev.sandrocaseiro.apitemplate.clients;

import dev.sandrocaseiro.apitemplate.models.api.ATokenReq;
import dev.sandrocaseiro.apitemplate.models.api.ATokenResp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "auth", url = "${endpoints.api1.base-url}")
public interface AuthClient {
    @PostMapping(value = "/v1/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    ATokenResp token(ATokenReq req);
}
