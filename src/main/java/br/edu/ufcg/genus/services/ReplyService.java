package br.edu.ufcg.genus.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.edu.ufcg.genus.exception.InvalidIDException;
import br.edu.ufcg.genus.exception.NotAuthorizedException;
import br.edu.ufcg.genus.inputs.ReplyCreationInput;
import br.edu.ufcg.genus.models.Discussion;
import br.edu.ufcg.genus.models.Reply;
import br.edu.ufcg.genus.models.Subject;
import br.edu.ufcg.genus.models.User;
import br.edu.ufcg.genus.repositories.ReplyRepository;
import br.edu.ufcg.genus.update_inputs.UpdateReplyInput;

@Service
public class ReplyService {
	
	@Autowired
	private ReplyRepository replyRepository;
	
	@Autowired
	private DiscussionService discussionService;
	
	@Autowired
	private UserService userService;
	
	public Reply findReplyById(Long id) {
		return replyRepository.findById(id)
			.orElseThrow(() -> new InvalidIDException("Discussion with passed ID was not found", id));
	}

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

	public Boolean removeReply(Long replyId) {
		Reply reply = replyRepository.findById(replyId)
			.orElseThrow(() -> new InvalidIDException("Reply with passed ID was not found", replyId));

		User user = userService.findLoggedUser();
		Subject subject = reply.getDiscussion().getSubject();

		if (!user.checkTeacher(subject) && !reply.getUser().equals(user)) throw new NotAuthorizedException("You don't have permission to do this");

		reply.setContent("REPLY_REMOVED");
		this.replyRepository.save(reply);

		return true;
	}

	public Reply updateReply(UpdateReplyInput input) {
		Reply reply = findReplyById(input.getReplyId());
		Discussion discussion = reply.getDiscussion();
		Subject subject = discussion.getSubject();
		User user = userService.findLoggedUser();

		if (!user.checkStudent(subject) && !user.checkTeacher(subject)) throw new NotAuthorizedException("You don't have permission to do this");

		if (input.getContent() != null) {
			reply.setContent(input.getContent());
		}
		return replyRepository.save(reply);
	}

}
