
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

import vn.toancauxanh.cms.service.CanLamSangService;
import vn.toancauxanh.cms.service.CauHoiService;
import vn.toancauxanh.cms.service.ChiTietBenhAnService;
import vn.toancauxanh.cms.service.ChiTietDonThuocService;
import vn.toancauxanh.cms.service.DichVuService;
import vn.toancauxanh.cms.service.DonThuocService;
import vn.toancauxanh.cms.service.HoSoBenhAnService;
import vn.toancauxanh.cms.service.ImageService;
import vn.toancauxanh.cms.service.LichHenKhamService;
import vn.toancauxanh.cms.service.SettingService;
import vn.toancauxanh.cms.service.ThuocService;
import vn.toancauxanh.cms.service.TraLoiService;
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
	
	@Value("${url.hosocanhan}")
    public String HOSOCANHAN = "";
    @Value("${url.hosocanhan}" + ":" + "${action.xem}")
    public String HOSOCANHANXEM = "";
    @Value("${url.hosocanhan}" + ":" + "${action.them}")
    public String HOSOCANHANTHEM = "";
    @Value("${url.hosocanhan}" + ":" + "${action.list}")
    public String HOSOCANHANLIST = "";
    @Value("${url.hosocanhan}" + ":" + "${action.xoa}")
    public String HOSOCANHANXOA = "";
    @Value("${url.hosocanhan}" + ":" + "${action.sua}")
    public String HOSOCANHANSUA = "";
	
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
	
	@Value("${url.hosobenhnhan}")
	public String HOSOBENHNHAN = "";
	@Value("${url.hosobenhnhan}" + ":" + "${action.xem}")
	public String HOSOBENHNHANXEM = "";
	@Value("${url.hosobenhnhan}" + ":" + "${action.them}")
	public String HOSOBENHNHANTHEM = "";
	@Value("${url.hosobenhnhan}" + ":" + "${action.list}")
	public String HOSOBENHNHANLIST = "";
	@Value("${url.hosobenhnhan}" + ":" + "${action.xoa}")
	public String HOSOBENHNHANXOA = "";
	@Value("${url.hosobenhnhan}" + ":" + "${action.sua}")
	public String HOSOBENHNHANSUA = "";
	
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
	
	@Value("${url.chitietbenhan}")
	public String CHITIETBENHAN = "";
	@Value("${url.chitietbenhan}" + ":" + "${action.xem}")
	public String CHITIETBENHANXEM = "";
	@Value("${url.chitietbenhan}" + ":" + "${action.them}")
	public String CHITIETBENHANTHEM = "";
	@Value("${url.chitietbenhan}" + ":" + "${action.list}")
	public String CHITIETBENHANLIST = "";
	@Value("${url.chitietbenhan}" + ":" + "${action.xoa}")
	public String CHITIETBENHANXOA = "";
	@Value("${url.chitietbenhan}" + ":" + "${action.sua}")
	public String CHITIETBENHANSUA = "";
	
	@Value("${url.canlamsang}")
	public String CANLAMSANG = "";
	@Value("${url.canlamsang}" + ":" + "${action.xem}")
	public String CANLAMSANGXEM = "";
	@Value("${url.canlamsang}" + ":" + "${action.them}")
	public String CANLAMSANGTHEM = "";
	@Value("${url.canlamsang}" + ":" + "${action.list}")
	public String CANLAMSANGLIST = "";
	@Value("${url.canlamsang}" + ":" + "${action.xoa}")
	public String CANLAMSANGXOA = "";
	@Value("${url.canlamsang}" + ":" + "${action.sua}")
	public String CANLAMSANGSUA = "";
	
	@Value("${url.thuoc}")
    public String THUOC = "";
    @Value("${url.thuoc}" + ":" + "${action.xem}")
    public String THUOCXEM = "";
    @Value("${url.thuoc}" + ":" + "${action.them}")
    public String THUOCTHEM = "";
    @Value("${url.thuoc}" + ":" + "${action.list}")
    public String THUOCLIST = "";
    @Value("${url.thuoc}" + ":" + "${action.xoa}")
    public String THUOCXOA = "";
    @Value("${url.thuoc}" + ":" + "${action.sua}")
    public String THUOCSUA = "";
    
    @Value("${url.donthuoc}")
    public String DONTHUOC = "";
    @Value("${url.donthuoc}" + ":" + "${action.xem}")
    public String DONTHUOCXEM = "";
    @Value("${url.donthuoc}" + ":" + "${action.them}")
    public String DONTHUOCTHEM = "";
    @Value("${url.donthuoc}" + ":" + "${action.list}")
    public String DONTHUOCLIST = "";
    @Value("${url.donthuoc}" + ":" + "${action.xoa}")
    public String DONTHUOCXOA = "";
    @Value("${url.donthuoc}" + ":" + "${action.sua}")
    public String DONTHUOCSUA = "";
    
    @Value("${url.chitietdonthuoc}")
    public String CHITIETDONTHUOC = "";
    @Value("${url.chitietdonthuoc}" + ":" + "${action.xem}")
    public String CHITIETDONTHUOCXEM = "";
    @Value("${url.chitietdonthuoc}" + ":" + "${action.them}")
    public String CHITIETDONTHUOCTHEM = "";
    @Value("${url.chitietdonthuoc}" + ":" + "${action.list}")
    public String CHITIETDONTHUOCLIST = "";
    @Value("${url.chitietdonthuoc}" + ":" + "${action.xoa}")
    public String CHITIETDONTHUOCXOA = "";
    @Value("${url.chitietdonthuoc}" + ":" + "${action.sua}")
    public String CHITIETDONTHUOCSUA = "";
    
    @Value("${url.lichhenkham}")
    public String LICHHENKHAM = "";
    @Value("${url.lichhenkham}" + ":" + "${action.xem}")
    public String LICHHENKHAMXEM = "";
    @Value("${url.lichhenkham}" + ":" + "${action.them}")
    public String LICHHENKHAMTHEM = "";
    @Value("${url.lichhenkham}" + ":" + "${action.list}")
    public String LICHHENKHAMLIST = "";
    @Value("${url.lichhenkham}" + ":" + "${action.xoa}")
    public String LICHHENKHAMXOA = "";
    @Value("${url.lichhenkham}" + ":" + "${action.sua}")
    public String LICHHENKHAMSUA = "";
    
    @Value("${url.taikhoan}")
    public String TAIKHOAN = "";
    @Value("${url.taikhoan}" + ":" + "${action.xem}")
    public String TAIKHOANXEM = "";
    @Value("${url.taikhoan}" + ":" + "${action.them}")
    public String TAIKHOANTHEM = "";
    @Value("${url.taikhoan}" + ":" + "${action.list}")
    public String TAIKHOANLIST = "";
    @Value("${url.taikhoan}" + ":" + "${action.xoa}")
    public String TAIKHOANXOA = "";
    @Value("${url.taikhoan}" + ":" + "${action.sua}")
    public String TAIKHOANSUA = "";
    
    @Value("${url.cauhoi}")
    public String CAUHOI = "";
    @Value("${url.cauhoi}" + ":" + "${action.xem}")
    public String CAUHOIXEM = "";
    @Value("${url.cauhoi}" + ":" + "${action.them}")
    public String CAUHOITHEM = "";
    @Value("${url.cauhoi}" + ":" + "${action.list}")
    public String CAUHOILIST = "";
    @Value("${url.cauhoi}" + ":" + "${action.xoa}")
    public String CAUHOIXOA = "";
    @Value("${url.cauhoi}" + ":" + "${action.sua}")
    public String CAUHOISUA = "";
    
    @Value("${url.traloi}")
    public String TRALOI = "";
    @Value("${url.traloi}" + ":" + "${action.xem}")
    public String TRALOIXEM = "";
    @Value("${url.traloi}" + ":" + "${action.them}")
    public String TRALOITHEM = "";
    @Value("${url.traloi}" + ":" + "${action.list}")
    public String TRALOILIST = "";
    @Value("${url.traloi}" + ":" + "${action.xoa}")
    public String TRALOIXOA = "";
    @Value("${url.traloi}" + ":" + "${action.sua}")
    public String TRALOISUA = "";
	// aend
	public String[] getRESOURCES() {
		return new String[] { NGUOIDUNG, VAITRO, HOSOTHONGTIN, DICHVU, NHANVIEN, 
				 THUOC, LICHHENKHAM, TAIKHOAN, TRALOI, CAUHOI, HOSOBENHNHAN, HOSOCANHAN};
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
		return "forward:/WEB-INF/zul/home.zul?resource=" + path + "&action=xem&file=/WEB-INF/zul/" + path  + "/edit-detail.zul&id="+ id;
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
	
//	public final NguoiDung getNguoiDung() {
//		return getNguoiDung();
//	}

	public final NguoiDungService getNguoiDungService() {
		return new NguoiDungService();
	}

	public final VaiTroService getVaiTros() {
		return new VaiTroService();
	}
	
	public final SettingService getSettings() {
		return new SettingService();
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
	
	public ChiTietBenhAnService getChiTietBenhAns() {
		return new ChiTietBenhAnService();
	}
	
	public CanLamSangService getCanLamSangs() {
		return new CanLamSangService();
	}
	
	public ThuocService getThuocs() {
        return new ThuocService();
    }
	
	public DonThuocService getDonThuocs() {
        return new DonThuocService();
    }
	
	public ChiTietDonThuocService getChiTietDonThuocs() {
        return new ChiTietDonThuocService();
    }
	
	public LichHenKhamService getLichHenKhams() {
        return new LichHenKhamService();
    }
	
	public CauHoiService getCauHois() {
		return new CauHoiService();
	}
	
	public TraLoiService getTraLois() {
		return new TraLoiService();
	}
	
	public final ImageService getImages() {
		return new ImageService();
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