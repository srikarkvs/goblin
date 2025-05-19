package com.example.goblin.sinks;

import com.example.goblin.entity.Customer;
import com.example.goblin.entity.JobConfig;
import com.example.goblin.interfaces.DataSink;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component("POSTGRES_SINK")
public class PostgresDataSink implements DataSink<Object> {

    private final JdbcTemplate jdbc;
    private final ObjectMapper mapper;

    public PostgresDataSink(JdbcTemplate jdbcTemplate,
                            ObjectMapper objectMapper) {
        this.jdbc   = jdbcTemplate;
        this.mapper = objectMapper;
    }

    @Override
    public void write(Stream<Object> data, JobConfig cfg) throws Exception {
        String schema    = cfg.getSchemaName().trim();
        String table     = cfg.getSinkProperties().trim().replace("\"", "");
        String fullTable = schema + "." + table;

        if(schema.equals("CUSTOMER")){
        String sql = """
            INSERT INTO %s
              (customer_id, first_name, last_name, email, phone, city, signup_date)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """.formatted(fullTable);

        data.forEach(raw -> {
            // 1) Convert whatever it is into Customer
            Customer cust = convertToCustomer(raw);

            // 2) Insert into Postgres
            jdbc.update(sql,
                    cust.getCustomerId(),
                    cust.getFirstName(),
                    cust.getLastName(),
                    cust.getEmail(),
                    cust.getPhone(),
                    cust.getCity(),
                    cust.getSignupDate()
            );
        });
    }}

    private Customer convertToCustomer(Object raw) {
        if (raw instanceof Customer) {
            return (Customer) raw;
        }
        // if it's a JsonNode or Map, mapper can handle it
        return mapper.convertValue(raw, Customer.class);
    }
}
