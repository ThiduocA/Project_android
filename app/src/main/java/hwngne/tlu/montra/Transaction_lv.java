package hwngne.tlu.montra;

public class Transaction_lv {
    private int image;
    private String title;
    private String description;
    private String cost;
    private String time;

    public Transaction_lv() {
    }

    public Transaction_lv(int image, String title, String description, String cost, String time) {
        this.image = image;
        this.title = title;
        this.description = description;
        this.cost = cost;
        this.time = time;
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

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
