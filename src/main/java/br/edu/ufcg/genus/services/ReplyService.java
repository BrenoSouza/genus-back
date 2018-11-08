package br.edu.ufcg.genus.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.edu.ufcg.genus.exception.InvalidIDException;
import br.edu.ufcg.genus.inputs.ReplyCreationInput;
import br.edu.ufcg.genus.inputs.ReplyToReplyInput;
import br.edu.ufcg.genus.models.Discussion;
import br.edu.ufcg.genus.models.Reply;
import br.edu.ufcg.genus.models.Subject;
import br.edu.ufcg.genus.models.User;
import br.edu.ufcg.genus.repositories.ReplyRepository;
import br.edu.ufcg.genus.update_inputs.UpdateReplyInput;
import br.edu.ufcg.genus.utils.PermissionChecker;
import br.edu.ufcg.genus.utils.ServerConstants;

@Service
public class ReplyService {
	
	@Autowired
	private ReplyRepository replyRepository;
	
	@Autowired
	private DiscussionService discussionService;
	
	
	public Reply findReplyById(Long id) {
		return replyRepository.findById(id)
			.orElseThrow(() -> new InvalidIDException("Discussion with passed ID was not found", id));
	}

	public Reply createReply(ReplyCreationInput input, User user) {
		Discussion discussion = discussionService.findDiscussionById(input.getForumPostId());

		Subject subject = discussion.getSubject();
		PermissionChecker.checkSubjectPermission(user, subject);

		Reply reply = new Reply(input.getContent(), user, discussion);
		discussion.addReply(reply);
		this.replyRepository.save(reply);
		return reply;		
	}
	
	public Reply replyToReply(ReplyToReplyInput input, User user) {
		Reply parent = findReplyById(input.getParentId());
		Discussion discussion = parent.getDiscussion();
		Subject subject = discussion.getSubject();
		PermissionChecker.checkSubjectPermission(user, subject);
		Reply reply = new Reply(input.getContent(), user, discussion, parent);
		discussion.addReply(reply);
		parent.addReply(reply);
		this.replyRepository.save(reply);
		return reply;
	}

	public Iterable<Reply> findRepliesByDiscussion(Long id, Integer page, Integer size) {
		return replyRepository.findByDiscussionId(PageRequest.of(page, size), id);
	}

	public Boolean removeReply(Long replyId, User user) {
		Reply reply = findReplyById(replyId);
		PermissionChecker.checkReplyPermission(user, reply);
		return removeReplyAndChildren(reply);
	}

	private Boolean removeReplyAndChildren(Reply reply) {
		for (Reply child : reply.getReplies()) {
			removeReplyAndChildren(child);
		}
		reply.setContent(ServerConstants.REMOVED);
		this.replyRepository.save(reply);
		return true;
	}

	public Reply updateReply(UpdateReplyInput input, User user) {
		Reply reply = findReplyById(input.getReplyId());
		Discussion discussion = reply.getDiscussion();
		Subject subject = discussion.getSubject();

		PermissionChecker.checkSubjectPermission(user, subject);

		if (input.getContent() != null) {
			reply.setContent(input.getContent());
		}
		return replyRepository.save(reply);
	}
	
	

}
