#!/bin/bash

# 1. Initialize the Git repository
git init

# 2. Set the standard user credentials
git config user.name "Programmer"
git config user.email "Programmer@example.com"

# 3. Set the additional custom configuration details
# These are added to the 'user' section to group them logically
git config user.bio "I am a Git learner"
git config user.company "Teknoturf"
git config user.location "Chennai"