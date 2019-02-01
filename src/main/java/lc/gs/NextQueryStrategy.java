package lc.gs;

/**
 * Strategy that returns the next query to be sent to google suggest API.
 */
public interface NextQueryStrategy {

    /**
     * Returns the next query to be sent to google suggest API.
     * @param currentQuery
     * @param currentResponses
     * @return the next query to be sent to google suggest API, null if no such query can be devised.
     */
    public String nextQuery(String currentQuery, String[] currentResponses);
    
}
