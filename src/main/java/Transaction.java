import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Transaction {
    private double amount;
    private Category category;
    private LocalDate date;
    private String note;
    private String id;
    private TransactionType type;

    public Transaction(TransactionType type, String note, Double amount, Category category, LocalDate date) {
        this.id = UUID.randomUUID().toString();
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.note = note;
        this.type = type;
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

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return type + " " + note + " (" + category + "): " + amount + "€ " + date.format(formatter) + "\n";
    }
}
