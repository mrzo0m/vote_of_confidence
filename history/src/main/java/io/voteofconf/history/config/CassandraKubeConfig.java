package io.voteofconf.history.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

@Configuration
@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "cassandra")
public class CassandraKubeConfig implements Serializable {

    private static final long serialVersionUID = 1L;
    private String contactPoints;
    private int port;
    private String keyspace;
    private String basePackages;

}
