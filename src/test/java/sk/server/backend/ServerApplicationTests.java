package sk.server.backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sk.server.backend.controller.RezervationController;
import sk.server.backend.controller.UserController;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class ServerApplicationTests {


	@Autowired
	private UserController userController;

	@Autowired
	private RezervationController rezervationController;
	@Test
	void contextLoads() throws Exception {
		assertThat(userController).isNotNull();
		assertThat(rezervationController).isNotNull();
	}

}
