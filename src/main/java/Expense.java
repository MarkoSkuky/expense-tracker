import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Expense {
    private double amount;
    private Category category;
    private LocalDate date;
    private String note;
    private String id;

    public Expense(String note, Double amount, Category category, LocalDate date) {
        this.id = UUID.randomUUID().toString();
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.note = note;
    }

    public double getAmount() {
        return amount;
    }

    public Category getCategory() {
        return category;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getNote() {
        return note;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return note + " (" + category + "): " + amount + "€ " + date.format(formatter) + "\n";
    }
}
