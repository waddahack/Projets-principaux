package ui;

import org.newdawn.slick.UnicodeFont;
import towser.Towser;

public class Text {
    
    private int x, y;
    String text;
    UnicodeFont font;
    
    public Text(int x, int y, String text){
        this.text = text;
        this.font = Towser.normal;
        this.x = x;
        this.y = y-getHeight()/2;
    }
    
    public Text(int x, int y, String text, UnicodeFont font){
        this.text = text;
        this.font = font;
        this.x = x;
        this.y = y-getHeight()/2;
    }
    
    public void render(){
        if(font != null)
            font.drawString(x, y, text);
    }
    
    public String getText(){
        return text;
    }
    
    public int getWidth(){
        if(font != null)
            return font.getWidth(text);
        else return 0;
    }
    
    public int getHeight(){
        if(font != null)
            return font.getHeight(text);
        else
            return 0;
    }
    
    public void setText(String t){
        text = t;
    }
    
    public void setFont(UnicodeFont f){
        font = f;
    }
    
    public int getX(){
        return x;
    }
    
    public void setX(int newX){
        x = newX;
    }
    
    public int getY(){
        return y;
    }
    
    public void setY(int newY){
        y = newY-getHeight()/2;
    }
}
