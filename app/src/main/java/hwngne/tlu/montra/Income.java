package hwngne.tlu.montra;

public class Income {
    private String category;
    private String description;
    private int cash;

    public Income(String category, String description, int cash) {
        this.category = category;
        this.description = description;
        this.cash = cash;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }
}
