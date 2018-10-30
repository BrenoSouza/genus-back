package br.edu.ufcg.genus.graphql.mutations;

import org.springframework.beans.factory.annotation.Autowired;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;

import br.edu.ufcg.genus.inputs.ReplyCreationInput;
import br.edu.ufcg.genus.models.Reply;
import br.edu.ufcg.genus.services.ReplyService;

public class ReplyMutations implements GraphQLMutationResolver {
	
	@Autowired
	private ReplyService replyService;
	
	public Reply createReply(ReplyCreationInput input) {
		return replyService.createReply(input);
	}

	public Boolean removeReply(Long replyId) {
		return this.replyService.removeReply(replyId);
	}
}
