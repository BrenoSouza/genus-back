package br.edu.ufcg.genus.graphql.mutations;

import org.springframework.beans.factory.annotation.Autowired;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;

import br.edu.ufcg.genus.inputs.ForumReplyCreationInput;
import br.edu.ufcg.genus.models.ForumReply;
import br.edu.ufcg.genus.services.ForumReplyService;

public class ForumReplyMutations implements GraphQLMutationResolver {
	
	@Autowired
	private ForumReplyService forumReplyService;
	
	public ForumReply createForumReply(ForumReplyCreationInput input) {
		return forumReplyService.createForumReply(input);
	}

}
