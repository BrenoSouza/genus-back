package br.edu.ufcg.genus.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ufcg.genus.inputs.ForumReplyCreationInput;
import br.edu.ufcg.genus.models.ForumPost;
import br.edu.ufcg.genus.models.ForumReply;
import br.edu.ufcg.genus.models.User;
import br.edu.ufcg.genus.repositories.ForumReplyRepository;

@Service
public class ForumReplyService {
	
	@Autowired
	private ForumReplyRepository forumReplyRepository;
	
	@Autowired
	private ForumPostService forumPostService;
	
	@Autowired
	private UserService userService;
	
	public ForumReply createForumReply(ForumReplyCreationInput input) {
		User user = userService.findLoggedUser();	
		ForumPost discussion = forumPostService.findForumPostById(input.getForumPostId());
		//TODO: ADD CHECK HERE TO SEE IF THIS USER CAN COMMENT ON THE DISCUSSION
		ForumReply reply = new ForumReply(input.getContent(), user, discussion);
		discussion.addReply(reply);
		this.forumReplyRepository.save(reply);
		return reply;		
	}

}
