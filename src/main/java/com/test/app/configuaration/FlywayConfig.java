package com.test.app.configuaration;

import org.flywaydb.core.Flyway;

public class FlywayConfig {
    private static final String URL = "jdbc:mysql://localhost:3306/nhahang?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    public static void migrate() {
        Flyway flyway = Flyway.configure()
                .dataSource(URL, USER, PASSWORD)
                .load();

        // Thực hiện migration
        flyway.migrate();
    }
}
