package graphql.spring.web.servlet;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.concurrent.CompletableFuture;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import graphql.ExecutionInput;
import graphql.ExecutionResultImpl;
import graphql.GraphQL;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "graphql.url=otherUrl")
@RunWith(SpringRunner.class)
public class IntegrationTestDifferentUrl {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	GraphQL graphql;

	@Test
	public void endpointIsAvailableWithDifferentUrl() {
		String query = "{foo}";

		ExecutionResultImpl executionResult = (ExecutionResultImpl) ExecutionResultImpl.newExecutionResult().data("bar")
				.build();
		CompletableFuture cf = CompletableFuture.completedFuture(executionResult);
		ArgumentCaptor<ExecutionInput> captor = ArgumentCaptor.forClass(ExecutionInput.class);
		Mockito.when(graphql.executeAsync(captor.capture())).thenReturn(cf);

		String body = this.restTemplate.getForObject("/otherUrl/?query={query}", String.class, query);

		assertThat(body, is("{\"data\":\"bar\"}"));
		assertThat(captor.getValue().getQuery(), is(query));
	}

}