

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.impl.GenericObjectPool;

public class ConnectionPool {
//    public static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    public static final String URL = "jdbc:mysql://localhost:3306/test"; 
    public static final String USERNAME = "root";
    public static final String PASSWORD = "root";
 
    private GenericObjectPool connectionPool = null;
   
 
    public DataSource setUp() throws Exception {
        //
        // Load JDBC Driver class.
        //
//        Class.forName(ConnectionPool.DRIVER).newInstance();
 
        //
        // Creates an instance of GenericObjectPool that holds our
        // pool of connections object.
        //
        
        DataSource ds = null;
//        ds.
        connectionPool = new GenericObjectPool();
        connectionPool.setMaxActive(10);
//        connectionPool.set
//        connectionPool.setMaxActive(maxActive)
//        connectionPool.setMaxIdle(maxIdle)
//        connectionPool.setMinIdle(minIdle);
        
        
 
        //
        // Creates a connection factory object which will be use by
        // the pool to create the connection object. We passes the
        // JDBC url info, username and password.
        //
        ConnectionFactory cf = new DriverManagerConnectionFactory(
                ConnectionPool.URL,
                ConnectionPool.USERNAME,
                ConnectionPool.PASSWORD);
        
        //
        // Creates a PoolableConnectionFactory that will wraps the
        // connection object created by the ConnectionFactory to add
        // object pooling functionality.
        //
        PoolableConnectionFactory pcf =
                new PoolableConnectionFactory(cf, connectionPool,
                        null, null, false, true);
        
        return new PoolingDataSource(connectionPool);
    }
 
    public GenericObjectPool getConnectionPool() {
        return connectionPool;
    }
 
    public static void main(String[] args) throws Exception {
        ConnectionPool demo = new ConnectionPool();
        DataSource dataSource = demo.setUp();
        demo.printStatus();
        Connection conn = null;
        Connection conn2 = null;
        PreparedStatement stmt = null;
 
        try {
            conn = dataSource.getConnection();
            conn2 = dataSource.getConnection();
            dataSource.getConnection();
            dataSource.getConnection();
            dataSource.getConnection();
            dataSource.getConnection();
            dataSource.getConnection();
            dataSource.getConnection();
            dataSource.getConnection().close();
            dataSource.getConnection().close();
            dataSource.getConnection().close();
            dataSource.getConnection().close();
            dataSource.getConnection().close();
            dataSource.getConnection().close();
            dataSource.getConnection().close();
            dataSource.getConnection().close();
            dataSource.getConnection().close();
            dataSource.getConnection().close();
            dataSource.getConnection().close();
            
           
            demo.printStatus();
 
            stmt = conn.prepareStatement("SELECT SG_UF,NO_UF from DESIPE.UNIDADES_FEDERACAO");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println("NO_UF: " + rs.getString("NO_UF"));
            }
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
 
        demo.printStatus();
    }
 
    /**
     * Prints connection pool status.
     */
    private void printStatus() {
        System.out.println("Max   : " + getConnectionPool().getMaxActive() + "; " +
            "Active: " + getConnectionPool().getNumActive() + "; " +
            "Idle  : " + getConnectionPool().getNumIdle());
    }
}