package GUI;

import java.awt.Font;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import org.jsoup.Jsoup;

import com.petebevin.markdown.MarkdownProcessor;

public class navigation extends JScrollPane{
	private MarkdownProcessor processor;
	private JList list;
	private DefaultListModel listmodel;
	
	private ArrayList<Integer> headline;
	
	public navigation() {
		// TODO 自动生成的构造函数存根
		this.processor=new MarkdownProcessor();
		this.headline=new ArrayList<>();
		
		this.listmodel=new DefaultListModel<>();
		
		this.list=new JList<>(listmodel);
		list.setFont(new Font("Microsoft YaHei", Font.PLAIN, 18));
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		this.getViewport().add(list);
		this.setBounds(0, 50, 200, 500);

	}
	
	
	public JList getlist()
	{
		return this.list;
	}
	
	public ArrayList<Integer> getheadline() {
		return this.headline;
		
	}
	
	public void update(String markdown) throws IOException 
	{
		this.listmodel.removeAllElements();
		this.headline.clear();
		
		BufferedReader MD=new BufferedReader(
													new StringReader(markdown));
		Pattern mypattern=Pattern.compile("<h[1-5]>.*");
		int headnum=0;
		String temp;
		for(int i=0;(temp=MD.readLine())!=null;i++)
		{
			temp=this.processor.markdown(temp);
			if(mypattern.matcher(temp).find())
			{
				temp=Jsoup.parse(temp).body().text();
				this.headline.add(i);
				this.listmodel.add(headnum++, temp);
			}
		}
		
	}
	
	
	

}
