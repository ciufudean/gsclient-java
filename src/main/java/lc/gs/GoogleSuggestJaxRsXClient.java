package lc.gs;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import java.io.IOException;
import java.util.StringTokenizer;

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

    public static void main(String[] args) throws IOException, InterruptedException {
        validateArgs(args);
        String query = args[0];
        GoogleSuggestJaxRsXClient app = new GoogleSuggestJaxRsXClient();
        while (query != null && query.length() > 0) {
            System.out.println("Querying for '" + query + "'");
            String nextSuggestion = app.getSuggestion(query);
            if (nextSuggestion != null) {
                query = nextSuggestion;
                Thread.sleep(1000);
            }
            else {
                query = dropFirstWord(query);
            }
        }
    }

    private static void validateArgs(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage " + GoogleSuggestJaxRsXClient.class.getName() + " <initial_query>");
            System.exit(1);
        }
    }

    /**
     * Drops the first word from <code>query</code> and returns the result. It returns the empty string in case
     * <code>query</code> is one word only.
     */
    private static String dropFirstWord(String query) {
        StringTokenizer st = new StringTokenizer(query, " ");
        st.nextToken(); // drop it
        StringBuilder sb = new StringBuilder();
        while (st.hasMoreTokens()) {
            sb.append(st.nextToken()).append(" ");
        }
        return sb.toString().trim();
    }

    /**
     * Queries google suggest for <code>query</code>.
     * @return the first of Google's suggestions that starts with the query and has more words than it.
     * <code>null</code> if no such option.
     */
    public String getSuggestion(String query) throws IOException {
        String fullResponse = getServerResponseBodyAsJson(query);
        System.out.println("Google suggest response: " + fullResponse);
        String[] suggestions = jsonToSuggestionArray(fullResponse);
        return getSuitableSuggestion(query, suggestions);
    }

    /** Picks from <code>suggestions</code> the first one that starts with <code>query</code> and has more 
     * words than it. Returns <code>null</code> if no such option.*/
    private String getSuitableSuggestion(String query, String[] suggestions) {
        for (String resp : suggestions) {
            if (resp.startsWith(query) && hasMoreWords(resp, query)) {
                return resp;
            }
        }
        return null;
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

    /**
     * @return <code>true</code> if <code>resp</code> has more words than <code>query</code>, the delimiter is space.
     */
    private boolean hasMoreWords(String resp, String query) {
        return new StringTokenizer(resp, " ").countTokens() > new StringTokenizer(query, " ").countTokens();
    }

}
