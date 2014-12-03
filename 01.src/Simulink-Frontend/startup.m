% Delta-Simulink
% Copyright (c) 2013, RIT, All rights reserved.
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

% addpath('C:\MATLAB_DL\Simulink-Frontend');
addpath('D:\WORKSPACE\OpenDeltaSimulin\01.src\Simulink-Frontend');
DeltaSimulinkJARPath='C:\MATLAB_DL\lib\delta-simulink-be-1.3.0-SNAPSHOT.jar';
DeltaSimulinkClasspath='C:\MATLAB_DL\lib\';
model_dir='C:\MATLAB\Model';
delta_dir='C:\MATLAB\Delta';
conf_file='C:\MATLAB\DefaultConfig.delta';
product_dir='C:\MATLAB\Product';
appendClassPath(DeltaSimulinkJARPath);
warning off MATLAB:dispatcher:nameConflict