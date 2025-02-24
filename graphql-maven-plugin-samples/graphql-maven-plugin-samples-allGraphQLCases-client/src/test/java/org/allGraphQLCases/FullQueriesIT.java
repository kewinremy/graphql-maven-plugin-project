package org.allGraphQLCases;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.allGraphQLCases.client.CEP_Episode_CES;
import org.allGraphQLCases.client.CINP_HumanInput_CINS;
import org.allGraphQLCases.client.CIP_Character_CIS;
import org.allGraphQLCases.client.CTP_AnotherMutationType_CTS;
import org.allGraphQLCases.client.CTP_Human_CTS;
import org.allGraphQLCases.client.CTP_MyQueryType_CTS;
import org.allGraphQLCases.client.util.AnotherMutationTypeExecutorAllGraphQLCases;
import org.allGraphQLCases.client.util.GraphQLRequest;
import org.allGraphQLCases.client.util.MyQueryTypeExecutorAllGraphQLCases;
import org.apache.commons.text.StringEscapeUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.graphql_java_generator.exception.GraphQLRequestExecutionException;
import com.graphql_java_generator.exception.GraphQLRequestPreparationException;

//Adding "webEnvironment = SpringBootTest.WebEnvironment.NONE" avoid this error:
//"No qualifying bean of type 'ReactiveClientRegistrationRepository' available"
//More details here: https://stackoverflow.com/questions/62558552/error-when-using-enablewebfluxsecurity-in-springboot
@SpringBootTest(classes = SpringTestConfig.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Execution(ExecutionMode.CONCURRENT)
class FullQueriesIT {

	@Autowired
	MyQueryTypeExecutorAllGraphQLCases myQuery;
	@Autowired
	AnotherMutationTypeExecutorAllGraphQLCases mutationType;

	GraphQLRequest mutationWithDirectiveRequest;
	GraphQLRequest mutationWithoutDirectiveRequest;
	GraphQLRequest withDirectiveTwoParametersRequest;
	GraphQLRequest multipleQueriesRequest;

	public static class ExtensionValue {
		public String name;
		public String forname;
	}

	@BeforeEach
	void setup() throws GraphQLRequestPreparationException {
		// The response preparation should be somewhere in the application initialization code.
		mutationWithDirectiveRequest = mutationType.getGraphQLRequest(//
				"mutation{createHuman (human: &humanInput) @testDirective(value:&value, anotherValue:?anotherValue)   "//
						+ "{id name appearsIn friends {id name}}}"//
		);

		mutationWithoutDirectiveRequest = mutationType.getGraphQLRequest(//
				"mutation{createHuman (human: &humanInput) {id name appearsIn friends {id name}}}"//
		);

		withDirectiveTwoParametersRequest = mutationType.getGraphQLRequest(
				"query{directiveOnQuery (uppercase: false) @testDirective(value:&value, anotherValue:?anotherValue)}");

		multipleQueriesRequest = myQuery.getGraphQLRequest("{"//
				+ " directiveOnQuery (uppercase: false) @testDirective(value:&value, anotherValue:?anotherValue)"//
				+ " withOneOptionalParam {id name appearsIn friends {id name}}"//
				+ " withoutParameters {appearsIn @skip(if: &skipAppearsIn) name @skip(if: &skipName) }"//
				+ "}");

	}

	@Execution(ExecutionMode.CONCURRENT)
	@Test
	void test_noDirective() throws GraphQLRequestExecutionException, GraphQLRequestPreparationException {

		// Go, go, go
		CTP_MyQueryType_CTS resp = myQuery.exec("{directiveOnQuery}"); // Direct queries should be used only for very
		// simple cases

		// Verifications
		assertNotNull(resp);
		List<String> ret = resp.getDirectiveOnQuery();
		assertNotNull(ret);
		assertEquals(0, ret.size());
	}

	@Execution(ExecutionMode.CONCURRENT)
	@Test
	void test_extensionsResponseField()
			throws GraphQLRequestExecutionException, GraphQLRequestPreparationException, JsonProcessingException {

		// Go, go, go
		CTP_MyQueryType_CTS resp = myQuery.exec("{directiveOnQuery}"); // Direct queries should be used only for very
		// simple cases

		// Verifications
		// The extensions field contains a CTP_Human_CTS instance, for the key "aValueToTestTheExtensionsField".
		// Check the org.allGraphQLCases.server.extensions.CustomBeans (creation of the customGraphQL Spring bean)
		assertNotNull(resp);
		assertNotNull(resp.getExtensions());
		assertNotNull(resp.getExtensionsAsMap());
		assertNotNull(resp.getExtensionsAsMap().get("aValueToTestTheExtensionsField"));
		ExtensionValue value = resp.getExtensionsField("aValueToTestTheExtensionsField", ExtensionValue.class);
		assertEquals("The name", value.name);
		assertEquals("The forname", value.forname);
	}

	@Execution(ExecutionMode.CONCURRENT)
	@Test
	void test_withDirectiveOneParameter() throws GraphQLRequestExecutionException, GraphQLRequestPreparationException {

		// Go, go, go

		// Direct queries should be used only for very simple cases, but you can do what you want... :)
		CTP_MyQueryType_CTS resp = myQuery.exec("{directiveOnQuery  (uppercase: true) @testDirective(value:&value)}", //
				"value", "the value", "skip", Boolean.FALSE);

		// Verifications
		assertNotNull(resp);
		List<String> ret = resp.getDirectiveOnQuery();
		assertNotNull(ret);
		assertEquals(1, ret.size());
		//
		assertEquals("THE VALUE", ret.get(0));
	}

	@Execution(ExecutionMode.CONCURRENT)
	@Test
	void test_withDirectiveTwoParameters() throws GraphQLRequestExecutionException, GraphQLRequestPreparationException {

		// Go, go, go
		CTP_MyQueryType_CTS resp = withDirectiveTwoParametersRequest.execQuery( //
				"value", "the value", "anotherValue", "the other value", "skip", Boolean.TRUE);

		// Verifications
		assertNotNull(resp);
		List<String> ret = resp.getDirectiveOnQuery();
		assertNotNull(ret);
		assertEquals(2, ret.size());
		//
		assertEquals("the value", ret.get(0));
		assertEquals("the other value", ret.get(1));
	}

	@Execution(ExecutionMode.CONCURRENT)
	@Test
	void test_customScalar_base64()
			throws GraphQLRequestExecutionException, GraphQLRequestPreparationException, UnsupportedEncodingException {
		// Preparation
		String str = "This a string with some special characters éàëöô";
		byte[] bytes = str.getBytes("UTF-8");
		//
		GraphQLRequest graphQLRequest = new GraphQLRequest(//
				"query {testBase64String (input: &input) { }}"//
		);

		// Go, go, go
		CTP_MyQueryType_CTS resp = myQuery.exec(graphQLRequest, "input", bytes);

		// Verifications
		assertNotNull(resp);
		assertArrayEquals(bytes, resp.getTestBase64String());
	}

	@Execution(ExecutionMode.CONCURRENT)
	@Test
	void test_mutation() throws GraphQLRequestExecutionException, GraphQLRequestPreparationException {
		// Preparation
		CINP_HumanInput_CINS input = new CINP_HumanInput_CINS();
		input.setName("a new name");
		List<CEP_Episode_CES> episodes = new ArrayList<>();
		episodes.add(CEP_Episode_CES.JEDI);
		episodes.add(CEP_Episode_CES.EMPIRE);
		episodes.add(CEP_Episode_CES.NEWHOPE);
		input.setAppearsIn(episodes);

		////////////////////////////////////////////////////////////////////////////////////////////////
		// WITHOUT DIRECTIVE

		// Go, go, go
		CTP_AnotherMutationType_CTS resp = mutationWithoutDirectiveRequest.execMutation("humanInput", input);

		// Verifications
		assertNotNull(resp);
		CTP_Human_CTS ret = resp.getCreateHuman();
		assertNotNull(ret);
		assertEquals("a new name", ret.getName());

		////////////////////////////////////////////////////////////////////////////////////////////////
		// WITH DIRECTIVE

		// Go, go, go
		resp = mutationWithDirectiveRequest.execMutation( //
				"humanInput", input, //
				"value", "the mutation value", //
				"anotherValue", "the other mutation value");

		// Verifications
		assertNotNull(resp);
		ret = resp.getCreateHuman();
		assertNotNull(ret);
		assertEquals("the other mutation value", ret.getName());
	}

	/**
	 * Test of this multiple query request :
	 * 
	 * <PRE>
	 * {
	 * directiveOnQuery (uppercase: false) @testDirective(value:&value, anotherValue:?anotherValue)
	 * withOneOptionalParam {id name appearsIn friends {id name}}
	 * withoutParameters {appearsIn @skip(if: &skipAppearsIn) name @skip(if: &skipName) }
	 * }
	 * </PRE>
	 */
	@Execution(ExecutionMode.CONCURRENT)
	@Test
	void test_multipleQueriesResponse() throws GraphQLRequestExecutionException {
		/*
		 * { directiveOnQuery (uppercase: false) @testDirective(value:&value, anotherValue:?anotherValue)
		 * 
		 * withOneOptionalParam {id name appearsIn friends {id name}}
		 * 
		 * withoutParameters {appearsIn @skip(if: &skipAppearsIn) name @skip(if: &skipName) }}
		 */

		////////////////////////////////////////////////////////////////////////////////////////////
		// Let's skip appearsIn but not name

		// Go, go, go
		CTP_MyQueryType_CTS resp = multipleQueriesRequest.execQuery( //
				"value", "An expected returned string", //
				"skipAppearsIn", true, //
				"skipName", false);

		// Verification
		assertNotNull(resp);
		//
		List<String> directiveOnQuery = resp.getDirectiveOnQuery();
		assertEquals(1, directiveOnQuery.size());
		assertEquals("An expected returned string", directiveOnQuery.get(0));
		//
		CIP_Character_CIS withOneOptionalParam = resp.getWithOneOptionalParam();
		assertNotNull(withOneOptionalParam.getFriends());
		//
		List<CIP_Character_CIS> withoutParameters = resp.getWithoutParameters();
		assertNotNull(withoutParameters);
		assertTrue(withoutParameters.size() > 0);
		assertNull(withoutParameters.get(0).getAppearsIn());
		assertNotNull(withoutParameters.get(0).getName());

		////////////////////////////////////////////////////////////////////////////////////////////
		// Let's skip appearsIn but not name

		// Go, go, go
		resp = multipleQueriesRequest.execQuery( //
				"value", "An expected returned string", //
				"skipAppearsIn", false, //
				"skipName", true);

		// Verification
		withoutParameters = resp.getWithoutParameters();
		assertNotNull(withoutParameters);
		assertTrue(withoutParameters.size() > 0);
		assertNotNull(withoutParameters.get(0).getAppearsIn());
		assertNull(withoutParameters.get(0).getName());
	}

	@Test
	@Execution(ExecutionMode.CONCURRENT)
	void test_Issue53_DateQueryParameter() throws GraphQLRequestPreparationException, GraphQLRequestExecutionException {
		// Preparation
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(2018, 02, 01);// Month is 0-based, so this date is 2018, January the first
		Date date = cal.getTime();
		//
		// Go, go, go
		CTP_MyQueryType_CTS resp = myQuery.exec("{issue53(date: &date)}", "date", date);

		// Verifications
		assertNotNull(resp);
		assertEquals(date, resp.getIssue53());
	}

	@Test
	@Execution(ExecutionMode.CONCURRENT)
	void test_Issue65_withGraphQLValuedParameter()
			throws GraphQLRequestPreparationException, GraphQLRequestExecutionException {
		// Preparation
		String request = "mutation mut1 {"//
				+ "createHuman (human:  {name: \"a name with a string that contains a \\\", two { { and a } \", friends: [], appearsIn: [JEDI,NEWHOPE]} )"//
				+ "@testDirective(value:?value, anotherValue:?anotherValue, anArray  : [  \"a string that contains [ [ and ] that should be ignored\" ,  \"another string\" ] , \r\n"
				+ "anObject:{    name: \"a name\" , appearsIn:[],friends : [{name:\"subname\",appearsIn:[],type:\"\"}],type:\"type\"})   {id name appearsIn friends {id name}}}";
		GraphQLRequest graphQLRequest = new GraphQLRequest(request);

		// Go, go, go
		CTP_Human_CTS human = mutationType.execWithBindValues(graphQLRequest, null).getCreateHuman();

		// Verifications
		assertEquals("a name with a string that contains a \", two { { and a } ", human.getName());
	}

	@Test
	@Execution(ExecutionMode.CONCURRENT)
	void test_Issue82_IntParameter() throws GraphQLRequestPreparationException, GraphQLRequestExecutionException {
		// Preparation
		GraphQLRequest request = myQuery
				.getGraphQLRequest("{withOneMandatoryParamDefaultValue (intParam: ?param)  {}}");

		// test 1 (with an int bind parameter)
		Integer ret = request.execQuery("param", 1).getWithOneMandatoryParamDefaultValue();
		assertEquals(1, ret);

		// test 2 (with an Integer bind parameter)
		ret = request.execQuery("param", Integer.valueOf(2)).getWithOneMandatoryParamDefaultValue();
		assertEquals(2, ret);

		// test 3 (with a hardcoded int parameter)
		request = myQuery.getGraphQLRequest("{withOneMandatoryParamDefaultValue (intParam: 3)  {}}");
		ret = request.execQuery().getWithOneMandatoryParamDefaultValue();
		assertEquals(3, ret);

		// test 4 (with a hardcoded boolean and string parameter)
		request = myQuery.getGraphQLRequest("{directiveOnQuery(uppercase: true) @testDirective(value:\"a value\") {}}");
		List<String> strings = request.execQuery().getDirectiveOnQuery();
		assertNotNull(strings);
		assertTrue(strings.size() == 1);
		assertEquals("A VALUE", strings.get(0));

		// test 5 (with a hardcoded Float parameter, that has an int value)
		request = myQuery.getGraphQLRequest("{issue82Float(aFloat: 5) {}}");
		assertEquals(5, request.execQuery().getIssue82Float());

		// test 6 (with a hardcoded Float parameter, that has a float value)
		request = myQuery.getGraphQLRequest("{issue82Float(aFloat: 6.6) {}}");
		assertEquals(6.6, request.execQuery().getIssue82Float());

		// test 7 (with an ID parameter)
		request = myQuery.getGraphQLRequest("{issue82ID(aID: \"123e4567-e89b-12d3-a456-426655440000\") {}}");
		assertTrue(request.execQuery().getIssue82ID().equalsIgnoreCase("123e4567-e89b-12d3-a456-426655440000"),
				"Should be '123e4567-e89b-12d3-a456-426655440000' but is '" + request.execQuery().getIssue82ID() + "'");

		// test 8 (with an enumeration)
		request = myQuery.getGraphQLRequest("{withEnum(episode: JEDI) {name}}");
		assertEquals("JEDI", request.execQuery().getWithEnum().getName());

		// test 9 (with a custom scalar)
		request = myQuery.getGraphQLRequest("{issue53(date: \"2021-05-20\") {}}");
		Date verif = new Calendar.Builder().setDate(2021, 5 - 1, 20).build().getTime();
		assertEquals(verif, request.execQuery().getIssue53());
	}

	@Test
	@Execution(ExecutionMode.CONCURRENT)
	void testEscapedStringParameters() throws GraphQLRequestPreparationException, GraphQLRequestExecutionException {
		// Preparation
		GraphQLRequest request;

		// test 1 (with a hardcoded boolean and string parameter that contains stuff to escape)
		String value = "\\, \"  trailing antislash \\";
		String query = "{directiveOnQuery(uppercase: true) @testDirective(value:\""
				+ StringEscapeUtils.escapeJson(value) + "\") {}}";
		request = myQuery.getGraphQLRequest(query);
		List<String> strings = request.execQuery().getDirectiveOnQuery();
		assertNotNull(strings);
		assertTrue(strings.size() == 1);
		assertEquals(value.toUpperCase(), strings.get(0));

		// test 2 (with a hardcoded boolean and string parameter that contains stuff to escape)
		value = "antislash then escaped double-quote \\\"";
		request = myQuery.getGraphQLRequest("{directiveOnQuery(uppercase: true) @testDirective(value:\""
				+ StringEscapeUtils.escapeJson(value) + "\") {}}");
		strings = request.execQuery().getDirectiveOnQuery();
		assertNotNull(strings);
		assertTrue(strings.size() == 1);
		assertEquals(value.toUpperCase(), strings.get(0));

		// test 3 (with a hardcoded boolean and string parameter that contains stuff to escape)
		value = "escaped values with string read as same bloc (rstuv, tuvw...) \rstuv\tuvw\nopq)";
		request = myQuery.getGraphQLRequest("{directiveOnQuery(uppercase: true) @testDirective(value:\""
				+ StringEscapeUtils.escapeJson(value) + "\") {}}");
		strings = request.execQuery().getDirectiveOnQuery();
		assertNotNull(strings);
		assertTrue(strings.size() == 1);
		assertEquals(value.toUpperCase(), strings.get(0));
	}

}
