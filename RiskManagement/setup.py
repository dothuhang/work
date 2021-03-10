import setuptools

with open("README.md", "r", encoding="utf-8") as fh:
    long_description = fh.read()

setuptools.setup(
    name="package-DO-THU-HANG", # Replace with your own username
    version="0.0.1",
    author="Thu-Hang DO",
    author_email="dothuhang156@gmail.com",
    description="Risk Management Tools package",
    long_description=long_description,
    long_description_content_type="text/markdown",
    url="https://github.com/dothuhang/work",
    project_urls={
        "Bug Tracker": "https://github.com/dothuhang/work/tree/master/RiskManagement",
    },
    classifiers=[
        "Programming Language :: Python :: 3",
        "License :: OSI Approved :: MIT License",
        "Operating System :: OS Independent",
    ],
    packages=setuptools.find_packages(),
    python_requires=">=3.6",
)