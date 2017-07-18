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
function appendClassPath( newEntries )
  jloader = com.mathworks.jmi.ClassLoaderManager.getClassLoaderManager;
  jloader.setEnabled(1);
  com.mathworks.jmi.OpaqueJavaInterface.enableClassReloading(1);
  oldPath = toStringArray(jloader.getClassPath);
  newPath = unique([oldPath, newEntries]);
  
  for i = 1 : length(newPath)
    fprintf('appending to java class path: %s\n', newPath{i});
  end
  
  
  jloader.setClassPath(newPath);
  jloader.load();
end


function result = toStringArray(javaArray)
%toStringArray convert an java.lang.String[] array to a cell array containing matlab strings.
%
  
  if isempty(javaArray)
    result = {};
    return;
  end
  result = cell(1, javaArray.length);
  for i=1:javaArray.length
    result{i} = char(javaArray(i));
  end
  
end
