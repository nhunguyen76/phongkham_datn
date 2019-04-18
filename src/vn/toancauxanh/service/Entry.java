
package vn.toancauxanh.service;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.config.ResourceNotFoundException;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.zkoss.util.resource.Labels;
import org.zkoss.zhtml.Object;

import vn.toancauxanh.cms.service.DichVuService;
import vn.toancauxanh.cms.service.HoSoBenhAnService;
import vn.toancauxanh.cms.service.ImageService;
import vn.toancauxanh.cms.service.LanguageService;
import vn.toancauxanh.cms.service.SettingService;
import vn.toancauxanh.gg.model.HoSoThongTin;
import vn.toancauxanh.model.GopYPhanMem;
import vn.toancauxanh.model.VaiTro;

@Configuration
@Controller
public class Entry extends BaseObject<Object> {
	static Entry instance;

	@Value("${trangthai.apdung}")
	public String TT_AP_DUNG = "";
	@Value("${trangthai.daxoa}")
	public String TT_DA_XOA = "";
	@Value("${trangthai.khongapdung}")
	public String TT_KHONG_AP_DUNG = "";

	// No image url
	public String URL_M_NOIMAGE = "/assetsfe/images/lg_noimage.png";
	public String URL_S_NOIMAGE = "/assetsfe/images/sm_noimage.png";

	@Value("${action.xem}")
	public String XEM = ""; // duoc xem bat ky
	@Value("${action.list}")
	public String LIST = ""; // duoc vao trang list search url
	@Value("${action.sua}")
	public String SUA = ""; // duoc sua bat ky
	@Value("${action.xoa}")
	public String XOA = ""; // duoc xoa bat ky
	@Value("${action.them}")
	public String THEM = ""; // duoc them
	@Value("${url.nguoidung}")
	public String NGUOIDUNG = "";
	
	@Value("${url.vaitro}")
	public String VAITRO = "";

	// uend
	public char CHAR_CACH = ':';
	public String CACH = CHAR_CACH + "";

	
	@Value("${url.vaitro}" + ":" + "${action.xem}")
	public String VAITROXEM;
	@Value("${url.vaitro}" + ":" + "${action.them}")
	public String VAITROTHEM = "";
	@Value("${url.vaitro}" + ":" + "${action.list}")
	public String VAITROLIST = "";
	@Value("${url.vaitro}" + ":" + "${action.xoa}")
	public String VAITROXOA = "";
	@Value("${url.vaitro}" + ":" + "${action.sua}")
	public String VAITROSUA = "";

	@Value("${url.nguoidung}" + ":" + "${action.xem}")
	public String NGUOIDUNGXEM = "";
	@Value("${url.nguoidung}" + ":" + "${action.them}")
	public String NGUOIDUNGTHEM = "";
	@Value("${url.nguoidung}" + ":" + "${action.list}")
	public String NGUOIDUNGLIST = "";
	@Value("${url.nguoidung}" + ":" + "${action.xoa}")
	public String NGUOIDUNGXOA = "";
	@Value("${url.nguoidung}" + ":" + "${action.sua}")
	public String NGUOIDUNGSUA = "";
	
	//////////////////////////////
	@Value("${url.hosothongtin}")
	public String HOSOTHONGTIN = "";
	@Value("${url.hosothongtin}" + ":" + "${action.xem}")
	public String HOSOTHONGTINXEM = "";
	@Value("${url.hosothongtin}" + ":" + "${action.them}")
	public String HOSOTHONGTINTHEM = "";
	@Value("${url.hosothongtin}" + ":" + "${action.list}")
	public String HOSOTHONGTINLIST = "";
	@Value("${url.hosothongtin}" + ":" + "${action.xoa}")
	public String HOSOTHONGTINXOA = "";
	@Value("${url.hosothongtin}" + ":" + "${action.sua}")
	public String HOSOTHONGTINSUA = "";
	
	@Value("${url.dichvu}")
	public String DICHVU = "";
	@Value("${url.dichvu}" + ":" + "${action.xem}")
	public String DICHVUXEM = "";
	@Value("${url.dichvu}" + ":" + "${action.them}")
	public String DICHVUTHEM = "";
	@Value("${url.dichvu}" + ":" + "${action.list}")
	public String DICHVULIST = "";
	@Value("${url.dichvu}" + ":" + "${action.xoa}")
	public String DICHVUXOA = "";
	@Value("${url.dichvu}" + ":" + "${action.sua}")
	public String DICHVUSUA = "";
	
	@Value("${url.hosobenhan}")
	public String HOSOBENHAN = "";
	@Value("${url.hosobenhan}" + ":" + "${action.xem}")
	public String HOSOBENHANXEM = "";
	@Value("${url.hosobenhan}" + ":" + "${action.them}")
	public String HOSOBENHANTHEM = "";
	@Value("${url.hosobenhan}" + ":" + "${action.list}")
	public String HOSOBENHANLIST = "";
	@Value("${url.hosobenhan}" + ":" + "${action.xoa}")
	public String HOSOBENHANXOA = "";
	@Value("${url.hosobenhan}" + ":" + "${action.sua}")
	public String HOSOBENHANSUA = "";
	
	@Value("${url.nhanvien}")
	public String NHANVIEN = "";
	@Value("${url.nhanvien}" + ":" + "${action.xem}")
	public String NHANVIENXEM = "";
	@Value("${url.nhanvien}" + ":" + "${action.them}")
	public String NHANVIENTHEM = "";
	@Value("${url.nhanvien}" + ":" + "${action.list}")
	public String NHANVIENLIST = "";
	@Value("${url.nhanvien}" + ":" + "${action.xoa}")
	public String NHANVIENXOA = "";
	@Value("${url.nhanvien}" + ":" + "${action.sua}")
	public String NHANVIENSUA = "";
	// aend
	public String[] getRESOURCES() {
		return new String[] { NGUOIDUNG, VAITRO, HOSOTHONGTIN, DICHVU, HOSOBENHAN, NHANVIEN };
	}

	public String[] getACTIONS() {
		return new String[] { LIST, XEM, THEM, SUA, XOA };
	}

	static {
		File file = new File(Labels.getLabel("filestore.root") + File.separator + Labels.getLabel("filestore.folder"));
		if (!file.exists()) {
			if (file.mkdir()) {
				System.out.println("Directory mis is created!");
			} else {
				System.out.println("Failed to create directory!");
			}
		}
	}
	@Autowired
	public Environment env;

	@Autowired
	DataSource dataSource;

	public Entry() {
		super();
		setCore();
		instance = this;
	}

	@Bean
	public FilterRegistrationBean cacheFilter() {
		FilterRegistrationBean rs = new FilterRegistrationBean(new CacheFilter());
		rs.addUrlPatterns("*.css");
		rs.addUrlPatterns("*.js");
		rs.addUrlPatterns("*.wpd");
		rs.addUrlPatterns("*.wcs");
		rs.addUrlPatterns("*.jpg");
		rs.addUrlPatterns("*.jpeg");
		rs.addUrlPatterns("*.png");
		rs.addUrlPatterns("*.svg");
		rs.addUrlPatterns("*.gif");
		return rs;
	}
	
	@RequestMapping(value = "/cpo/{path:.+$}")
	public String cp2(@PathVariable String path) {
		return "forward:/WEB-INF/zul/home.zul?resource=" + path + "&action=lietke&file=/WEB-INF/zul/" + path
				+ "/list.zhtml";
	}

	@RequestMapping(value = "/{path:.+$}")
	public String cp(@PathVariable String path) {
		return "forward:/WEB-INF/zul/home.zul?resource=" + path + "&action=lietke&file=/WEB-INF/zul/" + path
				+ "/list.zul";
	}

	@RequestMapping(value = "/sso-error")
	public String sso() {
		return "forward:/WEB-INF/zul/error-sso.zul";
	}

	@RequestMapping(value = "/dang-nhap-sso")
	public String loginSSO(HttpServletRequest request, HttpServletResponse response) {
		return "forward:/WEB-INF/zul/dang-nhap-sso.zul";
	}

	@RequestMapping(value = "/cp/{path:.+$}")
	public String cpAdmin(@PathVariable String path) {
		return "forward:/WEB-INF/zul/home.zul?resource=" + path + "&action=lietke&file=/WEB-INF/zul/" + path
				+ "/list.zul";
	}

	@RequestMapping(value = "/{path:.+$}/add")
	public String cpAdd(@PathVariable String path) {
		return "forward:/WEB-INF/zul/home.zul?resource=" + path + "&action=them&file=/WEB-INF/zul/" + path
				+ "/add-view.zhtml";
	}

	@RequestMapping(value = "/{path:.+$}/them-moi")
	public String cpAdd2(@PathVariable String path) {
		return "forward:/WEB-INF/zul/home.zul?resource=" + path + "&action=them&file=/WEB-INF/zul/" + path + "/add.zul";
	}

	@RequestMapping(value = "/{path:.+$}/chinh-sua/{id:\\d+}")
	public String cpEdit(@PathVariable String path, @PathVariable long id) {
		return "forward:/WEB-INF/zul/home.zul?resource=" + path + "&action=sua&file=/WEB-INF/zul/" + path
				+ "/add.zul&id=" + id;
	}

	@RequestMapping(value = "/{path:.+$}/chi-tiet/{id:\\d+}")
	public String cpDetail(@PathVariable String path, @PathVariable long id) {
		return "forward:/WEB-INF/zul/home.zul?resource=" + path + "&action=xem&file=/WEB-INF/zul/" + path
				+ "/detail.zul&id=" + id;
	}

	@RequestMapping(value = "/{path:.+$}/id/{id:\\d+}")
	public String cp(@PathVariable String path, @PathVariable Long id) {
		return "forward:/WEB-INF/zul/home.zul?resource=" + path + "&action=them&file=/WEB-INF/zul/" + path  + "/add.zul&id="+ id;
	}
	
	@RequestMapping(value = "/login")
	public String dangNhapBackend() {
		return "forward:/WEB-INF/zul/login.zul";
	}

	@RequestMapping(value = "/auth/logout")
	public void dangXuatBackend(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null) {
			response.sendRedirect(request.getContextPath() + "/cas/login");
		} else {
			new NguoiDungService().logoutNotRedirect(request, response);
		}
	}
	
	//FE
	@RequestMapping(value = "/home")
	public String home() {
		return "forward:/frontend/index.zhtml";
	}

	public final Quyen getQuyen() {
		return getNguoiDung().getTatCaQuyen();
	}

	public final NguoiDungService getNguoiDungService() {
		return new NguoiDungService();
	}

	public final VaiTroService getVaiTros() {
		return new VaiTroService();
	}
	
	public final SettingService getSettings() {
		return new SettingService();
	}

	public GopYPhanMem getGopYPhanMem() {
		return new GopYPhanMem();
	}
	
	public HoSoThongTinService getHoSoThongTins() {
		return new HoSoThongTinService();
	}

	public DichVuService getDichVus() {
		return new DichVuService();
	}
	
	public HoSoBenhAnService getHoSoBenhAns() {
		return new HoSoBenhAnService();
	}
	
	public final ImageService getImages() {
		return new ImageService();
	}

	public final LanguageService getLanguages() {
		return new LanguageService();
	}
	
	
	public boolean checkVaiTro(String vaiTro) {
		if (vaiTro == null || vaiTro.isEmpty()) {
			return false;
		}
		boolean rs = false;
		for (VaiTro vt : getNguoiDung().getVaiTros()) {
			if (vaiTro.equals(vt.getAlias())) {
				rs = true;
				break;
			}
		}
		return rs;// || getQuyen().get(vaiTro);
	}

	@Configuration
	@EnableWebMvc
	public static class MvcConfig extends WebMvcConfigurerAdapter {
		@Override
		public void addResourceHandlers(ResourceHandlerRegistry registry) {
			registry.addResourceHandler("/files/**").addResourceLocations("file:/home/vhttdata/hdndfiles/");
			registry.addResourceHandler("/assetsfe/**").addResourceLocations("/assetsfe/");
			registry.addResourceHandler("/backend/**").addResourceLocations("/backend/");
			registry.addResourceHandler("/img/**").addResourceLocations("/img/");
			registry.addResourceHandler("/login/**").addResourceLocations("/login/");
		}

		@Override
		public void configureViewResolvers(final ViewResolverRegistry registry) {
			registry.jsp("/WEB-INF/", "*");
		}

		@ExceptionHandler(ResourceNotFoundException.class)
		@ResponseStatus(HttpStatus.NOT_FOUND)
		public String handleResourceNotFoundException() {
			return "forward:/WEB-INF/zul/notfound.zul";
		}
	}

}