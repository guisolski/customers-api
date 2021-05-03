package api;
import java.sql.*;

public class Persistence {
    protected static String url, uname, password;
    protected static Connection con;
    protected static PreparedStatement preparedStatement;
    protected static ResultSet result;

    public Persistence() {
        url = "jdbc:mysql://localhost:3306/customers";
        uname = "root";
        password = "root";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Persistence(String url, String uname, String password) {
        Persistence.url = url;
        Persistence.uname = uname;
        Persistence.password = password;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected final void close() throws SQLException {
        if(this.con != null)
            this.con.close();
        if(this.preparedStatement != null)
            this.preparedStatement.close();
        if(this.result != null)
            this.result.close();
    }

    protected Connection connected() throws SQLException {
        return DriverManager.getConnection(this.url, this.uname, this.password);
    }

    protected void preparementGet(String query) {
        try {
            this.con = this.connected();
            this.preparedStatement = this.con.prepareStatement(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    protected void preparementUpdate(String query) throws SQLException {
        this.con = this.connected();
        this.preparedStatement = this.con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
    }

    protected void executeUpdate() {
        try {
            this.preparedStatement.executeUpdate();
            this.result = this.preparedStatement.getGeneratedKeys();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    protected void get() throws SQLException {
        this.result = this.preparedStatement.executeQuery();
    }
}
