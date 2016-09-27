package concord.service;


import concord.entity.EUser;
import concord.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by aboieriu on 9/13/16.
 */
@RestController
public class MainConcordController {

    @Autowired
    private IUserRepository userRepository;

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }
}
