package br.edu.ufcg.genus.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ufcg.genus.exception.InvalidIDException;
import br.edu.ufcg.genus.exception.NotAuthorizedException;
import br.edu.ufcg.genus.inputs.DiscussionCreationInput;
import br.edu.ufcg.genus.models.Discussion;
import br.edu.ufcg.genus.models.Institution;
import br.edu.ufcg.genus.models.Reply;
import br.edu.ufcg.genus.models.Subject;
import br.edu.ufcg.genus.models.User;
import br.edu.ufcg.genus.repositories.DiscussionRepository;
import br.edu.ufcg.genus.repositories.ReplyRepository;

@Service
public class DiscussionService {
	
	@Autowired
	private DiscussionRepository discussionRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SubjectService subjectService;
	
	@Autowired
	private ReplyRepository forumReplyRepository;
	
	public Discussion findDiscussionById(Long id) {
		return discussionRepository.findById(id)
			.orElseThrow(() -> new InvalidIDException("Discussion with passed ID was not found", id));
	}
	
	public Discussion createDiscussion(DiscussionCreationInput input) {
		User user = userService.findLoggedUser();
		Subject subject = subjectService.findSubjectById(input.getSubjectId());

		if (!user.checkStudent(subject) && !user.checkTeacher(subject)) throw new NotAuthorizedException("You don't have permission to do this");

		Discussion forumPost = new Discussion(user, subject, input.getTitle());
		subject.addDiscussion(forumPost);
		Reply reply = new Reply(input.getContent(), user, forumPost);
		forumPost.addReply(reply);
		discussionRepository.save(forumPost);
		forumReplyRepository.save(reply);
		return forumPost;
	}

}
