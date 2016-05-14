
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.logging.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileController {
 
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(FileController.class);
 
	@RequestMapping(value = "uploadFile", method = RequestMethod.POST)
	@ResponseBody
	public String uploadFile(@RequestParam("file") MultipartFile file) {// имена параметров (тут - "file") - из формы JSP.
            String name = null;
                if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();
 
				name = file.getOriginalFilename();
 
				String rootPath;
                            rootPath = "C:\\path\\";
				File dir = new File(rootPath + File.separator + "loadFiles");
 
				if (!dir.exists()) {
					dir.mkdirs();
				}
 
				File uploadedFile = new File(dir.getAbsolutePath() + File.separator + name);
 
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(uploadedFile));
				stream.write(bytes);
				stream.flush();
				stream.close();
 
				logger.info("uploaded: " + uploadedFile.getAbsolutePath());
 
				return "You successfully uploaded file=" + name;
 
			} catch (Exception e) {
				return "You failed to upload " + name + " => " + e.getMessage();
			}
		} else {
			return "You failed to upload " + name + " because the file was empty.";
		}
	}
 
}