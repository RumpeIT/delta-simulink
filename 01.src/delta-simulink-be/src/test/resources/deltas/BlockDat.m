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

