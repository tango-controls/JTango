package fr.soleil.TangoArchiving.ArchivingApi.DbConnection;

import fr.soleil.TangoArchiving.ArchivingApi.ConfigConst;

public class TdbDataBaseConnection extends DataBaseConnection {

	public TdbDataBaseConnection(String muser, String mpassword) 
	{
		super(ConfigConst.TDB_CLASS_DEVICE, muser, mpassword);
		// TODO Auto-generated constructor stub
	}

	protected String getDefaultDbHost() {
		// TODO Auto-generated method stub
		return ConfigConst.TDB_HOST;
	}

	protected String getDefaultDbName() {
		// TODO Auto-generated method stub
		return ConfigConst.TDB_SCHEMA_NAME;
	}

	protected String getDefaultDbPasswd() {
		// TODO Auto-generated method stub
		return ConfigConst.TDB_BROWSER_PASSWORD;
	}

	protected String getDefaultDbSchema() {
		// TODO Auto-generated method stub
		return ConfigConst.TDB_SCHEMA_NAME;
	}

	protected String getDefaultDbUser() {
		// TODO Auto-generated method stub
		return ConfigConst.TDB_BROWSER_USER;
	}

}
