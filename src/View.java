
import java.awt.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

public class View extends JFrame{
    Library lib ;
    JPanel p1 = new JPanel();
    JPanel p2 = new JPanel();
    Dimension d = null;
    
        JTextField uName = new JTextField(15);
        JPasswordField uPass = new JPasswordField(15);
    public View(Library tl){
        lib = tl;
        initGUI();
    }
    public void initGUI(){
        setVisible(true);
        setResizable(false);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        d = this.getSize();
        this.setLayout(null);
        this.setContentPane(p1);
        
        p1.setLayout(null);
        p1.add(new ImageIcon(ImageIO.read(new File("test.jpg"))));
        
        JPanel basic = new JPanel();
        basic.setLayout(new BoxLayout(basic, BoxLayout.Y_AXIS));

        basic.add(Box.createVerticalGlue());
        
        JPanel in1 = new JPanel();
        in1.setLayout(new BoxLayout(in1, BoxLayout.X_AXIS));
        in1.add(new JLabel(" UserName : "));
        in1.add(uName);
        in1.setSize(in1.getPreferredSize());
        
        JPanel in2 = new JPanel();
        in2.setLayout(new BoxLayout(in2, BoxLayout.X_AXIS));
        in2.add(new JLabel(" Password : "));
        in2.add(uPass);
        in2.setSize(in2.getPreferredSize());
                
        JPanel bottom = new JPanel();
        bottom.setAlignmentX(1f);
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.X_AXIS));

        JButton ok = new JButton("OK");
        JButton close = new JButton("Close");

        bottom.add(ok);
        bottom.add(Box.createRigidArea(new Dimension(5, 0)));
        bottom.add(close);
        bottom.add(Box.createRigidArea(new Dimension(15, 0)));

        basic.add(in1);
        basic.add(Box.createRigidArea(new Dimension(0, 15)));
        basic.add(in2);
        basic.add(Box.createRigidArea(new Dimension(0, 15)));
        basic.add(bottom);
        basic.add(Box.createRigidArea(new Dimension(0, 15)));
        basic.setSize(basic.getPreferredSize().width+50,basic.getPreferredSize().height+50);
        basic.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(61,61,255),3,true),"LOGIN"));
        
        addAtCentre(basic);
    }
    public void addAtCentre(JPanel p){
        int hiddenHeight=getInsets().bottom+getInsets().top;  
        int hiddenWidth=getInsets().left+getInsets().right;
        int h = d.height , w = d.width;
        int h2 = p.getHeight() , w2 = p.getWidth();
        
        p.setLocation((w-w2-hiddenWidth)/2, (h-h2-hiddenHeight)/2);
        add("Centre",p);
    }
}