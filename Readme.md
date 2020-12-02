# Privately Connecting Mobility to Infectious Diseases via Applied Cryptography

This repository contains the source code of the paper "Privately Connecting Mobility to Infectious Diseases via Applied Cryptography".

### Source code
The code is based on Microsoft SEAL (https://github.com/Microsoft/SEAL) and is compatible with Windows and Linux.
This repository contains a demo application to perform the matrix multiplication on encrypted data and to compute the challenge mask. Use `params.h` for a parametrization of the code.

### Compilation:
Execute the following commands to compile the source code:
```
git submodule update --init
cd SEAL
cmake .
make
cd ..
mkdir build
cd build
cmake ..
make
```
