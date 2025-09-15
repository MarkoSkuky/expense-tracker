import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

public class DatabaseManager {

    private static final String URL = "jdbc:sqlite:transactions.db";

    // pripojenie k databáze
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    // vytvorenie tabuľky
    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS transactions (" +
                "id TEXT PRIMARY KEY," +
                "type TEXT," +
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
    public static void insertTransaction(Transaction e) {
        String sql = "INSERT INTO transactions(id, type, note, amount, category, date) VALUES(" +
                "'" + e.getId() + "', " +
                "'" + e.getType().toString() + "', " +
                "'" + e.getNote() + "', " +
                e.getAmount() + ", " +
                "'" + e.getCategory().toString() + "', " +
                "'" + e.getDate().toString() + "'" +
                ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(sql);
            System.out.println("Transaction inserted: " + e.getNote());

        } catch (SQLException ex) {
            System.out.println("Error inserting Transaction: " + ex.getMessage());
        }
    }

    // načítanie všetkých výdavkov
    public static ArrayList<Transaction> getAllTransactionsFromDB() {
        ArrayList<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String id = rs.getString("id");
                String note = rs.getString("note");
                TransactionType type = TransactionType.valueOf(rs.getString("type"));
                double amount = rs.getDouble("amount");
                Category category = Category.valueOf(rs.getString("category"));
                LocalDate date = LocalDate.parse(rs.getString("date"));

                Transaction e = new Transaction(type, note, amount, category, date);
                e.setId(id);
                transactions.add(e);
            }

        } catch (SQLException ex) {
            System.out.println("Error reading transactions: " + ex.getMessage());
        }

        return transactions;
    }

    public static void removeFromDB(Transaction e) {
        String sql = "DELETE FROM transactions WHERE id='" + e.getId() + "';";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {

            int affectedRows = stmt.executeUpdate(sql); //toto je len na kontrolovanie ci naozaj sa odstranilo nieco z DB

            if (affectedRows > 0) {
                System.out.println("transaction deleted: " + e.getNote());
            } else {
                System.out.println("No transaction found with id: " + e.getId());
            }

        } catch (SQLException ex) {
            System.out.println("Error deleting transaction: " + ex.getMessage());
        }
    }

    public static void updateTransaction(Transaction e) {
        String sql = "UPDATE transactions SET " +
                "note = '" + e.getNote() + "', " +
                "amount = " + e.getAmount() + ", " +
                "category = '" + e.getCategory().toString() + "', " +
                "date = '" + e.getDate().toString() + "' " +
                "WHERE id = '" + e.getId() + "';";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {

            int affectedRows = stmt.executeUpdate(sql);

            if (affectedRows > 0) {
                System.out.println("Transaction updated: " + e.getNote());
            } else {
                System.out.println("No transaction found with id: " + e.getId());
            }

        } catch (SQLException ex) {
            System.out.println("Error updating transaction: " + ex.getMessage());
        }
    }
}
