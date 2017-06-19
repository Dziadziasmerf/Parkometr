package beans;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.tomcat.util.http.fileupload.util.Streams;
import persistence.service.interfaces.SpaceService;
import persistence.service.interfaces.TicketService;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@ViewScoped
@ManagedBean
public class ReportManager {

    @Inject
    SpaceService spaceService;

    @Inject
    TicketService ticketService;

    private byte[] exportContent;

    public boolean isReady() {
        return exportContent != null;
    }
    public void export() {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        ec.responseReset();
        ec.setResponseContentType("application/pdf");
        ec.setResponseContentLength(exportContent.length);
        String attachmentName = "attachment; filename=\"raport.pdf\"";
        ec.setResponseHeader("Content-Disposition", attachmentName);
        try {
            OutputStream output = ec.getResponseOutputStream();
            Streams.copy(new ByteArrayInputStream(exportContent), output, false);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        fc.responseComplete();
    }


    public void prepare() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Document document = new Document();

        try {
            PdfWriter writer = PdfWriter.getInstance(document, byteArrayOutputStream);

            document.open();
            document.add(new Paragraph("Ilosc sprzedanych biletów :" + ticketService.findAll().size()));

            document.close();
            writer.close();

            exportContent = byteArrayOutputStream.toByteArray();

        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public byte[] getExportContent() {
        return exportContent;
    }

    public void setExportContent(byte[] exportContent) {
        this.exportContent = exportContent;
    }
}
