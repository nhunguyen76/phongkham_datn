package vn.toancauxanh.model;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.image.AImage;
import org.zkoss.io.Files;
import org.zkoss.util.media.Media;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Messagebox;

@Entity
@Table(name = "settings")
public class Setting extends Model<Setting> {
	public static transient final Logger LOG = LogManager.getLogger(Setting.class.getName());
	private int widthMedium;
	private int widthSmall;
	private long dem;
	private int soNgayHetHan;
	private String donViHoTro = "";
	private String soDienThoaiHoTro = "";
	private String tenFileHDSD = "";
	private String urlFileHDSD = "";
	private boolean guiSMS;
	private String noiDungSMS = "";
	private String emailDonViHoTro;

	private String tenDonViHoTroKyThuat;
	private String sdtDonViHoTroKyThuat;
	private String emailHoTroKyThuat;
	private int soLuongTaiLieuChoPhepTaiLen = core().SO_LUONG_TAI_LIEU_DUOC_TAI_LEN_DEFAULT;
	private String diaChi = "";
	
	public Image imgQuanTriVien = new Image();

	public final static String IMG_MEDIUM_WIDTH = Labels.getLabel("conf.image.medium.width", "460");
	public final static String IMG_SMALL_WIDTH = Labels.getLabel("conf.image.small.width", "220");

	public int getWidthMedium() {
		return widthMedium;
	}
	
	public String getDiaChi() {
		return diaChi;
	}



	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}



	public int getSoLuongTaiLieuChoPhepTaiLen() {
		return soLuongTaiLieuChoPhepTaiLen;
	}



	public void setSoLuongTaiLieuChoPhepTaiLen(int soLuongTaiLieuChoPhepTaiLen) {
		this.soLuongTaiLieuChoPhepTaiLen = soLuongTaiLieuChoPhepTaiLen;
	}



	public void setWidthMedium(final int _widthMedium) {
		if (_widthMedium == 0) {
			this.widthMedium = Integer.parseInt(IMG_MEDIUM_WIDTH);
		} else {
			this.widthMedium = _widthMedium;
		}
	}

	public int getWidthSmall() {
		return widthSmall;
	}

	public void setWidthSmall(final int _widthSmall) {
		if (_widthSmall == 0) {
			this.widthSmall = Integer.parseInt(IMG_SMALL_WIDTH);
		} else {
			this.widthSmall = _widthSmall;
		}
	}

	public long getDem() {
		return dem;
	}

	public void setDem(long dem1) {
		this.dem = dem1;
	}

	public boolean isGuiSMS() {
		return guiSMS;
	}

	public void setGuiSMS(boolean guiSMS) {
		this.guiSMS = guiSMS;
	}

	@Column(length = 460)
	public String getNoiDungSMS() {
		return noiDungSMS;
	}

	public void setNoiDungSMS(String noiDungSMS) {
		this.noiDungSMS = noiDungSMS;
	}

	public int getSoNgayHetHan() {
		return soNgayHetHan;
	}

	public void setSoNgayHetHan(int soNgayHetHan) {
		this.soNgayHetHan = soNgayHetHan;
	}

	public String getDonViHoTro() {
		return donViHoTro;
	}

	public void setDonViHoTro(String donViHoTro) {
		this.donViHoTro = donViHoTro != null ? donViHoTro.trim() : donViHoTro;
	}

	public String getSoDienThoaiHoTro() {
		return soDienThoaiHoTro;
	}

	public void setSoDienThoaiHoTro(String soDienThoaiHoTro) {
		this.soDienThoaiHoTro = soDienThoaiHoTro;
	}

	public String getTenFileHDSD() {
		return tenFileHDSD;
	}

	public void setTenFileHDSD(String tenFileHDSD) {
		this.tenFileHDSD = tenFileHDSD;
	}

	public String getUrlFileHDSD() {
		return urlFileHDSD;
	}

	public void setUrlFileHDSD(String urlFileHDSD) {
		this.urlFileHDSD = urlFileHDSD;
	}

	public void addCounter() {
		dem++;
		transactioner().execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				em().merge(Setting.this);
			}
		});
	}

	@Transient
	public Image getImgQuanTriVien() {
		Image img = new Image();
		try {
			if (!getTenFileHDSD().isEmpty()) {
				if (getTenFileHDSD().toLowerCase().endsWith(".doc")
						|| getTenFileHDSD().toLowerCase().endsWith(".docx")) {
					img.setImageContent(
							new AImage(new File(Executions.getCurrent().getDesktop().getWebApp().getRealPath("")
									+ "/backend/assets/images/doc.png")));
				} else if (getTenFileHDSD().toLowerCase().endsWith(".pdf")) {
					img.setImageContent(
							new AImage(new File(Executions.getCurrent().getDesktop().getWebApp().getRealPath("")
									+ "/backend/assets/images/pdf.png")));
				} else if (getTenFileHDSD().toLowerCase().endsWith(".xls")
						|| getTenFileHDSD().toLowerCase().endsWith(".xlsx")) {
					img.setImageContent(
							new AImage(new File(Executions.getCurrent().getDesktop().getWebApp().getRealPath("")
									+ "/backend/assets/images/xls.png")));
				}
				imgQuanTriVien = img;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return imgQuanTriVien;
	}

	public void setImgQuanTriVien(Image imgQuanTriVien) {
		this.imgQuanTriVien = imgQuanTriVien;
	}

	public String getEmailDonViHoTro() {
		return emailDonViHoTro;
	}

	public void setEmailDonViHoTro(String emailDonViHoTro) {
		this.emailDonViHoTro = emailDonViHoTro;
	}

	public String getTenDonViHoTroKyThuat() {
		return tenDonViHoTroKyThuat;
	}

	public void setTenDonViHoTroKyThuat(String tenDonViHoTroKyThuat) {
		this.tenDonViHoTroKyThuat = tenDonViHoTroKyThuat != null ? tenDonViHoTroKyThuat.trim() : tenDonViHoTroKyThuat;
	}

	public String getSdtDonViHoTroKyThuat() {
		return sdtDonViHoTroKyThuat;
	}

	public void setSdtDonViHoTroKyThuat(String sdtDonViHoTroKyThuat) {
		this.sdtDonViHoTroKyThuat = sdtDonViHoTroKyThuat;
	}

	public String getEmailHoTroKyThuat() {
		return emailHoTroKyThuat;
	}

	public void setEmailHoTroKyThuat(String emailHoTroKyThuat) {
		this.emailHoTroKyThuat = emailHoTroKyThuat != null ? emailHoTroKyThuat.trim() : emailHoTroKyThuat;
	}

	@Command
	public void uploadFile(@BindingParam("media") Media media) {
		try {
			if (media.getByteData().length > 50 * 1024 * 1024) {
				Messagebox.show("Dung lượng file upload không được lớn hơn 50MB!");
				return;
			}

			if (media.getName().toLowerCase().endsWith(".docx") || media.getName().toLowerCase().endsWith(".doc")
					|| media.getName().toLowerCase().endsWith(".pdf")) {
				String pathFile = folderStore().concat(File.separator) + media.getName();
				final File newFile = new File(pathFile);
				if (newFile.exists()) {
					newFile.delete();
				}
				tenFileHDSD = media.getName();
				urlFileHDSD = pathFile;
				getImgQuanTriVien();
				BindUtils.postNotifyChange(null, null, this, "tenFileHDSD");
				BindUtils.postNotifyChange(null, null, this, "urlFileHDSD");
				BindUtils.postNotifyChange(null, null, this, "imgQuanTriVien");

				Files.copy(newFile, media.getStreamData());
				showNotification("Tải tập tin thành công!", "", "info");
			} else {
				showNotification("Chọn tập tin theo đúng định dạng (*.doc/*.docx/*.pdf)", "", "error");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Command
	public void xoaFile(@BindingParam("type") String type) {
		tenFileHDSD = "";
		urlFileHDSD = "";
		BindUtils.postNotifyChange(null, null, this, "tenFileHDSD");
		BindUtils.postNotifyChange(null, null, this, "urlFileHDSD");
	}

	@Command
	public void downloadFile() throws IOException {
		if (new File(urlFileHDSD).exists()) {
			Filedownload.save(new URL("file:" + File.separator + File.separator + urlFileHDSD).openStream(), null, tenFileHDSD);
		} else {
			showNotification("Không tìm thấy tập tin!", "", "error");
		}
	}

	@Transient
	private String getExtensionStr(final String strFileName) {
		String strExtension = "";
		if (strFileName.toLowerCase().endsWith(".doc")) {
			strExtension = "doc";
		} else if (strFileName.toLowerCase().endsWith(".docx")) {
			strExtension = "docx";
		} else if (strFileName.toLowerCase().endsWith(".pdf")) {
			strExtension = "docx";
		}
		return strExtension;
	}

}
