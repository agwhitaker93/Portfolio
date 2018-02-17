/**
********************************************************************************
*
*	@file		test-assignment2.cpp
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
//	Include
//******************************************************************************
#include <iomanip>
#include <exception>
#include <cmath>

#include "Image.h"


//-----------------------------
int main(int argc, char** argv)
//-----------------------------
{
    // Return code
    int error_code(0);
    
    // Catch exceptions
    try
    {
		// Load test images (two of size 600x400, one of size 333x300)
		std::cout << "Preparing test images." << std::endl;
        Image bandana;
		Image doggy;
        bandana.readASCII("../img/bandana.txt");
        doggy.readASCII("../img/doggy.txt");
		bandana.writeASCII("my_bandana.txt");
		doggy.writeASCII("my_doggy.txt");

        unsigned int bandana_pixels(bandana.getWidth() * bandana.getHeight());
		unsigned int doggy_pixels(doggy.getWidth() * doggy.getHeight());

        
        // Compute the histogram with a small number of bins
        // (compare with the same histogram in ImageJ)
		std::cout << "Saving histograms." << std::endl;
		bandana.writeHistogram(8, "my_bandana_histogram.txt");
		doggy.writeHistogram(8, "my_doggy_histogram.txt");
		bandana.writeHistogram(256, "my_bandana_histogram256.txt");
        
        // Load an image (lenna with salt and pepper noise)
		std::cout << "Preparing salt and pepper noise test images." << std::endl;
        Image bandana_noise;
		Image doggy_noise;
        bandana_noise.readASCII("../img/bandana_noise.txt");
		doggy_noise.readASCII("../img/doggy_noise.txt");
		bandana_noise.writeASCII("my_bandana_noise.txt");
		doggy_noise.writeASCII("my_doggy_noise.txt");

        // Compute the histogram with a small number of bins
        // (compare with the same histogram in ImageJ)
		std::cout << "Saving histograms." << std::endl;
		bandana_noise.writeHistogram(8, "my_bandana_noise_histogram.txt");
		doggy_noise.writeHistogram(8, "my_doggy_noise_histogram.txt");
        
		std::cout << std::endl << std::endl;
        
        
        
        
		//============= SAE ===============

		std::cout << "Test SAE:" << std::endl;

		// Should be ~ 0
		double SAE_test_1(bandana.computeSAE(bandana));
		double expected_results_3(0.0);
		std::cout << "SAE(bandana, bandana):  " << std::fixed << std::setprecision(2) <<
			SAE_test_1 << "   " <<
			(std::abs(SAE_test_1 - expected_results_3) < 1.0e-6 ? "SUCCESS" : "FAILURE") << std::endl;

		// Should be ~ 0
		double SAE_test_2(bandana.computeSAE(bandana_noise));
		std::cout << "SAE(bandana, bandana_noise): " << std::fixed << std::setprecision(2) <<
			SAE_test_2 << "   " <<
			(std::abs(SAE_test_2 - expected_results_3) < 1.0e-6 ? "SUCCESS" : "FAILURE") << std::endl;

		double SAE_test_3(bandana.computeSAE(!bandana));
		std::cout << "SAE(bandana, !bandana): " << std::fixed << std::setprecision(2) <<
			SAE_test_3 << "   " <<
			(std::abs(SAE_test_3 - expected_results_3) < 1.0e-6 ? "SUCCESS" : "FAILURE") << std::endl;

		double SAE_test_4(bandana.computeSAE(doggy));
		std::cout << "SAE(bandana, doggy): " << std::fixed << std::setprecision(2) <<
			SAE_test_4 << "   " <<
			(std::abs(SAE_test_4 - expected_results_3) < 1.0e-6 ? "SUCCESS" : "FAILURE") << std::endl;

		std::cout << std::endl << std::endl;





		//============= NCC ===============
        
        std::cout << "Test NCC:" << std::endl;
        
        // Should be ~ 100%
        double NCC_test_1(bandana.computeNCC((bandana * 3) + 2));
        double expected_results_1(1.0);
        std::cout << "NCC(bandana, bandana * 3 + 2):  " << std::fixed << std::setprecision(2) <<
                100.0 * NCC_test_1 << "\%   " <<
                (std::abs(NCC_test_1 - expected_results_1) < 1.0e-6?"SUCCESS":"FAILURE") << std::endl;
        
        // Should be ~ -100%
		double NCC_test_2(bandana.computeNCC((!bandana * 3) + 2));
		double expected_results_2(-1.0);
		std::cout << "NCC(bandana, -bandana * 3 + 2): " << std::fixed << std::setprecision(2) <<
			100.0 * NCC_test_2 << "\%   " <<
			(std::abs(NCC_test_2 - expected_results_2) < 1.0e-6 ? "SUCCESS" : "FAILURE") << std::endl;

		// Should not be ~ 100%
		double NCC_test_3(bandana.computeNCC(doggy));
		std::cout << "NCC(bandana, doggy): " << std::fixed << std::setprecision(2) <<
			100.0 * NCC_test_3 << "\%   " <<
			(std::abs(NCC_test_3 - expected_results_1) < 1.0e-6 ? "SUCCESS" : "FAILURE") << std::endl;

        std::cout << std::endl << std::endl;
		std::cout << "FILTER TYPE:\t" << " SAE  " << " MAE  " << "  NCC   " << "   STATUS" << std::endl;

        
        
        
        
        //======== MEDIAN FILTER ==========
        
        // Median filter on Lenna with salt and pepper noise
        Image my_bandana_noise_median_filter(bandana_noise.medianFilter());
        my_bandana_noise_median_filter.writeASCII("my_bandana_noise_median_filter.txt");
        
        // Same filter on ImageJ
        Image imagej_bandana_noise_median_filter;
        imagej_bandana_noise_median_filter.readASCII("../img/imagej_bandana_noise_median_filter.txt");
        
        // Compute the SAE/NCC between the image produced by ImageJ and the one produced by the code
        double SAE_median(imagej_bandana_noise_median_filter.computeSAE(my_bandana_noise_median_filter));
        double NCC_median(imagej_bandana_noise_median_filter.computeNCC(my_bandana_noise_median_filter));
        
        // Display image comparison metrics
        std::cout << std::fixed << std::setprecision(2) <<
                "Median      \t" <<
                SAE_median << "  " <<
                SAE_median / bandana_pixels << "  " <<
                100.0 * NCC_median << "\%   " <<
                (std::abs(NCC_median - 1.0) < 1.0e-6?"SUCCESS":"FAILURE") << std::endl;

        
        
        
        
        
        //======== GAUSSIAN FILTER ==========

        // Gaussian filter on Lenna with salt and pepper noise
        Image my_doggy_noise_gaussian_filter(doggy_noise.gaussianFilter());
        my_doggy_noise_gaussian_filter.writeASCII("my_doggy_noise_gaussian_filter.txt");

        // Same filter on ImageJ
        Image imagej_doggy_noise_gaussian_filter;
        imagej_doggy_noise_gaussian_filter.readASCII("../img/imagej_doggy_noise_gaussian_filter.txt");
        
        // Compute the SAE/NCC between the image produced by ImageJ and the one produced by the code
        double SAE_gaussian(imagej_doggy_noise_gaussian_filter.computeSAE(my_doggy_noise_gaussian_filter));
        double NCC_gaussian(imagej_doggy_noise_gaussian_filter.computeNCC(my_doggy_noise_gaussian_filter));
        
        // Display image comparison metrics
        std::cout << std::fixed << std::setprecision(2) <<
                "Gaussian      \t" <<
                SAE_gaussian << "  " <<
                SAE_gaussian / doggy_pixels << "  " <<
                100.0 * NCC_gaussian << "\%   " <<
                (std::abs(NCC_gaussian - 1.0) < 1.0e-6?"SUCCESS":"FAILURE") << std::endl;

        



        
		//======== MEAN FILTER ==========
        
        // Mean filter (also called box filter and average filter) on Lenna with salt and pepper noise
		Image my_bandana_noise_mean_filter(bandana_noise.meanFilter());
		my_bandana_noise_mean_filter.writeASCII("my_bandana_noise_mean_filter.txt");

        // Same filter on ImageJ
		Image imagej_bandana_noise_mean_filter;
		imagej_bandana_noise_mean_filter.readASCII("../img/imagej_bandana_noise_mean_filter.txt");
        
        // Compute the SAE/NCC between the image produced by ImageJ and the one produced by the code
		double SAE_mean(imagej_bandana_noise_mean_filter.computeSAE(my_bandana_noise_mean_filter));
		double NCC_mean(imagej_bandana_noise_mean_filter.computeNCC(my_bandana_noise_mean_filter));
        
        // Display image comparison metrics
		std::cout << std::fixed << std::setprecision(2) <<
			"Mean      \t" <<
			SAE_mean << "  " <<
			SAE_mean / bandana_pixels << "  " <<
			100.0 * NCC_mean << "\%   " <<
			(std::abs(NCC_mean - 1.0) < 1.0e-6 ? "SUCCESS" : "FAILURE") << std::endl;

        
        
        
        
        
        //======== LAPLACIAN FILTER ==========
        
        // Laplacian filter on Lenna
		Image my_doggy_laplacian_filter(doggy.laplacianFilter());
		my_doggy_laplacian_filter.writeASCII("my_doggy_laplacian_filter.txt");

        // Same filter on ImageJ
		Image imagej_doggy_laplacian_filter;
		imagej_doggy_laplacian_filter.readASCII("../img/imagej_doggy_laplacian_filter.txt");
        
        // Compute the SAE/NCC between the image produced by ImageJ and the one produced by the code
		double SAE_laplacian(imagej_doggy_laplacian_filter.computeSAE(my_doggy_laplacian_filter));
		double NCC_laplacian(imagej_doggy_laplacian_filter.computeNCC(my_doggy_laplacian_filter));
        
        // Display image comparison metrics
		std::cout << std::fixed << std::setprecision(2) <<
			"Laplacian      \t" <<
			SAE_laplacian << "  " <<
			SAE_laplacian / doggy_pixels << "  " <<
			100.0 * NCC_laplacian << "\%   " <<
			(std::abs(NCC_laplacian - 1.0) < 1.0e-6 ? "SUCCESS" : "FAILURE") << std::endl;

        
        
        
        //======== SOBEL EDGE DETECTOR ==========
        
        // Sobel edge detector on Lenna
		Image my_bandana_sobel_edge_detector(bandana.sobelEdge());
		my_bandana_sobel_edge_detector.writeASCII("my_bandana_sobel_edge_detector.txt");

		// Same filter on ImageJ
		Image imagej_bandana_sobel_edge_detector;
		imagej_bandana_sobel_edge_detector.readASCII("../img/imagej_bandana_sobel_edge_detector.txt");

		// Compute the SAE/NCC between the image produced by ImageJ and the one produced by the code
		double SAE_sobel_edge_detector(imagej_bandana_sobel_edge_detector.computeSAE(my_bandana_sobel_edge_detector));
		double NCC_sobel_edge_detector(imagej_bandana_sobel_edge_detector.computeNCC(my_bandana_sobel_edge_detector));

		// Display image comparison metrics
		std::cout << std::fixed << std::setprecision(2) <<
			"Sobel Edge     \t" <<
			SAE_sobel_edge_detector << "  " <<
			SAE_sobel_edge_detector / bandana_pixels << "  " <<
			100.0 * NCC_sobel_edge_detector << "\%   " <<
			(std::abs(NCC_sobel_edge_detector - 1.0) < 1.0e-6 ? "SUCCESS" : "FAILURE") << std::endl;
        
        
        
        
        
        
        //======== PREWITT EDGE DETECTOR =========
        
        // Prewitt edge detector on Lenna
		Image my_doggy_prewitt_edge_detector(doggy.prewittEdge());
		my_doggy_prewitt_edge_detector.writeASCII("my_doggy_prewitt_edge_detector.txt");

		// Same filter on ImageJ
		Image imagej_doggy_prewitt_edge_detector;
		imagej_doggy_prewitt_edge_detector.readASCII("../img/imagej_doggy_prewitt_edge_detector.txt");

		// Compute the SAE/NCC between the image produced by ImageJ and the one produced by the code
		double SAE_prewitt_edge_detector(imagej_doggy_prewitt_edge_detector.computeSAE(my_doggy_prewitt_edge_detector));
		double NCC_prewitt_edge_detector(imagej_doggy_prewitt_edge_detector.computeNCC(my_doggy_prewitt_edge_detector));

		// Display image comparison metrics
		std::cout << std::fixed << std::setprecision(2) <<
			"Prewitt Edge   \t" <<
			SAE_prewitt_edge_detector << "  " <<
			SAE_prewitt_edge_detector / doggy_pixels << "  " <<
			100.0 * NCC_prewitt_edge_detector << "\%   " <<
			(std::abs(NCC_prewitt_edge_detector - 1.0) < 1.0e-6 ? "SUCCESS" : "FAILURE") << std::endl;
        
        




		// ============= SEGMENTATION ================

		// Segmentation on bandana with thresholds arround at 33.56 and 137.48
		Image my_bandana_segmentation(bandana.segmentThresh(33.56, 137.48));
		my_bandana_segmentation.writeASCII("my_bandana_segmentation.txt");

		// Same segments in ImageJ
		Image imagej_bandana_segmentation;
		imagej_bandana_segmentation.readASCII("../img/imagej_bandana_segmentation.txt");

		// Computer the SAE/NCC between the image produced by ImageJ and the one produced by the code
		double SAE_segment(imagej_bandana_segmentation.computeSAE(my_bandana_segmentation));
		double NCC_segment(imagej_bandana_segmentation.computeNCC(my_bandana_segmentation));

		// Displa image comparison metrics
		std::cout << std::fixed << std::setprecision(2) <<
			"Segmentation \t" <<
			SAE_segment << "  " <<
			SAE_segment / bandana_pixels << "   " <<
			100.0 * NCC_segment << "\%   " <<
			(std::abs(NCC_segment - 1.0) < 1.0e-6 ? "SUCCESSS" : "FAILURE") << std::endl;




		//============== SHARPEN ================

		// Sharpen on Lenna, with value 'a' equal to 4.
		// Note that we can use use a 3x3 gaussian kernel
		Image my_doggy_sharpen(doggy.sharpen(4));
		my_doggy_sharpen.writeASCII("my_doggy_sharpen.txt");

		// Same filter on ImageJ
		Image imagej_doggy_sharpen;
		imagej_doggy_sharpen.readASCII("../img/imagej_doggy_sharpen.txt");

		// Compute the SAE/NCC between the image produced by ImageJ and the one produced by the code
		double SAE_sharpen(imagej_doggy_sharpen.computeSAE(my_doggy_sharpen));
		double NCC_sharpen(imagej_doggy_sharpen.computeNCC(my_doggy_sharpen));

		// Display image comparison metrics
		std::cout << std::fixed << std::setprecision(2) <<
			"Sharpen     \t" <<
			SAE_sharpen << "  " <<
			SAE_sharpen / doggy_pixels << "  " <<
			100.0 * NCC_sharpen << "\%   " <<
			(std::abs(NCC_sharpen - 1.0) < 1.0e-6 ? "SUCCESS" : "FAILURE") << std::endl;
        
        
        


		//============= BLENDING ================

		// Blending between bandana and doggy


		// Alpha from 0 to 1
		const unsigned int number_of_blended_images(5);
		std::cout << "Test for blending: see output images." << std::endl;
		for (unsigned int i(0); i < number_of_blended_images; ++i)
		{
			// Convert int to alpha in range [0, 1]
			double alpha(double(i) / double(number_of_blended_images - 1));

			// Blending
			Image blended_image(bandana.blending(doggy, alpha));

			// Create a file name
			std::stringstream file_name;
			file_name << "blend_" << i << ".txt";

			// Save the new image
			blended_image.writeASCII(file_name.str());
		}
    }
    // An error occured
    catch (const std::exception& error)
    {
        error_code = 1;
        std::cerr << error.what() << std::endl;
    }
    catch (const std::string& error)
    {
        error_code = 1;
        std::cerr << error << std::endl;
    }
    catch (const char* error)
    {
        error_code = 1;
        std::cerr << error << std::endl;
    }
    catch (...)
    {
        error_code = 1;
        std::cerr << "Unknown error" << std::endl;
    }

    return (error_code);
}
