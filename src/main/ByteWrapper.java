package main;

import java.util.Arrays;

public final class ByteWrapper {
    private final byte[] byteSequence;

    public ByteWrapper(byte[] byteSequence) {
        this.byteSequence = byteSequence;
    }

    public byte[] getByteSequence() {
        return byteSequence.clone();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ByteWrapper byteWrapper = (ByteWrapper) object;
        return Arrays.equals(byteSequence, byteWrapper.byteSequence);
    }

    @Override
    public String toString() {
        return Arrays.toString(byteSequence);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(byteSequence);
    }
}
