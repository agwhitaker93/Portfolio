/**
********************************************************************************
*
*	@file		test.cpp
*
*	@brief		BRIEF DESCRIPTION ABOUT THE CONTENT OF THE FILE.
*
*	@version	1.0
*
*	@date		DATE HERE
*
*	@author		YOUR NAME HERE
*
*
********************************************************************************
*/


//******************************************************************************
//	Include
//******************************************************************************
#include <sstream>
#include <iostream>
#include <exception>

#include "Image.h"

//******************************************************************************
//	Namespace
//******************************************************************************
using namespace std;

//-----------------------------
int main(int argc, char** argv)
//-----------------------------
{
    // Return code
    int error_code(0);
    
    // Catch exceptions
    try
    {
        // Good number of arguments
        if (argc == 3)
        {
            // Load an image
            Image test_image;
            //test_image.loadPGM(argv[1]);
        
            // Save the image
            //test_image.savePGM(argv[2]);
        
        
            // WRITE YOUR CODE HERE TO TEST THE CLASS

			// Load the testing images
			Image puppy;
			puppy.loadPGM("puppy.pgm");
			Image kitten;
			kitten.loadPGM("kitten.pgm");

			// Perform testing on the images
			// Reset Images at end of each block of testing if modified

			// Test copy functionality
			test_image = puppy;
			test_image.savePGM("copytest.pgm");

			// Test standalone operators
			test_image = puppy + kitten;
			test_image.savePGM("plustest.pgm");
			test_image = kitten + puppy;
			test_image.savePGM("plustestopposite.pgm");
			test_image = puppy + puppy;
			test_image.savePGM("plustestsame.pgm");

			test_image = puppy - kitten;
			test_image.savePGM("minustest.pgm");
			test_image = kitten - puppy;
			test_image.savePGM("minustestopposite.pgm");

			// Test operatorequals
			test_image = puppy;
			test_image += kitten;
			test_image.savePGM("plusequals.pgm");

			test_image = puppy;
			test_image -= kitten;
			test_image.savePGM("minusequals.pgm");

			// Test standalone operators with values
			test_image = kitten + 200;
			test_image.savePGM("valueplustest.pgm");

			test_image = kitten - 200;
			test_image.savePGM("valueminustest.pgm");

			test_image = kitten * 10;
			test_image.savePGM("valuemulttest.pgm");

			test_image = kitten / 100;
			test_image.savePGM("valuedivtest.pgm");

			// Test operatorequals with values
			test_image = kitten;
			test_image += 200;
			test_image.savePGM("valueplusequals.pgm");

			test_image = kitten;
			test_image -= 200;
			test_image.savePGM("valueminusequals.pgm");

			test_image = kitten;
			test_image *= 10;
			test_image.savePGM("valuemultequals.pgm");

			test_image = kitten;
			test_image /= 100;
			test_image.savePGM("valuedivequals.pgm");

			// Test inversion
			test_image = !puppy;
			test_image.savePGM("negativetest.pgm");

			// Test getters
			test_image = puppy;

			cout << "Performing getters on puppy.pgm" << endl;

			cout << "Max value: " << test_image.getMaxValue() << endl;

			cout << "Width: " << test_image.getWidth() << endl;

			cout << "Height: " << test_image.getHeight() << endl;

			cout << "Aspect ratio: " << test_image.getAspectRatio() << endl;

			cout << "Pixel at (10, 20): " << test_image.getPixel(10, 20) << endl;

			cout << "Smallest value: " << test_image.getSmallestValue() << endl;

			cout << "Largest value: " << test_image.getLargestValue() << endl;

			// Test setters
			cout << "Testing setters on puppy.pgm" << endl;

			cout << "Pixel before modfiication at (10, 20): " << test_image.getPixel(10, 20) << endl;

			test_image.setPixel(10, 20, 10.5);

			cout << "New pixel, after modification, at (10, 20): " << test_image.getPixel(10, 20) << endl;

			test_image.savePGM("settest.pgm");

			// Test getters and setters with out of bound values
			cout << "Testing overflow getters and setters on puppy.pgm" << endl;

			test_image = puppy;
			int width = test_image.getWidth();
			int height = test_image.getHeight();

			cout << "Pixel before modification at (width + 10, height + 10): " << test_image.getPixel(width + 10, height + 10) << endl;

			test_image.setPixel(width + 10, height + 10, 10);

			cout << "Pixel after modification at (width + 10, height + 10): " << test_image.getPixel(width + 10, height + 10) << endl;

			test_image.savePGM("testoverflow.pgm");

			// Test ASCII
			Image ascii_test;
			ascii_test = puppy;
			ascii_test.writeASCII("writeasciitest.txt");

			ascii_test.readASCII("puppy.txt");
			ascii_test.writeASCII("readasciitest.txt");

			// End result of source images
			puppy.savePGM("puppyend.pgm");
			kitten.savePGM("kittenend.pgm");

			Image loadTest;
			loadTest.loadPGM("kittenend.pgm");
			loadTest.savePGM("loadtested.pgm");
        }
        // Wrong number of argument
        else
        {
            // Build the error message
            std::stringstream error_message;
            error_message << "Wrong number of arguments, usage:" << std::endl;
            error_message << "\t" << argv[0] << " input_file_name.pgm output_file_name.pgm" << std::endl;
        
            // Throw the error
            throw (error_message.str());
        }
    }
    // An error occured
    catch (const std::exception& error)
    {
        error_code = 1;
        std::cerr << error.what() << std::endl;
    }
    catch (const std::string& error)
    {
        error_code = 1;
        std::cerr << error << std::endl;
    }
    catch (const char* error)
    {
        error_code = 1;
        std::cerr << error << std::endl;
    }
    catch (...)
    {
        error_code = 1;
        std::cerr << "Unknown error" << std::endl;
    }

    return (error_code);
}
