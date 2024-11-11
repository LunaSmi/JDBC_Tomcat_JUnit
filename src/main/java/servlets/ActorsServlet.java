package servlets;


import dao.ActorDao;
import entities.Actor;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import utils.JspHelper;
import java.util.List;



@WebServlet("/actors")
public class ActorsServlet extends HttpServlet {

    private final ActorDao actorDao = ActorDao.getInstance();

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    {
        List<Actor> list = actorDao.findAll();

        req.setAttribute("list",list);
        req.getRequestDispatcher(JspHelper.getPath("actors"))
                .forward(req, resp);

    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
    {
        //from body raw(text)
        String full_name;
        try(var reader = req.getReader()) {
            full_name= reader.readLine();
        }
        actorDao.save(new Actor(full_name));



    }
}
