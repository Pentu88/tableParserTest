package main;

// import liigaParser.parserTest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.sql.SQLOutput;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
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

                System.out.println(pvm + " klo " + cols.get(2).text() + " " + cols.get(3).text() + " " + cols.get(5).text());
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
