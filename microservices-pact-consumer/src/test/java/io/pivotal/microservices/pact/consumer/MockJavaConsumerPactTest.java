package io.pivotal.microservices.pact.consumer;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Rule;

import au.com.dius.pact.consumer.ConsumerPactTestMk2;
import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;

public class MockJavaConsumerPactTest extends ConsumerPactTestMk2 {
	@Rule
    public PactProviderRuleMk2 mockProvider = new PactProviderRuleMk2("test_provider", "localhost", 8080, this);

	@Override
    protected RequestResponsePact createPact(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("testreqheader", "testreqheadervalue");

        return builder
            .given("test state") // NOTE: Using provider states are optional, you can leave it out
            .uponReceiving("ExampleJavaConsumerPactTest test interaction")
                .path("/")
                .method("GET")
                .headers(headers)
            .willRespondWith()
                .status(200)
                .headers(headers)
                .body("{\"responsetest\": true, \"name\": \"harry\"}")
            .given("test state 2") // NOTE: Using provider states are optional, you can leave it out
            .uponReceiving("ExampleJavaConsumerPactTest second test interaction")
                .method("OPTIONS")
                .headers(headers)
                .path("/second")
                .body("")
            .willRespondWith()
                .status(200)
                .headers(headers)
                .body("")
                .given("test state") // NOTE: Using provider states are optional, you can leave it out
                .uponReceiving("DXC interaction")
                    .path("/")
                    .method("GET")
                    .headers(headers)
                .willRespondWith()
                    .status(200)
                    .headers(headers)
                    .body("{\"Number\": 123456789, \"Owner\": \"Keri Lee\",\"Balance\": 64531.76}")
            .toPact();
    }


    @Override
    protected String providerName() {
        return "test_provider";
    }

    @Override
    protected String consumerName() {
        return "test_consumer";
    }

    @Override
    protected void runTest(MockServer mockServer) throws IOException {
        Assert.assertEquals(new MockConsumerClient(mockServer.getUrl()).options("/second"), 200);
        Map expectedResponse = new HashMap();
        expectedResponse.put("responsetest", true);
        expectedResponse.put("name", "harry");
        assertEquals(new MockConsumerClient(mockServer.getUrl()).getAsMap("/", ""), expectedResponse);
        assertEquals(new MockConsumerClient(mockServer.getUrl()).options("/second"), 200);
    }

}