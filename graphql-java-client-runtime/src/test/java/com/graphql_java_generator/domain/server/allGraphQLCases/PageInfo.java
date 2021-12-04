/** Generated by the default template from graphql-java-generator */
package com.graphql_java_generator.domain.server.allGraphQLCases;

import java.util.HashMap;
import java.util.Map;


import com.graphql_java_generator.GraphQLField;
import com.graphql_java_generator.annotation.GraphQLInputParameters;
import com.graphql_java_generator.annotation.GraphQLObjectType;
import com.graphql_java_generator.annotation.GraphQLScalar;

/**
 *
 * @author generated by graphql-java-generator
 * @see <a href="https://github.com/graphql-java-generator/graphql-java-generator">https://github.com/graphql-java-generator/graphql-java-generator</a>
 */
@GraphQLObjectType("PageInfo")
public class PageInfo 
{

	public PageInfo(){
		// No action
	}

	@GraphQLScalar(fieldName = "hasNextPage", graphQLTypeSimpleName = "Boolean", javaClass = Boolean.class)
	Boolean hasNextPage;


	@GraphQLScalar(fieldName = "hasPreviousPage", graphQLTypeSimpleName = "Boolean", javaClass = Boolean.class)
	Boolean hasPreviousPage;


	@GraphQLScalar(fieldName = "startCursor", graphQLTypeSimpleName = "String", javaClass = String.class)
	String startCursor;


	@GraphQLScalar(fieldName = "endCursor", graphQLTypeSimpleName = "String", javaClass = String.class)
	String endCursor;



	public void setHasNextPage(Boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}

	public Boolean getHasNextPage() {
		return hasNextPage;
	}

	public void setHasPreviousPage(Boolean hasPreviousPage) {
		this.hasPreviousPage = hasPreviousPage;
	}

	public Boolean getHasPreviousPage() {
		return hasPreviousPage;
	}

	public void setStartCursor(String startCursor) {
		this.startCursor = startCursor;
	}

	public String getStartCursor() {
		return startCursor;
	}

	public void setEndCursor(String endCursor) {
		this.endCursor = endCursor;
	}

	public String getEndCursor() {
		return endCursor;
	}

    public String toString() {
        return "PageInfo {"
				+ "hasNextPage: " + hasNextPage
				+ ", "
				+ "hasPreviousPage: " + hasPreviousPage
				+ ", "
				+ "startCursor: " + startCursor
				+ ", "
				+ "endCursor: " + endCursor
        		+ "}";
    }

    /**
	 * Enum of field names
	 */
	 public static enum Field implements GraphQLField {
		HasNextPage("hasNextPage"),
		HasPreviousPage("hasPreviousPage"),
		StartCursor("startCursor"),
		EndCursor("endCursor");

		private String fieldName;

		Field(String fieldName) {
			this.fieldName = fieldName;
		}

		public String getFieldName() {
			return fieldName;
		}

		public Class<?> getGraphQLType() {
			return this.getClass().getDeclaringClass();
		}

	}

	public static Builder builder() {
			return new Builder();
		}



	/**
	 * Builder
	 */
	public static class Builder {
		private Boolean hasNextPage;
		private Boolean hasPreviousPage;
		private String startCursor;
		private String endCursor;


		public Builder withHasNextPage(Boolean hasNextPage) {
			this.hasNextPage = hasNextPage;
			return this;
		}
		public Builder withHasPreviousPage(Boolean hasPreviousPage) {
			this.hasPreviousPage = hasPreviousPage;
			return this;
		}
		public Builder withStartCursor(String startCursor) {
			this.startCursor = startCursor;
			return this;
		}
		public Builder withEndCursor(String endCursor) {
			this.endCursor = endCursor;
			return this;
		}

		public PageInfo build() {
			PageInfo _object = new PageInfo();
			_object.setHasNextPage(hasNextPage);
			_object.setHasPreviousPage(hasPreviousPage);
			_object.setStartCursor(startCursor);
			_object.setEndCursor(endCursor);
			return _object;
		}
	}
}
