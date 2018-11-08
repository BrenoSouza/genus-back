package br.edu.ufcg.genus.graphql.mutations;

import org.springframework.beans.factory.annotation.Autowired;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;

import br.edu.ufcg.genus.inputs.CreateAdvancedEntryCodeInput;
import br.edu.ufcg.genus.inputs.CreateEntryCodeInput;
import br.edu.ufcg.genus.models.Institution;
import br.edu.ufcg.genus.services.EntryCodeService;
import br.edu.ufcg.genus.services.UserService;

public class EntryCodeMutations implements GraphQLMutationResolver{
	
	@Autowired
	private EntryCodeService entryCodeService;
	@Autowired
	private UserService userService;
	
	public String createEntryCode(CreateEntryCodeInput input) {
		return this.entryCodeService.createEntryCode(input, userService.findLoggedUser());
	}
	
	public String createAdvancedEntryCode(CreateAdvancedEntryCodeInput input) {
		return this.entryCodeService.createAdvancedEntryCode(input, userService.findLoggedUser());
	}
	
	public Institution joinInstitution(String code) {
		return this.entryCodeService.joinInstitution(code, userService.findLoggedUser());
	}
}
