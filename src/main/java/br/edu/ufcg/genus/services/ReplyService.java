package br.edu.ufcg.genus.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.edu.ufcg.genus.exception.NotAuthorizedException;
import br.edu.ufcg.genus.inputs.ReplyCreationInput;
import br.edu.ufcg.genus.models.Discussion;
import br.edu.ufcg.genus.models.Reply;
import br.edu.ufcg.genus.models.Subject;
import br.edu.ufcg.genus.models.User;
import br.edu.ufcg.genus.repositories.ReplyRepository;

@Service
public class ReplyService {
	
	@Autowired
	private ReplyRepository replyRepository;
	
	@Autowired
	private DiscussionService discussionService;
	
	@Autowired
	private UserService userService;
	
	public Reply createReply(ReplyCreationInput input) {
		User user = userService.findLoggedUser();	
		Discussion discussion = discussionService.findDiscussionById(input.getForumPostId());

		Subject subject = discussion.getSubject();

		if (!user.checkStudent(subject) && !user.checkTeacher(subject)) throw new NotAuthorizedException("You don't have permission to do this");

		Reply reply = new Reply(input.getContent(), user, discussion);
		discussion.addReply(reply);
		this.replyRepository.save(reply);
		return reply;		
	}

	public Iterable<Reply> findRepliesByDiscussion(Long id, Integer page, Integer size) {
		return replyRepository.findByDiscussionId(PageRequest.of(page, size), id);
	}

}
