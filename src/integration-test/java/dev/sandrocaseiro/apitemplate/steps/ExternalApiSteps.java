package dev.sandrocaseiro.apitemplate.steps;

import io.cucumber.java8.En;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class ExternalApiSteps extends BaseSteps implements En {
    public ExternalApiSteps() {
        Before(() -> mockServer.reset());

        Given("External API is not working", this::stubNotWorking);

        Given("External API is working", this::stubIsWorking);
    }

    public void stubNotWorking() {
        stubFor(
          any(urlPathMatching("/api/.*"))
            .willReturn(
                aResponse()
                    .withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .withFixedDelay(5000)
            )
        );
    }

    public void stubIsWorking() {
        stubFor(
            post(urlPathMatching("/api/.*/token$"))
                .atPriority(1)
                .withHeader(HttpHeaders.CONTENT_TYPE, containing(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .willReturn(
                    aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withBodyFile("/auth-token.json")
                )
        );

        stubFor(
            patch(urlPathMatching("/api/.*/1/balance$"))
                .atPriority(1)
                .willReturn(
                    aResponse()
                        .withStatus(HttpStatus.NO_CONTENT.value())
                )
        );

        stubFor(
            get(urlPathMatching("/api/.*/[1-4]$"))
                .atPriority(1)
                .willReturn(
                    aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withBodyFile("/get-user.json")
                )
        );

        stubFor(
            any(urlPathMatching("/api/.*"))
                .atPriority(99)
                .willReturn(
                    aResponse()
                        .withStatus(HttpStatus.NOT_FOUND.value())
                )
        );
    }
}
