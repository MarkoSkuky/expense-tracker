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
    //    •	addExpense(Expense expense)
    //	•	removeExpense(Expense expense)
    //	•	getAllExpenses()
    //	•	getExpensesByCategory(Category category)
    //	•	getTotalExpenses()
    //	•	saveToFile() – uloží všetky výdavky do súboru
    //	•	loadFromFile() – načíta všetky výdavky zo súboru
}
