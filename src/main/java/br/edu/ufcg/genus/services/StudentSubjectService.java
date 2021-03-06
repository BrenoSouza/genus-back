package br.edu.ufcg.genus.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ufcg.genus.exception.InvalidIDException;
import br.edu.ufcg.genus.models.StudentSubject;
import br.edu.ufcg.genus.models.StudentSubjectId;
import br.edu.ufcg.genus.repositories.StudentSubjectRepository;

@Service
public class StudentSubjectService {
	
	@Autowired
	private StudentSubjectRepository studentSubjectRepository;
	
	public StudentSubject findStudentSubject(Long userId, Long subjectId) {
		StudentSubjectId ssid = new StudentSubjectId(userId, subjectId);
		StudentSubject studentSubject = studentSubjectRepository.findById(ssid)
				.orElseThrow(() -> new InvalidIDException("StudentSubject with passed ID was not found", ssid));
		return studentSubject;
	}
	
	public StudentSubject saveStudentSubject(StudentSubject studentSubject) {
		return this.studentSubjectRepository.save(studentSubject);
	}

}
