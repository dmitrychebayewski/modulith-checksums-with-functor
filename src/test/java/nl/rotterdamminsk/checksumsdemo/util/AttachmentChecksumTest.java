package nl.rotterdamminsk.checksumsdemo.util;

import nl.rotterdamminsk.checksumsdemo.common.ValueHolder;
import nl.rotterdamminsk.checksumsdemo.model.Attachment;
import nl.rotterdamminsk.checksumsdemo.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AttachmentChecksumTest {

    private byte[] pdfData1;
    private byte[] pdfData2;
    private byte[] pdfData3;
    private List<Role> roles1;
    private List<Role> roles2;

    @BeforeEach
    void setUp() {
        pdfData1 = "Sample PDF content 1".getBytes();
        pdfData2 = "Sample PDF content 2".getBytes();
        pdfData3 = "Sample PDF content 1".getBytes(); // Same as data1

        roles1 = new ArrayList<>();
        roles2 = new ArrayList<>();
        roles1.add(new Role());
        roles2.add(new Role());
    }

    @Test
    void calculateChecksum() {
        ValueHolder<AttachmentChecksumCalculator> calc1 = new ValueHolder<>(new Attachment(1L, "pdf", roles1, pdfData1)).map(AttachmentChecksumCalculator::new);
        ValueHolder<AttachmentChecksumCalculator> calc2 = new ValueHolder<>(new Attachment(2L, "pdf", roles2, pdfData2)).map(AttachmentChecksumCalculator::new);
        ValueHolder<AttachmentChecksumCalculator> clc3 = new ValueHolder<>(new Attachment(1L, "pdf", roles1, pdfData3)).map(AttachmentChecksumCalculator::new);

        assertTrue(calc1.getValue().isPresent(), "AttachmentChecksum should not be null");
        assertTrue(calc2.getValue().isPresent(), "AttachmentChecksum should not be null");
        assertTrue(clc3.getValue().isPresent(), "AttachmentChecksum should not be null");

        assertTrue(calc1.map(AttachmentChecksumCalculator::calculate).getValue().isPresent(), "AttachmentChecksum should not be null");
        assertTrue(calc2.map(AttachmentChecksumCalculator::calculate).getValue().isPresent(), "AttachmentChecksum should not be null");
        assertTrue(clc3.map(AttachmentChecksumCalculator::calculate).getValue().isPresent(), "AttachmentChecksum should not be null");

        // Different attachments should have different checksums
        assertNotEquals(calc1.map(AttachmentChecksumCalculator::calculate).getValue().get(),
                calc2.map(AttachmentChecksumCalculator::calculate).getValue().get(),
                "Different attachments should have different checksums");

        // Same content should have same checksums
        assertEquals(calc1.map(AttachmentChecksumCalculator::calculate).getValue().get(), clc3.map(AttachmentChecksumCalculator::calculate).getValue().get(),
                "Attachments with same content should have same checksums");
    }
}