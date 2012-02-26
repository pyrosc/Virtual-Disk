package virtualDisk;

import static virtualDisk.DiskType.READ_ONLY;
import static virtualDisk.FileValidator.validate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.IllegalSelectorException;

public class VirtualDisk {

	private RandomAccessFile randomAccessFile;
	private FileChannel fileChannel;
	private DiskType type;
	private boolean closed;

	public VirtualDisk(File file, DiskType type) throws FileNotFoundException {
		validate(file);
		this.type = type;
		this.closed = false;

		randomAccessFile = new RandomAccessFile(file, type.getType());
		this.fileChannel = randomAccessFile.getChannel();
	}

	public VirtualDisk(File file, DiskType type, long size) throws IOException {
		validate(file);
		this.type = type;
		this.closed = false;

		randomAccessFile = new RandomAccessFile(file, type.getType());
		randomAccessFile.setLength(size);
		this.fileChannel = randomAccessFile.getChannel();
	}

	public void close() throws IOException {
		if (closed) {
			return;
		}

		this.closed = true;
		this.randomAccessFile.close();
	}

	public boolean isClosed() {
		return this.closed;
	}

	public long getSize() throws IOException {
		return randomAccessFile.length();
	}

	public void read(long offset, ByteBuffer destination) throws IOException {
		checkIfClosed();

		int toRead = destination.remaining();
		if (offset + toRead > getSize()) {
			throw new IOException(); // TODO add new exception
		}

		while (toRead > 0) {
			final int read = fileChannel.read(destination, offset);
			if (read < 0) {
				throw new IOException();
			}

			toRead -= read;
			offset += read;
		}
	}

	public void write(long offset, ByteBuffer source) throws IOException {
		checkIfClosed();

		if (this.type == READ_ONLY) {
			throw new IOException(); // TODO new exception
		}

		int toWrite = source.remaining();

		if (offset + toWrite > getSize()) {
			throw new IOException(); // TODO add new exception
		}

		while (toWrite > 0) {
			final int written = fileChannel.write(source, offset);

			toWrite -= written;
			offset += written;
		}

	}

	public DiskType getDiskType() {
		return type;
	}

	private void checkIfClosed() {
		if (closed) {
			throw new IllegalSelectorException();
		}
	}
}