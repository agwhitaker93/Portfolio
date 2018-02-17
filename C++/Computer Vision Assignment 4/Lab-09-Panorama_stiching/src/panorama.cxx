/**
********************************************************************************
*
*    @file      panorama.cxx
*
*    @brief     A program using OpenCV to stitch images together.
*
*    @version   1.0
*
*    @date      27/02/2017
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
#include <sstream>  // Header to create stings using a stream
#include <vector>

#include <opencv2/opencv.hpp> // Main OpenCV header
#include <opencv2/features2d.hpp> // Header for the feature detectors


//******************************************************************************
//    Namespaces
//******************************************************************************
using namespace std;


//******************************************************************************
//    Global variables
//******************************************************************************
vector<cv::Mat> g_p_input_image_set;

//******************************************************************************
//    Function declaration
//******************************************************************************
cv::Mat autoCrop(const cv::Mat& anImage);


//******************************************************************************
//    Implementation
//******************************************************************************


//-----------------------------
int main(int argc, char** argv)
//-----------------------------
{
    try
    {
		//**********************************************************************
		// Process the command line arguments
		//**********************************************************************

        // Add your code here to check the number of command line arguments
        // If invalid, throw an error
        if (argc != 4) {
            std::string error_message;
            error_message  = "Usage: ";
            error_message += argv[0];
            error_message += " <input_image_1>";
            error_message += " <input_image_2>";
            error_message += " <output_image>";

            error_message += "\n\tExample: ";
            error_message += argv[0];
            error_message += " IMGP1.jpg";
            error_message += " IMGP2.jpg";
            error_message += " PANORAMA.jpg";

            throw error_message;
        }
    
    
		//**********************************************************************
		// Load the data
		//**********************************************************************

        // Add your code here
        cv::Mat temp_image = cv::imread(argv[1], CV_LOAD_IMAGE_COLOR);
        g_p_input_image_set.push_back(temp_image);

        temp_image = cv::imread(argv[2], CV_LOAD_IMAGE_COLOR);
        g_p_input_image_set.push_back(temp_image);



		
        //**********************************************************************
		// Display every image
		//**********************************************************************

        // Add your code here
        cv::namedWindow("Image 1", cv::WINDOW_AUTOSIZE);
        cv::imshow("Image 1", g_p_input_image_set[0]);

        cv::namedWindow("Image 2", cv::WINDOW_AUTOSIZE);
        cv::imshow("Image 2", g_p_input_image_set[1]);
    


        //**********************************************************************
		// Find features
		//**********************************************************************

        // Add your code here
		cv::Mat total_image(g_p_input_image_set[0]), current_image(g_p_input_image_set[1]);

        cv::Ptr<cv::FeatureDetector> p_feature_detector;
        p_feature_detector = cv::ORB::create();

        std::vector<cv::KeyPoint> p_total_image_keypoint_set;
        std::vector<cv::KeyPoint> p_current_image_keypoint_set;

        p_feature_detector->detect(total_image, p_total_image_keypoint_set);
        p_feature_detector->detect(current_image, p_current_image_keypoint_set);


        //**********************************************************************
        // Describe/Extract features
        //**********************************************************************
        
        // Add your code here
        cv::Ptr<cv::DescriptorExtractor> p_feature_extractor;
        p_feature_extractor = cv::ORB::create();

        cv::Mat total_image_descriptors;
        cv::Mat current_image_descriptors;
        
        p_feature_extractor->compute(total_image, p_total_image_keypoint_set, total_image_descriptors);
        p_feature_extractor->compute(current_image, p_current_image_keypoint_set, current_image_descriptors);

        
        
        //**********************************************************************
        // Match the features
        //**********************************************************************
        
        // Add your code here
        cv::Ptr<cv::DescriptorMatcher> p_feature_matcher;
        p_feature_matcher = cv::DescriptorMatcher::create("BruteForce-Hamming");
        
        vector<cv::DMatch> p_match_set;

        p_feature_matcher->match(total_image_descriptors, current_image_descriptors, p_match_set);
        
        
        //**********************************************************************
        // Compute some basic statitics (e.g. min, max) to filter the matches
        //**********************************************************************
        double min_distance(DBL_MAX);
        double max_distance(-DBL_MAX);
    
        // Add your code here
        for (std::vector<cv::DMatch>::const_iterator ite(p_match_set.begin()); ite != p_match_set.end(); ++ite) {
            if (ite->distance < min_distance) {
                min_distance = ite->distance;
            }
            else if (ite-> distance > max_distance) {
                max_distance = ite->distance;
            }
        }
 
        //**********************************************************************
        // Only use "good" matches (i.e. whose distance is less than 8 * min_distance)
        //**********************************************************************
    
        std::vector<cv::DMatch> p_good_match_set;
        for (std::vector<cv::DMatch>::const_iterator ite(p_match_set.begin()); ite != p_match_set.end(); ++ite) {
            if (ite->distance < 8 * min_distance) {
                p_good_match_set.push_back(*ite);
            }
        }

        //**********************************************************************
        // Get the 2D points in the key points from the good matches
        //**********************************************************************
    
        // Add your code here
        vector<cv::Point2f> p_total_image_point_set, p_current_image_point_set;

        for(vector<cv::DMatch>::const_iterator ite(p_good_match_set.begin()); ite != p_good_match_set.end(); ++ite) {
            cv::KeyPoint total_image_keypoint(p_total_image_keypoint_set[ite->queryIdx]);
            cv::KeyPoint current_image_keypoint(p_current_image_keypoint_set[ite->trainIdx]);

            p_total_image_point_set.push_back(total_image_keypoint.pt);
            p_current_image_point_set.push_back(current_image_keypoint.pt);
        }

        //**********************************************************************
        // Find the Homography Matrix
        //**********************************************************************

        // Add your code here
        cv::Mat homography_matrix(cv::findHomography(p_total_image_point_set, p_current_image_point_set, CV_RANSAC));

        //**********************************************************************
        // Use the Homography Matrix to warp the images
        //**********************************************************************
        cv::Mat output;

        // Add your code here
        cv::warpPerspective(total_image, output, homography_matrix, cv::Size(total_image.cols + current_image.cols, total_image.rows + 1));

        cv::Mat half(output(cv::Rect(0, 0, current_image.cols, current_image.rows)));
        current_image.copyTo(half);
        cv::imshow("Result", output);
        


        //**********************************************************************
        // Remove black edges
        //**********************************************************************
        output = autoCrop(output);
        cv::imshow("autoCrop", output);
    
        // Write the output
        cv::imwrite(argv[argc - 1], output);

        // Wait
        int key;
        do {
            key = cv::waitKey(0);
        } while (key != 27);
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


//--------------------------------------
cv::Mat autoCrop(const cv::Mat& anImage)
//--------------------------------------
{
    // Convert to grey scale
    cv::Mat grey_image;
    cv::cvtColor(anImage, grey_image, cv::COLOR_BGR2GRAY);

    // Convert to binary
    cv::Mat binary_image;
    cv::threshold(grey_image, binary_image, 1, 255, cv::THRESH_BINARY);
    
    // Find contours
    std::vector<std::vector<cv::Point> > p_contour_set;
    std::vector<cv::Vec4i> p_hierarchy_set;
    
    cv::findContours(binary_image, p_contour_set, p_hierarchy_set, CV_RETR_TREE, CV_CHAIN_APPROX_SIMPLE, cv::Point(0, 0) );
    cv::Rect bounding_rectangle(cv::boundingRect(p_contour_set.front()));

    // Crop the input image using the bounding rectangle
    cv::Mat output(anImage(bounding_rectangle));

    return output;
}
