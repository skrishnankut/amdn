package io.pivotal.microservices.pact.consumer;

import au.com.dius.pact.consumer.*;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.PactFragment;
import org.junit.Rule;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
/**
 * PACT TestCase
 * 
 * @author skrishnankut
 */
public class ConsumerPortTest {

    @Rule
    public PactProviderRule rule = new PactProviderRule("Service_Provider_ATT", this);

    @Pact(provider="Service_Provider_ATT", consumer="Service_Consumer_DXC")
    public PactFragment createFragment(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json;charset=UTF-8");

        return builder.uponReceiving("A request for Provider Service")
                .path("/userService")
                .method("GET")
                .willRespondWith()
                .headers(headers)
                .status(200)
                .body("[{\"value\":42}, {\"value\":100}]").toFragment();
             
    }

    @Test
    @PactVerification("Service_Provider_ATT")
    public void runTest() {
    	System.out.println("Inside @PactVerification :"+rule.getConfig().url());
    	System.out.println("Inside @PactVerification ::"+new ConsumerPort(rule.getConfig().url()).users());
        assertEquals(new ConsumerPort(rule.getConfig().url()).users(), Arrays.asList(new User(42), new User(100)));
    }
}
