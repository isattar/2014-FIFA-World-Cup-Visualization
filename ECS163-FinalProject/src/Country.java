

/**
 * Created with IntelliJ IDEA.
 * User: Irum
 * Date: 5/30/14
 * Time: 12:45 PM
 * To change this template use File | Settings | File Templates.
 */
import processing.core.PApplet;
import processing.data.Table;
import processing.data.TableRow;
import java.util.ArrayList;
import java.util.HashMap;

public class Country extends PApplet {
    String name;
    HashMap <String, ArrayList<Match>> matchHistory;
    HashMap <Integer, Statistics> statHistory;
    ArrayList<Ranking> rankHistory;//wbaron add
    String match1;
    String match2;
    String match3;
    int numParticipations = 0;
    int currentRank = 0;
    String bestResult;
    int highestrank = 0;
    int lowestRank = 1000;
    int champion;

    Country (String nam)
    {
        name = nam;
        matchHistory = new HashMap<String, ArrayList<Match>>();
        rankHistory = new ArrayList<Ranking>();//wbaron add
        statHistory = new HashMap<Integer, Statistics>();

        String cups[] = {"1930", "1934", "1938", "1950", "1954", "1958", "1962", "1966", "1970",
                "1974", "1978", "1982", "1986", "1990", "1994", "1998", "2002", "2006", "2010"};

        for (int i = 0; i < 19; i++)
        {
            Table table_matchHistory = loadTable(cups[i]+".csv", "header");
            int matchHistoryRows = table_matchHistory.getRowCount();
            for (int j = 0; j < matchHistoryRows; j++)
            {
                TableRow row = table_matchHistory.getRow(j);
                String team1 = row.getString("Team 1");
                String team2 = row.getString("Team 2");
                String score = row.getString("Score");
                String stage = row.getString("Stage");

                //add stage of game, and take care of draw
                if(team1.equals(name))
                {
                    Match newMatch = new Match(score, name, team2, stage, cups[i]);
                    if (matchHistory.containsKey(team2))
                    {
                        ArrayList<Match> temp = matchHistory.get(team2);
                        temp.add(newMatch);
                        matchHistory.put(team2, temp);
                    }
                    else
                    {
                        ArrayList<Match> temp = new ArrayList<Match>();
                        temp.add(newMatch);
                        matchHistory.put(team2, temp);
                    }
                }
                else if (team2.equals(name))
                {
                    Match newMatch = new Match(score, team1, name, stage, cups[i]);
                    if (matchHistory.containsKey(team1))
                    {
                        ArrayList<Match> temp = matchHistory.get(team1);
                        temp.add(newMatch);
                        matchHistory.put(team1, temp);
                    }
                    else
                    {
                        ArrayList<Match> temp = new ArrayList<Match>();
                        temp.add(newMatch);
                        matchHistory.put(team1, temp);
                    }
                }
            }
        }

        Table table_statHistory = loadTable("statistics.csv", "header");
        int statHistoryRows = table_statHistory.getRowCount();
        for (int j = 0; j < statHistoryRows; j++)
        {
            TableRow row = table_statHistory.getRow(j);
            Integer yea = Integer.valueOf(row.getString("Year"));
            String country = row.getString("Country");
            Integer Y = Integer.valueOf(row.getString("Yellow Card"));
            Integer R = Integer.valueOf(row.getString("Red Card"));
            Integer GF = Integer.valueOf(row.getString("Goals For"));
            Integer GA = Integer.valueOf(row.getString("Goals Against"));

            if(country.equals(name))
            {
                Statistics newStat = new Statistics(yea, Y, R, GF, GA);
                statHistory.put(yea, newStat);
            }
        }

        Table fixtures2014 = loadTable("2014Matches.csv", "header");
        int count = 0;
        for(TableRow row : fixtures2014.rows())
        {
            String team1 = row.getString("Team1");
            String team2 = row.getString("Team2");

            if(team1.equals(name))
            {
                if(count == 0)
                {
                    match1 = team2;
                    count++;
                }
                else if(count == 1)
                {
                    match2 = team2;
                    count++;
                }
                else if(count == 2)
                {
                    match3 = team2;
                    count++;
                }
            }
            else if(team2.equals(name))
            {
                if(count == 0)
                {
                    match1 = team1;
                    count++;
                }
                else if(count == 1)
                {
                    match2 = team1;
                    count++;
                }
                else if(count == 2)
                {
                    match3 = team1;
                    count++;
                }
            }
        }

        Table records2014 = loadTable("2014countriesRecords.csv", "header");
        for(TableRow row : records2014.rows())
        {
            String Country = row.getString("country");
            String bestResult = row.getString("bestResult");
            int numPar = row.getInt("numParticipation");
            int champ = row.getInt("champion");
           if(Country.equals(name))
           {
               this.bestResult = bestResult;
               numParticipations = numPar;
               champion = champ;
               break;
           }

        }

    }
}