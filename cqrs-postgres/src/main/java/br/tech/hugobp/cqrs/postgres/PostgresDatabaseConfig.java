package br.tech.hugobp.cqrs.postgres;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostgresDatabaseConfig {
    private String host;
    private Short port;
    private String database;
    private String username;
    private String password;
}
