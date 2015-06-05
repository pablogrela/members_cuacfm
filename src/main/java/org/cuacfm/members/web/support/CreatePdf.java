package org.cuacfm.members.web.support;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.paymember.PayMember;
import org.cuacfm.members.model.payprogram.PayProgram;
import org.cuacfm.members.model.util.States.states;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

/** The Class PdfFooterNumPag. */
public class CreatePdf {

   /** The Constant ALL. */
   private static final String ALL = "ALL";

   /** The Constant NOPAY. */
   private static final String NOPAY = "NOPAY";

   /** The Constant PAY. */
   private static final String PAY = "PAY";

   /**
    * View pdf.
    *
    * @param path
    *           the path
    * @param file
    *           the file
    * @return the response entity
    */
   public static ResponseEntity<byte[]> viewPdf(String path, String file) {
      Path path2 = Paths.get(path);
      byte[] contents = null;
      try {
         contents = Files.readAllBytes(path2);
      } catch (IOException e) {
         e.getMessage();
      }

      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.parseMediaType("application/pdf"));
      headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
      headers.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{file}")
            .buildAndExpand(file).toUri());
      headers.add("content-disposition", "attachment; filename=" + file + ";");
      return new ResponseEntity<byte[]>(contents, headers, HttpStatus.OK);
   }

   /** The Class HeaderFooter. */
   class HeaderFooter extends PdfPageEventHelper {

      @Override
      public void onEndPage(PdfWriter writer, Document document) {

         Phrase[] headers = new Phrase[3];

         PdfContentByte cb = writer.getDirectContent();

         headers[0] = new Phrase("CuacFM");
         ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT, headers[0], document.left() + 80,
               document.bottom() - 20, 0);

         Date day = new Date();
         headers[1] = new Phrase(DisplayDate.dateTimeToString(day));
         ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT, headers[1],
               (document.right() - document.left()) / 2 + document.leftMargin(),
               document.bottom() - 20, 0);

         headers[2] = new Phrase(String.format("Página %d", writer.getCurrentPageNumber()));
         ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT, headers[2], document.right() - 80,
               document.bottom() - 20, 0);
      }
   }

   /**
    * Creates the body.
    *
    * @param path
    *           the path
    * @param title
    *           the title
    * @param table
    *           the table
    */
   public void createBody(String path, String title, PdfPTable table) {
      try {

         /*
          * Declaramos document como un objeto DocumentAsignamos el tamaño de
          * hoja y los margenes
          */
         Document document = new Document(PageSize.A4, 20, 20, 20, 30);
         // Obtenemos la instancia del archivo a utilizar
         PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(new File(path)));

         // instanciamos la clase HeaderFooter
         HeaderFooter event = new HeaderFooter();
         // Al haber un evento se reconocerá en la clase
         writer.setPageEvent(event);

         // Agregamos un titulo al archivo
         String[] names = path.split("/");
         int namesLength = names.length;
         document.addTitle(names[namesLength - 1]);

         // Agregamos el autor del archivo
         document.addAuthor("cuacfm");

         // Abrimos el document para edición
         document.open();

         Paragraph parrafo = new Paragraph();
         parrafo.setAlignment(Paragraph.ALIGN_CENTER);
         parrafo.setFont(FontFactory.getFont("Sans", 20, Font.BOLD));
         parrafo.add(title);

         // Agregamos el texto al document
         document.add(Chunk.NEWLINE);
         document.add(parrafo);
         document.add(Chunk.NEWLINE);
         document.add(table);

         document.close(); // Cerramos el document
         writer.close(); // Cerramos writer

      } catch (FileNotFoundException | DocumentException e) {
         e.getMessage();
      }
   }

   /**
    * Creates the table.
    *
    * @param messageSource
    *           the message source
    * @param type
    *           the type
    * @param payMembers
    *           the user fee members
    * @return the pdf p table
    */
   public PdfPTable createTablePayMembers(MessageSource messageSource, String type,
         List<PayMember> payMembers) {

      PdfPTable table = new PdfPTable(7);
      Double totalPrice = Double.valueOf(0);
      Double collectPrice = Double.valueOf(0);
      float[] values = new float[] { 180, 80, 180, 120, 70, 70, 70 };

      if (!type.equals(ALL)) {
         table = new PdfPTable(6);
         values = new float[] { 180, 80, 180, 120, 70, 70 };
      }
      try {
         table.setWidths(values);
      } catch (DocumentException e) {
         e.getMessage();
      }
      table.setWidthPercentage(100);
      table.setSpacingBefore(0f);
      table.setSpacingAfter(0f);
      table.getDefaultCell().setUseAscender(true);
      table.getDefaultCell().setUseDescender(true);
      table.getDefaultCell().setBackgroundColor(BaseColor.LIGHT_GRAY);

      // Header and Footer
      for (int i = 0; i < 2; i++) {
         table.addCell(messageSource.getMessage("name", null, Locale.getDefault()));
         table.addCell(messageSource.getMessage("phone", null, Locale.getDefault()));
         table.addCell(messageSource.getMessage("email", null, Locale.getDefault()));
         table.addCell(messageSource.getMessage("programName", null, Locale.getDefault()));
         table.addCell(messageSource.getMessage("installment", null, Locale.getDefault()));
         table.addCell(messageSource.getMessage("price", null, Locale.getDefault()));
         if (type.equals(ALL)) {
            table.addCell(messageSource.getMessage("hasPay", null, Locale.getDefault()));
         }
      }
      table.getDefaultCell().setBackgroundColor(null);
      table.setHeaderRows(2);
      table.setFooterRows(1);

      // Elements of table
      for (PayMember u : payMembers) {

         table.getDefaultCell().setBackgroundColor(null);
         if (u.getInstallments() > 1) {
            table.getDefaultCell().setBackgroundColor(BaseColor.YELLOW);
         } else if ((type.equals(ALL)) && (!u.getState().equals(states.PAY))) {
            table.getDefaultCell().setBackgroundColor(BaseColor.RED);
         }

         if ((type.equals(ALL)) || (type.equals(NOPAY) && !u.getState().equals(states.PAY))
               || (type.equals(PAY) && u.getState().equals(states.PAY))) {
            table.addCell(u.getAccount().getName());
            table.addCell(String.valueOf(u.getAccount().getMobile()));
            table.addCell(u.getAccount().getEmail());
            table.addCell(String.valueOf(u.getAccount().getProgramName()));
            table.addCell(String.valueOf(u.getInstallment())
                  + messageSource.getMessage("of", null, Locale.getDefault())
                  + String.valueOf(u.getInstallments()));
            totalPrice = totalPrice + u.getPrice();
            table.addCell(String.valueOf(u.getPrice()));

            // Pay or no Pay
            if ((type.equals(ALL)) && (u.getState().equals(states.PAY))) {
               collectPrice = collectPrice + u.getPrice();
               table.addCell(String.valueOf(u.getPrice()));
               // table.addCell(messageSource.getMessage("hasPay", null,
            } else if ((type.equals(ALL)) && (!u.getState().equals(states.PAY))) {
               table.addCell(String.valueOf(0));
               // table.addCell(messageSource.getMessage("hasNoPay", null,
            }
         }
      }

      // Final Sum
      table.getDefaultCell().setBackgroundColor(BaseColor.GRAY);
      table.addCell(" ");
      table.addCell(" ");
      table.addCell(" ");
      table.addCell(" ");
      table.addCell(" ");
      table.addCell(String.valueOf(totalPrice));
      if (type.equals(ALL)) {
         table.addCell(String.valueOf(collectPrice));
      }
      return table;
   }

   /**
    * Creates the table.
    *
    * @param messageSource
    *           the message source
    * @param type
    *           the type
    * @param payPrograms
    *           the pay programs
    * @return the pdf p table
    */
   public PdfPTable createTablePayPrograms(MessageSource messageSource, String type,
         List<PayProgram> payPrograms) {

      PdfPTable table = new PdfPTable(6);
      Double totalPrice = Double.valueOf(0);
      Double collectPrice = Double.valueOf(0);
      float[] values = new float[] { 120, 180, 80, 180, 70, 70 };

      if (!type.equals(ALL)) {
         table = new PdfPTable(5);
         values = new float[] { 120, 180, 80, 180, 70 };
      }
      try {
         table.setWidths(values);
      } catch (DocumentException e) {
         e.getMessage();
      }
      table.setWidthPercentage(100);
      table.setSpacingBefore(0f);
      table.setSpacingAfter(0f);
      table.getDefaultCell().setUseAscender(true);
      table.getDefaultCell().setUseDescender(true);
      table.getDefaultCell().setBackgroundColor(BaseColor.LIGHT_GRAY);

      // Header and Footer
      for (int i = 0; i < 2; i++) {
         table.addCell(messageSource.getMessage("programName", null, Locale.getDefault()));
         table.addCell(messageSource.getMessage("name", null, Locale.getDefault()));
         table.addCell(messageSource.getMessage("phone", null, Locale.getDefault()));
         table.addCell(messageSource.getMessage("email", null, Locale.getDefault()));
         table.addCell(messageSource.getMessage("price", null, Locale.getDefault()));
         if (type.equals(ALL)) {
            table.addCell(messageSource.getMessage("hasPay", null, Locale.getDefault()));
         }
      }
      table.getDefaultCell().setBackgroundColor(null);
      table.setHeaderRows(2);
      table.setFooterRows(1);

      // Elements of table
      for (PayProgram p : payPrograms) {

         table.getDefaultCell().setBackgroundColor(null);

         if ((type.equals(ALL)) && (!p.getState().equals(states.PAY))) {
            table.getDefaultCell().setBackgroundColor(BaseColor.RED);
         }

         if ((type.equals(ALL)) || (type.equals(NOPAY) && !p.getState().equals(states.PAY))
               || (type.equals(PAY) && p.getState().equals(states.PAY))) {
            table.addCell(p.getProgram().getName());
            table.addCell(" ");
            table.addCell(" ");
            table.addCell(" ");
            totalPrice = totalPrice + p.getPrice();
            table.addCell(String.valueOf(p.getPrice()));

            // Pay or no Pay
            if ((type.equals(ALL)) && (p.getState().equals(states.PAY))) {
               collectPrice = collectPrice + p.getPrice();
               table.addCell(String.valueOf(p.getPrice()));
               // table.addCell(messageSource.getMessage("hasPay", null,
            } else if ((type.equals(ALL)) && (!p.getState().equals(states.PAY))) {
               table.addCell(String.valueOf(0));
               // table.addCell(messageSource.getMessage("hasNoPay", null,
            }
            for (Account u : p.getProgram().getAccounts()) {
               table.addCell(" ");
               table.addCell(u.getName());
               table.addCell(String.valueOf(u.getMobile()));
               table.addCell(u.getEmail());
               table.addCell(" ");
               if (type.equals(ALL)) {
                  table.addCell(" ");
               }
            }
         }
      }

      // Final Sum
      table.getDefaultCell().setBackgroundColor(BaseColor.GRAY);
      table.addCell(" ");
      table.addCell(" ");
      table.addCell(" ");
      table.addCell(" ");
      table.addCell(String.valueOf(totalPrice));
      if (type.equals(ALL)) {
         table.addCell(String.valueOf(collectPrice));
      }
      return table;
   }

}