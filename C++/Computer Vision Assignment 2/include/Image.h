#ifndef IMAGE_H
#define IMAGE_H


/**
********************************************************************************
*
*	@file		Image.h
*
*	@brief		BRIEF DESCRIPTION ABOUT THE CONTENT OF THE FILE.
*
*	@version	1.0
*
*   @todo       Implement the default constructor;
*   @todo       Implement the copy constructor;
*   @todo       Implement the destructor 
*               (don't forget to release the memory if needed);
*   @todo       Add the assignment operator;
*   @todo       Implement the code to read an image from an ASCII file 
*               as follows:
*               - Each line of the ASCII file corresponds to a line of 
*                 the image;
*               - Each pixel is the ASCII file corresponds to 
*                 a floating point number;
*               - Two successive pixels in the image are separated
*                 by a space character in the ASCII file
*                 the image.
*   @todo       Implement the code to save an image in an ASCII file;
*   @todo       Implement the arithmetic operators
*               (with a float as a parameter);
*   @todo       Implement the arithmetic operators 
*               (with an image as a parameter);
*   @todo       Implement the corresponding arithmetic/assignment  operators;
*   @todo       Implement the negation operator to return the negative image;
*   @todo       Add an accessor on the width of the image;
*   @todo       Add an accessor on the height of the image;
*   @todo       Add a method returning the aspect ratio;
*   @todo       Add a method to set the value of a given pixel;
*   @todo       Add a method returning the value of a given pixel;
*   @todo       Add a method returning the smallest value in the image;
*   @todo       Add a method returning the highest value in the image.
*
*	@date		DATE HERE
*
*	@author		YOUR NAME HERE
*
*
********************************************************************************
*/

//******************************************************************************
//	Include
//******************************************************************************
#include <string>
#include <vector>
#include <sstream> // Head file for stringstream
#include <fstream> // Head file for filestream
#include <algorithm>
#include <vector>
#include <iostream>
#include <cmath>


//==============================================================================
/**
*	@class	Image
*	@brief	Image is a class to manage a greyscale image.
*/
//==============================================================================
class Image
//------------------------------------------------------------------------------
{
//******************************************************************************
public:
    //--------------------------------------------------------------------------
    /// Default constructor.
    //--------------------------------------------------------------------------
    Image();


    //------------------------------------------------------------------------
    /// Copy constructor.
    /**
    * @param anImage: the image to copy
    */
    //------------------------------------------------------------------------
    Image(const Image& anImage);
    
    
    //------------------------------------------------------------------------
    /// Destructor.
    //------------------------------------------------------------------------
    ~Image();
    
    
    //------------------------------------------------------------------------
    /// Assignment operator (also called copy operator).
    /**
    * @param anImage: the image to copy
    * @return the updated version of the current image
    */
    //------------------------------------------------------------------------
    Image& operator=(const Image& anImage);
    
    
    //------------------------------------------------------------------------
    /// Release the memory.
    //------------------------------------------------------------------------
    void destroy();
    
    
    //------------------------------------------------------------------------
    /// Addition operator. Add anImage
    /**
    * @param anImage: the image to add
    * @return the resulting image
    */
    //------------------------------------------------------------------------
    Image operator+(const Image& anImage);
    
    
    //------------------------------------------------------------------------
    /// Subtraction operator. Add anImage
    /**
    * @param anImage: the image to subtract
    * @return the resulting image
    */
    //------------------------------------------------------------------------
    Image operator-(const Image& anImage);


    //------------------------------------------------------------------------
    /// Addition assignment operator. Add anImage
    /**
    * @param anImage: the image to add
    * @return the updated version of the current image
    */
    //------------------------------------------------------------------------
    Image& operator+=(const Image& anImage);


    //------------------------------------------------------------------------
    /// Subraction assignment operator. Add anImage
    /**
    * @param anImage: the image to subtract
    * @return the updated version of the current image
    */
    //------------------------------------------------------------------------
    Image& operator-=(const Image& anImage);
    
    
    //------------------------------------------------------------------------
    /// Addition operator. Add aValue to every pixel of the image
    /**
    * @param aValue: the value to add
    * @return the resulting image
    */
    //------------------------------------------------------------------------
    Image operator+(float aValue);


    //------------------------------------------------------------------------
    /// Subtraction operator. Subtract aValue to every pixel of the image
    /**
    * @param aValue: the value to subtract
    * @return the resulting image
    */
    //------------------------------------------------------------------------
    Image operator-(float aValue);


    //------------------------------------------------------------------------
    /// Multiplication operator. Multiply every pixel of the image by aValue
    /**
    * @param aValue: the value for the multiplication
    * @return the resulting image
    */
    //------------------------------------------------------------------------
    Image operator*(float aValue);
    
    
    //------------------------------------------------------------------------
    /// Division operator. Divide every pixel of the image by aValue
    /**
    * @param aValue: the value for the division
    * @return the resulting image
    */
    //------------------------------------------------------------------------
    Image operator/(float aValue);


    //------------------------------------------------------------------------
    /// Addition operator. Add aValue to every pixel of the image
    /**
    * @param aValue: the value to add
    * @return the updated version of the current image
    */
    //------------------------------------------------------------------------
    Image& operator+=(float aValue);


    //------------------------------------------------------------------------
    /// Subtraction operator. Subtract aValue to every pixel of the image
    /**
    * @param aValue: the value to subtract
    * @return the updated version of the current image
    */
    //------------------------------------------------------------------------
    Image& operator-=(float aValue);


    //------------------------------------------------------------------------
    /// Multiplication operator. Multiply every pixel of the image by aValue
    /**
    * @param aValue: the value for the multiplication
    * @return the updated version of the current image
    */
    //------------------------------------------------------------------------
    Image& operator*=(float aValue);


    //------------------------------------------------------------------------
    /// Division operator. Divide every pixel of the image by aValue
    /**
    * @param aValue: the value for the division
    * @return the updated version of the current image
    */
    //------------------------------------------------------------------------
    Image& operator/=(float aValue);


    //------------------------------------------------------------------------
    /// Negation operator. Compute the negative of the current image.
    /**
    * @return the negative image
    */
    //------------------------------------------------------------------------
    Image operator!();
    

    //------------------------------------------------------------------------
    /// Compute the maximum pixel value in the image
    /**
    * @return the maximum pixel
    */
    //------------------------------------------------------------------------
    float getMaxValue() const;
    

    //------------------------------------------------------------------------
    /// Add aShiftValue to every pixel, then multiply every pixel
    /// by aScaleValue
    /**
    * @param aShiftValue: the shift parameter of the filter
    * @param aScaleValue: the scale parameter of the filter
    */
    //------------------------------------------------------------------------
    void shiftScaleFilter(float aShiftValue, float aScaleValue);
    
    
    //------------------------------------------------------------------------
    /// Load an image from a PGM file
    /**
    * @param aFileName: the name of the file to load
    */
    //------------------------------------------------------------------------
    void loadPGM(const char* aFileName);
    
    
    //------------------------------------------------------------------------
    /// Load an image from a PGM file
    /**
    * @param aFileName: the name of the file to load
    */
    //------------------------------------------------------------------------
    void loadPGM(const std::string& aFileName);
    
    
    //------------------------------------------------------------------------
    /// Save the image in a PGM file
    /**
    * @param aFileName: the name of the file to write
    */
    //------------------------------------------------------------------------
    void savePGM(const char* aFileName);
    
    
    //------------------------------------------------------------------------
    /// Save the image in a PGM file
    /**
    * @param aFileName: the name of the file to write
    */
    //------------------------------------------------------------------------
    void savePGM(const std::string& aFileName);

	//(accessor on the width of the image)
	unsigned int getWidth() const;

	//(accessor on the height of the image)
	unsigned int getHeight() const;

	//(method returning the aspect ratio)
	double getAspectRatio() const;

	//(method to set the value(v) of a pixel given its horizontal(i) and vertical(j) indices)
	void setPixel(unsigned int i, unsigned int j, float v);

	//(method returning the value of a pixel given its horizontal and vertical indices)
	float getPixel(unsigned int i, unsigned int j) const;

	//(A method returning the smallest value in the image)
	float getSmallestValue() const;

	//(A method returning the largest value in the image)
	float getLargestValue() const;

	void readASCII(const std::string& aFileName);
	
	void writeASCII(const std::string& aFileName) const;

	void writeHistogram(int numBins, const std::string& aFileName) const;

	double computeNCC(Image anImage);

	double computeSAE(Image anImage);

	Image medianFilter();

	Image gaussianFilter();

	Image meanFilter();

	Image laplacianFilter();

	Image sobelEdge();

	Image prewittEdge();

	Image sharpen(double multiplier);

	Image blending(Image anImage, double aValue);

	Image segmentThresh(float thresh1, float thresh2);

	double getAverage() const;

	double getStandardDeviation() const;

	std::vector<double> getSurroundingPixels(int x, int y, int radius) const;

	double spatialConvolution(std::vector<double> surroundingPixels, std::vector<double> kernel);


//******************************************************************************
private:
    /// Number of pixel along the horizontal axis
    unsigned int m_width;


    /// Number of pixel along the vertical axis
    unsigned int m_height;

    
    /// The pixel data
    float* m_p_image;
};

#endif
