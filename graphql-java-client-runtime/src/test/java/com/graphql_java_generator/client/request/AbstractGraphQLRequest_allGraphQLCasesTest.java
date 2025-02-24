package com.graphql_java_generator.client.request;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.text.StringEscapeUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.graphql_java_generator.client.QueryExecutorImpl_allGraphqlCases_Test;
import com.graphql_java_generator.domain.client.allGraphQLCases.AnotherMutationType;
import com.graphql_java_generator.domain.client.allGraphQLCases.AnotherMutationTypeExecutor;
import com.graphql_java_generator.domain.client.allGraphQLCases.Droid;
import com.graphql_java_generator.domain.client.allGraphQLCases.Episode;
import com.graphql_java_generator.domain.client.allGraphQLCases.GraphQLRequest;
import com.graphql_java_generator.domain.client.allGraphQLCases.Human;
import com.graphql_java_generator.domain.client.allGraphQLCases.HumanInput;
import com.graphql_java_generator.domain.client.allGraphQLCases.MyQueryType;
import com.graphql_java_generator.domain.client.allGraphQLCases._extends;
import com.graphql_java_generator.exception.GraphQLRequestExecutionException;
import com.graphql_java_generator.exception.GraphQLRequestPreparationException;

@Execution(ExecutionMode.CONCURRENT)
class AbstractGraphQLRequest_allGraphQLCasesTest {

	HumanInput input;
	Map<String, Object> params = new HashMap<>();

	@BeforeEach
	void setup() {
		input = new HumanInput();
		input.setName("a new name");
		List<Episode> episodes = new ArrayList<>();
		episodes.add(Episode.JEDI);
		episodes.add(Episode.EMPIRE);
		episodes.add(Episode.NEWHOPE);
		input.setAppearsIn(episodes);

		params.put("humanInput", input);
		params.put("value", "the mutation value");
		params.put("anotherValue", "the other mutation value");
	}

	@Test
	@Execution(ExecutionMode.CONCURRENT)
	void testBuild_scalarInputParameters() throws GraphQLRequestPreparationException {
		// Go, go, go
		MyQueryType queryType = new MyQueryType("http://localhost");
		@SuppressWarnings("deprecation")
		AbstractGraphQLRequest graphQLRequest = queryType.getABreakResponseBuilder()
				.withQueryResponseDef("{case(test: DOUBLE)}").build();

		// Verification
		assertEquals(0, graphQLRequest.aliasFields.size());
		assertEquals(1, graphQLRequest.query.fields.size());
		QueryField aBreak = graphQLRequest.query.fields.get(0);
		assertEquals("aBreak", aBreak.name);
		assertNull(aBreak.alias);
		assertEquals(2, aBreak.fields.size(), " (with the added __typename field)");

		QueryField field = aBreak.fields.get(0);
		assertEquals("case", field.name);
		assertNull(field.alias);
		assertEquals(1, field.inputParameters.size());
		assertEquals("test", field.inputParameters.get(0).getName());
		assertEquals(_extends.DOUBLE, field.inputParameters.get(0).getValue());
	}

	@Test
	@Execution(ExecutionMode.CONCURRENT)
	void testBuild_Partial_createHuman()
			throws GraphQLRequestPreparationException, GraphQLRequestExecutionException, JsonProcessingException {
		// Preparation
		AnotherMutationType mutationType = new AnotherMutationType("http://localhost/graphql");
		params = new HashMap<>();
		params.put("anotherMutationTypeCreateHumanHuman", input);
		params.put("value", "the mutation value");
		params.put("anotherValue", "the other mutation value");

		// Go, go, go
		@SuppressWarnings("deprecation")
		AbstractGraphQLRequest graphQLRequest = mutationType.getCreateHumanResponseBuilder()
				.withQueryResponseDef("{id name appearsIn friends {id name}}}").build();

		// Verification
		assertEquals(0, graphQLRequest.aliasFields.size());
		assertEquals("{\"query\":\"mutation" //
				+ "{createHuman(human:{name:\\\"a new name\\\",appearsIn:[JEDI, EMPIRE, NEWHOPE]})"//
				+ "{id name appearsIn friends{id name __typename} __typename}}" //
				+ "\"}", //
				graphQLRequest.buildRequestAsString(params));

		QueryExecutorImpl_allGraphqlCases_Test.checkRequestMap(graphQLRequest.buildRequestAsMap(params), ""//
				+ "mutation" //
				+ "{createHuman(human:{name:\"a new name\",appearsIn:[JEDI, EMPIRE, NEWHOPE]})"//
				+ "{id name appearsIn friends{id name __typename} __typename}}", null, null);
	}

	@Test
	@Execution(ExecutionMode.CONCURRENT)
	void testBuild_Partial_createHuman_Alias_Errors()
			throws GraphQLRequestPreparationException, GraphQLRequestExecutionException {
		// Preparation
		AnotherMutationTypeExecutor mutationType = new AnotherMutationType("http://localhost/graphql");
		GraphQLRequestPreparationException e;

		e = assertThrows(GraphQLRequestPreparationException.class,
				() -> mutationType.getCreateHumanGraphQLRequest("{alias:id alias:name}"));
		assertTrue(e.getMessage().contains(" 'alias' "));

		e = assertThrows(GraphQLRequestPreparationException.class,
				() -> mutationType.getCreateHumanGraphQLRequest("{aliasId:id friends {aliasId:name}}}"));
		assertTrue(e.getMessage().contains(" 'aliasId' "));
	}

	@Test
	@Execution(ExecutionMode.CONCURRENT)
	void testBuild_Partial_createHuman_Alias()
			throws GraphQLRequestPreparationException, GraphQLRequestExecutionException, JsonProcessingException {
		Class<?> droidClass;
		Class<?> humanClass;

		// Preparation
		AnotherMutationType mutationType = new AnotherMutationType("http://localhost/graphql");
		params = new HashMap<>();
		params.put("anotherMutationTypeCreateHumanHuman", input);
		params.put("value", "the mutation value");
		params.put("anotherValue", "the other mutation value");

		// Go, go, go
		@SuppressWarnings("deprecation")
		AbstractGraphQLRequest graphQLRequest = mutationType.getCreateHumanResponseBuilder().withQueryResponseDef(
				"{aliasId : id aliasName : name aliasAppearsIn : appearsIn friendsAlias:friends {aliasId:id aliasName2:name}}}")
				.build();

		// Verification
		assertEquals(2, graphQLRequest.aliasFields.size(), "Aliases are defined for Human and Character");
		//
		Iterator<Class<?>> it = graphQLRequest.aliasFields.keySet().iterator();
		// The items in the keyset may be in any order. And we expet a Droid and a Human class:
		Class<?> class1 = it.next();
		if (class1 == Human.class) {
			humanClass = class1;
			droidClass = it.next();
		} else {
			droidClass = class1;
			humanClass = it.next();
		}
		//
		assertEquals(Human.class, humanClass);
		assertEquals(5, graphQLRequest.aliasFields.get(humanClass).size());
		assertEquals("id", graphQLRequest.aliasFields.get(humanClass).get("aliasId").getName());
		assertEquals("name", graphQLRequest.aliasFields.get(humanClass).get("aliasName").getName());
		assertEquals("name", graphQLRequest.aliasFields.get(humanClass).get("aliasName2").getName());
		assertEquals("appearsIn", graphQLRequest.aliasFields.get(humanClass).get("aliasAppearsIn").getName());
		assertEquals("friends", graphQLRequest.aliasFields.get(humanClass).get("friendsAlias").getName());
		//
		assertEquals(Droid.class, droidClass);
		assertEquals(2, graphQLRequest.aliasFields.get(droidClass).size());
		assertEquals("id", graphQLRequest.aliasFields.get(droidClass).get("aliasId").getName());
		assertEquals("name", graphQLRequest.aliasFields.get(droidClass).get("aliasName2").getName());
		//
		assertEquals("{\"query\":\"mutation" //
				+ "{createHuman(human:{name:\\\"a new name\\\",appearsIn:[JEDI, EMPIRE, NEWHOPE]})"//
				+ "{aliasId:id aliasName:name aliasAppearsIn:appearsIn friendsAlias:friends{aliasId:id aliasName2:name __typename} __typename}}" //
				+ "\"}", //
				graphQLRequest.buildRequestAsString(params));
		QueryExecutorImpl_allGraphqlCases_Test.checkRequestMap(graphQLRequest.buildRequestAsMap(params), ""//
				+ "mutation" //
				+ "{createHuman(human:{name:\"a new name\",appearsIn:[JEDI, EMPIRE, NEWHOPE]})"//
				+ "{aliasId:id aliasName:name aliasAppearsIn:appearsIn friendsAlias:friends{aliasId:id aliasName2:name __typename} __typename}}", //
				null, null);
	}

	@Test
	@Execution(ExecutionMode.CONCURRENT)
	void testBuild_Full_createHuman_withBuilder()
			throws GraphQLRequestPreparationException, GraphQLRequestExecutionException, JsonProcessingException {
		// Preparation
		AnotherMutationType mutationType = new AnotherMutationType("http://localhost/graphql");

		// Go, go, go
		@SuppressWarnings("deprecation")
		AbstractGraphQLRequest graphQLRequest = mutationType.getResponseBuilder().withQueryResponseDef(//
				"mutation { createHuman (human: &humanInput) @testDirective(value:&value, anotherValue:?anotherValue)   "//
						+ "{id name appearsIn friends {id name}}}"//
		).build();

		// Verification
		assertEquals(0, graphQLRequest.aliasFields.size());
		assertEquals("{\"query\":\"mutation" //
				+ "{createHuman(human:{name:\\\"a new name\\\",appearsIn:[JEDI, EMPIRE, NEWHOPE]}) @testDirective(value:\\\"the mutation value\\\",anotherValue:\\\"the other mutation value\\\")"//
				+ "{id name appearsIn friends{id name __typename} __typename}}" //
				+ "\"}", //
				graphQLRequest.buildRequestAsString(params));
		QueryExecutorImpl_allGraphqlCases_Test.checkRequestMap(graphQLRequest.buildRequestAsMap(params), ""//
				+ "mutation" //
				+ "{createHuman(human:{name:\"a new name\",appearsIn:[JEDI, EMPIRE, NEWHOPE]}) @testDirective(value:\"the mutation value\",anotherValue:\"the other mutation value\")"//
				+ "{id name appearsIn friends{id name __typename} __typename}}", //
				null, null);
	}

	@Test
	@Execution(ExecutionMode.CONCURRENT)
	void testBuild_Full_createHuman()
			throws GraphQLRequestPreparationException, GraphQLRequestExecutionException, JsonProcessingException {
		// Preparation

		// Go, go, go
		GraphQLRequest graphQLRequest = new GraphQLRequest(//
				"mutation {createHuman (human: &humanInput) @testDirective(value:&value, anotherValue:?anotherValue)   "//
						+ "{id name appearsIn friends {id name}}}"//
		);

		// Verification
		assertEquals(0, ((AbstractGraphQLRequest) graphQLRequest).aliasFields.size());
		assertEquals("{\"query\":\"mutation" //
				+ "{createHuman(human:{name:\\\"a new name\\\",appearsIn:[JEDI, EMPIRE, NEWHOPE]}) @testDirective(value:\\\"the mutation value\\\",anotherValue:\\\"the other mutation value\\\")"//
				+ "{id name appearsIn friends{id name __typename} __typename}}" //
				+ "\"}", //
				graphQLRequest.buildRequestAsString(params));
		QueryExecutorImpl_allGraphqlCases_Test.checkRequestMap(graphQLRequest.buildRequestAsMap(params), ""//
				+ "mutation" //
				+ "{createHuman(human:{name:\"a new name\",appearsIn:[JEDI, EMPIRE, NEWHOPE]}) @testDirective(value:\"the mutation value\",anotherValue:\"the other mutation value\")"//
				+ "{id name appearsIn friends{id name __typename} __typename}}", //
				null, null);
	}

	@Test
	@Execution(ExecutionMode.CONCURRENT)
	void testBuild_Full_base64() throws GraphQLRequestPreparationException, GraphQLRequestExecutionException,
			JsonProcessingException, UnsupportedEncodingException {
		// Preparation
		String str = "This a string with some special characters éàëöô";
		byte[] bytes = str.getBytes("UTF-8");
		String base64 = Base64.getEncoder().encodeToString(bytes);

		params = new HashMap<>();
		params.put("input", bytes);

		// Go, go, go
		GraphQLRequest graphQLRequest = new GraphQLRequest(//
				"query {testBase64String (input: &input) { }}"//
		);

		// Verification
		assertEquals(0, ((AbstractGraphQLRequest) graphQLRequest).aliasFields.size());
		assertEquals("{\"query\":\"query{testBase64String(input:\\\"" + base64 + "\\\")}\"}",
				graphQLRequest.buildRequestAsString(params));
		QueryExecutorImpl_allGraphqlCases_Test.checkRequestMap(graphQLRequest.buildRequestAsMap(params),
				"query{testBase64String(input:\"" + base64 + "\")}", null, null);
	}

	@Test
	@Execution(ExecutionMode.CONCURRENT)
	void testBuild_Full_createHuman_WithHardCodedParameters()
			throws GraphQLRequestPreparationException, GraphQLRequestExecutionException, JsonProcessingException {
		// Preparation
		params = new HashMap<>();
		params.put("value", "the directive value");
		params.put("anotherValue", "the other directive value");

		// Go, go, go
		String request = "mutation mut1 {"//
				+ "createHuman (human:  {name: \"a name with a string that contains a \\\", two { { and a } \", friends: [], appearsIn: [JEDI,NEWHOPE]} )"//
				+ "@testDirective("//
				+ "   value:?value, "//
				+ "   anotherValue:?anotherValue,"//
				+ "   anArray  : [  \"a string that contains [ [ and ] that should be ignored\" ,  \"another string\" ] , \r\n"//
				+ "   anObject:{    name: \"a name\" , appearsIn:[],friends : [{name:\"subname\",appearsIn:[],type:\"\"}],type:\"type\"}"
				+ ")   " //
				+ "{id name appearsIn friends {id name}}}";
		GraphQLRequest graphQLRequest = new GraphQLRequest(request);

		// Verification
		assertEquals(0, ((AbstractGraphQLRequest) graphQLRequest).aliasFields.size());
		assertEquals("{\"query\":\"mutation mut1" //
				+ "{createHuman(human:{name: \\\"a name with a string that contains a \\\\\\\", two { { and a } \\\", friends: [], appearsIn: [JEDI,NEWHOPE]})"
				+ " @testDirective(value:\\\"the directive value\\\",anotherValue:\\\"the other directive value\\\","
				+ "anArray:[  \\\"a string that contains [ [ and ] that should be ignored\\\" ,  \\\"another string\\\" ],"
				+ "anObject:{    name: \\\"a name\\\" , appearsIn:[],friends : [{name:\\\"subname\\\",appearsIn:[],type:\\\"\\\"}],type:\\\"type\\\"})"//
				+ "{id name appearsIn friends{id name __typename} __typename}}" //
				+ "\"}", //
				graphQLRequest.buildRequestAsString(params));
		QueryExecutorImpl_allGraphqlCases_Test.checkRequestMap(graphQLRequest.buildRequestAsMap(params), ""//
				+ "mutation mut1" //
				+ "{createHuman(human:{name: \"a name with a string that contains a \\\", two { { and a } \", friends: [], appearsIn: [JEDI,NEWHOPE]})"
				+ " @testDirective(value:\"the directive value\",anotherValue:\"the other directive value\","
				+ "anArray:[  \"a string that contains [ [ and ] that should be ignored\" ,  \"another string\" ],"
				+ "anObject:{    name: \"a name\" , appearsIn:[],friends : [{name:\"subname\",appearsIn:[],type:\"\"}],type:\"type\"})"//
				+ "{id name appearsIn friends{id name __typename} __typename}}", //
				null, null);
	}

	@Test
	@Execution(ExecutionMode.CONCURRENT)
	void testBuild_Full_createHuman_WithHardCodedParametersToBeEscaped()
			throws GraphQLRequestPreparationException, GraphQLRequestExecutionException, JsonProcessingException {

		// Preparation
		params = new HashMap<>();
		params.put("value", "the directive value");
		params.put("anotherValue", "the other directive value");

		// Go, go, go
		String request = "mutation mut1 {"//
				+ "createHuman (human:  {name: \"a name with a string that contains a \\\", two { { and a } \", friends: [], appearsIn: [JEDI,NEWHOPE]} )"//
				+ "@testDirective("//
				+ "   value:?value, "//
				+ "   anotherValue:?anotherValue,"//
				+ "   anArray  : [  \"a string that contains [ [ and ] that should be ignored\" ,  \"another string\" ] , \r\n"//
				+ "   anObject:{    name: \"a name\" , appearsIn:[],friends : [{name:\"subname\",appearsIn:[],type:\"\"}],type:\"type\"}"
				+ ")   " //
				+ "{id name appearsIn friends {id name}}}";
		GraphQLRequest graphQLRequest = new GraphQLRequest(request);

		// Verification
		assertEquals(0, ((AbstractGraphQLRequest) graphQLRequest).aliasFields.size());
		assertEquals("{\"query\":\"mutation mut1" //
				+ "{createHuman(human:{name: \\\"a name with a string that contains a \\\\\\\", two { { and a } \\\", friends: [], appearsIn: [JEDI,NEWHOPE]})"
				+ " @testDirective(value:\\\"the directive value\\\",anotherValue:\\\"the other directive value\\\","
				+ "anArray:[  \\\"a string that contains [ [ and ] that should be ignored\\\" ,  \\\"another string\\\" ],"
				+ "anObject:{    name: \\\"a name\\\" , appearsIn:[],friends : [{name:\\\"subname\\\",appearsIn:[],type:\\\"\\\"}],type:\\\"type\\\"})"//
				+ "{id name appearsIn friends{id name __typename} __typename}}" //
				+ "\"}", //
				graphQLRequest.buildRequestAsString(params));
		QueryExecutorImpl_allGraphqlCases_Test.checkRequestMap(graphQLRequest.buildRequestAsMap(params), ""//
				+ "mutation mut1" //
				+ "{createHuman(human:{name: \"a name with a string that contains a \\\", two { { and a } \", friends: [], appearsIn: [JEDI,NEWHOPE]})"
				+ " @testDirective(value:\"the directive value\",anotherValue:\"the other directive value\","
				+ "anArray:[  \"a string that contains [ [ and ] that should be ignored\" ,  \"another string\" ],"
				+ "anObject:{    name: \"a name\" , appearsIn:[],friends : [{name:\"subname\",appearsIn:[],type:\"\"}],type:\"type\"})"//
				+ "{id name appearsIn friends{id name __typename} __typename}}", //
				null, null);

		//////////////////////////////

		String value = "\\, \"  trailing antislash \\";
		String query = "{directiveOnQuery(uppercase: true) @testDirective(value:\""
				+ StringEscapeUtils.escapeJson(value) + "\") {}}";
		assertEquals(
				"{\"query\":\"query{directiveOnQuery(uppercase:true) @testDirective(value:\\\"\\\\\\\\, \\\\\\\"  trailing antislash \\\\\\\\\\\")}\"}",
				new GraphQLRequest(query).buildRequestAsString(null));

		value = "antislash then escaped double-quote \\\"";
		query = "{directiveOnQuery(uppercase: true) @testDirective(value:\"" + StringEscapeUtils.escapeJson(value)
				+ "\") {}}";
		assertEquals(
				"{\"query\":\"query{directiveOnQuery(uppercase:true) @testDirective(value:\\\"antislash then escaped double-quote \\\\\\\\\\\\\\\"\\\")}\"}",
				new GraphQLRequest(query).buildRequestAsString(null));

		value = "escaped values with string read as same bloc (rstuv, tuvw...) \rstuv\tuvw\nopq)";
		query = "{directiveOnQuery(uppercase: true) @testDirective(value:\"" + StringEscapeUtils.escapeJson(value)
				+ "\") {}}";
		assertEquals(
				"{\"query\":\"query{directiveOnQuery(uppercase:true) @testDirective(value:\\\"escaped values with string read as same bloc (rstuv, tuvw...) \\\\rstuv\\\\tuvw\\\\nopq)\\\")}\"}",
				new GraphQLRequest(query).buildRequestAsString(null));
	}

	@Test
	@Execution(ExecutionMode.CONCURRENT)
	void testBuild_Full_createHuman_KOInput1()
			throws GraphQLRequestPreparationException, GraphQLRequestExecutionException {
		// Preparation
		params = new HashMap<>();
		params.put("value", "the directive value");
		params.put("anotherValue", "the other directive value");

		// Go, go, go
		GraphQLRequestPreparationException e = assertThrows(GraphQLRequestPreparationException.class,
				() -> new GraphQLRequest(//
						"mutation {createHuman (human:  {name: \"a name\", friends: [], appearsIn: [JEDI,NEWHOPE], type: \"a type\")"
								+ "@testDirective(value:&value, anotherValue:?anotherValue, anArray  : [  \"a string\" ,  \"another string\" ] , \r\n"
								+ "anObject:{    name: \"a name\" , [{name=\"subname\"}],type:\"type\"})   "//
								+ "{id name appearsIn friends {id name}}}"//
				));

		// Verification
		assertTrue(e.getMessage().contains("The list of parameters for the field 'createHuman' is not finished"),
				"check of this error message: " + e.getMessage());
	}

	@Test
	@Execution(ExecutionMode.CONCURRENT)
	void testBuild_Full_createHuman_KOInput2()
			throws GraphQLRequestPreparationException, GraphQLRequestExecutionException {
		// Preparation

		// Go, go, go
		GraphQLRequestPreparationException e = assertThrows(GraphQLRequestPreparationException.class,
				() -> new GraphQLRequest(//
						"mutation {createHuman (human:  {name: \"a name\", friends: [], appearsIn: [JEDI,NEWHOPE], type: \"a type\"})"
								+ "@testDirective(value:&value, anotherValue:?anotherValue, anArray  : [  \"a string\" ,  \"another string\"  , \r\n"
								+ "anObject:{    name: \"a name\" , [{name=\"subname\"}],type:\"type\"})   "//
								+ "{id name appearsIn friends {id name}}}"//
				));

		// Verification
		assertTrue(e.getMessage().contains("Found the end of the GraphQL request before the end of the list"),
				"check of this error message: " + e.getMessage());
	}

}
