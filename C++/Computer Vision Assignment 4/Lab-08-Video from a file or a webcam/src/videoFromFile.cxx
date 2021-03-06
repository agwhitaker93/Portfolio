/**
********************************************************************************
*
*    @file      videoFromFile.cxx
*
*    @brief     A simple program using OpenCV to display a video and 
*               perform some image processing tasks.
*
*    @version   1.0
*
*    @date      15/02/2016
*
*    @author    Franck Vidal
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
#include <cmath>     // Header use round()

#include <opencv2/opencv.hpp> // Main OpenCV header


//******************************************************************************
//    Namespaces
//******************************************************************************
using namespace std;


//******************************************************************************
//    Global variables
//******************************************************************************
const double EPSILON(1.0e-5);

// Parameters of the Canny filter
int g_low_threshold(0);
const int g_max_low_threshold = 100;
const int g_ratio = 3;
const int g_kernel_size = 3;

const int g_edge = 5; // Edge around the images in the window

cv::Mat g_current_frame; // Store the current frame
cv::Mat g_edge_frame;    // Store the edges detected in the current frame
cv::Mat g_displayed_image; // The image displayed in the window

// The title of every window
std::string g_window_title("Video");


//******************************************************************************
//    Function declaration
//******************************************************************************
void CannyThreshold(int, void*);


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


		/**********************************************************************/
		/* Process the command line arguments                                 */
		/**********************************************************************/

        // No file to display
        if (argc != 3 && argc != 4)
        {
            // Create an error message
            std::string error_message;
            error_message  = "Usage: ";
            error_message += argv[0];
            error_message += " <input_video>";
            error_message += " <scaling_factor>";
            error_message += " [output_video]";

            error_message += "\n\tExample: ";
            error_message += argv[0];
            error_message += " SAMPLING.AVI";
            error_message += " 0.25";
            error_message += " test.avi";

            // Throw an error
            throw error_message;
        }


		// Get the file names
		input_file_name  = argv[1];
        double scaling_factor = atof(argv[2]);
    
        // An output file name has been specified
        if (argc == 4)
        {
            output_file_name = argv[3];
        }



		/**********************************************************************/
		/* Open the video                                                     */
		/**********************************************************************/
		
        // Open the video file
        cv::VideoCapture video_capture(input_file_name); // !@£ YOUR CODE HERE £@!
    
        // The image has not been loaded
        if (!video_capture.isOpened())
        {
            // Create an error message
            // !@£ YOUR CODE HERE £@!
            std::string error_message;
            error_message = "Could not open or find the video \"";
            error_message += input_file_name;
            error_message += "\".";
            // Throw an error
            // !@£ YOUR CODE HERE £@!
            throw error_message;
        }

        // Read the frame rate of the video (does not always work)
        double fps = video_capture.get(CV_CAP_PROP_FPS); // !@£ YOUR CODE HERE £@!
        cout << "Frame per seconds : " << fps << endl;

		// Convert in seconds per frame (default 30 ms / frame)
		double seconds_per_frame(1.0 / fps);

		// Convert in milliseconds
		int milliseconds_per_frame(round(seconds_per_frame * 1000.0));

        // Get the video size
		cv::Size input_video_size(video_capture.get(CV_CAP_PROP_FRAME_WIDTH), video_capture.get(CV_CAP_PROP_FRAME_HEIGHT)); // !@£ YOUR CODE HERE £@!

        // Apply the scaling factor
		cv::Size scaled_video_size(input_video_size.width * scaling_factor, input_video_size.height * scaling_factor); // !@£ YOUR CODE HERE £@!

        // Set the size of the image displayed in the window
		cv::Size target_video_size(g_edge * 3 + 2 * scaled_video_size.width, g_edge * 2 + scaled_video_size.height); // !@£ YOUR CODE HERE £@!

        // Create the output image
        g_displayed_image = cv::Mat(target_video_size.height, target_video_size.width, CV_8UC3, cv::Scalar(128, 128, 128)); // !@£ YOUR CODE HERE £@!
    

		/**********************************************************************/
		/* File writer                                                        */
		/**********************************************************************/
		cv::VideoWriter video_writer;
        
        // Open the output
        if (output_file_name.size())
        {
            // !@£ YOUR CODE HERE £@!
            // Get the codec of the input video
            //int input_codec = video_capture.get(CV_CAP_PROP_FOURCC);
            
            // Open the file writer with the same codec as the input
            //video_writer.open(output_file_name, input_codec, fps, target_video_size, true);

            // The file is not open
            if (!video_writer.isOpened()) {
                // Open the file writer with another codec
                video_writer.open(output_file_name, CV_FOURCC('M', 'J', 'P', 'G'), fps, target_video_size, true);
                
                // The file is not open
                if (!video_writer.isOpened()) {
                    std::cerr << "WARNING: Cannot create the output video." << std::endl;
                }
            }
        }



		/**********************************************************************/
		/* Create the windows                                                 */
		/**********************************************************************/
		
        // Create the window
        // !@£ YOUR CODE HERE £@!
        cv::namedWindow(g_window_title, cv::WINDOW_AUTOSIZE);

        // Create the slider
        // !@£ YOUR CODE HERE £@!
        cv::createTrackbar("Min threshold:", g_window_title, &g_low_threshold, g_max_low_threshold, CannyThreshold);



		/**********************************************************************/
		/* Event loop                                                         */
		/**********************************************************************/

        // Last key pressed
        int key;	
		
        // Input stream status
        bool input_stream_status(true);

		// Event loop
        do
        {
            // Grab the next frame if possible
            // If an error occured, throw an error
            if (!video_capture.read(g_current_frame))
            {
                // Throw an error
                throw "Could not read frames from the video.";
            }
        
            if (input_stream_status) {
                input_stream_status = video_capture.read(g_current_frame);
                // Resize the input if needed
                if (input_video_size != scaled_video_size)
                {
                    cv::resize(g_current_frame, g_current_frame, scaled_video_size);
                }

                // Process the image
                CannyThreshold(0, 0);

                // The file writer is working
                if (video_writer.isOpened())
                {
                    // Add the current frame
                    // !@£ YOUR CODE HERE £@!
                    //video_writer << g_displayed_image;
                    video_writer.write(g_displayed_image);
                }

                // Wait for the key press event for X ms
                // (with X equal to 'milliseconds_per_frame')
                key = cv::waitKey(milliseconds_per_frame);
            }
        }
        // Stop the loop if 'q' or 'Escape' have been pressed
        while (key != 'q' && key != 27);
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


//-----------------------------
void CannyThreshold(int, void*)
//-----------------------------
{
    // Reduce noise with a kernel 3x3
    cv::Mat blurred_frame;
    // !@£ YOUR CODE HERE £@!
    cv::Size gaussian_kernel(3, 3);
    cv::GaussianBlur(g_current_frame, blurred_frame, gaussian_kernel, 0.5);
    
    // Copy the result
    cv::Mat targetROI = g_displayed_image(cv::Rect(g_edge, g_edge, g_current_frame.cols, g_current_frame.rows));
    g_current_frame.copyTo(targetROI);

    // Canny detector
    // !@£ YOUR CODE HERE £@!
    //cv::Mat dst;
    //dst.create(g_current_frame.size(), g_current_frame.type());

    cv::Canny(blurred_frame, g_edge_frame, g_low_threshold, g_low_threshold * g_ratio, g_kernel_size);

    //dst = cv::Scalar::all(0);

    //g_current_frame.copyTo(dst, g_edge_frame);

    // Convert to RGB
    // !@£ YOUR CODE HERE £@!
    //g_edge_frame.convertTo(g_edge_frame, CV_GRAY2RGB);
    cv::cvtColor(g_edge_frame, g_edge_frame, CV_GRAY2RGB);

    // Copy the result
    targetROI = g_displayed_image(cv::Rect(g_edge * 2 + g_current_frame.cols, g_edge, g_edge_frame.cols, g_edge_frame.rows));
    g_edge_frame.copyTo(targetROI);

    // Display the images
    cv::imshow(g_window_title,  g_displayed_image);
}