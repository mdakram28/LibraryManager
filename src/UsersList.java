
import java.io.BufferedReader;
import java.io.BufferedWriter;
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
public class UsersList {
    User[] users = {};
    User active = null;
    boolean admin = false;
    String adminKey = getAdmin();
    
    public UsersList(){
        update();
    }
    public User getUserByKey(String key){
        User ret = null;
        for(int i=0;i<users.length;i++)
        {
            System.out.println(users[i].uId+"\t"+key);
            if(users[i].uId.equalsIgnoreCase(key))
            {
                ret = users[i];
            }
        }
        return ret;
    }
    public void update(){
        try{
        BufferedReader r = new BufferedReader(new FileReader(Library.usersFile));
        ArrayList<User> tu = new ArrayList<User>();
            String l="";
            while((l=r.readLine())!=null)
            {
                String[] dat = extractData(l,';');
                User u = new User();
                u.getUserByKey(dat[0]);
                tu.add(u);
                //System.out.println(u.uName);
            }
            r.close();
            
            users = new User[tu.size()];
            for(int i=0;i<tu.size();i++)
            {
                users[i] = tu.get(i);
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    public static String[] extractData(String s,char del){
        ArrayList<String> ret = new ArrayList<String>();
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
    public String getAdmin(){
        return "U_343G";
    }
    public void updateUsersFile(){
        try{
        FileWriter a = new FileWriter(Library.usersFile);
            BufferedWriter b = new BufferedWriter(a);
            PrintWriter p = new PrintWriter(b);
            for(int i=0;i<users.length;i++)
            {
                p.println(users[i].uId+";"+users[i].uName+";"+users[i].uPass);
            }
            p.close();
            b.close();
            a.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    public void addUser(User u){
        User[] tu = new User[users.length+1];
        int i;
        for(i = 0;i<users.length;i++)
        {
            tu[i] = users[i];
        }
        tu[i] = u;
        users = tu;
        u.updateFile();
        updateUsersFile();
    }
    public void removeUser(String n){
        User[] tu = new User[users.length-1];
        int i,j;
        for(i = 0,j=0;i<tu.length;i++,j++)
        {
            if(users[i].uName.equals(n) || users[i].uId.equalsIgnoreCase(n)){
                j++;
                users[i].removeUserRecords();
                Library.p.println("USER DELEATED\t\t\t\t"+Library.time());
            }
            tu[i] = users[j];
        }
        if(i==j)
        {
            System.out.println("* No Such user Found *");
        }
        users = tu;
        updateUsersFile();
    }
    public String generateNewUserKey(){
        String ret = "U_";
        ret += (int)((Math.random()*500)+100) + "";
        ret += (char)(((int)'A')+((int)(Math.random()*26)));
        if(!validateUserKey(ret))
        {
            ret = generateNewUserKey();
        }
        return ret;
    }
    public boolean validateUserKey(String key){
        boolean ret = true;
        for(int i=0;i<users.length;i++)
        {
            if(users[i].uId.equalsIgnoreCase(key))
            {
                ret = false;
                break;
            }
        }
        return ret;
    }
    public void adminStatus(){
        try{
            admin = active.uId.equals(adminKey);
            //System.out.println(active.uId+"\t"+adminKey+"\t"+admin);
        }
        catch(Exception e)
        {
            admin = false;
        }
    }
}
