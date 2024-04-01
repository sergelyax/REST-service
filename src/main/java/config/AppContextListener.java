package config;

import dao.PostDAO;
import dao.UserDAO;
import mapper.PostMapper;
import mapper.UserMapper;
import service.PostService;
import service.UserService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        UserDAO userDAO = new UserDAO();
        UserMapper userMapper = new UserMapper();
        UserService userService = new UserService(userDAO, userMapper);
        sce.getServletContext().setAttribute("userService", userService);

        PostDAO postDAO = new PostDAO();
        PostMapper postMapper = new PostMapper();
        PostService postService = new PostService(postDAO, postMapper);
        sce.getServletContext().setAttribute("postService", postService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        //
    }
}
