package ccs.framework.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalUtility {

	public static BigDecimal multiply(BigDecimal o1 , BigDecimal o2){
		if(o1 == null || o2 == null) return null;
		return o1.multiply(o2);
	}
	
	
	public static BigDecimal add(BigDecimal o1 , BigDecimal o2){
		if(o1 == null && o2 == null) return null;
		if(o1 == null) return clone(o2);
		if(o2 == null) return clone(o1);
		return o1.add(o2);
	}
	
	public static BigDecimal clone(BigDecimal o1){
		if(o1 == null) return null;
		return o1.add(BigDecimal.ZERO);
	}
	
	public static BigDecimal divide(BigDecimal o1, BigDecimal o2){
		if(o1 == null || o2 == null) return null;
		return o1.divide(o2, 10,RoundingMode.HALF_UP);
	}
	
	
	
	public static BigDecimal applyScale(BigDecimal o, String RUPDN_CMETH) {
		if(o == null || o == BigDecimal.ZERO) return o;
		
		if(StringUtility.isNullOrBlank(RUPDN_CMETH) || RUPDN_CMETH.length() != 3) return o;
		
		char[] modes = RUPDN_CMETH.toCharArray();
		//FN_GET_SEL_ROUND
		int scale = 0;
		
		String a =  StringUtility.toString(modes[0]);
		int roundMode = Integer.parseInt(StringUtility.toString(modes[1]));
		int size =   Integer.parseInt(StringUtility.toString(modes[2]));
		int SCALE_ROUND_MODE = 0;
		
		if(roundMode == 1){	//절상
			SCALE_ROUND_MODE = BigDecimal.ROUND_UP;
		} else if(roundMode == 2){	//절하
			SCALE_ROUND_MODE = BigDecimal.ROUND_DOWN;
		} else{	//반올림.
			SCALE_ROUND_MODE = BigDecimal.ROUND_HALF_UP;
		}
		
		if(StringUtility.isEqual(a, "I")){
			//
			return o.setScale(-1*size, SCALE_ROUND_MODE);
		} else{
			//소수자리
			return o.setScale(size, SCALE_ROUND_MODE);
		}
	}
}
