package br.edu.ufcg.genus.graphql.resolvers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;

import com.coxautodev.graphql.tools.GraphQLResolver;

import br.edu.ufcg.genus.models.Discussion;
import br.edu.ufcg.genus.models.Reply;
import br.edu.ufcg.genus.services.ReplyService;

public class DiscussionResolver implements GraphQLResolver<Discussion>{
	
	@Autowired
	private ReplyService replyService;
	/*
	public String getCreationDate(Discussion discussion) {
		DateFormat df = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
		return df.format(discussion.getCreationDate());
	}
	
	public String getLastUpdatedDate(Discussion discussion) {
		DateFormat df = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
		return df.format(discussion.getLastUpdatedDate());
	}*/
	
	public int getReplyNumber(Discussion discussion) {
		return discussion.getReplyNumber();
	}
	
	public Iterable<Reply> getReplies(Discussion discussion, Integer page, Integer size) {
		return replyService.findRepliesByDiscussion(discussion.getId(), page, size);
	}

}
