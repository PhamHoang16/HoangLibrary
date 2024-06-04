package items;

public class Book extends Item {
    private String author;

    public Book(String id, String title, String publisher, int year, String author) {
        super(id, title, publisher, year);
        this.author = author;
    }

    public Book() {

    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "id: " + this.getId() + ", title: " + this.getTitle() + ", publisher: "
                + this.getPublisher() + ", year: " + this.getYear() + ", author: " + this.getAuthor() + ", isBorrowed: " + this.getStatus();
    }
}
