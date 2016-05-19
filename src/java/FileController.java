import beans.ForumBean;
import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.request;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;


@ManagedBean
@ViewScoped
public class FileController implements Serializable {
        private Part file;  
        private String ch;
// private String fileContent;

public String upload(String name) {
       // LoginBean session = (LoginBean) req.getSession().getAttribute("loginBean");
    try (InputStream input = file.getInputStream()) {
       // String name = "";
        //name = session.getName();
        File f = new File("E:\\Git_directory\\NewForum2\\web\\resources\\Avatars\\" + name + ".jpg");
        //File f = new File("E:\\Git_directory\\NewForum2\\web\\resources\\Avatars\\" + name + ".jpg");
        if(f.exists()){
        f.delete();
        }
        Files.copy(input, new File("E:\\Git_directory\\NewForum2\\web\\resources\\Avatars\\" +name + ".jpg").toPath());
//        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
//        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
        return "/avatar.xhtml";
    }
    catch (IOException e) {
    return "/avatar.xhtml";
    }
}

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }
    
    public String getCh(){
        final Random random = new Random();
        this.ch = String.valueOf(random.nextInt(120000));
        return ch;
    }
    
    public void setCh(String ch){
        this.ch = ch;
    }
    private void redirect() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
        } catch (IOException ex) {
            Logger.getLogger(ForumBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
 
}