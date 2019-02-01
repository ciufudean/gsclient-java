package lc.gs;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import java.io.IOException;

/**
 * A Google suggest client that starts off with a user-provided initial query, then the next query 
 * is based on the response it gets:<ul>
 *     <li>the first suggestion that starts with the current suggestion but it has more words than it</li>
 *     <li>otherwise, the current query minus its first word</li>
 * </ul>
 * It stops when it can no create a next query.
 * <br />
 * Usage: <pre>
 *     java lc.gs.GoogleSuggestJaxRsXClient &lt;initial_query&gt;
 * </pre>
 * 
 * @author lucian.ciufudean
 */
public class GoogleSuggestJaxRsXClient {

    private Client client = ClientBuilder.newClient();
    private NextQueryStrategy nextQueryStrategy;

    public static void main(String[] args) throws IOException, InterruptedException {
        validateArgs(args);
        String query = args[0];
        
        GoogleSuggestJaxRsXClient client = new GoogleSuggestJaxRsXClient();
        client.setNextQueryStrategy(new LongerNextQueryStrategyImpl());
        while (query != null) {
            System.out.println("Querying for '" + query + "'");
            String[] suggestions = client.getSuggestions(query);
            query = client.getNextQuery(query, suggestions);
            if (query != null) {
                Thread.sleep(1000);
            }
        }
    }

    private static void validateArgs(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage " + GoogleSuggestJaxRsXClient.class.getName() + " <initial_query>");
            System.exit(1);
        }
    }

    private String getNextQuery(String query, String[] suggestions) {
        return this.nextQueryStrategy.nextQuery(query, suggestions);
    }

    private void setNextQueryStrategy(NextQueryStrategy nextQueryStrategy) {
        this.nextQueryStrategy = nextQueryStrategy;
    }

    /**
     * Queries google suggest for <code>query</code>.
     * @return the first of Google's suggestions that starts with the query and has more words than it.
     * <code>null</code> if no such option.
     */
    private String[] getSuggestions(String query) throws IOException {
        String fullResponse = getServerResponseBodyAsJson(query);
        System.out.println("Google suggest response: " + fullResponse);
        return jsonToSuggestionArray(fullResponse);
    }

    private String[] jsonToSuggestionArray(String fullResponse) throws IOException {
        final ObjectMapper mapper = new ObjectMapper().enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        return mapper.readValue(fullResponse, String[][].class)[1];
    }

    private String getServerResponseBodyAsJson(String query) {
        WebTarget webTarget =
                client.target("http://suggestqueries.google.com/complete/search")
                        .queryParam("q", query)
                        .queryParam("client", "firefox");
        Invocation.Builder invocationBuilder = webTarget.request();
        return invocationBuilder.get(String.class);
    }
    
}
