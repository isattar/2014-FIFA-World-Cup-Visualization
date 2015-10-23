import controlP5.ControlP5;
import controlP5.Range;
import controlP5.Textarea;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import processing.data.Table;

import controlP5.ControlEvent;
import controlP5.ControlP5;
import controlP5.Range;
import javafx.util.converter.DateStringConverter;
import org.gicentre.utils.stat.XYChart;
import processing.data.TableRow;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;


public class sketch extends PApplet {
    //Variables go here

    boolean drawMap = true;
    boolean drawCountry  = false;
    boolean drawMatch = false;
    boolean showHelp = false;
    int countryChosen = 0;
    int countryChosen2 = 0;


    //setup function
    public void setup(){
        matchSetup();
        mapSetup();
        countrySetup();
    }


    //draw function
    public void draw(){
        image(bg,0,0);

        if(drawMap){
            textAlign(LEFT);
            textFont(font,20);
            range.hide();
            range2.hide();
            helpbox.hide();
            drawMap();
        }
        else if(drawCountry){
            range.show();
            range2.hide();
            helpbox.hide();
            drawCountry(countryChosen);
        }
        else if(drawMatch)
        {
            range.hide();
            range2.show();
            if (showHelp)
            {
                helpbox.show();
            }
            else
            {
                helpbox.hide();
            }
            if(matchIndex == 1)
            {
                countryChosen2 = firstMatch.team2;
                String team1 = getTeam(firstMatch.team1);
                String team2 = getTeam(firstMatch.team2);
                drawPredict(team1, team2);
            }
            else if(matchIndex == 2)
            {
                countryChosen2 = secondMatch.team2;
                String team1 = getTeam(secondMatch.team1);
                String team2 = getTeam(secondMatch.team2);
                drawPredict(team1, team2);
            }
            else if(matchIndex == 3)
            {
                countryChosen2 = thirdMatch.team2;
                String team1 = getTeam(thirdMatch.team1);
                String team2 = getTeam(thirdMatch.team2);
                drawPredict(team1, team2);
            }

        }

    }



    //------------------------------------------------BEGIN IRUM'S STUFF------------------------------------------------
    ArrayList<Country> Countries2014;


    PImage matchbg;
    PImage teamSelected1;
    PImage teamSelected2;
    Range range2;
    Textarea helpbox;

    int rangeBeg = 0;
    int rangeEnd = 18;

    public void matchSetup()
    {
        size(960,600);
        //size(1280,800);
        bg = loadImage("MasterBackground.png");
        logo = loadImage("WorldCupLogo.png");
        matchbg = loadImage("TeamNameBackground.png");
        countryFont = loadFont("CooperBlack-65.vlw");
        textFont(countryFont, 20);

        //-------------------------------------------SLIDER SETUP-------------------------------------------------------

        cp5 = new ControlP5(this);


        range2 = cp5.addRange("CupRange")
                // disable broadcasting since setRange and setRangeValues will trigger an event
                .setBroadcast(false)
                .setPosition(390,(float)695.5)
                .setSize(466,15)
                .setHandleSize(20)
                .setRange(0,18)
                .setRangeValues(0,18)
                        // after the initialization we turn broadcast back on again
                .setBroadcast(true)
                .setColorForeground(color(255,200))
                .setColorBackground(color(255,40))
                .setLabelVisible(false);

        helpbox = cp5.addTextarea("help")
                .setPosition(960,30)
                .setSize(223,118)
                .setFont(createFont("arial",12))
                .setColor(255)
                .setLineHeight(14)
                .disableColorBackground()
                .setScrollForeground(255)
                .showScrollbar();

        helpbox.setText("Wins: this is calculated by taking the "
                +"percentage difference of wins for each "
                +"team (which is displayed on the circle), "
                +"the circle then moves towards which ever "
                +"team that has the higher percentage of "
                +"wins.                "
                +"Penalties: this is calculated by taking "
                +"the percentage difference of cards for "
                +"each team (which is displayed on the "
                +"circle), and if a team has a red card "
                +"it adds three to their total. Circle again "
                +"moves towards the team that has the higher "
                +"percentage.                        "
                +"Goals Scored: this is calculated by taking "
                +"the percentage difference of goals for "
                +"divided by total goals for each team (which "
                +"is displayed on the circle), circle again "
                +"moves towards the team that has the higher "
                +"percentage. ");

        //---------------------------------------------DATA SETUP-------------------------------------------------------
        Countries2014 = new ArrayList<Country>();
        Table table_Countries2014 = loadTable("2014countries.csv", "header");
        int Countries2014Rows = table_Countries2014.getRowCount();
        //println(Countries2014Rows);
        for (int i = 0; i < Countries2014Rows; i++)
        {
            TableRow row = table_Countries2014.getRow(i);
            String country = row.getString("Country");
            Country addCountry = new Country(country);
            Countries2014.add(addCountry);
        }
    }

    public void drawPredict(String country1, String country2)
    {
        //----------------------------------------DRAW GENERAL GUI------------------------------------------------------
        image(logo,550,10);
        image(matchbg,90,220);
        image(matchbg,710,220);

        fill(color(122,210,160));
        noStroke();
        rect(330,380,600,10);
        rect(330,480,600,10);
        rect(330,580,600,10);
        fill(color(54,131,87));
        noStroke();
        rect(635,320,5,330);
        fill(192,231,187);
        textFont(countryFont, 20);
        textAlign(CENTER);
        text("0%",640,665);
        text("100%",330,665);
        text("100%",930,665);
        ellipse(1240,50,40,40);
        textFont(countryFont, 30);
        textAlign(CENTER);
        fill(0);
        if(mouseX >= 1200 && mouseX <= 1280 && mouseY >= 10 && mouseY <= 70)
        {
            fill(color(255,0,0));
        }
        text("?",1240,60);

        fill(0);
        if (country1 == "Bosnia-Herzegovina")
        {
            textFont(countryFont, 35);
        }
        else
        {
            textFont(countryFont, 50);
        }
        textAlign(CENTER);
	    if(mouseX >= 200 && mouseX <= 425 && mouseY >= 200 && mouseY <= 300)
        {
            fill(color(255,0,0));
        }
        text(country1,335,275);
	    fill(0);

        teamSelected1 = loadImage("TeamPhotos/"+country1+"Main.png");
        image(teamSelected1,110,230,50,50);

        fill(192,231,187);;
        textFont(countryFont, 50);
        textAlign(CENTER);
        text("vs",639,275);

        fill(0);
        textFont(countryFont, 50);
        textAlign(CENTER);
	    if(mouseX >= 850 && mouseX <= 1020 && mouseY >= 200 && mouseY <= 300)
        {
            fill(color(255,0,0));
        }
        if (country2 == "Bosnia-Herzegovina")
        {
            textFont(countryFont, 35);
        }
        else
        {
            textFont(countryFont, 50);
        }
        text(country2,975,275);
	    fill(0);

        teamSelected2 = loadImage("TeamPhotos/"+country2+"Main.png");
        image(teamSelected2,730,230,50,50);

        fill(192,231,187);
        textFont(countryFont, 30);
        textAlign(CENTER);
        text("Wins",180,385);
        text("Wins",1100,385);
        fill(192,231,187);
        textFont(countryFont, 28);
        textAlign(CENTER);
        text("Penalties",180,485);
        text("Penalties",1100,485);
        fill(192,231,187);
        textFont(countryFont, 26);
        textAlign(CENTER);
        text("Goals Scored",180,585);
        text("Goals Scored",1100,585);

        //----------------------------------------PROCESSING DATA FOR DISPLAY-------------------------------------------

        int[] c1_wins = new int[19];
        int[] c1_losses = new int[19];
        int[] c1_GF = new int[19];
        int[] c1_GA = new int[19];
        int[] c1_GD = new int[19];
        int[] c1_YC = new int[19];
        int[] c1_RC = new int[19];
        int c1_cupsPlayed = 0;
        int[] c1_gamesPlayedArr = new int[19];
        int[] c2_wins = new int[19];
        int[] c2_losses = new int[19];
        int[] c2_GF = new int[19];
        int[] c2_GA = new int[19];
        int[] c2_YC = new int[19];
        int[] c2_RC = new int[19];
        int c2_cupsPlayed = 0;
        int[] c2_gamesPlayedArr = new int[19];
        int[] c2_GD = new int[19];

        int index1 = getTeamIndex(country1);
        int index2 = getTeamIndex(country2);
        Country c1 = Countries2014.get(index1);
        HashMap<String, ArrayList<Match>> c1Matches = c1.matchHistory;
        HashMap <Integer, Statistics> c1Stats = c1.statHistory;
        Country c2 = Countries2014.get(index2);
        HashMap <String, ArrayList<Match>> c2Matches = c2.matchHistory;
        HashMap <Integer, Statistics> c2Stats = c2.statHistory;


        for (Map.Entry<String, ArrayList<Match>> entry : c1Matches.entrySet())
        {
            ArrayList<Match> i = entry.getValue();
            for (Match m : i)
            {
                int j = getYearIndex(m.year);
                if (m.winner.equals(c1.name))
                {
                    c1_wins[j] = c1_wins[j] + 1;
                    c1_gamesPlayedArr[j] = c1_gamesPlayedArr[j] + 1;
                }
                else
                {
                    c1_losses[j] = c1_losses[j] + 1;
                    c1_gamesPlayedArr[j] = c1_gamesPlayedArr[j] + 1;
                }
            }
        }

        for (Map.Entry<String, ArrayList<Match>> entry : c2Matches.entrySet())
        {
            ArrayList<Match> i = entry.getValue();
            for (Match m : i)
            {
                int j = getYearIndex(m.year);
                if (m.winner.equals(c2.name))
                {
                    c2_wins[j] = c2_wins[j] + 1;
                    c2_gamesPlayedArr[j] = c2_gamesPlayedArr[j] + 1;
                }
                else
                {
                    c2_losses[j] = c2_losses[j] + 1;
                    c2_gamesPlayedArr[j] = c2_gamesPlayedArr[j] + 1;
                }
            }
        }

        for (Map.Entry<Integer, Statistics> entry : c1Stats.entrySet())
        {
            Statistics i = entry.getValue();
            c1_cupsPlayed = c1_cupsPlayed + 1;
            int j = getYearIndex(i.year);
            c1_GF[j] = i.goalsFor;
            c1_GA[j] = i.goalsAgainst;
            c1_YC[j] = i.yellowCards;
            c1_RC[j] = i.redCards;
            c1_GD[j] = c1_GF[j] - c1_GA[j];
        }

        for (Map.Entry<Integer, Statistics> entry : c2Stats.entrySet())
        {
            Statistics i = entry.getValue();
            c2_cupsPlayed = c2_cupsPlayed + 1;
            int j = getYearIndex(i.year);
            c2_GF[j] = i.goalsFor;
            c2_GA[j] = i.goalsAgainst;
            c2_YC[j] = i.yellowCards;
            c2_RC[j] = i.redCards;
            c2_GD[j] = c2_GF[j] - c2_GA[j];
        }

        double c1_totalWins = 0;
        double c2_totalWins = 0;
        double c1_totalLosses = 0;
        double c2_totalLosses = 0;
        double c1_totalGD = 0;
        double c2_totalGD = 0;
        double c1_totalGF = 0;
        double c2_totalGF = 0;
        double c1_totalGA = 0;
        double c2_totalGA = 0;
        double c1_totalG = 0;
        double c2_totalG = 0;
        double c1_gamesPlayed = 0;
        double c2_gamesPlayed = 0;
        double c1_totalCards = 0;
        double c2_totalCards = 0;
        double c1_totalRC = 0;
        double c2_totalRC = 0;
        double c1_totalYC = 0;
        double c2_totalYC = 0;
        float c1_winPer = 0;
        float c2_winPer = 0;
        float c1_GPer = 0;
        float c2_GPer = 0;


        //--------------------------------------CHANGING DATA BASED ON INTERACTION--------------------------------------

        if (rangeBeg == rangeEnd)
        {
            c1_totalWins = c1_wins[rangeBeg];
            c2_totalWins = c2_wins[rangeBeg];
            c1_totalLosses = c1_losses[rangeBeg];
            c2_totalLosses = c2_losses[rangeBeg];
            c1_totalGF = c1_GF[rangeBeg];
            c1_totalGA = c1_GA[rangeBeg];
            c2_totalGF = c2_GF[rangeBeg];
            c2_totalGA = c2_GA[rangeBeg];
            c1_totalGD = c1_GD[rangeBeg];
            c2_totalGD = c2_GD[rangeBeg];
            c1_gamesPlayed = c1_gamesPlayedArr[rangeBeg];
            c2_gamesPlayed = c2_gamesPlayedArr[rangeBeg];
            c1_totalYC = c1_YC[rangeBeg];
            c1_totalRC = c1_RC[rangeBeg];
            c2_totalYC = c2_YC[rangeBeg];
            c2_totalRC = c2_RC[rangeBeg];
            c1_totalCards = c1_YC[rangeBeg] + (c1_RC[rangeBeg]*3);
            c2_totalCards = c2_YC[rangeBeg] + (c2_RC[rangeBeg]*3);
        }

        for (int i = rangeBeg; i < rangeEnd+1; i++)
        {
            c1_totalWins = c1_totalWins + c1_wins[i];
            c2_totalWins = c2_totalWins + c2_wins[i];
            c1_totalLosses = c1_totalLosses + c1_losses[i];
            c2_totalLosses = c2_totalLosses + c2_losses[i];
            c1_totalGF = c1_totalGF + c1_GF[i];
            c1_totalGA = c1_totalGA + c1_GA[i];
            c2_totalGF = c2_totalGF + c2_GF[i];
            c2_totalGA = c2_totalGA + c2_GA[i];
            c1_totalGD = c1_totalGD + c1_GD[i];
            c2_totalGD = c2_totalGD + c2_GD[i];
            c1_gamesPlayed = c1_gamesPlayed + c1_gamesPlayedArr[i];
            c2_gamesPlayed = c2_gamesPlayed + c2_gamesPlayedArr[i];
            c1_totalYC = c1_totalYC + c1_YC[i];
            c1_totalRC = c1_totalRC + c1_RC[i];
            c2_totalYC = c2_totalYC + c2_YC[i];
            c2_totalRC = c2_totalRC + c2_RC[i];
            c1_totalCards = c1_totalCards + c1_YC[i] + (c1_RC[i]*3);
            c2_totalCards = c2_totalCards + c2_YC[i] + (c2_RC[i]*3);
        }

        fill(54,131,87);
        textFont(countryFont, 20);
        textAlign(CENTER);
        text((int)c1_totalWins+" out of "+(int)c1_gamesPlayed,180,405);
        text((int)c2_totalWins+" out of "+(int)c2_gamesPlayed,1100,405);
        text((int)c1_totalYC+" YC and "+(int)c1_totalRC+" RC",180,505);
        text((int)c2_totalYC+" YC and "+(int)c2_totalRC+" RC",1100,505);
        text((int)c1_totalGF+" GF and "+(int)c1_totalGA+" GA",180,605);
        text((int)c2_totalGF+" GF and "+(int)c2_totalGA+" GA",1100,605);

        fill(54,131,87);
        textFont(countryFont, 20);
        textAlign(CENTER);
        int fromCup = getYear(rangeBeg);
        int toCup = getYear(rangeEnd);
        text(fromCup+" - "+toCup,636,728);

        if(c1_gamesPlayed == 0)
        {
            c1_winPer = 0;
        }
        else
        {
            c1_winPer = (float) (c1_totalWins/c1_gamesPlayed) * 100;
        }
        if (c2_gamesPlayed == 0)
        {
            c2_winPer = 0;
        }
        else
        {
            c2_winPer = (float) (c2_totalWins/c2_gamesPlayed) * 100;
        }
        c1_totalG = c1_totalGF+c1_totalGA;
        c2_totalG = c2_totalGF+c2_totalGA;
        if(c1_totalG == 0)
        {
            c1_GPer = 0;
        }
        else
        {
            c1_GPer = (float) (c1_totalGF/c1_totalG) * 100;
        }
        if (c2_totalG == 0)
        {
            c2_GPer = 0;
        }
        else
        {
            c2_GPer = (float) (c2_totalGF/c2_totalG) * 100;
        }

        //----------------------------------------------DISPLAYING DATA-------------------------------------------------

        if (c1_winPer > c2_winPer)
        {
            float winDiff = c1_winPer - c2_winPer;
            float move1 = 300 * (winDiff/100);
            float xpos = 638 - move1;
            fill(128,210,154);
            ellipse(xpos,383,80,80);
            fill(192,231,187);
            ellipse(xpos,383,65,65);
            fill(54,131,87);
            textFont(countryFont, 20);
            textAlign(CENTER);
            text((int)winDiff+"%",xpos,388);
        }
        else if (c2_winPer > c1_winPer)
        {
            float winDiff = c2_winPer - c1_winPer;
            float move1 = 300 * (winDiff/100);
            float xpos = 638 + move1;
            fill(128,210,154);
            ellipse(xpos,383,80,80);
            fill(192,231,187);
            ellipse(xpos,383,65,65);
            fill(54,131,87);
            textFont(countryFont, 20);
            textAlign(CENTER);
            text((int)winDiff+"%",xpos,388);
        }
        else if (c1_winPer == 0 && c2_winPer == 0)
        {
            fill(128,210,154);
            ellipse(638,383,80,80);
            fill(192,231,187);
            ellipse(638,383,65,65);
            fill(54,131,87);
            textFont(countryFont, 20);
            textAlign(CENTER);
            text("0%",638,388);
        }
        if (c1_totalCards > c2_totalCards)
        {
            float perc = (float)((c1_totalCards-c2_totalCards)/(c1_totalCards+c2_totalCards));
            println(perc);
            float move2 = 300 * perc;
            float xpos = 638 - move2;
            fill(128,210,154);
            ellipse(xpos,483,80,80);
            fill(192,231,187);
            ellipse(xpos,483,65,65);
            fill(54,131,87);
            textFont(countryFont, 20);
            textAlign(CENTER);
            text((int)(perc*100)+"%",xpos,488);
        }
        else if (c2_totalCards > c1_totalCards)
        {
            float perc = (float)((c2_totalCards-c1_totalCards)/(c2_totalCards+c1_totalCards));
            float move2 = 300 * perc;
            float xpos = 638 + move2;
            fill(128,210,154);
            ellipse(xpos,483,80,80);
            fill(192,231,187);
            ellipse(xpos,483,65,65);
            fill(54,131,87);
            textFont(countryFont, 20);
            textAlign(CENTER);
            text((int)(perc*100)+"%",xpos,488);
        }
        else if (c1_totalCards == 0 && c2_totalCards == 0)
        {
            fill(128,210,154);
            ellipse(638,483,80,80);
            fill(192,231,187);
            ellipse(638,483,65,65);
            fill(54,131,87);
            textFont(countryFont, 20);
            textAlign(CENTER);
            text("0%",638,488);
        }
        if(c1_GPer > c2_GPer)
        {
            float GDiff = c1_GPer - c2_GPer;
            float move1 = 300 * (GDiff/100);
            float xpos = 638 - move1;
            fill(128,210,154);
            ellipse(xpos,583,80,80);
            fill(192,231,187);
            ellipse(xpos,583,65,65);
            fill(54,131,87);
            textFont(countryFont, 20);
            textAlign(CENTER);
            text((int)GDiff+"%",xpos,588);
        }
        else if(c2_GPer > c1_GPer)
        {
            float GDiff = c2_GPer - c1_GPer;
            float move1 = 300 * (GDiff/100);
            float xpos = 638 + move1;
            fill(128,210,154);
            ellipse(xpos,583,80,80);
            fill(192,231,187);
            ellipse(xpos,583,65,65);
            fill(54,131,87);
            textFont(countryFont, 20);
            textAlign(CENTER);
            text((int)GDiff+"%",xpos,588);
        }
        else if (c1_GPer == 0 && c2_GPer == 0)
        {
            fill(128,210,154);
            ellipse(638,583,80,80);
            fill(192,231,187);
            ellipse(638,583,65,65);
            fill(54,131,87);
            textFont(countryFont, 20);
            textAlign(CENTER);
            text("0%",638,588);
        }
    }

    //------------------------------------------------END IRUM'S STUFF--------------------------------------------------


    //Map Visualization stuff

    boolean cursorChanged = false;
    boolean toggleLabel = true;


    PFont font;

    PImage brasil;
    PImage bg;

    PImage logo;

    boolean lineLock = false;


    PImage groupA,groupB,groupC,groupD,groupE,groupF,groupG,groupH;
    PImage groupBackground;


    Marker manaus,fortaleza,recife,salvador,cuiaba,brasilia,belo,rio,curitiba,porto,natal,sao;

    TeamIcon mexico,brazil,cameroon,croatia;

    boolean groupAActive[] =  new boolean[32];
    boolean groupAHovered = false;
    boolean groupBHovered = false;
    boolean groupCHovered = false;
    boolean groupDHovered = false;
    boolean groupEHovered = false;
    boolean groupFHovered = false;
    boolean groupGHovered = false;
    boolean groupHHovered = false;


    TeamIcon spain,australia,netherlands,chile;

    TeamIcon colombia,japan, cote,greece;

    TeamIcon england,italy,costa,uruguay;

    TeamIcon switzerland,france,honduras,ecuador;

    TeamIcon argentina,bosnia,iran,nigeria;

    TeamIcon germany,ghana,portugal,usa;

    TeamIcon algeria,korea,russia,belgium;

    public void mapSetup() {
        size(1280,800);

        frameRate(60);
        brasil = loadImage("Brazil.png");
        bg = loadImage("MasterBackground.png");
        logo = loadImage("WorldCupLogo.png");

        //Groups
        groupA = loadImage("GroupA.png");

        groupB = loadImage("GroupB.png");

        groupC = loadImage("GroupC.png");

        groupD = loadImage("GroupD.png");

        groupE = loadImage("GroupE.png");

        groupF = loadImage("GroupF.png");

        groupG = loadImage("GroupG.png");

        groupH = loadImage("GroupH.png");


        //Setting up font
        font = loadFont("CooperBlackMS-26.vlw");
        textFont(font, 20);

        //Setting up groups
        groupBackground = loadImage("GroupBackground.png");


        //setting up markers for each city
        manaus = new Marker(526,800-473,15,"Manaus");
        fortaleza = new Marker(800,800-465,15,"Fortaleza");
        recife = new Marker(856,800-407,15,"Recife");
        salvador = new Marker(809,800-342,15,"Salvador");
        cuiaba = new Marker(577,800-306,15,"Cuiaba");
        brasilia = new Marker(685,800-304,15,"Brasilia");
        belo = new Marker(737,800-246,15,"Belo Horizonte");
        rio = new Marker(747,800-204,15,"Rio de Janeiro");
        sao = new Marker(702,800-195,15, "Sao Paulo");
        curitiba = new Marker(667,800-168,15, "Curitiba");
        porto = new Marker(637,800-98,15,"Porto Alegre");
        natal = new Marker(852,800-437,15,"Natal");

        //setting up Team logos
        brazil = new TeamIcon("Brasil",90,85,255,240,0,1,0,1474,1049.4f,0,0);
        brazil.loc.citiesToGoTo.add(sao);
        brazil.loc.citiesToGoTo.add(fortaleza);
        brazil.loc.citiesToGoTo.add(brasilia);
        brazil.loc.setPositions();

        mexico = new TeamIcon("Mexico",90,115,193,38,45,1,1,269.46f,390.5f,0,0);
        mexico.loc.citiesToGoTo.add(natal);
        mexico.loc.citiesToGoTo.add(fortaleza);
        mexico.loc.citiesToGoTo.add(recife);
        mexico.loc.setPositions();

        cameroon = new TeamIcon("Cameroon",90,145,16,149,75,2,2,1721.32f,1204.83f,0,0);
        cameroon.loc.citiesToGoTo.add(natal);
        cameroon.loc.citiesToGoTo.add(manaus);
        cameroon.loc.citiesToGoTo.add(brasilia);
        cameroon.loc.setPositions();

        croatia = new TeamIcon("Croatia",90,175,45,48,133,2,3,1673.03f,1763.61f,0,0);
        croatia.loc.citiesToGoTo.add(sao);
        croatia.loc.citiesToGoTo.add(manaus);
        croatia.loc.citiesToGoTo.add(recife);
        croatia.loc.setPositions();


        //Group B
        spain = new TeamIcon("Spain",90,275,249,175,59,1,4,753.34f,420.44f,0,0);
        spain.loc.citiesToGoTo.add(salvador);
        spain.loc.citiesToGoTo.add(rio);
        spain.loc.citiesToGoTo.add(curitiba);
        spain.loc.setPositions();

        australia = new TeamIcon("Australia",90,305,46,50,134,2,5,1045.34f,340.83f,0,0);
        australia.loc.citiesToGoTo.add(cuiaba);
        australia.loc.citiesToGoTo.add(porto);
        australia.loc.citiesToGoTo.add(curitiba);
        australia.loc.setPositions();

        netherlands = new TeamIcon("Netherlands",90,335,239,239,239,1,6,1433.52f,1084.29f,0,0);
        netherlands.loc.citiesToGoTo.add(salvador);
        netherlands.loc.citiesToGoTo.add(porto);
        netherlands.loc.citiesToGoTo.add(sao);
        netherlands.loc.setPositions();

        chile = new TeamIcon("Chile",90,365,193,38,45,2,7,980.34f,1084.29f,0,0);
        chile.loc.citiesToGoTo.add(cuiaba);
        chile.loc.citiesToGoTo.add(rio);
        chile.loc.citiesToGoTo.add(sao);
        chile.loc.setPositions();

        //Group C
        colombia = new TeamIcon("Colombia",90,465,250,176,62,1,8,688.5f,547.19f,0,0);
        colombia.loc.citiesToGoTo.add(belo);
        colombia.loc.citiesToGoTo.add(brasilia);
        colombia.loc.citiesToGoTo.add(cuiaba);
        colombia.loc.setPositions();

        japan = new TeamIcon("Japan",90,495,193,38,45,2,9,158.47f,1572.51f,0,0);
        japan.loc.citiesToGoTo.add(recife);
        japan.loc.citiesToGoTo.add(natal);
        japan.loc.citiesToGoTo.add(cuiaba);
        japan.loc.setPositions();

        cote = new TeamIcon("Ivory Coast",90,525,0,145,68,1,10,1029.75f,1049.4f,0,0);
        cote.loc.citiesToGoTo.add(recife);
        cote.loc.citiesToGoTo.add(brasilia);
        cote.loc.citiesToGoTo.add(fortaleza);
        cote.loc.setPositions();


        greece = new TeamIcon("Greece",90,555,45,49,133,2,11,1789.77f,269.46f,0,0);
        greece.loc.citiesToGoTo.add(belo);
        greece.loc.citiesToGoTo.add(natal);
        greece.loc.citiesToGoTo.add(fortaleza);
        greece.loc.setPositions();

        //Group D
        england = new TeamIcon("England",90,655,193,38,45,1,12,1670.52f,625.35f,0,0);
        england.loc.citiesToGoTo.add(manaus);
        england.loc.citiesToGoTo.add(sao);
        england.loc.citiesToGoTo.add(belo);
        england.loc.setPositions();

        italy = new TeamIcon("Italy",90,685,16,152,80,1,13,1763.61f,158.47f,0,0);
        italy.loc.citiesToGoTo.add(manaus);
        italy.loc.citiesToGoTo.add(recife);
        italy.loc.citiesToGoTo.add(natal);
        italy.loc.setPositions();

        costa = new TeamIcon("CostaRica",90,715,46,50,134,2,14,390.05f,1704.39f,0,0);
        costa.loc.citiesToGoTo.add(fortaleza);
        costa.loc.citiesToGoTo.add(recife);
        costa.loc.citiesToGoTo.add(belo);
        costa.loc.setPositions();


        uruguay = new TeamIcon("Uruguay",90,745,239,239,239,2,15,1160.65f,1269.31f,0,0);
        uruguay.loc.citiesToGoTo.add(fortaleza);
        uruguay.loc.citiesToGoTo.add(sao);
        uruguay.loc.citiesToGoTo.add(natal);
        uruguay.loc.setPositions();


        //Group E
        switzerland = new TeamIcon("Switzerland",1048,85,193,38,45,1,16, 658.06f,1621.97f,0,0);
        switzerland.loc.citiesToGoTo.add(brasilia);
        switzerland.loc.citiesToGoTo.add(salvador);
        switzerland.loc.citiesToGoTo.add(manaus);
        switzerland.loc.setPositions();

        france = new TeamIcon("France",1048,115,239,239,239,1,17,1432.52f,753.34f,0,0);
        france.loc.citiesToGoTo.add(porto);
        france.loc.citiesToGoTo.add(salvador);
        france.loc.citiesToGoTo.add(rio);
        france.loc.setPositions();

        honduras = new TeamIcon("Honduras",1048,145,38,82,158,2,18,340.83f, 1700.45f,0,0);
        honduras.loc.citiesToGoTo.add(porto);
        honduras.loc.citiesToGoTo.add(curitiba);
        honduras.loc.citiesToGoTo.add(manaus);
        honduras.loc.setPositions();

        ecuador = new TeamIcon("Ecuador",1048,175,249,175,59,2,19,671.80f,420.44f,0,0);
        ecuador.loc.citiesToGoTo.add(brasilia);
        ecuador.loc.citiesToGoTo.add(curitiba);
        ecuador.loc.citiesToGoTo.add(rio);
        ecuador.loc.setPositions();

        //Group F
        argentina = new TeamIcon("Argentina",1048,275,119,168,217,1,20,787.53f,577.63f,0,0);
        argentina.loc.citiesToGoTo.add(rio);
        argentina.loc.citiesToGoTo.add(belo);
        argentina.loc.citiesToGoTo.add(porto);
        argentina.loc.setPositions();

        bosnia = new TeamIcon("Bosnia",1048,305,45,49,133,1,21,981.59f,1193.05f,0,0);
        bosnia.loc.citiesToGoTo.add(rio);
        bosnia.loc.citiesToGoTo.add(cuiaba);
        bosnia.loc.citiesToGoTo.add(salvador);
        bosnia.loc.setPositions();

        iran = new TeamIcon("Iran",1048,335,193,38,45,2,22,440.91f,1304.59f,0,0);
        iran.loc.citiesToGoTo.add(curitiba);
        iran.loc.citiesToGoTo.add(belo);
        iran.loc.citiesToGoTo.add(salvador);
        iran.loc.setPositions();

        nigeria = new TeamIcon("Nigeria",1048,365,6,145,68,2,23,810.25f,1045.34f,0,0);
        nigeria.loc.citiesToGoTo.add(curitiba);
        nigeria.loc.citiesToGoTo.add(cuiaba);
        nigeria.loc.citiesToGoTo.add(porto);
        nigeria.loc.setPositions();

        //Group G
        germany = new TeamIcon("Germany",1048,465,50,48,36,1,24,639.17f,390.05f,0,0);
        germany.loc.citiesToGoTo.add(salvador);
        germany.loc.citiesToGoTo.add(fortaleza);
        germany.loc.citiesToGoTo.add(recife);
        germany.loc.setPositions();

        ghana = new TeamIcon("Ghana",1048,495,249,175,59,1,25,269.46f,1049.40f,0,0);
        ghana.loc.citiesToGoTo.add(natal);
        ghana.loc.citiesToGoTo.add(fortaleza);
        ghana.loc.citiesToGoTo.add(brasilia);
        ghana.loc.setPositions();

        portugal = new TeamIcon("Portugal",1048,525,18,153,81,2,26,1621.97f, 1204.84f,0,0);
        portugal.loc.citiesToGoTo.add(salvador);
        portugal.loc.citiesToGoTo.add(manaus);
        portugal.loc.citiesToGoTo.add(brasilia);
        portugal.loc.setPositions();

        usa = new TeamIcon("UnitedStates",1048,555,193,38,45,2,27,1721.32f, 1763.61f,0,0);
        usa.loc.citiesToGoTo.add(natal);
        usa.loc.citiesToGoTo.add(manaus);
        usa.loc.citiesToGoTo.add(recife);
        usa.loc.setPositions();

        //Group H
        algeria = new TeamIcon("Algeria",1048,655,8,150,74,1,28,577.63f,340.83f,0,0);
        algeria.loc.citiesToGoTo.add(belo);
        algeria.loc.citiesToGoTo.add(porto);
        algeria.loc.citiesToGoTo.add(curitiba);
        algeria.loc.setPositions();

        korea = new TeamIcon("Korea",1048,685,45,49,133,1,29,1045.34f,1084.29f,0,0);
        korea.loc.citiesToGoTo.add(cuiaba);
        korea.loc.citiesToGoTo.add(porto);
        korea.loc.citiesToGoTo.add(sao);
        korea.loc.setPositions();

        russia = new TeamIcon("Russia",1048,715,193,38,45,2,30,981.59f, 420.44f,0,0);
        russia.loc.citiesToGoTo.add(cuiaba);
        russia.loc.citiesToGoTo.add(rio);
        russia.loc.citiesToGoTo.add(curitiba);
        russia.loc.setPositions();

        belgium = new TeamIcon("Belgium",1048,745,249,175,59,2,31,787.53f,811.38f,0,0);
        belgium.loc.citiesToGoTo.add(belo);
        belgium.loc.citiesToGoTo.add(rio);
        belgium.loc.citiesToGoTo.add(sao);
        belgium.loc.setPositions();


        for(int i = 0; i < 32; i++){
            groupAActive[i] = false;
        }
    }

    public void drawMap() {

        image(bg, 0, 0);

        image(brasil, 340, 216);

        image(logo,550,10);


        //Drawing Stadium Markers
        manaus.draw();
        fortaleza.draw();
        recife.draw();
        salvador.draw();
        cuiaba.draw();
        brasilia.draw();
        belo.draw();
        rio.draw();
        sao.draw();
        curitiba.draw();
        porto.draw();
        natal.draw();

        //Distance Box
        image(groupBackground,320,75, groupBackground.width,40);
        text("Distance Traveled" ,320,75);

        //Distance 2 Box
        image(groupBackground,790,75, groupBackground.width+20,110);
        text("Total Distance" ,790,75);
        displayTotalMiles();


        //Drawing Group A
        groupAHovered = false;
        if(mouseX >= 72 && 72+groupA.width >= mouseX && mouseY >= 45 && mouseY <= 45+groupA.height){
            cursor(HAND);
            cursorChanged = true;
            groupAHovered = true;
            image(groupA,72,45,groupA.width + 5, groupA.height + 5);
        }
        else{
            image(groupA,72,45);
        }
        image(groupBackground,72,75);
        brazil.draw();
        cameroon.draw();
        croatia.draw();
        mexico.draw();


        //Drawing Group B
        groupBHovered = false;
        if(mouseX >= 72 && 72+groupA.width >= mouseX && mouseY >= 235 && mouseY <= 235+groupA.height){
            cursor(HAND);
            cursorChanged = true;
            groupBHovered = true;
            image(groupB,72,235,groupA.width + 5, groupA.height + 5);
        }
        else{
            image(groupB,72,235);
        }
        image(groupBackground,72,265);
        spain.draw();
        australia.draw();
        netherlands.draw();
        chile.draw();

        //Drawing Group C
        groupCHovered = false;
        if(mouseX >= 72 && 72+groupA.width >= mouseX && mouseY >= 425 && mouseY <= 425+groupA.height){
            cursor(HAND);
            cursorChanged = true;
            groupCHovered = true;
            image(groupC,72,425,groupA.width + 5, groupA.height + 5);
        }
        else{
            image(groupC,72,425);
        }
        image(groupBackground,72,455);
        colombia.draw();
        japan.draw();
        cote.draw();
        greece.draw();


        //Drawing Group D
        groupDHovered = false;
        if(mouseX >= 72 && 72+groupA.width >= mouseX && mouseY >= 615 && mouseY <= 615+groupA.height){
            cursor(HAND);
            cursorChanged = true;
            groupDHovered = true;
            image(groupD,72,615,groupA.width + 5, groupA.height + 5);
        }
        else{
            image(groupD,72,615);
        }
        image(groupBackground,72,645);
        england.draw();
        italy.draw();
        costa.draw();
        uruguay.draw();

        //Drawing Group E
        groupEHovered = false;
        if(mouseX >= 1030 && 1030+groupA.width >= mouseX && mouseY >= 45 && mouseY <= 45+groupA.height){
            cursor(HAND);
            cursorChanged = true;
            groupEHovered = true;
            image(groupE,1030,45,groupA.width + 5, groupA.height + 5);
        }
        else{
            image(groupE,1030,45);
        }
        image(groupBackground,1030,75);
        switzerland.draw();
        france.draw();
        honduras.draw();
        ecuador.draw();


        //Drawing Group F
        groupFHovered = false;
        if(mouseX >= 1030 && 1030+groupA.width >= mouseX && mouseY >= 235 && mouseY <= 235+groupA.height){
            cursor(HAND);
            cursorChanged = true;
            groupFHovered = true;
            image(groupF,1030,235,groupA.width + 5, groupA.height + 5);
        }
        else{
            image(groupF,1030,235);
        }
        image(groupBackground,1030,265);
        argentina.draw();
        bosnia.draw();
        iran.draw();
        nigeria.draw();

        //Drawing Group G
        groupGHovered = false;
        if(mouseX >= 1030 && 1030+groupA.width >= mouseX && mouseY >= 425 && mouseY <= 425+groupA.height){
            cursor(HAND);
            cursorChanged = true;
            groupGHovered = true;
            image(groupG,1030,425,groupA.width + 5, groupA.height + 5);
        }
        else{
            image(groupG,1030,425);
        }
        image(groupBackground,1030,455);
        germany.draw();
        ghana.draw();
        portugal.draw();
        usa.draw();

        //Drawing Group H
        groupHHovered = false;
        if(mouseX >= 1030 && 1030+groupA.width >= mouseX && mouseY >= 615 && mouseY <= 615+groupA.height){
            cursor(HAND);
            cursorChanged = true;
            groupHHovered = true;
            image(groupH,1030,615,groupA.width + 5, groupA.height + 5);
        }
        else{
            image(groupH,1030,615);
        }
        image(groupBackground,1030,645);
        korea.draw();
        algeria.draw();
        russia.draw();
        belgium.draw();


        //group A
        if(groupAActive[0]){
            brazil.loc.draw();
        }
        if(groupAActive[1]){
            mexico.loc.draw();
        }
        if(groupAActive[2]){
            croatia.loc.draw();
        }
        if(groupAActive[3]){
            cameroon.loc.draw();
        }
        if(groupAActive[4]){
            spain.loc.draw();

        }
        if(groupAActive[5]){
            australia.loc.draw();
        }
        if(groupAActive[6]){
            netherlands.loc.draw();
        }
        if(groupAActive[7]){
            chile.loc.draw();
        }
        if(groupAActive[8]){
            colombia.loc.draw();
        }
        if(groupAActive[9]){
            japan.loc.draw();
        }
        if(groupAActive[10]){
            cote.loc.draw();
        }
        if(groupAActive[11]){
            greece.loc.draw();
        }
        if(groupAActive[12]){
            england.loc.draw();
        }
        if(groupAActive[13]){
            italy.loc.draw();
        }
        if(groupAActive[14]){
            costa.loc.draw();
        }
        if(groupAActive[15]){
            uruguay.loc.draw();
        }
        if(groupAActive[16]){
            switzerland.loc.draw();
        }
        if(groupAActive[17]){
            france.loc.draw();
        }
        if(groupAActive[18]){
            honduras.loc.draw();
        }
        if(groupAActive[19]){
            ecuador.loc.draw();
        }
        if(groupAActive[20]){
            argentina.loc.draw();
        }
        if(groupAActive[21]){
            bosnia.loc.draw();
        }
        if(groupAActive[22]){
            iran.loc.draw();
        }
        if(groupAActive[23]){
            nigeria.loc.draw();
        }
        if(groupAActive[24]){
            germany.loc.draw();
        }
        if(groupAActive[25]){
            ghana.loc.draw();
        }
        if(groupAActive[26]){
            portugal.loc.draw();
        }
        if(groupAActive[27]){
            usa.loc.draw();
        }
        if(groupAActive[28]){
            algeria.loc.draw();
        }
        if(groupAActive[29]){
            korea.loc.draw();
        }
        if(groupAActive[30]){
            russia.loc.draw();
        }
        if(groupAActive[31]){
            belgium.loc.draw();
        }

        //Draw Labels On Cities
        if(toggleLabel){
            manaus.drawLabel();
            fortaleza.drawLabel();
            recife.drawLabel();
            salvador.drawLabel();
            cuiaba.drawLabel();
            brasilia.drawLabel();
            belo.drawLabel();
            rio.drawLabel();
            sao.drawLabel();
            curitiba.drawLabel();
            porto.drawLabel();
            natal.drawLabel();
        }
        else{
            if(manaus.isHovered()){
                manaus.drawLabel();
            }
            else if(fortaleza.isHovered()){
                fortaleza.drawLabel();
            }
            else if(recife.isHovered()){
                recife.drawLabel();
            }
            else if(salvador.isHovered()){
                salvador.drawLabel();
            }
            else if(cuiaba.isHovered()){
                cuiaba.drawLabel();
            }
            else if(brasilia.isHovered()){
                brasilia.drawLabel();
            }
            else if(belo.isHovered()){
                belo.drawLabel();
            }
            else if(rio.isHovered()){
                rio.drawLabel();
            }
            else if(curitiba.isHovered()){
                curitiba.drawLabel();
            }
            else if(porto.isHovered()){
                porto.drawLabel();
            }
            else if(natal.isHovered()){
                natal.drawLabel();
            }
            else if(sao.isHovered()){
                sao.drawLabel();
            }
        }




        if(groupAActive[0]){
            brazil.loc.drawIcon();
        }
        if(groupAActive[1]){
            mexico.loc.drawIcon();
        }
        if(groupAActive[2]){
            croatia.loc.drawIcon();
        }
        if(groupAActive[3]){
            cameroon.loc.drawIcon();
        }
        if(groupAActive[4]){
            spain.loc.drawIcon();
        }
        if(groupAActive[5]){
            australia.loc.drawIcon();
        }
        if(groupAActive[6]){
            netherlands.loc.drawIcon();
        }
        if(groupAActive[7]){
            chile.loc.drawIcon();
        }
        if(groupAActive[8]){
            colombia.loc.drawIcon();
        }
        if(groupAActive[9]){
            japan.loc.drawIcon();
        }
        if(groupAActive[10]){
            cote.loc.drawIcon();
        }
        if(groupAActive[11]){
            greece.loc.drawIcon();
        }
        if(groupAActive[12]){
            england.loc.drawIcon();
        }
        if(groupAActive[13]){
            italy.loc.drawIcon();
        }
        if(groupAActive[14]){
            costa.loc.drawIcon();
        }
        if(groupAActive[15]){
            uruguay.loc.drawIcon();
        }
        if(groupAActive[16]){
            switzerland.loc.drawIcon();
        }
        if(groupAActive[17]){
            france.loc.drawIcon();
        }
        if(groupAActive[18]){
            honduras.loc.drawIcon();
        }
        if(groupAActive[19]){
            ecuador.loc.drawIcon();
        }
        if(groupAActive[20]){
            argentina.loc.drawIcon();
        }
        if(groupAActive[21]){
            bosnia.loc.drawIcon();
        }
        if(groupAActive[22]){
            iran.loc.drawIcon();
        }
        if(groupAActive[23]){
            nigeria.loc.drawIcon();
        }
        if(groupAActive[24]){
            germany.loc.drawIcon();
        }
        if(groupAActive[25]){
            ghana.loc.drawIcon();
        }
        if(groupAActive[26]){
            portugal.loc.drawIcon();
        }
        if(groupAActive[27]){
            usa.loc.drawIcon();
        }
        if(groupAActive[28]){
            algeria.loc.drawIcon();
        }
        if(groupAActive[29]){
            korea.loc.drawIcon();
        }
        if(groupAActive[30]){
            russia.loc.drawIcon();
        }
        if(groupAActive[31]){
            belgium.loc.drawIcon();
        }




        cursorChanged = false;
        lineLock = false;
    }


    public static BigDecimal round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd;
    }

    public void displayTotalMiles(){
        fill(0);
        if(groupAActive[0] || groupAActive[1] || groupAActive[2] || groupAActive[3]){
            image(croatia.logo,790,75);
            text( round((croatia.loc.dist1+croatia.loc.dist2),2) + " Miles",820,92);

            image(cameroon.logo,790,101);
            text( round((cameroon.loc.dist1+cameroon.loc.dist2),2) + " Miles",820,120);

            image(brazil.logo,790,127);
            text(round((brazil.loc.dist1+brazil.loc.dist2),2) + " Miles",820,148);

            image(mexico.logo,790,153);
            text(round((mexico.loc.dist1+mexico.loc.dist2),2) + " Miles",820,173);
        }
        else if(groupAActive[4] || groupAActive[5] || groupAActive[6] || groupAActive[7]){
            image(netherlands.logo,790,75);
            text(round((netherlands.loc.dist1+netherlands.loc.dist2),2) + " Miles",820,92);

            image(chile.logo,790,101);
            text(round((chile.loc.dist1+chile.loc.dist2),2) + " Miles",820,120);

            image(australia.logo,790,127);
            text(round((australia.loc.dist1+australia.loc.dist2),2) + " Miles",820,148);

            image(spain.logo,790,153);
            text(round((spain.loc.dist1+spain.loc.dist2),2) + " Miles",820,173);
        }
        else if(groupAActive[8] || groupAActive[9] || groupAActive[10] || groupAActive[11]){
            image(cote.logo,790,75);
            text(round((cote.loc.dist1+cote.loc.dist2),2) + " Miles",820,92);

            image(greece.logo,790,101);
            text(round((greece.loc.dist1+greece.loc.dist2),2) + " Miles",820,120);

            image(japan.logo,790,127);
            text(round((japan.loc.dist1+japan.loc.dist2),2) + " Miles",820,148);

            image(colombia.logo,790,153);
            text(round((colombia.loc.dist1+colombia.loc.dist2),2) + " Miles",820,173);
        }
        else if(groupAActive[12] || groupAActive[13] || groupAActive[14] || groupAActive[15]){
            image(uruguay.logo,790,75);
            text(round((uruguay.loc.dist1+uruguay.loc.dist2),2) + " Miles",820,92);

            image(england.logo,790,101);
            text(round((england.loc.dist1+england.loc.dist2),2) + " Miles",820,120);

            image(italy.logo,790,127);
            text(round((italy.loc.dist1+italy.loc.dist2),2) + " Miles",820,148);

            image(costa.logo,790,153);
            text(round((costa.loc.dist1+costa.loc.dist2),2) + " Miles",820,173);
        }
        else if(groupAActive[16] || groupAActive[17] || groupAActive[18] || groupAActive[19]){
            image(switzerland.logo,790,75);
            text(round((switzerland.loc.dist1+switzerland.loc.dist2),2) + " Miles",820,92);

            image(france.logo,790,101);
            text(round((france.loc.dist1+france.loc.dist2),2) + " Miles",820,120);

            image(honduras.logo,790,127);
            text(round((honduras.loc.dist1+honduras.loc.dist2),2) + " Miles",820,148);

            image(ecuador.logo,790,153);
            text(round((ecuador.loc.dist1+ecuador.loc.dist2),2) + " Miles",820,173);
        }
        else if(groupAActive[20] || groupAActive[21] || groupAActive[22] || groupAActive[23]){
            image(bosnia.logo,790,75);
            text(round((bosnia.loc.dist1+bosnia.loc.dist2),2) + " Miles",820,92);

            image(nigeria.logo,790,101);
            text(round((nigeria.loc.dist1+nigeria.loc.dist2),2) + " Miles",820,120);

            image(iran.logo,790,127);
            text(round((iran.loc.dist1+iran.loc.dist2),2) + " Miles",820,148);

            image(argentina.logo,790,153);
            text(round((argentina.loc.dist1+argentina.loc.dist2),2) + " Miles",820,173);
        }
        else if(groupAActive[24] || groupAActive[25] || groupAActive[26] || groupAActive[27]){
            image(usa.logo,790,75);
            text(round((usa.loc.dist1+usa.loc.dist2) ,2)+ " Miles",820,92);

            image(portugal.logo,790,101);
            text(round((portugal.loc.dist1+portugal.loc.dist2),2) + " Miles",820,120);

            image(ghana.logo,790,127);
            text(round((ghana.loc.dist1+ghana.loc.dist2) ,2)+ " Miles",820,148);

            image(germany.logo,790,153);
            text(round((germany.loc.dist1+germany.loc.dist2),2) + " Miles",820,173);
        }
        else if(groupAActive[28] || groupAActive[29] || groupAActive[30] || groupAActive[31]){
            image(korea.logo,790,75);
            text(round((korea.loc.dist1+korea.loc.dist2),2) + " Miles",820,92);

            image(belgium.logo,790,101);
            text(round((belgium.loc.dist1+belgium.loc.dist2),2) + " Miles",820,120);

            image(russia.logo,790,127);
            text(round((russia.loc.dist1+russia.loc.dist2) ,2)+ " Miles",820,148);

            image(algeria.logo,790,153);
            text(round((algeria.loc.dist1+algeria.loc.dist2),2) + " Miles",820,173);
        }
    }



    boolean mouseLock = false;

    //Used for teams
    public class MapMarker{

        float x1, y1;
        float x2, y2;
        float x3, y3;
        float x4, y4;

        float xTemp, yTemp;

        float dist1,dist2,dist3,dist4;


        float r,g,b;
        int lr;


        PImage teamLogo;

        float deltaX1,deltaY1;
        float deltaX2,deltaY2;
        float deltaX3,deltaY3;
        float deltaX4,deltaY4;


        String name;
        boolean visible = false;
        int currentCity;
        ArrayList<Marker> citiesToGoTo = new ArrayList<Marker>();

        public MapMarker(PImage logo, String name, float r, float g, float b, int lr, float dist1, float dist2, float dist3, float dist4){

            this.r = r;
            this.g = g;
            this.b = b;
            currentCity = 0;
            teamLogo = logo;
            this.name = name;

            this.dist1 = dist1;
            this.dist2 = dist2;
            this.dist3 = dist3;
            this.dist4 = dist4;


            this.lr = lr;

        }

        //Sets the x1,y1, x2,y2....
        public void setPositions(){
            for(int i =0; i < citiesToGoTo.size();i++){
                Marker currentCity = citiesToGoTo.get(i);
                if(i == 0){
                    x1 = currentCity.x;
                    y1 = currentCity.y;
                }
                else if(i == 1){
                    x2 = currentCity.x;
                    y2 = currentCity.y;

                    deltaX1 = (x2 - x1)/80.0f;
                    deltaY1 = (y2 - y1)/80.0f;
                }
                else if(i == 2){
                    x3 = currentCity.x;
                    y3 = currentCity.y;

                    deltaX2 = (x3 - x2)/80.0f;
                    deltaY2 = (y3 - y2)/80.0f;
                }
                else if(i == 3){
                    x4 = currentCity.x;
                    y4 = currentCity.y;

                    deltaX3 = (x4 - x3)/80.0f;
                    deltaY3 = (y3 - y3)/80.0f;
                }
            }

            xTemp = x1;
            yTemp = y1;
        }


        public void draw(){
            if(currentCity >= 0){
                if(currentCity == 0){
                    strokeWeight(5);

                    stroke(r,g,b);

                    line(x1,y1,xTemp,yTemp);

                    xTemp += deltaX1;
                    yTemp += deltaY1;

                    if( (y2 - 4) < yTemp && yTemp < (y2 +4) && (x2 - 4) < xTemp && xTemp < (x2 + 4) ){
                        currentCity = 1;
                        xTemp = x2;
                        yTemp = y2;
                    }


                }
                else{

                    if(mouseIsOverLine(x1,y1,x2,y2) && !lineLock){
                        if(mousePressed && !mouseLock){
                            float t = r;
                            r = g;
                            g = b;
                            b = t;
                            mouseLock = true;
                        }
                        lineLock = true;
                        text(dist1 + " Miles",320,92);
                        strokeWeight(8);
                        stroke(g,b,r);

                    }
                    else{
                        strokeWeight(5);
                        stroke(r,g,b);
                    }
                    line(x1,y1,x2,y2);

                }
            }

            if(currentCity >= 1){
                if(currentCity == 1){
                    strokeWeight(5);
                    stroke(r,g,b);
                    line(x2,y2,xTemp,yTemp);

                    xTemp += deltaX2;
                    yTemp += deltaY2;

                    if( (y3 - 4) < yTemp && yTemp < (y3 + 4) && (x3 - 4) < xTemp && xTemp < (x3 + 4) ){
                        currentCity = 2;
                    }

                }
                else{
                    if(mouseIsOverLine(x2,y2,x3,y3) && !lineLock){
                        if(mousePressed && !mouseLock){
                            float t = r;
                            r = g;
                            g = b;
                            b = t;
                            mouseLock = true;
                        }
                        lineLock = true;
                        text(dist2 + " Miles",320,92);
                        strokeWeight(8);
                        stroke(g,b,r);
                    }
                    else{
                        strokeWeight(5);
                        stroke(r,g,b);
                    }
                    line(x2,y2,x3,y3);
                    currentCity = 2;
                }
            }


        }


        public void drawIcon(){
            if(currentCity == 2){
                if(lr == 1){
                    image(teamLogo,x3-20,y3,18,18);
                }
                else{
                    image(teamLogo,x3,y3,18,18);

                }
            }
            if(currentCity < 2)
                image(teamLogo,xTemp - 9f,yTemp,18,18);


        }

        public void makeVis(){
            visible = true;
        }

        public void makeInVis(){
            visible = false;
        }

        boolean mouseIsOverLine(float x1, float y1, float x2, float y2) {
            float d = dist(x1, y1, x2, y2);
            float d1 = dist(x1, y1, mouseX, mouseY);
            float d2 = dist(x2, y2, mouseX, mouseY);

            // distance between vertices must be similar to sum of distances from each vertex to mouse
            if (d1 + d2 < d + 1) {
                return true;
            }

            return false;
        }
    }

    public class Marker{
        float x, y;
        float r;
        String name;


        public Marker(float x, float y, float r, String name) {
            this.r = r;
            this.x = x;
            this.y = y;
            this.name = name;
        }

        public void draw(){
            noStroke();
            fill(255,0,255);
            ellipse(x,y,r-5,r-5);
            fill(255);

        }


        public boolean isHovered(){
            if(mouseX >= x-4 && mouseX <= x+4 && mouseY <= y + 4 && mouseY>= y-4){
                return true;
            }
            else{
                return false;
            }
        }
        public void drawLabel(){
            fill(0);
            textFont(font, 17);
            if(name.equals("Sao Paulo")){
                text(name,x-35,y-6);
            }
            else{
                fill(0);
                text(name,x-25,y-8);
            }
            textFont(font, 20);
        }

    }


    public void keyPressed() {
        if (key == ' ') {
            toggleLabel = !toggleLabel;
        }
        else if(key == BACKSPACE || key == DELETE){
            countryChosen = 0;
            drawMap = true;
            drawCountry = false;
        }
    }


    public void mouseReleased(){
        mouseLock = false;

    }

    public void mouseClicked(){

        if(drawMap)
        {
            if(groupAHovered) {
                groupAActive[1] = !groupAActive[0];
                groupAActive[2] = !groupAActive[0];
                groupAActive[3] = !groupAActive[0];
                groupAActive[0] = !groupAActive[0];
                if(!groupAActive[0]){
                    brazil.loc.setPositions();
                    brazil.loc.currentCity = 0;

                    mexico.loc.setPositions();
                    mexico.loc.currentCity = 0;

                    cameroon.loc.setPositions();
                    cameroon.loc.currentCity = 0;

                    croatia.loc.setPositions();
                    croatia.loc.currentCity = 0;
                }
                for(int i = 4; i < 32; i++){
                    groupAActive[i] = false;
                }
            }
            else if(brazil.isHovered()){
                groupAActive[0] = !groupAActive[0];
                if(!groupAActive[0]){
                    brazil.loc.setPositions();
                    brazil.loc.currentCity = 0;
                }

                for(int i = 4; i < 32; i++){
                    groupAActive[i] = false;
                }
            }
            else if(mexico.isHovered()){
                groupAActive[1] = !groupAActive[1];
                if(!groupAActive[1]){
                    mexico.loc.setPositions();
                    mexico.loc.currentCity = 0;
                }
                for(int i = 4; i < 32; i++){
                    groupAActive[i] = false;
                }
            }
            else if(croatia.isHovered()){
                groupAActive[2] = !groupAActive[2];
                if(!groupAActive[2]){
                    croatia.loc.setPositions();
                    croatia.loc.currentCity = 0;
                }
                for(int i = 4; i < 32; i++){
                    groupAActive[i] = false;
                }

            }
            else if(cameroon.isHovered()){
                groupAActive[3] = !groupAActive[3];
                if(!groupAActive[3]){
                    cameroon.loc.setPositions();
                    cameroon.loc.currentCity = 0;
                }
                for(int i = 4; i < 32; i++){
                    groupAActive[i] = false;
                }
            }
            else if(groupBHovered) {
                groupAActive[5] = !groupAActive[4];
                groupAActive[6] = !groupAActive[4];
                groupAActive[7] = !groupAActive[4];
                groupAActive[4] = !groupAActive[4];
                if(!groupAActive[4]){
                    spain.loc.setPositions();
                    spain.loc.currentCity = 0;

                    australia.loc.setPositions();
                    australia.loc.currentCity = 0;

                    netherlands.loc.setPositions();
                    netherlands.loc.currentCity = 0;

                    chile.loc.setPositions();
                    chile.loc.currentCity = 0;
                }
                for(int i = 0; i < 4; i++){
                    groupAActive[i] = false;

                }
                for(int i = 8; i < 32; i++){
                    groupAActive[i] = false;

                }
            }
            else if(spain.isHovered()){
                groupAActive[4] = !groupAActive[4];
                if(!groupAActive[4]){
                    spain.loc.setPositions();
                    spain.loc.currentCity = 0;
                }

                for(int i = 0; i < 4; i++){
                    groupAActive[i] = false;

                }
                for(int i = 8; i < 32; i++){
                    groupAActive[i] = false;

                }
            }
            else if(australia.isHovered()){
                groupAActive[5] = !groupAActive[5];
                if(!groupAActive[5]){
                    australia.loc.setPositions();
                    australia.loc.currentCity = 0;
                }
                for(int i = 0; i < 4; i++){
                    groupAActive[i] = false;

                }
                for(int i = 8; i < 32; i++){
                    groupAActive[i] = false;

                }
            }
            else if(netherlands.isHovered()){
                groupAActive[6] = !groupAActive[6];
                if(!groupAActive[6]){
                    netherlands.loc.setPositions();
                    netherlands.loc.currentCity = 0;
                }
                for(int i = 0; i < 4; i++){
                    groupAActive[i] = false;

                }
                for(int i = 8; i < 32; i++){
                    groupAActive[i] = false;

                }

            }
            else if(chile.isHovered()){
                groupAActive[7] = !groupAActive[7];
                if(!groupAActive[7]){
                    chile.loc.setPositions();
                    chile.loc.currentCity = 0;
                }
                for(int i = 0; i < 4; i++){
                    groupAActive[i] = false;

                }
                for(int i = 8; i < 32; i++){
                    groupAActive[i] = false;

                }
            }
            else if(groupCHovered) {
                groupAActive[9] = !groupAActive[8];
                groupAActive[10] = !groupAActive[8];
                groupAActive[11] = !groupAActive[8];
                groupAActive[8] = !groupAActive[8];
                if(!groupAActive[8]){
                    colombia.loc.setPositions();
                    colombia.loc.currentCity = 0;

                    japan.loc.setPositions();
                    japan.loc.currentCity = 0;

                    cote.loc.setPositions();
                    cote.loc.currentCity = 0;

                    greece.loc.setPositions();
                    greece.loc.currentCity = 0;
                }
                for(int i = 0; i < 8; i++){
                    groupAActive[i] = false;

                }
                for(int i = 12; i < 32; i++){
                    groupAActive[i] = false;

                }
            }
            else if(colombia.isHovered()){
                groupAActive[8] = !groupAActive[8];
                if(!groupAActive[8]){
                    colombia.loc.setPositions();
                    colombia.loc.currentCity = 0;
                }

                for(int i = 0; i < 8; i++){
                    groupAActive[i] = false;

                }
                for(int i = 12; i < 32; i++){
                    groupAActive[i] = false;

                }
            }
            else if(japan.isHovered()){
                groupAActive[9] = !groupAActive[9];
                if(!groupAActive[9]){
                    japan.loc.setPositions();
                    japan.loc.currentCity = 0;
                }
                for(int i = 0; i < 8; i++){
                    groupAActive[i] = false;

                }
                for(int i = 12; i < 32; i++){
                    groupAActive[i] = false;

                }
            }
            else if(cote.isHovered()){
                groupAActive[10] = !groupAActive[10];
                if(!groupAActive[10]){
                    cote.loc.setPositions();
                    cote.loc.currentCity = 0;
                }
                for(int i = 0; i < 8; i++){
                    groupAActive[i] = false;

                }
                for(int i = 12; i < 32; i++){
                    groupAActive[i] = false;

                }

            }
            else if(greece.isHovered()){
                groupAActive[11] = !groupAActive[11];
                if(!groupAActive[11]){
                    greece.loc.setPositions();
                    greece.loc.currentCity = 0;
                }
                for(int i = 0; i < 8; i++){
                    groupAActive[i] = false;

                }
                for(int i = 12; i < 32; i++){
                    groupAActive[i] = false;

                }
            }
            else if(groupDHovered) {
                groupAActive[13] = !groupAActive[12];
                groupAActive[14] = !groupAActive[12];
                groupAActive[15] = !groupAActive[12];
                groupAActive[12] = !groupAActive[12];
                if(!groupAActive[12]){
                    england.loc.setPositions();
                    england.loc.currentCity = 0;

                    italy.loc.setPositions();
                    italy.loc.currentCity = 0;

                    costa.loc.setPositions();
                    costa.loc.currentCity = 0;

                    uruguay.loc.setPositions();
                    uruguay.loc.currentCity = 0;
                }
                for(int i = 0; i < 12; i++){
                    groupAActive[i] = false;

                }
                for(int i = 16; i < 32; i++){
                    groupAActive[i] = false;

                }
            }
            else if(england.isHovered()){
                groupAActive[12] = !groupAActive[12];
                if(!groupAActive[12]){
                    england.loc.setPositions();
                    england.loc.currentCity = 0;
                }
                for(int i = 0; i < 12; i++){
                    groupAActive[i] = false;

                }
                for(int i = 16; i < 32; i++){
                    groupAActive[i] = false;

                }
            }
            else if(italy.isHovered()){
                groupAActive[13] = !groupAActive[13];
                if(!groupAActive[13]){
                    italy.loc.setPositions();
                    italy.loc.currentCity = 0;
                }
                for(int i = 0; i < 12; i++){
                    groupAActive[i] = false;

                }
                for(int i = 16; i < 32; i++){
                    groupAActive[i] = false;

                }
            }
            else if(costa.isHovered()){
                groupAActive[14] = !groupAActive[14];
                if(!groupAActive[14]){
                    costa.loc.setPositions();
                    costa.loc.currentCity = 0;
                }
                for(int i = 0; i < 12; i++){
                    groupAActive[i] = false;

                }
                for(int i = 16; i < 32; i++){
                    groupAActive[i] = false;

                }

            }
            else if(uruguay.isHovered()){
                groupAActive[15] = !groupAActive[15];
                if(!groupAActive[15]){
                    uruguay.loc.setPositions();
                    uruguay.loc.currentCity = 0;
                }
                for(int i = 0; i < 12; i++){
                    groupAActive[i] = false;

                }
                for(int i = 16; i < 32; i++){
                    groupAActive[i] = false;

                }
            }
            else if(groupEHovered) {
                groupAActive[17] = !groupAActive[16];
                groupAActive[18] = !groupAActive[16];
                groupAActive[19] = !groupAActive[16];
                groupAActive[16] = !groupAActive[16];
                if(!groupAActive[16]){
                    switzerland.loc.setPositions();
                    switzerland.loc.currentCity = 0;

                    france.loc.setPositions();
                    france.loc.currentCity = 0;

                    honduras.loc.setPositions();
                    honduras.loc.currentCity = 0;

                    ecuador.loc.setPositions();
                    ecuador.loc.currentCity = 0;
                }
                for(int i = 0; i < 16; i++){
                    groupAActive[i] = false;

                }
                for(int i = 20; i < 32; i++){
                    groupAActive[i] = false;
                }
            }
            else if(switzerland.isHovered()){
                groupAActive[16] = !groupAActive[16];
                if(!groupAActive[16]){
                    switzerland.loc.setPositions();
                    switzerland.loc.currentCity = 0;
                }
                for(int i = 0; i < 16; i++){
                    groupAActive[i] = false;

                }
                for(int i = 20; i < 32; i++){
                    groupAActive[i] = false;

                }
            }
            else if(france.isHovered()){
                groupAActive[17] = !groupAActive[17];
                if(!groupAActive[17]){
                    france.loc.setPositions();
                    france.loc.currentCity = 0;
                }
                for(int i = 0; i < 16; i++){
                    groupAActive[i] = false;

                }
                for(int i = 20; i < 32; i++){
                    groupAActive[i] = false;

                }
            }
            else if(honduras.isHovered()){
                groupAActive[18] = !groupAActive[18];
                if(!groupAActive[18]){
                    honduras.loc.setPositions();
                    honduras.loc.currentCity = 0;
                }

                for(int i = 0; i < 16; i++){
                    groupAActive[i] = false;

                }
                for(int i = 20; i < 32; i++){
                    groupAActive[i] = false;

                }
            }
            else if(ecuador.isHovered()){
                groupAActive[19] = !groupAActive[19];
                if(!groupAActive[19]){
                    ecuador.loc.setPositions();
                    ecuador.loc.currentCity = 0;
                }
                for(int i = 0; i < 16; i++){
                    groupAActive[i] = false;

                }
                for(int i = 20; i < 32; i++){
                    groupAActive[i] = false;

                }
            }
            else if(groupFHovered) {
                groupAActive[21] = !groupAActive[20];
                groupAActive[22] = !groupAActive[20];
                groupAActive[23] = !groupAActive[20];
                groupAActive[20] = !groupAActive[20];
                if(!groupAActive[20]){
                    argentina.loc.setPositions();
                    argentina.loc.currentCity = 0;

                    bosnia.loc.setPositions();
                    bosnia.loc.currentCity = 0;

                    iran.loc.setPositions();
                    iran.loc.currentCity = 0;

                    nigeria.loc.setPositions();
                    nigeria.loc.currentCity = 0;
                }
                for(int i = 0; i < 20; i++){
                    groupAActive[i] = false;

                }
                for(int i = 24; i < 32; i++){
                    groupAActive[i] = false;

                }
            }
            else if(argentina.isHovered()){
                groupAActive[20] = !groupAActive[20];
                if(!groupAActive[20]){
                    argentina.loc.setPositions();
                    argentina.loc.currentCity = 0;
                }
                for(int i = 0; i < 20; i++){
                    groupAActive[i] = false;

                }
                for(int i = 24; i < 32; i++){
                    groupAActive[i] = false;

                }
            }
            else if(bosnia.isHovered()){
                groupAActive[21] = !groupAActive[21];
                if(!groupAActive[21]){
                    bosnia.loc.setPositions();
                    bosnia.loc.currentCity = 0;
                }
                for(int i = 0; i < 20; i++){
                    groupAActive[i] = false;

                }
                for(int i = 24; i < 32; i++){
                    groupAActive[i] = false;

                }
            }
            else if(iran.isHovered()){
                groupAActive[22] = !groupAActive[22];
                if(!groupAActive[22]){
                    iran.loc.setPositions();
                    iran.loc.currentCity = 0;
                }
                for(int i = 0; i < 20; i++){
                    groupAActive[i] = false;

                }
                for(int i = 24; i < 32; i++){
                    groupAActive[i] = false;

                }

            }
            else if(nigeria.isHovered()){
                groupAActive[23] = !groupAActive[23];
                if(!groupAActive[23]){
                    nigeria.loc.setPositions();
                    nigeria.loc.currentCity = 0;
                }
                for(int i = 0; i < 20; i++){
                    groupAActive[i] = false;

                }
                for(int i = 24; i < 32; i++){
                    groupAActive[i] = false;

                }
            }
            else if(groupGHovered) {
                groupAActive[25] = !groupAActive[24];
                groupAActive[26] = !groupAActive[24];
                groupAActive[27] = !groupAActive[24];
                groupAActive[24] = !groupAActive[24];
                if(!groupAActive[24]){
                    germany.loc.setPositions();
                    germany.loc.currentCity = 0;

                    ghana.loc.setPositions();
                    ghana.loc.currentCity = 0;

                    portugal.loc.setPositions();
                    portugal.loc.currentCity = 0;

                    usa.loc.setPositions();
                    usa.loc.currentCity = 0;
                }
                for(int i = 0; i < 24; i++){
                    groupAActive[i] = false;

                }
                for(int i = 28; i < 32; i++){
                    groupAActive[i] = false;

                }
            }
            else if(germany.isHovered()){
                groupAActive[24] = !groupAActive[24];
                if(!groupAActive[24]){
                    germany.loc.setPositions();
                    germany.loc.currentCity = 0;
                }
                for(int i = 0; i < 24; i++){
                    groupAActive[i] = false;

                }
                for(int i = 28; i < 32; i++){
                    groupAActive[i] = false;

                }
            }
            else if(ghana.isHovered()){
                groupAActive[25] = !groupAActive[25];
                if(!groupAActive[25]){
                    ghana.loc.setPositions();
                    ghana.loc.currentCity = 0;
                }
                for(int i = 0; i < 24; i++){
                    groupAActive[i] = false;

                }
                for(int i = 28; i < 32; i++){
                    groupAActive[i] = false;

                }
            }
            else if(portugal.isHovered()){
                groupAActive[26] = !groupAActive[26];
                if(!groupAActive[26]){
                    portugal.loc.setPositions();
                    portugal.loc.currentCity = 0;
                }

                for(int i = 0; i < 24; i++){
                    groupAActive[i] = false;

                }
                for(int i = 28; i < 32; i++){
                    groupAActive[i] = false;

                }
            }
            else if(usa.isHovered()){
                groupAActive[27] = !groupAActive[27];
                if(!groupAActive[27]){
                    usa.loc.setPositions();
                    usa.loc.currentCity = 0;
                }
                for(int i = 0; i < 24; i++){
                    groupAActive[i] = false;

                }
                for(int i = 28; i < 32; i++){
                    groupAActive[i] = false;

                }
            }
            else if(groupHHovered) {
                groupAActive[29] = !groupAActive[28];
                groupAActive[30] = !groupAActive[28];
                groupAActive[31] = !groupAActive[28];
                groupAActive[28] = !groupAActive[28];
                if(!groupAActive[28]){
                    algeria.loc.setPositions();
                    algeria.loc.currentCity = 0;

                    korea.loc.setPositions();
                    korea.loc.currentCity = 0;

                    russia.loc.setPositions();
                    russia.loc.currentCity = 0;

                    belgium.loc.setPositions();
                    belgium.loc.currentCity = 0;
                }
                for(int i = 0; i < 28; i++){
                    groupAActive[i] = false;

                }
            }
            else if(algeria.isHovered()){
                groupAActive[28] = !groupAActive[28];
                if(!groupAActive[28]){
                    algeria.loc.setPositions();
                    algeria.loc.currentCity = 0;
                }
                for(int i = 0; i < 28; i++){
                    groupAActive[i] = false;

                }
            }
            else if(korea.isHovered()){
                groupAActive[29] = !groupAActive[29];
                if(!groupAActive[29]){
                    korea.loc.setPositions();
                    korea.loc.currentCity = 0;
                }
                for(int i = 0; i < 28; i++){
                    groupAActive[i] = false;

                }
            }
            else if(russia.isHovered()){
                groupAActive[30] = !groupAActive[30];
                if(!groupAActive[30]){
                    russia.loc.setPositions();
                    russia.loc.currentCity = 0;
                }
                for(int i = 0; i < 28; i++){
                    groupAActive[i] = false;

                }
            }
            else if(belgium.isHovered()){
                groupAActive[31] = !groupAActive[31];
                if(!groupAActive[31]){
                    belgium.loc.setPositions();
                    belgium.loc.currentCity = 0;
                }
                for(int i = 0; i < 28; i++){
                    groupAActive[i] = false;

                }
            }


            if(brazil.isTextHovered()){
                countryChosen = brazil.id;
                drawMap = false;
                drawCountry = true;
                drawMatch = false;
                cursor(ARROW);
            }
            else if(mexico.isTextHovered()){
                countryChosen = 2;
                drawMap = false;
                drawCountry = true;
                drawMatch = false;
                cursor(ARROW);
            }
            else if(croatia.isTextHovered()){
                countryChosen = 1;
                drawMap = false;
                drawCountry = true;
                drawMatch = false;
                cursor(ARROW);
            }
            else if(cameroon.isTextHovered()){
                countryChosen = 3;
                drawMap = false;
                drawCountry = true;
                drawMatch = false;
                cursor(ARROW);
            }
            else if(spain.isTextHovered()){
                countryChosen = 4;
                drawMap = false;
                drawCountry = true;
                drawMatch = false;
                cursor(ARROW);
            }
            else if(netherlands.isTextHovered()){
                countryChosen = 5;
                drawMap = false;
                drawCountry = true;
                drawMatch = false;
                cursor(ARROW);
            }
            else if(chile.isTextHovered()){
                countryChosen = 6;
                drawMap = false;
                drawCountry = true;
                drawMatch = false;
                cursor(ARROW);
            }
            else if(australia.isTextHovered()){
                countryChosen = 7;
                drawMap = false;
                drawCountry = true;
                drawMatch = false;
                cursor(ARROW);
            }
            else if(colombia.isTextHovered()){
                countryChosen = 8;
                drawMap = false;
                drawCountry = true;
                drawMatch = false;
                cursor(ARROW);
            }
            else if(greece.isTextHovered()){
                countryChosen = 9;
                drawMap = false;
                drawCountry = true;
                drawMatch = false;
                cursor(ARROW);
            }
            else if(cote.isTextHovered()){
                countryChosen = 10;
                drawMap = false;
                drawCountry = true;
                drawMatch = false;
                cursor(ARROW);
            }
            else if(japan.isTextHovered()){
                countryChosen = 11;
                drawMap = false;
                drawCountry = true;
                drawMatch = false;
                cursor(ARROW);
            }
            else if(uruguay.isTextHovered()){
                countryChosen = 12;
                drawMap = false;
                drawCountry = true;
                drawMatch = false;
                cursor(ARROW);
            }
            else if(costa.isTextHovered()){
                countryChosen = 13;
                drawMap = false;
                drawCountry = true;
                drawMatch = false;
                cursor(ARROW);
            }
            else if(england.isTextHovered()){
                countryChosen = 14;
                drawMap = false;
                drawCountry = true;
                drawMatch = false;
                cursor(ARROW);
            }
            else if(italy.isTextHovered()){
                countryChosen = 15;
                drawMap = false;
                drawCountry = true;
                drawMatch = false;
                cursor(ARROW);
            }
            else if(switzerland.isTextHovered()){
                countryChosen = 16;
                drawMap = false;
                drawCountry = true;
                drawMatch = false;
                cursor(ARROW);
            }
            else if(ecuador.isTextHovered()){
                countryChosen = 17;
                drawMap = false;
                drawCountry = true;
                drawMatch = false;
                cursor(ARROW);
            }
            else if(france.isTextHovered()){
                countryChosen = 18;
                drawMap = false;
                drawCountry = true;
                drawMatch = false;
                cursor(ARROW);
            }
            else if(honduras.isTextHovered()){
                countryChosen = 19;
                drawMap = false;
                drawCountry = true;
                drawMatch = false;
                cursor(ARROW);
            }
            else if(argentina.isTextHovered()){
                countryChosen = 20;
                drawMap = false;
                drawCountry = true;
                drawMatch = false;
                cursor(ARROW);
            }
            else if(bosnia.isTextHovered()){
                countryChosen = 21;
                drawMap = false;
                drawCountry = true;
                drawMatch = false;
                cursor(ARROW);
            }
            else if(iran.isTextHovered()){
                countryChosen = 22;
                drawMap = false;
                drawCountry = true;
                drawMatch = false;
                cursor(ARROW);
            }
            else if(nigeria.isTextHovered()){
                countryChosen = 23;
                drawMap = false;
                drawCountry = true;
                drawMatch = false;
                cursor(ARROW);
            }
            else if(germany.isTextHovered()){
                countryChosen = 24;
                drawMap = false;
                drawCountry = true;
                drawMatch = false;
                cursor(ARROW);
            }
            else if(portugal.isTextHovered()){
                countryChosen = 25;
                drawMap = false;
                drawCountry = true;
                drawMatch = false;
                cursor(ARROW);
            }
            else if(ghana.isTextHovered()){
                countryChosen = 26;
                drawMap = false;
                drawCountry = true;
                drawMatch = false;
                cursor(ARROW);
            }
            else if(usa.isTextHovered()){
                countryChosen = 27;
                drawMap = false;
                drawCountry = true;
                drawMatch = false;
                cursor(ARROW);
            }
            else if(belgium.isTextHovered()){
                countryChosen = 28;
                drawMap = false;
                drawCountry = true;
                drawMatch = false;
                cursor(ARROW);
            }
            else if(algeria.isTextHovered()){
                countryChosen = 29;
                drawMap = false;
                drawCountry = true;
                drawMatch = false;
                cursor(ARROW);
            }
            else if(russia.isTextHovered()){
                countryChosen = 30;
                drawMap = false;
                drawCountry = true;
                drawMatch = false;
                cursor(ARROW);
            }
            else if(korea.isTextHovered()){
                countryChosen = 31;
                drawMap = false;
                drawCountry = true;
                drawMatch = false;
                cursor(ARROW);
            }
        }


        else if(drawCountry)
        {
            if(firstMatch.isHovered() || firstMatch.isTextHovered())
            {
                drawMap = false;
                drawCountry = false;
                drawMatch = true;
            }
            else if(secondMatch.isHovered() || secondMatch.isTextHovered())
            {
                drawMap = false;
                drawCountry = false;
                drawMatch = true;
            }
            else if(thirdMatch.isHovered() || thirdMatch.isTextHovered())
            {
                drawMap = false;
                drawCountry = false;
                drawMatch = true;
            }
            else if(mouseX >= 1074 && mouseX <= 1262 && mouseY <= 201.5 && mouseY>= 12.5)
            {

                drawMap = true;
                drawCountry = false;
                drawMatch = false;
            }
        }
	    else if(drawMatch)
        {
            if(mouseX >= 200 && mouseX <= 425 && mouseY >= 200 && mouseY <= 300)
            {
                drawMap = false;
                drawCountry = true;
                drawMatch = false;
            }
            else if(mouseX >= 850 && mouseX <= 1020 && mouseY >= 200 && mouseY <= 300)
            {
                drawMap = false;
                drawCountry = true;
                drawMatch = false;
                countryChosen = countryChosen2;
            }
            else if (mouseX >= 540 && mouseX <= 700 && mouseY <= 250  && mouseY>= 10)
            {
                drawMap = true;
                drawCountry = false;
                drawMatch = false;
            }
            else if (mouseX >= 1200 && mouseX <= 1280 && mouseY >= 10 && mouseY <= 70)
            {
                if (showHelp == true)
                {
                    drawMap = false;
                    drawCountry = false;
                    drawMatch = true;
                    showHelp = false;
                }
                else
                {
                    drawMap = false;
                    drawCountry = false;
                    drawMatch = true;
                    showHelp = true;
                }
            }
        }



    }

    public class TeamIcon{
        PImage logo;
        String name;
        float x,y;

        int id;
        MapMarker loc;

        public TeamIcon(String name, float x, float y, float r, float g, float b, int lr, int id, float dist1, float dist2, float dist3, float dist4) {
            this.name = name;
            this.x = x;
            this.y = y;
            logo = loadImage("TeamPhotos/"+name+"Team.png");
            this.loc = new MapMarker(logo,name,r,g,b, lr, dist1, dist2, dist3, dist4);
            this.id = id;
        }


        public void draw(){
            if(isHovered()){
                cursorChanged = true;
                cursor(HAND);
                image(logo,x,y,30,30);
                fill(0);
                text(name, x+30,y+20);
            }
            else if(isTextHovered()){
                cursorChanged = true;
                cursor(HAND);
                image(logo,x,y);
                fill(255,20,20);
                text(name, x+30,y+20);
            }
            else{
                if(!cursorChanged){
                    cursor(ARROW);
                }
                image(logo, this.x, y);
                fill(0);
                text(name, this.x+30,y+20);
            }
        }

        public boolean isHovered(){
            if(mouseX >= x && mouseX <= x+26 && mouseY <= y + 26 && mouseY>= y){
                return true;
            }
            else{
                return false;
            }
        }

        public boolean isTextHovered(){
            if(mouseX >= x + 20 && mouseX <= x+100 && mouseY <= y + 26 && mouseY>= y){
                return true;
            }
            else{
                return false;
            }
        }

    }


    //Williams Country Code Stuff
    /****** William's data setup ********/

    int matchIndex = 0;//wbaron
    PFont countryFont;
    Table rankingTable;
    PImage teamSelected;
    PImage teamNameBackground;
    PImage worldCupLogo;
    PImage plotBackground;
    PImage infoBackground;
    PImage hRank;
    PImage lRank;
    PImage matchBackground;
    XYChart lineChart;
    XYChart lineChart2;
    XYChart lineChart3;
    XYChart lineChart4;
    ArrayList<String> datesAL;
    Object[] datesArray;
    Set<String> dates;
    ControlP5 cp5;
    int minVal = 0;
    int maxVal = 233;
    Range range;

    groupTeamIcon firstTeam;
    groupTeamIcon secondTeam;
    groupTeamIcon thirdTeam;
    groupTeamIcon fourthTeam;
    PImage graphGroup;

    groupMatch firstMatch;
    groupMatch secondMatch;
    groupMatch thirdMatch;



    //Williams draw and setup

    public void countrySetup(){
        cp5 = new ControlP5(this);

        range = cp5.addRange("DateRange")
                // disable broadcasting since setRange and setRangeValues will trigger an event
                .setBroadcast(false)
                .setPosition(350,(float)507.5)
                .setSize(566,15)
                .setHandleSize(20)
                .setRange(0,233)
                .setRangeValues(0,233)
                        // after the initialization we turn broadcast back on again
                .setBroadcast(true)
                .setColorForeground(color(255,200))
                .setColorBackground(color(255,40));

        //noStroke();

        dates = new LinkedHashSet<String>();

        countryFont = loadFont("CooperBlack-65.vlw");

        rankingTable = loadTable("WorldRankingHistory.csv","header");

        for(TableRow row : rankingTable.rows())
        {
            String date = row.getString("Date");
            int rank = row.getInt("Ranking");
            String team = row.getString("NationalTeam");

            //println(date + ";" + rank + ";" + pais);

            int index = getTeamIndex(team);

            Ranking temp = new Ranking(date,rank);
            Countries2014.get(index).rankHistory.add(temp);
            if(Countries2014.get(index).highestrank < rank)
            {
                Countries2014.get(index).highestrank = rank;
            }
            if(Countries2014.get(index).lowestRank > rank)
            {
                Countries2014.get(index).lowestRank = rank;
            }
            if(date.equals("May-14"))
            {
                Countries2014.get(index).currentRank = rank;
            }

            dates.add(date);
        }

//        for(Ranking r : Countries2014.get(8).rankHistory)
//        {
//            println(Countries2014.get(8).name + ":" + r.date + ":" + r.rank);
//        }//testing print

//        Iterator<String> itr = dates.iterator();
//        while(itr.hasNext())
//        {
//            String temp = itr.next();
//            println(temp);
//        }//testing print

        //println("how any dates woud we have total: " + dates.size());


        //plot

        firstTeam = new groupTeamIcon("",45,223);
        secondTeam = new groupTeamIcon("",45,293);
        thirdTeam = new groupTeamIcon("",45,363);
        fourthTeam = new groupTeamIcon("",45,433);

        firstMatch = new groupMatch("",963,576,1);
        secondMatch = new groupMatch("",963,651,2);
        thirdMatch = new groupMatch("", 963,721,3);


        lineChart = new XYChart(this);
        lineChart2 = new XYChart(this);
        lineChart3 = new XYChart(this);
        lineChart4 = new XYChart(this);

    }

    public void drawCountry(int c){
        teamView(c);
    }



    public void teamView(int index)
    {
        float max = 0;
        int teamChoice = 0;
        String country = Countries2014.get(index).name;
        //background of country logo
        noStroke();
        fill(192,231,187);
        ellipse(85,75,118,118);
        //country logo
        teamSelected = loadImage("TeamPhotos/"+country+"Main.png");
        image(teamSelected,46,36,78,78);
        //country name background
        teamNameBackground = loadImage("TeamNameBackground.png");
        image(teamNameBackground,(float)164.5,(float)39.5,471,71);
        matchBackground = loadImage("matchBackground.png");
        image(matchBackground,1001,556);
        image(matchBackground,1001,631);
        image(matchBackground,1001,701);

        //country name
        fill(0);
        if(country.equals("Bosnia-Herzegovina"))
        {
            textFont(countryFont, 42);
        }
        else
        {
            textFont(countryFont, 60);
        }
        textAlign(CENTER);
        text(country,400,90);
        //fifa world cup logo
        worldCupLogo = loadImage("WorldCupLogo.png");
        image(worldCupLogo,1074,13);
        //plot title
        fill(192,231,187);
        textFont(countryFont, 40);
        textAlign(CENTER);
        text("FIFA Ranking History",640,147);
        //plotBackground
        plotBackground = loadImage("plotBackground.png");
        image(plotBackground,(float)286.5,(float)168.5);
        range.setLabelVisible(false);

        datesArray = dates.toArray();
        //println(datesArray[dates.size() - 1].toString());//wbaron rememeber

        textFont(countryFont, 20);
        textAlign(CENTER);
        fill(192,231,187);
        text(datesArray[minVal].toString(),300,520);
        text(datesArray[maxVal].toString(),970,520); //wbaron rememeber

        //info setup
        int sizeArray = Countries2014.get(index).rankHistory.size();

        float[] xAxis1 = new float[maxVal - minVal];
        float[] yAxis1 = new float[maxVal - minVal];
        float[] yAxis2 = new float[maxVal - minVal];
        float[] yAxis3 = new float[maxVal - minVal];
        float[] yAxis4 = new float[maxVal - minVal];

        int j;
        int indexFirstRival;
        int indexSecondRival;
        int indexThirdRival;
        int[] high = {0,0,0,0};
        int[] low = {1000,1000,1000,1000};
        //switch for different teams
        switch (index)
        {
            case 0:
            case 1:
            case 2:
            case 3:
                //GUI display for group
                //team choice setup
                graphGroup = loadImage("GroupABig.png");
                image(graphGroup,(float)826.5,178,141,40);
                //group team key for plot
                firstTeam.logo = loadImage("teamPhotos/"+ Countries2014.get(0).name +"Main.png");
                firstTeam.name = Countries2014.get(0).name;
                firstTeam.r = 255;
                firstTeam.g = 240;
                firstTeam.b = 0;
                firstTeam.draw();

                secondTeam.logo = loadImage("teamPhotos/"+ Countries2014.get(1).name +"Main.png");
                secondTeam.name = Countries2014.get(1).name;
                secondTeam.r = 45;
                secondTeam.g = 49;
                secondTeam.b = 133;
                secondTeam.draw();

                thirdTeam.logo = loadImage("teamPhotos/"+ Countries2014.get(2).name +"Main.png");
                thirdTeam.name = Countries2014.get(2).name;
                thirdTeam.r = 193;
                thirdTeam.g = 38;
                thirdTeam.b = 45;
                thirdTeam.draw();

                fourthTeam.logo = loadImage("teamPhotos/"+ Countries2014.get(3).name +"Main.png");
                fourthTeam.name = Countries2014.get(3).name;
                fourthTeam.r = 10;
                fourthTeam.g = 149;
                fourthTeam.b = 75;
                fourthTeam.draw();

                //match fix text
                textFont(countryFont, 30);
                fill(192,231,187);
                textAlign(CENTER);
                text("Match 1",810,586);
                text("Match 2",810,661);
                text("Match 3",810,731);
                // vs text setup
                textFont(countryFont, 30);
                textAlign(CENTER);
                text("Vs",900,586);
                text("Vs",900,661);
                text("Vs",900,731);


                //matches setup
                indexFirstRival = getTeamIndex(Countries2014.get(index).match1);
                firstMatch.logo = loadImage("teamPhotos/" + Countries2014.get(indexFirstRival).name + "Main.png");
                firstMatch.rivalName = Countries2014.get(index).match1;
                firstMatch.draw();

                indexSecondRival = getTeamIndex(Countries2014.get(index).match2);
                secondMatch.logo = loadImage("teamPhotos/" + Countries2014.get(indexSecondRival).name + "Main.png");
                secondMatch.rivalName = Countries2014.get(index).match2;
                secondMatch.draw();

                indexThirdRival = getTeamIndex(Countries2014.get(index).match3);
                thirdMatch.logo = loadImage("teamPhotos/" + Countries2014.get(indexThirdRival).name + "Main.png");
                thirdMatch.rivalName = Countries2014.get(index).match3;
                thirdMatch.draw();

                firstMatch.team1 = index;
                firstMatch.team2 = indexFirstRival;
                secondMatch.team1 = index;
                secondMatch.team2 = indexSecondRival;
                thirdMatch.team1 = index;
                thirdMatch.team2 = indexThirdRival;

                //ranking title
                fill(192,231,187);
                textFont(countryFont, 30);
                textAlign(CENTER);
                text("Worst Ranking",1130,365);
                text("Best Ranking",1130,248);

                //ranking info
                hRank = loadImage("hRankBackground.png");
                lRank = loadImage("lRankBackground.png");
                image(hRank,1093,258,80,80);
                image(lRank,1093,377,80,80);

                //team info
                textFont(countryFont, 25);
                textAlign(LEFT);
                text("Participations in World Cup Finals",48,586);
                text("Current Fifa world ranking",80,661);
                text("Best Result in World Cup finals",62,731);

                //circle for info
                fill(128,210,154);
                ellipse(602,576,66,66);
                fill(192,231,187);
                ellipse(602,576,56,56);

                fill(128,210,154);
                ellipse(602,651,66,66);
                fill(192,231,187);
                ellipse(602,651,56,56);

                infoBackground = loadImage("infoBackground.png");
                image(infoBackground, 480, 700);

                textFont(countryFont, 30);
                textAlign(CENTER);
                fill(0);
                text(Countries2014.get(index).numParticipations,602,586);
                text(Countries2014.get(index).currentRank,602,661);
                text(Countries2014.get(index).bestResult,602,731);



                //data display for group
                j = 0;//array Index
                for(int i = minVal; i < maxVal; i++)
                {
                    xAxis1[j] = j;
                    yAxis1[j] = Countries2014.get(0).rankHistory.get(i).rank;
                    if(high[0] < yAxis1[j])
                    {
                        high[0] = (int)yAxis1[j];
                    }
                    if(low[0] > yAxis1[j])
                    {
                        low[0] = (int)yAxis1[j];
                    }
                    if(max < yAxis1[j])
                    {
                        max = yAxis1[j];
                    }
                    yAxis2[j] = Countries2014.get(1).rankHistory.get(i).rank;
                    if(high[1] < yAxis2[j])
                    {
                        high[1] = (int)yAxis2[j];
                    }
                    if(low[1] > yAxis2[j])
                    {
                        low[1] = (int)yAxis2[j];
                    }
                    if(max < yAxis2[j])
                    {
                        max = yAxis2[j];
                    }
                    yAxis3[j] = Countries2014.get(2).rankHistory.get(i).rank;
                    if(high[2] < yAxis3[j])
                    {
                        high[2] = (int)yAxis3[j];
                    }
                    if(low[2] > yAxis3[j])
                    {
                        low[2] = (int)yAxis3[j];
                    }
                    if(max < yAxis3[j])
                    {
                        max = yAxis3[j];
                    }
                    yAxis4[j] = Countries2014.get(3).rankHistory.get(i).rank;
                    if(high[3] < yAxis4[j])
                    {
                        high[3] = (int)yAxis4[j];
                    }
                    if(low[3] > yAxis4[j])
                    {
                        low[3] = (int)yAxis4[j];
                    }
                    if(max < yAxis4[j])
                    {
                        max = yAxis4[j];
                    }

                    j++;

                }

                //ranking text
                textFont(countryFont, 30);
                textAlign(CENTER);
                fill(0);
                text(high[index],1135,422);
                text(low[index],1135,305);

                //set data
                lineChart.setData(xAxis1,yAxis1);
                lineChart2.setData(xAxis1,yAxis2);
                lineChart3.setData(xAxis1,yAxis3);
                lineChart4.setData(xAxis1,yAxis4);

                //setMin and Max value
                lineChart.setMinY(0);
                lineChart2.setMinY(0);
                lineChart3.setMinY(0);
                lineChart4.setMinY(0);

                lineChart.setMaxY(max);
                lineChart2.setMaxY(max);
                lineChart3.setMaxY(max);
                lineChart4.setMaxY(max);

                //Set Color for all lines
                lineChart.setPointColour(color(firstTeam.r,firstTeam.g,firstTeam.b));
                lineChart.setLineColour(color(firstTeam.r,firstTeam.g,firstTeam.b));
                lineChart2.setPointColour(color(secondTeam.r,secondTeam.g,secondTeam.b));
                lineChart2.setLineColour(color(secondTeam.r,secondTeam.g,secondTeam.b));
                lineChart3.setPointColour(color(thirdTeam.r,thirdTeam.g,secondTeam.b));
                lineChart3.setLineColour(color(thirdTeam.r,thirdTeam.g,thirdTeam.b));
                lineChart4.setPointColour(color(fourthTeam.r,fourthTeam.g,fourthTeam.b));
                lineChart4.setLineColour(color(fourthTeam.r,fourthTeam.g,fourthTeam.b));

                if(index == 0)
                {
                    lineChart.setPointSize(5);
                    lineChart.setLineWidth(5);
                }
                else
                {
                    lineChart.setPointSize(2);
                    lineChart.setLineWidth(2);
                }
                if(index == 1)
                {
                    lineChart2.setPointSize(5);
                    lineChart2.setLineWidth(5);
                }
                else
                {
                    lineChart2.setPointSize(2);
                    lineChart2.setLineWidth(2);
                }
                if(index == 2)
                {
                    lineChart3.setPointSize(5);
                    lineChart3.setLineWidth(5);
                }
                else
                {
                    lineChart3.setPointSize(2);
                    lineChart3.setLineWidth(2);
                }
                if(index == 3)
                {
                    lineChart4.setPointSize(5);
                    lineChart4.setLineWidth(5);
                }
                else
                {
                    lineChart4.setPointSize(2);
                    lineChart4.setLineWidth(2);
                }

                // Axis formatting and labels.
                lineChart.showXAxis(true);
                lineChart.showYAxis(true);
                lineChart2.showXAxis(true);
                lineChart2.showYAxis(true);
                lineChart3.showXAxis(true);
                lineChart3.showYAxis(true);
                lineChart4.showXAxis(true);
                lineChart4.showYAxis(true);

                //showData
                textSize(9);
                lineChart.draw(270,180,707,323);
                lineChart2.draw(270,180,707,323);
                lineChart3.draw(270,180,707,323);
                lineChart4.draw(270,180,707,323);
                break;
            case 4:
            case 5:
            case 6:
            case 7:
                if(index == 4)
                {
                    teamChoice = 0;
                }
                if(index == 5)
                {
                    teamChoice = 1;
                }
                if(index == 6)
                {
                    teamChoice = 2;
                }
                if(index == 7)
                {
                    teamChoice = 3;
                }
                //GUI display for group
                //team choice setup
                graphGroup = loadImage("GroupBBig.png");
                image(graphGroup,(float)826.5,178,141,40);
                //group team key for plot
                firstTeam.logo = loadImage("teamPhotos/"+ Countries2014.get(4).name +"Main.png");
                firstTeam.name = Countries2014.get(4).name;
                firstTeam.r = 249;
                firstTeam.g = 175;
                firstTeam.b = 59;
                firstTeam.draw();

                secondTeam.logo = loadImage("teamPhotos/"+ Countries2014.get(5).name +"Main.png");
                secondTeam.name = Countries2014.get(5).name;
                secondTeam.r = 239;
                secondTeam.g = 239;
                secondTeam.b = 239;
                secondTeam.draw();

                thirdTeam.logo = loadImage("teamPhotos/"+ Countries2014.get(6).name +"Main.png");
                thirdTeam.name = Countries2014.get(6).name;
                thirdTeam.r = 193;
                thirdTeam.g = 38;
                thirdTeam.b = 45;
                thirdTeam.draw();

                fourthTeam.logo = loadImage("teamPhotos/"+ Countries2014.get(7).name +"Main.png");
                fourthTeam.name = Countries2014.get(7).name;
                fourthTeam.r = 46;
                fourthTeam.g = 50;
                fourthTeam.b = 134;
                fourthTeam.draw();

                //match fix text
                textFont(countryFont, 30);
                textAlign(CENTER);
                fill(192,231,187);
                text("Match 1",810,586);
                text("Match 2",810,661);
                text("Match 3",810,731);
                // vs text setup
                textFont(countryFont, 30);
                textAlign(CENTER);
                text("Vs",900,586);
                text("Vs",900,661);
                text("Vs",900,731);


                //matches setup
                indexFirstRival = getTeamIndex(Countries2014.get(index).match1);
                firstMatch.logo = loadImage("teamPhotos/" + Countries2014.get(indexFirstRival).name + "Main.png");
                firstMatch.rivalName = Countries2014.get(index).match1;
                firstMatch.draw();

                indexSecondRival = getTeamIndex(Countries2014.get(index).match2);
                secondMatch.logo = loadImage("teamPhotos/" + Countries2014.get(indexSecondRival).name + "Main.png");
                secondMatch.rivalName = Countries2014.get(index).match2;
                secondMatch.draw();

                indexThirdRival = getTeamIndex(Countries2014.get(index).match3);
                thirdMatch.logo = loadImage("teamPhotos/" + Countries2014.get(indexThirdRival).name + "Main.png");
                thirdMatch.rivalName = Countries2014.get(index).match3;
                thirdMatch.draw();

                firstMatch.team1 = index;
                firstMatch.team2 = indexFirstRival;
                secondMatch.team1 = index;
                secondMatch.team2 = indexSecondRival;
                thirdMatch.team1 = index;
                thirdMatch.team2 = indexThirdRival;

                //ranking title
                fill(192,231,187);
                textFont(countryFont, 30);
                textAlign(CENTER);
                text("Worst Ranking",1130,365);
                text("Best Ranking",1130,248);

                //ranking info
                hRank = loadImage("hRankBackground.png");
                lRank = loadImage("lRankBackground.png");
                image(hRank,1093,258,80,80);
                image(lRank,1093,377,80,80);

                //team info
                textFont(countryFont, 25);
                textAlign(LEFT);
                text("Participations in World Cup Finals",48,586);
                text("Current Fifa world ranking",80,661);
                text("Best Result in World Cup finals",62,731);

                //circle for info
                fill(128,210,154);
                ellipse(602,576,66,66);
                fill(192,231,187);
                ellipse(602,576,56,56);

                fill(128,210,154);
                ellipse(602,651,66,66);
                fill(192,231,187);
                ellipse(602,651,56,56);

                infoBackground = loadImage("infoBackground.png");
                image(infoBackground, 480, 700);

                textFont(countryFont, 30);
                textAlign(CENTER);
                fill(0);
                text(Countries2014.get(index).numParticipations,602,586);
                text(Countries2014.get(index).currentRank,602,661);
                text(Countries2014.get(index).bestResult,602,731);



                //data display for group
                j = 0;//array Index
                for(int i = minVal; i < maxVal; i++)
                {
                    xAxis1[j] = j;
                    yAxis1[j] = Countries2014.get(4).rankHistory.get(i).rank;
                    if(high[0] < yAxis1[j])
                    {
                        high[0] = (int)yAxis1[j];
                    }
                    if(low[0] > yAxis1[j])
                    {
                        low[0] = (int)yAxis1[j];
                    }
                    if(max < yAxis1[j])
                    {
                        max = yAxis1[j];
                    }
                    yAxis2[j] = Countries2014.get(5).rankHistory.get(i).rank;
                    if(high[1] < yAxis2[j])
                    {
                        high[1] = (int)yAxis2[j];
                    }
                    if(low[1] > yAxis2[j])
                    {
                        low[1] = (int)yAxis2[j];
                    }
                    if(max < yAxis2[j])
                    {
                        max = yAxis2[j];
                    }
                    yAxis3[j] = Countries2014.get(6).rankHistory.get(i).rank;
                    if(high[2] < yAxis3[j])
                    {
                        high[2] = (int)yAxis3[j];
                    }
                    if(low[2] > yAxis3[j])
                    {
                        low[2] = (int)yAxis3[j];
                    }
                    if(max < yAxis3[j])
                    {
                        max = yAxis3[j];
                    }
                    yAxis4[j] = Countries2014.get(7).rankHistory.get(i).rank;
                    if(high[3] < yAxis4[j])
                    {
                        high[3] = (int)yAxis4[j];
                    }
                    if(low[3] > yAxis4[j])
                    {
                        low[3] = (int)yAxis4[j];
                    }
                    if(max < yAxis4[j])
                    {
                        max = yAxis4[j];
                    }

                    j++;

                }

                //ranking text
                textFont(countryFont, 30);
                textAlign(CENTER);
                fill(0);
                text(high[teamChoice],1135,422);
                text(low[teamChoice],1135,305);

                //set data
                lineChart.setData(xAxis1,yAxis1);
                lineChart2.setData(xAxis1,yAxis2);
                lineChart3.setData(xAxis1,yAxis3);
                lineChart4.setData(xAxis1,yAxis4);

                //setMin and Max value
                lineChart.setMinY(0);
                lineChart2.setMinY(0);
                lineChart3.setMinY(0);
                lineChart4.setMinY(0);

                lineChart.setMaxY(max);
                lineChart2.setMaxY(max);
                lineChart3.setMaxY(max);
                lineChart4.setMaxY(max);

                //Set Color for all lines
                lineChart.setPointColour(color(firstTeam.r,firstTeam.g,firstTeam.b));
                lineChart.setLineColour(color(firstTeam.r,firstTeam.g,firstTeam.b));
                lineChart2.setPointColour(color(secondTeam.r,secondTeam.g,secondTeam.b));
                lineChart2.setLineColour(color(secondTeam.r,secondTeam.g,secondTeam.b));
                lineChart3.setPointColour(color(thirdTeam.r,thirdTeam.g,secondTeam.b));
                lineChart3.setLineColour(color(thirdTeam.r,thirdTeam.g,thirdTeam.b));
                lineChart4.setPointColour(color(fourthTeam.r,fourthTeam.g,fourthTeam.b));
                lineChart4.setLineColour(color(fourthTeam.r,fourthTeam.g,fourthTeam.b));

                if(index == 4)
                {
                    lineChart.setPointSize(5);
                    lineChart.setLineWidth(5);
                }
                else
                {
                    lineChart.setPointSize(2);
                    lineChart.setLineWidth(2);
                }
                if(index == 5)
                {
                    lineChart2.setPointSize(5);
                    lineChart2.setLineWidth(5);
                }
                else
                {
                    lineChart2.setPointSize(2);
                    lineChart2.setLineWidth(2);
                }
                if(index == 6)
                {
                    lineChart3.setPointSize(5);
                    lineChart3.setLineWidth(5);
                }
                else
                {
                    lineChart3.setPointSize(2);
                    lineChart3.setLineWidth(2);
                }
                if(index == 7)
                {
                    lineChart4.setPointSize(5);
                    lineChart4.setLineWidth(5);
                }
                else
                {
                    lineChart4.setPointSize(2);
                    lineChart4.setLineWidth(2);
                }

                // Axis formatting and labels.
                lineChart.showXAxis(true);
                lineChart.showYAxis(true);
                lineChart2.showXAxis(true);
                lineChart2.showYAxis(true);
                lineChart3.showXAxis(true);
                lineChart3.showYAxis(true);
                lineChart4.showXAxis(true);
                lineChart4.showYAxis(true);

                //showData
                textSize(9);
                lineChart.draw(270,180,707,323);
                lineChart2.draw(270,180,707,323);
                lineChart3.draw(270,180,707,323);
                lineChart4.draw(270,180,707,323);
                break;
            case 8:
            case 9:
            case 10:
            case 11:
                if(index == 8)
                {
                    teamChoice = 0;
                }
                if(index == 9)
                {
                    teamChoice = 1;
                }
                if(index == 10)
                {
                    teamChoice = 2;
                }
                if(index == 11)
                {
                    teamChoice = 3;
                }
                //GUI display for group
                //team choice setup
                graphGroup = loadImage("GroupCBig.png");
                image(graphGroup,(float)826.5,178,141,40);
                //group team key for plot
                firstTeam.logo = loadImage("teamPhotos/"+ Countries2014.get(8).name +"Main.png");
                firstTeam.name = Countries2014.get(8).name;
                firstTeam.r = 250;
                firstTeam.g = 176;
                firstTeam.b = 62;
                firstTeam.draw();

                secondTeam.logo = loadImage("teamPhotos/"+ Countries2014.get(9).name +"Main.png");
                secondTeam.name = Countries2014.get(9).name;
                secondTeam.r = 45;
                secondTeam.g = 49;
                secondTeam.b = 133;
                secondTeam.draw();

                thirdTeam.logo = loadImage("teamPhotos/"+ Countries2014.get(10).name +"Main.png");
                thirdTeam.name = Countries2014.get(10).name;
                thirdTeam.r = 0;
                thirdTeam.g = 145;
                thirdTeam.b = 68;
                thirdTeam.draw();

                fourthTeam.logo = loadImage("teamPhotos/"+ Countries2014.get(11).name +"Main.png");
                fourthTeam.name = Countries2014.get(11).name;
                fourthTeam.r = 193;
                fourthTeam.g = 38;
                fourthTeam.b = 45;
                fourthTeam.draw();

                //match fix text
                textFont(countryFont, 30);
                textAlign(CENTER);
                fill(192,231,187);
                text("Match 1",810,586);
                text("Match 2",810,661);
                text("Match 3",810,731);
                // vs text setup
                textFont(countryFont, 30);
                textAlign(CENTER);
                text("Vs",900,586);
                text("Vs",900,661);
                text("Vs",900,731);


                //matches setup
                indexFirstRival = getTeamIndex(Countries2014.get(index).match1);
                firstMatch.logo = loadImage("teamPhotos/" + Countries2014.get(indexFirstRival).name + "Main.png");
                firstMatch.rivalName = Countries2014.get(index).match1;
                firstMatch.draw();

                indexSecondRival = getTeamIndex(Countries2014.get(index).match2);
                secondMatch.logo = loadImage("teamPhotos/" + Countries2014.get(indexSecondRival).name + "Main.png");
                secondMatch.rivalName = Countries2014.get(index).match2;
                secondMatch.draw();

                indexThirdRival = getTeamIndex(Countries2014.get(index).match3);
                thirdMatch.logo = loadImage("teamPhotos/" + Countries2014.get(indexThirdRival).name + "Main.png");
                thirdMatch.rivalName = Countries2014.get(index).match3;
                thirdMatch.draw();

                firstMatch.team1 = index;
                firstMatch.team2 = indexFirstRival;
                secondMatch.team1 = index;
                secondMatch.team2 = indexSecondRival;
                thirdMatch.team1 = index;
                thirdMatch.team2 = indexThirdRival;


                //ranking title
                fill(192,231,187);
                textFont(countryFont, 30);
                textAlign(CENTER);
                text("Worst Ranking",1130,365);
                text("Best Ranking",1130,248);

                //ranking info
                hRank = loadImage("hRankBackground.png");
                lRank = loadImage("lRankBackground.png");
                image(hRank,1093,258,80,80);
                image(lRank,1093,377,80,80);

                //team info
                textFont(countryFont, 25);
                textAlign(LEFT);
                text("Participations in World Cup Finals",48,586);
                text("Current Fifa world ranking",80,661);
                text("Best Result in World Cup finals",62,731);

                //circle for info
                fill(128,210,154);
                ellipse(602,576,66,66);
                fill(192,231,187);
                ellipse(602,576,56,56);

                fill(128,210,154);
                ellipse(602,651,66,66);
                fill(192,231,187);
                ellipse(602,651,56,56);

                infoBackground = loadImage("infoBackground.png");
                image(infoBackground, 480, 700);

                textFont(countryFont, 30);
                textAlign(CENTER);
                fill(0);
                text(Countries2014.get(index).numParticipations,602,586);
                text(Countries2014.get(index).currentRank,602,661);
                text(Countries2014.get(index).bestResult,602,731);

                //data display for group
                j = 0;//array Index
                for(int i = minVal; i < maxVal; i++)
                {
                    xAxis1[j] = j;
                    yAxis1[j] = Countries2014.get(8).rankHistory.get(i).rank;
                    if(high[0] < yAxis1[j])
                    {
                        high[0] = (int)yAxis1[j];
                    }
                    if(low[0] > yAxis1[j])
                    {
                        low[0] = (int)yAxis1[j];
                    }
                    if(max < yAxis1[j])
                    {
                        max = yAxis1[j];
                    }
                    yAxis2[j] = Countries2014.get(9).rankHistory.get(i).rank;
                    if(high[1] < yAxis2[j])
                    {
                        high[1] = (int)yAxis2[j];
                    }
                    if(low[1] > yAxis2[j])
                    {
                        low[1] = (int)yAxis2[j];
                    }
                    if(max < yAxis2[j])
                    {
                        max = yAxis2[j];
                    }
                    yAxis3[j] = Countries2014.get(10).rankHistory.get(i).rank;
                    if(high[2] < yAxis3[j])
                    {
                        high[2] = (int)yAxis3[j];
                    }
                    if(low[2] > yAxis3[j])
                    {
                        low[2] = (int)yAxis3[j];
                    }
                    if(max < yAxis3[j])
                    {
                        max = yAxis3[j];
                    }
                    yAxis4[j] = Countries2014.get(11).rankHistory.get(i).rank;
                    if(high[3] < yAxis4[j])
                    {
                        high[3] = (int)yAxis4[j];
                    }
                    if(low[3] > yAxis4[j])
                    {
                        low[3] = (int)yAxis4[j];
                    }
                    if(max < yAxis4[j])
                    {
                        max = yAxis4[j];
                    }

                    j++;

                }

                //ranking text
                textFont(countryFont, 30);
                textAlign(CENTER);
                fill(0);
                text(high[teamChoice],1135,422);
                text(low[teamChoice],1135,305);

                //set data
                lineChart.setData(xAxis1,yAxis1);
                lineChart2.setData(xAxis1,yAxis2);
                lineChart3.setData(xAxis1,yAxis3);
                lineChart4.setData(xAxis1,yAxis4);

                //setMin and Max value
                lineChart.setMinY(0);
                lineChart2.setMinY(0);
                lineChart3.setMinY(0);
                lineChart4.setMinY(0);

                lineChart.setMaxY(max);
                lineChart2.setMaxY(max);
                lineChart3.setMaxY(max);
                lineChart4.setMaxY(max);

                //Set Color for all lines
                lineChart.setPointColour(color(firstTeam.r,firstTeam.g,firstTeam.b));
                lineChart.setLineColour(color(firstTeam.r,firstTeam.g,firstTeam.b));
                lineChart2.setPointColour(color(secondTeam.r,secondTeam.g,secondTeam.b));
                lineChart2.setLineColour(color(secondTeam.r,secondTeam.g,secondTeam.b));
                lineChart3.setPointColour(color(thirdTeam.r,thirdTeam.g,secondTeam.b));
                lineChart3.setLineColour(color(thirdTeam.r,thirdTeam.g,thirdTeam.b));
                lineChart4.setPointColour(color(fourthTeam.r,fourthTeam.g,fourthTeam.b));
                lineChart4.setLineColour(color(fourthTeam.r,fourthTeam.g,fourthTeam.b));

                if(index == 8)
                {
                    lineChart.setPointSize(5);
                    lineChart.setLineWidth(5);
                }
                else
                {
                    lineChart.setPointSize(2);
                    lineChart.setLineWidth(2);
                }
                if(index == 9)
                {
                    lineChart2.setPointSize(5);
                    lineChart2.setLineWidth(5);
                }
                else
                {
                    lineChart2.setPointSize(2);
                    lineChart2.setLineWidth(2);
                }
                if(index == 10)
                {
                    lineChart3.setPointSize(5);
                    lineChart3.setLineWidth(5);
                }
                else
                {
                    lineChart3.setPointSize(2);
                    lineChart3.setLineWidth(2);
                }
                if(index == 11)
                {
                    lineChart4.setPointSize(5);
                    lineChart4.setLineWidth(5);
                }
                else
                {
                    lineChart4.setPointSize(2);
                    lineChart4.setLineWidth(2);
                }

                // Axis formatting and labels.
                lineChart.showXAxis(true);
                lineChart.showYAxis(true);
                lineChart2.showXAxis(true);
                lineChart2.showYAxis(true);
                lineChart3.showXAxis(true);
                lineChart3.showYAxis(true);
                lineChart4.showXAxis(true);
                lineChart4.showYAxis(true);

                //showData
                textSize(9);
                lineChart.draw(270,180,707,323);
                lineChart2.draw(270,180,707,323);
                lineChart3.draw(270,180,707,323);
                lineChart4.draw(270,180,707,323);
                break;
            case 12:
            case 13:
            case 14:
            case 15:
                if(index == 12)
                {
                    teamChoice = 0;
                }
                if(index == 13)
                {
                    teamChoice = 1;
                }
                if(index == 14)
                {
                    teamChoice = 2;
                }
                if(index == 15)
                {
                    teamChoice = 3;
                }
                //GUI display for group
                //team choice setup
                graphGroup = loadImage("GroupDBig.png");
                image(graphGroup,(float)826.5,178,141,40);
                //group team key for plot
                firstTeam.logo = loadImage("teamPhotos/"+ Countries2014.get(12).name +"Main.png");
                firstTeam.name = Countries2014.get(12).name;
                firstTeam.r = 239;
                firstTeam.g = 239;
                firstTeam.b = 239;
                firstTeam.draw();

                secondTeam.logo = loadImage("teamPhotos/"+ Countries2014.get(13).name +"Main.png");
                secondTeam.name = Countries2014.get(13).name;
                secondTeam.r = 46;
                secondTeam.g = 50;
                secondTeam.b = 134;
                secondTeam.draw();

                thirdTeam.logo = loadImage("teamPhotos/"+ Countries2014.get(14).name +"Main.png");
                thirdTeam.name = Countries2014.get(14).name;
                thirdTeam.r = 193;
                thirdTeam.g = 38;
                thirdTeam.b = 45;
                thirdTeam.draw();

                fourthTeam.logo = loadImage("teamPhotos/"+ Countries2014.get(15).name +"Main.png");
                fourthTeam.name = Countries2014.get(15).name;
                fourthTeam.r = 16;
                fourthTeam.g = 152;
                fourthTeam.b = 80;
                fourthTeam.draw();

                //match fix text
                textFont(countryFont, 30);
                textAlign(CENTER);
                fill(192,231,187);
                text("Match 1",810,586);
                text("Match 2",810,661);
                text("Match 3",810,731);
                // vs text setup
                textFont(countryFont, 30);
                textAlign(CENTER);
                text("Vs",900,586);
                text("Vs",900,661);
                text("Vs",900,731);


                //matches setup
                indexFirstRival = getTeamIndex(Countries2014.get(index).match1);
                firstMatch.logo = loadImage("teamPhotos/" + Countries2014.get(indexFirstRival).name + "Main.png");
                firstMatch.rivalName = Countries2014.get(index).match1;
                firstMatch.draw();

                indexSecondRival = getTeamIndex(Countries2014.get(index).match2);
                secondMatch.logo = loadImage("teamPhotos/" + Countries2014.get(indexSecondRival).name + "Main.png");
                secondMatch.rivalName = Countries2014.get(index).match2;
                secondMatch.draw();

                indexThirdRival = getTeamIndex(Countries2014.get(index).match3);
                thirdMatch.logo = loadImage("teamPhotos/" + Countries2014.get(indexThirdRival).name + "Main.png");
                thirdMatch.rivalName = Countries2014.get(index).match3;
                thirdMatch.draw();

                firstMatch.team1 = index;
                firstMatch.team2 = indexFirstRival;
                secondMatch.team1 = index;
                secondMatch.team2 = indexSecondRival;
                thirdMatch.team1 = index;
                thirdMatch.team2 = indexThirdRival;


                //ranking title
                fill(192,231,187);
                textFont(countryFont, 30);
                textAlign(CENTER);
                text("Worst Ranking",1130,365);
                text("Best Ranking",1130,248);

                //ranking info
                hRank = loadImage("hRankBackground.png");
                lRank = loadImage("lRankBackground.png");
                image(hRank,1093,258,80,80);
                image(lRank,1093,377,80,80);

                //team info
                textFont(countryFont, 25);
                textAlign(LEFT);
                text("Participations in World Cup Finals",48,586);
                text("Current Fifa world ranking",80,661);
                text("Best Result in World Cup finals",62,731);

                //circle for info
                fill(128,210,154);
                ellipse(602,576,66,66);
                fill(192,231,187);
                ellipse(602,576,56,56);

                fill(128,210,154);
                ellipse(602,651,66,66);
                fill(192,231,187);
                ellipse(602,651,56,56);

                infoBackground = loadImage("infoBackground.png");
                image(infoBackground, 480, 700);

                textFont(countryFont, 30);
                textAlign(CENTER);
                fill(0);
                text(Countries2014.get(index).numParticipations,602,586);
                text(Countries2014.get(index).currentRank,602,661);
                text(Countries2014.get(index).bestResult,602,731);

                //data display for group
                j = 0;//array Index
                for(int i = minVal; i < maxVal; i++)
                {
                    xAxis1[j] = j;
                    yAxis1[j] = Countries2014.get(12).rankHistory.get(i).rank;
                    if(high[0] < yAxis1[j])
                    {
                        high[0] = (int)yAxis1[j];
                    }
                    if(low[0] > yAxis1[j])
                    {
                        low[0] = (int)yAxis1[j];
                    }
                    if(max < yAxis1[j])
                    {
                        max = yAxis1[j];
                    }
                    yAxis2[j] = Countries2014.get(13).rankHistory.get(i).rank;
                    if(high[1] < yAxis2[j])
                    {
                        high[1] = (int)yAxis2[j];
                    }
                    if(low[1] > yAxis2[j])
                    {
                        low[1] = (int)yAxis2[j];
                    }
                    if(max < yAxis2[j])
                    {
                        max = yAxis2[j];
                    }
                    yAxis3[j] = Countries2014.get(14).rankHistory.get(i).rank;
                    if(high[2] < yAxis3[j])
                    {
                        high[2] = (int)yAxis3[j];
                    }
                    if(low[2] > yAxis3[j])
                    {
                        low[2] = (int)yAxis3[j];
                    }
                    if(max < yAxis3[j])
                    {
                        max = yAxis3[j];
                    }
                    yAxis4[j] = Countries2014.get(15).rankHistory.get(i).rank;
                    if(high[3] < yAxis4[j])
                    {
                        high[3] = (int)yAxis4[j];
                    }
                    if(low[3] > yAxis4[j])
                    {
                        low[3] = (int)yAxis4[j];
                    }
                    if(max < yAxis4[j])
                    {
                        max = yAxis4[j];
                    }

                    j++;

                }

                //ranking text
                textFont(countryFont, 30);
                textAlign(CENTER);
                fill(0);
                text(high[teamChoice],1135,422);
                text(low[teamChoice],1135,305);

                //set data
                lineChart.setData(xAxis1,yAxis1);
                lineChart2.setData(xAxis1,yAxis2);
                lineChart3.setData(xAxis1,yAxis3);
                lineChart4.setData(xAxis1,yAxis4);

                //setMin and Max value
                lineChart.setMinY(0);
                lineChart2.setMinY(0);
                lineChart3.setMinY(0);
                lineChart4.setMinY(0);

                lineChart.setMaxY(max);
                lineChart2.setMaxY(max);
                lineChart3.setMaxY(max);
                lineChart4.setMaxY(max);

                //Set Color for all lines
                lineChart.setPointColour(color(firstTeam.r,firstTeam.g,firstTeam.b));
                lineChart.setLineColour(color(firstTeam.r,firstTeam.g,firstTeam.b));
                lineChart2.setPointColour(color(secondTeam.r,secondTeam.g,secondTeam.b));
                lineChart2.setLineColour(color(secondTeam.r,secondTeam.g,secondTeam.b));
                lineChart3.setPointColour(color(thirdTeam.r,thirdTeam.g,secondTeam.b));
                lineChart3.setLineColour(color(thirdTeam.r,thirdTeam.g,thirdTeam.b));
                lineChart4.setPointColour(color(fourthTeam.r,fourthTeam.g,fourthTeam.b));
                lineChart4.setLineColour(color(fourthTeam.r,fourthTeam.g,fourthTeam.b));

                if(index == 12)
                {
                    lineChart.setPointSize(5);
                    lineChart.setLineWidth(5);
                }
                else
                {
                    lineChart.setPointSize(2);
                    lineChart.setLineWidth(2);
                }
                if(index == 13)
                {
                    lineChart2.setPointSize(5);
                    lineChart2.setLineWidth(5);
                }
                else
                {
                    lineChart2.setPointSize(2);
                    lineChart2.setLineWidth(2);
                }
                if(index == 14)
                {
                    lineChart3.setPointSize(5);
                    lineChart3.setLineWidth(5);
                }
                else
                {
                    lineChart3.setPointSize(2);
                    lineChart3.setLineWidth(2);
                }
                if(index == 15)
                {
                    lineChart4.setPointSize(5);
                    lineChart4.setLineWidth(5);
                }
                else
                {
                    lineChart4.setPointSize(2);
                    lineChart4.setLineWidth(2);
                }

                // Axis formatting and labels.
                lineChart.showXAxis(true);
                lineChart.showYAxis(true);
                lineChart2.showXAxis(true);
                lineChart2.showYAxis(true);
                lineChart3.showXAxis(true);
                lineChart3.showYAxis(true);
                lineChart4.showXAxis(true);
                lineChart4.showYAxis(true);

                //showData
                textSize(9);
                lineChart.draw(270,180,707,323);
                lineChart2.draw(270,180,707,323);
                lineChart3.draw(270,180,707,323);
                lineChart4.draw(270,180,707,323);
                break;
            case 16:
            case 17:
            case 18:
            case 19:
                if(index == 16)
                {
                    teamChoice = 0;
                }
                if(index == 17)
                {
                    teamChoice = 1;
                }
                if(index == 18)
                {
                    teamChoice = 2;
                }
                if(index == 19)
                {
                    teamChoice = 3;
                }
                //GUI display for group
                //team choice setup
                graphGroup = loadImage("GroupEBig.png");
                image(graphGroup,(float)826.5,178,141,40);
                //group team key for plot
                firstTeam.logo = loadImage("teamPhotos/"+ Countries2014.get(16).name +"Main.png");
                firstTeam.name = Countries2014.get(16).name;
                firstTeam.r = 193;
                firstTeam.g = 38;
                firstTeam.b = 45;
                firstTeam.draw();

                secondTeam.logo = loadImage("teamPhotos/"+ Countries2014.get(17).name +"Main.png");
                secondTeam.name = Countries2014.get(17).name;
                secondTeam.r = 249;
                secondTeam.g = 175;
                secondTeam.b = 59;
                secondTeam.draw();

                thirdTeam.logo = loadImage("teamPhotos/"+ Countries2014.get(18).name +"Main.png");
                thirdTeam.name = Countries2014.get(18).name;
                thirdTeam.r = 239;
                thirdTeam.g = 239;
                thirdTeam.b = 239;
                thirdTeam.draw();

                fourthTeam.logo = loadImage("teamPhotos/"+ Countries2014.get(19).name +"Main.png");
                fourthTeam.name = Countries2014.get(19).name;
                fourthTeam.r = 38;
                fourthTeam.g = 82;
                fourthTeam.b = 158;
                fourthTeam.draw();

                //match fix text
                textFont(countryFont, 30);
                textAlign(CENTER);
                fill(192,231,187);
                text("Match 1",810,586);
                text("Match 2",810,661);
                text("Match 3",810,731);
                // vs text setup
                textFont(countryFont, 30);
                textAlign(CENTER);
                text("Vs",900,586);
                text("Vs",900,661);
                text("Vs",900,731);


                //matches setup
                indexFirstRival = getTeamIndex(Countries2014.get(index).match1);
                firstMatch.logo = loadImage("teamPhotos/" + Countries2014.get(indexFirstRival).name + "Main.png");
                firstMatch.rivalName = Countries2014.get(index).match1;
                firstMatch.draw();

                indexSecondRival = getTeamIndex(Countries2014.get(index).match2);
                secondMatch.logo = loadImage("teamPhotos/" + Countries2014.get(indexSecondRival).name + "Main.png");
                secondMatch.rivalName = Countries2014.get(index).match2;
                secondMatch.draw();

                indexThirdRival = getTeamIndex(Countries2014.get(index).match3);
                thirdMatch.logo = loadImage("teamPhotos/" + Countries2014.get(indexThirdRival).name + "Main.png");
                thirdMatch.rivalName = Countries2014.get(index).match3;
                thirdMatch.draw();

                firstMatch.team1 = index;
                firstMatch.team2 = indexFirstRival;
                secondMatch.team1 = index;
                secondMatch.team2 = indexSecondRival;
                thirdMatch.team1 = index;
                thirdMatch.team2 = indexThirdRival;


                //ranking title
                fill(192,231,187);
                textFont(countryFont, 30);
                textAlign(CENTER);
                text("Worst Ranking",1130,365);
                text("Best Ranking",1130,248);

                //ranking info
                hRank = loadImage("hRankBackground.png");
                lRank = loadImage("lRankBackground.png");
                image(hRank,1093,258,80,80);
                image(lRank,1093,377,80,80);

                //team info
                textFont(countryFont, 25);
                textAlign(LEFT);
                text("Participations in World Cup Finals",48,586);
                text("Current Fifa world ranking",80,661);
                text("Best Result in World Cup finals",62,731);

                //circle for info
                fill(128,210,154);
                ellipse(602,576,66,66);
                fill(192,231,187);
                ellipse(602,576,56,56);

                fill(128,210,154);
                ellipse(602,651,66,66);
                fill(192,231,187);
                ellipse(602,651,56,56);

                infoBackground = loadImage("infoBackground.png");
                image(infoBackground, 480, 700);

                textFont(countryFont, 30);
                textAlign(CENTER);
                fill(0);
                text(Countries2014.get(index).numParticipations,602,586);
                text(Countries2014.get(index).currentRank,602,661);
                text(Countries2014.get(index).bestResult,602,731);

                //data display for group
                j = 0;//array Index
                for(int i = minVal; i < maxVal; i++)
                {
                    xAxis1[j] = j;
                    yAxis1[j] = Countries2014.get(16).rankHistory.get(i).rank;
                    if(high[0] < yAxis1[j])
                    {
                        high[0] = (int)yAxis1[j];
                    }
                    if(low[0] > yAxis1[j])
                    {
                        low[0] = (int)yAxis1[j];
                    }
                    if(max < yAxis1[j])
                    {
                        max = yAxis1[j];
                    }
                    yAxis2[j] = Countries2014.get(17).rankHistory.get(i).rank;
                    if(high[1] < yAxis2[j])
                    {
                        high[1] = (int)yAxis2[j];
                    }
                    if(low[1] > yAxis2[j])
                    {
                        low[1] = (int)yAxis2[j];
                    }
                    if(max < yAxis2[j])
                    {
                        max = yAxis2[j];
                    }
                    yAxis3[j] = Countries2014.get(18).rankHistory.get(i).rank;
                    if(high[2] < yAxis3[j])
                    {
                        high[2] = (int)yAxis3[j];
                    }
                    if(low[2] > yAxis3[j])
                    {
                        low[2] = (int)yAxis3[j];
                    }
                    if(max < yAxis3[j])
                    {
                        max = yAxis3[j];
                    }
                    yAxis4[j] = Countries2014.get(19).rankHistory.get(i).rank;
                    if(high[3] < yAxis4[j])
                    {
                        high[3] = (int)yAxis4[j];
                    }
                    if(low[3] > yAxis4[j])
                    {
                        low[3] = (int)yAxis4[j];
                    }
                    if(max < yAxis4[j])
                    {
                        max = yAxis4[j];
                    }

                    j++;

                }

                //ranking text
                textFont(countryFont, 30);
                textAlign(CENTER);
                fill(0);
                text(high[teamChoice],1135,422);
                text(low[teamChoice],1135,305);

                //set data
                lineChart.setData(xAxis1,yAxis1);
                lineChart2.setData(xAxis1,yAxis2);
                lineChart3.setData(xAxis1,yAxis3);
                lineChart4.setData(xAxis1,yAxis4);

                //setMin and Max value
                lineChart.setMinY(0);
                lineChart2.setMinY(0);
                lineChart3.setMinY(0);
                lineChart4.setMinY(0);

                lineChart.setMaxY(max);
                lineChart2.setMaxY(max);
                lineChart3.setMaxY(max);
                lineChart4.setMaxY(max);

                //Set Color for all lines
                lineChart.setPointColour(color(firstTeam.r,firstTeam.g,firstTeam.b));
                lineChart.setLineColour(color(firstTeam.r,firstTeam.g,firstTeam.b));
                lineChart2.setPointColour(color(secondTeam.r,secondTeam.g,secondTeam.b));
                lineChart2.setLineColour(color(secondTeam.r,secondTeam.g,secondTeam.b));
                lineChart3.setPointColour(color(thirdTeam.r,thirdTeam.g,secondTeam.b));
                lineChart3.setLineColour(color(thirdTeam.r,thirdTeam.g,thirdTeam.b));
                lineChart4.setPointColour(color(fourthTeam.r,fourthTeam.g,fourthTeam.b));
                lineChart4.setLineColour(color(fourthTeam.r,fourthTeam.g,fourthTeam.b));

                if(index == 16)
                {
                    lineChart.setPointSize(5);
                    lineChart.setLineWidth(5);
                }
                else
                {
                    lineChart.setPointSize(2);
                    lineChart.setLineWidth(2);
                }
                if(index == 17)
                {
                    lineChart2.setPointSize(5);
                    lineChart2.setLineWidth(5);
                }
                else
                {
                    lineChart2.setPointSize(2);
                    lineChart2.setLineWidth(2);
                }
                if(index == 18)
                {
                    lineChart3.setPointSize(5);
                    lineChart3.setLineWidth(5);
                }
                else
                {
                    lineChart3.setPointSize(2);
                    lineChart3.setLineWidth(2);
                }
                if(index == 19)
                {
                    lineChart4.setPointSize(5);
                    lineChart4.setLineWidth(5);
                }
                else
                {
                    lineChart4.setPointSize(2);
                    lineChart4.setLineWidth(2);
                }

                // Axis formatting and labels.
                lineChart.showXAxis(true);
                lineChart.showYAxis(true);
                lineChart2.showXAxis(true);
                lineChart2.showYAxis(true);
                lineChart3.showXAxis(true);
                lineChart3.showYAxis(true);
                lineChart4.showXAxis(true);
                lineChart4.showYAxis(true);

                //showData
                textSize(9);
                lineChart.draw(270,180,707,323);
                lineChart2.draw(270,180,707,323);
                lineChart3.draw(270,180,707,323);
                lineChart4.draw(270,180,707,323);
                break;
            case 20:
            case 21:
            case 22:
            case 23:
                if(index == 20)
                {
                    teamChoice = 0;
                }
                if(index == 21)
                {
                    teamChoice = 1;
                }
                if(index == 22)
                {
                    teamChoice = 2;
                }
                if(index == 23)
                {
                    teamChoice = 3;
                }
                //GUI display for group
                //team choice setup
                graphGroup = loadImage("GroupFBig.png");
                image(graphGroup,(float)826.5,178,141,40);
                //group team key for plot
                firstTeam.logo = loadImage("teamPhotos/"+ Countries2014.get(20).name +"Main.png");
                firstTeam.name = Countries2014.get(20).name;
                firstTeam.r = 119;
                firstTeam.g = 168;
                firstTeam.b = 217;
                firstTeam.draw();

                secondTeam.logo = loadImage("teamPhotos/"+ Countries2014.get(21).name +"Main.png");
                secondTeam.name = Countries2014.get(21).name;
                secondTeam.r = 45;
                secondTeam.g = 49;
                secondTeam.b = 133;
                secondTeam.draw();

                thirdTeam.logo = loadImage("teamPhotos/"+ Countries2014.get(22).name +"Main.png");
                thirdTeam.name = Countries2014.get(22).name;
                thirdTeam.r = 193;
                thirdTeam.g = 38;
                thirdTeam.b = 45;
                thirdTeam.draw();

                fourthTeam.logo = loadImage("teamPhotos/"+ Countries2014.get(23).name +"Main.png");
                fourthTeam.name = Countries2014.get(23).name;
                fourthTeam.r = 0;
                fourthTeam.g = 145;
                fourthTeam.b = 68;
                fourthTeam.draw();

                //match fix text
                textFont(countryFont, 30);
                textAlign(CENTER);
                fill(192,231,187);
                text("Match 1",810,586);
                text("Match 2",810,661);
                text("Match 3",810,731);
                // vs text setup
                textFont(countryFont, 30);
                textAlign(CENTER);
                text("Vs",900,586);
                text("Vs",900,661);
                text("Vs",900,731);


                //matches setup
                indexFirstRival = getTeamIndex(Countries2014.get(index).match1);
                firstMatch.logo = loadImage("teamPhotos/" + Countries2014.get(indexFirstRival).name + "Main.png");
                firstMatch.rivalName = Countries2014.get(index).match1;
                firstMatch.draw();

                indexSecondRival = getTeamIndex(Countries2014.get(index).match2);
                secondMatch.logo = loadImage("teamPhotos/" + Countries2014.get(indexSecondRival).name + "Main.png");
                secondMatch.rivalName = Countries2014.get(index).match2;
                secondMatch.draw();

                indexThirdRival = getTeamIndex(Countries2014.get(index).match3);
                thirdMatch.logo = loadImage("teamPhotos/" + Countries2014.get(indexThirdRival).name + "Main.png");
                thirdMatch.rivalName = Countries2014.get(index).match3;
                thirdMatch.draw();

                firstMatch.team1 = index;
                firstMatch.team2 = indexFirstRival;
                secondMatch.team1 = index;
                secondMatch.team2 = indexSecondRival;
                thirdMatch.team1 = index;
                thirdMatch.team2 = indexThirdRival;


                //ranking title
                fill(192,231,187);
                textFont(countryFont, 30);
                textAlign(CENTER);
                text("Worst Ranking",1130,365);
                text("Best Ranking",1130,248);

                //ranking info
                hRank = loadImage("hRankBackground.png");
                lRank = loadImage("lRankBackground.png");
                image(hRank,1093,258,80,80);
                image(lRank,1093,377,80,80);

                //team info
                textFont(countryFont, 25);
                textAlign(LEFT);
                text("Participations in World Cup Finals",48,586);
                text("Current Fifa world ranking",80,661);
                text("Best Result in World Cup finals",62,731);

                //circle for info
                fill(128,210,154);
                ellipse(602,576,66,66);
                fill(192,231,187);
                ellipse(602,576,56,56);

                fill(128,210,154);
                ellipse(602,651,66,66);
                fill(192,231,187);
                ellipse(602,651,56,56);

                infoBackground = loadImage("infoBackground.png");
                image(infoBackground, 480, 700);

                textFont(countryFont, 30);
                textAlign(CENTER);
                fill(0);
                text(Countries2014.get(index).numParticipations,602,586);
                text(Countries2014.get(index).currentRank,602,661);
                text(Countries2014.get(index).bestResult,602,731);

                //data display for group
                j = 0;//array Index
                for(int i = minVal; i < maxVal; i++)
                {
                    xAxis1[j] = j;
                    yAxis1[j] = Countries2014.get(20).rankHistory.get(i).rank;
                    if(high[0] < yAxis1[j])
                    {
                        high[0] = (int)yAxis1[j];
                    }
                    if(low[0] > yAxis1[j])
                    {
                        low[0] = (int)yAxis1[j];
                    }
                    if(max < yAxis1[j])
                    {
                        max = yAxis1[j];
                    }
                    yAxis2[j] = Countries2014.get(21).rankHistory.get(i).rank;
                    if(high[1] < yAxis2[j])
                    {
                        high[1] = (int)yAxis2[j];
                    }
                    if(low[1] > yAxis2[j])
                    {
                        low[1] = (int)yAxis2[j];
                    }
                    if(max < yAxis2[j])
                    {
                        max = yAxis2[j];
                    }
                    yAxis3[j] = Countries2014.get(22).rankHistory.get(i).rank;
                    if(high[2] < yAxis3[j])
                    {
                        high[2] = (int)yAxis3[j];
                    }
                    if(low[2] > yAxis3[j])
                    {
                        low[2] = (int)yAxis3[j];
                    }
                    if(max < yAxis3[j])
                    {
                        max = yAxis3[j];
                    }
                    yAxis4[j] = Countries2014.get(23).rankHistory.get(i).rank;
                    if(high[3] < yAxis4[j])
                    {
                        high[3] = (int)yAxis4[j];
                    }
                    if(low[3] > yAxis4[j])
                    {
                        low[3] = (int)yAxis4[j];
                    }
                    if(max < yAxis4[j])
                    {
                        max = yAxis4[j];
                    }

                    j++;

                }

                //ranking text
                textFont(countryFont, 30);
                textAlign(CENTER);
                fill(0);
                text(high[teamChoice],1135,422);
                text(low[teamChoice],1135,305);

                //set data
                lineChart.setData(xAxis1,yAxis1);
                lineChart2.setData(xAxis1,yAxis2);
                lineChart3.setData(xAxis1,yAxis3);
                lineChart4.setData(xAxis1,yAxis4);

                //setMin and Max value
                lineChart.setMinY(0);
                lineChart2.setMinY(0);
                lineChart3.setMinY(0);
                lineChart4.setMinY(0);

                lineChart.setMaxY(max);
                lineChart2.setMaxY(max);
                lineChart3.setMaxY(max);
                lineChart4.setMaxY(max);

                //Set Color for all lines
                lineChart.setPointColour(color(firstTeam.r,firstTeam.g,firstTeam.b));
                lineChart.setLineColour(color(firstTeam.r,firstTeam.g,firstTeam.b));
                lineChart2.setPointColour(color(secondTeam.r,secondTeam.g,secondTeam.b));
                lineChart2.setLineColour(color(secondTeam.r,secondTeam.g,secondTeam.b));
                lineChart3.setPointColour(color(thirdTeam.r,thirdTeam.g,secondTeam.b));
                lineChart3.setLineColour(color(thirdTeam.r,thirdTeam.g,thirdTeam.b));
                lineChart4.setPointColour(color(fourthTeam.r,fourthTeam.g,fourthTeam.b));
                lineChart4.setLineColour(color(fourthTeam.r,fourthTeam.g,fourthTeam.b));

                if(index == 20)
                {
                    lineChart.setPointSize(5);
                    lineChart.setLineWidth(5);
                }
                else
                {
                    lineChart.setPointSize(2);
                    lineChart.setLineWidth(2);
                }
                if(index == 21)
                {
                    lineChart2.setPointSize(5);
                    lineChart2.setLineWidth(5);
                }
                else
                {
                    lineChart2.setPointSize(2);
                    lineChart2.setLineWidth(2);
                }
                if(index == 22)
                {
                    lineChart3.setPointSize(5);
                    lineChart3.setLineWidth(5);
                }
                else
                {
                    lineChart3.setPointSize(2);
                    lineChart3.setLineWidth(2);
                }
                if(index == 23)
                {
                    lineChart4.setPointSize(5);
                    lineChart4.setLineWidth(5);
                }
                else
                {
                    lineChart4.setPointSize(2);
                    lineChart4.setLineWidth(2);
                }

                // Axis formatting and labels.
                lineChart.showXAxis(true);
                lineChart.showYAxis(true);
                lineChart2.showXAxis(true);
                lineChart2.showYAxis(true);
                lineChart3.showXAxis(true);
                lineChart3.showYAxis(true);
                lineChart4.showXAxis(true);
                lineChart4.showYAxis(true);

                //showData
                textSize(9);
                lineChart.draw(270,180,707,323);
                lineChart2.draw(270,180,707,323);
                lineChart3.draw(270,180,707,323);
                lineChart4.draw(270,180,707,323);
                break;
            case 24:
            case 25:
            case 26:
            case 27:
                if(index == 24)
                {
                    teamChoice = 0;
                }
                if(index == 25)
                {
                    teamChoice = 1;
                }
                if(index == 26)
                {
                    teamChoice = 2;
                }
                if(index == 27)
                {
                    teamChoice = 3;
                }
                //GUI display for group
                //team choice setup
                graphGroup = loadImage("GroupGBig.png");
                image(graphGroup,(float)826.5,178,141,40);
                //group team key for plot
                firstTeam.logo = loadImage("teamPhotos/"+ Countries2014.get(24).name +"Main.png");
                firstTeam.name = Countries2014.get(24).name;
                firstTeam.r = 50;
                firstTeam.g = 48;
                firstTeam.b = 36;
                firstTeam.draw();

                secondTeam.logo = loadImage("teamPhotos/"+ Countries2014.get(25).name +"Main.png");
                secondTeam.name = Countries2014.get(25).name;
                secondTeam.r = 18;
                secondTeam.g = 153;
                secondTeam.b = 81;
                secondTeam.draw();

                thirdTeam.logo = loadImage("teamPhotos/"+ Countries2014.get(26).name +"Main.png");
                thirdTeam.name = Countries2014.get(26).name;
                thirdTeam.r = 249;
                thirdTeam.g = 175;
                thirdTeam.b = 59;
                thirdTeam.draw();

                fourthTeam.logo = loadImage("teamPhotos/"+ Countries2014.get(27).name +"Main.png");
                fourthTeam.name = Countries2014.get(27).name;
                fourthTeam.r = 193;
                fourthTeam.g = 38;
                fourthTeam.b = 45;
                fourthTeam.draw();

                //match fix text
                textFont(countryFont, 30);
                textAlign(CENTER);
                fill(192,231,187);
                text("Match 1",810,586);
                text("Match 2",810,661);
                text("Match 3",810,731);
                // vs text setup
                textFont(countryFont, 30);
                textAlign(CENTER);
                text("Vs",900,586);
                text("Vs",900,661);
                text("Vs",900,731);


                //matches setup
                indexFirstRival = getTeamIndex(Countries2014.get(index).match1);
                firstMatch.logo = loadImage("teamPhotos/" + Countries2014.get(indexFirstRival).name + "Main.png");
                firstMatch.rivalName = Countries2014.get(index).match1;
                firstMatch.draw();

                indexSecondRival = getTeamIndex(Countries2014.get(index).match2);
                secondMatch.logo = loadImage("teamPhotos/" + Countries2014.get(indexSecondRival).name + "Main.png");
                secondMatch.rivalName = Countries2014.get(index).match2;
                secondMatch.draw();

                indexThirdRival = getTeamIndex(Countries2014.get(index).match3);
                thirdMatch.logo = loadImage("teamPhotos/" + Countries2014.get(indexThirdRival).name + "Main.png");
                thirdMatch.rivalName = Countries2014.get(index).match3;
                thirdMatch.draw();

                firstMatch.team1 = index;
                firstMatch.team2 = indexFirstRival;
                secondMatch.team1 = index;
                secondMatch.team2 = indexSecondRival;
                thirdMatch.team1 = index;
                thirdMatch.team2 = indexThirdRival;


                //ranking title
                fill(192,231,187);
                textFont(countryFont, 30);
                textAlign(CENTER);
                text("Worst Ranking",1130,365);
                text("Best Ranking",1130,248);

                //ranking info
                hRank = loadImage("hRankBackground.png");
                lRank = loadImage("lRankBackground.png");
                image(hRank,1093,258,80,80);
                image(lRank,1093,377,80,80);

                //team info
                textFont(countryFont, 25);
                textAlign(LEFT);
                text("Participations in World Cup Finals",48,586);
                text("Current Fifa world ranking",80,661);
                text("Best Result in World Cup finals",62,731);

                //circle for info
                fill(128,210,154);
                ellipse(602,576,66,66);
                fill(192,231,187);
                ellipse(602,576,56,56);

                fill(128,210,154);
                ellipse(602,651,66,66);
                fill(192,231,187);
                ellipse(602,651,56,56);

                infoBackground = loadImage("infoBackground.png");
                image(infoBackground, 480, 700);

                textFont(countryFont, 30);
                textAlign(CENTER);
                fill(0);
                text(Countries2014.get(index).numParticipations,602,586);
                text(Countries2014.get(index).currentRank,602,661);
                text(Countries2014.get(index).bestResult,602,731);

                //data display for group
                j = 0;//array Index
                for(int i = minVal; i < maxVal; i++)
                {
                    xAxis1[j] = j;
                    yAxis1[j] = Countries2014.get(24).rankHistory.get(i).rank;
                    if(high[0] < yAxis1[j])
                    {
                        high[0] = (int)yAxis1[j];
                    }
                    if(low[0] > yAxis1[j])
                    {
                        low[0] = (int)yAxis1[j];
                    }
                    if(max < yAxis1[j])
                    {
                        max = yAxis1[j];
                    }
                    yAxis2[j] = Countries2014.get(25).rankHistory.get(i).rank;
                    if(high[1] < yAxis2[j])
                    {
                        high[1] = (int)yAxis2[j];
                    }
                    if(low[1] > yAxis2[j])
                    {
                        low[1] = (int)yAxis2[j];
                    }
                    if(max < yAxis2[j])
                    {
                        max = yAxis2[j];
                    }
                    yAxis3[j] = Countries2014.get(26).rankHistory.get(i).rank;
                    if(high[2] < yAxis3[j])
                    {
                        high[2] = (int)yAxis3[j];
                    }
                    if(low[2] > yAxis3[j])
                    {
                        low[2] = (int)yAxis3[j];
                    }
                    if(max < yAxis3[j])
                    {
                        max = yAxis3[j];
                    }
                    yAxis4[j] = Countries2014.get(27).rankHistory.get(i).rank;
                    if(high[3] < yAxis4[j])
                    {
                        high[3] = (int)yAxis4[j];
                    }
                    if(low[3] > yAxis4[j])
                    {
                        low[3] = (int)yAxis4[j];
                    }
                    if(max < yAxis4[j])
                    {
                        max = yAxis4[j];
                    }

                    j++;

                }

                //ranking text
                textFont(countryFont, 30);
                textAlign(CENTER);
                fill(0);
                text(high[teamChoice],1135,422);
                text(low[teamChoice],1135,305);

                //set data
                lineChart.setData(xAxis1,yAxis1);
                lineChart2.setData(xAxis1,yAxis2);
                lineChart3.setData(xAxis1,yAxis3);
                lineChart4.setData(xAxis1,yAxis4);

                //setMin and Max value
                lineChart.setMinY(0);
                lineChart2.setMinY(0);
                lineChart3.setMinY(0);
                lineChart4.setMinY(0);

                lineChart.setMaxY(max);
                lineChart2.setMaxY(max);
                lineChart3.setMaxY(max);
                lineChart4.setMaxY(max);

                //Set Color for all lines
                lineChart.setPointColour(color(firstTeam.r,firstTeam.g,firstTeam.b));
                lineChart.setLineColour(color(firstTeam.r,firstTeam.g,firstTeam.b));
                lineChart2.setPointColour(color(secondTeam.r,secondTeam.g,secondTeam.b));
                lineChart2.setLineColour(color(secondTeam.r,secondTeam.g,secondTeam.b));
                lineChart3.setPointColour(color(thirdTeam.r,thirdTeam.g,secondTeam.b));
                lineChart3.setLineColour(color(thirdTeam.r,thirdTeam.g,thirdTeam.b));
                lineChart4.setPointColour(color(fourthTeam.r,fourthTeam.g,fourthTeam.b));
                lineChart4.setLineColour(color(fourthTeam.r,fourthTeam.g,fourthTeam.b));

                if(index == 24)
                {
                    lineChart.setPointSize(5);
                    lineChart.setLineWidth(5);
                }
                else
                {
                    lineChart.setPointSize(2);
                    lineChart.setLineWidth(2);
                }
                if(index == 25)
                {
                    lineChart2.setPointSize(5);
                    lineChart2.setLineWidth(5);
                }
                else
                {
                    lineChart2.setPointSize(2);
                    lineChart2.setLineWidth(2);
                }
                if(index == 26)
                {
                    lineChart3.setPointSize(5);
                    lineChart3.setLineWidth(5);
                }
                else
                {
                    lineChart3.setPointSize(2);
                    lineChart3.setLineWidth(2);
                }
                if(index == 27)
                {
                    lineChart4.setPointSize(5);
                    lineChart4.setLineWidth(5);
                }
                else
                {
                    lineChart4.setPointSize(2);
                    lineChart4.setLineWidth(2);
                }

                // Axis formatting and labels.
                lineChart.showXAxis(true);
                lineChart.showYAxis(true);
                lineChart2.showXAxis(true);
                lineChart2.showYAxis(true);
                lineChart3.showXAxis(true);
                lineChart3.showYAxis(true);
                lineChart4.showXAxis(true);
                lineChart4.showYAxis(true);

                //showData
                textSize(9);
                lineChart.draw(270,180,707,323);
                lineChart2.draw(270,180,707,323);
                lineChart3.draw(270,180,707,323);
                lineChart4.draw(270,180,707,323);
                break;
            case 28:
            case 29:
            case 30:
            case 31:
                if(index == 28)
                {
                    teamChoice = 0;
                }
                if(index == 29)
                {
                    teamChoice = 1;
                }
                if(index == 30)
                {
                    teamChoice = 2;
                }
                if(index == 31)
                {
                    teamChoice = 3;
                }
                //GUI display for group
                //team choice setup
                graphGroup = loadImage("GroupHBig.png");
                image(graphGroup,(float)826.5,178,141,40);
                //group team key for plot
                firstTeam.logo = loadImage("teamPhotos/"+ Countries2014.get(28).name +"Main.png");
                firstTeam.name = Countries2014.get(28).name;
                firstTeam.r = 249;
                firstTeam.g = 175;
                firstTeam.b = 59;
                firstTeam.draw();

                secondTeam.logo = loadImage("teamPhotos/"+ Countries2014.get(29).name +"Main.png");
                secondTeam.name = Countries2014.get(29).name;
                secondTeam.r = 8;
                secondTeam.g = 150;
                secondTeam.b = 74;
                secondTeam.draw();

                thirdTeam.logo = loadImage("teamPhotos/"+ Countries2014.get(30).name +"Main.png");
                thirdTeam.name = Countries2014.get(30).name;
                thirdTeam.r = 193;
                thirdTeam.g = 38;
                thirdTeam.b = 45;
                thirdTeam.draw();

                fourthTeam.logo = loadImage("teamPhotos/"+ Countries2014.get(31).name +"Main.png");
                fourthTeam.name = Countries2014.get(31).name;
                fourthTeam.r = 45;
                fourthTeam.g = 49;
                fourthTeam.b = 133;
                fourthTeam.draw();

                //match fix text
                textFont(countryFont, 30);
                textAlign(CENTER);
                fill(192,231,187);
                text("Match 1",810,586);
                text("Match 2",810,661);
                text("Match 3",810,731);
                // vs text setup
                textFont(countryFont, 30);
                textAlign(CENTER);
                text("Vs",900,586);
                text("Vs",900,661);
                text("Vs",900,731);


                //matches setup
                indexFirstRival = getTeamIndex(Countries2014.get(index).match1);
                firstMatch.logo = loadImage("teamPhotos/" + Countries2014.get(indexFirstRival).name + "Main.png");
                firstMatch.rivalName = Countries2014.get(index).match1;
                firstMatch.draw();

                indexSecondRival = getTeamIndex(Countries2014.get(index).match2);
                secondMatch.logo = loadImage("teamPhotos/" + Countries2014.get(indexSecondRival).name + "Main.png");
                secondMatch.rivalName = Countries2014.get(index).match2;
                secondMatch.draw();

                indexThirdRival = getTeamIndex(Countries2014.get(index).match3);
                thirdMatch.logo = loadImage("teamPhotos/" + Countries2014.get(indexThirdRival).name + "Main.png");
                thirdMatch.rivalName = Countries2014.get(index).match3;
                thirdMatch.draw();

                firstMatch.team1 = index;
                firstMatch.team2 = indexFirstRival;
                secondMatch.team1 = index;
                secondMatch.team2 = indexSecondRival;
                thirdMatch.team1 = index;
                thirdMatch.team2 = indexThirdRival;


                //ranking title
                fill(192,231,187);
                textFont(countryFont, 30);
                textAlign(CENTER);
                text("Worst Ranking",1130,365);
                text("Best Ranking",1130,248);

                //ranking info
                hRank = loadImage("hRankBackground.png");
                lRank = loadImage("lRankBackground.png");
                image(hRank,1093,258,80,80);
                image(lRank,1093,377,80,80);

                //team info
                textFont(countryFont, 25);
                textAlign(LEFT);
                text("Participations in World Cup Finals",48,586);
                text("Current Fifa world ranking",80,661);
                text("Best Result in World Cup finals",62,731);

                //circle for info
                fill(128,210,154);
                ellipse(602,576,66,66);
                fill(192,231,187);
                ellipse(602,576,56,56);

                fill(128,210,154);
                ellipse(602,651,66,66);
                fill(192,231,187);
                ellipse(602,651,56,56);

                infoBackground = loadImage("infoBackground.png");
                image(infoBackground, 480, 700);

                textFont(countryFont, 30);
                textAlign(CENTER);
                fill(0);
                text(Countries2014.get(index).numParticipations,602,586);
                text(Countries2014.get(index).currentRank,602,661);
                text(Countries2014.get(index).bestResult,602,731);

                //data display for group
                j = 0;//array Index
                for(int i = minVal; i < maxVal; i++)
                {
                    xAxis1[j] = j;
                    yAxis1[j] = Countries2014.get(28).rankHistory.get(i).rank;
                    if(high[0] < yAxis1[j])
                    {
                        high[0] = (int)yAxis1[j];
                    }
                    if(low[0] > yAxis1[j])
                    {
                        low[0] = (int)yAxis1[j];
                    }
                    if(max < yAxis1[j])
                    {
                        max = yAxis1[j];
                    }
                    yAxis2[j] = Countries2014.get(29).rankHistory.get(i).rank;
                    if(high[1] < yAxis2[j])
                    {
                        high[1] = (int)yAxis2[j];
                    }
                    if(low[1] > yAxis2[j])
                    {
                        low[1] = (int)yAxis2[j];
                    }
                    if(max < yAxis2[j])
                    {
                        max = yAxis2[j];
                    }
                    yAxis3[j] = Countries2014.get(30).rankHistory.get(i).rank;
                    if(high[2] < yAxis3[j])
                    {
                        high[2] = (int)yAxis3[j];
                    }
                    if(low[2] > yAxis3[j])
                    {
                        low[2] = (int)yAxis3[j];
                    }
                    if(max < yAxis3[j])
                    {
                        max = yAxis3[j];
                    }
                    yAxis4[j] = Countries2014.get(31).rankHistory.get(i).rank;
                    if(high[3] < yAxis4[j])
                    {
                        high[3] = (int)yAxis4[j];
                    }
                    if(low[3] > yAxis4[j])
                    {
                        low[3] = (int)yAxis4[j];
                    }
                    if(max < yAxis4[j])
                    {
                        max = yAxis4[j];
                    }

                    j++;

                }

                //ranking text
                textFont(countryFont, 30);
                textAlign(CENTER);
                fill(0);
                text(high[teamChoice],1135,422);
                text(low[teamChoice],1135,305);

                //set data
                lineChart.setData(xAxis1,yAxis1);
                lineChart2.setData(xAxis1,yAxis2);
                lineChart3.setData(xAxis1,yAxis3);
                lineChart4.setData(xAxis1,yAxis4);

                //setMin and Max value
                lineChart.setMinY(0);
                lineChart2.setMinY(0);
                lineChart3.setMinY(0);
                lineChart4.setMinY(0);

                lineChart.setMaxY(max);
                lineChart2.setMaxY(max);
                lineChart3.setMaxY(max);
                lineChart4.setMaxY(max);

                //Set Color for all lines
                lineChart.setPointColour(color(firstTeam.r,firstTeam.g,firstTeam.b));
                lineChart.setLineColour(color(firstTeam.r,firstTeam.g,firstTeam.b));
                lineChart2.setPointColour(color(secondTeam.r,secondTeam.g,secondTeam.b));
                lineChart2.setLineColour(color(secondTeam.r,secondTeam.g,secondTeam.b));
                lineChart3.setPointColour(color(thirdTeam.r,thirdTeam.g,secondTeam.b));
                lineChart3.setLineColour(color(thirdTeam.r,thirdTeam.g,thirdTeam.b));
                lineChart4.setPointColour(color(fourthTeam.r,fourthTeam.g,fourthTeam.b));
                lineChart4.setLineColour(color(fourthTeam.r,fourthTeam.g,fourthTeam.b));

                if(index == 28)
                {
                    lineChart.setPointSize(5);
                    lineChart.setLineWidth(5);
                }
                else
                {
                    lineChart.setPointSize(2);
                    lineChart.setLineWidth(2);
                }
                if(index == 29)
                {
                    lineChart2.setPointSize(5);
                    lineChart2.setLineWidth(5);
                }
                else
                {
                    lineChart2.setPointSize(2);
                    lineChart2.setLineWidth(2);
                }
                if(index == 30)
                {
                    lineChart3.setPointSize(5);
                    lineChart3.setLineWidth(5);
                }
                else
                {
                    lineChart3.setPointSize(2);
                    lineChart3.setLineWidth(2);
                }
                if(index == 31)
                {
                    lineChart4.setPointSize(5);
                    lineChart4.setLineWidth(5);
                }
                else
                {
                    lineChart4.setPointSize(2);
                    lineChart4.setLineWidth(2);
                }

                // Axis formatting and labels.
                lineChart.showXAxis(true);
                lineChart.showYAxis(true);
                lineChart2.showXAxis(true);
                lineChart2.showYAxis(true);
                lineChart3.showXAxis(true);
                lineChart3.showYAxis(true);
                lineChart4.showXAxis(true);
                lineChart4.showYAxis(true);

                //showData
                textSize(9);
                lineChart.draw(270,180,707,323);
                lineChart2.draw(270,180,707,323);
                lineChart3.draw(270,180,707,323);
                lineChart4.draw(270,180,707,323);
                break;
            default:
                println("ERROR PASSES ALLOWED GROUP STAGE");

        }

    }


    //William's classes

    public class groupTeamIcon{
        PImage logo;
        String name;
        float x,y;
        float r,g,b;

        public groupTeamIcon(String name, float x, float y) {
            this.name = name;
            this.x = x;
            this.y = y;
            logo = loadImage("TeamPhotos/"+name+"Team.png");
        }

        public void draw(){
            if(isHovered()){
                cursorChanged = true;
                cursor(HAND);
                fill(r,g,b);
                ellipse(x,y ,50,50);
                image(logo,x - 20,y - 20,40,40);
                fill(r,g,b);
                textAlign(CENTER);
                if(name.equals("Bosnia-Herzegovina"))
                {
                    textFont(countryFont, 20);
                }
                else
                {
                    textFont(countryFont, 30);
                }
                text(name, x+130,y+15);
            }
            else{
                if(cursorChanged){
                    cursorChanged = false;
                    cursor(ARROW);
                }
                fill(r,g,b);
                ellipse(x,y ,50,50);
                image(logo,x - (float)17.5,y - (float)17.5,35,35);
                fill(192,231,187);
                textAlign(CENTER);
                if(name.equals("Bosnia-Herzegovina"))
                {
                    textFont(countryFont, 20);
                }
                else
                {
                    textFont(countryFont, 30);
                }
                text(name, x+130,y+15);
            }
        }

        public boolean isHovered(){
            if(mouseX >= x - 20 && mouseX <= x + 20 && mouseY <= y + 20 && mouseY >= y - 20){
                return true;
            }
            else{
                return false;
            }
        }
    }


    public class groupMatch{
        int code;
        int team1;
        int team2;
        PImage logo;
        String rivalName;
        float x,y;
        float r = 192;
        float g = 231;
        float b = 187;

        public groupMatch(String name, float x, float y, int index) {
            this.rivalName = name;
            this.x = x;
            this.y = y;
            code = index;
            logo = loadImage("TeamPhotos/"+name+"Team.png");
        }

        public void draw(){
            if(isHovered()){
                cursorChanged = true;
                cursor(HAND);
                fill(r,g,b);
                ellipse(x,y ,50,50);
                image(logo,x - 20,y - 20,40,40);
            }
            else{
                if(cursorChanged){
                    cursorChanged = false;
                    cursor(ARROW);
                }
                fill(r,g,b);
                ellipse(x,y ,50,50);
                image(logo,x - (float)17.5,y - (float)17.5,35,35);
                fill(192,231,187);
                textAlign(CENTER);
            }
            if(isTextHovered())
            {
                fill(255,0,0);
                textAlign(CENTER);
                if(rivalName.equals("Bosnia-Herzegovina"))
                {
                    textFont(countryFont, 20);
                }
                else
                {
                    textFont(countryFont, 30);
                }
                text(rivalName, x+160,y + 10);
            }
            else
            {
                fill(0);
                textAlign(CENTER);
                if(rivalName.equals("Bosnia-Herzegovina"))
                {
                    textFont(countryFont, 20);
                }
                else
                {
                    textFont(countryFont, 30);
                }
                text(rivalName, x+160,y + 10);
            }
        }

        public boolean isHovered(){
            if(mouseX >= x-20 && mouseX <= x + 20 && mouseY <= y + 20 && mouseY >= y - 20){
                matchIndex = code;
                return true;
            }
            else{
                return false;
            }
        }

        public boolean isTextHovered(){
            if(mouseX >= x + 40 && mouseX <= x + 200 && mouseY <= y + 20 && mouseY >= y - 20){
                matchIndex = code;
                return true;
            }
            else{
                return false;
            }
        }
    }

    //William/Irum's helper functions
    public int getTeamIndex(String t)
    {
        if(t.equals("Brazil"))
        {
            return 0;
        }
        else if(t.equals("Croatia"))
        {
            return 1;
        }
        else if(t.equals("Mexico"))
        {
            return 2;
        }
        else if(t.equals("Cameroon"))
        {
            return 3;
        }
        else if(t.equals("Spain"))
        {
            return 4;
        }
        else if(t.equals("Netherlands"))
        {
            return 5;
        }
        else if(t.equals("Chile"))
        {
            return 6;
        }
        else if(t.equals("Australia"))
        {
            return 7;
        }
        else if(t.equals("Colombia"))
        {
            return 8;
        }
        else if(t.equals("Greece"))
        {
            return 9;
        }
        else if(t.equals("Ivory Coast"))
        {
            return 10;
        }
        else if(t.equals("Japan"))
        {
            return 11;
        }
        else if(t.equals("Uruguay"))
        {
            return 12;
        }
        else if(t.equals("Costa Rica"))
        {
            return 13;
        }
        else if(t.equals("England"))
        {
            return 14;
        }
        else if(t.equals("Italy"))
        {
            return 15;
        }
        else if(t.equals("Switzerland"))
        {
            return 16;
        }
        else if(t.equals("Ecuador"))
        {
            return 17;
        }
        else if(t.equals("France"))
        {
            return 18;
        }
        else if(t.equals("Honduras"))
        {
            return 19;
        }
        else if(t.equals("Argentina"))
        {
            return 20;
        }
        else if(t.equals("Bosnia-Herzegovina")||t.equals("Bosnia"))
        {
            return 21;
        }
        else if(t.equals("Iran"))
        {
            return 22;
        }
        else if(t.equals("Nigeria"))
        {
            return 23;
        }
        else if(t.equals("Germany"))
        {
            return 24;
        }
        else if(t.equals("Portugal"))
        {
            return 25;
        }
        else if(t.equals("Ghana"))
        {
            return 26;
        }
        else if(t.equals("United States"))
        {
            return 27;
        }
        else if(t.equals("Belgium"))
        {
            return 28;
        }
        else if(t.equals("Algeria"))
        {
            return 29;
        }
        else if(t.equals("Russia"))
        {
            return 30;
        }
        else if(t.equals("South Korea"))
        {
            return 31;
        }
        return 32;
    }

    public int getYearIndex(Integer y)
    {
        if(y==1930)
        {
            return 0;
        }
        else if(y==1934)
        {
            return 1;
        }
        else if(y==1938)
        {
            return 2;
        }
        else if(y==1950)
        {
            return 3;
        }
        else if(y==1954)
        {
            return 4;
        }
        else if(y==1958)
        {
            return 5;
        }
        else if(y==1962)
        {
            return 6;
        }
        else if(y==1966)
        {
            return 7;
        }
        else if(y==1970)
        {
            return 8;
        }
        else if(y==1974)
        {
            return 9;
        }
        else if(y==1978)
        {
            return 10;
        }
        else if(y==1982)
        {
            return 11;
        }
        else if(y==1986)
        {
            return 12;
        }
        else if(y==1990)
        {
            return 13;
        }
        else if(y==1994)
        {
            return 14;
        }
        else if(y==1998)
        {
            return 15;
        }
        else if(y==2002)
        {
            return 16;
        }
        else if(y==2006)
        {
            return 17;
        }
        else if(y==2010)
        {
            return 18;
        }
        return 19;
    }

    public int getYear(Integer i)
    {
        if(i==0)
        {
            return 1930;
        }
        else if(i==1)
        {
            return 1934;
        }
        else if(i==2)
        {
            return 1938;
        }
        else if(i==3)
        {
            return 1950;
        }
        else if(i==4)
        {
            return 1954;
        }
        else if(i==5)
        {
            return 1958;
        }
        else if(i==6)
        {
            return 1962;
        }
        else if(i==7)
        {
            return 1966;
        }
        else if(i==8)
        {
            return 1970;
        }
        else if(i==9)
        {
            return 1974;
        }
        else if(i==10)
        {
            return 1978;
        }
        else if(i==11)
        {
            return 1982;
        }
        else if(i==12)
        {
            return 1986;
        }
        else if(i==13)
        {
            return 1990;
        }
        else if(i==14)
        {
            return 1994;
        }
        else if(i==15)
        {
            return 1998;
        }
        else if(i==16)
        {
            return 2002;
        }
        else if(i==17)
        {
            return 2006;
        }
        else if(i==18)
        {
            return 2010;
        }
        return 1930;
    }

    public String getTeam(Integer i)
    {
        if(i==0)
        {
            return "Brazil";
        }
        else if(i==1)
        {
            return "Croatia";
        }
        else if(i==2)
        {
            return "Mexico";
        }
        else if(i==3)
        {
            return "Cameroon";
        }
        else if(i==4)
        {
            return "Spain";
        }
        else if(i==5)
        {
            return "Netherlands";
        }
        else if(i==6)
        {
            return "Chile";
        }
        else if(i==7)
        {
            return "Australia";
        }
        else if(i==8)
        {
            return "Colombia";
        }
        else if(i==9)
        {
            return "Greece";
        }
        else if(i==10)
        {
            return "Ivory Coast";
        }
        else if(i==11)
        {
            return "Japan";
        }
        else if(i==12)
        {
            return "Uruguay";
        }
        else if(i==13)
        {
            return "Costa Rica";
        }
        else if(i==14)
        {
            return "England";
        }
        else if(i==15)
        {
            return "Italy";
        }
        else if(i==16)
        {
            return "Switzerland";
        }
        else if(i==17)
        {
            return "Ecuador";
        }
        else if(i==18)
        {
            return "France";
        }
        else if(i==19)
        {
            return "Honduras";
        }
        else if(i==20)
        {
            return "Argentina";
        }
        else if(i==21)
        {
            return "Bosnia-Herzegovina";
        }
        else if(i==22)
        {
            return "Iran";
        }
        else if(i==23)
        {
            return "Nigeria";
        }
        else if(i==24)
        {
            return "Germany";
        }
        else if(i==25)
        {
            return "Portugal";
        }
        else if(i==26)
        {
            return "Ghana";
        }
        else if(i==27)
        {
            return "United States";
        }
        else if(i==28)
        {
            return "Belgium";
        }
        else if(i==29)
        {
            return "Algeria";
        }
        else if(i==30)
        {
            return "Russia";
        }
        else if(i==31)
        {
            return "South Korea";
        }
        return "";
    }


    public void controlEvent(ControlEvent theControlEvent) {
        if(theControlEvent.isFrom("DateRange")) {
            // min and max values are stored in an array.
            // access this array with controller().arrayValue().
            // min is at index 0, max is at index 1.
            minVal = (int) theControlEvent.getController().getArrayValue(0);
            maxVal = (int) theControlEvent.getController().getArrayValue(1);
            //println("range Range, done.");
        }
        if(theControlEvent.isFrom("CupRange"))
        {
            rangeBeg = (int) theControlEvent.getController().getArrayValue(0);
            rangeEnd = (int) theControlEvent.getController().getArrayValue(1);
            //println("range Range, done.");
        }

    }

}
