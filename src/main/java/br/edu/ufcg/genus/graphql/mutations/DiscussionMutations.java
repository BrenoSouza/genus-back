package br.edu.ufcg.genus.graphql.mutations;

import org.springframework.beans.factory.annotation.Autowired;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;

import br.edu.ufcg.genus.inputs.DiscussionCreationInput;
import br.edu.ufcg.genus.models.Discussion;
import br.edu.ufcg.genus.services.DiscussionService;

public class DiscussionMutations implements GraphQLMutationResolver {
	
	@Autowired
	private DiscussionService forumPostService;
	
	public Discussion createDiscussion(DiscussionCreationInput input) {
		return this.forumPostService.createDiscussion(input);
	}

}
