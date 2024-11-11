package servlets;

import dao.CategoryDao;
import entities.Category;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import utils.JspHelper;
import java.util.Optional;


@WebServlet("/category")
public class CategoryServlet extends HttpServlet {

    private final CategoryDao categoryDao = CategoryDao.getInstance();

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)  {

        int id = Integer.parseInt(req.getParameter("id"));
        Optional<Category> category = categoryDao.findById(id);
        req.setAttribute("category",category);
        req.getRequestDispatcher(JspHelper.getPath("category"))
                .forward(req, resp);

    }

    @SneakyThrows
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {

        Category category = new Category();

        //from body raw(text)
        try(var reader = req.getReader()) {
            var line = reader.readLine();
             category.setId(Integer.parseInt(line));
             line = reader.readLine();
             category.setName(line);
        }
        categoryDao.update(category);

    }

    @SneakyThrows
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)  {
        int id = Integer.parseInt(req.getParameter("id"));
        categoryDao.delete(id);
    }
}

