/**
********************************************************************************
*
*	@file		Image.cpp
*
*	@brief		BRIEF DESCRIPTION ABOUT THE CONTENT OF THE FILE.
*
*	@version	1.0
*
*	@date		DATE HERE
*
*	@author		YOUR NAME HERE
*
*
********************************************************************************
*/


//******************************************************************************
//	Define
//******************************************************************************
#define LINE_SIZE 10000


//******************************************************************************
//	Include
//******************************************************************************
#include <sstream> // Head file for stringstream
#include <fstream> // Head file for filestream
#include <algorithm>
#include <vector>
#include<iostream>

#include "Image.h"


//------------------
Image::Image() :
	//------------------
	m_width(0),
	m_height(0),
	m_p_image(0)
	//------------------
{
	// NOTHING TO DO HERE
}


//--------------------------------
Image::Image(const Image& anImage) :
	//--------------------------------
	m_width(anImage.m_width),
	m_height(anImage.m_height),
	m_p_image(new float[m_width*m_height])
	//--------------------------------
{
	// IT IS THE CONSTRUCTOR, USE AN INITIALISATION LIST
	// ADD CODE HERE TO COPY THE DATA
	std::copy(anImage.m_p_image, anImage.m_p_image + (m_width*m_height), m_p_image);
}


//-------------
Image::~Image()
//-------------
{
	// ADD CODE HERE TO RELEASE THE MEMORY

	// Memory has been dynamically allocated
	if (m_p_image)
	{
		// Release the memory
		delete[] m_p_image;
	}
}


//-------------------
void Image::destroy()
//-------------------
{
	// Memory has been dynamically allocated
	if (m_p_image)
	{
		// Release the memory
		delete[] m_p_image;

		// Make sure the pointer is reset to NULL
		m_p_image = 0;
	}

	// There is no pixel in the image
	m_width = 0;
	m_height = 0;

}


//-------------------------------------------
Image& Image::operator=(const Image& anImage)
//-------------------------------------------
{
	destroy();
	// ADD CODE HERE TO COPY THE DATA
	m_width = anImage.m_width;
	m_height = anImage.m_height;
	m_p_image = new float[m_width * m_height];
	std::copy(anImage.m_p_image, anImage.m_p_image + (m_width*m_height), m_p_image);
	return *this;
}


//------------------------------------------
Image Image::operator+(const Image& anImage)
//------------------------------------------
{
	// ADD CODE HERE TO ADD TWO IMAGES TO EACH OTHER,
	// AND RETURN THE RESULTING IMAGE
	Image temp;
	temp.m_width = std::min(m_width, anImage.m_width);
	temp.m_height = std::min(m_height, anImage.m_height);
	temp.m_p_image = new float[temp.m_width*temp.m_height];

	for (unsigned int y = 0; y < temp.m_height; y++)
	{
		for (unsigned int x = 0; x < temp.m_width; x++)
		{
			//std::cout << "X: " << x << " Y: " << y << std::endl;
			temp.m_p_image[y * temp.m_width + x] = m_p_image[y * m_width + x] + anImage.m_p_image[y * anImage.m_width + x];
		}
	}

	return temp;
}


//------------------------------------------
Image Image::operator-(const Image& anImage)
//------------------------------------------
{
	// ADD CODE HERE TO SUBTRACT AN anImage TO THE CURRENT ONE,
	// AND RETURN THE RESULTING IMAGE

	Image temp;
	temp.m_width = std::min(m_width, anImage.m_width);
	temp.m_height = std::min(m_height, anImage.m_height);
	temp.m_p_image = new float[temp.m_width*temp.m_height];

	for (unsigned int y = 0; y < temp.m_height; y++)
	{
		for (unsigned int x = 0; x < temp.m_width; x++)
		{
			//std::cout << "X: " << x << " Y: " << y << std::endl;
			temp.m_p_image[y * temp.m_width + x] = m_p_image[y * m_width + x] - anImage.m_p_image[y * anImage.m_width + x];
		}
	}

	return temp;
}


//--------------------------------------------
Image& Image::operator+=(const Image& anImage)
//--------------------------------------------
{
	// ADD CODE HERE TO ADD TWO IMAGES TO EACH OTHER,
	// SAVE THE RESULT IN THE CURRENT IMAGE,
	// AND RETURN A REFERENCE TO THE CURRENT IMAGE

	int temp_m_width = std::min(m_width, anImage.m_width);
	int temp_m_height = std::min(m_height, anImage.m_height);
	float* temp_m_p_image = new float[temp_m_width*temp_m_height];

	for (int y = 0; y < temp_m_height; y++)
	{
		for (int x = 0; x < temp_m_width; x++)
		{
			//std::cout << "X: " << x << " Y: " << y << std::endl;
			temp_m_p_image[y * temp_m_width + x] = m_p_image[y * m_width + x] + anImage.m_p_image[y * anImage.m_width + x];
		}
	}

	m_width = temp_m_width;
	m_height = temp_m_height;
	std::copy(temp_m_p_image, temp_m_p_image + (m_width*m_height), m_p_image);

	return *this;
}


//--------------------------------------------
Image& Image::operator-=(const Image& anImage)
//--------------------------------------------
{
	// ADD CODE HERE TO SUBTRACT AN anImage TO THE CURRENT ONE,
	// SAVE THE RESULT IN THE CURRENT IMAGE,
	// AND RETURN A REFERENCE TO THE CURRENT IMAGE

	int temp_m_width = std::min(m_width, anImage.m_width);
	int temp_m_height = std::min(m_height, anImage.m_height);
	float* temp_m_p_image = new float[temp_m_width*temp_m_height];

	for (unsigned int y = 0; y < temp_m_height; y++)
	{
		for (unsigned int x = 0; x < temp_m_width; x++)
		{
			temp_m_p_image[y * temp_m_width + x] = m_p_image[y * m_width + x] - anImage.m_p_image[y * anImage.m_width + x];
		}
	}

	m_width = temp_m_width;
	m_height = temp_m_height;
	std::copy(temp_m_p_image, temp_m_p_image + (m_width*m_height), m_p_image);

	return *this;
}


//----------------------------------
Image Image::operator+(float aValue)
//----------------------------------
{
	// ADD CODE HERE TO ADD aValue TO EACH PIXEL OF THE CURRENT IMAGE,
	// AND RETURN THE RESULTING IMAGE

	Image temp;
	temp.m_width = m_width;
	temp.m_height = m_height;
	int size = temp.m_width*temp.m_height;
	temp.m_p_image = new float[size];

	for (int i = 0; i < size; i++)
	{
		temp.m_p_image[i] = m_p_image[i] + aValue;
	}

	return temp;
}


//----------------------------------
Image Image::operator-(float aValue)
//----------------------------------
{
	// ADD CODE HERE TO SUBTRACT aValue FROM EACH PIXEL OF THE CURRENT IMAGE,
	// AND RETURN THE RESULTING IMAGE

	Image temp;
	temp.m_width = m_width;
	temp.m_height = m_height;
	int size = temp.m_width*temp.m_height;
	temp.m_p_image = new float[size];

	for (int i = 0; i < size; i++)
	{
		temp.m_p_image[i] = m_p_image[i] - aValue;
	}

	return temp;
}


//----------------------------------
Image Image::operator*(float aValue)
//----------------------------------
{
	// ADD CODE HERE TO MULTIPLY aValue TO EACH PIXEL OF THE CURRENT IMAGE,
	// AND RETURN THE RESULTING IMAGE

	Image temp;
	temp.m_width = m_width;
	temp.m_height = m_height;
	int size = temp.m_width*temp.m_height;
	temp.m_p_image = new float[size];

	for (int i = 0; i < size; i++)
	{
		temp.m_p_image[i] = m_p_image[i] * aValue;
	}

	return temp;
}


//----------------------------------
Image Image::operator/(float aValue)
//----------------------------------
{
	// ADD CODE HERE TO DIVIDE EACH PIXEL OF THE CURRENT IMAGE BY aValue,
	// AND RETURN THE RESULTING IMAGE

	Image temp;
	temp.m_width = m_width;
	temp.m_height = m_height;
	int size = temp.m_width*temp.m_height;
	temp.m_p_image = new float[size];

	for (int i = 0; i < size; i++)
	{
		if (aValue != 0)
		{
			temp.m_p_image[i] = m_p_image[i] / aValue;
		}
	}

	return temp;
}


//-----------------------------------
Image& Image::operator+=(float aValue)
//-----------------------------------
{
	// ADD CODE HERE TO ADD aValue TO EACH PIXEL OF THE CURRENT IMAGE,
	// SAVE THE RESULT IN THE CURRENT IMAGE,
	// AND RETURN A REFERENCE TO THE CURRENT IMAGE

	int size = m_width * m_height;

	for (int i = 0; i < size; i++)
	{
		m_p_image[i] += aValue;
	}

	return *this;
}


//------------------------------------
Image& Image::operator-=(float aValue)
//------------------------------------
{
	// ADD CODE HERE TO SUBTRACT aValue FROM EACH PIXEL OF THE CURRENT IMAGE,
	// SAVE THE RESULT IN THE CURRENT IMAGE,
	// AND RETURN A REFERENCE TO THE CURRENT IMAGE

	int size = m_width * m_height;

	for (int i = 0; i < size; i++)
	{
		m_p_image[i] -= aValue;
	}

	return *this;
}


//------------------------------------
Image& Image::operator*=(float aValue)
//------------------------------------
{
	// ADD CODE HERE TO DIVIDE EACH PIXEL OF THE CURRENT IMAGE BY aValue
	// SAVE THE RESULT IN THE CURRENT IMAGE,
	// AND RETURN A REFERENCE TO THE CURRENT IMAGE

	int size = m_width * m_height;

	for (int i = 0; i < size; i++)
	{
		m_p_image[i] *= aValue;
	}

	return *this;
}


//------------------------------------
Image& Image::operator/=(float aValue)
//------------------------------------
{
	// ADD CODE HERE TO SUBTRACT aValue FROM EACH PIXEL OF THE CURRENT IMAGE,
	// SAVE THE RESULT IN THE CURRENT IMAGE,
	// AND RETURN A REFERENCE TO THE CURRENT IMAGE

	int size = m_width * m_height;

	for (int i = 0; i < size; i++)
	{
		m_p_image[i] /= aValue;
	}

	return *this;
}


//----------------------
Image Image::operator!()
//----------------------
{
	// ADD CODE HERE TO COMPUTE THE NEGATIVE IMAGE OF THE CURRENT IMAGE,
	// AND RETURN THE RESULTING IMAGE

	Image temp;
	temp.m_width = m_width;
	temp.m_height = m_height;
	int size = m_width * m_height;
	temp.m_p_image = new float[size];
	float range = getLargestValue() - getSmallestValue();

	for (int i = 0; i < size; i++)
	{
		temp.m_p_image[i] = range - m_p_image[i];
	}

	return temp;
}


//------------------------------
float Image::getMaxValue() const
//------------------------------
{
	return (*std::max_element(&m_p_image[0], &m_p_image[m_width * m_height]));
}


//----------------------------------------------------------------
void Image::shiftScaleFilter(float aShiftValue, float aScaleValue)
//----------------------------------------------------------------
{
	// Process every pixel of the image
	for (unsigned int i = 0; i < m_width * m_height; ++i)
	{
		// Apply the shilft/scale filter
		m_p_image[i] = (m_p_image[i] + aShiftValue) * aScaleValue;
	}
}


//----------------------------------------
void Image::loadPGM(const char* aFileName)
//----------------------------------------
{
	// Open the file
	std::ifstream input_file(aFileName, std::ifstream::binary);

	// The file does not exist
	if (!input_file.is_open())
	{
		// Build the error message
		std::stringstream error_message;
		error_message << "Cannot open the file \"" << aFileName << "\". It does not exist";

		// Throw an error
		throw (error_message.str());
	}
	// The file is open
	else
	{
		// Release the memory if necessary
		destroy();

		// Variable to store a line
		char p_line_data[LINE_SIZE];

		// Get the first line
		input_file.getline(p_line_data, LINE_SIZE);

		// Get the image type
		std::string image_type(p_line_data);

		// Valid ASCII format
		if ((image_type[0] == 'P' || image_type[0] == 'p') && image_type[1] == '2')
		{
			// Variable to save the max value
			int max_value(-1);

			// There is data to read
			unsigned int pixel_count(0);
			while (input_file.good())
			{
				// Get the new line
				input_file.getline(p_line_data, LINE_SIZE);

				// It is not a comment
				if (p_line_data[0] != '#')
				{
					// Store the line in a stream
					std::stringstream stream_line;
					stream_line << std::string(p_line_data);

					// The memory is not allocated
					if (!m_p_image && !m_width && !m_height)
					{
						// Load the image size
						stream_line >> m_width >> m_height;

						// Alocate the memory
						m_p_image = new float[m_width * m_height];

						// Out of memory
						if (!m_p_image)
						{
							throw ("Out of memory");
						}
					}
					// The max value is not set
					else if (max_value < 0)
					{
						// Get the max value;
						stream_line >> max_value;
					}
					// Read the pixel data
					else
					{
						// Process all the pixels of the line
						while (stream_line.good())
						{
							// Get the pixel value
							int pixel_value(-1);
							stream_line >> pixel_value;
							// The pixel exists
							if (pixel_count < m_width * m_height)
							{
								m_p_image[pixel_count++] = pixel_value;
							}
						}
					}
				}
			}
		}
		// Valid binary format
		else if ((image_type[0] == 'P' || image_type[0] == 'p') && image_type[1] == '5')
		{
			// Variable to save the max value
			int max_value(-1);

			// There is data to read
			unsigned int pixel_count(0);
			while (input_file.good() && !pixel_count)
			{
				// Process as an ASCII file
				if (!m_width || !m_height || max_value < 0)
				{
					// Get the new line
					input_file.getline(p_line_data, LINE_SIZE);

					// It is not a comment
					if (p_line_data[0] != '#')
					{
						// Store the line in a stream
						std::stringstream stream_line;
						stream_line << std::string(p_line_data);

						// The memory is not allocated
						if (!m_p_image && !m_width && !m_height)
						{
							// Load the image size
							stream_line >> m_width >> m_height;

							// Alocate the memory
							m_p_image = new float[m_width * m_height];

							// Out of memory
							if (!m_p_image)
							{
								throw ("Out of memory");
							}
						}
						// The max value is not set
						else
						{
							// Get the max value;
							stream_line >> max_value;
						}
					}
				}
				// Read the pixel data
				else
				{
					unsigned char* p_temp(new unsigned char[m_width * m_height]);

					// Out of memory
					if (!p_temp)
					{
						throw ("Out of memory");
					}

					input_file.read(reinterpret_cast<char*>(p_temp), m_width * m_height);

					for (unsigned int i(0); i < m_width * m_height; ++i)
					{
						++pixel_count;
						m_p_image[i] = p_temp[i];
					}
					delete[] p_temp;
				}
			}
		}
		// Invalid format
		else
		{
			// Build the error message
			std::stringstream error_message;
			error_message << "Invalid file (\"" << aFileName << "\")";

			// Throw an error
			throw (error_message.str());
		}
	}
}


//-----------------------------------------------
void Image::loadPGM(const std::string& aFileName)
//-----------------------------------------------
{
	loadPGM(aFileName.data());
}


//----------------------------------------
void Image::savePGM(const char* aFileName)
//----------------------------------------
{
	// Open the file
	std::ofstream output_file(aFileName);

	// The file does not exist
	if (!output_file.is_open())
	{
		// Build the error message
		std::stringstream error_message;
		error_message << "Cannot create the file \"" << aFileName << "\"";

		// Throw an error
		throw (error_message.str());
	}
	// The file is open
	else
	{
		// Set the image type
		output_file << "P2" << std::endl;

		// Print a comment
		output_file << "# ICP3038 -- Assignment 1 -- 2015/2016" << std::endl;

		// The image size
		output_file << m_width << " " << m_height << std::endl;

		// The get the max value
		output_file << std::min(255, std::max(0, int(getMaxValue()))) << std::endl;

		// ImageJ output
		//output_file << 255 << std::endl;

		// Process every line
		for (unsigned int j = 0; j < m_height; ++j)
		{
			// Process every column
			for (unsigned int i = 0; i < m_width; ++i)
			{
				// Process the pixel
				int pixel_value(m_p_image[j * m_width + i]);
				pixel_value = std::max(0, pixel_value);
				pixel_value = std::min(255, pixel_value);

				output_file << pixel_value;

				// It is not the last pixel of the line
				if (i < (m_width - 1))
				{
					output_file << " ";
				}
			}

			// It is not the last line of the image
			if (j < (m_height - 1))
			{
				output_file << std::endl;
			}
		}
	}
}


//-----------------------------------------------
void Image::savePGM(const std::string& aFileName)
//-----------------------------------------------
{
	savePGM(aFileName.data());
}

//------------------------
// Functions I've added
//------------------------
unsigned int Image::getWidth() const
{
	unsigned int width = m_width;
	return width;
}

unsigned int Image::getHeight() const
{
	unsigned int height = m_height;
	return height;
}

double Image::getAspectRatio() const
{
	double ratio = m_width / m_height;
	return ratio;
}

void Image::setPixel(unsigned int i, unsigned int j, float v)
{
	if (i >= m_width && i <= 0)
	{
		if (j >= m_height && j <= 0)
		{
			m_p_image[j * m_width + i] = v;
		}
	}
}

float Image::getPixel(unsigned int i, unsigned int j) const
{
	if (i >= m_width && i <= 0)
	{
		if (j >= m_height && j <= 0)
		{
			float pixel = m_p_image[j * m_width + i];
			return pixel;
		}
	}
	return NULL;
}

float Image::getSmallestValue() const
{
	float val = m_p_image[0 * m_width + 0];

	for (unsigned int y = 0; y < m_height; y++)
	{
		for (unsigned int x = 0; x < m_width; x++)
		{
			if (m_p_image[y*m_width + x] < val)
			{
				val = m_p_image[y * m_width + x];
			}
		}
	}

	return val;
}

float Image::getLargestValue() const
{
	float val = m_p_image[0 * m_width + 0];

	for (unsigned int y = 0; y < m_height; y++)
	{
		for (unsigned int x = 0; x < m_width; x++)
		{
			if (m_p_image[y*m_width + x] > val)
			{
				val = m_p_image[y * m_width + x];
			}
		}
	}

	return val;
}

void Image::readASCII(const std::string & aFileName)
{
	// Open the file to read the data
	std::ifstream input_file(aFileName);

	// The file is not open
	if (!input_file.is_open())
	{
		// Print an error message
		std::stringstream error_message;
		error_message << "Cannot open " << aFileName << " in writing mode" << std::endl;

		// Return an error code
		throw (error_message.str());
	}

	destroy();

	// Create the string that can contain many many characters
	char p_line[LINE_SIZE];
	std::vector<float> p_array;
	bool first_run = true;
	while (input_file.good())
	{
		// Read the first line
		input_file.getline(p_line, LINE_SIZE);

		// Convert the string into a stream
		std::stringstream line_as_stream;
		line_as_stream << p_line;
		double temp_value = 0.0;
		while (line_as_stream >> temp_value)
		{
			p_array.push_back(temp_value);

			if (first_run)
			{
				m_width++;
			}
		}
		m_height++;

		first_run = false;
	}

	//m_height--;
	std::cout << "Height: " << m_height << " Width: " << m_width << std::endl;
	m_p_image = new float[m_width * m_height];
	std::copy(std::begin(p_array), std::end(p_array), m_p_image);
}

void Image::writeASCII(const std::string & aFileName) const
{
	// Declare the output file stream and open it as an ASCII file
	std::ofstream output_file(aFileName);

	// The file is not open
	if (!output_file.is_open())
	{
		// Print an error message
		std::stringstream error_message;
		error_message << "Cannot open " << aFileName << " in writing mode" << std::endl;

		// Return an error code
		throw (error_message.str());
	}

	// Write image to file
	for (unsigned int j = 0; j < m_height; ++j)
	{
		for (unsigned int i = 0; i < m_width; ++i)
		{
			float pixel_value(m_p_image[j * m_width + i]);

			output_file << pixel_value;

			// There are more pixels on this line
			if (i < (m_width - 1))
			{
				output_file << " ";
			}
		}

		// There are more lines in the image
		if (j < (m_height - 1))
		{
			output_file << std::endl;
		}
	}

	// Close the file (optional as it is done in the destructor if needed)
	output_file.close();
}
