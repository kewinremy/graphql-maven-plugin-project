##
## Velocity template for the executor of a Query or a Mutation type (client side)
##
## The generated class contains:
## - All the utility classes that allow to prepare and execute the query/mutation
##
##
## This template has these inputs:
## packageUtilName 			The package where this class must be generated
## configuration		The plugin's configuration
## object					The query or mutation type, for which this executor is being generated
##
##
## Maven ignores the default value for springBeanSuffix, and replaces it by a null value. In this case, we replace the value by an empty String 
#if (!$configuration.springBeanSuffix) #set($springBeanSuffix="") #else #set($springBeanSuffix = ${configuration.springBeanSuffix}) #end
##
/** Generated by the default template from graphql-java-generator */
package ${packageUtilName};
##
## The inputParams macto lists the input parameters for a field
#macro(inputParams)
#foreach ($inputParameter in $field.inputParameters)
			${inputParameter.javaTypeFullClassname} ${inputParameter.javaName},
#end
#end
##
## The inputValues macro lists the input values for the parameters for a field
#macro(inputValues)#foreach ($inputParameter in $field.inputParameters), ${inputParameter.javaName}#end#end
##
##
##
#if($configuration.generateDeprecatedRequestResponse)
#set ($executionResponse = "${object.classSimpleName}Response")
#else
#set ($executionResponse = "${configuration.packageName}.${object.classSimpleName}")
#end


import java.util.HashMap;
import java.util.Map;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.ws.rs.client.Client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.graphql_java_generator.annotation.GraphQLNonScalar;
import com.graphql_java_generator.annotation.GraphQLObjectType;
import com.graphql_java_generator.annotation.GraphQLQuery;
import com.graphql_java_generator.annotation.GraphQLScalar; 
import com.graphql_java_generator.exception.GraphQLRequestExecutionException;
import com.graphql_java_generator.exception.GraphQLRequestPreparationException;
import com.graphql_java_generator.client.GraphQLMutationExecutor;
import com.graphql_java_generator.client.GraphQLObjectMapper;
import com.graphql_java_generator.client.GraphQLQueryExecutor;
import com.graphql_java_generator.client.request.InputParameter;
import com.graphql_java_generator.client.request.InputParameter.InputParameterType;
import com.graphql_java_generator.client.request.ObjectResponse;

#foreach($import in ${object.importsForUtilityClasses})
import $import;
#end

import com.graphql_java_generator.client.GraphQLConfiguration;
import com.graphql_java_generator.client.GraphqlClientUtils;
import com.graphql_java_generator.util.GraphqlUtils;


/**
#foreach ($comment in $object.comments)
 * $comment
#end
#if ($object.comments.size() > 0)
 * <BR/>
#end
 * This class contains the methods that allows the execution of the queries or mutations that are defined in the ${object.name} of the GraphQL schema.<BR/>
 * These methods allows:
 * <UL>
 * <LI>Preparation of full requests</LI>
 * <LI>Execution of full prepared requests</LI>
 * <LI>Execution of full direct requests</LI>
 * <LI>Preparation of partial requests</LI>
 * <LI>Execution of partial prepared requests</LI>
 * <LI>Execution of partial direct requests</LI>
 * </UL>
 * You'll find all the documentation on the <A HREF="https://graphql-maven-plugin-project.graphql-java-generator.com/client.html">client page doc</A>.
 * 
 * @author generated by graphql-java-generator
 * @see <a href="https://github.com/graphql-java-generator/graphql-java-generator">https://github.com/graphql-java-generator/graphql-java-generator</a>
 */
@Component
@SuppressWarnings("unused")
public class ${object.classSimpleName}Executor${springBeanSuffix} implements#if($object.requestType=="mutation") GraphQLMutationExecutor #else GraphQLQueryExecutor #end{

	/** Logger for this class */
	private static Logger logger = LoggerFactory.getLogger(${object.name}Executor${springBeanSuffix}.class);

	GraphqlClientUtils graphqlClientUtils = new GraphqlClientUtils();
	GraphqlUtils graphqlUtils = new GraphqlUtils();

	@Autowired
	@Qualifier("graphQLConfiguration${springBeanSuffix}")
	GraphQLConfiguration graphQLConfiguration${springBeanSuffix};

	/**
	 * This default constructor is used by Spring, when building the component, and by the Jackson deserializer.
	 */
	@Autowired
	public ${object.classSimpleName}Executor${springBeanSuffix}() {
## The @..@ is the placeholder for the maven resource filtering
		if (!"@project.version@".equals(graphqlUtils.getRuntimeVersion())) {
			throw new RuntimeException("The GraphQL runtime version doesn't match the GraphQL plugin version. The runtime's version is '"
					+ graphqlUtils.getRuntimeVersion() 
					+ "' whereas the GraphQL plugin version is '@project.version@'");
		}
		CustomScalarRegistryInitializer.initCustomScalarRegistry();
		DirectiveRegistryInitializer.initDirectiveRegistry();
	}

	/**
	 * This constructor expects a GraphQLConfiguration instance.  This is useful for non-Spring frameworks like Micronaut.
	 *
	 * @param configuration
	 *            an already built GraphQLConfiguration instance
	 */
	public ${object.classSimpleName}Executor${springBeanSuffix}(GraphQLConfiguration configuration) {
## The @..@ is the placeholder for the maven resource filtering
		if (!"@project.version@".equals(graphqlUtils.getRuntimeVersion())) {
			throw new RuntimeException("The GraphQL runtime version doesn't match the GraphQL plugin version. The runtime's version is '"
					+ graphqlUtils.getRuntimeVersion()
					+ "' whereas the GraphQL plugin version is '@project.version@'");
		}
		this.graphQLConfiguration${springBeanSuffix} = configuration;
		CustomScalarRegistryInitializer.initCustomScalarRegistry();
		DirectiveRegistryInitializer.initDirectiveRegistry();
	}

	/**
	 * This constructor expects the URI of the GraphQL server. This constructor works only for http servers, not for
	 * https ones.<BR/>
	 * For example: http://my.server.com/graphql
	 * 
	 * @param graphqlEndpoint
	 *            the http URI for the GraphQL endpoint
	 */
	public ${object.classSimpleName}Executor${springBeanSuffix}(String graphqlEndpoint) {
		this(new GraphQLConfiguration(graphqlEndpoint));
	}

	/**
	 * This constructor expects the URI of the GraphQL server. This constructor works only for https servers, not for
	 * http ones.<BR/>
	 * For example: https://my.server.com/graphql<BR/><BR/>
	 * {@link SSLContext} and {@link HostnameVerifier} are regular Java stuff. You'll find lots of documentation on the web. 
	 * The StarWars sample is based on the <A HREF="http://www.thinkcode.se/blog/2019/01/27/a-jersey-client-supporting-https">http://www.thinkcode.se/blog/2019/01/27/a-jersey-client-supporting-https</A> blog.
	 * But this sample implements a noHostVerification, which of course, is the simplest but the safest way to go.
	 * 
	 * @param graphqlEndpoint
	 *            the https URI for the GraphQL endpoint
	 * @param sslContext
	 * @param hostnameVerifier
	 */
	@SuppressWarnings("deprecation")
	public ${object.classSimpleName}Executor${springBeanSuffix}(String graphqlEndpoint, SSLContext sslContext, HostnameVerifier hostnameVerifier) {
		this.graphQLConfiguration${springBeanSuffix} = new GraphQLConfiguration(graphqlEndpoint, sslContext, hostnameVerifier);
		CustomScalarRegistryInitializer.initCustomScalarRegistry();
		DirectiveRegistryInitializer.initDirectiveRegistry();
	}

	/**
	 * This constructor expects the URI of the GraphQL server and a configured JAX-RS client
	 * that gives the opportunity to customize the REST request<BR/>
	 * For example: http://my.server.com/graphql
	 *
	 * @param graphqlEndpoint
	 *            the http URI for the GraphQL endpoint
	 * @param client
	 *            {@link Client} javax.ws.rs.client.Client to support customization of the rest request
	 */
	@SuppressWarnings("deprecation")
	public ${object.classSimpleName}Executor${springBeanSuffix}(String graphqlEndpoint, Client client) {
		this.graphQLConfiguration${springBeanSuffix} = new GraphQLConfiguration(graphqlEndpoint, client);
		CustomScalarRegistryInitializer.initCustomScalarRegistry();
		DirectiveRegistryInitializer.initDirectiveRegistry();
	}

	/**
	 * This method takes a <B>full request</B> definition, and executes the it against the GraphQL server. That is,
	 * the query contains the full string that <B><U>follows</U></B> the query/mutation/subscription keyword.<BR/>
	 * It offers a logging of the call (if in debug mode), or of the call and its parameters (if in trace mode).<BR/>
	 * For instance:
	 * 
	 * <PRE>
	 * Map<String, Object> params = new HashMap<>();
	 * params.put("heroParam", heroParamValue);
	 * params.put("skip", Boolean.FALSE);
	 * 
	 * MyQueryType response = myQueryType.execWithBindValues(
	 * 		"{hero(param:?heroParam) @include(if:true) {id name @skip(if: ?skip) appearsIn friends {id name}}}",
	 * 		params);
	 * Character c = response.getHero();
	 * </PRE>
	 * 
	 * @param queryResponseDef
	 *            The response definition of the ${object.requestType}, in the native GraphQL format (see here above). It must ommit the
	 *            query/mutation/subscription keyword, and start by the first { that follows.It may contain directives,
	 *            as explained in the GraphQL specs.
	 * @param parameters
	 *            The map of values, for the bind variables defined in the query. If there is no bind variable in the
	 *            defined Query, this argument may be null or an empty {@link Map}. The key is the parameter name, as
	 *            defined in the query (in the above sample: heroParam is an optional parameter and skip is a mandatory
	 *            one). The value is the parameter vale in its Java type (for instance a {@link Date} for the
	 *            {@link GraphQLScalarTypeDate}). The parameters which value is missing in this map will no be
	 *            transmitted toward the GraphQL server.
	 * @throws GraphQLRequestPreparationException
	 *             When an error occurs during the request preparation, typically when building the
	 *             {@link ObjectResponse}
	 * @throws GraphQLRequestExecutionException
	 *             When an error occurs during the request execution, typically a network error, an error from the
	 *             GraphQL server or if the server response can't be parsed
	 */
	public $executionResponse execWithBindValues(String queryResponseDef, Map<String, Object> parameters)
			throws GraphQLRequestExecutionException, GraphQLRequestPreparationException {
		logger.debug("Executing ${object.requestType} {} ", queryResponseDef);
		ObjectResponse objectResponse = getResponseBuilder().withQueryResponseDef(queryResponseDef).build();
		return exec(objectResponse, parameters);
	}

	/**
	 * This method takes a <B>full request</B> definition, and executes it against the GraphQL server. That is,
	 * the query contains the full string that <B><U>follows</U></B> the query/mutation/subscription keyword.<BR/>
	 * It offers a logging of the call (if in debug mode), or of the call and its parameters (if in trace mode).<BR/>
	 * For instance:
	 * 
	 * <PRE>
	 * MyQueryType response = myQueryType.execWithBindValues(
	 * 		"{hero(param:?heroParam) @include(if:true) {id name @skip(if: ?skip) appearsIn friends {id name}}}",
	 * 		"heroParam", heroParamValue, "skip", Boolean.FALSE);
	 * Character c = response.getHero();
	 * </PRE>
	 * 
	 * @param queryResponseDef
	 *            The response definition of the query, in the native GraphQL format (see here above). It must ommit the
	 *            query/mutation/subscription keyword, and start by the first { that follows.It may contain directives,
	 *            as explained in the GraphQL specs.
	 * @param paramsAndValues
	 *            This parameter contains all the name and values for the Bind Variables defined in the objectResponse
	 *            parameter, that must be sent to the server. Optional parameter may not have a value. They will be
	 *            ignored and not sent to the server. Mandatory parameter must be provided in this argument.<BR/>
	 *            This parameter contains an even number of parameters: it must be a series of name and values :
	 *            (paramName1, paramValue1, paramName2, paramValue2...)
	 * @throws GraphQLRequestPreparationException
	 *             When an error occurs during the request preparation, typically when building the
	 *             {@link ObjectResponse}
	 * @throws GraphQLRequestExecutionException
	 *             When an error occurs during the request execution, typically a network error, an error from the
	 *             GraphQL server or if the server response can't be parsed
	 */
	public $executionResponse exec(String queryResponseDef, Object... paramsAndValues)
			throws GraphQLRequestExecutionException, GraphQLRequestPreparationException {
		logger.debug("Executing ${object.requestType} {} ", queryResponseDef);
		ObjectResponse objectResponse = getResponseBuilder().withQueryResponseDef(queryResponseDef).build();
		return execWithBindValues(objectResponse, graphqlClientUtils.generatesBindVariableValuesMap(paramsAndValues));
	}

	/**
	 * This method takes a <B>full request</B> definition, and executes it against the GraphQL server. That is,
	 * the query contains the full string that <B><U>follows</U></B> the query/mutation/subscription keyword.<BR/>
	 * It offers a logging of the call (if in debug mode), or of the call and its parameters (if in trace mode).<BR/>
	 * For instance:
	 * 
	 * <PRE>
	 * ObjectResponse response;
	 * 
	 * public void setup() {
	 * 	// Preparation of the query
	 * 	objectResponse = myQueryType.getResponseBuilder()
	 * 			.withQueryResponseDef("{hero(param:?heroParam) @include(if:true) {id name @skip(if: ?skip) appearsIn friends {id name}}}").build();
	 * }
	 * 
	 * public void doTheJob() {
	 * ..
	 * Map<String, Object> params = new HashMap<>();
	 * params.put("heroParam", heroParamValue);
	 * params.put("skip", Boolean.FALSE);
	 * // This will set the value sinceValue to the sinceParam field parameter
	 * MyQueryType response = queryType.execWithBindValues(objectResponse, params);
	 * Character c = response.getHero();
	 * ...
	 * }
	 * </PRE>
	 * 
	 * @param objectResponse
	 *            The definition of the response format, that describes what the GraphQL server is expected to return
	 * @param parameters
	 *            The list of values, for the bind variables defined in the query. If there is no bind variable in the
	 *            defined Query, this argument may be null or an empty {@link Map}
	 * @throws GraphQLRequestExecutionException
	 *             When an error occurs during the request execution, typically a network error, an error from the
	 *             GraphQL server or if the server response can't be parsed
	 */
	public $executionResponse execWithBindValues(ObjectResponse objectResponse, Map<String, Object> parameters)
			throws GraphQLRequestExecutionException {
		if (logger.isTraceEnabled()) {
			if (parameters == null) {
				logger.trace("Executing ${object.requestType} without parameters");
			} else {
				StringBuffer sb = new StringBuffer("Executing root ${object.requestType} with parameters: ");
				boolean addComma = false;
				for (String key : parameters.keySet()) {
					sb.append(key).append(":").append(parameters.get(key));
					if (addComma)
						sb.append(", ");
					addComma = true;
				}
				logger.trace(sb.toString());
			}
		} else if (logger.isDebugEnabled()) {
			logger.debug("Executing ${object.requestType} '${object.name}'");
		}

		// Given values for the BindVariables
		parameters = (parameters != null) ? parameters : new HashMap<>();

		return graphQLConfiguration${springBeanSuffix}.getQueryExecutor().execute(objectResponse, parameters, ${executionResponse}.class);
	}

	/**
	 * This method takes a <B>full request</B> definition, and executes it against the GraphQL server. That is,
	 * the query contains the full string that <B><U>follows</U></B> the query/mutation/subscription keyword.<BR/>
	 * It offers a logging of the call (if in debug mode), or of the call and its parameters (if in trace mode).<BR/>
	 * For instance:
	 * 
	 * <PRE>
	 * ObjectResponse response;
	 * 
	 * public void setup() {
	 * 	// Preparation of the query
	 * 	 objectResponse = myQueryType.getResponseBuilder()
	 * 			.withQueryResponseDef("{hero(param:?heroParam) @include(if:true) {id name @skip(if: ?skip) appearsIn friends {id name}}}").build();
	 * }
	 * 
	 * public void doTheJob() {
	 * ..
	 * // This will set the value sinceValue to the sinceParam field parameter
	 * MyQueryType response = queryType.exec(objectResponse, "heroParam", heroParamValue, "skip", Boolean.FALSE);
	 * Character c = response.getHero();
	 * ...
	 * }
	 * </PRE>
	 * 
	 * @param objectResponse
	 *            The definition of the response format, that describes what the GraphQL server is expected to return
	 * @param paramsAndValues
	 *            This parameter contains all the name and values for the Bind Variables defined in the objectResponse
	 *            parameter, that must be sent to the server. Optional parameter may not have a value. They will be
	 *            ignored and not sent to the server. Mandatory parameter must be provided in this argument.<BR/>
	 *            This parameter contains an even number of parameters: it must be a series of name and values :
	 *            (paramName1, paramValue1, paramName2, paramValue2...)
	 * @throws GraphQLRequestExecutionException
	 *             When an error occurs during the request execution, typically a network error, an error from the
	 *             GraphQL server or if the server response can't be parsed
	 */
	public $executionResponse exec(ObjectResponse objectResponse, Object... paramsAndValues)
			throws GraphQLRequestExecutionException {
		return execWithBindValues(objectResponse, graphqlClientUtils.generatesBindVariableValuesMap(paramsAndValues));
	}

	/**
	 * Get the {@link com.graphql_java_generator.client.request.Builder} for a <B>full request</B>, as expected by the exec 
	 * and execWithBindValues methods.
	 * 
	 * @return
	 * @throws GraphQLRequestPreparationException
	 */
	public com.graphql_java_generator.client.request.Builder getResponseBuilder() throws GraphQLRequestPreparationException {
		return new com.graphql_java_generator.client.request.Builder(GraphQLRequest.class);
	}

	/**
	 * Get the {@link GraphQLRequest} for <B>full request</B>. For instance:
	 * <PRE>
	 * GraphQLRequest request = new GraphQLRequest(fullRequest);
	 * </PRE>
	 * 
	 * @param fullRequest The full GraphQLRequest, as specified in the GraphQL specification
	 * @return
	 * @throws GraphQLRequestPreparationException
	 */
	public GraphQLRequest getGraphQLRequest(String fullRequest) throws GraphQLRequestPreparationException {
		GraphQLRequest ret = new GraphQLRequest(fullRequest);
		ret.setInstanceConfiguration(graphQLConfiguration${springBeanSuffix});
		return ret;
	}

#foreach ($field in $object.fields)
	/**
#foreach ($comment in $field.comments)
	 * ${field.content}
#end
#if ($field.comments.size() > 0)
	 * <BR/>
#end
	 * This method executes a partial query against the GraphQL server. That is, the query that is one of the queries
	 * defined in the GraphQL query object. The queryResponseDef contains the part of the query that <B><U>is
	 * after</U></B> the query name.<BR/>
	 * For instance, if the query hero has one parameter (as defined in the GraphQL schema):
	 * 
	 * <PRE>
	 * Map<String, Object> params = new HashMap<>();
	 * params.put("skip", Boolean.FALSE);
	 *
	 * ${field.javaType} c = myQyeryType.${field.name}WithBindValues("{id name @skip(if: false) appearsIn friends {id name}}"#inputValues, params);
	 * </PRE>
	 * 
	 * It offers a logging of the call (if in debug mode), or of the call and its parameters (if in trace mode).<BR/>
	 * This method takes care of writing the query name, and the parameter(s) for the query. The given queryResponseDef
	 * describes the format of the response of the server response, that is the expected fields of the {@link Character}
	 * GraphQL type. It can be something like "{ id name }", if you want these fields of this type. Please take a look
	 * at the StarWars, Forum and other samples for more complex queries.<BR/>
	 * This method is valid for queries/mutations/subscriptions which don't have bind variables, as there is no
	 * <I>parameters</I> argument to pass the list of values.<BR/>
#if($graphqlUtils.isJavaReservedWords(${field.name}))
	 * This method name is prefixed by ${underscore}, as ${field.name} is a java reserved keyword. 
#end
	 * 
	 * @param queryResponseDef
	 *            The response definition of the query, in the native GraphQL format (see here above)
#foreach ($inputParameter in $field.inputParameters)
	 * @param ${inputParameter.name} Parameter for the ${field.name} field of ${object.name}, as defined in the GraphQL schema
#end
	 * @param parameters
	 *            The list of values, for the bind variables defined in the query. If there is no bind variable in the
	 *            defined Query, this argument may be null or an empty {@link Map}
	 * @throws GraphQLRequestPreparationException
	 *             When an error occurs during the request preparation, typically when building the
	 *             {@link ObjectResponse}
	 * @throws GraphQLRequestExecutionException
	 *             When an error occurs during the request execution, typically a network error, an error from the
	 *             GraphQL server or if the server response can't be parsed
	 */
## Note: we must use the ${query.type.classSimpleName}, as when the GraphQL schema uses request that return the query type, and 
## the query type object is in a separate package (plugin parameter separateUtilityClasses), then there is a conflict between 
## the current name and the query type object: they have the same name, but are in different packages 
#if(${field.type.scalar}) @GraphQLScalar #else @GraphQLNonScalar #end(fieldName = "${field.name}", graphQLTypeSimpleName = "${field.graphQLTypeSimpleName}", javaClass = ${field.type.classFullName}.class)
	public ${field.javaTypeFullClassname} ${field.javaName}WithBindValues(
			String queryResponseDef,
#inputParams()
			Map<String, Object> parameters)
			throws GraphQLRequestExecutionException, GraphQLRequestPreparationException {
		logger.debug("Executing ${object.requestType} '${field.name}': {} ", queryResponseDef);
		ObjectResponse objectResponse = get${field.pascalCaseName}ResponseBuilder().withQueryResponseDef(queryResponseDef).build();
		return ${field.javaName}(objectResponse#inputValues(), parameters);
	}

	/**
#foreach ($comment in $field.comments)
	 * ${field.content}
#end
#if ($field.comments.size() > 0)
	 * <BR/>
#end
	 * This method executes a partial query against the GraphQL server. That is, the query that is one of the queries
	 * defined in the GraphQL query object. The queryResponseDef contains the part of the query that <B><U>is
	 * after</U></B> the query name.<BR/>
	 * For instance, if the query hero has one parameter (as defined in the GraphQL schema):
	 * 
	 * <PRE>
	 * ${field.javaType} c = myQyeryType.${field.name}("{id name @skip(if: false) appearsIn friends {id name}}"#inputValues, "skip", Boolean.FALSE);
	 * </PRE>
	 * 
	 * It offers a logging of the call (if in debug mode), or of the call and its parameters (if in trace mode).<BR/>
	 * This method takes care of writing the query name, and the parameter(s) for the query. The given queryResponseDef
	 * describes the format of the response of the server response, that is the expected fields of the {@link Character}
	 * GraphQL type. It can be something like "{ id name }", if you want these fields of this type. Please take a look
	 * at the StarWars, Forum and other samples for more complex queries.<BR/>
	 * This method is valid for queries/mutations/subscriptions which don't have bind variables, as there is no
	 * <I>parameters</I> argument to pass the list of values.<BR/>
#if($graphqlUtils.isJavaReservedWords(${field.name}))
	 * This method name is prefixed by ${underscore}, as ${field.name} is a java reserved keyword. 
#end
	 * 
	 * @param queryResponseDef
	 *            The response definition of the query, in the native GraphQL format (see here above)
#foreach ($inputParameter in $field.inputParameters)
	 * @param ${inputParameter.name} Parameter for the ${field.name} field of ${object.name}, as defined in the GraphQL schema
#end
	 * @param parameters
	 *            The list of values, for the bind variables defined in the query. If there is no bind variable in the
	 *            defined Query, this argument may be null or an empty {@link Map}
	 * @throws GraphQLRequestPreparationException
	 *             When an error occurs during the request preparation, typically when building the
	 *             {@link ObjectResponse}
	 * @throws GraphQLRequestExecutionException
	 *             When an error occurs during the request execution, typically a network error, an error from the
	 *             GraphQL server or if the server response can't be parsed
	 */
## Note: we must use the ${query.type.classSimpleName}, as when the GraphQL schema uses request that return the query type, and 
## the query type object is in a separate package (plugin parameter separateUtilityClasses), then there is a conflict between 
## the current name and the query type object: they have the same name, but are in different packages 	#if(${field.type.scalar}) @GraphQLScalar #else @GraphQLNonScalar #end(fieldName = "${field.name}", graphQLTypeSimpleName = "${field.graphQLTypeSimpleName}", javaClass = ${field.type.classFullName}.class)
#if(${field.type.scalar}) @GraphQLScalar #else @GraphQLNonScalar #end(fieldName = "${field.name}", graphQLTypeSimpleName = "${field.graphQLTypeSimpleName}", javaClass = ${field.type.classFullName}.class)
	public ${field.javaTypeFullClassname} ${field.javaName}(
			String queryResponseDef,
#inputParams()
			Object... paramsAndValues)
			throws GraphQLRequestExecutionException, GraphQLRequestPreparationException {
		logger.debug("Executing ${object.requestType} '${field.name}': {} ", queryResponseDef);
		ObjectResponse objectResponse = get${field.pascalCaseName}ResponseBuilder().withQueryResponseDef(queryResponseDef).build();
		return ${field.javaName}WithBindValues(objectResponse#inputValues(), graphqlClientUtils.generatesBindVariableValuesMap(paramsAndValues));
	}

	/**
#foreach ($comment in $field.comments)
	 * ${field.content}
#end
#if ($field.comments.size() > 0)
	 * <BR/>
#end
	 * This method is expected by the graphql-java framework. It will be called when this query is called. It offers a
	 * logging of the call (if in debug mode), or of the call and its parameters (if in trace mode).<BR/>
	 * This method is valid for queries/mutations/subscriptions which don't have bind variables, as there is no
	 * <I>parameters</I> argument to pass the list of values.<BR/>
	 * Here is a sample:
	 * 
	 * <PRE>
	 * ObjectResponse response;
	 * public void setup() {
	 * 	// Preparation of the query
	 * 	response = queryType.getBoardsResponseBuilder()
	 * 			.withQueryResponseDef("{id name publiclyAvailable topics(since:?sinceParam){id}}").build();
	 * }
	 * 
	 * public void doTheJob() {
	 * ..
	 * Map<String, Object> params = new HashMap<>();
	 * params.put("sinceParam", sinceValue);
	 * // This will set the value sinceValue to the sinceParam field parameter
	 * ${field.javaType} ret = queryType.${field.name}WithBindValues(response#inputValues, params);
	 * ...
	 * }
	 * </PRE>
#if($graphqlUtils.isJavaReservedWords(${field.name}))
	 * This method name is prefixed by ${underscore}, as ${field.name} is a java reserved keyword. 
#end
	 * 
	 * @param objectResponse
	 *            The definition of the response format, that describes what the GraphQL server is expected to return
#foreach ($inputParameter in $field.inputParameters)
	 * @param ${inputParameter.name} Parameter for the ${field.name} field of ${object.name}, as defined in the GraphQL schema
#end
	 * @param parameters
	 *            The list of values, for the bind variables defined in the query. If there is no bind variable in the
	 *            defined Query, this argument may be null or an empty {@link Map}
	 * @throws GraphQLRequestExecutionException
	 *             When an error occurs during the request execution, typically a network error, an error from the
	 *             GraphQL server or if the server response can't be parsed
	 */
## Note: we must use the ${query.type.classSimpleName}, as when the GraphQL schema uses request that return the query type, and 
## the query type object is in a separate package (plugin parameter separateUtilityClasses), then there is a conflict between 
## the current name and the query type object: they have the same name, but are in different packages 	#if(${field.type.scalar}) @GraphQLScalar #else @GraphQLNonScalar #end(fieldName = "${field.name}", graphQLTypeSimpleName = "${field.graphQLTypeSimpleName}", javaClass = ${field.type.classFullName}.class)
#if(${field.type.scalar}) @GraphQLScalar #else @GraphQLNonScalar #end(fieldName = "${field.name}", graphQLTypeSimpleName = "${field.graphQLTypeSimpleName}", javaClass = ${field.type.classFullName}.class)
	public ${field.javaTypeFullClassname} ${field.javaName}WithBindValues(
			ObjectResponse objectResponse,
#inputParams()
			Map<String, Object> parameters)
			throws GraphQLRequestExecutionException  {
		if (logger.isTraceEnabled()) {
			logger.trace("Executing ${object.requestType} '${field.name}' with parameters: #foreach ($inputParameter in $field.inputParameters){}#if($foreach.hasNext),#end #end"#foreach ($inputParameter in $field.inputParameters), ${inputParameter.javaName}#end);
		} else if (logger.isDebugEnabled()) {
			logger.debug("Executing ${object.requestType} '${field.name}'");
		}
	
		// Given values for the BindVariables
		parameters = (parameters != null) ? parameters : new HashMap<>();
#foreach ($inputParameter in $field.inputParameters)
		parameters.put("${object.camelCaseName}${field.pascalCaseName}${inputParameter.pascalCaseName}", ${inputParameter.javaName});
#end

#if(${configuration.separateUtilityClasses})
		${configuration.packageName}.${field.owningType.classSimpleName} ret 
			= graphQLConfiguration${springBeanSuffix}.getQueryExecutor().execute(objectResponse, parameters, #if(${configuration.separateUtilityClasses})${configuration.packageName}.#end${field.owningType.classSimpleName}.class);
#else
		${field.owningType.classSimpleName} ret = graphQLConfiguration${springBeanSuffix}.getQueryExecutor().execute(objectResponse, parameters, ${field.owningType.classSimpleName}.class);
#end
		
		return ret.get${field.pascalCaseName}();
	}

	/**
#foreach ($comment in $field.comments)
	 * ${field.content}
#end
#if ($field.comments.size() > 0)
	 * <BR/>
#end
	 * This method is expected by the graphql-java framework. It will be called when this query is called. It offers a
	 * logging of the call (if in debug mode), or of the call and its parameters (if in trace mode).<BR/>
	 * This method is valid for queries/mutations/subscriptions which don't have bind variables, as there is no
	 * <I>parameters</I> argument to pass the list of values.<BR/>
	 * Here is a sample:
	 * 
	 * <PRE>
	 * ObjectResponse response;
	 * public void setup() {
	 * 	// Preparation of the query
	 * 	response = queryType.getBoardsResponseBuilder()
	 * 			.withQueryResponseDef("{id name publiclyAvailable topics(since:?sinceParam){id}}").build();
	 * }
	 * 
	 * public void doTheJob() {
	 * ..
	 * // This will set the value sinceValue to the sinceParam field parameter
	 * ${field.javaType} ret = queryType.${field.javaName}(response#inputValues, "sinceParam", sinceValue);
	 * ...
	 * }
	 * </PRE>
#if($graphqlUtils.isJavaReservedWords(${field.name}))
	 * This method name is prefixed by ${underscore}, as ${field.name} is a java reserved keyword. 
#end
	 * 
	 * @param objectResponse
	 *            The definition of the response format, that describes what the GraphQL server is expected to return
#foreach ($inputParameter in $field.inputParameters)
	 * @param ${inputParameter.name} Parameter for the ${field.name} field of ${object.name}, as defined in the GraphQL schema
#end
	 * @param paramsAndValues
	 *            This parameter contains all the name and values for the Bind Variables defined in the objectResponse
	 *            parameter, that must be sent to the server. Optional parameter may not have a value. They will be
	 *            ignored and not sent to the server. Mandatory parameter must be provided in this argument.<BR/>
	 *            This parameter contains an even number of parameters: it must be a series of name and values :
	 *            (paramName1, paramValue1, paramName2, paramValue2...)
	 * @throws GraphQLRequestExecutionException
	 *             When an error occurs during the request execution, typically a network error, an error from the
	 *             GraphQL server or if the server response can't be parsed
	 */
## Note: we must use the ${query.type.classSimpleName}, as when the GraphQL schema uses request that return the query type, and 
## the query type object is in a separate package (plugin parameter separateUtilityClasses), then there is a conflict between 
## the current name and the query type object: they have the same name, but are in different packages 	#if(${field.type.scalar}) @GraphQLScalar #else @GraphQLNonScalar #end(fieldName = "${field.name}", graphQLTypeSimpleName = "${field.graphQLTypeSimpleName}", javaClass = ${field.type.classFullName}.class)
#if(${field.type.scalar}) @GraphQLScalar #else @GraphQLNonScalar #end(fieldName = "${field.name}", graphQLTypeSimpleName = "${field.graphQLTypeSimpleName}", javaClass = ${field.type.classFullName}.class)
	public ${field.javaTypeFullClassname} ${field.javaName}(
			ObjectResponse objectResponse,
#inputParams()
			Object... paramsAndValues)
			throws GraphQLRequestExecutionException  {
		if (logger.isTraceEnabled()) {
			StringBuffer sb = new StringBuffer();
			sb.append("Executing ${object.requestType} '${field.name}' with bind variables: ");
			boolean addComma = false;
			for (Object o : paramsAndValues) {
				if (o != null) {
					sb.append(o.toString());
					if (addComma)
						sb.append(", ");
					addComma = true;
				}
			}
			logger.trace(sb.toString());
		} else if (logger.isDebugEnabled()) {
			logger.debug("Executing ${object.requestType} '${field.name}' (with bind variables)");
		}

		Map<String, Object> parameters = graphqlClientUtils.generatesBindVariableValuesMap(paramsAndValues);
#foreach ($inputParameter in $field.inputParameters)
		parameters.put("${object.camelCaseName}${field.pascalCaseName}${inputParameter.pascalCaseName}", ${inputParameter.javaName});
#end
		
#if (${configuration.separateUtilityClasses})
		${configuration.packageName}.${field.owningType.classSimpleName} ret 
			= graphQLConfiguration${springBeanSuffix}.getQueryExecutor().execute(objectResponse, parameters, #if(${configuration.separateUtilityClasses})${configuration.packageName}.#end${field.owningType.classSimpleName}.class);
#else
		${field.owningType.classSimpleName} ret = graphQLConfiguration${springBeanSuffix}.getQueryExecutor().execute(objectResponse, parameters, ${field.owningType.classSimpleName}.class);
#end

		return ret.get${field.pascalCaseName}();
	}

	/**
#foreach ($comment in $field.comments)
	 * ${field.content}
#end
#if ($field.comments.size() > 0)
	 * <BR/>
#end
	 * Get the {@link com.graphql_java_generator.client.request.Builder} for the ${field.type.classSimpleName}, as expected by the ${field.name} query.
	 * 
	 * @return
	 * @throws GraphQLRequestPreparationException
	 */
	public com.graphql_java_generator.client.request.Builder get${field.pascalCaseName}ResponseBuilder() throws GraphQLRequestPreparationException {
		return new com.graphql_java_generator.client.request.Builder(GraphQLRequest.class, "${field.name}", RequestType.${object.requestType}
#foreach ($inputParameter in $field.inputParameters)
			, InputParameter.newBindParameter("$springBeanSuffix", "${inputParameter.name}","${object.camelCaseName}${field.pascalCaseName}${inputParameter.pascalCaseName}",#if(${inputParameter.fieldTypeAST.mandatory}) InputParameterType.MANDATORY#else InputParameterType.OPTIONAL#end, "${inputParameter.graphQLTypeSimpleName}", ${inputParameter.fieldTypeAST.mandatory}, ${inputParameter.fieldTypeAST.listDepth}, ${inputParameter.fieldTypeAST.itemMandatory})
#end
			);
	}


	/**
#foreach ($comment in $field.comments)
	 * ${field.content}
#end
#if ($field.comments.size() > 0)
	 * <BR/>
#end
	 * Get the {@link GraphQLRequest} for the ${field.name} $type, created with the given Partial request.
	 * 
	 * @param partialRequest
	 * 				The Partial GraphQLRequest, as explained in the 
	 * 				<A HREF="https://graphql-maven-plugin-project.graphql-java-generator.com/client.html">plugin client documentation</A> 
	 * @return
	 * @throws GraphQLRequestPreparationException
	 */
	public GraphQLRequest get${field.pascalCaseName}GraphQLRequest(String partialRequest) throws GraphQLRequestPreparationException {
		return new GraphQLRequest(partialRequest, RequestType.${object.requestType}, "${field.name}"
#foreach ($inputParameter in $field.inputParameters)  ## Here, inputParameter is an instance of Field
		, InputParameter.newBindParameter("$springBeanSuffix", "${inputParameter.name}","${object.camelCaseName}${field.pascalCaseName}${inputParameter.pascalCaseName}",#if(${inputParameter.fieldTypeAST.mandatory}) InputParameterType.MANDATORY#else InputParameterType.OPTIONAL#end, "${inputParameter.graphQLTypeSimpleName}", ${inputParameter.fieldTypeAST.mandatory}, ${inputParameter.fieldTypeAST.listDepth}, ${inputParameter.fieldTypeAST.itemMandatory})
#end
		);
	}
	
#end
}
