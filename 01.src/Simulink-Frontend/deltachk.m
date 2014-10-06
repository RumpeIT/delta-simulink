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
function deltachk()
   global ckMode;
   
   mymodus     = ckMode;
  
   %TODO Define light Blue see: for tutorial
   mapObj = containers.Map({'add','remove','modify','replace','reset'}, {'green','red','blue','orangeWhite','black'});
   blocks=find_system( 'FindAll','on', 'Type', 'Block');
   lines=find_system( 'FindAll','on', 'Type', 'Line');
   if(strcmp(mymodus,'delta')==1)
    deltafyModel(blocks,lines);
   elseif (strcmp(mymodus,'normal')==1)
   normalizeModel(blocks,lines);
   
   end
end