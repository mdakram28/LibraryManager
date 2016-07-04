
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Akram
 */
public class Book {
    String bId = "" , bName = "" , bAuthor = "" , bDate = "";
    int bPg = 0;
    boolean issued = false;
    boolean issuable = true;
    User issuer = null;
    
    public static Book getBookById(String id){
        Book ret = new Book();
        ret.bId = id;
        try{
            BufferedReader r = new BufferedReader(new FileReader("data/books/"+id+"_fl.dat"));
            String l="";
            ret.bId = r.readLine();
            ret.bName = r.readLine();
            ret.bAuthor = r.readLine();
            ret.bPg = Integer.parseInt(r.readLine());
            ret.bDate = r.readLine();
            
            r.close();
        }
        catch(Exception e){

            System.out.println("A book from books.dat not found. Please consult the administrator.");
        }
        return ret;
    }
    public void updateFile(){
        try{
            FileWriter a = new FileWriter("data/books/"+bId+"_fl.dat");
            BufferedWriter b = new BufferedWriter(a);
            PrintWriter wr = new PrintWriter(b);
            wr.println(bId);
            wr.println(bName);
            wr.println((bAuthor.equals(""))?"N/A":bAuthor);
            wr.println(bPg);
            wr.println(bDate);
            
            wr.close();
            a.close();
            b.close();
            
        }
        catch(Exception exp){
            System.out.println(exp);
        }
    }
    public void removeBookRecords(){
        File f = new File("data/books/"+bId+"_fl.dat");
        f.delete();
    }
    public void displayDetials(){
        System.out.println("\nBook Name : "+bName);
        System.out.println("Book Id   : "+bId);
        System.out.println("Author    : "+bAuthor);
        System.out.println("D.O.C.    : "+bDate);
        System.out.println("No. of Pg : "+bPg);
        System.out.println("Issuable  : "+(issuable?"yes":"no"));
        System.out.println("Available : "+(issued?"no":"yes"));
    }
}