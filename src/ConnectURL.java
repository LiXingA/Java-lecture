import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectURL {
	public static void main(String[] args) {
		try {
			DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// Create a variable for the connection string.
		String connectionUrl = "jdbc:sqlserver://192.168.0.116\\MSSQLSERVER:1433;databaseName=master;user=sa;password=liznlixing";

		try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
			String SQL = "SELECT TOP 10 * FROM dbo.MSreplication_options";
			ResultSet rs = stmt.executeQuery(SQL);

			// Iterate through the data in the result set and display it.
			while (rs.next()) {
				System.out.println(rs.getString("optname") + " " + rs.getString("value"));
			}
		}
		// Handle any errors that may have occurred.
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
}