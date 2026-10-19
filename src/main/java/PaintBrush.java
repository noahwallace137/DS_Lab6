import java.util.Stack;
import javafx.scene.paint.Color;


public class PaintBrush
{

	
	Paint paint;
	
	
	enum BrushMode{
		paintMode,
		fillMode,
		pattern1Mode,
		pattern2Mode
	}

	BrushMode mode;
	
	Paint Gold = new PaintColor(Color.GOLD);
	Paint White = new PaintColor(Color.WHITE);
	
	
/**
set the "paint" for the paintbrush
*/	
	public void setPaint(Paint paint)
	{
		this.paint = paint;
   
   
	}


/*
   gets the present paint on the paint brush
*/
	public Paint getPaint()
	{
		return paint;
	}
	
   
   /*
   makes the paint on the paint brush a brigter shade.
   */
	public void setBrighter()
	{		
		paint = new PaintBrighter(paint);

	}


   /*
      makes the paint on the paintbrush a darker shade
   */
	public void setDarker()
	{
		paint = new PaintDarker(paint);
	}


   /*
      paints the mesh, using the current paint and mode at point x,y
   */
	public void paint(int x, int y, Paint[][] mesh)
	{
		if(!inBounds(x,y,mesh)) {
			return;
		}
		Paint original = mesh[x][y];
		boolean[][] visited = new boolean[mesh.length][mesh[0].length];
		if (mode == BrushMode.paintMode) {
			mesh[x][y] = paint;
			
		}
		else if (mode == BrushMode.fillMode) {
			recursiveFill(x, y, mesh, visited, original, false);
			
		}
		else if (mode == BrushMode.pattern1Mode) {
			recursiveFill(x, y, mesh, visited, original, true);

		}
		else if (mode == BrushMode.pattern2Mode) {
			stackFill(x, y, mesh, visited, original);

		}
		
	}
	public boolean inBounds(int x, int y, Paint[][] mesh) {
		return x >= 0 && x< mesh.length && y >= 0 && y <mesh[x].length;
		
	}
	public boolean eligible (int x, int y, Paint[][] mesh, boolean[][] visited, Paint original) {
		return inBounds(x, y, mesh) && mesh[x][y].equals(original) && !visited[x][y];
	}
	
	
	public void recursiveFill (int x, int y, Paint[][] mesh, boolean[][] visited, Paint original, boolean patternMode) {
		if (!eligible(x, y, mesh, visited, original)) {
			return;
		}
		visited[x][y] = true;
		if (patternMode) {
			if (x%2 == 00) {mesh[x][y] = Gold;
			}
			else { mesh[x][y] = White;
			}
		}
		else {
			mesh[x][y] = paint;
		}
		recursiveFill(x, y - 1, mesh, visited, original, patternMode);
		recursiveFill(x, y + 1, mesh, visited, original, patternMode);
		recursiveFill(x + 1, y, mesh, visited, original, patternMode);
		recursiveFill(x - 1, y, mesh, visited, original, patternMode);

	}
	
	public void stackFill (int x, int y, Paint[][] mesh, boolean[][] visited, Paint original) {
		Stack<int[]> pattern = new Stack<int[]>();
		
		visited[x][y] = true;
		pattern.push(new int[] {x, y});
		while (!pattern.isEmpty())
		{
			int[] current = pattern.pop();
			int currentx = current[0];
			int currenty = current[1];
			if (currentx % 2 == 0) {
				mesh[currentx][currenty] = Gold;
				
			}
			else {
				mesh[currentx][currenty] = White;
			}
			pushInStack(currentx, currenty - 1, mesh, visited, original, pattern);
			pushInStack(currentx, currenty + 1, mesh, visited, original, pattern);
			pushInStack(currentx + 1, currenty, mesh, visited, original, pattern);
			pushInStack(currentx - 1, currenty, mesh, visited, original, pattern);

		}
		
	}

	
	
	
	
	public void pushInStack(int x, int y, Paint[][] mesh, boolean[][] visited, Paint original, Stack<int[]> pattern) {
		if (eligible(x,y, mesh, visited, original )) {
			visited[x][y] = true;
			pattern.push(new int[] {x, y});
		}
	}
	
/*
   set the drawing mode of the paint brush.
*/
	public void pointMode()
	{
		mode= BrushMode.paintMode;
	}

	public void fillMode()
	{
		mode = BrushMode.fillMode;
	}

	public void pattern1Mode()
	{
		mode = BrushMode.pattern1Mode;
	}

	public void pattern2Mode()
	{
		mode = BrushMode.pattern2Mode;
	}

}
