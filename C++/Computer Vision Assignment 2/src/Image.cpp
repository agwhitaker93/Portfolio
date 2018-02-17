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
#include "Image.h"


//------------------
Image::Image():
//------------------
        m_width(0),
        m_height(0),
        m_p_image(0)
//------------------
{
	// NOTHING TO DO HERE
}


//--------------------------------
Image::Image(const Image& anImage):
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
    m_width  = 0;
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
                	delete [] p_temp;
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

	std::string error("Pixel defined is not within bounds");
	throw(error);
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

	std::string error("Pixel defined is not within bounds");
	throw(error);
}

float Image::getSmallestValue() const
{
	float val = m_p_image[0 * m_width + 0];
	for (unsigned int y = 0; y < m_height; y++)
	{
		for (unsigned int x = 0; x < m_width; x++)
		{
			if (m_p_image[y * m_width + x] < val)
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
			if (m_p_image[y * m_width + x] > val)
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
		if (strlen(p_line) != 0)
		{
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
	}

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

void Image::writeHistogram(int numBins, const std::string & aFileName) const
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

	float min = getSmallestValue();
	float max = getLargestValue();
	float binWidth = (max - min) / numBins;
	float* bins = new float[numBins];
	long int* count = new long int[numBins];

	// Calculate the start of each bin
	for (int i = 0; i < numBins; ++i)
	{
		bins[i] = min + (i * binWidth);
		count[i] = 0;
	}

	for (unsigned int y = 0; y < m_height; ++y)
	{
		for (unsigned int x = 0; x < m_width; ++x)
		{
			float pixel_value(m_p_image[y * m_width + x]);

			for (int i = 0; i < numBins; ++i)
			{
				if (i < (numBins - 1))
				{
					if (pixel_value >= bins[i] && pixel_value < bins[i + 1])
					{
						++count[i];
					}
				}

				else
				{
					if (pixel_value >= bins[i])
					{
						++count[i];
					}
				}
			}
		}
	}

	for (int i = 0; i < numBins; ++i)
	{
		output_file << bins[i] << " " << count[i];

		if (i < (numBins - 1))
		{
			output_file << std::endl;
		}
	}

	// Close the file (optional as it is done in the destructor if needed)
	output_file.close();
}

double Image::computeNCC(Image anImage)
{
	if (m_width == anImage.m_width)
	{
		if (m_height == anImage.m_height)
		{
			double sd1(getStandardDeviation());
			double sd2(anImage.getStandardDeviation());
			double avg1(getAverage());
			double avg2(anImage.getAverage());
			int size = m_width*m_height;
			double result = 0;

			for (unsigned int y = 0; y < m_height; ++y)
			{
				for (unsigned int x = 0; x < m_width; ++x)
				{
					double pixel_value_1 = m_p_image[y * m_width + x] - avg1;
					double pixel_value_2 = anImage.m_p_image[y * m_width + x] - avg2;

					result += (pixel_value_1*pixel_value_2) / (sd1*sd2);
				}
			}

			return (result/size);
		}
	}

	std::string error("Incompatible image sizes.");
	throw(error);
}

double Image::computeSAE(Image anImage)
{
	if (m_width == anImage.m_width)
	{
		if (m_height == anImage.m_height)
		{
			double dissimilarity = 0.0;
			for (unsigned int y = 0; y < m_height; ++y)
			{
				for (unsigned int x = 0; x < m_width; ++x)
				{
					double pixel_value1(m_p_image[y * m_width + x]);
					double pixel_value2(anImage.m_p_image[y*m_width + x]);

					dissimilarity += std::abs(pixel_value1 - pixel_value2);
				}
			}

			return dissimilarity;
		}
	}

	std::string error("Incompatible image sizes.");
	throw(error);
}

Image Image::medianFilter()
{
	Image temp;
	temp.m_width = m_width;
	temp.m_height = m_height;
	temp.m_p_image = new float[m_width * m_height];

	for (unsigned int y = 0; y < m_height; ++y)
	{
		for (unsigned int x = 0; x < m_width; ++x)
		{
			std::vector<double> surrounding(getSurroundingPixels(x, y, 1));

			std::sort(surrounding.begin(), surrounding.end());

			size_t vectorSize(surrounding.size());

			temp.m_p_image[y * m_width + x] = surrounding[vectorSize / 2];
		}
	}
	return temp;
}

Image Image::gaussianFilter()
{
	Image temp;
	temp.m_width = m_width;
	temp.m_height = m_height;
	temp.m_p_image = new float[m_width * m_height];

	for (unsigned int y = 0; y < m_height; ++y)
	{
		for (unsigned int x = 0; x < m_width; ++x)
		{
			std::vector<double> surrounding(getSurroundingPixels(x, y, 1));
			// apply gaussian kernel to surrounding pixels
			std::vector<double> kernel = { 1/16., 2/16., 1/16.,
										2/16., 4/16., 2/16.,
										1/16., 2/16., 1/16. };
			

			temp.m_p_image[y * m_width + x] = spatialConvolution(surrounding, kernel);
		}
	}
	return temp;
}

Image Image::meanFilter()
{
	Image temp;
	temp.m_width = m_width;
	temp.m_height = m_height;
	temp.m_p_image = new float[m_width * m_height];

	for (unsigned int y = 0; y < m_height; ++y)
	{
		for (unsigned int x = 0; x < m_width; ++x)
		{
			std::vector<double> surrounding(getSurroundingPixels(x, y, 1));
			// apply mean kernel to surrounding pixels
			std::vector<double> kernel = { 1/9., 1/9., 1/9., 
										1/9., 1/9., 1/9.,
										1/9., 1/9., 1/9. };

			temp.m_p_image[y * m_width + x] = spatialConvolution(surrounding, kernel);
		}
	}
	return temp;
}

Image Image::laplacianFilter()
{
	Image temp;
	temp.m_width = m_width;
	temp.m_height = m_height;
	temp.m_p_image = new float[m_width * m_height];

	for (unsigned int y = 0; y < m_height; ++y)
	{
		for (unsigned int x = 0; x < m_width; ++x)
		{
			std::vector<double> surrounding(getSurroundingPixels(x, y, 1));

			// apply laplacian kernel to surrounding pixels
			std::vector<double> kernel = { 0, 1, 0,
										1, -4, 1,
										0, 1, 0 };

			temp.m_p_image[y * m_width + x] = spatialConvolution(surrounding, kernel);
		}
	}
	return temp;
}

Image Image::sobelEdge()
{
	Image temp;
	temp.m_width = m_width;
	temp.m_height = m_height;
	temp.m_p_image = new float[m_width * m_height];

	for (unsigned int y = 0; y < m_height; ++y)
	{
		for (unsigned int x = 0; x < m_width; ++x)
		{
			std::vector<double> surrounding(getSurroundingPixels(x, y, 1));

			// apply laplacian kernel to surrounding pixels
			std::vector<double> kernel1 = { 1, 2, 1,
										0, 0, 0,
										-1, -2, -1 };
			std::vector<double> kernel2 = { 1, 0, -1,
										2, 0, -2,
										1, 0, -1 };

			double magnitude = abs(spatialConvolution(surrounding,kernel1)) + abs(spatialConvolution(surrounding,kernel2));
			temp.m_p_image[y * m_width + x] = magnitude;
		}
	}
	return temp;
}

Image Image::prewittEdge()
{
	Image temp;
	temp.m_width = m_width;
	temp.m_height = m_height;
	temp.m_p_image = new float[m_width * m_height];

	for (unsigned int y = 0; y < m_height; ++y)
	{
		for (unsigned int x = 0; x < m_width; ++x)
		{
			std::vector<double> surrounding(getSurroundingPixels(x, y, 1));

			// apply laplacian kernel to surrounding pixels
			std::vector<double> kernel1 = { 1, 1, 1,
				0, 0, 0,
				-1, -1, -1 };
			std::vector<double> kernel2 = { 1, 0, -1,
				1, 0, -1,
				1, 0, -1 };

			double magnitude = abs(spatialConvolution(surrounding, kernel1)) + abs(spatialConvolution(surrounding, kernel2));
			temp.m_p_image[y * m_width + x] = magnitude;
		}
	}
	return temp;
}

Image Image::sharpen(double multiplier)
{
	Image temp(*this);
	temp = temp.gaussianFilter();

	temp = *this - temp;

	temp = *this + (temp * multiplier);

	return temp;
}

Image Image::blending(Image anImage, double aValue)
{
	Image temp;
	temp.m_width = m_width;
	temp.m_height = m_height;
	temp.m_p_image = new float[m_width * m_height];

	double blendPcnt = 1 - aValue;

	temp = (*this * blendPcnt) + (anImage * aValue);

	return temp;
}

Image Image::segmentThresh(float thresh1, float thresh2)
{
	Image temp;
	temp.m_width = m_width;
	temp.m_height = m_height;
	temp.m_p_image = new float[m_width * m_height];

	for (unsigned int y = 0; y < m_height; ++y)
	{
		for (unsigned int x = 0; x < m_width; ++x)
		{
			if (thresh1 <= m_p_image[y * m_width + x] && m_p_image[y * m_width + x] <= thresh2)
			{
				temp.m_p_image[y * m_width + x] = 1;
			}

			else
			{
				temp.m_p_image[y * m_width + x] = 0;
			}
		}
	}

	return temp;
}

double Image::getAverage() const
{
	double avg = 0;
	int size = m_width * m_height;

	for (int y = 0; y < m_height; ++y)
	{
		for (int x = 0; x < m_width; ++x)
		{
			avg += m_p_image[y*m_width + x];
		}
	}

	return avg/size;
}

double Image::getStandardDeviation() const
{
	double avg = getAverage();
	double variance = 0;
	int size = m_width * m_height;

	for (int y = 0; y < m_height; ++y)
	{
		for (int x = 0; x < m_width; ++x)
		{
			variance += std::pow(m_p_image[y*m_width + x] - avg, 2);
		}
	}

	return std::sqrt(variance/size);
}

std::vector<double> Image::getSurroundingPixels(int coord_x, int coord_y, int radius) const
{
	if (radius <= 0)
	{
		std::string error("Invalid value given for 'radius'.");
		throw(error);
	}

	std::vector<double> surroundingPixels;
	int x, y;

	for (int j = (coord_y - radius); j <= (coord_y + radius); ++j)
	{
		for (int i = (coord_x - radius); i <= (coord_x + radius); ++i)
		{
			if (j < 0)
			{
				y = 0;
			}

			else if (j > m_height - 1)
			{
				y = m_height - 1;
			}

			else
			{
				y = j;
			}

			if (i < 0)
			{
				x = 0;
			}

			else if (i > m_width - 1)
			{
				x = m_width - 1;
			}

			else
			{
				x = i;
			}

			surroundingPixels.push_back(m_p_image[y * m_width + x]);
		}
	}

	return surroundingPixels;
}

double Image::spatialConvolution(std::vector<double> surroundingPixels, std::vector<double> kernel)
{
	std::vector<double>::iterator sIter;
	std::vector<double>::iterator kIter;
	double newValue(0);

	for (sIter = surroundingPixels.begin(), kIter = kernel.begin(); kIter != kernel.end(); ++kIter, ++sIter)
	{
		newValue += *sIter * *kIter;
	}

	return newValue;
}