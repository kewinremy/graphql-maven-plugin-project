/** Generated by the default template from graphql-java-generator */
package com.graphql_java_generator.domain.client.starwars;

import com.graphql_java_generator.client.directive.Directive;
import com.graphql_java_generator.client.directive.DirectiveLocation;
import com.graphql_java_generator.client.directive.DirectiveRegistry;
import com.graphql_java_generator.client.directive.DirectiveRegistryImpl;
import com.graphql_java_generator.client.request.InputParameter;
import com.graphql_java_generator.client.request.InputParameter.InputParameterType;
import com.graphql_java_generator.customscalars.CustomScalarRegistryImpl;

public class DirectiveRegistryInitializer {
	
	/**
	 * Initialization of the {@link DirectiveRegistry} with all known custom scalars, that is with all custom scalars
	 * defined in the project pom
	 */
	public static DirectiveRegistry initDirectiveRegistry() {
		DirectiveRegistry directiveRegistry = new DirectiveRegistryImpl();
		Directive directive;

		/////////////////////////////////////////////////////////////////////////////////////////////////////
		// Creating Directive skip
		/////////////////////////////////////////////////////////////////////////////////////////////////////
		directive = new Directive();
		directive.setName("skip");
		directive.setPackageName("org.graphql.mavenplugin.junittest.starwars_client_springconfiguration");
		directive.getArguments().add(
			InputParameter.newHardCodedParameter("",
					"if", null, "Boolean", true, 0, false));
		directive.getDirectiveLocations().add(DirectiveLocation.FIELD);
		directive.getDirectiveLocations().add(DirectiveLocation.FRAGMENT_SPREAD);
		directive.getDirectiveLocations().add(DirectiveLocation.INLINE_FRAGMENT);
		directiveRegistry.registerDirective(directive);

		/////////////////////////////////////////////////////////////////////////////////////////////////////
		// Creating Directive include
		/////////////////////////////////////////////////////////////////////////////////////////////////////
		directive = new Directive();
		directive.setName("include");
		directive.setPackageName("org.graphql.mavenplugin.junittest.starwars_client_springconfiguration");
		directive.getArguments().add(
			InputParameter.newHardCodedParameter("",
					"if", null, "Boolean", true, 0, false));
		directive.getDirectiveLocations().add(DirectiveLocation.FIELD);
		directive.getDirectiveLocations().add(DirectiveLocation.FRAGMENT_SPREAD);
		directive.getDirectiveLocations().add(DirectiveLocation.INLINE_FRAGMENT);
		directiveRegistry.registerDirective(directive);

		/////////////////////////////////////////////////////////////////////////////////////////////////////
		// Creating Directive defer
		/////////////////////////////////////////////////////////////////////////////////////////////////////
		directive = new Directive();
		directive.setName("defer");
		directive.setPackageName("org.graphql.mavenplugin.junittest.starwars_client_springconfiguration");
		directive.getArguments().add(
			InputParameter.newHardCodedParameter("",
					"if", null, "Boolean", true, 0, false));
		directive.getDirectiveLocations().add(DirectiveLocation.FIELD);
		directiveRegistry.registerDirective(directive);

		/////////////////////////////////////////////////////////////////////////////////////////////////////
		// Creating Directive deprecated
		/////////////////////////////////////////////////////////////////////////////////////////////////////
		directive = new Directive();
		directive.setName("deprecated");
		directive.setPackageName("org.graphql.mavenplugin.junittest.starwars_client_springconfiguration");
		directive.getArguments().add(
			InputParameter.newHardCodedParameter("",
					"reason", null, "String", false, 0, false));
		directive.getDirectiveLocations().add(DirectiveLocation.FIELD_DEFINITION);
		directive.getDirectiveLocations().add(DirectiveLocation.ENUM_VALUE);
		directiveRegistry.registerDirective(directive);


		DirectiveRegistryImpl.directiveRegistry = directiveRegistry;
		return directiveRegistry;
	}

}
