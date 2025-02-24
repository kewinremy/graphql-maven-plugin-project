/** Generated by the default template from graphql-java-generator */
package com.graphql_java_generator.domain.client.starwars;

import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.ws.rs.client.Client;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.graphql_java_generator.annotation.GraphQLInputParameters;
import com.graphql_java_generator.annotation.GraphQLNonScalar;
import com.graphql_java_generator.annotation.GraphQLObjectType;
import com.graphql_java_generator.annotation.GraphQLQuery;
import com.graphql_java_generator.annotation.GraphQLScalar;
import com.graphql_java_generator.annotation.RequestType;
import com.graphql_java_generator.client.GraphQLObjectMapper;
import com.graphql_java_generator.client.request.ObjectResponse;
import com.graphql_java_generator.exception.GraphQLRequestExecutionException;
import com.graphql_java_generator.exception.GraphQLRequestPreparationException;

/**
 * This class contains the response for a full request. See the
 * <A HREF="https://graphql-maven-plugin-project.graphql-java-generator.com/exec_graphql_requests.html">plugin web
 * site</A> for more information on full and partial requests.<BR/>
 * It also allows access to the _extensions_ part of the response. Take a look at the
 * <A HRE="https://spec.graphql.org/June2018/#sec-Response">GraphQL spec</A> for more information on this.
 * 
 * @author generated by graphql-java-generator
 * @see <a href=
 *      "https://github.com/graphql-java-generator/graphql-java-generator">https://github.com/graphql-java-generator/graphql-java-generator</a>
 */
@GraphQLQuery(name = "MutationType", type = RequestType.mutation)
@GraphQLObjectType("MutationType")
public class MutationType extends MutationTypeExecutor
		implements com.graphql_java_generator.client.GraphQLRequestObject {

	private GraphQLObjectMapper extensionMapper = null;
	private JsonNode extensions;
	private Map<String, JsonNode> extensionsAsMap = null;

	/**
	 * This map contains the deserialiazed values for the alias, as parsed from the json response from the GraphQL
	 * server. The key is the alias name, the value is the deserialiazed value (taking into account custom scalars,
	 * lists, ...)
	 */
	@com.graphql_java_generator.annotation.GraphQLIgnore
	Map<String, Object> aliasValues = new HashMap<>();

	public MutationType() {
		// No action
	}

	@JsonProperty("createHuman")
	@GraphQLInputParameters(names = { "name", "homePlanet" }, types = { "String", "String" }, mandatories = { true,
			false }, listDepths = { 0, 0 }, itemsMandatory = { false, false })
	@GraphQLNonScalar(fieldName = "createHuman", graphQLTypeSimpleName = "Human", javaClass = Human.class)
	Human createHuman;

	@JsonProperty("addFriend")
	@GraphQLInputParameters(names = { "idCharacter", "idNewFriend" }, types = { "String", "String" }, mandatories = {
			true, true }, listDepths = { 0, 0 }, itemsMandatory = { false, false })
	@GraphQLNonScalar(fieldName = "addFriend", graphQLTypeSimpleName = "Character", javaClass = Character.class)
	Character addFriend;

	@JsonProperty("__typename")
	@GraphQLScalar(fieldName = "__typename", graphQLTypeSimpleName = "String", javaClass = String.class)
	String __typename;

	public void setCreateHuman(Human createHuman) {
		this.createHuman = createHuman;
	}

	public Human getCreateHuman() {
		return createHuman;
	}

	public void setAddFriend(Character addFriend) {
		this.addFriend = addFriend;
	}

	public Character getAddFriend() {
		return addFriend;
	}

	public void set__typename(String __typename) {
		this.__typename = __typename;
	}

	public String get__typename() {
		return __typename;
	}

	/**
	 * This method is called during the json deserialization process, by the {@link GraphQLObjectMapper}, each time an
	 * alias value is read from the json.
	 * 
	 * @param aliasName
	 * @param aliasDeserializedValue
	 */
	public void setAliasValue(String aliasName, Object aliasDeserializedValue) {
		aliasValues.put(aliasName, aliasDeserializedValue);
	}

	/**
	 * Retrieves the value for the given alias, as it has been received for this object in the GraphQL response. <BR/>
	 * This method <B>should not be used for Custom Scalars</B>, as the parser doesn't know if this alias is a custom
	 * scalar, and which custom scalar to use at deserialization time. In most case, a value will then be provided by
	 * this method with a basis json deserialization, but this value won't be the proper custom scalar value.
	 * 
	 * @param alias
	 * @return
	 * @throws GraphQLRequestExecutionException
	 *             If the value can not be parsed
	 */
	public Object getAliasValue(String alias) throws GraphQLRequestExecutionException {
		Object value = aliasValues.get(alias);
		if (value instanceof GraphQLRequestExecutionException)
			throw (GraphQLRequestExecutionException) value;
		else
			return value;
	}

	@Override
	public String toString() {
		return "MutationType {" + "createHuman: " + createHuman + ", " + "addFriend: " + addFriend + ", "
				+ "__typename: " + __typename + "}";
	}

	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder
	 */
	public static class Builder {
		private Human createHuman;
		private Character addFriend;

		public Builder withCreateHuman(Human createHuman) {
			this.createHuman = createHuman;
			return this;
		}

		public Builder withAddFriend(Character addFriend) {
			this.addFriend = addFriend;
			return this;
		}

		public MutationType build() {
			MutationType _object = new MutationType();
			_object.setCreateHuman(createHuman);
			_object.setAddFriend(addFriend);
			_object.set__typename("MutationType");
			return _object;
		}
	}

	/** {@inheritDoc} */
	public MutationType(String graphqlEndpoint) {
		super(graphqlEndpoint);
	}

	/** {@inheritDoc} */
	public MutationType(String graphqlEndpoint, SSLContext sslContext, HostnameVerifier hostnameVerifier) {
		super(graphqlEndpoint, sslContext, hostnameVerifier);
	}

	/** {@inheritDoc} */
	public MutationType(String graphqlEndpoint, Client client) {
		super(graphqlEndpoint, client);
	}

	private GraphQLObjectMapper getExtensionMapper() {
		if (extensionMapper == null) {
			extensionMapper = new GraphQLObjectMapper(
					"org.graphql.mavenplugin.junittest.starwars_client_springconfiguration", null);
		}
		return extensionMapper;
	}

	public JsonNode getExtensions() {
		return extensions;
	}

	@Override
	public void setExtensions(JsonNode extensions) {
		this.extensions = extensions;
	}

	/**
	 * Returns the extensions as a map. The values can't be deserialized, as their type is unknown.
	 * 
	 * @return
	 */
	public Map<String, JsonNode> getExtensionsAsMap() {
		if (extensionsAsMap == null) {
			extensionsAsMap = getExtensionMapper().convertValue(extensions, new TypeReference<Map<String, JsonNode>>() {
			});
		}
		return extensionsAsMap;
	}

	/**
	 * Parse the value for the given _key_, as found in the <I>extensions</I> field of the GraphQL server's response,
	 * into the given _t_ class.
	 * 
	 * @param <T>
	 * @param key
	 * @param t
	 * @return null if the key is not in the <I>extensions</I> map. Otherwise: the value for this _key_, as a _t_
	 *         instance
	 * @throws JsonProcessingException
	 *             When there is an error when converting the key's value into the _t_ class
	 */
	public <T> T getExtensionsField(String key, Class<T> t) throws JsonProcessingException {
		JsonNode node = getExtensionsAsMap().get(key);
		return (node == null) ? null : getExtensionMapper().treeToValue(node, t);
	}

	/**
	 * This method is deprecated: please use {@link MutationTypeExecutor} class instead of this class, to execute this
	 * method. It is maintained to keep existing code compatible with the generated code. It will be removed in 2.0
	 * version.
	 */
	@Override
	@Deprecated
	public MutationTypeResponse execWithBindValues(String queryResponseDef, Map<String, Object> parameters)
			throws GraphQLRequestExecutionException, GraphQLRequestPreparationException {
		return super.exec(queryResponseDef, parameters);
	}

	/**
	 * This method is deprecated: please use {@link MutationTypeExecutor} class instead of this class, to execute this
	 * method. It is maintained to keep existing code compatible with the generated code. It will be removed in 2.0
	 * version.
	 */
	@Override
	@Deprecated
	public MutationTypeResponse exec(String queryResponseDef, Object... paramsAndValues)
			throws GraphQLRequestExecutionException, GraphQLRequestPreparationException {
		return super.exec(queryResponseDef, paramsAndValues);
	}

	/**
	 * This method is deprecated: please use {@link MutationTypeExecutor} class instead of this class, to execute this
	 * method. It is maintained to keep existing code compatible with the generated code. It will be removed in 2.0
	 * version.
	 */
	@Override
	@Deprecated
	public MutationTypeResponse execWithBindValues(ObjectResponse objectResponse, Map<String, Object> parameters)
			throws GraphQLRequestExecutionException {
		return super.execWithBindValues(objectResponse, parameters);
	}

	/**
	 * This method is deprecated: please use {@link MutationTypeExecutor} class instead of this class, to execute this
	 * method. It is maintained to keep existing code compatible with the generated code. It will be removed in 2.0
	 * version.
	 */
	@Override
	@Deprecated
	public MutationTypeResponse exec(ObjectResponse objectResponse, Object... paramsAndValues)
			throws GraphQLRequestExecutionException {
		return super.exec(objectResponse, paramsAndValues);
	}

	/**
	 * This method is deprecated: please use {@link MutationTypeExecutor} class instead of this class, to execute this
	 * method. It is maintained to keep existing code compatible with the generated code. It will be removed in 2.0
	 * version.
	 */
	@Override
	@Deprecated
	public com.graphql_java_generator.client.request.Builder getResponseBuilder()
			throws GraphQLRequestPreparationException {
		return super.getResponseBuilder();
	}

	/**
	 * This method is deprecated: please use {@link MutationTypeExecutor} class instead of this class, to execute this
	 * method. It is maintained to keep existing code compatible with the generated code. It will be removed in 2.0
	 * version.
	 */
	@Override
	@Deprecated
	public GraphQLRequest getGraphQLRequest(String fullRequest) throws GraphQLRequestPreparationException {
		return super.getGraphQLRequest(fullRequest);
	}

	/**
	 * This method is deprecated: please use {@link MutationTypeExecutor} class instead of this class, to execute this
	 * method. It is maintained to keep existing code compatible with the generated code. It will be removed in 2.0
	 * version.
	 */
	@Override
	@Deprecated
	@GraphQLNonScalar(fieldName = "createHuman", graphQLTypeSimpleName = "Human", javaClass = Human.class)
	public Human createHumanWithBindValues(String queryResponseDef, String name, String homePlanet,
			Map<String, Object> parameters)
			throws GraphQLRequestExecutionException, GraphQLRequestPreparationException {
		return super.createHumanWithBindValues(queryResponseDef, name, homePlanet, parameters);
	}

	/**
	 * This method is deprecated: please use {@link MutationTypeExecutor} class instead of this class, to execute this
	 * method. It is maintained to keep existing code compatible with the generated code. It will be removed in 2.0
	 * version.
	 */
	@Override
	@Deprecated
	@GraphQLNonScalar(fieldName = "createHuman", graphQLTypeSimpleName = "Human", javaClass = Human.class)
	public Human createHuman(String queryResponseDef, String name, String homePlanet, Object... paramsAndValues)
			throws GraphQLRequestExecutionException, GraphQLRequestPreparationException {
		return super.createHuman(queryResponseDef, name, homePlanet, paramsAndValues);
	}

	/**
	 * This method is deprecated: please use {@link MutationTypeExecutor} class instead of this class, to execute this
	 * method. It is maintained to keep existing code compatible with the generated code. It will be removed in 2.0
	 * version.
	 */
	@Override
	@Deprecated
	@GraphQLNonScalar(fieldName = "createHuman", graphQLTypeSimpleName = "Human", javaClass = Human.class)
	public Human createHumanWithBindValues(ObjectResponse objectResponse, String name, String homePlanet,
			Map<String, Object> parameters) throws GraphQLRequestExecutionException {
		return super.createHumanWithBindValues(objectResponse, name, homePlanet, parameters);
	}

	/**
	 * This method is deprecated: please use {@link MutationTypeExecutor} class instead of this class, to execute this
	 * method. It is maintained to keep existing code compatible with the generated code. It will be removed in 2.0
	 * version.
	 */
	@Override
	@Deprecated
	@GraphQLNonScalar(fieldName = "createHuman", graphQLTypeSimpleName = "Human", javaClass = Human.class)
	public Human createHuman(ObjectResponse objectResponse, String name, String homePlanet, Object... paramsAndValues)
			throws GraphQLRequestExecutionException {
		return super.createHuman(objectResponse, name, homePlanet, paramsAndValues);
	}

	/**
	 * This method is deprecated: please use {@link MutationTypeExecutor} class instead of this class, to execute this
	 * method. It is maintained to keep existing code compatible with the generated code. It will be removed in 2.0
	 * version.
	 */
	@Override
	@Deprecated
	public com.graphql_java_generator.client.request.Builder getCreateHumanResponseBuilder()
			throws GraphQLRequestPreparationException {
		return super.getCreateHumanResponseBuilder();
	}

	/**
	 * This method is deprecated: please use {@link MutationTypeExecutor} class instead of this class, to execute this
	 * method. It is maintained to keep existing code compatible with the generated code. It will be removed in 2.0
	 * version.
	 */
	@Override
	@Deprecated
	public GraphQLRequest getCreateHumanGraphQLRequest(String partialRequest)
			throws GraphQLRequestPreparationException {
		return super.getCreateHumanGraphQLRequest(partialRequest);
	}

	/**
	 * This method is deprecated: please use {@link MutationTypeExecutor} class instead of this class, to execute this
	 * method. It is maintained to keep existing code compatible with the generated code. It will be removed in 2.0
	 * version.
	 */
	@Override
	@Deprecated
	@GraphQLNonScalar(fieldName = "addFriend", graphQLTypeSimpleName = "Character", javaClass = Character.class)
	public Character addFriendWithBindValues(String queryResponseDef, String idCharacter, String idNewFriend,
			Map<String, Object> parameters)
			throws GraphQLRequestExecutionException, GraphQLRequestPreparationException {
		return super.addFriendWithBindValues(queryResponseDef, idCharacter, idNewFriend, parameters);
	}

	/**
	 * This method is deprecated: please use {@link MutationTypeExecutor} class instead of this class, to execute this
	 * method. It is maintained to keep existing code compatible with the generated code. It will be removed in 2.0
	 * version.
	 */
	@Override
	@Deprecated
	@GraphQLNonScalar(fieldName = "addFriend", graphQLTypeSimpleName = "Character", javaClass = Character.class)
	public Character addFriend(String queryResponseDef, String idCharacter, String idNewFriend,
			Object... paramsAndValues) throws GraphQLRequestExecutionException, GraphQLRequestPreparationException {
		return super.addFriend(queryResponseDef, idCharacter, idNewFriend, paramsAndValues);
	}

	/**
	 * This method is deprecated: please use {@link MutationTypeExecutor} class instead of this class, to execute this
	 * method. It is maintained to keep existing code compatible with the generated code. It will be removed in 2.0
	 * version.
	 */
	@Override
	@Deprecated
	@GraphQLNonScalar(fieldName = "addFriend", graphQLTypeSimpleName = "Character", javaClass = Character.class)
	public Character addFriendWithBindValues(ObjectResponse objectResponse, String idCharacter, String idNewFriend,
			Map<String, Object> parameters) throws GraphQLRequestExecutionException {
		return super.addFriendWithBindValues(objectResponse, idCharacter, idNewFriend, parameters);
	}

	/**
	 * This method is deprecated: please use {@link MutationTypeExecutor} class instead of this class, to execute this
	 * method. It is maintained to keep existing code compatible with the generated code. It will be removed in 2.0
	 * version.
	 */
	@Override
	@Deprecated
	@GraphQLNonScalar(fieldName = "addFriend", graphQLTypeSimpleName = "Character", javaClass = Character.class)
	public Character addFriend(ObjectResponse objectResponse, String idCharacter, String idNewFriend,
			Object... paramsAndValues) throws GraphQLRequestExecutionException {
		return super.addFriend(objectResponse, idCharacter, idNewFriend, paramsAndValues);
	}

	/**
	 * This method is deprecated: please use {@link MutationTypeExecutor} class instead of this class, to execute this
	 * method. It is maintained to keep existing code compatible with the generated code. It will be removed in 2.0
	 * version.
	 */
	@Override
	@Deprecated
	public com.graphql_java_generator.client.request.Builder getAddFriendResponseBuilder()
			throws GraphQLRequestPreparationException {
		return super.getAddFriendResponseBuilder();
	}

	/**
	 * This method is deprecated: please use {@link MutationTypeExecutor} class instead of this class, to execute this
	 * method. It is maintained to keep existing code compatible with the generated code. It will be removed in 2.0
	 * version.
	 */
	@Override
	@Deprecated
	public GraphQLRequest getAddFriendGraphQLRequest(String partialRequest) throws GraphQLRequestPreparationException {
		return super.getAddFriendGraphQLRequest(partialRequest);
	}

	/**
	 * This method is deprecated: please use {@link MutationTypeExecutor} class instead of this class, to execute this
	 * method. It is maintained to keep existing code compatible with the generated code. It will be removed in 2.0
	 * version.
	 */
	@Override
	@Deprecated
	@GraphQLScalar(fieldName = "__typename", graphQLTypeSimpleName = "String", javaClass = String.class)
	public java.lang.String __typenameWithBindValues(String queryResponseDef, Map<String, Object> parameters)
			throws GraphQLRequestExecutionException, GraphQLRequestPreparationException {
		return super.__typenameWithBindValues(queryResponseDef, parameters);
	}

	/**
	 * This method is deprecated: please use {@link MutationTypeExecutor} class instead of this class, to execute this
	 * method. It is maintained to keep existing code compatible with the generated code. It will be removed in 2.0
	 * version.
	 */
	@Override
	@Deprecated
	@GraphQLScalar(fieldName = "__typename", graphQLTypeSimpleName = "String", javaClass = String.class)
	public java.lang.String __typename(String queryResponseDef, Object... paramsAndValues)
			throws GraphQLRequestExecutionException, GraphQLRequestPreparationException {
		return super.__typename(queryResponseDef, paramsAndValues);
	}

	/**
	 * This method is deprecated: please use {@link MutationTypeExecutor} class instead of this class, to execute this
	 * method. It is maintained to keep existing code compatible with the generated code. It will be removed in 2.0
	 * version.
	 */
	@Override
	@Deprecated
	@GraphQLScalar(fieldName = "__typename", graphQLTypeSimpleName = "String", javaClass = String.class)
	public java.lang.String __typenameWithBindValues(ObjectResponse objectResponse, Map<String, Object> parameters)
			throws GraphQLRequestExecutionException {
		return super.__typenameWithBindValues(objectResponse, parameters);
	}

	/**
	 * This method is deprecated: please use {@link MutationTypeExecutor} class instead of this class, to execute this
	 * method. It is maintained to keep existing code compatible with the generated code. It will be removed in 2.0
	 * version.
	 */
	@Override
	@Deprecated
	@GraphQLScalar(fieldName = "__typename", graphQLTypeSimpleName = "String", javaClass = String.class)
	public java.lang.String __typename(ObjectResponse objectResponse, Object... paramsAndValues)
			throws GraphQLRequestExecutionException {
		return super.__typename(objectResponse, paramsAndValues);
	}

	/**
	 * This method is deprecated: please use {@link MutationTypeExecutor} class instead of this class, to execute this
	 * method. It is maintained to keep existing code compatible with the generated code. It will be removed in 2.0
	 * version.
	 */
	@Override
	@Deprecated
	public com.graphql_java_generator.client.request.Builder get__typenameResponseBuilder()
			throws GraphQLRequestPreparationException {
		return super.get__typenameResponseBuilder();
	}

	/**
	 * This method is deprecated: please use {@link MutationTypeExecutor} class instead of this class, to execute this
	 * method. It is maintained to keep existing code compatible with the generated code. It will be removed in 2.0
	 * version.
	 */
	@Override
	@Deprecated
	public GraphQLRequest get__typenameGraphQLRequest(String partialRequest) throws GraphQLRequestPreparationException {
		return super.get__typenameGraphQLRequest(partialRequest);
	}

}
