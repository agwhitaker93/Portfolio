/**
********************************************************************************
*
*    @file      ImageStitching.cxx
*
*    @brief     A program to stitch two images together using Laplacian pyramids.
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


//******************************************************************************
//    Function declaration
//******************************************************************************
LaplacianPyramid combinePyramids(unsigned int anOriginalWidth,
                                 const LaplacianPyramid& anInputPyramidLeft,
                                 const LaplacianPyramid& anInputPyramidRight);


//-----------------------------
int main(int argc, char** argv)
//-----------------------------
{
    try
    {
        // Check the number of arguments
        if (argc != 5)
        {
            std::string error_message("Wrong number of command line arguments\n");
            error_message += "Usage:\n";
            error_message += argv[0];
            error_message += "  number_of_levels(int)  left_image  right_image  output_image";
            throw "Wrong number of command line arguments";
        }

        // The Laplacian pyramids
        LaplacianPyramid left_image_laplacian_pyramid;
        LaplacianPyramid right_image_laplacian_pyramid;
        LaplacianPyramid merge_image_laplacian_pyramid;

        // get the number of levels in the pyramids
        int number_of_levels_in_pyramids(atoi(argv[1]));

        // Load the left image
        cv::Mat left_image = cv::imread( argv[2], CV_LOAD_IMAGE_COLOR);

        // The image is loaded
        if( !left_image.data )
        {
            throw " No data! -- Exiting the program ";
        }

        // Resize if needed
        cv::resize(left_image, left_image, cv::Size(left_image.cols / 3, left_image.rows / 3));

        // Load the right image
        cv::Mat right_image = cv::imread( argv[3], CV_LOAD_IMAGE_COLOR);

        // The image is loaded
        if( !right_image.data )
        {
            throw " No data! -- Exiting the program ";
        }

        // Resize if needed
        cv::resize(right_image, right_image, cv::Size(right_image.cols / 3, right_image.rows / 3));

        // Compute the Laplacian pyramids
        left_image_laplacian_pyramid.setImage(left_image,   number_of_levels_in_pyramids);
        right_image_laplacian_pyramid.setImage(right_image, number_of_levels_in_pyramids);

        // Display the Laplacian pyramids
        // (for testing)
        left_image_laplacian_pyramid.display("Left image Laplacian pyramid");
        right_image_laplacian_pyramid.display("Right image Laplacian pyramid");
        
        // Reconstruct the original images from the Laplacian pyramids
        // (for testing)
        cv::Mat left_image_reconstruction  = left_image_laplacian_pyramid.reconstruct(0);
        cv::Mat right_image_reconstruction = right_image_laplacian_pyramid.reconstruct(0);
        
        // Show the reconstructions
        // (for testing)
        cv::imshow("left_image_reconstruction",  left_image_reconstruction);
        cv::imshow("right_image_reconstruction", right_image_reconstruction);

        // Create a new Laplacian pyramid combining the left side of every level in left_image_laplacian_pyramid and 
        // the right side of every level in right_image_laplacian_pyramid
        merge_image_laplacian_pyramid = combinePyramids(left_image.cols, left_image_laplacian_pyramid, right_image_laplacian_pyramid);

        // Display the Laplacian pyramid
        // (for testing)
        merge_image_laplacian_pyramid.display("Merged image Laplacian pyramid");

        // Reconstruct the blended image from the new Laplacian pyramid
        cv::Mat applorange_reconstruction = merge_image_laplacian_pyramid.reconstruct(0);

        // Show the result
        // (for testing)
        cv::imshow("Applorange Reconstruction", applorange_reconstruction);

        // Crop the result
        cv::Mat result = applorange_reconstruction(cv::Rect(0, 0, left_image.cols, left_image.rows));

        // Show the cropped result
        imshow("Cropped result", result);
        
        // Wait for a key to be pressed
        int key(cv::waitKey(0));

        // Save the result in a image file
        cv::imwrite(argv[4], result);

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
                    

//---------------------------------------------------------------------------
LaplacianPyramid combinePyramids(unsigned int anOriginalWidth,
                                 const LaplacianPyramid& anInputPyramidLeft,
                                 const LaplacianPyramid& anInputPyramidRight)
//---------------------------------------------------------------------------
{
    // The pyramids have the same number of levels
    if (anInputPyramidLeft.getNumberOfLevels() == anInputPyramidRight.getNumberOfLevels())
    {
        // Create the output Laplacian pyramid
        LaplacianPyramid output_pyramid;

        //**********************************************************************
        // 1. Initialise the output Laplacian pyramid using the Laplacian
        // pyramid of the left image
        //**********************************************************************

        // Add your code here
		output_pyramid = anInputPyramidLeft;






        // Process each level
        unsigned int half_width;
        unsigned int height;
 
        for (int i = 0; i < anInputPyramidLeft.getNumberOfLevels(); ++i)
        {
            // The images of this level have the same size
            if (anInputPyramidLeft[i].rows == anInputPyramidRight[i].rows &&
                anInputPyramidLeft[i].cols == anInputPyramidRight[i].cols)
            {
                //**************************************************************
                // 2. Compute the width of half an image without padding
                //**************************************************************

                // Add your code here

                half_width = anOriginalWidth / pow(2,i+1);
                height = anInputPyramidLeft[i].rows;
                
                




                //**************************************************************
                // 3. Replace the right half of output_pyramid by the right half
                // of anInputPyramidRight
                //**************************************************************

                // Add your code here
				cv::Mat sourceROI = anInputPyramidRight[i](cv::Rect(half_width, 0,
					half_width, height));
				cv::Mat targetROI = output_pyramid[i](cv::Rect(half_width, 0,
					half_width, height));
				sourceROI.copyTo(targetROI);







            }
            // The images of this level do not have the same size
            else
            {
                throw "Images of different sizes in pyramids";
            }
        }

        // Return the new blended pyramid
        return (output_pyramid);
    }
    // The pyramids do not have the same number of levels
    else
    {
        throw "Pyramids of different sizes";
    }
}
