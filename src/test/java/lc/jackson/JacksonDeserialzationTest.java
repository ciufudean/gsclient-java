package lc.jackson;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This test parses strings similar to what Google suggest returns. GoogleSuggestJaxRsXClient depends on these tests.
 */
public class JacksonDeserialzationTest {

    @Test
    public void testJacksonDeserValueAsArray() throws IOException {
        final ObjectMapper mapper = new ObjectMapper()
                .enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        String[][] strings = mapper.readValue("[\"serverless\",[\"serverless\",\"serverless framework\"]]", String[][].class);
        assertTrue(strings.length == 2);
        assertTrue(strings[0].length == 1);
        assertEquals("serverless", strings[0][0]);
        assertTrue(strings[1].length == 2);
        assertEquals("serverless", strings[1][0]);
        assertEquals("serverless framework", strings[1][1]);
    }

    @Test
    public void testJacksonDeserEmptyValueAsArray() throws IOException {
        final ObjectMapper mapper = new ObjectMapper()
                .enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        String[][] strings = mapper.readValue(
                "[\"google_suggest_will_probably_not_return_any_suggestion_for_this_query\", []]", String[][].class);
        assertTrue(strings.length == 2);
        assertTrue(strings[0].length == 1);
        assertEquals("google_suggest_will_probably_not_return_any_suggestion_for_this_query", strings[0][0]);
        assertTrue(strings[1].length == 0);
    }
}
