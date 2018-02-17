clear, clc, close all
%prompt1 = 'Please enter a file name:';
%input(prompt1);
fileID = fopen('my_lenna_noise_histogram.txt');
formatSpec = '%f';
sizeA = [2 Inf];
B = fscanf(fileID,formatSpec,sizeA);
A = B';
bins = A(:,1);
count = A(:,2);
bar(bins,count,1);

%%
clear, clc, close all