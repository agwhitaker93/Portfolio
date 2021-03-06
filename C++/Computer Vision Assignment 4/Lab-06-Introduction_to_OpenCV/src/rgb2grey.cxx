/**
********************************************************************************
*
*	@file		rgb2grey.cxx
*
*	@brief		A program to convert a RGB image in a greyscale image using OpenCV.
*
*	@version	1.0
*
*	@date		29/01/2016
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


//-----------------------------
int main(int argc, char** argv)
//-----------------------------
{
    std::cout << "Test" << std::endl;
    try
    {
        // No file to display
        // No file to save
        if (argc != 3)
        {
            // Create an error message
            std::string error_message;
            error_message  = "usage: ";
            error_message += argv[0];
            error_message += " <input_image> <output_image>";

            // Throw an error
            throw error_message;
        }

        // Write your own code here


		cv::Mat image;
        cv::Mat grey_image;
		const string input_filename(argv[1]);
        const string output_filename(argv[2]);
		image = cv::imread(input_filename, CV_LOAD_IMAGE_COLOR);

		if (!image.data) {
			std::string error_message;
			error_message = "Could_not_open_or_find_the_image\"";
			error_message += input_filename;
			error_message += "\".";
		}

		string window_title;
		window_title = "Display_\"";
		window_title += output_filename;
		window_title += "\"";

		cv::namedWindow(window_title, cv::WINDOW_AUTOSIZE);

		cv::cvtColor(image, grey_image, CV_RGB2GRAY);

		if (!cv::imwrite(output_filename, grey_image)) {
			string error_message;
			error_message = "Could_not_write_the_image_\"";
			error_message += output_filename;
			error_message += "\"";

			throw error_message;
		}

		cv::imshow(window_title, grey_image);

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

