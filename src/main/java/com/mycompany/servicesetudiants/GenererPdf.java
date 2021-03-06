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
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPage;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.Year;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.StyleConstants.ColorConstants;

/**
 *
 * @author Surface Pro
 */
public class GenererPdf {  
    public static void DemandeStage(String TypeStage, String DebutStage, String FinStage, String NomEntreprise, String Apoge, String Nom, String Prenom, String Filiere, String EmailInstitutionnel){
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");  
        java.util.Date date = new java.util.Date();
        Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.BOLD);
        Font footerFont = new Font(Font.FontFamily.TIMES_ROMAN, 10);
        Document doc = new Document(PageSize.A4);
        try {
            PdfWriter.getInstance(doc, new FileOutputStream("src\\main\\resources\\Documents\\demande_de_stage_"+Prenom+"_"+Nom+"_"+formatter.format(date)+".pdf"));
            doc.open();
            Image img = Image.getInstance("src\\main\\resources\\images\\ENSA-T??touan.png");
            img.scaleAbsolute(90, 90);
            img.setAlignment(Image.ALIGN_LEFT);
            doc.add(img);
            doc.add(new Paragraph("                                                                                                        T??touan le 12/01/2020"));
            doc.add(new Paragraph(" "));
            doc.add(new Paragraph(" "));
            doc.add(new Paragraph("                     Le directeur de l'??cole Nationale des Sciences Appliqu??es de T??touan", boldFont));
            doc.add(new Paragraph("                                                                             A", boldFont));
            doc.add(new Paragraph("                                                  Monsieur le directeur General de "+NomEntreprise, boldFont));
            doc.add(new Paragraph(" "));
            doc.add(new Paragraph(" "));
            doc.add(new Paragraph("                     Objet : "+ TypeStage +" - De "+ DebutStage +" ?? "+ FinStage+"\n\n", boldFont));
            doc.add(new Paragraph("                     Monsieur,\n\n" +
"                     Soucieuse d'adapter sa formation aux exigences du march?? de l'emploi, L'Ecole Nationale des Sciences Appliqu??es de T??touan organise des stages pour ses ??leves dans les entreprises afin de consolider leurs connaissances acquises a l'??cole.\n\n" +
"                     A cet effet, nous vous demandons de bien vouloir admettre l?????leve " + Nom +" "+ Prenom +  " en " + Filiere + " pour effectuer un stage au sein de votre entreprise.\n\n" +
"                     En cas d'accord de votre part, nous vous saurions gr?? de bien vouloir nous retoumer dans les meilleurs d??lais la convention cadre ci-joint, dument remplie par vos soins.\n\n" +
"                     Nous pr??cisons que notre ??leve sera soumise a la r??glementation en vigueur dans votre entreprise.\n\n" +
"                     Nous assurons par ailleurs de notre entiere collaboration et nous sommes reconnaissants de l'int??r??t que vous portez ?? l'ENSA-T??touan.\n\n" +
"                     Nous vous prions d'agr??er, Monsieur, l'expression de notre consideration distingu??e.\n\n"));
            Image img1 = Image.getInstance("src\\main\\resources\\images\\cachet.png");
            img1.scaleAbsolute(250, 75);
            img1.setAlignment(Image.ALIGN_CENTER);
            doc.add(img1);
            LineSeparator ls = new LineSeparator();
            doc.add(new Chunk(ls));
            doc.add(new Paragraph("                              Ecole Nationale des Sciences Appliqu??es de T??touan. B.P 2222 M???hannech II ??? T??touan \n" +
"                                                           T??l. 053688027; Fax. 0539994624; www.ensate.uae.ma",footerFont));
        Rectangle rect= new Rectangle(577,825,18,15); 
        rect.enableBorderSide(1);
        rect.enableBorderSide(2);
        rect.enableBorderSide(4);
        rect.enableBorderSide(8);
        rect.setBorderColor(BaseColor.BLACK);
        rect.setBorderWidth(1);
        doc.add(rect);
        doc.close();
        SendEmail.envoyerEmailAvecDoc(EmailInstitutionnel, "la demande de stage", "demande_de_stage_"+Prenom+"_"+Nom+"_"+formatter.format(date)+".pdf", Prenom+" "+Nom);
        Desktop.getDesktop().open(new File ("src\\main\\resources\\Documents\\demande_de_stage_"+Prenom+"_"+Nom+"_"+formatter.format(date)+".pdf"));
           
        } 
        catch (DocumentException ex) {
            Logger.getLogger(GenererPdf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GenererPdf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GenererPdf.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public static void ConventionStage(String DebutStage, String FinStage, String NomEntreprise, String Apoge, String Nom, String Prenom, String Filiere, String EmailInstitutionnel){
        try {
            SimpleDateFormat formatter1 = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");  
            java.util.Date date1 = new java.util.Date();
            Clock clock = Clock.systemDefaultZone();
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
            java.util.Date date = new java.util.Date();
            Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD);
            Font footerFont = new Font(Font.FontFamily.TIMES_ROMAN, 10);
            Document doc = new Document(PageSize.A4);
            PdfWriter.getInstance(doc, new FileOutputStream("src\\main\\resources\\Documents\\convention_de_stage_"+Prenom+"_"+Nom+"_"+formatter1.format(date)+".pdf"));
            doc.open();
            Rectangle rect= new Rectangle(577,825,18,15);
                    rect.enableBorderSide(1);
                    rect.enableBorderSide(2);
                    rect.enableBorderSide(4);
                    rect.enableBorderSide(8);
                    rect.setBorderColor(BaseColor.BLACK);
                    rect.setBorderWidth(1);
                    doc.add(rect);
            Image img = Image.getInstance("src\\main\\resources\\images\\ENSA-T??touan.png");
            img.scaleAbsolute(100, 80);
            img.setAlignment(Image.ALIGN_LEFT);
            doc.add(img);
            LineSeparator ls3 = new LineSeparator();
            doc.add(new Chunk(ls3));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("                                                                         CONVENTION CADRE", boldFont));
            doc.add(new Paragraph("                                             Entreprise / Ecole Nationale  des Sciences Appliqu??es de T??touan\n\n\n", boldFont));
            doc.add(new Paragraph("  Entre :                  "+ NomEntreprise+"\n" , boldFont));
            doc.add(new Paragraph("  Repr??sent??e par son Directeur :\n"));
            doc.add(new Paragraph("                                                                                                                                                                  D'une part\n\n",boldFont));
            doc.add(new Paragraph(" Et : l???Ecole Nationale  des Sciences Appliqu??es de T??touan.",boldFont));
            doc.add(new Paragraph(" Repr??sent??e par : "+"Mr. Mostafa STITOU Directeur de l?????cole\n",boldFont));
            doc.add(new Paragraph("                                                                                                                                                                  D'autre part\n\n",boldFont));
            doc.add(new Paragraph(" Il a ??t?? convenu ce qui suit : \n\n"));
            doc.add(new Paragraph(" ARTICLE 1",boldFont));
            doc.add(new Paragraph("     La pr??sente convention r??git les rapports des deux parties, dans le cadre de l???organisation de stages d??? entreprises qui s???inscrivent dans le programme de formation de l?????tablissement de formation cit?? ci-dessus.\n\n"));
            doc.add(new Paragraph(" ARTICLE 2",boldFont));
            doc.add(new Paragraph("     Le stage de formation a pour but essentiel d'assurer l??? application pratique de l???enseignement donn?? par I??? ??tablissement, et d?????laborer un projet sur un th??me propos?? par l???entreprise.\n\n"));
            doc.add(new Paragraph(" ARTICLE 3",boldFont));
            doc.add(new Paragraph("     Le programme du stage est ??labor?? par le personnel charge de l???encadrement du stagiaire. En tenant compte du programme et de la sp??cialit?? des ??tudes de l?????l??ve, ainsi que  de la disponibilit?? des moyens humains et mat??riel de l???entreprise. Cette derni??re se r??serve le droit de r??orienter l???apprentissage en fonction des qualifications du stagiaire et du rythme de ses activit??s professionnelles.\n\n"));
            doc.add(new Paragraph(" ARTICLE 4",boldFont));
            doc.add(new Paragraph("     Pendant le stage, I ?????l??ve est soumis aux usages et r??glements de l???entreprise, notamment en mati??re de discipline, des horaires et des cong??s. En cas de manquement ?? ces r??gles, le chef d???entreprise se r??serve le droit de mettre fin au stage, apr??s avoir pr??venu le Directeur de l'EcoIe.\n"));
            
            LineSeparator ls = new LineSeparator();
            doc.add(new Chunk(ls));
            doc.add(new Paragraph("                              Ecole Nationale des Sciences Appliqu??es de T??touan. B.P 2222 M???hannech II ??? T??touan \n" +
                    "                                                           T??l. 053688027; Fax. 0539994624; www.ensate.uae.ma\n\n",footerFont));
            doc.add(img);
            LineSeparator ls4 = new LineSeparator();
            doc.add(new Chunk(ls4));
            doc.add(new Paragraph(" \nARTICLE 5",boldFont));
            doc.add(new Paragraph("     Au terme de son stage, le stagiaire est dans l???obligation de remettre une copie de son rapport de stage ?? l???entreprise.\n\n"));
            doc.add(new Paragraph(" ARTICLE 6",boldFont));
            doc.add(new Paragraph("     L?????l??ve ing??nieur s???engage ??  garder confidentielle toute information recueillie dans l???entreprise, et ?? n???utiliser en aucun cas ces informations pour faire l???objet d???une publication, communication ?? des tiers, conf??rences, ???, sans l???accord de l???entreprise.\n\n"));
            doc.add(new Paragraph(" ARTICLE 7",boldFont));
            doc.add(new Paragraph("     L?????l??ve stagiaire en p??riode de stage au sein de l???entreprise doit ??tre couvert, par ses propres moyens, pour les accidents de travail survenus par le fait ou ?? l???occasion de son s??jour.\n\n"));
            doc.add(new Paragraph(" ARTICLE 8",boldFont));
            doc.add(new Paragraph("     L???entreprise s???engage ?? fournir ?? l?????tablissement de formation, s???il le demande, son ??valuation sur le travail du stagiaire, m??me sur certains points particuliers qu???il jugera n??cessaires.\n\n"));
            doc.add(new Paragraph(" ARTICLE 9",boldFont));
            doc.add(new Paragraph("     En cas de non-respect de l???une des clauses de cette convention, aussi bien par l?????l??ve stagiaire que son ??tablissement d???origine, l???entreprise se r??serve le droit de mettre fin ?? ce stage.\n\n"));
            doc.add(new Paragraph(" ARTICLE 10",boldFont));
            doc.add(new Paragraph("     Dans le cadre de la pr??sente convention, l???entreprise "+NomEntreprise+"  accepte de recevoir l?????l??ve ing??nieur - stagiaire : "+Nom+ " " + Prenom+", inscrit ?? l'ENSA de T??touan pendant l???ann??e universitaire "+ Year.now().minusYears(1) + "-" +Year.now(clock) +" en "+Filiere+". Pour la p??riode allant du "+DebutStage+" au "+FinStage+".\n\n"));
            doc.add(new Paragraph(" Lu et approuv??                                                                   Fait ?? T??touan le "+formatter.format(date)+"\n\n"));
                    doc.add(new Paragraph(" Le Directeur de l???Entreprise                                                                  Le Directeur de l???Ecole\n                                                              Le stagiaire"));
                    Image img1 = Image.getInstance("src\\main\\resources\\images\\cachet.png");
                    img1.scaleAbsolute(190, 60);
                    img1.setAlignment(Image.ALIGN_RIGHT);
                    doc.add(img1);
                    Paragraph p= new Paragraph();
                    
                    LineSeparator ls1 = new LineSeparator();
                    doc.add(new Chunk(ls1));
                    doc.add(new Paragraph("                              Ecole Nationale des Sciences Appliqu??es de T??touan. B.P 2222 M???hannech II ??? T??touan \n" +
                            "                                                          T??l. 053688027; Fax. 0539994624; www.ensate.uae.ma",footerFont));
                    doc.add(rect);
                    doc.close();
                    SendEmail.envoyerEmailAvecDoc(EmailInstitutionnel, "la convention de stage", "convention_de_stage_"+Prenom+"_"+Nom+"_"+formatter1.format(date)+".pdf", Prenom+" "+Nom);
                    Desktop.getDesktop().open(new File ("src\\main\\resources\\Documents\\convention_de_stage_"+Prenom+"_"+Nom+"_"+formatter1.format(date)+".pdf"));

        } catch (FileNotFoundException ex) {
            Logger.getLogger(GenererPdf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(GenererPdf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GenererPdf.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void AttestationInscription(String Nom, String Prenom, String dateNaissance, String lieuNaissance, String CIN, String CNE, String Apoge, String anne_debut, String EmailInstitutionnel){
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");  
        SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date date = new java.util.Date();
        Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.BOLD);
        Font boldFontAttestation = new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.BOLD|Font.UNDERLINE);
        Font footerFont = new Font(Font.FontFamily.TIMES_ROMAN, 10);
        Document doc = new Document(PageSize.A4);
        try {
            PdfWriter.getInstance(doc, new FileOutputStream("src\\main\\resources\\Documents\\Attestation_d_inscription_"+Prenom+"_"+Nom+"_"+formatter.format(date)+".pdf"));
            doc.open();
            Image img = Image.getInstance("src\\main\\resources\\images\\logo2.png");
            img.scaleAbsolute(500 , 80);
            img.setAlignment(Image.ALIGN_CENTER);
            doc.add(img);
            LineSeparator ls1 = new LineSeparator();
            doc.add(new Chunk(ls1));
            doc.add(new Paragraph(" "));
            Paragraph p = new Paragraph("ATTTESTATION D'INSCRIPTION", boldFontAttestation);
            p.setAlignment(Element.ALIGN_CENTER);
            doc.add(p);
            doc.add(new Paragraph(" "));
            doc.add(new Paragraph(" "));
            Paragraph p2 = new Paragraph("                           Le directeur de l'Ecole Nationale des Sciences Appliqu??es de");
            doc.add(p2);
            doc.add(new Paragraph("                        T??touan, atteste que :"));
            doc.add(new Paragraph(" "));
            doc.add(new Paragraph(" "));
            Paragraph p4 = new Paragraph("                        Mr (Mlle)                   : "+Nom + " "+ Prenom);
            doc.add(p4);
            Paragraph p5 = new Paragraph("\n                        Date et lieu de naissance : "+dateNaissance+" ?? "+ lieuNaissance);
            doc.add(p5);
            Paragraph p6 = new Paragraph("\n                        CIN                       : "+CIN);
            doc.add(p6);
            Paragraph p7 = new Paragraph("\n                        CNE/Code Massar           : "+CNE);
            doc.add(p7);
            Paragraph p8 = new Paragraph("\n                        N?? Apog??e                 : "+Apoge);
            doc.add(p8);
            Paragraph p9= new Paragraph("\n\n                        Est insrit (e) ?? l'ENSA de T??touan, en...1??re...ann??e des ?? Deux\n" +
                            "                        Ann??es Pr??paratoires?? au cycle ing??nieur. pour l'ann??e universitaire \n                        "+ anne_debut + "/" + (Integer.valueOf(anne_debut)+1) +".\n\n");
            doc.add(p9);
            doc.add(new Paragraph(" "));
            Paragraph p10 = new Paragraph("                        La pr??sente attestation est d??livr??e ?? l'int??ress?? (e) pour servir et valoir\n                        ce que de droit.");
            doc.add(p10);
            Paragraph p11 = new Paragraph("\n                                                                                       Fait ?? T??touan le : "+formatter1.format(date)+"\n", boldFont);
            doc.add(p11);
            Image img1 = Image.getInstance("src\\main\\resources\\images\\cachet.png");
            img1.scaleAbsolute(250, 110);
            img1.setAlignment(Image.ALIGN_RIGHT);
            doc.add(img1);
            LineSeparator ls = new LineSeparator();
            doc.add(new Chunk(ls));
            Paragraph p3 = new Paragraph("Ecole Nationale des Sciences Appliqu??es de T??touan. B.P 2222 M???hannech II ??? T??touan \n" +
"T??l. 053688027; Fax. 0539994624; www.ensate.uae.ma", footerFont);
            p3.setAlignment(Element.ALIGN_CENTER);
            doc.add(p3);
        
        Rectangle rect= new Rectangle(577,825,18,15);
        rect.enableBorderSide(1);
        rect.enableBorderSide(2);
        rect.enableBorderSide(4);
        rect.enableBorderSide(8);
        rect.setBorderColor(BaseColor.BLACK);
        rect.setBorderWidth(1);
        doc.add(rect);
        doc.close();
        SendEmail.envoyerEmailAvecDoc(EmailInstitutionnel, "l'attestation d'inscription", "Attestation_d_inscription_"+Prenom+"_"+Nom+"_"+formatter.format(date)+".pdf", Prenom+" "+Nom);
        Desktop.getDesktop().open(new File ("src\\main\\resources\\Documents\\Attestation_d_inscription_"+Prenom+"_"+Nom+"_"+formatter.format(date)+".pdf"));

        } catch (DocumentException ex) {
            Logger.getLogger(GenererPdf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GenererPdf.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       
    }
        public static void AttestationReussite(String Prenom, String Nom, String LieuNaissance, String DateNaissance, String filiere, String annee_universitaire, String EmailInstitutionnel){
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
            java.util.Date date = new java.util.Date();
            SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
            Document document = new Document();
            PdfWriter instance = PdfWriter.getInstance(document,new FileOutputStream("src\\main\\resources\\Documents\\Attestation_de_reussite__"+Prenom+"_"+Nom+"_"+formatter.format(date)+".pdf"));
            document.open();
            LineSeparator line = new LineSeparator(1, 100, null, Element.ALIGN_CENTER, -2); 
            Image img = Image.getInstance("src\\main\\resources\\images\\at.png");
            img.scaleAbsolute(400, 70);
            img.setAlignment(Element.ALIGN_CENTER);
            Font f1 = new Font(Font.FontFamily.TIMES_ROMAN,18,Font.BOLD);
            Font f2 = new Font(Font.FontFamily.UNDEFINED,12);
            Font f3 = new Font(Font.FontFamily.TIMES_ROMAN,13,Font.BOLD);
            Font f4 = new Font(Font.FontFamily.TIMES_ROMAN,11,Font.BOLD,BaseColor.RED);
            Paragraph p1,p2,p3,p4,p5;
            p1 = (new Paragraph("\n\n\n ATTESTATION DE REUSSITE",f1));
            p1.setAlignment(Element.ALIGN_CENTER);
            p2 = (new Paragraph("\n\n\n           Le Directeur de l'Ecole Nationale des Sciences Appliqu??es atteste que l'??tudiant(e):\n\n           "+Prenom + " " + Nom + " N??(e) le : "+  DateNaissance + " ?? "+  LieuNaissance +" a ??t?? d??clar??(e) admis(e) au niveau : \n\n           "+ filiere + " au titre de l'ann??e universitaire :\n\n           "+annee_universitaire+"/" + (Integer.valueOf(annee_universitaire)+1) +".",f2));
            p3 = (new Paragraph("\n\n\nFait ?? T??touan le "+formatter1.format(date)+ "\n\nDirecteur\n\n\n",f3));
            p5 = (new Paragraph("\n\n"));
            Image img2 = Image.getInstance("src\\main\\resources\\images\\cachet.png");
            img2.scaleAbsolute(150, 100);
            img2.setAlignment(Element.ALIGN_CENTER);
            p4 = (new Paragraph("Avis important: Il ne peut ??tre d??livr?? qu'un seul exemplaire. Aucun duplicate ne sera fourni",f4));
            p3.setAlignment(Element.ALIGN_CENTER);
            p4.setAlignment(Element.ALIGN_CENTER);
            document.add(img);
            document.add(p1);
            document.add(p2);
            document.add(p3);
            document.add(p5);
            document.add(img2);
            document.add(p5);
            document.add(p5);
            document.add(new Chunk(line));
            document.add(p4);
            Rectangle rect= new Rectangle(577,825,18,15); 
            rect.enableBorderSide(1);
            rect.enableBorderSide(2);
            rect.enableBorderSide(4);
            rect.enableBorderSide(8);
            rect.setBorderColor(BaseColor.BLACK);
            rect.setBorderWidth(1);
            document.add(rect);
            document.close();
            SendEmail.envoyerEmailAvecDoc(EmailInstitutionnel, "l'attestation de reussite", "Attestation_de_reussite__"+Prenom+"_"+Nom+"_"+formatter.format(date)+".pdf", Prenom+" "+Nom); 
            Desktop.getDesktop().open(new File ("src\\main\\resources\\Documents\\Attestation_de_reussite__"+Prenom+"_"+Nom+"_"+formatter.format(date)+".pdf"));

        } catch (FileNotFoundException ex) {
            Logger.getLogger(GenererPdf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(GenererPdf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GenererPdf.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
        
         public static void AttestationScolarite(String Prenom, String Nom, String CIN, String CNE, String DateNaissance, String LieuNaissance, String filiere, String EmailInstitutionnel){
        try {
            SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
            java.util.Date date = new java.util.Date();
            Clock clock = Clock.systemDefaultZone();
            String diplome=null;
            if(filiere.equals("2AP1") || filiere.equals("2AP2")){
                diplome = "Ann??es Pr??paratoires au Cycle d'ing??nieur";
            }
            else{
                diplome = "Cycle d'ing??nieur";
            }
            
            Document document = new Document();
            PdfWriter instance = PdfWriter.getInstance(document,new FileOutputStream("src\\main\\resources\\Documents\\Attestation_de_scolarite__"+Prenom+"_"+Nom+"_"+formatter.format(date)+".pdf"));
            document.open();
            LineSeparator line = new LineSeparator(1, 100, null, Element.ALIGN_CENTER, -2); 
            Image img = Image.getInstance("src\\main\\resources\\images\\ENSA2.PNG");
            img.scaleAbsolute(400, 70);
            img.setAlignment(Element.ALIGN_CENTER);
            Font f1 = new Font(Font.FontFamily.TIMES_ROMAN,18,Font.BOLD|Font.UNDERLINE);
            Font f2 = new Font(Font.FontFamily.UNDEFINED,11);
            Font f3 = new Font(Font.FontFamily.TIMES_ROMAN,12,Font.BOLD);
            Font f4 = new Font(Font.FontFamily.TIMES_ROMAN,11,Font.BOLD,BaseColor.RED);
            Font f5 = new Font(Font.FontFamily.TIMES_ROMAN,11);
            Paragraph p1,p2,p3,p4,p5,p6,p7;
              p4 = (new Paragraph("\n"));
              p1 = (new Paragraph("ATTESTATION DE SCOLARITE",f1));
              p1.setAlignment(Element.ALIGN_CENTER);
              p2 = (new Paragraph("\n\n           Le Directeur de l'Ecole Nationale des Sciences Appliqu??es atteste que l'??tudiant(e):\n\n           Monsieur " + Nom + " " + Prenom + "\n\n           Num??ro de la carte d'identit?? nationale : " + CIN + "\n\n           Code national de l'??tudiant :               " + CNE + "\n\n           N??(e) le : " + DateNaissance + " ?? " + LieuNaissance +"\n\n           Poursuit ses ??tudes ?? l'Ecole Nationale des Sciences Appliqu??es pour l'ann??e universitaire :                          " + Year.now().minusYears(1) + "/" +Year.now(clock) + ".\n\n\n" +"           Dipl??me : " + diplome + "\n\n           Filli??re :  " + filiere ,f2));
              p3 = (new Paragraph("                                 Fait ?? T??touan le : "+formatter1.format(date)+"\n\n\nLe directeur",f3));
              p3.setAlignment(Element.ALIGN_CENTER);
              p6 = (new Paragraph("  Adresse : M'HANNECH || B.P.2222 T??touan\n\n                  T??l : 0539968802,FAX:0539994624",f5));
              p5 = (new Paragraph("\n\n"));
              Image img2 = Image.getInstance("src\\main\\resources\\images\\cachet.png");
              img2.setAlignment(Element.ALIGN_CENTER);
              p7 = (new Paragraph("Le pr??sent document n'est d??livr?? qu'en un seul exemplaire.\nIl appartient ?? l'??tudiant d'en faire des photocopies certifi??es conformes.",f4));
              p7.setAlignment(Element.ALIGN_CENTER);
              img2.scaleAbsolute(250, 110);
              img2.setAlignment(Element.ALIGN_CENTER);
              document.add(img); 
              document.add(p1);
              document.add(p2);
              document.add(p5);
              document.add(p3);
              document.add(img2);
              document.add(new Chunk(line));
              document.add(p6);
              document.add(new Chunk(line));
              document.add(p7);
            Rectangle rect= new Rectangle(577,825,18,15); 
            rect.enableBorderSide(1);
            rect.enableBorderSide(2);
            rect.enableBorderSide(4);
            rect.enableBorderSide(8);
            rect.setBorderColor(BaseColor.BLACK);
            rect.setBorderWidth(1);
            document.add(rect);
        
            document.close();
            SendEmail.envoyerEmailAvecDoc(EmailInstitutionnel, "l'attestation de scolarite", "Attestation_de_scolarite__"+Prenom+"_"+Nom+"_"+formatter.format(date)+".pdf", Prenom+" "+Nom); 
            Desktop.getDesktop().open(new File ("src\\main\\resources\\Documents\\Attestation_de_scolarite__"+Prenom+"_"+Nom+"_"+formatter.format(date)+".pdf"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GenererPdf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(GenererPdf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GenererPdf.class.getName()).log(Level.SEVERE, null, ex);
        } 
         }  
        
    public static void ReleveNote(String Prenom, String Nom, String EmailInstitutionnel, String CNE, String Apoge, String AnneeNotes){
        try {
            ConnexionBD.connecterbd();
            Connection conn = ConnexionBD.connecterbd();
            PreparedStatement ps = null;
            ResultSet rs= null;
            String login="SELECT * FROM releve_2019 WHERE Apoge = ' " + Apoge + "'";
            ps=conn.prepareStatement(login);
            rs=ps.executeQuery();
            Document document = new Document();
            SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
            java.util.Date date = new java.util.Date();
            PdfWriter instance = PdfWriter.getInstance(document,new FileOutputStream("src\\main\\resources\\Documents\\Releve_de_note__"+Prenom+"_"+Nom+"_"+formatter.format(date)+".pdf"));
            document.open();
            Image img = Image.getInstance("src\\main\\resources\\images\\ENSA2.PNG");
            img.scaleAbsolute(400, 70);
            img.setAlignment(Element.ALIGN_CENTER);
            document.add(img);
            LineSeparator ls = new LineSeparator();
            Font f1 = new Font(Font.FontFamily.TIMES_ROMAN,12,Font.UNDERLINE|Font.BOLD,BaseColor.RED);
            Font f2 = new Font(Font.FontFamily.TIMES_ROMAN,12,Font.UNDERLINE);
            Font f3 = new Font(Font.FontFamily.TIMES_ROMAN,12);
            Font f4 = new Font(Font.FontFamily.UNDEFINED,10,Font.BOLD);
            Font f5 = new Font(Font.FontFamily.UNDEFINED,11);
            Font f6 = new Font(Font.FontFamily.UNDEFINED,10,Font.BOLD,BaseColor.BLUE);
            Font f7 = new Font(Font.FontFamily.TIMES_ROMAN,12,Font.BOLD,BaseColor.RED);
            Font f8 = new Font(Font.FontFamily.UNDEFINED,14,Font.BOLD);
            Font f9 = new Font(Font.FontFamily.UNDEFINED,10);
            Paragraph p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11,p12;
            p1=null;
            p2=null;
            p3=null;
            p4=null;
            p5=null;
            p6=null;
            p7=null;
            p8=null;
            p9=null;
            p10=null;
            p11=null;
            p12=null;
            
            PdfPTable pdfPTable1 = new PdfPTable(3);
             
            PdfPTable pdfPTable2 = new PdfPTable(2);
            PdfPCell pdfPCell1=null;
            PdfPCell pdfPCell2=null;
            PdfPCell pdfPCell3=null;
            PdfPCell pdfPCell4=null;
            PdfPCell pdfPCell5=null;
            PdfPCell pdfPCell6=null;
            PdfPCell pdfPCell7=null;
            PdfPCell pdfPCell8=null;
            PdfPCell pdfPCell9=null;
            PdfPCell pdfPCell10 = null;
            PdfPCell pdfPCell11=null;
            PdfPCell pdfPCell12=null;
            
            PdfPCell pdfPCell13 = null;
            PdfPCell pdfPCell14 = null;
            PdfPCell pdfPCell15 = null;
            
            PdfPCell pdfPCell16 = null;
            PdfPCell pdfPCell17 = null;
            PdfPCell pdfPCell18 = null;
            
            PdfPCell pdfPCell19 = null;
            PdfPCell pdfPCell20 = null;
            PdfPCell pdfPCell21 = null;
            
            PdfPCell pdfPCell22 = null;
            PdfPCell pdfPCell23 = null;
            PdfPCell pdfPCell24 = null;
            //LIGNE2
            PdfPCell pdfPCell25 = null;
            PdfPCell pdfPCell26 = null;
            PdfPCell pdfPCell27 = null;
            
            PdfPCell pdfPCell28 = null;
            PdfPCell pdfPCell29 = null;
            PdfPCell pdfPCell30 = null;
            
            PdfPCell pdfPCell31 = null;
            PdfPCell pdfPCell32 = null;
            PdfPCell pdfPCell33 =null;
            
            PdfPCell pdfPCell34 = null;
            PdfPCell pdfPCell35 = null;
            PdfPCell pdfPCell36 = null;
            
            PdfPCell pdfPCell37 = null;
            PdfPCell pdfPCell38 = null;
            PdfPCell pdfPCell39 = null;
            
            PdfPCell pdfPCellN01 = null;
            PdfPCell pdfPCellN02 = null;
            PdfPCell pdfPCellN12 = null;

            PdfPCell pdfPCellN11 = null;
            
            PdfPCell pdfPCellN21 = null;
            PdfPCell pdfPCellN22 = null;
            
              while(rs.next()){
            if(rs.getString("Filiere").equalsIgnoreCase("2AP1")){
                p1 = (new Paragraph(" Relev?? des notes de la 1??re ann??e",f2));
            }
            if(rs.getString("Filiere").equalsIgnoreCase("2AP2")){
                p1 = (new Paragraph(" Relev?? des notes de la 2??me ann??e",f2));
            }
            if(rs.getString("Filiere").equalsIgnoreCase("GI1") || rs.getString("Filiere").equalsIgnoreCase("GC1")|| rs.getString("Filiere").equalsIgnoreCase("GM1")|| rs.getString("Filiere").equalsIgnoreCase("GSTR1")|| rs.getString("Filiere").equalsIgnoreCase("SCM1")){
                p1 = (new Paragraph(" Relev?? des notes de la 1??re ann??e cycle d'ing??nieur",f2));
            }
            if(rs.getString("Filiere").equalsIgnoreCase("GI2") || rs.getString("Filiere").equalsIgnoreCase("GC2")|| rs.getString("Filiere").equalsIgnoreCase("GM2")|| rs.getString("Filiere").equalsIgnoreCase("GSTR2")|| rs.getString("Filiere").equalsIgnoreCase("SCM2")){
                p1 = (new Paragraph(" Relev?? des notes de la 2??me ann??e cycle d'ing??nieur",f2));
            }
            if(rs.getString("Filiere").equalsIgnoreCase("GI3") || rs.getString("Filiere").equalsIgnoreCase("GC3")|| rs.getString("Filiere").equalsIgnoreCase("GM3")|| rs.getString("Filiere").equalsIgnoreCase("GSTR3")|| rs.getString("Filiere").equalsIgnoreCase("SCM3")){
                p1 = (new Paragraph(" Relev?? des notes de la 3??me ann??e cycle d'ing??nieur",f2));
            }
            p2 = (new Paragraph(" Fili??re : " + rs.getString("Filiere"),f1));
            p3= (new Paragraph(" Ann??e universitaire : " + AnneeNotes + "/" + (Integer.valueOf(AnneeNotes)+1) ,f3));
            p4= (new Paragraph("\n L?????l??ve Ing??nieur :",f5));
            p5= (new Paragraph(" Nom et Pr??nom : " + Nom + " " + Prenom,f5));
            p6= (new Paragraph(" CNE           : " + CNE,f5));
            p7= (new Paragraph(" Code Apog??e     : " + Apoge,f5));
            p8= (new Paragraph(" A obtenu les r??sultats suivants :",f5));
            p9= (new Paragraph("\n"));
            p10= (new Paragraph(" La pr??sente attestation est d??livr??e ?? l???int??ress??(e) pour servir et valoir ce que de droit.",f5));
            p11= (new Paragraph(" Fait ?? T??touan, le : "+formatter1.format(date),f5));
            p12= (new Paragraph(" AC : Acquis par Compensation                                                           NV : Non Valid??\n N.B. Le pr??sent document n???est d??livr?? qu???en un seul exemplaire. Il appartient ?? l?????tudiant d???en faire des photocopies certifi??es conformes.",f5));
            
            p1.setAlignment(Element.ALIGN_CENTER);
            p2.setAlignment(Element.ALIGN_CENTER);
            p3.setAlignment(Element.ALIGN_CENTER);
           //specify column widths
           //Create Table object, Here 4 specify the no. of columns
            
            //Create cells
            pdfPCell1 = new PdfPCell(new Paragraph("       Intitul?? du Module\n",f6));
            pdfPCell2 = new PdfPCell(new Paragraph("                Note/20",f6));
            pdfPCell3 = new PdfPCell(new Paragraph("                R??sultat",f6));
            //LIGNE2
            pdfPCell4 = new PdfPCell(new Paragraph(rs.getString("NomModule1")+" \n",f4));
            pdfPCell5 = new PdfPCell(new Paragraph("               " + rs.getString("NoteModule1"),f9));
            pdfPCell6 = new PdfPCell(new Paragraph("               " + rs.getString("ResultatModule1"),f9));	
            
            pdfPCell7 = new PdfPCell(new Paragraph(rs.getString("NomModule2") +" \n",f4));
            pdfPCell8 = new PdfPCell(new Paragraph("               " + rs.getString("NoteModule2"),f9));
            pdfPCell9 = new PdfPCell(new Paragraph("               " + rs.getString("ResultatModule2"),f9));
            
            pdfPCell10 = new PdfPCell(new Paragraph(rs.getString("NomModule3")+" \n",f4));
            pdfPCell11 = new PdfPCell(new Paragraph("               " + rs.getString("NoteModule3"),f9));
            pdfPCell12 = new PdfPCell(new Paragraph("               " + rs.getString("ResultatModule3"),f9));
            
            pdfPCell13 = new PdfPCell(new Paragraph(rs.getString("NomModule4")+" \n",f4));
            pdfPCell14 = new PdfPCell(new Paragraph("               " + rs.getString("NoteModule4"),f9));
            pdfPCell15 = new PdfPCell(new Paragraph("               " + rs.getString("ResultatModule4"),f9));
            
            pdfPCell16 = new PdfPCell(new Paragraph(rs.getString("NomModule5")+" \n",f4));
            pdfPCell17 = new PdfPCell(new Paragraph("               " + rs.getString("NoteModule5"),f9));
            pdfPCell18 = new PdfPCell(new Paragraph("               " + rs.getString("ResultatModule5"),f9));
            
            pdfPCell19 = new PdfPCell(new Paragraph(rs.getString("NomModule6")+" \n",f4));
            pdfPCell20 = new PdfPCell(new Paragraph("               " + rs.getString("NoteModule6"),f9));
            pdfPCell21 = new PdfPCell(new Paragraph("               " + rs.getString("ResultatModule6"),f9));
            
            pdfPCell22 = new PdfPCell(new Paragraph(rs.getString("NomModule7")+" \n",f4));
            pdfPCell23 = new PdfPCell(new Paragraph("               " + rs.getString("NoteModule7"),f9));
            pdfPCell24 = new PdfPCell(new Paragraph("               " + rs.getString("ResultatModule7"),f9));
            //LIGNE2
            pdfPCell25 = new PdfPCell(new Paragraph(rs.getString("NomModule8")+" \n",f4));
            pdfPCell26 = new PdfPCell(new Paragraph("               " + rs.getString("NoteModule8"),f9));
            pdfPCell27 = new PdfPCell(new Paragraph("               " + rs.getString("ResultatModule8"),f9));
            
            pdfPCell28 = new PdfPCell(new Paragraph(rs.getString("NomModule9")+" \n",f4));
            pdfPCell29 = new PdfPCell(new Paragraph("               " + rs.getString("NoteModule9"),f9));
            pdfPCell30 = new PdfPCell(new Paragraph("               " + rs.getString("ResultatModule9"),f9));
            
            pdfPCell31 = new PdfPCell(new Paragraph(rs.getString("NomModule10")+" \n",f4));
            pdfPCell32 = new PdfPCell(new Paragraph("               " + rs.getString("NoteModule10"),f9));
            pdfPCell33 = new PdfPCell(new Paragraph("               " + rs.getString("ResultatModule10"),f9));
            
            pdfPCell34 = new PdfPCell(new Paragraph(rs.getString("NomModule11")+" \n",f4));
            pdfPCell35 = new PdfPCell(new Paragraph("               " + rs.getString("NoteModule11"),f9));
            pdfPCell36 = new PdfPCell(new Paragraph("               " + rs.getString("ResultatModule11"),f9));
            
            pdfPCell37 = new PdfPCell(new Paragraph(rs.getString("NomModule12")+" \n",f4));
            pdfPCell38 = new PdfPCell(new Paragraph("               " + rs.getString("NoteModule12"),f9));
            pdfPCell39 = new PdfPCell(new Paragraph("               " + rs.getString("ResultatModule12"),f9));
            
            pdfPCellN01 = new PdfPCell(new Paragraph("                 Points du jury\n\n",f7));
            if(rs.getString("PointsJury")==null){
                pdfPCellN02 = new PdfPCell(new Paragraph("  "));
            }
            else{
                pdfPCellN02 = new PdfPCell(new Paragraph("  " + rs.getString("PointsJury")));
            }
            pdfPCellN12 = new PdfPCell(new Paragraph("                 " + rs.getString("MoyAnnee"),f8));

            pdfPCellN11 = new PdfPCell(new Paragraph("                 Moyenne du l???ann??e\n\n",f7));
            
            pdfPCellN21 = new PdfPCell(new Paragraph("                 R??sultat de l???ann??e\n\n",f7));
            pdfPCellN22 = new PdfPCell(new Paragraph("                 " + rs.getString("ResultatAnnee"),f8));
           
            }
            //Add cells to table
            document.add(p9);
            document.add(new Chunk(ls));
            document.add(p1);
            document.add(p2);
            document.add(p3);
            document.add(p4);
            document.add(p5);
            document.add(p6);
            document.add(p7);
            document.add(p8);
            pdfPTable1.addCell(pdfPCell1);
            pdfPTable1.addCell(pdfPCell2);
            pdfPTable1.addCell(pdfPCell3);
            pdfPTable1.addCell(pdfPCell4);
            pdfPTable1.addCell(pdfPCell5);
            pdfPTable1.addCell(pdfPCell6);
            pdfPTable1.addCell(pdfPCell7);
            pdfPTable1.addCell(pdfPCell8);
            pdfPTable1.addCell(pdfPCell9);
            pdfPTable1.addCell(pdfPCell10);
            pdfPTable1.addCell(pdfPCell11);
            pdfPTable1.addCell(pdfPCell12);
            pdfPTable1.addCell(pdfPCell13);
            pdfPTable1.addCell(pdfPCell14);
            pdfPTable1.addCell(pdfPCell15);
            pdfPTable1.addCell(pdfPCell16);
            pdfPTable1.addCell(pdfPCell17);
            pdfPTable1.addCell(pdfPCell18);
            pdfPTable1.addCell(pdfPCell19);
            pdfPTable1.addCell(pdfPCell20);
            pdfPTable1.addCell(pdfPCell21);
            pdfPTable1.addCell(pdfPCell22);
            pdfPTable1.addCell(pdfPCell23);
            pdfPTable1.addCell(pdfPCell24);
            pdfPTable1.addCell(pdfPCell25);
            pdfPTable1.addCell(pdfPCell26);
            pdfPTable1.addCell(pdfPCell27);
            pdfPTable1.addCell(pdfPCell28);
            pdfPTable1.addCell(pdfPCell29);
            pdfPTable1.addCell(pdfPCell30);
            pdfPTable1.addCell(pdfPCell31);
            pdfPTable1.addCell(pdfPCell32);
            pdfPTable1.addCell(pdfPCell33);
            pdfPTable1.addCell(pdfPCell34);
            pdfPTable1.addCell(pdfPCell35);
            pdfPTable1.addCell(pdfPCell36);
            pdfPTable1.addCell(pdfPCell37);
            pdfPTable1.addCell(pdfPCell38);
            pdfPTable1.addCell(pdfPCell39);
            document.add(p9);
            document.add(p9);
            pdfPTable2.addCell(pdfPCellN01);
            pdfPTable2.addCell(pdfPCellN02);
            pdfPTable2.addCell(pdfPCellN11);
            pdfPTable2.addCell(pdfPCellN12);
            pdfPTable2.addCell(pdfPCellN21);
            pdfPTable2.addCell(pdfPCellN22);
            document.add(pdfPTable1);
            document.add(p9);
            document.add(p9);
            document.add(pdfPTable2);
            document.add(p9);
            document.add(p9);
            document.add(p10);
            document.add(p9);
            document.add(p11);
            document.add(p9);
            document.add(p9);
            document.add(p12);
            
            Rectangle rect= new Rectangle(577,825,18,15); 
            rect.enableBorderSide(1);
            rect.enableBorderSide(2);
            rect.enableBorderSide(4);
            rect.enableBorderSide(8);
            rect.setBorderColor(BaseColor.BLACK);
            rect.setBorderWidth(1);
            document.add(rect);
            document.close();
            SendEmail.envoyerEmailAvecDoc(EmailInstitutionnel, "le relev?? de notes", "Releve_de_note__"+Prenom+"_"+Nom+"_"+formatter.format(date)+".pdf", Prenom+" "+Nom);
            Desktop.getDesktop().open(new File ("src\\main\\resources\\Documents\\Releve_de_note__"+Prenom+"_"+Nom+"_"+formatter.format(date)+".pdf"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GenererPdf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(GenererPdf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GenererPdf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(GenererPdf.class.getName()).log(Level.SEVERE, null, ex);
        }
}
}