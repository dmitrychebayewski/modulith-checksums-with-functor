package nl.rotterdamminsk.checksumsdemo.util;

import nl.rotterdamminsk.checksumsdemo.model.Attachment;
import nl.rotterdamminsk.checksumsdemo.model.Role;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class AttachmentChecksumCalculator {
    private final Attachment attachment;

    public AttachmentChecksumCalculator(Attachment attachment) {
        this.attachment = attachment;
    }

    /**
     * Optimized MD5 checksum calculation for all attachment fields
     * @return MD5 checksum as hex string
     */
    public String calculate() {
        if (this.attachment == null) {
            return null;
        }
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            // Use efficient byte operations instead of ByteArrayOutputStream
            updateDigestWithLong(md, attachment.getVersion());
            updateDigestWithString(md, attachment.getType());
            updateDigestWithRoles(md, attachment.getRoles());
            updateDigestWithByteArray(md, attachment.getData());

            byte[] hashBytes = md.digest();
            return bytesToHex(hashBytes);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not available", e);
        }
    }

    private void updateDigestWithLong(MessageDigest md, long value) {
        md.update((byte) (value >>> 56));
        md.update((byte) (value >>> 48));
        md.update((byte) (value >>> 40));
        md.update((byte) (value >>> 32));
        md.update((byte) (value >>> 24));
        md.update((byte) (value >>> 16));
        md.update((byte) (value >>> 8));
        md.update((byte) value);
    }
    private void updateDigestWithString(MessageDigest md, String str) {
        if (str != null) {
            md.update(str.getBytes(StandardCharsets.UTF_8));
        }
    }

    private void updateDigestWithRoles(MessageDigest md, List<Role> roles) {
        if (roles != null) {
            for (Role role : roles) {
                if (role != null) {
                    md.update(role.toString().getBytes(StandardCharsets.UTF_8));
                }
            }
        }
    }
    private void updateDigestWithByteArray(MessageDigest md, byte[] data) {
        if (data != null) {
            md.update(data);
        }
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
