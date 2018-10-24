package br.edu.ufcg.genus.graphql.resolvers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.coxautodev.graphql.tools.GraphQLResolver;

import br.edu.ufcg.genus.models.ForumReply;

public class ForumReplyResolver implements GraphQLResolver<ForumReply>{
	
	public String getDate(ForumReply forumReply) {
		DateFormat df = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
		return df.format(forumReply.getDate());		
	}
}
