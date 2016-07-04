
import java.io.*;
import java.util.*;
/**
 *
 * @author Akram
 */

class Library{
    Scanner in = new Scanner(System.in);
    String commands[] = {"addbook","issue","login","logout","register","bookslist","remove","userdetails","removeuser","changepassword","help","editbook","exit","file","submit","about","log"};
  String h = "COMMAND\t\tDescription                               USER REQ.       LOGGED IN REQ.\n";
    String helpCom[] = {
             "Add A new Book to Library                 (ADMINISTRATOR) (LOGGED IN)"
            ,"Issue Book (extended commands available)  (USER)          (LOGGED IN)"
            ,"Login to an Existing Account                              (LOGGED OUT)"
            ,"Logout                                    (USER)          (LOGGED IN)"
            ,"Register for a new Account                                (LOGGED OUT)"
            ,"Display Available Books in Library"
            ,"Remove a Book from Library(Permanent)     (ADMINISTRATOR) (LOGGED IN)"
            ,"Display User Details                      (USER)          (LOGGED IN)"
            ,"Remove existing User                      (ADMINISTRATOR) (LOGGED IN)"
            ,"Change Password of Current User           (USER)          (LOGGED IN)"
            ,"Diplay Available Commands And Description"
            ,"Edit a Books Details                      (ADMINISTRATOR) (LOGGED IN)"
            ,"Exit Library Application"
            ,"Display a File                            (ADMINISTRATOR) (LOGGED IN)"
            ,"Submit a issued Book                      (USER)          (LOGGED IN)"
            ,"Display details about a book "
            ,"Display log of current user               (USER)          (LOGGED IN)"
    };
    static File booksFile = new File("data/books.dat");
    static File usersFile = new File("data/users.dat");
    static BooksList bl = new BooksList();
    static UsersList ul = new UsersList();
    GregorianCalendar cal;
    String det = "";
    static PrintWriter p;
    
    public void start(){
        try{
            p = new PrintWriter(new BufferedWriter(new FileWriter("data\\log.dat",true)));
        }
        catch(Exception e){}
        updateExit();
        new Counter();
        //displayFile("C:\\Users\\Akram\\Desktop\\projects\\LibraryManager\\src\\data\\users.dat");
        //displayFile("C:\\Users\\Akram\\Desktop\\projects\\LibraryManager\\src\\data\\books.dat");
            displayHelp();
        while(true)
        {
            int cmd = getCommand();
            switch(cmd)
            {
                case 0  : storeNewBook();   break;
                case 1  : issueBook();      break;
                case 2  : userLogin();      break;
                case 3  : logOut();         break;
                case 4  : createNewUser();  break;
                case 5  : displayBooks();   break;
                case 6  : removeBook();     break;
                case 7  : userDetails();    break;
                case 8  : removeUser();     break;
                case 9  : changePass();     break;
                case 10 : displayHelp();    break;
                case 11 : modBook();        break;
                case 12 : System.exit(0);   break;
                case 13 : dispFile();       break;
                case 14 : submit();         break;
                case 15 : aboutBook();      break;
                case 16 : printLog();       break;
            }
        }
    }
    public int getCommand(){
        int ret = -1;
        System.out.print("> ");
        String input = in.nextLine();
        input+=" ";
        det = input.substring(input.indexOf(' ')+1).trim();
        input = input.substring(0,input.indexOf(' '));
        for(int i=0;i<commands.length;i++)
        {
            if(input.equalsIgnoreCase(commands[i]))
            {
                ret = i;
                if(helpCom[i].indexOf("(ADMINISTRATOR)")>-1)
                {
                    if(!ul.admin)
                    {
                        System.out.println("! Insufficient Privillage !");
                        input="";
                        ret = -1;
                    }
                    
                }
                else if(helpCom[i].indexOf("(USER)")>-1)
                {
                    if(ul.active==null)
                    {
                        System.out.println("! Login to continue !");
                        input="";
                        ret = -1;
                    }
                }
                if(helpCom[i].indexOf("(LOGGED OUT)")>-1)
                {
                    if(ul.active!=null)
                    {
                        System.out.println("! Log Out to Continue !");
                        input="";
                        ret = -1;
                    }
                }
                break;
            }
        }
        
        if(ret == -1)
        {
            if(!input.equals(""))System.out.println(" * INVALID COMMAND * ");
            ret = getCommand();
        }
        
        return ret;
    }
    
    public void dispFile(){
        if(det.equals("users"))
        {
            displayFile(usersFile.getPath());
        }
        else if(det.equals("books"))
        {
            displayFile(booksFile.getPath());
        }
        else
        {
            displayFile("data/"+det+"_fl.dat");
        }
    }
    public void updateExit(){
        try{
            BufferedReader r = new BufferedReader(new FileReader("data\\temp.dat"));
            String data = r.readLine();
            
            String t = "AppExit\t\t\t\t"+data;
            p.println(t);
            t = "AppStart\t\t\t"+time();
            p.println(t);
            
            r.close();
        }
        catch(Exception e){}
    }
    public void printLog(){
        if(det.equalsIgnoreCase("all"))
        {
            if(ul.admin)
            {
                p.close();
                displayFile("data\\log.dat");
                try{
                    p = new PrintWriter(new BufferedWriter(new FileWriter("data\\log.dat",true)));
                }
                catch(Exception e){}
            }
            else
            {
                System.out.println("! Insufficient Privilages !");
            }
            return;
        }
        if(!det.equals(""))
        {
            //System.out.println(ul.admin);
            if(ul.admin)
            {
                User u = ul.getUserByKey(det);
                if(u==null)
                {
                    System.out.println("! Invalid User Key !");
                }
                else
                {
                    if(u.log=="")
                    {
                        System.out.println("! Log of the specified user is empty !");
                    }
                    else
                    {
                        System.out.println("\n________________USER_LOG_________________");
                        System.out.println(u.log);
                    }
                }
            }
            else
            {
                System.out.println("! Insufficient Privilages !");
            }
        }
        else
        {
            if(ul.active.log=="")
                {
                    System.out.println("! Log of the current user is empty !");
                }
                else
                {
                    System.out.println("\n________________USER_LOG_________________");
                    System.out.println(ul.active.log);
                }
        }
    }
    public void submit(){
        String bn = det;
        Book b = bl.getBookByName(bn);
        if(b==null)
        {
            System.out.println("* Invalid Book name *");
            return;
        }
        //System.out.println("1**************************");
        ul.active.submitBook(b);
        //System.out.println("Thanks for using the Library");
    }
    public void aboutBook(){
        String bn = det;
        Book b = bl.getBookByName(bn);
        if(b==null)
        {
            System.out.println("* Invalid Book Name *");
            return;
        }
        
        b.displayDetials();
    }
    public void modBook(){
        //System.out.print("\tEnter Book name to edit > ");
        String bn = det;
        
        Book b = bl.getBookByName(bn);
        if(b==null)
        {
            System.out.println("* Invalid Book Name *");
            return;
        }
        
        System.out.print("\tEnter new name > ");
        String name = in.nextLine();
        
            System.out.print("\tEnter Author > ");
            String author = in.nextLine();
            
            System.out.print("\tEnter No of Pages > ");
            String pg = in.nextLine();
            
        b.bName = name;
        b.bAuthor = author;
        b.bPg = Integer.parseInt(pg);
        
        b.updateFile();
        bl.updateBooksFile();
    }
    public void displayHelp(){
        System.out.println("Available commands\n");
        for(int i=0;i<commands.length;i++)
        {
            System.out.println(commands[i]+(commands[i].length()>=8?"\t\t":"\t\t\t")+helpCom[i]);
        }
        System.out.println();
    }
    public void changePass(){
          System.out.print("\tEnter New Password > ");
          String p = in.nextLine();
          ul.active.uPass = p;
          ul.active.updateFile();
          ul.updateUsersFile();
    }
    public void userDetails(){
        User u = ul.getUserByKey(det.trim());
        //System.out.println(det);
        if(u==null){
            
        }
        else
        {
            //System.out.println("1");
            if(ul.admin)
            {
                //System.out.println("2");
                u.displayDetails();
            }
            else
            {
                System.out.println("* Insufficient privillages *");
            }
        }
        if(!det.equals("")){return;}
        
        if(ul.active==null)
        {
            System.out.println("* User not logged in *");
            return;
        }
            System.out.println("1");
        ul.active.displayDetails();
    }
    public void displayBooks(){
        for(int i=0;i<bl.books.length;i++)
        {
            Book b = bl.books[i];
            System.out.println(appendTab(b.bName,3)+appendTab(b.bAuthor,3)+appendTab(b.bId,1)+appendTab(b.bPg+"",1)+appendTab(b.bDate,2)+(b.issuable?'y':'n'));
        }
    }
    public void removeBook(){
        String bk = det;
        bl.removeBook(bk);
    }
    public void removeUser(){
        ul.removeUser(det);
    }
    public void issueBook(){
        BookCart cart = new BookCart();
        while(true)
        {
            System.out.print("\tIssue Command > ");
            String cmd = in.nextLine();
            cmd+=" ";
            String det = cmd.substring(cmd.indexOf(' ')+1).trim();
            cmd = cmd.substring(0,cmd.indexOf(' '));
            
            if(cmd.equalsIgnoreCase("add"))
            {
                if(Character.isDigit(det.charAt(0)))
                {
                    det = cart.shop.get(Integer.parseInt(det)-1).bName;
                }
                cart.add(det);
                cart.printCart();
            }
            else if(cmd.equalsIgnoreCase("remove"))
            {
                if(Character.isDigit(det.charAt(0)))
                {
                    det = cart.cart.get(Integer.parseInt(det)-1).bName;
                }
                cart.remove(det);
                cart.printCart();
            }
            else if(cmd.equalsIgnoreCase("cancel"))
            {
                System.out.println("Operation Cancelled by User");
                return;
            }
            else if(cmd.equalsIgnoreCase("done"))
            {
                cart.done(ul.active);
                break;
            }
            else if(cmd.equalsIgnoreCase("books"))
            {
                cart.printShop();
            }
        }
    }
    public void createNewUser(){
            System.out.print("\tUser Name :> ");
            String name = in.nextLine();
            
            System.out.print("\tPassword :> ");
            String pass = in.nextLine();
            
            System.out.print("\tSchool :> ");
            String school = in.nextLine();
            
            System.out.print("\tSTD. :> ");
            String std = in.nextLine();
            
            System.out.print("\tAddress :> ");
            String add = in.nextLine();
            
            System.out.print("\tAge :> ");
            String age = in.nextLine();
            
            System.out.print("\tTel. No :> ");
            String tel = in.nextLine();
            
            String key = ul.generateNewUserKey();
            System.out.print("\n\n\t\t** "+key+" **\n\n");
            
            cal = new GregorianCalendar();
            User u = new User();
            
            u.uId = key;
            u.uName = name;
            u.uPass = pass;
            u.uSchool = school;
            u.uClass = std;
            u.uAdd = add;
            u.uAge = age;
            u.uPh = tel;
            u.uDate = cal.get(Calendar.DATE)+" / "+cal.get(Calendar.MONTH)+" / "+cal.get(Calendar.YEAR);
            
            ul.addUser(u);
    }
    public void displayFile(String f){
        try{
            FileReader a = new FileReader(f);
            BufferedReader br = new BufferedReader(a);
            
            String s = "";
            while((s=br.readLine())!=null)
            {
                System.out.println(s);
            }
            br.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    public void userLogin(){
        
        System.out.print("\t\t\t\tUserName : ");
        String usern = in.nextLine();
        System.out.print("\t\t\t\tPassword : ");
        String pass=in.nextLine();
        
            boolean f = true;
            for(int i=0;i<ul.users.length;i++)
            {
                if(ul.users[i].uName.equals(usern) && ul.users[i].uPass.equals(pass))
                {
                    ul.active = ul.users[i];
                    String t = appendTab("login..",2)+time()+"\n";
                    p.print(Library.ul.active.uName+"\t"+t);
                    ul.active.log+=t;
                    ul.active.updateFile();
                    ul.adminStatus();
                    System.out.println("Hi "+usern);
                    f=false;
                    break;
                }
            }
            if(f)
            {
                System.out.println("* Invalid Username/Password *");
                return;
            }
    }
    public static String time(){
        GregorianCalendar cal = new GregorianCalendar();
        String dt = cal.get(Calendar.DATE)+"/"+cal.get(Calendar.MONTH)+"/"+cal.get(Calendar.YEAR)+"  ,  "+cal.get(Calendar.HOUR)+":"+cal.get(Calendar.MINUTE)+":"+cal.get(Calendar.SECOND);
        return dt;
    }
    public void logOut(){
        String t = "logout.\t\t"+time()+"\n";
        p.print(Library.ul.active.uName+"\t"+t);
        ul.active.log+=t;
        ul.active.updateFile();
        ul.active = null;
        ul.admin = false;
    }
    private void storeNewBook() {
        try{
            System.out.print("\tEnter Book Name :> ");
            String name = in.nextLine();
            
            System.out.print("\tEnter Author :> ");
            String author = in.nextLine();
            
            System.out.print("\tEnter No of Pages :> ");
            String pg = in.nextLine();
            
            System.out.print("\tEnter Issuable(Y/N) :> ");
            String issue = in.nextLine();
            
            String key = bl.generateNewBookId();
            System.out.print("\n\n\t\t** "+key+" **\n\n");
            
            cal = new GregorianCalendar();
            Book b = new Book();
            b.bId = key;
            b.bName = name;
            b.issuable = issue.equalsIgnoreCase("y");
            b.bAuthor = author;
            b.bPg = Integer.parseInt(pg);
            b.bDate = cal.get(Calendar.DATE)+" / "+cal.get(Calendar.MONTH)+" / "+cal.get(Calendar.YEAR);
            b.updateFile();
            bl.addBook(b);
            bl.updateBooksFile();
        }
        catch(Exception exp){
            System.out.println(exp);
        }
    }
    public String appendTab(String t,int l){
        String ret = t;
        int r = t.length()/8;
        ret+="\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t".substring(0,l-r);
        return ret;
    }
}