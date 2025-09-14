import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

public class DatabaseManager {

    private static final String URL = "jdbc:sqlite:expenses.db";

    // pripojenie k databáze
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    // vytvorenie tabuľky
    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS expenses (" +
                "id TEXT PRIMARY KEY," +
                "note TEXT," +
                "amount REAL," +
                "category TEXT," +
                "date TEXT" +
                ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table created or already exists.");
        } catch (SQLException e) {
            System.out.println("Error creating table: " + e.getMessage());
        }
    }

    // vloženie výdavku
    public static void insertExpense(Expense e) {
        String sql = "INSERT INTO expenses(id, note, amount, category, date) VALUES(" +
                "'" + e.getId() + "', " +
                "'" + e.getNote() + "', " +
                e.getAmount() + ", " +
                "'" + e.getCategory().toString() + "', " +
                "'" + e.getDate().toString() + "'" +
                ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(sql);
            System.out.println("Expense inserted: " + e.getNote());

        } catch (SQLException ex) {
            System.out.println("Error inserting expense: " + ex.getMessage());
        }
    }

    // načítanie všetkých výdavkov
    public static ArrayList<Expense> getAllExpensesFromDB() {
        ArrayList<Expense> expenses = new ArrayList<>();
        String sql = "SELECT * FROM expenses";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String id = rs.getString("id");
                String note = rs.getString("note");
                double amount = rs.getDouble("amount");
                Category category = Category.valueOf(rs.getString("category"));
                LocalDate date = LocalDate.parse(rs.getString("date"));

                Expense e = new Expense(note, amount, category, date);
                e.setId(id);
                expenses.add(e);
            }

        } catch (SQLException ex) {
            System.out.println("Error reading expenses: " + ex.getMessage());
        }

        return expenses;
    }

    public static void removeFromDB(Expense e) {
        String sql = "DELETE FROM expenses WHERE id='" + e.getId() + "';";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {

            int affectedRows = stmt.executeUpdate(sql); //toto je len na kontrolovanie ci naozaj sa odstranilo nieco z DB

            if (affectedRows > 0) {
                System.out.println("Expense deleted: " + e.getNote());
            } else {
                System.out.println("No expense found with id: " + e.getId());
            }

        } catch (SQLException ex) {
            System.out.println("Error deleting expense: " + ex.getMessage());
        }
    }

    public static void updateExpense(Expense e) {
        String sql = "UPDATE expenses SET " +
                "note = '" + e.getNote() + "', " +
                "amount = " + e.getAmount() + ", " +
                "category = '" + e.getCategory().toString() + "', " +
                "date = '" + e.getDate().toString() + "' " +
                "WHERE id = '" + e.getId() + "';";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {

            int affectedRows = stmt.executeUpdate(sql);

            if (affectedRows > 0) {
                System.out.println("Expense updated: " + e.getNote());
            } else {
                System.out.println("No expense found with id: " + e.getId());
            }

        } catch (SQLException ex) {
            System.out.println("Error updating expense: " + ex.getMessage());
        }
    }
}
