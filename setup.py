from setuptools import setup, find_packages

from packageinfo import VERSION, NAME

# Read description
with open('README.md', 'r') as readme:
    README_TEXT = readme.read()

# main setup configuration class
setup(
    name=NAME,
    version=VERSION,
    author='Material Informatics Team, Fraunhofer IWM.',
    url='www.simphony-project.eu',
    description='A dummy simulation wrapper for SimPhoNy',
    keywords='simphony, cuds, Fraunhofer IWM, dummy',
    long_description=README_TEXT,
    install_requires=[],
    packages=find_packages()
)
