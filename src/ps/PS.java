package ps;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PS {

    String edition;
    short year;
    String date;
    int assistants;
    int ticketsDay;
    float priceDay;
    int ticketsFullFest;
    float priceFullFest;
    int ticketsVip;
    float priceVip;
    String headLiners;

    private static List<PS> festivals = new ArrayList<>();

    public PS(String edition, short year, String date, int assistants, int ticketsDay, float priceDay, int ticketsFullFest, float priceFullFest, int ticketsVip, float priceVip, String headLiners) {
        this.edition = edition;
        this.year = year;
        this.date = date;
        this.assistants = assistants;
        this.ticketsDay = ticketsDay;
        this.priceDay = priceDay;
        this.ticketsFullFest = ticketsFullFest;
        this.priceFullFest = priceFullFest;
        this.ticketsVip = ticketsVip;
        this.priceVip = priceVip;
        this.headLiners = headLiners;
    }

    public int getTotalTickets() {
        return ticketsDay + ticketsFullFest + ticketsVip;
    }

    public float getEarnings() {
        return ticketsDay * priceDay + ticketsFullFest * priceFullFest + ticketsVip * priceVip;
    }

    public static void main(String[] args) {
        loadFile("src/ps/primaverasound-2.txt");
    }

    private static void menu() {
        System.out.println("\nMENU:");
        System.out.println("1. FIND TOTALS");
        System.out.println("2. SEARCH ARTIST");
        System.out.println("3. HEADLINERS");
        System.out.println("4. PRICE TICKETS");
        System.out.println("0. LEAVE");
        System.out.print("OPTION (0 .. 4) ? ");
    }

    private static void loadFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            br.readLine(); // Skip header
            String line;

            while ((line = br.readLine()) != null) {
                String[] fields = line.split(";");

                if (fields.length < 11) continue;

                PS festival = new PS(
                        fields[0],                      // edition
                        Short.parseShort(fields[1]),    // year
                        fields[2],                      // date
                        Integer.parseInt(fields[3]),    // assistant
                        Integer.parseInt(fields[4]),    // ticketsDay
                        Float.parseFloat(fields[5]),    // priceDay
                        Integer.parseInt(fields[6]),    // ticketsFullFest
                        Float.parseFloat(fields[7]),    // priceFullFest
                        Integer.parseInt(fields[8]),    // ticketsVip
                        Float.parseFloat(fields[9]),    // priceVip
                        fields[10]                      // headLiners
                );
                festivals.add(festival);
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }
}
