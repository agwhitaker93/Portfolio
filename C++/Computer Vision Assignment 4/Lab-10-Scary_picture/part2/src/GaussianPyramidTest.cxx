/**
********************************************************************************
*
*    @file      GaussianPyramidTest.cxx
*
*    @brief     A program using OpenCV to test our GaussianPyramid class.
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

#include "GaussianPyramid.h"


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
        //**********************************************************************
        // 1. Process the command line arguments
        //**********************************************************************

        // Add your code here to check the number of command line arguments
        // If invalid, throw an error
		if (argc != 2) {
			std::string error_message;
			error_message = "Usage: ";
			error_message += argv[0];
			error_message += " <input_image>";

			error_message += "\n\tExample: ";
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
        // 4. Compute the Gaussian pyramids using 3, 4, and 6 levels
        //**********************************************************************

        // Compute the Gaussian pyramids
        GaussianPyramid pyramid_6(input, 6);
        GaussianPyramid pyramid_4(input, 4);
        GaussianPyramid pyramid_3(input, 3);

        // Display the pyramids
        // (for testing)
        pyramid_3.display("Gaussian Pyramid with 3 levels");
        pyramid_4.display("Gaussian Pyramid with 4 levels");
        pyramid_6.display("Gaussian Pyramid with 6 levels");

        // Display individual images
        // (for testing)
        cv::Mat img0 = pyramid_3[0];
        cv::Mat img1 = pyramid_3(1);
        cv::Mat img2 = pyramid_3.getLevel(2);

        // There are stored using floating point numbers, so normalise them
        cv::normalize(img0, img0, 0, 255, cv::NORM_MINMAX, CV_8UC3);
        cv::normalize(img1, img1, 0, 255, cv::NORM_MINMAX, CV_8UC3);
        cv::normalize(img2, img2, 0, 255, cv::NORM_MINMAX, CV_8UC3);
        
        // Display them
        cv::imshow("0", img0);
        cv::imshow("1", img1);
        cv::imshow("2", img2);
        
        
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
