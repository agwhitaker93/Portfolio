/**
********************************************************************************
*
*    @file       edgeDetection1.cxx
*
*    @brief      A simple program using OpenCV to detect edges.
*
*    @version    1.0
*
*    @date       08/02/2016
*
*    @author     Franck Vidal
*
*
********************************************************************************
*/


//******************************************************************************
//    Includes
//******************************************************************************
#include <exception> // Header for catching exceptions
#include <iostream>  // Header to display text in the console
#include <string>    // Header to manipulate strings
#include <opencv2/opencv.hpp> // Main OpenCV header


//******************************************************************************
//    Namespaces
//******************************************************************************
using namespace std;


//******************************************************************************
//    Global variables
//******************************************************************************


//******************************************************************************
//    Function declaration
//******************************************************************************


//******************************************************************************
//    Implementation
//******************************************************************************


//-----------------------------
int main(int argc, char** argv)
//-----------------------------
{
    try
    {
		/**********************************************************************/
		/* Declare some local variables                                       */
		/**********************************************************************/

		// File names
		std::string input_file_name;
		std::string output_file_name;

        // Image structures
        cv::Mat rgb_image;        
        cv::Mat grey_image;
        cv::Mat gaussian_image;
        cv::Mat scharr_image;
        cv::Mat edge_image;
        
        // The title of every window
        std::string grey_image_window_title("Original data");
        std::string filtered_image_window_title("Filtered data");
        std::string edge_image_window_title("Edge data");
        

		/**********************************************************************/
		/* Process the command line arguments                                 */
		/**********************************************************************/

        // No file to display
        if (argc != 3)
        {
            // Create an error message
            std::string error_message;
            error_message  = "usage: ";
            error_message += argv[0];
            error_message += " <input_image>";
            error_message += " <output_image>";

            // Throw an error
            throw error_message;
        }

		// Get the file names
		input_file_name  = argv[1];
		output_file_name = argv[2];


		/**********************************************************************/
		/* Read the input                                                     */
		/**********************************************************************/
		
		
        // Open and read the image
        rgb_image = cv::imread(input_file_name, CV_LOAD_IMAGE_COLOR);

        // The image has not been loaded
        if (!rgb_image.data)
        {
            // Create an error message
            std::string error_message;
            error_message  = "Could not open or find the image \"";
            error_message += input_file_name;
            error_message += "\".";

            // Throw an error
            throw error_message;
        }

		/**********************************************************************/
		/* Convert the RGB data to greyscale                                  */
		/**********************************************************************/
				
		// Write your own code here to
        
        // 1) Convert the image from RGB to greyscale
		cv::cvtColor(rgb_image, grey_image, CV_RGB2GRAY);

        // 2) Convert the image from unsigned char to float
		grey_image.convertTo(grey_image, CV_32F);

        // 3) Normalise the image
        double max, offset = 1;
        /*cv::minMaxLoc(grey_image, NULL, &max);
        offset = max / 255;*/
        cv::normalize(grey_image, grey_image, 0, offset, cv::NORM_MINMAX, CV_32F);

        // Create the first window
        cv::namedWindow(grey_image_window_title, cv::WINDOW_AUTOSIZE);
                
        // Display the first window
        // Replace rgb_image by grey_image
        cv::imshow(grey_image_window_title, grey_image);
        
        
		/**********************************************************************/
		/* Apply a 3x3 Gaussian filter with sigma 0.5 to reduce noise         */
		/**********************************************************************/
		
		// Write your own code here
		cv::Size gaussian_kernel(3, 3);
		cv::GaussianBlur(grey_image, gaussian_image, gaussian_kernel, 0.5);
		
		/**********************************************************************/
		/* Gradient filter                                                    */
		/**********************************************************************/
		
		// Write your own code here to:
        
        // 1) Apply the Scharr filter on gaussian_image along the X-axis
        cv::Mat scharr_x;
        cv::Scharr(gaussian_image, scharr_x, CV_32F, 1, 0);
        
        // 2) Compute the absolute value of the gradient along the X-axis
        scharr_x = cv::abs(scharr_x);
        
        // 3) Apply the Scharr filter on gaussian_image along the Y-axis
        cv::Mat scharr_y;
        cv::Scharr(gaussian_image, scharr_y, CV_32F, 0, 1);
        
        // 4) Compute the absolute value of the gradient along the Y-axis
        scharr_y = cv::abs(scharr_y);
        
		// 5) Combined scharr_x and scharr_y together so that
		// scharr_image(x,y) = 0.5 * |scharr_x(x,y)| + 0.5 * |scharr_y(x,y)|
        addWeighted(scharr_x, 0.5, scharr_y, 0.5, 0.0, scharr_image);

        // Create the second window
        cv::namedWindow(filtered_image_window_title, cv::WINDOW_AUTOSIZE);
                
        // Display the second window
        // Replace rgb_image by scharr_image
        cv::imshow(filtered_image_window_title, scharr_image);


		/**********************************************************************/
		/* Threshold filter                                                   */
		/**********************************************************************/
        
        // Write your own code here
        cv::threshold(scharr_image, edge_image, 0.5, 1, cv::THRESH_BINARY);

        // Create the third window
        cv::namedWindow(edge_image_window_title, cv::WINDOW_AUTOSIZE);
                
        // Display the third window
        // Replace rgb_image by edge_image
        cv::imshow(edge_image_window_title, edge_image);
        
        // Wait for the user to press 'q' or 'Escape' (27 in ASCII code
        int key;
        do
        {
            key = cv::waitKey(0);
        }
        while (key != 'q' && key != 27);

		/**********************************************************************/
		/* Write the output                                                   */
		/**********************************************************************/
		
		// Write your own code here
        
		if (!cv::imwrite(output_file_name, edge_image)) {
			string error_message;
			error_message = "Could_not_write_the_image_\"";
			error_message += output_file_name;
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

