package br.edu.ufcg.genus.initialization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import br.edu.ufcg.genus.models.Institution;
import br.edu.ufcg.genus.models.User;
import br.edu.ufcg.genus.models.UserInstitution;
import br.edu.ufcg.genus.models.UserRole;
import br.edu.ufcg.genus.repositories.InstitutionRepository;
import br.edu.ufcg.genus.repositories.UserInstitutionRepository;
import br.edu.ufcg.genus.repositories.UserRepository;


@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private InstitutionRepository institutionRepository;

	@Autowired
	private UserInstitutionRepository userInstitutionRepository;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {

		User admin = new User("administrador", "admin@gmail.com", "123456");
		User prof1 = new User("professor1", "prof1@gmail.com", "123456");
		User prof2 = new User("professor2", "prof2@gmail.com", "123456");

		userRepository.save(admin);
		userRepository.save(prof1);
		userRepository.save(prof2);

		Institution institution = new Institution("Escola", "escola@gmail.com", "Rua qualquer", "838888888");
		
		institutionRepository.save(institution);

		UserInstitution userInstitution = institution.addUser(admin, UserRole.ADMIN);
		
		userInstitutionRepository.save(userInstitution);
		institutionRepository.save(institution);
		userRepository.save(admin);

	}

}