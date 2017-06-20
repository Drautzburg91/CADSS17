package Mqtt.Repository;

import java.sql.*;

import com.mysql.jdbc.Statement;
import org.springframework.stereotype.Repository;

/**
 * Created by Kim De Souza on 22.05.2017.
 */

@Repository("userRepository")
public class UserRepositoryImpl implements UserRepository
{


    Connection defaultConnection = null;

    public UserRepositoryImpl(Connection databaseConnection)
    {
        this.defaultConnection = databaseConnection;
    }

    public UserRepositoryImpl(){
        this.defaultConnection = connectDefaultConnection();
    }

    //Default Connection
    private Connection connectDefaultConnection()
    {
        Connection connection = null;

        try {
            connection 	 =	DriverManager.getConnection(
                    "jdbc:mysql://"
                            + System.getenv("CAD_DB_HOST") + ":"
                            + "3306" + "/"
                            + "WeatherSystemDatabase",
                    System.getenv("CAD_DB_USER"),
                    System.getenv("CAD_DB_PASSWORD"));
        }
        catch (SQLException e)
        {
            System.out.println("Connection Failed!:\n" + e.getMessage());
        }

        if (connection != null)
        {
            System.out.println("SUCCESS! Connection available");
        }
        else
        {
            System.out.println("FAILURE! Connection couldn't be established");
        }

        return connection;

    }

    //Check Connection
    public String checkDatabaseConnection()
    {
        boolean isConnected = false;

        try {
            isConnected = defaultConnection.isValid(5);

        } catch (SQLException | NullPointerException e)
        {
            return ("Connection failed: " + e.getMessage());
        }

        if(isConnected)
        {
            return "Connection works";
        }

        return "Connection failed";

    }

    //SystemUser
    public String insertSystemUser(String userName, String passwort, String additionalDescription)
    {
        //Check Incoming Data
        if(userName == null)
        {
            return ("Attribute userName - primary Key - expected value");
        }

        if(passwort == null)
        {
            return ("Attribute passwort - not null - max length: 10");
        }


        //MYSQL Query
        Statement statement;
        String query;
        int resultSet;
        try
        {
            statement = (Statement) defaultConnection.createStatement();

            query = "INSERT INTO " +
                    "SystemUser "+																	//Table Name
                    "(userName, passwort , description)"+
                    "VALUES"+
                    "('"+userName+"','"+passwort+"','"+additionalDescription+"');";				//Values for Insert


            resultSet  = statement.executeUpdate(query);


        } catch (SQLException e)
        {
            //System.out.println("Insert Operation failed:\n" + e.getMessage());						//Internal
            return("Insert Operation failed:\n" + e.getMessage());
        }




        return "Successfull Insert Operation - Table SystemUser - Rowamount: " + resultSet;
    }


    //VHost
    public String insertVHost(String vHostName, String additionalDescription)
    {
        //Check Incoming Data
        if(vHostName == null)
        {
            return ("Attribute userName - primary Key - expected value");
        }

        //MYSQL Query
        Statement statement;
        String query;
        int resultSet;
        try
        {
            statement = (Statement) defaultConnection.createStatement();

            query = "INSERT INTO " +
                    "VHost "+																	//Table Name
                    "(vHost_name, description)"+
                    "VALUES"+
                    "('"+vHostName+"','"+additionalDescription+"');";				//Values for Insert


            resultSet  = statement.executeUpdate(query);


        } catch (SQLException e)
        {
            //System.out.println("Insert Operation failed:\n" + e.getMessage());						//Internal
            return("Insert Operation failed:\n" + e.getMessage());
        }

        return "Successfull Insert Operation - Table VHost - Rowamount: " + resultSet;
    }


    //Assigned
    public String insertAssigned(String systemUser_userName, String vHost_name, String additionalInformation, boolean readRights, boolean writeRights, boolean configureRights)
    {
        //Check Incoming Data
        if(systemUser_userName == null)
        {
            return ("Attribute System UserName - primary Key - expected value");
        }

        if(vHost_name == null)
        {
            return ("Attribute vHost Name - primary Key - expected value");
        }

        //MYSQL Query
        Statement statement;
        String query;
        int resultSet;
        try
        {
            statement = (Statement) defaultConnection.createStatement();

            query = "INSERT INTO " +
                    "Assigned "+																	//Table Name
                    "(userName, vHost_name, information, readRights, writeRights, configureRights)"+
                    "VALUES"+
                    "('"+systemUser_userName+"','"+vHost_name+"','"+additionalInformation+"','"+readRights+"','"+writeRights+"','"+configureRights+"');";	//Values for Insert


            resultSet  = statement.executeUpdate(query);


        } catch (SQLException e)
        {
            //System.out.println("Insert Operation failed:\n" + e.getMessage());						//Internal
            return("Insert Operation failed:\n" + e.getMessage());
        }




        return "Successfull Insert Operation - Table Assigned - Rowamount: " + resultSet;
    }






    //SELECT MYSQL Operations
    //SELECT vHost - all
    public ResultSet selectVHostAll()
    {

        //Variables
        ResultSet temp_resultSet	= null;

        //MYSQL Query
        Statement statement;
        String query;

        try
        {
            statement = (Statement) defaultConnection.createStatement();

            query = "SELECT * " +
                    "FROM VHost;";																		//Table Name


            temp_resultSet  = statement.executeQuery(query);


        } catch (SQLException e)
        {
            System.out.println("SELECT Operation failed:\n" + e.getMessage());							//Internal
        }

        return temp_resultSet;
    }


    //SELECT System User - all
    public ResultSet selectSystemUserAll()
    {

        //Variables
        ResultSet temp_resultSet	= null;

        //MYSQL Query
        Statement statement;
        String query;

        try
        {
            statement = (Statement) defaultConnection.createStatement();

            query = "SELECT * " +
                    "FROM SystemUser;";																	//Table Name


            temp_resultSet  = statement.executeQuery(query);


        } catch (SQLException e)
        {
            System.out.println("SELECT Operation failed:\n" + e.getMessage());							//Internal

        }

        return temp_resultSet;
    }


    //SELECT  SystemUser Passwort
    public ResultSet selectSystemUserByUserName(String userName)
    {

        //Variables
        ResultSet temp_resultSet	= null;

        //Check incoming data
        if(userName == null)
        {
            return null;
        }

        //MYSQL Query
        Statement statement;
        String query;

        try
        {
            statement = (Statement) defaultConnection.createStatement();

            query = "SELECT * " +
                    "FROM SystemUser " +
                    "WHERE	userName = '"+userName+"';";


            temp_resultSet  = statement.executeQuery(query);


        } catch (SQLException e)
        {
            System.out.println("SELECT Operation failed:\n" + e.getMessage());							//Internal

        }

        return temp_resultSet;
    }


    //SELECT  Assigned - all
    public ResultSet selectAssignedAll()
    {

        //Variables
        ResultSet temp_resultSet	= null;

        //MYSQL Query
        Statement statement;
        String query;

        try
        {
            statement = (Statement) defaultConnection.createStatement();

            query = "SELECT * " +
                    "FROM Assigned;" ;



            temp_resultSet  = statement.executeQuery(query);


        } catch (SQLException e)
        {
            System.out.println("SELECT Operation failed:\n" + e.getMessage());							//Internal

        }

        return temp_resultSet;
    }


    //SELECT  Assigned - all
    public ResultSet selectAssignedUserByVHost(String vHost_name)
    {

        //Variables
        ResultSet temp_resultSet	= null;

        //Check Incoming Data
        if(vHost_name == null)
        {
            return null;
        }

        //MYSQL Query
        Statement statement;
        String query;

        try
        {
            statement = (Statement) defaultConnection.createStatement();

            query = "SELECT userName " +
                    "FROM Assigned "+
                    "WHERE vHost_name = '" +vHost_name+ "';";



            temp_resultSet  = statement.executeQuery(query);


        } catch (SQLException e)
        {
            System.out.println("SELECT Operation failed:\n" + e.getMessage());							//Internal

        }

        return temp_resultSet;
    }




}