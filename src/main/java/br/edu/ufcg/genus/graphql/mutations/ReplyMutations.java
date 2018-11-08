package br.edu.ufcg.genus.graphql.mutations;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;

import br.edu.ufcg.genus.exception.InvalidAttributesException;
import br.edu.ufcg.genus.inputs.ReplyCreationInput;
import br.edu.ufcg.genus.inputs.ReplyToReplyInput;
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
	
	public Reply replyToReply(ReplyToReplyInput input) {
		return replyService.replyToReply(input, userService.findLoggedUser());
	}

	public Boolean removeReply(Long replyId) {
		return this.replyService.removeReply(replyId, userService.findLoggedUser());
	}

	public Reply updateReply(UpdateReplyInput input) {

        Set<ConstraintViolation<UpdateReplyInput>> violations = validator.validate(input);
        if (violations.size() > 0) {
            Map<String, Object> extensions = new HashMap<>();
            violations.forEach((ConstraintViolation<UpdateReplyInput> v) -> {
                extensions.put(v.getMessage(), v.getMessage());
            });
            throw new InvalidAttributesException("Invalidattributes passed", extensions);
        }

        return replyService.updateReply(input, userService.findLoggedUser());
    }
}
