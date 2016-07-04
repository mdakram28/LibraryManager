
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
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
public class User {
    String uId = "" , uName = "" , uPass = "" , uSchool = "" , uClass = "" , uAdd = "" , uAge = "" , uPh = "" , uDate = "";
    double fine = 0.0;
    double issuePrice = 2.5;
    Book[] issued = {};
    String log="";
    
    public void getUserByKey(String key){
        try{
            FileReader a = new FileReader("data/users/"+key+"_fl.dat");
            BufferedReader br = new BufferedReader(a);
            
            ArrayList<Book> bk = new ArrayList<Book>();
            String line="";
            
            uId = br.readLine();
            uName = br.readLine();
            uPass = br.readLine();
            fine = Double.parseDouble(br.readLine());
            uSchool = br.readLine();
            uClass = br.readLine();
            uAdd = br.readLine();
            uAge = br.readLine();
            uPh = br.readLine();
            uDate = br.readLine();
            
            while((line=br.readLine())!=null)
            {
                if(line.charAt(0)=='*'){break;}
                try{
                    bk.add(Library.bl.getBookByName(line.trim()));
                    //System.out.println(bk.get((0)).bName);
                }
                catch(Exception e){System.out.println("error : "+e);}
            }
            log="";
            while((line=br.readLine())!=null)
            {
                log+=line+"\n";
            }
            issued = new Book[bk.size()];
            for(int i=0;i<bk.size();i++)
            {
                issued[i] = (Book)bk.get(i);
            }
            a.close();
            br.close();
            
        }
        catch(Exception e){
            System.out.println("* Error while reader "+key+" user file *");
        }
    }
    public void addIssuedBook(ArrayList<Book> bk){
        Book tb[] = new Book[issued.length+bk.size()];
        int i;
        GregorianCalendar cal = new GregorianCalendar();
        String dt = cal.get(Calendar.DATE)+"/"+cal.get(Calendar.MONTH)+"/"+cal.get(Calendar.YEAR)+"  ,  "+cal.get(Calendar.HOUR)+":"+cal.get(Calendar.MINUTE)+":"+cal.get(Calendar.SECOND);
            
        for(i=0;i<issued.length;i++)
        {
            tb[i] = issued[i];
        }
        for(int j=0;j<bk.size();j++,i++)
        {
            tb[i] = (Book)(bk.get(j));
            String t = "ISSUE..\t"+tb[i].bId+"\t"+dt+"\t"+tb[i].bName+"\n";
            Library.p.print(Library.ul.active.uName+"\t"+t);
            log += t;
        }
        issued = tb;
        updateFile();
    }
    public void submitBook(Book b){
        boolean f = true;
        for(int i=0;i<issued.length;i++)
        {
            if(issued[i].equals(b))
            {
                f = false;
                break;
            }
        }
        if(f)
        {
            System.out.println("* Book not found *");
            return;
        }
        
        Book[] tb = new Book[issued.length-1];
        GregorianCalendar cal = new GregorianCalendar();
        String dt = cal.get(Calendar.DATE)+"/"+cal.get(Calendar.MONTH)+"/"+cal.get(Calendar.YEAR)+"  ,  "+cal.get(Calendar.HOUR)+":"+cal.get(Calendar.MINUTE)+":"+cal.get(Calendar.SECOND);
        
        b.issued=false;
        String t = "SUBMIT.\t"+b.bId+"\t"+dt+"\t"+b.bName+"\n";
        Library.p.print(Library.ul.active.uName+"\t"+t);
        log += t;
        System.out.println("Book successfully submitted ...");
        int i,j;
        for(i=0,j=0;i<tb.length;i++)
        {
            //System.out.println(issued[i].bId+"\t"+b.bId);
            if(issued[i].equals(b))
            {
                j++;
            }
            tb[i] = issued[i+j];
        }
        issued = tb;
        updateFile();
        Library.bl.updateBooksFile();
    }
    public void updateUserDetails(){
        getUserByKey(uId);
    }
    public void removeUserRecords(){
        File f = new File("data/users/"+uId+"_fl.dat");
        f.delete();
        
        for(int i=0;i<issued.length;i++)
        {
            issued[i].issued = false;
        }
        
        Library.bl.updateBooksFile();
    }
    public void updateFile(){
        try{
            FileWriter a = new FileWriter("data/users/"+uId+"_fl.dat");
            BufferedWriter b = new BufferedWriter(a);
            PrintWriter wr = new PrintWriter(b);
            wr.println(uId);
            wr.println(uName);
            wr.println(uPass);
            wr.println(fine);
            wr.println(uSchool);
            wr.println(uClass);
            wr.println(uAdd);
            wr.println(uAge);
            wr.println(uPh);
            wr.println(uDate);
            
            for(int i=0;i<issued.length;i++)
            {
                wr.println(issued[i].bName);
            }
            wr.println("*");
            wr.print(log);
            wr.close();
            a.close();
            b.close();
        }
        catch(Exception exp){
            System.out.println(exp);
        }
    }
    public void displayDetails(){
        System.out.println("\nUserName  : "+uName);
        System.out.println("UserId    : "+uId);
        System.out.println("School    : "+uSchool);
        System.out.println("STD       : "+uClass);
        System.out.println("User Add  : "+uAdd);
        System.out.println("User Age  : "+uAge);
        System.out.println("Phone No  : "+uPh);
        System.out.println("D.O.C     : "+uDate);
        System.out.println("\tBooks Issued");
        for(int i=0;i<issued.length;i++)
        {
            System.out.println(issued[i].bName+"\t"+issued[i].bAuthor);
        }
        System.out.println("------log-------\n"+log);
    }
}
