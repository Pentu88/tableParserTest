package main;

// import liigaParser.parserTest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.select.Evaluator;

import java.net.URL;

class Ottelu {
    private String id, dateTime, home, away, result;

    public Ottelu(String id, String dateTime, String home, String away, String result) {
        this.id = id;
        this.dateTime = dateTime;
        this.home = home;
        this.away = away;
        this.result = result;
    }

    public String toString() {
        return (Integer.parseInt(id) < 10 ? "0" + id : id) + " " + dateTime + " " + home + " - " + away + " " + result;
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println("Liiga parser test!");

        try {
            URL url = new URL("https://liiga.fi/fi/ottelut/2020-2021/runkosarja/");
            Document document = Jsoup.parse(url, 3000);

            // Etsitään oikea taulukko
            Element table = document.select("table[id=games]").first();
            Elements rows = table.select("tr");

            // Puretaan taulukko
            String pvm = null;
            for (int i = 1; i < 15; i++) { // Ohitetaan otsikko rivi (int i = 1; i < rows.size(); i++)
                Element row = rows.get(i);
                Elements cols = row.select("td");
                
                String tmpPvm = cols.get(1).text();
                if (!tmpPvm.isBlank()) { pvm = tmpPvm; }

                //System.out.println(pvm + " klo " + cols.get(2).text() + " " + cols.get(3).text() + " " + cols.get(5).text());

                System.out.println(row2Match(pvm, cols));
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private static Ottelu row2Match(String date, Elements cols) {

        String id = "" + cols.get(0).text();
        String dateTime = date + " " + cols.get(2).text();

        String[] teams = cols.get(3).text().split("-", 2);
        String home = teams[0].trim();
        String away = teams[1].trim();

        String[] goals = cols.get(5).text().split("—", 2);
        String homeGoals = goals[0].trim();
        String awayGoals = goals[1].trim();

        String result = homeGoals + "-" + awayGoals;

        return new Ottelu(id, dateTime, home, away, result);
    }
}
