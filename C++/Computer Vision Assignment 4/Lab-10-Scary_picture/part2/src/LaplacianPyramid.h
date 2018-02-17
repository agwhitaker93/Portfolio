/**
 ********************************************************************************
 *
 *    @file      LaplacianPyarmid.h
 *
 *    @brief     A class to handle Laplacian Pyramids using OpenCV.
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


#ifndef __LaplacianPyarmid_h
#define __LaplacianPyarmid_h


//******************************************************************************
//    Includes
//******************************************************************************
#include "GaussianPyramid.h"


class LaplacianPyramid: public GaussianPyramid
{
public:
    // Default constructor (do nothing)
    LaplacianPyramid();

    // Build a Laplacian pyramid from a Gaussian pyramid
    LaplacianPyramid(const GaussianPyramid& aPyramid);
        
    // Copy constructor
    LaplacianPyramid(const LaplacianPyramid& aPyramid);

    // Construct a Laplacian pyramid from an image given a number of levels
    LaplacianPyramid(const cv::Mat& anImage, unsigned int aNumberOfLevels);

    // Construct a Laplacian pyramid from an image given a number of levels
    void setImage(const cv::Mat& anImage, unsigned int aNumberOfLevels);

    // Reconstruct an image at a given level
    cv::Mat reconstruct(unsigned int aLevel) const;

    // Build a Laplacian pyramid from a Gaussian pyramid
    LaplacianPyramid& operator=(const GaussianPyramid& aPyramid);

    // Copy operator
    LaplacianPyramid& operator=(const LaplacianPyramid& aPyramid);
};

#endif
