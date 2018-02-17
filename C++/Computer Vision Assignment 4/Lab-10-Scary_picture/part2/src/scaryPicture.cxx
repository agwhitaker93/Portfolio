/**
********************************************************************************
*
*    @file      scaryPicture.cxx
*
*    @brief     Pyramid blending using OpenCV.
*
*    @version   1.0
*
*    @date      20/03/2017
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
#include "LaplacianPyramid.h"


//******************************************************************************
//    Namespaces
//******************************************************************************
using namespace std;


//******************************************************************************
//    Function declaration
//******************************************************************************
LaplacianPyramid combinePyramids(
        const LaplacianPyramid& aBackgroundLaplacianPyramid,
        const LaplacianPyramid& aForegroundLaplacianPyramid,
        const GaussianPyramid& aForegroundMaskGaussianPyramid);


//-------------------------------
int main( int argc, char** argv )
//-------------------------------
{
    try
    {
        //----------------------------------------------------------------------
		// 1 - Process the command line arguments. 
		// If the number of command line arguments is invalid, throw an error.
        //----------------------------------------------------------------------

        // Check the number of arguments
        if (argc != 6)
        {
            std::string error_message("Wrong number of command line arguments\n");
            error_message += "Usage:\n";
            error_message += argv[0];
            error_message += "  number_of_levels(int)  background_image  foreground_image  foreground_mask_image  output_image";
            throw error_message;
        }

        // get the number of levels in the pyramids
        int number_of_levels_in_pyramids(atoi(argv[1]));

		// Get the image file names
		std::string background_image_file_name(argv[2]);
		std::string foreground_image_file_name(argv[3]);
		std::string foreground_mask_file_name( argv[4]);
		std::string output_image_file_name(    argv[5]);
		




        //----------------------------------------------------------------------
		// 2 - Load the image files.
        //----------------------------------------------------------------------

        // Load the images
        cv::Mat background_image = cv::imread(background_image_file_name);
        cv::Mat foreground_image = cv::imread(foreground_image_file_name);
        cv::Mat foreground_mask  = cv::imread(foreground_mask_file_name);





        //----------------------------------------------------------------------
		// 3 - Make sure the images (background, foreground and mask) are 
		// loaded, if it is not loaded, throw an error.
        //----------------------------------------------------------------------
        
        // The background image is not loaded
        if (!background_image.data)
        {
        	std::string error_message("No data in file \"");
        	error_message += argv[2];
        	error_message += "\". Exiting the program";
            throw error_message;
        }

        // The foreground image is not loaded
        if (!foreground_image.data)
        {
        	std::string error_message("No data in file \"");
        	error_message += argv[3];
        	error_message += "\". Exiting the program";
            throw error_message;
        }

        // The foreground mask is not loaded
        if (!foreground_mask.data)
        {
        	std::string error_message("No data in file \"");
        	error_message += argv[4];
        	error_message += "\". Exiting the program";
            throw error_message;
        }
        
        // Resize if needed
        //cv::resize(background_image, background_image, cv::Size(background_image.cols / 6, background_image.rows / 6));
        //cv::resize(foreground_image, foreground_image, cv::Size(foreground_image.cols / 6, foreground_image.rows / 6));
        //cv::resize(foreground_mask,  foreground_mask,  cv::Size(foreground_mask.cols  / 6, foreground_mask.rows  / 6));
        
                
        
        
        
        //----------------------------------------------------------------------
		// 4 - Make sure the images have the same size, if it is not loaded, 
		// throw an error.
        //----------------------------------------------------------------------
        
		// The images have different sizes
		if (background_image.rows != foreground_image.rows ||
			background_image.rows != foreground_mask.rows  ||
			background_image.cols != foreground_image.cols ||
			background_image.cols != foreground_mask.cols)
		{

            throw "The images have different sizes";
		}





        //----------------------------------------------------------------------
		// 5 - Compute the Laplacian pyramids of the background and 
		// foreground images.
        //----------------------------------------------------------------------
        
		// Lapalacian pyramid of the background image
        LaplacianPyramid background_image_laplacian_pyramid;

		// Lapalacian pyramid of the foreground image
		LaplacianPyramid foreground_image_laplacian_pyramid;

        // Compute the Laplacian pyramids
        background_image_laplacian_pyramid.setImage(background_image,
        		number_of_levels_in_pyramids);
        		
        foreground_image_laplacian_pyramid.setImage(foreground_image,
        		number_of_levels_in_pyramids);

        // Display the pyramids
        // (for testing)
        background_image_laplacian_pyramid.display("Background image Laplacian pyramid");
        foreground_image_laplacian_pyramid.display("Foreground image Laplacian pyramid");





        //----------------------------------------------------------------------
		// 6 - Compute the Gaussian pyramid of the foreground mask.
        //----------------------------------------------------------------------
        
		// Gaussian pyramid of the foreground mask
        GaussianPyramid  foreground_mask_gaussian_pyramid;
        
        // Compute the Gaussian pyramids
        foreground_mask_gaussian_pyramid.setImage(foreground_mask, number_of_levels_in_pyramids);

        // Display the pyramid
        // (for testing)
        foreground_mask_gaussian_pyramid.display("Foreground mask Gaussian pyramid");    
        
        
        

    
        //----------------------------------------------------------------------
		// 7 - Create a new Laplacian pyramid by merging the two 
		// Laplacian pyramids using the Gaussian pyramid as weighting factors.
        //----------------------------------------------------------------------
                
        // Lapalacian pyramid of the merged image
        LaplacianPyramid merge_image_laplacian_pyramid;

        // Create a new Laplacian pyramid combining the background and 
        // foreground, and by applying the mask as weights
        merge_image_laplacian_pyramid = combinePyramids(background_image_laplacian_pyramid,
            foreground_image_laplacian_pyramid,
            foreground_mask_gaussian_pyramid);

        // Display the pyramid
        // (for testing)
        merge_image_laplacian_pyramid.display("Blended pyramid");    
        




        //----------------------------------------------------------------------
		// 8 - Reconstruct the blended image from the new Laplacian pyramid.
        //----------------------------------------------------------------------

        // Reconstruct the blended image from the new Laplacian pyramid
        cv::Mat blended_image_reconstruction = merge_image_laplacian_pyramid.reconstruct(0);

        // Show the result
        // (for testing)
        cv::imshow("blended_image_reconstruction", blended_image_reconstruction);






        //----------------------------------------------------------------------
		// 9 - Crop the result so that the output has the same size as 
		// the input.
        //----------------------------------------------------------------------

        // Crop the result
        cv::Mat cropped_result = blended_image_reconstruction(
        		cv::Rect(0, 0, background_image.cols, background_image.rows));
        
        // Show the cropped result
        // (for testing)
        cv::imshow("cropped_result", cropped_result);




        //----------------------------------------------------------------------
		// 10 - Save the reconstruction into an image file.
        //----------------------------------------------------------------------

        // Save the result in a image file
        cv::imwrite(output_image_file_name, cropped_result);





        // Wait for a key to be pressed
        int key(cv::waitKey(0));


        // Exit the program
        return (EXIT_SUCCESS);
    }
    // An error has happened
    catch (const exception& error)
    {
        cerr << "ERROR: " << error.what() << endl;
        return (EXIT_FAILURE);
    }
    catch (const string& error)
    {
        cerr << "ERROR: " << error << endl;
        return (EXIT_FAILURE);
    }
    catch (const char* error)
    {
        cerr << "ERROR: " << error << endl;
        return (EXIT_FAILURE);
    }
}
                   

//-------------------------------------------------------------------------------------
LaplacianPyramid combinePyramids(const LaplacianPyramid& aBackgroundLaplacianPyramid,
                                 const LaplacianPyramid& aForegroundLaplacianPyramid,
                                 const GaussianPyramid& aForegroundMaskGaussianPyramid)
//-------------------------------------------------------------------------------------
{
    //--------------------------------------------------------------------------
    // (a) Check that the input pyramids have the same number of levels.
    // If not throw an error
    //--------------------------------------------------------------------------

    // The pyramids do not have the same number of levels
    if (aBackgroundLaplacianPyramid.getNumberOfLevels() != aForegroundLaplacianPyramid.getNumberOfLevels() ||
        aBackgroundLaplacianPyramid.getNumberOfLevels() != aForegroundMaskGaussianPyramid.getNumberOfLevels())
    {
		throw "Pyramids of different sizes";
    }




    
    
    //--------------------------------------------------------------------------
    // (b) - Create the output Laplacian pyramid
    // (initialise it using the background image Laplacian pyramid)
    //--------------------------------------------------------------------------
    
    // The output Laplacian pyramid
    LaplacianPyramid output_pyramid = aBackgroundLaplacianPyramid;





	//--------------------------------------------------------------------------
    // (c) - Process each level of the pyramids
    //--------------------------------------------------------------------------

    for (int i = 0; i < aBackgroundLaplacianPyramid.getNumberOfLevels(); ++i)
    {
		//--------------------------------------------------------------------------
		// i. Check that the images of this level have the same size.
		// If not throw an error
		//--------------------------------------------------------------------------    
    
        //!! ADD YOUR CODE HERE !!//
		if (aBackgroundLaplacianPyramid[i].rows != aForegroundLaplacianPyramid[i].rows ||
			aBackgroundLaplacianPyramid[i].cols != aForegroundLaplacianPyramid[i].cols ||
			aBackgroundLaplacianPyramid[i].rows != aForegroundMaskGaussianPyramid[i].rows ||
			aBackgroundLaplacianPyramid[i].cols != aForegroundMaskGaussianPyramid[i].cols)
		{

			throw "The levels have different sizes";
		}





		//----------------------------------------------------------------------
		// ii. Normalise the mask (grey scale image in floating point numbers
		// with values between 0 and 1)
		//----------------------------------------------------------------------

		// The mask
		cv::Mat mask = aForegroundMaskGaussianPyramid[i];
		
		// Convert the mask from RGB to greyscale

        //!! ADD YOUR CODE HERE !!//
		cv::cvtColor(mask, mask, CV_RGB2GRAY);
        
		// Convert the data type of the pixel to floating point numbers
        
        //!! ADD YOUR CODE HERE !!//
		mask.convertTo(mask, CV_32F);
        
		// The mask with values between 0 and 1
		
        //!! ADD YOUR CODE HERE !!//
		cv::normalize(mask, mask, 0, 1, cv::NORM_MINMAX, CV_32F);
        
        

		

            
		//----------------------------------------------------------------------
		// iii. Process every pixel (every row and every column) of 
		// the current level (i)
		//----------------------------------------------------------------------
		
	    // Process every row
        for (int y(0); y < aForegroundLaplacianPyramid[i].rows; ++y)
        {
            // Process every column
            for (int x(0); x < aForegroundLaplacianPyramid[i].cols; ++x)
            {
				//--------------------------------------------------------------
				// A - Get the mask value of the current pixel from
				// the Gaussian pyramid
				//--------------------------------------------------------------
						    
                // Get the intensity of the current pixl in the foreground mask
                double alpha;

                //!! ADD YOUR CODE HERE !!//
				alpha = mask.at<float>(y, x);
                


				//--------------------------------------------------------------
				// B - Get the colour values of from the Laplacian pyramids
				//--------------------------------------------------------------

                // Get the colour inf the background image
                cv::Vec3f colour1;

                //!! ADD YOUR CODE HERE !!//
				colour1 = aBackgroundLaplacianPyramid[i].at<cv::Vec3f>(y, x);
                
                // Get the colour inf the foreground image
                cv::Vec3f colour2;
                
                //!! ADD YOUR CODE HERE !!//
				colour2 = aForegroundLaplacianPyramid[i].at<cv::Vec3f>(y, x);

				//--------------------------------------------------------------
				// C - Compute the new pixel values in the output Laplacian pyramid
				//--------------------------------------------------------------
				
                // Apply the alpha value for blending
                cv::Vec3f new_colour;
                
                //!! ADD YOUR CODE HERE !!//
				new_colour = alpha * colour2 + (1 - alpha) * colour1;
                
                
                
                // Compute the blended colour
                output_pyramid[i].at<cv::Vec3f>(y, x) = new_colour;
            }
        }
    }





	//--------------------------------------------------------------------------
	// (d) - Return the new blended pyramid
	//--------------------------------------------------------------------------
	
    // Return the new blended pyramid
    return (output_pyramid);
}

