package items;

import java.util.Scanner;

public class CD extends Item {
    private String artist;

    public CD(String id, String title, String publisher, int year, String artist) {
        super(id, title, publisher, year);
        this.artist = artist;
    }

    public CD() {

    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    @Override
    public String toString() {
        return "id: " + this.getId() + ", title: " + this.getTitle() + ", publisher: "
                + this.getPublisher() + ", year: " + this.getYear() + ", artist: " + this.getArtist() + ", isBorrow: " + this.getStatus();
    }

    @Override
    public void enterDetail() {
        Scanner scanner = new Scanner(System.in);
        super.enterDetail();
        System.out.println("Enter artist:");
        String artist = scanner.nextLine();
        setArtist(artist);
    }
}
