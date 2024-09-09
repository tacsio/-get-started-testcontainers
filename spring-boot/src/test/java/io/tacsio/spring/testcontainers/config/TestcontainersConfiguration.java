package io.tacsio.spring.testcontainers.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.MSSQLServerContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration
public class TestcontainersConfiguration {

    @Bean
    @ServiceConnection
    MSSQLServerContainer<?> sqlServerContainer() {
        var container = new MSSQLServerContainer<>(DockerImageName.parse("mcr.microsoft.com/mssql/server:latest"));
        container.acceptLicense();

        return container;
    }

}
