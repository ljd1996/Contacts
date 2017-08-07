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
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "SearchByName")
public class SearchByName extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        ContactDao contactDao = new ContactDao();
        List<ContactBean> contactList = contactDao.getContactByName(request.getParameter("name"));
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("status",(contactList==null)?1:0);
            jsonObject.put("data", JSONArray.fromObject(contactList));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        response.getWriter().write(jsonObject.toString());
    }
}
