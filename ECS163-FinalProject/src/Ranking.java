import static processing.core.PApplet.println;

/**
 * Created with IntelliJ IDEA.
 * User: mauro
 * Date: 5/31/14
 * Time: 3:20 PM
 * To change this template use File | Settings | File Templates.
 */

public class Ranking {
    String date;
    Integer rank;

    Ranking(String d, Integer r)
    {
        date = d;
        rank = r;
    }

    void print()
    {
        println("date:" + date + " rank:" + rank);
    }
}
