/**
********************************************************************************
*
*	@file		logScale.cxx
*
*	@brief		A program to display an image in the log scale.
*	            Care is given to convert the RGB image to greyscale,
*	            to float and to avoid log(0) which is undefined.
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
            error_message += " <input_image>  <output_image>";

            // Throw an error
            throw error_message;
        }

        // Write your own code here

		cv::Mat image, grey_image, float_image, log_image, normalised_image;
		const string input_filename(argv[1]), output_filename(argv[2]);
		image = cv::imread(input_filename, CV_LOAD_IMAGE_COLOR);

		if (!image.data) {
			std::string error_message;
			error_message = "Could_not_open_or_find_the_image\"";
			error_message += input_filename;
			error_message += "\".";
		}

		cv::cvtColor(image, grey_image, CV_RGB2GRAY);
		grey_image.convertTo(float_image, CV_32FC1);
		cv::log(float_image + 1.0, log_image);
		cv::normalize(log_image, normalised_image, 0, 255, cv::NORM_MINMAX, CV_8UC1);

		string window_title;
		window_title = "Display_\"";
		window_title += input_filename;
		window_title += "\"";

		cv::namedWindow(window_title, cv::WINDOW_AUTOSIZE);

		cv::imshow(window_title, normalised_image);

		cv::waitKey(0);

		if (!cv::imwrite(output_filename, normalised_image)) {
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

