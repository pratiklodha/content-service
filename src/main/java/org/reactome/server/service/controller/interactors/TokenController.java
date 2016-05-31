package org.reactome.server.service.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.reactome.server.interactors.model.Interaction;
import org.reactome.server.service.manager.CustomInteractorManager;
import org.reactome.server.service.manager.InteractionManager;
import org.reactome.server.service.model.interactors.Interactors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
@Api(tags = "interactors", description = "Molecule interactors")
@RequestMapping(value = "/interactors/token")
@RestController
public class TokenController {

    private static final String CUSTOM_RESOURCE_NAME = "custom";

    @Autowired
    public CustomInteractorManager customInteractionManager;

    @Autowired
    public InteractionManager interactionManager;

    @ApiOperation(value = "Retrieve custom interactions associated with a token", response = Interactors.class, produces = "application/json")
    @RequestMapping(value = "/{token}", method = RequestMethod.POST, produces = "application/json", consumes = "text/plain")
    @ResponseBody
    public Interactors getInteractors(@ApiParam(value = "A token associated with a data submission", required = true)
                                      @PathVariable String token,
                                      @ApiParam(value = "Interactors accessions", required = true)
                                      @RequestBody String proteins) {

        /** Split param and put into a Set to avoid duplicates **/
        Set<String> accs = new HashSet<>(Arrays.asList(proteins.split("\\s*,\\s*")));

        Map<String, List<Interaction>> interactionMap = customInteractionManager.getInteractionsByTokenAndProteins(token, accs);

        return interactionManager.getCustomInteractionResult(interactionMap, CUSTOM_RESOURCE_NAME, token);
    }
}