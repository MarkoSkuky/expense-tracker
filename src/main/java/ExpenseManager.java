import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ExpenseManager {
    private ArrayList<Expense> expenses = new ArrayList<>();
    private static int nextId = 0;

    public ExpenseManager() {

    }

    /// ADD/REMOVE METHODS
    public void addExpense(Expense expense) {
        expenses.add(expense);
        DatabaseManager.insertExpense(expense);
    }
    public void removeExpense(Expense expense) {
        expenses.remove(expense);
        DatabaseManager.removeFromDB(expense);
    }

    /// UPDATE METHODS
    public void updateAmount(Expense e, double newAmount) {
        e.setAmount(newAmount);
        DatabaseManager.updateExpense(e);
    }

    public void updateCategory(Expense e, Category newCategory) {
        e.setCategory(newCategory);
        DatabaseManager.updateExpense(e);
    }

    public void updateDate(Expense e, LocalDate newDate) {
        e.setDate(newDate);
        DatabaseManager.updateExpense(e);
    }

    public void updateNote(Expense e, String newNote) {
        e.setNote(newNote);
        DatabaseManager.updateExpense(e);
    }

    public void updateId(Expense e, String newId) {
        e.setId(newId);
        DatabaseManager.updateExpense(e);
    }

    public ArrayList<Expense> getAllExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("You dont have any expenses yet");
        }
        return new ArrayList<Expense>(expenses);
    }

    public ArrayList<Expense> getExpensesByCategory(Category category) {
        ArrayList<Expense> filteredExpenses = new ArrayList<>();
        for (Expense expense : expenses) {
            if (expense.getCategory() == category) {
                filteredExpenses.add(expense);
            }
        }
        if (filteredExpenses.isEmpty()) {
            System.out.println("No expenses found in this category.");
        }
        return filteredExpenses;
    }

    public ArrayList<Expense> getExpensesByDate(LocalDate date) {
        ArrayList<Expense> filteredExpenses = new ArrayList<>();
        for (Expense e : expenses) {
            if (e.getDate().equals(date)) {
                filteredExpenses.add(e);
            }
        }
        if (filteredExpenses.isEmpty()) {
            System.out.println("There are no expenses on this date");

        }
        return filteredExpenses;
    }

    public ArrayList<Expense> getExpensesBetweenDates(LocalDate startDate, LocalDate endDate) {
        ArrayList<Expense> filteredExpenses = new ArrayList<>();
        for (Expense e : expenses) {
            if (!e.getDate().isBefore(startDate) && !e.getDate().isAfter(endDate)) {
                filteredExpenses.add(e);
            }
        }
        return filteredExpenses;
    }

    public double getTotalExpenses() {
        double totalExpenses = 0;
        for (Expense expense : expenses) {
            totalExpenses += expense.getAmount();
        }
        return totalExpenses;
    }

    public void saveToFile() throws IOException {
        String filePath = "files/expenses.txt";
        try (FileWriter writer = new FileWriter(filePath)) {
            for (Expense e : expenses) {
                writer.write(e.getId() + ";" + e.getNote() + ";" + e.getAmount() + ";" + e.getCategory() + ";" + e.getDate().toString() + "\n");
            }
        }
    }
    public void loadFromFile() throws IOException {
        String filePath = "files/expenses.txt";
        expenses.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 5) {
                    String id = parts[0];
                    String note = parts[1];
                    double amount = Double.parseDouble(parts[2]);
                    Category category = Category.valueOf(parts[3]);
                    LocalDate date = LocalDate.parse(parts[4]);

                    Expense expense = new Expense(note, amount, category, date);
                    expense.setId(id);

                    expenses.add(expense);
                }
            }
        }
    }
}
