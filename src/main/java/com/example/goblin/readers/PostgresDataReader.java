package com.example.goblin.readers;

import com.example.goblin.entity.Customer;
import com.example.goblin.entity.JobConfig;
import com.example.goblin.enums.SourceType;
import com.example.goblin.interfaces.DataReader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.stream.Stream;

@Component("POSTGRES")
public class PostgresDataReader implements DataReader<Customer> {

    private final JdbcTemplate jdbc;

    public PostgresDataReader(JdbcTemplate jdbcTemplate) {
        this.jdbc = jdbcTemplate;
    }

    @Override
    public Stream<Customer> read(JobConfig cfg) throws Exception {
        String schema = cfg.getSchemaName().trim();
        String table  = cfg.getSourceProperties().trim().replace("\"", "");
        String fullTable = "%s.%s".formatted(schema, table);

        String sql = """
            SELECT customer_id,
                   first_name,
                   last_name,
                   email,
                   phone,
                   city,
                   signup_date
              FROM %s
            """.formatted(fullTable);

        RowMapper<Customer> mapper = (ResultSet rs, int rowNum) -> {
            Customer c = new Customer();
            c.setCustomerId(rs.getString("customer_id"));
            c.setFirstName(rs.getString("first_name"));
            c.setLastName(rs.getString("last_name"));
            c.setEmail(rs.getString("email"));
            c.setPhone(rs.getString("phone"));
            c.setCity(rs.getString("city"));
            c.setSignupDate("signup_date");

            return c;
        };

        // fetch all rows and return as a Stream<Customer>
        return jdbc.query(sql, mapper).stream();
    }
}
