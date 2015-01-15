package it.fge.dtimes.test;

import it.fge.dtimes.model.user.*;

import org.springframework.context.*;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class TestUserAdministrator {

	public static void main(String[] args) {
		System.out.println("TestUserAdministrator");
		ApplicationContext ctx = null;
		try {
			ctx = new FileSystemXmlApplicationContext("WebContent/WEB-INF/spring-application-context.xml");

			saveUser(ctx);
			// getUserByUsername(ctx);

		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			if (ctx != null) {
				((ConfigurableApplicationContext) ctx).close();
			}
		}
		System.out.println("done");

	}

	protected static void saveUser(ApplicationContext ctx) {
		System.out.println("saveUser");
		UserDAO userDAO = ctx.getBean(UserDAO.class);

		UserDTO userDTO = new UserDTO();
		userDTO.setUsername("fernando");
		userDTO.setPassword("fernando");
		userDTO.setName("Fernando Giuseppe");
		userDTO.setSurname("Evangelisti");

		userDAO.save(userDTO);
	}

	protected static void getUserByUsername(ApplicationContext ctx) {
		System.out.println("getUserByUsername");
		UserDAO userDAO = ctx.getBean(UserDAO.class);
		UserDTO user = userDAO.findByUsername("fernando");
		System.out.println("User password : " + user.getPassword());

	}

}
