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
classdef BlockDat< handle
    %BLOCKDAT Summary of this class goes here
    %   Detailed explanation goes here
    
    properties
        origColor
        opName
        lineMap
    end
    
    methods
        function obj = BlockDat(origColor,opName)
         obj.origColor = origColor;
         obj.opName=opName;
         obj.lineMap=containers.Map('KeyType','char','ValueType','any');
      end
    end
    
end

