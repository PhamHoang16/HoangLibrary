package items;

public class Item implements Comparable<Item>{
    private String id;
    private String title;
    private String publisher;
    private int year;
    private boolean isBorrowed = false;

    public Item(String id, String title, String publisher, int year) {
        this.id = id;
        this.title = title;
        this.publisher = publisher;
        this.year = year;
    }

    public Item() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean getStatus() {
        return isBorrowed;
    }

    public void setStatus(boolean status) {
        this.isBorrowed = status;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public int compareTo(Item otherItem) {
        if (this.id == otherItem.getId()) return 0;
        return 1;
    }
}
