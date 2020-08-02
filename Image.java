import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import javax.imageio.ImageIO;

public class Image{
	 public static	BufferedImage img = null;
	 public static File f = null;
     public static Vector<Vector> pixles = new Vector<Vector>();
     //get image width and height
     public static int width ;
     public static int height ;
     public static int availableWidthPixles = 0;
     public static int availableHeightPixles = 0;
     
     
	
	  
	  public static void readImage()
	  {
		  //get width and height of image
		    width = img.getWidth();
		    height = img.getHeight();
	  }  
	  public static void getPixlesValues()
	  {
 
		    //get pixels value
		    for(int i = 0 ; i < img.getWidth();  i++)
		    {
		    	for (int j = 0 ; j < img.getHeight() ; j++)
		    	{
		    		Vector<Integer> vec = new Vector<Integer>();
		    		int p = img.getRGB(i,j);
		    	    //get red pixel
		    	    int r = (p>>16) & 0xff;
		    	    //get green pixel
		    	    int g = (p>>8) & 0xff;
		    	    //get blue pixel
		    	    int a = p & 0xff;
		    	    // add RGB of one pixel  to vector
		    	    vec.add(r);
		    	    vec.add(g);
		    	    vec.add(a);
		    	    // add that vector to another vector that saves the whole pixles
		    	    pixles.add(vec);
		    	}
		    }
	  }
	  public static String addZerosToLeft(String number)
	  {
		  // Adding zeros to the left of a string
		  for(int i = number.length() ; i < 8 ; i++)
		  {
			  number = "0"+number;
		  }
		  
		  return number;
	  }
	  // Replacing RGB integer values with RGB Binary values
	  public static void TransafrePixlesToBinary()
	  {
		  for(int i = 0 ; i < pixles.size() ; i++)
		    {
		        String bin = Integer.toBinaryString((int) pixles.get(i).get(0));
		        String bin1 = Integer.toBinaryString((int) pixles.get(i).get(1));
		        String bin2 = Integer.toBinaryString((int) pixles.get(i).get(2));
		        bin = addZerosToLeft(bin);
		        bin1 = addZerosToLeft(bin1);
		        bin2 = addZerosToLeft(bin2);
		        // clearing the RGB integer values and adding RGB binary values.
		        pixles.get(i).clear();
		        pixles.get(i).add(bin);
		        pixles.get(i).add(bin1);
		        pixles.get(i).add(bin2);
		    }
		    
	  }
	  // Cutting a specific char from string
	  public static String getCharFromString(String temp , int index)
	  {
		  String Char = "";
		  for(int i = 0 ; i < temp.length() ; i++)
		  {
			  if(i == index)
			  {
				  Char += temp.charAt(i);
				  break;
			  }
		  }
		  return Char;
	  }
	  // Replacing a specific char from string
	  public static String replaceCharInString(String temp , String x , int index)
	  {
		  String newString = "";
		  for(int i = 0 ; i < temp.length() ; i++)
		  {
			  if ( i == index)
			  {
				  newString+= x;
			  }
			  else
			  {
				  newString+=temp.charAt(i);
			  }
		  }
		  return newString;
	  }
	  public static void enCodeMessage(String message , int Places)
	  {
		  
		  int hegihtCounter = 0 , widthCounted = 0;
		  boolean check = false;
		  for(int i = 0 ; i < message.length() ; i++)
		  {
			 
			  char Character = message.charAt(i);
			  int CharInt =  Character ;
			  String BinaryChar = Integer.toBinaryString(CharInt);
			  BinaryChar = addZerosToLeft(BinaryChar);
			  if(BinaryChar.equals("00000000")) // The Binary for a null character ( The message reached it's end )
			  {
				  check = true; // boolean will be used to break
			  }
			  if (check || !BinaryChar.equals("00000000")) // if it is the last character or before the last.
			  {
				  boolean stop = false;
				  for( int c = 0 ; c < BinaryChar.length();) 
				  {
				  for (int k = availableWidthPixles ;  k < pixles.size() ;) // for loop starting with the next available ( not encoded ) pixel
				  {
					  
					  if(stop)
					  {
						  break;
					  }
					  
					  for(int p = 0 ; p < pixles.get(k).size() ; p++ , hegihtCounter++)
					  {
						  if(stop) // breaks the loop ( Used to ignore an un used pixel )
						  {
							  break;
						  }
						 
							  for(int j = Places ; j > 0 ; j-- , c++)
							  {
								  if(c == BinaryChar.length()) // end of loop. Used to break other loops
								  {
									  stop = true; 
								  }
								  String temp ="";
								  if(!stop)
								  {
									  char firstChar = BinaryChar.charAt(c);
									  temp += (String) pixles.get(k).get(p);
									  String test = replaceCharInString(temp, String.valueOf(firstChar) , temp.length()-j);
									  pixles.get(k).set(p, test);
									}		 
							  }
							  if (p == 2 || stop) // if the current char is encoded or the pixel finished it's R , G and B pixels so we skip to the next pixel
							  {
								  widthCounted++;
								  k++;
								  break;
							  }
					  }
				  	}
				  }
				  availableWidthPixles = widthCounted;
				  if(check)
				  {
					  System.out.println("Encoding finished " );
					  break;
				  }
				  
			  }
			  
		  }
	  }
	  public static void deCodeMessage(int Places)
	  {
		  boolean check = false;
		  String  temp2 = "" , Message = "";
		  for(int i = 0 ; i < pixles.size() ; i++ ) // loop for the whole pixles
		  {
			  for(int j = 0 ; j < pixles.get(i).size() ; j++) // loop for every pixel's Red Green and Blue
			  {
				  String temp = "" ;
				 for(int k = 0 ; k < Places ; k++ )// loop for Number of bits used in each pixel
				 {
				
					  String firstChar = getCharFromString((String) pixles.get(i).get(j), 8-Places+k ); // getting each encoded from the end of the pixel
					  if(temp2.length() >= 8 || ( temp2.length() + k == 8  ) )  // if the decoded character length is 8 or the next char will make it 8  -> break; ( So that the decoded char dosent get 9 bits )
					  {
						  break;  
					  }
					  else
					  {
						  temp += firstChar  ;
					  }
				 }
				 temp2 = temp2 + temp;
				  
				  if(temp2.length() >= 8)
				  {
					  if (temp2.equals("00000000")) // End of decoding
					  {
						  check = true;
						  break;
					  }
					  else
					  {
						  int tempInt = Integer.parseInt(temp2,2); // transforming the decoding character to String
						  Message+= (char) tempInt;
						  temp = "";
						  temp2 = "";
						  break;
					  }
				  }
				 
			  }
			  if(check)
			  {
				  GUI.P = Message; // the decoded message
				  break;
			  }
		  }
	  }
	  public static void saveEncodedImage(String savedName) throws IOException
	  {
	      BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); // creating a new photo to be saved with encoding message
		  for(int k = 0 ; k < pixles.size();)
		  {
			for (int i = 0; i < width; i++ ) {
			       for (int j = 0; j < height; j++) {
						  Color col = new Color((Integer.parseInt((String) pixles.get(k).get(0) , 2)),
								  (Integer.parseInt((String) pixles.get(k).get(1) , 2) ),
								  (Integer.parseInt((String) pixles.get(k).get(2) , 2))); // Transforming the RGB of a pixel into a Color
			            img.setRGB(i, j, col.getRGB()); // Setting a pixel in the new photo
			            k++;// Skipping to the next pixel
			         }
			  }
			  
		  }
		      // retrieve image
		      File outputfile = new File(savedName + ".png");
		      ImageIO.write(img,  "png", outputfile); // saved as a PNG to avoid image compression of other types , so that the encoded message dose not disappear
		}		  
}