package lc.gs;

import java.util.StringTokenizer;

public class LongerNextQueryStrategyImpl implements NextQueryStrategy {

    /**
     * Picks from <code>currentResponses</code> the first one that starts with <code>currentQuery</code>
     * and has more words than it. If no such response exists, it returns <code>currentQuery</code> after dropping
     * its first word. If <code>currentQuery</code> is one word only, it returns null.
     */
    public String nextQuery(String currentQuery, String[] currentResponses) {
        for (String resp : currentResponses) {
            if (resp.startsWith(currentQuery) && hasMoreWords(resp, currentQuery)) {
                return resp;
            }
        }
        
        String trimmedQuery = dropFirstWord(currentQuery);
        return trimmedQuery.length() > 0 ? trimmedQuery : null;
    }

    /**
     * @return <code>true</code> if <code>resp</code> has more words than <code>query</code>, 
     * <code>false</code> otherwise. The delimiter is space.
     */
    private static boolean hasMoreWords(String resp, String query) {
        return new StringTokenizer(resp, " ").countTokens() > new StringTokenizer(query, " ").countTokens();
    }

    /**
     * Drops the first word from <code>query</code> and returns the result. It returns the empty string in case
     * <code>query</code> is one word only.
     */
    private static String dropFirstWord(String query) {
        StringTokenizer st = new StringTokenizer(query, " ");
        if (st.hasMoreTokens()) {
            st.nextToken(); // drop it
        }
        else {
            return "";
        }
        
        StringBuilder sb = new StringBuilder();
        while (st.hasMoreTokens()) {
            sb.append(st.nextToken()).append(" ");
        }
        return sb.toString().trim();
    }
    
}
