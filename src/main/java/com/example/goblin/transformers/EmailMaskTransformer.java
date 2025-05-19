package com.example.goblin.transformers;

import com.example.goblin.entity.Customer;
import com.example.goblin.entity.JobConfig;
import com.example.goblin.interfaces.DataTransformer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component("EMAIL_MASK")
public class EmailMaskTransformer
        implements DataTransformer<JsonNode, Customer> {

    private final ObjectMapper mapper;

    // Better to have Spring inject a shared ObjectMapper
    public EmailMaskTransformer(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Customer transform(JsonNode input, JobConfig cfg) throws Exception {
        // 1) Convert the JSON tree into a Customer POJO
        Customer original = mapper.treeToValue(input, Customer.class);

        // 2) Copy into a new instance so we don't mutate the original
        Customer copy = BeanUtils.instantiateClass(Customer.class);
        BeanUtils.copyProperties(original, copy);

        // 3) Mask the email
        if (copy.getEmail() != null) {
            copy.setEmail(maskEmail(copy.getEmail()));
        }

        return copy;
    }

    private String maskEmail(String email) {
        var parts = email.split("@", 2);
        String local = parts[0], domain = parts.length > 1 ? parts[1] : "";
        // show first 2 chars, then stars
        String stars = "*".repeat(Math.max(0, local.length() - 2));
        String prefix = local.substring(0, Math.min(2, local.length()));
        return prefix + stars + (domain.isEmpty() ? "" : "@" + domain);
    }
}
