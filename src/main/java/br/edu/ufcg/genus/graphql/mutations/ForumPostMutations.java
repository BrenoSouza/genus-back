package br.edu.ufcg.genus.graphql.mutations;

import org.springframework.beans.factory.annotation.Autowired;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;

import br.edu.ufcg.genus.inputs.ForumPostCreationInput;
import br.edu.ufcg.genus.models.ForumPost;
import br.edu.ufcg.genus.services.ForumPostService;

public class ForumPostMutations implements GraphQLMutationResolver {
	
	@Autowired
	private ForumPostService forumPostService;
	
	public ForumPost createForumPost(ForumPostCreationInput input) {
		return this.forumPostService.createForumPost(input);
	}

}
