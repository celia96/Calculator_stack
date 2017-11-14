// Celia Choy
// Postfix Calculator Applet

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*; // for Stack

public class Calc extends Applet implements ActionListener {

    private static final long serialVersionUID = 1L; // to avoid Eclipse warning
    
    // instance variables
    
    protected Label result;         // label used to show result
    protected Stack<Integer> stack; // stack used for calculations
    protected int current;          // current number being entered
    protected boolean entered;      // whether current number has been entered
                                    // if so, show number in red

    // local color constants
    static final Color black = Color.black;
    static final Color white = Color.white;
    static final Color red = Color.red;
    static final Color blue = Color.blue;
    static final Color yellow = Color.yellow;
    static final Color orange = Color.orange;
    static final Color lblue = new Color(200, 220, 255);
    static final Color dred = new Color(160, 0, 100);
    static final Color dgreen = new Color(0, 120, 90);
    static final Color dblue = Color.blue.darker();

    public void init() {
        setFont(new Font("TimesRoman", Font.BOLD, 28));

        // add your code here to set up the applet.
	
        // hint: to get the blue border around the result label,
        // I used a BorderLayout and added empty labels to the borders

        // make good use of helper methods to keep your code readable -
        // a rule of thumb is: if it doesn't fit on the screen, the
        // method is too long (and you might loose points :( )


        // just a placeholder to get it to compile and to demonstrate
        // the event handling:
        
        this.current = 0;
        this.stack = new Stack<Integer>();
        this.entered = false;
        
        Panel p1 = new Panel();
        p1.setLayout(new BorderLayout());
        result = new Label("0", Label.RIGHT);
        result.setForeground(dgreen);
        result.setBackground(white);
        p1.add("Center", result); 
        p1.add("South", new Label());
        p1.add("North", new Label());
        p1.add("East", new Label());
        p1.add("West", new Label());
        p1.setBackground(dblue);
        
        Panel p2 = new Panel();
        p2.setLayout(new GridLayout(4, 4));
        p2.setBackground(dblue);
        p2.add(CButton("7", dgreen, yellow));
        p2.add(CButton("8", dgreen, yellow));
        p2.add(CButton("9", dgreen, yellow));
        p2.add(CButton("+", dblue, orange));
        p2.add(CButton("4", dgreen, yellow));
        p2.add(CButton("5", dgreen, yellow));
        p2.add(CButton("6", dgreen, yellow));
        p2.add(CButton("-", dblue, orange));
        p2.add(CButton("1", dgreen, yellow));
        p2.add(CButton("2", dgreen, yellow));
        p2.add(CButton("3", dgreen, yellow));
        p2.add(CButton("*", dblue, orange));
        p2.add(CButton("0", dgreen, yellow));
        p2.add(CButton("(-)", dred, yellow));
        p2.add(CButton("Pop", dred, yellow));
        p2.add(CButton("/", dblue, orange));
        
        Panel p3 = new Panel();
        p3.setLayout(new GridLayout(1, 1));
        p3.setBackground(dblue);
        p3.add(CButton("Enter", dblue, lblue));
        p3.add(CButton("Clear", dblue, lblue));

        setLayout(new BorderLayout());
        setBackground(dblue);
        add("North", p1);
        add("Center", p2);
        add("South", p3);
    }
   
    // a useful helper methods, given to you for free!

    // create a colored button
    protected Button CButton(String s, Color fg, Color bg) {
        Button b = new Button(s);
        b.setBackground(bg);
        b.setForeground(fg);
        b.addActionListener(this);
        return b;
    }
    
    // handle button clicks
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof Button) {
            String label = ((Button)e.getSource()).getLabel();
            if (label.equals("+")){
                add();
            }
            else if (label.equals("-")) {
                sub();
            }
            else if (label.equals("*")) {
                mult();
            }
            else if (label.equals("(-)")) {
                minus();
            }
            else if (label.equals("Pop")) {
                pop();
            }
            else if (label.equals("/")) {
                div();
            }
            else if (label.equals("Enter")) {
                enter();
            }
            else if (label.equals("Clear")) {
                clear();
            }
            
            // add similar calls for all other "non-number" buttons
            else {     // number button
                int n = Integer.parseInt(label);
                number(n);
            }
        }
    }

    // display number n in result label
    protected void show(int n) {
    	if (entered == true) {
        	result.setForeground(red);
    	} else {
    		result.setForeground(dgreen);
    	}
        result.setText(Integer.toString(n));
    }

    // handle add button
    protected void add() {
        System.out.println("add was pressed");
        if (entered == true) {
        	int a = stack.pop();
        	int b = stack.pop();
        	current = a + b;
        } else {
        	int a = stack.pop();
            current = current + a;
        }
        entered = true;
        show(current);
    }
    protected void sub() {
    	 System.out.println("sub was pressed");
    	 if (entered == true) {
         	int a = stack.pop();
         	int b = stack.pop();
         	current = b - a;
         } else {
        	int a = stack.pop();
            current = a - current;
         }
         entered = true;
         show(current);
    }
    protected void mult() {
    	 System.out.println("mult was pressed"); 
    	 if (entered == true) {
          	int a = stack.pop();
          	int b = stack.pop();
          	current = a * b;
          } else {
         	int a = stack.pop();
            current = a * current;
          }
          entered = true;
          show(current);
    }
    protected void minus() {
        System.out.println("minus was pressed");
        if (entered == true) {
        	current = stack.pop();
        	current = current - (current*2);
        	stack.push(current);
        	show(current);
        	current = 0;
        } else {
        	entered = true;
        	current = current - (current*2);
        	stack.push(current);
        	show(current);
        	current = 0;
        }
        
    }
    protected void pop() {
	 System.out.println("pop was pressed");
   	 if (stack.empty()) {
   		 current = 0;
   	 } else {
		 if (entered == true) {
			 stack.pop();
			 if (stack.empty()) {
				 current = 0;
			 } else {
				 current = stack.peek();
			 }
		 } else {
			 if (stack.empty()) {
				 current = 0;
			 } else {
				 entered = true;
				 current = stack.peek();
			 }
		 }
   	 }
   	 show(current);
   }
   protected void div() {
    	 System.out.println("div was pressed");
    	 if (entered == true) {
          	int a = stack.pop();
          	int b = stack.pop();
          	current = b / a;
          } else {
         	int a = stack.pop();
             current = a / current;
          }
          entered = true;
          show(current);
    }
    protected void enter() {
    	System.out.println("enter was pressed");
    	entered = true;
    	stack.push(current);
    	show(current);
    	current = 0;
    }
    protected void clear() {
    	System.out.println("clear was pressed");
    	stack = new Stack<Integer>();
    	current = 0;
    	entered = false;
    	show(current);    	
    }
    // handle number buttons
    protected void number(int n) {
    	entered = false;
        current = current * 10 + n;
    	show(current);
    }
}
