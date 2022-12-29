package lc.gs;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GoogleSuggestJaxRsXClientTest {

    private GoogleSuggestJaxRsXClient client;
    private Method methodToTest;

    @BeforeEach
    void setupAccessToPrivateMethod() throws NoSuchMethodException {
        methodToTest = GoogleSuggestJaxRsXClient.class.getDeclaredMethod("parseResponseToSuggestions", String.class);
        methodToTest.setAccessible(true);
    }
    
    @BeforeEach
    void createObjectUnderTest() {
        client = new GoogleSuggestJaxRsXClient();
    }
    
    @Test
    void testParseResponseToSuggestions_2022ResponseSample() throws InvocationTargetException, IllegalAccessException {
        String response = "[\"who is the president\",[\"who is the president of america\",\"who is the president of germany\",\"who is the president of usa\",\"who is the president of romania\",\"who is the president of italy\",\"who is the president of uk\",\"who is the president of france\",\"who is the president of brazil\",\"who is the president of england\",\"who is the president of north korea\"],[],{\"google:suggestsubtypes\":[[512],[512],[512],[512],[512],[512],[512],[512],[512],[512]]}]";
        String[] responsesArray = (String[]) methodToTest.invoke(client, response);
        assertEquals("who is the president of america", responsesArray[0]);
    }

    @Test
    public void testParseResponseToSuggestions_2021ResponseSample() throws IOException, InvocationTargetException, IllegalAccessException {
        final ObjectMapper mapper = new ObjectMapper()
                .enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        String response = "[\"serverless\",[\"serverless\",\"serverless framework\"]]";
        String[] responsesArray = (String[]) methodToTest.invoke(client, response);
        assertTrue(responsesArray.length == 2);
        assertEquals("serverless", responsesArray[0]);
        assertEquals("serverless framework", responsesArray[1]);
    }

    @Test
    public void testParseResponseToSuggestions_noSuggestions() throws IOException, InvocationTargetException, IllegalAccessException {
        String response = "[\"google_suggest_will_probably_not_return_any_suggestion_for_this_query\", []]";
        String[] responsesArray = (String[]) methodToTest.invoke(client, response);;
        assertTrue(responsesArray.length == 0);
    }
    
}
