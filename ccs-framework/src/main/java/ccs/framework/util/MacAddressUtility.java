package ccs.framework.util;


import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.UUID;

public class MacAddressUtility {

	public static long getMacAddressAsLong(){
		
		try {
			return getMacAddressAsLong(getMacAddressAsByte());
		}
		catch(Exception ex) {
			//String uid = UUID.randomUUID().toString().replaceAll("-", "");
			return UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
			//return getMacAddressAsLong(bytes);
		}
	}
	
	public static byte[] getMacAddressAsByte(){
		byte[] mac = null;
		try {
			InetAddress address = InetAddress.getLocalHost();
			NetworkInterface network  = NetworkInterface.getByInetAddress(address);
			if (network != null) {
				mac = network.getHardwareAddress();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return mac;
	}
	
	
	
	public static String getMacAddressAsString(){
		return getMacAddressAsString(getMacAddressAsByte());
	}
	

	
	private static String getMacAddressAsString(byte[] address) {
		StringBuilder builder = new StringBuilder();
		for (byte b : address) {
			if (builder.length() > 0) {
				builder.append("-");
			}
			builder.append(String.format("%02X", b & 0xFF));
		}
		return builder.toString();
	}
	
	private static long getMacAddressAsLong(byte[] address) {
		long mac = 0;
		for (int i = 0; i < 6; i++) {
			long t = (address[i] & 0xffL) << ((5 - i) * 8);
			mac |= t;
		}
		return mac;
	}
}