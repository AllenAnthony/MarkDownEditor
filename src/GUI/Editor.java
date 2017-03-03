package GUI;

import java.awt.Font;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.IOException;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.petebevin.markdown.MarkdownProcessor;

public class Editor extends JScrollPane{
	private JTextPane editor;
	private MarkdownProcessor processor;
	private JScrollBar scrollBar;
	
	public Editor(navigation mynavigation,display mydisplay )
	{
		// TODO 自动生成的构造函数存根
		this.processor=new MarkdownProcessor();
		this.editor=new JTextPane();
		this.scrollBar=this.getVerticalScrollBar();
		this.editor.setFont(new Font("Microsoft YaHei", Font.PLAIN, 18));
		
		
		this.getViewport().add(editor);
		this.setBounds(0, 50, 350, 500);
		
		this.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
			
			@Override
			public void adjustmentValueChanged(AdjustmentEvent arg0) {
				// TODO 自动生成的方法存根
				mydisplay.getVerticalScrollBar().setValue(getVerticalScrollBar().getValue());
			}
		});
		
		this.editor.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) 
			{
				// TODO 自动生成的方法存根
				String markdown=editor.getText();
				mydisplay.setContent(processor.markdown(markdown));
		        //mydisplay.setContent(processor.markdown(markdown));
		        try {
					mynavigation.update(markdown);
				} catch (IOException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO 自动生成的方法存根
				String markdown=editor.getText();
		        mydisplay.setContent(processor.markdown(markdown));
		        try {
					mynavigation.update(markdown);
				} catch (IOException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO 自动生成的方法存根
				String markdown=editor.getText();
		        mydisplay.setContent(processor.markdown(markdown));
		        try {
					mynavigation.update(markdown);
				} catch (IOException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
			}
		});
		
		mynavigation.getlist().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO 自动生成的方法存根
				int index=mynavigation.getlist().getSelectedIndex();
				if(index>=0)
				{
					int headnum=mynavigation.getheadline().get(index);
					getVerticalScrollBar().setValue(20*headnum);
					//this.setCaretPosition(headnum);
					mydisplay.getPane().setCaretPosition(headnum);
				}
				
			}
		});
		
	}
	
    public void Update(display mydisplay) throws IOException {
        String markdown=editor.getText();
        mydisplay.setContent(processor.markdown(markdown));
    }
	
	public JTextPane geteditor() {
		return this.editor;
		
	}
	

}
