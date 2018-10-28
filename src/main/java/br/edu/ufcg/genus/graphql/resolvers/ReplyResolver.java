package br.edu.ufcg.genus.graphql.resolvers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.coxautodev.graphql.tools.GraphQLResolver;

import br.edu.ufcg.genus.models.Reply;

public class ReplyResolver implements GraphQLResolver<Reply>{
	
	public String getDate(Reply reply) {
		DateFormat df = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
		return df.format(reply.getDate());		
	}
}
