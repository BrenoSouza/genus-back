package br.edu.ufcg.genus.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ufcg.genus.inputs.SubjectCreationInput;
import br.edu.ufcg.genus.exception.InvalidIDException;
import br.edu.ufcg.genus.models.Grade;
import br.edu.ufcg.genus.models.Institution;
import br.edu.ufcg.genus.models.StudentSubject;
import br.edu.ufcg.genus.models.Subject;
import br.edu.ufcg.genus.models.User;
import br.edu.ufcg.genus.models.UserRole;
import br.edu.ufcg.genus.repositories.StudentSubjectRepository;
import br.edu.ufcg.genus.repositories.SubjectRepository;
import br.edu.ufcg.genus.repositories.UserRepository;
import br.edu.ufcg.genus.update_inputs.UpdateSubjectInput;
import br.edu.ufcg.genus.utils.PermissionChecker;

@Service
public class SubjectService {
	
	@Autowired
	private SubjectRepository subjectRepository;
	
	@Autowired
	private InstitutionService institutionService;
	
	@Autowired
	private GradeService gradeService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private StudentSubjectRepository studentSubjectRepository;
	
	@Autowired
	private StudentSubjectService studentSubjectService;
	
	
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
	
	public Subject addTeacher(Long subjectId, Long teacherId, User user) {
		List<UserRole> permittedRolesOwner = new ArrayList<>();
		permittedRolesOwner.add(UserRole.ADMIN);
		List<UserRole> permitedRoles = new ArrayList<>();
		permitedRoles.add(UserRole.TEACHER);
		
    	User teacher = this.userService.findUserById(teacherId);
        Subject subject = findSubjectById(subjectId);
        Institution institution = subject.getGrade().getInstitution();
        
		PermissionChecker.checkPermission(user, institution.getId(), permittedRolesOwner);
		PermissionChecker.checkPermission(teacher, institution.getId(), permitedRoles);

        teacher.addSubject(subject);
        subject.addTeacher(teacher);
        
        this.subjectRepository.save(subject);
        this.gradeService.saveGradeInRepository(subject.getGrade());
        return findSubjectById(subjectId);
    }

    public Subject addStudent(Long subjectId, Long studentId, User user) {
		List<UserRole> permittedRolesOwner = new ArrayList<>();
		permittedRolesOwner.add(UserRole.ADMIN);
		List<UserRole> permittedRolesStudent = new ArrayList<>();
		permittedRolesStudent.add(UserRole.STUDENT);

        User student = this.userService.findUserById(studentId);

        Subject subject = findSubjectById(subjectId);
        Institution institution = subject.getGrade().getInstitution();
        
        PermissionChecker.checkPermission(user, institution.getId(), permittedRolesOwner);
        PermissionChecker.checkPermission(student, institution.getId(), permittedRolesStudent);
        
        StudentSubject studentSubject = new StudentSubject(student, subject);
        student.addSubjectStudent(studentSubject);
        subject.addStudent(studentSubject);
        
        this.studentSubjectRepository.save(studentSubject);
        this.subjectRepository.save(subject);
        this.gradeService.saveGradeInRepository(subject.getGrade());
        return findSubjectById(subjectId);
    }

	public Subject updateSubject(UpdateSubjectInput input, User user) {
		Subject subject = findSubjectById(input.getSubjectId());
		checkAdminPermission(subject, user);
        if (input.getName() != null) {
            subject.setName(input.getName());
		}
        
        if (input.getMimeType() != null && input.getPhoto() != null) {
			subject.setMimeType(input.getMimeType());
			subject.setPhoto(input.getPhoto());
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
	
	public List<Subject> addStudentToSubjectsInGrade(Long gradeId, Long studentId, User user) {
		List<UserRole> permittedRolesOwner = new ArrayList<>();
		permittedRolesOwner.add(UserRole.ADMIN);
		List<UserRole> permittedRolesStudent = new ArrayList<>();
		permittedRolesStudent.add(UserRole.STUDENT);
		
		User student = userService.findUserById(studentId);
		Grade grade = gradeService.findGradeById(gradeId);
		Institution institution = grade.getInstitution();
		
		PermissionChecker.checkPermission(user, institution.getId(), permittedRolesOwner);
        PermissionChecker.checkPermission(student, institution.getId(), permittedRolesStudent);
        
        List<Subject> addedSubjects = new ArrayList<>();
        List<StudentSubject> studentSubjects = new ArrayList<>();
        for(Subject subject : grade.getSubjects()) {
        	StudentSubject studentSubject = new StudentSubject(student, subject);
            boolean result = student.addSubjectStudent(studentSubject);
            if (result) {
            	subject.addStudent(studentSubject);
            	studentSubjects.add(studentSubject);
                addedSubjects.add(subject);
            }
        }
        
        this.studentSubjectRepository.saveAll(studentSubjects);
        this.subjectRepository.saveAll(addedSubjects);
        return addedSubjects;
    }
	
	public boolean removeInstitutionSubjectsFromUser(Long institutionId, Long studentId, User user) {
		boolean result = false;
		List<UserRole> permittedRolesOwner = new ArrayList<>();
		permittedRolesOwner.add(UserRole.ADMIN);
		
		PermissionChecker.checkPermission(user, institutionId, permittedRolesOwner);
		User student = userService.findUserById(studentId);
		List<StudentSubject> toBeDeleted = new ArrayList<>();
		Set<Grade> toBeUpdated = new HashSet<>();
		for(StudentSubject studentSubject: student.getSubjectsStudent()) {
			if(institutionId.equals(studentSubject.getSubject().getGrade().getInstitution().getId())) {
				studentSubject.getSubject().getGrade().completlyRemoveStudent(student);
				toBeUpdated.add(studentSubject.getSubject().getGrade());
				toBeDeleted.add(studentSubject);
			}
		}
		this.studentSubjectRepository.deleteAll(toBeDeleted);
		this.gradeService.saveGradresInRepository(toBeUpdated);
		result = true;
		return result;
	}
	
	public boolean removeTeacherFromInstitutionSubjects(Long institutionId, Long teacherId, User user) {
		boolean result = false;
		List<UserRole> permittedRolesOwner = new ArrayList<>();
		permittedRolesOwner.add(UserRole.ADMIN);
		
		PermissionChecker.checkPermission(user, institutionId, permittedRolesOwner);
		User teacher = userService.findUserById(teacherId);
		Set<Grade> gradesToBeUpdated = new HashSet<>();
		List<Subject> subjectsToBeUpdated = new ArrayList<>();
		for(Subject subject : teacher.getSubjects()) {
			if(institutionId.equals(subject.getGrade().getInstitution().getId())) {
				subject.removeTeacher(teacher);
				result = teacher.removeSubject(subject);
				subjectsToBeUpdated.add(subject);
				gradesToBeUpdated.add(subject.getGrade());
			}
		}
		
		subjectRepository.saveAll(subjectsToBeUpdated);
		userService.saveUserInRepository(teacher);
		this.gradeService.saveGradresInRepository(gradesToBeUpdated);
		
		return result;
	}
	
	public boolean removeStudentFromSubject(Long subjectId, Long studentId, User user) {
		boolean result = false;
		List<UserRole> permittedRolesOwner = new ArrayList<>();
		permittedRolesOwner.add(UserRole.ADMIN);
		Subject subject = findSubjectById(subjectId);
		Long institutionId = subject.getGrade().getInstitution().getId();
		PermissionChecker.checkPermission(user, institutionId, permittedRolesOwner);
		
		StudentSubject studentSubject = this.studentSubjectService.findStudentSubject(studentId, subjectId);
		User student = studentSubject.getUser();
		this.studentSubjectRepository.delete(studentSubject);
		result = true;
		subject.getGrade().removeStudent(student);
		this.gradeService.saveGradeInRepository(subject.getGrade());
		return result;
	}
	
	public boolean removeEveryStudentFromSubject(Long subjectId, User user) {
		boolean result = true;
		Subject subject = findSubjectById(subjectId);
		for(StudentSubject studSub : subject.getStudents()) {
			result = result && removeStudentFromSubject(subjectId, studSub.getUser().getId(), user);
		}
		return result;
	}
	
	public boolean removeTeacherFromSubject(Long subjectId, Long teacherId, User user) {
		boolean result = false;
		List<UserRole> permittedRolesOwner = new ArrayList<>();
		permittedRolesOwner.add(UserRole.ADMIN);
		Subject subject = findSubjectById(subjectId);
		Long institutionId = subject.getGrade().getInstitution().getId();
		PermissionChecker.checkPermission(user, institutionId, permittedRolesOwner);
		User teacher = null;
		for(User t: subject.getTeachers()) {
			if (t.getId() == teacherId) {
				teacher = t;
				break;
			}
		}
		if (teacher != null) {
			subject.removeTeacher(teacher);
			result = teacher.removeSubject(subject);
			subjectRepository.save(subject);
			userService.saveUserInRepository(teacher);
			this.gradeService.saveGradeInRepository(subject.getGrade());
		}
		return result;
	}
	
	public Subject copyStudentsFromSubject(Long fromId, Long toId, User user) {
		Subject sub = findSubjectById(fromId);
		for (StudentSubject studSub: sub.getStudents()) {
			addStudent(toId, studSub.getUser().getId(), user);
		}
		return findSubjectById(toId);
	}

	public void saveSubjectInRepository(Subject subject) {
		this.subjectRepository.save(subject);
	}

}
