import beans.LoginBean;
import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.request;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;


@ManagedBean
@ViewScoped
public class FileController implements Serializable {
        private Part file;
        
// private String fileContent;

public String upload(String name) {
       // LoginBean session = (LoginBean) req.getSession().getAttribute("loginBean");
    try (InputStream input = file.getInputStream()) {
       // String name = "";
        //name = session.getName();
        File f = new File("E:\\Git_directory\\NewForum2\\web\\resources\\Avatars\\" + name + ".jpg");
        if(f.exists()){
        f.delete();
        }
        Files.copy(input, new File("E:\\Git_directory\\NewForum2\\web\\resources\\Avatars\\" +name + ".jpg").toPath());
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
 
 
}