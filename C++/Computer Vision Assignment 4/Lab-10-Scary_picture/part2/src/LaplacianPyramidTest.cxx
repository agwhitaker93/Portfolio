 /**
********************************************************************************
*
*    @file      LaplacianPyramidTest.cxx
*
*    @brief     A program using OpenCV to test our LaplacianPyramid class.
*
*    @version   1.0
*
*    @date      13/03/2017
*
*    @author    Franck Vidal
*
*
********************************************************************************
*/


//******************************************************************************
//    Includes
//******************************************************************************
#include <opencv2/opencv.hpp> // Main OpenCV header

#include <exception> // Header for catching exceptions
#include <iostream>  // Header to display text in the console
#include <string>  // Header to use std::string

#include "LaplacianPyramid.h"


//******************************************************************************
//    Namespaces
//******************************************************************************
using namespace std;


//-----------------------------
int main(int argc, char** argv)
//-----------------------------
{
    try
    {
		cv::imshow("Test", cv::imread("C:\\Users\\darkv\\OneDrive\\Work\\Year 3\\Computer Vision\\Assignment4\\Lab-10-Scary_picture\\part1\\img\\orange.JPG", CV_LOAD_IMAGE_COLOR));
        //**********************************************************************
        // 1. Check the number of arguments
        // Process the command line arguments
        //**********************************************************************

        // Add your code here to check the number of command line arguments
        // If invalid, throw an error
		if (argc != 2) {
			std::string error_message;
			error_message = "Usage: ";
			error_message += argv[0];
			error_message += " <input_image>";

			error_message += "\n\nExample: ";
			error_message += argv[0];
			error_message += " orange.JPG";
		}



        //**********************************************************************
        // 2. Load the data
        //**********************************************************************

        // Add your code here
		cv::Mat input = cv::imread(argv[1], CV_LOAD_IMAGE_COLOR);




        //**********************************************************************
        // 3. Make sure the image is loaded, if it is not loaded, throw an error
        //**********************************************************************

        // Add your code here
		if (!input.data) {
			std::string error_message;
			error_message = "Could_not_open_or_find_the_image\"";
			error_message += argv[1];
			error_message += "\".";
		}




        //**********************************************************************
        // 4. Compute the Laplacian pyramids
        //**********************************************************************

        // Add your code here

        LaplacianPyramid pyramid_4;

		pyramid_4.setImage(input, 4);





        // Display the pyramids
        // (for testing)
        pyramid_4.display("Laplacian Pyramid with 4 levels");

        cv::imshow("Level 0", pyramid_4.getLevel(0));
        cv::imshow("Level 1", pyramid_4.getLevel(1));
        cv::imshow("Level 2", pyramid_4.getLevel(2));
        cv::imshow("Level 3", pyramid_4.getLevel(3));
        
        //**********************************************************************
        // 5. Reconstruct images at different level using the Laplacian pyramid
        //**********************************************************************

        // Add your code here

		cv::imshow("Reconsturction: Level 0", pyramid_4.reconstruct(0));
		cv::imshow("Reconsturction: Level 1", pyramid_4.reconstruct(1));
		cv::imshow("Reconsturction: Level 2", pyramid_4.reconstruct(2));
		cv::imshow("Reconsturction: Level 3", pyramid_4.reconstruct(3));





        // Wait for a key to be pressed
        int key(cv::waitKey(0));

        // Exit the program
        return (EXIT_SUCCESS);
    }
    // An error has happened
    catch (const std::exception& error)
    {
        cerr << "ERROR: " << error.what() << endl;
        return -1;
    }
    catch (const string& error)
    {
        cerr << "ERROR: " << error << endl;
        return -1;
    }
    catch (const char* error)
    {
        cerr << "ERROR: " << error << endl;
        return -1;
    }
}
