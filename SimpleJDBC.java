import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Scanner;

/**
 * @NIM 2201860355
 * @author Kevin Sandiho
 */
public class SimpleJDBC {
    static class ConnectDB{
        private static Connection sqlConnect;
        public static Connection connectDB() throws SQLException {
            try {
            String DB = "jdbc:mysql://localhost/ebookshop";
            String user = "root";
            String pass = "";
            DriverManager.registerDriver(new org.mariadb.jdbc.Driver());
            sqlConnect = (Connection) DriverManager.getConnection(DB,user,pass);
            } catch(SQLException e) {
                System.out.println("Tidak dapat terhubung ke DBMS, error:");
                System.err.println(e.getMessage());
                System.exit(0);
            }
            return sqlConnect;
        }
    }
	
    private final static void clearConsole() {
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")){
                Runtime.getRuntime().exec("cls");
            } else {
                Runtime.getRuntime().exec("clear");
            }
	}
        catch (final Exception ex){
            System.out.println("OS Not Supported!");
            System.err.println(ex.getMessage());
        }
    }
    
    public static void main(String args[]) {
        int pilihan;
        Scanner input = new Scanner(System.in);
        while(true){
            try{
                Connection conn=(Connection)ConnectDB.connectDB();
                Statement stt=conn.createStatement();
                ResultSet RsData = stt.executeQuery("SELECT * FROM books");
                System.out.println("| ID - Title - Author - Price - Qty |");
                System.out.println("-------------------------------------");
                while(RsData.next()){
                    int id = RsData.getInt("id");
                    String title = RsData.getString("title");
                    String author = RsData.getString("author");
                    float price = RsData.getFloat("price");
                    int qty = RsData.getInt("qty");
                    // Print Out Data
                    System.out.format("| %s | %s | %s | %s | %s |\n", id, title, author, price, qty);
                }
                stt.close();
                System.out.println("-------------------------------------");
            } catch(SQLException ex) {
                System.out.println("Tidak dapat terhubung ke DBMS, error message:");
                System.err.println(ex.getMessage());
            }
            
            System.out.println("\nPilih Salah satu menu dibawah ini :");
            System.out.println("1.) Penambahan Data");
            System.out.println("2.) Update Data");
            System.out.println("3.) Hapus Data");
            System.out.println("4.) Keluar");
            System.out.print("\nMasukan angka menu pilihan kamu : ");
            pilihan = input.nextInt();
            
            String title, author;
            float price;
            int id, qty;
            switch(pilihan){
                case 1:
                    System.out.println("Masukan detail data baru");
                    System.out.println("Title: ");
                    title = input.next();
                    System.out.println("Author: ");
                    author = input.next();
                    System.out.println("Price: ");
                    price = input.nextFloat();
                    System.out.println("Qty: ");
                    qty = input.nextInt();
                    
                    try{
                        Connection conn=(Connection)ConnectDB.connectDB();
                        Statement stt=conn.createStatement();
                        stt.executeQuery("INSERT INTO books(title,author,price,qty) VALUES('"+title+"','"+author+"','"+price+"','"+qty+"')");
                        System.out.println("TAMBAH DATA BERHASIL!");
                    } catch(SQLException ex) {
                        System.out.println("Tidak dapat terhubung ke DBMS, error message:");
                        System.err.println(ex.getMessage());
                    }
                    clearConsole();
                    break;
                
                case 2:
                    System.out.println("Masukan id data yang akan diubah: ");
                    id = input.nextInt();
                    System.out.println("\nMasukan detail perubahan");
                    System.out.println("Title: ");
                    title = input.next();
                    System.out.println("Author: ");
                    author = input.next();
                    System.out.println("Price: ");
                    price = input.nextFloat();
                    System.out.println("Qty: ");
                    qty = input.nextInt();
                    
                    try{
                        Connection conn=(Connection)ConnectDB.connectDB();
                        Statement stt=conn.createStatement();
                        stt.executeQuery("UPDATE books SET title='"+title+"',author='"+author+"',price='"+price+"',qty='"+qty+"' WHERE id='"+id+"'");
                        System.out.println("UPDATE BERHASIL!");
                    } catch(SQLException ex) {
                        System.out.println("Tidak dapat terhubung ke DBMS, error message:");
                        System.err.println(ex.getMessage());
                    }
                    clearConsole();
                    break;
                    
                case 3:
                    System.out.println("Masukan id data yang akan dihapus: ");
                    id = input.nextInt();
                    
                    try{
                        Connection conn=(Connection)ConnectDB.connectDB();
                        Statement stt=conn.createStatement();
                        stt.executeQuery("DELETE FROM books WHERE id='"+id+"'");
                        System.out.println("HAPUS DATA BERHASIL!");
                    } catch(SQLException ex) {
                        System.out.println("Tidak dapat terhubung ke DBMS, error message:");
                        System.err.println(ex.getMessage());
                    }
                    clearConsole();
                    break;
                    
                case 4:
                    System.out.println("Exiting Program...");
                    System.exit(0);
                
                default:
                    System.out.println("Pilihan Invalid!");
                    clearConsole();
                    break;
            }
        }
    }
}
