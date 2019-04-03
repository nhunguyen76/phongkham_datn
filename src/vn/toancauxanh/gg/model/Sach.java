package vn.toancauxanh.gg.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.sys.ValidationMessages;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.image.AImage;
import org.zkoss.image.Image;
import org.zkoss.io.Files;
import org.zkoss.util.media.Media;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.google.common.base.Strings;
import com.querydsl.core.annotations.QueryInit;

import vn.toancauxanh.model.Model;

@Entity
@Table(name = "sach")
public class Sach extends Model<Sach>{

	public static transient final Logger LOG = LogManager.getLogger(Image.class.getName());
	
	private String tieuDe;
	private String moTa;
	private Float rate;
	private Image imageContent;
	private String sachUrl = "";
	private String name = "";
	private boolean flagImage = true;
	private List<FileEntry> fileEntries = new ArrayList<>();

	public String getTieuDe() {
		return tieuDe;
	}

	public void setTieuDe(String tieuDe) {
		this.tieuDe = tieuDe;
	}

	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}

	public Float getRate() {
		return rate;
	}

	public void setRate(Float rate) {
		this.rate = rate;
	}

	@Transient
	public  org.zkoss.image.Image getImageContent() {
		if (imageContent == null && !noId()
				&& !core().TT_DA_XOA.equals(getTrangThai())) {
			if (flagImage) {

				try {
					loadImageIsView();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return imageContent;
	}

	public void setImageContent( org.zkoss.image.Image _imageContent) {
		this.imageContent = _imageContent;
	}
	
	public String getSachUrl() {
		return sachUrl;
	}

	public void setSachUrl( String sachUrl) {
		this.sachUrl = Strings.nullToEmpty(sachUrl);
	}
	
	private void loadImageIsView() throws FileNotFoundException, IOException {
		String imgName = "";
		String path = "";
		path = folderStore() + getName();
		if (!"".equals(getSachUrl()) && new File(path).exists()) {
			
			try (FileInputStream fis = new FileInputStream(new File(path));){
				setImageContent(new AImage(imgName, fis));
			}
		}
	}
	
	public String getName() {
		return name;
	}

	public void setName( String _name) {
		this.name = Strings.nullToEmpty(_name);
	}
	
	@Transient
	public boolean isFlagImage() {
		return flagImage;
	}

	public void setFlagImage(boolean _flagImage) {
		this.flagImage = _flagImage;
	}
	
	@Transient
	public AbstractValidator getValidatorSach() {
		return new AbstractValidator() {
			@Override
			public void validate(final  ValidationContext ctx) {
				if (getImageContent() == null) {
					addInvalidMessage(ctx, "error",
							"Bạn chưa chọn hình ảnh cho sách.");
				}
				Double gia = (Double) ctx.getProperties("giaTien")[0].getValue();
				System.out.println(gia);
				if (gia == 0) {
	                addInvalidMessage(ctx, "giaTien" ,"Không được để trống trường này.");
	            }
			}
		};
	}
	
	@Command
	public void saveSach(@BindingParam("list") final Object listObject,
			@BindingParam("attr") final String attr,
			@BindingParam("wdn") final Window wdn) throws IOException {
		
		transactioner().execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(
					TransactionStatus arg0) {
				try {
					beforeSaveImg();
				for (FileEntry fileEntry : getFileEntries()) {
					fileEntry.saveNotShowNotification();
				}
				save();
				wdn.detach();
				BindUtils.postNotifyChange(null, null, listObject, attr);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		});
	}
	
	@Command
	public void attachImages(@BindingParam("media") final Media media,
			@BindingParam("vmsgs")  final ValidationMessages vmsgs) {
		LOG.info("attachImages");
		if (media instanceof org.zkoss.image.Image) {
			if(media.getName().toLowerCase().endsWith(".png")
				|| media.getName().toLowerCase().endsWith(".jpg")){
				int lengthOfImage = media.getByteData().length;
				if (lengthOfImage > 10000000) {
			        showNotification("Chọn hình ảnh có dung lượng nhỏ hơn 10MB.", "", "error");
			        return;
				}
				else{
					String tenFile = media.getName();
					tenFile = tenFile.replace(" ", "");
					tenFile = tenFile.substring(0, tenFile.lastIndexOf(".")) + "_"
							+ Calendar.getInstance().getTimeInMillis()
							+ tenFile.substring(tenFile.lastIndexOf("."));
					setImageContent((org.zkoss.image.Image) media);			
					setName(tenFile);
					if (vmsgs != null) {
						vmsgs.clearKeyMessages("errLabel");
					}
					BindUtils.postNotifyChange(null, null, this, "imageContent");
					BindUtils.postNotifyChange(null, null, this, "name");
				}
			} else {
				showNotification("Chọn hình ảnh theo đúng định dạng (*.png, *.jpg)","","error");
			}
		} else {
			showNotification("File tải lên không phải hình ảnh!", "", "error");
		}
	}
	
	@Command
	public void deleteImg() {
		LOG.info("deleteImg--");
		setImageContent(null);
		setName("");
		flagImage = false;
		BindUtils.postNotifyChange(null, null, this, "imageContent");
		BindUtils.postNotifyChange(null, null, this, "name");
	}
	
	private boolean beforeSaveImg() throws IOException {
		if (getImageContent() == null) {
			return false;
		}
		saveImageToServer();
		return true;
	}

	protected void saveImageToServer() throws IOException {
		
		Image imageContent2 = getImageContent();
		if (imageContent2 != null) {
			// luu hinh
			LOG.info("saveImage() :" + folderStore() + getName());
			
			setSachUrl(folderCarUrl().concat(getName()));
			final File baseDir = new File(folderStore().concat(getName()));
			Files.copy(baseDir, imageContent2.getStreamData());
		}
	}
	
	@Transient
	public String folderCarUrl() {
		return "/" + Labels.getLabel("filestore.folder") + "/sach/";
	}
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "sach_has_file_entry", joinColumns = { @JoinColumn(name = "sach_id") }, inverseJoinColumns = { @JoinColumn(name = "file_entry_id") })
	@Fetch(value = FetchMode.SUBSELECT)
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	public List<FileEntry> getFileEntries() {
		return this.fileEntries;
	}

	public void setFileEntries(List<FileEntry> fileEntries1) {
		this.fileEntries = fileEntries1;
	}
	
	@Command
	public void downloadFile(@BindingParam("item") final FileEntry e)
			throws IOException {
		if (!getFileEntries().isEmpty()) {
			final String path = folderStore() + e.getTepDinhKem();
			if (new File(path).exists()) {
				String tenFileRename;
				if (e.getTepDinhKem().lastIndexOf('_') == -1) {
					tenFileRename = e.getTepDinhKem();
				} else {
					tenFileRename = e.getTepDinhKem().substring(0,
							e.getTepDinhKem().lastIndexOf('_'))
							+ e.getTepDinhKem().substring(
									e.getTepDinhKem().lastIndexOf('.'));
				}
				LOG.info("path downloadFile(): " + path);
				LOG.info("tenFileRename downloadFile(): "
						+ getFileEntries().get(0).getTepDinhKem());
				if (!"".equals(e.getTepDinhKem())) {
					Filedownload.save(new URL("file://" + path).openStream(),null, tenFileRename);
					//System.out.println("FileDownload save: file:///" + path);
					//System.out.println("user-agent: " + Executions.getCurrent().getHeader("user-agent"));					
				}

			} else {
				showNotification("Không tìm thấy tệp tin!", "", "error");
			}
		}
	}
	
	@Command
	public void deleteFileDinhKem(@BindingParam("item") final FileEntry e) {
		Messagebox.show("Bạn muốn xóa file đính kèm này?", "Xác nhận",
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION,
				new org.zkoss.zk.ui.event.EventListener<Event>() {
					@Override
					public void onEvent(final Event event) {
						if (Messagebox.ON_OK.equals(event.getName())) {
							getFileEntries().remove(e);
							BindUtils.postNotifyChange(null, null,
									Sach.this, "fileEntries");
						}
					}
				});
		// }
	}
	
	@Command
	public void uploadFile(@BindingParam("media") final Media media,
			@BindingParam("vmsgs")  ValidationMessages vmsgs)
			throws IOException {
		if (media.getName().toLowerCase().endsWith(".doc")
				|| media.getName().toLowerCase().endsWith(".docx")
				|| media.getName().toLowerCase().endsWith(".pdf")
				|| media.getName().toLowerCase().endsWith(".xls")
				|| media.getName().toLowerCase().endsWith(".xlsx")) {
			int length = media.getByteData().length;
			if (length > 50000000) {
		        showNotification("Chọn file đính kèm có dung lượng nhỏ hơn 50MB.", "", "error");
		        return;
			}
			else{
				final long dateTime = new Date().getTime();
				final String tenFile = unAccent(media.getName().substring(0,media.getName().lastIndexOf('.')))
						+ "_"
						+ dateTime
						+ media.getName().substring(media.getName().lastIndexOf('.'));
				final String filePathDoc = folderStore() + tenFile;
	
				final File baseDir = new File(filePathDoc);
				baseDir.getParentFile().mkdirs();
	
				FileEntry entry = new FileEntry();
				entry.setName(tenFile);
				entry.setExtension(getExtension(Strings.nullToEmpty(media.getName())));
				entry.setFileUrl(folderUrl() + tenFile);
				entry.setTepDinhKem(tenFile);
				entry.setTenHienThi(entry.getTenFileDinhKem());
				// getFileEntries().clear();
				getFileEntries().add(entry);
	
				Files.copy(baseDir, media.getStreamData());
				if (vmsgs != null) {
					vmsgs.clearKeyMessages("uploadbtn");
				}
				BindUtils.postNotifyChange(null, null, this, "fileEntries");
				BindUtils.postNotifyChange(null, null, vmsgs, "*");
				showNotification("Tải tập tin thành công!", "", "success");
			}
		} else {
			showNotification("Chọn tập tin theo đúng định dạng (*.doc, *.docx, *.xls, *.xlsx, *.pdf)", "", "error");
		}
	}

}
