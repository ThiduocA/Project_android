package hwngne.tlu.montra;

public class Transaction_lv {
    private int image;
    private String title;
    private String description;
    private int cost;
    private String time;

    public Transaction_lv() {
    }

    public Transaction_lv(int image, String title, String description, int cost, String time) {
        this.image = image;
        this.title = title;
        this.description = description;
        this.cost = cost;
        this.time = time;
    }

    public Transaction_lv(String title, String description, int cost, String time) {
        this.title = title;
        this.description = description;
        this.cost = cost;
        this.time = time;
    }

    public Transaction_lv(String title, int cost) {
        this.title = title;
        this.cost = cost;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
