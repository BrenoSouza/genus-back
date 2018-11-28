package br.edu.ufcg.genus.graphql.resolvers;

import org.springframework.beans.factory.annotation.Autowired;

import com.coxautodev.graphql.tools.GraphQLResolver;

import br.edu.ufcg.genus.models.Discussion;
import br.edu.ufcg.genus.models.Reply;
import br.edu.ufcg.genus.services.ReplyService;

public class DiscussionResolver implements GraphQLResolver<Discussion>{
	
	@Autowired
	private ReplyService replyService;
	
	public int getReplyNumber(Discussion discussion) {
		return discussion.getReplyNumber();
	}
	
	public Iterable<Reply> getReplies(Discussion discussion, Integer page, Integer size) {
		return replyService.findRepliesByDiscussion(discussion.getId(), page, size);
	}

}
