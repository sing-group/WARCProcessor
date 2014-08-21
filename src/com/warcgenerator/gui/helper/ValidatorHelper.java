package com.warcgenerator.gui.helper;

import org.apache.commons.validator.routines.IntegerValidator;

public class ValidatorHelper {
	public static boolean isNaturalNumberAndZero(String textToValidate) {
		IntegerValidator v = IntegerValidator.getInstance();
		
		if (!v.isValid(textToValidate) || 
				!v.isInRange(Integer.parseInt(textToValidate), 0, Integer.MAX_VALUE))
			return false;
		return true;
	}
	
	public static boolean isNaturalNumber(String textToValidate) {
		IntegerValidator v = IntegerValidator.getInstance();
		
		if (!v.isValid(textToValidate) || 
				!v.isInRange(Integer.parseInt(textToValidate), 1, Integer.MAX_VALUE))
			return false;
		return true;
	}
	
	public static boolean isPercentage(String textToValidate) {
		IntegerValidator v = IntegerValidator.getInstance();
		
		if (!v.isValid(textToValidate) || 
				!v.isInRange(Integer.parseInt(textToValidate), 0, 100))
			return false;
		return true;
	}
}
