package com.dailyservice.whatsappbot.model.v1.inputs;

import java.lang.annotation.ElementType;
import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.HibernateValidatorConfiguration;
import org.hibernate.validator.cfg.ConstraintMapping;
import org.hibernate.validator.cfg.defs.MaxDef;
import org.hibernate.validator.cfg.defs.MinDef;
import org.hibernate.validator.cfg.defs.NotNullDef;

import com.dailyservice.whatsappbot.config.LocaleConfig;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class NumberInput {

	
	
	private LocaleConfig localeConfig;
	
	Integer input;
	
	
	public String validate(Locale locale, int min, int max) {
		
		HibernateValidatorConfiguration configuration = Validation.byProvider(
	            HibernateValidator.class).configure();
		ConstraintMapping mapping = configuration.createConstraintMapping();
		
		mapping.type( NumberInput.class )
		    .property( "input", ElementType.FIELD)
		    .constraint(new NotNullDef().message("validation.whatsapp.input.invalid_input"))
		    .constraint(new MinDef().value(min).message("validation.whatsapp.input.invalid_input"))
		    .constraint(new MaxDef().value(max).message("validation.whatsapp.input.invalid_input"));
		    
		    configuration.addMapping( mapping );
		    ValidatorFactory factory = configuration.buildValidatorFactory();
		    
		    Validator validator = factory.getValidator();
		    String error = null;
		     Set<ConstraintViolation<NumberInput>> violations = validator.validate(this);
		     for (ConstraintViolation<NumberInput> violation : violations) {
			   error = localeConfig.get(locale, violation.getMessage());
		     }
		return error;
	}
}
