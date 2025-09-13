import java.io.IOException;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws IOException {
        ExpenseManager manager = new ExpenseManager();

        Expense[] expenses = {
                new Expense("Drogeria", 14.5, Category.SHOPPING, LocalDate.now().minusDays(1)),
                new Expense("Obed v reštaurácii", 8.9, Category.FOOD, LocalDate.now()),
                new Expense("Permanentka do fitka", 25.0, Category.SPORT, LocalDate.now().minusDays(7)),
                new Expense("Benzín", 45.0, Category.TRANSPORT, LocalDate.now().minusDays(2)),
                new Expense("Kino lístok", 6.5, Category.ENTERTAINMENT, LocalDate.now().minusDays(3)),
                new Expense("Zubár", 30.0, Category.HEALTH, LocalDate.now().minusDays(10)),
                new Expense("Kniha o Jave", 18.0, Category.EDUCATION, LocalDate.now().minusDays(5)),
                new Expense("Nové tričko", 22.0, Category.SHOPPING, LocalDate.now().minusDays(4)),
                new Expense("Elektrina", 60.0, Category.BILLS, LocalDate.now().minusDays(15)),
                new Expense("Raňajky", 4.2, Category.FOOD, LocalDate.now())
        };

        for (Expense e : expenses) {
            manager.addExpense(e);
        }

        manager.saveToFile();

        System.out.println(manager.getExpensesBetweenDates(LocalDate.of(2025, 9, 8), LocalDate.now()));

    }
}
