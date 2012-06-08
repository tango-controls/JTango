package fr.soleil.util;


import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.ThreadMXBean;
import java.util.List;
import fr.soleil.util.log.SoleilAppender;
import fr.soleil.util.log.SoleilLogger;

/**
 * It's a performance and memory usage logger
 */
public class PerfLogger
{
	public static SoleilLogger logger = new  SoleilLogger("PERF", SoleilAppender.s_strINFO);

	/**
	 * add a memory log
	 * @param strMethodName
	 */
	public static void addMemoryLog(String strMethodName)
	{
		MemoryUsage memoryUsage = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
		logger.addInfoLog(strMethodName + " memory usage point : " + (memoryUsage.getUsed()/1024));
	}
	
	/**
	 * Get the memory usage
	 *
	 */
	public static void getMemoryUsage()
	{
		// TODO the final implementation
		try {
            PrintWriter out = new PrintWriter(System.err);

            ThreadMXBean tb = ManagementFactory.getThreadMXBean();
            out.printf("Current thread count: %d%n", tb.getThreadCount());
            out.printf("Peak thread count: %d%n", tb.getPeakThreadCount());

            List<MemoryPoolMXBean> pools = ManagementFactory.getMemoryPoolMXBeans();
            for (MemoryPoolMXBean pool : pools) {
              MemoryUsage peak = pool.getPeakUsage();
              out.printf("Peak %s memory used: %,d%n", pool.getName(), peak.getUsed());
              out.printf("Peak %s memory reserved: %,d%n", pool.getName(), peak.getCommitted());
            }
            out.close();
          } catch (Throwable t) {
            System.err.println("Exception in agent: " + t);
          }
    }	
}