package com.graphql_java_generator.client.domain.allGraphQLCases;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.graphql_java_generator.annotation.GraphQLInputType;
import com.graphql_java_generator.annotation.GraphQLNonScalar;
import com.graphql_java_generator.annotation.GraphQLScalar;

/**
 * @author generated by graphql-java-generator
 * @see <a href=
 *      "https://github.com/graphql-java-generator/graphql-java-generator">https://github.com/graphql-java-generator/graphql-java-generator</a>
 */
@GraphQLInputType("CharacterInput")
public class CharacterInput {

	@JsonProperty("name")
	@GraphQLScalar(graphQLTypeName = "String", javaClass = String.class)
	String name;

	@JsonProperty("friends")
	@JsonDeserialize(contentAs = CharacterInput.class)
	@GraphQLNonScalar(graphQLTypeName = "CharacterInput", javaClass = CharacterInput.class)
	List<CharacterInput> friends;

	@JsonProperty("appearsIn")
	@JsonDeserialize(contentAs = Episode.class)
	@GraphQLScalar(graphQLTypeName = "Episode", javaClass = Episode.class)
	List<Episode> appearsIn;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setFriends(List<CharacterInput> friends) {
		this.friends = friends;
	}

	public List<CharacterInput> getFriends() {
		return friends;
	}

	public void setAppearsIn(List<Episode> appearsIn) {
		this.appearsIn = appearsIn;
	}

	public List<Episode> getAppearsIn() {
		return appearsIn;
	}

	@Override
	public String toString() {
		return "CharacterInput {" + "name: " + name + ", " + "friends: " + friends + ", " + "appearsIn: " + appearsIn
				+ "}";
	}
}
