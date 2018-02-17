/********************************************************************************
 *
 *    @file      LaplacianPyarmid.cxx
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


//******************************************************************************
//    Includes
//******************************************************************************
#include "LaplacianPyramid.h"


//----------------------------------
LaplacianPyramid::LaplacianPyramid()
//----------------------------------
{}


//-----------------------------------------------------------------
LaplacianPyramid::LaplacianPyramid(const GaussianPyramid& aPyramid)
//-----------------------------------------------------------------
{
    throw "Not implemented";
}


//-------------------------------------------------------------------
LaplacianPyramid::LaplacianPyramid(const LaplacianPyramid& aPyramid) :
//-------------------------------------------------------------------
        GaussianPyramid(dynamic_cast<const GaussianPyramid&>(aPyramid))
//-------------------------------------------------------------------
{
}


//--------------------------------------------------------------
LaplacianPyramid::LaplacianPyramid(const cv::Mat& anImage,
                                   unsigned int aNumberOfLevels)
//--------------------------------------------------------------
{
    setImage(anImage, aNumberOfLevels);
}


//-----------------------------------------------------------
void LaplacianPyramid::setImage(const cv::Mat& anImage,
                                unsigned int aNumberOfLevels)
//-----------------------------------------------------------
{
    // Make sure the pyramid has the right size
    m_pyramid.resize(aNumberOfLevels);

    //**************************************************************************
    // 1. (a) Create the Gaussian pyramid of anImage
    //**************************************************************************

    // Add your code here
	GaussianPyramid gaussian_pyramid(anImage, aNumberOfLevels);








    // Last image ID to have been processed
    unsigned int last_id(aNumberOfLevels);


    //**************************************************************************
    // 1. (b) Add the low resolution image in the Laplacian pyramid
    //**************************************************************************

    cv::Mat tmp = gaussian_pyramid.getLevel(--last_id);
    m_pyramid[last_id] = tmp;

    for (unsigned int i = 0; i < aNumberOfLevels - 1; ++i)
    {
        //**********************************************************************
        // 1. (c) Increase the resolution of the previous image (tmp) and
        // save it in dst
        //**********************************************************************

        // Add your code here
        cv::Mat dst;

		cv::pyrUp(tmp, dst, cv::Size(tmp.cols * 2, tmp.rows * 2));




        //**********************************************************************
        // 1. (d) Get the new source from the Gaussian pyramid
        //**********************************************************************

        // Add your code here

		tmp = gaussian_pyramid.getLevel(--last_id);




        //**********************************************************************
        // 1. (e) Store the new image in the pyramid
        //**********************************************************************

        // Add your code here

		m_pyramid[last_id] = tmp - dst;




    }
}


//--------------------------------------------------------------
cv::Mat LaplacianPyramid::reconstruct(unsigned int aLevel) const
//--------------------------------------------------------------
{
    // The level is not available
    if (aLevel >= m_pyramid.size())
    {
        throw "Level not available";
    }

    cv::Mat reconstruction;
    
    if (m_pyramid.size())
    {
        for (int i = 0; i < m_pyramid.size() - aLevel; ++i)
        {
            if (i == 0)
            {
                reconstruction = m_pyramid[m_pyramid.size() - 1 - i];
            }
            else
            {
                cv::Mat dst;
                pyrUp(reconstruction, dst, cv::Size(reconstruction.cols * 2, reconstruction.rows * 2));

                reconstruction = dst + m_pyramid[m_pyramid.size() - 1 - i];
            }
        }

        reconstruction.convertTo(reconstruction, CV_8UC3);
    }
    
    return (reconstruction);
}


//----------------------------------------------------------------------------
LaplacianPyramid& LaplacianPyramid::operator=(const GaussianPyramid& aPyramid)
//----------------------------------------------------------------------------
{
    throw "Not implemented";
}


//-----------------------------------------------------------------------------
LaplacianPyramid& LaplacianPyramid::operator=(const LaplacianPyramid& aPyramid)
//-----------------------------------------------------------------------------
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
