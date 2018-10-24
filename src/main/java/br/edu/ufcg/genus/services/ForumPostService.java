package br.edu.ufcg.genus.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ufcg.genus.exception.InvalidIDException;
import br.edu.ufcg.genus.inputs.ForumPostCreationInput;
import br.edu.ufcg.genus.inputs.ForumReplyCreationInput;
import br.edu.ufcg.genus.models.ForumPost;
import br.edu.ufcg.genus.models.ForumReply;
import br.edu.ufcg.genus.models.Subject;
import br.edu.ufcg.genus.models.User;
import br.edu.ufcg.genus.repositories.ForumPostRepository;
import br.edu.ufcg.genus.repositories.ForumReplyRepository;

@Service
public class ForumPostService {
	
	@Autowired
	private ForumPostRepository forumPostRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SubjectService subjectService;
	
	@Autowired
	private ForumReplyRepository forumReplyRepository;
	
	public ForumPost findForumPostById(Long id) {
		return forumPostRepository.findById(id)
			.orElseThrow(() -> new InvalidIDException("Discussion with passed ID was not found", id));
	}
	
	public ForumPost createForumPost(ForumPostCreationInput input) {
		User user = userService.findLoggedUser();
		Subject subject = subjectService.findSubjectById(input.getSubjectId());
		//TODO: ADD CHECK HERE TO SEE IF THIS USER CAN CREATE THE FORUM POST ON THE SUBJECT
		ForumPost forumPost = new ForumPost(user, subject, input.getTitle());
		subject.addForumPost(forumPost);
		ForumReply reply = new ForumReply(input.getContent(), user, forumPost);
		forumPost.addReply(reply);
		forumPostRepository.save(forumPost);
		forumReplyRepository.save(reply);
		return forumPost;
	}

}
