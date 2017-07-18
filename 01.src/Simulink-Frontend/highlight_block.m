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

function highlight_block(block_id)
mapObj = containers.Map({'add','remove','modify','replace','reset'}, {'green','red','blue','orangeWhite','black'});
 uDat=get_param(block_id,'UserData');
 opName=uDat.opName;
 %hilite_system(block_id, mapObj(opName));
 set_param(block_id,'HiliteAncestors', mapObj(opName));
 parent=get_param(block_id, 'Parent');
 while not(strcmp(get_param(parent, 'Type') ,'block_diagram')) && not(isempty(get_param(parent,'UserData')))
    uDat=get_param(parent,'UserData');
    opName=uDat.opName;
    set_param(parent,'HiliteAncestors', mapObj(opName));
    %hilite_system(parent, mapObj(opName));
    parent=get_param(parent, 'Parent');
 end
end
