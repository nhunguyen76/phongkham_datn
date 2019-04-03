package vn.toancauxanh.service;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.Init;
import vn.toancauxanh.model.NguoiDung;

public class HeaderService extends BasicService<NguoiDung> {
	private NguoiDung user;
	private NguoiDung currentUser;
	
	@Init
	public void bootstrap() {
		user = getNhanVien();
	}
	
	public NguoiDung getUser() {
		return user;
	}
	
	public NguoiDung getCurrentUser() {
		currentUser = getNhanVien();
		user = currentUser;
		BindUtils.postNotifyChange(null, null, currentUser, "user");
		return currentUser;
	}
	
	public void setCurrentUser(NguoiDung currentUser) {
		this.currentUser = currentUser;
	}
	
}
