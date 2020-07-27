package dev.sandrocaseiro.apitemplate;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

@Component
public class MockServer {
    public final WireMockServer mockServer;

    public MockServer() {
        mockServer = new WireMockServer(
            options()
                .port(8089)
                .usingFilesUnderClasspath("mocks")
        );
        mockServer.start();
        configureFor(mockServer.port());
    }

    public void reset() {
        mockServer.resetMappings();
    }

    @PreDestroy
    public void dispose() {
        mockServer.shutdownServer();
        while (mockServer.isRunning()) {
            try {
                Thread.sleep(100L);
            }
            catch (InterruptedException e) {

            }
        }
    }
}
