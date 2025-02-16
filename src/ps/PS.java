package ps;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        loadFile("src/ps/primaverasound-2.txt");
        int option;

        do {
            menu();
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    // Find totals
                    findTotals();
                    break;
                case 2:
                    // Search artist
                    searchArtist(scanner);
                    break;
                case 3:
                    // Headliners
                    break;
                case 4:
                    // Price tickets
                    break;
                case 0:
                    // Leave
                    break;
                default:
                    System.out.println("Invalid option, try again.");
            }
        } while (option != 0);

        scanner.close();
    }

    private static void menu() {
        System.out.println("\nMENU:");
        System.out.println("1. FIND TOTALS");
        System.out.println("2. SEARCH ARTIST");
        System.out.println("3. HEADLINERS");
        System.out.println("4. PRICE TICKETS");
        System.out.println("0. LEAVE");
        System.out.print("OPTION (0 .. 4) ? CHOOSE MENU OPTION: ");
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

    private int getTotalTickets() {
        return ticketsDay + ticketsFullFest + ticketsVip;
    }

    private float getEarnings() {
        return ticketsDay * priceDay + ticketsFullFest * priceFullFest + ticketsVip * priceVip;
    }

    private static void findTotals() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/ps/totals.txt"))) {
            bw.write("EDITION;YEAR;DATE;ASSISTANTS;TICKETSDAY;PRICEDAY;TICKETSFULLFEST;PRICEFULLFEST;TICKETSVIPS;PRICEVIP;TOTALTICKETS;EARNINGS\n");
            for (PS festival : festivals) {
                bw.write(festival.edition + ";" + festival.year + ";" + festival.date + ";" + festival.assistants + ";" +
                         festival.ticketsDay + ";" + festival.priceDay + ";" + festival.ticketsFullFest + ";" + festival.priceFullFest + ";" +
                         festival.ticketsVip + ";" + festival.priceVip + ";" + festival.getTotalTickets() + ";" + festival.getEarnings() + "\n");
            }
            System.out.println("File totals.txt has been created.");
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
        }
    }

    private static void searchArtist(Scanner scanner) {
        System.out.print("Enter artist: ");
        String artist = scanner.nextLine().toLowerCase();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/ps/artist.txt"))) {
            bw.write("EDITION;YEAR;HEADLINERS\n");
            for (PS festival : festivals) {
                if (festival.headLiners.toLowerCase().contains(artist)) {
                    bw.write(festival.edition + ";" + festival.year + ";" + festival.headLiners + "\n");
                }
            }
            System.out.println("File artist.txt has been created.");
        } catch (IOException e) {
            System.err.println("Error writing the file: " + e.getMessage());
        }
    }
}
