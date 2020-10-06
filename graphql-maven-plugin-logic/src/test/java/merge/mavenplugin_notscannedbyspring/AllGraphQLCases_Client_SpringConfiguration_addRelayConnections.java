/**
 * 
 */
package merge.mavenplugin_notscannedbyspring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

/**
 * The Spring configuration used for JUnit tests
 * 
 * @author etienne-sf
 */
@Configuration
@ComponentScan(basePackages = "com.graphql_java_generator", excludeFilters = {
		@Filter(type = FilterType.REGEX, pattern = ".*\\.GenerateRelaySchema.*"),
		@Filter(type = FilterType.REGEX, pattern = ".*\\.GraphQL.*"),
		@Filter(type = FilterType.REGEX, pattern = ".*CompilationTestHelper") })
public class AllGraphQLCases_Client_SpringConfiguration_addRelayConnections extends AbstractSpringConfiguration {

	public AllGraphQLCases_Client_SpringConfiguration_addRelayConnections() {
		super("src/test/resources", "allGraphQLCases*.graphqls", "allGraphQLCases.graphqls",
				"allGraphQLCases_addRelayConnections", true);
	}
}
