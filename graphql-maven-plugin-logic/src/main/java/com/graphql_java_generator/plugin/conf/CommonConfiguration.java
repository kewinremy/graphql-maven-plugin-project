/**
 * 
 */
package com.graphql_java_generator.plugin.conf;

import java.io.File;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This interface contains all the configuration parameters for the <I>graphql</I> goal (Maven) or task (Gradle) of the
 * plugin, as an interface.<BR/>
 * All these methods are directly the property names, to map against a Spring {@link Configuration} that defines the
 * {@link Bean}s. These beans can then be reused in Spring Component, thank to Spring IoC and its dependency injection
 * capability.
 * 
 * @author etienne-sf
 */
public interface CommonConfiguration {

	// The String constant must be a constant expression, for use in the GraphqlMavenPlugin class.
	// So all these are String, including Boolean and Enum. Boolean are either "true" or "false"
	public final String DEFAULT_ADD_RELAY_CONNECTIONS = "false";
	public final String DEFAULT_MAX_TOKENS = "2147483647"; // Integer.MAX_VALUE
	public final String DEFAULT_PACKAGE_NAME = "com.generated.graphql";
	public final String DEFAULT_SCHEMA_FILE_FOLDER = "src/main/resources";
	public final String DEFAULT_SCHEMA_FILE_PATTERN = "*.graphqls";
	public final String DEFAULT_SKIP_GENERATION_IF_SCHEMA_HAS_NOT_CHANGED = "true";

	/**
	 * <I>(Useless, since 1.18.7)</I>Defines the options that maximum number of tokens that the GraphQL schema parser
	 * may read. The default value is Integer.MAX_VALUE (=2147483647). If the schema contains more than
	 * <I>maxTokens</I>, the build will fail with an error.
	 * 
	 * @return
	 */
	public Integer getMaxTokens();

	/**
	 * Get the {@link File} for the current project's directory. This allows to compute the full path of file that are
	 * within this project (like custom templates for instance)
	 * 
	 * @return
	 */
	public File getProjectDir();

	/**
	 * The main resources folder, typically '/src/main/resources' of the current project. That's where the GraphQL
	 * schema(s) are expected to be: in this folder, or one of these subfolders
	 */
	public File getSchemaFileFolder();

	/**
	 * <P>
	 * The pattern to find the graphql schema file(s). The default value is "/*.graphqls" meaning that the maven plugin
	 * will search all graphqls files in the "/src/main/resources" folder (please check also the <I>schemaFileFolder</I>
	 * plugin parameter).
	 * </P>
	 * <P>
	 * You can put the star (*) joker in the filename, to retrieve several files at ones, for instance
	 * <I>/myschema*.graphqls</I> will retrieve the <I>/src/main/resources/myschema.graphqls</I> and
	 * <I>/src/main/resources/myschema_extend.graphqls</I> files.
	 * <P>
	 */
	public String getSchemaFilePattern();

	/**
	 * <P>
	 * Map of the code templates to be used: this allows to override the default templates, and control exactly what
	 * code is generated by the plugin.
	 * </P>
	 * <P>
	 * You can override any of the Velocity templates of the project. The list of templates is defined in the enum
	 * CodeTemplate, that you can <A HREF=
	 * "https://github.com/graphql-java-generator/graphql-maven-plugin-project/blob/master/graphql-maven-plugin-logic/src/main/java/com/graphql_java_generator/plugin/CodeTemplate.java">check
	 * here</A>.
	 * </P>
	 * <P>
	 * You can find a sample in the <A HREF=
	 * "https://github.com/graphql-java-generator/graphql-maven-plugin-project/blob/master/graphql-maven-plugin-samples/graphql-maven-plugin-samples-CustomTemplates-client/pom.xml">CustomTemplates
	 * client sample</A>.
	 * </P>
	 * <P>
	 * <B>Important notice:</B> Please note that the default templates may change in the future. And some of these
	 * modifications would need to be reported into the custom templates. We'll try to better expose a stable public API
	 * in the future.
	 * </P>
	 */
	public Map<String, String> getTemplates();

	/**
	 * <P>
	 * True if the plugin is configured to add the Relay connection capabilities to the field marked by the
	 * <I>&#064;RelayConnection</I> directive.
	 * </P>
	 * <P>
	 * If so, the plugin reads the provided GraphQL schema file(s), and enriches them with the interfaces and types
	 * needed to respect the Relay Connection specification. The entry point for that is the
	 * <I>&#064;RelayConnection</I> directive.
	 * </P>
	 * <P>
	 * You'll find all the information on the plugin web site. Please check the <A
	 * HREF="https://graphql-maven-plugin-project.graphql-java-generator.com/client_add_relay_connection.html>client
	 * Relay capability page</A> or the <A
	 * HREF="https://graphql-maven-plugin-project.graphql-java-generator.com/server_add_relay_connection.html>server
	 * Relay capability page</A>.
	 * </P>
	 */
	public boolean isAddRelayConnections();

	/**
	 *  An optional prefix for the generated java classes for GraphQL types.
	 */
	public String getTypePrefix();

	/**
	 *  An optional suffix for the generated java classes for GraphQL types.
	 */
	public String getTypeSuffix();

	/**
	 *  An optional prefix for the generated java classes for GraphQL input objects.
	 */
	public String getInputPrefix();

	/**
	 *  An optional suffix for the generated java classes for GraphQL input objects.
	 */
	public String getInputSuffix();

	/**
	 *  An optional prefix for the generated java classes for GraphQL unions.
	 */
	public String getUnionPrefix();

	/**
	 *  An optional suffix for the generated java classes for GraphQL unions.
	 */
	public String getUnionSuffix();

	/**
	 *  An optional prefix for the generated java classes for GraphQL interfaces.
	 */
	public String getInterfacePrefix();

	/**
	 *  An optional prefix for the generated java classes for GraphQL interfaces.
	 */
	public String getInterfaceSuffix();
	/**
	 *  An optional prefix for the generated java classes for GraphQL enums.
	 */
	public String getEnumPrefix();

	/**
	 *  An optional suffix for the generated java classes for GraphQL enums.
	 */
	public String getEnumSuffix();

	/**
	 * This method is used only in {@link GeneratePojoConfiguration}.
	 * 
	 * @return The {@link GeneratePojoConfiguration} implementation of this method always returns true
	 * @see GeneratePojoConfiguration#isGenerateJacksonAnnotations()
	 */
	default public boolean isGenerateJacksonAnnotations() {
		return true;
	}

	/**
	 * <P>
	 * This parameter is now <B><I>deprecated</I></B>: it's value used in the plugin is always true, that is: if the
	 * generated sources or resources are older than the GraphQL schema file(s), then there is no source or resource
	 * generation. In clear, the source and resource generation is executed only if the provided input (GraphQL
	 * schema...) has been updated since the last plugin execution.
	 * </P>
	 */
	@Deprecated
	public boolean isSkipGenerationIfSchemaHasNotChanged();

	/**
	 * The default name of the target filename.<BR/>
	 * This method must be accessible by the Velocity engine. Thus, it can not be a <I>default</I> interface method.
	 */
	default public String getDefaultTargetSchemaFileName() {
		return GenerateGraphQLSchemaConfiguration.DEFAULT_TARGET_SCHEMA_FILE_NAME;
	}

	/** Logs all the configuration parameters (only when in the debug level) */
	public void logConfiguration();

	/** Logs all the common configuration parameters (only when in the debug level) */
	public default void logCommonConfiguration() {
		Logger logger = LoggerFactory.getLogger(getClass());
		if (logger.isDebugEnabled()) {
			logger.debug("  Common parameters:");
			logger.debug("    addRelayConnections: " + isAddRelayConnections());
			logger.debug("    defaultTargetSchemaFileName: " + getDefaultTargetSchemaFileName());
			logger.debug("    parserOptions.maxTokens: " + getMaxTokens());
			logger.debug("    projectDir: " + getProjectDir().getAbsolutePath());
			logger.debug("    schemaFileFolder: " + getSchemaFileFolder());
			logger.debug("    schemaFilePattern: " + getSchemaFilePattern());
			logger.debug("    skipGenerationIfSchemaHasNotChanged: " + isSkipGenerationIfSchemaHasNotChanged());
			logger.debug("    Templates: "
					+ (Objects.nonNull(getTemplates())
							? getTemplates().entrySet().stream()
									.map(entry -> String.format("%s=%s", entry.getKey(), entry.getValue()))
									.collect(Collectors.joining(", "))
							: ""));
			logger.debug("    typePrefix: " + getTypePrefix());
			logger.debug("    typeSuffix: " + getTypeSuffix());
			logger.debug("    inputPrefix: " + getInputPrefix());
			logger.debug("    inputSuffix: " + getInputSuffix());
			logger.debug("    enumPrefix: " + getEnumPrefix());
			logger.debug("    enumSuffix: " + getEnumSuffix());
			logger.debug("    interfacePrefix: " + getInterfacePrefix());
			logger.debug("    interfaceSuffix: " + getInterfaceSuffix());
			logger.debug("    unionPrefix: " + getUnionPrefix());
			logger.debug("    unionSuffix: " + getUnionSuffix());
		}
	}

}
