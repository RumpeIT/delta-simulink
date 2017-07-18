% Delta-Simulink
% Copyright (c) 2013, RIDT, All rights reserved.
% 
% This project is free software; you can redistribute it and/or
% modify it under the terms of the GNU Lesser General Public
% License as published by the Free Software Foundation; either
% version 3.0 of the License, or (at your option) any later version.
% This library is distributed in the hope that it will be useful,
% but WITHOUT ANY WARRANTY; without even the implied warranty of
% MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
% Lesser General Public License for more details.
% 
% You should have received a copy of the GNU Lesser General Public
% License along with this project.
% 
% 
global Delta_Simulink_Version;
global DeltaSimulinkJARPath;
global DeltaSimulinkClasspath;
global model_dir;
global delta_dir;
global conf_file;
global product_dir;
global DeltaSimulinkJARPath;
Delta_Simulink_Version='1.0';
DeltaSimulinkJARPath='C:\MATLAB_DL\lib\delta-simulink-be-1.3.0-SNAPSHOT.jar';
DeltaSimulinkClasspath='C:\MATLAB_DL\lib\';
model_dir='C:\MATLAB_ML\Model\';
delta_dir='C:\MATLAB_ML\Delta\';
conf_file='C:\MATLAB_ML\DefaultConfig.delta';
product_dir='C:\MATLAB_ML\Product\';
appendClassPath(DeltaSimulinkJARPath);
warning off MATLAB:dispatcher:nameConflict
