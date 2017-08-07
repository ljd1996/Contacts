package servlet;

import dao.ContactDao;
import entity.ContactBean;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UpdateContact")
public class UpdateContact extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        ContactDao contactDao = new ContactDao();
        ContactBean contact = new ContactBean();
        contact.setId(Integer.valueOf(request.getParameter("id")));
        contact.setName(request.getParameter("name"));
        contact.setNumber(request.getParameter("number"));
        contact.setPhone(request.getParameter("phone"));
        int status = contactDao.updateContact(contact);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("status",status);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        response.getWriter().write(jsonObject.toString());
    }
}
