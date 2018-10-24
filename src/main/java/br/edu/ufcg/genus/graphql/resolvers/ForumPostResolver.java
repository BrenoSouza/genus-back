package br.edu.ufcg.genus.graphql.resolvers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.coxautodev.graphql.tools.GraphQLResolver;

import br.edu.ufcg.genus.models.ForumPost;

public class ForumPostResolver implements GraphQLResolver<ForumPost>{
	
	public String getCreationDate(ForumPost forumPost) {
		DateFormat df = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
		return df.format(forumPost.getCreationDate());
	}
	
	public String getLastUpdatedDate(ForumPost forumPost) {
		DateFormat df = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
		return df.format(forumPost.getLastUpdatedDate());
	}

}
