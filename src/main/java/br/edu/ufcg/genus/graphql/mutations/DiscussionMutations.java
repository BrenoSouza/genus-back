package br.edu.ufcg.genus.graphql.mutations;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;

import br.edu.ufcg.genus.exception.InvalidAttributesException;
import br.edu.ufcg.genus.inputs.DiscussionCreationInput;
import br.edu.ufcg.genus.models.Discussion;
import br.edu.ufcg.genus.services.DiscussionService;
import br.edu.ufcg.genus.services.UserService;
import br.edu.ufcg.genus.update_inputs.UpdateDiscussionInput;

public class DiscussionMutations implements GraphQLMutationResolver {
	
	@Autowired
	private DiscussionService forumPostService;
	@Autowired
    private Validator validator;
	@Autowired
	private UserService userService;

	public Discussion createDiscussion(DiscussionCreationInput input) {
		return this.forumPostService.createDiscussion(input, userService.findLoggedUser());
	}

	public Boolean removeDiscussion(Long discussionId) {
		return this.forumPostService.removeDiscussion(discussionId, userService.findLoggedUser());
	}

	public Discussion updateDiscussion(UpdateDiscussionInput input) {

        Set<ConstraintViolation<UpdateDiscussionInput>> violations = validator.validate(input);
        if (violations.size() > 0) {
            Map<String, Object> extensions = new HashMap<>();
            violations.forEach((ConstraintViolation<UpdateDiscussionInput> v) -> {
                extensions.put(v.getMessage(), v.getMessage());
            });
            throw new InvalidAttributesException("Invalidattributes passed", extensions);
        }
        return forumPostService.updateDiscussion(input, userService.findLoggedUser());
    }

}
