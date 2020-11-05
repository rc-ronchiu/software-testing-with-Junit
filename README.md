#Purpose
In this porject, I practiced how to implement Junit against a program. Moreover, I also learned and implemented the theories of software testing such as input partitioning, boundary value analysis, and coverage-based testing. This help me understanding the science of software testing.

#How to run this program with the unitest
To compile, run:

   ant compile_orig

To run the test scripts on the original (hopefully non-faulty) implementation, use:

   ant test -Dprogram="original" -Dtest="Partitioning"

or

   ant test -Dprogram="original" -Dtest="Boundary"

To run a test script on the first mutant, use:

   ant test -Dprogram="mutant-1" -Dtest="Boundary"

To clean all class files, run:

   ant clean
