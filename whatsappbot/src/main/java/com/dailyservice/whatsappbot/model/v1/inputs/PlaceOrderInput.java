package com.dailyservice.whatsappbot.model.v1.inputs;

import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.dailyservice.whatsappbot.config.LocaleConfig;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaceOrderInput {

	
	private LocaleConfig localeConfig;
	
	
	@NotNull(message = "validation.whatsapp.input.invalid_input")
	@Min(value = 1, message = "validation.whatsapp.input.invalid_input")
    @Max(value = 2, message = "validation.whatsapp.input.invalid_input")
	Integer input;
	
	
	public String validate(Locale locale) {
		
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		String error = null;
		Set<ConstraintViolation<PlaceOrderInput>> violations = validator.validate(this);
		for (ConstraintViolation<PlaceOrderInput> violation : violations) {
			   error = localeConfig.get(locale, violation.getMessage());
		       System.out.println(error);
		}
		return error;
	}
}
