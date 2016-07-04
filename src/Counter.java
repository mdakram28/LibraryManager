
import java.io.*;
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
public class Counter extends Thread {
    GregorianCalendar cal;
    public Counter(){
        this.start();
    }
    @Override
    public void run(){
        System.out.println("running ..... ");
        PrintWriter p;
        while(true)
        {
            try{
                p = new PrintWriter(new BufferedWriter(new FileWriter("data\\temp.dat")));
                String t = time();
                p.println(t);
                //System.out.println(t);
                p.close();
                Thread.sleep(900);
            }
            catch(Exception e){
                System.out.println(e);
            }
        }
    }
    public String time(){
        cal = new GregorianCalendar();
        String dt = cal.get(Calendar.DATE)+"/"+cal.get(Calendar.MONTH)+"/"+cal.get(Calendar.YEAR)+"  ,  "+cal.get(Calendar.HOUR)+":"+cal.get(Calendar.MINUTE)+":"+cal.get(Calendar.SECOND);
        return dt;
    }
}
