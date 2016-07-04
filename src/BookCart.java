
import java.util.ArrayList;

/**
 *
 * @author Akram
 */
public class BookCart {
    ArrayList<Book> shop = new ArrayList<Book>();
    ArrayList<Book> cart = new ArrayList<Book>();
    
    public BookCart(){
        for(int i = 0;i<Library.bl.books.length;i++)
        {
            if(Library.bl.books[i].issuable && !(Library.bl.books[i].issued))
            {
                shop.add(Library.bl.books[i]);
            }
        }
    }
    public void printShop(){
        System.out.println("\n---------------------AVAILABLE------------------------------");
        for(int i=0;i<shop.size();i++)
        {
            System.out.println(((Book)(shop.get(i))).bName + "\t\t\t"+((Book)(shop.get(i))).bAuthor);
        }
        System.out.println("------------------------------------------------------------\n");
    }
    public void printCart(){
        System.out.println("\n---------------------YOUR-CART-------------------------------");
        for(int i=0;i<cart.size();i++)
        {
            System.out.println(((Book)(cart.get(i))).bName + "\t\t\t"+((Book)(cart.get(i))).bAuthor);
        }
        System.out.println("------------------------------------------------------------\n");
    }
    public void add(String bn){
        bn = bn.trim();
        boolean f = true;
        for(int i=0;i<shop.size();i++)
        {
            if(((Book)(shop.get(i))).bName.equalsIgnoreCase(bn))
            {
                f = false;
                cart.add((Book)(shop.get(i)));
                shop.remove(i);
                break;
            }
        }
        if(f)
        {
            System.out.println("* Book is already issued or is not available *");
        }
        else
        {
            System.out.println("Book added to cart : "+bn);
        }
    }
    public void remove(String bn){
        bn = bn.trim();
        boolean f = true;
        for(int i=0;i<cart.size();i++)
        {
            if(((Book)(cart.get(i))).bName.equalsIgnoreCase(bn))
            {
                f = false;
                shop.add((Book)(shop.get(i)));
                cart.remove(i);
                break;
            }
        }
        if(f)
        {
            System.out.println("* Book not added to cart *");
        }
        else
        {
            System.out.println("Book removed from cart : "+bn);
        }
    }
    public void done(User u){
        int i;
        for(i=0;i<cart.size();i++)
        {
            ((Book)(cart.get(i))).issued = true;
            //((Book)(cart.get(i))).issuer = u;
        }
        System.out.println(i+" Books Successfully Issued ");
        u.addIssuedBook(cart);
        Library.bl.updateBooksFile();
        u.updateFile();
    }
}
