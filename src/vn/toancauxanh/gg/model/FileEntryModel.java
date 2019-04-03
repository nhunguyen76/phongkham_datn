package vn.toancauxanh.gg.model;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import antlr.Utils;
import lombok.Data;

@Data
public class FileEntryModel {

 private static final Logger LOGGER = LoggerFactory.getLogger(Utils.class.getName());
 	
 	@JsonIgnore
 	private Properties properties;

    public String getFileUrlStr() {
    	properties = new Properties();
        try {
            File file = ResourceUtils.getFile("classpath:application.properties");
            InputStream in = new FileInputStream(file);
            properties.load(in);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        return properties.getProperty("hostname") + fileUrl;
    }
		
	private Long id;
	@JsonIgnore
	private String fileUrl = "";
	private String tenHienThi = "";
	private String tepDinhKem = "";

	
}
