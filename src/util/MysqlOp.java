package util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MysqlOp {
    public static String url = "jdbc:mysql://rm-uf65wbvomnp2mufa6o.mysql.rds.aliyuncs.com:3306/playebean";//characterEncoding=GBK
    public static String username = "root";
    public static String password = "Zzc7382788";
    public static Connection con;
    public static Statement stmt;
    public static ResultSet rs;
    
    public static void main(String[] args) throws SQLException {
        connect();
//        operation();
        test();
        stmt.close();
        con.close();
    }
    public static void test() {
        String sql_select = "select * from stock ";//where id=1";
//        String sql_insert = "insert into tablename (col1,col2..) values('1','2'...)";
//        String sql_update = "update tablename set colname='update' where id=1";
        //insert(sql_insert);
          select(sql_select);
        //update(sql_update);
    }
    public static void connect() {
        // ��λ����
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("���������ɹ�!"); 
        } catch (ClassNotFoundException e) {
            System.out.println("��������ʧ��!");
            e.printStackTrace();
        }
        // ��������
        try {
            con = DriverManager.getConnection(url, username, password);
            stmt = con.createStatement();
            System.out.println("���ݿ����ӳɹ�!"); 
        } catch(SQLException e) {
            System.out.println("���ݿ�����ʧ��!"); 
        }
    }
    public static void select(String sql) {
        try {
            rs = stmt.executeQuery(sql);
            ResultSetMetaData meta_data = rs.getMetaData();//����
            for (int i_col = 1; i_col <= meta_data.getColumnCount(); i_col++) {
                System.out.print(meta_data.getColumnLabel(i_col) + "   ");
            }
            System.out.println();
            List codes=new ArrayList();
            List names=new ArrayList();		
            while (rs.next()) {
               // for (int i_col = 1; i_col <= meta_data.getColumnCount(); i_col++) {
                   // System.out.print(rs.getString(i_col) + "  ");
                String name=	rs.getString(3);
                String code =	rs.getString(2);
                codes.add(code);
                names.add(name);
            
            }
            rs.close();
            
            for (int i=0; i<codes.size();i++) {
            
            	 String code =	(String)codes.get(i);
            	 String name=(String)names.get(i);
                String simple_name = Pinyin.getPinYinHeadChar(name);
                
                System.out.print(code);
                System.out.print(name);
                System.out.print(simple_name);
                
                String sql_update = "update stock set simple_name='"+simple_name+"' where code="+ code;
                update(sql_update);
                
               // }
                System.out.println("--->"+i+"--");
            }
            
        }catch (Exception e) {
            System.out.println("���ݲ�ѯʧ��!");
        }
    }
    public static void insert(String sql) {
        try {
            stmt.clearBatch();
            stmt.addBatch(sql);
            stmt.executeBatch();
            System.out.println("���ݲ���ɹ�!");
        }catch (Exception e) {
            System.out.println("���ݲ���ʧ��!");
        }
        
    }
    public static void update(String sql) {
        try {
            stmt.executeUpdate(sql);
            System.out.println("���ݸ��³ɹ�!");
        }catch (Exception e) {
            System.out.println("���ݸ���ʧ��!");
        }
    }
}