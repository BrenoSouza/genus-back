package br.edu.ufcg.genus.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ufcg.genus.exception.InvalidIDException;
import br.edu.ufcg.genus.exception.NotAuthorizedException;
import br.edu.ufcg.genus.inputs.DiscussionCreationInput;
import br.edu.ufcg.genus.models.Discussion;
import br.edu.ufcg.genus.models.Subject;
import br.edu.ufcg.genus.models.User;
import br.edu.ufcg.genus.repositories.DiscussionRepository;
import br.edu.ufcg.genus.update_inputs.UpdateDiscussionInput;

@Service
public class DiscussionService {
	
	@Autowired
	private DiscussionRepository discussionRepository;
	
	@Autowired
	private SubjectService subjectService;
	
	public Discussion findDiscussionById(Long id) {
		return discussionRepository.findById(id)
			.orElseThrow(() -> new InvalidIDException("Discussion with passed ID was not found", id));
	}
	
	public Discussion createDiscussion(DiscussionCreationInput input, User user) {
		Subject subject = subjectService.findSubjectById(input.getSubjectId());

		if (!user.checkStudent(subject) && !user.checkTeacher(subject)) throw new NotAuthorizedException("You don't have permission to do this");

		Discussion forumPost = new Discussion(user, subject, input.getTitle(), input.getContent());
		subject.addDiscussion(forumPost);
		discussionRepository.save(forumPost);
		return forumPost;
	}

	public boolean removeDiscussion(long id, User user) {
		Discussion discussion = findDiscussionById(id);
		Subject subject = discussion.getSubject();
		if (!user.checkTeacher(subject) && !discussion.getCreator().equals(user)) throw new NotAuthorizedException("You don't have permission to do this");

		discussionRepository.deleteById(id);
		return true;
	}

	public Discussion updateDiscussion(UpdateDiscussionInput input, User user) {
		Discussion discussion = findDiscussionById(input.getDiscussionId());
		Subject subject = discussion.getSubject();

		if (!user.checkTeacher(subject) && !discussion.getCreator().equals(user)) throw new NotAuthorizedException("You don't have permission to do this");

        if (input.getTitle() != null) {
            discussion.setTitle(input.getTitle());
		}
		Date now = new Date();
		discussion.setLastUpdatedDate(now);
        return discussionRepository.save(discussion);
	}
}
