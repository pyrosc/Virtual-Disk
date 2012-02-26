package virtualDisk;

public enum DiskType {
	READ_ONLY("r"), READ_WRITE("rw");

	private String type;

	DiskType(String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
	}
}