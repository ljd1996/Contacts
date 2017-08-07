package dao;

import entity.ContactBean;
import util.DBHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ContactDao {

    public final static int PAGE_NUM = 10;
    public final static int STATUS_SUCCESS = 0;
    public final static int STATUS_FAILED = 1;
    public final static int STATUS_ILLEGAL = 2;
    public final static int STATUS_UNCOMPLETE = 3;

    /**
     * 获得所有的联系人数据
     * @return
     */
    public List<ContactBean> getAllContact(){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<ContactBean> contactList = new ArrayList<ContactBean>();
        try {
            conn = DBHelper.getConnection();
            String sql = "SELECT * FROM contacts";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()){
                ContactBean contact = new ContactBean();
                contact.setId(rs.getInt("id"));
                contact.setName(rs.getString("name"));
                contact.setNumber(rs.getString("number"));
                contact.setPhone(rs.getString("phone"));
                contactList.add(contact);
            }
            return contactList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    /**
     * 根据指定学号获取联系人
     * @param number
     * @return
     */
    public ContactBean getContactByNumber(String number) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.getConnection();
            String sql = "SELECT * FROM contacts where number="+"\""+number+"\";";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            ContactBean contact = new ContactBean();
            if (rs.next()){
                contact.setId(rs.getInt("id"));
                contact.setName(rs.getString("name"));
                contact.setNumber(rs.getString("number"));
                contact.setPhone(rs.getString("phone"));
                return contact;
            }
            contact.setId(-1);
            return contact;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    /**
     * 根据指定姓名获取联系人
     * @param name
     * @return
     */
    public List<ContactBean> getContactByName(String name) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<ContactBean> resultList = new ArrayList<ContactBean>();
        try {
            conn = DBHelper.getConnection();
            String sql = "SELECT * FROM contacts where name="+"\""+name+"\";";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()){
                ContactBean contact = new ContactBean();
                contact.setId(rs.getInt("id"));
                contact.setName(rs.getString("name"));
                contact.setNumber(rs.getString("number"));
                contact.setPhone(rs.getString("phone"));
                resultList.add(contact);
            }
            return resultList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    /**
     * 根据指定id获取联系人
     * @param id
     * @return
     */
    public ContactBean getContactById(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.getConnection();
            String sql = "SELECT * FROM contacts where id=?;";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            if (rs.next()) {
                ContactBean contact = new ContactBean();
                contact.setId(rs.getInt("id"));
                contact.setName(rs.getString("name"));
                contact.setNumber(rs.getString("number"));
                contact.setPhone(rs.getString("phone"));
                return contact;
            }
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    /**
     * 添加联系人
     * @param contact
     */
    public int addContact(ContactBean contact){
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DBHelper.getConnection();
            stmt = conn.createStatement();

            String sql_check = "SELECT number FROM contacts WHERE number="+"\""+contact.getNumber()+"\";";

            ResultSet rs = stmt.executeQuery(sql_check);
            if (rs.next()){
                return STATUS_ILLEGAL;
            }

            String sql = "INSERT INTO contacts VALUES(NULL,"+"\""+contact.getName()+"\""+","+"\""+contact.getNumber()
                    +"\""+","+"\""+contact.getPhone()+"\""+");";
            if (stmt.executeUpdate(sql)>0) {
                System.out.println("成功");
                return STATUS_SUCCESS;
            }
            return STATUS_FAILED;
        } catch (Exception e) {
            e.printStackTrace();
            return STATUS_FAILED;
        }finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    /**
     * 通过指定id删除联系人
     * @param id
     */
    public int deleteContactById(int id){
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DBHelper.getConnection();
            String sql = "DELETE FROM contacts WHERE id="+id+";";
            stmt = conn.prepareStatement(sql);
            if (stmt.executeUpdate()>0) {
                return STATUS_SUCCESS;
            }
            return STATUS_FAILED;
        } catch (Exception e) {
            e.printStackTrace();
            return STATUS_FAILED;
        }finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    /**
     * 更新指定联系人数据
     * @param contact
     */
    public int updateContact(ContactBean contact){
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DBHelper.getConnection();

            stmt = conn.createStatement();

            String sql_check = "SELECT number FROM contacts WHERE number="+"\""+contact.getNumber()+"\" AND id!="+contact.getId()+";";
            ResultSet rs = stmt.executeQuery(sql_check);
            if (rs.next()){
                return STATUS_ILLEGAL;
            }

            String sql = "UPDATE contacts SET name="+"\""+contact.getName()+"\""+",number="+"\""+contact.getNumber()+"\""+
                    ",phone="+"\""+contact.getPhone()+"\""+" WHERE id="+contact.getId()+";";
            if (stmt.executeUpdate(sql)>0) {
                return STATUS_SUCCESS;
            }
            return STATUS_FAILED;
        } catch (Exception e) {
            e.printStackTrace();
            return STATUS_FAILED;
        }finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取页面总数
     * @return
     */
    public int getPageSum(){
        List<ContactBean> list = getAllContact();
        int sum = (list.size()%PAGE_NUM)==0?list.size()/PAGE_NUM:list.size()/PAGE_NUM+1;
        return (sum==0)?1:sum;
    }

    /**
     * 获取指定页面的元素
     * @param page
     * @return
     */
    public List<ContactBean> getPageContacts(int page){
        List<ContactBean> list = getAllContact();
        List<ContactBean> page_list = new ArrayList<>();
        if (page>getPageSum()||page<1||list==null){
            return null;
        }
        if (list.size()==0){
            return page_list;
        }
        if (page==getPageSum()){
            page_list = list.subList((page-1)*PAGE_NUM,list.size());
        } else {
            page_list = list.subList((page-1)*PAGE_NUM,page*PAGE_NUM);
        }
        return page_list;
    }
}
