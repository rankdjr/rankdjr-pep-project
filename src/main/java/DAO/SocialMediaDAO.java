package DAO;

import Model.Account;
import Model.Message;
import Util.ConnectionUtil;
import java.sql.*;

public class SocialMediaDAO {

    public Account getAccountByUsername(String username){
        Connection connection = ConnectionUtil.getConnection();
        try {
            
            String sql = "SELECT * FROM account WHERE username = ?;";
            
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, username);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account acc = new Account(rs.getInt("account_id"), rs.getString("username"),
                        rs.getString("password"));
                return acc;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account createAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        
        try {            
            String sql = "insert into account (username, password) values (?, ?);" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_account_id = (int) pkeyResultSet.getLong(1);
                return new Account(generated_account_id, account.getUsername(), account.getPassword());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return null;
    }
}
