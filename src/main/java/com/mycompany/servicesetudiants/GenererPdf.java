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
        SendEmail.envoyerEmailAvecDoc(EmailInstitutionnel, "la demande de stage", "demande_de_stage_"+Prenom+"_"+Nom+"_"+formatter.format(date)+".pdf", Prenom+" "+Nom);
        //Desktop.getDesktop().open(new File ("src\\main\\resources\\Documents\\Demande_De_Stage.pdf"));
           
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
            Image img = Image.getInstance("src\\main\\resources\\images\\ENSA-Tétouan.png");
            img.scaleAbsolute(100, 80);
            img.setAlignment(Image.ALIGN_LEFT);
            doc.add(img);
            LineSeparator ls3 = new LineSeparator();
            doc.add(new Chunk(ls3));
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("                                                                         CONVENTION CADRE", boldFont));
            doc.add(new Paragraph("                                             Entreprise / Ecole Nationale  des Sciences Appliquées de Tétouan\n\n\n", boldFont));
            doc.add(new Paragraph("  Entre :                  "+ NomEntreprise+"\n" , boldFont));
            doc.add(new Paragraph("  Représentée par son Directeur :\n"));
            doc.add(new Paragraph("                                                                                                                                                                  D'une part\n\n",boldFont));
            doc.add(new Paragraph(" Et : l’Ecole Nationale  des Sciences Appliquées de Tétouan.",boldFont));
            doc.add(new Paragraph(" Représentée par : "+"Mr. Mostafa STITOU Directeur de l’école\n",boldFont));
            doc.add(new Paragraph("                                                                                                                                                                  D'autre part\n\n",boldFont));
            doc.add(new Paragraph(" Il a été convenu ce qui suit : \n\n"));
            doc.add(new Paragraph(" ARTICLE 1",boldFont));
            doc.add(new Paragraph("     La présente convention régit les rapports des deux parties, dans le cadre de l’organisation de stages d‘ entreprises qui s’inscrivent dans le programme de formation de l’établissement de formation cité ci-dessus.\n\n"));
            doc.add(new Paragraph(" ARTICLE 2",boldFont));
            doc.add(new Paragraph("     Le stage de formation a pour but essentiel d'assurer l‘ application pratique de l’enseignement donné par I’ établissement, et d’élaborer un projet sur un thème proposé par l’entreprise.\n\n"));
            doc.add(new Paragraph(" ARTICLE 3",boldFont));
            doc.add(new Paragraph("     Le programme du stage est élaboré par le personnel charge de l’encadrement du stagiaire. En tenant compte du programme et de la spécialité des études de l’élève, ainsi que  de la disponibilité des moyens humains et matériel de l’entreprise. Cette dernière se réserve le droit de réorienter l’apprentissage en fonction des qualifications du stagiaire et du rythme de ses activités professionnelles.\n\n"));
            doc.add(new Paragraph(" ARTICLE 4",boldFont));
            doc.add(new Paragraph("     Pendant le stage, I ’élève est soumis aux usages et règlements de l’entreprise, notamment en matière de discipline, des horaires et des congés. En cas de manquement à ces règles, le chef d’entreprise se réserve le droit de mettre fin au stage, après avoir prévenu le Directeur de l'EcoIe.\n"));
            
            LineSeparator ls = new LineSeparator();
            doc.add(new Chunk(ls));
            doc.add(new Paragraph("                              Ecole Nationale des Sciences Appliquées de Tétouan. B.P 2222 M’hannech II — Tétouan \n" +
                    "                                                           Tél. 053688027; Fax. 0539994624; www.ensate.uae.ma\n\n",footerFont));
            doc.add(img);
            LineSeparator ls4 = new LineSeparator();
            doc.add(new Chunk(ls4));
            doc.add(new Paragraph(" \nARTICLE 5",boldFont));
            doc.add(new Paragraph("     Au terme de son stage, le stagiaire est dans l’obligation de remettre une copie de son rapport de stage à l’entreprise.\n\n"));
            doc.add(new Paragraph(" ARTICLE 6",boldFont));
            doc.add(new Paragraph("     L’élève ingénieur s’engage à  garder confidentielle toute information recueillie dans l’entreprise, et à n’utiliser en aucun cas ces informations pour faire l’objet d’une publication, communication à des tiers, conférences, …, sans l’accord de l’entreprise.\n\n"));
            doc.add(new Paragraph(" ARTICLE 7",boldFont));
            doc.add(new Paragraph("     L’élève stagiaire en période de stage au sein de l’entreprise doit être couvert, par ses propres moyens, pour les accidents de travail survenus par le fait ou à l’occasion de son séjour.\n\n"));
            doc.add(new Paragraph(" ARTICLE 8",boldFont));
            doc.add(new Paragraph("     L’entreprise s’engage à fournir à l’établissement de formation, s’il le demande, son évaluation sur le travail du stagiaire, même sur certains points particuliers qu’il jugera nécessaires.\n\n"));
            doc.add(new Paragraph(" ARTICLE 9",boldFont));
            doc.add(new Paragraph("     En cas de non-respect de l’une des clauses de cette convention, aussi bien par l’élève stagiaire que son établissement d’origine, l’entreprise se réserve le droit de mettre fin à ce stage.\n\n"));
            doc.add(new Paragraph(" ARTICLE 10",boldFont));
            doc.add(new Paragraph("     Dans le cadre de la présente convention, l’entreprise "+NomEntreprise+"  accepte de recevoir l’élève ingénieur - stagiaire : "+Nom+ " " + Prenom+", inscrit à l'ENSA de Tétouan pendant l’année universitaire "+ Year.now().minusYears(1) + "-" +Year.now(clock) +" en "+Filiere+". Pour la période allant du "+DebutStage+" au "+FinStage+".\n\n"));
            doc.add(new Paragraph(" Lu et approuvé                                                                   Fait à Tétouan le "+formatter.format(date)+"\n\n"));
                    doc.add(new Paragraph(" Le Directeur de l’Entreprise                                                                  Le Directeur de l’Ecole\n                                                              Le stagiaire"));
                    Image img1 = Image.getInstance("src\\main\\resources\\images\\cachet.png");
                    img1.scaleAbsolute(190, 60);
                    img1.setAlignment(Image.ALIGN_RIGHT);
                    doc.add(img1);
                    Paragraph p= new Paragraph();
                    
                    LineSeparator ls1 = new LineSeparator();
                    doc.add(new Chunk(ls1));
                    doc.add(new Paragraph("                              Ecole Nationale des Sciences Appliquées de Tétouan. B.P 2222 M’hannech II — Tétouan \n" +
                            "                                                          Tél. 053688027; Fax. 0539994624; www.ensate.uae.ma",footerFont));
                    doc.add(rect);
                    doc.close();
                    SendEmail.envoyerEmailAvecDoc(EmailInstitutionnel, "la convention de stage", "convention_de_stage_"+Prenom+"_"+Nom+"_"+formatter1.format(date)+".pdf", Prenom+" "+Nom);
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
            Paragraph p2 = new Paragraph("                           Le directeur de l'Ecole Nationale des Sciences Appliquées de");
            doc.add(p2);
            doc.add(new Paragraph("                        Tétouan, atteste que :"));
            doc.add(new Paragraph(" "));
            doc.add(new Paragraph(" "));
            Paragraph p4 = new Paragraph("                        Mr (Mlle)                   : "+Nom + " "+ Prenom);
            doc.add(p4);
            Paragraph p5 = new Paragraph("\n                        Date et lieu de naissance : "+dateNaissance+" à "+ lieuNaissance);
            doc.add(p5);
            Paragraph p6 = new Paragraph("\n                        CIN                       : "+CIN);
            doc.add(p6);
            Paragraph p7 = new Paragraph("\n                        CNE/Code Massar           : "+CNE);
            doc.add(p7);
            Paragraph p8 = new Paragraph("\n                        N° Apogée                 : "+Apoge);
            doc.add(p8);
            Paragraph p9= new Paragraph("\n\n                        Est insrit (e) à l'ENSA de Tétouan, en...1ère...année des « Deux\n" +
                            "                        Années Préparatoires» au cycle ingénieur. pour l'année universitaire \n                        "+ anne_debut + "/" + (Integer.valueOf(anne_debut)+1) +".\n\n");
            doc.add(p9);
            doc.add(new Paragraph(" "));
            Paragraph p10 = new Paragraph("                        La présente attestation est délivrée à l'intéressé (e) pour servir et valoir\n                        ce que de droit.");
            doc.add(p10);
            Paragraph p11 = new Paragraph("\n                                                                                       Fait à Tétouan le : "+formatter1.format(date)+"\n", boldFont);
            doc.add(p11);
            Image img1 = Image.getInstance("src\\main\\resources\\images\\cachet.png");
            img1.scaleAbsolute(250, 110);
            img1.setAlignment(Image.ALIGN_RIGHT);
            doc.add(img1);
            LineSeparator ls = new LineSeparator();
            doc.add(new Chunk(ls));
            Paragraph p3 = new Paragraph("Ecole Nationale des Sciences Appliquées de Tétouan. B.P 2222 M’hannech II — Tétouan \n" +
"Tél. 053688027; Fax. 0539994624; www.ensate.uae.ma", footerFont);
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
            p2 = (new Paragraph("\n\n\n           Le Directeur de l'Ecole Nationale des Sciences Appliquées atteste que l'étudiant(e):\n\n           "+Prenom + " " + Nom + " Né(e) le : "+  DateNaissance + " à "+  LieuNaissance +" a été déclaré(e) admis(e) au niveau : \n\n           "+ filiere + " au titre de l'année universitaire :\n\n           "+annee_universitaire+"/" + (Integer.valueOf(annee_universitaire)+1) +".",f2));
            p3 = (new Paragraph("\n\n\nFait à Tétouan le "+formatter1.format(date)+ "\n\nDirecteur\n\n\n",f3));
            p5 = (new Paragraph("\n\n"));
            Image img2 = Image.getInstance("src\\main\\resources\\images\\cachet.png");
            img2.scaleAbsolute(150, 100);
            img2.setAlignment(Element.ALIGN_CENTER);
            p4 = (new Paragraph("Avis important: Il ne peut être délivré qu'un seul exemplaire. Aucun duplicate ne sera fourni",f4));
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
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GenererPdf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(GenererPdf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GenererPdf.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//    public static void ReleveNote(String Prenom, String Nom){
//        try {
//            // TODO code application logic here
//            
//            Document document = new Document();
//            PdfWriter instance = PdfWriter.getInstance(document,new FileOutputStream("src\\relve_note.pdf"));
//            document.open();
//            Image img = Image.getInstance("src\\images\\ENSA2.PNG");
//            img.scaleAbsolute(400, 70);
//            img.setAlignment(Element.ALIGN_CENTER);
//            document.add(img);
//            LineSeparator ls = new LineSeparator();
//            Font f1 = new Font(Font.FontFamily.TIMES_ROMAN,12,Font.UNDERLINE|Font.BOLD,BaseColor.RED);
//            Font f2 = new Font(Font.FontFamily.TIMES_ROMAN,12,Font.UNDERLINE);
//            Font f3 = new Font(Font.FontFamily.TIMES_ROMAN,12);
//            Font f4 = new Font(Font.FontFamily.UNDEFINED,10,Font.BOLD);
//            Font f5 = new Font(Font.FontFamily.UNDEFINED,11);
//            Font f6 = new Font(Font.FontFamily.UNDEFINED,10,Font.BOLD,BaseColor.BLUE);
//            Font f7 = new Font(Font.FontFamily.TIMES_ROMAN,12,Font.BOLD,BaseColor.RED);
//            Font f8 = new Font(Font.FontFamily.UNDEFINED,14,Font.BOLD);
//            Font f9 = new Font(Font.FontFamily.UNDEFINED,10);
//            Paragraph p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11,p12;
//            p1 = (new Paragraph(" Relevé des notes de la 2ème année",f2));
//            p2 = (new Paragraph(" Filière : Deux Années Préparatoires (2AP)",f1));
//            p3= (new Paragraph(" Année universitaire : 2019-2020",f3));
//            p4= (new Paragraph("\n L’élève Ingénieur :",f5));
//            p5= (new Paragraph(" Nom et Prénom : HAMZA Aymane",f5));
//            p6= (new Paragraph(" CNE           : P120045696",f5));
//            p7= (new Paragraph(" Code Apogée     : 18044084",f5));
//            p8= (new Paragraph(" A obtenu les résultats suivants pour la deuxième année de la filière 2AP :",f5));
//            p9= (new Paragraph("\n"));
//            p10= (new Paragraph(" La présente attestation est délivrée à l’intéressé(e) pour servir et valoir ce que de droit.",f5));
//            p11= (new Paragraph(" Fait à Tétouan, le : 30/09/2020",f5));
//            p12= (new Paragraph(" AC : Acquis par Compensation                                                           NV : Non Validé\n N.B. Le présent document n’est délivré qu’en un seul exemplaire. Il appartient à l’étudiant d’en faire des photocopies certifiées conformes.",f5));
//            
//            p1.setAlignment(Element.ALIGN_CENTER);
//            p2.setAlignment(Element.ALIGN_CENTER);
//            p3.setAlignment(Element.ALIGN_CENTER);
//           //specify column widths
//           //Create Table object, Here 4 specify the no. of columns
//          
//            PdfPTable pdfPTable1 = new PdfPTable(3);
//             
//            PdfPTable pdfPTable2 = new PdfPTable(2);
//            
//            //Create cells
//            PdfPCell pdfPCell1 = new PdfPCell(new Paragraph("       Intitulé du Module\n",f6));
//            PdfPCell pdfPCell2 = new PdfPCell(new Paragraph("                Note/20",f6));
//            PdfPCell pdfPCell3 = new PdfPCell(new Paragraph("                Résultat",f6));
//            //LIGNE2
//            PdfPCell pdfPCell4 = new PdfPCell(new Paragraph(" Algèbre3\n",f4));
//            PdfPCell pdfPCell5 = new PdfPCell(new Paragraph("               11,5",f9));
//            PdfPCell pdfPCell6 = new PdfPCell(new Paragraph("               Validé",f9));
//            
//            PdfPCell pdfPCell7 = new PdfPCell(new Paragraph(" Analyse3\n",f4));
//            PdfPCell pdfPCell8 = new PdfPCell(new Paragraph("               10",f9));
//            PdfPCell pdfPCell9 = new PdfPCell(new Paragraph("               Validé",f9));
//            
//            PdfPCell pdfPCell10 = new PdfPCell(new Paragraph(" Physique3\n",f4));
//            PdfPCell pdfPCell11 = new PdfPCell(new Paragraph("               16",f9));
//            PdfPCell pdfPCell12 = new PdfPCell(new Paragraph("               Validé",f9));
//            
//            PdfPCell pdfPCell13 = new PdfPCell(new Paragraph(" Mécanique2\n",f4));
//            PdfPCell pdfPCell14 = new PdfPCell(new Paragraph("               10",f9));
//            PdfPCell pdfPCell15 = new PdfPCell(new Paragraph("               Validé",f9));
//            
//            PdfPCell pdfPCell16 = new PdfPCell(new Paragraph(" Info2\n",f4));
//            PdfPCell pdfPCell17 = new PdfPCell(new Paragraph("               10",f9));
//            PdfPCell pdfPCell18 = new PdfPCell(new Paragraph("               Validé",f9));
//            
//            PdfPCell pdfPCell19 = new PdfPCell(new Paragraph(" LC3\n",f4));
//            PdfPCell pdfPCell20 = new PdfPCell(new Paragraph("               15,25",f9));
//            PdfPCell pdfPCell21 = new PdfPCell(new Paragraph("               Validé",f9));
//            
//            PdfPCell pdfPCell22 = new PdfPCell(new Paragraph(" Analyse4\n",f4));
//            PdfPCell pdfPCell23 = new PdfPCell(new Paragraph("               15",f9));
//            PdfPCell pdfPCell24 = new PdfPCell(new Paragraph("               Validé",f9));
//            //LIGNE2
//            PdfPCell pdfPCell25 = new PdfPCell(new Paragraph(" Maths Appliquées\n",f4));
//            PdfPCell pdfPCell26 = new PdfPCell(new Paragraph("               12",f9));
//            PdfPCell pdfPCell27 = new PdfPCell(new Paragraph("               Validé",f9));
//            
//            PdfPCell pdfPCell28 = new PdfPCell(new Paragraph(" Physique4\n",f4));
//            PdfPCell pdfPCell29 = new PdfPCell(new Paragraph("               15",f9));
//            PdfPCell pdfPCell30 = new PdfPCell(new Paragraph("               Validé",f9));
//            
//            PdfPCell pdfPCell31 = new PdfPCell(new Paragraph(" Electronique\n",f4));
//            PdfPCell pdfPCell32 = new PdfPCell(new Paragraph("               14,18",f9));
//            PdfPCell pdfPCell33 = new PdfPCell(new Paragraph("               Validé",f9));
//            
//            PdfPCell pdfPCell34 = new PdfPCell(new Paragraph(" Management\n",f4));
//            PdfPCell pdfPCell35 = new PdfPCell(new Paragraph("               15,6",f9));
//            PdfPCell pdfPCell36 = new PdfPCell(new Paragraph("               Validé",f9));
//            
//            PdfPCell pdfPCell37 = new PdfPCell(new Paragraph(" LC4\n",f4));
//            PdfPCell pdfPCell38 = new PdfPCell(new Paragraph("               15,19",f9));
//            PdfPCell pdfPCell39 = new PdfPCell(new Paragraph("               Validé",f9));
//            
//            PdfPCell pdfPCellN01 = new PdfPCell(new Paragraph("                 Points du jury\n\n",f7));
//            PdfPCell pdfPCellN02 = new PdfPCell(new Paragraph("  "));
//            PdfPCell pdfPCellN12 = new PdfPCell(new Paragraph("                 13,311",f8));
//
//            PdfPCell pdfPCellN11 = new PdfPCell(new Paragraph("                 Moyenne du l’année\n\n",f7));
//            
//            PdfPCell pdfPCellN21 = new PdfPCell(new Paragraph("                 Résultat de l’année\n\n",f7));
//            PdfPCell pdfPCellN22 = new PdfPCell(new Paragraph("                 Admis",f8));
//           
//
//            //Add cells to table
//            document.add(p9);
//            document.add(new Chunk(ls));
//            document.add(p1);
//            document.add(p2);
//            document.add(p3);
//            document.add(p4);
//            document.add(p5);
//            document.add(p6);
//            document.add(p7);
//            document.add(p8);
//            pdfPTable1.addCell(pdfPCell1);
//            pdfPTable1.addCell(pdfPCell2);
//            pdfPTable1.addCell(pdfPCell3);
//            pdfPTable1.addCell(pdfPCell4);
//            pdfPTable1.addCell(pdfPCell5);
//            pdfPTable1.addCell(pdfPCell6);
//            pdfPTable1.addCell(pdfPCell7);
//            pdfPTable1.addCell(pdfPCell8);
//            pdfPTable1.addCell(pdfPCell9);
//            pdfPTable1.addCell(pdfPCell10);
//            pdfPTable1.addCell(pdfPCell11);
//            pdfPTable1.addCell(pdfPCell12);
//            pdfPTable1.addCell(pdfPCell13);
//            pdfPTable1.addCell(pdfPCell14);
//            pdfPTable1.addCell(pdfPCell15);
//            pdfPTable1.addCell(pdfPCell16);
//            pdfPTable1.addCell(pdfPCell17);
//            pdfPTable1.addCell(pdfPCell18);
//            pdfPTable1.addCell(pdfPCell19);
//            pdfPTable1.addCell(pdfPCell20);
//            pdfPTable1.addCell(pdfPCell21);
//            pdfPTable1.addCell(pdfPCell22);
//            pdfPTable1.addCell(pdfPCell23);
//            pdfPTable1.addCell(pdfPCell24);
//            pdfPTable1.addCell(pdfPCell25);
//            pdfPTable1.addCell(pdfPCell26);
//            pdfPTable1.addCell(pdfPCell27);
//            pdfPTable1.addCell(pdfPCell28);
//            pdfPTable1.addCell(pdfPCell29);
//            pdfPTable1.addCell(pdfPCell30);
//            pdfPTable1.addCell(pdfPCell31);
//            pdfPTable1.addCell(pdfPCell32);
//            pdfPTable1.addCell(pdfPCell33);
//            pdfPTable1.addCell(pdfPCell34);
//            pdfPTable1.addCell(pdfPCell35);
//            pdfPTable1.addCell(pdfPCell36);
//            pdfPTable1.addCell(pdfPCell37);
//            pdfPTable1.addCell(pdfPCell38);
//            pdfPTable1.addCell(pdfPCell39);
//            document.add(p9);
//            document.add(p9);
//            pdfPTable2.addCell(pdfPCellN01);
//            pdfPTable2.addCell(pdfPCellN02);
//            pdfPTable2.addCell(pdfPCellN11);
//            pdfPTable2.addCell(pdfPCellN12);
//            pdfPTable2.addCell(pdfPCellN21);
//            pdfPTable2.addCell(pdfPCellN22);
//            document.add(pdfPTable1);
//            document.add(p9);
//            document.add(p9);
//            document.add(pdfPTable2);
//            document.add(p9);
//            document.add(p9);
//            document.add(p10);
//            document.add(p9);
//            document.add(p11);
//            document.add(p9);
//            document.add(p9);
//            document.add(p12);
//            
//            Rectangle rect= new Rectangle(577,825,18,15); 
//            rect.enableBorderSide(1);
//            rect.enableBorderSide(2);
//            rect.enableBorderSide(4);
//            rect.enableBorderSide(8);
//            rect.setBorderColor(BaseColor.BLACK);
//            rect.setBorderWidth(1);
//            document.add(rect);
//        
//            document.close();
//            Desktop.getDesktop().open(new File ("src\\relve_note.pdf"));
//        } catch (BadElementException | IOException ex) {
//            Logger.getLogger(PdfRel.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    public static void AttestationScolarite(){
//        Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.BOLD);
//        Font footerFont = new Font(Font.FontFamily.TIMES_ROMAN, 10);
//        Document doc = new Document(PageSize.A4);
//        try {
//            PdfWriter.getInstance(doc, new FileOutputStream("src\\main\\resources\\Documents\\exemple.pdf"));
//            doc.open();
//            Image img = Image.getInstance("src\\main\\resources\\images\\ENSA-TÃ©touan.png");
//            img.scaleAbsolute(90, 90);
//            img.setAlignment(Image.ALIGN_LEFT);
//            doc.add(img);
//            // Ajouter une paragraphe qui contient le titre du document
//        Paragraph title = new Paragraph();
//            addEmptyLine(title, 2);
//            title.add(new Paragraph("ATTESTATION DE SCOLARITE", catFont));
//            title.setAlignment(Element.ALIGN_CENTER);
//            addEmptyLine(title, 1);
//            document.add(title);
//           
//           
//            // Ajouter une paragraphe qui contient le corps du document + les informations de l'étudiant
//            Paragraph preface = new Paragraph();
//            preface.add(new Paragraph(
//                    "Le Directeur de l'Ecole Nationale des Sciences Appliquées atteste que l'étudiant(e): "));
//            addEmptyLine(preface, 1);
//            preface.add(new Paragraph(
//                    DataEtu.getNom()));
//            addEmptyLine(preface, 1);
//            preface.add(new Paragraph(
//                    "Numéro de la Carte Nationale d'Identité: " + DataEtu.getCIN()));
//            addEmptyLine(preface, 1);
//            preface.add(new Paragraph(
//                    "Code national de l'étudiant(e): " + DataEtu.getCNE()));
//            addEmptyLine(preface, 1);
//            preface.add(new Paragraph(
//                    "Né(e) le : "+ DataEtu.getdateStr() + " à " + DataEtu.getVille()));
//            addEmptyLine(preface, 1);
//            preface.add(new Paragraph(
//                    "Poursuit ses études à l'Ecole Nationale des Sciences Appliquées de Tétouan pour l'année universitaire : " + DataEtu.getAnnee()));
//            addEmptyLine(preface, 2);
//            preface.add(new Paragraph(
//                    "Diplome : " + DataEtu.getDiplome()));
//            addEmptyLine(preface, 1);
//            preface.add(new Paragraph(
//                    "Filiere : " + DataEtu.getFiliere()));
//            addEmptyLine(preface, 1);
//            preface.add(new Paragraph(
//                    "Année : " + DataEtu.getAnneeS()));      
//            preface.setIndentationLeft(40);
//            document.add(preface);
//           
//            // Ajouter une paragraphe qui contient la date et le lieu de la réalisation du document
//            Paragraph footer = new Paragraph();
//            addEmptyLine(footer, 3);
//            footer.add(new Paragraph(
//                       "Fait à Tétouan, Le " + format.format(date),
//                       smallBold));
//            addEmptyLine(footer, 1);          
//            footer.add(new Paragraph(
//                       "Directeur ", smallBold));
//            addEmptyLine(footer, 1);
//            footer.setAlignment(Element.ALIGN_CENTER);
//            document.add(footer);
//           
//            // Ajouter la signature
//            float[] colsWidth2 = {1f,3f,1f};        
//            PdfPTable table4 = new PdfPTable(colsWidth2);
//            table4.getDefaultCell().setBorder(0);
//            PdfPCell cellFour = new PdfPCell(img4, true);
//            cellFour.setBorder(Rectangle.NO_BORDER);
//            table4.addCell(" ");
//            table4.addCell(cellFour);
//            table4.addCell(" ");        
//            document.add(table4);
//               
//            // paragraphe vide pour la séparation
//            Paragraph saut = new Paragraph();
//            addEmptyLine(saut, 1);
//            document.add(saut);
//           
//           
//            // Ajouter une ligne
//            document.add(new LineSeparator());
//            LineSeparator sep = new LineSeparator();
//            sep.setOffset(3);
//            document.add(sep);
//               
//            // Ajouter une paragraphe : avis important
//            Paragraph footer2 = new Paragraph();
//            footer2.add(new Paragraph(
//                        "Le présent document n'est délivré qu'en un seul exemplaire.",
//                        redFont));
//            footer2.add(new Paragraph(
//                        "Il appartient à l'étudiant d'en faire des photocopies certifiées conformes.",
//                        redFont));
//            footer2.setAlignment(Element.ALIGN_CENTER);
//            document.add(footer2);
//    }   catch (FileNotFoundException ex) {
//            Logger.getLogger(GenererPdf.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (DocumentException ex) {
//            Logger.getLogger(GenererPdf.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (BadElementException ex) {
//            Logger.getLogger(GenererPdf.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(GenererPdf.class.getName()).log(Level.SEVERE, null, ex);
//        }
//}
}