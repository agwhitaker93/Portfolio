/**
********************************************************************************
*
*    @file      GaussianPyramid.cxx
*
*    @brief     A classto handle Gaussian Pyramids using OpenCV.
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
#include "GaussianPyramid.h"


//--------------------------------
GaussianPyramid::GaussianPyramid()
//--------------------------------
{}


//---------------------------------------------------------------
GaussianPyramid::GaussianPyramid(const GaussianPyramid& aPyramid)
//---------------------------------------------------------------
{
    // Copy all the levels
    for (std::vector<cv::Mat>::const_iterator ite(aPyramid.m_pyramid.begin());
        ite != aPyramid.m_pyramid.end();
        ++ite)
    {
        // Add the clone to the vector
        // See http://docs.opencv.org/3.2.0/d6/d6d/tutorial_mat_the_basic_image_container.html
        // For the difference between operator=, copyTo, and clone.
        m_pyramid.push_back(ite->clone());
    }
}


//------------------------------------------------------------
GaussianPyramid::GaussianPyramid(const cv::Mat& anImage,
                                 unsigned int aNumberOfLevels)
//------------------------------------------------------------
{
    setImage(anImage, aNumberOfLevels);
}


//----------------------------------------------------------
void GaussianPyramid::setImage(const cv::Mat& anImage,
                               unsigned int aNumberOfLevels)
//----------------------------------------------------------
{
    // Make sure the pyramid is empty
    m_pyramid.clear();

    //**************************************************************************
    // 1. (a) Make sure the size is compatible with pyramids
    //**************************************************************************

    // Add your code here to use nextPowerOfTwo(...)
    int width = nextPowerOfTwo(anImage.cols);
    int height = nextPowerOfTwo(anImage.rows);

    // Convert the image if necessary
    cv::Mat tmp;
    if (anImage.channels() == 1)
    {
        cv::cvtColor(anImage, tmp, CV_GRAY2RGB);
    }
    else if (anImage.channels() != 3)
    {
        throw "Invalid file format. Only greyscale and RGB images are supported.";
    }
    else
    {
        tmp = anImage;
    }
    tmp.convertTo(tmp, CV_32FC3);






    //**************************************************************************
    // 1. (b) Pad the image if necessary
    //**************************************************************************

    // Add your code here
	tmp = setCanvasSize(tmp, width, height);

    //**************************************************************************
    // 1. (c) Add the original image
    //**************************************************************************

    // Add your code here
	m_pyramid.push_back(tmp.clone());





    // Add the other sizes
    for (unsigned int i(1); i < aNumberOfLevels; ++i)
    {
        if (tmp.cols >= 2 && tmp.rows >= 2)
        {
            //******************************************************************
            // 1. (d) Add level of decreasing size in the pyramid
            //******************************************************************

            // Add your code here
			cv:pyrDown(tmp, tmp, cv::Size(tmp.cols / 2, tmp.rows / 2));

			m_pyramid.push_back(tmp);


        }
    }
}


//--------------------------------------------------------------------------
GaussianPyramid& GaussianPyramid::operator=(const GaussianPyramid& aPyramid)
//--------------------------------------------------------------------------
{
    // Empty the current pyramid
    m_pyramid.clear();

    // Copy all the levels
    for (std::vector<cv::Mat>::const_iterator ite(aPyramid.m_pyramid.begin());
        ite != aPyramid.m_pyramid.end();
        ++ite)
    {
        // Add the clone to the vector
        // See http://docs.opencv.org/3.2.0/d6/d6d/tutorial_mat_the_basic_image_container.html
        // For the difference between operator=, copyTo, and clone.
        m_pyramid.push_back(ite->clone());
    }

    return (*this);
}


//----------------------------------------------------------
void GaussianPyramid::display(const char* aWindowName) const
//----------------------------------------------------------
{
    // Make sure the pyramid is not empty
    if (m_pyramid.size())
    {
        // Edge around each level
        int EDGE = 2;
        
        // Find the image size
        unsigned int width(EDGE);
        unsigned int height(getLevel(0).rows * 1.5 + EDGE * 3.0);
        
        for (std::vector<cv::Mat>::const_iterator ite(m_pyramid.begin() + 1);
             ite != m_pyramid.end();
             ++ite)
        {
            width += ite->cols + EDGE;
        }
        
        // Make sure the first level can be displayed
        if (width < getLevel(0).cols + EDGE * 2)
        {
            width =  getLevel(0).cols + EDGE * 2;
        }
        
        // Create an image where to store the pyramid
        cv::Mat pyramid_image(height, width, CV_8UC3);
        int x_offset(EDGE);
        int y_offset(EDGE);
        
        // Display all the levels
        for (unsigned int i = 0; i < m_pyramid.size(); ++i)
        {
            // Normalise the current levelbetween 0 and 255
            cv::Mat tmp_image;
            cv::normalize(getLevel(i), tmp_image, 0, 255, cv::NORM_MINMAX, CV_8UC3);
            
            // Store the current level into the global image
            cv::Mat targetROI = pyramid_image(cv::Rect(x_offset, y_offset, tmp_image.cols, tmp_image.rows));
            tmp_image.copyTo(targetROI);
            
            // Update the offset
            if (i == 0)
            {
                y_offset += tmp_image.rows + EDGE;
            }
            else
            {
                x_offset += tmp_image.cols + EDGE;
            }
        }
        
        // Create window
        cv::namedWindow(aWindowName, CV_WINDOW_AUTOSIZE);
        
        // Show the image
        cv::imshow(aWindowName, pyramid_image);
    }
}


//-----------------------------------------------------
unsigned int GaussianPyramid::getNumberOfLevels() const
//-----------------------------------------------------
{
    return (m_pyramid.size());
}


//-----------------------------------------------------------------
const cv::Mat& GaussianPyramid::getLevel(unsigned int aLevel) const
//-----------------------------------------------------------------
{
    // The level is not available
    if (aLevel >= m_pyramid.size())
    {
        throw "Level not available";
    }

    // Return the level
    return (m_pyramid[aLevel]);
}


//-----------------------------------------------------
cv::Mat& GaussianPyramid::getLevel(unsigned int aLevel)
//-----------------------------------------------------
{
    // The level is not available
    if (aLevel >= m_pyramid.size())
    {
        throw "Level not available";
    }

    // Return the level
    return (m_pyramid[aLevel]);
}


//-------------------------------------------------------------------
const cv::Mat& GaussianPyramid::operator[](unsigned int aLevel) const
//-------------------------------------------------------------------
{
    return (getLevel(aLevel));
}


//-------------------------------------------------------
cv::Mat& GaussianPyramid::operator[](unsigned int aLevel)
//-------------------------------------------------------
{
    return (getLevel(aLevel));
}


//-------------------------------------------------------------------
const cv::Mat& GaussianPyramid::operator()(unsigned int aLevel) const
//-------------------------------------------------------------------
{
    return (getLevel(aLevel));
}


//-------------------------------------------------------
cv::Mat& GaussianPyramid::operator()(unsigned int aLevel)
//-------------------------------------------------------
{
    return (getLevel(aLevel));
}


//----------------------------------------------------------------
cv::Mat GaussianPyramid::setCanvasSize(const cv::Mat& anImage,
                                       unsigned int aWidth,
                                       unsigned int aHeight) const
//----------------------------------------------------------------
{
    cv::Mat tmp(aHeight, aWidth, anImage.type());
    tmp.setTo(cv::Scalar::all(0));
    anImage.copyTo(tmp(cv::Rect(0, 0, anImage.cols, anImage.rows)));

    return (tmp);
}


//---------------------------------------------------
int GaussianPyramid::nextPowerOfTwo(int aValue) const
//---------------------------------------------------
{
    //**************************************************************************
    // 2. If aValue is a power of two, return aValue, if not return the next
    // power of two:
    //
    //  GaussianPyramid::nextPowerOfTwo(100) returns 128.
    //
    //  GaussianPyramid::nextPowerOfTwo(128) returns 128.
    //
    //  GaussianPyramid::nextPowerOfTwo(129) returns 256.
    //
    //**************************************************************************

    // Add your code here
	int power = 1;
	while (power != aValue) {
		if (power > aValue) {
			aValue = power;
		} 
		else if (power < aValue) {
			power *= 2;
		}
	}
    
    return power;
}
