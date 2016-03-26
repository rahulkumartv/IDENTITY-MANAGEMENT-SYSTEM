package fr.tbr.iamcore.utils;

import java.math.BigInteger;
import java.security.SecureRandom;

public final class Utils {
	private static SecureRandom random = new SecureRandom();
	public static String generateGUID()
	{
		return new BigInteger(30, random).toString(32);
	}

}
