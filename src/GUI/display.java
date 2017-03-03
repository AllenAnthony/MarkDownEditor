package GUI;

import java.awt.Font;
import java.awt.ScrollPane;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.text.JTextComponent;
import javax.swing.text.html.HTMLEditorKit;

import org.jsoup.Jsoup;

public class display extends JScrollPane{
	public JEditorPane htmlpane;
	private HTMLEditorKit kit;
	
	public display() {
		// TODO 自动生成的构造函数存根
		this.htmlpane=new JEditorPane();
		this.kit=new HTMLEditorKit();
		this.htmlpane.setEditorKit(kit);
		this.htmlpane.setContentType("text/html");
		this.htmlpane.setEditable(false);
		this.htmlpane.setFont(new Font("Microsoft YaHei", Font.PLAIN, 18));
		
		this.getViewport().add(htmlpane);
		this.setBounds(0, 50, 350, 500);
		
	}
	
	
	public String gethtml() {
		return Jsoup.parse(htmlpane.getText()).html();
		
	}
	
	public void  importCSS(String css) {
		kit.getStyleSheet().addRule(css);
		
	}

	public void setContent(String markdown) {
		// TODO 自动生成的方法存根
		htmlpane.setText(markdown);
	}
	


	public JEditorPane getPane() {
		// TODO 自动生成的方法存根
		return htmlpane;
	}


	

}
