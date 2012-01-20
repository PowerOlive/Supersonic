package be.hehehe.supersonic.model;

public class SongModel {
	private String id;
	private String title;
	private String artist;
	private String album;
	private String coverId;

	private int track;
	private long size;
	private int duration;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public int getTrack() {
		return track;
	}

	public void setTrack(int track) {
		this.track = track;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getCoverId() {
		return coverId;
	}

	public void setCoverId(String coverId) {
		this.coverId = coverId;
	}

}