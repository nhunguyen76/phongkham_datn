package vn.toancauxanh.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.DefaultTreeModel;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.TreeNode;
import org.zkoss.zul.Window;

import com.google.common.base.Strings;
import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.enums.VaiTroEnum;
import vn.toancauxanh.service.Quyen;

@Entity
// @SequenceGenerator(name = "per_class_gen", sequenceName =
// "HIBERNATE_SEQUENCE", allocationSize = 1)
@Table(name = "vaitro", indexes = {@Index(columnList = "alias"), @Index(columnList = "tenVaiTro") })
public class VaiTro extends Model<VaiTro> {
	public static transient final Logger LOG = LogManager.getLogger(VaiTro.class.getName());

	public static final String QUANTRIVIEN = "quantrivien";
	public static final String BACSI = "bacsi";
	public static final String BENHNHAN = "benhnhan";
	public static final String NHANVIEN = "nhanvien";

	public static final String[] VAITRO_DEFAULTS = {
			QUANTRIVIEN, 
			BACSI, 
			BENHNHAN,
			NHANVIEN };

	private Set<String> quyens = new HashSet<>();
	private Set<String> quyenEdits = quyens;
	private String tenVaiTro = "";
	private String alias = "";
	private int soThuTu;

	public VaiTro() {
		super();
	}

	public VaiTro(String ten, String quyen) {
		super();
		tenVaiTro = ten;
		setAlias(quyen.trim());
	}

	public int getSoThuTu() {
		return soThuTu;
	}

	public void setSoThuTu(int soThuTu) {
		this.soThuTu = soThuTu;
	}

	@Transient
	public List<NguoiDung> getListNguoiDung() {
		JPAQuery<NguoiDung> q = find(NguoiDung.class)
				.where(QNguoiDung.nguoiDung.trangThai.ne(core().TT_DA_XOA))
				.where(QNguoiDung.nguoiDung.vaiTros.contains(this));
		return q.fetch();
	}

	Set<TreeNode<String[]>> selectedItems = new HashSet<>();

	@Transient
	@NotifyChange({ "selectedItems", "model", "*" })
	public DefaultTreeModel<String[]> getModel() {
		getQuyens();
		selectedItems.clear();
		
		final HashSet<TreeNode<String[]>> openItems_ = new HashSet<>();
		
		final TreeNode<String[]> rootNode = new DefaultTreeNode<>(new String[] {}, new ArrayList<DefaultTreeNode<String[]>>());
		
		final DefaultTreeModel<String[]> model = new DefaultTreeModel<>(rootNode, true);
		
		model.setMultiple(true);
		final Set<String> allQuyens = new HashSet<>();
		
		final long q = find(VaiTro.class).fetchCount();
		
		if(q==0){
			for (String vaiTro : VAITRO_DEFAULTS) {
				allQuyens.addAll(getQuyenMacDinhs(vaiTro));
			}
		} else {
			allQuyens.addAll(getQuyenAllMacDinhs());
		}
		
		for (String resource : core().getRESOURCES()) {
			DefaultTreeNode<String[]> parentNode = new DefaultTreeNode<>(
					new String[] { Labels.getLabel("url." + resource + ".mota"), resource },
					new ArrayList<DefaultTreeNode<String[]>>());
			if (quyens.contains(resource)) {
				selectedItems.add(parentNode);
				openItems_.add(parentNode);
				model.setOpenObjects(openItems_);
			}
			for (String action : core().getACTIONS()) {
				String quyen = resource + Quyen.CACH + action;
				if (allQuyens.contains(quyen)) {
					DefaultTreeNode<String[]> childNode = new DefaultTreeNode<>(
							new String[] { Labels.getLabel("action." + action + ".mota"), quyen },
							new ArrayList<DefaultTreeNode<String[]>>());
					if (quyens.contains(quyen)) {
						selectedItems.add(childNode);
						openItems_.add(childNode);
						openItems_.add(parentNode);
					}
					parentNode.add(childNode);
				}
			}
			rootNode.add(parentNode);
		}
		quyenEdits = new HashSet<>(quyens);
		model.setOpenObjects(openItems_);
		//LOG.info(quyens.size() + "size");
		//LOG.info(quyenEdits.size() + "size");
		//LOG.info(selectedItems.size() + "size");
		//LOG.info(model.getSelectionCount() + "size");
		return model;
	}

	public String getAlias() {
		return alias;
	}

	@Override
	public void save() {
		setTenVaiTro(getTenVaiTro().trim().replaceAll("\\s+", " "));
		setQuyens(quyenEdits);
		// quyens.addAll(quyenEdits);
		// quyens.retainAll(quyenEdits);
		// quyens.clear();
		// quyens.addAll(quyenEdits);
		//LOG.info(quyenEdits.size() + "size");
		//LOG.info(quyens.size() + "size");
		//LOG.info(selectedItems.size() + "size");
		if (noId()) {
			showNotification("Đã lưu thành công!", "", "success");
		} else {
			showNotification("Đã cập nhật thành công!", "", "success");
		}
		doSave();
	}

	@Transient
	public Set<String> getQuyenAllMacDinhs() {
		Set<String> quyens1 = new HashSet<>();
		
		quyens1.add(core().NGUOIDUNGTHEM);
		quyens1.add(core().NGUOIDUNGSUA);
		quyens1.add(core().NGUOIDUNGXEM);
		quyens1.add(core().NGUOIDUNGLIST);
		quyens1.add(core().NGUOIDUNGXOA);
		
		quyens1.add(core().VAITROTHEM);
		quyens1.add(core().VAITROSUA);
		quyens1.add(core().VAITROXEM);
		quyens1.add(core().VAITROLIST);
		quyens1.add(core().VAITROXOA);
		
		quyens1.add(core().HOSOTHONGTINTHEM);
		quyens1.add(core().HOSOTHONGTINLIST);
		quyens1.add(core().HOSOTHONGTINSUA);
		quyens1.add(core().HOSOTHONGTINXOA);
		quyens1.add(core().HOSOTHONGTINXEM);
		
		quyens1.add(core().DICHVUTHEM);
		quyens1.add(core().DICHVULIST);
		quyens1.add(core().DICHVUSUA);
		quyens1.add(core().DICHVUXOA);
		quyens1.add(core().DICHVUXEM);
		
		quyens1.add(core().HOSOBENHANTHEM);
		quyens1.add(core().HOSOBENHANLIST);
		quyens1.add(core().HOSOBENHANSUA);
		quyens1.add(core().HOSOBENHANXOA);
		quyens1.add(core().HOSOBENHANXEM);
		
		quyens1.add(core().NHANVIENTHEM);
		quyens1.add(core().NHANVIENLIST);
		quyens1.add(core().NHANVIENSUA);
		quyens1.add(core().NHANVIENXOA);
		quyens1.add(core().NHANVIENXEM);
//		
//		quyens1.add(core().CHITIETBENHANTHEM);
//		quyens1.add(core().CHITIETBENHANLIST);
//		quyens1.add(core().CHITIETBENHANSUA);
//		quyens1.add(core().CHITIETBENHANXOA);
//		quyens1.add(core().CHITIETBENHANXEM);
//		
//		quyens1.add(core().CANLAMSANGTHEM);
//		quyens1.add(core().CANLAMSANGLIST);
//		quyens1.add(core().CANLAMSANGSUA);
//		quyens1.add(core().CANLAMSANGXOA);
//		quyens1.add(core().CANLAMSANGXEM);
//		
//		quyens1.add(core().CHITIETDONTHUOCTHEM);
//		quyens1.add(core().CHITIETDONTHUOCLIST);
//		quyens1.add(core().CHITIETDONTHUOCSUA);
//		quyens1.add(core().CHITIETDONTHUOCXOA);
//		quyens1.add(core().CHITIETDONTHUOCXEM);
		
		quyens1.add(core().THUOCTHEM);
        quyens1.add(core().THUOCLIST);
        quyens1.add(core().THUOCSUA);
        quyens1.add(core().THUOCXOA);
        quyens1.add(core().THUOCXEM);
        
        quyens1.add(core().DONTHUOCTHEM);
        quyens1.add(core().DONTHUOCLIST);
        quyens1.add(core().DONTHUOCSUA);
        quyens1.add(core().DONTHUOCXOA);
        quyens1.add(core().DONTHUOCXEM);
		
		quyens1.add(core().LICHHENKHAMTHEM);
		quyens1.add(core().LICHHENKHAMLIST);
		quyens1.add(core().LICHHENKHAMSUA);
		quyens1.add(core().LICHHENKHAMXOA);
		quyens1.add(core().LICHHENKHAMXEM);
		
		quyens1.add(core().TAIKHOANTHEM);
		quyens1.add(core().TAIKHOANLIST);
		quyens1.add(core().TAIKHOANSUA);
		quyens1.add(core().TAIKHOANXOA);
		quyens1.add(core().TAIKHOANXEM);
		
		quyens1.add(core().CAUHOITHEM);
        quyens1.add(core().CAUHOILIST);
        quyens1.add(core().CAUHOISUA);
        quyens1.add(core().CAUHOIXOA);
        quyens1.add(core().CAUHOIXEM);
        
        quyens1.add(core().TRALOITHEM);
        quyens1.add(core().TRALOILIST);
        quyens1.add(core().TRALOISUA);
        quyens1.add(core().TRALOIXOA);
        quyens1.add(core().TRALOIXEM);
		
        //
		quyens1.add(core().HOSOBENHNHANTHEM);
		quyens1.add(core().HOSOBENHNHANLIST);
		quyens1.add(core().HOSOBENHNHANSUA);
		quyens1.add(core().HOSOBENHNHANXOA);
		quyens1.add(core().HOSOBENHNHANXEM);
		
		quyens1.add(core().HOSOCANHANTHEM);
		quyens1.add(core().HOSOCANHANLIST);
		quyens1.add(core().HOSOCANHANSUA);
		quyens1.add(core().HOSOCANHANXOA);
		quyens1.add(core().HOSOCANHANXEM);
		
		return quyens1;
	}
	
	@Transient
	public Set<String> getQuyenMacDinhs(String alias1) {
		Set<String> quyens1 = new HashSet<>();
		if (!alias1.isEmpty()) {
			if (QUANTRIVIEN.equals(alias1)) {
				
				quyens1.add(core().HOSOTHONGTINTHEM);
				quyens1.add(core().HOSOTHONGTINLIST);
				quyens1.add(core().HOSOTHONGTINSUA);
				quyens1.add(core().HOSOTHONGTINXOA);
				quyens1.add(core().HOSOTHONGTINXEM);
			}
		}
		return quyens1;
	}

	@Transient
	public Set<String> getQuyenMacDinhs() {
		return getQuyenMacDinhs(getAlias());
	}
	
	@ElementCollection(fetch = FetchType.EAGER)
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	@CollectionTable(name = "vaitro_quyens", joinColumns = {@JoinColumn(name = "vaitro_id")})
	public Set<String> getQuyens() {
		if (quyens.isEmpty()) {
			quyens.addAll(getQuyenMacDinhs());
		}
		return quyens;
	}

	public String getTenVaiTro() {
		return tenVaiTro;
	}

	public void setAlias(String alias1) {
		this.alias = Strings.nullToEmpty(alias1);
	}

	public void setQuyens(final Set<String> dsChoPhep) {
		quyens = dsChoPhep;
	}

	public void setTenVaiTro(final String _tenVaiTro) {
		tenVaiTro = Strings.nullToEmpty(_tenVaiTro) != null ? Strings.nullToEmpty(_tenVaiTro).trim() : Strings.nullToEmpty(_tenVaiTro);
	}

	@Override
	public String toString() {
		return super.toString() + " " + tenVaiTro;
	}

	@Transient
	public Set<TreeNode<String[]>> getSelectedItems() {
		return selectedItems;
	}

	public void setSelectedItems(Set<TreeNode<String[]>> selectedItems_) {
		Set<TreeNode<String[]>> selectedItemsTmp = new HashSet<>();
		selectedItemsTmp.addAll(selectedItems);
		for (final TreeNode<String[]> node : selectedItems) {
			if (!selectedItems_.contains(node)) {
				quyenEdits.remove(node.getData()[1]);
				selectedItemsTmp.remove(node);

				// remove parent
				TreeNode<String[]> pNode = node.getParent();
				if (pNode != null && selectedItems_.contains(pNode)) {
					quyenEdits.remove(pNode.getData()[1]);
					selectedItemsTmp.remove(pNode);
				}
				// remove all child
				if (node.getChildCount() > 0) {
					for (TreeNode<String[]> n : node.getChildren()) {
						quyenEdits.remove(n.getData()[1]);
						selectedItemsTmp.remove(n);
					}
				}
			}
		}
		for (final TreeNode<String[]> node : selectedItems_) {
			if (!selectedItems.contains(node)) {
				quyenEdits.add(node.getData()[1]);
				selectedItemsTmp.add(node);
				if (node.getChildCount() > 0) {
					for (TreeNode<String[]> n : node.getChildren()) {
						quyenEdits.add(n.getData()[1]);
						selectedItemsTmp.add(n);
					}
				}
			}
		}
		selectedItems = selectedItemsTmp;
		BindUtils.postNotifyChange(null, null, this, "quyenEdits");
		BindUtils.postNotifyChange(null, null, this, "selectedItems");
	}

	private boolean checkApDung;

	@Transient
	public boolean isCheckApDung() {
		checkApDung = false;
		if (core().TT_AP_DUNG.equals(getTrangThai())) {
			checkApDung = true;
		}
		return checkApDung;
	}

	public void setCheckApDung(boolean _isCheckApDung) {
		if (_isCheckApDung) {
			setTrangThai(core().TT_AP_DUNG);
		} else {
			setTrangThai(core().TT_KHONG_AP_DUNG);
		}
		this.checkApDung = _isCheckApDung;
	}

	@Command
	public void khoaThanhVien(@BindingParam("vm") final Object vm) {

		Messagebox.show("Báº¡n muá»‘n khÃ³a vai trÃ² nÃ y?", "XÃ¡c nháº­n", Messagebox.CANCEL | Messagebox.OK,
				Messagebox.QUESTION, new EventListener<Event>() {
					@Override
					public void onEvent(final Event event) {
						if (Messagebox.ON_OK.equals(event.getName())) {
							setCheckApDung(false);
							save();
							BindUtils.postNotifyChange(null, null, vm, "vaiTroQuery");
						}
					}
				});

	}

	@Command
	public void moKhoaThanhVien(@BindingParam("vm") final Object vm) {
		Messagebox.show("Báº¡n muá»‘n má»Ÿ khÃ³a vai trÃ² nÃ y?", "XÃ¡c nháº­n", Messagebox.CANCEL | Messagebox.OK,
				Messagebox.QUESTION, new EventListener<Event>() {
					@Override
					public void onEvent(final Event event) {
						if (Messagebox.ON_OK.equals(event.getName())) {
							setCheckApDung(true);
							save();
							BindUtils.postNotifyChange(null, null, vm, "vaiTroQuery");
						}
					}
				});
	}

	private boolean checkKichHoat;

	public boolean isCheckKichHoat() {
		return checkKichHoat;
	}

	public void setCheckKichHoat(boolean checkKichHoat) {
		this.checkKichHoat = checkKichHoat;
	}

	@Command
	public void toggleLock(@BindingParam("list") final Object obj) {
		String dialogText = "";
		if (checkKichHoat) {
			dialogText = "Báº¡n muá»‘n kÃ­ch hoáº¡t vai trÃ² Ä‘Ã£ chá»�n?";
		} else {
			dialogText = "Báº¡n muá»‘n ngá»«ng kÃ­ch hoáº¡t vai trÃ² Ä‘Ã£ chá»�n?";
		}
		Messagebox.show(dialogText, "XÃ¡c nháº­n", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION,
				new EventListener<Event>() {
					@Override
					public void onEvent(final Event event) {
						if (Messagebox.ON_OK.equals(event.getName())) {
							if (checkKichHoat) {
								setCheckKichHoat(false);
							} else {
								setCheckKichHoat(true);
							}
							save();
							BindUtils.postNotifyChange(null, null, obj, "vaiTroQuery");
						}
					}
				});
	}

	private NguoiDung selectedNguoiDung;

	@Transient
	public NguoiDung getSelectedNguoiDung() {
		return selectedNguoiDung;
	}

	public void setSelectedNguoiDung(NguoiDung selectNguoiDung) {
		this.selectedNguoiDung = selectNguoiDung;
	}

	@Command
	public void addNguoiDung() {
		if (selectedNguoiDung != null) {
			if (selectedNguoiDung.getVaiTros().contains(this)) {
				showNotification("NhÃ¢n viÃªn " +  " Ä‘Ã£ cÃ³ vai trÃ² nÃ y!", "", "warning");
			} else {
				Messagebox.show(
						"Báº¡n cÃ³ cháº¯c cháº¯n muá»‘n thÃªm vai trÃ² " + this.getTenVaiTro() + " cho nhÃ¢n viÃªn "
								,
						"XÃ¡c nháº­n", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new EventListener<Event>() {
							@Override
							public void onEvent(final Event event) {
								if (Messagebox.ON_OK.equals(event.getName())) {
									selectedNguoiDung.getVaiTros().add(VaiTro.this);
									selectedNguoiDung.save();
									setSelectedNguoiDung(null);
									BindUtils.postNotifyChange(null, null, VaiTro.this, "listNguoiDung");
									BindUtils.postNotifyChange(null, null, VaiTro.this, "selectedNguoiDung");
								}
							}
						});
			}
		} else {
			showNotification("Báº¡n chÆ°a nháº­p tÃªn nhÃ¢n viÃªn hoáº·c tÃªn nhÃ¢n viÃªn khÃ´ng tá»“n táº¡i.", "", "warning");
		}
	}

	@Command
	public void saveVaiTro(@BindingParam("list") final Object listObject,
			@BindingParam("attr") final String attr,
			@BindingParam("wdn") final Window wdn) {
		setTenVaiTro(getTenVaiTro().trim().replaceAll("\\s+", " "));
		setQuyens(quyenEdits);
		save();
		wdn.detach();
		BindUtils.postNotifyChange(null, null, listObject, "vaiTroQuery");
	}
	
	@Transient
	public boolean isMacDinh() {
		return Arrays.asList(VAITRO_DEFAULTS).contains(this.getAlias());
	}
}
