package virtualDisk;

import static virtualDisk.DiskType.READ_WRITE;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class VirtualDiskTest {

	@Test
	public void createDisk() throws IOException {
		String path = "D://disk.vd";
		File file = new File(path);
		file.createNewFile();
		new VirtualDisk(file, READ_WRITE);
	}

	@Test
	public void createDiskWithSize() throws IOException {
		String path = "D://disk2.vd";
		File file = new File(path);
		file.createNewFile();
		new VirtualDisk(file, READ_WRITE, 1024 * 1024);
	}
}