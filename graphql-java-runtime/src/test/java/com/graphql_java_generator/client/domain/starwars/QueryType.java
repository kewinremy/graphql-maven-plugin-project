package com.graphql_java_generator.client.domain.starwars;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.graphql_java_generator.annotation.GraphQLNonScalar;
import com.graphql_java_generator.annotation.GraphQLQuery;
import com.graphql_java_generator.client.QueryExecutor;
import com.graphql_java_generator.client.QueryExecutorImpl;
import com.graphql_java_generator.client.request.Builder;
import com.graphql_java_generator.client.request.InputParameter;
import com.graphql_java_generator.client.request.ObjectResponse;
import com.graphql_java_generator.client.response.GraphQLRequestExecutionException;
import com.graphql_java_generator.client.response.GraphQLRequestPreparationException;

/**
 * @author generated by graphql-java-generator
 * @See https://github.com/graphql-java-generator/graphql-java-generator
 */
public class QueryType {

	/** Logger for this class */
	private static Logger logger = LogManager.getLogger();

	final QueryExecutor executor;

	/**
	 * This constructor expects the URI of the GraphQL server. This constructor works only for http servers, not for
	 * https ones.<BR/>
	 * For example: http://my.server.com/graphql
	 * 
	 * @param graphqlEndpoint
	 *            the http URI for the GraphQL endpoint
	 */
	public QueryType(String graphqlEndpoint) {
		this.executor = new QueryExecutorImpl(graphqlEndpoint);
	}

	/**
	 * This constructor expects the URI of the GraphQL server. This constructor works only for http servers, not for
	 * https ones.<BR/>
	 * For example: https://my.server.com/graphql
	 * 
	 * @param graphqlEndpoint
	 *            the https URI for the GraphQL endpoint
	 * @param sslContext
	 * @param hostnameVerifier
	 */
	public QueryType(String graphqlEndpoint, SSLContext sslContext, HostnameVerifier hostnameVerifier) {
		this.executor = new QueryExecutorImpl(graphqlEndpoint, sslContext, hostnameVerifier);
	}

	/**
	 * This method is expected by the graphql-java framework. It will be called when this query is called. It offers a
	 * logging of the call (if in debug mode), or of the call and its parameters (if in trace mode).<BR/>
	 * This method takes care of writting the query name, and the parameter(s) for the query. The given queryResponseDef
	 * describes the format of the response of the server response, that is the expected fields of the {@link Character}
	 * GraphQL type. It can be something like "{ id name }", if you want these fields of this type. Please take a look
	 * at the StarWars, Forum and other samples for more complex queries.
	 * 
	 * @param queryResponseDef
	 *            The response definition of the query, in the native GraphQL format (see here above)
	 * @param episode
	 *            Parameter 1 of this query
	 * @throws IOException
	 * @throws GraphQLRequestPreparationException
	 *             When an error occurs during the request preparation, typically when building the
	 *             {@link ObjectResponse}
	 * @throws GraphQLRequestExecutionException
	 *             When an error occurs during the request execution, typically a network error, an error from the
	 *             GraphQL server or if the server response can't be parsed
	 */
	@GraphQLNonScalar(graphqlType = Character.class)
	@GraphQLQuery
	public Character hero(String queryResponseDef, Episode episode, Map<String, Object> parameters)
			throws GraphQLRequestExecutionException, GraphQLRequestPreparationException {
		logger.debug("Executing of query 'hero' in query mode: {} ", queryResponseDef);
		ObjectResponse objectResponse = getHeroResponseBuilder().withQueryResponseDef(queryResponseDef).build();
		return hero(objectResponse, episode, parameters);
	}

	@GraphQLNonScalar(graphqlType = Character.class)
	@GraphQLQuery
	public Character hero(String queryResponseDef, Episode episode)
			throws GraphQLRequestExecutionException, GraphQLRequestPreparationException {
		return hero(queryResponseDef, episode, null);
	}

	/**
	 * This method is expected by the graphql-java framework. It will be called when this query is called. It offers a
	 * logging of the call (if in debug mode), or of the call and its parameters (if in trace mode).
	 * 
	 * @param objectResponse
	 *            The definition of the response format, that describes what the GraphQL server is expected to return
	 * @param episode
	 * @throws IOException
	 * @throws GraphQLRequestExecutionException
	 *             When an error occurs during the request execution, typically a network error, an error from the
	 *             GraphQL server or if the server response can't be parsed
	 */
	@GraphQLNonScalar(graphqlType = Character.class)
	@GraphQLQuery
	public Character hero(ObjectResponse objectResponse, Episode episode, Map<String, Object> parameters)
			throws GraphQLRequestExecutionException {
		if (logger.isTraceEnabled()) {
			logger.trace("Executing of query 'hero' with parameters: {} ", episode);
		} else if (logger.isDebugEnabled()) {
			logger.debug("Executing of query 'hero'");
		}

		// Given values for the BindVariables
		parameters = (parameters != null) ? parameters : new HashMap<>();
		parameters.put("queryTypeHeroEpisode", episode);

		if (!Character.class.equals(objectResponse.getFieldClass())) {
			throw new GraphQLRequestExecutionException("The ObjectResponse parameter should be an instance of "
					+ Character.class + ", but is an instance of " + objectResponse.getClass().getName());
		}

		QueryTypeHero ret = executor.execute("query", objectResponse, parameters, QueryTypeHero.class);

		return ret.hero;
	}

	/**
	 * Get the {@link ObjectResponse.Builder} for the Character, as expected by the hero query.
	 * 
	 * @return
	 * @throws GraphQLRequestPreparationException
	 */
	public Builder getHeroResponseBuilder() throws GraphQLRequestPreparationException {
		Builder builder = new Builder(getClass(), "hero");
		builder.withInputParameter(InputParameter.newBindParameter("episode", "queryTypeEpisode"));
		return builder;
	}

	/**
	 * This method is expected by the graphql-java framework. It will be called when this query is called. It offers a
	 * logging of the call (if in debug mode), or of the call and its parameters (if in trace mode).<BR/>
	 * This method takes care of writing the query name, and the parameter(s) for the query. The given queryResponseDef
	 * describes the format of the response of the server response, that is the expected fields of the {@link Character}
	 * GraphQL type. It can be something like "{ id name }", if you want these fields of this type. Please take a look
	 * at the StarWars, Forum and other samples for more complex queries.
	 * 
	 * @param queryResponseDef
	 *            The response definition of the query, in the native GraphQL format (see here above)
	 * @param episode
	 *            Parameter 1 of this query
	 * @throws IOException
	 * @throws GraphQLRequestPreparationException
	 *             When an error occurs during the request preparation, typically when building the
	 *             {@link ObjectResponse}
	 * @throws GraphQLRequestExecutionException
	 *             When an error occurs during the request execution, typically a network error, an error from the
	 *             GraphQL server or if the server response can't be parsed
	 */
	@GraphQLNonScalar(graphqlType = Character.class)
	@GraphQLQuery
	public List<Character> characters(String queryResponseDef, Episode episode, Map<String, Object> parameters)
			throws GraphQLRequestExecutionException, GraphQLRequestPreparationException {
		logger.debug("Executing of query 'characters' in query mode: {} ", queryResponseDef);
		ObjectResponse objectResponse = getCharactersResponseBuilder().withQueryResponseDef(queryResponseDef).build();
		return characters(objectResponse, episode, parameters);
	}

	/**
	 * This method is expected by the graphql-java framework. It will be called when this query is called. It offers a
	 * logging of the call (if in debug mode), or of the call and its parameters (if in trace mode).
	 * 
	 * @param objectResponse
	 *            The definition of the response format, that describes what the GraphQL server is expected to return
	 * @param episode
	 * @throws IOException
	 * @throws GraphQLRequestExecutionException
	 *             When an error occurs during the request execution, typically a network error, an error from the
	 *             GraphQL server or if the server response can't be parsed
	 */
	@GraphQLNonScalar(graphqlType = Character.class)
	@GraphQLQuery
	public List<Character> characters(ObjectResponse objectResponse, Episode episode, Map<String, Object> parameters)
			throws GraphQLRequestExecutionException {
		if (logger.isTraceEnabled()) {
			logger.trace("Executing of query 'characters' with parameters: {} ", episode);
		} else if (logger.isDebugEnabled()) {
			logger.debug("Executing of query 'characters'");
		}

		// Given values for the BindVariables
		parameters = (parameters != null) ? parameters : new HashMap<>();
		parameters.put("queryTypeHeroEpisode", episode);

		if (!Character.class.equals(objectResponse.getFieldClass())) {
			throw new GraphQLRequestExecutionException("The ObjectResponse parameter should be an instance of "
					+ Character.class + ", but is an instance of " + objectResponse.getClass().getName());
		}

		QueryTypeCharacters ret = executor.execute("query", objectResponse, parameters, QueryTypeCharacters.class);

		return ret.characters;
	}

	/**
	 * Get the {@link ObjectResponse.Builder} for the Character, as expected by the characters query.
	 * 
	 * @return
	 * @throws GraphQLRequestPreparationException
	 */
	public Builder getCharactersResponseBuilder() throws GraphQLRequestPreparationException {
		return new Builder(getClass(), "characters");
	}

	/**
	 * This method is expected by the graphql-java framework. It will be called when this query is called. It offers a
	 * logging of the call (if in debug mode), or of the call and its parameters (if in trace mode).<BR/>
	 * This method takes care of writting the query name, and the parameter(s) for the query. The given queryResponseDef
	 * describes the format of the response of the server response, that is the expected fields of the {@link Human}
	 * GraphQL type. It can be something like "{ id name }", if you want these fields of this type. Please take a look
	 * at the StarWars, Forum and other samples for more complex queries.
	 * 
	 * @param queryResponseDef
	 *            The response definition of the query, in the native GraphQL format (see here above)
	 * @param episode
	 *            Parameter 1 of this query
	 * @throws IOException
	 * @throws GraphQLRequestPreparationException
	 *             When an error occurs during the request preparation, typically when building the
	 *             {@link ObjectResponse}
	 * @throws GraphQLRequestExecutionException
	 *             When an error occurs during the request execution, typically a network error, an error from the
	 *             GraphQL server or if the server response can't be parsed
	 */
	@GraphQLNonScalar(graphqlType = Human.class)
	@GraphQLQuery
	public Human human(String queryResponseDef, String id, Map<String, Object> parameters)
			throws GraphQLRequestExecutionException, GraphQLRequestPreparationException {
		logger.debug("Executing of query 'human' in query mode: {} ", queryResponseDef);
		ObjectResponse objectResponse = getHumanResponseBuilder().withQueryResponseDef(queryResponseDef).build();
		return human(objectResponse, id, parameters);
	}

	/**
	 * This method is expected by the graphql-java framework. It will be called when this query is called. It offers a
	 * logging of the call (if in debug mode), or of the call and its parameters (if in trace mode).
	 * 
	 * @param objectResponse
	 *            The definition of the response format, that describes what the GraphQL server is expected to return
	 * @param episode
	 * @throws IOException
	 * @throws GraphQLRequestExecutionException
	 *             When an error occurs during the request execution, typically a network error, an error from the
	 *             GraphQL server or if the server response can't be parsed
	 */
	@GraphQLNonScalar(graphqlType = Human.class)
	@GraphQLQuery
	public Human human(ObjectResponse objectResponse, String id, Map<String, Object> parameters)
			throws GraphQLRequestExecutionException {
		if (logger.isTraceEnabled()) {
			logger.trace("Executing of query 'human' with parameters: {} ", id);
		} else if (logger.isDebugEnabled()) {
			logger.debug("Executing of query 'human'");
		}

		// Given values for the BindVariables
		parameters = (parameters != null) ? parameters : new HashMap<>();
		parameters.put("queryTypeHumanId", id);

		if (!Human.class.equals(objectResponse.getFieldClass())) {
			throw new GraphQLRequestExecutionException("The ObjectResponse parameter should be an instance of "
					+ Human.class + ", but is an instance of " + objectResponse.getClass().getName());
		}

		QueryTypeHuman ret = executor.execute("query", objectResponse, parameters, QueryTypeHuman.class);

		return ret.human;
	}

	/**
	 * Get the {@link ObjectResponse.Builder} for the Human, as expected by the human query.
	 * 
	 * @return
	 * @throws GraphQLRequestPreparationException
	 */
	public Builder getHumanResponseBuilder() throws GraphQLRequestPreparationException {
		return new Builder(getClass(), "human");
	}

	/**
	 * This method is expected by the graphql-java framework. It will be called when this query is called. It offers a
	 * logging of the call (if in debug mode), or of the call and its parameters (if in trace mode).<BR/>
	 * This method takes care of writting the query name, and the parameter(s) for the query. The given queryResponseDef
	 * describes the format of the response of the server response, that is the expected fields of the {@link Droid}
	 * GraphQL type. It can be something like "{ id name }", if you want these fields of this type. Please take a look
	 * at the StarWars, Forum and other samples for more complex queries.
	 * 
	 * @param queryResponseDef
	 *            The response definition of the query, in the native GraphQL format (see here above)
	 * @param episode
	 *            Parameter 1 of this query
	 * @throws IOException
	 * @throws GraphQLRequestPreparationException
	 *             When an error occurs during the request preparation, typically when building the
	 *             {@link ObjectResponse}
	 * @throws GraphQLRequestExecutionException
	 *             When an error occurs during the request execution, typically a network error, an error from the
	 *             GraphQL server or if the server response can't be parsed
	 */
	@GraphQLNonScalar(graphqlType = Droid.class)
	@GraphQLQuery
	public Droid droid(String queryResponseDef, String id, Map<String, Object> parameters)
			throws GraphQLRequestExecutionException, GraphQLRequestPreparationException {
		logger.debug("Executing of query 'droid' in query mode: {} ", queryResponseDef);
		ObjectResponse objectResponse = getDroidResponseBuilder().withQueryResponseDef(queryResponseDef).build();
		return droid(objectResponse, id, parameters);
	}

	/**
	 * This method is expected by the graphql-java framework. It will be called when this query is called. It offers a
	 * logging of the call (if in debug mode), or of the call and its parameters (if in trace mode).
	 * 
	 * @param objectResponse
	 *            The definition of the response format, that describes what the GraphQL server is expected to return
	 * @param episode
	 * @throws IOException
	 * @throws GraphQLRequestExecutionException
	 *             When an error occurs during the request execution, typically a network error, an error from the
	 *             GraphQL server or if the server response can't be parsed
	 */
	@GraphQLNonScalar(graphqlType = Droid.class)
	@GraphQLQuery
	public Droid droid(ObjectResponse objectResponse, String id, Map<String, Object> parameters)
			throws GraphQLRequestExecutionException {
		if (logger.isTraceEnabled()) {
			logger.trace("Executing of query 'droid' with parameters: {} ", id);
		} else if (logger.isDebugEnabled()) {
			logger.debug("Executing of query 'droid'");
		}

		// Given values for the BindVariables
		parameters = (parameters != null) ? parameters : new HashMap<>();
		parameters.put("queryTypeDroidId", id);

		if (!Droid.class.equals(objectResponse.getFieldClass())) {
			throw new GraphQLRequestExecutionException("The ObjectResponse parameter should be an instance of "
					+ Droid.class + ", but is an instance of " + objectResponse.getClass().getName());
		}

		QueryTypeDroid ret = executor.execute("query", objectResponse, parameters, QueryTypeDroid.class);

		return ret.droid;
	}

	/**
	 * Get the {@link ObjectResponse.Builder} for the Droid, as expected by the droid query.
	 * 
	 * @return
	 * @throws GraphQLRequestPreparationException
	 */
	public Builder getDroidResponseBuilder() throws GraphQLRequestPreparationException {
		return new Builder(getClass(), "droid");
	}

}
