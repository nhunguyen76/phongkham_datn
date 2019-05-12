package vn.toancauxanh.service;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

import vn.toancauxanh.gg.model.HoSoThongTin;
import vn.toancauxanh.model.NguoiDung;

public class HeaderService extends BasicService<NguoiDung> {
	private NguoiDung user;
	private NguoiDung currentUser;
	
	@Init
	public void bootstrap() {
		user = getNguoiDung();
	}
	
	public NguoiDung getUser() {
		return user;
	}
	
	public NguoiDung getCurrentUser() {
		currentUser = getNguoiDung();
		user = currentUser;
		BindUtils.postNotifyChange(null, null, currentUser, "user");
		return currentUser;
	}
	
	public void setCurrentUser(NguoiDung currentUser) {
		this.currentUser = currentUser;
	}
	
	///

	@Command
	public void redirectPageSession(@BindingParam("url") String url, @BindingParam("vm") HoSoThongTin vm) {
		if (vm != null) {
			url = url.concat("/" + vm.getId());
		}
		putArgSessionPage();
		Executions.getCurrent().sendRedirect(url);
	}

	public void putArgSessionPage() {
		Session session = Sessions.getCurrent();
		// chỗ này là chỗ lưu session này, lưu cái getarg là lưu cả cái page
		session.setAttribute("tiemPhongVM", getArg());
	}
	
}
