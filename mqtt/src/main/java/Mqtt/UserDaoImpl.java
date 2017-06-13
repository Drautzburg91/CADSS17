package Mqtt;

import java.sql.*;

/**
 * Created by Kim de Souza on 13.06.2017.
 */
public class UserDaoImpl implements UserDao {

    //Variables&References
    Connection writerConnection;
    Connection readerConnection;

    User user;


    //[GENERAL]
    //--Constructor
    public UserDaoImpl(Connection writerConnection, Connection readerConnection)
    {
        this.writerConnection = writerConnection;
        this.readerConnection = readerConnection;
        this.user = new User();
    }

    public UserDaoImpl(){
        this.writerConnection = connectDefaultWriter();
        this.readerConnection = connectDefaultReader();
        this.user = new User();
    }


    //Default Connection
    //Writer Connection
    private java.sql.Connection connectDefaultWriter()
    {
        java.sql.Connection connection = null;

        try {

            //connection =  DriverManager.getConnection(default_writer);
            connection	 =	DriverManager.getConnection("jdbc:mysql://" +
                    "database4cad.cc1ormgk3ins.us-east-2.rds.amazonaws.com" + ":" + "3306" + "/" +
                    "WeatherSystemDatabase", "CAD_MASTER_JDBC", "HTWGhtwg");
        }
        catch (SQLException e)
        {
            System.out.println("Connection Failed!:\n" + e.getMessage());
        }

        if (connection != null)
        {
            System.out.println("SUCCESS! WRITER is available");
        }
        else
        {
            System.out.println("FAILURE! WRITER couldn't be established");
        }

        return connection;
    }

    //Reader Connection
    private java.sql.Connection connectDefaultReader()
    {
        java.sql.Connection connection = null;

        try {
            //connection =  DriverManager.getConnection(default_reader);
            connection 	 =	DriverManager.getConnection("jdbc:mysql://" + "database4cad-us-east-2b.cc1ormgk3ins.us-east-2.rds.amazonaws.com" + ":" + "3306" + "/" + "WeatherSystemDatabase", "CAD_MASTER_JDBC", "HTWGhtwg");
        }
        catch (SQLException e)
        {
            System.out.println("Connection Failed!:\n" + e.getMessage());
        }

        if (connection != null)
        {
            System.out.println("SUCCESS! Reader is available");
        }
        else
        {
            System.out.println("FAILURE! Reader couldn't be established");
        }

        return connection;

    }

    //Check Connection
    public String checkDatabaseConnection()
    {
        boolean isConnected = false;

        try {
            isConnected = readerConnection.isValid(5);

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

    //################################################################################################################
    //[RabbitMQ]
    //################################################################################################################
    //Insert MYSQL Operations

    //SystemUser
    public String insertSystemUser(String userName, String password, String additionalDescription)
    {
        //Check Incoming Data
        if(userName == null)
        {
            return ("Attribute userName - primary Key - expected value");
        }

        if(password == null || password.length() > 10)
        {
            return ("Attribute passwort - not null - max length: 10");
        }


        //MYSQL Query
        Statement statement;
        String query;
        int resultSet;
        try
        {
            statement = (Statement) writerConnection.createStatement();

            query = "INSERT INTO " +
                    "SystemUser "+																	//Table Name
                    "(userName, passwort , description)"+
                    "VALUES"+
                    "('"+userName+"','"+password+"','"+additionalDescription+"');";				//Values for Insert


            resultSet  = statement.executeUpdate(query);


        }
        catch (SQLException e)
        {
            //System.out.println("Insert Operation failed:\n" + e.getMessage());						//Internal
            return("Insert Operation failed:\n" + e.getMessage());
        }
        return "Successfull Insert Operation - Table User - Rowamount: " + resultSet;
    }

    public String insertSystemUser(String userName, String password){

        //Check Incoming Data
        if(userName == null)
        {
            return ("Attribute userName - primary Key - expected value");
        }

        if(password == null || password.length() > 10)
        {
            return ("Attribute passwort - not null - max length: 10");
        }


        //MYSQL Query
        Statement statement;
        String query;
        int resultSet;
        try
        {
            statement = (Statement) writerConnection.createStatement();

            query = "INSERT INTO " +
                    "SystemUser "+																	//Table Name
                    "(userName, passwort , description)"+
                    "VALUES"+
                    "('"+userName+"','"+password+"','null');";				//Values for Insert

            resultSet  = statement.executeUpdate(query);
        }
        catch (SQLException e)
        {
            return("Insert Operation failed:\n" + e.getMessage());
        }
        return "Successfull Insert Operation - Table User - Rowamount: " + resultSet;
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
            statement = (Statement) writerConnection.createStatement();

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




        return "Successfull Insert Operation - Table User - Rowamount: " + resultSet;
    }


    //Assigned
    public String insertAssigned(String systemUser_userName, String vHost_name, String additionalInformation)
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
            statement = (Statement) writerConnection.createStatement();

            query = "INSERT INTO " +
                    "Assigned "+																	//Table Name
                    "(userName, vHost_name, information)"+
                    "VALUES"+
                    "('"+systemUser_userName+"','"+vHost_name+"','"+additionalInformation+"');";	//Values for Insert


            resultSet  = statement.executeUpdate(query);


        } catch (SQLException e)
        {
            //System.out.println("Insert Operation failed:\n" + e.getMessage());						//Internal
            return("Insert Operation failed:\n" + e.getMessage());
        }




        return "Successfull Insert Operation - Table User - Rowamount: " + resultSet;
    }

    @Override
    public String insertAssigned(String systemUserUserName, String vHostName) {
        return null;
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
            statement = (Statement) readerConnection.createStatement();

            query = "SELECT * " +
                    "FROM VHost;";																		//Table Name


            temp_resultSet  = statement.executeQuery(query);


        } catch (SQLException e)
        {
            System.out.println("Insert Operation failed:\n" + e.getMessage());							//Internal
            //return("Insert Operation failed:\n" + e.getMessage());
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
            statement = (Statement) readerConnection.createStatement();

            query = "SELECT * " +
                    "FROM SystemUser;";																	//Table Name


            temp_resultSet  = statement.executeQuery(query);


        } catch (SQLException e)
        {
            System.out.println("Insert Operation failed:\n" + e.getMessage());							//Internal

        }

        return temp_resultSet;
    }


    //SELECT  SystemUser Passwort
    public ResultSet selectSystemUserPwByUserName(String userName)
    {

        //Variables
        ResultSet temp_resultSet	= null;

        //Check incoming data
        if(userName == null)
        {return null;}

        //MYSQL Query
        Statement statement;
        String query;

        try
        {
            statement = (Statement) readerConnection.createStatement();

            query = "SELECT passwort " +
                    "FROM SystemUser " +
                    "WHERE	userName = '"+userName+"';";


            temp_resultSet  = statement.executeQuery(query);


        } catch (SQLException e)
        {
            System.out.println("Insert Operation failed:\n" + e.getMessage());							//Internal

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
            statement = (Statement) readerConnection.createStatement();

            query = "SELECT * " +
                    "FROM Assigned;" ;



            temp_resultSet  = statement.executeQuery(query);


        } catch (SQLException e)
        {
            System.out.println("Insert Operation failed:\n" + e.getMessage());							//Internal

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
            statement = (Statement) readerConnection.createStatement();

            query = "SELECT userName " +
                    "FROM Assigned "+
                    "WHERE vHost_name = '" +vHost_name+ "';";



            temp_resultSet  = statement.executeQuery(query);


        } catch (SQLException e)
        {
            System.out.println("Insert Operation failed:\n" + e.getMessage());							//Internal

        }

        return temp_resultSet;
    }


}
