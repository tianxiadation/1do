package com.luqi.util.validation;

/**
 * @author coco
 * @date 2020年9月17日 上午11:35:49
 * 
 */
public class ValidatorUtil {
	/**
	 * 对象校验，默认group
	 * 
	 * @param t 待校验对象
	 */
	/*
	public static <T> ValidateResult validatorObj(T t){
	return validatorObj(t, Default.class);
	}
	
	*//**
		 * 对象校验
		 * 
		 * @param t      待校验对象
		 * @param groups 组
		 * @return
		 *//*
			public static <T> ValidateResult validatorObj(T t, Class<?>...groups){
			ValidateResult result = new ValidateResult();
			Set<ConstraintViolation<T>> constraintViolations = Validation.buildDefaultValidatorFactory().getValidator().validate(t, groups);
			Iterator<ConstraintViolation<T>> iter = constraintViolations.iterator();
			StringBuffer errorMsg = new StringBuffer();
			String op = "";
			while (iter.hasNext()) {
			   result.setValid(false);
			      String message = iter.next().getMessage();  
			      errorMsg.append(op).append(message);
			      op = ",";
			  }
			  result.setErrorInfo(errorMsg.toString());
			return result;
			}*/
}