
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Akram
 */
public class BooksList {
    Book[] books = {};
    
    public BooksList(){
        //validateBooksFile();
        //updateBooksFile();
        this.update();
    }
    public void update(){
        
        try{
        BufferedReader r = new BufferedReader(new FileReader(Library.booksFile));
        ArrayList<Book> bk = new ArrayList<Book>();
            String l="";
            String key="";
            while((l=r.readLine())!=null)
            {
                String[] dat = extractData(l,';');
                Book temp = Book.getBookById(dat[0]);
                temp.issuable = dat[2].equalsIgnoreCase("y");
                temp.issued = dat[3].substring(0,1).equalsIgnoreCase("y");
                //System.out.println("2");
                //if(temp.issued){temp.issuer = Library.ul.getUserByKey(dat[3].substring(1));}
                //System.out.println("1");
                bk.add(temp);
            }
            r.close();
            
            books = new Book[bk.size()];
            for(int i=0;i<bk.size();i++)
            {
                books[i] = (Book)bk.get(i);
            }
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    public String[] extractData(String s,char del){
        ArrayList<String> ret = new ArrayList<String>();
        s+=del;
        String w = "";
        for(int i=0;i<s.length();i++)
        {
            char ch = s.charAt(i);
            
            if(ch==del)
            {
                ret.add(w);
                w="";
            }
            else
            {
                w+=ch;
            }
        }
        String[] r = new String[ret.size()];
        for(int i = 0;i<ret.size();i++)
        {
            r[i] = ret.get(i);
        }
        return r;
    }
    public Book getBookByName(String nm){
        Book ret = null;
        for(int i=0;i<books.length;i++)
        {
            if(books[i].bName.equalsIgnoreCase(nm))
            {
                ret = books[i];
            }
        }
        
        return ret;
    }
    public void addBook(Book bk){
        Book[] tbk = new Book[books.length+1];
        int i;
        for(i = 0;i<books.length;i++)
        {
            tbk[i] = books[i];
        }
        tbk[i] = bk;
        books = tbk;
        tbk = null;
        
        bk.updateFile();
        updateBooksFile();
    }
    public void updateBooksFile(){
        try{
        FileWriter a = new FileWriter(Library.booksFile);
            BufferedWriter b = new BufferedWriter(a);
            PrintWriter p = new PrintWriter(b);
            for(int i=0;i<books.length;i++)
            {
                p.println(books[i].bId+";"+books[i].bName+";"+(books[i].issuable?'y':'n')+";"+(books[i].issued?'y':'n'));
            }
            p.close();
            b.close();
            a.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    public void removeBook(String n){
        Book[] tb = new Book[books.length-1];
        int i,j;
        for(i = 0,j=0;i<tb.length;i++,j++)
        {
            if(books[i].bName.equalsIgnoreCase(n)){
                j++;
                books[i].removeBookRecords();
            }
            tb[i] = books[j];
        }
        if(i==j)
        {
            System.out.println("* No Such book Found *");
        }
        
        books = tb;
        updateBooksFile();
    }
    public String generateNewBookId(){
        String ret = "";
        ret += (char)(((int)'A')+((int)(Math.random()*26)));
        ret += (int)((Math.random()*500)+100) + "";
        ret += (char)(((int)'A')+((int)(Math.random()*26)));
        if(!validateBookId(ret))
        {
            ret = generateNewBookId();
        }
        return ret;
    }
    public boolean validateBookId(String id){
        boolean ret = true;
        for(int i=0;i<books.length;i++)
        {
            if(books[i].bId.equalsIgnoreCase(id))
            {
                ret = false;
                break;
            }
        }
        return ret;
    }
    public void validateBooksFile(){
        try{
            ArrayList<String> f = new ArrayList<String>();
            File[] files = new File("data/books").listFiles();

            for (File file : files) {
                if (file.isFile()) {
                    f.add(file.getName());
                }
            }
            books = new Book[f.size()];
            for(int i=0;i<books.length;i++)
            {
                books[i] = new Book();
                BufferedReader r = new BufferedReader(new FileReader("data/books/"+f.get(i)));
                books[i].bId = r.readLine();
                books[i].bName = r.readLine();
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}
