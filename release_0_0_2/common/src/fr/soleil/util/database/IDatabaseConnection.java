package fr.soleil.util.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Database Connection interface to access database
 * @author BARBA-ROSSA
 *
 */
public interface IDatabaseConnection
{

	/*****************************************************************************
	 * This method allows the connection to Database
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws SQLException
	 **************************************************************************/
	public void connect() throws InstantiationException, IllegalAccessException, SQLException;

	/**
	 * We take a Database connection and a statement
	 * @param strHost
	 * @param strUser
	 * @param strPasswd
	 * @param strNameBDD
	 * @throws SQLException
	 * @return boolean
	 */
	public boolean connect(String strHost, String strUser, String strPasswd,
			String strNameBDD) throws InstantiationException, IllegalAccessException, SQLException;

	/**
	 * We close the statement from database.
	 *
	 */
	public void closeStatement();
	
	/**
	 * We release the connection from database.
	 *
	 */
	public void disconnect();

	/************************************************************************
	 * This method allows to execute a SQL query
	 * @param query String : the SQL query to execute
	 * @return ResultSet : the result of the query
	 * @throws SQLException  
	 ************************************************************************/
	public ResultSet executeQuery(String query) throws SQLException;

	/****************************************************************************
	 * This method allows to get the column's name of a resultset
	 * @param rsResult ResultSet : result of the query
	 * @return List : the list of the columns
	 * @throws SQLException
	 **************************************************************************/
	public List getColumnsName(ResultSet rsResult) throws SQLException;
	
	
	/**
	 * Return a SQL Connection
	 * @return Connection
	 */
	public Connection getConn();
}