package br.edu.ufcg.genus;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.edu.ufcg.genus.exception.GraphQLErrorAdapter;
import br.edu.ufcg.genus.graphql.mutations.EntryCodeMutations;
import br.edu.ufcg.genus.graphql.mutations.EvaluationMutations;
import br.edu.ufcg.genus.graphql.mutations.EvaluationResultMutations;
import br.edu.ufcg.genus.graphql.mutations.DiscussionMutations;
import br.edu.ufcg.genus.graphql.mutations.ReplyMutations;
import br.edu.ufcg.genus.graphql.mutations.GradeMutations;
import br.edu.ufcg.genus.graphql.mutations.InstitutionMutations;
import br.edu.ufcg.genus.graphql.mutations.NotificationMutations;
import br.edu.ufcg.genus.graphql.mutations.SubjectMutations;
import br.edu.ufcg.genus.graphql.mutations.UserMutations;
import br.edu.ufcg.genus.graphql.queries.DiscussionQueries;
import br.edu.ufcg.genus.graphql.queries.EmailQueries;
import br.edu.ufcg.genus.graphql.queries.EvaluationQueries;
import br.edu.ufcg.genus.graphql.queries.EvaluationResultQueries;
import br.edu.ufcg.genus.graphql.queries.NotificationQueries;
import br.edu.ufcg.genus.graphql.queries.GradeQueries;
import br.edu.ufcg.genus.graphql.queries.InstitutionQueries;
import br.edu.ufcg.genus.graphql.queries.ReplyQueries;
import br.edu.ufcg.genus.graphql.queries.StudentSubjectQueries;
import br.edu.ufcg.genus.graphql.queries.SubjectQueries;
import br.edu.ufcg.genus.graphql.queries.UserQueries;
import br.edu.ufcg.genus.graphql.queries.UserRoleQueries;
import br.edu.ufcg.genus.graphql.resolvers.DiscussionResolver;
import br.edu.ufcg.genus.graphql.resolvers.GradeResolver;
import br.edu.ufcg.genus.graphql.resolvers.InstitutionResolver;
import br.edu.ufcg.genus.graphql.resolvers.UserResolver;
import br.edu.ufcg.genus.graphql.resolvers.SubjectResolver;
import graphql.servlet.GraphQLErrorHandler;
import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;

@SpringBootApplication
public class GenusApplication {

	public static void main(String[] args) {
		SpringApplication.run(GenusApplication.class, args);
	}
	
	@Bean
	public GraphQLErrorHandler errorHandler() {
		return new GraphQLErrorHandler() {
		
			@Override
			public List<GraphQLError> processErrors(List<GraphQLError> errors) {
				List<GraphQLError> clientErrors = errors.stream()
						.filter(this::isClientError)
						.collect(Collectors.toList());

				List<GraphQLError> serverErros = errors.stream()
					.filter(e -> !isClientError(e))
					.map(GraphQLErrorAdapter::new)
					.collect(Collectors.toList());

				List<GraphQLError> e = new ArrayList<>();
				e.addAll(clientErrors);
				e.addAll(serverErros);

				return e;
			}

			protected boolean isClientError(GraphQLError e) {
				return !(e instanceof ExceptionWhileDataFetching
					|| e instanceof Throwable);
			}
		};
	}
	
	@Bean
	public UserQueries userQueries() {
		return new UserQueries();
	}
		
	@Bean
	public UserMutations userMutations() {
		return new UserMutations();
	}
	
	@Bean
	public UserResolver userResolver() {
		return new UserResolver();
	}
	
	@Bean
	public NotificationQueries notificationQueries() {
		return new NotificationQueries();
	}

	@Bean
	public NotificationMutations notificationMutations() {
		return new NotificationMutations();
	}
	
	@Bean
	public InstitutionResolver institutionResolver() {
		return new InstitutionResolver();
	}

	@Bean
	public InstitutionQueries institutionQueries() {
		return new InstitutionQueries();
	}

	@Bean
	public InstitutionMutations institutionMutations() {
		return new InstitutionMutations();
	}
	
	@Bean
	public GradeResolver gradeResolver() {
		return new GradeResolver();
	}

	@Bean
	public GradeQueries gradeQueries() {
		return new GradeQueries();
	}
	
	@Bean
	public GradeMutations gradeMutations() {
		return new GradeMutations();		
	}

	@Bean
	public SubjectResolver subjectResolver() {
		return new SubjectResolver();
	}
	
	@Bean
	public SubjectQueries subjectQueries() {
		return new SubjectQueries();
	}
	
	@Bean
	public SubjectMutations subjectMutations() {
		return new SubjectMutations();
	}
	
	@Bean
	public EntryCodeMutations entryCodeMutations() {
		return new EntryCodeMutations();
	}
	
	@Bean
	public UserRoleQueries userRoleQueries() {
		return new UserRoleQueries();
	}
	
	@Bean
	public DiscussionResolver discussionResolver() {
		return new DiscussionResolver();
	}
	
	@Bean
	public DiscussionMutations discussionMutations() {
		return new DiscussionMutations();
	}
	
	@Bean
	public ReplyMutations replyMutations() {
		return new ReplyMutations();
	}
	
	@Bean
	public ReplyQueries replyQueries() {
		return new ReplyQueries();
	}
	
	@Bean
	public DiscussionQueries discussionQueries() {
		return new DiscussionQueries();
	}
	
	@Bean
	public EvaluationMutations evaluationMutations() {
		return new EvaluationMutations();
	}
	
	@Bean
	public EvaluationQueries evaluationQueries() {
		return new EvaluationQueries();
	}
	
	@Bean
	public StudentSubjectQueries studentSubjectQueries() {
		return new StudentSubjectQueries();
	}
	
	@Bean
	public EmailQueries emailQueries() {
		return new EmailQueries();
	}
	
	@Bean
	public EvaluationResultQueries evaluationResultQueries() {
		return new EvaluationResultQueries();
	}
	
	@Bean
	public EvaluationResultMutations evaluationResultMutations() {
		return new EvaluationResultMutations();
	}
}
