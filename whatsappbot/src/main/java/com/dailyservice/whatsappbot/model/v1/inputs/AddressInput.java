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
import org.hibernate.validator.cfg.defs.NotNullDef;

import com.dailyservice.whatsappbot.config.LocaleConfig;
import com.dailyservice.whatsappbot.utils.Utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressInput {
	
	
	private double lat;
	private double lng;

	private LocaleConfig localeConfig;

	public String validate(Locale locale, Float lat, Float lng) {

		HibernateValidatorConfiguration configuration = Validation.byProvider(HibernateValidator.class).configure();
		ConstraintMapping mapping = configuration.createConstraintMapping();

		mapping.type(AddressInput.class)
		.property("lat", ElementType.FIELD)
		.property("lng", ElementType.FIELD)
		.constraint(new NotNullDef().message("validation.whatsapp.input.invalid_input"));
		

		configuration.addMapping(mapping);
		ValidatorFactory factory = configuration.buildValidatorFactory();

		Validator validator = factory.getValidator();
		String error = null;
		Set<ConstraintViolation<AddressInput>> violations = validator.validate(this);
		for (ConstraintViolation<AddressInput> violation : violations) {
			  error = localeConfig.get(locale, violation.getMessage());
		}
		
		if(!Utils.isValidLatitude(lat) && !Utils.isValidLongitude(lng)) {
			error = localeConfig.get(locale, "validation.whatsapp.input.invalid_input");
		}
		return error;
	}
	
	
}
