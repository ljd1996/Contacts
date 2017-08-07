package servlet;

import dao.ContactDao;
import entity.ContactBean;
import net.sf.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "DeleteContact")
public class DeleteContact extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        ContactDao contactDao = new ContactDao();
        int status = contactDao.deleteContactById(Integer.valueOf(request.getParameter("id")));
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("status",status);
            jsonObject.put("page_size", contactDao.getPageSum());
            jsonObject.put("contact_size", (contactDao.getAllContact().size()==0)?1:contactDao.getAllContact().size());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        response.getWriter().write(jsonObject.toString());
    }
}
