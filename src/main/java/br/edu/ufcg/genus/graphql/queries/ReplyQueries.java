package br.edu.ufcg.genus.graphql.queries;

import org.springframework.beans.factory.annotation.Autowired;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;

import br.edu.ufcg.genus.models.Reply;
import br.edu.ufcg.genus.services.ReplyService;

public class ReplyQueries implements GraphQLQueryResolver{
	
	@Autowired
	private ReplyService replyService;
	
	public Reply findReply(Long id) {
		return replyService.findReplyById(id);
	}

}
