package servlets;

import dao.ActorDao;
import entities.Actor;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import java.util.Optional;



@WebServlet("/actor")
public class ActorServlet extends HttpServlet {
    private final ActorDao actorDao = ActorDao.getInstance();

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)  {
        Optional<Actor> actor = actorDao.findById(Integer.parseInt(req.getParameter("id")));

    }

    @SneakyThrows
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        Actor actor = new Actor();

        //from body raw(text)
        try(var reader = req.getReader()) {
            var line = reader.readLine();
            actor.setId(Integer.parseInt(line));
            line = reader.readLine();
            actor.setFullName(line);
        }
        actorDao.update(actor);

    }

    @SneakyThrows
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)  {
        var id = Integer.parseInt(req.getParameter("id"));
        actorDao.delete(id);

    }
}
