package fr.tbr.iam.iamweb.services.utils;

import java.util.Map;

public final class Utils {
	public static String extractParameterValue(Map<String, String[]> parameterMap, String parameterName) {
		String parameterValue = null;
		String[] values = parameterMap.get(parameterName);
		if (values != null){
			parameterValue = values[0];
		}
		return parameterValue;
	}

}
