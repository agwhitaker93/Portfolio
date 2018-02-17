/**
********************************************************************************
*
*	@file		meanFilter.cpp
*
*	@brief		A program to perform the mean filter using OpenCV
*
*	@version	1.0
*
*	@date		31/01/2017
*
*	@author		Franck Vidal
*
*
********************************************************************************
*/


//******************************************************************************
//	Includes
//******************************************************************************
#include <cstdlib>   // Header for atoi and atof
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
        // No file to save
        if (argc != 3 && argc != 4)
        {
            // Create an error message
            std::string error_message;
            error_message  = "usage: ";
            error_message += argv[0];
            error_message += " <input_image>  <output_image> <kernel_radius>";

            // Throw an error
            throw error_message;
        }

        // Filter radius
        unsigned int radius(1);

        if (argc == 4)
        {
            radius = atoi(argv[3]);
        }
    
        // Write your own code here

		cv::Mat image, blur_image;
		const string input_filename(argv[1]), output_filename(argv[2]);
		unsigned int kernel_size = 1 + (radius * 2);
		cv::Size filter_size(kernel_size, kernel_size);

		image = cv::imread(input_filename, CV_LOAD_IMAGE_COLOR);

		if (!image.data) {
			std::string error_message;
			error_message = "Could_not_open_or_find_the_image\"";
			error_message += input_filename;
			error_message += "\".";
		}

		cv::blur(image, blur_image, filter_size);

		string window_title;
		window_title = "Display_\"";
		window_title += input_filename;
		window_title += "\"";

		cv::namedWindow(window_title, cv::WINDOW_AUTOSIZE);

		cv::imshow(window_title, blur_image);

		cv::waitKey(0);

		if (!cv::imwrite(output_filename, blur_image)) {
			string error_message;
			error_message = "Could_not_write_the_image_\"";
			error_message += output_filename;
			error_message += "\"";

			throw error_message;
		}

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

