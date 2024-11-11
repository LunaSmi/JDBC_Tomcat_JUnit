package servlets;

import dao.CategoryDao;
import entities.Category;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import utils.JspHelper;
import java.util.List;


@WebServlet("/categories")
public class CategoriesServlet  extends HttpServlet {

    private final CategoryDao categoryDao = CategoryDao.getInstance();

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)  {
        List<Category> list = categoryDao.findAll();

        req.setAttribute("list", list);
        req.getRequestDispatcher(JspHelper.getPath("categories"))
                .forward(req, resp);

    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        //from body raw(text)
        String name;
        try(var reader = req.getReader()) {
            name= reader.readLine();
        }
        categoryDao.save(new Category(name));

    }
}
