import ij.*;
import ij.io.*;
import ij.process.*;


public class ImageJTest {

	public static void main(String[] args) {
		Opener opener = new Opener();  
		ImagePlus imp = opener.openImage("Teste.jpg");  
		ImageProcessor processor = imp.getProcessor();
		processor.flipHorizontal();
		imp.show();

	}

}
