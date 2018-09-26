package br.edu.ufcg.genus.graphql.mutations;

import org.springframework.beans.factory.annotation.Autowired;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;

import br.edu.ufcg.genus.inputs.CreateEntryCodeInput;
import br.edu.ufcg.genus.services.EntryCodeService;

public class EntryCodeMutations implements GraphQLMutationResolver{
	
	@Autowired
	private EntryCodeService entryCodeService;
	
	public String createEntryCode(CreateEntryCodeInput input) {
		return this.entryCodeService.createEntryCode(input);
	}
}
