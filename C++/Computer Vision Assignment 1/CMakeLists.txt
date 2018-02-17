cmake_minimum_required(VERSION 2.6)


project(ICP3038-Assignment1-20162017)

# If you want to use C++ 11: 
#  i) Change 2.6 into 3.1 in the first line
# ii) Uncomment the line below
#set (CMAKE_CXX_FLAGS "-std=c++11")

# Add the include path
include_directories(include)

# Add the file needed to create the executable program
add_executable(assignment1 
    include/Image.h 
    src/Image.cpp 
    src/test.cpp)
