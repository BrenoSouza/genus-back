package br.edu.ufcg.genus.graphql.resolvers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.coxautodev.graphql.tools.GraphQLResolver;

import br.edu.ufcg.genus.models.Discussion;

public class DiscussionResolver implements GraphQLResolver<Discussion>{
	
	public String getCreationDate(Discussion discussion) {
		DateFormat df = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
		return df.format(discussion.getCreationDate());
	}
	
	public String getLastUpdatedDate(Discussion discussion) {
		DateFormat df = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
		return df.format(discussion.getLastUpdatedDate());
	}
	
	public int getReplyNumber(Discussion discussion) {
		return discussion.getReplyNumber();
	}

}
