import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Main {

	public static void main(String[] args) {
		Connection conn = null;
		try {
			 conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "root");
			 PreparedStatement ps = conn.prepareStatement("select * from test.father where id = ?");
			 ps.setInt(1, 4);
			 
			 ResultSet rs = ps.executeQuery();
			 while(rs.next()){
				 String nome = rs.getString("name");
				 String sobrenome = rs.getString(3);
				 
				 System.out.println("nome: " + nome);
				 System.out.println("sobrenome: " + sobrenome);
			 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
	}
	
}
