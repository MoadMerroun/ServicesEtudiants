/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.servicesetudiants;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPage;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.StyleConstants.ColorConstants;

/**
 *
 * @author Surface Pro
 */
public class GenererDemandeStage {
    public static void DemandeStage(String TypeStage, String DebutStage, String FinStage, String NomEntreprise, String Apoge, String Nom, String Prenom, String Filiere, String EmailInstitutionnel){
        Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.BOLD);
        Font footerFont = new Font(Font.FontFamily.TIMES_ROMAN, 10);
        Document doc = new Document(PageSize.A4);
        try {
            PdfWriter.getInstance(doc, new FileOutputStream("src\\main\\resources\\Documents\\demande_de_stage_"+Prenom+"_"+Nom+".pdf"));
            doc.open();
            Image img = Image.getInstance("src\\main\\resources\\images\\ENSA-Tétouan.png");
            img.scaleAbsolute(90, 90);
            img.setAlignment(Image.ALIGN_LEFT);
            doc.add(img);
            doc.add(new Paragraph("                                                                                                        Tétouan le 12/01/2020"));
            doc.add(new Paragraph(" "));
            doc.add(new Paragraph(" "));
            doc.add(new Paragraph("                     Le directeur de l'École Nationale des Sciences Appliquées de Tétouan", boldFont));
            doc.add(new Paragraph("                                                                             A", boldFont));
            doc.add(new Paragraph("                                                  Monsieur le directeur General de "+NomEntreprise, boldFont));
            doc.add(new Paragraph(" "));
            doc.add(new Paragraph(" "));
            doc.add(new Paragraph("                     Objet : "+ TypeStage +" - De "+ DebutStage +" à "+ FinStage+"\n\n", boldFont));
            doc.add(new Paragraph("                     Monsieur,\n\n" +
"                     Soucieuse d'adapter sa formation aux exigences du marché de l'emploi, L'Ecole Nationale des Sciences Appliquées de Tétouan organise des stages pour ses éleves dans les entreprises afin de consolider leurs connaissances acquises a l'école.\n\n" +
"                     A cet effet, nous vous demandons de bien vouloir admettre l’éleve " + Nom +" "+ Prenom +  " en " + Filiere + " pour effectuer un stage au sein de votre entreprise.\n\n" +
"                     En cas d'accord de votre part, nous vous saurions gré de bien vouloir nous retoumer dans les meilleurs délais la convention cadre ci-joint, dument remplie par vos soins.\n\n" +
"                     Nous précisons que notre éleve sera soumise a la réglementation en vigueur dans votre entreprise.\n\n" +
"                     Nous assurons par ailleurs de notre entiere collaboration et nous sommes reconnaissants de l'intérét que vous portez à l'ENSA-Tétouan.\n\n" +
"                     Nous vous prions d'agréer, Monsieur, l'expression de notre consideration distinguée.\n\n"));
            Image img1 = Image.getInstance("src\\main\\resources\\images\\cachet.png");
            img1.scaleAbsolute(250, 75);
            img1.setAlignment(Image.ALIGN_CENTER);
            doc.add(img1);
            LineSeparator ls = new LineSeparator();
            doc.add(new Chunk(ls));
            doc.add(new Paragraph("                              Ecole Nationale des Sciences Appliquées de Tétouan. B.P 2222 M’hannech II — Tétouan \n" +
"                                                           Tél. 053688027; Fax. 0539994624; www.ensate.uae.ma",footerFont));
        Rectangle rect= new Rectangle(577,825,18,15); 
        rect.enableBorderSide(1);
        rect.enableBorderSide(2);
        rect.enableBorderSide(4);
        rect.enableBorderSide(8);
        rect.setBorderColor(BaseColor.BLACK);
        rect.setBorderWidth(1);
        doc.add(rect);
        doc.close();
        SendEmail.envoyerEmailDemandeDeStage(EmailInstitutionnel, "la demande de stage",Prenom +"_"+Nom, "demande_de_stage_",Prenom+" "+Nom);
        //Desktop.getDesktop().open(new File ("src\\main\\resources\\Documents\\Demande_De_Stage.pdf"));
            
                    } 
        catch (DocumentException ex) {
            Logger.getLogger(GenererDemandeStage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GenererDemandeStage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GenererDemandeStage.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
