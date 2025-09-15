import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class TransactionManager {
    private ArrayList<Transaction> transactions = new ArrayList<>();

    public TransactionManager() {

    }

    public void loadFromDatabase() {
        transactions = DatabaseManager.getAllTransactionsFromDB();
    }

    /// ADD/REMOVE METHODS
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        DatabaseManager.insertTransaction(transaction);
    }
    public void removeTransaction(Transaction transaction) {
        transactions.remove(transaction);
        DatabaseManager.removeFromDB(transaction);
    }

    /// UPDATE METHODS
    public void updateAmount(Transaction e, double newAmount) {
        e.setAmount(newAmount);
        DatabaseManager.updateTransaction(e);
    }

    public void updateCategory(Transaction e, Category newCategory) {
        e.setCategory(newCategory);
        DatabaseManager.updateTransaction(e);
    }

    public void updateDate(Transaction e, LocalDate newDate) {
        e.setDate(newDate);
        DatabaseManager.updateTransaction(e);
    }

    public void updateNote(Transaction e, String newNote) {
        e.setNote(newNote);
        DatabaseManager.updateTransaction(e);
    }

    public void updateId(Transaction e, String newId) {
        e.setId(newId);
        DatabaseManager.updateTransaction(e);
    }

    public ArrayList<Transaction> getAllTransactions() {
        if (transactions.isEmpty()) {
            System.out.println("You dont have any expenses yet");
        }
        return new ArrayList<Transaction>(transactions);
    }

    public ArrayList<Transaction> getAllIncomeTransactions() {
        ArrayList<Transaction> filteredTransactions = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (transaction.getType() == TransactionType.INCOME) {
                filteredTransactions.add(transaction);
            }
        }
        if (filteredTransactions.isEmpty()) {
            System.out.println("No transactions found in this category.");
        }
        return filteredTransactions;
    }

    public ArrayList<Transaction> getAllExpenseTransactions() {
        ArrayList<Transaction> filteredTransactions = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (transaction.getType() == TransactionType.EXPENSE) {
                filteredTransactions.add(transaction);
            }
        }
        if (filteredTransactions.isEmpty()) {
            System.out.println("No transactions found in this category.");
        }
        return filteredTransactions;
    }

    public double getTotalIncomeValue() {
        double value = 0;
        for (Transaction t : transactions) {
            if (t.getType() == TransactionType.INCOME) {
                value += t.getAmount();
            }
        }
        return value;
    }

    public double getTotalExpenseValue() {
        double value = 0;
        for (Transaction t : transactions) {
            if (t.getType() == TransactionType.EXPENSE) {
                value += t.getAmount();
            }
        }
        return value;
    }

    public ArrayList<Transaction> getTransactionsByCategory(Category category) {
        ArrayList<Transaction> filteredTransactions = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (transaction.getCategory() == category) {
                filteredTransactions.add(transaction);
            }
        }
        if (filteredTransactions.isEmpty()) {
            System.out.println("No transactions found in this category.");
        }
        return filteredTransactions;
    }

    public ArrayList<Transaction> getTransactionsByDate(LocalDate date) {
        ArrayList<Transaction> filteredTransactions = new ArrayList<>();
        for (Transaction e : transactions) {
            if (e.getDate().equals(date)) {
                filteredTransactions.add(e);
            }
        }
        if (filteredTransactions.isEmpty()) {
            System.out.println("There are no expenses on this date");

        }
        return filteredTransactions;
    }

    public ArrayList<Transaction> getTransactionsBetweenDates(LocalDate startDate, LocalDate endDate) {
        ArrayList<Transaction> filteredTransactions = new ArrayList<>();
        for (Transaction e : transactions) {
            if (!e.getDate().isBefore(startDate) && !e.getDate().isAfter(endDate)) {
                filteredTransactions.add(e);
            }
        }
        return filteredTransactions;
    }

    public double getBalance() {
        double balance = 0.0;
        for (Transaction transaction : transactions) {
            if (transaction.getType() == TransactionType.INCOME) {
                balance += transaction.getAmount();
            }
            else {
                balance -= transaction.getAmount();
            }
        }
        return balance;
    }

    public void saveToFile() throws IOException {
        String filePath = "files/expenses.txt";
        try (FileWriter writer = new FileWriter(filePath)) {
            for (Transaction e : transactions) {
                writer.write(e.getId() + ";" + e.getType() + ";" + e.getNote() + ";" + e.getAmount() + ";" + e.getCategory() + ";" + e.getDate().toString() + "\n");
            }
        }
    }
    public void loadFromFile() throws IOException {
        String filePath = "files/expenses.txt";
        transactions.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 6) {
                    String id = parts[0];
                    TransactionType type = TransactionType.valueOf(parts[1]);
                    String note = parts[2];
                    double amount = Double.parseDouble(parts[3]);
                    Category category = Category.valueOf(parts[4]);
                    LocalDate date = LocalDate.parse(parts[5]);

                    Transaction transaction = new Transaction(type, note, amount, category, date);
                    transaction.setId(id);

                    transactions.add(transaction);
                }
            }
        }
    }
}
