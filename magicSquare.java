import java.awt.*;
import java.applet.*;
import java.awt.event.*;

/**
 * <p> * Title: CO32018 - Internet Programming - A Magic Square with a Hole</p>
 * 
 * <p>Description: Using all 28 dominoes, form a rectangular array with a central
 * hole. The sum of each <br />
 * of the eight rows and columns and the two diagonals must add up to 21. For
 * rows, add whole <br />
 * dominoes; for diagonals, add the 6 half dominoes from corner to corner</p>
 * 
 * <p>Copyright: Ray Nimmo(c) 2006</p>
 * 
 * <p>Version 1.8.9</p>
 * 
 * <p>Author Ray Nimmo - 05005802</p>
 */

public class magicSquare extends Applet implements MouseListener,
		MouseMotionListener {
	/**
	 * <p>
	 * Calls to set background, initialize mouse event listeners and initialize
	 * dominoes
	 * </p>
	 */
	public void init() {
		setBackGround(); 			   	  // call to draw background colour and set applet size
		this.addMouseListener(this); 	  // mouse listener added to board
		this.addMouseMotionListener(this);// mouse motion listener added to board
		initializeDominoes(); 		 	  // call to initialize all dominoes
	}

	/**
	 * <p>
	 * Calls to paint the board and dominoes<br />
	 * Also forces check for solved puzzle at every repaint <br />
	 * Method also contains nested if statements dealing with checks for boolean
	 * values<br />
	 * relating to user attempts at puzzle and if relevant message is displayed
	 * </p>
	 */
	public void paint(Graphics g) {
		paintBoard(g); 		// call to draw all board areas and decorative effects
		paintDominoes(g); 	// call to draw all dominoes to stockpile
		puzzleSolved(g); 	// check for puzzle completion
		// checks if user has attempted puzzle - or just clicked solve
		if (showAttemptedMessage == true)
			if (removeAttemptedMessage == false)
				attemptMessage(g);
		if (removeAttemptedMessage == true)
			resetAttempt();
		if (showSolutionText == true)
			showSolutionClickedText(g);
		if (showEasyStartText == true)
			showEasyStartClickedText(g);
	}

	// public void update (Graphics g)
	// {
	// paint(g);
	// }

	// ////////////////////////////////////////////////////////////////////////////////
	// DRAW FUNCTIONS FOR PLAYING BOARD
	// ////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * <p>
	 * Contains calls to paint all background items on board
	 * </p>
	 */
	public void paintBoard(Graphics g) {
		drawPlayingBoard(g); 	// call to set background colour and size
		drawBorders(g); 		// call to draw the coloured partition
		drawStockpile(g); 		// call to draw the stockpile
		drawAnswerAreas(g); 	// call to draw answer areas
		countLineValues(g); 	// call to check scores - re-check on every paint
	}

	/**
	 * <p>
	 * Sets color of background and size of applet
	 * </p>
	 * 
	 */
	public void setBackGround() {
		setBackground(Color.cyan);
		setSize(620, 568);
	}

	/**
	 * <p>
	 * Contains all code relating to drawing the playing board
	 * </p>
	 */
	public void drawPlayingBoard(Graphics g) {
		g.setColor(Color.black);
		x = 15; 		// set up x to be the first x-co'ords of first box
		for (int i = 1; i <= 4; i++) { 		// loop four times on the x-axis
			y = 15; 	// set up y to be the first y-co'ords of first box
			for (int j = 1; j <= 8; j++) {  // loop four times on the y-axis
				if (y > 183 && y < 319 && x > 109 && x < 208) {
					// exclusion from drawing the central boxes by setting
					// an exlusion zone around their co-ordinates. Initially
					// used the drawBlank(g) function to overwrite with
					// background colour. Found it works fine with no command
				} else
					drawPiece(g); 	// call to draw an 80*45 box on screen
				y = y + 61; 		// next loop increase y by 75
									// this will loop on y-axis 8 times
			}
			x = x + 96; 	// only reached after 8 loops on y-axis
							// when reached increases x by 150 for next loop
							// this moves cursor along then accesses y loop
							// again for another 8 boxes on y-axis
		}
	}

	/**
	 * <p>
	 * Function not used at present
	 * </p>
	 * <p>
	 * draws a space on the board set colour same as background so is not seen
	 * </p>
	 */
	public void drawBlank(Graphics g) {
		g.setColor(Color.cyan);
		g.drawRect(x, y, 80, 45);
	}

	/**
	 * <p>
	 * Draws a domino placeholder area on board
	 * </p>
	 */
	public void drawPiece(Graphics g) {
		g.setColor(Color.blue);
		g.drawRect(x, y, 90, 55); 			// draws a blue rectangle as placeholder
		g.setColor(Color.red);
		g.drawRect(x + 1, y + 1, 88, 53); 	// draws a red inside line to spaceholder
		g.setColor(Color.yellow);
		g.fillRect(x + 2, y + 2, 86, 51); 	// fills inside of placeholder yellow
	}

	/**
	 * <p>
	 * Draws white space for scores for vertical,horizontal and diagonal scores
	 * </p>
	 */
	public void drawAnswerAreas(Graphics g) {
		g.setColor(Color.white);
			// answers for horizontal placeholders - shown on left of screen
		g.drawRect(400, 25, 25, 25); 	// first line - at top
		g.fillRect(400, 25, 25, 25);
		g.drawRect(400, 85, 25, 25); 	// second line
		g.fillRect(400, 85, 25, 25);
		g.drawRect(400, 148, 25, 25); 	// third line
		g.fillRect(400, 148, 25, 25);
		g.drawRect(400, 208, 25, 25); 	// fourth line
		g.fillRect(400, 208, 25, 25);
		g.drawRect(400, 270, 25, 25); 	// fifth line
		g.fillRect(400, 270, 25, 25);
		g.drawRect(400, 330, 25, 25); 	// sixth line
		g.fillRect(400, 330, 25, 25);
		g.drawRect(400, 391, 25, 25); 	// seventh line
		g.fillRect(400, 391, 25, 25);
		g.drawRect(400, 452, 25, 25); 	// eighth line - at bottom
		g.fillRect(400, 452, 25, 25);
		// answers for vertical placeholders - shown at bottom of screen
		g.drawRect(23, 502, 24, 24); 	// first line - at left
		g.fillRect(23, 502, 24, 24);
		g.drawRect(65, 502, 24, 24); 	// second line
		g.fillRect(65, 502, 24, 24);
		g.drawRect(120, 502, 25, 25); 	// third line
		g.fillRect(120, 502, 25, 25);
		g.drawRect(163, 502, 25, 25); 	// fourth line
		g.fillRect(163, 502, 25, 25);
		g.drawRect(216, 502, 25, 25); 	// fifth line - at right
		g.fillRect(216, 502, 25, 25);
		g.drawRect(259, 502, 25, 25); 	// sixth line - at right
		g.fillRect(259, 502, 25, 25);
		g.drawRect(313, 502, 25, 25); 	// seventh line - at right
		g.fillRect(313, 502, 25, 25);
		g.drawRect(354, 502, 25, 25); 	// eighth line - at right
		g.fillRect(354, 502, 25, 25);
		// answers for diagonal placeholders - shown in middle of board
		g.drawRect(160, 213, 25, 25); 	// diagonal score area from top left to bottom right
		g.fillRect(160, 213, 25, 25);
		g.drawRect(216, 213, 25, 25); 	// diagonal score area from top right to bottom left
		g.fillRect(216, 213, 25, 25);
	}

	/**
	 * <p>
	 * Draws the red/yellow partitions around board - decoration only
	 * </p>
	 */
	public void drawBorders(Graphics g) {
		g.setColor(Color.red); 			// red line sections
		g.drawLine(470, 5, 470, 560); 		// middle vertical partition
		g.drawLine(472, 5, 472, 560);
		g.drawLine(614, 5, 614, 560); 		// right vertical border
		g.drawLine(616, 3, 616, 562);
		g.drawLine(5, 560, 614, 560); 		// bottom border
		g.drawLine(3, 562, 616, 562);
		g.drawLine(3, 3, 616, 3); 			// top border
		g.drawLine(5, 5, 614, 5);
		g.drawLine(3, 3, 3, 562); 			// left vertical border
		g.drawLine(5, 5, 5, 560);
		g.setColor(Color.yellow); 		// yellow insets to lines
		g.drawLine(471, 5, 471, 561); 		// middle vertical partition
		g.drawLine(615, 5, 615, 561); 		// right vertical border
		g.drawLine(5, 561, 615, 561); 		// bottom border
		g.drawLine(4, 4, 615, 4); 			// top border
		g.drawLine(4, 4, 4, 561); 			// left vertical border
		g.setColor(Color.red);
		g.drawLine(156, 199, 244, 315); 	// diagonal - top left to bottom right
		g.drawLine(244, 199, 156, 314); 	// diagonal top right to bottom left
										// diagonal score labels in center of board
		g.drawString("Diagonal", 127, 260);
		g.drawString("Line Scores", 222, 260);
	}

	/**
	 * <p>
	 * Draws stockpile place on board - this is where all dominoes will
	 * originate from
	 * </p>
	 */
	public void drawStockpile(Graphics g) {
		g.setColor(Color.red);
		g.drawRect(505, 225, 80, 45);
		drawButtons(g);
	}

	/**
	 * <p>
	 * Draws control buttons on right side of playing board
	 * </p>
	 */
	public void drawButtons(Graphics g) {
		g.setColor(Color.blue); 		// draws blue borders around buttons
		g.drawRect(510, 350, 65, 20);
		g.drawRect(510, 410, 65, 20);
		g.drawRect(510, 470, 65, 20);
		g.setColor(Color.green); 		// fills button with green middle
		g.fillRect(511, 351, 64, 19);
		g.fillRect(511, 411, 64, 19);
		g.fillRect(511, 471, 64, 19);
		g.setColor(Color.black); 		// draw text on buttons
		g.drawString("Easy Start", 515, 365);
		g.drawString("Solution", 519, 425);
		g.drawString("Clear", 526, 485);
	}

	/**
	 * <p>
	 * Method contains all calls to paint each domino on board
	 * </p>
	 */
	public void paintDominoes(Graphics g) {
		drawDomino66(g);
		drawDomino64(g);
		drawDomino35(g);
		drawDomino56(g);
		drawDomino20(g);
		drawDominoZ3(g);
		drawDominoDZ(g);
		drawDomino10(g);
		drawDominoZ4(g);
		drawDomino55(g);
		drawDomino45(g);
		drawDomino44(g);
		drawDomino43(g);
		drawDomino63(g);
		drawDomino33(g);
		drawDomino62(g);
		drawDomino52(g);
		drawDomino14(g);
		drawDomino24(g);
		drawDomino32(g);
		drawDomino22(g);
		drawDomino50(g);
		drawDomino61(g);
		drawDomino15(g);
		drawDomino11(g);
		drawDominoZ6(g);
		drawDomino21(g);
		drawDomino13(g);
	}

	// ///////////////////////////////////////////////////////////////////////////
	// END OF DRAW FUNCTIONS FOR PLAYING BOARD
	// ///////////////////////////////////////////////////////////////////////////

	/**
	 * <p>
	 * Method initializes each domino as a new Point()
	 * </p>
	 */
	public void initializeDominoes() {
		domino66 = new Point(505, 225);
		domino64 = new Point(505, 225);
		domino35 = new Point(505, 225);
		domino56 = new Point(505, 225);
		domino20 = new Point(505, 225);
		dominoZ3 = new Point(505, 225);
		dominoDZ = new Point(505, 225);
		domino10 = new Point(505, 225);
		dominoZ4 = new Point(505, 225);
		domino55 = new Point(505, 225);
		domino45 = new Point(505, 225);
		domino44 = new Point(505, 225);
		domino43 = new Point(505, 225);
		domino63 = new Point(505, 225);
		domino33 = new Point(505, 225);
		domino62 = new Point(505, 225);
		domino52 = new Point(505, 225);
		domino14 = new Point(505, 225);
		domino24 = new Point(505, 225);
		domino32 = new Point(505, 225);
		domino50 = new Point(505, 225);
		domino22 = new Point(505, 225);
		domino61 = new Point(505, 225);
		domino15 = new Point(505, 225);
		domino11 = new Point(505, 225);
		dominoZ6 = new Point(505, 225);
		domino21 = new Point(505, 225);
		domino13 = new Point(505, 225);
	}

	/**
	 * <p>
	 * Clears line scores and redraws all dominoes on stockpile
	 * </p>
	 */
	public void clearBoard() {
		initializeDominoes(); 	// call to redraw dominoes on stockpile
		clearLineScores(); 		// call to reset all line scores
	}

	/**
	 * <p>
	 * Reset boolean values relating to user attempts
	 * </p>
	 */
	public void resetAttempt() {
		showAttemptedMessage = false;
		removeAttemptedMessage = false;
	}

	/**
	 * <p>
	 * Method called from mouse pressed and released methods - method contains
	 * all board area mappings
	 * </p>
	 * <p>
	 * All areas on board mapped out into selection areas processing for control
	 * of pieces to originate from nested if statements
	 * </p>
	 */
	public void getMousePoint(MouseEvent e) {

		getSelectedDomino(e); 	// call to read value of domino selected

		// 510,350,x+65,y+20 - coordinates of "Easy Start" button
		if ((mouseX > 510) && (mouseX < 575) && (mouseY > 350)
				&& (mouseY < 370)) {
			clearBoard(); 			// call to reset board and scores
			displayEasyStart(); 	// call to display 'Easy Start' option
			showEasyStartText = true;
			showSolutionText = false;
		}
		// 510,410,x+65,y+20 - coordinates of "Solution" button
		if ((mouseX > 510) && (mouseX < 575) && (mouseY > 410)
				&& (mouseY < 430)) {
			showAttemptedMessage = false;
			showSolutionText = true;
			showEasyStartText = false;
			checkAttempted(); 		// call to check if attempt has been made

		}
		// 510,470,x+65,y+20 - coordinates of "Clear" button
		if ((mouseX > 510) && (mouseX < 575) && (mouseY > 470)
				&& (mouseY < 490)) {
			clearBoard(); 			// call to reset board and scores
			showEasyStartText = false;
			showSolutionText = false;
		}
		// 491,501,x+109,y+39 - coordinates of 'let me try' button
		if ((mouseX > 491) && (mouseX < 600) && (mouseY > 501)
				&& (mouseY < 540)) {
			if (tryButtonEnabled == true) {
				clearBoard();		// call to reset board and scores
				removeAttemptedMessage = true;
				showSolutionText = false;
			}
		}
		if ((mouseX > 614) && (mouseX < 616) && (mouseY > 4) && (mouseY < 6)) {
			clearBoard(); 	// microdot to allow programmer access to solution
			try { 	// dot situated at top right corner of screen on red/yellow border
				showSolution();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
				// all board area mappings
									// first horizontal row
		if ((mouseX > 15) && (mouseX < 95) && (mouseY > 15) && (mouseY < 60)) {
			area01value_left = currentValue_left;
			area01value_right = currentValue_right;
			boardAreaValues_left[0] = area01value_left;
			boardAreaValues_right[0] = area01value_right;
		} else if ((mouseX > 110) && (mouseX < 190) && (mouseY > 15)
				&& (mouseY < 60)) {
			area02value_left = currentValue_left;
			area02value_right = currentValue_right;
			boardAreaValues_left[1] = area02value_left;
			boardAreaValues_right[1] = area02value_right;
		} else if ((mouseX > 207) && (mouseX < 287) && (mouseY > 15)
				&& (mouseY < 60)) {
			area03value_left = currentValue_left;
			area03value_right = currentValue_right;
			boardAreaValues_left[2] = area03value_left;
			boardAreaValues_right[2] = area03value_right;
		} else if ((mouseX > 303) && (mouseX < 383) && (mouseY > 15)
				&& (mouseY < 60)) {
			area04value_left = currentValue_left;
			area04value_right = currentValue_right;
			boardAreaValues_left[3] = area04value_left;
			boardAreaValues_right[3] = area04value_right;
									// second horizontal row
		} else if ((mouseX > 15) && (mouseX < 95) && (mouseY > 75)
				&& (mouseY < 120)) {
			area05value_left = currentValue_left;
			boardAreaValues_left[4] = area05value_left;
			area05value_right = currentValue_right;
			boardAreaValues_right[4] = area05value_right;
		} else if ((mouseX > 110) && (mouseX < 190) && (mouseY > 75)
				&& (mouseY < 120)) {
			area06value_left = currentValue_left;
			boardAreaValues_left[5] = area06value_left;
			area06value_right = currentValue_right;
			boardAreaValues_right[5] = area06value_right;
		} else if ((mouseX > 207) && (mouseX < 287) && (mouseY > 75)
				&& (mouseY < 120)) {
			area07value_left = currentValue_left;
			boardAreaValues_left[6] = area07value_left;
			area07value_right = currentValue_right;
			boardAreaValues_right[6] = area07value_right;
		} else if ((mouseX > 303) && (mouseX < 383) && (mouseY > 75)
				&& (mouseY < 120)) {
			area08value_left = currentValue_left;
			boardAreaValues_left[7] = area08value_left;
			area08value_right = currentValue_right;
			boardAreaValues_right[7] = area08value_right;
									// third horizontal row
		} else if ((mouseX > 15) && (mouseX < 95) && (mouseY > 138)
				&& (mouseY < 182)) {
			area09value_left = currentValue_left;
			boardAreaValues_left[8] = area09value_left;
			area09value_right = currentValue_right;
			boardAreaValues_right[8] = area09value_right;
		} else if ((mouseX > 110) && (mouseX < 190) && (mouseY > 138)
				&& (mouseY < 182)) {
			area10value_left = currentValue_left;
			boardAreaValues_left[9] = area10value_left;
			area10value_right = currentValue_right;
			boardAreaValues_right[9] = area10value_right;
		} else if ((mouseX > 207) && (mouseX < 287) && (mouseY > 138)
				&& (mouseY < 182)) {
			area11value_left = currentValue_left;
			boardAreaValues_left[10] = area11value_left;
			area11value_right = currentValue_right;
			boardAreaValues_right[10] = area11value_right;
		} else if ((mouseX > 303) && (mouseX < 383) && (mouseY > 138)
				&& (mouseY < 182)) {
			area12value_left = currentValue_left;
			boardAreaValues_left[11] = area12value_left;
			area12value_right = currentValue_right;
			boardAreaValues_right[11] = area12value_right;
									// fourth horizontal row
		} else if ((mouseX > 15) && (mouseX < 95) && (mouseY > 198)
				&& (mouseY < 241)) {
			area13value_left = currentValue_left;
			boardAreaValues_left[12] = area13value_left;
			area13value_right = currentValue_right;
			boardAreaValues_right[12] = area13value_right;
		} else if ((mouseX > 303) && (mouseX < 383) && (mouseY > 198)
				&& (mouseY < 241)) {
			area14value_left = currentValue_left;
			boardAreaValues_left[13] = area14value_left;
			area14value_right = currentValue_right;
			boardAreaValues_right[13] = area14value_right;
									// fifth horizontal row
		} else if ((mouseX > 15) && (mouseX < 95) && (mouseY > 260)
				&& (mouseY < 303)) {
			area15value_left = currentValue_left;
			boardAreaValues_left[14] = area15value_left;
			area15value_right = currentValue_right;
			boardAreaValues_right[14] = area15value_right;
		} else if ((mouseX > 303) && (mouseX < 383) && (mouseY > 260)
				&& (mouseY < 303)) {
			area16value_left = currentValue_left;
			boardAreaValues_left[15] = area16value_left;
			area16value_right = currentValue_right;
			boardAreaValues_right[15] = area16value_right;
									// sixth horizontal row
		} else if ((mouseX > 15) && (mouseX < 95) && (mouseY > 320)
				&& (mouseY < 364)) {
			area17value_left = currentValue_left;
			boardAreaValues_left[16] = area17value_left;
			area17value_right = currentValue_right;
			boardAreaValues_right[16] = area17value_right;
		} else if ((mouseX > 110) && (mouseX < 190) && (mouseY > 320)
				&& (mouseY < 364)) {
			area18value_left = currentValue_left;
			boardAreaValues_left[17] = area18value_left;
			area18value_right = currentValue_right;
			boardAreaValues_right[17] = area18value_right;
		} else if ((mouseX > 207) && (mouseX < 287) && (mouseY > 320)
				&& (mouseY < 364)) {
			area19value_left = currentValue_left;
			boardAreaValues_left[18] = area19value_left;
			area19value_right = currentValue_right;
			boardAreaValues_right[18] = area19value_right;
		} else if ((mouseX > 303) && (mouseX < 383) && (mouseY > 320)
				&& (mouseY < 364)) {
			area20value_left = currentValue_left;
			boardAreaValues_left[19] = area20value_left;
			area20value_right = currentValue_right;
			boardAreaValues_right[19] = area20value_right;
									// seventh horizontal row
		} else if ((mouseX > 15) && (mouseX < 95) && (mouseY > 381)
				&& (mouseY < 425)) {
			area21value_left = currentValue_left;
			boardAreaValues_left[20] = area21value_left;
			area21value_right = currentValue_right;
			boardAreaValues_right[20] = area21value_right;
		} else if ((mouseX > 110) && (mouseX < 190) && (mouseY > 381)
				&& (mouseY < 425)) {
			area22value_left = currentValue_left;
			boardAreaValues_left[21] = area22value_left;
			area22value_right = currentValue_right;
			boardAreaValues_right[21] = area22value_right;
		} else if ((mouseX > 207) && (mouseX < 287) && (mouseY > 381)
				&& (mouseY < 425)) {
			area23value_left = currentValue_left;
			boardAreaValues_left[22] = area23value_left;
			area23value_right = currentValue_right;
			boardAreaValues_right[22] = area23value_right;
		} else if ((mouseX > 303) && (mouseX < 383) && (mouseY > 381)
				&& (mouseY < 425)) {
			area24value_left = currentValue_left;
			boardAreaValues_left[23] = area24value_left;
			area24value_right = currentValue_right;
			boardAreaValues_right[23] = area24value_right;
									// eighth horizontal row
		} else if ((mouseX > 15) && (mouseX < 95) && (mouseY > 442)
				&& (mouseY < 486)) {
			area25value_left = currentValue_left;
			boardAreaValues_left[24] = area25value_left;
			area25value_right = currentValue_right;
			boardAreaValues_right[24] = area25value_right;
		} else if ((mouseX > 110) && (mouseX < 190) && (mouseY > 442)
				&& (mouseY < 486)) {
			area26value_left = currentValue_left;
			boardAreaValues_left[25] = area26value_left;
			area26value_right = currentValue_right;
			boardAreaValues_right[25] = area26value_right;
		} else if ((mouseX > 207) && (mouseX < 287) && (mouseY > 442)
				&& (mouseY < 486)) {
			area27value_left = currentValue_left;
			boardAreaValues_left[26] = area27value_left;
			area27value_right = currentValue_right;
			boardAreaValues_right[26] = area27value_right;
		} else if ((mouseX > 303) && (mouseX < 383) && (mouseY > 442)
				&& (mouseY < 486)) {
			area28value_left = currentValue_left;
			boardAreaValues_left[27] = area28value_left;
			area28value_right = currentValue_right;
			boardAreaValues_right[27] = area28value_right;
		} else {
		}
	}

	/**
	 * <p>
	 * Sets left side value of current domino selected
	 * </p>
	 */
	public int setValue_left(int domValue_left) {
		currentValue_left = domValue_left;
		return currentValue_left;
	}

	/**
	 * <p>
	 * Sets right side value of current domino selected
	 * </p>
	 */
	public int setValue_right(int domValue_right) {
		currentValue_right = domValue_right;
		return currentValue_right;
	}

	/**
	 * <p>
	 * Method retrieves right side value of current domino selected
	 * </p>
	 */
	public int getValue_left() {
		currentValue_left = 0;
		if (select == 100)
			domValue_left = 0;
		if (select == 10)
			domValue_left = 1;
		if (select == 20)
			domValue_left = 2;
		if (select == 3)
			domValue_left = 0;
		if (select == 4)
			domValue_left = 0;
		if (select == 50)
			domValue_left = 5;
		if (select == 6)
			domValue_left = 0;
		if (select == 11)
			domValue_left = 1;
		if (select == 21)
			domValue_left = 2;
		if (select == 13)
			domValue_left = 1;
		if (select == 14)
			domValue_left = 1;
		if (select == 15)
			domValue_left = 1;
		if (select == 61)
			domValue_left = 6;
		if (select == 22)
			domValue_left = 2;
		if (select == 32)
			domValue_left = 3;
		if (select == 24)
			domValue_left = 2;
		if (select == 52)
			domValue_left = 5;
		if (select == 62)
			domValue_left = 6;
		if (select == 33)
			domValue_left = 3;
		if (select == 43)
			domValue_left = 4;
		if (select == 35)
			domValue_left = 3;
		if (select == 63)
			domValue_left = 6;
		if (select == 44)
			domValue_left = 4;
		if (select == 45)
			domValue_left = 4;
		if (select == 64)
			domValue_left = 6;
		if (select == 55)
			domValue_left = 5;
		if (select == 56)
			domValue_left = 5;
		if (select == 66)
			domValue_left = 6;
		setValue_left(domValue_left);
		return domValue_left;
	}

	/**
	 * <p>
	 * Method retrieves right side value of current domino selected
	 * </p>
	 */
	public int getValue_right() {
		currentValue_right = 0;
		if (select == 100)
			domValue_right = 0;
		if (select == 10)
			domValue_right = 0;
		if (select == 20)
			domValue_right = 0;
		if (select == 3)
			domValue_right = 3;
		if (select == 4)
			domValue_right = 4;
		if (select == 50)
			domValue_right = 0;
		if (select == 6)
			domValue_right = 6;
		if (select == 11)
			domValue_right = 1;
		if (select == 21)
			domValue_right = 1;
		if (select == 13)
			domValue_right = 3;
		if (select == 14)
			domValue_right = 4;
		if (select == 15)
			domValue_right = 5;
		if (select == 61)
			domValue_right = 1;
		if (select == 22)
			domValue_right = 2;
		if (select == 32)
			domValue_right = 2;
		if (select == 24)
			domValue_right = 4;
		if (select == 52)
			domValue_right = 2;
		if (select == 62)
			domValue_right = 2;
		if (select == 33)
			domValue_right = 3;
		if (select == 43)
			domValue_right = 3;
		if (select == 35)
			domValue_right = 5;
		if (select == 63)
			domValue_right = 3;
		if (select == 44)
			domValue_right = 4;
		if (select == 45)
			domValue_right = 5;
		if (select == 64)
			domValue_right = 4;
		if (select == 55)
			domValue_right = 5;
		if (select == 56)
			domValue_right = 6;
		if (select == 66)
			domValue_right = 6;
		setValue_right(domValue_right);
		return domValue_right;
	}

	/**
	 * <p>
	 * Method called when dominoes are selected
	 * </p>
	 */
	public void getSelectedDomino(MouseEvent e) {
		if (mousePoint.x > domino66.x && mousePoint.x < domino66.x + 20
				&& mousePoint.y > domino66.y && mousePoint.y < domino66.y + 20) {
			select = 66;
			completed = true;	// completed message allowed when second last
								// domino is taken from stockpile
		}
		if (mousePoint.x > domino64.x && mousePoint.x < domino64.x + 20
				&& mousePoint.y > domino64.y && mousePoint.y < domino64.y + 20)
			select = 64;
		if (mousePoint.x > domino35.x && mousePoint.x < domino35.x + 80
				&& mousePoint.y > domino35.y && mousePoint.y < domino35.y + 45)
			select = 35;
		if (mousePoint.x > domino56.x && mousePoint.x < domino56.x + 80
				&& mousePoint.y > domino56.y && mousePoint.y < domino56.y + 45)
			select = 56;
		if (mousePoint.x > domino20.x && mousePoint.x < domino20.x + 80
				&& mousePoint.y > domino20.y && mousePoint.y < domino20.y + 45)
			select = 20;
		if (mousePoint.x > dominoZ3.x && mousePoint.x < dominoZ3.x + 80
				&& mousePoint.y > dominoZ3.y && mousePoint.y < dominoZ3.y + 45)
			select = 3;
		if (mousePoint.x > dominoDZ.x && mousePoint.x < dominoDZ.x + 80
				&& mousePoint.y > dominoDZ.y && mousePoint.y < dominoDZ.y + 45)
			select = 100;
		if (mousePoint.x > domino10.x && mousePoint.x < domino10.x + 80
				&& mousePoint.y > domino10.y && mousePoint.y < domino10.y + 45)
			select = 10;
		if (mousePoint.x > dominoZ4.x && mousePoint.x < dominoZ4.x + 80
				&& mousePoint.y > dominoZ4.y && mousePoint.y < dominoZ4.y + 45)
			select = 4;
		if (mousePoint.x > domino55.x && mousePoint.x < domino55.x + 80
				&& mousePoint.y > domino55.y && mousePoint.y < domino55.y + 45)
			select = 55;
		if (mousePoint.x > domino45.x && mousePoint.x < domino45.x + 80
				&& mousePoint.y > domino45.y && mousePoint.y < domino45.y + 45)
			select = 45;
		if (mousePoint.x > domino44.x && mousePoint.x < domino44.x + 80
				&& mousePoint.y > domino44.y && mousePoint.y < domino44.y + 45)
			select = 44;
		if (mousePoint.x > domino43.x && mousePoint.x < domino43.x + 80
				&& mousePoint.y > domino43.y && mousePoint.y < domino43.y + 45)
			select = 43;
		if (mousePoint.x > domino63.x && mousePoint.x < domino63.x + 80
				&& mousePoint.y > domino63.y && mousePoint.y < domino63.y + 45)
			select = 63;
		if (mousePoint.x > domino33.x && mousePoint.x < domino33.x + 80
				&& mousePoint.y > domino33.y && mousePoint.y < domino33.y + 45)
			select = 33;
		if (mousePoint.x > domino62.x && mousePoint.x < domino62.x + 80
				&& mousePoint.y > domino62.y && mousePoint.y < domino62.y + 45)
			select = 62;
		if (mousePoint.x > domino52.x && mousePoint.x < domino52.x + 80
				&& mousePoint.y > domino52.y && mousePoint.y < domino52.y + 45)
			select = 52;
		if (mousePoint.x > domino14.x && mousePoint.x < domino14.x + 80
				&& mousePoint.y > domino14.y && mousePoint.y < domino14.y + 45)
			select = 14;
		if (mousePoint.x > domino24.x && mousePoint.x < domino24.x + 80
				&& mousePoint.y > domino24.y && mousePoint.y < domino24.y + 45)
			select = 24;
		if (mousePoint.x > domino32.x && mousePoint.x < domino32.x + 80
				&& mousePoint.y > domino32.y && mousePoint.y < domino32.y + 45)
			select = 32;
		if (mousePoint.x > domino22.x && mousePoint.x < domino22.x + 80
				&& mousePoint.y > domino22.y && mousePoint.y < domino22.y + 45)
			select = 22;
		if (mousePoint.x > domino50.x && mousePoint.x < domino50.x + 80
				&& mousePoint.y > domino50.y && mousePoint.y < domino50.y + 45)
			select = 50;
		if (mousePoint.x > domino61.x && mousePoint.x < domino61.x + 80
				&& mousePoint.y > domino61.y && mousePoint.y < domino61.y + 45)
			select = 61;
		if (mousePoint.x > domino15.x && mousePoint.x < domino15.x + 80
				&& mousePoint.y > domino15.y && mousePoint.y < domino15.y + 45)
			select = 15;
		if (mousePoint.x > domino11.x && mousePoint.x < domino11.x + 80
				&& mousePoint.y > domino11.y && mousePoint.y < domino11.y + 45)
			select = 11;
		if (mousePoint.x > dominoZ6.x && mousePoint.x < dominoZ6.x + 80
				&& mousePoint.y > dominoZ6.y && mousePoint.y < dominoZ6.y + 45)
			select = 6;
		if (mousePoint.x > domino21.x && mousePoint.x < domino21.x + 80
				&& mousePoint.y > domino21.y && mousePoint.y < domino21.y + 45)
			select = 21;
		if (mousePoint.x > domino13.x && mousePoint.x < domino13.x + 80
				&& mousePoint.y > domino13.y && mousePoint.y < domino13.y + 45)
			select = 13;
	}

	/**
	 * <p>
	 * Method fills 6 areas of board for puzzle 'easy start'
	 * </p>
	 */
	public void displayEasyStart() {
		assignArea_a4();
		assignArea_a1();
		assignArea_d1();
		assignArea_d4();
		assignArea_h1();
		assignArea_h4();

	}

	/**
	 * <p>
	 * Method fills all areas of board to reveal solution
	 * </p>
	 */
	public void showSolution() throws InterruptedException {
		assignArea_a1();
		assignArea_a2();
		assignArea_a3();
		assignArea_a4();
		assignArea_b1();
		assignArea_b2();
		assignArea_b3();
		assignArea_b4();
		assignArea_c1();
		assignArea_c2();
		assignArea_c3();
		assignArea_c4();
		assignArea_d1();
		assignArea_d4();
		assignArea_e1();
		assignArea_e4();
		assignArea_f1();
		assignArea_f2();
		assignArea_f3();
		assignArea_f4();
		assignArea_g1();
		assignArea_g2();
		assignArea_g3();
		assignArea_g4();
		assignArea_h1();
		assignArea_h2();
		assignArea_h3();
		assignArea_h4();
		completed = false;
	}

	// //////////////////////////////////////////////////////////////////////////////////
	// methods below deal with all mouse events
	// /////////////////////////////////////////////////////////////////////////////////

	public void mousePressed(MouseEvent e) {
		mouseX = e.getX(); 		// when mouse pressed retrieve
		mouseY = e.getY(); 		// x&y co-ords for processing
		mousePoint = e.getPoint();
		getMousePoint(e);
		getValues();
	}

	/**
	 * <p>
	 * Contains calls to get left and right side values
	 * </p>
	 */
	public void getValues() {
		getValue_left();
		getValue_right();
	}

	/**
	 * <p>
	 * Mousedragged event allows selection of variable select to determine
	 * domino selected
	 * </p>
	 */
	public void mouseDragged(MouseEvent e) {
		mousePoint = e.getPoint();
		if (select == 66)
			domino66 = mousePoint;
		if (select == 64)
			domino64 = mousePoint;
		if (select == 35)
			domino35 = mousePoint;
		if (select == 56)
			domino56 = mousePoint;
		if (select == 20)
			domino20 = mousePoint;
		if (select == 3)
			dominoZ3 = mousePoint;
		if (select == 100)
			dominoDZ = mousePoint;
		if (select == 10)
			domino10 = mousePoint;
		if (select == 4)
			dominoZ4 = mousePoint;
		if (select == 55)
			domino55 = mousePoint;
		if (select == 45)
			domino45 = mousePoint;
		if (select == 44)
			domino44 = mousePoint;
		if (select == 43)
			domino43 = mousePoint;
		if (select == 63)
			domino63 = mousePoint;
		if (select == 33)
			domino33 = mousePoint;
		if (select == 62)
			domino62 = mousePoint;
		if (select == 52)
			domino52 = mousePoint;
		if (select == 14)
			domino14 = mousePoint;
		if (select == 24)
			domino24 = mousePoint;
		if (select == 32)
			domino32 = mousePoint;
		if (select == 50)
			domino50 = mousePoint;
		if (select == 22)
			domino22 = mousePoint;
		if (select == 61)
			domino61 = mousePoint;
		if (select == 15)
			domino15 = mousePoint;
		if (select == 11)
			domino11 = mousePoint;
		if (select == 6)
			dominoZ6 = mousePoint;
		if (select == 21)
			domino21 = mousePoint;
		if (select == 13)
			domino13 = mousePoint;
		repaint();
	}

	/**
	 * <p>
	 * Method triggers calls to get coordinates and also clears all variables<br />
	 * relating to current domino selected
	 * </p>
	 */
	public void mouseReleased(MouseEvent e) {
		mouseX = e.getX(); 		// when mouse pressed retrieve
		mouseY = e.getY(); 		// x&y co-ords for processing
		mousePoint = e.getPoint();
		getMousePoint(e);
		repaint();
		select = 0;
		domValue_left = 0;
		currentValue_left = 0;
		domValue_right = 0;
		currentValue_right = 0;
	}

	// mouse events added for interface
	public void mouseEntered(MouseEvent e) {
	}

	public void mouseOver(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e) {
	}

	public void mouseClicked(MouseEvent e) {
	}

	/**
	 * <p>
	 * Allows for placing of domino and setting scores for autocomplete methods
	 * in this area
	 * <p>
	 */
	public void assignArea_a1() { // first horizontal line solution
		domino22.x = 20;
		domino22.y = 20;
		area01value_left = 2;
		area01value_right = 2;
		boardAreaValues_left[0] = area01value_left;
		boardAreaValues_right[0] = area01value_right;
	}

	/**
	 * <p>
	 * Allows for placing of domino and setting scores for autocomplete methods
	 * in this area
	 * <p>
	 */
	public void assignArea_a2() {
		domino13.x = 115;
		domino13.y = 20;
		area02value_left = 1;
		area02value_right = 3;
		boardAreaValues_left[1] = area02value_left;
		boardAreaValues_right[1] = area02value_right;
	}

	/**
	 * <p>
	 * Allows for placing of domino and setting scores for autocomplete methods
	 * in this area
	 * <p>
	 */
	public void assignArea_a3() {
		domino64.x = 212;
		domino64.y = 20;
		area03value_left = 6;
		area03value_right = 4;
		boardAreaValues_left[2] = area03value_left;
		boardAreaValues_right[2] = area03value_right;
	}

	/**
	 * <p>
	 * Allows for placing of domino and setting scores for autocomplete methods
	 * in this area
	 * <p>
	 */
	public void assignArea_a4() {
		dominoZ3.x = 308;
		dominoZ3.y = 20;
		area04value_left = 0;
		area04value_right = 3;
		boardAreaValues_left[3] = area04value_left;
		boardAreaValues_right[3] = area04value_right;
	}

	/**
	 * <p>
	 * Allows for placing of domino and setting scores for autocomplete methods
	 * in this area
	 * <p>
	 */
	public void assignArea_b1() { // second horizontal line solution
		domino14.x = 20;
		domino14.y = 80;
		area05value_left = 1;
		area05value_right = 4;
		boardAreaValues_left[4] = area05value_left;
		boardAreaValues_right[4] = area05value_right;
	}

	/**
	 * <p>
	 * Allows for placing of domino and setting scores for autocomplete methods
	 * in this area
	 * <p>
	 */
	public void assignArea_b2() {
		domino15.x = 115;
		domino15.y = 80;
		area06value_left = 1;
		area06value_right = 5;
		boardAreaValues_left[5] = area06value_left;
		boardAreaValues_right[5] = area06value_right;
	}

	/**
	 * <p>
	 * Allows for placing of domino and setting scores for autocomplete methods
	 * in this area
	 * <p>
	 */
	public void assignArea_b3() {
		domino62.x = 212;
		domino62.y = 80;
		area07value_left = 6;
		area07value_right = 2;
		boardAreaValues_left[6] = area07value_left;
		boardAreaValues_right[6] = area07value_right;
	}

	/**
	 * <p>
	 * Allows for placing of domino and setting scores for autocomplete methods
	 * in this area
	 * <p>
	 */
	public void assignArea_b4() {
		domino20.x = 308;
		domino20.y = 80;
		area08value_left = 2;
		area08value_right = 0;
		boardAreaValues_left[7] = area08value_left;
		boardAreaValues_right[7] = area08value_right;
	}

	/**
	 * <p>
	 * Allows for placing of domino and setting scores for autocomplete methods
	 * in this area
	 * <p>
	 */
	public void assignArea_c1() { // third horizontal line solution
		domino10.x = 20;
		domino10.y = 142;
		area09value_left = 1;
		area09value_right = 0;
		boardAreaValues_left[8] = area09value_left;
		boardAreaValues_right[8] = area09value_right;
	}

	/**
	 * <p>
	 * Allows for placing of domino and setting scores for autocomplete methods
	 * in this area
	 * <p>
	 */
	public void assignArea_c2() {
		domino44.x = 115;
		domino44.y = 142;
		area10value_left = 4;
		area10value_right = 4;
		boardAreaValues_left[9] = area10value_left;
		boardAreaValues_right[9] = area10value_right;
	}

	/**
	 * <p>
	 * Allows for placing of domino and setting scores for autocomplete methods
	 * in this area
	 * <p>
	 */
	public void assignArea_c3() {
		domino24.x = 212;
		domino24.y = 142;
		area11value_left = 2;
		area11value_right = 4;
		boardAreaValues_left[10] = area11value_left;
		boardAreaValues_right[10] = area11value_right;
	}

	/**
	 * <p>
	 * Allows for placing of domino and setting scores for autocomplete methods
	 * in this area
	 * <p>
	 */
	public void assignArea_c4() {
		domino33.x = 308;
		domino33.y = 142;
		area12value_left = 3;
		area12value_right = 3;
		boardAreaValues_left[11] = area12value_left;
		boardAreaValues_right[11] = area12value_right;
	}

	/**
	 * <p>
	 * Allows for placing of domino and setting scores for autocomplete methods
	 * in this area
	 * <p>
	 */
	public void assignArea_d1() { // fourth horizontal line solution
		domino56.x = 20;
		domino56.y = 203;
		area13value_left = 5;
		area13value_right = 6;
		boardAreaValues_left[12] = area13value_left;
		boardAreaValues_right[12] = area13value_right;
	}

	/**
	 * <p>
	 * Allows for placing of domino and setting scores for autocomplete methods
	 * in this area
	 * <p>
	 */
	public void assignArea_d4() {
		domino55.x = 308;
		domino55.y = 203;
		area14value_left = 5;
		area14value_right = 5;
		boardAreaValues_left[13] = area14value_left;
		boardAreaValues_right[13] = area14value_right;
	}

	/**
	 * <p>
	 * Allows for placing of domino and setting scores for autocomplete methods
	 * in this area
	 * <p>
	 */
	public void assignArea_e1() { // fifth horizontal line solution
		domino45.x = 20;
		domino45.y = 265;
		area15value_left = 4;
		area15value_right = 5;
		boardAreaValues_left[14] = area15value_left;
		boardAreaValues_right[14] = area15value_right;
	}

	/**
	 * <p>
	 * Allows for placing of domino and setting scores for autocomplete methods
	 * in this area
	 * <p>
	 */
	public void assignArea_e4() {
		domino66.x = 308;
		domino66.y = 265;
		area16value_left = 6;
		area16value_right = 6;
		boardAreaValues_left[15] = area16value_left;
		boardAreaValues_right[15] = area16value_right;
	}

	/**
	 * <p>
	 * Allows for placing of domino and setting scores for autocomplete methods
	 * in this area
	 * <p>
	 */
	public void assignArea_f1() { // sixth horizontal line solution
		domino11.x = 20;
		domino11.y = 325;
		area17value_left = 1;
		area17value_right = 1;
		boardAreaValues_left[16] = area17value_left;
		boardAreaValues_right[16] = area17value_right;
	}

	/**
	 * <p>
	 * Allows for placing of domino and setting scores for autocomplete methods
	 * in this area
	 * <p>
	 */
	public void assignArea_f2() {
		domino63.x = 115;
		domino63.y = 325;
		area18value_left = 6;
		area18value_right = 3;
		boardAreaValues_left[17] = area18value_left;
		boardAreaValues_right[17] = area18value_right;
	}

	/**
	 * <p>
	 * Allows for placing of domino and setting scores for autocomplete methods
	 * in this area
	 * <p>
	 */
	public void assignArea_f3() {
		dominoZ6.x = 212;
		dominoZ6.y = 325;
		area19value_left = 0;
		area19value_right = 6;
		boardAreaValues_left[18] = area19value_left;
		boardAreaValues_right[18] = area19value_right;
	}

	/**
	 * <p>
	 * Allows for placing of domino and setting scores for autocomplete methods
	 * in this area
	 * <p>
	 */
	public void assignArea_f4() {
		dominoZ4.x = 308;
		dominoZ4.y = 325;
		area20value_left = 0;
		area20value_right = 4;
		boardAreaValues_left[19] = area20value_left;
		boardAreaValues_right[19] = area20value_right;
	}

	/**
	 * <p>
	 * Allows for placing of domino and setting scores for autocomplete methods
	 * in this area
	 * <p>
	 */
	public void assignArea_g1() { // seventh horizontal line solution
		domino21.x = 20;
		domino21.y = 386;
		area21value_left = 2;
		area21value_right = 1;
		boardAreaValues_left[20] = area21value_left;
		boardAreaValues_right[20] = area21value_right;
	}

	/**
	 * <p>
	 * Allows for placing of domino and setting scores for autocomplete methods
	 * in this area
	 * <p>
	 */
	public void assignArea_g2() {
		domino35.x = 115;
		domino35.y = 386;
		area22value_left = 3;
		area22value_right = 5;
		boardAreaValues_left[21] = area22value_left;
		boardAreaValues_right[21] = area22value_right;
	}

	/**
	 * <p>
	 * Allows for placing of domino and setting scores for autocomplete methods
	 * in this area
	 * <p>
	 */
	public void assignArea_g3() {
		domino32.x = 212;
		domino32.y = 386;
		area23value_left = 3;
		area23value_right = 2;
		boardAreaValues_left[22] = area23value_left;
		boardAreaValues_right[22] = area23value_right;
	}

	/**
	 * <p>
	 * Allows for placing of domino and setting scores for autocomplete methods
	 * in this area
	 * <p>
	 */
	public void assignArea_g4() {
		domino50.x = 308;
		domino50.y = 386;
		area24value_left = 5;
		area24value_right = 0;
		boardAreaValues_left[23] = area24value_left;
		boardAreaValues_right[23] = area24value_right;
	}

	/**
	 * <p>
	 * Allows for placing of domino and setting scores for autocomplete methods
	 * in this area
	 * <p>
	 */
	public void assignArea_h1() { // eighth horizontal line solution
		domino52.x = 20;
		domino52.y = 447;
		area25value_left = 5;
		area25value_right = 2;
		boardAreaValues_left[24] = area25value_left;
		boardAreaValues_right[24] = area25value_right;
	}

	/**
	 * <p>
	 * Allows for placing of domino and setting scores for autocomplete methods
	 * in this area
	 * <p>
	 */
	public void assignArea_h2() {
		domino61.x = 115;
		domino61.y = 447;
		area26value_left = 6;
		area26value_right = 1;
		boardAreaValues_left[25] = area26value_left;
		boardAreaValues_right[25] = area26value_right;
	}

	/**
	 * <p>
	 * Allows for placing of domino and setting scores for autocomplete methods
	 * in this area
	 * <p>
	 */
	public void assignArea_h3() {
		domino43.x = 212;
		domino43.y = 447;
		area27value_left = 4;
		area27value_right = 3;
		boardAreaValues_left[26] = area27value_left;
		boardAreaValues_right[26] = area27value_right;
	}

	/**
	 * <p>
	 * Allows for placing of domino and setting scores for autocomplete methods
	 * in this area
	 * <p>
	 */
	public void assignArea_h4() {
		dominoDZ.x = 308;
		dominoDZ.y = 447;
		area28value_left = 0;
		area28value_right = 0;
		boardAreaValues_left[27] = area28value_left;
		boardAreaValues_right[27] = area28value_right;
	}

	/**
	 * <p>
	 * Method counts values of all line totals and prints to answer areas
	 * </p>
	 */
	public void countLineValues(Graphics g) {
		// horizontal lines
		HL01value = area01value_left + area01value_right + area02value_left
				+ area02value_right + area03value_left + area03value_right
				+ area04value_left + area04value_right;
		HL02value = area05value_left + area05value_right + area06value_left
				+ area06value_right + area07value_left + area07value_right
				+ area08value_left + area08value_right;
		HL03value = area09value_left + area09value_right + area10value_left
				+ area10value_right + area11value_left + area11value_right
				+ area12value_left + area12value_right;
		HL04value = area13value_left + area13value_right + area14value_left
				+ area14value_right;
		HL05value = area15value_left + area15value_right + area16value_left
				+ area16value_right;
		HL06value = area17value_left + area17value_right + area18value_left
				+ area18value_right + area19value_left + area19value_right
				+ area20value_left + area20value_right;
		HL07value = area21value_left + area21value_right + area22value_left
				+ area22value_right + area23value_left + area23value_right
				+ area24value_left + area24value_right;
		HL08value = area25value_left + area25value_right + area26value_left
				+ area26value_right + area27value_left + area27value_right
				+ area28value_left + area28value_right;
		// vertical lines
		VL01value = area01value_left + area05value_left + area09value_left
				+ area13value_left + area15value_left + area17value_left
				+ area21value_left + area25value_left;
		VL02value = area01value_right + area05value_right + area09value_right
				+ area13value_right + area15value_right + area17value_right
				+ area21value_right + area25value_right;
		VL03value = area02value_left + area06value_left + area10value_left
				+ area18value_left + area22value_left + area26value_left;
		VL04value = area02value_right + area06value_right + area10value_right
				+ area18value_right + area22value_right + area26value_right;
		VL05value = area03value_left + area07value_left + area11value_left
				+ area19value_left + area23value_left + area27value_left;
		VL06value = area03value_right + area07value_right + area11value_right
				+ area19value_right + area23value_right + area27value_right;
		VL07value = area04value_left + area08value_left + area12value_left
				+ area14value_left + area16value_left + area20value_left
				+ area24value_left + area28value_left;
		VL08value = area04value_right + area08value_right + area12value_right
				+ area14value_right + area16value_right + area20value_right
				+ area24value_right + area28value_right;
		// diagonal lines
		DL01value = area01value_left + area05value_right + area10value_left
				+ area19value_right + area24value_left + area28value_right;
		DL02value = area04value_right + area08value_left + area11value_right
				+ area18value_left + area21value_right + area25value_left;

		checkLimits(g); // call to check that line total does not exceed 21

		g.setColor(Color.black); // display all totals
		g.drawString(Integer.toString(HL01value), 406, 43); // horizontal values
		g.drawString(Integer.toString(HL02value), 406, 103);
		g.drawString(Integer.toString(HL03value), 406, 165);
		g.drawString(Integer.toString(HL04value), 406, 224);
		g.drawString(Integer.toString(HL05value), 406, 286);
		g.drawString(Integer.toString(HL06value), 406, 347);
		g.drawString(Integer.toString(HL07value), 406, 408);
		g.drawString(Integer.toString(HL08value), 406, 469);
		g.drawString(Integer.toString(VL01value), 26, 520); // vertical values
		g.drawString(Integer.toString(VL02value), 68, 520);
		g.drawString(Integer.toString(VL03value), 123, 520);
		g.drawString(Integer.toString(VL04value), 166, 520);
		g.drawString(Integer.toString(VL05value), 219, 520);
		g.drawString(Integer.toString(VL06value), 262, 520);
		g.drawString(Integer.toString(VL07value), 316, 520);
		g.drawString(Integer.toString(VL08value), 357, 520);
		g.drawString(Integer.toString(DL01value), 167, 230); // diagonal
																// values
		g.drawString(Integer.toString(DL02value), 224, 230);
	}

	/**
	 * <p>
	 * Method checks for line scores completion<br />
	 * answer area turns green if value = 21<br />
	 * answer area turns red if value > 21<br />
	 * </p>
	 */
	public void checkLimits(Graphics g) {
		if (HL01value > 21) {
			g.setColor(Color.red);
			g.fillRect(400, 25, 25, 25);
		}
		if (HL01value == 21) {
			g.setColor(Color.green);
			g.fillRect(400, 25, 25, 25);
		}
		if (HL02value > 21) {
			g.setColor(Color.red);
			g.fillRect(400, 85, 25, 25);
		}
		if (HL02value == 21) {
			g.setColor(Color.green);
			g.fillRect(400, 85, 25, 25);
		}
		if (HL03value > 21) {
			g.setColor(Color.red);
			g.fillRect(400, 148, 25, 25);
		}
		if (HL03value == 21) {
			g.setColor(Color.green);
			g.fillRect(400, 148, 25, 25);
		}
		if (HL04value > 21) {
			g.setColor(Color.red);
			g.fillRect(400, 208, 25, 25);
		}
		if (HL04value == 21) {
			g.setColor(Color.green);
			g.fillRect(400, 208, 25, 25);
		}
		if (HL05value > 21) {
			g.setColor(Color.red);
			g.fillRect(400, 270, 25, 25);
		}
		if (HL05value == 21) {
			g.setColor(Color.green);
			g.fillRect(400, 270, 25, 25);
		}
		if (HL06value > 21) {
			g.setColor(Color.red);
			g.fillRect(400, 330, 25, 25);
		}
		if (HL06value == 21) {
			g.setColor(Color.green);
			g.fillRect(400, 330, 25, 25);
		}
		if (HL07value > 21) {
			g.setColor(Color.red);
			g.fillRect(400, 391, 25, 25);
		}
		if (HL07value == 21) {
			g.setColor(Color.green);
			g.fillRect(400, 391, 25, 25);
		}
		if (HL08value > 21) {
			g.setColor(Color.red);
			g.fillRect(400, 452, 25, 25);
		}
		if (HL08value == 21) {
			g.setColor(Color.green);
			g.fillRect(400, 452, 25, 25);
		}
		if (VL01value > 21) {
			g.setColor(Color.red);
			g.fillRect(23, 502, 24, 24);
		}
		if (VL01value == 21) {
			g.setColor(Color.green);
			g.fillRect(23, 502, 24, 24);
		}
		if (VL02value > 21) {
			g.setColor(Color.red);
			g.fillRect(65, 502, 24, 24);
		}
		if (VL02value == 21) {
			g.setColor(Color.green);
			g.fillRect(65, 502, 24, 24);
		}
		if (VL03value > 21) {
			g.setColor(Color.red);
			g.fillRect(120, 502, 25, 25);
		}
		if (VL03value == 21) {
			g.setColor(Color.green);
			g.fillRect(120, 502, 25, 25);
		}
		if (VL04value > 21) {
			g.setColor(Color.red);
			g.fillRect(163, 502, 25, 25);
		}
		if (VL04value == 21) {
			g.setColor(Color.green);
			g.fillRect(163, 502, 25, 25);
		}
		if (VL05value > 21) {
			g.setColor(Color.red);
			g.fillRect(216, 502, 25, 25);
		}
		if (VL05value == 21) {
			g.setColor(Color.green);
			g.fillRect(216, 502, 25, 25);
		}
		if (VL06value > 21) {
			g.setColor(Color.red);
			g.fillRect(259, 502, 25, 25);
		}
		if (VL06value == 21) {
			g.setColor(Color.green);
			g.fillRect(259, 502, 25, 25);
		}
		if (VL07value > 21) {
			g.setColor(Color.red);
			g.fillRect(313, 502, 25, 25);
		}
		if (VL07value == 21) {
			g.setColor(Color.green);
			g.fillRect(313, 502, 25, 25);
		}
		if (VL08value > 21) {
			g.setColor(Color.red);
			g.fillRect(354, 502, 25, 25);
		}
		if (VL08value == 21) {
			g.setColor(Color.green);
			g.fillRect(354, 502, 25, 25);
		}
		if (DL01value > 21) {
			g.setColor(Color.red);
			g.fillRect(160, 213, 25, 25);
		}
		if (DL01value == 21) {
			g.setColor(Color.green);
			g.fillRect(160, 213, 25, 25);
		}
		if (DL02value > 21) {
			g.setColor(Color.red);
			g.fillRect(216, 213, 25, 25);
		}
		if (DL02value == 21) {
			g.setColor(Color.green);
			g.fillRect(216, 213, 25, 25);
		}
	}

	/**
	 * <p>
	 * Checks if user has made attempt at puzzle
	 * </p>
	 */
	public void checkAttempted() {
		if (HL01value == 21 && HL04value == 21 && HL08value == 21) {
			clearBoard(); // call to reset board and scores
			try {
				showSolution();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			showAttemptedMessage = false;
		} else {
			showAttemptedMessage = true;
		}
	}

	/**
	 * <p>
	 * Displays message showing easy start function enabled
	 * </p>
	 */
	public void showEasyStartClickedText(Graphics g) {
		Font font2 = new Font("Dialog", Font.BOLD, 24);
		g.setFont(font2);
		g.setColor(Color.black);
		g.drawString("Easy Start", 482, 50);
		g.drawString("Enabled", 490, 77);
		g.setColor(Color.red);
		g.drawString("Easy Start", 484, 52);
		g.drawString("Enabled", 492, 79);
	}

	/**
	 * <p>
	 * Displays message showing that user has clicked to view solution
	 * </p>
	 */
	public void showSolutionClickedText(Graphics g) {
		Font font3 = new Font("Dialog", Font.BOLD, 24);
		g.setFont(font3);
		g.setColor(Color.black);
		g.drawString("Show", 510, 25);
		g.drawString("Solution", 493, 50);
		g.drawString("Enabled", 494, 79);
		g.setColor(Color.red);
		g.drawString("Show", 512, 27);
		g.drawString("Solution", 495, 52);
		g.drawString("Enabled", 496, 81);
	}

	/**
	 * <p>
	 * Displays message prompting user to attempt puzzle<br />
	 * (displayed only if attempt was not made)
	 * </p>
	 */
	public void attemptMessage(Graphics g) {
		Font font1 = new Font("Dialog", Font.BOLD, 56);
		g.setFont(font1);
		g.setColor(Color.black);
		g.drawString("At least give it a try !", 38, 150);
		g.drawString("Complete highlighted", 30, 315);
		g.drawString("areas to enable solve", 33, 390);
		g.setColor(Color.red);
		g.drawString("At least give it a try !", 41, 153);
		g.drawString("Complete highlighted", 33, 318);
		g.drawString("areas to enable solve", 36, 393);
		showHighlightedAreas(g);
	}

	/**
	 * <p>
	 * Displays highlighted places that must be filled before solution view is
	 * enabled <br />
	 * Reference to transparency - http://code.dreamincode.net/snippet358.htm
	 * </p>
	 */
	public void showHighlightedAreas(Graphics g) {
		g.setColor(Color.blue.brighter());
		g.drawRect(10, 10, 425, 63);
		g.setColor(new Color(220, 0, 0, 80)); // 255,0,0,128 = 50% transparent red
		g.fillRect(10, 10, 425, 63); // highlight row A
		g.fillRect(10, 195, 425, 63); // highlight row D
		g.fillRect(10, 439, 425, 63); // highlight row H
				// button displayed to clear board
		g.setColor(Color.red);
		g.drawRect(489, 499, 112, 42);
		g.drawRect(490, 500, 110, 40);
		g.setColor(Color.yellow);
		g.fillRect(491, 501, 109, 39);
		Font font3 = new Font("Dialog", Font.PLAIN, 24);
		g.setFont(font3);
		g.setColor(Color.black);
		g.drawString("let me try", 495, 530);
		tryButtonEnabled = true;
	}

	/**
	 * <p>
	 * Draws puzzle completed message<br />
	 * Only works if truly completed - auto solve does not trigger completed
	 * text<br />
	 * Only triggered if boolean value of completed=true
	 * </p>
	 */
	public void puzzleSolved(Graphics g) {
		if (HL01value == 21 & HL02value == 21 & HL03value == 21
				& HL04value == 21 & HL05value == 21 & HL06value == 21
				& HL07value == 21 & HL08value == 21 & VL01value == 21
				& VL02value == 21 & VL03value == 21 & VL04value == 21
				& VL05value == 21 & VL06value == 21 & VL07value == 21
				& VL08value == 21 & DL01value == 21 & DL02value == 21) {
			if (completed == true) {
				Font font1 = new Font("Dialog", Font.BOLD, 96);
				g.setFont(font1);
				g.setColor(Color.black); 		// draws text shadow
				g.drawString("Completed", 50, 200);
				g.drawString("Well Done", 65, 380);
				Font font2 = new Font("Dialog", Font.BOLD, 96);
				g.setFont(font2);
				g.setColor(Color.red); 			// draws text message
				g.drawString("Completed", 53, 203);
				g.drawString("Well Done", 68, 383);
			}
		}
	}

	/**
	 * <p>
	 * Sets all values to zero - clearing board scores
	 * </p>
	 */
	public void clearLineScores() {
		area01value_left = 0;
		area01value_right = 0;
		boardAreaValues_left[0] = area01value_left;
		boardAreaValues_right[0] = area01value_right;
		area02value_left = 0;
		area02value_right = 0;
		boardAreaValues_left[1] = area02value_left;
		boardAreaValues_right[1] = area02value_right;
		area03value_left = 0;
		area03value_right = 0;
		boardAreaValues_left[2] = area03value_left;
		boardAreaValues_right[2] = area03value_right;
		area04value_left = 0;
		area04value_right = 0;
		boardAreaValues_left[3] = area04value_left;
		boardAreaValues_right[3] = area04value_right;
		area05value_left = 0;
		area05value_right = 0;
		boardAreaValues_left[4] = area05value_left;
		boardAreaValues_right[4] = area05value_right;
		area06value_left = 0;
		area06value_right = 0;
		boardAreaValues_left[5] = area06value_left;
		boardAreaValues_right[5] = area06value_right;
		area07value_left = 0;
		area07value_right = 0;
		boardAreaValues_left[6] = area07value_left;
		boardAreaValues_right[6] = area07value_right;
		area08value_left = 0;
		area08value_right = 0;
		boardAreaValues_left[7] = area08value_left;
		boardAreaValues_right[7] = area08value_right;
		area09value_left = 0;
		area09value_right = 0;
		boardAreaValues_left[8] = area09value_left;
		boardAreaValues_right[8] = area09value_right;
		area10value_left = 0;
		area10value_right = 0;
		boardAreaValues_left[9] = area10value_left;
		boardAreaValues_right[9] = area10value_right;
		area11value_left = 0;
		area11value_right = 0;
		boardAreaValues_left[10] = area11value_left;
		boardAreaValues_right[10] = area11value_right;
		area12value_left = 0;
		area12value_right = 0;
		boardAreaValues_left[11] = area12value_left;
		boardAreaValues_right[11] = area12value_right;
		area13value_left = 0;
		area13value_right = 0;
		boardAreaValues_left[12] = area13value_left;
		boardAreaValues_right[12] = area13value_right;
		area14value_left = 0;
		area14value_right = 0;
		boardAreaValues_left[13] = area14value_left;
		boardAreaValues_right[13] = area14value_right;
		area15value_left = 0;
		area15value_right = 0;
		boardAreaValues_left[14] = area15value_left;
		boardAreaValues_right[14] = area15value_right;
		area16value_left = 0;
		area16value_right = 0;
		boardAreaValues_left[15] = area16value_left;
		boardAreaValues_right[15] = area16value_right;
		area17value_left = 0;
		area17value_right = 0;
		boardAreaValues_left[16] = area17value_left;
		boardAreaValues_right[16] = area17value_right;
		area18value_left = 0;
		area18value_right = 0;
		boardAreaValues_left[17] = area18value_left;
		boardAreaValues_right[17] = area18value_right;
		area19value_left = 0;
		area19value_right = 0;
		boardAreaValues_left[18] = area19value_left;
		boardAreaValues_right[18] = area19value_right;
		area20value_left = 0;
		area20value_right = 0;
		boardAreaValues_left[19] = area20value_left;
		boardAreaValues_right[19] = area20value_right;
		area21value_left = 0;
		area21value_right = 0;
		boardAreaValues_left[20] = area21value_left;
		boardAreaValues_right[20] = area21value_right;
		area22value_left = 0;
		area22value_right = 0;
		boardAreaValues_left[21] = area22value_left;
		boardAreaValues_right[21] = area22value_right;
		area23value_left = 0;
		area23value_right = 0;
		boardAreaValues_left[22] = area23value_left;
		boardAreaValues_right[22] = area23value_right;
		area24value_left = 0;
		area24value_right = 0;
		boardAreaValues_left[23] = area24value_left;
		boardAreaValues_right[23] = area24value_right;
		area25value_left = 0;
		area25value_right = 0;
		boardAreaValues_left[24] = area25value_left;
		boardAreaValues_right[24] = area25value_right;
		area26value_left = 0;
		area26value_right = 0;
		boardAreaValues_left[25] = area26value_left;
		boardAreaValues_right[25] = area26value_right;
		area27value_left = 0;
		area27value_right = 0;
		boardAreaValues_left[26] = area27value_left;
		boardAreaValues_right[26] = area27value_right;
		area28value_left = 0;
		area28value_right = 0;
		boardAreaValues_left[27] = area28value_left;
		boardAreaValues_right[27] = area28value_right;
	}

	/**
	 * <p>
	 * Initializes domino, sets name,position and number of dots
	 * </p>
	 */
	public void drawDominoDZ(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(dominoDZ.x + 1, dominoDZ.y + 1, 79, 44);
		g.setColor(Color.black);
		g.drawRect(dominoDZ.x, dominoDZ.y, 80, 45);
		g.drawLine(dominoDZ.x + 40, dominoDZ.y, dominoDZ.x + 40,
				dominoDZ.y + 45);
	}

	/**
	 * <p>
	 * Initializes domino, sets name,position and number of dots
	 * </p>
	 */
	public void drawDomino10(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(domino10.x + 1, domino10.y + 1, 79, 44);
		g.setColor(Color.black);
		g.drawRect(domino10.x, domino10.y, 80, 45);
		g.drawLine(domino10.x + 40, domino10.y, domino10.x + 40,
				domino10.y + 45);
		drawDots10(g);
	}

	/**
	 * <p>
	 * Initializes domino, sets name,position and number of dots
	 * </p>
	 */
	public void drawDomino20(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(domino20.x + 1, domino20.y + 1, 79, 44);
		g.setColor(Color.black);
		g.drawRect(domino20.x, domino20.y, 80, 45);
		g.drawLine(domino20.x + 40, domino20.y, domino20.x + 40,
				domino20.y + 45);
		drawDots20(g);
	}

	/**
	 * <p>
	 * Initializes domino, sets name,position and number of dots
	 * </p>
	 */
	public void drawDominoZ3(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(dominoZ3.x + 1, dominoZ3.y + 1, 79, 44);
		g.setColor(Color.black);
		g.drawRect(dominoZ3.x, dominoZ3.y, 80, 45);
		g.drawLine(dominoZ3.x + 40, dominoZ3.y, dominoZ3.x + 40,
				dominoZ3.y + 45);
		drawDotsZ3(g);
	}

	/**
	 * <p>
	 * Initializes domino, sets name,position and number of dots
	 * </p>
	 */
	public void drawDominoZ4(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(dominoZ4.x + 1, dominoZ4.y + 1, 79, 44);
		g.setColor(Color.black);
		g.drawRect(dominoZ4.x, dominoZ4.y, 80, 45);
		g.drawLine(dominoZ4.x + 40, dominoZ4.y, dominoZ4.x + 40,
				dominoZ4.y + 45);
		drawDotsZ4(g);
	}

	/**
	 * <p>
	 * Initializes domino, sets name,position and number of dots
	 * </p>
	 */
	public void drawDomino50(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(domino50.x + 1, domino50.y + 1, 79, 44);
		g.setColor(Color.black);
		g.drawRect(domino50.x, domino50.y, 80, 45);
		g.drawLine(domino50.x + 40, domino50.y, domino50.x + 40,
				domino50.y + 45);
		drawDots50(g);
	}

	/**
	 * <p>
	 * Initializes domino, sets name,position and number of dots
	 * </p>
	 */
	public void drawDominoZ6(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(dominoZ6.x + 1, dominoZ6.y + 1, 79, 44);
		g.setColor(Color.black);
		g.drawRect(dominoZ6.x, dominoZ6.y, 80, 45);
		g.drawLine(dominoZ6.x + 40, dominoZ6.y, dominoZ6.x + 40,
				dominoZ6.y + 45);
		drawDotsZ6(g);
	}

	/**
	 * <p>
	 * Initializes domino, sets name,position and number of dots
	 * </p>
	 */
	public void drawDomino11(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(domino11.x + 1, domino11.y + 1, 79, 44);
		g.setColor(Color.black);
		g.drawRect(domino11.x, domino11.y, 80, 45);
		g.drawLine(domino11.x + 40, domino11.y, domino11.x + 40,
				domino11.y + 45);
		drawDots11(g);
	}

	/**
	 * <p>
	 * Initializes domino, sets name,position and number of dots
	 * </p>
	 */
	public void drawDomino21(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(domino21.x + 1, domino21.y + 1, 79, 44);
		g.setColor(Color.black);
		g.drawRect(domino21.x, domino21.y, 80, 45);
		g.drawLine(domino21.x + 40, domino21.y, domino21.x + 40,
				domino21.y + 45);
		drawDots21(g);
	}

	/**
	 * <p>
	 * Initializes domino, sets name,position and number of dots
	 * </p>
	 */
	public void drawDomino13(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(domino13.x + 1, domino13.y + 1, 79, 44);
		g.setColor(Color.black);
		g.drawRect(domino13.x, domino13.y, 80, 45);
		g.drawLine(domino13.x + 40, domino13.y, domino13.x + 40,
				domino13.y + 45);
		drawDots13(g);
		this.addMouseMotionListener(this);
	}

	/**
	 * <p>
	 * Initializes domino, sets name,position and number of dots
	 * </p>
	 */
	public void drawDomino14(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(domino14.x + 1, domino14.y + 1, 79, 44);
		g.setColor(Color.black);
		g.drawRect(domino14.x, domino14.y, 80, 45);
		g.drawLine(domino14.x + 40, domino14.y, domino14.x + 40,
				domino14.y + 45);
		drawDots14(g);
	}

	/**
	 * <p>
	 * Initializes domino, sets name,position and number of dots
	 * </p>
	 */
	public void drawDomino15(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(domino15.x + 1, domino15.y + 1, 79, 44);
		g.setColor(Color.black);
		g.drawRect(domino15.x, domino15.y, 80, 45);
		g.drawLine(domino15.x + 40, domino15.y, domino15.x + 40,
				domino15.y + 45);
		drawDots15(g);
	}

	/**
	 * <p>
	 * Initializes domino, sets name,position and number of dots
	 * </p>
	 */
	public void drawDomino61(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(domino61.x + 1, domino61.y + 1, 79, 44);
		g.setColor(Color.black);
		g.drawRect(domino61.x, domino61.y, 80, 45);
		g.drawLine(domino61.x + 40, domino61.y, domino61.x + 40,
				domino61.y + 45);
		drawDots61(g);
	}

	/**
	 * <p>
	 * Initializes domino, sets name,position and number of dots
	 * </p>
	 */
	public void drawDomino22(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(domino22.x + 1, domino22.y + 1, 79, 44);
		g.setColor(Color.black);
		g.drawRect(domino22.x, domino22.y, 80, 45);
		g.drawLine(domino22.x + 40, domino22.y, domino22.x + 40,
				domino22.y + 45);
		drawDots22(g);
	}

	/**
	 * <p>
	 * Initializes domino, sets name,position and number of dots
	 * </p>
	 */
	public void drawDomino32(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(domino32.x + 1, domino32.y + 1, 79, 44);
		g.setColor(Color.black);
		g.drawRect(domino32.x, domino32.y, 80, 45);
		g.drawLine(domino32.x + 40, domino32.y, domino32.x + 40,
				domino32.y + 45);
		drawDots32(g);
	}

	/**
	 * <p>
	 * Initializes domino, sets name,position and number of dots
	 * </p>
	 */
	public void drawDomino24(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(domino24.x + 1, domino24.y + 1, 79, 44);
		g.setColor(Color.black);
		g.drawRect(domino24.x, domino24.y, 80, 45);
		g.drawLine(domino24.x + 40, domino24.y, domino24.x + 40,
				domino24.y + 45);
		drawDots24(g);
	}

	/**
	 * <p>
	 * Initializes domino, sets name,position and number of dots
	 * </p>
	 */
	public void drawDomino52(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(domino52.x + 1, domino52.y + 1, 79, 44);
		g.setColor(Color.black);
		g.drawRect(domino52.x, domino52.y, 80, 45);
		g.drawLine(domino52.x + 40, domino52.y, domino52.x + 40,
				domino52.y + 45);
		drawDots52(g);
	}

	/**
	 * <p>
	 * Initializes domino, sets name,position and number of dots
	 * </p>
	 */
	public void drawDomino62(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(domino62.x + 1, domino62.y + 1, 79, 44);
		g.setColor(Color.black);
		g.drawRect(domino62.x, domino62.y, 80, 45);
		g.drawLine(domino62.x + 40, domino62.y, domino62.x + 40,
				domino62.y + 45);
		drawDots62(g);
	}

	/**
	 * <p>
	 * Initializes domino, sets name,position and number of dots
	 * </p>
	 */
	public void drawDomino33(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(domino33.x + 1, domino33.y + 1, 79, 44);
		g.setColor(Color.black);
		g.drawRect(domino33.x, domino33.y, 80, 45);
		g.drawLine(domino33.x + 40, domino33.y, domino33.x + 40,
				domino33.y + 45);
		drawDots33(g);
	}

	/**
	 * <p>
	 * Initializes domino, sets name,position and number of dots
	 * </p>
	 */
	public void drawDomino43(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(domino43.x + 1, domino43.y + 1, 79, 44);
		g.setColor(Color.black);
		g.drawRect(domino43.x, domino43.y, 80, 45);
		g.drawLine(domino43.x + 40, domino43.y, domino43.x + 40,
				domino43.y + 45);
		drawDots43(g);
	}

	/**
	 * <p>
	 * Initializes domino, sets name,position and number of dots
	 * </p>
	 */
	public void drawDomino35(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(domino35.x + 1, domino35.y + 1, 79, 44);
		g.setColor(Color.black);
		g.drawRect(domino35.x, domino35.y, 80, 45);
		g.drawLine(domino35.x + 40, domino35.y, domino35.x + 40,
				domino35.y + 45);
		drawDots35(g);
	}

	/**
	 * <p>
	 * Initializes domino, sets name,position and number of dots
	 * </p>
	 */
	public void drawDomino63(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(domino63.x + 1, domino63.y + 1, 79, 44);
		g.setColor(Color.black);
		g.drawRect(domino63.x, domino63.y, 80, 45);
		g.drawLine(domino63.x + 40, domino63.y, domino63.x + 40,
				domino63.y + 45);
		drawDots63(g);
	}

	/**
	 * <p>
	 * Initializes domino, sets name,position and number of dots
	 * </p>
	 */
	public void drawDomino44(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(domino44.x + 1, domino44.y + 1, 79, 44);
		g.setColor(Color.black);
		g.drawRect(domino44.x, domino44.y, 80, 45);
		g.drawLine(domino44.x + 40, domino44.y, domino44.x + 40,
				domino44.y + 45);
		drawDots44(g);
	}

	/**
	 * <p>
	 * Initializes domino, sets name,position and number of dots
	 * </p>
	 */
	public void drawDomino45(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(domino45.x + 1, domino45.y + 1, 79, 44);
		g.setColor(Color.black);
		g.drawRect(domino45.x, domino45.y, 80, 45);
		g.drawLine(domino45.x + 40, domino45.y, domino45.x + 40,
				domino45.y + 45);
		drawDots45(g);
	}

	/**
	 * <p>
	 * Initializes domino, sets name,position and number of dots
	 * </p>
	 */
	public void drawDomino64(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(domino64.x + 1, domino64.y + 1, 79, 44);
		g.setColor(Color.black);
		g.drawRect(domino64.x, domino64.y, 80, 45);
		g.drawLine(domino64.x + 40, domino64.y, domino64.x + 40,
				domino64.y + 45);
		drawDots64(g);
	}

	/**
	 * <p>
	 * Initializes domino, sets name,position and number of dots
	 * </p>
	 */
	public void drawDomino55(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(domino55.x + 1, domino55.y + 1, 79, 44);
		g.setColor(Color.black);
		g.drawRect(domino55.x, domino55.y, 80, 45);
		g.drawLine(domino55.x + 40, domino55.y, domino55.x + 40,
				domino55.y + 45);
		drawDots55(g);
	}

	/**
	 * <p>
	 * Initializes domino, sets name,position and number of dots
	 * </p>
	 */
	public void drawDomino56(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(domino56.x + 1, domino56.y + 1, 79, 44);
		g.setColor(Color.black);
		g.drawRect(domino56.x, domino56.y, 80, 45);
		g.drawLine(domino56.x + 40, domino56.y, domino56.x + 40,
				domino56.y + 45);
		drawDots56(g);
	}

	/**
	 * <p>
	 * Initializes domino, sets name,position and number of dots
	 * </p>
	 */
	public void drawDomino66(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(domino66.x + 1, domino66.y + 1, 79, 44);
		g.setColor(Color.black);
		g.drawRect(domino66.x, domino66.y, 80, 45);
		g.drawLine(domino66.x + 40, domino66.y, domino66.x + 40,
				domino66.y + 45);
		drawDots66(g);
	}

	/**
	 * <p>
	 * Method draws set amount of dots with reference to domino name
	 * </p>
	 */
	public void drawDots00(Graphics g) {
		// double zero
	}

	/**
	 * <p>
	 * Method draws set amount of dots with reference to domino name
	 * </p>
	 */
	public void drawDots10(Graphics g) {
		// one - zero
		// one - left
		g.drawOval(domino10.x + 17, domino10.y + 18, 8, 8);
		g.fillOval(domino10.x + 17, domino10.y + 18, 8, 8);
	}

	/**
	 * <p>
	 * Method draws set amount of dots with reference to domino name
	 * </p>
	 */
	public void drawDots20(Graphics g) {
		// two - zero
		// two - left
		g.drawOval(domino20.x + 10, domino20.y + 11, 8, 8);
		g.drawOval(domino20.x + 25, domino20.y + 29, 8, 8);
		g.fillOval(domino20.x + 10, domino20.y + 11, 8, 8);
		g.fillOval(domino20.x + 25, domino20.y + 29, 8, 8);
	}

	/**
	 * <p>
	 * Method draws set amount of dots with reference to domino name
	 * </p>
	 */
	public void drawDotsZ3(Graphics g) {
		// zero - three
		// three - right
		g.drawOval(dominoZ3.x + 46, dominoZ3.y + 9, 8, 8);
		g.drawOval(dominoZ3.x + 55, dominoZ3.y + 20, 8, 8);
		g.drawOval(dominoZ3.x + 65, dominoZ3.y + 30, 8, 8);
		g.fillOval(dominoZ3.x + 46, dominoZ3.y + 9, 8, 8);
		g.fillOval(dominoZ3.x + 55, dominoZ3.y + 20, 8, 8);
		g.fillOval(dominoZ3.x + 65, dominoZ3.y + 30, 8, 8);
	}

	/**
	 * <p>
	 * Method draws set amount of dots with reference to domino name
	 * </p>
	 */
	public void drawDotsZ4(Graphics g) {
		// zero - four
		// four - right
		g.drawOval(dominoZ4.x + 48, dominoZ4.y + 11, 8, 8);
		g.drawOval(dominoZ4.x + 63, dominoZ4.y + 11, 8, 8);
		g.drawOval(dominoZ4.x + 48, dominoZ4.y + 29, 8, 8);
		g.drawOval(dominoZ4.x + 63, dominoZ4.y + 29, 8, 8);
		g.fillOval(dominoZ4.x + 48, dominoZ4.y + 11, 8, 8);
		g.fillOval(dominoZ4.x + 63, dominoZ4.y + 11, 8, 8);
		g.fillOval(dominoZ4.x + 48, dominoZ4.y + 29, 8, 8);
		g.fillOval(dominoZ4.x + 63, dominoZ4.y + 29, 8, 8);
	}

	/**
	 * <p>
	 * Method draws set amount of dots with reference to domino name
	 * </p>
	 */
	public void drawDots50(Graphics g) {
		// five - zero
		// five - left
		g.drawOval(domino50.x + 7, domino50.y + 9, 8, 8);
		g.drawOval(domino50.x + 7, domino50.y + 30, 8, 8);
		g.drawOval(domino50.x + 16, domino50.y + 20, 8, 8);
		g.drawOval(domino50.x + 27, domino50.y + 30, 8, 8);
		g.drawOval(domino50.x + 27, domino50.y + 9, 8, 8);
		g.fillOval(domino50.x + 7, domino50.y + 9, 8, 8);
		g.fillOval(domino50.x + 7, domino50.y + 30, 8, 8);
		g.fillOval(domino50.x + 16, domino50.y + 20, 8, 8);
		g.fillOval(domino50.x + 27, domino50.y + 30, 8, 8);
		g.fillOval(domino50.x + 27, domino50.y + 9, 8, 8);
	}

	/**
	 * <p>
	 * Method draws set amount of dots with reference to domino name
	 * </p>
	 */
	public void drawDotsZ6(Graphics g) {
		// zero - six
		// six - right
		g.drawOval(dominoZ6.x + 45, dominoZ6.y + 11, 8, 8);
		g.drawOval(dominoZ6.x + 56, dominoZ6.y + 11, 8, 8);
		g.drawOval(dominoZ6.x + 67, dominoZ6.y + 11, 8, 8);
		g.drawOval(dominoZ6.x + 45, dominoZ6.y + 29, 8, 8);
		g.drawOval(dominoZ6.x + 56, dominoZ6.y + 29, 8, 8);
		g.drawOval(dominoZ6.x + 67, dominoZ6.y + 29, 8, 8);
		g.fillOval(dominoZ6.x + 45, dominoZ6.y + 11, 8, 8);
		g.fillOval(dominoZ6.x + 56, dominoZ6.y + 11, 8, 8);
		g.fillOval(dominoZ6.x + 67, dominoZ6.y + 11, 8, 8);
		g.fillOval(dominoZ6.x + 45, dominoZ6.y + 29, 8, 8);
		g.fillOval(dominoZ6.x + 56, dominoZ6.y + 29, 8, 8);
		g.fillOval(dominoZ6.x + 67, dominoZ6.y + 29, 8, 8);
	}

	/**
	 * <p>
	 * Method draws set amount of dots with reference to domino name
	 * </p>
	 */
	public void drawDots11(Graphics g) {
		// one - one
		// one - left
		g.drawOval(domino11.x + 17, domino11.y + 20, 8, 8);
		g.fillOval(domino11.x + 17, domino11.y + 20, 8, 8);
		// one - right
		g.drawOval(domino11.x + 55, domino11.y + 18, 8, 8);
		g.fillOval(domino11.x + 55, domino11.y + 18, 8, 8);
	}

	/**
	 * <p>
	 * Method draws set amount of dots with reference to domino name
	 * </p>
	 */
	public void drawDots21(Graphics g) {
		// two - one
		// two - left
		g.drawOval(domino21.x + 10, domino21.y + 11, 8, 8);
		g.drawOval(domino21.x + 25, domino21.y + 29, 8, 8);
		g.fillOval(domino21.x + 10, domino21.y + 11, 8, 8);
		g.fillOval(domino21.x + 25, domino21.y + 29, 8, 8);
		// one - right
		g.drawOval(domino21.x + 55, domino21.y + 18, 8, 8);
		g.fillOval(domino21.x + 55, domino21.y + 18, 8, 8);
	}

	/**
	 * <p>
	 * Method draws set amount of dots with reference to domino name
	 * </p>
	 */
	public void drawDots13(Graphics g) {
		// one - three
		// one - left
		g.drawOval(domino13.x + 17, domino13.y + 20, 8, 8);
		g.fillOval(domino13.x + 17, domino13.y + 20, 8, 8);
		// three - right
		g.drawOval(domino13.x + 46, domino13.y + 9, 8, 8);
		g.drawOval(domino13.x + 55, domino13.y + 20, 8, 8);
		g.drawOval(domino13.x + 65, domino13.y + 30, 8, 8);
		g.fillOval(domino13.x + 46, domino13.y + 9, 8, 8);
		g.fillOval(domino13.x + 55, domino13.y + 20, 8, 8);
		g.fillOval(domino13.x + 65, domino13.y + 30, 8, 8);
	}

	/**
	 * <p>
	 * Method draws set amount of dots with reference to domino name
	 * </p>
	 */
	public void drawDots14(Graphics g) {
		// one - four
		// one - left
		g.drawOval(domino14.x + 17, domino14.y + 20, 8, 8);
		g.fillOval(domino14.x + 17, domino14.y + 20, 8, 8);
		// four - right
		g.drawOval(domino14.x + 48, domino14.y + 11, 8, 8);
		g.drawOval(domino14.x + 63, domino14.y + 11, 8, 8);
		g.drawOval(domino14.x + 48, domino14.y + 29, 8, 8);
		g.drawOval(domino14.x + 63, domino14.y + 29, 8, 8);
		g.fillOval(domino14.x + 48, domino14.y + 11, 8, 8);
		g.fillOval(domino14.x + 63, domino14.y + 11, 8, 8);
		g.fillOval(domino14.x + 48, domino14.y + 29, 8, 8);
		g.fillOval(domino14.x + 63, domino14.y + 29, 8, 8);
	}

	/**
	 * <p>
	 * Method draws set amount of dots with reference to domino name
	 * </p>
	 */
	public void drawDots15(Graphics g) {
		// one - five
		// one - left
		g.drawOval(domino15.x + 17, domino15.y + 20, 8, 8);
		g.fillOval(domino15.x + 17, domino15.y + 20, 8, 8);
		// five - right
		g.drawOval(domino15.x + 45, domino15.y + 9, 8, 8);
		g.drawOval(domino15.x + 45, domino15.y + 30, 8, 8);
		g.drawOval(domino15.x + 55, domino15.y + 20, 8, 8);
		g.drawOval(domino15.x + 65, domino15.y + 30, 8, 8);
		g.drawOval(domino15.x + 65, domino15.y + 9, 8, 8);
		g.fillOval(domino15.x + 45, domino15.y + 9, 8, 8);
		g.fillOval(domino15.x + 45, domino15.y + 30, 8, 8);
		g.fillOval(domino15.x + 55, domino15.y + 20, 8, 8);
		g.fillOval(domino15.x + 65, domino15.y + 30, 8, 8);
		g.fillOval(domino15.x + 65, domino15.y + 9, 8, 8);
	}

	/**
	 * <p>
	 * Method draws set amount of dots with reference to domino name
	 * </p>
	 */
	public void drawDots61(Graphics g) {
		// six - one
		// six - left
		g.drawOval(domino61.x + 5, domino61.y + 11, 8, 8);
		g.drawOval(domino61.x + 16, domino61.y + 11, 8, 8);
		g.drawOval(domino61.x + 28, domino61.y + 11, 8, 8);
		g.drawOval(domino61.x + 5, domino61.y + 29, 8, 8);
		g.drawOval(domino61.x + 16, domino61.y + 29, 8, 8);
		g.drawOval(domino61.x + 28, domino61.y + 29, 8, 8);
		g.fillOval(domino61.x + 5, domino61.y + 11, 8, 8);
		g.fillOval(domino61.x + 16, domino61.y + 11, 8, 8);
		g.fillOval(domino61.x + 28, domino61.y + 11, 8, 8);
		g.fillOval(domino61.x + 5, domino61.y + 29, 8, 8);
		g.fillOval(domino61.x + 16, domino61.y + 29, 8, 8);
		g.fillOval(domino61.x + 28, domino61.y + 29, 8, 8);
		// one - right
		g.drawOval(domino61.x + 55, domino61.y + 18, 8, 8);
		g.fillOval(domino61.x + 55, domino61.y + 18, 8, 8);
	}

	/**
	 * <p>
	 * Method draws set amount of dots with reference to domino name
	 * </p>
	 */
	public void drawDots22(Graphics g) {
		// two - two
		// two - left
		g.drawOval(domino22.x + 10, domino22.y + 11, 8, 8);
		g.drawOval(domino22.x + 25, domino22.y + 29, 8, 8);
		g.fillOval(domino22.x + 10, domino22.y + 11, 8, 8);
		g.fillOval(domino22.x + 25, domino22.y + 29, 8, 8);
		// two - right
		g.drawOval(domino22.x + 47, domino22.y + 11, 8, 8);
		g.drawOval(domino22.x + 62, domino22.y + 29, 8, 8);
		g.fillOval(domino22.x + 47, domino22.y + 11, 8, 8);
		g.fillOval(domino22.x + 62, domino22.y + 29, 8, 8);
	}

	/**
	 * <p>
	 * Method draws set amount of dots with reference to domino name
	 * </p>
	 */
	public void drawDots32(Graphics g) {
		// three - two
		// three - left
		g.drawOval(domino32.x + 9, domino32.y + 9, 8, 8);
		g.drawOval(domino32.x + 17, domino32.y + 20, 8, 8);
		g.drawOval(domino32.x + 27, domino32.y + 30, 8, 8);
		g.fillOval(domino32.x + 9, domino32.y + 9, 8, 8);
		g.fillOval(domino32.x + 17, domino32.y + 20, 8, 8);
		g.fillOval(domino32.x + 27, domino32.y + 30, 8, 8);
		// two - right
		g.drawOval(domino32.x + 47, domino32.y + 11, 8, 8);
		g.drawOval(domino32.x + 62, domino32.y + 29, 8, 8);
		g.fillOval(domino32.x + 47, domino32.y + 11, 8, 8);
		g.fillOval(domino32.x + 62, domino32.y + 29, 8, 8);
	}

	/**
	 * <p>
	 * Method draws set amount of dots with reference to domino name
	 * </p>
	 */
	public void drawDots24(Graphics g) {
		// two - four
		// two - left
		g.drawOval(domino24.x + 10, domino24.y + 11, 8, 8);
		g.drawOval(domino24.x + 25, domino24.y + 29, 8, 8);
		g.fillOval(domino24.x + 10, domino24.y + 11, 8, 8);
		g.fillOval(domino24.x + 25, domino24.y + 29, 8, 8);
		// four - right
		g.drawOval(domino24.x + 48, domino24.y + 11, 8, 8);
		g.drawOval(domino24.x + 63, domino24.y + 11, 8, 8);
		g.drawOval(domino24.x + 48, domino24.y + 29, 8, 8);
		g.drawOval(domino24.x + 63, domino24.y + 29, 8, 8);
		g.fillOval(domino24.x + 48, domino24.y + 11, 8, 8);
		g.fillOval(domino24.x + 63, domino24.y + 11, 8, 8);
		g.fillOval(domino24.x + 48, domino24.y + 29, 8, 8);
		g.fillOval(domino24.x + 63, domino24.y + 29, 8, 8);
	}

	/**
	 * <p>
	 * Method draws set amount of dots with reference to domino name
	 * </p>
	 */
	public void drawDots52(Graphics g) {
		// five - two
		// five - left
		g.drawOval(domino52.x + 7, domino52.y + 9, 8, 8);
		g.drawOval(domino52.x + 7, domino52.y + 30, 8, 8);
		g.drawOval(domino52.x + 16, domino52.y + 20, 8, 8);
		g.drawOval(domino52.x + 27, domino52.y + 30, 8, 8);
		g.drawOval(domino52.x + 27, domino52.y + 9, 8, 8);
		g.fillOval(domino52.x + 7, domino52.y + 9, 8, 8);
		g.fillOval(domino52.x + 7, domino52.y + 30, 8, 8);
		g.fillOval(domino52.x + 16, domino52.y + 20, 8, 8);
		g.fillOval(domino52.x + 27, domino52.y + 30, 8, 8);
		g.fillOval(domino52.x + 27, domino52.y + 9, 8, 8);
		// two - right
		g.drawOval(domino52.x + 47, domino52.y + 11, 8, 8);
		g.drawOval(domino52.x + 62, domino52.y + 29, 8, 8);
		g.fillOval(domino52.x + 47, domino52.y + 11, 8, 8);
		g.fillOval(domino52.x + 62, domino52.y + 29, 8, 8);
	}

	/**
	 * <p>
	 * Method draws set amount of dots with reference to domino name
	 * </p>
	 */
	public void drawDots62(Graphics g) {
		// six - two
		// six - left
		g.drawOval(domino62.x + 5, domino62.y + 11, 8, 8);
		g.drawOval(domino62.x + 16, domino62.y + 11, 8, 8);
		g.drawOval(domino62.x + 28, domino62.y + 11, 8, 8);
		g.drawOval(domino62.x + 5, domino62.y + 29, 8, 8);
		g.drawOval(domino62.x + 16, domino62.y + 29, 8, 8);
		g.drawOval(domino62.x + 28, domino62.y + 29, 8, 8);
		g.fillOval(domino62.x + 5, domino62.y + 11, 8, 8);
		g.fillOval(domino62.x + 16, domino62.y + 11, 8, 8);
		g.fillOval(domino62.x + 28, domino62.y + 11, 8, 8);
		g.fillOval(domino62.x + 5, domino62.y + 29, 8, 8);
		g.fillOval(domino62.x + 16, domino62.y + 29, 8, 8);
		g.fillOval(domino62.x + 28, domino62.y + 29, 8, 8);
		// two - right
		g.drawOval(domino62.x + 47, domino62.y + 11, 8, 8);
		g.drawOval(domino62.x + 62, domino62.y + 29, 8, 8);
		g.fillOval(domino62.x + 47, domino62.y + 11, 8, 8);
		g.fillOval(domino62.x + 62, domino62.y + 29, 8, 8);
	}

	/**
	 * <p>
	 * Method draws set amount of dots with reference to domino name
	 * </p>
	 */
	public void drawDots33(Graphics g) {
		// three - three
		// three - left
		g.drawOval(domino33.x + 9, domino33.y + 9, 8, 8);
		g.drawOval(domino33.x + 17, domino33.y + 20, 8, 8);
		g.drawOval(domino33.x + 27, domino33.y + 30, 8, 8);
		g.fillOval(domino33.x + 9, domino33.y + 9, 8, 8);
		g.fillOval(domino33.x + 17, domino33.y + 20, 8, 8);
		g.fillOval(domino33.x + 27, domino33.y + 30, 8, 8);
		// three - right
		g.drawOval(domino33.x + 46, domino33.y + 9, 8, 8);
		g.drawOval(domino33.x + 55, domino33.y + 20, 8, 8);
		g.drawOval(domino33.x + 65, domino33.y + 30, 8, 8);
		g.fillOval(domino33.x + 46, domino33.y + 9, 8, 8);
		g.fillOval(domino33.x + 55, domino33.y + 20, 8, 8);
		g.fillOval(domino33.x + 65, domino33.y + 30, 8, 8);
	}

	/**
	 * <p>
	 * Method draws set amount of dots with reference to domino name
	 * </p>
	 */
	public void drawDots43(Graphics g) {
		// four - three
		// four - left
		g.drawOval(domino43.x + 10, domino43.y + 11, 8, 8);
		g.drawOval(domino43.x + 25, domino43.y + 11, 8, 8);
		g.drawOval(domino43.x + 10, domino43.y + 29, 8, 8);
		g.drawOval(domino43.x + 25, domino43.y + 29, 8, 8);
		g.fillOval(domino43.x + 10, domino43.y + 11, 8, 8);
		g.fillOval(domino43.x + 25, domino43.y + 11, 8, 8);
		g.fillOval(domino43.x + 10, domino43.y + 29, 8, 8);
		g.fillOval(domino43.x + 25, domino43.y + 29, 8, 8);
		// three - right
		g.drawOval(domino43.x + 46, domino43.y + 9, 8, 8);
		g.drawOval(domino43.x + 55, domino43.y + 20, 8, 8);
		g.drawOval(domino43.x + 65, domino43.y + 30, 8, 8);
		g.fillOval(domino43.x + 46, domino43.y + 9, 8, 8);
		g.fillOval(domino43.x + 55, domino43.y + 20, 8, 8);
		g.fillOval(domino43.x + 65, domino43.y + 30, 8, 8);
	}

	/**
	 * <p>
	 * Method draws set amount of dots with reference to domino name
	 * </p>
	 */
	public void drawDots35(Graphics g) {
		// three - five
		// three - left
		g.drawOval(domino35.x + 9, domino35.y + 9, 8, 8);
		g.drawOval(domino35.x + 17, domino35.y + 20, 8, 8);
		g.drawOval(domino35.x + 27, domino35.y + 30, 8, 8);
		g.fillOval(domino35.x + 9, domino35.y + 9, 8, 8);
		g.fillOval(domino35.x + 17, domino35.y + 20, 8, 8);
		g.fillOval(domino35.x + 27, domino35.y + 30, 8, 8);
		// five - right
		g.drawOval(domino35.x + 45, domino35.y + 9, 8, 8);
		g.drawOval(domino35.x + 45, domino35.y + 30, 8, 8);
		g.drawOval(domino35.x + 55, domino35.y + 20, 8, 8);
		g.drawOval(domino35.x + 65, domino35.y + 30, 8, 8);
		g.drawOval(domino35.x + 65, domino35.y + 9, 8, 8);
		g.fillOval(domino35.x + 45, domino35.y + 9, 8, 8);
		g.fillOval(domino35.x + 45, domino35.y + 30, 8, 8);
		g.fillOval(domino35.x + 55, domino35.y + 20, 8, 8);
		g.fillOval(domino35.x + 65, domino35.y + 30, 8, 8);
		g.fillOval(domino35.x + 65, domino35.y + 9, 8, 8);
	}

	/**
	 * <p>
	 * Method draws set amount of dots with reference to domino name
	 * </p>
	 */
	public void drawDots63(Graphics g) {
		// six - three
		// six - left
		g.drawOval(domino63.x + 5, domino63.y + 11, 8, 8);
		g.drawOval(domino63.x + 16, domino63.y + 11, 8, 8);
		g.drawOval(domino63.x + 28, domino63.y + 11, 8, 8);
		g.drawOval(domino63.x + 5, domino63.y + 29, 8, 8);
		g.drawOval(domino63.x + 16, domino63.y + 29, 8, 8);
		g.drawOval(domino63.x + 28, domino63.y + 29, 8, 8);
		g.fillOval(domino63.x + 5, domino63.y + 11, 8, 8);
		g.fillOval(domino63.x + 16, domino63.y + 11, 8, 8);
		g.fillOval(domino63.x + 28, domino63.y + 11, 8, 8);
		g.fillOval(domino63.x + 5, domino63.y + 29, 8, 8);
		g.fillOval(domino63.x + 16, domino63.y + 29, 8, 8);
		g.fillOval(domino63.x + 28, domino63.y + 29, 8, 8);
		// three - right
		g.drawOval(domino63.x + 46, domino63.y + 9, 8, 8);
		g.drawOval(domino63.x + 55, domino63.y + 20, 8, 8);
		g.drawOval(domino63.x + 65, domino63.y + 30, 8, 8);
		g.fillOval(domino63.x + 46, domino63.y + 9, 8, 8);
		g.fillOval(domino63.x + 55, domino63.y + 20, 8, 8);
		g.fillOval(domino63.x + 65, domino63.y + 30, 8, 8);
	}

	/**
	 * <p>
	 * Method draws set amount of dots with reference to domino name
	 * </p>
	 */
	public void drawDots44(Graphics g) {
		// four - four
		// four - left
		g.drawOval(domino44.x + 10, domino44.y + 11, 8, 8);
		g.drawOval(domino44.x + 25, domino44.y + 11, 8, 8);
		g.drawOval(domino44.x + 10, domino44.y + 29, 8, 8);
		g.drawOval(domino44.x + 25, domino44.y + 29, 8, 8);
		g.fillOval(domino44.x + 10, domino44.y + 11, 8, 8);
		g.fillOval(domino44.x + 25, domino44.y + 11, 8, 8);
		g.fillOval(domino44.x + 10, domino44.y + 29, 8, 8);
		g.fillOval(domino44.x + 25, domino44.y + 29, 8, 8);
		// four - right
		g.drawOval(domino44.x + 48, domino44.y + 11, 8, 8);
		g.drawOval(domino44.x + 63, domino44.y + 11, 8, 8);
		g.drawOval(domino44.x + 48, domino44.y + 29, 8, 8);
		g.drawOval(domino44.x + 63, domino44.y + 29, 8, 8);
		g.fillOval(domino44.x + 48, domino44.y + 11, 8, 8);
		g.fillOval(domino44.x + 63, domino44.y + 11, 8, 8);
		g.fillOval(domino44.x + 48, domino44.y + 29, 8, 8);
		g.fillOval(domino44.x + 63, domino44.y + 29, 8, 8);
	}

	/**
	 * <p>
	 * Method draws set amount of dots with reference to domino name
	 * </p>
	 */
	public void drawDots45(Graphics g) {
		// four - five
		// four - left
		g.drawOval(domino45.x + 10, domino45.y + 11, 8, 8);
		g.drawOval(domino45.x + 25, domino45.y + 11, 8, 8);
		g.drawOval(domino45.x + 10, domino45.y + 29, 8, 8);
		g.drawOval(domino45.x + 25, domino45.y + 29, 8, 8);
		g.fillOval(domino45.x + 10, domino45.y + 11, 8, 8);
		g.fillOval(domino45.x + 25, domino45.y + 11, 8, 8);
		g.fillOval(domino45.x + 10, domino45.y + 29, 8, 8);
		g.fillOval(domino45.x + 25, domino45.y + 29, 8, 8);
		// five - right
		g.drawOval(domino45.x + 45, domino45.y + 9, 8, 8);
		g.drawOval(domino45.x + 45, domino45.y + 30, 8, 8);
		g.drawOval(domino45.x + 55, domino45.y + 20, 8, 8);
		g.drawOval(domino45.x + 65, domino45.y + 30, 8, 8);
		g.drawOval(domino45.x + 65, domino45.y + 9, 8, 8);
		g.fillOval(domino45.x + 45, domino45.y + 9, 8, 8);
		g.fillOval(domino45.x + 45, domino45.y + 30, 8, 8);
		g.fillOval(domino45.x + 55, domino45.y + 20, 8, 8);
		g.fillOval(domino45.x + 65, domino45.y + 30, 8, 8);
		g.fillOval(domino45.x + 65, domino45.y + 9, 8, 8);
	}

	/**
	 * <p>
	 * Method draws set amount of dots with reference to domino name
	 * </p>
	 */
	public void drawDots64(Graphics g) {
		// six - four
		// six - left
		g.drawOval(domino64.x + 5, domino64.y + 11, 8, 8);
		g.drawOval(domino64.x + 16, domino64.y + 11, 8, 8);
		g.drawOval(domino64.x + 28, domino64.y + 11, 8, 8);
		g.drawOval(domino64.x + 5, domino64.y + 29, 8, 8);
		g.drawOval(domino64.x + 16, domino64.y + 29, 8, 8);
		g.drawOval(domino64.x + 28, domino64.y + 29, 8, 8);
		g.fillOval(domino64.x + 5, domino64.y + 11, 8, 8);
		g.fillOval(domino64.x + 16, domino64.y + 11, 8, 8);
		g.fillOval(domino64.x + 28, domino64.y + 11, 8, 8);
		g.fillOval(domino64.x + 5, domino64.y + 29, 8, 8);
		g.fillOval(domino64.x + 16, domino64.y + 29, 8, 8);
		g.fillOval(domino64.x + 28, domino64.y + 29, 8, 8);
		// four - right
		g.drawOval(domino64.x + 48, domino64.y + 11, 8, 8);
		g.drawOval(domino64.x + 63, domino64.y + 11, 8, 8);
		g.drawOval(domino64.x + 48, domino64.y + 29, 8, 8);
		g.drawOval(domino64.x + 63, domino64.y + 29, 8, 8);
		g.fillOval(domino64.x + 48, domino64.y + 11, 8, 8);
		g.fillOval(domino64.x + 63, domino64.y + 11, 8, 8);
		g.fillOval(domino64.x + 48, domino64.y + 29, 8, 8);
		g.fillOval(domino64.x + 63, domino64.y + 29, 8, 8);
	}

	/**
	 * <p>
	 * Method draws set amount of dots with reference to domino name
	 * </p>
	 */
	public void drawDots55(Graphics g) {
		// five - five
		// five - left
		g.drawOval(domino55.x + 7, domino55.y + 9, 8, 8);
		g.drawOval(domino55.x + 7, domino55.y + 30, 8, 8);
		g.drawOval(domino55.x + 16, domino55.y + 20, 8, 8);
		g.drawOval(domino55.x + 27, domino55.y + 30, 8, 8);
		g.drawOval(domino55.x + 27, domino55.y + 9, 8, 8);
		g.fillOval(domino55.x + 7, domino55.y + 9, 8, 8);
		g.fillOval(domino55.x + 7, domino55.y + 30, 8, 8);
		g.fillOval(domino55.x + 16, domino55.y + 20, 8, 8);
		g.fillOval(domino55.x + 27, domino55.y + 30, 8, 8);
		g.fillOval(domino55.x + 27, domino55.y + 9, 8, 8);
		// five - right
		g.drawOval(domino55.x + 45, domino55.y + 9, 8, 8);
		g.drawOval(domino55.x + 45, domino55.y + 30, 8, 8);
		g.drawOval(domino55.x + 55, domino55.y + 20, 8, 8);
		g.drawOval(domino55.x + 65, domino55.y + 30, 8, 8);
		g.drawOval(domino55.x + 65, domino55.y + 9, 8, 8);
		g.fillOval(domino55.x + 45, domino55.y + 9, 8, 8);
		g.fillOval(domino55.x + 45, domino55.y + 30, 8, 8);
		g.fillOval(domino55.x + 55, domino55.y + 20, 8, 8);
		g.fillOval(domino55.x + 65, domino55.y + 30, 8, 8);
		g.fillOval(domino55.x + 65, domino55.y + 9, 8, 8);
	}

	/**
	 * <p>
	 * Method draws set amount of dots with reference to domino name
	 * </p>
	 */
	public void drawDots56(Graphics g) {
		// five - six
		// five - left
		g.drawOval(domino56.x + 7, domino56.y + 9, 8, 8);
		g.drawOval(domino56.x + 7, domino56.y + 30, 8, 8);
		g.drawOval(domino56.x + 16, domino56.y + 20, 8, 8);
		g.drawOval(domino56.x + 27, domino56.y + 30, 8, 8);
		g.drawOval(domino56.x + 27, domino56.y + 9, 8, 8);
		g.fillOval(domino56.x + 7, domino56.y + 9, 8, 8);
		g.fillOval(domino56.x + 7, domino56.y + 30, 8, 8);
		g.fillOval(domino56.x + 16, domino56.y + 20, 8, 8);
		g.fillOval(domino56.x + 27, domino56.y + 30, 8, 8);
		g.fillOval(domino56.x + 27, domino56.y + 9, 8, 8);
		// six - right
		g.drawOval(domino56.x + 45, domino56.y + 11, 8, 8);
		g.drawOval(domino56.x + 56, domino56.y + 11, 8, 8);
		g.drawOval(domino56.x + 67, domino56.y + 11, 8, 8);
		g.drawOval(domino56.x + 45, domino56.y + 29, 8, 8);
		g.drawOval(domino56.x + 56, domino56.y + 29, 8, 8);
		g.drawOval(domino56.x + 67, domino56.y + 29, 8, 8);
		g.fillOval(domino56.x + 45, domino56.y + 11, 8, 8);
		g.fillOval(domino56.x + 56, domino56.y + 11, 8, 8);
		g.fillOval(domino56.x + 67, domino56.y + 11, 8, 8);
		g.fillOval(domino56.x + 45, domino56.y + 29, 8, 8);
		g.fillOval(domino56.x + 56, domino56.y + 29, 8, 8);
		g.fillOval(domino56.x + 67, domino56.y + 29, 8, 8);
	}

	/**
	 * <p>
	 * Method draws set amount of dots with reference to domino name
	 * </p>
	 */
	public void drawDots66(Graphics g) {
		// six - six
		// six - left
		g.drawOval(domino66.x + 5, domino66.y + 11, 8, 8);
		g.drawOval(domino66.x + 16, domino66.y + 11, 8, 8);
		g.drawOval(domino66.x + 28, domino66.y + 11, 8, 8);
		g.drawOval(domino66.x + 5, domino66.y + 29, 8, 8);
		g.drawOval(domino66.x + 16, domino66.y + 29, 8, 8);
		g.drawOval(domino66.x + 28, domino66.y + 29, 8, 8);
		g.fillOval(domino66.x + 5, domino66.y + 11, 8, 8);
		g.fillOval(domino66.x + 16, domino66.y + 11, 8, 8);
		g.fillOval(domino66.x + 28, domino66.y + 11, 8, 8);
		g.fillOval(domino66.x + 5, domino66.y + 29, 8, 8);
		g.fillOval(domino66.x + 16, domino66.y + 29, 8, 8);
		g.fillOval(domino66.x + 28, domino66.y + 29, 8, 8);
		// six - right
		g.drawOval(domino66.x + 45, domino66.y + 11, 8, 8);
		g.drawOval(domino66.x + 56, domino66.y + 11, 8, 8);
		g.drawOval(domino66.x + 67, domino66.y + 11, 8, 8);
		g.drawOval(domino66.x + 45, domino66.y + 29, 8, 8);
		g.drawOval(domino66.x + 56, domino66.y + 29, 8, 8);
		g.drawOval(domino66.x + 67, domino66.y + 29, 8, 8);
		g.fillOval(domino66.x + 45, domino66.y + 11, 8, 8);
		g.fillOval(domino66.x + 56, domino66.y + 11, 8, 8);
		g.fillOval(domino66.x + 67, domino66.y + 11, 8, 8);
		g.fillOval(domino66.x + 45, domino66.y + 29, 8, 8);
		g.fillOval(domino66.x + 56, domino66.y + 29, 8, 8);
		g.fillOval(domino66.x + 67, domino66.y + 29, 8, 8);
	}

	// ///////////////////////////////////////////////////////////////////////
	// variable dclarations
	// ///////////////////////////////////////////////////////////////////////

	private static final long serialVersionUID = 3544958753154740791L;

	private int x, y, select = 0;

	private int currentValue_left, currentValue_right, domValue_left,
			domValue_right;

	private int mouseX, mouseY;

	private int boardAreaValues_left[] = new int[28];

	private int boardAreaValues_right[] = new int[28];

	private Point mousePoint;

	private Point dominoDZ, domino10, domino20, dominoZ3, dominoZ4, domino50,
			dominoZ6;

	private Point domino11, domino21, domino13, domino14, domino15, domino61;

	private Point domino22, domino32, domino24, domino52, domino62;

	private Point domino33, domino43, domino35, domino63;

	private Point domino44, domino45, domino64;

	private Point domino55, domino56;

	private Point domino66;

	private boolean completed = false, showSolutionText = false,
			showEasyStartText = false;;

	private boolean showAttemptedMessage = false,
			removeAttemptedMessage = false;;

	private boolean tryButtonEnabled = false;

	private int HL01value, HL02value, HL03value, HL04value, HL05value,
			HL06value, HL07value, HL08value;

	private int VL01value, VL02value, VL03value, VL04value, VL05value,
			VL06value, VL07value, VL08value;

	private int DL01value, DL02value;

	private int area01value_left, area02value_left, area03value_left,
			area04value_left; // first line

	private int area05value_left, area06value_left, area07value_left,
			area08value_left; // second line

	private int area09value_left, area10value_left, area11value_left,
			area12value_left; // third line

	private int area13value_left, area14value_left; // fourth line

	private int area15value_left, area16value_left; // fifth line

	private int area17value_left, area18value_left, area19value_left,
			area20value_left; // sixth line

	private int area21value_left, area22value_left, area23value_left,
			area24value_left; // seventh line

	private int area25value_left, area26value_left, area27value_left,
			area28value_left; // eighth line

	private int area01value_right, area02value_right, area03value_right,
			area04value_right; // first line

	private int area05value_right, area06value_right, area07value_right,
			area08value_right; // second line

	private int area09value_right, area10value_right, area11value_right,
			area12value_right; // third line

	private int area13value_right, area14value_right; // fourth line

	private int area15value_right, area16value_right; // fifth line

	private int area17value_right, area18value_right, area19value_right,
			area20value_right; // sixth line

	private int area21value_right, area22value_right, area23value_right,
			area24value_right; // seventh line

	private int area25value_right, area26value_right, area27value_right,
			area28value_right; // eighth line

} // end of magicSquare.class
