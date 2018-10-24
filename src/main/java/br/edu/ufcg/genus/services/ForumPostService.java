package br.edu.ufcg.genus.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ufcg.genus.inputs.ForumPostCreationInput;
import br.edu.ufcg.genus.models.ForumPost;
import br.edu.ufcg.genus.models.Subject;
import br.edu.ufcg.genus.models.User;
import br.edu.ufcg.genus.repositories.ForumPostRepository;

@Service
public class ForumPostService {
	
	@Autowired
	private ForumPostRepository forumPostRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SubjectService subjectService;
	
	public ForumPost createForumPost(ForumPostCreationInput input) {
		User user = userService.findLoggedUser();
		Subject subject = subjectService.findSubjectById(input.getSubjectId());
		//TODO: ADD CHECK HERE TO SEE IF THIS USER CAN CREATE THE FORUM POST ON THE SUBJECT
		ForumPost forumPost = new ForumPost(user, subject, input.getTitle());
		subject.addForumPost(forumPost);
		forumPostRepository.save(forumPost);
		return forumPost;
	}

}
