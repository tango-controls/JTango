package fr.soleil.util;


import fr.soleil.util.log.SoleilAppender;
import fr.soleil.util.log.SoleilLogger;

/**
 * The logger for utilities
 * @author BARBA-ROSSA
 *
 */
public class UtilLogger
{
	public static SoleilLogger logger = new  SoleilLogger("UTIL", SoleilAppender.s_strFATAL);
}