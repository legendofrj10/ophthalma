package sample;
import java.sql.*;

public class test{
    public static void main(String[] args){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://"+common.getIP()+":3306/OSPITALITY","yashraj","Raj"
            );
            DatabaseMetaData metadata = con.getMetaData();
            ResultSet result = metadata.getTables(null,null,null,new String[]{"TABLE"});

            while(result.next()){
                System.out.println(result.getString("TABLE_NAME"));
                System.out.println(result.getString("REMARKS"));
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
