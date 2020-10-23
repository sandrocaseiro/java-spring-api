package dev.sandrocaseiro.template;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.common.Slf4jNotifier;
import dev.sandrocaseiro.template.steps.ExternalApiSteps;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

@Component
@Log4j2
public class MockServer {
    public final WireMockServer mockServer;
    @Autowired
    public Environment env;

    public MockServer() {
        mockServer = new WireMockServer(
            options()
                .port(8089)
                .usingFilesUnderClasspath("mocks")
                .notifier(new Slf4jNotifier(true))
        );
        mockServer.start();
        configureFor(mockServer.port());
        log.info("Started Wiremock");
    }

    public void reset() {
        mockServer.resetMappings();
    }

    @PostConstruct
    public void init() {
        if (!"true".equals(env.getProperty("isTest"))) {
            ExternalApiSteps.stubIsWorking();
        }
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
