package br.edu.ufcg.genus.graphql.queries;

import org.springframework.beans.factory.annotation.Autowired;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;

import br.edu.ufcg.genus.models.Discussion;
import br.edu.ufcg.genus.services.DiscussionService;

public class DiscussionQueries implements GraphQLQueryResolver {
	
	@Autowired
	private DiscussionService discussionService;
	
	public Discussion findDiscussion(Long id) {
		return discussionService.findDiscussionById(id);
	}

}
