package vn.toancauxanh.gg.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.sys.ValidationMessages;
import org.zkoss.image.AImage;
import org.zkoss.image.Image;
import org.zkoss.io.Files;
import org.zkoss.util.media.Media;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import vn.toancauxanh.model.Model;

@Entity
@Table(name = "canlamsang")
public class CanLamSang extends Model<CanLamSang>{

	private HoSoBenhAn benhAn;
	private String tieuDe;
	private String ghiChu;
	private Date ngayKham;
	private Image imageContent;
	private String iconName = "";
	private String iconUrl = "";

	@ManyToOne
	public HoSoBenhAn getBenhAn() {
		return benhAn;
	}

	public void setBenhAn(HoSoBenhAn benhAn) {
		this.benhAn = benhAn;
	}
	
	public String getIconName() {
		return iconName;
	}

	public void setIconName(String iconName) {
		this.iconName = iconName;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	@Transient
	private boolean flagImage = true;

	public void setImageContent(org.zkoss.image.Image _imageContent) {
		this.imageContent = _imageContent;
	}
	
	@Transient
	public org.zkoss.image.Image getImageContent() throws FileNotFoundException, IOException {
		if (imageContent == null && !core().TT_DA_XOA.equals(getTrangThai())) {
			if (flagImage) {
				loadImageIsView();
			}
		}
		return imageContent;
	}

	private void loadImageIsView() throws FileNotFoundException, IOException {
		String imgName = "";
		String path = "";
		path = folderStore() + getIconName();
		if (!"".equals(getIconUrl()) && new File(path).exists()) {
			try (FileInputStream fis = new FileInputStream(new File(path));) {
				setImageContent(new AImage(imgName, fis));
			}
		} else {
			String filePath = Executions.getCurrent().getDesktop().getWebApp()
					.getRealPath("/backend/assets/images/man.png");
			try (FileInputStream fis = new FileInputStream(new File(filePath));) {
				setImageContent(new AImage("imge", fis));
			}
		}
	}

	private boolean beforeSaveImg() throws IOException {
		if (getImageContent() == null) {
			return false;
		}
		saveImageToServer();
		return true;
	}

	@Command
	public void attachImages(@BindingParam("media") final Media media,
			@BindingParam("vmsgs") final ValidationMessages vmsgs) {
		if (media instanceof org.zkoss.image.Image) {
			if (media.getName().toLowerCase().endsWith(".png") || media.getName().toLowerCase().endsWith(".jpg")) {
				int lengthOfImage = media.getByteData().length;
				if (lengthOfImage > 10000000) {
					showNotification("Chọn hình ảnh có dung lượng nhỏ hơn 10MB.", "", "error");
					return;
				} else {
					String tenFile = media.getName();

					tenFile = tenFile.replace(" ", "");
					tenFile = unAccent(tenFile.substring(0, tenFile.lastIndexOf("."))) + "_"
							+ Calendar.getInstance().getTimeInMillis() + tenFile.substring(tenFile.lastIndexOf("."));
					setImageContent((org.zkoss.image.Image) media);
					setIconName(tenFile);
					if (vmsgs != null) {
						vmsgs.clearKeyMessages("errLabel");
					}
					BindUtils.postNotifyChange(null, null, this, "imageContent");
					BindUtils.postNotifyChange(null, null, this, "iconname");
				}
			} else {
				showNotification("Chọn hình ảnh theo đúng định dạng (*.png, *.jpg)", "", "error");
			}
		} else {
			showNotification("Không phải hình ảnh", "", "warning");
		}
	}
	
	@Command
	public void getAttachImages(@BindingParam("media") final Media media,
			@BindingParam("vmsgs") final ValidationMessages vmsgs) {
		if (media instanceof org.zkoss.image.Image) {
			if (media.getName().toLowerCase().endsWith(".png") || media.getName().toLowerCase().endsWith(".jpg")) {
				int lengthOfImage = media.getByteData().length;
				if (lengthOfImage > 10000000) {
					showNotification("Chọn hình ảnh có dung lượng nhỏ hơn 10MB.", "", "error");
					return;
				} else {
					String tenFile = media.getName();

					tenFile = tenFile.replace(" ", "");
					tenFile = unAccent(tenFile.substring(0, tenFile.lastIndexOf("."))) + "_"
							+ Calendar.getInstance().getTimeInMillis() + tenFile.substring(tenFile.lastIndexOf("."));
					setImageContent((org.zkoss.image.Image) media);
					setIconName(tenFile);
					if (vmsgs != null) {
						vmsgs.clearKeyMessages("errLabel");
					}
					BindUtils.postNotifyChange(null, null, this, "imageContent");
					BindUtils.postNotifyChange(null, null, this, "iconname");
				}
			} else {
				showNotification("Chọn hình ảnh theo đúng định dạng (*.png, *.jpg)", "", "error");
			}
		} else {
			showNotification("Không phải hình ảnh", "", "warning");
		}
	}
	
	protected void saveImageToServer() throws IOException {

		Image imageContent2 = getImageContent();
		if (imageContent2 != null) {
			// luu hinh
			if (getIconName() != null && !getIconName().isEmpty()) {
				setIconUrl(folderImageUrl().concat(getIconName()));
				final File baseDir = new File(folderStore().concat(getIconName()));
				Files.copy(baseDir, imageContent2.getStreamData());
			}
		}
	}

	@Transient
	public String folderImageUrl() {
		return "/" + Labels.getLabel("filestore.folder") + "/canlamsang/";
	}

	public String getTieuDe() {
		return tieuDe;
	}

	public void setTieuDe(String tieuDe) {
		this.tieuDe = tieuDe;
	}

	@Column(columnDefinition="TEXT")
	public String getGhiChu() {
		return ghiChu;
	}

	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}

	public Date getNgayKham() {
		return ngayKham;
	}

	public void setNgayKham(Date ngayKham) {
		this.ngayKham = ngayKham;
	}

	@Command
	public void saveCanLamSang(@BindingParam("wdn") final Window wdn, 
			@BindingParam("vmArgs") Object object,
			@BindingParam("attr") final String attr)
			throws IOException {
		beforeSaveImg();
		save();
		wdn.detach();
		BindUtils.postNotifyChange(null, null, object, attr);
	}
	
}
