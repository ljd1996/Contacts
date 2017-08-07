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

@WebServlet(name = "SearchByNum")
public class SearchByNum extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        ContactDao contactDao = new ContactDao();
        ContactBean contact = contactDao.getContactByNumber(request.getParameter("number"));
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("status",(contact==null)?1:0);
            jsonObject.put("data", JSONArray.fromObject(contact));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(jsonObject.toString());
        response.getWriter().write(jsonObject.toString());
    }
}
