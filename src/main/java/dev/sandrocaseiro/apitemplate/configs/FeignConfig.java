package dev.sandrocaseiro.apitemplate.configs;

import feign.Client;
import feign.Feign;
import feign.Request;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

public class FeignConfig {
    @Value("${feign.connectTimeout}")
    private int connectTimeout;
    @Value("${feign.readTimeout}")
    private int readTimeout;
    @Value("${feign.followRedirects}")
    private boolean followRedirects;
    @Value("${feign.ignore-ssl}")
    private boolean ignoreSSL;

    @Bean
    public Feign.Builder feignBuilder() {
        return Feign.builder()
            .options(options());
    }

    public Request.Options options() {
        return new Request.Options(
            connectTimeout,
            TimeUnit.MILLISECONDS,
            readTimeout,
            TimeUnit.MILLISECONDS,
            followRedirects
        );
    }

    @Bean
    public Client client() throws NoSuchAlgorithmException, KeyManagementException {
        return new feign.okhttp.OkHttpClient(httpClient());
    }

    public OkHttpClient httpClient() throws KeyManagementException, NoSuchAlgorithmException {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
            .followSslRedirects(followRedirects)
            .followRedirects(followRedirects)
            .retryOnConnectionFailure(false);

        if (ignoreSSL)
            ignoreSSLCetificates(builder);

        return builder.build();
    }

    @Bean
    public Encoder feignEncoder() {
        return new SpringFormEncoder(new JacksonEncoder());
    }

    @Bean
    public Decoder feignDecoder() {
        return new JacksonDecoder(WebConfig.getJsonMapper());
    }

    private void ignoreSSLCetificates(okhttp3.OkHttpClient.Builder builder) throws NoSuchAlgorithmException, KeyManagementException {
        final TrustManager[] trustAllCerts = new TrustManager[] {
            new X509TrustManager() {
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[]{};
                }
            }
        };

        final SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
        builder
            .sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager)trustAllCerts[0])
            .hostnameVerifier((hostname, sslSession) -> true);
    }
}
