package br.edu.ufcg.genus.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ufcg.genus.inputs.SubjectCreationInput;
import br.edu.ufcg.genus.exception.InvalidIDException;
import br.edu.ufcg.genus.models.Grade;
import br.edu.ufcg.genus.models.Institution;
import br.edu.ufcg.genus.models.Subject;
import br.edu.ufcg.genus.models.User;
import br.edu.ufcg.genus.models.UserRole;
import br.edu.ufcg.genus.repositories.SubjectRepository;
import br.edu.ufcg.genus.repositories.UserRepository;
import br.edu.ufcg.genus.update_inputs.UpdateSubjectInput;
import br.edu.ufcg.genus.utils.PermissionChecker;

@Service
public class SubjectService {
	
	@Autowired
	private SubjectRepository subjectRepository;
	
	@Autowired
	private SubjectService subjectService;
	
	@Autowired
	private InstitutionService institutionService;
	
	@Autowired
	private GradeService gradeService;
	
	@Autowired
	private UserRepository userRepository;
	
	public Subject createSubject(SubjectCreationInput input, User user) {
		Grade grade = this.gradeService.findGradeById(input.getGradeId());
		Institution institution = this.institutionService.findById(grade.getInstitution().getId());
		ArrayList<UserRole> permitedRoles = new ArrayList<>();
		permitedRoles.add(UserRole.ADMIN);
		PermissionChecker.checkPermission(user, institution.getId(), permitedRoles);
		Subject newSubject = new Subject(grade, input.getName());
		subjectRepository.save(newSubject);
		return newSubject;
	}
	
	public Subject findSubjectById(long id) {
		return this.subjectRepository.findById(id).orElseThrow(() -> new InvalidIDException("Subject with passed ID was not found", id));
	}

	public Iterable<Subject> findSubjectsByGrade(Long gradeId) {
		Grade grade = this.gradeService.findGradeById(gradeId);
		return grade.getSubjects();
	}
		
	public Iterable<User> findTeachersBySubject(Long subjectId) {
		Subject subject = this.subjectService.findSubjectById(subjectId);
        return subject.getTeachers();
	}
	
	public Iterable<User> findStudentsBySubject(Long subjectId) {
		Subject subject = this.subjectService.findSubjectById(subjectId);
        return subject.findStudents();
    }

	public Subject updateSubject(UpdateSubjectInput input, User user) {
		Subject subject = findSubjectById(input.getSubjectId());
		checkAdminPermission(subject, user);
        if (input.getName() != null) {
            subject.setName(input.getName());
		}
        return subjectRepository.save(subject);
	}
	
	public boolean removeSubject(long id, User owner) {
		Subject subject = findSubjectById(id);
		checkAdminPermission(subject, owner);
		for(User user: subject.getTeachers()) {
			user.removeSubject(subject);
		}
		userRepository.saveAll(subject.getTeachers());
		this.subjectRepository.deleteById(id);
		return true;
	}
	
	private void checkAdminPermission(Subject subject, User user) {
		List<UserRole> permitedRoles = new ArrayList<>();
		permitedRoles.add(UserRole.ADMIN);
		long institutionId = subject.getGrade().getInstitution().getId();
		PermissionChecker.checkPermission(user, institutionId, permitedRoles);
	}

}
