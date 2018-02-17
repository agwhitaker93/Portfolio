/**
********************************************************************************
*
*    @file      GaussianPyramid.h
*
*    @brief     A class to handle Gaussian Pyramids using OpenCV.
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


#ifndef __GaussianPyarmid_h
#define __GaussianPyarmid_h


//******************************************************************************
//    Includes
//******************************************************************************
#include <vector> // To store images in a STL vector

#include <opencv2/opencv.hpp> // Main OpenCV header



class GaussianPyramid
{
public:
    // Default constructor (do nothing)
    GaussianPyramid();

    // Copy constructor
    GaussianPyramid(const GaussianPyramid& aPyramid);

    // Construct a Gaussian pyramid from an image given a number of levels
    GaussianPyramid(const cv::Mat& anImage, unsigned int aNumberOfLevels);

    // Construct a Gaussian pyramid from an image given a number of levels
    void setImage(const cv::Mat& anImage, unsigned int aNumberOfLevels);

    // Copy operator
    GaussianPyramid& operator=(const GaussianPyramid& aPyramid);

    // Display a pyramid
    void display(const char* aWindowName = "Pyramid") const;

    // Accessor on the number of levels in the pyramid
    unsigned int getNumberOfLevels() const;

    // Accessor on a given level of the pyramid
    const cv::Mat& getLevel(unsigned int aLevel) const;

    // Accessor on a given level of the pyramid
    cv::Mat& getLevel(unsigned int aLevel);

    // Accessor on a given level of the pyramid
    const cv::Mat& operator[](unsigned int aLevel) const;

    // Accessor on a given level of the pyramid
    cv::Mat& operator[](unsigned int aLevel);

    // Accessor on a given level of the pyramid
    const cv::Mat& operator()(unsigned int aLevel) const;

    // Accessor on a given level of the pyramid
    cv::Mat& operator()(unsigned int aLevel);


protected:
    // Pad the image with black if needed
    cv::Mat setCanvasSize(const cv::Mat& anImage,
        unsigned int aWidth, 
        unsigned int aHeight) const;

    // Get the next power of two if aValue is not a power of two.
    // If it is, return aValue
    int nextPowerOfTwo(int aValue) const;

    // Store every level of the pyramid, m_pyramid[0] has the highest resolution
    std::vector<cv::Mat> m_pyramid;
};

#endif
