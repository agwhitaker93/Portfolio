/**
********************************************************************************
*
*	@file		DisplayImage.cxx
*
*	@brief		A simple program using OpenCV to open an image and 
*               display it in a window.
*
*	@version	1.0
*
*	@date		30/01/2017
*
*	@author		Franck Vidal
*
*
********************************************************************************
*/


//******************************************************************************
//	Includes
//******************************************************************************
#include <exception> // Header for catching exceptions
#include <iostream>  // Header to display text in the console
#include <opencv2/opencv.hpp> // Main OpenCV header


//******************************************************************************
//	Namespaces
//******************************************************************************
using namespace std;


//******************************************************************************
//	Global variables
//******************************************************************************


//******************************************************************************
//	Function declaration
//******************************************************************************


//******************************************************************************
//	Implementation
//******************************************************************************


//-----------------------------
int main(int argc, char** argv)
//-----------------------------
{
    try
    {
        // No file to display
        if (argc != 2)
        {
            // Create an error message
            std::string error_message;
            error_message  = "usage: ";
            error_message += argv[0];
            error_message += " <input_image>";

            // Throw an error
            throw error_message;
        }

        // Write your own code here
        //....
        //....
        //....

		cv::Mat image;
		//const string input_filename(argv[1]);
		const string input_filename(argv[1]);
		image = cv::imread(input_filename, CV_LOAD_IMAGE_COLOR);

		if (!image.data) {
			std::string error_message;
			error_message = "Could_not_open_or_find_the_image\"";
			error_message += input_filename;
			error_message += "\".";
		}

		string window_title;
		window_title = "Display_\"";
		window_title += input_filename;
		window_title += "\"";

		cv::namedWindow(window_title, cv::WINDOW_AUTOSIZE);

		cv::imshow(window_title, image);

		cv::waitKey(0);
    }
    // An error occured
    catch (const std::exception& error)
    {
        // Display an error message in the console
        cerr << error.what() << endl;
    }
    catch (const std::string& error)
    {
        // Display an error message in the console
        cerr << error << endl;
    }
    catch (const char* error)
    {
        // Display an error message in the console
        cerr << error << endl;
    }

    // Exit the program
    return 0;
}

