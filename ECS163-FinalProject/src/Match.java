import processing.core.PApplet;

/**
 * Created with IntelliJ IDEA.
 * User: Irum
 * Date: 5/30/14
 * Time: 1:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class Match extends PApplet
{
    String team1;
    String team2;
    Integer team1Goals;
    Integer team2Goals;
    String winner;
    String stage;
    Integer year;

    Match (String scor, String teamone, String teamtwo, String stag, String yea)
    {
        team1 = teamone;
        team2 = teamtwo;
        stage = stag;
        year = Integer.valueOf(yea);
        String[] splitScore = scor.split("-");
        team1Goals = Integer.valueOf(splitScore[0]);
        team2Goals = Integer.valueOf(splitScore[1]);
        if (team1Goals > team2Goals)
        {
            winner = team1;
        }
        else if  (team2Goals > team1Goals)
        {
            winner = team2;
        }
        else
        {
            winner = "draw";
        }
    }
}
