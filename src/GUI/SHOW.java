package GUI;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SHOW {

	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		JFrame frame=new JFrame("MardownEditor");
		Container rootpane=frame.getContentPane();
		
		frame.setLayout(null);
		

        JLabel navigationLabel=new JLabel("catalog",JLabel.CENTER);
        navigationLabel.setBounds(0,0,200,50);
		JLabel editorLabel=new JLabel("editor",JLabel.CENTER);
		editorLabel.setBounds(0,0,350,50);
		JLabel displayLabel=new JLabel("displayer",JLabel.CENTER);
        displayLabel.setBounds(0,0,350,50);
        
        navigation mynavigation=new navigation();
        display mydisplay=new display();
        Editor myeditor=new Editor(mynavigation, mydisplay);
        
        JPanel left=new JPanel();
        left.setBounds(10,0,205,600);
        JPanel middle=new JPanel();
        middle.setBounds(215,0,355,600);
        JPanel right=new JPanel();
        right.setBounds(570,0,355,600);
        left.setLayout(null);
        middle.setLayout(null);
        right.setLayout(null);
        
        left.add(navigationLabel);
        left.add(mynavigation);
        right.add(displayLabel);
        right.add(mydisplay);
        middle.add(editorLabel);
        middle.add(myeditor);

        Menu menuBar=new Menu(myeditor,mydisplay);
        frame.setJMenuBar(menuBar);

        rootpane.add(left);
        rootpane.add(middle);
        rootpane.add(right);

        frame.setSize(940,620);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);//使窗口显示在屏幕中央
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

	}

}
