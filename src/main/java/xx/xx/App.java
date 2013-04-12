package xx.xx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
    	Connection conn = null;

        try
        {
            String userName = "hawk";
            String password = "hawk";
            String url = "jdbc:mysql://192.168.8.44/hawk";
            Class.forName ("com.mysql.jdbc.Driver").newInstance ();
            conn = DriverManager.getConnection (url, userName, password);
            System.out.println ("Database connection established");
            
            PreparedStatement st = conn.prepareStatement("insert into mm (name) values (?)");
            for (int i = 0; i < 1000; i++) {
				st.setString(1, "a" + i);
				st.executeUpdate();
				Thread.sleep(1000);
			}
            
        }
        catch (Exception e)
        {
            System.err.println ("Cannot connect to database server");
        }
        finally
        {
            if (conn != null)
            {
                try
                {
                    conn.close ();
                    System.out.println ("Database connection terminated");
                }
                catch (Exception e) { /* ignore close errors */ }
            }
        }
    }
}
