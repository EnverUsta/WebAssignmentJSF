
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.enterprise.context.RequestScoped;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author DELL
 */
@ManagedBean
@RequestScoped
public class User {
    static final String databaseConnectionUrl = "jdbc:derby://localhost:1527/DigiMeet";
    String name;
    String surname;
    String userName;
    String country;
    String gender;
    static String e_mail;
    static String password;
    String post;
    List<String> myPosts = new ArrayList<>();

    boolean control_of_accept;   // hükmlülük yasal şeyleri kabul etme tiki

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String passwordString) {
        password = passwordString;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getE_mail() {
        return e_mail;
    }

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public boolean isControl_of_accept() {
        return control_of_accept;
    }

    public void setControl_of_accept(boolean control_of_accept) {
        this.control_of_accept = control_of_accept;
    }
    
    public String signUp(){
        try{
            Connection conn = DriverManager.getConnection(databaseConnectionUrl);
            PreparedStatement pSt = conn.prepareStatement("insert into USERS values(?,?,?,?,?)");
            
            
            pSt.setString(1, name);
            pSt.setString(2, surname);
            pSt.setString(3, e_mail);
            pSt.setString(4, password);
            pSt.setString(5, gender);
            pSt.executeUpdate();
            
            pSt.close();
            conn.close();
        }catch(SQLException e)
        {
            e.getErrorCode();
        }
        return "login?faces-redirect=true";  
    }
    
    public String goSignup() {
        return "signup?faces-redirect=true";
    }

    public String goLogin() {
        return "login?faces-redirect=true";
    }

    public String goForgotPassWord() {
        return "forgotPassword?faces-redirect=true";
    }

    public String goNewPassWord() {
        return "newPassword?faces-redirect=true";
    }

    public String goProfile() {
        return "profile?faces-redirect=true";
    }

    public String goAccount() {
        return "account?faces-redirect=true";
    }

    public String goNewPost() {
        return "newPost?faces-redirect=true";
    }

    public String goFriends() {
        return "friends?faces-redirect=true";
    }
    
    public String goMyFriends()
    {
        return "myFriends?faces-redirect=true";
    }

    public String goMainPage() {
        return "mainPage?faces-redirect=true";
    }

    public String goTrends() {
        return "trends?faces-redirect=true";
    }

    public String func() {
        return "<p style=\"background-color:yellow;width:200px;"
                    + "padding:5px\">Name: " + getName() + "<br/>E-Mail: "
                + getE_mail() + "</p>";
    }
    
    public String logOff()
    {
        e_mail = null;
        password = null;
        return "login?faces-redirect=true";
    }

    public String fetchUserName(){
        String returnName = "abc";
        try{
            Connection conn = DriverManager.getConnection(databaseConnectionUrl);
            PreparedStatement pst = conn.prepareStatement("select * from users where email=?");
            pst.setString(1, e_mail);
            ResultSet rs = pst.executeQuery();
            while(rs.next())
            {
                returnName = rs.getString("NAME");
            }
            conn.close();
            pst.close();
            rs.close();
        }catch(SQLException e)
        {
            System.out.println("An error occured");
        }
        return returnName;
    }
    
    public String fetchSurname()
    {
        String returnSurName = "abc";
        try{
            Connection conn = DriverManager.getConnection(databaseConnectionUrl);
            PreparedStatement pst = conn.prepareStatement("select * from users where email=?");
            pst.setString(1, e_mail);
            ResultSet rs = pst.executeQuery();
            while(rs.next())
            {
                returnSurName = rs.getString("SURNAME");
            }
            conn.close();
            pst.close();
            rs.close();
        }catch(SQLException e)
        {
            System.out.println("An error occured");
        }
        return returnSurName;
    }
    
    public String controlForgot()
    {
        String controlUserName = fetchUserName();
        if(controlUserName.equals(userName)){
            return "newPassword?faces-redirect=true";
        }
        return "forgotPassword?faces-redirect=true";
    }
    
    public String share() {      // new post  işmeleri else in içinde database işlemeleri yapıalcak
        if (post.isEmpty()) {
            return "newPost?faces-redirect=true";
        } else {
            try{
                Connection conn = DriverManager.getConnection(databaseConnectionUrl);
                PreparedStatement pSt = conn.prepareStatement("insert into POSTS values(?,?,?)");
                pSt.setString(1, e_mail);
                pSt.setString(2, password);
                pSt.setString(3, post);
                pSt.executeUpdate();
                conn.close();
                pSt.close();
                return "mainPage?faces-redirect=true";
            }catch(SQLException e)
            {
                return "newPost?faces-redirect=true";
            }
        }
    }
    
    public void getMyPostsFromDatabase()
    {
        try{
            Connection conn = DriverManager.getConnection(databaseConnectionUrl);
            PreparedStatement pst = conn.prepareStatement("select * from posts where email=? and password=?");
            pst.setString(1, e_mail);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();
            myPosts.clear();
            while(rs.next())
            {
                String added = rs.getString("POST");
                myPosts.add(added);
            }
            pst.close();
            rs.close();
            conn.close();
        }
        catch(SQLException e)
        {
            System.out.println("An error occured");
        }
    }
    
    public List<String> getMyPosts()
    {
        getMyPostsFromDatabase();
        return myPosts;
    }
    
    public List<String> getMyFriendsFromDatabase()
    {
        List<String> myFriends = new ArrayList<>();
        try{
            Connection conn = DriverManager.getConnection(databaseConnectionUrl);
            PreparedStatement pst = conn.prepareStatement("select friend2 from friends where friend1=?");
            pst.setString(1, e_mail);
            ResultSet rs = pst.executeQuery();
            while(rs.next())
            {
                myFriends.add(rs.getString(1));
            }
            conn.close();
            pst.close();
            rs.close();
        }catch(SQLException e)
        {
          System.out.println("An error occurred");  
        }
        return myFriends;
    }
    
    public List<String> getMyFriendsPosts(){
        List<String> myFriendsPosts = new ArrayList<>();
        try{
            Connection conn = DriverManager.getConnection(databaseConnectionUrl);
            PreparedStatement pst = conn.prepareStatement("SELECT POSTS.POST from posts INNER JOIN FRIENDS ON POSTS.EMAIL = FRIENDS.FRIEND2 and FRIEND1=?");
            pst.setString(1, e_mail);
            ResultSet rs = pst.executeQuery();
            while(rs.next())
            {
                myFriendsPosts.add(rs.getString(1));
            }
            conn.close();
            pst.close();
            rs.close();
        }catch(SQLException e)
        {
            System.out.println("An error occured");
        }
        return myFriendsPosts;
    }
    
    public String addFriend() throws SQLException
    {
        //Isthere This kind of friend
        boolean isFound = false;
        Connection conn = DriverManager.getConnection(databaseConnectionUrl);
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select email from users");
        while(rs.next())
        {
            if(userName.equalsIgnoreCase(rs.getString(1))){
                isFound = true;
                break;
            }
        }
        boolean isAlreadyAdded = false;
        PreparedStatement st2 = conn.prepareStatement("select friend2 from FRIENDS where friend1=?");
        st2.setString(1, e_mail);
        ResultSet rs2 = st2.executeQuery();
        while(rs2.next())
        {
            if(userName.equals(rs2.getString(1))){
                isAlreadyAdded = true;
                break;
            }
        }
        st2.close();
        rs2.close();
        if(isFound && !isAlreadyAdded){
            PreparedStatement pst = conn.prepareStatement("insert into FRIENDS values(?,?)");
            pst.setString(1, e_mail);
            pst.setString(2, userName);
            pst.executeUpdate();
            return "profile?faces-redirect=true";
        }
        return "friends?faces-redirect=true";
    }

    public String updateAccount() {   //profil->account ||  burda yeni veri yazmış olanları (!isEmpty()) ile olabilir mesela  kontrol edip dataBase de güncellicez
        //country yi kontrol ederken seçili şehir databasede ki ile aynı değilse update edilicek

        return "account?faces-redirect=true";   // başka returne gerek yok bu değişmicek, sadece buranın üstünded dataBase işlemelri olucak
    }

    public String updatePassword() throws SQLException {   //databasede şifreyei güncellicez

        if (password.isEmpty()) {
            return "newPassword?faces-redirect=true";
        } else {
            Connection conn = DriverManager.getConnection(databaseConnectionUrl);
            PreparedStatement pst = conn.prepareStatement("Update USERS set password=? where email=?");
            pst.setString(1, password);
            pst.setString(2, e_mail);
            pst.executeUpdate();
            return "login?faces-redirect=true";
        }
    }
   
    public String login(){  //else if de database de  password ve username uyuşuyomu diye bakıcaz uyuşuyorsa return ile mainPage ekranına yönlendir
        //burası değişicek if(database işlemleri okeyse)-> mainPage sayfasına  birde else ile login sayfasına
        if ((e_mail.isEmpty()) || (password.isEmpty())) {
            return "login?faces-redirect=true";//"Username or password is empty!";
        } else {
            try{
                Connection conn = DriverManager.getConnection(databaseConnectionUrl);
                Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
                ResultSet rs = st.executeQuery("SELECT EMAIL, PASSWORD FROM APP.USERS");
                boolean isFound = false;
                while(rs.next())
                {
                    String fetchEmail = rs.getString(1);
                    String fetchPassword = rs.getString(2);
                    if( e_mail.equals(fetchEmail) && password.equals(fetchPassword)){
                        isFound = true;
                        break;
                    }
                }
                conn.close();
                st.close();
                rs.close();
                if(isFound)
                    return "mainPage?faces-redirect=true";
                else
                    return "login?faces-redirect=true";
            }catch(SQLException e)
            {
                return "login?faces-redirect=true";
            } 
        }
    }
}
