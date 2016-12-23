package dto;

import java.util.Date;

public class EntriesPojo {

	private Integer id;
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public boolean isDownloadStatus() {
		return downloadStatus;
	}

	public void setDownloadStatus(boolean downloadStatus) {
		this.downloadStatus = downloadStatus;
	}

	public Date getDownloadStarted() {
		return downloadStarted;
	}

	public void setDownloadStarted(Date downloadStarted) {
		this.downloadStarted = downloadStarted;
	}

	public Date getDownloadCompleted() {
		return downloadCompleted;
	}

	public void setDownloadCompleted(Date downloadCompleted) {
		this.downloadCompleted = downloadCompleted;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	private String name;
	private String owner;
	private boolean downloadStatus;
	private Date downloadStarted;
	private Date downloadCompleted;
	private String path;

	public EntriesPojo(String name, String owner, boolean downloadStatus, Date downloadStarted, Date downloadCompleted,
			String path) {
		super();
		this.name = name;
		this.owner = owner;
		this.downloadStatus = downloadStatus;
		this.downloadStarted = downloadStarted;
		this.downloadCompleted = downloadCompleted;
		this.path = path;
	}

}
