package fr.esrf.TangoApi;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoDs.Except;

public class IORDumpUtil {
	// ===============================================================
	// ===============================================================
	public static void printSyntax() {
		System.out.println("IORdump <ior string>      or");
		System.out.println("IORdump -f <ior file name>");
	}

	// ===============================================================
	// ===============================================================
	public static String getIor(String filename) throws FileNotFoundException, SecurityException, IOException {
		FileInputStream fid = new FileInputStream(filename);
		int nb = fid.available();
		byte[] inStr = new byte[nb];
		fid.read(inStr);
		String str = new String(inStr);
		fid.close();
		System.out.println(str);
		return str.trim();
	}

	// ===============================================================
	// ===============================================================
	public static void main(String[] args) {
		try {
			switch (args.length) {
			case 1:
				System.out.println(new fr.esrf.TangoApi.IORdump(args[0]).toString());
				break;
			case 2:
				if (args[0].equals("-f")) {
					System.out.println(new fr.esrf.TangoApi.IORdump(null, getIor(args[1])).toString());
					break;
				}
			default:
				printSyntax();
			}
		} catch (DevFailed e) {
			Except.print_exception(e);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Device name ?");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
