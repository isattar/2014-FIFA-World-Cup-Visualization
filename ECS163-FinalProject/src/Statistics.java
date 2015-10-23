import processing.core.PApplet;

/**
 * Created with IntelliJ IDEA.
 * User: Irum
 * Date: 6/2/14
 * Time: 3:32 AM
 * To change this template use File | Settings | File Templates.
 */
public class Statistics extends PApplet
{
    Integer year;
    Integer yellowCards;
    Integer redCards;
    Integer goalsFor;
    Integer goalsAgainst;

    Statistics (Integer yea, Integer Y, Integer R, Integer GF, Integer GA)
    {
        year = yea;
        yellowCards = Y;
        redCards = R;
        goalsFor = GF;
        goalsAgainst = GA;
    }
}
