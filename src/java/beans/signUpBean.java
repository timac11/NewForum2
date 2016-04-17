/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import DAO.Factory;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Date;
import java.util.Locale;

//For send a message
import java.util.Properties;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import logic.User;
import static logic.hash_password.md5Apache;
import helpers.*;
import javax.faces.context.FacesContext;

@ManagedBean(name = "signUpBean")
@SessionScoped
public class SignUpBean implements Serializable {

    @ManagedProperty(value = "#{loginBean}")
    private LoginBean loginBean;
    private String name;
    private String password;
    private String repeatPassword;
    private String inputCode;
    private String email;
    private String secretCode;
    private UIComponent mybutton;
    private static long id = 7;
    private boolean isSended = false;

    public boolean getIsSended() {
        return isSended;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public void setMybutton(UIComponent mybutton) {
        this.mybutton = mybutton;
    }

    public UIComponent getMybutton() {
        return mybutton;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInputCode() {
        return inputCode;
    }

    public void setInputCode(String inputCode) {
        this.inputCode = inputCode;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String createSecretCode() {
        Random r = new Random();
        String code = Integer.toString(Math.abs(r.nextInt()));
        return code;
    }

    public void sendEmailMess() throws SQLException {
        if (this.create()) {
            final String username = "teamforumnetcrec@gmail.com";
            final String password = "7894Jhfgbeio";
            secretCode = createSecretCode();
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("teamforumnetcrec@gmail.com"));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(email));
                message.setSubject("Team Forum");
                // message.setText("<i>Hello</i> Dear friend, it's a code for verification : " + secretCode);
                message.setContent("<span style=\"font-family:Lucida Sans Unicode; font-weight: bold; font-style:italic; font-size:20px;font-variant:small-caps; color:CornflowerBlue\"><i><h2>Hello Dear friend!</h2></i><h3>it's a code for verification :</h3>" + secretCode + "</span>", "text/html");
                Transport.send(message);
                isSended = true;
                System.out.println("Done");

            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public String verification() throws SQLException {
        if (inputCode.equals(secretCode)) {
            Locale.setDefault(Locale.ENGLISH);
            User s1 = new User();
            //s1.setId(++id);
            s1.setName(this.getName());
            s1.setPassword(md5Apache(this.getPassword(), this.name, this.email));
            s1.setRights("User");
            s1.setEmail(this.email);
            Date d = new Date(199);
            s1.setDate(d);
            if (Factory.getInstance().getUserDAO().addUser(s1)){
            doLogin();
            return PageNavigation.toIndex;
            }
            else {
                return "error-page.xhtml";
            }
        } else {
            return PageNavigation.toLogin;
        }

    }

    public boolean create() throws SQLException {
        Locale.setDefault(Locale.ENGLISH);
        if (!this.password.equals(this.repeatPassword)) {
            FacesMessage message = new FacesMessage("Incorrect password or email. Try again");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(mybutton.getClientId(context), message);
            return false;
        }

        String EMAIL_PATTERN
                = "^[_A-Za-z0-9-]+(\\."
                + "[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*"
                + "(\\.[A-Za-z]{2,})$";
        Pattern pattern;
        pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(this.email);

        if (!matcher.matches()) {
            FacesMessage msg = new FacesMessage("Invalid email format. Try again");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(mybutton.getClientId(context), msg);
            return false;
        }
        if (!Factory.getInstance().getUserDAO().getUserByName(this.name).getName().equals("")) {
            FacesMessage msg = new FacesMessage("Sorry, this nick is used. Try again");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(mybutton.getClientId(context), msg);
            return false;
        }
        if (Factory.getInstance().getUserDAO().getUserByName(this.name).getName().equals("_")) {
            FacesMessage msg = new FacesMessage("Sorry, system error. Try again");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(mybutton.getClientId(context), msg);
            return false;
        }
        if (!Factory.getInstance().getUserDAO().getUserByMale(this.email).getName().equals("")) {
            FacesMessage msg = new FacesMessage("Sorry, but this mail is used. Try again");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(mybutton.getClientId(context), msg);
            return false;
        }
        return true;
    }

    private void doLogin() {
        loginBean.setIsLogged(true);
        loginBean.setName(name);
        loginBean.setPassword(password);
    }

    public SignUpBean() {
    }
}
