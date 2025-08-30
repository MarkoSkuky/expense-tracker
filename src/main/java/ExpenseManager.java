import java.util.ArrayList;

public class ExpenseManager {
    private ArrayList<Expense> expenses = new ArrayList<>();
    private static int nextId = 0;

    public ExpenseManager() {

    }
//...
    public void addExpense(Expense expense) {
        expenses.add(expense);
        expense.setId(nextId);
        nextId++;
    }
    public void removeExpense(Expense expense) {
        expenses.remove(expense);
    }
    public void removeExpenseById(int id) {
        //neskor skusit spravit cez iterator
        for (int i = 0; i < expenses.size(); i++) {
            if (expenses.get(i).getId() == id) {
                expenses.remove(i);
                return;
            }
        }
        System.out.println("Expense with this id does not exist.");
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

    public double getTotalExpenses() {
        double totalExpenses = 0;
        for (Expense expense : expenses) {
            totalExpenses += expense.getAmount();
        }
        return totalExpenses;
    }

    //	•	saveToFile() – uloží všetky výdavky do súboru
    //	•	loadFromFile() – načíta všetky výdavky zo súboru
}
