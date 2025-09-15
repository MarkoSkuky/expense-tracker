import java.io.IOException;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws IOException {
        TransactionManager manager = new TransactionManager();
        DatabaseManager.createTable();
        manager.loadFromDatabase();
//
//        Transaction[] transactions = {
//                new Transaction(TransactionType.EXPENSE, "Drogeria", 14.5, Category.SHOPPING, LocalDate.now().minusDays(1)),
//                new Transaction(TransactionType.EXPENSE, "Obed v reštaurácii", 8.9, Category.FOOD, LocalDate.now()),
//                new Transaction(TransactionType.EXPENSE, "Permanentka do fitka", 25.0, Category.SPORT, LocalDate.now().minusDays(7)),
//                new Transaction(TransactionType.EXPENSE, "Benzín", 45.0, Category.TRANSPORT, LocalDate.now().minusDays(2)),
//                new Transaction(TransactionType.EXPENSE, "Kino lístok", 6.5, Category.ENTERTAINMENT, LocalDate.now().minusDays(3)),
//                new Transaction(TransactionType.EXPENSE, "Zubár", 30.0, Category.HEALTH, LocalDate.now().minusDays(10)),
//                new Transaction(TransactionType.EXPENSE, "Kniha o Jave", 18.0, Category.EDUCATION, LocalDate.now().minusDays(5)),
//                new Transaction(TransactionType.EXPENSE, "Nové tričko", 22.0, Category.SHOPPING, LocalDate.now().minusDays(4)),
//                new Transaction(TransactionType.EXPENSE, "Elektrina", 60.0, Category.BILLS, LocalDate.now().minusDays(15)),
//                new Transaction(TransactionType.EXPENSE, "Raňajky", 4.2, Category.FOOD, LocalDate.now()),
//                new Transaction(TransactionType.INCOME, "Výplata", 1200.0, Category.SALARY, LocalDate.now().minusDays(3)),
//                new Transaction(TransactionType.INCOME, "Predaj starej knihy", 15.0, Category.OTHER, LocalDate.now().minusDays(2)),
//                new Transaction(TransactionType.INCOME, "Bonus z práce", 200.0, Category.SALARY, LocalDate.now().minusDays(1)),
//                new Transaction(TransactionType.INCOME, "Darček od kamaráta", 50.0, Category.GIFT, LocalDate.now())
//        };

//
//        for (Transaction t : transactions) {
//            manager.addTransaction(t);
//        }
//        for (Transaction t : DatabaseManager.getAllTransactionsFromDB()) {
//            System.out.println(t);
//        }

        manager.addTransaction(new Transaction(TransactionType.INCOME, "DruhaVyplata", 500.0, Category.SALARY, LocalDate.now()));

        System.out.println("Tvoj zostatok uctu: " + manager.getBalance());
        System.out.println("Tvoje prijmy: " + manager.getTotalIncomeValue());
        System.out.println("Tvoje vydavky: " + manager.getTotalExpenseValue());

        for (Transaction t : manager.getAllIncomeTransactions()) {
            System.out.println(t);
        }

    }
}
