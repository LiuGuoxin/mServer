package tools.data.input;

import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessByteStream implements SeekableInputStreamBytestream
{
    private final RandomAccessFile raf;
    private long read;
    
    public RandomAccessByteStream(final RandomAccessFile raf) {
        this.read = 0L;
        this.raf = raf;
    }
    
    @Override
    public final int readByte() {
        try {
            final int temp = this.raf.read();
            if (temp == -1) {
                throw new RuntimeException("EOF");
            }
            ++this.read;
            return temp;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public final void seek(final long offset) throws IOException {
        this.raf.seek(offset);
    }
    
    @Override
    public final long getPosition() throws IOException {
        return this.raf.getFilePointer();
    }
    
    @Override
    public final long getBytesRead() {
        return this.read;
    }
    
    @Override
    public final long available() {
        try {
            return this.raf.length() - this.raf.getFilePointer();
        }
        catch (IOException e) {
            System.err.println("ERROR" + e);
            return 0L;
        }
    }
    
    @Override
    public final String toString(final boolean b) {
        return this.toString();
    }
}
