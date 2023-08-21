package com.basicex.sdk;

import com.basicex.sdk.model.params.InvoiceCreateParams;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class BasicExClientTest {
    @Test
    void basicExClientConfigTest() {
        BasicExClient client = new BasicExClient("asdfsafsadf");

        client.invoices().create(InvoiceCreateParams.builder()
                .amount(BigDecimal.TEN)
                .currency("USD").build())
    }
}
