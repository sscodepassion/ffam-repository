package com.ffam.workdistapi.model;

import static com.openpojo.reflection.impl.PojoClassFactory.getPojoClass;
import static java.lang.Class.forName;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.openpojo.reflection.PojoClass;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.NoFieldShadowingRule;
import com.openpojo.validation.rule.impl.NoPublicFieldsRule;
import com.openpojo.validation.rule.impl.NoStaticExceptFinalRule;
import com.openpojo.validation.test.impl.GetterTester;

public class ModelTests {

	@Test
	public void testModel() throws ClassNotFoundException {
		Validator validator = ValidatorBuilder.create()
				.with(new NoPublicFieldsRule())
				.with(new NoFieldShadowingRule())
				.with(new NoStaticExceptFinalRule())
				.with(new GetterTester())
				.build();
		
		List<PojoClass> pojoClasses = new ArrayList<>();
		pojoClasses.add(getPojoClass(forName("com.ffam.workdistapi.model.Agent")));
		pojoClasses.add(getPojoClass(forName("com.ffam.workdistapi.model.Skill")));
		pojoClasses.add(getPojoClass(forName("com.ffam.workdistapi.model.Task")));
		
		validator.validate(pojoClasses);
	}
}
