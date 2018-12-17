package br.edu.ufcg.genus.graphql.mutations;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;

import br.edu.ufcg.genus.exception.InvalidAttributesException;
import br.edu.ufcg.genus.inputs.ReplyCreationInput;
import br.edu.ufcg.genus.models.Reply;
import br.edu.ufcg.genus.services.ReplyService;
import br.edu.ufcg.genus.services.UserService;
import br.edu.ufcg.genus.update_inputs.UpdateReplyInput;

public class ReplyMutations implements GraphQLMutationResolver {
	
	@Autowired
	private ReplyService replyService;
	@Autowired
    private Validator validator;
	@Autowired
	private UserService userService;

	public Reply createReply(ReplyCreationInput input) {
		return replyService.createReply(input, userService.findLoggedUser());
	}

	public Boolean removeReply(Long replyId) {
		return this.replyService.removeReply(replyId, userService.findLoggedUser());
	}

	public Reply updateReply(UpdateReplyInput input) {

        Set<ConstraintViolation<UpdateReplyInput>> violations = validator.validate(input);
        if (violations.size() > 0) {
        	List<String> errors = new ArrayList<>();
            violations.forEach((ConstraintViolation<UpdateReplyInput> v) -> {
                errors.add(v.getMessage());
            });
            throw new InvalidAttributesException("Invalidattributes passed", errors);
        }

        return replyService.updateReply(input, userService.findLoggedUser());
    }
}
