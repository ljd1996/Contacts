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

@WebServlet(name = "AddContact")
public class AddContact extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        ContactDao contactDao = new ContactDao();
        ContactBean contact = new ContactBean();
        contact.setName(request.getParameter("name"));
        contact.setNumber(request.getParameter("number"));
        contact.setPhone(request.getParameter("phone"));
        int status = contactDao.addContact(contact);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("status",status);
            jsonObject.put("page_size", contactDao.getPageSum());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        response.getWriter().write(jsonObject.toString());
    }
}
