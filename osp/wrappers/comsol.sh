#!/bin/bash
#
#$ -cwd
#$ -V
#$ -j y
#$ -S /bin/bash
#
# Define a list of queue instances which may be used to execute this job
#$ -q all.q@compute-0-1.local
#
# Set the Parallel Environment and number of nodes.
#$ -pe orte 1
#
# NUMPROC=NODES x vs  or NUMPROC=NODES
# NUMPROC=6
# EXECUTABLE
#EXECUTABLE=fluent
#
# Put your Job commands here.
#------------------------------------------------

# Use
# mpirun./xhpl -np $NUMPROC $EXECUTABLE
# for Gigabit network instead of Myrinet network

nohup comsol batch -tmpdir /output_directory -inputfile Transport_and_adsorption.mph -outputfile test.mph > out.log &  


#------------------------------------------------

