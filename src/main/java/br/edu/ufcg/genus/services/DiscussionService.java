package br.edu.ufcg.genus.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ufcg.genus.exception.InvalidIDException;
import br.edu.ufcg.genus.inputs.DiscussionCreationInput;
import br.edu.ufcg.genus.models.Discussion;
import br.edu.ufcg.genus.models.StudentSubject;
import br.edu.ufcg.genus.models.Subject;
import br.edu.ufcg.genus.models.User;
import br.edu.ufcg.genus.repositories.DiscussionRepository;
import br.edu.ufcg.genus.update_inputs.UpdateDiscussionInput;
import br.edu.ufcg.genus.utils.PermissionChecker;

@Service
public class DiscussionService {
	
	@Autowired
	private DiscussionRepository discussionRepository;
	
	@Autowired
	private SubjectService subjectService;
	
	@Autowired
	private NotificationService notificationService;

	public Discussion findDiscussionById(Long id) {
		return discussionRepository.findById(id)
			.orElseThrow(() -> new InvalidIDException("Discussion with passed ID was not found", id));
	}
	
	public Discussion createDiscussion(DiscussionCreationInput input, User user) {
		Subject subject = subjectService.findSubjectById(input.getSubjectId());
		PermissionChecker.checkSubjectPermission(user, subject);
		Discussion forumPost = new Discussion(user, subject, input.getTitle(), input.getContent());
		subject.addDiscussion(forumPost);
		Discussion discussion = discussionRepository.save(forumPost);

		Long subjectId = subject.getId();
		Long gradeId = subject.getGrade().getId();
		Long institutionId = subject.getGrade().getInstitution().getId();

		for (StudentSubject studentSubject : subject.getStudents()) {
			if (!studentSubject.getUser().equals(user)) {
				notificationService.createNotification("NEW_DISCUSSION", discussion.getId(), forumPost.getTitle(), user.getUsername(), 
														studentSubject.getUser(), institutionId, gradeId, subjectId, discussion.getId());
			}
		}

		for (User teacher : subject.getTeachers()) {
			if (!teacher.equals(user)) {
				notificationService.createNotification("NEW_DISCUSSION", discussion.getId(), forumPost.getTitle(), user.getUsername(),
														teacher, institutionId, gradeId, subjectId, discussion.getId());
			}
		}
		return forumPost;
	}

	public boolean removeDiscussion(long id, User user) {
		Discussion discussion = findDiscussionById(id);
		PermissionChecker.checkDiscussionPermission(user, discussion);
		discussionRepository.deleteById(id);
		return true;
	}

	public Discussion updateDiscussion(UpdateDiscussionInput input, User user) {
		Discussion discussion = findDiscussionById(input.getDiscussionId());
		PermissionChecker.checkDiscussionPermission(user, discussion);
		
        if (input.getTitle() != null) {
            discussion.setTitle(input.getTitle());
		}
		//Date now = new Date();
		//discussion.setLastUpdatedDate(now);
        return discussionRepository.save(discussion);
	}
}
